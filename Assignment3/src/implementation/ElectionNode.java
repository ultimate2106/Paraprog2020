package implementation;

import java.util.concurrent.CountDownLatch;

import abstraction.INode;
import output.InternalConnectionData;

public class ElectionNode extends SimpleNode {
	private int currentMasterId = 0;
	
	public ElectionNode(String name, boolean initiator, CountDownLatch startLatch, int id) {
		super(name, initiator, startLatch);
		
		this.id = id;
		
		if(initiator) {
			currentMasterId = id;
		}
	}
	
	@Override
	public synchronized void wakeup(INode neighbour, int id) {		
		// When idle or when my master is weak.
		if(currentState.equals(NodeState.Idle)) {
			if(!initiator || (initiator && currentMasterId < id)) {
				currentMasterId = id;
				wokeupBy = neighbour;
				++messageCount;
			}
			
			currentState = NodeState.SendMessages;
		} else if(currentMasterId <= id) {			
			if(currentMasterId < id) {
				System.out.println(name + " got reset.");
				currentMasterId = id;
				wokeupBy = neighbour;
				
				//Reset stuff
				messageCount = 0;
				internalConnectionData.GetData().clear();
			}
			
			++messageCount;
			
			if(currentMasterId < id) {
				currentState = NodeState.SendMessages;
			}
		}
		
		System.out.println(name + " got wakeup from " + neighbour.toString() + ". CurrentId: " + currentMasterId);
	}
	
	@Override
	public synchronized void echo(INode neighbour, Object data, int currentMasterId) {
		if(this.currentMasterId == currentMasterId) {
			System.out.println(name + " got echo from " + neighbour.toString());
			
			++messageCount;
			
			internalConnectionData.AddConnection(name + "->" + neighbour.toString());
			internalConnectionData.AddConnections((InternalConnectionData)data);
		}
	}
	
	@Override
	protected void SendWakeups() {
		for(INode node : neighbours) {
			if(!node.equals(wokeupBy)) {
				node.wakeup(this, currentMasterId);
			}
		}
		
		currentState = NodeState.WaitAnswers;
	}
	
	@Override
	protected boolean ShallSendEcho() {
		if(initiator && currentMasterId == id)
			return false;
		
		return true;
	}
	
	@Override
	protected void SendEcho() {
		wokeupBy.echo(this, internalConnectionData, currentMasterId);
	}

	@Override
	protected void Finish() {
		if(initiator && currentMasterId == id) {
			internalConnectionData.PrintTree();
			System.out.println();
			System.out.println("Start next round!");
			currentState = NodeState.SendMessages;
		} else {
			System.out.println(name + " going idle.");
			currentState = NodeState.Idle;
		}
	}
}
