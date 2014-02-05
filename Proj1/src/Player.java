public class Player {
	protected String name;
	protected Hand hand = new Hand();
	
	public Player(){
		name = "User";
	}
	
	public Player(String userName){
		this();
		name = userName;
	}
	
	/*
	 * returns a deep copy of the hand
	 */
	public Hand getHand(){
		//deep copy?
		return hand;
	}
	
	/*
	 * returns the name of the player
	 */
	public String getName(){
		return name;
	}
}
