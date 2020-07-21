package node;

import abstraction.NodeAbstract;

public class Node extends NodeAbstract implements abstraction.Node {

	public Node(String name, boolean initiator) {
		super(name, initiator);
	}

	@Override
	public void hello(abstraction.Node neighbour) {
		// TODO Auto-generated method stub

	}

	@Override
	public void wakeup(abstraction.Node neighbour) {
		// TODO Auto-generated method stub

	}

	@Override
	public void echo(abstraction.Node neighbour, Object data) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setupNeighbours(abstraction.Node... neighbours) {
		// TODO Auto-generated method stub

	}

}
