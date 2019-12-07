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
	
	public void extractData(String data)
	{
		data = data.strip();
		String[] List = data.split("\\s");
		
		setX(Double.parseDouble(List[0]));
		setY(Double.parseDouble(List[1]));
		setHeight(Double.parseDouble(List[2]));
		setWidth(Double.parseDouble(List[3]));
		setLength(Double.parseDouble(List[4]));
	}
	
	@Override
	public String print()
	{
		return "2 " + 																//2 = box
				Double.toString(super.x) + " " + Double.toString(super.y) +			//location
				" " + Double.toString(height) + 									//height
				" " + Double.toString(width) +										//width
				" " + Double.toString(length);										//length
	}
}
