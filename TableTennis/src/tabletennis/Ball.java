package tabletennis;

public class Ball extends TableObject{
	
	public Ball(int x, int y, int d, int delta)
	{
		super(x, y, d, delta, delta);
	}
	
	public void xAxisBounce()
	{
		deltaX = -deltaX;
		playBounceSound();
	}
	
	public void yAxisBounce()
	{
		deltaY = -deltaY;
		playBounceSound();
	}
	
	public void increaseSpeed()
	{
		if(Math.abs(deltaX) >= Math.abs(deltaY))
		{
			if (deltaY > 0)
				deltaY += 2;
			else
				deltaY -= 2;
			System.out.println("deltaY: " + deltaY);
		}
		else
		{
			if (deltaX > 0)
				deltaX += 2;
			else
				deltaX -= 2;
			System.out.println("deltaX: " + deltaX);
		}
	}
}
