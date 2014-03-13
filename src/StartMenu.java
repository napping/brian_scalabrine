import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class StartMenu implements Runnable {

	public static Integer hiscore = 0;
	public static Integer hispeed = 0;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
	    final JFrame frame = new JFrame("Board Wars!");
	    frame.setLocation(525,150);
	    frame.setBounds(525, 150, 330, 530);
	    
	    JButton play_button = new JButton("Play now!");
	    JButton instructions = new JButton("Instructions");

	    JPanel panel = new JPanel();
	    JLabel BoardWars = null;
	    
	    try {
			BoardWars = new JLabel(new ImageIcon(ImageIO.read(new File("HomePage.png"))));
		} catch (IOException e1) {
			System.out.println("Internal error" + e1.getMessage());
		}
	    
	    JLabel score = new JLabel(hiscore.toString());
	    JLabel speed = new JLabel(hispeed.toString() + " letters per second:");
	    
	    panel.add(BoardWars);
	    panel.add(play_button);
	    panel.add(instructions);
	    panel.add(score);
	    panel.add(speed);
	    frame.add(panel);
	   // frame.add(instructions);

	    
	    play_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        SwingUtilities.invokeLater(new Start());
		        frame.setVisible(false);
			}
			});
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setVisible(true);

	    
	}

	public static void main(String[] args) { 
		SwingUtilities.invokeLater(new StartMenu());
	}
}
