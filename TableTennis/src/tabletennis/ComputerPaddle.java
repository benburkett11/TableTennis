package tabletennis;

public class ComputerPaddle extends TableObject{

	protected int pIndex;
	protected int paddleSpeed = 10;
	protected int lowerYBound = 0;
	protected boolean mySideOfField = false;
	protected boolean towardsComp = false;
	private int score = 0;
	
	protected int slope;
	protected int slopeXCord;
	protected int slopeYCord;
	protected boolean wallBounce = false;
	protected int varB;	//this will hold the b value for slope intercept equation
	protected int yIntercept;	//holder of the xIntercept when y = 0 or y = yBound
	protected int yPaddle;
	protected boolean trackUp = false;
	protected boolean trackBall = false;

	
	public ComputerPaddle(int x, int y, int d, int dX, int dY, int pI)
	{
		super(x, y, d, dX, dY);
		pIndex = pI;
	}
	
	public void trackBall(Ball b)
	{
		getBallData(b);
		if (towardsComp)
		{
			outerif:
			if (trackBall)
			{
				if ( yPaddle > topYBound && yPaddle < (bottomYBound - 15))
					break outerif;
				if (objectYMid < yPaddle)
				{
					moveDown();
				}
				else if (objectYMid > yPaddle)
				{
					moveUp();
				}
			}
			else if(trackUp)
			{
				moveUp();
			}
			else if(!trackUp)
			{
				moveDown();
			}
		}
	}
	
	private void getBallData(Ball b)
	{
		findSlope(b);
		findB(b);
		moveTowardsCompPaddle(b);
		checkBounds(b);
	}
	
	public void incScore()
	{
		score++;
	}
	
	public int getIntScore()
	{
		return score;
	}
	
	public String getScore()
	{
		return Integer.toString(score);
	}
	
	public void findSlope(Ball b)
	{
		slopeXCord = b.xCord + b.deltaX;
		slopeYCord = (-1 * b.yCord) + (-1 * b.deltaY);
		slope = (slopeYCord - (-1 * b.yCord)) / (slopeXCord - b.xCord);
		findB(b);
	}
	
	public void findB(Ball b)
	{
		varB = b.xCord * slope;
		varB = (-1 * b.yCord) - varB;
	}
	
	public void checkBounds(Ball b)
	{
		if (towardsComp)
		{
			//checking this like it is in a the 4th quadrant of a graph, to make these calculations easier
			yIntercept = slope * 0 + varB;
			yPaddle = slope * rightXBound + varB;
			if ( yPaddle <= 0 && yPaddle >= -1 * b.yBound )
			{ 
				trackBall = true;
				yPaddle = -yPaddle;	//turn it back to regular java grid
			}
			else if(yPaddle >= 0)
			{
				trackBall = false;
				trackUp = true;
			}
			else if(yPaddle <= -1 * b.yBound )
			{
				trackBall = false;
				trackUp = false;
			}
				
		}
	}
	
	public void moveTowardsCompPaddle(Ball b)
	{
		//check to see if it is moving towards the computer
		//then check to see if the ball is on the computer's side
		//so we can start tracking it with the computer's paddle
		if (b.xCord > slopeXCord)
		{
			if (slopeXCord < playfieldWidth /2)
			{
				towardsComp = true;
			}
		}
		else if ( b.xCord < slopeXCord)
		{
			towardsComp = false;
		}
	}
	
	public void moveUp()
	{
		deltaY = -paddleSpeed; //deltaY needs to be negative to go up
		yCord += deltaY;
		
		if (yCord <= 0)
		{
			yCord = 0;	
		}
	}
	
	public void moveDown()
	{
		deltaY = paddleSpeed;	//handle if the up or down key was pressed
		yCord += deltaY;
		
		if (yCord >= yBound)
		{
			yCord = yBound;
		}
	}
}
