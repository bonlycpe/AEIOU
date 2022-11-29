package AEIOU;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

public class FrameGame extends standBoard implements ImageObserver {

	public static final int ROWS = 1170;
	public static final int COLUMNS = 810;

	private static Font font1 = new Font("Berlin Sans FB Demi", Font.BOLD, 56);
	private static JLabel countLabel;
	private static JLabel A;
	private static JLabel E;
	private static JLabel I;
	private static JLabel O;
	private static JLabel U;
	private static JLabel roll;
	private static JLabel text1;
	
	// image that represents the girl's position on the board
	private static JLabel image;
	private static Timer timer;
	private static JPanel contentPane;

	private static int count = 1; // image
	private static String ddSecond, ddMinute;
	private static DecimalFormat dFormat = new DecimalFormat("00");

	private static JPanel AEIOUBlock;
	private static JPanel staminaBlock;
	private static JPanel textBlock;
	private static JPanel timeBlock;
	private static JPanel imBlock;
	
	//sound in game
	private static Clip soundGun;
	private static Clip soundA;
	private static Clip soundE;
	private static Clip soundI;
	private static Clip soundO;
	private static Clip soundU;
	
	private static int confirmMove = 0;
	
	
	public FrameGame(int a) {
		mode = a;
	}

	public static void initWindow() {
		// create a window frame and set the title in the toolbar
		window = new JFrame("AEIOU");
		window.setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		window.setContentPane(contentPane);
		window.getContentPane().setBackground(Color.WHITE);
		window.setSize(1033, 829);
		// when we close the window, stop the app
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// create the jpanel to draw on.
		// this also initializes the game loop
		Board board = new Board();
		window.addKeyListener(board);

		second = 0;
		minute = 1;
		simpleTimer();
		timer.start();

		AEIOUBlock = new JPanel();
		AEIOUBlock.setBackground(Color.GREEN);

		staminaBlock = new JPanel();
		staminaBlock.setBackground(Color.YELLOW);

		textBlock = new JPanel();
		textBlock.setBackground(Color.PINK);

		timeBlock = new JPanel();
		timeBlock.setBackground(Color.ORANGE);

		imBlock = new JPanel();
		imBlock.setBackground(new Color(28, 105, 223, 255));

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup().addGap(27)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(staminaBlock, GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)
						.addComponent(textBlock, GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)
						.addComponent(timeBlock, GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)
						.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
								.addComponent(AEIOUBlock, GroupLayout.PREFERRED_SIZE, 174, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(imBlock, GroupLayout.PREFERRED_SIZE, 178, GroupLayout.PREFERRED_SIZE)
								.addGap(8)))
				.addGap(32).addComponent(board, GroupLayout.PREFERRED_SIZE, 594, GroupLayout.PREFERRED_SIZE)
				.addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(board, GroupLayout.DEFAULT_SIZE, 769, Short.MAX_VALUE)
								.addGroup(gl_contentPane.createSequentialGroup()
										.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addComponent(imBlock, 0, 0, Short.MAX_VALUE).addComponent(AEIOUBlock,
														GroupLayout.PREFERRED_SIZE, 496, GroupLayout.PREFERRED_SIZE))
										.addGap(18)
										.addComponent(staminaBlock, GroupLayout.PREFERRED_SIZE, 82,
												GroupLayout.PREFERRED_SIZE)
										.addGap(10)
										.addComponent(textBlock, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addComponent(timeBlock, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addContainerGap()))));

		image = new JLabel("");
		image.setHorizontalAlignment(SwingConstants.CENTER);
		image.setForeground(Color.BLACK);
		image.setFont(new Font("Berlin Sans FB Demi", Font.BOLD, 21));
		image.setBackground(Color.BLACK);
		GroupLayout gl_imBlock = new GroupLayout(imBlock);
		gl_imBlock.setHorizontalGroup(gl_imBlock.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_imBlock.createSequentialGroup().addContainerGap()
						.addComponent(image, GroupLayout.PREFERRED_SIZE, 166, Short.MAX_VALUE).addContainerGap()));
		gl_imBlock.setVerticalGroup(gl_imBlock.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_imBlock.createSequentialGroup().addContainerGap()
						.addComponent(image, GroupLayout.PREFERRED_SIZE, 434, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(52, Short.MAX_VALUE)));
		imBlock.setLayout(gl_imBlock);

		countLabel = new JLabel("01:00");
		countLabel.setFont(new Font("Berlin Sans FB Demi", Font.BOLD, 70));
		countLabel.setHorizontalAlignment(SwingConstants.CENTER);
		countLabel.setBounds(96, 71, 200, 50);

		JLabel lblPressBackspaceBack = new JLabel("Press Backspace back to menu");
		lblPressBackspaceBack.setForeground(Color.RED);
		lblPressBackspaceBack.setFont(new Font("Berlin Sans FB Demi", Font.BOLD, 18));

		GroupLayout gl_timeBlock = new GroupLayout(timeBlock);
		gl_timeBlock
				.setHorizontalGroup(gl_timeBlock.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING,
						gl_timeBlock.createSequentialGroup().addContainerGap(60, Short.MAX_VALUE)
								.addGroup(gl_timeBlock.createParallelGroup(Alignment.LEADING)
										.addComponent(countLabel, GroupLayout.PREFERRED_SIZE, 230,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(lblPressBackspaceBack, GroupLayout.PREFERRED_SIZE, 269,
												GroupLayout.PREFERRED_SIZE))
								.addGap(37)));
		gl_timeBlock.setVerticalGroup(gl_timeBlock.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_timeBlock.createSequentialGroup().addGap(20)
						.addComponent(countLabel, GroupLayout.PREFERRED_SIZE, 57, Short.MAX_VALUE).addGap(18)
						.addComponent(lblPressBackspaceBack, GroupLayout.PREFERRED_SIZE, 21,
								GroupLayout.PREFERRED_SIZE)));
		timeBlock.setLayout(gl_timeBlock);

		text1 = new JLabel("Please Enter to boost stamina");
		textBlock.add(text1);
		text1.setFont(new Font("Berlin Sans FB Demi", Font.BOLD, 18));

		roll = new JLabel("Boost Stamina");
		staminaBlock.add(roll);
		roll.setBackground(Color.WHITE);
		roll.setForeground(Color.BLACK);
		roll.setHorizontalAlignment(SwingConstants.CENTER);
		roll.setFont(new Font("Berlin Sans FB Demi", Font.BOLD, 55));

		image.setIcon(new ImageIcon(path + "1.jpg"));

		E = new JLabel("E");
		E.setHorizontalAlignment(SwingConstants.CENTER);
		E.setFont(font1);

		A = new JLabel("A");
		A.setHorizontalAlignment(SwingConstants.CENTER);
		A.setFont(font1);
		I = new JLabel("I");
		I.setHorizontalAlignment(SwingConstants.CENTER);
		I.setFont(font1);
		O = new JLabel("O");
		O.setHorizontalAlignment(SwingConstants.CENTER);
		O.setFont(font1);
		U = new JLabel("U");
		U.setHorizontalAlignment(SwingConstants.CENTER);
		U.setFont(font1);
		GroupLayout gl_AEIOUBlock = new GroupLayout(AEIOUBlock);
		gl_AEIOUBlock.setHorizontalGroup(gl_AEIOUBlock.createParallelGroup(Alignment.LEADING).addGroup(gl_AEIOUBlock
				.createSequentialGroup()
				.addGroup(gl_AEIOUBlock.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_AEIOUBlock.createSequentialGroup().addContainerGap().addComponent(O))
						.addGroup(Alignment.LEADING, gl_AEIOUBlock.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_AEIOUBlock.createSequentialGroup().addContainerGap().addComponent(U))
								.addGroup(Alignment.LEADING,
										gl_AEIOUBlock.createSequentialGroup().addGap(61)
												.addGroup(gl_AEIOUBlock.createParallelGroup(Alignment.LEADING, false)
														.addComponent(A, GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(E, GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(I, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
				.addContainerGap(70, Short.MAX_VALUE)));
		gl_AEIOUBlock.setVerticalGroup(gl_AEIOUBlock.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_AEIOUBlock.createSequentialGroup().addGap(35).addComponent(A).addGap(18).addComponent(E)
						.addPreferredGap(ComponentPlacement.RELATED, 29, Short.MAX_VALUE).addComponent(I).addGap(18)
						.addComponent(O).addGap(18).addComponent(U).addGap(69)));
		AEIOUBlock.setLayout(gl_AEIOUBlock);
		contentPane.setLayout(gl_contentPane);

		// window.add(timer);

		// window.add(board);
		// pass keyboard inputs to the jpanel
		// don't allow the user to resize the window
		window.setResizable(false);
		// open window in the center of the screen
		window.setLocationRelativeTo(null);

		// display the window
		window.setVisible(true);

	}

	public static void simpleTimer() {
        count = 0;
        timer = new Timer(1000, new ActionListener() {
            @SuppressWarnings("static-access")
            public void actionPerformed(ActionEvent e) {
                try {
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(pathm + "gun.wav"));
                    soundGun = AudioSystem.getClip();
                    soundGun.open(inputStream);
                    AudioInputStream inputA = AudioSystem.getAudioInputStream(new File(pathm + "A.wav"));
                    soundA = AudioSystem.getClip();
                    soundA.open(inputA);
                    AudioInputStream inputE = AudioSystem.getAudioInputStream(new File(pathm + "E.wav"));
                    soundE = AudioSystem.getClip();
                    soundE.open(inputE);
                    AudioInputStream inputI = AudioSystem.getAudioInputStream(new File(pathm + "I.wav"));
                    soundI = AudioSystem.getClip();
                    soundI.open(inputI);
                    AudioInputStream inputO = AudioSystem.getAudioInputStream(new File(pathm + "O.wav"));
                    soundO = AudioSystem.getClip();
                    soundO.open(inputO);
                    AudioInputStream inputU = AudioSystem.getAudioInputStream(new File(pathm + "U.wav"));
                    soundU = AudioSystem.getClip();
                    soundU.open(inputU);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                if (stamina == 0) {
                    roll.setText("Boost Stamina");

                } else {
                    roll.setText("Stamina -> " + stamina);
                    flagAEIOU = true;
                }
                if (flagAEIOU) {
                    second--;
                    count++;
                    
                    Random r = new Random();
                    int x = r.nextInt(4)+1;
                    
                    if(x==0)
                    	confirmMove++;
                    else
                    	confirmMove--;
                    if(confirmMove==2)
                    {
                    	x=3;
                    	confirmMove=0;
                    }
                    
                    A.setForeground(Color.RED);
                    switch (count % 5) {
                    case 1:
                    	soundU.stop();
                    	soundA.start();
                        E.setForeground(Color.BLACK);
                        I.setForeground(Color.BLACK);
                        O.setForeground(Color.BLACK);
                        U.setForeground(Color.BLACK);
                        if(x==1)
                        {
                            botMove = true;
                        }
                        break;
                    case 2:
                    	soundA.stop();
                    	soundE.start();
                        E.setForeground(Color.RED);
                        if(x==2)
                        {
                            botMove = true;
                        }
                        break;
                    case 3:
                    	soundE.stop();
                    	soundI.start();
                        I.setForeground(Color.RED);
                        if(x==3)
                        {
                            botMove = true;
                        }
                        break;
                    case 4:
                    	soundI.stop();
                    	soundO.start();
                        O.setForeground(Color.RED);
                        if(x==4)
                        {
                            botMove = true;
                        }
                        break;
                    case 0:
                    	soundO.stop();
                    	soundU.start();
                        U.setForeground(Color.RED);
                        if (stamina > 0) {
                            if (!hide)
                                fail = true;
                        }
                        break;
                    }
                    ddSecond = dFormat.format(second);
                    ddMinute = dFormat.format(minute);
                    countLabel.setText(ddMinute + ":" + ddSecond);
                    if (second == -1) {
                        second = 59;
                        minute--;
                        ddSecond = dFormat.format(second);
                        ddMinute = dFormat.format(minute);
                        countLabel.setText(ddMinute + ":" + ddSecond);
                    }
                    
                    if (minute == 0 && second == 0) {
                        fail = true;
                    }
                    
                    if (fail) {
                    	soundGun.start();
                    	image.setIcon(new ImageIcon(path+"4.png"));
                    }
                    else {
                        if (count % 2 == 1) {
                            if (second % 5 == 0) {
                                image.setIcon(new ImageIcon(path+"3.jpg"));
                            } else
                                image.setIcon(new ImageIcon(path+"1.jpg"));
                        } else {
                            if (second % 5 == 0) {
                                image.setIcon(new ImageIcon(path+"3.jpg"));
                            } else
                                image.setIcon(new ImageIcon(path+"2.jpg"));
                        }
                    }

                    if (fail || win) {
                        timer.stop();
                        if (win) {
                            int res = JOptionPane.showOptionDialog(new JFrame(), "Do you want to play again?",
                                    "WIN!!!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                                    new Object[] { "Yes", "No" }, JOptionPane.YES_OPTION);
                            if (res == JOptionPane.YES_OPTION) {
                                window.dispose();
                                timer.stop();
                                new FrameGame(mode).initWindow();

                            } else if (res == JOptionPane.NO_OPTION) {
                                window.dispose();
                                new FrameMenu().initWindow();
                            }
                        } else {        
                            int res = JOptionPane.showOptionDialog(new JFrame(), "Do you want to play again?",
                                    "GameOver!!!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                                    new Object[] { "Yes", "No" }, JOptionPane.YES_OPTION);
                            if (res == JOptionPane.YES_OPTION) {
                                window.dispose();
                                timer.stop();
                                new FrameGame(mode).initWindow();
                            } else if (res == JOptionPane.NO_OPTION) {
                                window.dispose();
                                new FrameMenu().initWindow();
                            }
                        }
                    }
                }
            }

        });
    }

	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		return false;
	}

}