package flappyBirds.test1;

import java.awt.*;

import javax.swing.*;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.*;
import org.jbox2d.dynamics.*;

public class Pipe {
	
	public double size = 0.5;
	public boolean bottom = false;
	public int height;
	
	Game game;
	ApplicationFB1 applicationFB1;
	Image icon;
	
	Body body;
	
	public Pipe(Game game, ApplicationFB1 applicationFB1, double size, boolean bottom) {
		this.bottom = bottom;
		this.game = game;
		this.applicationFB1 = applicationFB1;
		if (bottom) {
			Image icon = new ImageIcon("resources/pipeBottom.png").getImage();
			this.icon = (new ImageIcon(icon.getScaledInstance(50, (new ImageIcon("resources/pipeBottom.png")).getIconHeight()/2, Image.SCALE_SMOOTH))).getImage();
			height = (int) (applicationFB1.height*size);
		}
		else {
			Image icon = new ImageIcon("resources/pipeTop.png").getImage();
			this.icon = (new ImageIcon(icon.getScaledInstance(50, (new ImageIcon("resources/pipetop.png")).getIconHeight()/2, Image.SCALE_SMOOTH))).getImage();
			height = (int) (applicationFB1.height*size);
			height = applicationFB1.height-height-(new ImageIcon("resources/pipeTop.png")).getIconHeight()/2;
		}
		BodyDef bd = new BodyDef();
		bd.setGravityScale(0);
		bd.setType(BodyType.KINEMATIC);
		bd.setPosition(new Vec2(7, 10-((float)height)/100));
		body = game.world.createBody(bd);
		PolygonShape shape = new PolygonShape();
		if (bottom) {
			shape.set(new Vec2[] {new Vec2(0, 0), new Vec2(0.5f, 0), new Vec2(0.5f, -10), new Vec2(0, -10)}, 4);
		}
		else {
			shape.set(new Vec2[] {new Vec2(0, 0), new Vec2(0.5f, 0), new Vec2(0.5f, -10), new Vec2(0, -10)}, 4);
		}
		FixtureDef fixture = new FixtureDef();
		fixture.setShape(shape);
		body.createFixture(fixture);
		body.setLinearVelocity(new Vec2(-1f, 0));
	}
	
	public void destroy() {
		body.destroyFixture(body.getFixtureList());
		game.world.destroyBody(body);
	}
	
}
