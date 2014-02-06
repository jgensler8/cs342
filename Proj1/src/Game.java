import java.io.IOException;
import java.util.ArrayList;

public class Game {
	// the max hand size that is allowed for any hand
	public final static int MAX_HAND_SIZE = 5;
	// the max number of computer players that can play against the human
	public final static int MAX_COMPUTER_PLAYERS = 3;
	
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
		System.out.println("List the cards you wish to discard: ");
		makeHumanMove( human, deck, discard);
		
		for( Opponent opponent : opponents){
			makeComputerMove( opponent, deck, discard);
		}
		
		calcWinner( human, opponents);
	}
	
	private static void calcWinner(Human user, ArrayList<Opponent> opponents) {
		// TODO Auto-generated method stub
		
	}

	private static void makeHumanMove( Human human, CardPile deck, CardPile discard){
		//get the cards to discard
		
		//
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
			//TODO
		}
		else if( opponentHand.hasTwoPair() ){
			//TODO
		}
		else if( opponentHand.hasOnePair() ){
			//TODO
		}
		else{
			//TODO
		}
	}
}
