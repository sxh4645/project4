import java.util.HashMap;

/**
 * Class SessionManager maintains the sessions' model objects in the Network
 * ConnectFour Game server.
 * 
 * @author Shane Hare
 *
 */
public class SessionManager implements ViewListener{

	// Hidden data members.
    private HashMap<Integer,ConnectFourModel> sessions =
	      new HashMap<Integer,ConnectFourModel>();	
	   
	private int sessionID = 0;
	
	public synchronized void join(ViewProxy proxy, String name) {
		ConnectFourModel model = sessions.get(sessionID);
		if (model == null){
			System.out.println("Creating new Session");
			model = new ConnectFourModel();
			sessions.put(sessionID, model);
		}
		else{
			System.out.println("Found Existing Session");
			//Two Players found, go to next Iteration
			sessionID = sessionID + 1;
		}
		
		model.addModelListener(proxy);
		proxy.setViewListener(model);
		model.join(proxy, name);
	}

	public void newGame() {
		// TODO Auto-generated method stub
		
	}

	public void action(int player, int column) {
		// TODO Auto-generated method stub
		
	}
	
}
