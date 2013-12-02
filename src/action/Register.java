package action;

/**
 * Created with IntelliJ IDEA.
 * User: joaosimoes
 * Date: 11/26/13
 * Time: 11:32 AM
 * To change this template use File | Settings | File Templates.
 */
public class Register extends User {

    private String username;
    private String password;

    @Override
    public String execute() {
        super.execute();
        System.out.println(username+"   "+password);
        try {
            user.registerUser(username,password);
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
