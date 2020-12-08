package flappyBirds.test2;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;


public class Bird {
	
	public int score = 0;
	public double speed = 0;
	public double posY = 5;
	public double velocityY = 0;
	
	Game game;
	ApplicationFB2 applicationFB2;
	BufferedImage icon;
	
	public Bird(Game game, ApplicationFB2 applicationFB2) throws IOException {
		this.game = game;
		this.applicationFB2 = applicationFB2;
		icon = ImageIO.read(new File("resources/bird.png"));
	}
	
}
