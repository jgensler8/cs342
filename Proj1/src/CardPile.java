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
	private ArrayList<Card> pile = new ArrayList<Card>();
	
	public CardPile(){
	}
	public CardPile(int mode){
		if( mode == 1 ){
			pile = generateDeck();
		}
	}
	
	/*
	 * pull a card form the deck
	 * if a card can't be drawn, throw an exception
	 */
	public Card drawCard(){
		try{
			return pile.remove(0);
		}catch(Exception e){
			e.printStackTrace();
		}
		Card top = new Card(0,0);
		return top;
	}
	
	/*
	 * giving a card back to a pile (mostly used to discard)
	 */
	public void returnCard(Card toReturn){
		pile.add(toReturn);
	}
	
	/*
	 * generate a standard 52 card deck and return it
	 */
	private ArrayList<Card> generateDeck(){
		ArrayList<Card> deckPile = new ArrayList<Card>();
		for(int rank = 1; rank < 14; ++rank){
			for(int suit = 0; suit < 4; ++suit){
				Card card = new Card(rank, suit);
				deckPile.add( card);
			}
		}
		return deckPile;
	}
	
	/*
	 * shuffle the deck
	 */
	public void shuffle(){
		Card temp;
		int randomIndex;
		for(int switches = 0; switches < 200; ++switches){
			randomIndex = ((int)Math.random()) % pile.size();
			temp = pile.remove(randomIndex);
			pile.add( temp);
		}
	}
}
