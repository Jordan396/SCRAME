package groups;

import java.io.Serializable;
import java.util.HashMap;

import students.Student;

public class Group implements Serializable {

	private static final long serialVersionUID = 5L;

	private int totalGroupSize;
	private int vacancies;
	private HashMap<String, Student> registeredStudents = new HashMap<String, Student>();

	public Group(int numVacanciesPerGroup) {
		this.totalGroupSize = numVacanciesPerGroup;
		this.vacancies = numVacanciesPerGroup;
	}

	public int getTotalGroupSize() {
		return this.totalGroupSize;
	}

	public int getNumVacancies() {
		return this.vacancies;
	}

	public void registerStudent(Student student) {
		registeredStudents.put(student.getName(), student);
		vacancies = vacancies - 1;
	}
	
	public void printRegisteredStudents() {
		System.out.println("|--------------------------------------------------------|");
		System.out.println("| Here's a list of students registered in this group     |");
		System.out.println("|--------------------------------------------------------|");
		for (HashMap.Entry<String, Student> entry : this.registeredStudents.entrySet()) {
			System.out.println(String.format("| %-" + 55 + "s" + "|", entry.getKey()));
		}
		System.out.println("|--------------------------------------------------------|");
	}

}
