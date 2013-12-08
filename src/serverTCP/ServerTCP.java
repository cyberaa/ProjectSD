package serverTCP;


import java.io.IOException;
import java.net.*;
import java.rmi.RMISecurityManager;

/**
 * Created with IntelliJ IDEA.
 * User: joaosimoes
 * Date: 10/15/13
 * Time: 4:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class ServerTCP
{
    //RMI connection
    protected static String rmiServerAddress;
    protected static int rmiRegistryPort;

	//UDP connection to other server;
	protected static int ownUDPport;
	protected static InetAddress serverTCPaddress;
	protected static int serverTCPport;
	protected static DatagramSocket socketUDP;
	protected static byte[] buffer;
	protected static DatagramPacket dataUDP;

    //TCP UserConnection
    protected static int conPort;
    protected static ServerSocket conListenSocket;
	protected static int notPort;
	protected static ServerSocket notListenSocket;

	public static Notifications nots;

	protected static boolean isMain = false;

	private static final int timeout = 10;

    public static void main(String args[]) {
        //Verify the number of given arguments.
        if(args.length != 7)
        {
            System.out.println("Usage: java ServerTCP <server_udp_port> <server_connection_port> <server_notify_port> <rmi_registry_address> <rmi_registry_port> <other_server_address> <other_server_port>");
            return;
        }

	    //Set system policies.
	    System.getProperties().put("java.security.policy", "policy.all");
        System.setProperty("java.rmi.server.hostname", "JN-AsusUbuntu");
	    //System.setSecurityManager(new RMISecurityManager());

	    //Get command line arguments.
	    ownUDPport = Integer.parseInt(args[0]);
        conPort = Integer.parseInt(args[1]);
	    notPort = Integer.parseInt(args[2]);
        rmiServerAddress = args[3];
        rmiRegistryPort = Integer.parseInt(args[4]);
	    try {
		    serverTCPaddress = InetAddress.getByName(args[5]);
	    } catch (UnknownHostException e) {
		    System.out.println("Invalid IP address for other server:\n" + e);
	    }
	    serverTCPport = Integer.parseInt(args[6]);

	    try {
		    socketUDP = new DatagramSocket(ownUDPport);
		    buffer = new byte[1];
		    dataUDP = new DatagramPacket(buffer, buffer.length);
	    } catch (SocketException e) {
		    System.out.println("Could not create UDP listener socket:\n" + e);
		    return;
	    }

	    //Check if other server is up and as a main.
	    tryReceive();

	    while (true)
	    {
		    if (isMain)
			    runMainServer();
		    else
			    runSecondaryServer();
	    }
    }

    protected static void runMainServer()
    {
	    System.out.println("Becoming main server!");

        try {
            conListenSocket = new ServerSocket(conPort);
            //conListenSocket.setSoTimeout(timeout);
        } catch (IOException ie) {
            System.out.println("Error in server socket creation.\n"+ ie);
        }

        System.out.println("RMI server at: "+rmiServerAddress+":"+rmiRegistryPort);
        System.out.println("Ready to accept connections.\nConnection port:\t"+conPort+"\nNotifications port:\t"+notPort+"\n");

        Socket s1, s2;

        while (isMain)
        {
            send();

            try {
	            s2 = conListenSocket.accept();

	            new UserConnection(s2);
            } catch (SocketTimeoutException e) {
                //Do nothing.
                continue;
            } catch (IOException e) {
                System.out.println("UserConnection listen socket error:\n" + e);
                continue;
            }
        }
    }

	protected static void runSecondaryServer()
	{
		System.out.println("Becoming secondary server.");

		while(!isMain)
			tryReceive();
	}

	protected static void tryReceive()
	{
		try {
			socketUDP.setSoTimeout(1000);
			socketUDP.receive(dataUDP);
			isMain = false;
		} catch (SocketException e) {
			isMain = true;
		} catch (IOException e) {
			isMain = true;
		}
	}

	protected static void send()
	{
        String msg = "ping";
        byte[] bufferSend  = msg.getBytes();
		DatagramPacket data = new DatagramPacket(bufferSend, bufferSend.length, serverTCPaddress, serverTCPport);
		try {
			socketUDP.send(data);
		} catch (IOException e) {
			//System.out.println("Could not send DatagramPacket:\n" + e);
		}
	}
}
