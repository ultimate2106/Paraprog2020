package main;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import abstraction.Node;
import implementation.NodeFactory;
import implementation.NodeFactory.NodeType;

public class Start {
	private static CountDownLatch startLatch = new CountDownLatch(1);
	private static ArrayList<Node> nodes = new ArrayList<Node>();
	private static boolean isElection = true;
	
	public static void main(String[] args) {
		// Create nodes and add them to list
		Node nodeA;
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
		nodes.get(4).setupNeighbours(nodes.get(1), nodes.get(3));
		
		ExecutorService execService = Executors.newFixedThreadPool(nodes.size());
		for(Node node : nodes) {
			execService.submit(node);
		}
		
		startLatch.countDown();
	}

}
