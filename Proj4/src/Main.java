import NameBuilder.NameBuilder;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		NameBuilder builder = new NameBuilder(10);
		
		int len = 100;
		System.out.println("NAMES:");
		for(int x = 0 ; x < len; x++ ){
			System.out.println( builder.generateUniqueName());
		}
	}

}
