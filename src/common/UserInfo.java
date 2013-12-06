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
    private int root;
    private double money;

    public UserInfo(int userId, String username, int root, double money) {
        this.userId = userId;
        this.username = username;
        this.root = root;
        this.money = money;
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

    public int getRoot() {
        return root;
    }

    public void setRoot(int root) {
        this.root = root;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
