package AEIOU;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Hole extends standBoard {
	// image that represents the hole position on the board
	private BufferedImage image;
	// current position of hole on the board 
	private Point pos;
	//Spawn state
	public boolean Spawn = false;
	public boolean nowSpawn = false;
	
	public Hole(int y) {
		loadImage();
		pos = new Point(0, y);
	}
	
	//Set position and spawn state
	public void holeSet(Point playerPos) {
		if (checkPosition[playerPos.y - 1][playerPos.x] == 0) {
			this.pos.y = playerPos.y - 1;
			this.pos.x = playerPos.x;
			checkPosition[pos.y][pos.x] = 3;
			Spawn = true;
		}
	}

	private void loadImage() {
		try {
			image = ImageIO.read(new File(path + "hole.png"));
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

	public boolean getSpawn() {
		return Spawn;
	}

	public void holeSpawn(boolean Spawn) {
		this.Spawn = Spawn;
	}

	public boolean getnowSpawn() {
		return nowSpawn;
	}

	public void nowSpawn(boolean nowSpawn) {
		this.nowSpawn = nowSpawn;
	}

}
