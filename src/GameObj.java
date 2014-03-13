import java.awt.Graphics;


public class GameObj {
	//Coordinates are given by the upper-left hand corner of the object. This position should always be within bounds.
	//0 <= pos_x <= max_x , 0 <= pos_y <= max_y 
	public int pos_x;
	public int pos_y;
	
	public int max_x;
	public int max_y;
	
	
	public int vel_x;
	public int vel_y;
	
	public int width;
	public int height;

	public boolean existence;
	
	public GameObj(int pos_x, int pos_y, int court_width, int court_height, int vel_x, int vel_y, int width, int height) { 
		this.pos_x = pos_x;
		this.pos_y = pos_y;
		this.max_x = court_width - width;
		this.max_y = court_height - height;
		this.vel_x = vel_x;
		this.vel_y = vel_y;
		this.width = width;
		this.height = height; 
		this.existence = true;
	}
	
	public void draw(Graphics g) { 
		//override
	}
	
	public void move() {
		pos_x += vel_x;
		pos_y += vel_y;
		//could override
		
	}



}
