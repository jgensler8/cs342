import java.util.ArrayList;

//import Card;

public class Player {
	protected String name;
	private ArrayList<Card> hand = new ArrayList<Card>();
	private ArrayList<Card> playable = new ArrayList<Card>();
	
	public Player(){
		name = "User";
	}
	public Player(String userName){
		this();
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
	public ArrayList<Card> getHand(){
		return hand;
	}
	
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
