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
	
	// Extract Sphere data from a String
	// Used for Load
	public void extractData(String data)
	{
		data = data.strip();
		String[] List = data.split("\\s");
		
		setX(Double.parseDouble(List[0]));
		setY(Double.parseDouble(List[1]));
		setRadius(Double.parseDouble(List[2]));
			
	}
	
	@Override
	public String print()
	{
		return "0 " + 																//0 = sphere
				Double.toString(super.x) + " " + Double.toString(super.y) +			//location
				" " + Double.toString(radius);										//radius
	}
}
