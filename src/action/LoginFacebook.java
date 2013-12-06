package action;

/**
 * Created with IntelliJ IDEA.
 * User: joaosimoes
 * Date: 12/6/13
 * Time: 12:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class LoginFacebook extends User {

    private String token;

    public String execute() {
        super.execute();
        boolean auth = user.authenticateFacebook(token);
        if(auth == true) {
            return SUCCESS;
        }
        else {
            return ERROR;
        }
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
