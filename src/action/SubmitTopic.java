package action;

import common.TopicInfo;
import common.rmi.ExistingTopicException;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: joaosimoes
 * Date: 11/28/13
 * Time: 4:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class SubmitTopic extends User {

    private String topicText;
    private ArrayList<TopicInfo> topics;

    private String responseTopic;

    public String execute() {
        super.execute();
        try {
            user.submitTopic(topicText);
            topics = user.showTopics();
        } catch (ExistingTopicException e) {
            responseTopic = "topicExists";
            return SUCCESS;
        } catch (RemoteException e) {
            responseTopic = "rmi";
            return SUCCESS;
        } catch (SQLException e) {
            responseTopic = "rmi";
            return SUCCESS;
        }
        responseTopic = "success";
        return  SUCCESS;
    }

    public void setTopicText(String topicText) {
        this.topicText = topicText;
    }

    public String getresponseTopic() {
        return this.responseTopic;
    }

    public ArrayList<TopicInfo> getTopics() {
        return topics;
    }

    public void setTopics(ArrayList<TopicInfo> topics) {
        this.topics = topics;
    }
}
