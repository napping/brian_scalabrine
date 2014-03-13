import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Images.ResourceLoader;


public class MotherShip extends GameObj {
	
	private Image img;

	public Image explode_img;

	public static final int SHIP_X = 0;
	public static int SHIP_Y; //the y coordinate of the top of the mothership
	
	public int WIDTH;
	public static int HEIGHT;
	
	public int COURT_HEIGHT;
	
	public static boolean explode;
	
	public MotherShip(int court_width, int court_height) {
		super(SHIP_X, ((court_height * 3) / 4), court_width, court_height, 0, 0, court_width, (court_height / 5));
		
		SHIP_Y = (court_height * 3) / 4;
		
		this.WIDTH = court_width;
		HEIGHT = court_height / 4;
		
		this.COURT_HEIGHT = court_height;
		
		explode = false;

		img = ResourceLoader.getImage("ship.png");
		explode_img = ResourceLoader.getImage("explode.png");
	}
	
	//draws the unmoving mothership when playing, and when enemy hits it
	//it draws the exploded image
	@Override
	public void draw(Graphics g) { 
		if (!explode) {
			g.drawImage(img, SHIP_X, SHIP_Y, WIDTH, HEIGHT, null);			
		} else { 
			g.drawImage(explode_img, SHIP_X, SHIP_Y, WIDTH, HEIGHT, null);
		}
	}
}
