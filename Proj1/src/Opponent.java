import java.util.ArrayList;

public class Opponent extends Player {

	public Opponent(String name) {
		super(name);
	}

	/*
	 * remove cards from hand that have no value
	 */
	public void discardCards() {

		if (super.getHand().hasRoyalFlush()) {
			// do nothing - best hand!
			return;
		}

		if (super.getHand().hasStraightFlush()) {
			// do nothing - good hand
			return;
		}

		if (super.getHand().hasStraightFlush()) {
			// do nothing - good hand
			return;
		}

		if (super.getHand().hasFourOfAKind()) {

			// discard extra cards that are not paired
			this.discardNonPairCards();

			return;
		}

		if (super.getHand().hasFullHouse()) {
			// do nothing - good hand
			return;
		}

		if (super.getHand().hasFlush()) {
			// do nothing - good hand
			return;
		}

		if (super.getHand().hasStraight()) {
			// do nothing - good hand
			return;
		}

		if (super.getHand().hasThreeOfAKind()) {

			// discard extra cards that are not paired
			this.discardNonPairCards();

			return;
		}

		if (super.getHand().hasTwoPair()) {

			// discard extra cards that are not paired
			this.discardNonPairCards();

			return;
		}

		if (super.getHand().hasOnePair()) {

			// discard extra cards that are not paired
			this.discardNonPairCards();

			return;
		}

		// If the hand evaluates to "High Card", determine if the user has 4
		// cards of the same suit. If so, discard the card of the different
		// suit.
		int suit = this.getHand().getSuitByCount(4);
		if (suit > -1) {
			// get suit to discard from hand
			suit = this.getHand().getSuitByCount(1);
			this.getHand().discardCardsBySuit(suit);

			return;
		}

		// determine if the user has 4 cards in sequence. If so, discard the
		// card that is out of sequence.
		int highCardInSeq = this.getHand().getHighestRankOfSequence(4);
		if (highCardInSeq > -1) {
			if (highCardInSeq == Rank.ACE)
				highCardInSeq = Rank.KING + 1;
			// discard every card to the left
			this.getHand().discardCardsByRange(0, highCardInSeq - 4);
			// discard every card to the right
			this.getHand().discardCardsByRange(highCardInSeq + 1, Rank.KING);

			return;
		}

		// if the user has an Ace, discard the other four cards. Otherwise, keep
		// the two highest cards and discard the other 3.
		ArrayList<Card> cards = this.getHand().getHighestToLowestRankCards();
		if (cards.get(cards.size() - 1).getRank() == Rank.ACE) {
			// discard everything but this ACE
			this.getHand().discardCardsByRange(Rank.TWO, Rank.KING);
		} else {
			// worst cards are at the end so loop from end of list towards
			// beginning
			for (int i = cards.size() - 1; i > Game.MAX_HAND_SIZE
					- Game.NORMAL_DISCARD_LIMIT - 1; i--) {
				this.getHand().discardCard(cards.get(i));
			}
		}
	}

	/*
	 * discard cards that are not paired - have no value
	 */
	private void discardNonPairCards() {

		int aceCount = super.getHand().getAceCount();
		int discardLimit = Game.NORMAL_DISCARD_LIMIT;
		if (aceCount > 0) {
			discardLimit = Game.ACE_DISCARD_LIMIT;
		}

		// discard extra cards that are not paired
		ArrayList<Card> discardCards = super.getHand().getNonPairCards();
		for (Card card : discardCards) {

			// apply same discard rule from Game
			discardLimit--;
			if ((card.getRank() == Rank.ACE) && (aceCount == 1)) {
				discardLimit--;
			}

			if (discardLimit < 0) {
				// no more discard allowed
				break;
			}

			super.getHand().discardCard(card);
		}
	}
}
