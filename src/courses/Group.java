package courses;

import java.io.Serializable;
import java.util.HashMap;

import students.Student;

public class Group implements Serializable {

	private static final long serialVersionUID = -5454488238928894287L;
	private int capacity;
	private int vacancies;
	private HashMap<String, Student> registeredStudents = new HashMap<String, Student>();

	public Group(int capacity) {
		this.capacity = capacity;
		this.vacancies = capacity;
	}

	public int getCapacity() {
		return this.capacity;
	}

	public int getVacancies() {
		return this.vacancies;
	}

	public void registerStudent(Student student) {
		registeredStudents.put(student.getName(), student);
		vacancies = vacancies - 1;
	}
	
	public HashMap<String, Student> getRegisteredStudents() {
		return this.registeredStudents;
	}

}
