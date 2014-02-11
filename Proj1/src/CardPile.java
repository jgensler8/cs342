import java.util.ArrayList;

public class CardPile {

	private ArrayList<Card> _pile;

	public CardPile() {
		_pile = new ArrayList<Card>();
	}

	/*
	 * initializes our current pile to a deck of 52 cards
	 * (this will only be called when shuffle is requested)
	 */
	private void init() {
		for (int rank = Rank.ACE; rank <= Rank.KING; ++rank) {
			_pile.add(new Card(Suit.CLUBS, rank));
			_pile.add(new Card(Suit.HEARTS, rank));
			_pile.add(new Card(Suit.SPADES, rank));
			_pile.add(new Card(Suit.DIAMONDS, rank));
		}
	}

	/*
	 * randomizes position of cards within deck
	 */
	public void shuffle() {
		init();
		
		//--- Shuffle by exchanging each element randomly
		Card temp;
		int randomIndex;
		int randomSwitches = 500 + ((int)(512*Math.random())%200); //at least 500 plus up to 599 more
		for(int switches = 0; switches < randomSwitches; ++switches){
			randomIndex = ((int)(1024*Math.random())) % _pile.size();
			temp = _pile.remove(randomIndex);
			_pile.add( temp);
		}
	}

	/*
	 * pull a card from deck if card can't be drawn, throw exception
	 */
	public Card drawCard() {
		if (!_pile.isEmpty()) {
			try {
				return _pile.remove(0);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Card top = new Card();
		return top;
	}

	/*
	 * giving a card back to a pile (mostly used to discard)
	 */
	public void returnCard(Card card){
		_pile.add(card);
	}

}
