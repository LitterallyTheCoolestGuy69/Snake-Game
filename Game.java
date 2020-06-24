import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;
public class Game extends JPanel implements KeyListener, ActionListener
{
	private boolean isStarted = false;
	private boolean isWon = false;
	private boolean gameOver = false;
	private boolean gameReallyOver = false;
	private int gameOverCount = 0;
	private int snakeLength;
	private int appleAmt = 3;
	private int snakeSpeed = 100;
	private int headX, headY;
	private int[][] grid;
	private Timer timer;
	private Direction dir;
	private boolean traveledInDirection = false;
	public Game()
	{
		timer = new Timer(snakeSpeed, this);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		startGame();
		
	}
	public boolean isOver()
	{
		return gameReallyOver;
	}
	public void paint(Graphics g)
	{
		System.out.println("here as well");
		if(isStarted)
		{
			if(isWon)
			{
				
			}
			else if(gameOver)
			{
				timer.stop();
				gameOverCount++;
				if(gameOverCount >= 300)
				{
					gameReallyOver = true;
				}
				else
				{
					g.setColor(Color.black);
					g.fillRect(0,0, 500, 500);
					g.setColor(Color.red);
					g.setFont(new Font("Times Roman", 0, 24));
					g.drawString("Game Over", 250, 200);
					update();
				}
			}
			else
			{
				printGrid();
				g.setColor(Color.black);
				g.fillRect(0, 0, 510, 510);
				
				for(int i = 0; i < 20; i++)
				{
					for(int j = 0; j < 20; j++)
					{
						if(grid[i][j] > 0)
						{
							g.setColor(Color.white);
							g.fillRect(j*25,i*25,25,25);
						}
						else if(grid[i][j] < 0)
						{
							g.setColor(Color.red);
							g.fillRect(j*25,i*25,25,25);
						}
					}
				}
				g.setColor(Color.LIGHT_GRAY);
				for(int i = 0; i < 21; i++)
				{
					g.drawLine(25*i,0,25*i,500);
				}
				for(int i = 0; i < 21; i++)
				{
					g.drawLine(0,25*i,500,25*i);
				}
			}
		}
		else
		{
			g.setColor(Color.black);
			g.fillRect(0, 0, 510, 510);
			g.setColor(Color.white);
			g.setFont(new Font("Times Roman", 0, 24));
			g.drawString("Press s to start", 250, 250);
		}
	}
	public void update()
	{
		this.setSize(501,500);
		this.setSize(500,500);
	}
	
	public void startGame()
	{
		isStarted = true;
		grid = new int[20][20];
		snakeLength = 3;
		//This loops fills in the whole grid with empty squares
		for(int i = 0; i < 20; i++)
		{
			for(int j = 0; j < 20; j++)
			{
				grid[i][j] = 0;
			}
		}
				
		grid[10][1] = 1;//Puts the snake in place
		grid[10][2] = 2;//Puts the snake in place
		grid[10][3] = 3;//Puts the snake in place
		dir = Direction.R; //Sets the direction
		grid[10][14] = -3; //Puts the apple in place
		headY = 10;
		headX = 3;
		timer.start();
	}
	
	public void actionPerformed(ActionEvent e)
	{
		System.out.println("Here also");
		timer.start();
		boolean death = false;
		if(dir == Direction.R)
		{
			try
			{
				if(grid[headY][headX+1] == -3)
				{
					grid[headY][headX+1] = grid[headY][headX]+1;
					decrementGrid();
					eatApple();
					headX++;
				}
				else if(grid[headY][headX+1] <= 1)
				{
					grid[headY][headX+1] = grid[headY][headX]+1;
					decrementGrid();
					headX++;
				}
				else
				{
					death = true;
				}
			}
			catch(Exception ex)
			{
				death = true;
			}
			traveledInDirection = true;
		}
		else if(dir == Direction.L)
		{
			try
			{
				if(grid[headY][headX-1] == -3)
				{
					grid[headY][headX-1] = grid[headY][headX]+1;
					decrementGrid();
					eatApple();
					headX--;
				}
				else if(grid[headY][headX-1] <= 1)
				{
					grid[headY][headX-1] = grid[headY][headX]+1;
					decrementGrid();
					headX--;
				}
				else
				{
					death = true;
				}
			}
			catch(Exception ex)
			{
				death = true;
			}
			traveledInDirection = true;
		}
		else if(dir == Direction.U)
		{
			try
			{
				if(grid[headY-1][headX] == -3)
				{
					grid[headY-1][headX] = grid[headY][headX]+1;
					decrementGrid();
					eatApple();
					headY--;
				}
				else if(grid[headY-1][headX] <= 1)
				{
					grid[headY-1][headX] = grid[headY][headX]+1;
					decrementGrid();
					headY--;
				}
				else
				{
					death = true;
				}
			}
			catch(Exception ex)
			{
				death = true;
			}
			traveledInDirection = true;
		}
		else if(dir == Direction.D)
		{
			try
			{
				if(grid[headY+1][headX] == -3)
				{
					grid[headY+1][headX] = grid[headY][headX]+1;
					decrementGrid();
					eatApple();
					headY++;
				}
				else if(grid[headY+1][headX] <= 1)
				{
					grid[headY+1][headX] = grid[headY][headX]+1;
					decrementGrid();
					headY++;
				}
				else
				{
					death = true;
				}
			}
			catch(Exception ex)
			{
				death = true;
			}
			traveledInDirection = true;
		}
		
		if(death)
		{
			gameOver = true;
		}
		this.update();
	}
	
	public void eatApple()
	{
		snakeLength += appleAmt;
		for(int i = 0; i < 20; i++)
		{
			for(int j = 0; j < 20; j++)
			{
				if(grid[i][j]>0)
				{
					grid[i][j] += appleAmt;
				}
			}
		}
		
		boolean openPosition = false;
		for(int i = 0; i < 20; i++)
		{
			for(int j = 0; j < 20; j++)
			{
				if(grid[i][j] == 0)
					openPosition = true;
			}
		}
		if(openPosition)
		{
			int x = (int)(Math.random()*20);
			int y = (int)(Math.random()*20);
			while(grid[x][y] != 0)
			{
				x = (int)(Math.random()*20);
				y = (int)(Math.random()*20);
			}
			grid[x][y] = -3;
		}
		else
		{
			isWon = true;
		}
	}
	
	public void decrementGrid()
	{
		for(int i = 0; i < 20; i++)
		{
			for(int j = 0; j < 20; j++)
			{
				if(grid[i][j] > 0)
					grid[i][j]--;
			}
		}
	}
	
	
	public void keyPressed(KeyEvent e) 
	{
		System.out.println("here");
		if(!isStarted && e.getKeyCode() == KeyEvent.VK_S)
		{
			System.out.println("Got here");
		}
		else if(isStarted)
		{
			if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			{
				if(dir != Direction.L && traveledInDirection == true)
				{
					dir = Direction.R;
					traveledInDirection = false;
				}
			}
			else if(e.getKeyCode() == KeyEvent.VK_LEFT)
			{
				if(dir != Direction.R && traveledInDirection == true)
				{
					dir = Direction.L;
					traveledInDirection = false;
				}
			}
			else if(e.getKeyCode() == KeyEvent.VK_UP)
			{
				if(dir != Direction.D && traveledInDirection == true)
				{
					dir = Direction.U;
					traveledInDirection = false;
				}
			}
			else if(e.getKeyCode() == KeyEvent.VK_DOWN)
			{
				if(dir != Direction.U && traveledInDirection == true)
				{
					dir = Direction.D;
					traveledInDirection = false;
				}
			}
		}
	}
	public void printGrid()
	{
		for(int i = 0; i < 20; i++)
		{
			 for(int j = 0; j < 20; j++)
			 {
				 System.out.print(grid[i][j] + " ");
			 }
			 System.out.println();
		}
	}
	
	public void keyReleased(KeyEvent e) {	}
	public void keyTyped(KeyEvent e) {   }
	
	enum Direction
	{
		U,D,L,R;
		public Direction opposite()
		{
			if(this == U)
				return D;
			else if(this == D)
				return U;
			else if(this == L)
				return R;
			else if(this == R)
				return L;
			return R;
		}
	}
}
