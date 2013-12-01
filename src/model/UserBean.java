package model;

import common.IdeaInfo;
import common.ShareInfo;
import common.TopicInfo;
import common.UserInfo;
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
    private RemoteTransactions transactions;
    private int userID;
    private String username;

    public UserBean() {

        try {
            um =  (RemoteUserManager) Naming.lookup(rmiAddress+"UserManager");
            ideas = (RemoteIdeas) Naming.lookup(rmiAddress+"Ideas");
            topics= (RemoteTopics) Naming.lookup(rmiAddress+"Topics");
            transactions = (RemoteTransactions) Naming.lookup(rmiAddress+"Transactions");
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
        UserInfo rmiResponse = um.authenticate(username,password);
        this.userID = rmiResponse.getUserId();
        this.username = rmiResponse.getUsername();

        if(userID == -1)
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

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void submitIdea(ArrayList<String> topics, String text, String investment) throws IOException, SQLException {
        ideas.submitIdea(topics, userID, Double.parseDouble(investment), text, null, "-", 0);
    }

    public void submitTopic(String topic) throws ExistingTopicException, RemoteException, SQLException {
        topics.newTopic(topic);
    }

    public ArrayList<TopicInfo> showTopics() throws RemoteException, SQLException {
        return topics.listTopics();
    }

    public ArrayList<IdeaInfo> showTopicIdeas(int topicId) throws RemoteException, SQLException {
        return ideas.viewIdeasTopic(topicId, userID);
    }

    public ArrayList<ShareInfo> showIdeaShares(int idea_id) throws RemoteException, SQLException {
        return transactions.getShares(idea_id);
    }

    public void addToWatchlist(int idea_id) throws SQLException, RemoteException {
        ideas.addToWatchlist(userID,idea_id);
    }

    public void buyShares() {

    }

    public ArrayList<IdeaInfo> watchlist() throws RemoteException, SQLException {
         return ideas.viewWatchlist(userID);
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
