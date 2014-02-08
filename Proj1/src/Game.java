import java.util.ArrayList;
import java.util.Scanner;

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
		while( numComps > 0){
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
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter the number of computers you want to play against: ");
		int numComputers  = scanner.nextInt();
		while( numComputers < 1 || numComputers > 3){
			System.out.println("Sorry, the number must be 1, 2, or 3. Enter again: ");
			numComputers = scanner.nextInt();
		}
		return numComputers;
	}
	
	/*
	 * the moves of user and robot are contained here
	 */
	static void launchGame(Human human, ArrayList<Opponent> opponents, CardPile deck, CardPile discard ){
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
		//print out all cards
		human.printHand();
		for( Opponent opponent : opponents)
			opponent.printHand();
		//print out victor
		//TODO
	}
	
	/*
	 * used to check if a human has entered valid cards to discard
	 * the indices stored in the array list are assumed to be ADJUSTED to start at
	 * index 0.
	 * possible user errors:
	 *    - index could be out of bound
	 *    - number of indices could be too high
	 *    - if they have an ace and discard 4, the ace can't be discarded (right?)
	 *    - enter the same number twice
	 */
	private static Boolean isValidDiscard(Hand hand, ArrayList<Integer> toDiscard){
		if( toDiscard.isEmpty() ) return true;
		int discardLimit = 3;
		for( Card c : hand._cards){
			if( c.getRank() == Card.ACE) discardLimit = 4;
		}
		
		for( int x : toDiscard){
			if ( x < 0 || x > MAX_HAND_SIZE-1){
				System.out.println("You entered card(s) out of the range");
				return false;
			}
			else if( discardLimit == 4 //they had an ace
					&& toDiscard.size() == 4 //and they are trying to discard 4 cards
					&& hand._cards.get(x).getRank() == Card.ACE){ // and they are discarding the ace
				System.out.println("You tried to discard the ace you are holding");
				return false;
			}
		}
		if( toDiscard.size() > discardLimit ){ //they can discard 4 cards BUT NOT including the ace
			System.out.println("You entered too many cards!");
			return false;
		}
		
		return true;
	}

	/*
	 * get the cards that the human wants to discard
	 */
	private static void makeHumanMove( Human human, CardPile deck, CardPile discard){
		Scanner scanner = new Scanner(System.in);
		ArrayList<Integer> toDiscardIndices = new ArrayList<Integer>();
		ArrayList<Card> toDiscardCards = new ArrayList<Card>();
		do{
			toDiscardIndices.clear();
			human.printHand();
			System.out.println("List the cards you wish to discard: ");
			String inputLine = scanner.nextLine();
			if( !inputLine.isEmpty()){
				String[] splitLine = inputLine.split(" ");
				for( String s : splitLine){
					toDiscardIndices.add( Integer.parseInt(s)-1 ); // USER INDEX = hand index-1
				}
			}
		}while( !isValidDiscard( human.getHand(), toDiscardIndices) ); //check if there is an error
		for( int x : toDiscardIndices){
			toDiscardCards.add( human._hand._cards.get( x) );
		}
		human._hand._cards.removeAll(toDiscardCards);
		discard.returnCards( toDiscardCards);
		//fill the humans hand up
		while( human._hand._cards.size() < MAX_HAND_SIZE){
			human._hand._cards.add( deck.drawCard() );
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
