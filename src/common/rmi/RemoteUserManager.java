package common.rmi;

import common.UserInfo;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: joaonuno
 * Date: 10/12/13
 * Time: 7:08 PM
 * To change this template use File | Settings | File Templates.
 */
public interface RemoteUserManager extends Remote
{
	public UserInfo authenticate(String name, String pass) throws RemoteException, UserAuthenticationException, SQLException;

	public void register(String name, String pass) throws RemoteException, ExistingUserException, SQLException;

    public double getMoney(int userId) throws SQLException, RemoteException;

    public UserInfo authenticateFace(String user_face, String token, String username) throws UserAuthenticationException, SQLException, RemoteException;

    public UserInfo registerNewFace(String user_face, String token, String username) throws SQLException, RemoteException;

	public void registerNotificationCallback(int user_id, RemoteNotifications nots) throws RemoteException;
}
