package students;

import java.io.Serializable;
import java.util.HashMap;

import courses.Course;

public class Student implements Serializable {

	private static final long serialVersionUID = 4L;

	private String name;
	private HashMap<String, Course> courses;
	private HashMap<String, Result> results;

	public Student(String name) {
		this.name = name;
		this.courses = new HashMap<String, Course>();
		this.results = new HashMap<String, Result>();
	}

	public String getName() {
		return this.name;
	}

	public HashMap<String, Course> getCourses() {
		return this.courses;
	}

	public HashMap<String, Result> getResults() {
		return this.results;
	}
	
	public void addCourseToStudent(Course course) {
		this.results.put(course.getCourseName(), new Result(course));
		if (this.courses.containsKey(course.getCourseName())) {
			return;
		} else {
			this.courses.put(course.getCourseName(), course);
		}
	}
}
