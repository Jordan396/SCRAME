package courses;

import java.io.Serializable;
import java.util.HashMap;

import groups.Group;

public class Course implements Serializable {

	private static final long serialVersionUID = 2L;

	private String courseName;
	private String professorName;
	private int numTutorialGroups;
	private int numVacanciesPerTutorialGroup;
	private int numLaboratoryGroups;
	private int numVacanciesPerLaboratoryGroup;

	public HashMap<Integer, Group> tutorials;
	public HashMap<Integer, Group> laboratories;

	public Course(String courseName, String professorName, int numTutorialGroups, int numVacanciesPerTutorialGroup,
			int numLaboratoryGroups, int numVacanciesPerLaboratoryGroup) {
		this.courseName = courseName;
		this.professorName = professorName;
		this.numTutorialGroups = numTutorialGroups;
		this.numVacanciesPerTutorialGroup = numVacanciesPerTutorialGroup;
		this.numLaboratoryGroups = numLaboratoryGroups;
		this.numVacanciesPerLaboratoryGroup = numVacanciesPerLaboratoryGroup;
		this.tutorials = new HashMap<Integer, Group>();
		this.laboratories = new HashMap<Integer, Group>();

		for (int tutorialId = 0; tutorialId < numTutorialGroups; tutorialId++) {
			tutorials.put(tutorialId, new Group(numVacanciesPerTutorialGroup));
		}

		for (int laboratoryId = 0; laboratoryId < numLaboratoryGroups; laboratoryId++) {
			tutorials.put(laboratoryId, new Group(numVacanciesPerLaboratoryGroup));
		}
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

	public int getNumTutorialGroups() {
		return numTutorialGroups;
	}

	public int getNumLaboratoryGroups() {
		return numLaboratoryGroups;
	}

	public void printTutorials() {
		System.out.println("|--------------------------------------------------------|");
		System.out.println("| Here's a list of tutorial groups for this course       |");
		System.out.println("|--------------------------------------------------------|");
		System.out.println("| Group Number |               Vacancies                 |");
		System.out.println("|--------------------------------------------------------|");
		for (int tutorialId = 0; tutorialId < numTutorialGroups; tutorialId++) {
			System.out.println(String.format("| %-12d | [%d / %d]%20s|", tutorialId + 1,
					numVacanciesPerTutorialGroup - tutorials.get(tutorialId).getAvailableVacancies(), numVacanciesPerTutorialGroup, ' '));
		}
		System.out.println("|--------------------------------------------------------|");
	}

	public void printLaboratories() {
		System.out.println("|--------------------------------------------------------|");
		System.out.println("| Here's a list of laboratory groups for this course     |");
		System.out.println("|--------------------------------------------------------|");
		System.out.println("| Lab   Number |               Vacancies                 |");
		System.out.println("|--------------------------------------------------------|");
		for (int laboratoryId = 0; laboratoryId < numLaboratoryGroups; laboratoryId++) {
			System.out.println(String.format("| %-12d | [%d / %d]%20s|", laboratoryId,
					numVacanciesPerLaboratoryGroup - laboratories.get(laboratoryId).getAvailableVacancies(), numVacanciesPerLaboratoryGroup, ' '));
		}
		System.out.println("|--------------------------------------------------------|");
	}
}
