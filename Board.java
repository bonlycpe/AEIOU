package AEIOU;

import java.awt.*;
import java.awt.event.*;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

import javax.swing.*;

public class Board extends JPanel implements ActionListener, KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// controls the delay between each tick
	private final int DELAY = 15;
	// size of the board
	public static final int TILE_SIZE = 45;
	public static final int ROWS = 17;
	public static final int COLUMNS = 13;

	// timer
	private Timer timer;

	// objects that appear on the game board
	private Player player;

	// controls how many bot on the board
	private Bot[] bot = new Bot[12];
	private int j;
	private static int numbot = 12;

	// controls how many obstructions on the board
	private static int[] ramdomObSpwanX;
	private static int[] ramdomObSpwanY;
	private static Hole[] holes;
	private static Rock[] rocks;
	private static Tree[] trees;

	public Board() {

		// set the game board size
		standBoard.modeSet();
		setPreferredSize(new Dimension(TILE_SIZE * COLUMNS, TILE_SIZE * ROWS));
		// set the game board background color
		setBackground(new Color(255, 81, 28, 255));

		int i;

		// initialize the game state
		player = new Player();

		// bot
		bot[0] = new Bot(player.sentPosition());
		for (i = 1; i < numbot; i++) {
			bot[i] = new Bot();
		}

		// obstructions
		holes = new Hole[Hole.holeIndex];
		rocks = new Rock[Rock.rockIndex];
		trees = new Tree[Tree.treeIndex];
		int sameTree = Tree.treeIndex;

		// rocks
		ramdomObSpwanX = spwan(Rock.rockIndex, 12);
		ramdomObSpwanY = spwan(Rock.rockIndex, 13);
		for (i = 0; i < Rock.rockIndex; i++) {
			rocks[i] = new Rock(ramdomObSpwanX[i], ramdomObSpwanY[i]);
		}

		// trees
		int start = 0;
		while (true) {
			ramdomObSpwanX = spwan(sameTree, 12);
			ramdomObSpwanY = spwan(sameTree, 6);
			for (i = start, j = 0; j < ramdomObSpwanX.length; i++, j++) {
				if (standBoard.checkPosition[ramdomObSpwanY[j]][ramdomObSpwanX[j]] == 0) {
					trees[i] = new Tree(ramdomObSpwanX[j], ramdomObSpwanY[j]);
					sameTree--;
				} else
					i--;
			}
			if (sameTree == 0)
				break;
			else {
				start = Tree.treeIndex - sameTree - 1;
			}

		}

		// holes
		ramdomObSpwanY = spwan(Hole.holeIndex, 9);
		for (i = 0; i < Hole.holeIndex; i++) {
			holes[i] = new Hole(ramdomObSpwanY[i]);
		}

		// this timer will call the actionPerformed() method every DELAY ms
		timer = new Timer(DELAY, this);
		timer.start();
	}

	private int[] spwan(int needNum, int maxRan) {
		Random r = new Random();
		// use LinkedHashSet to maintain insertion order
		int[] spwanSent = new int[needNum];
		int i = 0;
		Set<Integer> spwanPoint = new LinkedHashSet<Integer>();
		while (spwanPoint.size() < needNum) {
			Integer next = r.nextInt(maxRan) + 1;
			// adding to a set, this will automatically do a containment check
			if (next > 2)
				spwanPoint.add(next);

		}
		for (Integer s : spwanPoint) {
			spwanSent[i++] = s;
		}
		return spwanSent; // sent array of int
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// this method is called by the timer every DELAY.
		// update the state of game and animation before the graphics are redrawn.
		// calling repaint() will trigger paintComponent() to run again,
		// which will refresh/redraw the graphics.
		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// when calling g.drawImage() , use "this" for the ImageObserver
		// draw graphics.
		int i;

		// Background
		drawBackground(g);

		// Player
		player.draw(g, this);

		// Bot
		for (i = 0; i < numbot; i++) {
			bot[i].draw(g, this);
		}

		// Obstructions
		for (i = 0; i < Hole.holeIndex; i++) {
			if (holes[i].getSpawn()) {
				holes[i].draw(g, this);
			}
		}
		for (i = 0; i < Rock.rockIndex; i++) {
			if (rocks[i].getPos().y + 1 == player.getPos().y && rocks[i].getPos().x == player.getPos().x)
				rocks[i].draw(g, this);
			else if (rocks[i].getPos().x - 1 == player.getPos().x && rocks[i].getPos().y == player.getPos().y)
				rocks[i].draw(g, this);
			else if (rocks[i].getPos().x + 1 == player.getPos().x && rocks[i].getPos().y == player.getPos().y)
				rocks[i].draw(g, this);
			else if (rocks[i].getPos().y - 1 == player.getPos().y && rocks[i].getPos().x == player.getPos().x)
				rocks[i].draw(g, this);
		}
		for (i = 0; i < Tree.treeIndex; i++) {
			if (trees[i].getnowSpawn()) {
				trees[i].draw(g, this);
			} else if (trees[i].getPos().y + 3 == player.getPos().y) {
				trees[i].draw(g, this);
			}
		}

		// this smooths out animations
		Toolkit.getDefaultToolkit().sync();
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// react to key events
		int i;
		player.keyPressed(e);

		for (i = 0; i < Hole.holeIndex; i++) {
			if (!holes[i].getnowSpawn())
				if (player.getPos().y == holes[i].getPos().y) {
					holes[i].holeSet(player.getPos());
				}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	private void drawBackground(Graphics g) {
		// draw a background
		g.setColor(new Color(255, 217, 18, 255));
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLUMNS; col++) {
				if ((row + col) % 2 == 1) {
					if (row == ROWS - 1)
						g.setColor(new Color(28, 105, 223, 255));
					// draw a square tile at the current row/column position
					// fillRect(int x, int y, int width, int height);
					g.fillRect(col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE);
				}
			}
		}

		// bot Move but in condition of botMove in App
		if (standBoard.botMove) {
			for (int i = 0; i < numbot; i++) {
				bot[i].runPosition();
			}
			standBoard.botMove = false;
		}

		// Check hidden state of player
		if (player.getPos().y - 1 > -1)
			if (standBoard.checkPosition[player.getPos().y - 1][player.getPos().x] == 1
					|| standBoard.checkPosition[player.getPos().y - 1][player.getPos().x] == 5) {
				standBoard.hide = true;
			} else {
				standBoard.hide = false;
			}
	}
}
