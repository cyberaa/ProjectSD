package common.rmi;

import common.ShareInfo;
import common.TransactionInfo;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: joaonuno
 * Date: 10/12/13
 * Time: 6:33 PM
 * To change this template use File | Settings | File Templates.
 */
public interface RemoteTransactions extends Remote
{
	public void setShareValue(int user_id, int idea_id, double new_value) throws RemoteException, SQLException;

	public int buyShares(int user_id, int idea_id, int share_num, double price_per_share, double new_price_share, boolean fromQueue) throws RemoteException, SQLException, NotEnoughCashException, NotEnoughSharesException;

	public ArrayList<ShareInfo> getShares(int idea_id) throws RemoteException, SQLException;

	public ArrayList<TransactionInfo> showHistory(int user_id, int idea_id) throws RemoteException, SQLException;

    public ArrayList<TransactionInfo> showIdeaHistory(int idea_id) throws RemoteException, SQLException;

	public void takeover(int user_id, int idea_id) throws RemoteException, SQLException;
}
