package model;

import common.*;
import common.rmi.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.FacebookApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

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
    private int root;
    private double money = 0;
    private String AppSecret = "af8edf703b7a95f5966e9037b545b7ce";
    private String id;
	private Notifications nots;
    private String token;

    public UserBean() {

        try {
            um =  (RemoteUserManager) Naming.lookup(rmiAddress+"UserManager");
            ideas = (RemoteIdeas) Naming.lookup(rmiAddress+"Ideas");
            topics= (RemoteTopics) Naming.lookup(rmiAddress+"Topics");
            transactions = (RemoteTransactions) Naming.lookup(rmiAddress+"Transactions");
	        nots = new Notifications();
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
        UserInfo rmiResponse = um.authenticate(username,password, nots);
        this.userID = rmiResponse.getUserId();
        this.username = rmiResponse.getUsername();
        this.root = rmiResponse.getRoot();
        this.money = rmiResponse.getMoney();

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

    public int getRoot() {
        return root;
    }

    public void setRoot(int root) {
        this.root = root;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public void submitIdea(ArrayList<String> topics, String text, String investment) throws IOException, SQLException, NotEnoughCashException {
        ideas.submitIdea(topics, userID, Double.parseDouble(investment), text, null, "-", 0, token, username, id);
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

    public void removeFromWatchlist(int idea_id) throws SQLException, RemoteException {
        ideas.removeFromWatchlist(userID,idea_id);
    }

    public void buyShares(int idea_id, int num_parts, double value, double new_value) throws RemoteException, SQLException, NotEnoughSharesException, NotEnoughCashException {
        transactions.buyShares(userID,idea_id,num_parts,value,new_value,false,token,id);
    }

    public ArrayList<IdeaInfo> watchlist() throws RemoteException, SQLException {
         return ideas.viewWatchlist(userID);
    }

    public ArrayList<IdeaInfo> portfolio() throws SQLException, RemoteException {
        return ideas.viewPortfolio(userID);
    }

    public ArrayList<IdeaInfo> hallOfFame() throws RemoteException, SQLException {
        return ideas.viewHallOfFame();
    }

    public ArrayList<TransactionInfo> userTransactions(int idea_id) throws RemoteException, SQLException {
        return transactions.showHistory(userID, idea_id);
    }

    public ArrayList<TransactionInfo> ideaTransactions(int idea_id) throws RemoteException, SQLException {
        return transactions.showIdeaHistory(idea_id);
    }

    public void setShareValue(int idea_id, double price) throws RemoteException, SQLException {
        transactions.setShareValue(userID,idea_id,price);
    }

    public ArrayList<IdeaInfo> searchIdea(String key) throws SQLException, RemoteException {
        return ideas.searchIdea(key, userID);
    }

    public  ArrayList<TopicInfo> searchTopic(String key) throws SQLException, RemoteException {
        return topics.searchTopic(key);
    }

    public void takeOver(int ideaId) throws RemoteException, SQLException {
        transactions.takeover(userID,ideaId);
    }

    public void getMoneyFromRMI() {
        if(userID == -1 || userID == 0) {
            return;
        }
        try {
            this.money = um.getMoney(userID);
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (RemoteException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public boolean authenticateFacebook(String token) throws UserAuthenticationException, SQLException, RemoteException {
        OAuthService service = new ServiceBuilder()
                .provider(FacebookApi.class)
                .apiKey("436480809808619")
                .apiSecret("af8edf703b7a95f5966e9037b545b7ce")
                .callback("http://localhost:8080")   //should be the full URL to this action
                .build();
        OAuthRequest authRequest = new OAuthRequest(Verb.GET, "https://graph.facebook.com/me?access_token="+token);
        Token token_final = new Token(token,AppSecret);
        service.signRequest(token_final, authRequest);
        Response authResponse = authRequest.send();

        try {
            username = new JSONObject(authResponse.getBody()).getString("name");
            id = new JSONObject(authResponse.getBody()).getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
            //FIXME WHAT TO DO WITH THIS?????
        }
        if (id == null)
            return false;
        UserInfo u = um.authenticateFace(id,token,username);
        userID = u.getUserId();
        money = u.getMoney();
        this.token = token;
        return true;
    }

    public void deleteIdea(int idea_id) throws NotFullOwnerException, RemoteException, SQLException {
        ideas.deleteIdea(idea_id,userID,token);
    }
}
