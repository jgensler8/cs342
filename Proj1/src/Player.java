import java.util.ArrayList;

//import Card;

public class Player {
	protected String name;
	protected ArrayList<Card> hand = new ArrayList<Card>();
	protected ArrayList<Card> playable = new ArrayList<Card>();
	protected int maxHandSize;
	
	public Player(int handSize){
		name = "User";
		maxHandSize = handSize;
	}
	public Player(int handSize, String userName){
		this(handSize);
		name = userName;
	}
	
	/*
	 * return the name of the player
	 */
	public String getName(){
		return name;
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
		for(int x = 0; x < playable.size(); ++x){
			if( playable.get(x).getRank() > highest ){
				highest = playable.get(x).getRank();
			}
		}
		return highest;
	}
	
	/*
	 * add card to the players hand
	 * if the hand "overflows," throw an exception to stop the game
	 */
	public void addCard( Card C){
		hand.add( C);
		if( hand.size() > maxHandSize){
			//TODO ***THROW SOME EXCEPTIN OR SOMETHING
		}
	}
	
	/*
	 * discard a card index
	 */
	public void discardFromHand(int index, CardPile discard){
		discard.returnCard( hand.remove(index));
	}
	
	/*
	 * return a deep copy of the players hand
	 */
	public ArrayList<Card> getHand(){
		//TODO make this a deep copy?
		return hand;
	}
	
	/*
	 * print the players hand to system.out
	 */
	public void printHand(){
		System.out.print( name + "'s hand: ");
		for(int cardNum = 0; cardNum < hand.size(); ++cardNum ){
			System.out.print( (cardNum+1) + ") " + hand.get(cardNum).getPrintable() + " ");
		}
		System.out.println("");
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
