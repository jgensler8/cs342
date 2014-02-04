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
		ArrayList<Opponent> Robots = new ArrayList<Opponent>();
		//generate the number of robots to play against
		while( numComps >= 0){
			Opponent toAdd = new Opponent(maxHandSize, "Computer " + Integer.toString(numComps) );
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
	static void initPlayersHands( Human theUser, ArrayList<Opponent> theRobots, CardPile Deck){
		for( int handCounter = 0; handCounter < maxHandSize; ++handCounter){
			theUser.addCard( Deck.drawCard() );
			for( Opponent R : theRobots){
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
	static void launchGame(Human User, ArrayList<Opponent> Robots, CardPile Deck, CardPile Discard ){
		printHand( User);
		System.out.println("List the cards you wish to discard: ");
		//TODO*******
		int userScore = User.hand.evalHand();
		for( Opponent R : Robots){
			R.makeMove( Deck, Discard);
		}
		int highestBotScore = 0, botScore, botNum = 0;
		for( int botIndex = 0; botIndex < Robots.size(); ++botIndex){
			botScore = Robots.get(botIndex).hand.evalHand();
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
	
	/*
	 * print the players hand to system.out
	 */
	public static void printHand(Human H){
		Hand userHand = H.getHand();
		System.out.print( H.getName() + "'s hand: ");
		for(int cardNum = 0; cardNum < userHand.Cards.size(); ++cardNum ){
			System.out.print( (cardNum+1) + ") " + userHand.Cards.get(cardNum).getPrintable() + " ");
		}
		System.out.println("");
	}
	public static void printHand( Opponent O){
		Hand oppHand = O.getHand();
		System.out.print( O.getName() + "'s hand: ");
		for(int cardNum = 0; cardNum < oppHand.Cards.size(); ++cardNum ){
			System.out.print( (cardNum+1) + ") " + oppHand.Cards.get(cardNum).getPrintable() + " ");
		}
		System.out.println("");
	}

}
