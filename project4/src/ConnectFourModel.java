import java.util.LinkedList;
import java.io.IOException;
import java.util.Iterator;

/**
 * Model to keep track of the two players in a singleton board.
 * 
 * @author Shane
 *
 */
public class ConnectFourModel implements ViewListener{

	// Hidden data members.
	private C4Board board = new C4Board();
		
	private ModelListener playerOne = null;
	private ModelListener playerTwo = null;
	
	private String playerOneName = "";
	private String playerTwoName = "";
	
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
				playerOne = modelListener;
			}
			else if (players == 1){
				players = 2;
				playerTwo = modelListener;
			}		
			
			modelListener.playerJoin(players);	
			
		}
		catch(IOException ex){
			//Don't record bad listener
		}

	}	
	
	/**
	 * When a player joins the game alert all players
	 */
	public void join(ViewProxy proxy, String name) {
		try{
			if(players == 1){
				this.playerOneName = name;
			}
			else if (players == 2){
				this.playerTwoName = name;
			}
			
			if (playerOne != null){
				playerOne.setName(1, playerOneName);
			}
			
			if (playerTwo != null){
				playerTwo.setName(1, playerOneName);
				playerTwo.setName(2, playerTwoName);
				
				playerOne.setName(2, playerTwoName);
			} 
			
			if (players == 2){
				newGame();
			}
			
		}
		catch(IOException ex){
			System.err.println(ex);
		}		
	}

	/**
	 * Create a new game and reset the turns
	 */
	public void newGame() throws IOException {
		board.resetBoard();
		
		playerOne.newGame();
		playerTwo.newGame();
		
		turn = 1;
		playerOne.setTurn(turn);
		playerTwo.setTurn(turn);

	}

	/**
	 * Check if the action is valid, if not do not report anything
	 */
	public void action(int player, int column) throws IOException {
		int row = board.validMove(column);
		
		if (row != -1){
			board.addPlayerMarker(player, row, column);
			playerOne.addMove(player, row, column);
			playerTwo.addMove(player, row, column);
		}
		
		boolean dead = board.deadGame();
				
		int[] win = board.hasWon();
		
		if (win != null || dead){
			turn = 0;
		}
		else{
			if (turn == 1){
				turn = 2;
			}
			else{
				turn = 1;
			}
		}
		
		playerOne.setTurn(turn);
		playerTwo.setTurn(turn);		
		
	}

}
