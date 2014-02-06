import java.util.ArrayList;
import java.util.Collections;

public class Hand {
	
	// the max hand size that is allowed for any hand
	public final int MAX_HAND_SIZE = Game.MAX_HAND_SIZE;
	// the actual list of cards that the hand contains
	ArrayList<Card> _cards;
	
	public Hand(){	
		_cards = new ArrayList<Card>();
	}
	
	/*
	 * add card to the players hand
	 * if the hand "overflows," throw an exception to setup the game
	 */
	public void add( Card C){
		if( _cards.size() > MAX_HAND_SIZE){
			//TODO ***THROW SOME EXCEPTION OR SOMETHING
		}
		else{
			_cards.add( C);
		}
	}
	
	/*
	 * discard a card index
	 * NOTE: starts numbering at zero
	 */
	public Card remove(int index){
		if( index > _cards.size() ){
			//TODO ***THROW SOME EXCEPTION
		}
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
		if( hasRoyalFlush() )		score += 180;
		else if( hasStraightFlush())score += 160;
		else if( hasFourOfAKind() ) score += 140;
		else if( hasFullHouse() ) 	score += 120; // TODO RANK OF TRIPLE IS THE "highest card" 
		else if( hasFlush() ) 		score += 100;
		else if( hasStraight() ) 	score += 80;
		else if( hasThreeOfAKind())	score += 60;
		else if( hasTwoPair() ) 	score += 40;
		else if( hasOnePair() ) 	score += 20;
		
		score += getHighestInPlayable(); //SEE ABOVE
		return score;
	}
	
	/*
	 * return the highest card in the playable hand
	 * this is used to rank two hands of the same type
	 */
	private int getHighestInPlayable() {
		//TODO *******************************MAKE THIS WORK FOR ACE HIGH************
		int highest = 0;
		for( Card c : _cards){
			if( c.getRank() > highest ){
				highest = c.getRank();
			}
		}
		return highest;
	}
	
	/*
	 * separate functions to determine if a certain type of
	 * playable hand applies to the one in this player's hand
	 */
	/*
	 * A,K,Q,J,10 in any suit
	 */
	Boolean hasRoyalFlush(){
		return hasStraightFlush(); //TODO // && cards == A K Q J 10
	}
	/*
	 * straight + flush 
	 */
	Boolean hasStraightFlush(){
		return hasStraight() && hasFlush();
	}
	/*
	 * four of one type of card
	 * only need to check the first two cards of a five card hand
	 */
	Boolean hasFourOfAKind(){
		for(int startCard = 0; startCard < (_cards.size()-3) ; ++startCard){ //we don't have to check the last 3 cards in the array
		int rankToCompare = _cards.get(startCard).getRank();
		int matchedCards = 0;
		for(int otherCard = startCard+1; otherCard < _cards.size(); ++otherCard){
			if( _cards.get(otherCard).getRank() == rankToCompare) ++matchedCards;
		}
		if( matchedCards == 4) return true;
	}
	return false;
	}
	/*
	 * three of a kind + two of a kind
	 */
	Boolean hasFullHouse(){
		return hasThreeOfAKind() && hasOnePair(); //TODO what do we do when we find the three of a kind?
		//perhaps we can actually enumerate this one
	}
	/*
	 * five cards of same suit
	 */
	Boolean hasFlush(){
		int firstSuit = _cards.get(0).getSuit();
		for(int cardNum = 1; cardNum < _cards.size(); ++cardNum ){
			if( firstSuit != _cards.get(cardNum).getSuit() ) return false;
		}
		return true;
	}
	/*
	 * five cards "in a row"
	 * suit doesn't matter
	 */
	Boolean hasStraight(){
		this.orderDescending();
		int previousRank = _cards.get(0).getRank();
		for(int cardNum = 1; cardNum < _cards.size(); previousRank = _cards.get(cardNum).getRank(), ++cardNum){
			if( _cards.get(cardNum).getRank() != (previousRank-1) ) return false; //TODO make work with A K Q J 10
		}
		return true;
	}
	/*
	 * three cards of same rank
	 */
	Boolean hasThreeOfAKind(){
		for(int startCard = 0; startCard < (_cards.size()-2) ; ++startCard){ //we don't have to check the last 2 cards in the array
			int rankToCompare = _cards.get(startCard).getRank();
			int matchedCards = 0;
			for(int otherCard = startCard+1; otherCard < _cards.size(); ++otherCard){
				if( _cards.get(otherCard).getRank() == rankToCompare) ++matchedCards;
			}
			if( matchedCards == 3) return true;
		}
		return false;
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
		for(int startCard = 0; startCard < (_cards.size()-3) ; ++startCard){
			int rankToCompare = _cards.get(startCard).getRank();
			int matchedCards = 0;
			for(int otherCard = startCard+1; otherCard < _cards.size(); ++otherCard){
				if( _cards.get(otherCard).getRank() == rankToCompare) ++matchedCards;
			}
			if( matchedCards == 2) return true;
		}
		return false;
	}
}
