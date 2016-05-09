import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ViewProxy implements ModelListener
{
	// Hidden data members.
	private Socket socket;
	private DataOutputStream out;
	private DataInputStream in;
	private ViewListener viewListener;	
	
	// Exported constructors.

	/**
	* Construct a new view proxy.
	*
	* @param  socket  Socket.
	*
	* @exception  IOException
	*     Thrown if an I/O error occurred.
	*/	
	public ViewProxy(Socket socket) throws IOException{
		this.socket = socket;
		out = new DataOutputStream (socket.getOutputStream());
		in = new DataInputStream (socket.getInputStream());
	}
	
	// Exported operations.
	
	/**
	* Set the view listener object for this view proxy.
	*
	* @param  viewListener  View listener.
	*/	
	public void setViewListener(ViewListener viewListener){
		if (this.viewListener == null)
        {
			this.viewListener = viewListener;
			new ReaderThread() .start();
        }
		else
        {
			this.viewListener = viewListener;
        }		
	}
	
	public void playerJoin(int player) {
		// TODO Auto-generated method stub
		
	}

	public void setName(int player, String name) {
		// TODO Auto-generated method stub
		
	}

	public void setTurn(int player) {
		// TODO Auto-generated method stub
		
	}

	public void addMove(int player, int r, int c) {
		// TODO Auto-generated method stub
		
	}

	public void newGame() {
		// TODO Auto-generated method stub
		
	}

}
