package implementation;

import java.util.concurrent.CountDownLatch;

import abstraction.INode;
import output.InternalConnectionData;

/**
 * 
 * @author Benjamin Scheer, Dominic Schroeder, Dominic Jaeger
 *
 */
public class ElectionNode extends SimpleNode {
	/*
	 * Aktuelle ID
	 */
	private int currentMasterId = 0;
	/*
	 * Ob der Algorithmus abgeschlossen ist
	 */
	private boolean isShuttingDown = false;
	/*
	 * Ob diese Node zurückgesetzt werden muss
	 */
	private boolean reset = false;
	/*
	 * Initiator dieser Node
	 */
	private INode owner = null;
	/*
	 * Letzte Node zum aufwecken, falls man beim aufwecken von einer Node unterbrochen wurde.
	 */
	private INode lastNode = null;
	
	/**
	 * 
	 * @param name Name der Node
	 * @param initiator Abfrage ob die Node ein Initiator ist
	 * @param startLatch Zähler für die Nachbarn
	 * @param id ID der Node
	 */
	public ElectionNode(String name, boolean initiator, CountDownLatch startLatch, int id) {
		super(name, initiator, startLatch);
		
		this.id = id;
		
		if(initiator) {
			currentMasterId = id;
		}
	}
	
	/**
	 * 
	 * @return Initiator der Node
	 */
	public INode getOwner() 
	{
		return owner;
	}
	
	/* 
	 * Wird von anderen Nodes aufgerufen, um diese Node aufzuwecken
	 * 
	 * @param neighbour Node die diese Node aufweckt 
	 * @param id ID der Node die diese aufweckt
	 */
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
	
	/**
	 * Setzt bestimmte Eigenschaften der Node zurück.
	 * 
	 * @param isFinished Abfrage ob der Election Algorithmus abgeschlossen ist
	 */
	private synchronized void Reset(boolean isFinished) {
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
	
	/**
	 * Wird von anderen Nodes aufgerufen, um dieser Node eine Bestätigung zu schicken
	 * 
	 * @param neighbour Node von der ein Echo kommt
	 * @param data Spannbaum
	 * @param currentMasterId ID der Nachbar Node
	 */
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
	
	
	/**
	 * Startet das Aufwecken der Nachbarn
	 */
	@Override
	protected synchronized void SendWakeups() {
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
	
	/**
	 * @return Rückgabe ob die Node Initiator ist
	 */
	@Override
	protected synchronized boolean ShallSendEcho() {
		if(initiator && currentMasterId == id)
			return false;
		return true;
	}
	
	/**
	 * Startet das Senden von Echos zu den Nachbarn
	 */
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

	/**
	 * Stopt das Electionverfahren und startet den Echo Algorithmus
	 */
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
	
	/**
	 * Wird beim abschluss des Election Algorithmus ausgeführt.
	 */
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
				SendEcho();
				break;
				
			}
		
			try {
				sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
