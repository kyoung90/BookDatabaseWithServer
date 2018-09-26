/*
	 * 	CPEN 457 - Programming Languages
	 * 	tdbHandler for Clients
	 * 	Authors: Kenneth Young, Roberto Santana
	 * 	Spring 2018
	 * 
	 */


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class tdbHandler extends Thread{		//defining the handler class, we extend the thread class for multithreaded implementation
	Socket clientSocket;					//defining our socket
	BST database;							//defining our database as a full binary search tree

	public tdbHandler(Socket cs, BST db){	//constructor for when we create an object passing the socket number and the BST object
		try{
    		this.clientSocket = cs;			//we have this in a try and catch to not affect other clients if any connection can't be made
    		this.database = db;
    	}catch(Exception e)
    	{
    		e.printStackTrace();
    	}   
	}

	public void run(){

		try{
			Codes code = new Codes();			//creating a "codes" object that has our server client communication protocol

		    DataInputStream inStream = new DataInputStream(clientSocket.getInputStream());		//defining our datainput stream to receive info over the network
		    DataOutputStream outStream = new DataOutputStream(clientSocket.getOutputStream());	//defining our dataoutput stream to send info over the network
		    String clientMessage="", serverMessage="";											//creating our string variables and initializing them to empty to use them later for messages

		    // Run till the client sends the exit code
		    while(true){
		 		// Read from client
		 		clientMessage = inStream.readUTF();

		 		// Handle client requests

		 		// If client requested to add a genre
		 		if (clientMessage.equals(code.ADDGENRE)){
		 			// Respond with OK to acknowledge request
		 			outStream.writeUTF(code.OK);
		 			outStream.flush();

		 			// Wait for string to enter as genre in the BST
		 			clientMessage = inStream.readUTF();

		 			// Add to BST
		 			database.insert(clientMessage);

		 			// Show all genres in BST for testing purposes
		 			System.out.println(database.order());
		 		}
		 		// If client requested to add a book
		 		else if(clientMessage.equals(code.ADDBOOK)){
		 			// Respond with OK to acknowledge request
		 			outStream.writeUTF(code.OK);
		 			outStream.flush();

		 			// Create book and singly list where we will store authors and book info
	 				Book book = new Book();
	 				SinglyList sl = new SinglyList();

	 				// Receive data from client one at a time
	 				// Receive title
		 			clientMessage = inStream.readUTF();

		 			// Add title to book
		 			book.setTitle(clientMessage);

		 			// Receive plot
		 			clientMessage = inStream.readUTF();

		 			// Add plot to book
		 			book.setPlot(clientMessage);

		 			// Receive publisher
		 			clientMessage = inStream.readUTF();

		 			// Add publisher to book
		 			book.setPublisher(clientMessage);

		 			// Receive release year
		 			clientMessage = inStream.readUTF();

		 			// Add release year to book
		 			book.setReleaseYear(clientMessage);

		 			// Receive authors
		 			clientMessage = inStream.readUTF();

		 			// Segment authors received into different strings
		 			String[] authors = clientMessage.split(" ");

		 			// Add each author to the single linked list
		 			// Since every author has a first name and last name, we will iterate through the data received in sets of 2
		 			for(int i = 0; i < authors.length; i += 2){
		 				// Add first name and last name and insert it into a single linked list
		 				sl.insert(new Authors(authors[i], authors[i+1]));
		 			}

		 			// Add authors to book
		 			book.setAuthors(sl);

		 			// Receive Genre
		 			clientMessage = inStream.readUTF();

		 			// Add book to database by entering correct genre and the book
		 			database.addBook(clientMessage, book);

		 		}
		 		else if(clientMessage.equals(code.MODIFYBOOK)){
		 			// Respond with OK to acknowledge request
		 			outStream.writeUTF(code.OK);
		 			outStream.flush();

		 			// Create book and singly list where we will store authors and book info
	 				Book book = new Book();
	 				SinglyList sl = new SinglyList();

	 				// Receive data from client one at a time
	 				// Receive title
		 			clientMessage = inStream.readUTF();

		 			// Add title to book
		 			book.setTitle(clientMessage);

		 			// Receive plot
		 			clientMessage = inStream.readUTF();

		 			// Add plot to book
		 			book.setPlot(clientMessage);

		 			// Receive publisher
		 			clientMessage = inStream.readUTF();

		 			// Add publisher to book
		 			book.setPublisher(clientMessage);

		 			// Receive release year
		 			clientMessage = inStream.readUTF();

		 			// Add release year to book
		 			book.setReleaseYear(clientMessage);

		 			// Receive authors
		 			clientMessage = inStream.readUTF();

		 			// Segment authors received into different strings
		 			String[] authors = clientMessage.split(" ");

		 			// Add each author to the single linked list
		 			// Since every author has a first name and last name, we will iterate through the data received in sets of 2
		 			for(int i = 0; i < authors.length; i += 2){
		 				// Add first name and last name and insert it into a single linked list
		 				sl.insert(new Authors(authors[i], authors[i+1]));
		 			}

		 			// Add authors to book
		 			book.setAuthors(sl);

		 			// Receive old book title so that you can delete it and then afterwards insert the new book
		 			clientMessage = inStream.readUTF();

		 			// Delete old book
		 			database.deleteBook(clientMessage);


		 			// Receive Genre
		 			clientMessage = inStream.readUTF();

		 			// Add book to database by entering correct genre and the book
		 			database.addBook(clientMessage, book);

		 		}	
		 		// If client requested to list all genre
		 		else if(clientMessage.equals(code.LISTALLGENRE)){
		 			// Respond with OK to acknowledge request
		 			outStream.writeUTF(code.OK);
		 			outStream.flush();

		 			// Send all genres currently in the BST
		 			outStream.writeUTF(database.order());
		 			outStream.flush();
		 		}
		 		else if(clientMessage.equals(code.CHECKIFGENREEXISTS)){
		 			// Respond with OK to acknowledge request
		 			outStream.writeUTF(code.OK);
		 			outStream.flush();

		 			// Wait for client to enter user input
		 			clientMessage = inStream.readUTF();

		 			// Store if what the client entered exists, but store it as a string instead of a bool
		 			String exists = String.valueOf(database.find(clientMessage));
		 			System.out.println("Exists? " + exists);

		 			// Send the value to the client
		 			outStream.writeUTF(exists);
		 			outStream.flush();
		 		}
		 		else if(clientMessage.equals(code.LISTALLBOOKBYGENRE)){
		 			// Respond with OK to acknowledge request
		 			outStream.writeUTF(code.OK);
		 			outStream.flush();
		 			
		 			// Send all genres and books currently in the BST
		 			outStream.writeUTF(database.printBooks());
		 			outStream.flush();

		 		}
		 		else if(clientMessage.equals(code.LISTALLBOOKFORPARTICULARGENRE)){
		 			// Respond with OK to acknowledge request
		 			outStream.writeUTF(code.OK);
		 			outStream.flush();

		 			// Wait for client to enter genre
		 			clientMessage = inStream.readUTF();

		 			// Send all books for the genre specified
		 			outStream.writeUTF(database.getGenreBooks(clientMessage));
		 			outStream.flush();
		 		}
		 		else if(clientMessage.equals(code.SEARCHFORABOOK)){
		 			// Respond with OK to acknowledge request
		 			outStream.writeUTF(code.OK);
		 			outStream.flush();

		 			// Wait for client to enter book title
		 			clientMessage = inStream.readUTF();

		 			// If it exists, return the information about the book, if it doesn't, return that it wasn't found
		 			outStream.writeUTF(database.findBook(clientMessage));
		 			outStream.flush();
		 		}

		 		else if(clientMessage.equals(code.EXIT)){		//handling the connection closing correctly
					outStream.writeUTF(code.OK);				//we acknowledge the client's request to close the connection
		 			outStream.flush();

					outStream.close();							//closing the data outputstream
					inStream.close();							//closing the data inputstream
					clientSocket.close();						//finally closing the socket
					System.out.println("Client has disconnected!");
					break;

		 		}
			}

			// Close connection
			

	    } catch (IOException e) {
            System.out.println("Exception caught");
            System.out.println(e.getMessage());
        }
    }
}

// // Read form client
// clientMessage=inStream.readUTF();
// 		        System.out.println("From Client " + ": Genre is:"+clientMessage);
// 		        database.insert(clientMessage);
// 		        database.order();

// // Write to client
// serverMessage="From Server to Client- Genre inserted ";
// outStream.writeUTF(serverMessage);
// outStream.flush();