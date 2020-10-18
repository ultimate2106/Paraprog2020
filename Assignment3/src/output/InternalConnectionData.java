package output;

import java.util.ArrayList;

/**
 * Aufbau des Spannbaumes
 * 
 * @author Benjamin Scheer, Dominic Schroeder, Dominic Jaeger
 *
 */
public class InternalConnectionData {
	private ArrayList<String> data = new ArrayList<String>();
	
	/**
	 * 
	 * @param connection Übergabe einer Node Verbindung
	 */
	public synchronized void AddConnection(String connection) {
		data.add(connection);
	}
	
	/**
	 * 
	 * @param newData Übergabe einer Liste von Verbindungen unter den Nodes
	 */
	public synchronized void AddConnections(InternalConnectionData newData) {
		for(String dataItem : newData.GetData()) {
			data.add(dataItem);
		}
	}
	
	/**
	 * 
	 * @return Rückgabe der Liste der Verbindungen
	 */
	public ArrayList<String> GetData() {
		return data;
	}
	
	public synchronized void PrintTree() {
		for(String connection : data) {
			System.out.println(connection);
		}
	}
}
