package common;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: joaosimoes
 * Date: 11/30/13
 * Time: 3:03 AM
 * To change this template use File | Settings | File Templates.
 */
public class UserInfo implements Serializable {


    private static final long serialVersionUID = 7964236110099079596L;
    private int userId;
    private String username;

    public UserInfo(int userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
