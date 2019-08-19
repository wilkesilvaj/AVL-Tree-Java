package avlTree;

import java.text.NumberFormat;

public class BinaryTree {
	
Node root;
NumberFormat nFormat = NumberFormat.getCurrencyInstance();
	
	private class Node {
		Account account;
		Node left;
		Node right;
		int height;
		
		Node(Account newAccount)	{
			account = newAccount;
			left = null;
			right = null;
			height = 0;
		}
		
	}
	
	// Pointers of the list
	Node currentNode;
	Node parent;
	
	// Tree Constructor
	BinaryTree ()	{
		currentNode = null;
		root = null;
	}
	
	// Method to get height of node
	public int getHeight(Node node)	{
		// IF node == null, return -1, else return node.height
		return node == null ? -1 : node.height;
	}
	
	/* Function to max of left/right node */
    private int max(int lhs, int rhs)
    {
        return lhs > rhs ? lhs : rhs;
    }	
	
    
    // Get Balance factor of a node 
    int getBalance(Node node)  
    {  
        if (node == null)  
            return 0;  
        return getHeight(node.left) - getHeight(node.right);  
    }  
    
    
	// Method which verifies if the tree is empty
	public Boolean isEmpty()	{
		return root == null;
	}
	
	public void addNode(Account newAccount){
		root = addNode(newAccount, root);
	}
	
	// Method which adds/inserts a new node
	private Node addNode(Account newAccount, Node node)
    {
        if (node == null)
            node = new Node(newAccount);
        // If the new account's number is LOWER than OR EQUAL to the current node's
        else if (newAccount.accountNumber <= node.account.accountNumber)
        {
            node.left = addNode( newAccount, node.left );
            if( getHeight( node.left ) - getHeight( node.right ) == 2 )
                if( newAccount.accountNumber < node.left.account.accountNumber )
                    node = rotateWithLeftChild( node );
                else
                    node = doubleWithLeftChild( node );
        }
        // If the new account's number is HIGHER than the current node's
        else
        {
            node.right = addNode( newAccount, node.right );
            if( getHeight( node.right ) - getHeight( node.left ) == 2 )
                if( newAccount.accountNumber > node.right.account.accountNumber)
                    node = rotateWithRightChild( node );
                else
                    node = doubleWithRightChild( node );
        }
            
        node.height = max( getHeight( node.left ), getHeight( node.right ) ) + 1;
        return node;
    }
	
			
	public void inOrdertraverse(Node currentNode, int search)
	{		
		if(currentNode != null)
		{
			if (currentNode.account.accountNumber > search)	{
				System.out.println(currentNode.account.accountNumber);
				inOrdertraverse(currentNode.left, search);
			}
			else if (currentNode.account.accountNumber < search)	{
				System.out.println(currentNode.account.accountNumber);
				inOrdertraverse(currentNode.right, search);
				
			}
			else	{
				System.out.println("Account #"+search+" found, account details are as follows:");
				System.out.println("\n===============================================");								
				System.out.println("Account #: "+currentNode.account.accountNumber);
				System.out.println("Full Name: "+currentNode.account.firstName + " " + currentNode.account.lastName);
				System.out.println("Address: " + currentNode.account.address);
				System.out.println("Current Amount: "+nFormat.format(currentNode.account.amount));
				System.out.println("===============================================");
			}
		}
		else	{
			System.out.println(search+" does not exist in this context");
		}
			
	}
	

	
	/* Method for deleting a node
	   Reference: https://www.youtube.com/watch?v=JsgqnTLjfps */
	public Node deleteNode(Node currentNode, int search)
	{		
		if (currentNode == null)	{
			System.out.println(search+" does not exist in this context");
			return null;
		}
		
		
		if (currentNode.account.accountNumber < search)	{
			currentNode.right = deleteNode(currentNode.right,search);
		}	else if (currentNode.account.accountNumber > search)	{
			currentNode.left = deleteNode(currentNode.left, search);
		}	else	{ // The account was found
				if (currentNode.left == null || currentNode.right == null)	{
				Node temp = null;
				/** Assigns temp to left child, if still null, the existing child is on the right.
				 *  Else, the existing child is on the left	 */
				temp = currentNode.left == null ? currentNode.right : currentNode.left;
				
				// NO CHILDREN SCENARIO - Deletes the Node by returning null to the current node 
				if (temp == null)	{
					return null;
				}	else	{// ONE CHILD SCENARIO - Returns the ONLY child of the current node being deleted as to rearrange the tree
					return currentNode;
				}					
			
			}	else	{ // TWO CHILDREN SCENARIO - 
				Node successor = getSuccessor(currentNode);
				/** Overwriting the node meant to be deleted with the data
				 * from its right child's left child's data. (THE MININUM value on the right tree) 
				 */
				currentNode.account = successor.account;
				// Deletes the right child
				currentNode.right = deleteNode(currentNode.right, successor.account.accountNumber);
			}
									
		}
		
		// If the tree had only one node then return  
        if (currentNode == null)  
            return currentNode;  
        
     // Updates the height of the current node  
        currentNode.height = max(getHeight(root.left), getHeight(root.right)) + 1; 
        
        // Gets the balance factor of the current node to verify if it has become unbalanced
        int balance = getBalance(currentNode);
        
        
     // If this node becomes unbalanced, then there are 4 cases  
        // Left Left Scenario  
        if (balance > 1 && getBalance(currentNode.left) >= 0)  
            return rotateWithLeftChild(currentNode);  
  
        // Left Right Scenario  
        if (balance > 1 && getBalance(currentNode.left) < 0)  
        {  
            return doubleWithLeftChild(currentNode) ;
        }     

        // Right Right Scenario  
        if (balance < -1 && getBalance(currentNode.right) <= 0)  
            return rotateWithRightChild(currentNode);  
  
        // Right Left Scenario  
        if (balance < -1 && getBalance(currentNode.right) > 0)  
        {  
        	return doubleWithRightChild(currentNode);
        }  		
			
		return currentNode;
				
	}
	
	/* Method which gets lowest account number in the right subtree
	 * of the Node passed to it  */
	public Node getSuccessor(Node node){
		if (node == null)	{
			return null;
		}
		Node temp = node.right;
		while (temp.left != null)	{
			temp = temp.left;			
		}
		return temp;
	}
	
		
	  /* Rotate binary tree node with left child 
	     LEFT LEFT*/     
    private Node rotateWithLeftChild(Node k2)
    {
        Node k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        k2.height = max( getHeight( k2.left ), getHeight( k2.right ) ) + 1;
        k1.height = max( getHeight( k1.left ), k2.height ) + 1;
        return k1;
    }

    /* Rotate binary tree node with right child 
       RIGHT RIGHT*/
    private Node rotateWithRightChild(Node k1)
    {
        Node k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        k1.height = max( getHeight( k1.left ), getHeight( k1.right ) ) + 1;
        k2.height = max( getHeight( k2.right ), k1.height ) + 1;
        return k2;
    }
    
    
    /**
     * Double rotate binary tree node: first left child
     * with its right child; then node k3 with new left child 
     * LEFT RIGHT*/
    private Node doubleWithLeftChild(Node k3)
    {
        k3.left = rotateWithRightChild( k3.left );
        return rotateWithLeftChild( k3 );
    }
    /**
     * Double rotate binary tree node: first right child
     * with its left child; then node k1 with new right child 
     * RIGHT LEFT*/      
    private Node doubleWithRightChild(Node k1)
    {
        k1.right = rotateWithLeftChild( k1.right );
        return rotateWithRightChild( k1 );
    }    
	
	
	
	
	

}
