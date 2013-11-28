package action;

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

    private String response;

    @Override
    public String execute() {
        System.out.print(username+"   "+password);
        super.execute();
        try {
            user.authenticateUser(username,password);
        } catch (Exception e) {
            System.out.println("Error 3" + e);
            response = "error";
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
