package flappyBirds.test3;

import java.awt.Canvas;

public class GameCanvas extends Canvas {
	
	ApplicationFB3 applicationFB3;
	
	public GameCanvas(ApplicationFB3 applicationFB3) {
		this.applicationFB3 = applicationFB3;
		setSize(applicationFB3.width, applicationFB3.height);
		setVisible(true);
	}
	
}
