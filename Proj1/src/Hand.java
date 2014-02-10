import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

public class Hand {
	// the max hand size that is allowed for any hand
	public final int MAX_HAND_SIZE = Game.MAX_HAND_SIZE;
	// the actual list of cards that the hand contains
	private ArrayList<Card> _cards;
	
	/*
	ArrayList<Card> _tieBreakers;
	private int _score;
	*/
	
	Hashtable<Integer,Integer> _rankHashCount; 
	Hashtable<Integer,Integer> _suitHashCount;
	
	public Hand(){	
		this._cards = new ArrayList<Card>();
		this._rankHashCount = new Hashtable<Integer,Integer>();
		this._suitHashCount = new Hashtable<Integer,Integer>();
	}
	
	/*
	 * add card to the players hand
	 * if the hand "overflows," throw an exception to setup the game
	 */
	public void add( Card C){
		if( this._cards.size() > MAX_HAND_SIZE){
			//TODO
			//maybe throw exception
		}
		else{
			this._cards.add( C);
			if( this._rankHashCount.containsKey(C.getRank()) ){
				int prevRankHashValue = this._rankHashCount.get(C.getRank());
				this._rankHashCount.remove( C.getRank());
				this._rankHashCount.put(C.getRank(), prevRankHashValue +1);
			}
			else{
				this._rankHashCount.put(C.getRank(), 1);
			}
			if( this._suitHashCount.containsKey(C.getSuit())){
				int prevSuitHashValue = this._suitHashCount.get(C.getSuit());
				this._suitHashCount.remove( C.getSuit());
				this._suitHashCount.put(C.getSuit(), prevSuitHashValue + 1);
			}
			else{
				this._suitHashCount.put(C.getSuit(), 1);
			}
		}
	}
	
	/*
	 * discard a card index
	 * NOTE: starts numbering at zero
	 */
	public Card remove(int index){
		if( index > this._cards.size() ){
			//TODO
			//maybe throw exception
		}
		int prevRankHashValue = this._rankHashCount.get( this._cards.get(index).getRank());
		int prevSuitHashValue = this._suitHashCount.get( this._cards.get(index).getSuit());
		int prevRankHashKey = this._cards.get(index).getRank();
		int prevSuitHashKey = this._cards.get(index).getSuit();
		this._rankHashCount.remove(prevRankHashKey);
		this._suitHashCount.remove(prevSuitHashKey);
		this._rankHashCount.put(prevRankHashKey, prevRankHashValue-1);
		this._suitHashCount.put(prevSuitHashKey, prevSuitHashValue-1);
		return this._cards.remove(index);
	}
	
	/*
	 * discard a card index
	 * NOTE: starts numbering at zero
	 */
	public ArrayList<Card> removeAll(ArrayList<Integer> indices){
		ArrayList<Card> discarded = new ArrayList<Card>();
		for( int index : indices){
			if( index > this._cards.size() ){
				//TODO
				//maybe throw exception
			}
			//recalculate the table
			int prevRankHashKey = this._cards.get(index).getRank();
			int prevSuitHashKey = this._cards.get(index).getSuit();
			int prevRankHashValue = this._rankHashCount.get( prevRankHashKey);
			int prevSuitHashValue = this._suitHashCount.get( prevSuitHashKey);
			this._rankHashCount.remove(prevRankHashKey);
			this._suitHashCount.remove(prevSuitHashKey);
			this._rankHashCount.put(prevRankHashKey, prevRankHashValue-1);
			this._suitHashCount.put(prevSuitHashKey, prevSuitHashValue-1);
			discarded.add( this._cards.get(index));
		}
		this._cards.removeAll(discarded);
		return discarded;
	}
	
	/*
	 * return the size of the hand
	 */
	public int size(){
		return this._cards.size();
	}
	
	/*
	 * index should be passed starting from 0 and going to MAX_HAND_SIZE-1
	 */
	public String toString(int index){
		if( index >= this._cards.size() || index < 0){
			return "";
		}
		else return this._cards.get(index).toString();
	}
	
	/*
	 * get the suit at an index of the hand
	 */
	public int getSuit(int index){
		if( index >= this._cards.size() || index < 0){
			return -1;
		}
		return this._cards.get(index).getSuit();
	}
	
	/*
	 * get the rank at an index of the hand
	 */
	public int getRank(int index){
		if( index >= this._cards.size() || index < 0){
			return -1;
		}
		return this._cards.get(index).getRank();
	}
	
	/*
	 * 
	 */
	public void refill(CardPile deck) {
		while( this._cards.size() < MAX_HAND_SIZE){
			this.add( deck.drawCard());
		}
	}
	
	/*
	 * order the cards from highest value first to lowest value last
	 */
	public void orderDescending(){
		Collections.sort(this._cards, Card.cardComparatorDesc);
	}
	
	/*
	 * return a score heuristic of the playable hand
	 * this heuristic can be used to see if one of two 
	 * hands of the same type ranks higher but will not
	 * work when these hands tie.
	 */
	private int evalHand(){
		int score = 0;
		if( hasRoyalFlush() ) score += 180;
		else if( hasStraightFlush()) score += 160;
		else if( hasFourOfAKind() ) score += 140;
		else if( hasFullHouse() ) score += 120;
		else if( hasFlush() ) score += 100;
		else if( hasStraight() ) score += 80;
		else if( hasThreeOfAKind()) score += 60;
		else if( hasTwoPair() ) score += 40;
		else if( hasOnePair() ) score += 20;
		return score;
	}
	
	/*
	 * find out if this.hand beats otherhand
	 * 1: true, this hand beats otherhand
	 * 0: false, this hand fails to beat otherhand
	 * -1: tie, these hands are the same
	 */
	public int willBeat( Hand otherHand){
		//first lets see if the two hands tie
		int myScore = this.evalHand();
		int otherScore = otherHand.evalHand();
		if( myScore > otherScore) return 1;
		else if( otherScore > myScore) return 0;
		else{
			//find out which hand they tie on
			if( this.hasRoyalFlush() ){
				//no high card
				//no tie breaker
				return -1;
			}
			else if( this.hasStraightFlush()){
				//one high card
				//no tie breaker
			}
			else if( this.hasFourOfAKind()){
				//one high card
				//no tie breaker
			}
			else if( this.hasFullHouse()){
				//two high cards
				//one tie breaker
			}
			else if( this.hasFlush()){
				//one high card
				//no tie breaker
			}
			else if( this.hasStraight()){
				//one high card
				//no tie breaker
			}
			else if( this.hasThreeOfAKind()){
				//one high card
				//two tie breakers
			}
			else if( this.hasTwoPair()){
				//two high cards
				//one tie breaker
			}
			else if( this.hasOnePair()){
				//one high card
				//three tie breakers
			}
			else{
				this.orderDescending();
				otherHand.orderDescending();
				for(int testIndex = 0; testIndex < MAX_HAND_SIZE; ++testIndex){
					if( this._cards.get(testIndex).getRank() > this._cards.get(testIndex).getRank())
						return 1;
					else if( this._cards.get(testIndex).getRank() < this._cards.get(testIndex).getRank())
						return 0;
				}
			}
		}
		return -1;
	}
	
	/*
	 * separate functions to determine if a certain type of
	 * playable hand applies to the one in this player's hand
	 */
	/*
	 * A,K,Q,J,10 in any suit
	 */
	public Boolean hasRoyalFlush(){
		return hasStraightFlush() && hasVal( Card.ACE) && hasVal( Card.KING);
	}
	/*
	 * straight + flush 
	 */
	public Boolean hasStraightFlush(){
		return hasFlush() && hasStraight();
	}
	/*
	 * four of one type of card
	 * only need to check the first two cards of a five card hand
	 */
	public Boolean hasFourOfAKind(){
		if( this._rankHashCount.containsValue(4)) return true;
		else return false;
	}
	/*
	 * three of a kind + two of a kind
	 */
	public Boolean hasFullHouse(){
		return hasThreeOfAKind() && hasOnePair();
	}
	/*
	 * five cards of same suit
	 */
	public Boolean hasFlush(){
		if( this._suitHashCount.containsValue(5)) return true;
		else return false;
	}
	/*
	 * five cards "in a row"
	 * suit doesn't matter
	 */
	public Boolean hasStraight(){
		if( this.hasVal(Card.ACE) && this.hasVal(Card.KING) && this.hasVal(Card.QUEEN)
				&& this.hasVal(Card.JACK) && this.hasVal(Card.TEN)) return true; //ace high straight
		this.orderDescending();
		int previousRank = this._cards.get(0).getRank();
		for(int cardNum = 1; cardNum < this._cards.size(); previousRank = this._cards.get(cardNum).getRank(), ++cardNum){
			if( this._cards.get(cardNum).getRank() != (previousRank-1) ) return false;
		}
		return true;
	}
	/*
	 * three cards of same rank
	 */
	public Boolean hasThreeOfAKind(){
		if( this._rankHashCount.containsValue(3) ) return true;
		else return false;
	}
	/*
	 * two pairs of cards of same rank
	 */
	public Boolean hasTwoPair(){
		int pairCounter = 0, pairRank1 = -1;
		for( Card c : this._cards){
			if( this._rankHashCount.get( c.getRank()) == 2 ){
				if( pairRank1 == -1) pairRank1 = c.getRank();
				else if( pairRank1 != c.getRank() ) ++pairCounter; //the first pair has been set,this card is not the pair
			}
		}
		if( pairCounter == 2) return true;
		else return false;
	}
	/*
	 * a pair of cards of same rank
	 */
	public Boolean hasOnePair(){
		if( this._rankHashCount.containsValue(2)) return true;
		else return false;
	}
	/*
	 * determine if a hand has an ace
	 * this is used for determining if a plater can discard 3 or 4 cards
	 */
	public Boolean hasVal(int val){
		for(Card c : this._cards){
			if( c.getRank() == val) return true;
		}
		return false;
	}
	
	/*
	 * see if the card at this index contributes to a three of a kind
	 */
	public Boolean contibutesToFour( int index){
		if( this._rankHashCount.get( this._cards.get(index).getRank()) == 4) return true;
		return false;
	}
	
	/*
	 * see if the card at this index contributes to a three of a kind
	 */
	public Boolean contributesToThree( int index){
		if( this._rankHashCount.get( this._cards.get(index).getRank()) == 3) return true;
		return false;
	}
	
	/*
	 * see if the card at this index contributes to a pair of cards
	 */
	public Boolean contributesToPair( int index){
		if( this._rankHashCount.get( this._cards.get(index).getRank()) == 2) return true;
		return false;
	}
}
