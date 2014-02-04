import java.util.ArrayList;

public class Hand {
	// the max hand size that is allowed for any hand
	public final int HANDSIZE = 5;
	// the actual list of cards that the hand contains
	ArrayList<Card> Cards = new ArrayList<Card>();
	
	public Hand(){	
	
	}
	
	/*
	 * add card to the players hand
	 * if the hand "overflows," throw an exception to stop the game
	 */
	public void add( Card C){
		if( Cards.size() > HANDSIZE){
			//TODO ***THROW SOME EXCEPTIN OR SOMETHING
		}
		else{
			Cards.add( C);
		}
	}
	
	/*
	 * discard a card index
	 */
	public Card remove(int index){
		return Cards.remove(index);
	}
	
	/*
	 * order the cards from highest value first to lowest value last
	 */
	public void orderDescending(){
		
	}
	
	/*
	 * return a deep copy of the players hand
	 */
	public ArrayList<Card> getHand(){
		//TODO make this a deep copy?
		return Cards;
	}
	
	/*
	 * return a score heuristic of the playable hand
	 * this heuristic can be used to see if one of two 
	 * hands of the same type ranks higher but will not
	 * work when these hands tie.
	 */
	public int evalHand(){
		int score = 0;
		if( hasStraightFlush() ) 	score += 160;
		else if( hasFourOfAKind() ) score += 140;
		else if( hasFullHouse() ) 	score += 120;
		else if( hasFlush() ) 		score += 100;
		else if( hasStraight() ) 	score += 80;
		else if( hasThreeOfAKind())	score += 60;
		else if( hasTwoPair() ) 	score += 40;
		else if( hasOnePair() ) 	score += 20;
		
		score += getHighestInPlayable();
		return score;
	}
	
	/*
	 * return the highest card in the playable hand
	 * this is used to rank two hands of the same type
	 */
	private int getHighestInPlayable() {
		//TODO *******************************MAKE THIS WORK FOR ACE HIGH************
		int highest = 0;
		for(int x = 0; x < Cards.size(); ++x){
			if( Cards.get(x).getRank() > highest ){
				highest = Cards.get(x).getRank();
			}
		}
		return highest;
	}
	
	/*
	 * separate functions to determine if a certain type of
	 * playable hand applies to the one in this player's hand
	 */
	private Boolean hasStraightFlush(){
		return false;
	}
	private Boolean hasFourOfAKind(){
		return false;
	}
	private Boolean hasFullHouse(){
		return false;
	}
	private Boolean hasFlush(){
		return false;
	}
	private Boolean hasStraight(){
		return false;
	}
	private Boolean hasThreeOfAKind(){
		return false;
	}
	private Boolean hasTwoPair(){
		return false;
	}
	private Boolean hasOnePair(){
		return false;
	}
	
}
