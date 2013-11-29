package action;

import common.IdeaInfo;

/**
 * Created with IntelliJ IDEA.
 * User: joaosimoes
 * Date: 11/28/13
 * Time: 7:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class SearchIdea extends User {

    private String searchKey;
    private IdeaInfo[] ideas;


    public String execute() {
        return SUCCESS;
    }

    public IdeaInfo[] getIdeas() {
        return ideas;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }
}
