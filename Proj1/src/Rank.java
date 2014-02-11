public final class Rank {

	public static final int ACE = 1;
	public static final int TWO = 2;
	public static final int THREE = 3;
	public static final int FOUR = 4;
	public static final int FIVE = 5;
	public static final int SIX = 6;
	public static final int SEVEN = 7;
	public static final int EIGHT = 8;
	public static final int NINE = 9;
	public static final int TEN = 10;
	public static final int JACK = 11;
	public static final int QUEEN = 12;
	public static final int KING = 13;

	/*
	 * return string representation of input rank integer
	 */
	public static String toString(int rank) {
		String ret = "NA";
		switch (rank) {
		case ACE:
			ret = "A";
			break;
		case JACK:
			ret = "J";
			break;
		case QUEEN:
			ret = "Q";
			break;
		case KING:
			ret = "K";
			break;
		default:
			if (rank >= TWO && rank <= TEN)
				ret = String.valueOf(rank);
			break;
		}
		return ret;
	}
	
	/*
	 * return true if input suit integer is a valid suit
	 */
	public static Boolean isValid(int rank) {
		return (rank >= ACE && rank <= KING);
	}
}
