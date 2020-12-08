package flappyBirds.test2;

public class PipePair {
	
	Game game;
	ApplicationFB2 applicationFB2;
	
	double space = 0.09;
	
	Pipe top;
	Pipe bottom;
	double height;
	double posX = 7;
	
	public PipePair(Game game, ApplicationFB2 applicationFB2, double height) {
		this.game = game;
		this.applicationFB2 = applicationFB2;
		this.height = -height/4;
		createPipes();
	}
	
	public void createPipes() {
		this.height = height;
		top = new Pipe(game, applicationFB2, 0.5-height+space/2, false);
		bottom = new Pipe(game, applicationFB2, height+0.5+space/2, true);
	}
	
}
