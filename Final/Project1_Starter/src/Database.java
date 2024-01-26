import java.util.ArrayList;

//import java.awt.Rectangle;

/**
 * This class is responsible for interfacing between the command processor and
 * the SkipList. The responsibility of this class is to further interpret
 * variations of commands and do some error checking of those commands. This
 * class further interpreting the command means that the two types of remove
 * will be overloaded methods for if we are removing by name or by coordinates.
 * Many of these methods will simply call the appropriate version of the
 * SkipList method after some preparation.
 * 
 * @author CS Staff
 * 
 * @version 2021-08-23
 */
public class Database {

    // this is the SkipList object that we are using
    // a string for the name of the rectangle and then
    // a rectangle object, these are stored in a KVPair,
    // see the KVPair class for more information
    private SkipList<String, Rectangle2> list;

    /**
     * The constructor for this class initializes a SkipList object with String
     * and Rectangle a its parameters.
     */
    public Database() {
        list = new SkipList<String, Rectangle2>();
       
    }


    /**
     * Inserts the KVPair in the SkipList if the rectangle has valid coordinates
     * and dimensions, that is that the coordinates are non-negative and that
     * the rectangle object has some area (not 0, 0, 0, 0). This insert will
     * insert the KVPair specified into the sorted SkipList appropriately
     * 
     * @param pair
     *            the KVPair to be inserted
     */
    public void insert(KVPair<String, Rectangle2> pair) {
    	Rectangle2 temp=new Rectangle2(pair.getValue());
    	boolean valid=true,found=false;
    	
    	if(temp.x<0||temp.y<0||temp.width<=0||temp.height<=0)
    		valid=false;
    
    	if(!(temp.width*temp.height>0))valid=false;
    	if(temp.y+temp.height>1024||temp.x+temp.width>1024)valid=false;
    	
    	ArrayList<KVPair<String, Rectangle2>> result=list.search(pair.getKey());
   
    	if(!result.isEmpty())  {
    		
    		for(int i=0;i<result.size();i++) {
    			
    			if(result.get(i).getKey().equals(pair.getKey())&&result.get(i).getValue().equals(pair.getValue()))
    				found=true;
    			
    		}
    		
    	}
    	if(found)return;
    	if(valid) {
    		list.insert(pair);
    		System.out.println("Rectangle inserted: ("+pair.getKey()+", "+temp.toString()+")");
    	}
    	else
    		System.out.println("Rectangle rejected: ("+pair.getKey()+", "+temp.toString()+")");

    }


    /**
     * Removes a rectangle with the name "name" if available. If not an error
     * message is printed to the console.
     * 
     * @param name
     *            the name of the rectangle to be removed
     */
    public void remove(String name) {
    	KVPair<String,Rectangle2>temp=list.remove(name);
    	if(temp==null) 
    		System.out.println("Rectangle not found: ("+name+")");
    	else 
    		System.out.println("Rectangle removed: ("+temp.getKey().toString()+", "+temp.getValue().toString()+")");

    }


    /**
     * Removes a rectangle with the specified coordinates if available. If not
     * an error message is printed to the console.
     * 
     * @param x
     *            x-coordinate of the rectangle to be removed
     * @param y
     *            x-coordinate of the rectangle to be removed
     * @param w
     *            width of the rectangle to be removed
     * @param h
     *            height of the rectangle to be removed
     */
    public void remove(int x, int y, int w, int h) {
    	KVPair<String,Rectangle2>temp=list.removeByValue(new Rectangle2(x,y,w,h));
    	if(temp==null)
    		  System.out.println("Rectangle not found: ("+new Rectangle2(x,y,w,h).toString()+")");
    	else 
    		System.out.println("Rectangle removed: ("+temp.getKey().toString()+", "+temp.getValue().toString()+")");
    }


    /**
     * Displays all the rectangles inside the specified region. The rectangle
     * must have some area inside the area that is created by the region,
     * meaning, Rectangles that only touch a side or corner of the region
     * specified will not be said to be in the region. You will need a SkipList Iterator for this 
     * 
     * @param x
     *            x-Coordinate of the region
     * @param y
     *            y-Coordinate of the region
     * @param w
     *            width of the region
     * @param h
     *            height of the region
     */
    public void regionsearch(int x, int y, int w, int h) {
    	Rectangle2 rect=new Rectangle2(x,y,w,h);
    	if(x<0||y<0||!(w>0&&h>0))
    		System.out.println("Rectangle rejected: ("+rect.toString()+")");
    	else {
    		System.out.println("Rectangles intersecting region ("+rect.toString()+"):");
    		for(KVPair<String ,Rectangle2>pair:list) {
    			if(rect.intersects(pair.getValue()))
    				System.out.println("("+pair.getKey()+", "+pair.getValue().toString()+")");
    		}
    		
    	}
    	

    }



    /**
     * Prints out all the rectangles that Intersect each other by calling the
     * SkipList method for intersections. You will need to use two SkipList Iterators for this
     */
    public void intersections() {
    	System.out.println("Intersection pairs:");
    	ArrayList<KVPair<String, Rectangle2>> rectangles = new ArrayList<KVPair<String, Rectangle2>>();
    	
    	for(KVPair<String ,Rectangle2>pair:list) {
    		if(pair.equals(null)!=true)
    		rectangles.add(pair);
    	}
    	
    
    	
    	for(int i=0;i<rectangles.size();i++) {
    		for(int j=0;j<rectangles.size();j++) {
    			if(i==j)continue;
    			if (rectangles.get(i).getValue().intersects(rectangles.get(j).getValue())) {
    				System.out.println("("+rectangles.get(i).getKey()+", "+rectangles.get(i).getValue().toString()+" | "+
    						rectangles.get(j).getKey()+", "+rectangles.get(j).getValue().toString()+")");
    			}
    			}
    		}
    	
    	
    }
    


    /**
     * Prints out all the rectangles with the specified name in the SkipList.
     * This method will delegate the searching to the SkipList class completely.
     * 
     * @param name
     *            name of the Rectangle to be searched for
     */
    public void search(String name) {
    	ArrayList<KVPair<String, Rectangle2>> result=list.search(name);
    	
    	if(result.isEmpty())  System.out.println("Rectangle not found: "+name);
    	else {
    		System.out.println("Rectangle found:");
    		for(int i=0;i<result.size();i++)
    			System.out.println("("+result.get(i).getKey()+", "+result.get(i).getValue().toString()+")");
    			
    		
    	}
    }


    /**
     * Prints out a dump of the SkipList which includes information about the
     * size of the SkipList and shows all of the contents of the SkipList. This
     * will all be delegated to the SkipList.
     */
    public void dump() {
        list.dump();
    }
}