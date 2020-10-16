package implementation;

import java.util.concurrent.CountDownLatch;

import abstraction.INode;
import output.InternalConnectionData;

public class ElectionNode extends SimpleNode {
	private int currentMasterId = 0;
	private boolean isShuttingDown = false;
	private boolean reset = false;
	private INode owner = null;
	private INode lastNode = null;
	
	public ElectionNode(String name, boolean initiator, CountDownLatch startLatch, int id) {
		super(name, initiator, startLatch);
		
		this.id = id;
		
		if(initiator) {
			currentMasterId = id;
		}
	}
	
	public INode getOwner() 
	{
		return owner;
	}
	
	@Override
	public synchronized void wakeup(INode neighbour, int id) {
		owner = this;
		// When idle or when my master is weak.
		if(currentState.equals(NodeState.Idle)) {
		//Equals should only apply in second run. If not working, remove equals and reset ID instead at end of election.
			if(!initiator || (initiator && currentMasterId <= id)) {
				currentMasterId = id;
				wokeupBy = neighbour;
				++messageCount;
			}
			
			currentState = NodeState.SendMessages;
		} else if(currentMasterId <= id) {	
			if(currentMasterId < id) {
				System.out.println(name + " got reset from "+ neighbour +".");
				currentMasterId = id;
				wokeupBy = neighbour;
				
				//Reset stuff
				Reset(false);
				currentState = NodeState.SendMessages;
			}
			
			++messageCount;
		}
		
		System.out.println(name + " got wakeup from " + neighbour.toString() +
												(!isShuttingDown ? ". CurrentId: " + currentMasterId : ""));
		owner = null;
		notifyAll();
	}
	
	private synchronized void Reset(boolean isFinished) {
		//System.out.println(this + " Reset: messageCount is " + messageCount);
		messageCount = 0;
		internalConnectionData.GetData().clear();
		lastNode = null;
		if(isFinished) {
			isShuttingDown = true;
			wokeupBy = null;
			reset = false;
		} 
		else 
		{
			reset = true;
		}
		
	}
	
	@Override
	public synchronized void echo(INode neighbour, Object data, int currentMasterId) {
		owner = this;
		if(this.currentMasterId == currentMasterId) {
			System.out.println(name + " got echo from " + neighbour.toString());
				
			++messageCount;
				
			internalConnectionData.AddConnection(name + "->" + neighbour.toString());
			internalConnectionData.AddConnections((InternalConnectionData)data);
		}
		owner = null;
		notifyAll();
	}
	
	
	@Override
	protected synchronized void SendWakeups() {
		//System.out.println(this + ".SendWakeups()");
		reset = false;
		boolean timeout = false;
		owner = this;
		for(INode node : neighbours) {
			if(!node.equals(wokeupBy) && (node == lastNode || lastNode == null)) {
				
				if(lastNode != null) {
					lastNode = null;
				}
				while(!(((ElectionNode)node).getOwner() == null || ((ElectionNode)node).getOwner() == this)) {
					try {
						if(timeout || reset) {	
							lastNode = !reset ? node : null;
							reset = false;
							owner = null;
							notifyAll();
							return;
						}
						wait(1);	
						timeout = true;
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				if(!reset) {
				    node.wakeup(this, currentMasterId);
				} else {
					reset = false;
					lastNode = null;
					owner = null;
					notifyAll();
					return;
				}
				
			} 
		}
		currentState = NodeState.WaitAnswers;
		owner = null;
		notifyAll();
	}
	
	@Override
	protected synchronized boolean ShallSendEcho() {
		if(initiator && currentMasterId == id)
			return false;
		return true;
	}
	
	@Override
	protected synchronized void SendEcho() {
		boolean reset = false;
		boolean timeout = false;
		owner = this;
		
		while(!((messageCount >= neighbours.size() 
				&& currentState == NodeState.WaitAnswers) 
			&& (!ShallSendEcho()
			    || (((ElectionNode)wokeupBy).getOwner() == null 
						|| ((ElectionNode)wokeupBy).getOwner() == this))		
		)) {
			try {
				if(timeout || reset) 
				{
					reset = false;
					owner = null;
					notifyAll();
					return;
				}
				wait(1);
				timeout = true;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(!reset) {
			if(ShallSendEcho()) {
				wokeupBy.echo(this, internalConnectionData, currentMasterId);
			} 
			Finish();	
		} else {
			reset = false;
		}	
		owner = null;
		notifyAll();
	}

	@Override
	protected synchronized void Finish() {
		if(!isShuttingDown) {
			if(initiator && currentMasterId == id) {
				internalConnectionData.PrintTree();
				finishElection();
				System.out.println();
				System.out.println("Start Echo!");
				currentState = NodeState.SendMessages;
			} else {	
				System.out.println(name + " going idle.");
				currentState = NodeState.Idle;
			}
			
		} else {
			Shutdown();
		}
	}
	
	public synchronized void finishElection() 
	{   if(!isShuttingDown) {
			Reset(true);
			for(INode node : neighbours) 
			{
				((ElectionNode)node).finishElection();
			}
			System.out.println("For " + name + " is Election over");
		}		
	}
	
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
				//System.out.println(this + ".SendEcho()");
				SendEcho();
				break;
				
			}
		
			//Thread.yield();
			try {
				sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
