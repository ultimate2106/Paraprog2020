package old;

/**
 * This interface defines the methods of each node participating in the echo
 * algorithm. These methods can be called by the neighbours of a node.
 */
public interface Election_INode {

	/**
	 * Greetings from a neighbour. Before starting the echo algorithm this message
	 * is called within <code>setupNeighbours</code> once by every node for all its
	 * initially known neighbours.
	 * 
	 * The main purpose is that <code>this</code> node gets the chance to add the
	 * calling <code>neighbour</code> to its own list of neighbours (just in case
	 * the neighbour was previously unknown to this node although the neighbour
	 * itself knows this node).
	 * 
	 * @param neighbour
	 */
	public void hello(Election_INode neighbour);

	/**
	 * Incoming "wakeup" message from a neighbour.
	 * 
	 * @param neighbour
	 * @param identification
	 */
	public void wakeup(Election_INode neighbour, int id);

	/**
	 * Incoming "echo" message from a neighbour. The neighbour can also send some
	 * data with this echo message. The data object might e.g. contain information
	 * about edges of the spanning tree known so far; if not needed, set data to
	 * null.
	 * 
	 * @param neighbour
	 * @param data
	 */
	public void echo(Election_INode neighbour, Object data, int id, int identities);
	public void echo(Election_INode neighbour, Object data, int id);
}
