package flappyBirds.test3;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.util.*;
import java.util.List;

import javax.swing.ImageIcon;

import org.neat.calculate.Calculator;
import org.neat.neat.Neat;


public class Game {
	
	Graphics g;
	
	ArrayList<Bird> birds;
	ArrayList<PipePair> pipes = new ArrayList();
	
	ApplicationFB3 applicationFB3;
	
	Neat neat;
	
	int ticks = 0;
	int score = 0;
	
	public Game(ApplicationFB3 applicationFB3, Neat neat) throws IOException {
		this.neat = neat;
		this.applicationFB3 = applicationFB3;
		birds = new ArrayList<>();
		pipes.add(new PipePair(this, applicationFB3, 0));
		pipes.get(0).posX -= 2.5;
		addPipe();
		pipes.get(0).posX -= 2.5;
		pipes.get(1).posX -= 2.5;
		addPipe();
		pipes.get(0).posX -= 1;
		pipes.get(1).posX -= 1;
		pipes.get(2).posX -= 1;
		g = applicationFB3.canvas.getGraphics();
		applicationFB3.canvas.setBackground(new Color(195, 195, 255));
		applicationFB3.canvas.repaint();
		List<Calculator> calculators = neat.createCalculators();
		for (Calculator calculator : calculators) {
			addBird(calculator);
		}
	}
	
	public void addBird(Calculator calculator) throws IOException {
		birds.add(new Bird(this, applicationFB3, calculator));
	}
	
	public void addPipe() {
		pipes.add(new PipePair(this, applicationFB3, Math.min(2.9, Math.max(-2.9, (Math.random()*2-1)*Math.min(2, (float)ticks/100)+1-4*pipes.get(pipes.size()-1).height))));
		//pipes.add(new PipePair(this, application, 2.9));
	}
	
	public void drawGame() {
		//application.canvas.repaint();
		//g.fillRect(0, 0, 1000, 1000);
		g.setColor(new Color(195, 195, 255));
		g.fillRect(applicationFB3.width-200, 50, 200, 50);
		//g.setColor(Color.red);
		g.fillRect(100-30, 0, 60, 1000);
		for (PipePair pipe : pipes) {
			if (pipe.posX<-0.5|| pipe.posX>7.5) {
				continue;
			}
			g.fillRect((int) (pipe.posX*100)+40, (int)((pipe.height+1-pipe.space)*500)-800, 25, 800);
			g.fillRect((int) (pipe.posX*100)+40, (int)((pipe.height+1+pipe.space)*500), 25, 800);
		}
		g.setColor(Color.black);
		for (Bird bird : birds) {
			if (!bird.alive) {
				continue;
			}
			g.drawImage(rotateImage(bird.icon, -Math.atan(bird.velocityY)/2), 100-20, (int) (1000-bird.posY*100)-20, null);
			g.fillOval(100-4, (int)(1000-bird.posY*100)-4, 8, 8);
		}
		for (PipePair pipe : pipes) {
			/*g.drawImage(pipe.top.icon, (int) (pipe.posX*100), pipe.top.height+100, null);
			g.drawImage(pipe.bottom.icon, (int) (pipe.posX*100), pipe.bottom.height+100, null);*/
			g.drawImage(pipe.top.icon, (int)(pipe.posX*100), (int)((pipe.height+1-pipe.space)*500)-pipe.top.icon.getHeight(null), null);
			g.drawImage(pipe.bottom.icon, (int)(pipe.posX*100), (int)((pipe.height+1+pipe.space)*500), null);
		}
		g.setColor(Color.red);
		Font font = new Font("Times New Roman", Font.BOLD, 30);
		g.setFont(font);
		g.drawString("Score: "+score, applicationFB3.width-200, 100);
		g.drawString("Generation: "+applicationFB3.generation, applicationFB3.width-200, 120);
	}
	
	public void step(double factor) {
		for (PipePair pipePair : pipes) {
			pipePair.posX -= factor;
		}
		if (8-pipes.get(pipes.size()-1).posX>2.5) {
			addPipe();
		}
		if (pipes.get(0).posX<-1) {
			pipes.remove(0);
			score++;
		}
		for (Bird bird : birds) {
			if (!bird.alive) {
				continue;
			}
			bird.posY += bird.velocityY*factor-Math.pow(factor, 2);
			bird.velocityY -= 2*factor;
			if (bird.velocityY>0) {
				bird.velocityY -= 56*factor;
			}
			bird.velocityY = Math.max(bird.velocityY, -100);
		}
		ticks++;
	}
	
	public void stepBirds() {
		/*PipePair pipePair1 = pipes.get(0);
		PipePair pipePair2 = pipes.get(1);
		for (Bird bird : birds) {
			//bird.tick(pipePair1, pipePair2);
		}*/
		PipePair pipePair;
		if (pipes.get(0).posX>0) {
			pipePair = pipes.get(0);
		}
		else {
			pipePair = pipes.get(1);
		}
		for (Bird bird : birds) {
			bird.tick(pipePair);
		}
	}
	
	
	public boolean test(Bird bird) {
		/*for (PipePair pipePair : pipes) {
			if (pipePair.posX<1&&pipePair.posX>0.9) {
				if (pipePair.top.size*10<bird.posY) {
					return false;
				}
				if (pipePair.bottom.size*10>bird.posY) {
					return false;
				}
			}
		}
		if (bird.posY>10||bird.posY<0.2) {
			return false;
		}
		return true;*/
		for (PipePair pipePair : pipes) {
			if (pipePair.posX<1&&pipePair.posX>0.5) {
				if ((pipePair.height+1+pipePair.space)*5<10-bird.posY) {
					return false;
				}
				if ((pipePair.height+1-pipePair.space)*5>10-bird.posY) {
					return false;
				}
			}
		}
		if (bird.posY>10||bird.posY<0.2) {
			return false;
		}
		return true;
	}
	
	public void testAll() {
		for (Bird bird : birds) {
			bird.test();
		}
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
