// If right child, the left most descendant of the right child
// If no children, the first ancestor for which the child is the left child

import java.lang.*;
import java.io.*;
import java.util.HashMap;

public class BST {
    private static class Node {
	public final int value;
	public Node left;
	public Node right;
	public Node parent;
	public Node(int value) {
	    this.value = value;
	}
    };
    private final Node root;
    public BST(Node root) {
	this.root = root;
    }

    // Assume it is a tree node under root.
    // returns null if it is the last node.
    Node nextWithParent(Node node) {
	if (node == null) {
	    return null;
	}
	if (node.right != null) {
	    node = node.right;
	    while (node.left != null) {
		node = node.left;
	    }
	    return node;
	} else {
	    Node curr = node;
	    while (node.parent != null &&
		   node.parent.left != node) {
		node = node.parent;
	    }
	    // If node == root, next is null.
	    // If node.parent.left == node, node.parent is next.
	    return node.parent;
	}
    }

    Node next(Node node) {
	if (node == null || root == null) {
	    return null;
	}
	found = false;
	return nextRecur(node, root);
    }

    private boolean found = false;

    private Node nextRecur(Node node, Node curr) {
	if (curr.left != null) {
	   Node result = nextRecur(node, curr.left);
	   if (result != null) {
	       return result;
	   }
	}
	if (found) {
	    return curr;
	}
	if (curr == node) {
	    found = true;
	}
	if (curr.right != null) {
	    Node result = nextRecur(node, curr.right);
	    if (result != null) {
		return result;
	    }
	}
	return null;
    }

    public static void main(String args[]) {
	InputStreamReader converter = new InputStreamReader(System.in);
	BufferedReader in = new BufferedReader(converter);
	// Assume no equal value items.
	// Input format:
	// Each line has either 3 entries or 2 entries delimited by comma,
	// For 3 item entry: node_value,left_value,right_value,
	//    if a node has no left/right child, the corresponding entry is -1
	// For 2 item entry as the last input: root,node_value
	//    specifies the root and the node to find next for.
	HashMap<Integer, Node> map = new HashMap<Integer, Node>();
	String curLine;
	Node root = null, nodeToFind = null;
	try {
	    while((curLine = in.readLine()) != null && curLine.length() != 0) {
		String[] list = curLine.split(",");
		if (list.length == 2) {
		    root = map.get(Integer.valueOf(list[0]));
		    nodeToFind = map.get(Integer.valueOf(list[1]));
		} else if (list.length == 3) {
		    Integer value = Integer.valueOf(list[0]);
		    Node node = map.containsKey(value) ?
			map.get(value) : new Node(value);
		    map.put(Integer.valueOf(list[0]), node);    
		    if (list[1].length() > 0 && Integer.valueOf(list[1]) > 0) {
			value = Integer.valueOf(list[1]);
			if (map.containsKey(value)) {
			    node.left = map.get(value);
			    node.left.parent = node;
			} else {
			    Node left = new Node(value);
			    node.left = left;
			    left.parent = node;
			    map.put(value, left);
			}
		    }
		    if (list[2].length() > 0 && Integer.valueOf(list[2]) > 0) {
			value = Integer.valueOf(list[2]);
			if (map.containsKey(value)) {
			    node.right = map.get(value);
			    node.right.parent = node;
			} else {
			    Node right = new Node(value);
			    node.right = right;
			    right.parent = node;
			    map.put(value, right);
			}
		    }
		    System.out.println("Node " + node.value +
				       " Left " + (node.left != null ? node.left.value : -1) +
				       " Right " + (node.right != null ? node.right.value : -1));
		}
	    }
	} catch (IOException e) {
	    System.out.println(e);
	}

	BST tree = new BST(root);
	System.out.println("Next node method 1: " +
			   (tree.nextWithParent(nodeToFind) != null ?
			    tree.nextWithParent(nodeToFind).value : "-1"));
	System.out.println("Next node method 2: " +
			   (tree.next(nodeToFind) != null ?
			    tree.next(nodeToFind).value : "-1"));
    }
};


                    