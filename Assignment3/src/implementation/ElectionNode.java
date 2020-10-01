package implementation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import abstraction.INode;
import abstraction.Node.NodeState;
import output.InternalConnectionData;

public class ElectionNode extends SimpleNode {
	private int currentMasterId = 0;
	private boolean isShuttingDown = false;
	private List<INode> additionelWokeupsBy = Collections.synchronizedList(new ArrayList<INode>());
	private INode owner = null;
	
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
		/*while(owner != null) {
			try {
				wait(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
		//owner = this;
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
			} /*else if(currentMasterId == id) {
				//System.out.println(name + "Two Wakeups"); 
				additionelWokeupsBy.add(neighbour);
			}*/
			
			++messageCount;
		}
		
		System.out.println(name + " got wakeup from " + neighbour.toString() +
												(!isShuttingDown ? ". CurrentId: " + currentMasterId : ""));
		//owner = null;
		//notifyAll();
	}
	
	private synchronized void Reset(boolean isFinished) {
		//System.out.println(this + " Reset: messageCount is " + messageCount);
		messageCount = 0;
		internalConnectionData.GetData().clear();
		additionelWokeupsBy = Collections.synchronizedList(new ArrayList<INode>());
		if(isFinished) {
			isShuttingDown = true;
			wokeupBy = null;
		}
	}
	
	@Override
	public synchronized void echo(INode neighbour, Object data, int currentMasterId) {
		/*while(owner != null) {
			try {
				wait(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		owner = this;*/
		if(this.currentMasterId == currentMasterId) {
			System.out.println(name + " got echo from " + neighbour.toString());
			
			++messageCount;
			
			if(data != null) {
				internalConnectionData.AddConnection(name + "->" + neighbour.toString());
				internalConnectionData.AddConnections((InternalConnectionData)data);
			}
		}
		//owner = null;
		//notifyAll();
	}
	
	@Override
	protected void SendWakeups() {
		/*while(owner != null) {
			try {
				wait(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		owner = this;*/
		INode localWokeupBy = wokeupBy;
		List<INode> localAdditionelWokeups = additionelWokeupsBy;
		int localId = currentMasterId;
		
		for(INode node : neighbours) {
			if(!node.equals(localWokeupBy) && !localAdditionelWokeups.contains(node)) {
				System.out.println(name + " send wakeup to " + node);
				node.wakeup(this, localId);
			}
		}
	
		if(localId == currentMasterId){
			currentState = NodeState.WaitAnswers;
		}
		//owner = null;
		//notifyAll();
	}
	
	@Override
	protected synchronized boolean ShallSendEcho() {
		/*while(owner != null) {
			try {
				wait(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		owner = this;*/
		if(initiator && currentMasterId == id)
			return false;
		
		//owner = null;
		//notifyAll();
		return true;
	}
	
	@Override
	protected void SendEcho() {
		/*while(owner != null) {
			try {
				wait(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
		//owner = this;
		int localId = currentMasterId;
		InternalConnectionData localdata = internalConnectionData;
		wokeupBy.echo(this, localdata, localId);
		/*for(INode node : additionelWokeupsBy) 
		{
			node.echo(this, null, currentMasterId);
		}*/
		//owner = null;
		//notifyAll();
	}

	@Override
	protected synchronized void Finish() {
		/*while(owner != null) {
			try {
				wait(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		owner = this;*/
		
		if(!isShuttingDown) {
			if(initiator && currentMasterId == id) {
				internalConnectionData.PrintTree();
				System.out.println();
				System.out.println("Start Echo!");
				currentState = NodeState.SendMessages;
			} else {	
				System.out.println(name + " going idle.");
				currentState = NodeState.Idle;
			}
			
			Reset(true);
		} else {
			Shutdown();
		}
		//owner = null;
		//notifyAll();
	}
}
