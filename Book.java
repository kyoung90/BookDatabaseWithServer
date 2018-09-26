/*
	 * 	CPEN 457 - Programming Languages
	 * 	Book information
	 * 	Authors: Kenneth Young, Roberto Santana
	 * 	Spring 2018
	 * 
	 */
public class Book {
	
	protected String title;			//defining the title as a string
	protected String plot;			//defining the plot as a string
	protected SinglyList authors;	//defining the authors as a SinglyList from our class
	protected String publisher;		//defining the publisher as a string
	protected String releaseYear;	//defining the releaseYear as a string

	//Default Constructor
	
	Book (){						//default constructor that assigns empty strings and an empty authors list
		title = null;
		plot = null;
		authors = null;
		publisher = null;
		releaseYear = null;
	}
	
	//Constructor
	/*Parameters
	 * t = title
	 * p = plot
	 * a = authors
	 * d = publisher
	 * r = releaseYear
	 * 
	 */
	Book (String t, String p, SinglyList a, String d, String r){	//creating a book object with the supplied strings and list
		this.title = t;
		this.plot = p;
		this.authors = a;
		this.publisher = d;
		this.releaseYear = r;
	}
	
	/*
	 * getTitle method
	 * Task: get book title 
	 */
	public String getTitle(){
		return title;			//function that returns the title data of the book in string format
	}
	/*
	 * getPlot method
	 * Task: get book plot
	 */
	public String getPlot(){
		return plot;			//function that returns the plot data of the book in string format
	}
	/*
	 * getAuthors method
	 * Task: get book authors
	 */
	public SinglyList getAuthors(){
		return authors;			//function that returns the authors of the book in SinglyList format
	}
	/*
	 * getPublisher method
	 * Task: get book publisher
	 */
	public String getPublisher(){
		return publisher;		//function that returns the publisher of the book in string format
	}
	/*
	 * getReleaseYear method
	 * Task: get book release year
	 */
	public String getReleaseYear(){
		return releaseYear;		//function that returns the releaseYear of the book in string format
	}
	/*
     * 	setTitle method
     * 	Task: set book title
     * 
     * Parameter 
     * t = book title 
     * 
     */
	public void setTitle(String t) {
		this.title = t;					//function to allow us to set the book's title
	}
	
	/*
     * 	setTitle method
     * 	Task: set book plot
     * 
     * Parameter 
     * p = book plot 
     * 
     */
	public void setPlot(String p) {
		this.plot = p;					//function that allows us to set the book's plot
	}
	/*
     * 	setAuthors method
     * 	Task: set book authors
     * 
     * Parameter 
     * a = book authors 
     * 
     */
	public void setAuthors(SinglyList a) {
		this.authors = a;				//function that allows us to set the book's authors to the SinglyList provided
	}
	/*
     * 	setPublisher method
     * 	Task: set book publisher
     * 
     * Parameter 
     * d = book publisher 
     * 
     */

	public void setPublisher(String d) {
		this.publisher = d;				//function that allows us to set the book's publisher
	}
	
	/*
     * 	setReleaseYear method
     * 	Task: set book release year
     * 
     * Parameter 
     * r = book release year 
     * 
     */
	public void setReleaseYear(String r) {
		this.releaseYear = r;			//function that allows us to set the book's releaseYear
	}
		
	/*
     * 	toString method
     * 	Task: return a string with all the book information
     * 
     * 
     */
	public String toString(){
		String info = "";
		info += "Title: " + this.title;
		info += "\nPlot: " + this.plot;
		info += "\nAuthors: " + authors.getAuthors();	//uses the getAuthors() function to access the SinglyList and return authors
		info += "\nPublisher: " + this.publisher;
		info += "\nRelease Year: " + this.releaseYear;
		return info;
	}
		


	}


