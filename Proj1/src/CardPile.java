/*
 * CARD VALUE
 * 1 = ace
 * 2 = two
 * 3 = three
 * ...
 * 9 = nine
 * 10 = ten
 * 11 = jack
 * 12 = queen
 * 13 = king
 * 
 * CARD SUIT
 * 0 = club
 * 1 = heart
 * 2 = spade
 * 3 = diamond
 */
import java.util.ArrayList;

public class CardPile {
	
	private ArrayList<Card> _pile;
	
	public CardPile(){
		_pile = new ArrayList<Card>();
	}
	public CardPile(int mode){
		this();
		if( mode == 1 ){
			generateDeck();
		}
	}
	
	/*
	 * pull a card form the deck
	 * if a card can't be drawn, throw an exception
	 */
	public Card drawCard(){
		if( !_pile.isEmpty() ){
			return _pile.remove(0);
		}
		Card top = new Card(0,0);
		return top;
	}
	
	/*
	 * giving a card back to a pile (mostly used to discard)
	 */
	public void returnCard(Card card){
		_pile.add(card);
	}
	
	/*
	 * generate a standard 52 card deck and return it
	 */
	private void generateDeck(){
		for(int rank = Card.ACE; rank <= Card.KING; ++rank){
			_pile.add( new Card(rank, Card.CLUBS));
			_pile.add( new Card(rank, Card.HEARTS));
			_pile.add( new Card(rank, Card.SPADES));
			_pile.add( new Card(rank, Card.DIAMONDS));
		}
	}
	
	/*
	 * shuffle the deck
	 */
	public void shuffle(){
		Card temp;
		int randomIndex;
		for(int switches = 0; switches < 200; ++switches){
			randomIndex = ((int)Math.random()) % _pile.size();
			temp = _pile.remove(randomIndex);
			_pile.add( temp);
		}
	}
	
	/*
	 * add an array of cards to this card pile
	 */
	public void returnCards(ArrayList<Card> toDiscardCards) {
		_pile.addAll( toDiscardCards);
	}
}
