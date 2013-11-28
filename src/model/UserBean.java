package model;

import common.IdeaInfo;
import common.TopicInfo;
import common.rmi.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: joaosimoes
 * Date: 11/23/13
 * Time: 8:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserBean {

    String rmiAddress = "rmi://127.0.0.1:7777/";

    private RemoteUserManager um;
    private RemoteIdeas ideas;
    private RemoteTopics topics;
    private int userID;

    public UserBean() {

        try {
            um =  (RemoteUserManager) Naming.lookup(rmiAddress+"UserManager");
            ideas = (RemoteIdeas) Naming.lookup(rmiAddress+"Ideas");
            topics= (RemoteTopics) Naming.lookup(rmiAddress+"Topics");
        } catch (NotBoundException e) {
            System.out.println("NotBoundException");
        } catch (MalformedURLException e) {
            System.out.println("Malformed");
        } catch (RemoteException e) {
            System.out.println("RemoteException");
        }
        userID = -1;
    }

    public boolean authenticateUser(String username, String password) throws RemoteException, UserAuthenticationException, SQLException {
        int rmiResponse = this.userID = um.authenticate(username,password);
        if(rmiResponse == -1)
            return false;
        else
            return true;
    }

    public void registerUser(String username, String password) throws RemoteException, ExistingUserException, SQLException {
        um.register(username,password);
    }

    public int getUserID() {
        return userID;
    }

    public void submitIdea(ArrayList<String> topics, String text, String investment) throws IOException, SQLException {
        ideas.submitIdea(topics, userID, Double.parseDouble(investment), text, null, "-", 0);
    }

    public void submitTopic(String topic) throws ExistingTopicException, RemoteException, SQLException {
        topics.newTopic(topic);
    }

    public void buyShares() {

    }

    public IdeaInfo[] watchlist() {
        return null;
    }

    public IdeaInfo[] searchIdea(String ideaKey) {
        return null;
    }

    public TopicInfo[] searchTopic(String topicKey) {
        return null;
    }

    public IdeaInfo[] portfolio() {
        return null;
    }

    public IdeaInfo[] hallOfFame() {
        return null;
    }

}
