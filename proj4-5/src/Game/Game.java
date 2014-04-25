package Game;

import java.io.*;
import java.util.*;

import javax.swing.JLabel;

public class Game implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<Player> _players;
	Deck _draw;
	Deck _discard;
	Table _table;
	
	/**
	 * 
	 */
	public Game(ArrayList<String> playersNames){
		//initialize the decks
		this._draw = new Deck();
		this._draw.initPhaseTen();
		this._discard = new Deck();
		
		//initialize the players
		this._players = new ArrayList<Player>();
		for(int playerIndex = 0; playerIndex < playersNames.size(); playerIndex++){
			this._players.add(playerIndex, new Player( playersNames.get(playerIndex) ) );
			this._players.get(playerIndex).addCards( this._draw.draw(5) );
		}
	}
	
	
	
	
	//// *** moves of a player
	
	/**
	 * called when a player wants to discard a card from their hand
	 * @param player , the name of the player that wants to discard a card from their hand
	 * @param card , the card that the player wished to discard
	 */
	public void discard( String player, Card card){
		
	}
	
	/**
	 * called when a player wants to draw a card
	 * @param player , the player that wants to draw the card
	 * @param deckType , 1: draw deck 2: discard deck
	 */
	public void drawcard( String player, int deckType){
		
	}
	
	/**
	 * called when a player wants to make a play with the selected cards
	 */
	public void makePlay( String player, ArrayList<Card> cards){
		
	}
}
