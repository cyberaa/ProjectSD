package common.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created with IntelliJ IDEA.
 * User: joaonuno
 * Date: 12/6/13
 * Time: 4:17 PM
 * To change this template use File | Settings | File Templates.
 */
public interface RemoteNotifications extends Remote
{
	public void pushNotification(String message, boolean isNotif) throws RemoteException;
}
