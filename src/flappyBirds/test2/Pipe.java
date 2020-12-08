package flappyBirds.test2;

import java.awt.*;

import javax.swing.*;

public class Pipe {
	
	public double size = 0.5;
	public boolean bottom = false;
	public int height;
	public double posY;
	
	Game game;
	ApplicationFB2 applicationFB2;
	Image icon;
	
	public Pipe(Game game, ApplicationFB2 applicationFB2, double size, boolean bottom) {
		this.bottom = bottom;
		this.size = size;
		this.game = game;
		this.applicationFB2 = applicationFB2;
		if (bottom) {
			Image icon = new ImageIcon("resources/pipeBottom.png").getImage();
			this.icon = (new ImageIcon(icon.getScaledInstance(50, (new ImageIcon("resources/pipeBottom.png")).getIconHeight()/2, Image.SCALE_SMOOTH))).getImage();
			this.height = (int) (applicationFB2.height*size);
		}
		else {
			Image icon = new ImageIcon("resources/pipeTop.png").getImage();
			this.icon = (new ImageIcon(icon.getScaledInstance(50, (new ImageIcon("resources/pipetop.png")).getIconHeight()/2, Image.SCALE_SMOOTH))).getImage();
			this.height = (int) (applicationFB2.height*size);
			this.height = applicationFB2.height-height-(new ImageIcon("resources/pipeTop.png")).getIconHeight()/2;
		}
	}
	
}
