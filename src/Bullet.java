import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Images.ResourceLoader;


public class Bullet extends GameObj {
	//must do calculations to get what direction to shoot

	public String img_file = "bullet.png";
	private Image img;
	
	public int POS_X; 
	public int POS_Y; 
	
	public int VEL_X = 0;
	public int VEL_Y = 0;
	
	public static int SIZE = 20;
	
	public int COURT_WIDTH;
	public int COURT_HEIGHT;
	
    public boolean target;

    public int approachTickCount;
    
    
    //the Shootable that this bullet will target
    public Shootable enemy;
	
	public Bullet(Shootable enemy, int court_width, int court_height, boolean missed) {
		super(0, court_height * 3 / 4, court_width, court_height, 0, 0, SIZE, SIZE);
		this.target = true; //i don't know if this is right, what should it start with?
		
		//places the starting position of the bullet to be right at the 
		//top center of the MotherShip
		this.POS_X = court_width / 2 - 10;
		this.POS_Y = court_height * 3 / 4;

		this.enemy = enemy;
		
		
		getVel();
		
		if (img_file != null) { 
			img = ResourceLoader.getImage(img_file); 
		}
		
	}
		
	//Takes in the Shootable target and assigns VEL_X and VEL_Y
	//to guide the bullet to hit the Shootable in a quarter of a second.
	


	public void getVel() { 	
		int target_x = enemy.shot_from_x;
		int target_y = enemy.POS_Y;
			VEL_X = (target_x - POS_X) / 7;
			VEL_Y = (target_y - POS_Y) / 7;
	}
	
	
	@Override
	public void move() { 
		if (!hitBorder() && !enemy.hit_bullet) {	
			POS_X += VEL_X;
			POS_Y += VEL_Y;
			approachTickCount++;
			if (approachTickCount > 9) { 
				enemy.hit_bullet = true;
				enemy.existence = false;
			} else { 
				this.existence = false;
			}
		}
	}	
	
	public boolean hitBorder() { 
		if (pos_x < 0 || pos_x > max_x || pos_y < 0 || pos_y > max_y) { 
			return true;
		} else {
			return false;
		}
	}

	/**TEST*/
	@Override
	public void draw(Graphics g) { 
		if (!hitBorder() && approachTickCount < 7) { 
			g.drawImage(img, POS_X, POS_Y, SIZE, SIZE, null);
		}
	}
	
}
