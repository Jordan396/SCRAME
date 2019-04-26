package courses;

import java.io.Serializable;
import java.util.HashMap;

import professors.Professor;

public class Course implements Serializable {
	
	private static final long serialVersionUID = 2L;
	
	Professor professor;
	HashMap<String, Integer> registeredStudents = new HashMap<>(); 
	
	public Course(String courseName) {
		
	}
}
