import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

public class Hand {
	// the max hand size that is allowed for any hand
	public final int MAX_HAND_SIZE = Game.MAX_HAND_SIZE;
	// the actual list of cards that the hand contains
	ArrayList<Card> _cards;
	
	/*
	ArrayList<Card> _tieBreakers;
	private int _score;
	*/
	
	Hashtable<Integer,Integer> _rankHashCount; 
	Hashtable<Integer,Integer> _suitHashCount;
	
	public Hand(){	
		_cards = new ArrayList<Card>();
		_rankHashCount = new Hashtable<Integer,Integer>();
		_suitHashCount = new Hashtable<Integer,Integer>();
	}
	
	/*
	 * add card to the players hand
	 * if the hand "overflows," throw an exception to setup the game
	 */
	public void add( Card C){
		if( _cards.size() > MAX_HAND_SIZE){
			//TODO
			//maybe throw exception
		}
		else{
			_cards.add( C);
			if( _rankHashCount.containsKey(C.getRank()) ){
				_rankHashCount.put(C.getRank(), _rankHashCount.get(C.getRank())+1);
			}
			else{
				_rankHashCount.put(C.getRank(), 1);
			}
			if( _suitHashCount.containsKey(C.getSuit())){
				//_suitHashCount.
				_suitHashCount.put(C.getSuit(), _suitHashCount.get(C.getSuit()) + 1);
			}
			else{
				_suitHashCount.put(C.getSuit(), 1);
			}
		}
	}
	
	/*
	 * discard a card index
	 * NOTE: starts numbering at zero
	 */
	public Card remove(int index){
		if( index > _cards.size() ){
			//TODO
			//maybe throw exception
		}
		_rankHashCount.put( _cards.get(index).getRank(),
				_rankHashCount.get( _cards.get(index).getRank())-1);
		_suitHashCount.put( _cards.get(index).getSuit(),
				_rankHashCount.get( _cards.get(index).getSuit())-1);
		return _cards.remove(index);
	}
	
	/*
	 * order the cards from highest value first to lowest value last
	 */
	public void orderDescending(){
		Collections.sort(_cards, Card.cardComparatorDesc);
	}
	
	/*
	 * return a score heuristic of the playable hand
	 * this heuristic can be used to see if one of two 
	 * hands of the same type ranks higher but will not
	 * work when these hands tie.
	 */
	public int evalHand(){
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
	Boolean hasRoyalFlush(){
		return hasStraightFlush() && hasVal( Card.ACE) && hasVal( Card.KING);
	}
	/*
	 * straight + flush 
	 */
	Boolean hasStraightFlush(){
		return hasFlush() && hasStraight();
	}
	/*
	 * four of one type of card
	 * only need to check the first two cards of a five card hand
	 */
	Boolean hasFourOfAKind(){
		if( _rankHashCount.containsValue(4)) return true;
		else return false;
	}
	/*
	 * three of a kind + two of a kind
	 */
	Boolean hasFullHouse(){
		return hasThreeOfAKind() && hasOnePair();
	}
	/*
	 * five cards of same suit
	 */
	Boolean hasFlush(){
		if( _suitHashCount.containsValue(5)) return true;
		else return false;
	}
	/*
	 * five cards "in a row"
	 * suit doesn't matter
	 */
	Boolean hasStraight(){
		if( _rankHashCount.containsKey(Card.ACE)
				&& _rankHashCount.containsKey(Card.KING)
				&& _rankHashCount.containsKey(Card.QUEEN)
				&& _rankHashCount.containsKey(Card.JACK)
				&& _rankHashCount.containsKey(Card.TEN)) return true; //ace high straight
		this.orderDescending();
		int previousRank = _cards.get(0).getRank();
		for(int cardNum = 1; cardNum < _cards.size(); previousRank = _cards.get(cardNum).getRank(), ++cardNum){
			if( _cards.get(cardNum).getRank() != (previousRank-1) ) return false;
		}
		return true;
	}
	/*
	 * three cards of same rank
	 */
	Boolean hasThreeOfAKind(){
		if( _rankHashCount.containsValue(3) ) return true;
		else return false;
	}
	/*
	 * two pairs of cards of same rank
	 */
	Boolean hasTwoPair(){
		for(int startCard = 0; startCard < (_cards.size()-3) ; ++startCard){
			int rankToCompare = _cards.get(startCard).getRank();
			int matchedCards = 0;
			for(int otherCard = startCard+1; otherCard < _cards.size(); ++otherCard){
				if( _cards.get(otherCard).getRank() == rankToCompare) ++matchedCards;
			}
			if( matchedCards == 2) return true; //TODO maybe remove the matched cards and search the sublist?
		}
		return false;
	}
	/*
	 * a pair of cards of same rank
	 */
	Boolean hasOnePair(){
		if( _rankHashCount.containsValue(2)) return true;
		else return false;
	}
	/*
	 * determine if a hand has an ace
	 * this is used for determining if a plater can discard 3 or 4 cards
	 */
	Boolean hasVal(int val){
		for(Card c : _cards){
			if( c.getRank() == val) return true;
		}
		return false;
	}
}
