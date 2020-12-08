package flappyBirds.test2;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.util.*;

import javax.swing.ImageIcon;


public class Game {
	
	Graphics g;
	
	Bird bird;
	ArrayList<PipePair> pipes = new ArrayList();
	
	ApplicationFB2 applicationFB2;
	
	public Game(ApplicationFB2 applicationFB2) throws IOException {
		this.applicationFB2 = applicationFB2;
		bird = new Bird(this, applicationFB2);
		pipes.add(new PipePair(this, applicationFB2, 0));
		g = applicationFB2.canvas.getGraphics();
		applicationFB2.canvas.setBackground(new Color(195, 195, 255));
		applicationFB2.canvas.repaint();
	}
	
	public void addPipe() {
		pipes.add(new PipePair(this, applicationFB2, Math.min(0.9, Math.max(-0.9, (Math.random()*2-1)*0.5+pipes.get(pipes.size()-1).height))));
	}
	
	public void drawGame() {
		g.setColor(new Color(195, 195, 255));
		g.fillRect(0, 0, 1000, 1000);
		g.drawImage(rotateImage(bird.icon, -Math.atan(bird.velocityY)/2), 100-20, (int) (1000-bird.posY*100)-20, null);
		g.setColor(Color.black);
		if (!test()) {
			g.setColor(Color.red);
		}
		for (PipePair pipe : pipes) {
			g.drawImage(pipe.top.icon, (int) (pipe.posX*100), pipe.top.height, null);
			g.drawImage(pipe.bottom.icon, (int) (pipe.posX*100), pipe.bottom.height, null);
		}
		g.fillOval(100-4, (int)(1000-bird.posY*100)-4, 8, 8);
	}
	
	public void step(double factor) {
		for (PipePair pipePair : pipes) {
			pipePair.posX -= factor;
		}
		bird.posY += bird.velocityY*factor;
		bird.velocityY -= 2*factor;
		if (bird.velocityY>0) {
			bird.velocityY -= 56*factor;
		}
		bird.velocityY = Math.max(bird.velocityY, -100);
		if (7-pipes.get(pipes.size()-1).posX>2.5) {
			addPipe();
		}
		if (pipes.get(0).posX<-1) {
			pipes.remove(0);
		}
		drawGame();
	}
	
	
	public boolean test() {
		for (PipePair pipePair : pipes) {
			if (pipePair.posX<1&&pipePair.posX>0.5) {
				if ((pipePair.height+1+pipePair.space)*5<bird.posY) {
					return false;
				}
				if ((pipePair.height+1-pipePair.space)*5>bird.posY) {
					return false;
				}
			}
		}
		if (bird.posY>10||bird.posY<0.2) {
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
