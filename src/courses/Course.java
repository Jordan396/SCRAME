package courses;

import java.io.Serializable;
import java.util.HashMap;

import assessments.Component;
import groups.Group;
import professors.Professor;
import students.Student;

public class Course implements Serializable {

	private static final long serialVersionUID = 2L;

	private String courseName;
	private Professor professor;
	private int numVacanciesPerLectureGroup;
	private int numTutorialGroups;
	private int numVacanciesPerTutorialGroup;
	private int numLaboratoryGroups;
	private int numVacanciesPerLaboratoryGroup;

	private Group lecture;
	private HashMap<Integer, Group> tutorials;
	private HashMap<Integer, Group> laboratories;
	private HashMap<String, Student> students;
	private HashMap<String, Component> components;

	public Course(String courseName, Professor professor, int numVacanciesPerLectureGroup, int numTutorialGroups,
			int numVacanciesPerTutorialGroup, int numLaboratoryGroups, int numVacanciesPerLaboratoryGroup) {
		this.courseName = courseName;
		this.professor = professor;

		this.numVacanciesPerLectureGroup = numVacanciesPerLectureGroup;
		this.numTutorialGroups = numTutorialGroups;
		this.numVacanciesPerTutorialGroup = numVacanciesPerTutorialGroup;
		this.numLaboratoryGroups = numLaboratoryGroups;
		this.numVacanciesPerLaboratoryGroup = numVacanciesPerLaboratoryGroup;

		this.lecture = new Group(numVacanciesPerLectureGroup);
		this.tutorials = new HashMap<Integer, Group>();
		for (int tutorialId = 0; tutorialId < numTutorialGroups; tutorialId++) {
			tutorials.put(tutorialId, new Group(numVacanciesPerTutorialGroup));
		}
		this.laboratories = new HashMap<Integer, Group>();
		for (int laboratoryId = 0; laboratoryId < numLaboratoryGroups; laboratoryId++) {
			laboratories.put(laboratoryId, new Group(numVacanciesPerLaboratoryGroup));
		}

		this.students = new HashMap<String, Student>();
		this.components = new HashMap<String, Component>();
	}

	public String getCourseName() {
		return this.courseName;
	}

	public int getNumTutorialGroups() {
		return numTutorialGroups;
	}

	public int getNumLaboratoryGroups() {
		return numLaboratoryGroups;
	}

	public HashMap<Integer, Group> getTutorials() {
		return this.tutorials;
	}

	public HashMap<Integer, Group> getLaboratories() {
		return this.laboratories;
	}

	public HashMap<String, Student> getStudents() {
		return this.students;
	}

	public Group getLecture() {
		return this.lecture;
	}

	public boolean hasVacancies() {
		int vacanciesForGroup = 0;

		if (this.lecture.getVacancies() == 0) {
			return false;
		}

		if (numTutorialGroups != 0) {
			for (int tutorialId = 0; tutorialId < this.numTutorialGroups; tutorialId++) {
				vacanciesForGroup += tutorials.get(tutorialId).getVacancies();
			}
			if (vacanciesForGroup == 0) {
				return false;
			}
		}

		vacanciesForGroup = 0;

		if (numLaboratoryGroups != 0) {
			for (int laboratoryId = 0; laboratoryId < this.numLaboratoryGroups; laboratoryId++) {
				vacanciesForGroup += laboratories.get(laboratoryId).getVacancies();
			}
			if (vacanciesForGroup == 0) {
				return false;
			}
		}
		return true;
	}

	public boolean hasTutorial() {
		if (numTutorialGroups == 0) {
			return false;
		}
		return true;
	}

	public boolean hasLaboratory() {
		if (numLaboratoryGroups == 0) {
			return false;
		}
		return true;
	}

	public void printLectureVacancies() {
		System.out.println("|--------------------------------------------------------|");
		System.out.println("| Here's a list of lecture groups for this course        |");
		System.out.println("|--------------------------------------------------------|");
		System.out.println("| Group Number |               Vacancies                 |");
		System.out.println("|--------------------------------------------------------|");

		System.out.println(String.format("| 1            | [%-4d / %-4d]                           |",
				(lecture.getCapacity() - lecture.getVacancies()), lecture.getCapacity()));

		System.out.println("|--------------------------------------------------------|");
	}

	public void printTutorialVacancies() {
		System.out.println("|--------------------------------------------------------|");
		System.out.println("| Here's a list of tutorial groups for this course       |");
		System.out.println("|--------------------------------------------------------|");
		System.out.println("| Group Number |               Vacancies                 |");
		System.out.println("|--------------------------------------------------------|");
		for (int tutorialId = 0; tutorialId < numTutorialGroups; tutorialId++) {
			System.out.println(String.format("| %-12d | [%-4d / %-4d]                           |", tutorialId + 1,
					numVacanciesPerTutorialGroup - tutorials.get(tutorialId).getVacancies(),
					numVacanciesPerTutorialGroup));
		}
		System.out.println("|--------------------------------------------------------|");
	}

	public void printLaboratoryVacancies() {
		System.out.println("|--------------------------------------------------------|");
		System.out.println("| Here's a list of laboratory groups for this course     |");
		System.out.println("|--------------------------------------------------------|");
		System.out.println("| Lab   Number |               Vacancies                 |");
		System.out.println("|--------------------------------------------------------|");
		for (int laboratoryId = 0; laboratoryId < numLaboratoryGroups; laboratoryId++) {
			System.out.println(String.format("| %-12d | [%-4d / %-4d]                           |", laboratoryId + 1,
					numVacanciesPerLaboratoryGroup - laboratories.get(laboratoryId).getVacancies(),
					numVacanciesPerLaboratoryGroup));
		}
		System.out.println("|--------------------------------------------------------|");
	}

	public void setComponentWeightage(String componentName, float weightage) {
		if (this.components == null) {
			this.components = new HashMap<String, Component>();
		}

		if (this.components.containsKey(componentName)) {
			this.components.get(componentName).setWeightage(weightage);
		} else {
			this.components.put(componentName, new Component(componentName, weightage));
		}
	}

	public HashMap<String, Component> getComponents() {
		return this.components;
	}

	public void addStudentToCourse(Student student) {
		if (this.students.containsKey(student.getName())) {
			return;
		} else {
			this.students.put(student.getName(), student);
		}
	}
	
	public void clearComponents() {
		this.components.clear();
	}

}
