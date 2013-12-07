package action;

import common.IdeaInfo;
import common.rmi.NotFullOwnerException;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: joaosimoes
 * Date: 12/7/13
 * Time: 1:47 AM
 * To change this template use File | Settings | File Templates.
 */
public class DeleteIdea extends User {

    private int ideaId;
    private ArrayList<IdeaInfo> ideas;

    public String execute() {
        super.execute();
        try {
            user.deleteIdea(ideaId);
            ideas = user.portfolio();
        } catch (NotFullOwnerException e) {
            return ERROR;
        } catch (RemoteException e) {
            return ERROR;
        } catch (SQLException e) {
            return ERROR;
        }
        user.getMoneyFromRMI();
        return SUCCESS;
    }

    public int getIdeaId() {
        return ideaId;
    }

    public void setIdeaId(int ideaId) {
        this.ideaId = ideaId;
    }

    public ArrayList<IdeaInfo> getIdeas() {
        return ideas;
    }

    public void setIdeas(ArrayList<IdeaInfo> ideas) {
        this.ideas = ideas;
    }
}
