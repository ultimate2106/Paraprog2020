package output;

import java.util.ArrayList;

public class InternalConnectionData {
	private ArrayList<String> data = new ArrayList<String>();
	
	public void AddConnections(InternalConnectionData newData) {
		for(String dataItem : newData.GetData()) {
			data.add(dataItem);
		}
	}
	
	public ArrayList<String> GetData() {
		return data;
	}
}
