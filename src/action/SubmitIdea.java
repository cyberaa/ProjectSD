package action;

import com.opensymphony.xwork2.ActionSupport;
import common.IdeaInfo;
import common.rmi.NotEnoughCashException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: joaosimoes
 * Date: 11/27/13
 * Time: 7:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class SubmitIdea extends User {

    private String topic;
    private String text;
    private String investment;

    private IdeaInfo[] ideas;

    private String responseIdea;


    @Override
    public String execute() {
        System.out.println(topic);
        System.out.println(text);
        System.out.println(investment);
        super.execute();
        ArrayList<String> topics = parseTopics(topic);
        try {
            user.submitIdea(topics, text, investment);
        } catch (IOException e) {
            System.out.println("IO Exception" + e);
            responseIdea = "rmi";
            return SUCCESS;
        } catch (SQLException e) {
            System.out.println("SQL Exception");
            responseIdea = "rmi";
            return SUCCESS;
        } catch (NotEnoughCashException e) {
            return ERROR;
        }
        user.getMoneyFromRMI();
        super.writeUserCookie();
        responseIdea = "success";
        return SUCCESS;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setInvestment(String investment) {
        this.investment = investment;
    }

    public String getResponseIdea() {
        System.out.println("Entrei nisto");
        return this.responseIdea;
    }

	private ArrayList<String> parseTopics(String topics)
	{
		ArrayList<String> ret = new ArrayList<String>();

		String[] result = topic.split("; ");

		for (int i=0; i< result.length; i++)
			ret.add(result[i]);

		return ret;
	}
}
