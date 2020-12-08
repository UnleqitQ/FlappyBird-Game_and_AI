package flappyBirds.test3;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.*;

import org.neat.calculate.Calculator;


public class Bird {
	
	public int score = 0;
	public double speed = 0;
	public double posY = 4.55;
	public double velocityY = 0;
	
	Game game;
	ApplicationFB3 applicationFB3;
	BufferedImage icon;
	Calculator calculator;
	boolean alive = true;
	
	public Bird(Game game, ApplicationFB3 applicationFB3, Calculator calculator) throws IOException {
		calculator.genome.fitness = 0;
		this.game = game;
		this.applicationFB3 = applicationFB3;
		this.calculator = calculator;
		icon = ImageIO.read(new File("./resources/bird.png"));
	}
	
	/*public void tick(PipePair pipePair1/, PipePair pipePair2/) {
		Map<Long, Float> inputs = new HashMap<>();
		inputs.put(0L, (float) posY);
		inputs.put(1L, (float) velocityY);
		inputs.put(2L, (float) pipePair1.posX);
		inputs.put(3L, (float) pipePair1.height);
		//inputs.put(4L, (float) pipePair2.posX);
		//inputs.put(5L, (float) pipePair2.height);
		calculator.setInputValues(inputs);
		Map<Long, Float> outputs = calculator.getOutputs();
		if (outputs.get(4L)>0.5) {
			velocityY = 5;
		}
	}*/
	public void tick(PipePair pipePair1/*, PipePair pipePair2*/) {
		Map<Long, Float> inputs = new HashMap<>();
		inputs.put(0L, (float) posY);
		inputs.put(1L, (float) velocityY);
		inputs.put(2L, (float) (pipePair1.posX-1)*100);
		inputs.put(3L, (float) (1000-posY*100-(pipePair1.height+1+pipePair1.space)*500));
		//inputs.put(4L, (float) pipePair2.posX);
		//inputs.put(5L, (float) pipePair2.height);
		calculator.setInputValues(inputs);
		Map<Long, Float> outputs = calculator.getOutputs();
		if (outputs.get(4L)>0.5) {
			velocityY = 5;
		}
	}
	
	public void test() {
		if (!alive) {
			return;
		}
		calculator.genome.fitness = (float) Math.pow(game.ticks/1000, 2);
		if (!game.test(this)) {
			alive = false;
		}
	}
	
}
