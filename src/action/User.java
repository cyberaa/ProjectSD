package action;

import com.opensymphony.xwork2.ActionSupport;
import interceptors.UserAware;
import model.UserBean;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: joaosimoes
 * Date: 11/25/13
 * Time: 7:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class User extends ActionSupport implements SessionAware, UserAware, ServletResponseAware,
        ServletRequestAware {

    private static final String COOKIE_NAME = "ClientCookie";

    protected Map<String, Object> session;

    protected UserBean user;

    protected HttpServletResponse servletResponse;
    protected HttpServletRequest servletRequest;

    public void getUserSession() {
        this.user = (UserBean) session.get("user");
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    public void writeUserCookie() {
        System.out.println("User Id:" + user.getUserID());
        Cookie c = new Cookie(COOKIE_NAME, Integer.toString(user.getUserID()));
        System.out.println("Cookie created");
        System.out.println(servletResponse);
        c.setMaxAge(60*60*24*365);
        servletResponse.addCookie(c);
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public UserBean getUser() {
        return this.user;
    }

    public Map<String, Object> getSession() {
        return session;
    }

    public HttpServletResponse getServletResponse() {
        return servletResponse;
    }

    @Override
    public void setServletResponse(HttpServletResponse servletResponse) {
        System.out.println("Setting");
        this.servletResponse = servletResponse;
    }

    public HttpServletRequest getServletRequest() {
        return servletRequest;
    }

    @Override
    public void setServletRequest(HttpServletRequest servletRequest) {
        this.servletRequest = servletRequest;
    }

    public String execute() {
        getUserSession();
        System.out.println("Getting Money");
        //this.user.getMoneyFromRMI();
        System.out.println("Money Set");
        return SUCCESS;
    }
}
