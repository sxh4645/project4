import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Main program to connect to and play ConnectFour over a network.
 * 
 * @author Shane
 *
 */
public class ConnectFour {

	public static void main(String[] args) throws Exception {
		
		//DEBUG REMOVE ME
		args = new String[3];
		args[0] = "localhost";
		args[1] = "8080";
		args[2] = "Bob2";
		
		
		//java ConnectFour localhost 5678 Blake
		if ( args.length != 3){
			System.err.println("Usage: java ConnectFour <host> <port> <name>");
			System.exit(1);
		}
		
		//Variables
		String host 	= args[0];
		int port 		= Integer.parseInt(args[1]);
		String name 	= args[2];
		
		//Error Check for Connection to Socket
		try{
			//Create Socket to Server
			Socket socket = new Socket();
			socket.connect(new InetSocketAddress (host, port));
					        
			C4Board board 		= new C4Board();
			ModelProxy proxy 	= new ModelProxy(socket);
			C4UI view 			= new C4UI(board, name);
	        
			view.setViewListener(proxy);
			proxy.setModelListener(view);
			proxy.join(null, name);			
		}
		catch(UnknownHostException ex){
			System.err.println("UnknownHost at " + host);
		}

	}

}
