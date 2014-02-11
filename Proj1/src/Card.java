
public class Card {

	private int _rank;
	private int _suit;

	public Card() {
		_rank = -1;
		_suit = -1;
	}

	public Card(int suit, int rank) {

		if (Suit.isValid(suit))
			_suit = suit;
		else {
			_suit = -1;
		}

		if (Rank.isValid(rank))
			_rank = rank;
		else {
			_rank = -1;
		}

	}

	/*
	 * get the rank of the card
	 */
	public int getRank() {
		return _rank;
	}

	/*
	 * get the suit of the card
	 */
	public int getSuit() {
		return _suit;
	}

	/*
	 * return string representation of card instance
	 */
	public String toString() {
		return Rank.toString(_rank) + Suit.toString(_suit);
	}

	/*
	 * return true if card instance is valid
	 */
	public Boolean isValid() {
		return _suit != -1 && _rank != -1;
	}
}
