package students;

import java.io.Serializable;
import java.util.HashMap;

import courses.Course;

/**
 * Student class describing a student object.
 * 
 * @author jordan396
 */
public class Student implements Serializable {

	private static final long serialVersionUID = -664614176542581931L;
	private String name;
	private HashMap<String, Course> courses;
	private HashMap<String, Result> results;

	/**
	 * Constructor for Student object.
	 * 
	 * Each student is identified by a unique name. The student object keeps track
	 * of the Courses the student is registered under, as well as the Results
	 * obtained for each course.
	 * 
	 * @param name Name of the student
	 * 
	 * @return A Student object
	 */
	public Student(String name) {
		this.name = name;
		this.courses = new HashMap<String, Course>();
		this.results = new HashMap<String, Result>();
	}
	
	/**
	 * Getter method for this student's name.
	 * 
	 * @return Name of this student
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Getter method for this student's courses.
	 * 
	 * @return HashMap courses
	 */
	public HashMap<String, Course> getCourses() {
		return this.courses;
	}
	
	/**
	 * Getter method for this student's results.
	 * 
	 * @return HashMap results
	 */
	public HashMap<String, Result> getResults() {
		return this.results;
	}
	
	/**
	 * This method adds a Course to the student's HashMap courses.
	 */
	public void addCourseToStudent(Course course) {

		if (this.courses.containsKey(course.getName())) {
			return;
		} else {
			this.results.put(course.getName(), new Result(course));
			this.courses.put(course.getName(), course);
		}
	}
}
