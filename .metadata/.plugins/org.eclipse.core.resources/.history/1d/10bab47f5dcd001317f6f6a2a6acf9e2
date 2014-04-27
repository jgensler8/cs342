package Game;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import Game.CardAttributes;

/*
 * !!!!!!!!!!!!!!!!!!!!! I don't have pictures for wild or skip cards yet
 */

public class Card extends JLabel implements CardAttributes{
	private static final long serialVersionUID = 1L;
	private int _rank;		//1,2,3,4,5,6,7,8,9,10,11,12, wild, skip
	private int _color;		//blue, yellow, red, green
	
	/**
	 * construct the card, validate its parameters
	 * 
	 * @param rank  should match those in CardAttributes Interface
	 * @param color should match those in CardAttributes Interface
	 */
	public Card(int rank, int color){
		//validate type
		if( rank < ONE || rank > WILD){
			this._rank = -1;
		}
		else{
			this._rank = rank;
		}
		
		//validate color
		if( color != YELLOW && color != RED && color != GREEN && color != BLUE){
			this._color = -1;
		}
		else{
			this._color = color;
		}
		
		//construct the path to the image that this card represents
		String path = "Game/CardImages/"+ this._rank;
		switch(this._color){
		case BLUE: 
			path += "_Blue.png";
			break;
		case RED:
			path += "_Red.png";
			break;
		case YELLOW:
			path += "_Yellow.png";
			break;
		case GREEN:
			path += "_Green.png";
			break;
		}

		ClassLoader cl = this.getClass().getClassLoader();
		try {
			this.setIcon( new ImageIcon( cl.getResource(path)));
		} catch(Exception e){
			//TODO
			this.setIcon( new ImageIcon( cl.getResource("Game/CardImages/1_Blue.png")));
			e.printStackTrace();
		}
	}
	
	/**
	 * get the type of this card
	 */
	public int getRank(){
		return this._rank;
	}
	
	/**
	 * get the color of this card
	 */
	public int getColor(){
		return this._color;
	}
}
