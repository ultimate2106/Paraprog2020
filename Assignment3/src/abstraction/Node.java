package abstraction;

import java.util.concurrent.CountDownLatch;

import output.InternalConnectionData;

public abstract class Node extends NodeAbstract {
	public enum NodeState {
		Idle,
		SendMessages,
		WaitAnswers
	}
	
	protected InternalConnectionData internalConnectionData = new InternalConnectionData();
	
	protected NodeState currentState = NodeState.Idle;
	
	protected boolean isRunning = true;
	
	protected INode wokeupBy = null;
	
	protected boolean isAwake = false;
	
	protected int messageCount = 0;
	
	private CountDownLatch startLatch;
	
	// -------------------------------------------------------------------------------------------------------------
	
	
	public Node(String name, boolean initiator, CountDownLatch startLatch) {
		super(name, initiator);
		this.startLatch = startLatch;
	}

	// Setup methods
	// ----------------------------------------------------------------------------------
	@Override
	public void hello(INode neighbour) {
		neighbours.add(neighbour);
	}

	@Override
	public void setupNeighbours(INode... neighbours) {
		for(INode node : neighbours) {
			this.neighbours.add(node);
			node.hello(this);
		}
	}
	// ----------------------------------------------------------------------------------
		
	
	// Thread methods
	// ----------------------------------------------------------------------------------
	@Override
	public void run() {
		if(initiator) {
			System.out.println(name + " is initiator");
			currentState = NodeState.SendMessages;
		}
		
		try {
			startLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		while(isRunning) {
			switch(currentState) {
			case Idle:
				// Node is not awake (sleeping). So do nothing :)
				break;
			case SendMessages:
				SendWakeups();
				break;
			case WaitAnswers:
				if(messageCount >= neighbours.size()) {
					if(!initiator) {
						SendEcho();
					}
					Finish();
				}
				break;
			}
		}
	}
	
	public void Shutdown() {
		isRunning = false;
	}
	
	protected abstract void SendWakeups();
	protected abstract void SendEcho();
	protected abstract void Finish();
	// ----------------------------------------------------------------------------------
}
