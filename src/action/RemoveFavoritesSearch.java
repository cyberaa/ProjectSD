package action;

import common.IdeaInfo;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: joaosimoes
 * Date: 12/7/13
 * Time: 8:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class RemoveFavoritesSearch extends User {

    private ArrayList<IdeaInfo> ideas;
    private int ideaId;
    private String key;

    public String execute() {
        super.execute();
        try {
            user.removeFromWatchlist(ideaId);
            ideas = user.searchIdea(key);
        } catch (SQLException e) {
            return ERROR;
        } catch (RemoteException e) {
            return ERROR;
        }
        user.getMoneyFromRMI();
        return SUCCESS;
    }

    public ArrayList<IdeaInfo> getIdeas() {
        return ideas;
    }

    public void setIdeas(ArrayList<IdeaInfo> ideas) {
        this.ideas = ideas;
    }

    public int getIdeaId() {
        return ideaId;
    }

    public void setIdeaId(int ideaId) {
        this.ideaId = ideaId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
