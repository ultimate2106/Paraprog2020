package node;

import abstraction.Election_INode;

public class ElectionData {
	private Election_INode identification = null;
	private int numberOfIdentities = 0;
	
	public ElectionData(Election_INode identification, int numberOfIdentities) {
		this.identification = identification;
		if(numberOfIdentities > 0) {
			this.numberOfIdentities = numberOfIdentities;
		} else {
			throw new IllegalStateException("Holland in Not");
		}
		
	}
	
	public boolean hasSameIdentification(Election_INode identification)
	{
		return this.identification == identification;
	}
	
	public int getData() 
	{
		return numberOfIdentities;
	}
	
	public void add(int arg) 
	{ 
		if(arg > 0) {
			numberOfIdentities += arg;
		} else {
			throw new IllegalStateException("Holland in Not");
		}
	}
}
