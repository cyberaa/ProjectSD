package action;

import java.rmi.RemoteException;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: joaosimoes
 * Date: 12/5/13
 * Time: 12:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class TakeOver extends User {

    private int ideaId;

    public String execute() {
        super.execute();
        try {
            user.takeOver(ideaId);
        } catch (RemoteException e) {
            return ERROR;
        } catch (SQLException e) {
            return ERROR;
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
}
