package Agents;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import Chat.ChatPanel;
import Game.GamePanel;

public class ClientAgent {

	private String _userName;
	private String _buddy;
	private Socket _soc;
	private ObjectInputStream _inStream;
	private ObjectOutputStream _outStream;
	private Thread _daemon;
	private ChatPanel _chatPanel;
	private GamePanel _gamePanel;

	public ClientAgent(InetAddress addr, int port, ChatPanel chatPanel, GamePanel gamePanel) throws IOException{
		//init the connection to the panels
		this._chatPanel = chatPanel;
		this._gamePanel = gamePanel;
		
		//TODO
		_userName = "SET ME UP WHEN I CONENCT TO SERVER";
		
		//init the connection
		new AcceptClient(addr, port).start();
		System.out.println("asdf");
	}

	private class AcceptClient extends Thread{
		public AcceptClient(InetAddress addr, int port) throws IOException{
			_soc = new Socket(addr, port);
			_inStream = new ObjectInputStream(_soc.getInputStream());
			_outStream = new ObjectOutputStream(_soc.getOutputStream());
		}
		public void run() {
			while (true) {
				try {
					Message message = (Message) _inStream.readObject();
					
					//cool switch case with message
					
					System.out.println(message);
					/*
					String[] msgArray = _inStream.readUTF().split("\\|\\|");
					if (msgArray.length > 1) {
						if (msgArray[0].equals(Message.ALL_USERS_IN_ROOM)) {
							//_chatPanel.setStuff();
							_userName = msgArray[1];
						} else if (msgArray[0].equals("$DATA$")) {
							_chatPanel.appendToDisplay("\n" + msgArray[1]);
						} else if (msgArray[0].equals("$ERROR$")) {
							_chatPanel.appendToDisplay("\n" + msgArray[1]);
						} else if (msgArray[0].equals("$USERS$")) {
							// Relay update list of online users to everyone
							_outStream.writeUTF(formatMsgToSrv("$UPDATE$",
									msgArray[1].trim()));
						} else if (msgArray[0].equals("$UPDATE$")) {
							String[] userArray = msgArray[1].trim().split("\\~");
							ArrayList<String> users = new ArrayList<String>();
							for( String user : userArray)
								users.add(user);
							_chatPanel.updateConnectedList(users);
						}
					}
					 */
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}	
		
	}

	/**
	 * called when the chat window is closing and the agent must tell the server
	 * that it is closing the conenction
	 */
	public void quit(){
		try {
            // send message to server that client disconnected, so server can
            // notify other clients with the updated list of online users
            _outStream.writeObject(new Message(this._userName, "", Message.PLAYER_EXITED, ""));
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	/**
	 * send text to the server
	 * @param message, the message to be sent
	 */
	public void sendMessage(String text) {
		try {
			_outStream.writeObject(new Message(this._userName, Message.ALL_USERS_IN_ROOM, Message.MESSAGE, text ));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
