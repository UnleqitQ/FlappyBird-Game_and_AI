package flappyBirds.test3;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.*;
import org.neat.*;
import org.neat.activationFunction.ActivationArcTan;
import org.neat.activationFunction.ActivationFunction;
import org.neat.gene.ConnectionGene;
import org.neat.gene.NodeGene;
import org.neat.genome.Genome;
import org.neat.neat.*;

public class ApplicationFB3 {
	
	public JFrame frame;
	public JFrame netFrame;
	public GameCanvas canvas;
	public NetCanvas netCanvas;
	public Game game;
	public int width = 750;
	public int height = 1000;
	private boolean go;
	Neat neat;
	public boolean show;
	public boolean jump = false;
	public int sizeNode = 40;
	public int generation = 0;
	public double n = 20;
	double sleepDelay = 10;
	
	Map<String, Image> activationIcons;
	
	public static void main(String[] args) {
		ApplicationFB3 applicationFB3 = new ApplicationFB3();
	}
	
	public ApplicationFB3() {
		System.out.println("Start");
		int calcSteps = 1;
		System.out.println("Init Icons");
		initIcons();
		System.out.println("Init Wins");
		initialize();
		System.out.println("Create Neat");
		neat = new Neat(4, 1, true);
		System.out.println("Config Neat");
		neat.config.initGenome.fullMesh = true;
		neat.config.crossover.takeSecondNode = 0.2f;
		neat.config.crossover.takeSecondConnection = 0.1f;
		neat.config.crossover.thinConnections = true;
		neat.config.crossover.thinViaPercentage = true;
		neat.config.crossover.thinProbOrPerc = 0.02f;
		neat.config.mutate.addExistingNode = true;
		neat.config.mutate.averageAddNodesPerMutate = 0.1f;
		neat.config.mutate.averageAddExistingNodesPerMutate = 0.075f;
		neat.config.mutate.averageAddConnectionsPerMutate = 0.25f;
		neat.config.mutate.averageToggleNodesPerMutate = 0.1f;
		neat.config.mutate.averageToggleConnectionsPerMutate = 0.15f;
		neat.config.mutate.nodeActivation = true;
		neat.config.mutate.genomeActivation = true;
		neat.config.mutate.averageNewNodesActivationPerMutate = 0.05f;
		neat.config.mutate.genomeActivationProb = 0.025f;
		neat.config.mutate.hasWeightBorders = true;
		neat.config.mutate.weightBorders = 1;
		neat.config.species.stagnationDuration = 10;
		neat.activationFunctions.add(ActivationFunction.Clamped);
		neat.activationFunctions.add(ActivationFunction.Gauss);
		neat.activationFunctions.add(ActivationFunction.Hat);
		neat.activationFunctions.add(ActivationFunction.Inverted);
		neat.activationFunctions.add(ActivationFunction.ArcTan);
		neat.activationFunctions.add(ActivationFunction.Step);
		neat.activationFunctions.add(ActivationFunction.SiLU);
		neat.activationFunctions.add(ActivationFunction.Sin);
		neat.activationFunctions.add(ActivationFunction.Softplus);
		System.out.println("Init Canvs");
		canvas = new GameCanvas(this);
		frame.add(canvas);
		netCanvas = new NetCanvas(this);
		netFrame.add(netCanvas);
		go = true;
		show = true;
		System.out.println("Create KeyListener");
		KeyListener keyListener = new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (!go) {
					go = true;
				}
				if (e.getKeyCode()==KeyEvent.VK_P) {
					show = !show;
				}
				if (e.getKeyCode()==KeyEvent.VK_X) {
					if (game.ticks<1000) {
						return;
					}
					jump = true;
				}
				if (e.getKeyCode()==KeyEvent.VK_R) {
					canvas.repaint();
				}
				if (e.getKeyCode()==KeyEvent.VK_LEFT) {
					if (n<80000) {
						n*=2;
					}
				}
				if (e.getKeyCode()==KeyEvent.VK_RIGHT) {
					if (n>2) {
						n/=2;
					}
				}
				if (e.getKeyCode()==KeyEvent.VK_MINUS) {
					if (sleepDelay<200000) {
						sleepDelay*=2;
					}
				}
				if (e.getKeyCode()==KeyEvent.VK_PLUS) {
					if (sleepDelay>2) {
						sleepDelay/=2;
					}
				}
			}
		};
		System.out.println("add Listener");
		canvas.addKeyListener(keyListener);
		frame.addKeyListener(keyListener);
		netFrame.addKeyListener(keyListener);
		netCanvas.addKeyListener(keyListener);
		while (!go) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		System.out.println("Init Neat");
		neat.initialize();
		System.out.println("Create 10 Genomes");
		neat.createGenomes(10);
		System.out.println("Mutate");
		neat.mutate();
		System.out.println("Randomly breed 40 Genomes");
		neat.breedrand(40);
		System.out.println("Mutate");
		neat.mutate();
		//neat.breedrand(30);
		//neat.mutate();
		System.out.println("Init Specicate");
		neat.initSpecicate();
		System.out.println("Specicate0");
		neat.specicate();
		neat.realignSpeciesRepresentative();
		neat.clearGenomesOfSpecies();
		neat.specicate();
		System.out.println("Specicate0 Done");
		while (true) {
			try {
				generation++;
				System.out.println("Init Game");
				game = new Game(this, neat);
				boolean run = true;
				while (run) {
					if (jump) {
						break;
					}
					run = false;
					for (Bird bird : game.birds) {
						if (bird.alive) {
							run = true;
							break;
						}
					}
					//System.out.println("Test if Birds Alive");
					game.testAll();
					/*for (int k = 0; k < calcSteps; k++) {
						game.step((double)sleepDelay/(double)calcSteps/1000);
					}*/
					//System.out.println("Tick Game");
					game.step(0.01);
					game.stepBirds();
					if (show&&Math.floorMod(game.ticks,1)==0) {
						//System.out.println("Draw Game");
						game.drawGame();
					}
					if (show&&Math.floorMod(game.ticks,(int) (sleepDelay*100))==0) {
						//canvas.repaint();
					}
					if (Math.floorMod(game.ticks,(int) (1*n))==0) {
						//System.out.println("Show best Genome Net");
						neat.sortGenomes();
						drawNet(neat.genomes.getList().get(0), 0, 0, 1, 1);
					}
					if (show) {
						try {
							Thread.sleep((long) sleepDelay);
						}
						catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				jump = false;
				System.out.println();
				System.out.println("Generation: "+generation);
				System.out.println("Score: "+game.score);
				System.out.println();
				//neat.clearSavedGenomes();
				neat.calcSpeciesFitness();
				neat.addSpeciesFitnessToHist();
				neat.sortGenomes();
				neat.sortSpecies();
				System.out.println("Cut Species");
				neat.cutGenomesInSpecies(0.7f);
				System.out.println("Stagnate");
				neat.stagnate(0.2f, 0.2f);
				//neat.saveGenomesCopy();
				System.out.println("Breed");
				neat.breedSpeciesUpTo(50);
				System.out.println("Mutate");
				neat.mutate();
				//neat.loadGenomesCopyNew();
				System.out.println("Specicate");
				neat.clearGenomesOfSpecies();
				neat.specicate();
				neat.removeEmptySpecies();
				//neat.specicate();
				neat.sortGenomes();
				drawNet(neat.genomes.getList().get(0), 0, 0, 1, 1);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void initIcons() {
		activationIcons = new HashMap<>();
		addToMap("Abs");
		addToMap("ArcTan");
		addToMap("Clamped");
		addToMap("Cube");
		addToMap("ELU");
		addToMap("Exp");
		addToMap("Gauss");
		addToMap("Hat");
		addToMap("Identity");
		addToMap("Inverted");
		addToMap("LeakyReLU");
		addToMap("Log");
		addToMap("ReLU");
		addToMap("Sigmoid");
		addToMap("SiLU");
		addToMap("Sin");
		addToMap("Softplus");
		addToMap("Square");
		addToMap("Step");
		addToMap("Tanh");
	}
	private void addToMap(String name) {
		activationIcons.put("Activation"+name, (new ImageIcon("./resources/Activations/"+name+".png")).getImage().getScaledInstance(sizeNode, sizeNode, Image.SCALE_SMOOTH));
	}
	
	private void initialize() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(0, 0, width, height);
		frame.setUndecorated(true);
		frame.setVisible(true);
		netFrame = new JFrame();
		netFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//netFrame.setBounds(width+50, 0, 1100, 1000);
		netFrame.setBounds(0, 0, 1920, 1080);
		netFrame.setUndecorated(true);
		//netFrame.setAlwaysOnTop(true);
		netFrame.setVisible(true);
	}
	
	private void drawNet(Genome genome, int pX, int pY, int nX, int nY) {
		//netCanvas.repaint();
		int dist = 200;
		try {
			Thread.sleep(2);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		//System.out.print(ActivationArcTan.class.getSimpleName());
		Graphics2D g = (Graphics2D) netCanvas.getGraphics();
		g.setColor(Color.white);
		int startX = (int) (netCanvas.getWidth()*(float)pX/(float)nX);
		int startY = (int) (netCanvas.getWidth()*(float)pX/(float)nX);
		g.fillRect(startX, startY, netCanvas.getWidth()/nX, netCanvas.getHeight()/nY);
		if (genome == null) {
			return;
		}
		Map<Float, List<NodeGene>> nodesByX = genome.nodes.getNodesByX();
		List<ConnectionGene> connections = genome.connections.getList();
		g.setStroke(new BasicStroke(4));
		for (Entry<Float, List<NodeGene>> layerEntry : nodesByX.entrySet()) {
			for (NodeGene node : layerEntry.getValue()) {
				if (node.enabled) {
					g.setColor(Color.black);
				}
				else {
					g.setColor(Color.gray);
				}
				Image icon = activationIcons.get(node.activation.getClass().getSimpleName());
				//icon = icon.getScaledInstance(sizeNode/nX, sizeNode/nY, Image.SCALE_SMOOTH);
				int posX = (int) ((float)netCanvas.getWidth()*((float)layerEntry.getKey()*0.9+0.05))/nX+startX;
				int posY = (int) ((float)netCanvas.getHeight()*((float)layerEntry.getValue().indexOf(node)/(float)layerEntry.getValue().size()*0.9+0.05))/nY+startY;
				//g.fillOval(posX-10, posY-10, 20, 20);
				g.drawImage(icon, posX-sizeNode/2/nX, (int)(posY-(sizeNode/2-dist*Math.pow(layerEntry.getKey(), 2))/nX), null);
				g.drawImage(icon, posX-sizeNode/2/nX, (int)(posY-(sizeNode/2-dist*Math.pow(layerEntry.getKey(), 2))/nY), null);
				g.drawOval(posX-sizeNode/2/nX, (int)(posY-sizeNode/2+dist*Math.pow(layerEntry.getKey(), 2))/nY, sizeNode/nX, sizeNode/nY);
				//System.out.println(node);
			}
		}
		for (ConnectionGene connectionGene : connections) {
			if (connectionGene.weight>0) {
				g.setColor(Color.blue);
				if (!connectionGene.enabled) {
					g.setColor(new Color(0, 255, 255));
				}
			}
			else {
				g.setColor(Color.red);
				if (!connectionGene.enabled) {
					g.setColor(new Color(255, 255, 0));
				}
			}
			g.setStroke(new BasicStroke(2*(float) Math.ceil(10*Math.abs(connectionGene.weight*4))/10));
			if (connectionGene.weight==0) {
				if (connectionGene.enabled) {
					g.setColor(Color.black);
				}
				else {
					g.setColor(Color.gray);
				}
				g.setStroke(new BasicStroke(1));
			}
			NodeGene from = connectionGene.fromGene;
			NodeGene to = connectionGene.toGene;
			List<NodeGene> fromLayer = nodesByX.get(from.x);
			List<NodeGene> toLayer = nodesByX.get(to.x);
			int posX1 = (int) ((float)netCanvas.getWidth()*(from.x*0.9+0.05))/nX+startX;
			int posX2 = (int) ((float)netCanvas.getWidth()*(to.x*0.9+0.05))/nX+startX;
			int posY1 = (int) ((float)netCanvas.getHeight()*((float)fromLayer.indexOf(from)/(float)fromLayer.size()*0.9+0.05))/nY+startY;
			int posY2 = (int) ((float)netCanvas.getHeight()*((float)toLayer.indexOf(to)/(float)toLayer.size()*0.9+0.05))/nY+startY;
			g.drawLine(posX1+sizeNode/2, (int)(posY1+dist*Math.pow(from.x, 2)), posX2-sizeNode/2, (int)(posY2+dist*Math.pow(to.x, 2)));
			//System.out.println(connectionGene);
		}
		g.setStroke(new BasicStroke(4));
		for (Entry<Float, List<NodeGene>> layerEntry : nodesByX.entrySet()) {
			for (NodeGene node : layerEntry.getValue()) {
				if (node.enabled) {
					g.setColor(Color.black);
				}
				else {
					g.setColor(Color.gray);
				}
				Image icon = activationIcons.get(node.activation.getClass().getSimpleName());
				//icon = icon.getScaledInstance(sizeNode/nX, sizeNode/nY, Image.SCALE_SMOOTH);
				int posX = (int) ((float)netCanvas.getWidth()*((float)layerEntry.getKey()*0.9+0.05))/nX+startX;
				int posY = (int) ((float)netCanvas.getHeight()*((float)layerEntry.getValue().indexOf(node)/(float)layerEntry.getValue().size()*0.9+0.05))/nY+startY;
				//g.fillOval(posX-10, posY-10, 20, 20);
				g.drawImage(icon, posX-sizeNode/2/nX, (int)(posY-(sizeNode/2-dist*Math.pow(layerEntry.getKey(), 2))/nX), null);
				g.drawImage(icon, posX-sizeNode/2/nX, (int)(posY-(sizeNode/2-dist*Math.pow(layerEntry.getKey(), 2))/nY), null);
				g.drawOval(posX-sizeNode/2/nX, (int)(posY-sizeNode/2+dist*Math.pow(layerEntry.getKey(), 2))/nY, sizeNode/nX, sizeNode/nY);
				//System.out.println(node);
			}
		}
		g.setColor(Color.red);
		Font font = new Font("Times New Roman", Font.BOLD, 30);
		g.setFont(font);
		if (game!=null) {
			g.drawString("Score: "+game.score, 1920-200, 50);
		}
		g.drawString("Generation: "+generation, 1920-200, 70);
		font = new Font("Times New Roman", Font.PLAIN, 20);
		g.setFont(font);
		g.drawString("In-/Decrease Frame Rate: +/-", 1920-400, 90);
		g.drawString("In-/Decrease Net Rate: left/right", 1920-400, 110);
		g.drawString("Show Game: P", 1920-400, 130);
		g.drawString("Repaint Canvas: R", 1920-400, 150);
		g.drawString("New Gen: X", 1920-400, 170);
	}
	
}
