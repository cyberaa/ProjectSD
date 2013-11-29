package action;

import common.TopicInfo;

/**
 * Created with IntelliJ IDEA.
 * User: joaosimoes
 * Date: 11/28/13
 * Time: 7:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class SearchTopic extends User {

    private String topicKey;
    private TopicInfo[] topics;

    public String execute() {
        return SUCCESS;
    }

    public TopicInfo[] getTopics() {
        return topics;
    }

    public void setTopicKey(String topicKey) {

        this.topicKey = topicKey;
    }
}
