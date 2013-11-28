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
public class SubmitTopic extends Client {

    private String topicText;

    private String response;

    public String execute() {
        super.execute();
        try {
            user.submitTopic(topicText);
        } catch (ExistingTopicException e) {
            response = "topicExists";
            return SUCCESS;
        } catch (RemoteException e) {
            response = "rmi";
            return SUCCESS;
        } catch (SQLException e) {
            response = "rmi";
            return SUCCESS;
        }
        response = "success";
        return  SUCCESS;
    }

    public void setTopicText(String topicText) {
        this.topicText = topicText;
    }

    public String getResponse() {
        return this.response;
    }
}
