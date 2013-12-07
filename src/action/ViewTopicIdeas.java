package action;

import common.IdeaInfo;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: joaosimoes
 * Date: 11/30/13
 * Time: 1:47 AM
 * To change this template use File | Settings | File Templates.
 */
public class ViewTopicIdeas extends User {

    private ArrayList<IdeaInfo> ideas;

    private int topicId;
    private String topicText;

    public String execute() {
        System.out.println("\n\n" + topicId);
        super.execute();
        try {
            ideas = user.showTopicIdeas(topicId);
        } catch (RemoteException e) {
            return ERROR;
        } catch (SQLException e) {
            return ERROR;
        }
        for(int i=0; i<ideas.size(); i++) {
            System.out.println(ideas.get(i).getText());
        }
        user.getMoneyFromRMI();
        return SUCCESS;
    }

    public ArrayList<IdeaInfo> getIdeas() {
        return ideas;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
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
}
