package sortingClasses.azim.mostdesired;
import java.util.ArrayList;


/**
 * 
 * @author azim
 *<br>
 *	This is a class with a static sort method which uses Insertion Sort.
 *	This method sorts an array of Doubles in ascending order and rearranges an array of int
 *so that its order changes exactly as the first array changed.
 *<br>
 *	Insertion sort is one of the best choices for nearly-sorted arrays. (http://www.sorting-algorithms.com/)
 */

public class InsertionSort {
  

//    public static void sort(Double[] arr, int[] indices) {
//    	
//        for (int i = 1; i < arr.length; i++) {
//            Double valueToSort = arr[i];
//            int indexToSort = indices[i];
//            int j = i;
//            while (j > 0 && arr[j - 1] > valueToSort) {
//                arr[j] = arr[j - 1];
//                indices[j] = indices[j-1];
//                j--;
//            }
//            arr[j] = valueToSort;
//            indices[j] = indexToSort;
//        }
//    }
    
    public static void sort(ArrayList<Double> arr, ArrayList<Integer> indices) {
    	
        for (int i = 1; i < arr.size(); i++) {
            Double valueToSort = arr.get(i);
            int indexToSort = indices.get(i);
            int j = i;
            while (j > 0 && arr.get(j - 1) > valueToSort) {
            	arr.set(j, arr.get(j - 1));
                indices.set(j, indices.get(j - 1));
                j--;
            }
            arr.set(j, valueToSort);
            indices.set(j, indexToSort);
        }
    }
 
}


//http://www.journaldev.com/585/insertion-sort-in-java-algorithm-and-code-with-example