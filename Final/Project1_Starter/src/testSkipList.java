import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class testSkipList {

	SkipList<String, Rectangle2> list= new SkipList<String, Rectangle2>();
	Database data=new Database();

	@Test
	 public void validInsertion() {
	     
	        int expected = list.size()+1;
	    	KVPair<String, Rectangle2> pair= new KVPair<String, Rectangle2>("a",new Rectangle2(  1 ,1 ,2 ,4));
	    	list.insert(pair);
	        assertEquals(expected, list.size());
	    }
	@Test
	 public void invalidInsertion() {
	     
	        int expected = list.size();
	    	KVPair<String, Rectangle2> pair= new KVPair<String, Rectangle2>("a",new Rectangle2(  -1 ,-1 ,2 ,4));
	    	data.insert(pair);
	        assertEquals(expected, list.size());
	    }
	

	@Test
	public void testRemoveByName() {
		KVPair<String, Rectangle2> pair= new KVPair<String, Rectangle2>("a",new Rectangle2(1, 0, 2, 4));
    	list.insert(pair);
    	 int expected = list.size()-1;
    
    list.remove("a");
    assertEquals(expected, list.size());
}
	@Test
	public void invalidRemoveByName() {
		KVPair<String, Rectangle2> pair= new KVPair<String, Rectangle2>("a",new Rectangle2(1, 0, 2, 4));
    	list.insert(pair);
    	 int expected = list.size();
    
    list.remove("b");
    assertEquals(expected, list.size());
} 
	@Test
	public void searchTest() {
		ArrayList<KVPair<String, Rectangle2>> result = new ArrayList<>();
		KVPair<String, Rectangle2> pair= new KVPair<String, Rectangle2>("r",new Rectangle2(1, 0, 2, 4));
		list.insert(pair);
	
		result.add(pair);
		 
		 assertEquals(result,list.search("r"));
	}
	@Test
	public void notFoundSearchTest() {
		ArrayList<KVPair<String, Rectangle2>> result = new ArrayList<>();
		KVPair<String, Rectangle2> pair= new KVPair<String, Rectangle2>("r",new Rectangle2(1, 0, 2, 4));
		list.insert(pair);
		 assertEquals(result,list.search("c"));
	}
	
	@Test
	public void testRemoveByValue() {
		KVPair<String, Rectangle2> pair= new KVPair<String, Rectangle2>("a",new Rectangle2(1, 0, 2, 4));
    	list.insert(pair);
    	 int expected = list.size()-1;
    
    list.removeByValue(new Rectangle2(1, 0, 2, 4));
    assertEquals(expected, list.size());
}
	@Test
	public void invalidRemoveByValue() {
		KVPair<String, Rectangle2> pair= new KVPair<String, Rectangle2>("a",new Rectangle2(1, 0, 2, 4));
    	list.insert(pair);
    	 int expected = list.size();
    
    list.removeByValue(new Rectangle2(1, 0, 5, 4));
    assertEquals(expected, list.size());
}

}
