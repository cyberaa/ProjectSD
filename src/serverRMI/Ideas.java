package serverRMI;

import common.IdeaInfo;
import common.IdeasNestedPack;
import common.rmi.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: joaonuno
 * Date: 10/13/13
 * Time: 6:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class Ideas extends UnicastRemoteObject implements RemoteIdeas
{
    public Ideas() throws RemoteException{
        super();
    }

    /**
     * Insert new idea in database.
     * @param topics Topic text. If topic not exists, a new topic is created.
     * @param user_id User who posted idea.
     * @param investment
     * @param text
     * @throws RemoteException
     * @throws SQLException
     */
    public void submitIdea(ArrayList<String> topics, int user_id, double investment, String text, byte[] fileData, String filename, int current) throws RemoteException, SQLException, IOException
    {
        if (!filename.equals("-")) {
            FileOutputStream fos = new FileOutputStream("assets/"+filename);
            BufferedOutputStream bos = new BufferedOutputStream(fos);

            bos.write(fileData, 0 , current);
            bos.flush();
            bos.close();
        }

        Connection db = ServerRMI.getConnection();

        int tries = 0;
        int maxTries = 3;
        PreparedStatement stmt = null;
        ArrayList<Integer> topicIds = new ArrayList<Integer>();
        ResultSet rs;

	    try {
		    db.setAutoCommit(false);

		    String query = "SELECT * FROM idea WHERE user_id = ? AND number_parts = ? AND active = ? AND text LIKE ? AND attach LIKE ? AND in_hall LIKE ?";

		    try {
			    stmt = db.prepareStatement(query);
			    stmt.setInt(1, user_id);
			    stmt.setInt(2, 100000);
			    stmt.setInt(3, 1);
			    stmt.setString(4, text);
			    stmt.setString(5, filename);
                stmt.setInt(6, 0);

			    rs = stmt.executeQuery();

			    if(rs.next())
				    return;
		    } catch (SQLException e) {
			    System.out.println(e);
			    if(db != null) {
				    db.rollback();
			    }
		    } finally {
			    if(stmt != null) {
				    stmt.close();
			    }
		    }

		    //Get topic ID.
		    String aux;
            int topic_id;
            System.out.println("Size 2: "+topics.size());
		    for(int i=0; i < topics.size(); i++)
		    {
			    aux = topics.get(i);
			    try {
                    topic_id = ServerRMI.topics.getTopicID(aux);
                    if (topic_id != -1) {
                        topicIds.add(topic_id);
                    }
                    else {
                        topic_id = ServerRMI.topics.newTopic(aux);
                        topicIds.add(topic_id);
                    }
			    } catch (ExistingTopicException ete) {
                    db.close();
				    // Topic already exists
			    }
		    }

		    //Insert idea.
		    query = "INSERT INTO idea (id,user_id,number_parts,active,text,attach,in_hall) VALUES (seq_idea.nextval,?,?,?,?,?,?)";

            try {
                stmt = db.prepareStatement(query);
                stmt.setInt(1, user_id);
                stmt.setInt(2, 100000);
                stmt.setInt(3, 1);
                stmt.setString(4, text);
                stmt.setString(5, filename);
                stmt.setInt(6, 0);

                stmt.executeQuery();
            } catch (SQLException e) {
                System.out.println(e);
                if(db != null) {
                    db.rollback();
                }
            } finally {
                if(stmt != null) {
                    stmt.close();
                }
            }

            int idea_id = 0;

            query = "SELECT seq_idea.currval as id FROM dual";

            try {
                stmt = db.prepareStatement(query);

                rs = stmt.executeQuery();

                rs.next();

                idea_id = rs.getInt("id");
            } catch (SQLException e) {
                System.out.println(e);
                if(db != null) {
                    db.rollback();
                }
            } finally {
                if(stmt != null) {
                    stmt.close();
                }
            }


		    //Create relationship between topic and idea.
		    query = "INSERT INTO idea_has_topic (idea_id,topic_id) VALUES (?,?)";

		    for(int i=0; i < topicIds.size(); i++)
		    {

                try {
                    stmt = db.prepareStatement(query);
                    stmt.setInt(1, idea_id);
                    stmt.setInt(2, topicIds.get(i));

                    stmt.executeQuery();
                } catch (SQLException e) {
                    if(db != null) {
                        db.rollback();
                    }
                } finally {
                    if(stmt != null) {
                        stmt.close();
                    }
                }

		    }

            //Insert initial share of idea
            query = "INSERT INTO idea_share (id,idea_id,user_id,parts,VALUE) VALUES (seq_idea_share.nextval,?,?,?,?)";

            try {
                stmt = db.prepareStatement(query);
                stmt.setInt(1, idea_id);
                stmt.setInt(2, user_id);
                stmt.setInt(3, 100000);
                stmt.setDouble(4, investment / 100000);

                stmt.executeQuery();
            } catch (SQLException e) {
                System.out.println(e);
                if(db != null) {
                    db.rollback();
                }
            } finally {
                if(stmt != null) {
                    stmt.close();
                }
            }

		    db.commit();
	    } catch (SQLException e) {
		    System.out.println("\n"+e+"\n");
		    if(db != null)
			    db.rollback();
		    throw e;
	    } finally {
		    db.setAutoCommit(true);
		    db.close();
	    }
	}

	/**
	 * Delete idea identified by <em>idea_id</em>.
	 * @param idea_id The identifier of the idea to delete.
	 * @param user_id The identifier of the user trying to delete the idea.
	 * @throws RemoteException
	 * @throws SQLException
	 * @throws NotFullOwnerException
	 */
    public void deleteIdea(int idea_id, int user_id) throws RemoteException, SQLException, NotFullOwnerException
    {
	    Connection db = ServerRMI.getConnection();

	    try {
		    //Verify that user owns all shares.
		    int numParts = ServerRMI.transactions.getNumberShares(db, idea_id);

		    int tries = 0;
		    int maxTries = 3;
		    PreparedStatement stmt = null;
		    ResultSet rs;

		    String verify = "SELECT parts FROM shares WHERE idea_id = ? AND user_id = ?";

		    while(tries < maxTries)
		    {
			    try {
				    stmt = db.prepareStatement(verify);
				    stmt.setInt(1, idea_id);
				    stmt.setInt(2, user_id);

				    rs = stmt.executeQuery();

				    if(rs.next())
				    {
					    if(rs.getInt("parts") == numParts)
						    break;
				    }
				    else
					    throw new NotFullOwnerException();
			    } catch (SQLException e) {
                    System.out.println(e);
				    if(tries++ > maxTries) {
					    throw e;
				    }
			    } finally {
				    if(stmt != null)
					    stmt.close();
			    }
		    }

		    //Update active field to 0.
		    String update = "UPDATE idea SET active = 0 WHERE id = ?";

		    while(tries < maxTries)
		    {
			    try {
				    stmt = db.prepareStatement(update);
				    stmt.setInt(1, idea_id);

				    stmt.executeQuery();
				    break;
			    } catch (SQLException e) {
				    System.out.println(e);
				    if(tries++ > maxTries)
					    throw e;
			    } finally {
				    if(stmt != null)
					    stmt.close();
			    }
		    }

		    //Remove from idea_has_topic.
		    String remove = "DELETE FROM idea_has_topic WHERE idea_id = ?";
		    while(tries < maxTries)
		    {
			    try {
				    stmt = db.prepareStatement(remove);
				    stmt.setInt(1, idea_id);

				    stmt.executeQuery();
				    break;
			    } catch (SQLException e) {
				    System.out.println(e);
				    if(tries++ > maxTries)
					    throw e;
			    } finally {
				    if(stmt != null)
					    stmt.close();
			    }
		    }

		    //Remove from shares.
		    remove = "DELETE FROM shares WHERE idea_id = ?";
		    while(tries < maxTries)
		    {
			    try {
				    stmt = db.prepareStatement(remove);
				    stmt.setInt(1, idea_id);

				    stmt.executeQuery();
				    break;
			    } catch (SQLException e) {
				    System.out.println(e);
				    if(tries++ > maxTries)
					    throw e;
			    } finally {
				    if(stmt != null)
					    stmt.close();
			    }
		    }

		    db.commit();
	    } catch (SQLException e) {
		    System.out.println(e);
		    if(db != null)
			    db.rollback();
	    } finally {
		    db.setAutoCommit(true);
	    }
	}

	/**
	 *
	 * @param topic_id
	 * @return
	 * @throws RemoteException
	 * @throws SQLException
	 */
    public ArrayList<IdeaInfo> viewIdeasTopic(int topic_id, int user_id) throws RemoteException, SQLException
    {
        Connection db = ServerRMI.getConnection();

        db.setAutoCommit(false);

        int tries = 0;
        int maxTries = 3;
        PreparedStatement stmt = null;
        ResultSet rs;
        ArrayList<IdeaInfo> ideas = new ArrayList<IdeaInfo>();

        String query = "SELECT lol.id, lol.text, lol.username, lol.sduser_id, lol.idea_id FROM (SELECT idea.id, idea.text, sduser.username, lol.sduser_id, lol.idea_id, idea.in_hall FROM idea LEFT OUTER JOIN (Select watchlist.* FROM watchlist where sduser_id = ?) lol ON idea.id = lol.idea_id LEFT OUTER JOIN sduser ON sduser.id = idea.user_id) lol, idea_has_topic WHERE lol.id = idea_has_topic.idea_id AND idea_has_topic.topic_id = ? AND lol.in_hall = 0";

        while(tries < maxTries)
        {
            try {
                stmt = db.prepareStatement(query);
                stmt.setInt(1, user_id);
                stmt.setInt(2, topic_id);

                rs = stmt.executeQuery();

                while(rs.next()) {
                    if(rs.getString("sduser_id") == null) {
                        ideas.add(new IdeaInfo(rs.getInt("id"), rs.getString("username"), rs.getString("text"), 0));
                    }
                    else {
                        ideas.add(new IdeaInfo(rs.getInt("id"), rs.getString("username"), rs.getString("text"), 1));
                    }

                }
                break;
            } catch (SQLException e) {
                System.out.println(e);
                if(tries++ > maxTries) {
                    throw e;
                }
            } finally {
	            if(stmt != null)
		            stmt.close();
            }
        }

        return ideas;
    }

    public void addToWatchlist(int user_id, int idea_id) throws SQLException
    {
        Connection db = ServerRMI.getConnection();

        int tries = 0;
        int maxTries = 3;
        PreparedStatement stmt = null;

        System.out.println(user_id+" "+idea_id);

        String query = "INSERT INTO watchlist (idea_id,sduser_id) values (?,?)";

        while(tries < maxTries)
        {
            try {
                stmt = db.prepareStatement(query);
                stmt.setInt(1, idea_id);
                stmt.setInt(2, user_id);

                stmt.executeQuery();
                break;
            } catch (SQLException e) {
                System.out.println(e);
                if(tries++ > maxTries) {
                    throw e;
                }
            } finally {
                if(stmt != null)
                    stmt.close();
            }
        }
    }

    public ArrayList<IdeaInfo> viewWatchlist(int user_id) throws SQLException, RemoteException {

        Connection db = ServerRMI.getConnection();

        ArrayList<IdeaInfo> ideas = new ArrayList<IdeaInfo>();

        try {
            int tries = 0;
            int maxTries = 3;
            PreparedStatement stmt = null;
            ResultSet rs;

            db.setAutoCommit(false);

            String verify = "SELECT idea.id, idea.text, sduser.username FROM watchlist, idea, sduser WHERE watchlist.sduser_id = ? AND idea.id = watchlist.idea_id AND idea.user_id = sduser.id AND idea.in_hall = 0";

            while(tries < maxTries)
            {
                try {
                    stmt = db.prepareStatement(verify);
                    stmt.setInt(1, user_id);

                    rs = stmt.executeQuery();

                    while(rs.next())
                    {
                         ideas.add(new IdeaInfo(rs.getInt("id"), rs.getString("username"), rs.getString("text"), 1));
                    }
                    break;
                } catch (SQLException e) {
                    System.out.println(e);
                    if(tries++ > maxTries) {
                        throw e;
                    }
                } finally {
                    if(stmt != null)
                        stmt.close();
                }
            }
            db.commit();
        } catch (SQLException e) {
            System.out.println(e);
            if(db != null)
                db.rollback();
        } finally {
            db.setAutoCommit(true);
        }
        return ideas;
    }

    public ArrayList<IdeaInfo> viewHallOfFame() throws RemoteException, SQLException {
        Connection db = ServerRMI.getConnection();

        ArrayList<IdeaInfo> ideas = new ArrayList<IdeaInfo>();

        try {
            int tries = 0;
            int maxTries = 3;
            PreparedStatement stmt = null;
            ResultSet rs;

            db.setAutoCommit(false);

            String verify = "SELECT idea.id, idea.text, sduser.username FROM idea, sduser WHERE idea.in_hall = 1 AND idea.user_id = sduser.id";

            while(tries < maxTries)
            {
                try {
                    stmt = db.prepareStatement(verify);

                    rs = stmt.executeQuery();

                    while(rs.next())
                    {
                        ideas.add(new IdeaInfo(rs.getInt("id"), rs.getString("username"), rs.getString("text"), 1));
                    }
                    break;
                } catch (SQLException e) {
                    System.out.println(e);
                    if(tries++ > maxTries) {
                        throw e;
                    }
                } finally {
                    if(stmt != null)
                        stmt.close();
                }
            }
            db.commit();
        } catch (SQLException e) {
            System.out.println(e);
            if(db != null)
                db.rollback();
        } finally {
            db.setAutoCommit(true);
        }
        return ideas;
    }

    public ArrayList<IdeaInfo> viewPortfolio(int user_id) throws SQLException, RemoteException {
        Connection db = ServerRMI.getConnection();

        ArrayList<IdeaInfo> ideas = new ArrayList<IdeaInfo>();

        try {
            int tries = 0;
            int maxTries = 3;
            PreparedStatement stmt = null;
            ResultSet rs;

            db.setAutoCommit(false);

            String verify = "SELECT idea.id, idea.text, sduser.username, idea_share.parts, idea_share.value FROM idea, idea_share, sduser WHERE idea.user_id = sduser.id AND idea_share.user_id = ? AND idea_share.idea_id = idea.id";

            while(tries < maxTries)
            {
                try {
                    stmt = db.prepareStatement(verify);

                    stmt.setInt(1, user_id);

                    rs = stmt.executeQuery();

                    while(rs.next())
                    {
                        ideas.add(new IdeaInfo(rs.getInt("id"), rs.getString("username"), rs.getString("text"), 1, rs.getInt("parts"), rs.getDouble("value")));
                    }
                    break;
                } catch (SQLException e) {
                    System.out.println(e);
                    if(tries++ > maxTries) {
                        throw e;
                    }
                } finally {
                    if(stmt != null)
                        stmt.close();
                }
            }
            db.commit();
        } catch (SQLException e) {
            System.out.println(e);
            if(db != null)
                db.rollback();
        } finally {
            db.setAutoCommit(true);
        }
        return ideas;
    }
}
