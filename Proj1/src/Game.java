import java.io.IOException;
import java.util.ArrayList;

public class Game {
	// the max hand size that is allowed for any hand
	public final static int MAX_HAND_SIZE = 5;
	// the max number of computer players that can play against the human
	public final static int MAX_COMPUTER_PLAYERS = 3;
	// the normal discard limit
	public final static int NORMAL_DISCARD_LIMIT = 3;
	//the discard limit for holding an ace
	public final static int ACE_DISCARD_LIMIT = 4;
	
	
	public static void main( String[] Args){
		printGreeting();
		int numComps = getNumComps();
		
		//generate basic game items
		CardPile deck = new CardPile(1);
		deck.shuffle();
		CardPile discard = new CardPile();
		Human human = new Human("Jeff");
		ArrayList<Opponent> opponents = new ArrayList<Opponent>();
		
		//generate the number of robots to play against
		while( numComps >= 0){
			opponents.add( new Opponent("Computer " + Integer.toString(numComps) ));
			--numComps;
		}
		
		//deal out the cards
		initPlayersHands( human, opponents, deck);

		//start the game
		launchGame( human, opponents, deck, discard);
	}
	
	/*
	 * initial message sent at beginning of game
	 */
	static void printGreeting(){
		System.out.println("Welcome to Poker!");
	}
	
	/*
	 * Distribute maxHandSize cards to each player and robot
	 */
	static void initPlayersHands( Human human, ArrayList<Opponent> opponents, CardPile deck){
		for( int handCounter = 0; handCounter < MAX_HAND_SIZE; ++handCounter){
			human.getHand().add( deck.drawCard() );
			for( Opponent opponent : opponents){
				opponent.getHand().add( deck.drawCard() );
			}
		}
		human.getHand().orderDescending();
		for( Opponent R: opponents){
			R.getHand().orderDescending();
		}
	}
	
	/*
	 * Get the user to specify the number of computers it wants to play against
	 */
	static int getNumComps(){
		int userNumComps = 0;
		System.out.print("Enter the number of computers you want to play against: ");
		try {
			userNumComps = System.in.read();
			userNumComps -= 49;
		} catch (IOException e) {
			e.printStackTrace();
		}
		while( userNumComps < 1 && userNumComps > MAX_COMPUTER_PLAYERS){ // if the number of computers is not 1,2,3,or4
			System.out.println("Sorry, the number must be 1, 2, or 3");
			try {
				userNumComps = System.in.read();
				userNumComps -= 49;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return userNumComps;
	}
	
	/*
	 * the moves of user and robot are contained here
	 */
	static void launchGame(Human human, ArrayList<Opponent> opponents, CardPile deck, CardPile discard ){
		human.printHand();
		makeHumanMove( human, deck, discard);
		
		for( Opponent opponent : opponents){
			makeComputerMove( opponent, deck, discard);
		}
		
		calculateWinner( human, opponents);
	}
	
	/*
	 * find the winner and print it out
	 */
	private static void calculateWinner(Human human, ArrayList<Opponent> opponents) {
		//compare human hand to opponents hands
		//TODO
		//print out victor
		//TODO
	}

	/*
	 * get the cards that the human wants to discard
	 */
	private static void makeHumanMove( Human human, CardPile deck, CardPile discard){
		System.out.println("List the cards you wish to discard: ");
		if( human.getHand().hasVal( Card.ACE) ){
			//TODO
			//they can discard 4 cards BUT NOT including the ace
		}
		else{
			//TODO
			//they can discard 3 cards
		}
	}
	
	/*
	 * decides if it wants to draw more cards
	 */
	private static void makeComputerMove(Opponent opponent, CardPile deck, CardPile discard){
		Hand opponentHand = opponent.getHand();
		if( opponentHand.hasRoyalFlush() ){
			//don't do anything
		}
		else if( opponentHand.hasStraightFlush()){
			//don't do anything
		}
		else if( opponentHand.hasFourOfAKind() ){
			//don't do anything
		}
		else if( opponentHand.hasFullHouse() ){
			//don't do anything
		}
		else if( opponentHand.hasFlush() ){
			//don't do anything
		}
		else if( opponentHand.hasStraight() ){
			//don't do anything
		}
		else if( opponentHand.hasThreeOfAKind()){
			//basically, if one card is over a threshold (lets say 10)
			//then we would keep that card and discard the other in order to try and get a two pair
			//will not try and get flush
		}
		else if( opponentHand.hasTwoPair() ){
			//if the last card is over the threshold, don't do anything
			//else pick up a new card
		}
		else if( opponentHand.hasOnePair() ){
			//flush may be considered?
			//try and get a 3 of a kind if a card is over threshold
		}
		else{
			//flush?
			//straight?
			//we could use something along the lines of the one listed in the 
			//assignment pdf if you would like. It doesn't really make a difference
			//to me so long as it works
		}
	}
}
