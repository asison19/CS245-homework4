package homework4;

public class Cylinder extends Shape
{
	private double radius;
	private double height;
	
	public Cylinder(double x, double y, double radius, double height)
	{
		super.x = x;
		super.y = y;
		this.radius = radius;
		this.height = height;
	}
	
	public double getRadius()
	{
		return radius;
	}
	
	public double getHeight()
	{
		return height;
	}
	
	public void setRadius(double radius)
	{
		this.radius = radius;
	}
	
	public void setHeight(double height)
	{
		this.height = height;
	}
	
	@Override
	public String print()
	{
		return "1 " + 																//1 = cylinder
				Double.toString(super.x) + "," + Double.toString(super.y) +			//location
				" " + Double.toString(radius) + 									//radius
				" " + Double.toString(height);										//height
	}
}
