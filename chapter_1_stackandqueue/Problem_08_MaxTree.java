package chapter_1_stackandqueue;

import java.util.HashMap;
import java.util.Stack;

public class Problem_08_MaxTree {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int data) {
			this.value = data;
		}
	}

	public static Node getMaxTree(int[] arr) {
		Node[] nArr = new Node[arr.length];
		for (int i = 0; i != arr.length; i++) {
			nArr[i] = new Node(arr[i]);
		}
		Stack<Node> stack = new Stack<Node>();
		HashMap<Node, Node> lBigMap = new HashMap<Node, Node>();//键代表左子树，值代表左边第一个比它大的数
		HashMap<Node, Node> rBigMap = new HashMap<Node, Node>();
		for (int i = 0; i != nArr.length; i++) {
			Node curNode = nArr[i];
			while ((!stack.isEmpty()) && stack.peek().value < curNode.value) {
				popStackSetMap(stack, lBigMap);
			}
			stack.push(curNode);
		}
		while (!stack.isEmpty()) {
			popStackSetMap(stack, lBigMap);
		}
		for (int i = nArr.length - 1; i != -1; i--) {
			Node curNode = nArr[i];
			while ((!stack.isEmpty()) && stack.peek().value < curNode.value) {
				popStackSetMap(stack, rBigMap);
			}
			stack.push(curNode);
		}
		while (!stack.isEmpty()) {
			popStackSetMap(stack, rBigMap);
		}
		Node head = null;
		for (int i = 0; i != nArr.length; i++) {
			Node curNode = nArr[i];
			Node left = lBigMap.get(curNode);
			Node right = rBigMap.get(curNode);
			if (left == null && right == null) {//如果左右两边都没有比curNode大的数，则它为根节点
				head = curNode;
			} else if (left == null) {//如果左边没有比curNode大的数。则把curNode放入右边第一个比它大的树的子树
				if (right.left == null) {
					right.left = curNode;
				} else {
					right.right = curNode;
				}
			} else if (right == null) {
				if (left.left == null) {
					left.left = curNode;
				} else {
					left.right = curNode;
				}
			} else {//如果都存在，则需要选择左右两边较小的那个数作为父节点
				Node parent = left.value < right.value ? left : right;
				if (parent.left == null) {
					parent.left = curNode;
				} else {
					parent.right = curNode;
				}
			}
		}
		return head;
	}

	public static void popStackSetMap(Stack<Node> stack, HashMap<Node, Node> map) {
		Node popNode = stack.pop();
		if (stack.isEmpty()) {
			map.put(popNode, null);
		} else {
			map.put(popNode, stack.peek());
		}
	}

	public static void printPreOrder(Node head) {
		if (head == null) {
			return;
		}
		System.out.print(head.value + " ");
		printPreOrder(head.left);
		printPreOrder(head.right);
	}

	public static void printInOrder(Node head) {
		if (head == null) {
			return;
		}
		printPreOrder(head.left);
		System.out.print(head.value + " ");
		printPreOrder(head.right);
	}

	public static void main(String[] args) {
		int[] uniqueArr = { 3, 4, 5, 1, 2 };
		Node head = getMaxTree(uniqueArr);
		printPreOrder(head);
		System.out.println();
		printInOrder(head);

	}

}
