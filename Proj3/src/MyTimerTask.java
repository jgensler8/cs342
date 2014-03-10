import java.util.*;

// http://stackoverflow.com/questions/1979538/in-java-what-event-is-continuously-fired-on-mouse-button-down
public class MyTimerTask extends TimerTask {
	Car car;
	
	public MyTimerTask(Car c) {
		car = c;
		System.out.printf("%s : %d %d, %d %d\n", c.name, c.width, c.height, c.xPos, c.yPos);
	}
	
	@Override public void run() {	
		car.setSize(++car.width, ++car.height);
	}
}
