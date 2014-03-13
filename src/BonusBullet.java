import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Images.ResourceLoader;


public class BonusBullet extends GameObj {
	//this BonusBullet just starts at the MotherShip and fires straight up, #forvisualeffect :) 
	
	public String img_file = "bonus_bullet.png";
	private Image img;
	
	public int POS_X;
	public int POS_Y;
	
	static int court_width; 
	static int court_height;
	
	public static int VEL_Y = 15;
	
	public static int SIZE = 20;
		
	public static List<BonusBullet> bonus_bullets = new ArrayList<BonusBullet>();
	
	public BonusBullet(int pos_x, int cw, int ch) { 
		super(pos_x, ch * 3 / 4, cw, ch, 0, VEL_Y, 20, 20);
		
		this.POS_X = pos_x;
		this.POS_Y = ch * 3 / 4;
		
		court_width = cw;
		court_height = ch;
				
		if (img_file != null) { 
			img = ResourceLoader.getImage(img_file);
		}
				
	}
	
	@Override
	public void move() {
		if (!hitBorder()) {
			POS_Y -= VEL_Y;
		}
	}
	
	public boolean hitBorder() { 
		if (POS_X < 0 || POS_X > court_width || POS_Y < 0 || POS_Y > court_height) { 
			return true;
		} else {
			return false;
		}
	}
	
	public void draw(Graphics g) {
		if (!hitBorder()) { 
			g.drawImage(img, POS_X, POS_Y - 20, SIZE, SIZE, null);
		}
	}
	/** moved this over to LevelMaker
	public static List<BonusBullet> createBonusList() { 
		bonus_bullets.add(new BonusBullet(0, court_width, court_height));
		bonus_bullets.add(new BonusBullet(court_width / 5, court_width, court_height));
		bonus_bullets.add(new BonusBullet(court_width * 2 / 5, court_width, court_height));
		bonus_bullets.add(new BonusBullet(court_width * 3 / 5, court_width, court_height));
		bonus_bullets.add(new BonusBullet(court_width * 4 / 5, court_width, court_height));
		return bonus_bullets;
	}
	*/
	
	
}
