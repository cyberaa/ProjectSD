package action;

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
public class SubmitIdea extends Client {

    private String topic;
    private String text;
    private String investment;

    private String response;


    @Override
    public String execute() {
        System.out.println(topic);
        System.out.println(text);
        System.out.println(investment);
        super.execute();
        ArrayList<String> topics = new ArrayList<String>();
        topics.add(topic);
        try {
            user.submitIdea(topics, text, investment);
        } catch (IOException e) {
            System.out.println("IO Exception" + e);
            response = "rmi";
            return SUCCESS;
        } catch (SQLException e) {
            System.out.println("SQL Exception");
            response = "rmi";
            return SUCCESS;
        }
        response = "success";
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

    public String getResponse() {
        System.out.println("Entrei nisto");
        return this.response;
    }



}
