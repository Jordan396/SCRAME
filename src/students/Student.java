package students;

import java.io.Serializable;
import java.util.HashMap;

import courses.Course;

public class Student implements Serializable {

	private static final long serialVersionUID = 4L;

	private String name;
	private HashMap<String, Course> registeredCourses = new HashMap<String, Course>();
	private HashMap<String, Result> courseResults = new HashMap<String, Result>();

	public Student(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public HashMap<String, Course> getRegisteredCourses() {
		return this.registeredCourses;
	}

}
