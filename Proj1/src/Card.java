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
	public int getRank(){
		return rank;
	}
	public int getSuit(){
		return suit;
	}
}
