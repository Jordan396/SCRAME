package courses;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Session class representing the delivery method of the course (e.g. lecture)
 * containing groups of students.
 * 
 * @author jordan396
 */
public class Session implements Serializable {

	private static final long serialVersionUID = -8863384508499686534L;

	private String name;
	private HashMap<Integer, Group> groups;

	/**
	 * Constructor for Session object.
	 * 
	 * Each session maintains a HashMap of groups, where each group contains
	 * students registered for the course.
	 * 
	 * @param name Name of the session
	 * 
	 * @return A Session object
	 */
	public Session(String name) {
		this.name = name;
		this.groups = new HashMap<Integer, Group>();
	}

	/**
	 * This method adds a group to the HashMap groups.
	 * 
	 * @param groupId  ID of the group to be added.
	 * @param capacity Maximum number of students the group can hold
	 */
	public void insertGroup(int groupId, int capacity) {
		this.groups.put(groupId, new Group(capacity));
	}

	/**
	 * Getter method for this session's groups.
	 * 
	 * @return HashMap groups
	 */
	public HashMap<Integer, Group> getGroups() {
		return this.groups;
	}

}
