import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import Images.ResourceLoader;


public class Shootable extends GameObj {
/** An object that appears from the top of screen and comes at the mothership. 
 * 
 * It has position, velocity, size, an x-coord from where it appears, 
 * 	
 */
	//Coordinates are given by the upper-left hand corner of the object. This position should always be within bounds.
	//0 <= pos_x <= max_x , 0 <= pos_y <= max_y 
	public String img_file;
	private Image img;

	/**change later*/
	public String explode_file = "explode.png";
	private Image explode_img;
	
	//this should be an integer from 1 to 5
	public int shot_from_position;
	public int shot_from_x;
	public int POS_Y = 0;

	public int VEL_X = 0;
	public int VEL_Y;
	
	public static int WIDTH = 50;
	public static int HEIGHT = 50;
	
	//this is the word that is under the Shootable 
	//that must be typed for the bullet to destroy it.
	public String word;
	
	public boolean hit_ship;
	public boolean approach_bullet;
	public boolean hit_bullet;
	
	//public int msecs; //this is when the shootable should come in, after how many milliseconds (seconds * 1000)
			
	//this is the tickCount for how long the explosion is shown. 1000 milliseconds is the max.
	public int explodeTickCount;
	
	//lowest tip of the shootable object, accounting the height and word height
	private int lowest_y;
	
	private int text_width;
	
	public Shootable(String word, String img_file, int x, int y, int y_vel, int court_width, int court_height) {
		super(x, y, court_width, court_height, 0, y_vel, WIDTH, HEIGHT);
		
		this.shot_from_position = x;
		this.shot_from_x = (court_width / 5) * (shot_from_position - 1);
		
		this.POS_Y = y;
		this.VEL_Y = y_vel;
		this.word = word;
		this.hit_ship = false;
		this.approach_bullet = false;
		this.img_file = img_file;
		this.explodeTickCount = 0;
		this.hit_bullet = false; 
		this.existence = false;
				
		if (img == null) { 
			img = ResourceLoader.getImage(img_file);
			explode_img = ResourceLoader.getImage(explode_file);
			}
	} 
	
	@Override
	public void move() { 
		if (!this.hit_bullet && !this.hit_ship) {
			POS_Y += VEL_Y;
			this.lowest_y = POS_Y + HEIGHT; 
			hitShip();
		} else { 
			this.existence = false;
		}
		/** I don't know if this is necessary, but I have handle "winning" and "losing" in some way.
		if (super.hitShip(this)) { 
			//disappear? Should this be in GameCourt instead?
			GameArena.lost_yet = true;
		}*/
		
		/**if hitsMotherShip(MotherShip ship) { 
			[disappear?]
			hit_ship = true;
		 */
	}
	
	@Override 
	public void draw(Graphics g) { 
		//must be a pic of something with the label underneath... maybe just save the whole thing as an image
		if (!this.hit_bullet && !this.hit_ship) { 
			g.drawImage(img, shot_from_x, POS_Y, WIDTH, HEIGHT, null);
			g.setColor(Color.BLACK);
			for (int y = (POS_Y + HEIGHT - 2); y < lowest_y + 14; y++) { 
				g.drawLine(shot_from_x, y, shot_from_x + WIDTH, y);
			}
			g.setColor(Color.WHITE);
			FontMetrics fm = g.getFontMetrics(); 
			this.text_width = fm.stringWidth(word);
			g.drawString(word, shot_from_x + (WIDTH - text_width) / 2, lowest_y + 10); //draws word right under the image
			//draw vpair of string and label? or just draw the string on the label?
			if (approach_bullet == true) { 
				g.setColor(Color.RED);
			}					
		} else {
			g.drawImage(explode_img, shot_from_x, POS_Y, WIDTH, HEIGHT, null);
			this.explodeTickCount += 20;
		}
		
	}
	
	
	//changes the explode boolean in MotherShip
	public void hitShip() { 
		if (lowest_y >= MotherShip.SHIP_Y)
			if (HealthMeter.left > 0){ 
				HealthMeter.left --;
				this.hit_ship = true;
				ScoreMeter.score -= 500;
			} else { 
				MotherShip.explode = true;
		}
	}	
}
