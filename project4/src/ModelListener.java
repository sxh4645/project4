import java.io.IOException;

/**
 * Used for the Communication to the View and the Controller
 * 
 * @author Shane
 *
 */
public interface ModelListener {

	/**
	 * Id of player returned from the Server
	 * @param player Id of player
	 * @throws IOException 
	 */
	public void playerJoin(int player) throws IOException;
	
	/**
	 * Returns player name with their ID from the server
	 * @param player Id of player
	 * @param name String name of Player
	 * @throws IOException 
	 */
	public void setName(int player, String name) throws IOException;
	
	/**
	 * Tells the View what players turn it is
	 * @param player ID of player
	 * @throws IOException 
	 */
	public void setTurn(int player) throws IOException;

	/**
	 * Move returned from the Server
	 * 
	 * @param player Id of player
	 * @param r Row
	 * @param c Column
	 * @throws IOException 
	 */
	public void addMove(int player, int r, int c) throws IOException;

	/**
	 * New game option returned from the server
	 * @throws IOException 
	 */
	public void newGame() throws IOException;
}

