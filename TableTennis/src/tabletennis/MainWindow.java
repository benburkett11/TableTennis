package tabletennis;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.InputStream;

public class MainWindow extends JPanel implements KeyListener, Runnable {

	private final int windowWidth = 600;
	private final int windowHeight = 300;
	private final int paddleLength = 80;
	private final int paddleWidth = 15;
	private final Color paddleColor = new Color(0, 0, 0);
	private final Color ballColor = new Color(255, 0, 0);
	private final Font courier = new Font("Courier", Font.BOLD, 14);
	//private final String picName = "tabletennis_Resources/picture3FIXED.png";

	//private Image image;
	//private InputStream is;
	
	ExecutorService threadExecutor = Executors.newCachedThreadPool();
	
	private Ball ball;
	private Vector<Paddle> paddle;	//computer at index 0, player at index 1
	private boolean playerLost = false;
	private boolean computerLost = false;
	private boolean startGame = false;
	private long cPaddleTimeOfDeath;
	
	private boolean upHeld = false;
	private boolean downHeld = false;
	private boolean incSpeed = true;
	
	public MainWindow()
	{
		super();
		//configure the game
		//configureGUI();
		configureGameData();
		this.setFocusable(true);

		addKeyListener(this);

	}
	
	/*private void configureGUI()
	{	
		
		try {
			is = tabletennis.StartMain.class.getResourceAsStream(picName);

			image = ImageIO.read(is);
		} 
		catch (IOException e) {System.out.println("ARGLERASDA");e.printStackTrace();}
	}*/
	
	private void configureGameData()
	{
		//set the size
		TableObject.playfieldWidth = windowWidth;
		TableObject.playfieldHeight = windowHeight;
		
		paddle = new Vector<Paddle>();
		//create the player's and computer's paddle
		paddle.add(new Paddle(50, (windowHeight/2 - paddleLength/2), paddleWidth, paddleLength, 0));	//computer
		paddle.add(new Paddle(550, (windowHeight/2 - paddleLength/2), paddleWidth, paddleLength, 1));	//player
		//create a new ball
		ball = new Ball(windowWidth/2, windowHeight/2, 15, 10);
	}
	
	public synchronized void playGame()
	{
		while (startGame)
		{
			// Measure the current time in an effort to keep up a consistent frame rate
			long time = System.currentTimeMillis();

			if (playerLost)
			{
				break;
			}
			if (computerLost && cPaddleTimeOfDeath + 3000 < time)
			{
				computerLost = false;
				//ball.reset();
			}
			
			if (!playerLost && !computerLost)
			{
				handleCollisions();
				moveTableObjects();
				repaint();
			}
			
			// Sleep until it's time to draw the next frame 
			// (i.e. 32 ms after this frame started processing)
			try
			{
				long delay = Math.max(0, 32-(System.currentTimeMillis()-time));
							
				Thread.sleep(delay);
			}
			catch(InterruptedException e){e.printStackTrace();}
		}
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		//g.drawImage(image, 0, 0, null);
		g.setColor(paddleColor);
		for (Paddle p : paddle)
			g.fillRect(p.getX(), p.getY(), p.getWidth(), p.getD());
		
		g.setColor(ballColor);
		g.fillOval(ball.getX(), ball.getY(), ball.getD(), ball.getD());
		
		g.setFont(courier);
		g.drawString("SCORE:", 5, 285);
		g.drawString(paddle.get(1).getScore(), 55, 285);
	}
	
	private void handleCollisions()
	{
		int index = 0;
		
		for (Paddle p : paddle)
		{
			if (ball.checkForLoss(p))
			{
				if (index == 0)
				{
					computerLost = true;
					cPaddleTimeOfDeath = System.currentTimeMillis();
					break;
				}
				else if (index == 1)
				{
					playerLost = true;
					break;
				}	
			}/*
			if(ball.overlappingY(p))
			{
				ball.yAxisBounce();
			}
			else*/ if(ball.overlappingX(p))
			{
				ball.xAxisBounce();
			}
			index++;
		}
	}
	
	private void moveTableObjects()
	{		
		updateBall();
		updatePaddle();
	}
	
	private void updatePaddle()
	{
		for (Paddle p : paddle)
		{
			switch(p.pIndex)
			{
			case 0:
				p.trackBall(ball);
				break;
			case 1:
				//Have to handle paddle movement between erasing
				if (upHeld)
					p.moveUp();
				if (downHeld)
					p.moveDown();
				break;
			default:
				break;
			}
		}
	}
	
	private void updateBall()
	{
		ball.move();
		if (paddle.get(1).getIntScore() > 0 && paddle.get(1).getIntScore() % 5 == 0)
		{
			if(incSpeed)
			{
				ball.increaseSpeed();
				incSpeed = false;
			}
		}
		else if (paddle.get(1).getIntScore() % 5 != 0)
		{
			incSpeed = true;
		}
		
	}

	@Override
	public void keyPressed(KeyEvent key) {
		// Mark down which important keys have been pressed
		if(key.getKeyCode() == KeyEvent.VK_UP)
			this.upHeld = true;
		if(key.getKeyCode() == KeyEvent.VK_DOWN)
			this.downHeld = true;
		if(key.getKeyCode() == KeyEvent.VK_ENTER)
		{
			startGame = true;
			threadExecutor.execute(this);
			//threadExecutor.shutdown();
		}
			
	}

	@Override
	public void keyReleased(KeyEvent key) {
		// Mark down which important keys have been pressed
		if(key.getKeyCode() == KeyEvent.VK_UP)
			this.upHeld = false;
		if(key.getKeyCode() == KeyEvent.VK_DOWN)
			this.downHeld = false;
	}

	@Override
	public void keyTyped(KeyEvent arg0) {}

	@Override
	public void run() {
		playGame();
		
	}
}
