package implementation;

import java.util.concurrent.CountDownLatch;

import abstraction.Node;

public class NodeFactory {
	private static int nextNodeId = 1;
	private static boolean isInitiatorDeclared = false;
	
	public static enum NodeType {
		SimpleNode,
		ElectionNode
	}
	
	public static Node GetNode(NodeType type, String name, boolean isInitiator, CountDownLatch startLatch) {
		switch(type) {
		case SimpleNode:
			if(!isInitiatorDeclared) {
				isInitiatorDeclared = isInitiator;
				return new SimpleNode(name, isInitiator, startLatch);
			}
			
			return new SimpleNode(name, false, startLatch);
		case ElectionNode:
			return new ElectionNode(name, isInitiator, startLatch, nextNodeId++);
		default:
			return null;
		}
	}
}
