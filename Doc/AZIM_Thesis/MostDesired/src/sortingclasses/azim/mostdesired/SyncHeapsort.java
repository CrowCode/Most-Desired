package sortingclasses.azim.mostdesired;

/**
 * 
 * @author azim
 *<br>
 *	This is a class with a static sort method which uses heapsort.
 *	This method sorts an array of Doubles in ascending order and rearranges an array of int
 *so that its order changes exactly as the first array changed.
 */
public class SyncHeapsort {

	private static Double[] a;
	private static int[] b;
    private static int n;
    private static int left;
    private static int right;
    private static int largest;

    
    public static void buildheap(Double []a){
        n=a.length-1;
        for(int i=n/2;i>=0;i--){
            maxheap(a,i);
        }
    }
    
    public static void maxheap(Double[] a, int i){
    	
        left=2*i;
        right=2*i+1;
        if(left <= n && a[left] > a[i]){
            largest=left;
        }
        else{
            largest=i;
        }
        
        if(right <= n && a[right] > a[largest]){
            largest=right;
        }
        if(largest!=i){
            exchange(i,largest);
            maxheap(a, largest);
        }
    }
    
    public static void exchange(int i, int j){
        Double t=a[i];
        a[i]=a[j];
        a[j]=t; 
        
        int tt =  b[i];
        b[i] = b[j];
        b[j] = tt;
    }
    
    public static void sort(Double []a0, int[]b0){
    	
    	assert(a0.length == b0.length);
    	
        a = a0;
        b = b0;
        buildheap(a);
        
        for(int i=n;i>0;i--){
            exchange(0, i);
            n=n-1;
            maxheap(a, 0);
        }
    }
    
}
