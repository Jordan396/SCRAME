package groups;

import java.io.Serializable;
import java.util.HashMap;

import students.Student;

public class Group implements Serializable {

	private static final long serialVersionUID = 5L;

	private int totalGroupSize;
	private int availableVacancies;

	HashMap<String, Student> registeredStudents = new HashMap<String, Student>();

	public Group(int numVacanciesPerGroup) {
		this.totalGroupSize = numVacanciesPerGroup;
		this.availableVacancies = numVacanciesPerGroup;
	}

	public int getTotalGroupSize() {
		return this.totalGroupSize;
	}

	public int getAvailableVacancies() {
		return this.availableVacancies;
	}

	public void registerStudent(Student student) {
		registeredStudents.put(student.getName(), student);
		availableVacancies = availableVacancies - 1;
	}

}
