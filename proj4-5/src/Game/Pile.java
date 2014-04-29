package Game;

import java.io.Serializable;
import java.util.*;

public class Pile implements Serializable{
    private static int[] colors = new int[] { Card.GREEN, Card.BLUE,
                    Card.RED, Card.YELLOW };

    // deck to draw card
    private Stack<Card> _deck = null;
    // pile of discarded card
    private Stack<Card> _discards = null;
    // cards played by players and append-able by other players
    private ArrayList<ArrayList<Card>> _sets = null; // based off set
    private ArrayList<ArrayList<Card>> _runs = null; // based off run
    private ArrayList<ArrayList<Card>> _colors = null; // based off color

    public Pile() {
            init();
    }

    /**
     * initialize deck of cards
     */
    private void init() {
            _deck = new Stack<Card>();
            _discards = new Stack<Card>();
            // 96 numbered cards: two of each value from 1-12, in each of four
            // colors.
            for (int iPair = 1; iPair <= 2; iPair++) {
                    for (int iColor : colors) {
                            for (int iRank = 1; iRank <= 12; iRank++) {
                                    _deck.push(new Card(iColor, iRank));
                            }
                    }
            }
            // eight Wild cards
            for (int i = 1; i <= 8; i++) {
                    _deck.push(new Card(0, Card.WILD));
            }
            // four Skip cards
            for (int i = 1; i <= 4; i++) {
                    _deck.push(new Card(0, Card.SKIP));
            }
            // shuffle deck
            shuffle();
    }

    /**
     * shuffle deck in random order
     */
    private void shuffle() {
            // TODO: finish me
    }

    /**
     * @return top card of the deck
     */
    public Card getFromDeck() {
            return _deck.size() > 0 ? _deck.remove(0) : null;
    }

    /**
     * @return top card of the discards
     */
    public Card getFromDiscards() {
            if (_discards == null)
                    _discards = new Stack<Card>();
            return _discards.size() > 0 ? _discards.pop() : null;
    }

    /**
     * add card to pile of discards
     * 
     * @param card
     *            card to be added
     */
    public void addToDiscards(Card card) {
            if (_discards == null)
                    _discards = new Stack<Card>();
            _discards.push(card);
    }

    /**
     * add list cards that are in set onto the floor to be eligible to be
     * appended by other players
     * 
     * @param cards
     *            cards to be added
     */
    public void addToSets(ArrayList<Card> cards) {
            if (_sets == null) {
                    _sets = new ArrayList<ArrayList<Card>>();
            }
            _sets.add(cards);
    }

    /**
     * add list cards that are in run onto the floor to be eligible to be
     * appended by other players
     * 
     * @param cards
     *            cards to be added
     */
    public void addToRuns(ArrayList<Card> cards) {
            if (_runs == null) {
                    _runs = new ArrayList<ArrayList<Card>>();
            }
            _runs.add(cards);
    }

    /**
     * add list cards that are the same color onto the floor to be eligible to
     * be appended by other players
     * 
     * @param cards
     *            cards to be added
     */
    public void addToColors(ArrayList<Card> cards) {
            if (_colors == null) {
                    _colors = new ArrayList<ArrayList<Card>>();
            }
            _colors.add(cards);
    }

    /**
     * append card that is in set with a list of sets on the floor
     * 
     * @param cards
     *            card to be appended
     * @param i
     *            index of list of sets to append to
     */
    public void appendToSet(Card card, int i) {
            if (_sets == null) {
                    // TODO: why would this happen?
                    return;
            }
            if (_sets.size() <= i) {
                    // TODO: why would this happen?
                    return;
            }
            _sets.get(i).add(card);
    }

    /**
     * append card that is in run with a list of runs on the floor
     * 
     * @param cards
     *            card to be appended
     * @param i
     *            index of list of runs to append to
     */
    public void appendToRun(Card card, int i) {
            if (_runs == null) {
                    // TODO: why would this happen?
                    return;
            }
            if (_runs.size() <= i) {
                    // TODO: why would this happen?
                    return;
            }
            _runs.get(i).add(card);
    }

    /**
     * append card that is the same color with a list of uniform colors on the
     * floor
     * 
     * @param cards
     *            card to be appended
     * @param i
     *            index of list of uniform colors to append to
     */
    public void appendToColor(Card card, int i) {
            if (_colors == null) {
                    // TODO: why would this happen?
                    return;
            }
            if (_colors.size() <= i) {
                    // TODO: why would this happen?
                    return;
            }
            _colors.get(i).add(card);
    }

	/**
	 * @return either blank card or the card on top
	 */
	public Card renderDraw(){
		return new Card(Card.DRAW,Card.DRAW).render(null);
	}
	
	/**
	 * @return either blank card or the card on top
	 */
	public Card renderDiscard(){
		try{
			return this._discards.peek().render(null);
		} catch( EmptyStackException e){
			return new Card(Card.BLANK, Card.BLANK).render(null);
		}
	}
	
	/**
	 * 
	 */
	public void returnCard( Card c){
		this._discards.push( c);
	}
}
