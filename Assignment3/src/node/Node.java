package node;

import java.util.concurrent.CountDownLatch;

import abstraction.INode;
import abstraction.NodeAbstract;

public class Node extends NodeAbstract{

	private boolean isAwake = false;
	private int wakeupCounter = 0;
	private INode wokeupBy;
	private boolean sentWakeups = false;
	
	private CountDownLatch rdyForEchoLatch;
	
	//Debug
	private boolean showPrint = true;
	
	public Node(String name, boolean initiator, CountDownLatch rdyLatch) {
		super(name, initiator);
		rdyForEchoLatch = rdyLatch;
		if(initiator) {
			isAwake = true;
		}
	}

	@Override
	public void hello(INode neighbour) {
		this.neighbours.add(neighbour);
	}

	@Override
	public synchronized void wakeup(INode neighbour) {
		System.out.println(name + " got wakeup by " + neighbour);
		// TODO Auto-generated method stub
		if(!this.isAwake) {
			wokeupBy = neighbour;
			this.isAwake = true;
		}
		
		showPrint = true;
		
		++this.wakeupCounter;
	}

	@Override
	public synchronized void echo(INode neighbour, Object data) {
		System.out.println(name + " got echo by " + neighbour);
		
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

	private void printTree() {	
		System.out.println("Done!");
		System.out.println();
	}

	@Override
	public void run() {
		try {
			rdyForEchoLatch.await();
			
			while(true) {
				if(this.isAwake) {							
					if(wakeupCounter == neighbours.size()) {
						wakeupCounter = 0;
						sentWakeups = false;
						if(initiator) {
							printTree();
							Thread.sleep(1000);
						} else {
							this.isAwake = false;
							wokeupBy.echo(this, null);
						}
					} else {
						if(!sentWakeups) {
							for(INode node : neighbours) {
								if(node != wokeupBy) {
									node.wakeup(this);
								}
							}
							sentWakeups = true;
						}
					}
				}
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
