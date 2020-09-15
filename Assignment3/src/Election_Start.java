import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import node.Election_Node;


public class Election_Start {
	private static CountDownLatch rdyForEchoLatch = new CountDownLatch(1);

	public static void main(String[] args) {
		/*if(args.length != 0) {
			HashMap<String,Election_Node> nodeMap = new HashMap<String,Election_Node>(args.length);
			String[] keys = new String[args.length];
			String[][] neighboursKeys = new String[args.length][];
		
			for(int i = 0; i < args.length; ++i) {
				
				String[] nodeAndNeighbours = args[i].split(",");
				keys[i] = nodeAndNeighbours[0];
				
				boolean isInit = false;
				
				int id = Integer.parseInt(nodeAndNeighbours[1]);
				if(id >= 0) {
					isInit = true;
				}				

				nodeMap.put(keys[i], new Election_Node(keys[i], id, isInit, rdyForEchoLetch));
				
				
				
				neighboursKeys[i] = new String[nodeAndNeighbours.length-2];
				
				for(int j = 0; j < nodeAndNeighbours.length-2; ++j) {
					neighboursKeys[i][j] = nodeAndNeighbours[j+2];
				}
			}
			
			for(int i = 0; i < neighboursKeys.length; ++i) 
			{
				Election_Node[] neighbours = new Election_Node[neighboursKeys[i].length];
				for(int j = 0; j < neighboursKeys[i].length; ++j) 
				{
					neighbours[j] = nodeMap.get(neighboursKeys[i][j]);
				}
				nodeMap.get(keys[i]).setupNeighbours(neighbours);
			}
			
			ExecutorService exec = Executors.newFixedThreadPool(10);
			for(int i = 0; i < keys.length; ++i) {
				exec.submit(nodeMap.get(keys[i]));
			}		
			rdyForEchoLetch.countDown();
			
		}else {
			System.out.println("Usage: Must specify at least one argument");
		}
	*/
		
	Election_Node a = new Election_Node("a", true, rdyForEchoLatch);
	Election_Node b = new Election_Node("b", false, rdyForEchoLatch);
	Election_Node c = new Election_Node("c",false,rdyForEchoLatch);
	Election_Node d = new Election_Node("d",true,rdyForEchoLatch);
	
	a.setupNeighbours(b,c);
	b.setupNeighbours(a,d);
	c.setupNeighbours(a,d);
	d.setupNeighbours(b,c);
	
	ExecutorService exec = Executors.newFixedThreadPool(10);
	exec.submit(a);
	exec.submit(b);
	exec.submit(c);
	exec.submit(d);
	rdyForEchoLatch.countDown();
	exec.shutdown();
	}
}
	
