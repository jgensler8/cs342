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
	private int rank;
	private int suit;

	public Card(int userRank, int userSuit){
		if( userRank >= 1 || userRank <= 13){
			rank = userRank;
		}
		else{
			//TODO
		}
		if( userSuit >= 0 || userSuit <= 3){
			suit = userSuit;
		}
		else{
			//TODO
		}
	}
	
	/*
	 * get the rank of the card
	 */
	public int getRank(){
		return rank;
	}	
	
	/*
	 * get the suit of the card
	 */
	public int getSuit(){
		return suit;
	}
	
	/*
	 * generate a string that represents the value of the card
	 */
	public String getPrintable(){
		String printable = "";
		switch( rank){
		case 1: printable += "A"; break;
		case 11: printable += "J"; break;
		case 12: printable += "Q"; break;
		case 13: printable += "K"; break;
		default: printable += Integer.toString(rank);
		}
		printable += ",";
		switch( suit){
		case 0: printable += "Cl"; break;
		case 1: printable += "He"; break;
		case 2: printable += "Sp"; break;
		case 3: printable += "Di";
		}
		return printable;
	}
}
