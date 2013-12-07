package common;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: joaonuno
 * Date: 10/14/13
 * Time: 11:39 AM
 * To change this template use File | Settings | File Templates.
 */
public class ShareInfo implements Serializable
{
	private static final long serialVersionUID = 8008689959006952390L;

	private int id;
	private int idea_id;
	private int user_id;
    private String username;
	private int parts;
	private double value;

	public ShareInfo(int id, int idea_id, int user_id, int parts, double value, String username)
	{
		this.id = id;
		this.idea_id = idea_id;
		this.user_id = user_id;
        this.username = username;
		this.parts = parts;
		this.value = value;
	}

	public String toString()
	{
		return "ID: "+id+"\tIdea ID: "+idea_id+"\tUser ID: "+user_id+"\tParts: "+parts+"\tValue: "+value;
	}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdea_id() {
        return idea_id;
    }

    public void setIdea_id(int idea_id) {
        this.idea_id = idea_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getParts() {
        return parts;
    }

    public void setParts(int parts) {
        this.parts = parts;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
