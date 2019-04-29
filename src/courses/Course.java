package courses;

import java.io.Serializable;
import java.util.HashMap;

import assessments.MainComponent;
import professors.Professor;
import students.Student;

public class Course implements Serializable {

	private static final long serialVersionUID = -6918510535972049633L;
	private String name;
	private Professor professor;
	private HashMap<String, Session> sessions;
	private HashMap<String, MainComponent> components;
	private HashMap<String, Student> students;

	public Course(String courseName, Professor professor) {
		this.name = courseName;
		this.professor = professor;
		this.sessions = new HashMap<String, Session>();
		this.components = new HashMap<String, MainComponent>();
		this.students = new HashMap<String, Student>();
	}

	public String getName() {
		return this.name;
	}

	public void insertSessions(String sessionName) {
		this.sessions.put(sessionName, new Session(sessionName));
	}

	public HashMap<String, Session> getSessions() {
		return this.sessions;
	}

	public HashMap<String, MainComponent> getComponents() {
		return this.components;
	}

	public HashMap<String, Student> getStudents() {
		return this.students;
	}

	public boolean hasVacancies() {
		int vacanciesForSession;
		for (HashMap.Entry<String, Session> session : this.sessions.entrySet()) {
			vacanciesForSession = 0;
			for (HashMap.Entry<Integer, Group> group : session.getValue().getGroups().entrySet()) {
				vacanciesForSession = vacanciesForSession + group.getValue().getVacancies();
			}
			if (vacanciesForSession == 0) {
				return false;
			}
		}
		return true;
	}

	public void printVacanciesForSession(String sessionName) {
		System.out.println("|--------------------------------------------------------|");
		System.out.printf("| %-54s |\n", sessionName);
		System.out.println("|--------------------------------------------------------|");
		System.out.println("| GroupID |                Vacancies                     |");
		System.out.println("|--------------------------------------------------------|");
		if (this.sessions.containsKey(sessionName)) {
			for (HashMap.Entry<Integer, Group> group : this.sessions.get(sessionName).getGroups().entrySet()) {
				System.out.println(String.format("| %-7d | [%-4d / %-4d]                                |",
						group.getKey(), group.getValue().getCapacity() - group.getValue().getVacancies(),
						group.getValue().getCapacity()));
			}
		}
		System.out.println("|--------------------------------------------------------|");
	}

	public void printStudentListForSessionGroup(String sessionName, int groupId) {
		System.out.println("|--------------------------------------------------------|");
		System.out.println("|---------------       STUDENTS      --------------------|");
		System.out.println("|--------------------------------------------------------|");

		for (HashMap.Entry<String, Student> student : this.getSessions().get(sessionName).getGroups().get(groupId)
				.getRegisteredStudents().entrySet()) {
			System.out.printf("| %-54s |\n", student.getKey());
		}
		System.out.println("|--------------------------------------------------------|");
	}

	public void addStudentToCourse(Student student) {
		if (this.students.containsKey(student.getName())) {
			return;
		} else {
			this.students.put(student.getName(), student);
		}
	}

	public void clearComponents() {
		for (HashMap.Entry<String, MainComponent> component : this.components.entrySet()) {
			component.getValue().clearSubcomponents();
		}
		this.components.clear();
	}

	public void setComponentWeightage(String componentName, float weightage) {
		if (this.components == null) {
			this.components = new HashMap<String, MainComponent>();
		}
		this.components.put(componentName, new MainComponent(componentName, weightage));
	}
}



