import java.util.LinkedList;
import java.io.IOException;
import java.util.Iterator;

public class ConnectFourModel implements ViewListener{

	// Hidden data members.
	private C4Board board = new C4Board();
	
	private LinkedList<ModelListener> listeners =
	      new LinkedList<ModelListener>();
	
	private int players = 0;
	private int turn 	= 0;
	
	// Exported constructors.

	/**
	* Construct a new Go model.
	*/	
	public ConnectFourModel()
	{
		
	}
   
	// Exported operations.
	/**
	* Add the given model listener to this Go model.
	*
	* @param  modelListener  Model listener.
	 * @throws IOException 
	*/
	public synchronized void addModelListener (ModelListener modelListener)
	{
		try{

			//Determine Players
			if(players == 0){
				players = 1;
			}
			else if (players == 1){
				players = 2;
			}
			
			modelListener.playerJoin(players);
						
			listeners.add (modelListener);		
			
		}
		catch(IOException ex){
			//Don't record bad listener
			players = players - 1;
			System.err.println(ex);
		}

	}	
	
	public void join(ViewProxy proxy, String name) {
		try{
			proxy.setName(players, name);
		}
		catch(IOException ex){
			System.err.println(ex);
		}		
	}

	public void newGame() {
		board.resetBoard();
		
		turn = 1;
		
		try{
			Iterator<ModelListener> iter = listeners.iterator();
			while (iter.hasNext()){
				ModelListener listener = iter.next();
				listener.setTurn(turn);
			}
		}
		catch(IOException ex){
			
		}
	}

	public void action(int player, int column) {
			
	}

}
