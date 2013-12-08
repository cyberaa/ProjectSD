package serverTCP;

import common.rmi.RemoteNotifications;

import java.rmi.RemoteException;

/**
 * Created with IntelliJ IDEA.
 * User: joaonuno
 * Date: 12/7/13
 * Time: 2:16 AM
 * To change this template use File | Settings | File Templates.
 */
public class Notifications implements RemoteNotifications
{
	public void pushNotification(String message, boolean isNotif) throws RemoteException
	{
		System.out.println("\nNew notification:\n"+message);
	}
}
