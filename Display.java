import java.awt.Color;

import javax.swing.JFrame;

public class Display
{
	public static void main(String[] args)
	{
		while(true)
		{
			startAGame();
		}
		
	}
	public static boolean startAGame()
	{
		JFrame screen;
		Game game;

		screen = new JFrame();
		game = new Game();
	
		screen.setBounds(10,10,510,510);
		screen.setBackground(Color.LIGHT_GRAY);
		screen.setResizable(true);
		screen.setVisible(true);
		screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		screen.setSize(800, 800);
		screen.addKeyListener(game);
		screen.add(game);
		while(!game.isOver()) {}
		return true;
	}
}
