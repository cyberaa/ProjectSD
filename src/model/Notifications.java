package model;

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
	protected Notifications() throws RemoteException {}

	public void pushNotification(String message) throws RemoteException
	{
		System.out.println("New message:\n"+message);
	}
}
