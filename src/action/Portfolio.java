package action;

import common.IdeaInfo;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: joaosimoes
 * Date: 11/28/13
 * Time: 7:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class Portfolio extends User {

    private ArrayList<IdeaInfo> ideas;

    public String execute() {
        super.execute();
        try {
            ideas = user.portfolio();
        } catch (SQLException e) {
            return ERROR;
        } catch (RemoteException e) {
            return ERROR;
        }
        return SUCCESS;
    }

    public ArrayList<IdeaInfo> getIdeas() {
        return ideas;
    }

    public void setIdeas(ArrayList<IdeaInfo> ideas) {
        this.ideas = ideas;
    }
}
