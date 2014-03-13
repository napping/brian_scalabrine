import java.awt.Color;
import java.awt.Graphics;


public class HitMeter extends GameObj {

	public int POS_X;
	public int POS_Y;
	
	public int WIDTH;
	public static int HEIGHT = MotherShip.HEIGHT / 8;
	
	public int STRING_X;
	public int STRING_Y;
	
	public static int tickCount;
	public static boolean hit;
	
	public HitMeter(int court_width, int court_height, boolean left) { 
		super(0, court_height * 3 / 4, court_width, court_height, 0, 0, court_width / 12, HEIGHT);
		if (left == true) {
			this.POS_X = court_width * 2 / 5;
		} else { 
			//there are two hit meters that surround the gun.  #forvisualeffect
			this.POS_X = court_width * 42 / 80;
		}
		this.POS_Y = court_height * 3 / 4 + 20;
		
		this.WIDTH = court_width / 15;
		
		tickCount = 0;
	}
	
	public void draw(Graphics g) { 
		g.setColor(Color.DARK_GRAY);
		for (int y = POS_Y; y < (POS_Y + HEIGHT); y++) { 
			g.drawLine(POS_X, y, POS_X + WIDTH, y);
		}
		if (tickCount < 10 && hit) { 
			g.setColor(Color.RED);
			for (int y = POS_Y; y < (POS_Y + HEIGHT); y++) { 
				g.drawLine(POS_X, y, POS_X + WIDTH, y);
			}		}
		if (tickCount == 10) { 
			hit = false;
			tickCount = 0;
		}
		
	}
	
}
