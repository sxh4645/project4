
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * 
 * @author Shane
 * 
 * Used as the main communication from the Controller + View to the Model (Server)
 *
 */
public class ModelProxy implements ViewListener{

	// Hidden data members.
	private Socket socket;
	private DataOutputStream out;
	private DataInputStream in;
	private ModelListener modelListener;
    
    // Exported constructors.
    /**
    * Construct a new model proxy.
    * ... 
    */
    public ModelProxy(Socket socket) throws IOException{
        this.socket = socket;
        out = new DataOutputStream (socket.getOutputStream());
        in = new DataInputStream (socket.getInputStream());
    }
    
    // Exported operations.
    /**
    * Set the model listener object for this model proxy.
    * ... 
    */
    public void setModelListener(ModelListener modelListener){
        this.modelListener = modelListener;
        new ReaderThread() .start();
    }

	/**
	* join with the player name to send to the server
	* ... 
	 * @throws IOException 
	*/
	public void join(ViewProxy proxy, String name) throws IOException
	{
		out.writeByte('J');
		out.writeUTF(name);
	    out.flush();
	}
    
	/**
	 * Sends the clear code to the server to reset the game
	 * @throws IOException 
	 */
	public void newGame() throws IOException {
		out.writeByte('C');
		out.flush();
	}

	/**
	 * Sends the move made from the UI to the server
	 * @throws IOException 
	 */
	public void action(int player, int column) throws IOException {
		out.writeByte('A');
		out.writeByte(player);
		out.writeByte(column);
		out.flush();		
	}    
    

// Hidden helper classes.
    /**
     * Class ReaderThread receives messages from the network,
     * decodes them, and invokes the proper methods to process them.
     * ... 
     */
    private class ReaderThread extends Thread
    {
        public void run() {
            try {        	
                while (true) {
                	
                	String name;
                	int r, c, id, t;
                	
                	byte b = in.readByte();                	        	
                	
                    switch (b) {
                        // number <p>
                        case 'J':
                        	id = in.readByte();
                        	System.out.println("ClientInc: " + b + " " + id);
                        	modelListener.playerJoin(id);
                            break;
                        // name <p> <n>
                        case 'N':
                        	id 		= in.readByte();
                        	name 	= in.readUTF();
                        	System.out.println("ClientInc: " + b + " " + id + " " + name);
                        	modelListener.setName(id, name);
                        	break;
                        //turn <p>
                        case 'T':
                        	t = in.readByte();
                        	modelListener.setTurn(t);
                        	break;
                        //add <p> <r> <c>
                        case 'A':
                        	id = in.readByte();
                        	r = in.readByte();
                        	c = in.readByte();
                        	modelListener.addMove(id, r, c);
                        	break;
                        case 'C':
                        	modelListener.newGame();
                        	break;
                        default:
                            System.err.println ("Bad message");
                            break;
                        }
                    }
                }
           catch (Exception exc) 
           {
           }
            finally {
                try {
                    socket.close();
                    }
                catch (IOException exc) {
                    }
                }
            }
        }
    }
