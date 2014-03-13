import java.awt.Color;
import java.awt.Graphics;

import javax.swing.*;


public class TextBox extends GameObj {
	
	public int POS_X;
	//places it in the center of the mothership
	/** This means that TextBox must be called after MotherShip has been created*/
	public static int POS_Y = MotherShip.SHIP_Y + (MotherShip.HEIGHT / 2);
	
	public int WIDTH;
	public static int HEIGHT = MotherShip.HEIGHT * 7 / 32;
	
	public int COURT_WIDTH;
	public int COURT_HEIGHT;
	
	public static String text;
	
	public int TEXT_X;
	public int TEXT_Y;

	public TextBox(int court_width, int court_height) { 
		super((court_height / 4), POS_Y, court_width, court_height, 0, 0, (court_width * 3 / 4), HEIGHT);
		this.POS_X = court_width / 4;
		this.WIDTH = court_width * 1 / 2;
		this.COURT_WIDTH = court_width;
		this.COURT_HEIGHT = court_height;
		text = "";
		this.TEXT_X = POS_X + 5;
		this.TEXT_Y = POS_Y + (HEIGHT * 7/8);
	}
	
	@Override
	public void draw(Graphics g) { 
		if (!MotherShip.explode) {
			g.drawRect(POS_X, POS_Y, WIDTH, HEIGHT);
			for (int y = POS_Y; y <= (POS_Y + HEIGHT); y++) {
				g.setColor(Color.WHITE);
				g.drawLine(POS_X, y, POS_X + WIDTH, y);
				g.setColor(Color.DARK_GRAY);
				g.drawString(text, TEXT_X, TEXT_Y);
				
			}
		}
	}
}
