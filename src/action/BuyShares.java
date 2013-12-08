package action;

import common.ShareInfo;
import common.rmi.NotEnoughCashException;
import common.rmi.NotEnoughSharesException;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: joaosimoes
 * Date: 11/28/13
 * Time: 7:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuyShares extends User {

    private int ideaId;
    private int share_num;
    private double price_share;
    private double new_price_share;
    private String ideaText;
    private String ideaOwner;
    private ArrayList<ShareInfo> shares;

    private String responseBuy;

    public String execute() {
        super.execute();
        try {
            user.buyShares(ideaId,share_num,price_share, new_price_share);
            shares = user.showIdeaShares(ideaId);
        } catch (RemoteException e) {
            responseBuy = "error";
            return LOGIN;
        } catch (SQLException e) {
            responseBuy = "error";
            return LOGIN;
        } catch (NotEnoughSharesException e) {
            responseBuy = "nes";
            return SUCCESS;
        } catch (NotEnoughCashException e) {
            responseBuy = "nec";
            return SUCCESS;
        }
        user.getMoneyFromRMI();
        super.writeUserCookie();
        responseBuy = "success";
        return SUCCESS;
    }

    public int getIdeaId() {
        return ideaId;
    }

    public void setIdeaId(int ideaId) {
        this.ideaId = ideaId;
    }

    public int getShare_num() {
        return share_num;
    }

    public void setShare_num(int share_num) {
        this.share_num = share_num;
    }

    public double getNew_price_share() {
        return new_price_share;
    }

    public void setNew_price_share(double new_price_share) {
        this.new_price_share = new_price_share;
    }

    public double getPrice_share() {
        return price_share;
    }

    public void setPrice_share(double price_share) {
        this.price_share = price_share;
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

    public String getResponseBuy() {
        return responseBuy;
    }

    public void setResponseBuy(String responseBuy) {
        this.responseBuy = responseBuy;
    }
}
