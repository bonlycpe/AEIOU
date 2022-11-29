package AEIOU;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Rock extends standBoard {
	// image that represents the rock position on the board
	private BufferedImage image;
	// current position of rock on the board
	private Point pos;

	public Rock(int x, int y) {
		loadImage();
		pos = new Point(x, y);
		checkPosition[y][x] = 4;
	}

	private void loadImage() {
		try {
			image = ImageIO.read(new File(path + "rock.png"));
		} catch (IOException exc) {
			System.out.println("Error opening image file: " + exc.getMessage());
		}
	}

	public void draw(Graphics g, ImageObserver observer) {
		g.drawImage(image, pos.x * Board.TILE_SIZE, pos.y * Board.TILE_SIZE, observer);
	}

	public Point getPos() {
		return pos;
	}

	public void setPos(Point pos) {
		this.pos = pos;
	}

}
