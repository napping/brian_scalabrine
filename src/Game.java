import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import Images.ResourceLoader;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Game implements Runnable {

	public static Integer hiscore = 778750;
	public static Integer hispeed = 0;
	public static JFrame frame;
	public static JLabel score_meter = new JLabel(" High Score:  \n"  + hiscore.toString() + " points ");
	public static JLabel speed_meter = new JLabel(" Highest Speed:  \n" + hispeed.toString()+ " letters per second ");
	
	@Override
	public void run() {
				
	    frame = new JFrame("Board Wars!");
	    frame.setLocation(525,150);
	    frame.setBounds(525, 150, 350, 550);
	    	    
	    JButton play_button = new JButton("Play now!");
	    JButton instructions = new JButton("Instructions");

	    JPanel panel = new JPanel();
	    JPanel score = new JPanel();
	    JPanel speed = new JPanel();
	    JLabel BoardWars = null;
	    
	    BoardWars = new JLabel(new ImageIcon(ResourceLoader.getImage("HomePage.png")));

	    score.add(score_meter);
	    speed.add(speed_meter);
	    score.setBorder(BorderFactory.createEtchedBorder(Color.BLACK, Color.WHITE));
	    speed.setBorder(BorderFactory.createEtchedBorder(Color.BLACK, Color.WHITE));
	    
	    panel.add(BoardWars);
	    panel.add(play_button);
	    panel.add(instructions);
	    panel.add(score);
	    panel.add(speed);
	    panel.setAlignmentX(Component.CENTER_ALIGNMENT);
	    panel.setBackground(Color.BLACK);
	    
	    frame.add(panel);
	   // frame.add(instructions);
	    
	    //invokes a new Start() game, and makes this frame go away.
	    play_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Start());
				frame.setVisible(false);
			}
			});
	    
	    instructions.addActionListener(new ActionListener() { 
	    	public void actionPerformed(ActionEvent e) { 
	    		JOptionPane.showMessageDialog(frame,
	    				"Greetings, brave space-dweller!  Sorry for the lack of sexiness in these instructions and\n"
	    				+ " in the Start Menu... Are you ready to participate in a rigorous battle between \n"
	    				+ "you and dozens of enemies?  Are you ready to move your fingers faster than the \n"
	    				+ "speed of light?  Are you ready to give your all to protect your glorious Mothership \n"
	    				+ "from the evil that permeates the galaxy?  If yes, then hit \"Play now!\" in the Start Menu to"
	    				+ "begin! \n"
	    				+ "\n"
	    				+ "Once the battle starts, enemies will come flying towards your Mothership on the bottom of your\n"
	    				+ "screen.  If any of the enemies pass the white SHIELD line, then DAMAGE will be done \n"
	    				+ "to your ship!  Your SHIELD can stand THREE hits – any more will explode your ship, permanently \n"
	    				+ "obliterating you and all that you hold dear in this universe.  To fight back, TYPE the word \n"
	    				+ "that appears beneath the enemy and press ENTER.  If what you entered in the TEXTBOX matches \n"
	    				+ "the enemy’s word, the HITMETER will turn red, and your MOTHERSHIP gun will shoot a bullet toward \n"
	    				+ "that enemy and destroy it.  After shooting down five enemies, a bonus fire will be available; just\n"
	    				+ "hit the SPACEBAR to release it and destroy all the enemies currently on the screen!  \n"
	    				+ "\n"
	    				+ "In the lower left hand corner you will find a SCOREMETER.  Each enemy you shoot down will award \n"
	    				+ "you +1000 points =D!  Each “miss” or word incorrectly typed when hitting ENTER will deduct you -250\n"
	    				+ " points :-(…  Each enemy you destroy with your crazy awesome bonus will award you +1750 points =D!  \n"
	    				+ "And lastly, each enemy that hits your ship and damages your shield will deduct you -500 points :-(… \n"
	    				+ "\n"
	    				+ "In the lower right hand corner you will fight a SPEEDMETER, which tells you your typing speed in\n"
	    				+ " letters per second.  It updates each time you press ENTER! \n"
	    				+ "\n"
	    				+ "Lastly, hitting the number key ZERO will allow you to PAUSE the game, and you can either resume or \n"
	    				+ "quit from there!\n"
	    				+ "\n"
	    				+ "Do not worry about numbers or capital letters in the ENEMIES’ words; they will all be short, \n"
	    				+ "lowercase words!  And the longer the game is running, the more and the faster the enemies will come!!!\n"
	    				+ "\n"
	    				+ "Alright, the epic battle between you and the universe is ready to start!  Hit “Play now!” to begin!!!", 
	    				"Board Wars Instructions", JOptionPane.INFORMATION_MESSAGE);
	    }
	    });
	    
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setVisible(true);

	    
	}

	public static void main(String[] args) { 
		SwingUtilities.invokeLater(new Game());
	}
}
