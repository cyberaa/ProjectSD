package common;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: joaonuno
 * Date: 10/12/13
 * Time: 6:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class TransactionInfo implements Serializable
{
	private static final long serialVersionUID = -6346265992255318206L;

	private String seller;
	private String buyer;

	private int parts;
	private int total;

	public TransactionInfo(String seller, String buyer, int parts, int total)
	{
		this.seller = seller;
		this.buyer = buyer;
		this.parts = parts;
		this.total = total;
	}

	public String toString()
	{
		return "Seller: "+seller+"\tBuyer: "+buyer+"\tParts: "+parts+"\tTotal: "+total;
	}

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public int getParts() {
        return parts;
    }

    public void setParts(int parts) {
        this.parts = parts;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
