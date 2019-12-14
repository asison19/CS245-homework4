package homework4;

public class Cylinder extends ModifiedShape
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
	
	// Extract Cylinder data from a String
	// Used for Load
	public void extractData(String data)
	{
		data = data.strip();
		String[] List = data.split("\\s");
		
		setX(Double.parseDouble(List[0]));
		setY(Double.parseDouble(List[1]));
		setRadius(Double.parseDouble(List[2]));
		setHeight(Double.parseDouble(List[3]));
			
	}
	
	@Override
	public String print()
	{
		return "1 " + 																//1 = cylinder
				Double.toString(super.x) + " " + Double.toString(super.y) +			//location
				" " + Double.toString(radius) + 									//radius
				" " + Double.toString(height);										//height
	}
}
