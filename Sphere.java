package homework4;

public class Sphere extends Shape
{
	private double radius;
	
	public Sphere(double x, double y, double radius)
	{
		super.x = x;
		super.y = y;
		this.radius = radius;
	}
	
	public double getRadius()
	{
		return radius;
	}
	
	public void setRadius(double radius)
	{
		this.radius = radius;
	}
	
	@Override
	public String print()
	{
		return "0 " + 																//0 = sphere
				Double.toString(super.x) + "," + Double.toString(super.y) +			//location
				" " + Double.toString(radius);										//radius
	}
}
