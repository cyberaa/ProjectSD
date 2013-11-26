package action;

/**
 * Created with IntelliJ IDEA.
 * User: joaosimoes
 * Date: 11/26/13
 * Time: 11:32 AM
 * To change this template use File | Settings | File Templates.
 */
public class Register extends Client {

    private String username;
    private String password;

    @Override
    public String execute() {
        super.execute();
        try {
            user.authenticateUser(username,password);
        } catch (Exception e) {
            System.out.println("Error 3" + e);
            return ERROR;
        }
        return SUCCESS;
    }
}
