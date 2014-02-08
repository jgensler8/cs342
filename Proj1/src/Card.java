import java.util.Comparator;

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
public class Card {
	//Ranks
	public final static int ACE = 1;
	public final static int KING = 13;
	public final static int QUEEN = 12;
	public final static int JACK = 11;
	public final static int TEN = 10;
	public final static int NINE = 9;
	public final static int EIGHT = 8;
	public final static int SEVEN = 7;
	public final static int SIX = 6;
	public final static int FIVE = 5;
	public final static int FOUR = 4;
	public final static int THREE = 3;
	public final static int TWO = 2;
	//Suits
	public final static int CLUBS = 1;
	public final static int HEARTS = 2;
	public final static int SPADES = 3;
	public final static int DIAMONDS = 4;
	//member variables
	private int _rank;
	private int _suit;
	
	
	public Card(){
		_rank = -1;
		_suit = -1;
	}
	public Card(int userRank, int userSuit){
		if( userRank >= ACE || userRank <= KING){
			_rank = userRank;
		}
		else{
			//TODO
			// defensive programming, maybe throw exception
		}
		if( userSuit == CLUBS || userSuit == HEARTS 
				|| userSuit == SPADES || userSuit == DIAMONDS){
			_suit = userSuit;
		}
		else{
			//TODO
			// defensive programming, maybe throw exception
		}
	}
	
	/*
	 * get the rank of the card
	 */
	public int getRank(){
		return _rank;
	}	
	
	/*
	 * get the suit of the card
	 */
	public int getSuit(){
		return _suit;
	}
	
	/*
	 * generate a string that represents the value of the card
	 */
	public String toString(){
		String printable = "";
		switch( _rank){
		case 1: printable += "A"; break;
		case 11: printable += "J"; break;
		case 12: printable += "Q"; break;
		case 13: printable += "K"; break;
		default: printable += Integer.toString(_rank);
		}
		printable += ",";
		switch( _suit){
		case 1: printable += "Cl"; break;
		case 2: printable += "He"; break;
		case 3: printable += "Sp"; break;
		case 4: printable += "Di";
		}
		return printable;
	}
	
	/*
	 * used in sorting a hand in descending value
	 */
	public static Comparator<Card> cardComparatorDesc = new Comparator<Card>(){
		public int compare(Card A, Card B){
			return B._rank - A._rank;
		}	
	};
}
