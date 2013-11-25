package action;

import com.opensymphony.xwork2.ActionSupport;
import model.UserInfo;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: joaosimoes
 * Date: 11/23/13
 * Time: 8:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class Login extends ActionSupport implements SessionAware {

    private String username;
    private String password;
    private Map<String, Object> session;

    @Override
    public String execute() {

        return SUCCESS;
    }

    public void setLogin(UserInfo user) {
         this.session.put("user", user);
    }

    public UserInfo getLogin() {
        return (UserInfo) this.session.get("user");
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

    @Override
    public void setSession(Map<String, Object> session) {
         this.session = session;
    }
}
