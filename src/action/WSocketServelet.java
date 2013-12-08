package action;

import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: joaosimoes
 * Date: 12/8/13
 * Time: 1:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class WSocketServelet extends WebSocketServlet {

    protected StreamInbound createWebSocketInbound(String subProtocol, HttpServletRequest request) {
        return new WSocketClient(request);
    }

}
