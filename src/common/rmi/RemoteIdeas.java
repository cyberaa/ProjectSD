package common.rmi;

import common.IdeaInfo;
import common.IdeasNestedPack;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: joaonuno
 * Date: 10/12/13
 * Time: 6:33 PM
 * To change this template use File | Settings | File Templates.
 */
public interface RemoteIdeas extends Remote
{
	public void submitIdea(ArrayList<String> topics, int user_id, double investment, String text, byte[] fileData, String filename, int current) throws RemoteException, SQLException, IOException;

	public void deleteIdea(int idea_id, int user_id) throws RemoteException, SQLException, NotFullOwnerException;

	public ArrayList<IdeaInfo> viewIdeasTopic(int topic_id, int user_id) throws RemoteException, SQLException;

    public void addToWatchlist(int user_id, int idea_id) throws RemoteException, SQLException;

    public ArrayList<IdeaInfo> viewWatchlist(int user_id) throws RemoteException, SQLException;
}
