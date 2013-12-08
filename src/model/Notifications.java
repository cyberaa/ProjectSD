package model;

import action.WSocketClient;
import common.rmi.RemoteNotifications;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created with IntelliJ IDEA.
 * User: joaonuno
 * Date: 12/6/13
 * Time: 4:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class Notifications extends UnicastRemoteObject implements RemoteNotifications
{
    private WSocketClient socket;

	public Notifications(WSocketClient socket) throws RemoteException {
        this.socket = socket;
    }

	/**
	 *
	 * @param message
	 * @param isNotif True if message is a buy/sell notification. False if it is an update
	 *                on the market value of an idea.
	 * @throws RemoteException
	 */
	public void pushNotification(String message, boolean isNotif) throws RemoteException
	{
		System.out.println("New message:\n"+message);
        socket.notify(message, isNotif);
	}
}
