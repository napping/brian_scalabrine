import java.awt.Color;
import java.awt.Graphics;


public class BonusIndicator extends GameObj {

	public static boolean bonus;
	public static boolean ready;
	
	public int POS_X;
	public static int POS_Y = MotherShip.SHIP_Y;
	
	public int WIDTH;
	public static int HEIGHT = MotherShip.HEIGHT / 4;

	
	public BonusIndicator(int court_width, int court_height) { 
		super(0, POS_Y, court_width, court_height, 0, 0, court_width / 5, HEIGHT);
		
		this.POS_X = 0;
		this.WIDTH = court_width * 7 / 24;
		ready = false;
		bonus = false;
	}
	
	public void draw(Graphics g) { 
		if (!ready) { 	
			g.setColor(Color.LIGHT_GRAY);
		} else { 
			g.setColor(Color.RED);
		}
		for(int y = POS_Y; y < POS_Y + HEIGHT; y++) { 
			g.drawLine(POS_X, y, POS_X + WIDTH, y);
		}
		g.setColor(Color.BLACK);
		g.drawString("BONUS: ", POS_X + 3, POS_Y + (HEIGHT / 2) - 3);
		
		if (ready) { 
			g.setColor(Color.WHITE);
			g.drawString("Just hit SPACE!", POS_X + 3, POS_Y + HEIGHT - 3);
		} else { 
			g.drawString("Not ready", POS_X + 3, POS_Y + HEIGHT - 3);
		}
	}
}
