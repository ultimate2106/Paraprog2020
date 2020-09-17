package abstraction;

import java.util.concurrent.CountDownLatch;

import output.InternalConnectionData;

public abstract class Node extends NodeAbstract {
	public enum NodeState {
		Idle,
		SendMessages,
		WaitAnswers
	}
	
	private CountDownLatch startLatch;
	
	protected int id = 0;
	protected boolean isRunning = true;
	
	// Set in wakeup
	protected NodeState currentState = NodeState.Idle;
	protected INode wokeupBy = null;

	// Stuff to reset in election
	protected InternalConnectionData internalConnectionData = new InternalConnectionData();	
	protected int messageCount = 0;
	
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
					if(ShallSendEcho()) {
						SendEcho();
					}
					Finish();
				}
				break;				
			}
		
			Thread.yield();
		}
	}
	
	public void Shutdown() {
		isRunning = false;
	}
		
	protected abstract void SendWakeups();
	protected abstract boolean ShallSendEcho();
	protected abstract void SendEcho();
	protected abstract void Finish();
	// ----------------------------------------------------------------------------------
}
