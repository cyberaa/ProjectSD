package action;

import common.IdeaInfo;

/**
 * Created with IntelliJ IDEA.
 * User: joaosimoes
 * Date: 11/28/13
 * Time: 7:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class Watchlist extends Client {

    private IdeaInfo[] ideas;

    public String execute() {
        return SUCCESS;
    }

    public IdeaInfo[] getIdeas() {
        return this.ideas;
    }

}
