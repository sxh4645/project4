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
	 */
	public void playerJoin(int player);
	
	/**
	 * Returns player name with their ID from the server
	 * @param player Id of player
	 * @param name String name of Player
	 */
	public void setName(int player, String name);
	
	/**
	 * Tells the View what players turn it is
	 * @param player ID of player
	 */
	public void setTurn(int player);

	/**
	 * Move returned from the Server
	 * 
	 * @param player Id of player
	 * @param r Row
	 * @param c Column
	 */
	public void addMove(int player, int r, int c);

	/**
	 * New game option returned from the server
	 */
	public void newGame();
}

