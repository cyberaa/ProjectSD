package action;

import common.IdeaInfo;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: joaosimoes
 * Date: 12/3/13
 * Time: 5:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class SetShareValue extends User {

    private int ideaId;
    private double sharePrice;
    private ArrayList<IdeaInfo> ideas;

    public String execute() {
        System.out.println("\n\n Entrei \n");
        super.execute();
        try {
            user.setShareValue(ideaId,sharePrice);
            ideas = user.portfolio();
        } catch (RemoteException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        user.getMoneyFromRMI();
        return SUCCESS;
    }

    public int getIdeaId() {
        return ideaId;
    }

    public void setIdeaId(int ideaId) {
        this.ideaId = ideaId;
    }

    public double getSharePrice() {
        return sharePrice;
    }

    public void setSharePrice(double sharePrice) {
        this.sharePrice = sharePrice;
    }

    public ArrayList<IdeaInfo> getIdeas() {
        return ideas;
    }

    public void setIdeas(ArrayList<IdeaInfo> ideas) {
        this.ideas = ideas;
    }
}
