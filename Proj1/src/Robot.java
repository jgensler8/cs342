
public class Robot extends Player{
	//Contains AI, no IO
	
	public Robot(int handSize){
		super( handSize);
		name = "Robot";
	}
	public Robot(int handSize, String userName){
		super( handSize, userName);
	}
	
	/*
	 * performs logic with hands, evaluates hand, and prints the final hand
	 * and results to system.out
	 */
	public void makeMove(CardPile Deck, CardPile Discard){
		
	}
}
