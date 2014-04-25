package Game;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import Game.CardAttributes;

public class Card extends JLabel implements CardAttributes{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int _type;
	private int _color;
	
	/**
	 * construct the card, validate its parameters
	 * 
	 * @param type  should match those in CardAttributes Interface
	 * @param color should match those in CardAttributes Interface
	 */
	public Card(int type, int color){
		//validate type
		if( type < ONE || type > WILD){
			this._type = -1;
		}
		else{
			this._type = type;
		}
		
		//validate color
		if( color != YELLOW && color != RED && color != GREEN && color != BLUE){
			this._color = -1;
		}
		else{
			this._color = color;
		}
		
		//construct the path to the image that this card represents
		String path = "Game/CardImages/"+ this._type;
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
	public int getType(){
		return this._type;
	}
	
	/**
	 * get the color of this card
	 */
	public int getColor(){
		return this._color;
	}
}
