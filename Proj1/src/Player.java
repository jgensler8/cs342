import java.util.ArrayList;

//import Card;

public class Player {
	protected String name;
	private ArrayList<Card> hand = new ArrayList<Card>();
	private ArrayList<Card> playable = new ArrayList<Card>();
	private int maxHandSize;
	
	public Player(int handSize){
		name = "User";
		maxHandSize = handSize;
	}
	public Player(int handSize, String userName){
		this(handSize);
		name = userName;
	}
	
	public String getName(){
		return name;
	}
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
	public void addCard( Card C){
		hand.add( C);
		if( hand.size() > maxHandSize){
			//TODO
		}
	}
	public void discardFromHand(Card c, CardPile discard){
		discard.returnCard( c);
	}
	public ArrayList<Card> getHand(){
		return hand;
	}
	
	/*
	 * Methods 
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
