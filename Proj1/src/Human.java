
public class Human extends Player{
	//doesn't contain AI, but IO
	
	public Human(int handSize){
		super( handSize);
		name = "Human";
	}
	public Human(int handSize, String userName){
		super( handSize, userName);
	}
	//
}
