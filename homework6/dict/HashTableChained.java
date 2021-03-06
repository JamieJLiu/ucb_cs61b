package dict;

import list.*;
/**
 *  HashTableChained implements a Dictionary as a hash table with chaining.
 *  All objects used as keys must have a valid hashCode() method, which is
 *  used to determine which bucket of the hash table an entry is stored in.
 *  Each object's hashCode() is presumed to return an int between
 *  Integer.MIN_VALUE and Integer.MAX_VALUE.  The HashTableChained class
 *  implements only the compression function, which maps the hash code to
 *  a bucket in the table's range.
 *
 *  DO NOT CHANGE ANY PROTOTYPES IN THIS FILE.
 **/

public class HashTableChained implements Dictionary {

  /**
   *  Place any data fields here.
   **/
	private static final int SIZEESTIMATE = 100;
	private int numOfEntries;
	private int numOfBk;
	public int collisions;
    private List[] buckets;
    
  /** 
   *  Construct a new empty hash table intended to hold roughly sizeEstimate
   *  entries.  (The precise number of buckets is up to you, but we recommend
   *  you use a prime number, and shoot for a load factor between 0.5 and 1.)
   **/

  public HashTableChained(int sizeEstimate) {
    // Your solution here.
	  numOfBk = (int)(sizeEstimate/0.75);
	  buckets = new List[numOfBk];
	  for(int i = 0; i < numOfBk; i++){
		  buckets[i] = new SList();
		  
	  }
	  numOfEntries = 0;
	  collisions = 0;
	  
  }

  /** 
   *  Construct a new empty hash table with a default size.  Say, a prime in
   *  the neighborhood of 100.
   **/

  public HashTableChained() {
    // Your solution here.
	numOfBk = (int)(SIZEESTIMATE/0.75);
	buckets = new List[numOfBk];
	  for(int i = 0; i < numOfBk; i++){
		  buckets[i] = new SList();
		  
	  }
	  numOfEntries = 0;
	  collisions = 0;
  }

  /**
   *  Converts a hash code in the range Integer.MIN_VALUE...Integer.MAX_VALUE
   *  to a value in the range 0...(size of hash table) - 1.
   *
   *  This function should have package protection (so we can test it), and
   *  should be used by insert, find, and remove.
   **/

  int compFunction(int code) {
    // Replace the following line with your solution.
	  int h;
	  h =  (233333 * code + 23333333) % 16908799;
	  if (h < 0) {
	      h = (h + 16908799)  % 16908799;
	    }
	  
      return h % numOfBk;
  }

  /** 
   *  Returns the number of entries stored in the dictionary.  Entries with
   *  the same key (or even the same key and value) each still count as
   *  a separate entry.
   *  @return number of entries in the dictionary.
   **/

  public int size() {
    // Replace the following line with your solution.
      
	  return numOfEntries;
  }

  /** 
   *  Tests if the dictionary is empty.
   *
   *  @return true if the dictionary has no entries; false otherwise.
   **/

  public boolean isEmpty() {
    // Replace the following line with your solution.
    if(numOfEntries==0){
    	return true;
    }else{
    	return false;
    }
  }

  /**
   *  Create a new Entry object referencing the input key and associated value,
   *  and insert the entry into the dictionary.  Return a reference to the new
   *  entry.  Multiple entries with the same key (or even the same key and
   *  value) can coexist in the dictionary.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the key by which the entry can be retrieved.
   *  @param value an arbitrary object.
   *  @return an entry containing the key and value.
   **/

  public Entry insert(Object key, Object value) {
    // Replace the following line with your solution.
      int b;
	  
	  Entry e = new Entry();
      e.key = key;
      e.value = value;
      
      b =  compFunction(key.hashCode());
      if(! buckets[b].isEmpty()){
    	  collisions++;
      }
      buckets[b].insertBack(e);
	  numOfEntries++;
	  return e;
  }

  /** 
   *  Search for an entry with the specified key.  If such an entry is found,
   *  return it; otherwise return null.  If several entries have the specified
   *  key, choose one arbitrarily and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
 
   **/

  public Entry find(Object key)  {
    // Replace the following line with your solution.
      int b;
      b =  compFunction(key.hashCode());
	  if(buckets[b].isEmpty()){ return null;}
	  try {
			return (Entry)(buckets[b].front().item());
	  } catch (InvalidNodeException e) {
			// TODO Auto-generated catch block
			
	  }
	  return null;
	  
	  
	  
  }

  /** 
   *  Remove an entry with the specified key.  If such an entry is found,
   *  remove it from the table and return it; otherwise return null.
   *  If several entries have the specified key, choose one arbitrarily, then
   *  remove and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   */

  public Entry remove(Object key) {
    // Replace the following line with your solution.
	  int b;
      b =  compFunction(key.hashCode());
      if(! buckets[b].isEmpty()){
    	  try {
			  Entry e =  (Entry)(buckets[b].front().item());
			  buckets[b].front().remove();
			  numOfEntries--;
			  return e;
		  } catch (InvalidNodeException e) {
			  // TODO Auto-generated catch block
			  e.printStackTrace();
		  }
      }
      return null;
      
  }

  /**
   *  Remove all entries from the dictionary.
   */
  public void makeEmpty() {
    // Your solution here.
	  for(int i = 0; i<numOfBk; i++ ){
		  buckets[i] = new SList();
	  }
	  numOfEntries = 0;
	  collisions = 0;
  }

}