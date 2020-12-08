package flappyBirds.test1;

public class PipePair {
	
	Game game;
	ApplicationFB1 applicationFB1;
	
	double space = 0.045;
	
	Pipe top;
	Pipe bottom;
	double height;
	
	public PipePair(Game game, ApplicationFB1 applicationFB1, double height) {
		this.game = game;
		this.applicationFB1 = applicationFB1;
		this.height = -height/2;
		createPipes();
	}
	
	public void createPipes() {
		this.height = height;
		top = new Pipe(game, applicationFB1, 0.5-height+space, false);
		bottom = new Pipe(game, applicationFB1, height+0.5+space, true);
	}
	
	public void setPosX(int value) {
		int posX = value;
		top.body.getPosition().x = posX;
		bottom.body.getPosition().x = posX;
	}
	
	public void moveLeft(int pixels) {
		setPosX((int) (top.body.getPosition().x-pixels));
	}
	
}
