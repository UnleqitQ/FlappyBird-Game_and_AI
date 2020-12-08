package flappyBirds.test1;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.util.*;

import javax.swing.ImageIcon;

import org.jbox2d.common.*;
import org.jbox2d.dynamics.*;

public class Game {
	
	World world;
	Graphics g;
	
	Bird bird;
	ArrayList<PipePair> pipes = new ArrayList();
	
	ApplicationFB1 applicationFB1;
	
	public Game(ApplicationFB1 applicationFB1) throws IOException {
		initPhysics();
		this.applicationFB1 = applicationFB1;
		bird = new Bird(this, applicationFB1);
		pipes.add(new PipePair(this, applicationFB1, 0));
		g = applicationFB1.canvas.getGraphics();
		applicationFB1.canvas.setBackground(new Color(195, 195, 255));
		applicationFB1.canvas.repaint();
	}
	
	public void addPipe() {
		pipes.add(new PipePair(this, applicationFB1, Math.min(0.9, Math.max(-0.9, (Math.random()*2-1)*0.5+pipes.get(pipes.size()-1).height))));
	}
	
	private void initPhysics() {
		world = new World(new Vec2(0, -5));
		world.setContinuousPhysics(true);
	}
	
	public void drawGame() {
		g.setColor(new Color(195, 195, 255));
		g.fillRect(0, 0, 1000, 1000);
		g.drawImage(rotateImage(bird.icon, -Math.atan(bird.body.getLinearVelocity().y)/2), (int) (bird.body.getWorldCenter().x*100)-22, (int) (1000-bird.body.getWorldCenter().y*100)-22, null);
		g.setColor(Color.black);
		for (PipePair pipe : pipes) {
			g.drawImage(pipe.top.icon, (int) (pipe.top.body.getWorldCenter().x*100), pipe.top.height, null);
			g.drawImage(pipe.bottom.icon, (int) (pipe.bottom.body.getWorldCenter().x*100), pipe.bottom.height, null);
		}
	}
	
	public void step() {
		world.step(0.5f, 1, 1);
		if (7-pipes.get(pipes.size()-1).top.body.getPosition().x>2.5) {
			addPipe();
		}
		if (pipes.get(0).top.body.getPosition().x<-1) {
			pipes.remove(0);
		}
		drawGame();
	}
	
	
	public boolean test() {
		if (bird.body.getContactList()!=null) {
			if (bird.body.getContactList().contact!=null) {
				if (bird.body.getContactList().contact.isTouching()) {
					return false;
				}
			}
		}
		if (bird.body.getPosition().y>10||bird.body.getPosition().y<0.2) {
			return false;
		}
		return true;
	}
	
	public static BufferedImage rotateImage(BufferedImage image, double angle) {
		double sin = Math.abs(Math.sin(angle));
		double cos = Math.abs(Math.cos(angle));
		int w = (int) Math.floor(image.getWidth() * cos + image.getHeight() * sin);
		int h = (int) Math.floor(image.getHeight() * cos + image.getWidth() * sin);
		BufferedImage rotatedImage = new BufferedImage(w, h, image.getType());
		AffineTransform at = new AffineTransform();
		at.translate(w / 2, h / 2);
		at.rotate(angle,0, 0);
		at.translate(-image.getWidth() / 2, -image.getHeight() / 2);
		AffineTransformOp rotateOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		rotateOp.filter(image,rotatedImage);
		return rotatedImage;
	}
	public static BufferedImage rotateImage(BufferedImage image, int angle) {
		return rotateImage(image, Math.toRadians(angle));
	}
	public static BufferedImage resizeImage(BufferedImage image, int width, int height) {
		BufferedImage resizedImage = new BufferedImage(width, height, image.getType());
		AffineTransform at = new AffineTransform();
		at.translate(width, height);
		AffineTransformOp resizeOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BICUBIC);
		resizeOp.filter(image, resizedImage);
		return resizedImage;
	}
	
}
