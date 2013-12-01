package action;

import common.rmi.ExistingTopicException;

import java.rmi.RemoteException;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: joaosimoes
 * Date: 11/28/13
 * Time: 4:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class SubmitTopic extends User {

    private String topicText;

    private String responseTopic;

    public String execute() {
        try {
            user.submitTopic(topicText);
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
}
