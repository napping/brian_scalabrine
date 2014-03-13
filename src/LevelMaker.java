import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import Images.ResourceLoader;

import java.util.Random;

//this is based off my raw Level_1 class.  This is meant to make any level, with a List of enemies
//to come in.
public class LevelMaker extends JPanel {
	
	JButton resume = new JButton("Resume");
	JButton quit = new JButton("Quit");
	

	public static final int COURT_WIDTH = 300;
	public static final int COURT_HEIGHT = 500;
	public static final int INTERVAL = 20; 
	
	private Image background;
	
	public boolean playing;
	private JLabel status;
	
	//list of enemies that will come down. randomly generated on an interfval
	public List<Shootable> enemies;
	
	//objects on the screen
	private MotherShip mship;
	private TextBox textbox;
	public int text_width;
	private ScoreMeter sm;
	private SpeedMeter spdm;
	private HitMeter hm;
	private HitMeter hm2;
	private HealthMeter health;
	private BonusIndicator bonus;
	
	//this is the text that is evaluated when Enter is pressed
	public String shot_text = "";

	int tick_count;

	//the interval at which enemies come down. Thus, the lower it is, the faster the enemies come down.
	public static int difficulty;
	
	//generates a random number for the dictionary array for an enemy
	Random random_word = new Random();

	Random random_pic = new Random();
	
	Random random_posx = new Random();
	
	Random random_vel = new Random();
	
	//these are used to get words/min
	public int speedTickCount = 0;
	public boolean speedCount = false;
	public int numChars = 3;
	
	public Integer maxSpeed;
	
	public int bonusMeter;

	List<Bullet> bullets;
	List<BonusBullet> bonus_bullets;
	public static int bonusTickCount;
	
	public boolean paused;
	
//	public JButton again = new JButton("Play again!");
//	public JButton back; new JButton("Return to Main Menu");
	
	boolean explodeCount = false;
			
	public Image lost = ResourceLoader.getImage("explode.png");
	
	public LevelMaker(String background_file, final JLabel status) { 
		
		if (background_file != null) { 
			background = ResourceLoader.getImage(background_file);
		}
		
		setBorder(BorderFactory.createLineBorder(Color.BLACK)); 
		Timer timer = new Timer(INTERVAL, new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if (!paused) { 
					tick(); //Does everything that should happen within every interval.
					tick_count += INTERVAL;
					if (speedCount) { 
						speedTickCount++;
					}
					if (HitMeter.hit) { 
						HitMeter.tickCount++;
					}
				}
			}
		});
		
		timer.start();
		
		setFocusable(true);
		
		resume.addActionListener(new ActionListener() { 
			
			public void actionPerformed(ActionEvent e) { 
				paused = false;
			}
		});
		
		addKeyListener(new KeyAdapter() { 
			public void keyPressed(KeyEvent e) {  
				char character = e.getKeyChar();

				if (character == KeyEvent.VK_0) {
					paused = true;
					
					Integer selection = JOptionPane.showOptionDialog(status, 
					        "Board Wars is paused.", 
					        "Paused", 
					        JOptionPane.OK_CANCEL_OPTION, 
					        JOptionPane.INFORMATION_MESSAGE, 
					        null, 
					        new String[]{"Resume", "Quit"}, 
					        "default");
					if (selection == 0 || selection == JOptionPane.CLOSED_OPTION) { 
						paused = false;
					} else { 
						Start.frame.setVisible(false);
						Game.frame.setVisible(true);
						reset();
						paused = true;
						playing = false;
					}
				} 
				
				
				if (character == KeyEvent.VK_ENTER) { 
					if (speedTickCount > 0) {	
						SpeedMeter.speed = ((TextBox.text.length() * 50) / (speedTickCount));
						if (maxSpeed < spdm.shownSpeed) { 
							maxSpeed = spdm.shownSpeed;
						}
					}
					shot_text = TextBox.text;
					SpeedMeter.word = TextBox.text;
					TextBox.text = "";
					speedCount = false;
					speedTickCount = 0;
					ScoreMeter.score = ScoreMeter.score - 250;
				}

				if (character == KeyEvent.VK_SPACE && BonusIndicator.ready) { 
					bonus_bullets = createBonusList();
					BonusIndicator.bonus = true;
				}
				
				if (character == KeyEvent.VK_BACK_SPACE && !TextBox.text.equals("")) { 
					TextBox.text = TextBox.text.substring(0, (TextBox.text.length() - 1));
				}
				
				if (text_width < textbox.WIDTH - 10 && character != KeyEvent.VK_ENTER && character != KeyEvent.VK_BACK_SPACE && character != KeyEvent.VK_SPACE && character != KeyEvent.VK_0) {
					speedCount = true;
					TextBox.text += character;
				}
			}
		});
		
		this.status = status;
	}
	
	public void reset() {
		mship = new MotherShip(COURT_WIDTH, COURT_HEIGHT);
		
		textbox = new TextBox(COURT_WIDTH, COURT_HEIGHT);
		
		sm = new ScoreMeter(COURT_WIDTH, COURT_HEIGHT);
				
		spdm = new SpeedMeter(COURT_WIDTH, COURT_HEIGHT);
		
		hm = new HitMeter(COURT_WIDTH, COURT_HEIGHT, true);
		
		hm2 = new HitMeter(COURT_WIDTH, COURT_HEIGHT, false);
		
		health = new HealthMeter(COURT_WIDTH, COURT_HEIGHT);	
		
		bonus = new BonusIndicator(COURT_WIDTH, COURT_HEIGHT);
		
		enemies = new ArrayList<Shootable>();
		bullets = new ArrayList<Bullet>();
		bonus_bullets = new ArrayList<BonusBullet>();
		
		enemies.clear();
		bullets.clear();
		bonus_bullets.clear();
		
		maxSpeed = 0;
		
		this.playing = true;
		status.setText("Running...");
		
		/**change back to background_file*/
		this.paused = false;
		this.bonusMeter = 0;
		bonusTickCount = 0;
		
		difficulty = 1000;
		this.tick_count = 0;
		
		Resources.vels.clear();
		Resources.vels.add(1);
		Resources.vels.add(1);
		Resources.vels.add(2);
		
		// Make sure that this component has the keyboard focus  
		requestFocusInWindow();
	} 
	
	@SuppressWarnings("deprecation")
	void tick(){
		
		if (tick_count == 10000) { 
			difficulty = 900;
		}
		if (tick_count == 20000) { 
			difficulty = 800;  
		} 
		
		if (tick_count == 40000) { 
			difficulty = 700;
		}
		
		if (tick_count == 60000) { 
			Resources.vels.add(1);
			Resources.vels.add(2);
			Resources.vels.add(3);
		}
		
		if (tick_count == 80000) { 
			difficulty = 600;
		}
		
		if (tick_count == 100000) { 
			difficulty = 500;
		}

		if (tick_count == 130000) { 
			Resources.vels.add(2);
			Resources.vels.add(3);
		}
		
		if (tick_count == 160000) {
			difficulty = 400;
		}
		
		if (tick_count == 190000) { 
			Resources.vels.add(3);
		}
		
		
		if (tick_count == 220000) {
			difficulty = 300;
		}

		if (tick_count == 250000) {
			difficulty = 200;
		}

		if (tick_count % 5000 == 0) { 
			for (int i = 0; i < enemies.size(); i++) { 
				if (!enemies.get(i).existence) { 
					enemies.remove(i); 
				}
			}
		}
		
		
		//playing = !MotherShip.explode;
		if (playing) {
			//moves all the enemies, toward the ship
			if (enemies.size() != 0) { 
				for (int i = 0; i < enemies.size(); i++) {
					enemies.get(i).move();
				}
			}
			//generates the shootables to come down given the intervals. Randomized.
			if (tick_count % difficulty == 0) { 
				enemies.add(new Shootable(Resources.dictionary[random_word.nextInt(Resources.dictionary.length)], 
						Resources.pics[random_pic.nextInt(Resources.pics.length)], 
						random_posx.nextInt(5) + 1,
						0,
						Resources.vels.get(random_vel.nextInt(Resources.vels.size())),
						COURT_WIDTH,
						COURT_HEIGHT
						));
			}
			
			//moves all the bullets, if they exist
			if (bullets.size() != 0) { 
				for (int i = 0; i < bullets.size(); i++)  {
					bullets.get(i).move();
				}
			}
			
			if (bonus_bullets.size() != 0) { 
				for (int i = 0; i < bonus_bullets.size(); i++) {  
					bonus_bullets.get(i).move();
				}
				if (bonus_bullets.get(0).hitBorder()) { 
					bonus_bullets.clear();
				}
			}
			
			if (!BonusIndicator.bonus) { 
				String[] enemiesText = getText(enemies);
				for (int i = 0; i < enemiesText.length; i++) { 
					if (shot_text.equals(enemiesText[i]) && enemies.get(i).existence) { 
						enemies.get(i).approach_bullet = true;
						bullets.add(new Bullet(enemies.get(i), COURT_WIDTH, COURT_HEIGHT, false));
						ScoreMeter.score += 1250; //adds 1000 to score
						shot_text = "";
						HitMeter.hit = true;
						this.bonusMeter++;
					} 
				}
			} else { 
				bonusTickCount++;
				if (bonusTickCount > 13) { 
					for (int i = 0; i < enemies.size(); i++) { 
						if (enemies.get(i).existence) { 
							enemies.get(i).hit_bullet = true;
							enemies.get(i).existence = false;
							ScoreMeter.score += 1750; //higher score for using bonus
							bonusTickCount = 0;
						}
					}
					this.bonusMeter = 0;
					BonusIndicator.bonus = false;
					BonusIndicator.ready = false;
				}
				bullets.clear();
			}
			
			if (this.bonusMeter >= 5) { 
				BonusIndicator.ready = true;
			}

			
			if (explodeCount) { 	
				if (ScoreMeter.score > Game.hiscore) { 
					Game.hiscore = ScoreMeter.score;
					Game.score_meter.setText(" High Score:  \n"  + ScoreMeter.score.toString() + " points "); 
				}
				if (maxSpeed > Game.hispeed) { 
					Game.hispeed = maxSpeed;
					Game.speed_meter.setText(" Highest Speed:  \n" + maxSpeed.toString()+ " letters per second "); 
				}
				int end = JOptionPane.showOptionDialog(null, 
						"Terrific typing!  Your final score was " + ScoreMeter.score + "! \nYour highest typing speed was " + maxSpeed + " letters per second!", 
						"Mothership Exploded!", 
						JOptionPane.DEFAULT_OPTION, 
						JOptionPane.INFORMATION_MESSAGE, 
						null, 
						new String[] {"Play again!","Back to Main Menu"}, 
						null);
				if (end == 0) { 
					reset();
				} else {
					Start.frame.setVisible(false);
					Game.frame.setVisible(true);
					reset();
					paused = true;
					this.playing = false;
				}
				
		/**
		 * 					Integer selection = JOptionPane.showOptionDialog(status, 
					        "Board Wars is paused.", 
					        "Paused", 
					        JOptionPane.OK_CANCEL_OPTION, 
					        JOptionPane.INFORMATION_MESSAGE, 
					        null, 
					        new String[]{"Resume", "Quit"}, 
					        "default");
					if (selection == 0 || selection == JOptionPane.CLOSED_OPTION) { 
						paused = false;
					} else { 
						SwingUtilities.invokeLater(new Game());
					}		
		 */
				
				explodeCount = false;
			}
			
			if (MotherShip.explode) { 
				explodeCount = true;
			}
				
		}
			// update the display
			repaint();
			
			//System.out.println(Resources.vels.size());
			//System.out.println(enemies.size());
		 //System.out.println(ScoreMeter.score);
		 //System.out.println(Game.hiscore);
	}
	
	public boolean waitFor(int tick_count, int secs) { 
		return (tick_count >= secs);
	}
	
	public String[] getText(List<Shootable> enemies) {
		String[] returnArray = new String[enemies.size()];
		for (int i = 0; i < enemies.size(); i++) { 
			String thisWord = enemies.get(i).word;
			returnArray[i] = thisWord;
		}
		return returnArray;
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(background, 0, 0, COURT_WIDTH, COURT_HEIGHT, null);
		mship.draw(g);
		textbox.draw(g);
		for (int i = 0; i < enemies.size(); i++) {
			if (enemies.get(i).explodeTickCount < 1000) {
				enemies.get(i).draw(g);
				enemies.get(i).existence = true;
			}

		}
		if (!BonusIndicator.bonus) { 
			if (bullets.size() != 0) { 
				for (int i = 0; i < bullets.size(); i++)  {
					if (bullets.get(i).approachTickCount < 7) {
						bullets.get(i).draw(g);
					}
				}
			} 
		}
		
		if (bonus_bullets.size() != 0) { 
			for (int i = 0; i < bonus_bullets.size(); i++) { 
				bonus_bullets.get(i).draw(g);
			}
		}
		
		if (!MotherShip.explode) {
			spdm.draw(g);
			hm.draw(g);
			hm2.draw(g);
			health.draw(g);
			bonus.draw(g);
			
			if (HealthMeter.left != 0) { 
				g.setColor(Color.WHITE);
				for (int y = MotherShip.SHIP_Y - 3; y < MotherShip.SHIP_Y; y++) { 
					g.drawLine(0, y, COURT_WIDTH, y);
				}
			}
		}
		
		sm.draw(g);

		//for keeping the characters within the TextBox; this method needed the Graphics library
		FontMetrics fm = g.getFontMetrics(); 
		this.text_width = fm.stringWidth(TextBox.text);

	}
	
	public List<BonusBullet> createBonusList() {
		List<BonusBullet> returnList = new ArrayList<BonusBullet>();
		int x = COURT_WIDTH;
		int y = COURT_HEIGHT;
		
		returnList.add(new BonusBullet(15, x, y));
		returnList.add(new BonusBullet(x / 5 + 15, x, y));
		returnList.add(new BonusBullet(x * 2 / 5 + 15, x, y));
		returnList.add(new BonusBullet(x * 3 / 5 + 15, x, y));
		returnList.add(new BonusBullet(x * 4 / 5 + 15, x, y));
		
		return returnList;
	}
	
	@Override
	public Dimension getPreferredSize() { 
		return new Dimension(COURT_WIDTH, COURT_HEIGHT);
	}	
}