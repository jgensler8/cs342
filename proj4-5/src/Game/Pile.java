package Game;

import java.io.Serializable;
import java.util.*;

public class Pile implements Serializable{
	private static final long serialVersionUID = 1L;
	private Stack<Card> _deck = null;
    private Stack<Card> _discards = null;

	/**
	 * construct a deck for Phase10 Game
	 */
	public Pile(){
		this._deck = new Stack<Card>();
		this._discards = new Stack<Card>();
		//this.initPhaseTen();
	}
	
	/**
	 * 
	 */
	public void shuffle(){
		
	}
	
	/**
	 * draw a card from the deck, return null if the deck is empty
	 */
	public Card draw(){
		if(this._deck.size() > 0)
			return this._deck.pop();
		else
			return null;
	}
	
	/**
	 * draw a specified amount of cards from the deck.
	 * return null if the amount is greater than the amount of cards in the deck
	 */
	public ArrayList<Card> draw(int numToDraw){
		if( numToDraw > this._deck.size() || numToDraw <= 0) return null;
		else{
			ArrayList<Card> toReturn = new ArrayList<Card>();
			for(int counter = 0; counter < numToDraw; counter++ ){
				toReturn.add( this._deck.pop() );
			}
			return toReturn;
		}
	}
	
	/**
	 * initialize deck for the Phase Ten Game
	 */
	public void initPhaseTen(){
		for(int type = Card.ONE; type <= Card.WILD; type++){
			this._deck.push( new Card(type, Card.BLUE));
			this._deck.push( new Card(type, Card.YELLOW));
			this._deck.push( new Card(type, Card.RED));
			this._deck.push( new Card(type, Card.YELLOW));
		}
	}
	
	/**
	 * @return either blank card or the card on top
	 */
	public Card renderDraw(){
		return new Card(Card.DRAW,Card.DRAW).render();
	}
	
	/**
	 * @return either blank card or the card on top
	 */
	public Card renderDiscard(){
		try{
			return this._discards.peek().render();
		} catch( EmptyStackException e){
			return new Card(Card.BLANK, Card.BLANK).render();
		}
	}
	
	/**
	 * 
	 */
	public void returnCard( Card c){
		this._discards.push( c);
	}
}
