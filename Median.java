import java.io.*;
import java.util.ArrayList;

public class Median {
    public static int getK(Integer[] array, Integer[] probability, int start, int end, int k) {
	if (start >= end) {
	    return -1;
	}
	int current = array[start];
	int sum = probability[start];
	int partition = start;
	for (int i = start + 1; i <= end; i++) {
	    if (array[i] < current) {
		sum += probability[i];
		int temp = array[i];
		array[i] = array[partition + 1];
		array[partition + 1] = temp;
		int d = probability[i];
		probability[i] = probability[partition + 1];
		probability[partition + 1] = d;
		partition++;
	    }
	}
	if (partition != start) {
	    array[start] = array[partition];
	    array[partition] = current;
	    int d = probability[start];
	    probability[start] = probability[partition];
	    probability[partition] = d;
	}
	// Sum includes the partition entry.
	if (sum == k) {
	    return array[partition];
	}
	System.out.println(sum + " " + k);
	if (sum < k) {
	    return getK(array, probability, partition + 1, end, k - sum);
	} else {
	    return getK(array, probability, start, partition - 1, k);
	}
    }

    public static void main(String args[]) {
	// Refactor out
	InputStreamReader converter = new InputStreamReader(System.in);
	BufferedReader in = new BufferedReader(converter);
	ArrayList<Integer> values = new ArrayList<Integer>();
	ArrayList<Integer> probs = new ArrayList<Integer>();
	int sum = 0;
	String curLine;
	try {
	    while((curLine = in.readLine()) != null && curLine.length() != 0) {
		String[] list = curLine.split(",");
		values.add(Integer.valueOf(list[0]));
		probs.add(Integer.valueOf(list[1]));
		sum += Integer.valueOf(list[1]);
	    }
	} catch (IOException e) {
	    System.out.println(e);
	}

	Integer[] array = values.toArray(new Integer[values.size()]);
	Integer[] probability = probs.toArray(new Integer[probs.size()]);
	System.out.println(getK(array, probability, 0, array.length - 1, sum / 2));
    }
}