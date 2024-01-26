import java.awt.Rectangle;

public class Rectangle2 extends Rectangle{

	 public Rectangle2(int x, int y, int width, int height) {
	        super(x, y, width, height);
	    }
	
	public Rectangle2(Rectangle2 value) {
		// TODO Auto-generated constructor stub
		 super(value.x, value.y, value.width, value.height);
	}

	@Override
	    public String toString() {
	        return + x + ", " + y + ", " + width + ", " + height ;
	    }
}