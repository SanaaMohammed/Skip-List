import java.util.ArrayList;

/**
 * The purpose of this class is to parse a text file into its appropriate, line
 * by line commands for the format specified in the project spec.
 * 
 * @author CS Staff
 * 
 * @version 2021-08-23
 */
public class CommandProcessor {

    // the database object to manipulate the
    // commands that the command processor
    // feeds to it
    private Database data;

    /**
     * The constructor for the command processor requires a database instance to
     * exist, so the only constructor takes a database class object to feed
     * commands to.
     * 
     * @param dataIn
     *            the database object to manipulate
     */
    public CommandProcessor() {
        data = new Database();
    }


    /**
     * This method identifies keywords in the line and calls methods in the
     * database as required. Each line command will be specified by one of the
     * keywords to perform the actions within the database required. These
     * actions are performed on specified objects and include insert, remove,
     * regionsearch, search, intersections, and dump. If the command in the file line is not
     * one of these, an appropriate message will be written in the console. This
     * processor method is called for each line in the file. Note that the
     * methods called will themselves write to the console, this method does
     * not, only calling methods that do.
     * 
     * @param line
     *            a single line from the text file
     */
    public void processor(String line) {
    	line = line.trim().replaceAll("\\s+", " ");
    	String[] words = line.split(" ");
    int x=0,y=0,w = 0,h=0,n=words.length;
    	String command=words[0];
    	Rectangle2 rect=null;
    	KVPair<String,Rectangle2>pair=null;
    	if(words.length>2){
    		
    		h=Integer.parseInt(words[n-1]);
    		w=Integer.parseInt(words[n-2]);
    		y=Integer.parseInt(words[n-3]);
    		x=Integer.parseInt(words[n-4]);
    		//System.out.println("h = "+h);
    		rect=new Rectangle2(x,y,w,h);
    	}
    	
    	switch (command) {
    	    case "insert":
    	    	data.insert(new KVPair<String,Rectangle2>(words[1],rect));
    	        break;
    	    case "remove":
    	      if(n==2)data.remove(words[1]);
    	      else data.remove(x, y, w, h);
    	        break;
    	    case "regionsearch":
    	      data.regionsearch(x, y, w, h);
    	        break;
    	    case "intersections":
    	       data.intersections();
    	        break;
    	    case "search":
    	        data.search(words[1]);
    	        break;
    	    case "dump":
    	      data.dump();
    	        break;
    	    default:
    	        break;
    	}



    }
}