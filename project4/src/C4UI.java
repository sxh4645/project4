import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Class C4UI provides the user interface for the network game of Connect Four.
 *
 * @author  Alan Kaminsky
 * @version 13-Oct-2014
 */
public class C4UI implements ModelListener
	{

// Hidden data members.

	private C4BoardIntf c4board;

	private JFrame frame;
	private C4Panel boardPanel;
	private JTextField message;
	private JButton newGameButton;
	private ViewListener viewListener;
	
	private int player 		= -1;
	private int playerTurn 	= -1;
	
	private String playerName;
	private String opponentName;

	// Exported constructors.

	/**
	 * Construct a new Connect Four UI.
	 *
	 * @param  board  Connect Four board.
	 * @param  name   Player's name.
	 */
	public C4UI(C4BoardIntf board, String name)
		{
		c4board = board;

		// Set up window.
		frame = new JFrame ("Connect Four -- " + name);
		Container pane = frame.getContentPane();
		JPanel p1 = new JPanel();
		pane.add (p1);
		p1.setLayout (new BoxLayout (p1, BoxLayout.Y_AXIS));
		p1.setBorder (BorderFactory.createEmptyBorder (10, 10, 10, 10));

		// Create and add widgets.
		boardPanel = new C4Panel (c4board);
		boardPanel.setAlignmentX (0.5f);
		p1.add (boardPanel);
		p1.add (Box.createVerticalStrut (10));
		JPanel p2 = new JPanel();
		p2.setLayout (new BoxLayout (p2, BoxLayout.X_AXIS));
		p2.setAlignmentX (0.5f);
		p1.add (p2);
		message = new JTextField (20);
		message.setAlignmentY (0.5f);
		message.setEditable (false);
		message.setText ("Waiting for partner");
		p2.add (message);
		p2.add (Box.createHorizontalStrut (10));
		newGameButton = new JButton ("New Game");
		newGameButton.setAlignmentY (0.5f);
		newGameButton.setEnabled (false);
		p2.add (newGameButton);

		// Clicking the Connect Four panel informs the view listener.
		boardPanel.addMouseListener (new MouseAdapter()
			{
			public void mouseClicked (MouseEvent e)
				{
					//Don't continue if it's not your turn
					if (playerTurn == player){
						int c = boardPanel.clickToColumn (e);

						viewListener.action(player, c);

					}
				}
			});

		// Clicking the New Game button informs the view listener.
		newGameButton.addActionListener (new ActionListener()
			{
			public void actionPerformed (ActionEvent e)
				{
					viewListener.newGame();
				}
			});

		// Closing the window exits the client.
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);

		// Display window.
		frame.pack();
		frame.setVisible (true);
		}
	
	/**
	 * 
	 * @param viewListener
	 */
	public void setViewListener(ViewListener viewListener)
	{	
		this.viewListener = viewListener;
	}

	/**
	 *  When the player starts the game he sets what player they are (1 or 2)
	 */
	public void playerJoin(int player) {
		this.player 	= player;	
		this.playerTurn = 0;
	}

	/**
	 * Used to save the name of the players for display purposes
	 */
	public void setName(int player, String name) {
		if (player == this.player){
			this.playerName = name;
		}
		else{
			this.opponentName = name;
		}
	}
	
	/**
	 * Used from the viewListener to display information to the UI
	 */
	public void setTurn(int player) {
		this.playerTurn = player;
		
		//Allow new Game button to be pressed
		newGameButton.setEnabled (true);
		
		//Display which player's turn it is
		if (this.playerTurn == 0 || c4board.deadGame()){
			message.setText ("Game over");
		}
		else if (this.playerTurn == this.player){
			message.setText ("Your turn");
		}
		else{
			message.setText (opponentName + "'s turn");
		}
	}

	/**
	 * When the provided move is given from the server it will
	 * change the board state and repaint it	 
	 */
	public void addMove(int player, int r, int c){
		c4board.addPlayerMarker(player, r, c);
		
		boardPanel.repaint();
	}

	/**
	 * When the server sends a new game, clear the board and reset the ui.
	 */
	public void newGame(){
		c4board.resetBoard();

		boardPanel.repaint();
	}

}
