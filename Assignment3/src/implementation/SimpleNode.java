package implementation;
import abstraction.INode;
import abstraction.Node;
import output.InternalConnectionData;

public class SimpleNode extends Node {

	public SimpleNode(String name, boolean initiator) {
		super(name, initiator);
	}
	
	//Monitor methods (synch)
	// ----------------------------------------------------------------------------------
	@Override
	public synchronized void wakeup(INode neighbour) {
		++messageCount;
		
		if(currentState.equals(NodeState.Idle)) {
			wokeupBy = neighbour;
			currentState = NodeState.SendMessages;
		}
	}

	@Override
	public synchronized void echo(INode neighbour, Object data) {
		++messageCount;
		
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
		Shutdown();
	}
}
