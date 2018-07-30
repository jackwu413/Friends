package friends;

import structures.Queue;
import structures.Stack;

import java.util.*;

public class Friends {
	
	/**
	 * Finds the shortest chain of people from p1 to p2.
	 * Chain is returned as a sequence of names starting with p1,
	 * and ending with p2. Each pair (n1,n2) of consecutive names in
	 * the returned chain is an edge in the graph.
	 * 
	 * @param g Graph for which shortest chain is to be found.
	 * @param p1 Person with whom the chain originates
	 * @param p2 Person at whom the chain terminates
	 * @return The shortest chain from p1 to p2. Null if there is no
	 *         path from p1 to p2
	 */
	public static ArrayList<String> shortestChain(Graph g, String p1, String p2) {
		if (!g.map.containsKey(p1) || !g.map.containsKey(p2)) {
			System.out.println("One or both of the names is not in the graph");
			return null;
		}
		boolean[] visited = new boolean[g.members.length];
		int[] parents = new int[g.members.length];
		Queue<Integer> q = new Queue<Integer>();
		ArrayList<String> result = new ArrayList<String>();
		q.enqueue(g.map.get(p1));
		while (!q.isEmpty()) {
			int a = q.dequeue(); 
			visited[a] = true;
			Friend tmp = g.members[a].first;
			while (tmp != null) {
				if (visited[tmp.fnum] == false) {
					q.enqueue(tmp.fnum);
					if (parents[tmp.fnum] == 0) {
						parents[tmp.fnum] = a;
					}
					if (g.members[tmp.fnum].name.equals(p2)) {
//						System.out.println("You've gotten to the destination");
						int temp = g.map.get(p2);
						while (parents[temp] != g.map.get(p1)) {
							result.add(0,g.members[(parents[temp])].name);
							temp = parents[temp];
						} 
						result.add(0,g.members[(parents[temp])].name);
						result.add(p2);
						System.out.println("This is the shortest path: " + result);
						return result;
					}
				}
				tmp = tmp.next;
			}
		}
		
//		if (result.size() == 0) {
//			System.out.println("null");
			return null;
//		}
//		return result;
	}
	
	/**
	 * Finds all cliques of students in a given school.
	 * 
	 * Returns an array list of array lists - each constituent array list contains
	 * the names of all students in a clique.
	 * 
	 * @param g Graph for which cliques are to be found.
	 * @param school Name of school
	 * @return Array list of clique array lists. Null if there is no student in the
	 *         given school
	 */
	public static ArrayList<ArrayList<String>> cliques(Graph g, String school) {
		boolean[] inaclique = new boolean[g.members.length];
		ArrayList<ArrayList<String>> cliques = new ArrayList<ArrayList<String>>();
		for (int i = 0; i < g.members.length; i++) {
			if (!inaclique[i]) {
				if (g.members[i].student && g.members[i].school.equals(school)) {
					ArrayList<String> clique = new ArrayList<String>();
					if (!clique.contains(g.members[i].name)) {
						clique.add(g.members[i].name);
					}
					cliques.add(clique);
					Queue<Integer> q = new Queue<Integer>();
					q.enqueue(i);
					while (!q.isEmpty()) {
						int a = q.dequeue();
						inaclique[a] = true;
						if (!clique.contains(g.members[a].name)) {
							clique.add(g.members[a].name);
						}
						Friend tmp = g.members[a].first;
						while (tmp != null) {
							if (g.members[tmp.fnum].student && g.members[tmp.fnum].school.equals(school)) {
								if (inaclique[tmp.fnum] == false) {
									q.enqueue(tmp.fnum);
								}
							}
							tmp = tmp.next;
						}
						
					}
				}
			}
		}
		System.out.println("Here are the cliques at: " + school);
		System.out.println(cliques);
		if (cliques.size() ==0) {
			return null;
		}
		return cliques;
	}
	
	/**
	 * Finds and returns all connectors in the graph.
	 * 
	 * @param g Graph for which connectors needs to be found.
	 * @return Names of all connectors. Null if there are no connectors.
	 */
	public static ArrayList<String> connectors(Graph g) {
		
		ArrayList<String> connectors = new ArrayList<String>();
		
		int [] sizes = new int[g.members.length];
		for (int i = 0; i < g.members.length; i++) {
			Friend tmp = g.members[i].first;
			int count = 0;
			while (tmp != null) {
				count++;
				tmp = tmp.next;
			}
			sizes[i] = count;
		}
		
		//Everything above here just assigns list sizes to each vertex
		
		
		for (int k = 0; k < g.members.length; k++) {
			if (sizes[k] > 1) {
//				System.out.println("Just removed " + g.members[k].name);
				ArrayList<String> surrounders = new ArrayList<String>();
				
				Friend temp = g.members[k].first;
				Friend newtemp = temp;
				
				while (temp != null) {
					surrounders.add(g.members[temp.fnum].name);
					temp = temp.next;
				}
				 

				g.members[k].first = null;
				for (int m = 0; m <= surrounders.size()-1; m++) {
					for(int n = 0; n <= surrounders.size()-1; n++) {
						if (m != n) {
							String first = surrounders.get(m);
							String second = surrounders.get(n);
							if (shortestChain(g, first, second) == null) {
//								System.out.println("no connection between " + first +" + "+ second);
								if (!connectors.contains(g.members[k].name)) {
									connectors.add(g.members[k].name);
									break;
								}
							}
						}	
					}	
					if (connectors.contains(g.members[k].name)) {
						break;
					}
				}
				g.members[k].first = newtemp;
			}
		}
		
		if (connectors.size() == 0) {
			System.out.println("NULL RESULT");
			return null;
		}
		System.out.println(connectors.size() +" " + "CONNECTORS: ");
		System.out.println(connectors);
		return connectors;
	}
}