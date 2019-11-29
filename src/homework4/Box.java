package homework4;

public class Box extends Shape
{
	private double height;
	private double length;
	private double width;
	
	public Box(double x, double y, double height, double width, double length)
	{
		super.x = x;
		super.y = y;
		this.height = height;
		this.width = width;
		this.length = length;
	}
	
	public double getHeight()
	{
		return height;
	}
	
	public double getWidth()
	{
		return width;
	}
	
	public double getLength()
	{
		return length;
	}
	
	public void setHeight(double height)
	{
		this.height = height;
	}
	
	public void setWidth(double width)
	{
		this.width = width;
	}
	
	public void setLength(double length)
	{
		this.length = length;
	}
	
	@Override
	public String print()
	{
		return "2 " + 																//2 = box
				Double.toString(super.x) + "," + Double.toString(super.y) +			//location
				" " + Double.toString(height) + 									//height
				" " + Double.toString(width) +										//width
				" " + Double.toString(length);										//length
	}
}
