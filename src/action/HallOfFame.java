package action;

import common.IdeaInfo;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: joaosimoes
 * Date: 11/28/13
 * Time: 7:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class HallOfFame extends User {

    private ArrayList<IdeaInfo> ideas;

    public String execute() {
        super.execute();
        try {
            ideas = user.hallOfFame();
        } catch (RemoteException e) {
            return LOGIN;
        } catch (SQLException e) {
            return LOGIN;
        }
        user.getMoneyFromRMI();
        super.writeUserCookie();
        return SUCCESS;
    }

    public ArrayList getIdeas() {
        return this.ideas;
    }

}
