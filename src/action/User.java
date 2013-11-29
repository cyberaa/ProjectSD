package action;

import com.opensymphony.xwork2.ActionSupport;
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
public class User extends ActionSupport implements SessionAware {

    protected Map<String, Object> session;

    protected UserBean user;

    public void getUserSession() {
        if (!session.containsKey("user")) {
            this.user = new UserBean();
            session.put("user", user);
        }
        else {
            this.user = (UserBean) session.get("user");
        }
    }

    @Override
    public void setSession(Map<String, Object> session) {
          this.session = session;
    }

    public String execute() {
        getUserSession();
        return SUCCESS;
    }
}
