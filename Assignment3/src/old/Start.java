package old;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class Start {
	private static CountDownLatch rdyForEchoLetch = new CountDownLatch(1);
	
	public static void main(String[] args) {
		if(args.length != 0) {
			HashMap<String,Node> nodeMap = new HashMap<String,Node>(args.length);
			String[] keys = new String[args.length];
			String[][] neighboursKeys = new String[args.length][];
			//nodes[0] = new Node("node0", true, rdyForEchoLetch);
		
			for(int i = 0; i < args.length; ++i) {
				
				String[] nodeAndNeighbours = args[i].split(",");
				keys[i] = nodeAndNeighbours[0];
				
				boolean isInit = false;
				
				if(i == 0) {
					isInit = true;
				}
				
				nodeMap.put(keys[i], new Node(keys[i], isInit, rdyForEchoLetch));
				
				neighboursKeys[i] = new String[nodeAndNeighbours.length-1];
				
				for(int j = 0; j < nodeAndNeighbours.length-1; ++j) {
					neighboursKeys[i][j] = nodeAndNeighbours[j+1];
				}
			}
			
			for(int i = 0; i < neighboursKeys.length; ++i) 
			{
				Node[] neighbours = new Node[neighboursKeys[i].length];
				for(int j = 0; j < neighboursKeys[i].length; ++j) 
				{
					neighbours[j] = nodeMap.get(neighboursKeys[i][j]);
				}
				nodeMap.get(keys[i]).setupNeighbours(neighbours);
			}
		
			//nodes[0].setupNeighbours(nodes[1], nodes[2]);
			//nodes[1].setupNeighbours(nodes[3], nodes[4]);
			try {
			Thread.sleep(3000);
		
			ExecutorService exec = Executors.newFixedThreadPool(10);
			//ExecutorService exec = Executors.newCachedThreadPool();
			for(int i = 0; i < keys.length; ++i) {
				exec.submit(nodeMap.get(keys[i]));
			}		
			rdyForEchoLetch.countDown();
			
			exec.shutdown();
			
				while (!exec.awaitTermination(5, TimeUnit.SECONDS)) {
					
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			System.out.println("Usage: Must specify at least one argument");
		}
	}
}