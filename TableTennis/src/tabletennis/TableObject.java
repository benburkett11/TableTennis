package tabletennis;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

public class TableObject {
	static int playfieldWidth;
	static int playfieldHeight;
	//private final String paddleBounceSound = "tableTennis_Resources/Paddle.WAV";
	protected int xCord;
	protected int yCord;
	protected int xBound;
	protected int yBound;
	protected int leftXBound;
	protected int rightXBound;
	protected int topYBound;
	protected int bottomYBound;
	protected int objectYMid;
	protected int diameter; //height for the rectangle
	protected int deltaX;
	protected int deltaY;
	protected final int buffer = 12;
	//protected AudioClip clip;
	
	public TableObject(int x, int y, int d,int dX, int dY)
	{
		xCord = x;
		yCord = y;
		diameter = d;
		deltaX = dX;
		deltaY = dY;
		xBound = playfieldWidth - d - buffer;
		yBound = playfieldHeight - d - buffer;
		//try {
		//	clip = Applet.newAudioClip(new URL(paddleBounceSound));
    	//} catch (java.net.MalformedURLException murle) {
        //	System.out.println(murle);
    	//}
		setObjectBounds();
	}
	
	public void setObjectBounds()
	{
		leftXBound = xCord;
		rightXBound = xCord + diameter;
		topYBound = yCord;
		bottomYBound = yCord + diameter;
		objectYMid = yCord + diameter / 2;
	}
	
	public void playBounceSound()
	{
		//clip.play();
	}
	
	public boolean checkForLoss(Paddle p)
	{
		switch(p.pIndex)
		{
		//computer's paddle
		case 0:
			if (leftXBound <= p.leftXBound)
				return true;
			break;
		//player's paddle
		case 1:
			if (rightXBound >= p.rightXBound)
				return true;
			break;
		default:
			break;
		}
		return false;
	}
	
	public boolean overlappingX(Paddle p)
	{
		switch(p.pIndex)
		{
		case 0:
			//checks for the left paddle
			if ((topYBound >= p.topYBound || bottomYBound >= p.topYBound) && 
					((topYBound <= p.bottomYBound) || bottomYBound <= p.bottomYBound))
			{
				if(( leftXBound <= p.rightXBound && rightXBound >= p.rightXBound))
					return true;
				else if (rightXBound >= p.leftXBound && leftXBound <= p.leftXBound)
					return true;
			}
			break;
		case 1:
			//checks for the right paddle
			if (((topYBound >= p.topYBound) || bottomYBound >= p.topYBound) && 
					((topYBound <= p.bottomYBound) || bottomYBound <= p.bottomYBound))
			{
				if( rightXBound >= p.leftXBound && leftXBound <= p.leftXBound)
				{
					p.incScore();
					return true;
				}
				else if (leftXBound <= p.rightXBound && rightXBound >= p.rightXBound)
					return true;
			}
			break;
		default:
			break;
		}		
		return false;
	}
	
	public int getObjectMid()
	{
		return yCord + diameter / 2;
	}
	
	public boolean overlappingY(Paddle p)
	{
		
		if ((leftXBound >= p.leftXBound || rightXBound >= p.leftXBound) &&
				(leftXBound <= p.rightXBound || rightXBound <= p.rightXBound))
		{
			if(bottomYBound >= p.topYBound && topYBound <= p.topYBound)
				return true;
			else if(topYBound <= p.bottomYBound && bottomYBound >= p.bottomYBound)
				return true;
		}
		return false;
	}
	
	public void move()
	{
		xCord += deltaX;
		yCord += deltaY;
		
		if ( xCord < 0 )
		{
			xCord = 0;
			deltaX = -deltaX;
		}
		else if ( xCord > xBound )
		{
			xCord = xBound;
			deltaX = -deltaX;
		}
		
		if ( yCord < 0 )
		{
			yCord = 0;
			deltaY = -deltaY;
		}
		else if ( yCord > yBound )
		{
			yCord = yBound;
			deltaY = -deltaY;
		}
		//call setObjectBounds method to update the shape in "space"
		setObjectBounds();
	}
	
	public int getX()
	{
		return this.xCord;
	}
	
	public int getY()
	{
		return this.yCord;
	}
	
	public int getD()
	{
		return this.diameter;
	}
	
	public int getDX()
	{
		return this.deltaX;
	}
	
	public int getDY()
	{
		return this.deltaY;
	}
}
