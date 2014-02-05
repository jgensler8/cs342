import java.io.IOException;
import java.util.ArrayList;

public class Game {
	static final int HANDSIZE = 5; 
	
	public static void main( String[] Args){
		printGreeting();
		int numComps = getNumComps();
		
		//generate basic game items
		CardPile deck = new CardPile(1);
		deck.shuffle();
		CardPile discard = new CardPile();
		Human User = new Human( HANDSIZE);
		ArrayList<Opponent> opponents = new ArrayList<Opponent>();
		
		//generate the number of robots to play against
		while( numComps >= 0){
			Opponent toAdd = new Opponent(HANDSIZE, "Computer " + Integer.toString(numComps) );
			opponents.add( toAdd);
			--numComps;
		}
		
		//deal out the cards
		initPlayersHands( User, opponents, deck);

		//start the game
		launchGame( User, opponents, deck, discard);
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
	static void initPlayersHands( Human user, ArrayList<Opponent> opponents, CardPile deck){
		for( int handCounter = 0; handCounter < HANDSIZE; ++handCounter){
			user.hand._cards.add( deck.drawCard() );
			for( Opponent R : opponents){
				R.hand._cards.add( deck.drawCard() );
			}
		}
		user.hand.orderDescending();
		for( Opponent R: opponents){
			R.hand.orderDescending();
		}
	}
	
	/*
	 * Get the user to specify the number of computers it wants to play against
	 */
	static int getNumComps(){
		int input = 0;
		System.out.print("Enter the number of computers you want to play against: ");
		try {
			input = System.in.read();
			input -= 49;
		} catch (IOException e) {
			e.printStackTrace();
		}
		while( input < 1 && input > 3){ // if the number of computers is not 1,2,3,or4
			System.out.println("Sorry, the number must be 1, 2, or 3");
			try {
				input = System.in.read();
				input -= 49;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return input;
	}
	
	/*
	 * the moves of user and robot are contained here
	 */
	static void launchGame(Human user, ArrayList<Opponent> opponents, CardPile deck, CardPile discard ){
		printHand( user);
		System.out.println("List the cards you wish to discard: ");
		//TODO*******
		int userScore = user.hand.evalHand();
		for( Opponent R : opponents){
			R.makeMove();
		}
		int highestBotScore = 0, botScore, botNum = 0;
		for( int botIndex = 0; botIndex < opponents.size(); ++botIndex){
			botScore = opponents.get(botIndex).hand.evalHand();
			if( botScore > highestBotScore){
				highestBotScore = botScore;
				botNum = botIndex;
			}
		}
		
		//calculate if the human or a bot won
		if( userScore > highestBotScore){
			System.out.println("CONGRATS, " + user.getName() + "! You've won!");
		}
		else if( userScore < highestBotScore){
			System.out.println("BUMMER! " + opponents.get(botNum).getName() + " has beat you!");
		}
		else{
			System.out.println("WOW! the game resulted in a tie of hands");
		}
		
	}
	
	/*
	 * print the players hand to system.out
	 */
	public static void printHand(Human human){
		Hand userHand = human.getHand();
		System.out.print( human.getName() + "'s hand: ");
		for(int cardNum = 0; cardNum < userHand._cards.size(); ++cardNum ){
			System.out.print( (cardNum+1) + ") " + userHand._cards.get(cardNum).toString() + " ");
		}
		System.out.println("");
	}
	public static void printHand( Opponent opponent){
		Hand oppHand = opponent.getHand();
		System.out.print( opponent.getName() + "'s hand: ");
		for(int cardNum = 0; cardNum < oppHand._cards.size(); ++cardNum ){
			System.out.print( (cardNum+1) + ") " + oppHand._cards.get(cardNum).toString() + " ");
		}
		System.out.println("");
	}

}
