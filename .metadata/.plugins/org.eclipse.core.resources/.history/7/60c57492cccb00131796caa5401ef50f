package Game;

import java.util.*;

import javax.swing.*;

public class Hand extends JPanel{
	private ArrayList<Card> _cards;
	
	/**
	 * construct a hand;
	 */
	public Hand(){
		this._cards = new ArrayList<Card>();
	}
	
	/**
	 * add a card to the hand
	 * @param the Card to add to the hand
	 */
	public void addCard(Card toAdd){
		this._cards.add(toAdd);
	}
	
	/**
	 * remove a card from the hand
	 * @param index of the card to remove
	 */
	public Card removeCard(int index){
		return this._cards.remove(index);
	}
	
	/**
	 * reset the hand's attributes
	 */
	public Hand render(){
		this.removeAll();
		for(Card c : this._cards){
			this.add(c);
		}
		return this;
	}
}
