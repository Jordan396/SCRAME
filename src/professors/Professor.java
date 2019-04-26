package professors;

import java.io.Serializable;
import java.util.HashMap;

import courses.Course;

public class Professor implements Serializable {

	private static final long serialVersionUID = 6L;

	private String name;
	HashMap<String, Course> courses;

	public Professor(String name) {
		this.name = name;
	}

	public void addCourseToProfessor(Course course) {
		if (this.courses.containsKey(course.getCourseName())) {
			return;
		} else {
			this.courses.put(course.getCourseName(), course);
		}
	}
}
