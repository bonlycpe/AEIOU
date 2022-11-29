package AEIOU;

import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Player extends standBoard {

	// image that represents the player's position on the board
	private BufferedImage image;
	// current position of the player on the board
	private Point pos;
	// For random
	private int x;
	//Sound win 
	private static Clip soundWin;

	// State for move
	private boolean flag = false;
	private boolean enterRoll = true;

	public Player() {
		//load sound
		try {
			AudioInputStream inputWin = AudioSystem.getAudioInputStream(new File(pathm + "win.wav"));
			soundWin = AudioSystem.getClip();
			soundWin.open(inputWin);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// load the assets
		loadImage();

		for (int i = 0; i < 17; i++)
			for (int j = 0; j < 13; j++)
				checkPosition[i][j] = 0;
		// 0 ->empty , 1 -> player , 2 -> bot , 3 -> hole , 4 -> rock , 5 -> tree

		while (true) {
			Random r = new Random();
			x = r.nextInt();
			x %= 12;
			if (x >= 0 && x <= 12)
				break;
		}

		// initialize the state
		pos = new Point(x, 16);
		checkPosition[pos.y][pos.x] = 1;

	}

	public int sentPosition() {
		return x;
	}

	private void loadImage() {
		try {
			image = ImageIO.read(new File(path + "Playerrun2.png"));
		} catch (IOException exc) {
			System.out.println("Error opening image file: " + exc.getMessage());
		}
	}

	public void draw(Graphics g, ImageObserver observer) {
		g.drawImage(image, pos.x * Board.TILE_SIZE, pos.y * Board.TILE_SIZE, observer);
	}

	public boolean getFlag() {
		return flag;
	}

	public void keyPressed(KeyEvent e) {
		int i;
		int xp = pos.x;
		int yp = pos.y;
		String key = KeyEvent.getKeyText(e.getKeyCode());

		// For back to menu
		if (key.equals("Backspace")) {
			window.dispose();
			new FrameMenu();
			FrameMenu.initWindow();
		}

		// For boost stamina and run game
		if (key.equals("Enter") && enterRoll) {
			flag = true;
			while (true) {
				Random r = new Random();
				stamina = r.nextInt();
				stamina %= 6;
				if (stamina >= 1 && stamina <= 6)
					break;
			}
			enterRoll = false;
		} else if (!enterRoll) {
			flag = false;
			if (key.equals("w") || key.equals("W")) {
				if (pos.y - 1 > -1)
					if (checkPosition[pos.y - 1][pos.x] == 0 || checkPosition[pos.y - 1][pos.x] == 3) {
						pos.translate(0, -1);
						stamina--;
					}
			} else if (key.equals("d") || key.equals("D")) {
				if (pos.x + 1 < 13)
					if (checkPosition[pos.y][pos.x + 1] == 0 || checkPosition[pos.y][pos.x + 1] == 3) {
						pos.translate(1, 0);
						stamina--;
					}
			} else if (key.equals("s") || key.equals("S")) {
				if (pos.y + 1 < 17)
					if (checkPosition[pos.y + 1][pos.x] == 0 || checkPosition[pos.y + 1][pos.x] == 3) {
						pos.translate(0, 1);
						stamina--;
					}
			} else if (key.equals("a") || key.equals("A")) {
				if (pos.x - 1 > -1)
					if (checkPosition[pos.y][pos.x - 1] == 0 || checkPosition[pos.y][pos.x - 1] == 3) {
						pos.translate(-1, 0);
						stamina--;
					}
			}
		}

		// For move backward when met hole
		if (checkPosition[pos.y][pos.x] == 3) {
			for (i = 1; i < 7; i++) {
				if (pos.y + i < 17)
					if (checkPosition[pos.y + i][pos.x] == 0)
						pos.y++;
			}
		}

		// For boost stamina again when stamina is 0
		if (stamina == 0) {
			flag = false;
			enterRoll = true;
		}

		// Reposition
		checkPosition[yp][xp] = 0;
		checkPosition[pos.y][pos.x] = 1;

		// Win state
		if (pos.y == 0) {
			win = true;
			soundWin.start();
		}

	}

	public Point getPos() {
		return pos;
	}

}
