package main;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import abstraction.Node;
import implementation.NodeFactory;
import implementation.NodeFactory.NodeType;

public class Start {
	private static CountDownLatch startLatch = new CountDownLatch(1);
	private static ArrayList<Node> nodes = new ArrayList<Node>();
	
	public static void main(String[] args) {
		// Create nodes and add them to list
		Node nodeA = NodeFactory.GetNode(NodeType.SimpleNode, "A", true, startLatch);
		nodes.add(nodeA);
		
		Node nodeB = NodeFactory.GetNode(NodeType.SimpleNode, "B", true, startLatch);
		nodes.add(nodeB);
		
		Node nodeC = NodeFactory.GetNode(NodeType.SimpleNode, "C", true, startLatch);
		nodes.add(nodeC);
		
		Node nodeD = NodeFactory.GetNode(NodeType.SimpleNode, "D", true, startLatch);
		nodes.add(nodeD);
		
		Node nodeE = NodeFactory.GetNode(NodeType.SimpleNode, "E", true, startLatch);
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
