import java.awt.Color;
import java.awt.Graphics;


public class SpeedMeter extends GameObj {

	public static int speed;
	public Integer shownSpeed;
	public static String word;
	
	public int POS_X;
	public int POS_Y;
	
	//the coordinates of the "SPEED: "
	public int SPEED_Y;
	
	//the coordinates of the meter
	public int METER_Y;
	
	public int WIDTH;
	public static int HEIGHT = MotherShip.HEIGHT * 1 / 4;
	
	public int COURT_WIDTH;
	
	public SpeedMeter(int court_width, int court_height) { 
		super(court_width * 5 / 6, court_height * 3 / 4, court_width, court_height, 0, 0, court_width / 6, HEIGHT);
		
		this.POS_X = court_width * 5 / 6;
		this.POS_Y = court_height - HEIGHT;
		
		this.SPEED_Y = POS_Y + (HEIGHT * 7 / 16);
		this.METER_Y = POS_Y + (HEIGHT * 7 / 8);
		
		this.COURT_WIDTH = court_width;
		
		speed = 0;
	}
	
	@Override
	public void draw(Graphics g) { 
		for (int y = POS_Y; y < (POS_Y + HEIGHT); y++) { 
			g.setColor(Color.DARK_GRAY);
			g.drawLine(POS_X, y, COURT_WIDTH, y);
		}

		if (word != null) { 
			shownSpeed = (speed);
		} else { 
			shownSpeed = 0;
		}
		if (shownSpeed > 19) { 
			g.setColor(Color.RED);
		} else if (shownSpeed > 14) { 
			g.setColor(Color.MAGENTA);
		} else if (shownSpeed > 9) { 
			g.setColor(Color.ORANGE);
		} else { 
			g.setColor(Color.BLACK);
		}
		g.drawString("SPEED: ", POS_X + 3, SPEED_Y);
		g.drawString(shownSpeed.toString() + " l/s", POS_X + 5, METER_Y);
		
	}
}
