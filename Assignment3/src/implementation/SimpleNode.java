package implementation;
import java.util.concurrent.CountDownLatch;

import abstraction.INode;
import abstraction.Node;
import output.InternalConnectionData;

public class SimpleNode extends Node {

	public SimpleNode(String name, boolean initiator, CountDownLatch startLatch) {
		super(name, initiator, startLatch);
	}
	
	//Monitor methods (synch)
	// ----------------------------------------------------------------------------------
	@Override
	public synchronized void wakeup(INode neighbour) {
		System.out.println(name + " got wakeup from " + neighbour.toString());
		
		++messageCount;
		
		if(currentState.equals(NodeState.Idle)) {
			wokeupBy = neighbour;
			currentState = NodeState.SendMessages;
		}
	}

	@Override
	public synchronized void echo(INode neighbour, Object data) {
		System.out.println(name + " got echo from " + neighbour.toString());
		
		++messageCount;
		
		internalConnectionData.AddConnection(name + "->" + neighbour.toString());
		internalConnectionData.AddConnections((InternalConnectionData)data);
	}

	@Override
	protected void SendWakeups() {
		for(INode node : neighbours) {
			if(!node.equals(wokeupBy)) {
				node.wakeup(this);
			}
		}
		
		currentState = NodeState.WaitAnswers;
	}
	
	@Override
	protected void SendEcho() {
		wokeupBy.echo(this, internalConnectionData);
	}
	
	
	@Override
	protected void Finish() {
		System.out.println(name + " is finished :)");
		
		if(initiator) {
			internalConnectionData.PrintTree();
		}
		
		Shutdown();
	}
}
