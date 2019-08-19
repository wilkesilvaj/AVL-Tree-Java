package avlTree;

import java.io.File;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class MyProgram {

	
	/** Author: João Vitor Wilke Silva
	 *	Student #: 300278748
	 *
	 *	References: 
	 *  https://www.sanfoundry.com/java-program-implement-avl-tree/
	 *  https://www.youtube.com/watch?v=JsgqnTLjfps
	 *  https://www.geeksforgeeks.org/avl-tree-set-2-deletion/*  
	 *  Visual tool for checking Tree: 
	 *  http://www.cs.armstrong.edu/liang/animation/web/AVLTree.html
	 */
	public static void main(String[] args) {
		
		try {
			String option="";
			File myFile = new File("MOCK_DATA.txt");
			Scanner myData = new Scanner(myFile);
			// Loops through the file
			BinaryTree myTree = new BinaryTree();
			while (myData.hasNextLine()) {
				Account newAccount = new Account(myData.nextLine());
				myTree.addNode(newAccount);
			}

			while (JOptionPane.showConfirmDialog(null, "Would you like to see our Main Menu?","Warning",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)	{	
			
				option = JOptionPane.showInputDialog("Welcome to BST Banking"
						+ "\nPlease type in the desired operation"
						+ "\n1 -> To create a new account"
						+ "\n2 -> To search for an account"
						+ "\n3 -> To delete an account");
				
				// If the user chooses to search for an account
				if (option.equals("2"))	{
					int account = Integer.parseInt(JOptionPane.showInputDialog("Please type in the Account # to be searched for"));
					System.out.println("\n===============================================");
					System.out.println("Initializing search for account #"+account);
					myTree.inOrdertraverse(myTree.root, account);
				}
				// If the user chooses to add a new account
				else if (option.equals("1"))	{
					Random rand = new Random();
					int account = rand.nextInt(1000)+1;
					String name = JOptionPane.showInputDialog("Please type in your full name");
					String[] names = name.split(" ");
					Double amount = Double.parseDouble(JOptionPane.showInputDialog("Please type in the amount which will be deposited in the account"));
					String address = JOptionPane.showInputDialog("Please type in your address");
					Account newAccount = new Account(account, names[0], names[1], amount, address);
					myTree.addNode(newAccount);
					
					System.out.println("\n===============================================");
					System.out.println("Creating a new account...");
					System.out.println("New Account #"+account+" succesfully created");
					System.out.println("===============================================");
				}
				// If the user chooses to delete an account 
				else if (option.equals("3"))	{
					int account = Integer.parseInt(JOptionPane.showInputDialog("Please type in the Account # to be searched for"));
					System.out.println("\n===============================================");
					System.out.println("Attempting to delete specified account");
					myTree.deleteNode(myTree.root, account);
				}
				// If the user inputs something invalid
				else	{
					JOptionPane.showMessageDialog(null,"Please type in a valid option");
				}

			}	
						
			
			}		
			catch (Exception ex)	{
				System.out.println(ex.getMessage());
			}
		

	}

}
