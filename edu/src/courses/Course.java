package courses;

import java.io.Serializable;
import java.util.HashMap;

import professors.Professor;
import students.Student;

/**
 * Course class to add register students, print list of students registered, set
 * coursework components and more.
 * 
 * @author jordan396
 */
public class Course implements Serializable {

	private static final long serialVersionUID = -6918510535972049633L;
	private String name;
	private Professor professor;
	private HashMap<String, Session> sessions;
	private HashMap<String, MainComponent> components;
	private HashMap<String, Student> students;

	/**
	 * Constructor for Course object.
	 * 
	 * Each course must be assigned a course coordinator (Professor). A course
	 * object keeps track of HashMaps of three key objects:
	 * <ul>
	 * <li>sessions: The delivery format for the course (e.g. lecture)</li>
	 * <li>components: The components of which students will be graded on</li>
	 * <li>students: The students registered under this course</li>
	 * </ul>
	 * 
	 * @param courseName Name of the course
	 * @param professor  Course coordinator for the course
	 * 
	 * @return A Course object
	 */
	public Course(String courseName, Professor professor) {
		this.name = courseName;
		this.professor = professor;
		this.sessions = new HashMap<String, Session>();
		this.components = new HashMap<String, MainComponent>();
		this.students = new HashMap<String, Student>();
	}

	/**
	 * Getter method for this course's name.
	 * 
	 * @return Name of this course
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Getter method for this course's sessions.
	 * 
	 * @return Sessions for this course
	 */
	public HashMap<String, Session> getSessions() {
		return this.sessions;
	}

	/**
	 * Getter method for this course's components.
	 * 
	 * @return Components for this course
	 */
	public HashMap<String, MainComponent> getComponents() {
		return this.components;
	}

	/**
	 * Getter method for this course's students.
	 * 
	 * @return Students registered for this course
	 */
	public HashMap<String, Student> getStudents() {
		return this.students;
	}

	/**
	 * This method inserts a session into this course's sessions HashMap.
	 */
	public void insertSessions(String sessionName) {
		this.sessions.put(sessionName, new Session(sessionName));
	}

	/**
	 * This method checks if the course has vacancies available.
	 * 
	 * A vacancy is available when there is at least one vacancy in every course
	 * component. This way, we assume that when a student registers for the course,
	 * the student must be registered under every course component. If at least one
	 * course component has no vacancies, the entire course will not have vacancies.
	 * 
	 * @return true if course has vacancies; false otherwise.
	 */
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

	/**
	 * This method prints a list of vacancies of groups across a specified session.
	 * 
	 * @param sessionName Name of the session to print group vacancies from
	 */
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

	/**
	 * This method prints a list of students for a specified session and group.
	 * 
	 * @param sessionName Name of the session to print student list from
	 * @param groupId     ID of the group in the specified session to print student
	 *                    list from
	 */
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

	/**
	 * This method adds a student to the course.
	 * 
	 * @param student Student object to be added to the course
	 */
	public void addStudentToCourse(Student student) {
		if (this.students.containsKey(student.getName())) {
			return;
		} else {
			this.students.put(student.getName(), student);
		}
	}

	/**
	 * This method resets the HashMap components for this course.
	 */
	public void clearComponents() {
		for (HashMap.Entry<String, MainComponent> component : this.components.entrySet()) {
			component.getValue().clearSubcomponents();
		}
		this.components.clear();
	}

	/**
	 * This method sets the weightage of a specified component in this course.
	 */
	public void setComponentWeightage(String componentName, float weightage) {
		if (this.components == null) {
			this.components = new HashMap<String, MainComponent>();
		}
		this.components.put(componentName, new MainComponent(componentName, weightage));
	}
}
