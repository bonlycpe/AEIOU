package AEIOU;

import javax.swing.JFrame;

public class standBoard {
	// Path of image
	protected static String p = "C:\\Users\\Chale\\Desktop\\Github\\java\\LAB_FINAL\\src\\AEIOU\\";
	protected static String path = p+"images\\";
	protected static String pathm = p+"music\\";

	// For check state of each position
	protected static int checkPosition[][] = new int[17][13];
	// Stamina of player
	protected static int stamina = 0;
	// player state
	protected static boolean hide = false;
	// MODE in game
	protected static final int NORMAL = 1;
	protected static final int HARD = 2;
	protected static final int INSANE = 3;
	// Current mode
	protected static int mode;
	// Game state
	protected static boolean win = false;
	protected static boolean fail = false;
	// Bot move state
	protected static boolean botMove = false;
	// Bot move speed
	protected static int botSpeed;
	// State for start game
	protected static boolean flagAEIOU = false;
	// Timer
	protected static int second, minute;
	// Amount of obstructions
	protected static int holeIndex = 0;
	protected static int treeIndex = 0;
	protected static int rockIndex = 0;
	// Open game in window
	protected static JFrame window;

	
	public static void modeSet() {
		// Set game mode
		// 1 -> Normal , 2 -> Hard , 3 -> Insane
		if (mode == 1) {
			holeIndex = 1;
			treeIndex = 4;
			rockIndex = 5;
			botSpeed = 2;
		} else if (mode == 2) {
			holeIndex = 3;
			treeIndex = 1;
			rockIndex = 8;
			botSpeed = 3;
		} else if (mode == 3) {
			holeIndex = 5;
			treeIndex = 0;
			rockIndex = 10;
			botSpeed = 4;
		}
	}

	public standBoard() {
		//Set game
		stamina = 0;
		win = false;
		hide = false;
		botMove = false;
		fail = false;
		win = false;
		flagAEIOU = false;
	}

}
