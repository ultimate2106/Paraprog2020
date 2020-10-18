package implementation;

import java.util.concurrent.CountDownLatch;

import abstraction.Node;

/**
 * 
 * @author Benjamin Scheer, Dominic Schroeder, Dominic Jaeger
 *
 */
public class NodeFactory {
	private static int nextNodeId = 1;
	private static boolean isInitiatorDeclared = false;
	
	public static enum NodeType {
		SimpleNode,
		ElectionNode
	}
	
	/**
	 * Rückgabe eines bestimmten Node Objektes
	 * 
	 * @param type Überprüft, ob es sich um den Echo oder Election Algorithmus handelt
	 * @param name Name der Node
	 * @param isInitiator Abfrage ob die Node ein Initiator ist
	 * @param startLatch Zähler für die Nachbarn
	 * @return Node für den Echo oder Election Algorithmus. Bei falscher Eingabe wird null zurückgegeben.
	 */
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
