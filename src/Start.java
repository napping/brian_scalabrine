import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;


public class Start implements Runnable {
	static JFrame frame;
	@Override
	public void run() {
		frame = new JFrame("Board Wars!");
	    frame.setLocation(525, 150);
	    
	    final LevelMaker Play = new LevelMaker("stars.png", new JLabel("Running..."));
	    
	    frame.add(Play);
	    
	    frame.pack();
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setVisible(true);

	    Play.reset();
	}

    public static void main(String[] args){
        SwingUtilities.invokeLater(new Start());
    }
}
//wrapper