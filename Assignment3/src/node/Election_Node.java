package node;

import java.util.concurrent.CountDownLatch;

import abstraction.Election_INode;
import abstraction.Election_NodeAbstract;

public class Election_Node extends Election_NodeAbstract{
	private boolean isAwake = false;
	private boolean threadIsRunning = true;
	private boolean sentRequest = false;
	
	private int wakeupCounter = 0;
	
	private Election_INode wokeupBy;
	private Election_INode identificationNode;
	private ElectionData electionData = null;
	
	
	private CountDownLatch rdyForEchoLatch;
	
	@Override
	public int ID() 
	{
		return id;
	}
	
	//Debug
//	private boolean showPrint = true;

	public Election_Node(String name,int id, boolean initiator, CountDownLatch rdyForEchoLatch) {
		super(name, id, initiator);
		this.rdyForEchoLatch = rdyForEchoLatch;
		
		if(initiator) {
			isAwake = true;
			identificationNode = this;
		}
	}

	@Override
	public void hello(Election_INode neighbour) {
		this.neighbours.add(neighbour);
	}

	@Override
	public synchronized void wakeup(Election_INode neighbour, Election_INode identification) {
		System.out.println(name + " got wakeup by " + neighbour);
		
		if(!isAwake) 
		{
			isAwake = true;
		}
		
		if(identificationNode == null ^ (identificationNode != null && identificationNode.ID() < identification.ID())) {
			identificationNode = identification;
			wokeupBy = neighbour;
			sentRequest = false;
			wakeupCounter = 1;
			electionData = null;
		} else if(identificationNode.ID() == identification.ID()) {
			++wakeupCounter;
		}	
	}

	@Override
	public synchronized void echo(Election_INode neighbour, Object data) {
		System.out.println(name + " got echo by " + neighbour);
		ElectionData tmp = (ElectionData)data;
		if(tmp.hasSameIdentification(identificationNode)) 
		{
			if(electionData == null) 
			{
				electionData = new ElectionData(identificationNode, tmp.getData());
			}
			else 
			{
				electionData.add(tmp.getData());
			}
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

	@Override
	public void run() {
		try {
			rdyForEchoLatch.await();
			
			while(threadIsRunning) {
				if(isAwake) {						
					if(wakeupCounter == neighbours.size()) {
						//wakeupCounter = 0;
						//sentWakeups = false;
						
						if(initiator) {
							printTree();						
						} else {
							isAwake = false;
							if(electionData == null) {
								wokeupBy.echo(this, new ElectionData(identificationNode,neighbours.size()-1));
							} else {
								electionData.add(neighbours.size());
								wokeupBy.echo(this, electionData);
							}
							
							
						}
						//threadIsRunning = false;
					} else {
						if(!sentRequest) {						
							for(Election_INode node : neighbours) {
								if(node != wokeupBy) {								
									if(identificationNode != null) {
										node.wakeup(this, identificationNode);
									} else {
										throw new IllegalStateException("Holland in Not");
									}
								}
							} 
							sentRequest = true;
						} else {
							sleep(1000);
						}
					} 
				} else {
					sleep(1000);
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
