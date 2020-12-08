package flappyBirds.test1;

import java.awt.Canvas;

public class GameCanvas extends Canvas {
	
	ApplicationFB1 applicationFB1;
	
	public GameCanvas(ApplicationFB1 applicationFB1) {
		this.applicationFB1 = applicationFB1;
		setSize(applicationFB1.width, applicationFB1.height);
		setVisible(true);
	}
	
}
