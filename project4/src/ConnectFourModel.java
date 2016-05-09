import java.util.LinkedList;
import java.io.IOException;
import java.util.Iterator;

public class ConnectFourModel implements ViewListener{

	// Hidden data members.
	private C4Board board = new C4Board();
	
	private LinkedList<ModelListener> listeners =
	      new LinkedList<ModelListener>();
	
	
	
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
	*/
	public synchronized void addModelListener (ModelListener modelListener)
	{
		listeners.add (modelListener);
	}	
	
	public void join(String name) {
		// TODO Auto-generated method stub
		
	}

	public void newGame() {
		// TODO Auto-generated method stub
		
	}

	public void action(int player, int column) {
		// TODO Auto-generated method stub
		
	}

}
