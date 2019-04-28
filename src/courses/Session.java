package courses;

import java.io.Serializable;
import java.util.HashMap;

public class Session implements Serializable{

	private static final long serialVersionUID = -8863384508499686534L;
	
	private String name;
	private HashMap<Integer, Group> groups;
	
	public Session(String name) {
		this.name = name;
		this.groups = new HashMap<Integer, Group>();
	}
	
	public void insertGroup(int groupId, int capacity) {
		this.groups.put(groupId, new Group(capacity));
	}
	
	public HashMap<Integer, Group> getGroups(){
		return this.groups;
	}

}
