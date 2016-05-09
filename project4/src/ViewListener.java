import java.io.IOException;

/**
 * This class is used as an interface for the View to talk to the 
 * Controller and Model
 * 
 * @author Shane
 *
 */
public interface ViewListener
{	
	/**
	 * Sends the name of the player to the server to join
	 * @param name Name of Player
	 * @throws IOException 
	 */
	public void join(ViewProxy proxy, String name) throws IOException;
	
	/**
	 * Sends the new game command to the server
	 * @throws IOException 
	 */
	public void newGame() throws IOException;
	
	/**
	 * Sends the player id and the move the player made to the server
	 * @param player (1 or 2) player id
	 * @param column column number of player move
	 * @throws IOException 
	 */
	public void action(int player, int column) throws IOException;
}