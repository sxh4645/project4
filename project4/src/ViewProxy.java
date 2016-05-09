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
		out 		= new DataOutputStream (socket.getOutputStream());
		in 			= new DataInputStream (socket.getInputStream());
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
	
	public void playerJoin(int player) throws IOException {
		out.writeByte('J');
		out.writeByte(player);
		out.flush();
		
	}

	public void setName(int player, String name) throws IOException {
		out.writeByte('N');
		out.writeByte(player);
		out.writeUTF(name);
		out.flush();
		
	}

	public void setTurn(int player) throws IOException {
		out.writeByte('T');
		out.writeByte(player);
		out.flush();	
	}

	public void addMove(int player, int r, int c) throws IOException {
		out.writeByte('A');
		out.writeByte(player);
		out.writeByte(r);
		out.writeByte(c);
		out.flush();
		
	}

	public void newGame() throws IOException {
		out.writeByte('C');
		out.flush();		
	}
	
	// Hidden helper classes.

	/**
	* Class ReaderThread receives messages from the network, decodes them, and
	* invokes the proper methods to process them.
	*
	* @author  Shane Hare
	*
	*/
	private class ReaderThread extends Thread
	{
			public void run()
			{
				try
	            {
					for (;;)
					{
						String name;
						int r, c;
						
						byte b = in.readByte();
						
						System.out.print("ServerInc: " + b + " ");
						
						switch (b)
						{
						case 'J':
							name = in.readUTF();
							System.out.println(name);
							viewListener.join (ViewProxy.this, name);
							break;
						case 'A':
							r = in.readByte();
							c = in.readByte();
							//viewListener.addMarker (r, c, color);
							break;
						case 'C':
							viewListener.newGame();
							break;
						default:
							System.err.println ("Bad message");
							break;
						}
	               }
	            }
			catch (IOException exc)
			{
			}
			finally
			{
				try
				{
					socket.close();
				}
			    catch (IOException exc)
				{
				}
		    }
		 }
      }

   }	
