package serverRMI;

import common.IdeaInfo;
import common.rmi.ExistingUserException;
import common.rmi.RemoteNotifications;
import common.rmi.RemoteUserManager;
import common.rmi.UserAuthenticationException;
import common.UserInfo;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: joaonuno
 * Date: 10/13/13
 * Time: 11:33 AM
 * To change this template use File | Settings | File Templates.
 */
public class UserManager extends UnicastRemoteObject implements RemoteUserManager
{
	protected final int startCash = 1000000;

	public UserManager() throws RemoteException
	{
		super();
	}

	/**
	 * Authenticates user. If the user credentials are wrong throws UserAuthenticationException.
	 * @param name The username of the user to authenticate.
	 * @param pass The password of the user to authenticate.
	 * @throws RemoteException
	 * @throws UserAuthenticationException
	 * @throws SQLException
	 */
	public UserInfo authenticate(String name, String pass) throws RemoteException, UserAuthenticationException, SQLException
	{
		Connection db = ServerRMI.getConnection();

		int tries = 0, maxTries = 3;

		PreparedStatement queryUser = null;
		ResultSet resultSet = null;

		String query = "SELECT id, username, is_root, cash FROM sduser WHERE username LIKE ? AND password LIKE ?";

		while(tries < maxTries)
		{
			try {
				queryUser = db.prepareStatement(query);
				queryUser.setString(1, name);
				queryUser.setString(2, hashPassword(pass));

				resultSet = queryUser.executeQuery();

				if(!resultSet.next())
				{
					//User password combination does not exist.
					throw new UserAuthenticationException();
				}

                int userId = resultSet.getInt("id");
                String username = resultSet.getString("username");
                int isRoot = resultSet.getInt("is_root");
                double money = resultSet.getDouble("cash");

				return new UserInfo(userId,username,isRoot,money);
			} catch (SQLException e) {
				System.out.println(e);
				if(tries++ > maxTries)
					throw e;
			} finally {
				if(queryUser != null)
					queryUser.close();
			}
		}

		db.close();

		return new UserInfo(-1,"", 0,0);
	}

    public UserInfo authenticateFace(String user_face, String token, String username) throws UserAuthenticationException, SQLException, RemoteException {
        Connection db = ServerRMI.getConnection();

        int tries = 0, maxTries = 3;

        PreparedStatement queryUser = null;
	    PreparedStatement functionCall = null;
        ResultSet resultSet = null;

        String query = "SELECT id, face_id, username, is_root, cash FROM sduser WHERE id LIKE ?";
	    String call = "SELECT authenticate(?,?) as id FROM dual";

        while(tries < maxTries)
        {
            try {
	            functionCall = db.prepareStatement(call);
	            functionCall.setString(1, username);
	            functionCall.setString(2, user_face);

	            resultSet = functionCall.executeQuery();

	            resultSet.next();
	            int id = resultSet.getInt("id");

                queryUser = db.prepareStatement(query);
                queryUser.setInt(1, id);

                resultSet = queryUser.executeQuery();

                resultSet.next();
                int userId = resultSet.getInt("id");
                int isRoot = resultSet.getInt("is_root");
                double money = resultSet.getDouble("cash");

                System.out.println(userId+" "+isRoot+" "+money);

                return new UserInfo(userId,username,isRoot,money);
            } catch (SQLException e) {
                System.out.println(e);
                if(tries++ > maxTries)
                    throw e;
            } finally {
                if(queryUser != null)
                    queryUser.close();
            }
        }

        db.close();

        return new UserInfo(-1,"", 0,0);
    }

    public UserInfo registerNewFace(String user_face, String token, String username) throws SQLException, RemoteException {
        Connection db = ServerRMI.getConnection();

        int tries = 0, maxTries = 3;

        PreparedStatement queryUser = null;
        ResultSet resultSet = null;

        String query = "INSERT INTO sduser (id, username, password, cash, is_root, face_id) VALUES(seq_sduser.nextval,?,?,?,?,?)";

        while(tries < maxTries)
        {
            try {
                queryUser = db.prepareStatement(query);
                queryUser.setString(1, username);
                queryUser.setString(2, "face");
                queryUser.setDouble(3, 1000000);
                queryUser.setInt(4, 0);
                queryUser.setString(5, user_face);

                queryUser.executeQuery();

                break;
            } catch (SQLException e) {
                System.out.println(e);
                if(tries++ > maxTries)
                    throw e;
            } finally {
                if(queryUser != null)
                    queryUser.close();
            }
        }

        query = "SELECT id, is_root, cash FROM sduser WHERE face_id LIKE ?";

        while(tries < maxTries)
        {
            try {
                queryUser = db.prepareStatement(query);
                queryUser.setString(1, user_face);

                resultSet = queryUser.executeQuery();

                resultSet.next();

                int userId = resultSet.getInt("id");
                int isRoot = resultSet.getInt("is_root");
                double money = resultSet.getDouble("cash");

                System.out.println("Register: " + userId + " " + isRoot + " " + money);

                return new UserInfo(userId, "", isRoot, money);
            } catch (SQLException e) {
                System.out.println(e);
                if(tries++ > maxTries)
                    throw e;
            } finally {
                if(queryUser != null)
                    queryUser.close();
            }
        }

        db.close();

        return new UserInfo(-1,"", 0,0);
    }

	/**
	 * Registers a new user. If desired username is in use throws ExistingUserException
	 * @param name  The desired password of the user to register.
	 * @param pass  The desired password of the user to register.
	 * @throws RemoteException
	 * @throws ExistingUserException
	 * @throws SQLException
	 */
	public void register(String name, String pass) throws RemoteException, ExistingUserException, SQLException
	{
		Connection db = ServerRMI.getConnection();

		int tries = 0, maxTries = 3;

		PreparedStatement queryUser = null;
		PreparedStatement insertUser = null;
		ResultSet resultSet = null;

		String query = "SELECT id FROM sduser WHERE username LIKE ?";
		String insert = "INSERT INTO sduser (id, username, password, cash, is_root) VALUES(seq_sduser.nextval,?,?,?,?)";

		//See if username is already in use.
		while(tries < maxTries)
		{
			try {
				queryUser = db.prepareStatement(query);
				queryUser.setString(1, name);

				resultSet = queryUser.executeQuery();

				if(resultSet.next())
					throw new ExistingUserException();

				break;
			} catch (SQLException e) {
				System.out.println("\n"+e+"\n");
				if(tries++ > maxTries)
					throw e;
			} finally {
				if(queryUser != null)
					queryUser.close();
			}
		}

		//If execution reaches this point, insert new user.
		try {
			db.setAutoCommit(false);

			insertUser = db.prepareStatement(insert);
			insertUser.setString(1, name);
			insertUser.setString(2, hashPassword(pass));
			insertUser.setInt(3, startCash);
			insertUser.setInt(4, 0);

			insertUser.executeQuery();

			db.commit();
		} catch (SQLException e) {
			System.out.println("\n"+e+"\n");
			if(db != null)
				db.rollback();
			throw e;
		} finally {
			if(insertUser != null)
				insertUser.close();

			db.setAutoCommit(true);
		}

		db.close();
	}

	/**
	 * Hashes the password using MD5 and returns it.
	 * @param pass The plaintext password to be hashed.
	 * @return The hashed password.
	 */
	protected String hashPassword(String pass)
	{
		/*MessageDigest m = null;
		try {
			m = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Cannot find hashing algorithm:\n" + e);
			System.exit(-1);
		}
		if(m == null)
		{
			System.out.println("Cannot find hashing algorithm.");
			System.exit(-1);
		}

		m.reset();
		m.update(pass.getBytes());

		byte[] digest = m.digest();
		BigInteger bigInt = new BigInteger(1,digest);
		String hashText = bigInt.toString(16);

		while(hashText.length() < 32)
			hashText = "0" + hashText;

		return hashText;*/

		return pass;
	}

    public double getMoney(int userId) throws SQLException, RemoteException
    {
        Connection db = ServerRMI.getConnection();

        int tries = 0, maxTries = 3;

        PreparedStatement queryUser = null;
        ResultSet resultSet;

        double money = 0;

        String query = "SELECT sduser.cash FROM sduser WHERE sduser.id = ?";

        while(tries < maxTries)
        {
            try {
                queryUser = db.prepareStatement(query);
                queryUser.setInt(1, userId);

                resultSet = queryUser.executeQuery();

                if(resultSet.next()) {
                    money = resultSet.getDouble("cash");
                }

                return money;
            } catch (SQLException e) {
                System.out.println(e);
                if(tries++ > maxTries)
                    throw e;
            } finally {
                if(queryUser != null)
                    queryUser.close();
            }
        }

        db.close();

        System.out.println("Sai RMI"+"  "+money);


        return money;
    }

	public void registerNotificationCallback(int user_id, RemoteNotifications nots) throws RemoteException
	{
		boolean alreadyRegistered = false;

		for (int i=0; i < ServerRMI.users.size(); i++)
			if(ServerRMI.users.get(i) == user_id)
				alreadyRegistered = true;

		if(!alreadyRegistered)
			ServerRMI.users.add(user_id);

		if(alreadyRegistered)
			ServerRMI.userNotifications.remove(user_id);
		ServerRMI.userNotifications.put(user_id, nots);
	}
}
