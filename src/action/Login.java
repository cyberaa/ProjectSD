package action;

import common.rmi.UserAuthenticationException;

import java.rmi.RemoteException;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: joaosimoes
 * Date: 11/23/13
 * Time: 8:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class Login extends User {

    private String username;
    private String password;

    private String responseLogin;

    @Override
    public String execute() {
        System.out.print(username+"   "+password);
        super.execute();
        System.out.println("Sai");
        try {
            user.authenticateUser(username,password);
        } catch (RemoteException e) {
            responseLogin = "rmi";
            System.out.println(responseLogin + e);
            return ERROR;
        } catch (UserAuthenticationException e) {
            responseLogin = "failedAuth";
            System.out.println(responseLogin + e);
            return ERROR;
        } catch (SQLException e) {
            responseLogin = "rmi";
            System.out.println(responseLogin + e);
            return ERROR;
        }
        user.getMoneyFromRMI();
        responseLogin = "success";
        System.out.println(responseLogin);
        return SUCCESS;
    }

    public void setUsername(String username) {
         this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getResponseLogin() {
        return this.responseLogin;
    }
}
