package implementation;
import java.util.concurrent.CountDownLatch;

import abstraction.INode;
import abstraction.Node;
import output.InternalConnectionData;

/**
 * 
 * @author Benjamin Scheer, Dominic Schroeder, Dominic Jaeger
 *
 */
public class SimpleNode extends Node {

	/**
	 * 
	 * @param name Name der Node
	 * @param initiator Abfrage ob die Node ein Initiator ist
	 * @param startLatch Zähler für die Nachbarn
	 */
	public SimpleNode(String name, boolean initiator, CountDownLatch startLatch) {
		super(name, initiator, startLatch);
	}
	
	//Monitor methods (synch)
	// ----------------------------------------------------------------------------------
	/**
	 * Wird von anderen Nodes aufgerufen, um diese Node aufzuwecken
	 * 
	 * @param neighbour Node die diese aufweckt
	 * @param id ID der Nachbar Node
	 */
	@Override
	public synchronized void wakeup(INode neighbour, int id) {
		System.out.println(name + " got wakeup from " + neighbour.toString());
		
		++messageCount;
		
		if(currentState.equals(NodeState.Idle)) {
			wokeupBy = neighbour;
			currentState = NodeState.SendMessages;
		}
	}
	
	/**
	 * Wird von anderen Nodes aufgerufen, um dieser Node eine Bestätigung zu schicken
	 * 
	 * @param neighbour Node von der ein Echo kommt
	 * @param data Spannbaum
	 * @param id ID der Nachbar Node
	 */
	@Override
	public synchronized void echo(INode neighbour, Object data, int id) {
		System.out.println(name + " got echo from " + neighbour.toString());
		
		++messageCount;
		
		internalConnectionData.AddConnection(name + "->" + neighbour.toString());
		internalConnectionData.AddConnections((InternalConnectionData)data);
	}

	
	/**
	 * Startet das Aufwecken der Nachbarn
	 */
	@Override
	protected void SendWakeups() {
		for(INode node : neighbours) {
			if(!node.equals(wokeupBy)) {
				node.wakeup(this, id);
			}
		}
		
		currentState = NodeState.WaitAnswers;
	}
	
	/**
	 * @return Rückgabe ob die Node Initiator ist
	 */
	@Override
	protected boolean ShallSendEcho() {
		if(initiator)
			return false;
		
		return true;
	}
	
	/**
	 * Startet das Senden von Echos zu den Nachbarn
	 */
	@Override
	protected void SendEcho() {
		System.out.println(name + " sending echo to " + wokeupBy);
		wokeupBy.echo(this, internalConnectionData, id);
	}
	
	/**
	 * Stopt den Echo Algorithmus und lässt den Graphen auf die Konsole schreiben
	 */
	@Override
	protected void Finish() {
		try {
			sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if(initiator) {
			internalConnectionData.PrintTree();
		}
		
		Shutdown();
	}
}
