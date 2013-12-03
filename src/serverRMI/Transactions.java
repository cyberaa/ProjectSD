package serverRMI;

import common.ShareInfo;
import common.TransactionInfo;
import common.rmi.NotEnoughCashException;
import common.rmi.NotEnoughSharesAtDesiredPriceException;
import common.rmi.NotEnoughSharesException;
import common.rmi.RemoteTransactions;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



/**
 * Created with IntelliJ IDEA.
 * User: joaonuno
 * Date: 10/13/13
 * Time: 6:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class Transactions extends UnicastRemoteObject implements RemoteTransactions
{
	public Transactions() throws RemoteException {}

	/**
	 * Changes the value per share of the idea identified by <em>idea_id</em> to new_value.
	 * @param idea_id The identifier of the idea to update.
	 * @param new_value The new share value to have for the idea identified by <em>idea_id</em>.
	 * @throws RemoteException
	 * @throws SQLException
	 */
	public void setShareValue(int user_id, int idea_id, double new_value) throws RemoteException, SQLException
	{
        Connection db = ServerRMI.getConnection();

		PreparedStatement updateValue = null;

		String query = "UPDATE shares SET value = ? WHERE user_id = ? AND idea_id = ?";

		try {
			db.setAutoCommit(false);

			updateValue = db.prepareStatement(query);
			updateValue.setDouble(1, new_value);
			updateValue.setInt(2, user_id);
			updateValue.setInt(3, idea_id);

			updateValue.executeQuery();

			db.commit();

			//Check queue.
			TransactionalTrading.checkQueue(idea_id);
		} catch (SQLException e) {
			System.out.println("\n"+e+"\n");
			if(db != null)
				db.rollback();
			throw e;
		} finally {
			if(updateValue != null)
				updateValue.close();
			db.setAutoCommit(true);
		}

		db.close();
	}

	/**
	 * Buy <em>share_num</em> shares from idea identified by <em>idea_id</em> at
	 * <em>price_per_share</em>. The shares will then be sold at <em>new_price_share</em>.
	 * The new share holder is the user identified by <em>user_id</em>.
	 * @param user_id The id of the user buying shares.
	 * @param idea_id The id of the idea from which shares will be bought.
	 * @param share_num The amount of shares to buy.
	 * @param price_per_share The price to pay for each share.
	 * @param new_price_share The price the new share holder will sell his new shares for.
	 * @throws RemoteException
	 * @throws SQLException
	 * @throws NotEnoughCashException
	 * @throws NotEnoughSharesException
	 * @throws NotEnoughSharesAtDesiredPriceException
	 */
	public int buyShares(int user_id, int idea_id, int share_num, double price_per_share, double new_price_share, boolean fromQueue) throws RemoteException, SQLException, NotEnoughCashException, NotEnoughSharesException
	{
		int ret = -1;
		System.out.println("\nGetting connection...");
        Connection db = ServerRMI.getConnection();
		System.out.println("Got connection: "+db);

		try {
			db.setAutoCommit(false);

			//Check if user has enough cash.
			double userCash = getCash(db, user_id);
			if(userCash < share_num * price_per_share) {
				throw new NotEnoughCashException();
            }
			System.out.println("Has enough cash.");

			//Verify that the idea has enough shares to be bought.
			int amountShares = getNumberShares(db, idea_id);
			if(share_num > amountShares) {
				throw new NotEnoughSharesException();
            }
			System.out.println("Has enough shares.");

			//Get list of idea shares
			ArrayList<ShareInfo> shares = _getShares(db, idea_id);

			System.out.println("Size: "+shares.size());
			for(int k=0; k < shares.size(); k++) //DEBUG
				System.out.println(shares.get(k));

			//Select ideas to be bought and add them to sharesToBuy.
			ArrayList<ShareToBuy> sharesToBuy;
			sharesToBuy = getSharesToBuy(shares, share_num, price_per_share, user_id);

			System.out.println("Size: "+sharesToBuy.size());
			for(int k=0; k < sharesToBuy.size(); k++) //DEBUG
				System.out.println(sharesToBuy.get(k));

			//Remove (or update) all selected shares to be bought.
			int lastIndex = sharesToBuy.size() -1;
			for(int i=0; i < lastIndex; i++)
				deleteShare(db, sharesToBuy.get(i).id);

			ShareToBuy lastShare = sharesToBuy.get(lastIndex);
			if(lastShare.numToBuy == lastShare.total)
				deleteShare(db, lastShare.id);
			else
				updateShare(db, lastShare.id, lastShare.total - lastShare.numToBuy, lastShare.value);

			System.out.println("Shares updated/removed.");

			//Create new share for the buyer or update previous amount of shares.
			ShareInfo aux1;
			boolean updated = false;
			for(int i=0; i < shares.size(); i++)
			{
				aux1 = shares.get(i);
				if(aux1.getUser_id() == user_id)
				{
					updateShare(db, aux1.getId(), aux1.getParts() + share_num, new_price_share);
					updated = true;
					break;
				}
			}
			if(!updated)
				createShare(db, idea_id, user_id, share_num, new_price_share);

			System.out.println("New shares created/Old shares updated.");

			//Give money to sellers and update transaction history.
			ShareToBuy aux2;
			int transactionMoney;
			int totalCash=0, totalShares=0;
			for(int i=0; i < sharesToBuy.size(); i++)
			{
				aux2 = sharesToBuy.get(i);
				transactionMoney = aux2.numToBuy * aux2.value;
				totalCash += transactionMoney;
				totalShares += aux2.numToBuy;

				//Give money to sellers.
				giveOrTakeUserCash(db, aux2.user_id, transactionMoney, true);
				//Update transaction history.
				createTransaction(db, idea_id, aux2.user_id, user_id, aux2.numToBuy, transactionMoney);

				//Create and store notification.
				ServerRMI.notifications.insertNotification(db, user_id, ServerRMI.notifications.createNotificationString(db, idea_id, aux2.user_id, user_id, aux2.numToBuy, transactionMoney));
				ServerRMI.notifications.insertNotification(db, aux2.user_id, ServerRMI.notifications.createNotificationString(db, idea_id, aux2.user_id, user_id, aux2.numToBuy, transactionMoney));
            }

			System.out.println("Money given to sellers and transaction history updated.");

			//Remove money from buyer.
			giveOrTakeUserCash(db, user_id, totalCash, false);

			System.out.println("Money taken from buyer.");

			//Transactional trading stuff.
			if(!fromQueue)
			{
				if(totalShares < share_num)
				{
					System.out.println("Queueing...");
					TransactionalTrading.enqueue(db, user_id, idea_id, share_num-totalShares, price_per_share, price_per_share);
				}

				if(totalShares != 0)
				{
					System.out.println("Checking queue...");
					TransactionalTrading.checkQueue(idea_id);
				}
			}
			else
			{
				ret = share_num-totalShares;
			}

			System.out.println("Transactional trading finished.");

			db.commit();
			System.out.println("Changes committed.");
		} catch (SQLException e) {
			System.out.println(e);
			if(db != null)
				db.rollback();
			throw e;
		} finally {
			db.setAutoCommit(true);
			db.close();

			return ret;
		}
	}

	/**
	 *
	 * @param user_id The user identifier.
	 * @return The amount of cash the user identified by <em>user_id</em> has available.
	 * @throws SQLException
	 */
	protected double getCash(Connection db, int user_id) throws SQLException
	{
		int tries = 0;
		int maxTries = 3;
		PreparedStatement gCash = null;
		ResultSet rs;

		String cash = "SELECT cash FROM sduser WHERE id = ?";

		while(tries < maxTries)
		{
			try {
				gCash = db.prepareStatement(cash);
				gCash.setInt(1, user_id);

				rs = gCash.executeQuery();

				if(rs.next())
					return rs.getInt("cash");
			} catch (SQLException e) {
				System.out.println(e);
				if(tries++ > maxTries)
					throw e;
			} finally {
				if(gCash != null)
					gCash.close();
			}
		}

		return 0;
	}

	/**
	 * Query the database for all the shares of the idea identified by <em>idea_id</em> and return them
	 * in a <em>ShareInfo</em> <em>ArrayList</em>.
	 * @param idea_id The identifier of the idea whose shares will be returned.
	 * @return Shares of the idea identified by <em>idea_id</em>.
	 * @throws RemoteException
	 * @throws SQLException
	 */
	protected ArrayList<ShareInfo> _getShares(Connection db, int idea_id) throws RemoteException, SQLException
	{
        int tries = 0;
		int maxTries = 3;
		PreparedStatement gShares = null;
		ResultSet rs;
		ArrayList<ShareInfo> ret = new ArrayList<ShareInfo>();

		String shares = "SELECT * FROM idea_share WHERE idea_id = ? ORDER BY value ASC";

		while(tries < maxTries)
		{
			try {
				gShares = db.prepareStatement(shares);
				gShares.setInt(1, idea_id);

				rs = gShares.executeQuery();

				while(rs.next())
				{
					ret.add(new ShareInfo(rs.getInt("id"), rs.getInt("idea_id"), rs.getInt("user_id"), rs.getInt("parts"), rs.getInt("value"), ""));
				}

				break;
			} catch (SQLException e) {
				if(tries++ > maxTries)
					throw e;
			} finally {
				if(gShares != null)
					gShares.close();
			}
		}

		return ret;
	}

	/**
	 * Get the number of share parts of the idea identified by <em>idea_id</em>.
	 * @param idea_id The identifier of the idea whose number of parts we want to retrieve.
	 * @return The number of parts of the idea identified by <em>idea_id</em>.
	 * @throws SQLException
	 */
	protected int getNumberShares(Connection db, int idea_id) throws SQLException
	{
		int tries = 0;
		int maxTries = 3;
		PreparedStatement gParts = null;
		ResultSet rs;

		String parts = "SELECT number_parts FROM idea WHERE id = ?";

        try {
            gParts = db.prepareStatement(parts);
            gParts.setInt(1, idea_id);

            rs = gParts.executeQuery();

            if(rs.next()) {
                int x = rs.getInt("number_parts");
                return x;
            }
        } catch (SQLException e) {
            System.out.println(e);
            if(tries++ > maxTries)
                throw e;
        } finally {
            if(gParts != null)
                gParts.close();;

        }

		return 0;
	}

	/**
	 * Parses a list of shares to decide which shares will be bought. It is already
	 * assumed the buyer has enough money to buy the amount of shares at the desired
	 * price. Deals with the possibility of not existing enough shares being sold
	 * for the same or less price the buyer desires by throwing an exception.
	 * @param shares <em>ArrayList</em> containing all the shares of an idea.
	 * @param share_num Number of shares to select from <em>shares</em>.
	 * @param price_per_share The price the buyer is willing to py for each share.
	 * @return <em>ArrayList</em> containing all the shares to be bought.
	 * @throws NotEnoughSharesAtDesiredPriceException
	 */
	protected ArrayList<ShareToBuy> getSharesToBuy(ArrayList<ShareInfo> shares, int share_num, double price_per_share, double user_id) throws NotEnoughSharesException
	{
        ShareInfo aux;
		int auxPrice, auxNum;
		int counterShares = 0;
		ArrayList<ShareToBuy> sharesToBuy = new ArrayList<ShareToBuy>();
		for(int i = 0; i < shares.size(); i++)
		{
			aux = shares.get(i);

			//User cannot buy shares from himself.
			if(aux.getUser_id() == user_id)
				continue;

			auxPrice = aux.getValue();
			auxNum = aux.getParts();

			//Verify that shares can be bought at desired price.
			if(price_per_share >= auxPrice)
			{
				if(counterShares + auxNum <= share_num) //Need more shares than the ones this user has.
				{
					sharesToBuy.add(new ShareToBuy(aux.getId(), aux.getUser_id(), aux.getParts(), aux.getParts(), aux.getValue()));
					counterShares += aux.getParts();

					if(counterShares == share_num)
						break;
				}
				else //With the amount of shares this user has the original request can be fulfilled.
				{
					sharesToBuy.add(new ShareToBuy(aux.getId(), aux.getUser_id(), share_num - counterShares, aux.getParts(), aux.getValue()));
					counterShares += share_num - counterShares;

					break;
				}
			}
			else
				break;
		}

		if(counterShares != share_num)
			throw new NotEnoughSharesException();

		return sharesToBuy;
	}

	/**
	 * Deletes the share identified by <em>id</em>.
	 * <p>NOTE: this function does not commit the changes to the database!</p>
	 * @param id The identifier of the share to delete.
	 * @throws SQLException
	 */
	protected void deleteShare(Connection db, int id) throws SQLException
	{
		int tries = 0;
		int maxTries = 3;
		PreparedStatement dShare = null;

		String share = "DELETE FROM idea_share WHERE id = ?";

		while(tries < maxTries)
		{
			try {
				dShare = db.prepareStatement(share);
				dShare.setInt(1, id);

				dShare.executeQuery();
				break;
			} catch (SQLException e) {
				System.out.println(e);
				if(tries++ > maxTries)
					throw e;
			} finally {
				if(dShare != null)
					dShare.close();
			}
		}
	}

	/**
	 * Updates the amount of parts a user has of the share identified by <em>id</em>.
	 * <p>NOTE: this function does not commit the changes to the database!</p>
	 * @param id The identifier of the share to update.
	 * @param newNumParts The number of parts of the idea the user will now have.
	 * @throws SQLException
	 */
	protected void updateShare(Connection db, int id, int newNumParts, double value) throws SQLException
	{
		int tries = 0;
		int maxTries = 3;
		PreparedStatement uShare = null;

		String share = "UPDATE idea_share SET parts = ?, value = ? WHERE id = ?";

		while(tries < maxTries)
		{
			try {
				uShare = db.prepareStatement(share);
				uShare.setInt(1, newNumParts);
				uShare.setDouble(2, value);
				uShare.setInt(3, id);

				uShare.executeQuery();
				break;
			} catch (SQLException e) {
				System.out.println("updateShare():\n"+e);
				if(tries++ > maxTries)
					throw e;
			} finally {
				if(uShare != null)
					uShare.close();
			}
		}
	}

	/**
	 * Creates a new share with the given values.
	 * <p>NOTE: this function does not commit the changes to the database!</p>
	 * @param idea_id The id of the idea the share is of.
	 * @param user_id The id of the user the shares belong to.
	 * @param share_num The number of parts of the idea the user has.
	 * @param price The price at which the shares will be sold.
	 * @throws SQLException
	 */
	public void createShare(Connection db, int idea_id, int user_id, int share_num, double price) throws SQLException
	{
        int tries = 0;
		int maxTries = 3;
		PreparedStatement cShare = null;

		String share = "INSERT INTO idea_share VALUES (seq_idea_share.nextval, ?, ?, ?, ?)";

		while(tries < maxTries)
		{
			try {
				cShare = db.prepareStatement(share);
				cShare.setInt(1, idea_id);
				cShare.setInt(2, user_id);
				cShare.setInt(3, share_num);
				cShare.setDouble(4, price);

				cShare.executeQuery();
				break;
			} catch (SQLException e) {
				System.out.println(e);
				if(tries++ > maxTries)
					throw e;
			} finally {
				if(cShare != null)
					cShare.close();
			}
		}
	}

	/**
	 * Removes <em>money</em> amount of cash from user identified by <em>user_id</em>.
	 * <p>NOTE: this function does not commit the changes to the database!</p>
	 * @param user_id The id of the user from whom to withdraw cash.
	 * @param money The amount of cash to withdraw.
	 * @throws SQLException
	 */
	protected void giveOrTakeUserCash(Connection db, int user_id, int money, boolean give) throws SQLException
	{
        //Get current cash.
		int curCash = 0;
		int tries = 0, maxTries = 3;
		PreparedStatement gotCash = null;
		ResultSet rs;

		String cash = "SELECT cash FROM sduser WHERE id = ?";

		while(tries < maxTries)
		{
			try {
				gotCash = db.prepareStatement(cash);
				gotCash.setInt(1, user_id);

				rs = gotCash.executeQuery();
				if(rs.next())
				{
					curCash = rs.getInt("cash");
					break;
				}
			} catch (SQLException e) {
				System.out.println(e);
				if(tries++ > maxTries)
					throw e;
			} finally {
				if(gotCash != null)
					gotCash.close();
			}
		}

		//Update cash.
		if(give)
			updateUserCash(db, user_id, curCash + money);
		else
			updateUserCash(db, user_id, curCash - money);
	}

	/**
	 * Updates the user's cash to <em>cash</em>. The user is identified by <em>user_id</em>.
	 * <p>NOTE: this function does not commit the changes to the database!</p>
	 * @param user_id The id of the user whose cash will be updated.
	 * @param cash The new amount of cash the user will have.
	 * @throws SQLException
	 */
	protected void updateUserCash(Connection db, int user_id, int cash) throws SQLException
	{
		int tries = 0;
		int maxTries = 3;
		PreparedStatement uShare = null;

		String share = "UPDATE sduser SET cash = ? WHERE id = ?";

		while(tries < maxTries)
		{
			try {
				uShare = db.prepareStatement(share);
				uShare.setInt(1, cash);
				uShare.setInt(2, user_id);

				uShare.executeQuery();
				break;
			} catch (SQLException e) {
				System.out.println(e);
				if(tries++ > maxTries)
					throw e;
			}
			finally {
				if(uShare != null)
					uShare.close();
			}
		}
	}

	/**
	 * Create a new transaction for the transactions history with the given values.
	 * @param idea_id The id of the idea from which shares where transacted.
	 * @param seller_id The id of the user who sold shares.
	 * @param buyer_id The id of the user who bought shares.
	 * @param share_num The number of shares transacted.
	 * @param transactionMoney The total money involved in the transaction.
	 * @throws SQLException
	 */
	protected void createTransaction(Connection db, int idea_id, int seller_id, int buyer_id, int share_num, int transactionMoney) throws SQLException
	{
		int tries = 0;
		int maxTries = 3;
		PreparedStatement cShare = null;

		String share = "INSERT INTO idea_transaction VALUES (seq_idea_transaction.nextval, ?, ?, ?, ?, ?, systimestamp)";

		while(tries < maxTries)
		{
			try {
				cShare = db.prepareStatement(share);
				cShare.setInt(1, idea_id);
				cShare.setInt(2, seller_id);
				cShare.setInt(3, buyer_id);
				cShare.setInt(4, share_num);
				cShare.setInt(5, transactionMoney);

				cShare.executeQuery();
				break;
			} catch (SQLException e) {
				System.out.println(e);
				if(tries++ > maxTries)
					throw e;
			} finally {
				if(cShare != null)
					cShare.close();
			}
		}
	}

	/**
	 * Query the database for all the shares of the idea identified by <em>idea_id</em> and return them
	 * in a <em>ShareInfo</em> <em>ArrayList</em>.
	 * @param idea_id The identifier of the idea whose shares will be returned.
	 * @return Shares of the idea identified by <em>idea_id</em>.
	 * @throws RemoteException
	 * @throws SQLException
	 */
	public ArrayList<ShareInfo> getShares(int idea_id) throws RemoteException, SQLException
	{
		Connection db = ServerRMI.getConnection();

		int tries = 0;
		int maxTries = 3;
		PreparedStatement gShares = null;
		ResultSet rs;
		ArrayList<ShareInfo> ret = new ArrayList<ShareInfo>();

		String shares = "SELECT idea_share.*, sduser.username FROM idea_share, sduser WHERE idea_share.idea_id = ? AND idea_share.user_id = sduser.id ORDER BY value ASC";

		while(tries < maxTries)
		{
			try {
				gShares = db.prepareStatement(shares);
				gShares.setInt(1, idea_id);

				rs = gShares.executeQuery();

				while(rs.next())
				{
					ret.add(new ShareInfo(rs.getInt("id"), rs.getInt("idea_id"), rs.getInt("user_id"), rs.getInt("parts"), rs.getInt("value"), rs.getString("username")));
				}

				break;
			} catch (SQLException e) {
				if(tries++ > maxTries)
					throw e;
			} finally {
				if(gShares != null)
					gShares.close();
			}
		}

		return ret;
	}

	public ArrayList<TransactionInfo> showHistory(int user_id, int idea_id) throws RemoteException, SQLException
	{
		Connection db = ServerRMI.getConnection();

		ArrayList<TransactionInfo> ret = new ArrayList<TransactionInfo>();

		int tries = 0;
		int maxTries = 3;
		PreparedStatement gTransactions = null;
		ResultSet rs;

		String transactions = "SELECT u1.username as buyer, u2.username as seller, idea_transaction.number_parts, idea_transaction.value FROM sduser u1, sduser u2, idea_transaction, idea WHERE u1.id = idea_transaction.buyer_id AND u2.id = idea_transaction.seller_id AND (seller_id = ? OR buyer_id = ?) AND idea.id = idea_transaction.idea_id AND idea_transaction.idea_id = ?";

		while(tries < maxTries)
		{
			try {
				gTransactions = db.prepareStatement(transactions);
				gTransactions.setInt(1, user_id);
				gTransactions.setInt(2, user_id);
                gTransactions.setInt(3, idea_id);

				rs = gTransactions.executeQuery();

				while(rs.next())
					ret.add(new TransactionInfo(rs.getString("seller"), rs.getString("buyer"), rs.getInt("parts"), rs.getInt("value")));

				break;
			} catch (SQLException e) {
				System.out.println(e);
				if(tries++ > maxTries)
					throw e;
			}
			finally {
				if(gTransactions != null)
					gTransactions.close();
			}
		}

		db.close();

		return ret;
	}

    public ArrayList<TransactionInfo> showIdeaHistory(int idea_id) throws RemoteException, SQLException
    {
        Connection db = ServerRMI.getConnection();

        ArrayList<TransactionInfo> ret = new ArrayList<TransactionInfo>();

        int tries = 0;
        int maxTries = 3;
        PreparedStatement gTransactions = null;
        ResultSet rs;

        String transactions = "SELECT u1.username as buyer, u2.username as seller, idea_transaction.number_parts, idea_transaction.value FROM sduser u1, sduser u2, idea_transaction, idea WHERE u1.id = idea_transaction.buyer_id AND u2.id = idea_transaction.seller_id AND idea.id = idea_transaction.idea_id AND idea_transaction.idea_id = ?";

        while(tries < maxTries)
        {
            try {
                gTransactions = db.prepareStatement(transactions);
                gTransactions.setInt(1, idea_id);

                rs = gTransactions.executeQuery();

                while(rs.next())
                    ret.add(new TransactionInfo(rs.getString("seller"), rs.getString("buyer"), rs.getInt("parts"), rs.getInt("value")));

                break;
            } catch (SQLException e) {
                System.out.println(e);
                if(tries++ > maxTries)
                    throw e;
            }
            finally {
                if(gTransactions != null)
                    gTransactions.close();
            }
        }

        db.close();

        return ret;
    }
}
