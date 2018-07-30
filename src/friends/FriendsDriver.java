package friends;

import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;


public class FriendsDriver {
	public static void main(String[] args) {
		System.out.println("Program Started");
		Graph g;
		try {
			//list 1 is number 5 (p1 to p10)
//			list 2 is number 4 (p1 to p50)
			File myfile = new File("list2.txt");	
			Scanner sc = new Scanner(myfile);
			g = new Graph(sc);
			sc.close();
			Friends.shortestChain(g, "p1", "p50");	
//			Friends.cliques(g, "rutgers");
//			Friends.connectors(g);
		} catch (FileNotFoundException e){
			System.out.println("Error: File Not Found");
		}
	}
	
}

