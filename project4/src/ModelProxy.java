
import java.io.PrintStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

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
    private Scanner in;
    private PrintStream out;
    private ModelListener modelListener;
    
    // Exported constructors.
    /**
    * Construct a new model proxy.
    * ... 
    */
    public ModelProxy(Socket socket) throws IOException{
        this.socket = socket;
		out = new PrintStream(socket.getOutputStream());
		in  = new Scanner(socket.getInputStream());
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
	*/
	public void join(String name)
	{
	    out.print("join " + name + System.lineSeparator());
	    out.flush();
	}
    
	/**
	 * Sends the clear code to the server to reset the game
	 */
	public void newGame() {
		out.print("clear" + System.lineSeparator());
		out.flush();
	}

	/**
	 * Sends the move made from the UI to the server
	 */
	public void action(int player, int column) {
		out.print("add " + player + " " + column + System.lineSeparator());
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
                	
                	String read = in.nextLine();
                	String[] data = read.split(" ");          	
                	
                    switch (data[0]) {
                        // number <p>
                        case "number":
                        	modelListener.playerJoin(Integer.parseInt(data[1]));
                            break;
                        // name <p> <n>
                        case "name":
                        	modelListener.setName(Integer.parseInt(data[1]), data[2]);
                        	break;
                        //turn <p>
                        case "turn":
                        	modelListener.setTurn(Integer.parseInt(data[1]));
                        	break;
                        //add <p> <r> <c>
                        case "add":
                        	modelListener.addMove(Integer.parseInt(data[1]),
                        			Integer.parseInt(data[2]), Integer.parseInt(data[3]));
                        	break;
                        case "clear":
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
