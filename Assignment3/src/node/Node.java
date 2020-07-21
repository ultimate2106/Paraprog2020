package node;

import abstraction.INode;
import abstraction.NodeAbstract;

public class Node extends NodeAbstract{

	private boolean isAwake = false;
	private int wakeupCounter = 0;
	private INode wokeupBy;
	private boolean sentWakeups = false;
	
	public Node(String name, boolean initiator) {
		super(name, initiator);
		if(initiator) isAwake = true;
	}

	@Override
	public void hello(INode neighbour) {
		this.neighbours.add(neighbour);
	}

	@Override
	public synchronized void wakeup(INode neighbour) {
		// TODO Auto-generated method stub
		if(!this.isAwake) {
			wokeupBy = neighbour;
			this.isAwake = true;
		}
		++this.wakeupCounter;
	}

	@Override
	public synchronized void echo(INode neighbour, Object data) {
		// TODO Auto-generated method stub
		++this.wakeupCounter;
	}

	@Override
	public void setupNeighbours(INode... neighbours) {
		// TODO Auto-generated method stub
		for(INode node : neighbours) {
			this.neighbours.add(node);
			node.hello(this);
		}
	}

	private void printTree()
	{
		
	}

	@Override
	public void run() {
		while(true) {
			if(this.isAwake) {
				if(wakeupCounter == neighbours.size()) {
					wokeupBy.echo(this, null);
					this.isAwake = false;
					if(initiator) {

					}
				} 
				else {
					if(!sentWakeups) {
						for(INode node : neighbours) {
							node.wakeup(this);
						}
						sentWakeups = true;
					}
				}
			}

			
		}
	}

}
