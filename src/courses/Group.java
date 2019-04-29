package courses;

import java.io.Serializable;
import java.util.HashMap;

import students.Student;

/**
 * Group class to contain students from a course's session.
 * 
 * @author jordan396
 */
public class Group implements Serializable {

	private static final long serialVersionUID = -5454488238928894287L;
	private int capacity;
	private int vacancies;
	private HashMap<String, Student> registeredStudents = new HashMap<String, Student>();

	/**
	 * Contructor for Group object.
	 * 
	 * Each group maintains a HashMap of students registered under the group, as
	 * well as its capacity and vacancies available.
	 * 
	 * @param capacity Maximum number of students that can be registered for group
	 * 
	 * @return A Group object
	 */
	public Group(int capacity) {
		this.capacity = capacity;
		this.vacancies = capacity;
	}

	/**
	 * Getter method for this group's capacity.
	 * 
	 * @return Capacity for this group
	 */
	public int getCapacity() {
		return this.capacity;
	}

	/**
	 * Getter method for this group's vacancies.
	 * 
	 * @return Number of vacancies available for this group
	 */
	public int getVacancies() {
		return this.vacancies;
	}
	
	/**
	 * Getter method for this group's HashMap registeredStudents.
	 * 
	 * @return HashMap registeredStudents
	 */
	public HashMap<String, Student> getRegisteredStudents() {
		return this.registeredStudents;
	}

	/**
	 * This method registers a student under this group.
	 * 
	 * Students can only be registered if there are vacancies available. When a
	 * student is registered, the number of vacancies is decremented by one.
	 * 
	 * @param student Student to be registered
	 */
	public void registerStudent(Student student) {
		registeredStudents.put(student.getName(), student);
		vacancies = vacancies - 1;
	}
	
	

}
