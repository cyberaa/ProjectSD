package action;

import common.rmi.UserAuthenticationException;

import java.rmi.RemoteException;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: joaosimoes
 * Date: 12/6/13
 * Time: 12:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class LoginFacebook extends User {

    private String token;

    public String execute() {
        super.execute();
        boolean auth;
        try {
            auth = user.authenticateFacebook(token);
        } catch (UserAuthenticationException e) {
            return ERROR;
        } catch (SQLException e) {
            return ERROR;
        } catch (RemoteException e) {
            return ERROR;
        }
        if(auth == true) {
            user.getMoneyFromRMI();
            return SUCCESS;
        }
        else {
            return ERROR;
        }
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
