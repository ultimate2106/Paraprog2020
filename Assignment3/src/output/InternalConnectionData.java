package output;

import java.util.ArrayList;

public class InternalConnectionData {
	private ArrayList<String> data = new ArrayList<String>();
	
	public void AddConnection(String connection) {
		data.add(connection);
	}
	
	public void AddConnections(InternalConnectionData newData) {
		for(String dataItem : newData.GetData()) {
			data.add(dataItem);
		}
	}
	
	public ArrayList<String> GetData() {
		return data;
	}
	
	public void PrintTree() {
		for(String connection : data) {
			System.out.println(connection);
		}
	}
}
