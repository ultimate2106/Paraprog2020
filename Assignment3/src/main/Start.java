package main;

import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import abstraction.Node;
import implementation.NodeFactory;
import implementation.NodeFactory.NodeType;

/**
 * 
 * @author Benjamin Scheer, Dominic Schroeder, Dominic Jaeger
 *
 */
public class Start {
	private static CountDownLatch startLatch = new CountDownLatch(1);
	private static HashMap<String, Node> nodes = new HashMap<String, Node>();
	private static boolean isElection = false;
	
	/**
	 * Stellt die Verbindungen zwischen den Nodes her
	 * 
	 * @param connections Liste der Nodes, die sich untereinander kennen.
	 */
	private static void createConnections(String[] connections) 
	{
		Node[] neighbours = new Node[connections.length - 1];
		for(int i = 0; i < neighbours.length; ++i) {
			Node node = nodes.get(connections[i+1]);
			neighbours[i] = node;
			if(node == null) {
				System.out.println("Usage: Node " + connections[i+1] + " don't exist");
			}
		}
		Node node = nodes.get(connections[0]);
		if(node == null) {
			System.out.println("Usage: Node " + connections[0] + " don't exist");
		}
		node.setupNeighbours(neighbours);
	}
	
	/**
	 * Erzeugt die Nodes
	 * 
	 * @param initiatorNames Array mit den Namen der Initiatoren
	 * @param nodeNames Array mit den Namen aller Nodes
	 * @param type Der Typ gibt an, welcher Algorithmus angewendet wird.
	 */
	private static void createNodes(String[] initiatorNames, String[] nodeNames, NodeType type) 
	{
		boolean isInitiator = false;
		
		
		for(int i = 0; i < nodeNames.length; ++i) {
			
			for(int j = 0; j < initiatorNames.length; ++j) 
			{
				isInitiator = nodeNames[i].equalsIgnoreCase(initiatorNames[j]);
				if(isInitiator)
				{
					break;
				}
			}
			//System.out.println(nodeNames[i] + ": isInitiator = "+isInitiator);
			nodes.put(nodeNames[i], NodeFactory.GetNode(type, nodeNames[i], isInitiator, startLatch));
		}
	}
	
	/**
	 * Startet das Erzeugen des Graphen
	 * 
	 * @param args Die Übergebenden args von der Main. Hier steht der zu benutzende Algurithmus, sowie die Knoten und deren Verbindung untereinander drin.
	 */
	private static void buildGraph(String[] args) 
	{
		isElection = args[0].toUpperCase().equals("TRUE");
		
		if(!isElection) {
			System.out.println("No election.");
			createNodes(args[2].split(","), args[1].split(","), NodeType.SimpleNode);
		} else {
			System.out.println("Election enabled.");
			createNodes(args[2].split(","), args[1].split(","), NodeType.ElectionNode);
		}
		
		for(int i = 3; i < args.length; ++i) {
			createConnections(args[i].split(","));
		}
	}
	
	public static void main(String[] args) {
		// Create nodes and add them to list
		if(args.length >= 4) {
			buildGraph(args);
			
			ExecutorService execService = Executors.newFixedThreadPool(nodes.size());
			for(Node node : nodes.values()) {
				execService.submit(node);
			}
			
			startLatch.countDown();
			try {
				execService.shutdown();
				execService.awaitTermination(5, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Usage: Must specify at least four arguments \n");
			System.out.println("arg[0] --- Argument must be 'true' or 'false' (without ').\n"
							+ "true = use Election Algorithm \n"
							+ "false = use Echo Algorithm \n\n"
							+ "arg[1] --- Specify the names of the Nodes seperated with a comma.\n"
							+ "For Example a,b,c = Graph with the Nodes a, b and c \n\n"
							+ "arg[2] --- Specify the Initiators.\n"
							+ "For Example a,b = Graph has the initiators a and b. \n\n"
							+ "arg[i >= 3] --- Specify the connections of a Node\n"
							+ "For Example b,d,e = b has a connection with d and b has a connection with e, "
							+ "but d and e hasn't a connection\n");
							
							
		}	
	}

}
