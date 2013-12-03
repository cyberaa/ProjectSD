package action;

import common.TransactionInfo;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: joaosimoes
 * Date: 12/3/13
 * Time: 12:57 AM
 * To change this template use File | Settings | File Templates.
 */
public class UserTransactions extends User {

    private ArrayList<TransactionInfo> transactions;
    private int ideaId;
    private String ideaText;
    private String ideaOwner;

    public String execute() {
        super.execute();
        try {
            transactions = user.userTransactions(ideaId);
        } catch (RemoteException e) {
            return ERROR;
        } catch (SQLException e) {
            return ERROR;
        }
        return SUCCESS;
    }

    public ArrayList<TransactionInfo> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<TransactionInfo> transactions) {
        this.transactions = transactions;
    }

    public int getIdeaId() {
        return ideaId;
    }

    public void setIdeaId(int ideaId) {
        this.ideaId = ideaId;
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
