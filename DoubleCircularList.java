/*
 * 	CPEN 457 - Programming Languages
 *  Double Circular Linked List Class
 * 	Authors: Kenneth Young, Roberto Santana
 * 	Spring 2018
 * 
 */
public class DoubleCircularList {
	protected ListNode start, end; // start node and end node of the linked list
    public int size; // size of the linked list
 
    /*
	 * Default Constructor
	 * 
	 */
    public DoubleCircularList()
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
    
    public void insert(Book d){
    	ListNode nptr = new ListNode(d, null, null);
        //If list is empty, add at start
    	if (start == null)
        {            
            nptr.setLinkNext(nptr);     //we first set the next pointer of the current node to the nptr
            nptr.setLinkPrev(nptr);     //then we set the prev pointer of the current node to the nptr
            start = nptr;               //we then specify that this new node is the starting node
            end = start;                //then we also specify that is also the end
            size++;                     //increment the list by 1
            return;                     //exit the function
        }
    	//If new book title > list start book title, then add new book and make the new book the start
        else if(start.data.getTitle().compareTo(d.getTitle()) > 0){
        		nptr.setLinkPrev(end);  //set the current node's prev to the end
        		end.setLinkNext(nptr);  //set the end pointer to the new nptr
        		start.setLinkPrev(nptr);//set the start pointer to the new nptr
        		nptr.setLinkNext(start);//set the nptr's next to point to the start
        		start = nptr;           //set the starting pointer to the new nptr
        		size++;                 //increment the list by 1
        		return;                 //exit the function
        	}
    	//If list new book title < end list book title, then add new book and make the new book the end
        else if(end.data.getTitle().compareTo(d.getTitle()) <0){
        	nptr.setLinkPrev(end);      //set nptr's prev to point at end
			end.setLinkNext(nptr);      //set end's next to nptr
            start.setLinkPrev(nptr);    //set start's prev to nptr
            nptr.setLinkNext(start);    //set nptr's next to point to start
            end = nptr;                 //set end to the new nptr
            size++;                     //increment the list by 1
            return;                     //exit the function
        }
        
        //Find correct place to insert it in the middle and insert it(inserts before current) 
    	//Checks if new book title > current book title
        else{	
        	
        	ListNode current = start.getLinkNext();                    //we define a "current" node to help us go through the list and keep pointers consistent
        	while (current != start){                                  //while we have not reached the end, which in this case, means start as to not end early
        	if(current.data.getTitle().compareTo(d.getTitle()) > 0){   //if this operation is true then the new book goes before the current node
        		nptr.setLinkPrev(current.getLinkPrev());               //change the nptr's prev pointer to the current node's prev
        		current.getLinkPrev().setLinkNext(nptr);;              //change previous to the current node's next to the nptr
        		current.setLinkPrev(nptr);                             //we then change the previous of the current node to the new nptr
        		nptr.setLinkNext(current);                             //finally, we change the new nptr's next to the current node
        		                                                       //here, we have successfully added the new node
        		size++;                                                //we increment the list's size by 1
        		
        		return;                                                //exit the function
        	}
        	
        	current = current.getLinkNext();                           //if the previous operation was not done, then we move the current ptr to the next available ptr
            
        	}
        } 
    }
    
 
    /*
     * 	searchBook method
     * 	Task: search and return the book information
     * 
     * Parameter
     * t : title
     * 
     */
    public Book searchBook(String t)
    {
    	ListNode current = start;                             //starting our search at the start of the lit
    	for(int i = 0; i < size; i++){                        //traversing the whole list
    		if(current.data.getTitle().equalsIgnoreCase(t)){  //if the node data "title" equals our search string, then return this data
                return current.data;
    		}
    		current = current.next;                           //if the previous operation was not done, then we change the current ptr to the next to be able to continue traversing the list
    	}
    	return null;
    }
    /*
     * 	findBook method
     * 	Task: find if the book exists
     * 
     * Parameter 
     * t : title
     * 
     */
    public boolean findBook(String t)
    {
    	ListNode current = start;                            //we create a current node to aid in traversing the list
    	for(int i = 0; i < size; i++){                       //traverse the list while we have not reached the end
    		if(current.data.getTitle().equalsIgnoreCase(t)){ //if the book we are looking for matches the book that is in the node, return that we found it
    			return true;
    		}
    		current = current.next;                          //if the operation above was not done, then move the pointer to the next available pointer to continue traversing the list
    	}
    	return false;
    }
    
    /*
     * 	showBook method
     * 	Task: Show the title of all books in the list
     * 
     */
    public void showBooks(){
    	ListNode current = start;
    	for(int i = 0; i < getSize(); i++){                        //traverse the list while we have not reached the end
    		System.out.print(current.data.getTitle() + "\n");      //print our the data in the current node
    		current = current.getLinkNext();                       //move the current node to the next node
    	}
    }

    public String showBooksWithInfo(){
        String booksInfo = "";
        ListNode current = start;                                       //function to show books with all associated information
        for(int i = 0; i < getSize(); i++){                        //traverse the list while we have not reached the end
            booksInfo += "\n" + current.data.getTitle() + "\n";           //print's the title
            booksInfo += current.data.getReleaseYear() + "\n";     //print's the release year
            booksInfo += current.data.getPublisher() + "\n";       //print's the publisher
            current = current.getLinkNext();                            //moves the current pointer to the next available pointer
        }
        return booksInfo;
    }

    public void deleteNode(String a){                                   //function that enables the possibility to delete a node
        ListNode nptr = start;                                          //creating a node to help us traverse the list

        if (nptr.getDataTitle().compareTo(a) == 0){                     //if the comparison between the title of the current node and the supplied title "a" is 0 then this is the node we want to delete
            nptr.getLinkPrev().setLinkNext(nptr.getLinkNext());         //we change the next current ptr's prev to the next ptr of the current ptr
            nptr.getLinkNext().setLinkPrev(nptr.getLinkPrev());         //we change the prev of the current ptr's next to the prev prt of the current ptr
            start = nptr.getLinkNext();                                 //we then change the starting ptr to the nptr's next
            size--;                                                     //after all these operations we decrement the list by 1 to symbolize the decrement of the list, the garbage collector then takes care of the data that's not being pointed to
        }
        else{
            nptr = nptr.getLinkNext();                                  //if the previous operation was not done, this means the node was not the one we wanted to delete, so we move to the next node
            while (nptr != end){                                        //traverse the list finding the correct node
                if(nptr.getDataTitle().compareTo(a) == 0){              //if the comparison between the title of the current node and the supplied title "a" is 0 then this is the node we want to delete
                    nptr.getLinkPrev().setLinkNext(nptr.getLinkNext()); //we change the next current ptr's prev to the next ptr of the current ptr
                    nptr.getLinkNext().setLinkPrev(nptr.getLinkPrev()); //we change the prev of the current ptr's next to the prev prt of the current ptr
                    
                    size--;                                             //after all these operations we decrement the list by 1 to symbolize the decrement of the list, the garbage collector then takes care of the data that's not being pointed to

                    return;
                }
                else{
                    nptr = nptr.getLinkNext();                          //reaching the end of list
                }
            }

            if (nptr.getDataTitle().compareTo(a) == 0){                //checking if the current node's data is the the one we are looking for
                nptr.getLinkPrev().setLinkNext(nptr.getLinkNext());    //moving ptr operations to delete node as above
                nptr.getLinkNext().setLinkPrev(nptr.getLinkPrev());
                end = nptr.getLinkPrev();                              //here, since the node was the last node we have to change the end ptr to point to the prev
                size--;                                                //decrementing the size by 1 to symbolize that the list has one less node
                return;                                                //exiting the function
            } 
        }
        
    }
}




/*
 * 
 * 	ListNode class
 * */
class ListNode    
{
    protected Book data; // book
    protected ListNode next, prev; // the next node and the previous node
 
    /*
	 * Default Constructor
	 * 
	 */
    public ListNode()
    {
        next = null;        //initializing the nodes
        prev = null;
        data = null;
    }
    
    //Constructor
  	/*Parameters
  	 * d = book
  	 * n = next node
  	 * p = previous node
  	 */
    public ListNode(Book d, ListNode n, ListNode p)
    {
        data = d;           //initializing the nodes to the supplied values
        next = n;
        prev = p;
    }
    
    
    /*
     * 	setLinkNext method
     * 	Task: set next node
     * 
     * Parameter
     * n = next node
     * 
     */
    public void setLinkNext(ListNode n)
    {
        next = n;           //changing the next prt to the supplied node    
    }
    
    
    /*
     * 	setLinkPrev method
     * 	Task: set previous node
     * 
     * Parameter
     * p = previous node
     * 
     */
    public void setLinkPrev(ListNode p)
    {
        prev = p;         //changing the prev pointer to the supplied node
    } 
    
   
    /*
     * 	getLinkNext method
     * 	Task: get next node
     * 
     * 
     */
    public ListNode getLinkNext()
    {
        return next;    //supplying the next pointer to the caller
    }
    
    
    /*
     * 	getLinkPrev method
     * 	Task: get previous node
     * 
     * 
     */
    public ListNode getLinkPrev()
    {
        return prev;    //supplying the prev pointer to the caller
    }
    
    
    /*
     * 	setData method
     * 	Task: set book data
     * 
     * Parameter: d : book data
     * 
     */
    public void setData(Book d)
    {
        data = d;       //assigning date to the supplied book
    }
    
    public String getDataTitle()
    {
        return data.getTitle();     //supplying the book's title in string format
    }
    
    /*
     * 	getData method
     * 	Task: get book information
     * 
     * 
     */
    public String getData()
    {
        return data.toString(); //supplying the book's whole information in string format
    }
}