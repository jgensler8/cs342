package Game;


import java.util.*;

public class Player {
	private Hand _hand;		//players hand of cards
	private String _name;	//players name
	
	public Player(String name){
		this._name = name;
		this._hand = new Hand();
	}
	
	/**
	 * add a card to the players hand
	 */
	public void addCard(Card card){
		this._hand.addCard(card);
	}
	
	/**
	 * add a list of cards to the hand
	 */
	public void addCards(ArrayList<Card> cards){
		for(Card card : cards){
			this._hand.addCard( card);	
		}
	}
	
	/**
	 * remove a particular card from the player's hand
	 */
	public Card removeCard(int index){
		return this._hand.removeCard(index);
	}
	
	/**
	 * return a copy of the player's hand
	 * TODO
	 */
	public Hand getHand(){
		return this._hand;
	}
}
