package AEIOU;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Tree extends standBoard {
	// image that represents the tree position on the board
	private BufferedImage image;
	// current position of tree on the board
	private Point pos;
	// Spawn state
	public boolean nowSpawn = false;

	public Tree(int x, int y) {
		loadImage();
		pos = new Point(x, y);
		checkPosition[y][x] = 5;
	}

	private void loadImage() {
		try {
			image = ImageIO.read(new File(path + "tree.png"));
		} catch (IOException exc) {
			System.out.println("Error opening image file: " + exc.getMessage());
		}
	}

	public void draw(Graphics g, ImageObserver observer) {
		g.drawImage(image, pos.x * Board.TILE_SIZE, pos.y * Board.TILE_SIZE, observer);
		nowSpawn = true;
	}

	public Point getPos() {
		return pos;
	}

	public void setPos(Point pos) {
		this.pos = pos;
	}

	public boolean getnowSpawn() {
		return nowSpawn;
	}

	public void nowSpawn(boolean nowSpawn) {
		this.nowSpawn = nowSpawn;
	}

}
