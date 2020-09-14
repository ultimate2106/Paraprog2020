package implementation;

import abstraction.Node;

public class NodeFactory {
	private static int nextNodeId = 1;
	private static boolean isInitiatorDeclared = false;
	
	public static enum NodeType {
		SimpleNode,
		ElectionNode
	}
	
	public static Node GetNode(NodeType type, String name, boolean isInitiator) {
		switch(type) {
		case SimpleNode:
			if(!isInitiatorDeclared) {
				isInitiatorDeclared = isInitiator;
				return new SimpleNode(name, isInitiator);
			}
			
			return new SimpleNode(name, false);
		case ElectionNode:
		default:
			return null;
		}
	}
}
