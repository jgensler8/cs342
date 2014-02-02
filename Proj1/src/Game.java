import java.io.IOException;
import java.util.ArrayList;

public class Game {
	static int maxHandSize = 5; 
	
	public static void main( String[] Args){
		printGreeting();
		int numComps = getNumComps();
		
		//now generate the game
		CardPile Deck = new CardPile(1);
		Deck.shufflePile();
		CardPile Discard = new CardPile();
		Human User = new Human( maxHandSize);
		ArrayList<Robot> Robots = new ArrayList<Robot>();
		//generate the number of robots to play against
		while( numComps >= 0){
			Robot toAdd = new Robot(maxHandSize, "Computer " + Integer.toString(numComps) );
			Robots.add( toAdd);
			--numComps;
		}
		//deal out the cards
		initPlayersHands( User, Robots, Deck);

		//game has started
		launchGame( User, Robots, Deck, Discard);
		
		
		//play game again?:w
		
	}
	
	/*
	 * initial message sent at beginning of game
	 */
	static void printGreeting(){
		System.out.println("Welcome to Poker!");
	}
	
	/*
	 * print the Humans Hand to system.out
	 */
	static void printHand( Human User){
		System.out.print( User.getName() + "'s hand: ");
		ArrayList<Card> UserHand = User.getHand();
		for( Card c : UserHand){
			System.out.print( c.getPrintable() + " ");
		}
		System.out.println("");
	}
	
	/*
	 * Distribute maxHandSize cards to each player and robot
	 */
	static void initPlayersHands( Human theUser, ArrayList<Robot> theRobots, CardPile Deck){
		for( int handCounter = 0; handCounter < maxHandSize; ++handCounter){
			theUser.addCard( Deck.drawCard() );
			for( Robot R : theRobots){
				R.addCard( Deck.drawCard() );
			}
		}
	}
	
	/*
	 * Get the user to specify the number of computers it wants to play against
	 */
	static int getNumComps(){
		int input = 0;
		System.out.println("Enter the number of computers you want to play against");
		try {
			input = System.in.read();
			input -= 49;
		} catch (IOException e) {
			e.printStackTrace();
		}
		while( input < 1 && input > 4){ // if the number of computers is not 1,2,3,or4
			System.out.println("Sorry, the number must be 1, 2, 3, or 4");
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
	static void launchGame(Human User, ArrayList<Robot> Robots, CardPile Deck, CardPile Discard ){
		//show user hand
		printHand( User);
		//get user move
		
		
		//resolve user hand and bot hands
		
	}
}
