/*
 * 	CPEN 457 - Programming Languages
 *  Binary Search Tree Class
 * 	Authors: Kenneth Young, Roberto Santana
 * 	Spring 2018
 * 
 */

import java.util.Stack;

class BSTNode {
	
	BSTNode left, right; 	//defining the two childs
	String genreTitle;		//defining the header and naming it genreTitle according to the project specifications
	DoubleCircularList bookList;	//since the project requires our BST to have a DoubleCircularList inside, we define it here
	
	// Default Constructor
	public BSTNode(){
		
		left = null;	//initializing the childs to null
		right = null;
		genreTitle = null;	//initializing the genre as an empty string
		bookList =  new DoubleCircularList();	//seperating the memory space for the DCL
	}

	// Constructor with only genre
	public BSTNode(String genre){
		
		left = null;	//initializing the childs to null
		right = null;
		genreTitle = genre;	//initializing the genre as an empty string
		bookList = new DoubleCircularList();	//seperating the memory space for the DCL
	}
	
	// Constructor with genre and list
	public BSTNode(String genre, DoubleCircularList books){
		
		left = null;
		right = null;
		genreTitle = genre;
		bookList = books;	//assigning the bookList to the supplied list
	}
	
	// Setter functions
	public synchronized void setRight(BSTNode rt){	//to enable assigning the right child to a node
		right = rt;
	}
	
	public synchronized void setLeft(BSTNode lt){	//to enable assigning the left child to a node
		left = lt;
	}
	
	public synchronized void setGenre(String genre){	//to enable assigning the genre of the node
		genreTitle = genre;
	}
	
	public synchronized void setDCList(DoubleCircularList books){	//to enable assigning DCL property with a supplied DCL
		bookList = books;
	}
	
	// Getter functions
	public synchronized BSTNode getRight(){	//returns the right child pointer
		return right;
	}
	
	public synchronized BSTNode getLeft(){	//returns the left child pointer
		return left;
	}
	
	public synchronized String getGenre(){	//returns the genre of the node
		return genreTitle;
	}
	
	public synchronized DoubleCircularList getDCList(){	//returns the DCL property of the node
		return bookList;
	}
}

public class BST {	//Defining the BST class
	BSTNode root;	//creating the node
	
	// Constructor
	public BST(){
		root = null;	//defining the root as null
	}
	
	public BST(BSTNode node){
		root = node;	//if we create a BST with a BSTNode then we assign that node as the root
	}
	
	// Checking if empty
	public synchronized boolean isEmpty(){
		return root == null;		//if the BST is empty then the root node must be null
	}
	
	public synchronized void insert(String genreTitle){	//Inserting a genre to the BST
		boolean genre = find(genreTitle);	//checking if genre exists
		if (!genre)							//if genre does not exist then proceed
		root = insert(genreTitle, root);	//call next function for inserting genre into BSTNode
		
		else {
			System.out.println("Genre is already in the database");	//Signal to user that the genre already exists
		}
	}
	
	// Insert recursively
	public synchronized BSTNode insert(String genre, BSTNode node){
		if (node == null){		//checking if the supplied node is null
			node = new BSTNode(genre);		//if it is null create a new node with the supplied genre in this location
			node.bookList = new DoubleCircularList();	//create new DCL for this node
		}
		
		else {
			// Comparing both strings
			int compare = genre.compareTo(node.getGenre());
			// New genre goes before old genre
			if (compare < 0)
				node.left = insert(genre, node.getLeft());
			else // New genre goes after old genre
				node.right = insert(genre, node.getRight());
		}
		return node;
	}
	
	// Find function
	public synchronized boolean find(String g){
		return find(root, g);
	}
	// Find recursively
	private synchronized boolean find(BSTNode node, String g){
		boolean found = false;
		
		while ((node != null) && !found) {		//loop while we the current node is not null and we have not found our target
			int compare = g.compareTo(node.getGenre());	//we use the string function "comparteTo()" see where we should move next
			
			if (compare < 0)		//if this value is below 0, this means the new genre is alphabetically less than the node we are checking so we move to the left
				node = node.getLeft();
			
			else if (compare > 0)	//if this value is above 0, this means the new genre is alphabetically more than the node we are checking so we move to the right
				node = node.getRight();
			// Search was successful
			else {
				found = true;	//setting found to true to ensure we do not keep searching in other instances of the recursive function
				break; //breaking out of the loop to ensure we do not keep searching in this instance of the recursive function
			}
			
		}
		
		return found;	//return the boolean found to signal the caller if the genre was found or not
	}
	
	// Get all book information from single genre
	public synchronized void getInfo(BSTNode node){
		System.out.println("All books with the genre " + node.getGenre() + ":");
		node.getDCList().showBooks();	//to access books, we have to access them from the DCList associated to the node
	}
		
	// Traversal function
	public synchronized String order(){
		return order(root);	//calling the helper function
	}
	
	private synchronized String order(BSTNode node){	//function to traverse the BST and print in order
		String ordered = "";
		if (node == null) {		//if the node is null, we return an empty string
			ordered = "";
			return ordered;
		}
		else {

			ordered = order(node.getLeft()) + node.getGenre() +  "\n" + order(node.getRight());		//if not null, we add the info recursively to the string starting from the leftmost Node


			return ordered;		//finally, this string will have all the required information stored in the correct format and order
		}
	}

	public synchronized void addBook(String genre, Book book){		//support for adding books to the DCL associated with the BSTNode
		BSTNode node = addBook(root, genre);			//we find where to insert by calling seperate function
		node.getDCList().insert(book);					//then we proceed to insert the book to the DCL
	}

	private synchronized BSTNode addBook(BSTNode node, String g){	//function to aid the previous function to know to which node to add the book
		
		while (node != null) {							//loop while we the current node is not null and we have not found our target



		
			int compare = g.compareTo(node.getGenre());	//we use the string function "comparteTo()" see where we should move next
			
			if (compare < 0)
				node = node.getLeft();					//if this value is below 0, this means the new genre is alphabetically less than the node we are checking so we move to the left
			
			else if (compare > 0)						//if this value is above 0, this means the new genre is alphabetically more than the node we are checking so we move to the right
				node = node.getRight();
			// Search was successful
			else {
				break;									//breaking out of the loop to ensure we do not keep searching in this instance of the recursive function
			}
			
		}
		
		return node;									//returning the node that we will insert to

	}

	public synchronized String printBooks(){							//calling helper function
		return printBooks(root);
	}
	
	public synchronized String printBooks(BSTNode node){				//function that prints books in order
		String booksInfo ="";
		if (node == null) {
			return "";
		}
		else {
			booksInfo += printBooks(node.getLeft()) + node.getGenre() + "\n\n" + node.getDCList().showBooksWithInfo() + printBooks(node.getRight()) + "\n";
			// printBooks(node.getLeft());					//traversing the BST and printing in order
			// System.out.println(node.getGenre() + " ");
			// node.getDCList().showBooksWithInfo();		//accessing the nodes DCL and using function to print all book information
			//getInfo(node);
			// printBooks(node.getRight());
		}
		return booksInfo;
	}

	public synchronized String getGenreBooks(String genre){			//function to show and print books for a specific genre
		BSTNode node = addBook(root, genre);			//we use the helper function for "addBook()" to aid in looking for the specified genre
		return node.getDCList().showBooksWithInfo();			//we access the DCL associated with the BSTNode and print all information
	}

	public synchronized String findBook(String b){						//function to call findBook() and specify to start at the root
		
		boolean found = findBook(root, b);

		if (found == true){								//if we successfuly found the Book, we proceed
			BSTNode node = returnBook(root, b);			//we use returnBook() to look for the node where the book is located
			return "\n" + node.getDCList().searchBook(b).toString() + "\n" + "Genre: " + node.getGenre();	//print all information for the book by accessing the DCL associated with the supplied node
		}
		else{
			return "Book was not found";	//signal to the user that the book does not exist
		}

	}

	private synchronized boolean findBook(BSTNode node, String b){	//helper function for findBook()
		boolean found = false;

			if (node != null){							//checking if supplied node is not null to proceed
				found = node.getDCList().findBook(b);	//we access the DCL associated with the node and call findBook() recursively to tell us if the book exists
				
				if (found == true){						//if the book exists, exit the function and return found
					return found;
				}
				else{
					found = findBook(node.getLeft(), b);	//we check the left child for the book if it does not exist

					if(found == true){					//if the book exists here, exit the function and return found
						return found;
					}
					else{
						found = findBook(node.getRight(), b);	//we check the right child for the book if it does not exist in the previous locations
					}
					
				}
			}
		return found;									//if all else fails, then return found which is going to be false value
	}

	private synchronized BSTNode returnBook(BSTNode node, String b){		//function that returns the NODE where the book you are looking for is located: Implemented using STACKS because recursion is the devil
		boolean found = false;

		Stack<BSTNode> stack = new Stack<BSTNode>();	//defining a stack to aid in the BST traversal
         
        //first node to be visited will be the left one
        while (node != null) {							
            stack.push(node);						//push all left childs to the stack
            node = node.getLeft();
        }
         
        // traverse the tree
        while (stack.size() > 0) {					//while stack is not empty
           
            // visit the top node
            node = stack.pop();						//pop the nodes one by one to traverse the BST
            found = node.getDCList().findBook(b);	//check if the book is in this node
            if(found){								//if we found the book, break the loop
            	break;
            }
            else{
	            if (node.getRight() != null) {		//if the right child is not null, we move to the right
	                node = node.getRight();
	                // the next node to be visited is the leftmost
	                while (node != null) {			//make sure node is not null while operating and push all the left childs to the stack
	                    stack.push(node);
	                    node = node.getLeft();
	                }
	            }
	        }
        }

        return node;			//once we find the book, we return the node where it is located
	}

	public synchronized void deleteBook(String title){			//function to aid in modifying a book, to achieve book modification, we first delete the book, then add the new "modified" book
		BSTNode node = returnBook(root, title);		//we look for the node that contains the book we want to delete
		node.getDCList().deleteNode(title);			//we enter the associated DCL and delete the node of said list that contains the specified title as the book title
	}
}