import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;

public class Hand {
	// the max hand size that is allowed for any hand
	public final int MAX_HAND_SIZE = Game.MAX_HAND_SIZE;

	private Boolean _isAscendingCards = false;
	private Boolean _isDescendingCards = false;

	private Hashtable<Integer, Integer> _ranks;
	private Hashtable<Integer, Integer> _suits;
	private ArrayList<Card> _cards;

	private String _handHas = "High Card";

	public Hand() {
		_ranks = new Hashtable<Integer, Integer>();
		_suits = new Hashtable<Integer, Integer>();

		_cards = new ArrayList<Card>();
	}

	public Hand(ArrayList<Card> cards) {
		_ranks = new Hashtable<Integer, Integer>();
		_suits = new Hashtable<Integer, Integer>();

		// add each card this way so hashtables are correctly populated
		for (Card card : cards) {
			this.addCard(card);
		}
	}

	/*
	 * string representation of how hand would win if won
	 */
	public String getHandHas() {
		return _handHas;
	}

	/*
	 * add card to the hand if the hand has too many cards, throw an exception
	 * to stop the game
	 */
	public void addCard(Card card) {
		_cards.add(card);

		// update ranks count hash table
		if (_ranks.containsKey(card.getRank())) {
			_ranks.put(card.getRank(), _ranks.get(card.getRank()) + 1);
		} else {
			_ranks.put(card.getRank(), 1);
		}

		// update suits count hash table
		if (_suits.containsKey(card.getSuit())) {
			_suits.put(card.getSuit(), _suits.get(card.getSuit()) + 1);
		} else {
			_suits.put(card.getSuit(), 1);
		}
	}

	/*
	 * discard a card input cannot be index because position of cards
	 * dynamically change
	 */
	public void discardCard(Card card) {
		// update ranks count hash table
		_ranks.put(card.getRank(), _ranks.get(card.getRank()) - 1);

		// update suits count hash table
		_suits.put(card.getSuit(), _suits.get(card.getSuit()) - 1);

		_cards.remove(card);
	}

	/*
	 * remove all cards of this suit
	 */
	public void discardCardsBySuit(int suit) {
		for (Card card : _cards) {
			if (card.getSuit() == suit) {
				this.discardCard(card);
			}
		}
	}

	/*
	 * remove all cards within range of ranks
	 */
	public void discardCardsByRange(int lowestRank, int highestRank) {
		
		if (!_isAscendingCards)
			this.sortCardsByRank();
		
		for (int i = 0; i < _cards.size(); i++) {
			if (_cards.get(i).getRank() >= lowestRank && _cards.get(i).getRank() <= highestRank) {
				this.discardCard(_cards.get(i--));
			}
		}
	}

	/*
	 * return number of cards in hand
	 */
	public int getCardCount() {
		return _cards.size();
	}

	/*
	 * get cards left to right, highest to lowest rank
	 */
	public ArrayList<Card> getHighestToLowestRankCards() {
		if (!_isDescendingCards)
			sortCardsByRank(false);
		return _cards;
	}

	public ArrayList<Card> getHighestToLowestRankCards(Boolean isAceBest) {
		if (!_isDescendingCards)
			sortCardsByRank(false, isAceBest);
		return _cards;
	}

	/*
	 * get cards left to rank, best pairing to worst pairing
	 */
	public ArrayList<Card> getBestToWorstPairingCards() {
		// TODO: finish me if you want 5 point extra credit!
		return null;
	}

	/*
	 * helper function to sort cards by rank
	 */

	// always ascending order
	private void sortCardsByRank() {
		sortCardsByRank(true);
	}

	// allow for ascending or descending
	private void sortCardsByRank(Boolean isAscending) {
		_cards = getSortedCards(_cards, isAscending);

		_isAscendingCards = isAscending;
		_isDescendingCards = !isAscending;
	}

	// allow for ascending or descending AND position of ACE at beginning or end
	private void sortCardsByRank(Boolean isAscending, Boolean isAceBest) {
		_cards = getSortedCards(_cards, isAscending);

		if (!isAscending) {
			if (isAceBest) {
				while (_cards.get(_cards.size() - 1).getRank() == Rank.ACE) {
					_cards.add(0, _cards.remove(_cards.size() - 1));
				}
				_isAscendingCards = false;
				_isDescendingCards = false;

				return;
			}
		}
		_isAscendingCards = isAscending;
		_isDescendingCards = !isAscending;
	}

	private ArrayList<Card> getSortedCards(ArrayList<Card> cards,
			Boolean isAscending) {
		for (int i = 0; i < cards.size() - 1; i++)
			for (int j = i + 1; j < cards.size(); j++) {
				if ((isAscending && (cards.get(i).getRank() > cards.get(j)
						.getRank()))
						|| (!isAscending && (cards.get(i).getRank() < cards
								.get(j).getRank()))) {
					cards.add(i, cards.remove(j));
				}
			}

		return cards;
	}

	/*
	 * return number of Aces in hand this will get determine how many cards
	 * player can discard
	 */
	public int getAceCount() {
		return _ranks.containsKey(Rank.ACE) ? _ranks.get(Rank.ACE) : 0;
	}

	/*
	 * return card at index
	 */
	public Card getCardAtIndex(int index) {
		if ((index >= _cards.size()) || index < 0) {
			return null;
		}
		return _cards.get(index);
	}

	/*
	 * returns cards that cannot be paired
	 */
	public ArrayList<Card> getNonPairCards() {
		ArrayList<Card> cards = new ArrayList<Card>();
		// loop through all cards in paired hashtable
		for (Integer rank : _ranks.keySet()) {
			if (_ranks.get(rank) == 1) {
				// we have one kind of this card so find it
				for (Card card : _cards) {
					if (card.getRank() == rank) {
						// add card as non-paired
						cards.add(card);
						break;
					}
				}
			}
		}
		return cards;
	}

	/*
	 * find out if this.hand beats otherhand 1: true, this hand beats otherhand
	 * 0: false, this hand fails to beat otherhand -1: tie, these hands are the
	 * same
	 */
	public int willBeat(Hand otherHand) {
		// first lets see if the two hands tie
		int myScore = this.evalHand();
		int otherScore = otherHand.evalHand();
		if (myScore > otherScore)
			return 1;
		else if (otherScore > myScore)
			return 0;
		else {
			// find out which hand they tie on
			// ignore these because it is impossible to break ties:
			// - Royal Flush
			// - Straight Flush (evalHand would have given unique score)
			// - 4 of Kind (impossible)
			// - Full House (evalHand would have given unique score)
			// - Straight (evalHand would have given unique score)
			// - 3 of Kind (impossible)

			if (this.hasFlush()) {
				// compare each other's highest to lowest cards
				ArrayList<Card> myCards = this.getHighestToLowestRankCards();
				ArrayList<Card> opponentCards = otherHand
						.getHighestToLowestRankCards();
				for (int i = 0; i < Game.MAX_HAND_SIZE; i++) {
					int myRank = myCards.get(i).getRank();
					if (myRank == Rank.ACE) // ACE is best in this scenario
						myRank = Rank.KING + 1;
					int opponentRank = opponentCards.get(i).getRank();
					if (opponentRank == Rank.ACE) // ACE is best in this
													// scenario
						opponentRank = Rank.KING + 1;
					if (myRank > opponentRank)
						return 1;
					else if (myRank < opponentRank)
						return 0;
				}
				// impossible to break tie because all cards have been evaluated

			} else if (this.hasTwoPair()) {
				// compare each other's 2 pairs, highest to lowest
				ArrayList<Integer> myRanks = this.getCardRanksByPair(2);
				ArrayList<Integer> opponentRanks = otherHand
						.getCardRanksByPair(2);

				// remember ACE is best in this scenario

				// evaluate each other's highest ranked pairs
				int myIndex = (myRanks.get(0) == Rank.ACE ? Rank.KING + 1
						: myRanks.get(0)) > (myRanks.get(1) == Rank.ACE ? Rank.KING + 1
						: myRanks.get(1)) ? 0 : 1;
				int opponentIndex = (opponentRanks.get(0) == Rank.ACE ? Rank.KING + 1
						: opponentRanks.get(0)) > (opponentRanks.get(1) == Rank.ACE ? Rank.KING + 1
						: opponentRanks.get(1)) ? 0 : 1;

				if (myRanks.get(myIndex) > opponentRanks.get(opponentIndex))
					return 1;
				else if (myRanks.get(myIndex) < opponentRanks
						.get(opponentIndex))
					return 0;

				// evaluate each other's lowest ranked pairs
				myIndex = (myIndex + 1) % 2;
				opponentIndex = (opponentIndex + 1) % 2;

				if (myRanks.get(myIndex) > opponentRanks.get(opponentIndex))
					return 1;
				else if (myRanks.get(myIndex) < opponentRanks
						.get(opponentIndex))
					return 0;

				// evaluate each other's unused card to break ties
				// there could only be one of these left for each player
				int myKicker = this.getNonPairCards().get(0).getRank();
				if (myKicker == Rank.ACE)
					myKicker = Rank.KING + 1;
				int opponentKicker = otherHand.getNonPairCards().get(0)
						.getRank();
				if (opponentKicker == Rank.ACE)
					opponentKicker = Rank.KING + 1;

				if (myKicker > opponentKicker)
					return 1;
				else if (myKicker < opponentKicker)
					return 0;

				// impossible to break tie because all cards have been evaluated

			} else if (this.hasOnePair()) {
				// evaluate each other's unused card to break ties
				ArrayList<Card> myKickers = getSortedCards(
						this.getNonPairCards(), false);
				ArrayList<Card> opponentKickers = getSortedCards(
						otherHand.getNonPairCards(), false);

				// move ACE to beginning of list if exists
				if (myKickers.get(myKickers.size() - 1).getRank() == Rank.ACE) {
					myKickers.add(0, myKickers.remove(myKickers.size() - 1));
				}
				if (opponentKickers.get(opponentKickers.size() - 1).getRank() == Rank.ACE) {
					opponentKickers.add(0,
							opponentKickers.remove(opponentKickers.size() - 1));
				}

				// compare each other's first unused cards - one of them might
				// be ACE
				int myFirstKicker = (myKickers.get(0).getRank() == Rank.ACE ? Rank.KING + 1
						: myKickers.get(0).getRank());
				int opponentFirstKicker = (opponentKickers.get(0).getRank() == Rank.ACE ? Rank.KING + 1
						: opponentKickers.get(0).getRank());
				if (myFirstKicker > opponentFirstKicker)
					return 1;
				else if (myFirstKicker < opponentFirstKicker)
					return 0;

				// there should be 2 remaining cards to evaluate for each player
				// we can ignore ACE's here
				for (int i = 1; i < myKickers.size(); i++) {
					if (myKickers.get(i).getRank() > opponentKickers.get(i)
							.getRank())
						return 1;
					else if (myKickers.get(i).getRank() < opponentKickers
							.get(i).getRank())
						return 0;
				}

				// impossible to break tie because all cards have been evaluated
			} else {
				// they both have crap cards, so whoever has highest card is
				// winner
				// evaluate each other's unused card to break ties
				ArrayList<Card> myKickers = getSortedCards(
						this.getNonPairCards(), false);
				ArrayList<Card> opponentKickers = getSortedCards(
						otherHand.getNonPairCards(), false);

				// move ACE to beginning of list if exists
				if (myKickers.get(myKickers.size() - 1).getRank() == Rank.ACE) {
					myKickers.add(0, myKickers.remove(myKickers.size() - 1));
				}
				if (opponentKickers.get(opponentKickers.size() - 1).getRank() == Rank.ACE) {
					opponentKickers.add(0,
							opponentKickers.remove(opponentKickers.size() - 1));
				}

				// compare card by card to see who has highest
				for (int i = 0; i < MAX_HAND_SIZE; i++) {
					int myCardRank = myKickers.get(i).getRank() == Rank.ACE ? Rank.KING + 1
							: myKickers.get(i).getRank();
					int opponentCardRank = opponentKickers.get(i).getRank() == Rank.ACE ? Rank.KING + 1
							: opponentKickers.get(i).getRank();
					if (myCardRank > opponentCardRank)
						return 1;
					else if (myCardRank < opponentCardRank)
						return 0;
				}
			}
		}
		return -1;
	}

	/*
	 * returns unique hand value - helpful when comparing versus other's hand so
	 * we decide who the round this will not avoid tiebreaker scenario using
	 * kicker
	 */
	public int evalHand() {

		int score = 0;
		if (hasRoyalFlush()) {
			_handHas = "Royal Flush";
			score += 260;
		} else if (hasStraightFlush()) {
			_handHas = "Straigt Flush";
			score += 230;
			// ACE sometimes needs to be lowest and sometimes highest
			int rank = getHighestCardRank();
			if (getAceCount() > 0) {
				if (rank == Rank.KING) {
					// Then ACE should be highest rank, so increase rank value
					rank++;
				} else {
					// do nothing, ACE should be lowest rank
				}
			}
			score += rank;
		} else if (hasFourOfAKind()) {
			_handHas = "Four of a Kind";
			score += 200;
			// remember ACE is best in this scenario
			int rank = getCardRanksByPair(4).get(0);
			score += (rank == Rank.ACE) ? Rank.KING + 1 : rank;
		} else if (hasFullHouse()) {
			_handHas = "Full House";
			score += 170;
			// remember ACE is best in this scenario
			int rank = getCardRanksByPair(3).get(0);
			score += (rank == Rank.ACE) ? Rank.KING + 1 : rank;
			// do not need to score 1 Pair because there will be only one
			// instance of a single rank's 3 of Kind
		} else if (hasFlush()) {
			_handHas = "Flush";
			score += 140;
			// In game, we compare first highest, then second highest, ...
			// and this becomes problematic when all ranks add up to high score
			// even though hand's highest rank isn't the best.
			// willBeat method needs to handle this, so do nothing more here
		} else if (hasStraight()) {
			_handHas = "Straight";
			score += 110;
			// ACE sometimes needs to be lowest and sometimes highest
			int rank = getHighestCardRank();
			if (getAceCount() > 0) {
				if (rank == Rank.KING) {
					// Then ACE should be highest rank, so increase rank value
					rank++;
				} else {
					// do nothing, ACE should be lowest rank
				}
			}
			score += rank;
		} else if (hasThreeOfAKind()) {
			_handHas = "Three of a Kind";
			score += 80;
			// remember ACE is best in this scenario
			int rank = getCardRanksByPair(3).get(0);
			score += ((rank == Rank.ACE) ? Rank.KING + 1 : rank);
		} else if (hasTwoPair()) {
			_handHas = "Two Pair";
			score += 50;
			// In game, we compare first highest, then second highest, ...
			// and this becomes problematic when all ranks add up to high score
			// even though hand's highest rank isn't the best.
			// willBeat method needs to handle this, so do nothing more here
		} else if (hasOnePair()) {
			_handHas = "One Pair";
			score += 20;
			// remember ACE is best in this scenario
			int rank = getCardRanksByPair(2).get(0);
			score += ((rank == Rank.ACE) ? Rank.KING + 1 : rank);
		}
		return score;
	}

	/*
	 * helper methods for determining hand's unique score
	 */

	// get highest value of card rank
	private int getHighestCardRank() {
		int rank = 0;
		for (Card card : _cards) {
			if (rank < card.getRank()) {
				rank = card.getRank();
			}
		}
		return rank;
	}

	// get the rank of pair
	public ArrayList<Integer> getCardRanksByPair(int pair) {
		ArrayList<Integer> ranks = new ArrayList<Integer>();
		for (int rank : _ranks.keySet()) {
			if (_ranks.get(rank) == pair) {
				ranks.add(rank);
				// hand cannot have more than one 3 of Kind or 4 of Kind
				if (pair != 2)
					break;
			}
		}
		return ranks;
	}

	/*
	 * there exists n number of cards with the same suit, return -1 if n number
	 * of cards of same suit not found, otherwise return suit
	 */
	public int getSuitByCount(int n) {
		for (int key : _suits.keySet()) {
			if (_suits.get(key) == n)
				return key;
		}
		return -1;
	}

	/*
	 * return highest rank of n number of cards in sequence, return -1 if n
	 * number of cards not found in sequence, otherwise return highest rank
	 */
	public int getHighestRankOfSequence(int n) {

		if (!_isAscendingCards)
			sortCardsByRank();

		int seqCount = 0;
		int highCard = 0;
		Card previousCard = null;
		// loop through each card in _cards
		for (int i = 0; i < _cards.size(); i++) {
			Card card = _cards.get(i);
			if (previousCard == null) {
				// do nothing on first card
			} else if (previousCard.getRank() != card.getRank() - 1) {
				if (previousCard.getRank() == Rank.ACE) {

					// move ACE to end of hand
					// our straight may have royal cards
					_cards.remove(previousCard);
					_cards.add(_cards.size(), previousCard);
					i--;

					// moving ACE will arithmetically break our sort
					_isAscendingCards = false;

				} else if ((previousCard.getRank() == Rank.KING)
						&& (card.getRank() == Rank.ACE)) {

					// DO nothing, cards are in sequence

				} else {
					// breakage in sequence
					// does seqCount ever again have a chance to equal n?
					if ((_cards.size() - (i + 1)) < n)
						break;
					// start over
					seqCount = 0;
				}
			} else {
				// so far so good
				seqCount++;
				highCard = card.getRank();
			}
			previousCard = card; // use for next iteration comparison
		}

		return (seqCount == n) ? highCard : -1;
	}

	/*
	 * end helper methods
	 */

	/*
	 * all cards have same suit and are 10 thru ACE
	 */

	public Boolean hasRoyalFlush() {
		Boolean hasRoyalFlush;
		if (hasRoyalFlush = hasFlush()) {
			if (hasRoyalFlush = hasStraight()) {
				int suit = _cards.get(0).getSuit();
				if (hasRoyalFlush = _cards.contains(new Card(suit, Rank.ACE))
						&& _cards.contains(new Card(suit, Rank.KING))) {
					// User has Royal Flush!
				}
			}
		}

		return hasRoyalFlush;
	}

	/*
	 * all cards have same suit and are in sequence
	 */
	public Boolean hasStraightFlush() {
		Boolean hasStraightFlush;
		if (hasStraightFlush = hasFlush()) {
			if (hasStraightFlush = hasStraight()) {
				// User has Straight Flush!
			}
		}
		return hasStraightFlush;
	}

	/*
	 * there exists four of the same rank
	 */
	public Boolean hasFourOfAKind() {
		return _ranks.containsValue(4);
	}

	/*
	 * one pair and three of kind
	 */
	public Boolean hasFullHouse() {

		return (hasOnePair() && hasThreeOfAKind());
	}

	/*
	 * all cards have same suit
	 */
	public Boolean hasFlush() {
		return _suits.containsValue(MAX_HAND_SIZE);
	}

	/*
	 * all cards are in sequence
	 */
	public Boolean hasStraight() {

		if (!_isAscendingCards)
			sortCardsByRank();

		Boolean hasStraight = true;
		Card previousCard = null;
		// loop through each card in _cards
		for (int i = 0; i < _cards.size(); i++) {
			Card card = _cards.get(i);
			if (previousCard == null) {
				// do nothing on first card
			} else if (previousCard.getRank() != card.getRank() - 1) {
				if (previousCard.getRank() == Rank.ACE) {

					// move ACE to end of hand
					// our straight may have royal cards
					_cards.remove(previousCard);
					_cards.add(_cards.size(), previousCard);
					i--;

					// moving ACE will arithmetically break our sort
					_isAscendingCards = false;

				} else if ((previousCard.getRank() == Rank.KING)
						&& (card.getRank() == Rank.ACE)) {

					// DO nothing, cards are in sequence

				} else {

					hasStraight = false; // break if sequence broke
					break;
				}
			}
			previousCard = card; // use for next iteration comparison
		}
		return hasStraight;
	}

	/*
	 * there exists three of the same rank
	 */
	public Boolean hasThreeOfAKind() {
		return _ranks.containsValue(3);
	}

	/*
	 * there exists two pairs of the same rank
	 */
	public Boolean hasTwoPair() {
		/*
		 * get Collection of values contained in Hashtable using Collection
		 * values() method of Hashtable class
		 */
		Collection<Integer> c = _ranks.values();
		// iterate through the collection
		Iterator<Integer> itr = c.iterator();
		int countPairs = 0;
		while (itr.hasNext()) {
			if ((int) itr.next() == 2) {
				countPairs++;
			}
		}

		return (countPairs == 2);
	}

	/*
	 * there exists one pair of the same rank
	 */
	public Boolean hasOnePair() {
		return _ranks.containsValue(2);
	}

}
