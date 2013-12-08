package action;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import common.rmi.RemoteUserManager;
import model.Notifications;
import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.WsOutbound;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Created with IntelliJ IDEA.
 * User: joaosimoes
 * Date: 12/8/13
 * Time: 1:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class WSocketClient extends MessageInbound implements Serializable {

    String rmiAddress = "rmi://127.0.0.1:7777/";
    private static final String COOKIE_NAME = "ClientCookie";

    private int userID;

    private RemoteUserManager um;

    public WSocketClient(HttpServletRequest request) {
        System.out.println("Inside Socket Constructor");
        Cookie[] cookies = request.getCookies();
        if ( cookies != null ) {
            for ( Cookie c : cookies ) {
                if ( c.getName().equals(COOKIE_NAME) ) {
                    System.out.println("Cookie Accepted");
                    userID = Integer.parseInt(c.getValue());
                    rmiInitialize();
                }
            }
        }
    }

    protected void onOpen(WsOutbound outbound) {
        System.out.println("Inside onOpen");
        try {
            System.out.println("Creating notification object");
            Notifications not = new Notifications(this);
            System.out.println("Registering Notification Callback");
            um.registerNotificationCallback(userID,not);
            System.out.println("onOpen done");
        } catch (RemoteException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void notify(String message, boolean isNotif) {
        JSONObject obj = new JSONObject();
        try {
            if(isNotif) {
                obj.put("notif", true);
                obj.put("message", message);
            }
            else {
                obj.put("notif", false);
                obj.put("message", message);
            }
        } catch (JSONException e) {

        }
        try {
            getWsOutbound().writeTextMessage(CharBuffer.wrap(obj.toString()));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    protected void onClose(int status) {
    }

    protected void onTextMessage(CharBuffer message) throws IOException {
    }

    protected void onBinaryMessage(ByteBuffer message) throws IOException {
        throw new UnsupportedOperationException("Binary messages not supported.");
    }

    protected void rmiInitialize() {
        try {
            um = (RemoteUserManager) Naming.lookup(rmiAddress + "UserManager");
        } catch (NotBoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (MalformedURLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (RemoteException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }



}
