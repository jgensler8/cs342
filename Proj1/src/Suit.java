public final class Suit {

	public static final int CLUBS = 0;
	public static final int HEARTS = 1;
	public static final int SPADES = 2;
	public static final int DIAMONDS = 3;

	/*
	 * return string representation of input suit integer
	 */
	public static String toString(int suit) {
		String ret = "NA";
		switch (suit) {
		case CLUBS:
			ret = "C";
			break;
		case HEARTS:
			ret = "H";
			break;
		case SPADES:
			ret = "S";
			break;
		case DIAMONDS:
			ret = "D";
			break;
		}
		return ret;
	}
	
	/*
	 * return true if input suit integer is a valid suit
	 */
	public static Boolean isValid(int suit) {
		return (suit >= CLUBS && suit <= DIAMONDS);
	}
}
