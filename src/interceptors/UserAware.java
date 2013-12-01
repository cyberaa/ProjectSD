package interceptors;

import model.UserBean;

/**
 * Created with IntelliJ IDEA.
 * User: joaosimoes
 * Date: 11/29/13
 * Time: 10:48 AM
 * To change this template use File | Settings | File Templates.
 */
public interface UserAware {

    public void setUser(UserBean user);

    public UserBean getUser();
}
