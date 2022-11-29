package AEIOU;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import AEIOU.FrameMenu;

public class FrameMenu extends standBoard implements ImageObserver {

	private static JFrame window;
	private static JPanel contentPane;
	private static Clip sound;
	private static AudioInputStream inputStream;

	public static void initWindow() {
		// create a window frame and set the title in the toolbar
		JFrame window = new JFrame("AEIOU");
		window.setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		window.setContentPane(contentPane);
		window.getContentPane().setBackground(Color.WHITE);
		//load sound
		try {
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(pathm + "background.wav"));
			sound = AudioSystem.getClip();
			sound.open(inputStream);
			sound.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

		JPanel blockMain = new JPanel();

		blockMain.setBounds(5, 5, 758, 612);
		blockMain.setBackground(new Color(153, 204, 153));

		JButton babyButton = new JButton("HARD");
		babyButton.setBounds(274, 294, 184, 59);
		babyButton.setForeground(Color.WHITE);
		babyButton.setBackground(new Color(0, 159, 177, 255));
		babyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				window.dispose();
				FrameGame g = new FrameGame(HARD);
				sound.stop();
				g.initWindow();

			}
		});
		babyButton.setFont(new Font("Berlin Sans FB Demi", Font.BOLD, 35));

		JButton noobButton = new JButton("NORMAL");
		noobButton.setBounds(33, 294, 184, 59);
		noobButton.setForeground(Color.WHITE);
		noobButton.setBackground(new Color(199, 126, 158, 255));
		noobButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				window.dispose();
				FrameGame g = new FrameGame(NORMAL);
				sound.stop();
				g.initWindow();
			}
		});
		noobButton.setFont(new Font("Berlin Sans FB Demi", Font.BOLD, 35));

		JButton godButton = new JButton("INSANE");
		godButton.setBounds(508, 294, 184, 59);
		godButton.setForeground(Color.WHITE);
		godButton.setBackground(new Color(0, 109, 96, 255));
		godButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				window.dispose();
				FrameGame g = new FrameGame(INSANE);
				sound.stop();
				g.initWindow();
			}
		});
		contentPane.setLayout(null);
		godButton.setFont(new Font("Berlin Sans FB Demi", Font.BOLD, 35));
		contentPane.add(blockMain);
		blockMain.setLayout(null);
		blockMain.add(noobButton);
		blockMain.add(babyButton);
		blockMain.add(godButton);

		JButton exitButton = new JButton("EXIT");
		exitButton.setBounds(290, 409, 146, 69);
		exitButton.setBackground(Color.WHITE);
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});
		exitButton.setFont(new Font("Berlin Sans FB Demi", Font.BOLD, 44));
		blockMain.add(exitButton);

		JLabel header_1 = new JLabel("");
		header_1.setBounds(-23, 0, 771, 602);
		header_1.setIcon(new ImageIcon(path + "Original.gif"));
		header_1.setHorizontalAlignment(SwingConstants.CENTER);
		header_1.setFont(new Font("Berlin Sans FB Demi", Font.BOLD, 24));
		header_1.setBackground(new Color(102, 255, 153));
		blockMain.add(header_1);
		window.setSize(755, 640);
		// when we close the window, stop the app
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// don't allow the user to resize the window
		window.setResizable(false);
		// open window in the center of the screen
		window.setLocationRelativeTo(null);

		// display the window
		window.setVisible(true);

	}

	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		return false;
	}

}