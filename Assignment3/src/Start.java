import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import node.Node;

public class Start {
	private static CountDownLatch rdyForEchoLetch = new CountDownLatch(1);
	
	public static void main(String[] args) {
		Node[] nodes = new Node[5];
		nodes[0] = new Node("node0", true, rdyForEchoLetch);
		
		for(int i = 1; i < nodes.length; ++i) {
			nodes[i] = new Node("node"+i, false, rdyForEchoLetch);
		}
		
		nodes[0].setupNeighbours(nodes[1], nodes[2]);
		nodes[1].setupNeighbours(nodes[3], nodes[4]);
		
		ExecutorService exec = Executors.newFixedThreadPool(5);
		
		for(int i = 0; i < nodes.length; ++i) {
			exec.submit(nodes[i]);
		}
		
		rdyForEchoLetch.countDown();
	}
}