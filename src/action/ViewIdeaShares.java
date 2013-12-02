package action;

import common.ShareInfo;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: joaosimoes
 * Date: 11/30/13
 * Time: 4:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class ViewIdeaShares extends User {

    private int ideaId;
    private String ideaText;
    private String ideaOwner;
    private ArrayList<ShareInfo> shares;

    public String execute() {
        super.execute();
        try {
            shares = user.showIdeaShares(ideaId);
        } catch (RemoteException e) {
            return ERROR;
        } catch (SQLException e) {
            return ERROR;
        }
        return SUCCESS;
    }

    public int getIdeaId() {
        return ideaId;
    }

    public void setIdeaId(int ideaId) {
        this.ideaId = ideaId;
    }

    public ArrayList<ShareInfo> getShares() {
        return shares;
    }

    public void setShares(ArrayList<ShareInfo> shares) {
        this.shares = shares;
    }

    public String getIdeaText() {
        return ideaText;
    }

    public void setIdeaText(String ideaText) {
        this.ideaText = ideaText;
    }

    public String getIdeaOwner() {
        return ideaOwner;
    }

    public void setIdeaOwner(String ideaOwner) {
        this.ideaOwner = ideaOwner;
    }
}
