/*
	 * 	CPEN 457 - Programming Languages
	 *  Singly Linked List and Author Classes
	 * 	Authors: Kenneth Young, Roberto Santana 
	 * 	Spring 2018
	 * 
	 */
public class SinglyList {
	protected Node start, end; // start node and end node of the linked list
    public int size; // size of the linked list
 
    /*
	 * Default Constructor
	 * 
	 */
    public SinglyList()
    {
        start = null;
        end = null;
        size = 0;
    }
    
    /*
     * 	isEmpty method
     * 	Task: check if the linked list is empty
     * 
     * 
     */
    public boolean isEmpty()
    {
        return start == null;
    }
    
    /*
     * 	getSize method
     * 	Task: get size of the linked list
     * 
     * 
     */
    public int getSize()
    {
        return size;
    }
    
    /*
     * 	insert method
     * 	Task: insert an element in the correct order alphabetically
     * 
     * 
     */
    
    public void insert(Authors a){
    	Node nptr = new Node(a, null);
        
    	//If list empty, add author to start of list and make it start
    	if (start == null){
    		start = nptr;
    		end = nptr;
    		size++;       //increment size of list by 1 to symbolize new node added
    		return;
    	}
    	//If new author lastname > list start author last name, then add new author and make the new author the start
    	else if (start.authors.getLastName().compareTo(a.getLastName()) > 0){
    		nptr.setLinkNext(start);
    		start = nptr;
    		size++;
    		return;
    	}
    	//If list new author last name < end list author last name, then add new author and make the new author the end
    	else if (end.authors.getLastName().compareTo(a.getLastName()) < 0){
    		end.setLinkNext(nptr);
    		end = nptr;
    		size++;
    		return;
    		
    	}
    	//Check where to add the new author based on his last name in the middle of the linked list
    	//(checks the last names in the list and compares if its < than the author's last name entered, 
    	//if so insert the author before the element
    	else {
    		Node current = start.getLinkNext();
    		Node prevptr = start;
    		while (current != end){
    			if(current.authors.getLastName().compareTo(a.getLastName()) > 0){
    				nptr.setLinkNext(current);
    				prevptr.setLinkNext(nptr);
    				size++;
    				return;
    				
    			}
    			prevptr = prevptr.getLinkNext();
    			current = current.getLinkNext();
    		}
    	}
    }
    public void showAuthors(){                              //traverse the list until the end and print all authors with the help of "getLastName() method"
    	Node current = start;
    	for(int i = 0; i < getSize(); i++){
    		System.out.print(current.authors.getLastName() + " ");
    		current = current.getLinkNext();               //set current to the next ptr to keep traversing the list
    	}
    }
    
    public String getAuthors(){						//function to return the authors that are in the list
    	String info = "";                          //declare info string to add information to it
    	Node current = start;
    	for(int i = 0; i < getSize(); i++){       //traverse list and add all authors to it and while it is not the last author add a comma to the string
    		if(i < getSize() -1)
    			info += current.authors.toString() + ", ";
    		else 
    			info += current.authors.toString();
    		current = current.getLinkNext();
    	}
    	return info;                              //return the original string with all the information added
    	
    }
    
    // Deletes the node that matches the author given
    public void deleteAuthor(String a){
        Node nptr = start;
        Node current = start;

        // If the element is the first one in the list, just make the start ptr point to the next element in the list
        if (nptr.getAuthors().compareTo(a) == 0){
            start = nptr.getLinkNext();
            size--;
        }
        else{
            nptr = nptr.getLinkNext();
            // Iterate through the whole list. If you find the author that matches the parameter given, delete the node
            // by making the previous node ptr point to the node's next pointer
            while(nptr != end){
                if(nptr.getAuthors().compareTo(a) == 0){
                    current.setLinkNext(nptr.getLinkNext());            //set the next to the current's ptr to the next of the specified nptr
                    size--;
                    return;
                }
                else{
                    nptr = nptr.getLinkNext();                        //move the nptr to keep operations in line
                    current = current.getLinkNext();                  //move current to keep operations in line and too keep traversing the list
                }
            }

            if (nptr.getAuthors().compareTo(a) == 0){                 //keep checking the node to see if the author is the one you want to delete
                current.setLinkNext(null);
                end = current;
                size--;                                              //if deleted, decrement size so we can symbolize the deletion
            }
        }

    }
}

class Node    
{
    protected Authors authors;
    protected Node next;// the next node 
 
    /*
	 * Default Constructor
	 * 
	 */
    public Node()
    {
        next = null;                //initializing the next node to null
        authors = null;             //initializing the authors list to null
    }
    
    //Constructor
  	/*Parameters
  	 * a = authors
  	 * n = next node
  	 */
    public Node(Authors a, Node n)
    {
        authors = a;              //initializing the authors list to the supplied list
        next = n;                 //initializing the next node to the supplied node
    }
    
    
    /*
     * 	setLinkNext method
     * 	Task: set next node
     * 
     * Parameter
     * n = next node
     * 
     */
    public void setLinkNext(Node n)
    {
        next = n;               //function that allows us to set the next node to the supplied node
    }
    
    /*
     * 	getLinkNext method
     * 	Task: get next node
     * 
     * 
     */
    public Node getLinkNext()
    {
        return next;          //function that allows us to access the next node ptr
    }
    
    /*
     * 	setAuthors method
     * 	Task: set authors
     * 
     * Parameter: d : movie data
     * 
     */
    public void setAuthors(Authors a)       //function that allows us to set the authors to a supplied list
    {
        authors = a;
    }
    
    
    /*
     * 	getAuthors method
     * 	Task: get author Last name and first name information
     * 
     * 
     */
    public String getAuthors()
    {
        return authors.toString();          //function that allows us to access the authors list
    }
    
    
}

//Authors Class
 class Authors{
	 protected String firstName;           //defining author first name as a string
	 protected String lastName;            //defining author last name as a string
	
	 //Default Constructor
	 Authors(){
		 firstName = null;                //initializing the author first name as null
		 lastName = null;                 //initializing the author last name as null
	 }
	 
	//Constructor
		/*Parameters
		 * fn = first name
		 * ln = last name
		 */
	 Authors(String fn, String ln){
		 firstName = fn;                 //assigning the author first name to the supplied string
		 lastName = ln;                  //asssigning the author last name to the supplied string
	 }
	 
	 	/*
		* getFirstName method
		* Task: get author's first name
		*/
		public String getFirstName(){
			return firstName;            //function that allows us to access the author's first name string
		}
		
		/*
		* getLastName method
		* Task: get author's last name
		*/
		public String getLastName(){
			return lastName;             //function that allows us to access the author's last name string
		}
		/*
	     * 	setFirstName method
	     * 	Task: set author's first name
	     * 
	     * Parameter 
	     * fn = First Name 
	     * 
	     */
		public void setFirstName(String fn) {     //function that allows us to set the author's first name to the supplied string
			this.firstName = fn;
		}
	 
		/*
	     * 	setLastName method
	     * 	Task: set author's last name
	     * 
	     * Parameter 
	     * ln = Last Name 
	     * 
	     */
		public void setLastName(String ln) {    //function that allows us to the the author's last name to the supplied string
			this.lastName = ln;
		}
		
		/*
	     * 	toString method
	     * 	Task: return a string with all the author names
	     * 
	     * 
	     */
		public String toString(){                 //function that prints all the information by creating a string and accessing the required information 
			String info = "";
			info += this.lastName + " " + this.firstName;
			return info;
		}
}
