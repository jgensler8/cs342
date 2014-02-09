public class Player {
	protected String _name;
	protected Hand _hand = new Hand();
	
	public Player(){
		_name = "User";
	}
	
	public Player(String userName){
		this();
		_name = userName;
	}
	
	/*
	 * returns a deep copy of the hand
	 */
	public Hand getHand(){
		//deep copy?
		return _hand;
	}
	
	/*
	 * returns the name of the player
	 */
	public String getName(){
		return _name;
	}
	
	/*
	 * print the value of this players hand
	 */
	public void printHandValue(){
		String toPrint = this._name + " has ";
		if( _hand.hasRoyalFlush() ) toPrint += "a royal flush!";
		else if( _hand.hasStraightFlush()) toPrint += "a straight flush!";
		else if( _hand.hasFourOfAKind() ) toPrint += "four of a kind!";
		else if( _hand.hasFullHouse() ) toPrint += "a full house!";
		else if( _hand.hasFlush() ) toPrint += "a flush!";
		else if( _hand.hasStraight() ) toPrint += "a straight!";
		else if( _hand.hasThreeOfAKind()) toPrint += "three of a kind!";
		else if( _hand.hasTwoPair() ) toPrint += "two pairs!";
		else if( _hand.hasOnePair() ) toPrint += "one pair!";
		else toPrint += "nothing...";
		System.out.println( toPrint);
	}
	
	
	/*
	 * print the players hand to system.out
	 */
	public void printHand(){
		System.out.print( _name + "'s hand: ");
		for(int cardNum = 0; cardNum < _hand.size(); ++cardNum ){
			System.out.print( (cardNum+1) + ") " + _hand.toString(cardNum) + " ");
		}
		System.out.println("");
	}
}
