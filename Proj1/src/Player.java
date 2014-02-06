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
	 * print the players hand to system.out
	 */
	public void printHand(){
		System.out.print( _name + "'s hand: ");
		for(int cardNum = 0; cardNum < _hand._cards.size(); ++cardNum ){
			System.out.print( (cardNum+1) + ") " + _hand._cards.get(cardNum).toString() + " ");
		}
		System.out.println("");
	}
}
