import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Game {

	// the max hand size that is allowed for any hand
	public final static int MAX_HAND_SIZE = 5;
	// the max number of computer players that can play against the human
	public final static int MAX_COMPUTER_PLAYERS = 3;
	// the normal discard limit
	public final static int NORMAL_DISCARD_LIMIT = 3;
	// the discard limit for holding an ace
	public final static int ACE_DISCARD_LIMIT = 4;
	// index position of user amongst players
	public final static int USER_INDEX_POSITION = 0;
	// set to true to enter debug mode
	public static final boolean DEBUG = false;

	public static void main(String[] args) {

		Boolean didUserWin = false;
		CardPile deck;

		ArrayList<Player> players = new ArrayList<Player>();
		players.add(new User("Human"));

		printGreeting();

		int numComps;
		if (DEBUG) {
			numComps = 3;
		} else {
			/*
			 * ask for number of player
			 */
			numComps = getNumComps();
		}
		/*
		 * Create computer players
		 */
		for (int i = 0; i < numComps; i++) {
			players.add(new Opponent("Comp " + String.valueOf(i + 1)));
		}

		/*
		 * get our deck of cards
		 */
		deck = new CardPile();

		Boolean isContinuePlay = true;

		while (isContinuePlay) {

			/*
			 * randomizes position of cards within deck
			 */
			deck.shuffle();

			System.out.println("Cards have been shuffled.  Let's play!");
			pause();

			System.out.println("Dealer is dealing cards to players...");
			pause();

			/*
			 * draw cards for each player
			 */
			if (DEBUG) {
				players.get(0).getHand()
						.addCard(new Card(Suit.SPADES, Rank.ACE));
				players.get(0).getHand()
						.addCard(new Card(Suit.HEARTS, Rank.TEN));
				players.get(0).getHand()
						.addCard(new Card(Suit.HEARTS, Rank.TWO));
				players.get(0).getHand()
						.addCard(new Card(Suit.SPADES, Rank.FIVE));
				players.get(0).getHand()
						.addCard(new Card(Suit.CLUBS, Rank.FOUR));
				players.get(1).getHand()
						.addCard(new Card(Suit.HEARTS, Rank.ACE));
				players.get(1).getHand()
						.addCard(new Card(Suit.SPADES, Rank.NINE));
				players.get(1).getHand()
						.addCard(new Card(Suit.SPADES, Rank.THREE));
				players.get(1).getHand()
						.addCard(new Card(Suit.CLUBS, Rank.KING));
				players.get(1).getHand()
						.addCard(new Card(Suit.SPADES, Rank.FOUR));
				players.get(2).getHand()
						.addCard(new Card(Suit.SPADES, Rank.QUEEN));
				players.get(2).getHand()
						.addCard(new Card(Suit.CLUBS, Rank.TEN));
				players.get(2).getHand()
						.addCard(new Card(Suit.CLUBS, Rank.JACK));
				players.get(2).getHand()
						.addCard(new Card(Suit.HEARTS, Rank.SIX));
				players.get(2).getHand()
						.addCard(new Card(Suit.CLUBS, Rank.FOUR));
				players.get(3).getHand()
						.addCard(new Card(Suit.DIAMONDS, Rank.TWO));
				players.get(3).getHand()
						.addCard(new Card(Suit.SPADES, Rank.NINE));
				players.get(3).getHand()
						.addCard(new Card(Suit.DIAMONDS, Rank.THREE));
				players.get(3).getHand()
						.addCard(new Card(Suit.SPADES, Rank.TEN));
				players.get(3).getHand()
						.addCard(new Card(Suit.HEARTS, Rank.FOUR));
			} else {
				for (int c = 1; c <= MAX_HAND_SIZE; c++) {
					for (Player player : players) {
						player.getHand().addCard(deck.drawCard());
					}
				}

			}
			/*
			 * show user their hand in descending rank
			 */
			showHand(players.get(USER_INDEX_POSITION),
					"The cards in your hand are: ");
			pause();

			System.out.println("It is time now to discard unwanted cards.");
			/*
			 * get user's unwanted cards
			 */
			ArrayList<Integer> discardIndices = getDiscardedCards(players
					.get(USER_INDEX_POSITION));

			/*
			 * physically discard the user's cards
			 */
			ArrayList<Card> discardCards = new ArrayList<Card>();
			for (int discardIndex : discardIndices) {
				Card card = players.get(USER_INDEX_POSITION).getHand()
						.getCardAtIndex(discardIndex - 1);
				if (card != null) {
					discardCards.add(card);
				} else {
					System.out.println("Failed to get card at index "
							+ discardIndex);
				}
			}
			for (Card discardCard : discardCards) {
				players.get(USER_INDEX_POSITION).getHand()
						.discardCard(discardCard);
			}

			if (!DEBUG) {
				/*
				 * opponents discard their non-valuable cards
				 */
				for (int i = 0; i < players.size(); i++) {
					// we loop through all index values of array not assuming
					// which
					// index position user is at... if index is user, continue
					if (i == USER_INDEX_POSITION)
						continue;
					// now we are in index position related to opponent
					// Cast player as opponent
					Opponent opponent = (Opponent) players.get(i);
					opponent.discardCards();

					int discarded = MAX_HAND_SIZE
							- players.get(i).getHand().getCardCount();
					if (discarded > 0) {
						System.out.println(players.get(i).getName()
								+ " has discarded " + discarded + " card"
								+ (discarded > 1 ? "s" : ""));
					} else {
						System.out.println(players.get(i).getName()
								+ " has chosen to keep their hand");
					}
				}
			}
			pause();

			/*
			 * loop through each player and draw new cards
			 */
			for (Player player : players) {
				int drawn = 0;
				for (int i = player.getHand().getCardCount() + 1; i <= MAX_HAND_SIZE; i++) {
					player.getHand().addCard(deck.drawCard());
					drawn++;
				}
				if (drawn > 0)
					System.out.println(player.getName() + " has drawn " + drawn
							+ " card" + (drawn > 1 ? "s" : ""));
			}
			pause();

			/*
			 * show everyone's hand and determine who has best
			 */
			int highestScore = -1;
			for (Player player : players) {
				int playerScore = player.getHand().evalHand();
				if (highestScore < playerScore) {
					highestScore = playerScore;
				}

				System.out.println(player.getName() + " has a "
						+ player.getHand().getHandHas());
				showHand(player, player.getName() + "'s hand: ");
				System.out.println();
			}
			// We need ArrayList of who has highest score in case of ties
			ArrayList<Player> winners = new ArrayList<Player>();
			for (Player player : players) {
				int playerScore = player.getHand().evalHand();
				if (highestScore == playerScore) {
					winners.add(player);
				}
			}

			System.out.println();
			if (winners.size() == 1) {
				System.out.println(winners.get(0).getName()
						+ " wins this hand with a "
						+ winners.get(0).getHand().getHandHas() + ".");
				didUserWin = (winners.get(0).getName() == players.get(
						USER_INDEX_POSITION).getName());
			} else {
				// we need to break ties
				for (int i = 0; i < winners.size() - 1; i++)
					for (int j = i + 1; j < winners.size(); j++) {
						Player player1 = winners.get(i);
						Player player2 = winners.get(j);
						if (player1.equals(player2))
							continue;
						int result = player1.getHand().willBeat(
								player2.getHand());
						if (result == 1) {
							// player 2 lost this match, so remove from winners
							// list
							winners.remove(player2);
							j--;
						} else if (result == 0) {
							// player 1 lost this match, so remove from winners
							// list
							winners.remove(player1);
							j--;
						}
					}
				if (winners.size() == 1) {
					System.out.println(winners.get(0).getName()
							+ " wins this hand with a "
							+ winners.get(0).getHand().getHandHas() + ".");
					didUserWin = (winners.get(0).getName() == players.get(
							USER_INDEX_POSITION).getName());
				} else {
					System.out.println("There was an unbreakable tie.");
				}
			}

			// Placeholder - hardcode to end game
			isContinuePlay = false;

		}

		if (didUserWin)
			System.out.println("Thanks for playing.  You rock!");
		else
			System.out.println("Thanks for playing.  Better luck next time.");
	}

	/*
	 * force user to press enter to continue
	 */
	static void pause() {
		if (DEBUG)
			return;
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.println("press Enter to continue...");
			br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * initial message sent at beginning of game
	 */
	static void printGreeting() {
		System.out.println("Welcome to Poker!");
	}

	/*
	 * Get the user to specify the number of computers it wants to play against
	 */
	static int getNumComps() {
		Boolean isNotValid;
		int input = 0;
		do {
			System.out
					.print("Enter the number of computers you want to play against: ");
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));
			try {
				input = Integer.parseInt(br.readLine());
				if (isNotValid = input < 1 || input > MAX_COMPUTER_PLAYERS) {
					System.out.println("You must compete against 1 to "
							+ MAX_COMPUTER_PLAYERS + " players.");
				}
			} catch (IOException ex) {
				System.out.println("You must enter a number between 1 to "
						+ MAX_COMPUTER_PLAYERS + ".");
				isNotValid = true;
			}
		} while (isNotValid);
		return input;
	}

	/*
	 * print player's hand to screen
	 */
	static void showHand(Player player, String msg) {
		int i = 0;
		System.out.print(msg);
		for (Card card : player.getHand().getHighestToLowestRankCards(true)) {
			System.out.print(String.valueOf(++i) + ") " + card.toString()
					+ "   ");
		}
		System.out.println();
	}

	/*
	 * prompt for and return cards to discard based off their index position
	 */
	static ArrayList<Integer> getDiscardedCards(Player player) {

		System.out.println();
		int aceCount = player.getHand().getAceCount();

		ArrayList<Integer> list = new ArrayList<Integer>();
		// control to keep asking for user input until valid input received
		Boolean isInvalid = true;
		while (isInvalid) {

			int discardLimit = NORMAL_DISCARD_LIMIT;
			if (aceCount > 0) {
				discardLimit = ACE_DISCARD_LIMIT;

				System.out
						.println("Since you have an Ace you can keep the Ace and discard the other four cards.");
			}

			isInvalid = false;
			System.out
					.print("List the card numbers you wish to discard (Enter 0 to keep existing hand). > ");

			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));
			try {
				Boolean isFirstCard = true;
				String[] indices = br.readLine().split(" ");
				for (String index : indices) {
					try {
						int idx = Integer.parseInt(index);

						// user opt to keep current hand
						if (isFirstCard && idx == 0) {
							return list;
						}
						isFirstCard = false;

						// get card in hand that user index input corresponds to
						Card card = player.getHand().getCardAtIndex(idx - 1);
						if (card == null) {
							// index out of range - card not found
							System.out.println("Card not found... Try again.");
							isInvalid = true;
							list.clear();
							break;
						}

						list.add(idx);
						discardLimit--;
						// if user discards an Ace, confirm they have another
						// Ace so they maintain a valid discard limit
						if ((card.getRank() == Rank.ACE) && (aceCount == 1)) {
							// user discarded their only Ace, reduce discard
							// limit
							discardLimit--;
						}

						if (discardLimit < 0) {
							// user discarded too many cards
							System.out
									.println("Cannot exceed your discard limit... Try again.");
							isInvalid = true;
							list.clear();
							break;
						}

					} catch (NumberFormatException ex) {
						System.out
								.println("Invalid number received... Try again.");
						isInvalid = true;
						list.clear();
						break;
					}
				}
			} catch (IOException ex) {
				System.out.println("Invalid number received... Try again.");
				isInvalid = true;
				list.clear();
				break;
			}
		}
		return list;
	}

}
