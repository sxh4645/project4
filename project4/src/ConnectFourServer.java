import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectFourServer {
	
	public static void main (String[] args) throws Exception
    {
		//DEBUG REMOVE ME
		args = new String[2];
		args[0] = "localhost";
		args[1] = "8080";
		
		if (args.length != 2) usage();
		String host = args[0];
		int port = Integer.parseInt (args[1]);

		ServerSocket serversocket = new ServerSocket();
		serversocket.bind (new InetSocketAddress (host, port));

		SessionManager manager = new SessionManager();

		for (;;)
		{
			Socket socket = serversocket.accept();
			ViewProxy proxy = new ViewProxy (socket);
			proxy.setViewListener (manager);
		}
    }

	/**
	 * Print a usage message and exit.
	 */
	private static void usage()
	{
		System.err.println ("Usage: java GoServer <host> <port>");
		System.exit (1);
	}
}
