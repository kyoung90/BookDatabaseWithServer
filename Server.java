/*
	 * 	CPEN 457 - Programming Languages
	 * 	Serverside Implementation (Database and connection accepter)
	 * 	Authors: Kenneth Young, Roberto Santana
	 * 	Spring 2018
	 * 
	 */



import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server{

	public static void main(String[] args) throws IOException {

		BST database = new BST();

		// CODE TO ADD SOME DUMMY DATA
		Authors author1 = new Authors(new String("Kenneth"), new String("Young"));
		Authors author2 = new Authors(new String("Gabriela"), new String("Rivera"));
		Authors author3 = new Authors(new String("Maria"), new String("Lopez"));
		Authors author4 = new Authors(new String("Onyx"), new String("Blake"));
		Authors author5 = new Authors(new String("Oscar"), new String("Castro"));
		Authors author6 = new Authors(new String("Roberto"), new String("Santana"));
		
		SinglyList SL1 = new SinglyList();
		SinglyList SL2 = new SinglyList();
		SinglyList SL3 = new SinglyList();


		SL1.insert(author1);
		SL1.insert(author2);
		SL2.insert(author3);
		SL2.insert(author4);
		SL3.insert(author5);
		SL3.insert(author6);
		
		Book m7 = new Book(new String("meep"), new String("ploto"), SL1, new String("Directorrr"), new String("1994"));
		Book m1 = new Book(new String("car"), new String("plotboss"), SL1, new String("directorboss"), new String("1995"));
		Book m2 = new Book(new String("lop"), new String("plotboss"), SL2, new String("directorboss"), new String("1995"));
		Book m3 = new Book(new String("desu"), new String("plotboss"), SL2, new String("directorboss"), new String("1995"));
		Book m4 = new Book(new String("arg"), new String("plotboss"), SL3, new String("directorboss"), new String("1995"));
		Book m5 = new Book(new String("kay"), new String("plotboss"), SL3, new String("directorboss"), new String("1995"));
		Book m6 = new Book(new String("zee"), new String("plotboss"), SL3, new String("directorboss"), new String("1995"));

		database.insert("Drama");
		database.insert("Horror");
		database.insert("Comedy");
		database.insert("Action");

		database.addBook("Drama", m1);
		database.addBook("Drama", m2);
		database.addBook("Horror", m3);
		database.addBook("Comedy", m4);
		database.addBook("Action", m5);
		database.addBook("Action", m6);
		database.addBook("Action", m7);

		//handling port number
		if (args.length != 1) {
            System.err.println("Enter <port number>");
            System.exit(1);
        }

        //getting por number from entered information
        int port = Integer.parseInt(args[0]);

        try{
			ServerSocket serverSocket = new ServerSocket(port);		//creating a new socket for clients
	        System.out.println("Waiting for clients");
	        
	        while(true){
		        Socket clientSocket = serverSocket.accept();		//when clients asks for connection, accept this connection!
		        System.out.println("Client connected!");
		        
		        //Create the handler for each client connection
		        tdbHandler handler = new tdbHandler(clientSocket, database);
		        //Do data transfer
	        	handler.start();

	        	// break;
		        
	        }        
	        
	        //Close the server socket
	        // serverSocket.close();
	        
		} catch (IOException e) {
		
		}	

	}
}