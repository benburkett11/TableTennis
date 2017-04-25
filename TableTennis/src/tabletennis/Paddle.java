package tabletennis;

public class Paddle extends ComputerPaddle{

	protected int width;

	
	public Paddle(int x, int y, int w, int h, int i)
	{
		super(x, y, h, 0, 0, i);
		width = w;
		setObjectBounds();
	}
	
	//over ride parents setObjectBounds method to handle the paddles width
	public void setObjectBounds()
	{
		super.setObjectBounds();
		rightXBound = xCord + width; //TableObect setBounds is for a circle, paddle is more specific
	}
	
	public int getWidth()
	{
		return width;
	}
	//over ride parent move, to fix objectbounds
	public void moveDown()
	{
		//call the moveDown method
		super.moveDown();
		//call setObjectBounds method to update the shape in "space"
		setObjectBounds();
	}
	public void moveUp()
	{
		//call the moveUp method
		super.moveUp();
		//call setObjectBounds method to update the shape in "space"
		setObjectBounds();
	}
}
