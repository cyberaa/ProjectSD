package action;

import com.opensymphony.xwork2.ActionSupport;
import model.UserInfo;
import org.apache.struts2.interceptor.SessionAware;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: joaosimoes
 * Date: 11/25/13
 * Time: 7:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class Client extends ActionSupport implements SessionAware {

    protected Map<String, Object> session;

    protected UserInfo user;

    public void getUserSession() {
        if (!session.containsKey("user")) {
            this.user = new UserInfo();
            session.put("user", user);
        }
        else {
            this.user = (UserInfo) session.get("user");
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
