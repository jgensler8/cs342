package Game;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.UUID;

/**
 * The Player class maintains an identity of a player and their current game
 * state.
 * 
 * @author Azita
 * 
 */
public class Player implements Serializable{

    // delimiter used for serialization - must be unique to class
    private final static String DELIMITER = "#";

    // Unique identifier of player
    private String _ID;
    // player's screen name
    private String _name;
    // room assigned to player
    private String _roomID;
    // cards in player's hand
    private Hand _hand;
    // player's input stream (needed because it can be instantiate only once)
    private transient ObjectInputStream _inStream;
    // player's input stream (needed because it can be instantiate only once)
    private transient ObjectOutputStream _outStream;


    /**
     * FOR USE ON THE SERVER
     */
    public Player(String name) {
            init();
            _name = name;
    }

    /**
     * FOR USE ON THE SERVER
     */
    public Player(String ID, String name) {
            init();
            _ID = ID;
            _name = name;
    }
    
    /**
     * FOR USE ON THE CLIENT ? XXX
     */
    public Player(Player p){
    	this._ID = p._ID;
    	this._name = p._name;
    	this._roomID = p._roomID;
    	this._hand = p._hand;
    }

    /**
     * helper method to class constructor
     */
    private void init() {
    	 // generate random unique identifier
        _ID = UUID.randomUUID().toString();
        _hand = new Hand();
        _roomID = "";
    }

    /**
     * @return Player's Unique identifier
     */
    public String getID() {
            return _ID;
    }

    /**
     * assign socket connection between server and client
     * 
     * @param socket
     *            socket connection
     * @throws IOException
     */
    public void setSocketStream(ObjectInputStream inStream, ObjectOutputStream outStream) throws IOException {
            _inStream = inStream;
            _outStream = outStream;
    }

    /**
     * @return player input stream given socket
     */
    public ObjectInputStream getInputStream() {
            return _inStream;
    }

    /**
     * @return player output stream given socket
     */
    public ObjectOutputStream getOutputStream() {
            return _outStream;
    }
    
    /**
     * assign player to a room
     * 
     * @param room
     *            unique identifier of room
     */
    public void setRoomID(String roomID) {
            _roomID = roomID;
    }

    /**
     * @return unique identifier of room player is assigned to
     */
    public String getRoomID() {
            return _roomID;
    }

    /**
     * @return player's screen name
     */
    public String getName() {
            return _name;
    }

    /**
     * @return cards in player's possession
     */
    public Hand getHand() {
            return _hand;
    }
}

