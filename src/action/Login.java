package action;

import com.opensymphony.xwork2.ActionSupport;
import common.rmi.UserAuthenticationException;
import model.UserInfo;
import org.apache.struts2.interceptor.SessionAware;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: joaosimoes
 * Date: 11/23/13
 * Time: 8:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class Login extends Client {

    private String username;
    private String password;

    @Override
    public String execute() {
        System.out.print(username+"   "+password);
        super.execute();
        try {
            user.authenticateUser(username,password);
        } catch (Exception e) {
            System.out.println("Error 3" + e);
            return ERROR;
        }
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
}
