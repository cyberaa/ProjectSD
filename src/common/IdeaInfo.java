package common;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: joaonuno
 * Date: 10/13/13
 * Time: 6:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class IdeaInfo implements Serializable
{
	private static final long serialVersionUID = 1470130151433907211L;

    private ArrayList<TopicInfo> relatedTopics;
	private int idea_id;
    private String text;
    private String ideaOwner;
    private int isFavorite;


    /**
     *
     * @param idea_id
     * @param ideaOwner
     * @param text
     */
    public IdeaInfo(int idea_id, String ideaOwner, String text, int isFavorite) {
        this.idea_id = idea_id;
        this.text = text;
        this.ideaOwner = ideaOwner;
        this.isFavorite = isFavorite;
    }

    public void setRelatedTopics(ArrayList<TopicInfo> relatedTopics) {
        this.relatedTopics = relatedTopics;
    }

    public void setIdea_id(int idea_id) {
        this.idea_id = idea_id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setIdeaOwner(String ideaOwner) {
        this.ideaOwner = ideaOwner;
    }

    public ArrayList<TopicInfo> getRelatedTopics() {
        return relatedTopics;
    }

    public int getIdea_id() {
        return idea_id;
    }

    public String getText() {
        return text;
    }

    public String getIdeaOwner() {
        return ideaOwner;
    }

    public int getisFavorite() {
        return isFavorite;
    }

    public void setFavorite(int favorite) {
        isFavorite = favorite;
    }
}
