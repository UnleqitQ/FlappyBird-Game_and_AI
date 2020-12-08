package flappyBirds.test2;

import java.awt.Canvas;

public class GameCanvas extends Canvas {
	
	ApplicationFB2 applicationFB2;
	
	public GameCanvas(ApplicationFB2 applicationFB2) {
		this.applicationFB2 = applicationFB2;
		setSize(applicationFB2.width, applicationFB2.height);
		setVisible(true);
	}
	
}
