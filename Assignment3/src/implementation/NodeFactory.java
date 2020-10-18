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
	 * R�ckgabe eines bestimmten Node Objektes
	 * 
	 * @param type �berpr�ft, ob es sich um den Echo oder Election Algorithmus handelt
	 * @param name Name der Node
	 * @param isInitiator Abfrage ob die Node ein Initiator ist
	 * @param startLatch Z�hler f�r die Nachbarn
	 * @return Node f�r den Echo oder Election Algorithmus. Bei falscher Eingabe wird null zur�ckgegeben.
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
