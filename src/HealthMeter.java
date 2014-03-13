import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;


public class HealthMeter extends GameObj{

	public int POS_X;
	public int POS_Y;
	
	public int WIDTH;
	public static int HEIGHT = MotherShip.HEIGHT / 8;
	
	public int court_height;
	public static int left;
	
	int from;
	
	public HealthMeter(int court_width, int court_height) { 
		super(court_width * 1 / 3, court_height * 3 / 4, court_width, court_height, 0, 0, court_width * 1 / 3, HEIGHT);
		this.POS_X = court_width * 1 / 3;
		this.POS_Y = court_height - (2 * HEIGHT);
		
		this.WIDTH = court_width * 1 / 3;
		this.court_height = court_height;
		
		left = 3;
	}
	
	public void draw(Graphics g) { 
		g.setColor(Color.GREEN);
		for (int y = POS_Y; y < POS_Y + HEIGHT; y++) { 
			g.drawLine(POS_X, y, POS_X + WIDTH, y);
		}
		
		g.setColor(Color.BLACK);
		for(int y = POS_Y + HEIGHT; y < court_height; y++) {
			g.drawLine(POS_X, y, POS_X + WIDTH, y);
		}
		g.setColor(Color.WHITE);
		g.drawString("SHIELD", POS_X + (WIDTH / 4), court_height - 3);
		
		if (left == 2) { 
			from = POS_X + (WIDTH * 2 / 3);
		} else if (left == 1) { 
			from = POS_X + (WIDTH / 3); 
		} else if (left == 0) { 
			from = POS_X;
		}
		
		if (left != 3) { 
			g.setColor(Color.RED);
			for (int y = POS_Y; y < POS_Y + HEIGHT; y++) { 
				g.drawLine(from, y, POS_X + WIDTH, y);
			}
		}
		

		
	}
}
