package main;

import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import abstraction.Node;
import implementation.NodeFactory;
import implementation.NodeFactory.NodeType;

public class Start {
	private static CountDownLatch startLatch = new CountDownLatch(1);
	private static HashMap<String, Node> nodes = new HashMap<String, Node>();
	private static boolean isElection = false;
	
	private static void createConnections(String[] connections) 
	{
		Node[] neighbours = new Node[connections.length - 1];
		for(int i = 0; i < neighbours.length; ++i) {
			Node node = nodes.get(connections[i+1]);
			neighbours[i] = node;
			if(node == null) {
				System.out.println(node + " not Exist");
			}
		}
		Node node = nodes.get(connections[0]);
		if(node == null) {
			System.out.println(node + " not Exist");
		}
		node.setupNeighbours(neighbours);
	}
	
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
	
	private static void buildGraph(String[] args) 
	{
		isElection = args[0].toUpperCase().equals("TRUE");
		
		if(!isElection) {
			System.out.println("No election.");
			createNodes(args[1].split(","), args[2].split(","), NodeType.SimpleNode);
		} else {
			System.out.println("Election enabled.");
			createNodes(args[1].split(","), args[2].split(","), NodeType.ElectionNode);
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("Usage: Must specify at least four arguments");
		}	
		
		/*Node nodeA;
		Node nodeB;
		Node nodeC;
		Node nodeD;
		Node nodeE;
		
		if(!isElection) {
			System.out.println("No election.");
			nodeA = NodeFactory.GetNode(NodeType.SimpleNode, "A", true, startLatch);
			nodeB = NodeFactory.GetNode(NodeType.SimpleNode, "B", true, startLatch);
			nodeC = NodeFactory.GetNode(NodeType.SimpleNode, "C", true, startLatch);
			nodeD = NodeFactory.GetNode(NodeType.SimpleNode, "D", true, startLatch);
			nodeE = NodeFactory.GetNode(NodeType.SimpleNode, "E", true, startLatch);	
		} else {
			System.out.println("Election enabled.");
			
			boolean initiator = true;
			Random rnd = new Random();
			
			nodeA = NodeFactory.GetNode(NodeType.ElectionNode, "A", initiator, startLatch);
			
			initiator = rnd.nextBoolean();
			nodeB = NodeFactory.GetNode(NodeType.ElectionNode, "B", initiator, startLatch);
			initiator = rnd.nextBoolean();
			nodeC = NodeFactory.GetNode(NodeType.ElectionNode, "C", initiator, startLatch);
			initiator = rnd.nextBoolean();
			nodeD = NodeFactory.GetNode(NodeType.ElectionNode, "D", initiator, startLatch);
			initiator = rnd.nextBoolean();
			nodeE = NodeFactory.GetNode(NodeType.ElectionNode, "E", initiator, startLatch);
		}
		
		nodes.add(nodeA);
		nodes.add(nodeB);
		nodes.add(nodeC);
		nodes.add(nodeD);
		nodes.add(nodeE);
		
		// Connect the nodes (neighbours ya know..)
		nodes.get(0).setupNeighbours(nodes.get(1), nodes.get(2));
		nodes.get(1).setupNeighbours(nodes.get(3), nodes.get(4));
		nodes.get(3).setupNeighbours(nodes.get(1), nodes.get(3), nodes.get(4));
		nodes.get(4).setupNeighbours(nodes.get(1), nodes.get(3));*/
		
		
	}

}
