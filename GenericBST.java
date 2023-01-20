// Julianne Mazzaro
// NID: ju458824
// COP 3503, Fall 2021

// ====================
// GenericBST: BST.java
// ====================
// Basic binary search tree (BST) implementation that supports insert() and
// delete() operations. This framework is provided for you to modify as part of
// Programming Assignment #2.

import java.io.*;
import java.util.*;

class Node<T extends Comparable<T>>
{
	T data;
	Node<T> left, right;

	Node(T data)
	{
		this.data = data;
	}
}

public class GenericBST<AnyType extends Comparable<AnyType>>
{
	private Node<AnyType> root;

	public void insert(AnyType data)
	{
		root = insert(root, data);
	}
	
	// This method creates a node and recursivley places it in the BST
	private Node<AnyType> insert(Node<AnyType> root, AnyType data)
	{
		if (root == null)
		{
			return new Node<>(data);
		}
		else if (data.compareTo(root.data) < 0)
		{
			root.left = insert(root.left, data);
		}
		else if (data.compareTo(root.data) > 0)
		{
			root.right = insert(root.right, data);
		}

		return root;
	}

	public void delete(AnyType data)
	{
		root = delete(root, data);
	}
	
	// Deletes a node if found, and returns the deleted value
	// Returns null if the BST did not contain the desired node
	private Node<AnyType> delete(Node<AnyType> root, AnyType data)
	{
		if (root == null)
		{
			return null;
		}
		else if (data.compareTo(root.data) < 0)
		{
			root.left = delete(root.left, data);
		}
		else if (data.compareTo(root.data) > 0)
		{
			root.right = delete(root.right, data);
		}
		else
		{
			if (root.left == null && root.right == null)
			{
				return null;
			}
			else if (root.left == null)
			{
				return root.right;
			}
			else if (root.right == null)
			{
				return root.left;
			}
			else
			{
				root.data = findMax(root.left);
				root.left = delete(root.left, root.data);
			}
		}

		return root;
	}

	// This method assumes root is non-null, since this is only called by
	// delete() on the left subtree, and only when that subtree is not empty.
	private AnyType findMax(Node<AnyType> root)
	{
		while (root.right != null)
		{
			root = root.right;
		}

		return root.data;
	}

	public boolean contains(AnyType data)
	{
		return contains(root, data);
	}
	
	// Recursive search for node containing data
	private boolean contains(Node<AnyType> root, AnyType data)
	{
		// Base case
		if (root == null)
		{
			return false;
		}
		else if (data.compareTo(root.data) < 0)
		{
			return contains(root.left, data);
		}
		else if (data.compareTo(root.data) > 0)
		{
			return contains(root.right, data);
		}
		else
		{
			return true;
		}
	}

	public void inorder()
	{
		System.out.print("In-order Traversal:");
		inorder(root);
		System.out.println();
	}
	
	// Recursive print of BST inorder, starting at left, then node, then moving to the right.
	private void inorder(Node<AnyType> root)
	{
		if (root == null)
			return;

		inorder(root.left);
		System.out.print(" " + root.data);
		inorder(root.right);
	}
	
	public void preorder()
	{
		System.out.print("Pre-order Traversal:");
		preorder(root);
		System.out.println();
	}
	
	// Recursive print of BST preorder, starting at the node, then going left, then moving to the right.
	private void preorder(Node<AnyType> root)
	{
		if (root == null)
			return;

		System.out.print(" " + root.data);
		preorder(root.left);
		preorder(root.right);
	}

	public void postorder()
	{
		System.out.print("Post-order Traversal:");
		postorder(root);
		System.out.println();
	}
	
	// Recursive print of BST postorder, starting to the left, then going right, then printing the node.
	private void postorder(Node<AnyType> root)
	{
		if (root == null)
			return;

		postorder(root.left);
		postorder(root.right);
		System.out.print(" " + root.data);
	}
	
	public static double difficultyRating()
	{
		return 1.5;
	}
	
	public static double hoursSpent()
	{
		return 4.5;
	}
}
