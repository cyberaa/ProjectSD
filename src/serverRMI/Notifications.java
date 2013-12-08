package serverRMI;

import common.NotificationInfo;
import common.rmi.RemoteNotifications;

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
 * Date: 10/22/13
 * Time: 10:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class Notifications
{
	public Notifications(){}

	/**
	 * Insert a notification into the database. Notifications are
	 * fetched and delivered later.
	 * @param user_id The id of the user to whom the notification will be sent.
	 * @param not The notification itself.
	 * @throws RemoteException
	 * @throws SQLException
	 */
	public void insertNotification(Connection db, int user_id, String not) throws SQLException
	{
		System.out.println("Entered insertNotification.");
		RemoteNotifications nots = ServerRMI.userNotifications.get(user_id);
		System.out.println("Got remote object from hash table.");
		if(nots != null) {
            System.out.println("[WS] Will push notification.");
			try {
				nots.pushNotification(not);
			} catch (RemoteException e) {
				e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
			}
			System.out.println("[WS] Notification pushed!");
        }
		else
		{
			System.out.println("[DB] Will push notification.");
			PreparedStatement insert = null;

			String query = "INSERT INTO notification VALUES (seq_notification.nextval, ?, ?)";

			try {
				insert = db.prepareStatement(query);
				insert.setInt(1, user_id);
				insert.setString(2, not);

				insert.executeQuery();
			} catch (SQLException e) {
				System.out.println(e);
				if(db != null)
					db.rollback();
			} finally {
				if(insert != null)
					insert.close();
			}
			System.out.println("[DB] Notification pushed!");
		}
	}

	/**
	 * Fetch all notifications which
	 * @param user_id
	 * @return
	 * @throws RemoteException
	 * @throws SQLException
	 */
	public ArrayList<NotificationInfo> getNotifications(int user_id) throws SQLException
	{
		ArrayList<NotificationInfo> ret = new ArrayList<NotificationInfo>();

		Connection db = ServerRMI.getConnection();

		PreparedStatement getNotifications = null;
		String query = "SELECT * FROM notification WHERE user_id = ?";
		ResultSet rs = null;

		try {
			getNotifications = db.prepareStatement(query);
			getNotifications.setInt(1, user_id);

			rs = getNotifications.executeQuery();

			while(rs.next())
				ret.add(new NotificationInfo(rs.getInt("id"), user_id, rs.getString("text")));

		} finally {
			if(getNotifications != null)
				getNotifications.close();

			db.close();
		}

		return ret;
	}

	/**
	 * Remove the given notifications from the database.
	 * @param not_ids The notifications to remove.
	 * @throws RemoteException
	 * @throws SQLException
	 */
	public void removeNotifications(ArrayList<NotificationInfo> not_ids) throws  SQLException
	{
		PreparedStatement remove = null;
		String query = "DELETE FROM notification WHERE id = ?";

		Connection db = ServerRMI.getConnection();
		db.setAutoCommit(false);

		try {
			for(int i=0; i < not_ids.size(); i++)
			{
				remove = db.prepareStatement(query);
				remove.setInt(1, not_ids.get(i).id);

				remove.executeQuery();
			}

			db.commit();
		} catch (SQLException e) {
			System.out.println(e);
			if(db != null)
				db.rollback();
		} finally {
			if(remove != null)
				remove.close();

			db.setAutoCommit(true);
			db.close();
		}
	}


	/**
	 * Create a notification string describing a transaction.
	 * @param idea_id The id of the idea.
	 * @param seller_id The seller id.
	 * @param buyer_id The buyer id.
	 * @param parts The number of parts of the idea sold.
	 * @param totalPrice The total amount of money involved in the transaction.
	 * @return A <em>String</em> describing the transaction.
	 */
	public String createNotificationString(Connection db, int idea_id, int seller_id, int buyer_id, int parts, double totalPrice) throws SQLException
	{
		System.out.println("Entered create notification.");
		String buyer, seller;

		PreparedStatement getNotifications = null;
		String query = "SELECT username FROM sduser WHERE id = ?";
		ResultSet rs;

		try {
			getNotifications = db.prepareStatement(query);

			//Get buyer's username.
			getNotifications.setInt(1, buyer_id);
			rs = getNotifications.executeQuery();

			rs.next();
			buyer = rs.getString("username");

			//Get seller's username.
			getNotifications.setInt(1, seller_id);
			rs = getNotifications.executeQuery();

			rs.next();
			seller = rs.getString("username");

		} finally {
			if(getNotifications != null)
				getNotifications.close();
		}

		System.out.println("Notification created.");
		return buyer + " bought " + parts + " from " + seller + " (idea " + idea_id +") for a total of " + totalPrice + " coins.";
	}

	public static void broadcastMessage(String message) throws RemoteException
	{
		for(int i=0; i < ServerRMI.users.size(); i++)
			ServerRMI.userNotifications.get(ServerRMI.users.get(i)).pushNotification(message);
	}
}
