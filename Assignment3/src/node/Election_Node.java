package node;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import abstraction.Election_INode;
import abstraction.Election_NodeAbstract;

public class Election_Node extends Election_NodeAbstract{
	private boolean isAwake = false;
	private boolean threadIsRunning = true;
	private boolean sentRequest = false;
	
	private int wakeupCounter = 0;
	private int id = -1;
	private int numberOfIdentities = 0;
	
	private Set<Election_INode> wokeupByThisNodes = new HashSet<Election_INode>();
	
	
	private CountDownLatch rdyForEchoLatch;
	
	//Debug
//	private boolean showPrint = true;
	
	@Override
	public String toString() {
		if(initiator || id >= 0) {
			return name + "(Wave " + id + ")";
		}
		else {
			return super.toString();
		}
	}

	public Election_Node(String name, boolean initiator, CountDownLatch rdyForEchoLatch) {
		super(name, initiator);
		this.rdyForEchoLatch = rdyForEchoLatch;
		
		if(initiator) {
			isAwake = true;
			this.id = (int) getId();
		}
	}

	@Override
	public void hello(Election_INode neighbour) {
		this.neighbours.add(neighbour);
	}

	@Override
	public synchronized void wakeup(Election_INode neighbour, int id) {
		System.out.println(name + " got request from " + neighbour);
		
		if(!isAwake) 
		{
			isAwake = true;
		}
		
		if(this.id == -1 || this.id < id) {
			System.out.println("Wave " + id + " absorb Node "+ name);
			this.id = id;
			if(wokeupByThisNodes.size() > 0) {
				wokeupByThisNodes.removeAll(wokeupByThisNodes);
			}
			wokeupByThisNodes.add(neighbour);
			sentRequest = false;
			wakeupCounter = 1;
			//initiator = false;
		} else if(this.id == id) {
			++wakeupCounter;
			wokeupByThisNodes.add(neighbour);
		}	
	}

	@Override
	public synchronized void echo(Election_INode neighbour, Object data, int id, int identities) {
		
		if(this.id == id) 
		{
			System.out.println(name + " got the number of all known identities (" + identities + ") from " + neighbour);
			numberOfIdentities += identities + 1;
			++this.wakeupCounter;
		}
	}
	
	@Override
	public synchronized void echo(Election_INode neighbour, Object data, int id) 
	{
		if(this.id == id) 
		{
			System.out.println(name + ": " + neighbour + " is already known");
			++this.wakeupCounter;
		}
	}

	@Override
	public void setupNeighbours(Election_INode... neighbours) {
		// TODO Auto-generated method stub
		for(Election_INode node : neighbours) {
			this.neighbours.add(node);
			//System.out.println(node + " is a neighbour of " + this);
			node.hello(this);
		}
	}

	private void printTree() {	
		System.out.println("Done!");
		System.out.println();
	}
	
	private void printLeader() 
	{
		System.out.println(name + " is Leader with " + numberOfIdentities + " identities");
	}

	@Override
	public void run() {
		try {
			rdyForEchoLatch.await();
			
			while(threadIsRunning) {
				if(isAwake) {						
					if(wakeupCounter == neighbours.size()) {
						//wakeupCounter = 0;
						//sentWakeups = false;
						
						if(initiator && id == (int) getId()) {
							printLeader();
							printTree();	
						} else {
							
							isAwake = false;
							Object[] nodes = wokeupByThisNodes.toArray();
							
							
							for(int i = 0; i < nodes.length; ++i) {
								if(i == 0) {
									//((Election_Node)nodes[i]).echo(this, null, id, numberOfIdentities + (neighbours.size() - wokeupByThisNodes.size()));
									((Election_Node)nodes[i]).echo(this, null, id, numberOfIdentities);
								} else {
									((Election_Node)nodes[i]).echo(this, null, id);
								}
							}
							
							
						}
						threadIsRunning = false;
					} else {
						if(!sentRequest) {						
							for(Election_INode node : neighbours) {
								if(!wokeupByThisNodes.contains(node)) {								
									node.wakeup(this, id);
								}
							} 
							sentRequest = true;
						} else {
							sleep(1);
						}
					} 
				} else {
					sleep(1);
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
