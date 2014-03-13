import java.awt.Color;
import java.awt.Graphics;


public class ScoreMeter extends GameObj {
	
	public int POS_X;
	public int POS_Y;
	
	//the coordinates of the "SCORE: "
	public int SCORE_Y;
	
	//the coordinates of the score
	public int METER_Y;
	
	public int WIDTH;
	public static int HEIGHT = MotherShip.HEIGHT / 4;
	
	public static Integer score;
	
	public ScoreMeter(int court_width, int court_height) { 
		super(0, court_height * 3 / 4, court_width, court_height, 0, 0, court_width / 6, HEIGHT);
		this.POS_X = 0;
		this.POS_Y = court_height - HEIGHT;
		
		this.WIDTH = court_width / 6;
		
		this.SCORE_Y = POS_Y + (HEIGHT * 7 / 16);
		this.METER_Y = POS_Y + (HEIGHT * 7 / 8);
		
		score = 0;
	}
	
	@Override
	public void draw(Graphics g) { 
		for (int y = POS_Y; y < (POS_Y + HEIGHT); y++) { 
			g.setColor(Color.LIGHT_GRAY);
			g.drawLine(0, y, WIDTH, y);
		}
		g.setColor(Color.BLACK);
		g.drawString("SCORE: ", POS_X + 3, SCORE_Y);
		g.drawString(score.toString(), POS_X + 5, METER_Y);
		
	}
	
}
