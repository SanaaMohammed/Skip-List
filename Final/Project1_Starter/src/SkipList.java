import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


/**
 * This class implements SkipList data structure and contains an inner SkipNode
 * class which the SkipList will make an array of to store data.
 * 
 * @author CS Staff
 * 
 * @version 2021-08-23
 * @param <K>
 *            Key
 * @param <V>
 *            Value
 */
public class SkipList<K extends Comparable<? super K>, V>
    implements Iterable<KVPair<K, V>> {
    private SkipNode head; // First element of the top level
    private int size; // number of entries in the Skip List
    int totalLevels;

    /**
     * Initializes the fields head, size and level
     */
    public SkipList() {
        head = new SkipNode(null, 0);
        size = 0;
        totalLevels=0;
    }


    /**
     * Returns a random level number which is used as the depth of the SkipNode
     * 
     * @return a random level number
     */
    int randomLevel() {
        int lev;
        Random value = new Random();
        for (lev = 0; Math.abs(value.nextInt()) % 2 == 0; lev++) {
            // Do nothing
        }
        return lev; // returns a random level
    }


    /**
     * Searches for the KVPair using the key which is a Comparable object.
     * 
     * @param key
     *            key to be searched for
     */
    public ArrayList<KVPair<K, V>> search(K key) {
    	 ArrayList<KVPair<K, V>> result = new ArrayList<>();
    	 SkipNode temp=head;
    	 
    	 for(int i=totalLevels;i>=0;i--) {
    		 
    		 while(temp.forward[i]!=null&&temp.forward[i].element().getKey().compareTo(key)<=0)
    		 {
    			
    			 temp = temp.forward[i];
    			  if (temp.element().getKey().compareTo(key)==0) {
    	                result.add(temp.pair);
    	            }
    		 }
    		 
    	 }
    	
    	 return result;
    
    }



    /**
     * @return the size of the SkipList
     */
    public int size() {
        return size;
    }


    @SuppressWarnings("unchecked")
    public void insert(KVPair<K, V> it) {
    	int level=randomLevel();
    	
    	if(level>totalLevels) 
    		adjustHead(level);
    	
    	SkipNode[] update=(SkipNode[])Array.newInstance(SkipList.SkipNode.class, level+1);
    	SkipNode temp=head;
    	
    	for(int i=level;i>=0;i--) 
    	{
    		while(temp.forward[i]!=null&&temp.forward[i].pair.compareTo(it)<0)
    		temp=temp.forward[i];
    		
    		update[i]=temp;
    	}
    	
    	temp=new SkipNode(it,level);
    	
    	for(int i=0;i<=level;i++)
    	{
    		temp.forward[i]=update[i].forward[i];
    		update[i].forward[i]=temp;
    		
    	}
    	
    ++size;
    }


    @SuppressWarnings("unchecked")
    private void adjustHead(int newLevel) {
    	SkipNode temp=head;
    	head=new SkipNode(new KVPair(null,null),newLevel);
    	
    	for(int i=0;i<=totalLevels;i++)
    		head.forward[i]=temp.forward[i];
    		
    		
    	totalLevels =newLevel;
    	
    }



    /**
     * Removes the KVPair that is passed in as a parameter and returns true if
     * the pair was valid and false if not.
     * 
     * @param pair
     *            the KVPair to be removed
     * @return returns the removed pair if the pair was valid and null if not
     */

    @SuppressWarnings("unchecked")
    public KVPair<K, V> remove(K key) {
    	SkipNode []update=(SkipNode [])Array.newInstance(SkipList.SkipNode.class, totalLevels+1);
    	SkipNode temp=head;
    	for (int i = temp.level; i >= 0; i--) {
    		while (temp.forward[i] != null && temp.forward[i].element().getKey().compareTo(key) < 0) {
    				temp = temp.forward[i];
    		}
    	update[i] = temp;
    	} 	
    	temp = temp.forward[0];

    	if (temp != null && temp.element().getKey().equals(key) ) {
    		for (int i = 0; i <= totalLevels; i++) {
    			if (update[i].forward[i] != temp) {
    				break;
    			}
    			update[i].forward[i] = temp.forward[i];
    		}
    		size--;
    		return temp.pair;
    	}
    	
    	return null;    
}




    /**
     * Removes a KVPair with the specified value.
     * 
     * @param val
     *            the value of the KVPair to be removed
     * @return returns true if the removal was successful
     */
    public KVPair<K, V> removeByValue(V val) {
    	
    	  SkipNode temp = head.forward[0];
    	 
    	  while(temp!=null) {
    		  if(temp.element().getValue().equals(val)) { 
    			  
    			  return remove(temp.element().getKey());
    		  }
    		  temp=temp.forward[0];
    	  }
        return null;
    }


    /**
     * Prints out the SkipList in a human readable format to the console.
     */
    public void dump() {
    	System.out.println("SkipList dump:");
    	SkipNode temp=head;
    	System.out.println("Node has depth "+temp.forward.length+", Value (null)");
    	temp=temp.forward[0];
    	while(temp!=null) {
    		   System.out.println("Node has depth " + temp.forward.length+
    			 ", Value ("+temp.element().getKey()+", "+temp.element().getValue().toString()+")");
    	temp=temp.forward[0];
    	}
    	 System.out.println("SkipList size is: " + size);
    }

    /**
     * This class implements a SkipNode for the SkipList data structure.
     * 
     * @author CS Staff
     * 
     * @version 2016-01-30
     */
    private class SkipNode {

        // the KVPair to hold
        private KVPair<K, V> pair;
        // what is this
        private SkipNode [] forward;
        // the number of levels
        private int level;

        /**
         * Initializes the fields with the required KVPair and the number of
         * levels from the random level method in the SkipList.
         * 
         * @param tempPair
         *            the KVPair to be inserted
         * @param level
         *            the number of levels that the SkipNode should have
         */
        @SuppressWarnings("unchecked")
        public SkipNode(KVPair<K, V> tempPair, int level) {
            pair = tempPair;
            forward = (SkipNode[])Array.newInstance(SkipList.SkipNode.class,
                level + 1);
            this.level = level;
        }


        /**
         * Returns the KVPair stored in the SkipList.
         * 
         * @return the KVPair
         */
        public KVPair<K, V> element() {
            return pair;
        }

    }


    private class SkipListIterator implements Iterator<KVPair<K, V>> {
        private SkipNode current;

        public SkipListIterator() {
            current = head.forward[0];
        }


        @Override
        public boolean hasNext() {
        	 return current != null && current.element().getKey() != null;
        }


        @Override
        public KVPair<K, V> next() {
        	 KVPair<K, V> result = current.pair;
        	 
             current = current.forward[0];
             return result;
        }

    }

    @Override
    public Iterator<KVPair<K, V>> iterator() {
        // TODO Auto-generated method stub
        return new SkipListIterator();
    }

}