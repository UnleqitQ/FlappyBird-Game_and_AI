package flappyBirds.test3;

public class PipePair {
	
	Game game;
	ApplicationFB3 applicationFB3;
	
	double space = 0.09;
	
	Pipe top;
	Pipe bottom;
	double height;
	double posX = 8;
	
	public PipePair(Game game, ApplicationFB3 applicationFB3, double height) {
		this.game = game;
		this.applicationFB3 = applicationFB3;
		this.height = -height/4;
		createPipes();
	}
	
	public void createPipes() {
		this.height = height;
		//top = new Pipe(game, application, 0.5-height+space, false);
		//bottom = new Pipe(game, application, height+0.5+space, true);
		top = new Pipe(game, applicationFB3, 0.5-height+space/2, false);
		bottom = new Pipe(game, applicationFB3, height+0.5+space/2, true);
	}
	
}
