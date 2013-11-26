package model;

import common.rmi.ExistingUserException;
import common.rmi.RemoteUserManager;
import common.rmi.UserAuthenticationException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: joaosimoes
 * Date: 11/23/13
 * Time: 8:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserBean {

    String rmiAddress = "rmi://127.0.0.1:7777/";

    private RemoteUserManager um;
    private int userID;

    public UserBean() {

        try {
            um =  (RemoteUserManager) Naming.lookup(rmiAddress+"UserManager");
        } catch (NotBoundException e) {
            System.out.println("NotBoundException");
        } catch (MalformedURLException e) {
            System.out.println("Malformed");
        } catch (RemoteException e) {
            System.out.println("RemoteException");
        }
        userID = -1;
    }

    public boolean authenticateUser(String username, String password) throws RemoteException, UserAuthenticationException, SQLException {
        int rmiResponse = this.userID = um.authenticate(username,password);
        if(rmiResponse == -1)
            return false;
        else
            return true;
    }

    public void registerUser(String username, String password) throws RemoteException, ExistingUserException, SQLException {
        um.register(username,password);
    }

    public int getUserID() {
        return userID;
    }

}
