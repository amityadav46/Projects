package foundation.july23_BST;

import java.util.*;

public class BST_iterator implements Iterable<Integer> {
	
	private static class Node {
		int data;
		Node left;
		Node right;

		Node(int data) {
			this.data = data;
		}
	}

	Node root;

	BST_iterator(int[] arr) {
		for (int val : arr) {
			root = add(root, val);
		}
	}

	Node add(Node node, int data) {
		if (node == null) {
			return new Node(data);
		} else if (data > node.data) {
			node.right = add(node.right, data);
		} else if (data < node.data) {
			node.left = add(node.left, data);
		} else {
		}

		return node;
	}

	void display(Node node) {
		if (node == null) {
			return;
		}

		display(node.left);
		System.out.print(node.data + " ");
		display(node.right);
	}

	void display() {
		display(root);
		System.out.println();
	}

	public Iterator<Integer> iterator() {
		return new bstiterator(root);
	}

	static class bstiterator implements Iterator<Integer> {
		
		private static class Pair {
			Node node;
			int state;

			Pair(Node node, int state) {
				this.node = node;
				this.state = state;
			}
		}

		private Stack<Pair> stack = new Stack<>();
		private int val;

		bstiterator(Node node) {
			if (node != null) {
				stack.push(new Pair(node, 0));
				next();
			}
		}

		public boolean hasNext() {
			return val != -1;
		}

		public Integer next() {
			int rv = val;
			val = -1;

			while (stack.size() > 0) {
				Pair top = stack.peek();
				if (top.state == 0) {
					top.state++;
					if (top.node.left != null) {
						stack.push(new Pair(top.node.left, 0));
					}
				} else if (top.state == 1) {
					top.state++;
					val = top.node.data;
					break;
				} else if (top.state == 2) {
					top.state++;
					if (top.node.right != null) {
						stack.push(new Pair(top.node.right, 0));
					}
				} else if (top.state == 3) {
					stack.pop();
				}
			}

			return rv;
		}

	}

	public static void main(String[] args) {
		int[] arr = { 50, 25, 12, 37, 30, 40, 75, 62, 60, 70, 87 };
		BST_iterator tree = new BST_iterator(arr);
		tree.display();

		int sum = 0;
		int min = 100000;
		int max = -100000;
		for (int val : tree) {
			System.out.println(val);
			sum += val;
			min = Math.min(min, val);
			max = Math.max(max, val);
		}
		System.out.println(sum + " " + min + " " + max);

		// int tar = 100;
		// Iterator<Integer> litr = tree.iterator();
		// Iterator<Integer> ritr = tree.riterator();
		//
		// int left = litr.next();
		// int right = ritr.next();
		//
		// while (left < right) {
		// System.out.print(left + " " + right);
		// if (left + right == tar) {
		// System.out.println("*");
		// left = litr.next();
		// right = ritr.next();
		// } else if (left + right > tar) {
		// System.out.println();
		// right = ritr.next();
		// } else if (left + right < tar) {
		// System.out.println();
		// left = litr.next();
		// }
		//
		// }
	}
}