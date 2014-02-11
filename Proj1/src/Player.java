
public class Player {
	
	protected String _name;
	
	protected Hand _hand;
	
	public Player() {
		_name = "Computer";
		_hand = new Hand();
	}

	public Player(String name) {
		_name = name;
		_hand = new Hand();
	}
	
	public Hand getHand() {
		return _hand;
	}
	
	/*
	 * return name of player
	 */
	public String getName() {
		return _name;
	}
	
}
