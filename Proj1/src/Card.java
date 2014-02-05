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
	private int _rank;
	private int _suit;
	public final static int ACE = 1;
	public final static int KING = 13;
	public final static int CLUBS = 1;
	public final static int HEARTS = 2;
	public final static int SPADES = 3;
	public final static int DIAMONDS = 4;
	
	
	public Card(){
		_rank = -1;
		_suit = -1;
	}
	public Card(int userRank, int userSuit){
		if( userRank >= 1 || userRank <= 13){
			_rank = userRank;
		}
		else{
			//TODO
		}
		if( userSuit >= 0 || userSuit <= 3){
			_suit = userSuit;
		}
		else{
			//TODO
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
		case 0: printable += "Cl"; break;
		case 1: printable += "He"; break;
		case 2: printable += "Sp"; break;
		case 3: printable += "Di";
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
