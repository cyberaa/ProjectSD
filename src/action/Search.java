package action;

import common.IdeaInfo;
import common.TopicInfo;

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
public class Search extends User {

    private String searchKey;
    private ArrayList<IdeaInfo> ideas;
    private ArrayList<TopicInfo> topics;
    private String wat;

    public String execute() {
        super.execute();
        if(wat.equals("Idea")) {
            System.out.println("Entrei ideia");
            try {
                ideas = user.searchIdea(searchKey);
            } catch (SQLException e) {
                return ERROR;
            } catch (RemoteException e) {
                return ERROR;
            }
            user.getMoneyFromRMI();
            return "idea";
        }
        else {
            try {
                topics = user.searchTopic(searchKey);
            } catch (SQLException e) {
                return ERROR;
            } catch (RemoteException e) {
                return ERROR;
            }
            user.getMoneyFromRMI();
            return "topic";
        }
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public ArrayList<IdeaInfo> getIdeas() {
        return ideas;
    }

    public void setIdeas(ArrayList<IdeaInfo> ideas) {
        this.ideas = ideas;
    }

    public ArrayList<TopicInfo> getTopics() {
        return topics;
    }

    public void setTopics(ArrayList<TopicInfo> topics) {
        this.topics = topics;
    }

    public String getWat() {
        return wat;
    }

    public void setWat(String wat) {
        this.wat = wat;
    }
}
