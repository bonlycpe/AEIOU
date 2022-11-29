package AEIOU;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Bot extends standBoard {
	// image that represents the bot position on the board
	private BufferedImage image;
	private BufferedImage image2;

	// current position of bot on the board
	private Point pos;

	// For random
	private int x;
	private int y;

	// For start/end
	private int status = 1;

	// For generate first state
	private static int[] empty = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

	// For first bot
	public Bot(int playerPositon) {
		// load the assets
		loadImage();
		for (int i = 0; i < empty.length; i++) {
			empty[i] = 0;
		}
		// initialize the state
		empty[playerPositon] = 1;
		setPosition();
	}

	// For another bot
	public Bot() {
		// load the assets
		loadImage();
		// initialize the state
		setPosition();
	}

	private void setPosition() {
		while (true) {
			// Random position and initialize the state
			Random r = new Random();
			x = r.nextInt();
			x %= 13;
			if (x >= 0 && x <= 13)
				if (empty[x] == 0) {
					empty[x] = 1;
					break;
				}
		}
		pos = new Point(x, 16);
	}

	public void runPosition() {
		// runPosition if BotMove is true when it random form app
		if (botMove)
			if (status == 1) {
				int i = 0;
				int xp = pos.x;
				int yp = pos.y;
				int guaranteeMove;
				Random r = new Random();
				while (true) {
					x = r.nextInt();
					x %= 5;
					if (x >= 1 && x <= 5)
						break;
				}
				while (true) {
					guaranteeMove = r.nextInt();
					guaranteeMove %= botSpeed;
					if (guaranteeMove >= 1 && guaranteeMove <= botSpeed)
						break;
				}
				for (i = 0; i < guaranteeMove; i++) {
					if (pos.y - 1 > -1)
						if (checkPosition[pos.y - 1][pos.x] == 0)
							pos.translate(0, -1);
					if (pos.y == 0) {
						status = 2;
						if (second - 2 >= 0)
							second -= 2;
						break;
					}
				}
				// Auto move
				for (i = 0; i < x && status == 1; i++) {
					while (true) {
						Random p = new Random();
						y = p.nextInt();
						y %= 4;
						if (y >= 1 && y <= 3)
							break;
					}
					if (y == 1) {
						if (pos.y - 1 > -1) {
							if (checkPosition[pos.y - 1][pos.x] == 0) {
								pos.translate(0, -1);
								if (pos.y == 0) {
									status = 2;
									if (second - 2 >= 0)
										second -= 2;
									break;
								}
							}
						} else
							i--;

					} else if (y == 2) {
						if (pos.x + 1 < 13) {
							if (checkPosition[pos.y][pos.x + 1] == 0)
								pos.translate(1, 0);
						} else
							i--;
					} else if (y == 3) {
						if (pos.x - 1 > -1) {
							if (checkPosition[pos.y][pos.x - 1] == 0)
								pos.translate(-1, 0);
						} else
							i--;
					}
				}

				// Reposition
				checkPosition[yp][xp] = 0;
				checkPosition[pos.y][pos.x] = 1;
			}

	}

	private void loadImage() {
		try {
			image = ImageIO.read(new File(path + "bot1.png"));
			image2 = ImageIO.read(new File(path + "triangle.png"));
		} catch (IOException exc) {
			System.out.println("Error opening image file: " + exc.getMessage());
		}
	}

	public void draw(Graphics g, ImageObserver observer) {
		if (status == 1)
			g.drawImage(image, pos.x * Board.TILE_SIZE, pos.y * Board.TILE_SIZE, observer);
		else
			g.drawImage(image2, pos.x * Board.TILE_SIZE, pos.y * Board.TILE_SIZE, observer);
	}

	public Point getPos() {
		return pos;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
