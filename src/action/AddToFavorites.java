package action;

import common.IdeaInfo;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: joaosimoes
 * Date: 11/30/13
 * Time: 7:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class AddToFavorites extends User {

    private int ideaId;

    private ArrayList<IdeaInfo> ideas;
    private String topicText;
    private int topicId;

    private String responseWatch;

    public String execute() {
        super.execute();
        try {
            user.addToWatchlist(ideaId);
            ideas = user.showTopicIdeas(topicId);
        } catch (SQLException e) {
            responseWatch = "rmi";
            return SUCCESS;
        } catch (RemoteException e) {
            responseWatch = "rmi";
            return SUCCESS;
        }
        user.getMoneyFromRMI();
        responseWatch = "success";
        return SUCCESS;
    }

    public int getIdeaId() {
        return ideaId;
    }

    public void setIdeaId(int ideaId) {
        this.ideaId = ideaId;
    }

    public String getResponseWatch() {
        return responseWatch;
    }

    public void setResponseWatch(String responseWatch) {
        this.responseWatch = responseWatch;
    }

    public ArrayList<IdeaInfo> getIdeas() {
        return ideas;
    }

    public void setIdeas(ArrayList<IdeaInfo> ideas) {
        this.ideas = ideas;
    }

    public String getTopicText() {
        return topicText;
    }

    public void setTopicText(String topicText) {
        this.topicText = topicText;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }
}
