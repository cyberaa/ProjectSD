package action;

import common.IdeaInfo;
import common.rmi.NotFullOwnerException;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: joaosimoes
 * Date: 12/7/13
 * Time: 1:47 AM
 * To change this template use File | Settings | File Templates.
 */
public class DeleteIdea extends User {

    private int ideaId;
    private ArrayList<IdeaInfo> ideas;

    private String responseDel;

    public String execute() {
        super.execute();
        try {
            user.deleteIdea(ideaId);
        } catch (NotFullOwnerException e) {
            try {
                ideas = user.portfolio();
            } catch (SQLException e1) {
                return LOGIN;
            } catch (RemoteException e1) {
                return LOGIN;
            }
            responseDel = "nfo";
            return SUCCESS;
        } catch (RemoteException e) {
            return LOGIN;
        } catch (SQLException e) {
            return LOGIN;
        }
        user.getMoneyFromRMI();
        super.writeUserCookie();
        responseDel = "success";
        return SUCCESS;
    }

    public int getIdeaId() {
        return ideaId;
    }

    public void setIdeaId(int ideaId) {
        this.ideaId = ideaId;
    }

    public ArrayList<IdeaInfo> getIdeas() {
        return ideas;
    }

    public void setIdeas(ArrayList<IdeaInfo> ideas) {
        this.ideas = ideas;
    }

    public String getResponseDel() {
        return responseDel;
    }

    public void setResponseDel(String responseDel) {
        this.responseDel = responseDel;
    }
}
