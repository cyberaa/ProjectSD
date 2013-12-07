package action;

import com.opensymphony.xwork2.ActionSupport;
import interceptors.UserAware;
import model.UserBean;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: joaosimoes
 * Date: 11/25/13
 * Time: 7:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class User extends ActionSupport implements SessionAware, UserAware {

    protected Map<String, Object> session;

    protected UserBean user;

    public void getUserSession() {
        this.user = (UserBean) session.get("user");
    }

    @Override
    public void setSession(Map<String, Object> session) {
          this.session = session;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public UserBean getUser() {
        return this.user;
    }

    public String execute() {
        getUserSession();
        System.out.println("Getting Money");
        //this.user.getMoneyFromRMI();
        System.out.println("Money Set");
        return SUCCESS;
    }
}
