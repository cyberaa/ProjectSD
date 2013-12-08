package action;

import common.TopicInfo;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: joaosimoes
 * Date: 11/29/13
 * Time: 10:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class ListTopics extends User {

    private ArrayList<TopicInfo> topics;

    private String responseShowTopics;

    public String execute() {
        System.out.println("Entrei Topicos");
        super.execute();
        System.out.println("Passei Execute");
        try {
            topics = user.showTopics();
        } catch (Exception e) {
            return LOGIN;
        }
        for(int i=0; i<topics.size(); i++) {
            System.out.println(topics.get(i).text);
        }
        user.getMoneyFromRMI();
        super.writeUserCookie();
        return SUCCESS;
    }

    public ArrayList<TopicInfo> getTopics() {
        return this.topics;
    }

    public String getResponseShowTopics() {
        return this.responseShowTopics;
    }
}
