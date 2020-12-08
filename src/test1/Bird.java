package flappyBirds.test1;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.MassData;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

public class Bird {
	
	public int score = 0;
	public double speed = 0;
	
	Game game;
	ApplicationFB1 applicationFB1;
	BufferedImage icon;
	
	Body body;
	
	public Bird(Game game, ApplicationFB1 applicationFB1) throws IOException {
		this.game = game;
		this.applicationFB1 = applicationFB1;
		icon = ImageIO.read(new File("resources/bird.png"));
		BodyDef bd = new BodyDef();
		bd.setGravityScale(1.0f);
		bd.type = BodyType.DYNAMIC;
		bd.position = new Vec2(1.0f, 5f);
		body = game.world.createBody(bd);
		CircleShape shape = new CircleShape();
		shape.setRadius(0.12f);
		FixtureDef fixture = new FixtureDef();
		fixture.setShape(shape);
		MassData massData = new MassData();
		massData.mass = 0;
		body.setMassData(massData);
		body.createFixture(fixture);
		body.setLinearVelocity(new Vec2(01, 0));
		System.out.println(body.getWorldCenter());
	}
	
}
