
public class BombField extends BombButton {
	private static final long serialVersionUID = 1L;
	
	private int _width;
	private int _height;
	private BombButton[][] _bombArray;
	/*
	 * 
	 */
	public BombField(int userWidth, int userHeight) {
		_width = userWidth;
		_height = userHeight;
		_bombArray = new BombButton[userWidth][userHeight];
	}

	
}
