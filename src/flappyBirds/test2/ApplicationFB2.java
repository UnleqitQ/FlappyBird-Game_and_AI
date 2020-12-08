package flappyBirds.test2;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.*;

public class ApplicationFB2 {
	
	public JFrame frame;
	public GameCanvas canvas;
	public Game game;
	public int width = 750;
	public int height = 1000;
	private boolean go;
	
	public static void main(String[] args) {
		ApplicationFB2 applicationFB2 = new ApplicationFB2();
	}
	
	public ApplicationFB2() {
		initialize();
		canvas = new GameCanvas(this);
		frame.add(canvas);
		go = false;
		try {
			game = new Game(this);
		}
		catch (IOException e1) {
			e1.printStackTrace();
		}
		canvas.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (!go) {
					go = true;
				}
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					game.bird.velocityY = 5;
				}
			}
		});
		game.drawGame();
		while (!go) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		while (true) {
			//System.out.println(game.bird.body.getContactList());
			game.step(0.01);
			if (!game.test()) {
				//break;
			}
			try {
				Thread.sleep(10);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void initialize() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(0, 0, width, height);
		frame.setUndecorated(true);
		frame.setVisible(true);
	}
	
}
