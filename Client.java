/*
     *  CPEN 457 - Programming Languages
     *  Client Side Code
     *  Authors: Kenneth Young, Roberto Santana
     *  Spring 2018
     * 
     */

/*To run this code: 
    1- your terminal must have the path to the java jdk set. To do this, run in the cmd PATH=%PATH%;"C:\Program Files\Java\jdk1.8.0_121\bin";
*/

import java.util.*;
import java.io.*;
import java.net.*;

public class Client {	//defining our client class
	public static void main(String[] args) throws IOException{

		if (args.length != 2) {		//input validation for when we try to connect to the server, this tells the user the correct way to request connection
            System.err.println("Usage: java Client <host name> <port number>");
            System.exit(1);
        }

	    String hostName = args[0];		//we grab the hostname and the port number from the user input at runtime
	    int port = Integer.parseInt(args[1]);

	    try{ 
            Codes code = new Codes();		//creating our codes object that handles the server-client protocol
            Socket tdbSocket = new Socket(hostName, port);		//creating a new socket from the acquired information
             
            DataInputStream inStream=new DataInputStream(tdbSocket.getInputStream());		//defining the dataInputStream to enable receiving messages over the network
            DataOutputStream outStream=new DataOutputStream(tdbSocket.getOutputStream());	//defining the dataOutputStream to enable sending messages over the network
            BufferedReader br=new BufferedReader(new InputStreamReader(System.in));			//defining our buffered reader to use it in the future for user input
           
            
            

            printMenu(inStream, outStream, br, code);		//calling the print menu function to show the user the menu

             
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);		//tell the user that we could not find the host
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +	//tell the user that we could not establish the I/O connection to the host
                hostName);
            System.exit(1);
        }


	}

	/*
		Function: printMenu

		Prints all information regarding the menu and enables user interaction.

		Parameters: inStream (needed to handle receiving info over network), outStream (needed to sending info over the network),
		br (needed to accept user input and not have errors with data),
		code (needed to handle our server-client protocol information)

	*/

    public static void printMenu(DataInputStream inStream, DataOutputStream outStream, BufferedReader br, Codes code)
    {

    Scanner intInput = new Scanner(System.in);		//defining scanner for int type user inputs
    Scanner stringInput = new Scanner(System.in);	//defining scanner for string type user inputs
    int option=0;									//defining variable to allow us to select options

    while(option !=8)
    {

    System.out.println("");
    System.out.println("Welcome, select an option:");
    System.out.println("1. Add a genre");
    System.out.println("2. Add a book");
    System.out.println("3. Modify a book");
    System.out.println("4. List all genre");
    System.out.println("5. List all book by genre");
    System.out.println("6. List all book for a particular genre");
    System.out.println("7. Search for a book");
    System.out.println("8. Exit");
    System.out.println("");

    System.out.print("Please write your selection: ");
    option = intInput.nextInt();

    if(option !=1 && option !=2 && option !=3 && option !=4 && option !=5 && option !=6 && option !=7 && option !=8){
    System.out.print("Invalid menu item specified, please write your selection: ");
    option = intInput.nextInt();
    }

    switch(option){
    case 1:
    {
        try{
            String clientMessage="", serverMessage="";
            //Sending to server the request to add a genre
            outStream.writeUTF(code.ADDGENRE);
            outStream.flush();

            //Wait for server response
            serverMessage = inStream.readUTF();

            // If server responded that we are good to go then continue
            if (serverMessage.equals(code.OK)){
               
                // Read genre from client
                System.out.print("Please enter the genre: ");
                clientMessage = br.readLine();

                // Send to server
                outStream.writeUTF(clientMessage);
                outStream.flush();
                System.out.println("Genre was successfully inserted!");

            }
            // If we got to this point, then there was either something wrong with the connection or something weird happened. ABORT!!
            else{
                System.out.println("Something weird has happened. Exiting");
                System.exit(0);
                
            }

        } catch (IOException e) {
            System.out.println("Exception caught");
            System.out.println(e.getMessage());		//print exception error
        }

        // Print the menu again
        printMenu(inStream, outStream, br, code);
        break;

    }

    case 2:
    {
        // Create strings to hold server and client messages
        String clientMessage="", serverMessage="";

        try{

            // Sending request to server to list book
            outStream.writeUTF(code.LISTALLGENRE);
            outStream.flush(); 

            // Read Response from server
            serverMessage = inStream.readUTF(); 

            // if server responded with OK then continue
            if (serverMessage.equals(code.OK)){
                
                // Receive genres from server
                serverMessage = inStream.readUTF();

                // Print all genres received from server and ask user for book info.
                System.out.println("To add a book, please select a genre. Here is a list of all available genres: ");
                System.out.print(serverMessage);
                System.out.println("");
               
               // Receive the genre where the user wants to enter his book
                System.out.println("Enter the genre: ");
                clientMessage = br.readLine();

                // Store the genre where the user wants to enter his book
                String genreSelected = clientMessage;

                // Check if what the user entered exists in the BST by sending what the user entered to the server
                // Sending request to check if what user entered exists
                outStream.writeUTF(code.CHECKIFGENREEXISTS);
                outStream.flush();

                // Read Response from server
                serverMessage = inStream.readUTF(); 

                // if server responded with OK then continue
                if (serverMessage.equals(code.OK)){
                    // send what the user entered so the server can respond if it exists or not
                    outStream.writeUTF(clientMessage);
                    outStream.flush();

                    // Receive from server if it exists or not in a String
                    serverMessage = inStream.readUTF();

                    // If it exists, then continue
                    if (serverMessage.equals("true")){

                        // Ask user for the books title
                        System.out.print("Enter the book's title: ");
                        String title = br.readLine();
                        // book.setTitle(title);

                        // Ask user for the books plot
                        System.out.print("Enter the book's plot: ");
                        String plot = br.readLine();
                        //     book.setPlot(plot);

                        // Ask user for the amount of authors so that you know how many authors to make space for
                        System.out.print("Enter the amount of authors: ");
                        int authorsAmount  = Integer.parseInt(br.readLine());

                        // Ask user for authors and store it in an string
                        String authors = "";
            
                        for(int i = 0; i < authorsAmount; i++){
                            // Ask user for each author
                            System.out.print("Please enter one at a time. Enter the name of the author followed by the last name (ex- Albert Einstein): ");
                            authors += br.readLine() + " ";
                        }

                        // Ask user for the book's publisher
                        System.out.print("Enter the book's publisher: ");
                        String publisher = br.readLine();

                        // Ask user for the book's release year
                        System.out.print("Enter the book's release year: ");
                        String releaseYear = br.readLine();

                        // Print out all the information that the user entered
                        System.out.println("Information entered was: ");
                        System.out.println("Title: " + title);
                        System.out.println("Genre: " + genreSelected);
                        System.out.println("Plot: " + plot);
                        System.out.println("Authors: " + authors);
                        System.out.println("Publisher: " + publisher);
                        System.out.println("Release Year: " + releaseYear);

                        // Request the server to add a book
                        outStream.writeUTF(code.ADDBOOK);
                        outStream.flush();

                        // Read Response from server
                        serverMessage = inStream.readUTF(); 

                        // if server responded with OK then continue
                        if (serverMessage.equals(code.OK)){
                            // Send all information to the server one at a time so that the server can add the book
                            // Sending title
                            outStream.writeUTF(title);
                            outStream.flush();

                            // Sending plot
                            outStream.writeUTF(plot);
                            outStream.flush();

                            // Sending publisher
                            outStream.writeUTF(publisher);
                            outStream.flush();

                            // Sending release year
                            outStream.writeUTF(releaseYear);
                            outStream.flush();

                            // Sending authors
                            outStream.writeUTF(authors);
                            outStream.flush();

                            // Sending Genre
                            outStream.writeUTF(genreSelected);
                            outStream.flush();

                        }
                        // If we got to this point, then there was either something wrong with the connection or something weird happened. ABORT!!
                        else{
                            System.out.println("Something weird has happened. Exiting");
                            System.exit(0);
                        }

                    }
                    // If it doesnt exist, then print out that it doesnt exist
                    else{
                        System.out.println("\nThe genre you entered doesn't exist");                        
                    }
                }
                // If we got to this point, then there was either something wrong with the connection or something weird happened. ABORT!!
                else{
                    System.out.println("Something weird has happened. Exiting");
                    System.exit(0);
                } 
            }
            // If we got to this point, then there was either something wrong with the connection or something weird happened. ABORT!!
            else{
                System.out.println("Something weird has happened. Exiting");
                System.exit(0);   
            }

        } catch (IOException e) {
            System.out.println("Exception caught");
            System.out.println(e.getMessage());		//print the exception error message
        }

        // Print the menu again
        printMenu(inStream, outStream, br, code);
        break;
    }
    
    case 3:
    {
        // Create strings to hold server and client messages
        String clientMessage="", serverMessage="";
        
        try{
            // Ask user to enter the title of the book he wants to search for
            System.out.print("Please enter the book's title: ");
            clientMessage = br.readLine();

            // Store book title
            String oldBookTitle = clientMessage;
        
            // Sending request to search for a book
            outStream.writeUTF(code.SEARCHFORABOOK);
            outStream.flush();

            // Read Response from server
            serverMessage = inStream.readUTF(); 

            // if server responded with OK then continue
            if (serverMessage.equals(code.OK)){
                // Send server the title of the book the client wants to search for
                outStream.writeUTF(clientMessage);
                outStream.flush();

                // Receive information about the book and print it. 
                serverMessage = inStream.readUTF();
                System.out.println(serverMessage);

                // If book wasn't found, then exit
                if(serverMessage.equals("Book was not found")){
                    System.out.println("Please enter a book that exists next time.");
                }
                // Otherwise continue
                else{
                    // Ask the user if he wants to modify the book
                    System.out.println("Are you sure you want to modify this book(y/n)?");
                    char opt = br.readLine().charAt(0);

                    // If user wants to modify book, then continue
                    if(opt == 'y' || opt == 'Y'){  
                        // Sending request to server to list book
                        outStream.writeUTF(code.LISTALLGENRE);
                        outStream.flush(); 

                        // Read Response from server
                        serverMessage = inStream.readUTF(); 

                        // if server responded with OK then continue
                        if (serverMessage.equals(code.OK)){
                            
                            // Receive genres from server
                            serverMessage = inStream.readUTF();

                            // Print all genres received from server and ask user for book info.
                            System.out.println("Please select a genre. Here is a list of all available genres: ");
                            System.out.print(serverMessage);
                           
                           // Receive the genre where the user wants to enter his book
                            clientMessage = br.readLine();

                            // Store the genre where the user wants to enter his book
                            String genreSelected = clientMessage;

                            // Check if what the user entered exists in the BST by sending what the user entered to the server
                            // Sending request to check if what user entered exists
                            outStream.writeUTF(code.CHECKIFGENREEXISTS);
                            outStream.flush();

                            // Read Response from server
                            serverMessage = inStream.readUTF(); 

                            // if server responded with OK then continue
                            if (serverMessage.equals(code.OK)){
                                // send what the user entered so the server can respond if it exists or not
                                outStream.writeUTF(clientMessage);
                                outStream.flush();

                                // Receive from server if it exists or not in a String
                                serverMessage = inStream.readUTF();

                                // If it exists, then continue
                                if (serverMessage.equals("true")){

                                    // Ask user for the books title
                                    System.out.println("Enter the book's title: ");
                                    String title = br.readLine();
                                    // book.setTitle(title);

                                    // Ask user for the books plot
                                    System.out.println("Enter the book's plot: ");
                                    String plot = br.readLine();
                                    //     book.setPlot(plot);

                                    // Ask user for the amount of authors so that you know how many authors to make space for
                                    System.out.println("Enter the amount of authors");
                                    int authorsAmount  = Integer.parseInt(br.readLine());

                                    // Ask user for authors and store it in an string
                                    String authors = "";
                        
                                    for(int i = 0; i < authorsAmount; i++){
                                        // Ask user for each author
                                        System.out.print("Please enter one at a time. Enter the name of the author followed by the last name (ex- Albert Einstein): ");
                                        authors += br.readLine() + " ";
                                    }

                                    // Ask user for the book's publisher
                                    System.out.println("Enter the book's publisher: ");
                                    String publisher = br.readLine();

                                    // Ask user for the book's release year
                                    System.out.println("Enter the book's release year: ");
                                    String releaseYear = br.readLine();

                                    // Print out all the information that the user entered
                                    System.out.println("Information entered was: ");
                                    System.out.println("Title: " + title);
                                    System.out.println("Genre: " + genreSelected);
                                    System.out.println("Plot: " + plot);
                                    System.out.println("Authors: " + authors);
                                    System.out.println("Publisher: " + publisher);
                                    System.out.println("Release Year: " + releaseYear);

                                    // Request the server to add a book
                                    outStream.writeUTF(code.MODIFYBOOK);
                                    outStream.flush();

                                    // Read Response from server
                                    serverMessage = inStream.readUTF(); 

                                    // if server responded with OK then continue
                                    if (serverMessage.equals(code.OK)){
                                        // Send all information to the server one at a time so that the server can add the book
                                        // Sending title
                                        outStream.writeUTF(title);
                                        outStream.flush();

                                        // Sending plot
                                        outStream.writeUTF(plot);
                                        outStream.flush();

                                        // Sending publisher
                                        outStream.writeUTF(publisher);
                                        outStream.flush();

                                        // Sending release year
                                        outStream.writeUTF(releaseYear);
                                        outStream.flush();

                                        // Sending authors
                                        outStream.writeUTF(authors);
                                        outStream.flush();

                                        // sending old book title in casse it was changed to server. This is sent so that it can be deleted correctly
                                        outStream.writeUTF(oldBookTitle);
                                        outStream.flush();

                                        // Sending Genre
                                        outStream.writeUTF(genreSelected);
                                        outStream.flush();

                                    }
                                    // If we got to this point, then there was either something wrong with the connection or something weird happened. ABORT!!
                                    else{
                                        System.out.println("Something weird has happened. Exiting");
                                        System.exit(0);
                                    }

                                }
                                // If it doesnt exist, then print out that it doesnt exist
                                else{
                                    System.out.println("The genre you entered doesn't exist");                        
                                }
                            }
                            // If we got to this point, then there was either something wrong with the connection or something weird happened. ABORT!!
                            else{
                                System.out.println("Something weird has happened. Exiting");
                                System.exit(0);
                            } 
                        }
                        // If we got to this point, then there was either something wrong with the connection or something weird happened. ABORT!!
                        else{
                            System.out.println("Something weird has happened. Exiting");
                            System.exit(0);   
                        }
                                }
                    // otherwise, exit logic
                    else{
                        System.out.println("No changes were made.");
                    }
                }


            }
            // If we got to this point, then there was either something wrong with the connection or something weird happened. ABORT!!
            else{
                System.out.println("Something weird has happened. Exiting");
                System.exit(0);   
            }

        } catch (IOException e) {
            System.out.println("Exception caught");
            System.out.println(e.getMessage());
        }

        break;
    }

    case 4:
    {
        // Create strings to hold server and client messages
        String clientMessage="", serverMessage="";

        try{

            // Sending request to server to list book
            outStream.writeUTF(code.LISTALLGENRE);
            outStream.flush(); 

            // Read Response from server
            serverMessage = inStream.readUTF(); 

            // if server responded with OK then continue
            if (serverMessage.equals(code.OK)){
                
                // Receive genres from server
                serverMessage = inStream.readUTF();

                // Print all genres received from server.
                System.out.println("Here is a list of all genres: ");
                System.out.println("");
                System.out.print(serverMessage);     

            } 
            // If we got to this point, then there was either something wrong with the connection or something weird happened. ABORT!!
            else{
                System.out.println("Something weird has happened. Exiting");
                System.exit(0);   
            }

        } catch (IOException e) {
            System.out.println("Exception caught");
            System.out.println(e.getMessage());
        }

        // Print the menu again
        printMenu(inStream, outStream, br, code);
        break; 
    }

    case 5:
    {
        // Create strings to hold server and client messages
        String clientMessage="", serverMessage="";

        try{
            // Sending request to server to list book
            outStream.writeUTF(code.LISTALLBOOKBYGENRE);
            outStream.flush(); 

            // Read Response from server
            serverMessage = inStream.readUTF(); 

            // if server responded with OK then continue
            if (serverMessage.equals(code.OK)){
                
                // Receive genres and books from server
                serverMessage = inStream.readUTF();

                // Print all genres and books received from server.
                System.out.println("Here is a list of all books ordered by genres: ");
                System.out.print(serverMessage);     

            } 
            // If we got to this point, then there was either something wrong with the connection or something weird happened. ABORT!!
            else{
                System.out.println("Something weird has happened. Exiting");
                System.exit(0);   
            }
        } catch (IOException e) {
            System.out.println("Exception caught");
            System.out.println(e.getMessage());
        }

        break;
    }

    case 6:
    {
        // Create strings to hold server and client messages
        String clientMessage="", serverMessage="";

        try{

            // Receive the genre where the user wants to list all books
            System.out.print("Please enter the genre you want to list all books for: ");
            clientMessage = br.readLine();

            // Store the genre where the user wants to list all books
            String genreSelected = clientMessage;

            // Check if what the user entered exists in the BST by sending what the user entered to the server
            // Sending request to check if what user entered exists
            outStream.writeUTF(code.CHECKIFGENREEXISTS);
            outStream.flush();

            // Read Response from server
            serverMessage = inStream.readUTF(); 

            // if server responded with OK then continue
            if (serverMessage.equals(code.OK)){
                // send what the user entered so the server can respond if it exists or not
                outStream.writeUTF(clientMessage);
                outStream.flush();

                // Receive from server if it exists or not in a String
                serverMessage = inStream.readUTF();

                // If it exists, then continue
                if (serverMessage.equals("true")){
                    // Sending request to server to list book for a particular genre
                    outStream.writeUTF(code.LISTALLBOOKFORPARTICULARGENRE);
                    outStream.flush();

                    // Read Response from server
                    serverMessage = inStream.readUTF(); 

                    // if server responded with OK then continue
                    if (serverMessage.equals(code.OK)){

                        // Sending genre to server
                        outStream.writeUTF(genreSelected);
                        outStream.flush();

                        // Receive books from server
                        serverMessage = inStream.readUTF();

                        // Print all books for the genre specified.
                        System.out.println("Here is a list of all books ordered for the genre " + genreSelected + ": ");
                        System.out.print(serverMessage);     

                    } 
                    // If we got to this point, then there was either something wrong with the connection or something weird happened. ABORT!!
                    else{
                        System.out.println("Something weird has happened. Exiting");
                        System.exit(0);   
                    }
                }
                // If it doesnt exist, then print out that it doesnt exist
                else{
                    System.out.println("The genre you entered doesn't exist");                        
                }
            }
            // If we got to this point, then there was either something wrong with the connection or something weird happened. ABORT!!
            else{
                System.out.println("Something weird has happened. Exiting");
                System.exit(0);   
            }

        } catch (IOException e) {
            System.out.println("Exception caught");
            System.out.println(e.getMessage());
        }
     
     break;
    }

    case 7:
    {

        // Create strings to hold server and client messages
        String clientMessage="", serverMessage="";
        
        try{
            // Ask user to enter the title of the book he wants to search for
            System.out.print("Please enter the book's title: ");
            clientMessage = br.readLine();
        
            // Sending request to search for a book
            outStream.writeUTF(code.SEARCHFORABOOK);
            outStream.flush();

            // Read Response from server
            serverMessage = inStream.readUTF(); 

            // if server responded with OK then continue
            if (serverMessage.equals(code.OK)){
                // Send server the title of the book the client wants to search for
                outStream.writeUTF(clientMessage);
                outStream.flush();

                // Receive information about the book and print it. If it doesn't exist, server will handle that
                serverMessage = inStream.readUTF();
                System.out.println(serverMessage);
            }
            // If we got to this point, then there was either something wrong with the connection or something weird happened. ABORT!!
            else{
                System.out.println("Something weird has happened. Exiting");
                System.exit(0);   
            }

        } catch (IOException e) {
            System.out.println("Exception caught");
            System.out.println(e.getMessage());		//print exception error message
        }

        break;
    }

    case 8:
    {
        // Create strings to hold server and client messages
        String clientMessage="", serverMessage="";

        try{
            // Sending request to exit connection
            outStream.writeUTF(code.EXIT);
            outStream.flush();
            serverMessage = inStream.readUTF();

            if(serverMessage.equals(code.OK)){
                outStream.close();
                inStream.close();

            // Exit connection
            System.out.println("Ending connection. Goodbye!");
            }  

           
        } catch (IOException e) {
            System.out.println("Exception caught");
            System.out.println(e.getMessage());
        }

        break;
    }

}//SwitchEnd

}//WhileEnd

}//PrintMenuEnd
}



// String clientMessage="",serverMessage="";
// // Code to read input and send info
// clientMessage=br.readLine();
// outStream.writeUTF(clientMessage);
// outStream.flush();

// // Code to receive info
// serverMessage=inStream.readUTF();
// System.out.println(serverMessage);