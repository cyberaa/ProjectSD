package action;

/**
 * Created with IntelliJ IDEA.
 * User: joaosimoes
 * Date: 11/28/13
 * Time: 7:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuyShares extends User {

    private String idea;
    private String share_num;
    private String new_price_share;

    public String execute() {
        return SUCCESS;
    }

    public void setIdea(String idea) {
        this.idea = idea;
    }

    public void setShare_num(String share_num) {
        this.share_num = share_num;
    }

    public void setNew_price_share(String new_price_share) {
        this.new_price_share = new_price_share;
    }
}
