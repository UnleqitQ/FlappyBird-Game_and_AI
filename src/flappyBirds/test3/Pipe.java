package flappyBirds.test3;

import java.awt.*;

import javax.swing.*;

public class Pipe {
	
	public double size = 0.5;
	public boolean bottom = false;
	public int height;
	
	Game game;
	ApplicationFB3 applicationFB3;
	Image icon;
	
	public Pipe(Game game, ApplicationFB3 applicationFB3, double size, boolean bottom) {
		this.bottom = bottom;
		this.game = game;
		this.size = size;
		this.applicationFB3 = applicationFB3;
		if (bottom) {
			Image icon = new ImageIcon("./resources/pipeBottom.png").getImage();
			this.icon = (new ImageIcon(icon.getScaledInstance(50, (new ImageIcon("./resources/pipeBottom.png")).getIconHeight()/2, Image.SCALE_SMOOTH))).getImage();
			height = (int) (applicationFB3.height*size);
		}
		else {
			Image icon = new ImageIcon("./resources/pipeTop.png").getImage();
			this.icon = (new ImageIcon(icon.getScaledInstance(50, (new ImageIcon("./resources/pipetop.png")).getIconHeight()/2, Image.SCALE_SMOOTH))).getImage();
			height = (int) (applicationFB3.height*size);
			height = applicationFB3.height-height-(new ImageIcon("./resources/pipeTop.png")).getIconHeight()/2;
		}
	}
	
}
