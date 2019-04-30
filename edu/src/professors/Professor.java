package professors;

import java.io.Serializable;
import java.util.HashMap;

import courses.Course;

/**
 * Professor class to describe a Professor object.
 * 
 * @author jordan396
 */
public class Professor implements Serializable {

	private static final long serialVersionUID = 6082938758774354092L;
	private String name;
	HashMap<String, Course> courses;

	/**
	 * Constructor for Professor object.
	 * 
	 * Each professor can be assigned 0 to many courses as the course coordinator.
	 * 
	 * @param name Name of the professor
	 * 
	 * @return A Professor object
	 */
	public Professor(String name) {
		this.name = name;
		this.courses = new HashMap<String, Course>();
	}
	
	/**
	 * This method assigns a course to this professor.
	 * 
	 * @param course Course to be assigned
	 */
	public void addCourseToProfessor(Course course) {
		if (this.courses.containsKey(course.getName())) {
			return;
		} else {
			this.courses.put(course.getName(), course);
		}
	}
}
