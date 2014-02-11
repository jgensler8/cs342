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
	//the threshold that the AI will use to decide if it will discard
	public final static int OPPONENT_DISCARD_THRESH = 10;
	
	public static void main( String[] Args){
		//the start message of the game
		printGreeting();
		
		//generate basic game items
		int numComps = getNumComps();
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
	 * find the winner and print it out
	 * player ---> score
	 * we need to compare scores but keep the player with the score
	 * find the highest score(s)
	 * resolve a winner among the highest scores
	 */
	private static void calculateWinner(Human human, ArrayList<Opponent> opponents) {
		//compare human hand to opponents hands
		Boolean tieFlag = false;
		ArrayList<String> tyingNames = new ArrayList<String>();
		int winnerIndex = -1;
		tyingNames.add(human.getName());
		for( int oppIndex = 0; oppIndex < opponents.size(); ++oppIndex){
			if( winnerIndex == -1){ //human is current winner
				switch( opponents.get(oppIndex)._hand.willBeat( human._hand) ){
				case 1:
					winnerIndex = oppIndex;
					tieFlag = false;
					tyingNames.clear();
					tyingNames.add( opponents.get(oppIndex).getName()); //add name for future reference
					break;
				case -1:
					tieFlag = true;
					tyingNames.add( opponents.get(oppIndex).getName());
					break;
				case 0: //nothing happened
				}
			}
			else{ //an opponent is current winner
				switch( opponents.get(oppIndex)._hand.willBeat( opponents.get(winnerIndex)._hand )){
				case 1:
					winnerIndex = oppIndex;
					tieFlag = false;
					tyingNames.clear();
					tyingNames.add( opponents.get(oppIndex).getName()); //add name for future reference
					break;
				case -1:
					tieFlag = true;
					tyingNames.add( opponents.get(oppIndex).getName());
					break;
				case 0: //nothing happened
				}
			}
		}
		//print out all cards
		human.printHand();
		//System.out.println( human.getName() + "'s score: " + human._hand.evalHand());
		human.printHandValue();
		for( Opponent opponent : opponents){
			opponent.printHand();
			//System.out.println( opponent.getName() + "'s score: " + opponent._hand.evalHand() );
			opponent.printHandValue();
		}
		//print out victor
		if( tieFlag){
			if(winnerIndex == -1){
				System.out.println("You've tied!");
			}
			else{
				System.out.println("You've lost, but the game resulted in a tie");
			}
			System.out.println("Here are the tying players:");
			for( String name : tyingNames)
				System.out.println(name);
		}
		else if( winnerIndex == -1){
			System.out.println("Horray! You Won!");
		}
		else{
			System.out.println("Sorry, " + opponents.get(winnerIndex).getName() + " won!");
		}
	}
	
	/*
	 * used to check if a human has entered valid cards to discard
	 * the indices stored in the array list are assumed to be ADJUSTED to start at
	 * index 0.
	 * possible user errors:
	 *    - index could be out of bound
	 *    - number of indices could be too high
	 *    - if they have an ace and discard 4, the ace can't be discarded (right?)
	 *    - enter the same number twice TODO XXX XXX XXX XXX XXX XXX XXX XXX XXX XXX XXX XXX XXX XXX FIXME
	 */
	private static Boolean isValidDiscard(Hand hand, ArrayList<Integer> toDiscard){
		if( toDiscard.isEmpty() ) return true;
		int discardLimit = NORMAL_DISCARD_LIMIT;
		if( hand.hasVal(Card.ACE)) discardLimit = ACE_DISCARD_LIMIT;
		
		for( int discardIndex : toDiscard){
			if ( discardIndex < 0 || discardIndex > hand.size()){
				System.out.println("You entered card(s) out of the range");
				return false;
			}
			else if( discardLimit == ACE_DISCARD_LIMIT //they had an ace
					&& toDiscard.size() == ACE_DISCARD_LIMIT //and they are trying to discard 4 cards
					&& hand.getRank(discardIndex) == Card.ACE){ // and they are discarding the ace
				return false;
			}
		}
		
		if( toDiscard.size() > discardLimit ){
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
		discard.returnCards( human._hand.removeAll(toDiscardIndices) );
		human._hand.refill(deck);
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
			//TODO
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
			//get the cards not in the three of a kind
			ArrayList<Integer> toCheck = new ArrayList<Integer>();
			ArrayList<Integer> toRemove = new ArrayList<Integer>();
			for( int cardIndex = 0; cardIndex < opponent._hand.size(); ++cardIndex){
				if( !opponent._hand.contributesToThree(cardIndex))
					toCheck.add( cardIndex);
			}
			for( int checkThresh : toCheck){
				if( checkThresh < OPPONENT_DISCARD_THRESH){
					if( toRemove.isEmpty()){
						System.out.println( opponent.getName() + " is dscarding cards");
					}
					toRemove.add( checkThresh);
				}
			}
			discard.returnCards( opponent._hand.removeAll( toRemove));
			opponent._hand.refill(deck);
		}
		else if( opponentHand.hasTwoPair() ){
			//if the last card is over the threshold, don't do anything
			//else pick up a new card
			int toCheck = -1;
			for( int cardIndex = 0; cardIndex < opponent._hand.size(); ++cardIndex){
				if( !opponent._hand.contributesToPair(cardIndex)){
					toCheck = cardIndex;
					break;
				}
			}
			if( toCheck < OPPONENT_DISCARD_THRESH){
				discard.returnCard( ( opponent._hand.remove( toCheck)));
				System.out.println( opponent.getName() + " is dscarding cards");
			}
			opponent._hand.refill(deck);
		}
		else if( opponentHand.hasOnePair() ){
			//try and get a 3 of a kind if a card is over threshold
			ArrayList<Integer> toCheck = new ArrayList<Integer>();
			ArrayList<Integer> toRemove = new ArrayList<Integer>();
			for( int cardIndex = 0; cardIndex < opponent._hand.size(); ++cardIndex){
				if( !opponent._hand.contributesToPair(cardIndex))
					toCheck.add( cardIndex);
			}
			for( int checkThresh : toCheck){
				if( checkThresh < OPPONENT_DISCARD_THRESH){
					if( toRemove.isEmpty()){
						System.out.println( opponent.getName() + " is dscarding cards");
					}
					toRemove.add( checkThresh);
				}
			}
			discard.returnCards( opponent._hand.removeAll( toRemove));
			opponent._hand.refill(deck);
		}
		else{
			//go for flush?
			//go for straight?
			//we could use something along the lines of the one listed in the 
			//assignment pdf if you would like. It doesn't really make a difference
			//to me so long as it works
			ArrayList<Integer> toRemove = new ArrayList<Integer>();
			for( int cardIndex = 0; cardIndex < opponent._hand.size(); ++cardIndex){
				if( opponent._hand.getRank(cardIndex) < OPPONENT_DISCARD_THRESH){
					if(toRemove.isEmpty()){
						System.out.println( opponent.getName() + " is dscarding cards");
					}
					toRemove.add( cardIndex);
				}
			}
			discard.returnCards( opponent._hand.removeAll( toRemove));
			opponent._hand.refill(deck);
		}
	}
}
