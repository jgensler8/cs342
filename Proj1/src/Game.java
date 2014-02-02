import java.io.IOException;
import java.util.ArrayList;

public class Game {
	static int maxHandSize = 5; 
	
	public static void main( String[] Args){
		printGreeting();
		int numComps = getNumComps();
		
		//generate basic game items
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

		//start the game
		launchGame( User, Robots, Deck, Discard);
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
		System.out.print("Enter the number of computers you want to play against: ");
		try {
			input = System.in.read();
			input -= 49; 				//ascii to decimal
		} catch (IOException e) {
			e.printStackTrace();
		}
		while( input < 1 && input > 4){ // if the number of computers is not 1,2,3,or4
			System.out.println("Sorry, the number must be 1, 2, 3, or 4");
			try {
				input = System.in.read();
				input -= 49; 			//ascii to decimal
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
		User.printHand();												//show user hand
		System.out.println("List the cards you wish to discard: ");		//get user move
		//TODO*******
		int userScore = User.evalHand();								//resolve user hand
		for( Robot R : Robots){											//get the bot's move
			R.makeMove( Deck, Discard);
		}
		int highestBotScore = 0, botScore, botNum = 0;
		for( int botIndex = 0; botIndex < Robots.size(); ++botIndex){	//resolve bot(s) hand
			botScore = Robots.get(botIndex).evalHand();
			if( botScore > highestBotScore){
				highestBotScore = botScore;
				botNum = botIndex;
			}
		}
			//calculate if the human or a bot won
		if( userScore > highestBotScore){
			System.out.println("CONGRATS, " + User.getName() + "! You've won!");
		}
		else if( userScore < highestBotScore){
			System.out.println("BUMMER! " + Robots.get(botNum).getName() + " has beat you!");
		}
		else{
			System.out.println("WOW! the game resulted in a tie of hands");
		}
		
	}
}
