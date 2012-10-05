import java.lang.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.*;

/**
 * Choose among the sort methods "mergesort", "quicksort" or "heapsort"
 * by specifying the method as command line arguments.
 * 
 * The program accepts strings line-by-line as standard input until the end of
 * input or an empty line and sort the strings ascending order.
 */
public class Sort
{ 
    public static void quickSort(String[] list) {
        quickSortInternal(list, 0, list.length - 1);
    }
    
    private static void quickSortInternal(String[] list, int start, int end) {
        if (start >= end) {
	    return;
        }
	String pivot = list[start];
	// Point to the last item that is smaller than the pivot.                                                                                                                                                 
        int partition = start;
	// Invariant: after each iteration, any item in [start + 1, partition] is
	// less than pivot or start == partition and any item in
	// [partition + 1, index] is greater or equal to pivot or
	// partition == index.
	// Notice partition <= index through the iterations.
	// After going through all the items in [start + 1, end], partition is:
	// - equals start, which means no item is smaller than pivot.
	// - equals end, which means no item is greater or equal to pivot.
	// - items between [start + 1, partition] is smaller than pivot; items between
	//   [partition + 1, index] is greater or equal to pivot.
	// In any of the above three cases, swap list[start] with list[partition] will
	// make any item in subarray [start, partition) smaller than list[partition] and
	// any item in subarray (partition, end] greater or equal to list[partition].
	for (int index = start + 1; index <= end; index++) {
	    if (list[index].compareTo(pivot) < 0) {
		String temp = list[index];
		list[index] = list[partition + 1];
		list[partition + 1] = temp;
		partition++;
            }
	}
        if (partition != start) {
            list[start] = list[partition];
	    list[partition] = pivot;
        }
	if (start < partition - 1) {
            quickSortInternal(list, start, partition - 1);
	}
	if (partition + 1 < end) {
	    quickSortInternal(list, partition + 1, end);
	}
    }
    
    public static void mergeSort(String[] list) {
        mergeSortInternal(list, 0, list.length - 1);
    }
    
    private static void mergeSortInternal(String[] list, int start, int end) {
        if (start >= end) {
            return;
        }
	int mid = (start + end) >> 1;
        if (mid > start) {
            mergeSortInternal(list, start, mid);
        }
        if (end > mid + 1) {
            mergeSortInternal(list, mid + 1, end);
        }
        String[] mergedList = new String[end - start + 1];
        int pointer1 = start, pointer2 = mid + 1, i = 0;
        while (pointer1 <= mid && pointer2 <= end) {
            if (list[pointer1].compareTo(list[pointer2]) > 0) {
                mergedList[i++] = list[pointer2++];
            } else {
                mergedList[i++] = list[pointer1++];
            }
        }
        while (pointer1 <= mid) {
            mergedList[i++] = list[pointer1++];
        }
        while (pointer2 <= end) {
            mergedList[i++] = list[pointer2++];
        }
        for (i = start; i <= end; i++) {
            list[i] = mergedList[i - start];
        }
    }
    
    public static void heapSort(String[] list) {
        if (list.length <= 1) {
            return;
        }
        
        // Generate heap
        for (int i = 1; i < list.length; i++) {
            int child = i;
	    int parent = ((child + 1) >> 1) - 1;
            // upward heapify
            while (child > 0 &&
		   list[parent].compareTo(list[child]) < 0) {
                String temp = list[parent];
	        list[parent] = list[child];
	        list[child] = temp;
                child = parent;
		parent = ((child + 1) >> 1) - 1;
            }
        }
        
        for (int i = list.length - 1; i > 0; i--) {
            int parent = 0;
            String last = list[i];
	    list[i] = list[parent];
            list[parent] = last;
            while (true) {
		int child1 = ((parent + 1) << 1) - 1, child2 = (parent + 1) << 1;
                if (child1 < i && list[child1].compareTo(last) > 0 ||
		    child2 < i && list[child2].compareTo(last) > 0) {
                    int index = child1;
                    if (child2 < i && list[child1].compareTo(list[child2]) < 0) {
			index = child2;
                    }
                    String temp = list[index];
                    list[index] = list[parent];
                    list[parent] = temp;
                    parent = index;
                } else {
                    break;
                }
            }
        }
    }
    
    /**
     * This is the main entry point for the application
     */
    public static void main(String args[])
    {
        System.out.println("Input strings to be sorted line-by-line");
        InputStreamReader converter = new InputStreamReader(System.in);
	BufferedReader in = new BufferedReader(converter);
        ArrayList<String> input = new ArrayList<String>();
        String curLine;
        try {
	    while((curLine = in.readLine()) != null && curLine.length() != 0) {
		input.add(curLine);
	    }
        } catch (IOException e) {
            System.out.println(e);
        }
        
        String[] array = input.toArray(new String[input.size()]);
        if (args.length > 0) {
            if (args[0].equals("quicksort")) {
		quickSort(array);
            } else if (args[0].equals("mergesort")) {
                mergeSort(array);
            } else if (args[0].equals("heapsort")) {
		heapSort(array);
            } else {
                System.out.println(
				   "Supported sort methods are \"quicksort\", \"mergesort\", \"heapsort\"." +
				   " Provide one of them as the input!");
            }
        }
	for(String s : array) {
	    System.out.println(s);
	}
    }
}

