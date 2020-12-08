package flappyBirds.test3;

import java.awt.Canvas;

public class NetCanvas extends Canvas {
	
	ApplicationFB3 applicationFB3;
	
	public NetCanvas(ApplicationFB3 applicationFB3) {
		this.applicationFB3 = applicationFB3;
		setSize(applicationFB3.netFrame.getWidth(), applicationFB3.netFrame.getHeight());
		setVisible(true);
	}
	
}
