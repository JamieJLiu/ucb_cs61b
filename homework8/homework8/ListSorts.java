package homework8;

import list.*;

public class ListSorts {

  private final static int SORTSIZE = 1000;

  /**
   *  makeQueueOfQueues() makes a queue of queues, each containing one item
   *  of q.  Upon completion of this method, q is empty.
   *  @param q is a LinkedQueue of objects.
   *  @return a LinkedQueue containing LinkedQueue objects, each of which
   *    contains one object from q.
   **/
  public static LinkedQueue makeQueueOfQueues(LinkedQueue q) {
    // Replace the following line with your solution.
	try {
      LinkedQueue s = new LinkedQueue();
	  while(!q.isEmpty()){
    	  LinkedQueue k = new LinkedQueue();
    	  Object x;
		
			x = q.dequeue();
			k.enqueue(x);
		
		s.enqueue(k);
//		System.out.println(q.size());
      }
//	  System.out.println(s.toString());
	  return s;
	} catch (QueueEmptyException e) {
			// TODO Auto-generated catch block
		System.out.println("error");
    }
	return null;
  }

  /**
   *  mergeSortedQueues() merges two sorted queues into a third.  On completion
   *  of this method, q1 and q2 are empty, and their items have been merged
   *  into the returned queue.
   *  @param q1 is LinkedQueue of Comparable objects, sorted from smallest 
   *    to largest.
   *  @param q2 is LinkedQueue of Comparable objects, sorted from smallest 
   *    to largest.
   *  @return a LinkedQueue containing all the Comparable objects from q1 
   *   and q2 (and nothing else), sorted from smallest to largest.
   **/
  public static LinkedQueue mergeSortedQueues(LinkedQueue q1, LinkedQueue q2) {
    // Replace the following line with your solution.
      LinkedQueue q = new LinkedQueue();
      try {
    	while(!(q1.isEmpty()) && !(q2.isEmpty())){  
			Comparable item1 = (Comparable)q1.front();
			Comparable item2 = (Comparable)q2.front();
			int c = item1.compareTo(item2);
			if(c < 0){
				q.enqueue(q1.dequeue());
			}else{
				q.enqueue(q2.dequeue());
			}
		
        }
    	if(!q2.isEmpty()){
    		while(!q2.isEmpty()){
    			q.enqueue(q2.dequeue());
    		}
    	}else{
    		while(!q1.isEmpty()){
    			q.enqueue(q1.dequeue());
    		}
    	}
      } catch (QueueEmptyException e) {
			System.out.println("error");
	  }
//      System.out.println(q.toString());
	  
      return q;
  }

  /**
   *  partition() partitions qIn using the pivot item.  On completion of
   *  this method, qIn is empty, and its items have been moved to qSmall,
   *  qEquals, and qLarge, according to their relationship to the pivot.
   *  @param qIn is a LinkedQueue of Comparable objects.
   *  @param pivot is a Comparable item used for partitioning.
   *  @param qSmall is a LinkedQueue, in which all items less than pivot
   *    will be enqueued.
   *  @param qEquals is a LinkedQueue, in which all items equal to the pivot
   *    will be enqueued.
   *  @param qLarge is a LinkedQueue, in which all items greater than pivot
   *    will be enqueued.  
   **/   
  public static void partition(LinkedQueue qIn, Comparable pivot, 
                               LinkedQueue qSmall, LinkedQueue qEquals, 
                               LinkedQueue qLarge) {
    // Your solution here.
	  try {
		  while(!qIn.isEmpty()){
		  
			
			Comparable i = (Comparable) qIn.dequeue();
			int c = i.compareTo(pivot);
			if(c<0){
				qSmall.enqueue(i);
//				 System.out.println(qSmall.toString());
			}else if(c == 0){
				qEquals.enqueue(i);
//				 System.out.println(qEquals.toString());
			}else{
				qLarge.enqueue(i);
			}
		
		  
	    }
	  } catch (QueueEmptyException e) {
			System.out.println("error");
		}
  }

  /**
   *  mergeSort() sorts q from smallest to largest using mergesort.
   *  @param q is ai LinkedQueue of Comparable objects.
   **/
  public static void mergeSort(LinkedQueue q) {
    // Your solution here.
	  LinkedQueue s = makeQueueOfQueues(q);
//	  System.out.println(s.toString());
	  try {
		while(s.size() > 1){
		  LinkedQueue q1 = (LinkedQueue) s.dequeue();
		  LinkedQueue q2 = (LinkedQueue) s.dequeue();
		  s.enqueue(mergeSortedQueues(q1,q2));
		}
	
		 
		q.append((LinkedQueue) s.dequeue());
	} catch (QueueEmptyException e) {
		System.out.println("error");
	}
	  
  }

  /**
   *  quickSort() sorts q from smallest to largest using quicksort.
   *  @param q is a LinkedQueue of Comparable objects.
   **/
  public static void quickSort(LinkedQueue q) {
    // Your solution here.
	  try {
		int k = (int) makeRandom(q.size()).front();
		Comparable pivot = (Comparable) q.nth(k);
		
		LinkedQueue qSmall = new LinkedQueue();
		LinkedQueue qEquals = new LinkedQueue();
		LinkedQueue qLarge = new LinkedQueue();
		partition(q,pivot,qSmall,qEquals,qLarge);
	    LinkedQueue s = qSmall;
	    if(s.size()>1){
	    	quickSort(s);
	    }
	    LinkedQueue l = qLarge;
	    if(l.size()>1){
	    	quickSort(l);
	    }
		q.append(qSmall);
		q.append(qEquals);
		q.append(qLarge);
		
	} catch (QueueEmptyException e) {
		System.out.println("error");
	}
	  
  }

  /**
   *  makeRandom() builds a LinkedQueue of the indicated size containing
   *  Integer items.  The items are randomly chosen between 0 and size - 1.
   *  @param size is the size of the resulting LinkedQueue.
   **/
  public static LinkedQueue makeRandom(int size) {
    LinkedQueue q = new LinkedQueue();
    for (int i = 0; i < size; i++) {
      q.enqueue(new Integer((int) (size * Math.random())));
    }
    return q;
  }

  /**
   *  main() performs some tests on mergesort and quicksort.  Feel free to add
   *  more tests of your own to make sure your algorithms works on boundary
   *  cases.  Your test code will not be graded.
   **/
  public static void main(String [] args) {

    LinkedQueue q = makeRandom(10);
    System.out.println(q.toString());
    mergeSort(q);
    System.out.println(q.toString());

    q = makeRandom(10);
    System.out.println(q.toString());
    quickSort(q);
    System.out.println(q.toString());

     
    Timer stopWatch = new Timer();
    q = makeRandom(SORTSIZE);
    stopWatch.start();
    mergeSort(q);
    stopWatch.stop();
    System.out.println("Mergesort time, " + SORTSIZE + " Integers:  " +
                       stopWatch.elapsed() + " msec.");

    stopWatch.reset();
    q = makeRandom(SORTSIZE);
    stopWatch.start();
    quickSort(q);
    stopWatch.stop();
    System.out.println("Quicksort time, " + SORTSIZE + " Integers:  " +
                       stopWatch.elapsed() + " msec.");
    
  }

}