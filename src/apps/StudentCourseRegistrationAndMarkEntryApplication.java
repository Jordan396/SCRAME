package apps;

import java.io.Serializable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Scanner;

import assessments.Component;
import courses.Course;
import professors.Professor;
import students.Result;
import students.Student;

public class StudentCourseRegistrationAndMarkEntryApplication implements Serializable {

	private static final long serialVersionUID = 1L;
	static Scanner scanner = new Scanner(System.in);

	// Variables to store objects in current instance of SCRAME
	HashMap<String, Student> students;
	HashMap<String, Course> courses;
	HashMap<String, Professor> professors;

	public static void main(String[] args) {
		// Variables for saving/loading SCRAME
		String filename = "scrame.ser";
		StudentCourseRegistrationAndMarkEntryApplication SCRAME = null;

		System.out.println("Checking for existing applications...");

		try {
			// Reading the object from a file
			FileInputStream file = new FileInputStream(filename);
			ObjectInputStream in = new ObjectInputStream(file);
			SCRAME = (StudentCourseRegistrationAndMarkEntryApplication) in.readObject();
			in.close();
			file.close();
			System.out.println("Application found! Loading application...");
		} catch (IOException ex) {
			System.out.println("No application found! Creating a new application...");
			SCRAME = new StudentCourseRegistrationAndMarkEntryApplication();
		} catch (ClassNotFoundException ex) {
			System.out.println("ClassNotFoundException caught.");
		}

		while (true) {
			System.out.println("|----------------------------------------------------------|");
			System.out.println("| Student Course Registration And Mark Entry (SCRAME) Menu |");
			System.out.println("|----------------------------------------------------------|");
			System.out.println("| Please select a command below                            |");
			System.out.println("| 1. Add a student                                         |");
			System.out.println("| 2. Add a course                                          |");
			System.out.println("| 3. Register student for course                           |");
			System.out.println("| 4. Check available slots for class                       |");
			System.out.println("| 5. Print student list                                    |");
			System.out.println("| 6. Enter course assessment components weightage          |");
			System.out.println("| 7. Enter coursework score                                |");
			System.out.println("| 8. Enter exam score                                      |");
			System.out.println("| 9. Print course statistics                               |");
			System.out.println("| 10. Print student transcript                             |");
			System.out.println("|----------------------------------------------------------|");
			System.out.println("|                                   | (0) Save | (-1) Exit |");
			System.out.println("|----------------------------------------------------------|");

			int userCommand = scanner.nextInt();
			scanner.nextLine();

			switch (userCommand) {
			case 1:
				SCRAME.addStudent();
				break;
			case 2:
				SCRAME.addCourse();
				break;
			case 3:
				SCRAME.registerStudentForCourse();
				break;
			case 4:
				SCRAME.checkAvailableSlotInClass();
				break;
			case 5:
				SCRAME.printStudentListForCourse();
				break;
			case 6:
				SCRAME.enterCourseComponentsWeightage();
				break;
			case 7:
				SCRAME.enterCourseworkScore();
				break;
			case 8:
				SCRAME.enterExamScore();
				break;
			case 9:
				SCRAME.printCourseStatistics();
				break;
			case 10:
				SCRAME.printStudentTranscript();
				break;
			case -1:
				System.out.println("Exiting application...");
				scanner.close();
				return;
			case 0:
				System.out.println("Saving application...");

				try {
					// Saving of object in a file
					FileOutputStream file = new FileOutputStream(filename);
					ObjectOutputStream out = new ObjectOutputStream(file);

					// Method for serialization of object
					out.writeObject(SCRAME);

					out.close();
					file.close();

					System.out.println("Exiting application...");
				} catch (IOException ex) {
					System.out.println("IOException caught");
				}
				break;
			}

		}

	}

	public StudentCourseRegistrationAndMarkEntryApplication() {
		this.students = new HashMap<String, Student>();
		this.courses = new HashMap<String, Course>();
	}

	public void addStudent() {
		String studentName;

		System.out.println("Enter the name of the student you'd like to add:");
		studentName = getInputString(30);
		if (this.students.containsKey(studentName)) { // Student by that name already exists
			System.out.printf("Sorry, student %s already exists!\n", studentName);
			return;
		}
		this.students.put(studentName, new Student(studentName));
		System.out.println("Student added successfully!");
	}

	public void addCourse() {
		String courseName;
		String professorName;
		int numTutorialGroups = 0;
		int numVacanciesPerTutorialGroup = 0;
		int numLaboratoryGroups = 0;
		int numVacanciesPerLaboratoryGroup = 0;
		Professor professor = null;

		// Get course
		System.out.println("Enter the name of the course you'd like to add:");
		courseName = getInputString(30);
		if (this.courses.containsKey(courseName)) { // Student by that name already exists
			System.out.printf("Sorry, the course %s already exists!\n", courseName);
			return;
		}

		// Get professor for course
		System.out.println("Enter the name of the professor coordinating this course:");
		professorName = getInputString(30);

		// Get tutorial for course
		System.out.println("How many tutorial groups does this course have?");
		numTutorialGroups = getInputInteger(0, 10);
		if (numTutorialGroups != 0) {
			System.out.println("How many vacancies are there in each tutorial group?");
			numVacanciesPerTutorialGroup = getInputInteger(0, 1000);
			scanner.nextLine();
		}

		// Get laboratory for course
		if (numTutorialGroups != 0) {
			System.out.println("How many laboratory groups does this course have?");
			numLaboratoryGroups = getInputInteger(0, 10);
			if (numLaboratoryGroups != 0) {
				System.out.println("How many vacancies are there in each laboratory group?");
				numVacanciesPerLaboratoryGroup = getInputInteger(0, 1000);
				scanner.nextLine();
			}
		}

		// Check if professor already exists
		if (this.professors.containsKey(professorName)) {
			professor = this.professors.get(professorName);
		} else {
			professor = new Professor(professorName);
			this.professors.put(professorName, professor);
		}

		Course course = new Course(courseName, professor, numTutorialGroups, numVacanciesPerTutorialGroup,
				numLaboratoryGroups, numVacanciesPerLaboratoryGroup);
		this.courses.put(courseName, course);
		professor.addCourseToProfessor(course);

		System.out.println("Course added successfully!");
	}

	public void registerStudentForCourse() {
		String studentName;
		String courseName;
		int tutorialGroupId;
		int laboratoryGroupId;
		Course course;
		Student student;

		// Print list of students for user's reference
		this.printStudents();
		System.out.println("Enter the name of the student you'd like to register for course:");
		studentName = getInputString(30);
		if (!this.students.containsKey(studentName)) { // Student by that name does not exist
			System.out.printf("Sorry, student %s does not exist!\n", studentName);
			return;
		}

		student = this.students.get(studentName);

		// Print list of courses for user's reference
		this.printCourses();
		System.out.println("Enter the name of the course you'd like to register student to:");
		courseName = getInputString(30);
		if (!this.courses.containsKey(courseName)) { // Course by that name does not exist
			System.out.printf("Sorry, course %s does not exist!\n", courseName);
			return;
		}

		course = this.courses.get(courseName);

		// Check if course has vacancies
		if (!course.hasVacancies()) {
			System.out.println("Sorry, this course has no more vacancies.");
			return;
		}
		// Check if student is already registered
		if (student.getCourses().containsKey(courseName)) {
			System.out.println("Sorry, student is already registered under this course.");
			return;
		}

		// Assign student to course and vice versa
		course.addStudentToCourse(student);
		student.addCourseToStudent(course);

		// Assign student to lecture group
		this.courses.get(courseName).getLecture().registerStudent(this.students.get(studentName));

		// Assign student to tutorial group
		if (this.courses.get(courseName).hasTutorial()) {
			while (true) {
				this.courses.get(courseName).printTutorialVacancies();
				System.out.println("Enter a tutorial group number to register student under:\n");
				tutorialGroupId = getInputInteger(0, 10);
				if (tutorialGroupId > (this.courses.get(courseName).getNumTutorialGroups())) {
					System.out.println("That tutorial group number does not exist. Please try again.");
				}
				if (this.courses.get(courseName).getTutorials().get(tutorialGroupId).getNumVacancies() == 0) {
					System.out.println("That tutorial group is full. Please try again.");
				}
				this.courses.get(courseName).getTutorials().get(tutorialGroupId)
						.registerStudent(this.students.get(studentName));
				break;
			}
		}

		// Assign student to laboratory group
		if (this.courses.get(courseName).hasLaboratory()) {
			while (true) {
				this.courses.get(courseName).printLaboratoryVacancies();
				System.out.println("Enter a laboratory group number to register student under:\n");
				laboratoryGroupId = getInputInteger(0, 10);
				if (laboratoryGroupId > (this.courses.get(courseName).getNumLaboratoryGroups())) {
					System.out.println("That laboratory group number does not exist!");
				}
				if (this.courses.get(courseName).getLaboratories().get(laboratoryGroupId).getNumVacancies() == 0) {
					System.out.println("That laboratory group is full!");
				}
				this.courses.get(courseName).getLaboratories().get(laboratoryGroupId)
						.registerStudent(this.students.get(studentName));
				break;
			}
		}
		System.out.println("Student registered for course successfully!");
		return;
	}

	public void checkAvailableSlotInClass() {
		String courseName;

		System.out.println("Enter the name of the course you'd like to check vacancies for:");
		courseName = getInputString(30);
		if (!this.courses.containsKey(courseName)) { // Course by that name does not exist
			System.out.printf("Sorry, course %s does not exist!\n", courseName);
			return;
		}

		this.courses.get(courseName).printLectureVacancies();

		if (this.courses.get(courseName).hasTutorial()) {
			this.courses.get(courseName).printTutorialVacancies();
		}
		if (this.courses.get(courseName).hasLaboratory()) {
			this.courses.get(courseName).printLaboratoryVacancies();
		}
	}

	public void printStudentListForCourse() {
		String courseName;
		int tutorialGroupId;
		int laboratoryGroupId;

		System.out.println("Enter the name of the course you'd like to print student list from:");
		courseName = getInputString(30);
		if (!this.courses.containsKey(courseName)) { // Course by that name does not exist
			System.out.printf("Sorry, course %s does not exist!\n", courseName);
			return;
		}

		System.out.printf("Main lecture group:");
		this.courses.get(courseName).getLecture().printRegisteredStudents();

		if (this.courses.get(courseName).hasTutorial()) {
			this.courses.get(courseName).printTutorialVacancies();
			System.out.printf("Enter a tutorial group to print student list from:");
			tutorialGroupId = getInputInteger(0, 10);
			if (tutorialGroupId > (this.courses.get(courseName).getNumTutorialGroups())) {
				System.out.println("That tutorial group number does not exist.");
				return;
			}
			this.courses.get(courseName).getTutorials().get(tutorialGroupId).printRegisteredStudents();
		}

		if (this.courses.get(courseName).hasLaboratory()) {
			this.courses.get(courseName).printLaboratoryVacancies();
			System.out.printf("Enter a laboratory group to print student list from:");
			laboratoryGroupId = getInputInteger(0, 10);
			if (laboratoryGroupId > (this.courses.get(courseName).getNumLaboratoryGroups())) {
				System.out.println("That laboratory group number does not exist.");
				return;
			}
			this.courses.get(courseName).getLaboratories().get(laboratoryGroupId).printRegisteredStudents();
		}
	}

	public void enterCourseComponentsWeightage() {
		Course course;
		String courseName;
		int examWeightage;
		int courseworkWeightage;

		int numSubcomponents;
		String subcomponentName;
		int subcomponentWeightage;
		int totalSubcomponentWeightage;

		System.out.println("Enter the name of the course you'd like to assign components weightage to:");
		courseName = scanner.nextLine();
		if (!this.courses.containsKey(courseName)) { // Course by that name does not exist
			System.out.printf("Sorry, course %s does not exist!\n", courseName);
			return;
		}
		course = this.courses.get(courseName);

		System.out.println("Enter the weightage for this course's exam:");
		examWeightage = getInputInteger(0, 100);
		while ((examWeightage < 0) || (examWeightage > 100)) {
			System.out.println("Exam weightage must be an integer that falls between 0 - 100. Please try again.");
			examWeightage = scanner.nextInt();
			scanner.nextLine();
		}
		course.setComponentWeightage("ExamMainComponent", examWeightage);

		courseworkWeightage = 100 - examWeightage;

		while (true) {
			totalSubcomponentWeightage = 0;

			System.out.println("How many coursework components does this course have?");
			numSubcomponents = getInputInteger(0, 10);

			if (numSubcomponents == 0) {
				course.setComponentWeightage("CourseworkMainComponent", courseworkWeightage);
				break;
			} else {
				System.out.println("You are about to enter the weightage for each coursework component.");
				System.out.println("Bear in mind that total coursework component weightage must sum to 100.");

				for (int componentId = 0; componentId < numSubcomponents; componentId++) {
					System.out.printf("Enter the name of coursework component %d:\n", componentId + 1);
					subcomponentName = getInputString(30);
					while (subcomponentName.equals("ExamMainComponent")
							|| subcomponentName.equals("CourseworkMainComponent")) {
						System.out.printf("%s cannot be used as a component name!\n", subcomponentName);
						System.out.printf("Enter the name of coursework component %d:\n", componentId + 1);
						subcomponentName = getInputString(30);
					}
					System.out.printf("Enter the weightage for component %s:\n", subcomponentName);
					subcomponentWeightage = getInputInteger(0, 100);
					totalSubcomponentWeightage = totalSubcomponentWeightage + subcomponentWeightage;
					course.setComponentWeightage(subcomponentName, subcomponentWeightage * (courseworkWeightage / 100));
				}
				if (totalSubcomponentWeightage != 100) {
					System.out.printf(
							"Total weightage for coursework components was %d which is invalid. Please try again.\n",
							totalSubcomponentWeightage);
				} else {
					break;
				}
			}

		}
		System.out.println("Course components weightage assigned successfully!");
	}

	public void enterCourseworkScore() {
		Course course;
		String courseName;
		Result result;
		String componentName;
		int score;

		System.out.println("Enter the name of the course you'd like to enter coursework score to:");
		courseName = scanner.nextLine();
		if (!this.courses.containsKey(courseName)) { // Course by that name does not exist
			System.out.printf("Sorry, course %s does not exist!\n", courseName);
			return;
		}
		course = this.courses.get(courseName);

		if (course.getComponents() == null) {
			System.out.println("Components for this course must be set prior to entering score.");
			return;
		}
		System.out.printf("Setting score for %s...\n", courseName);

		for (HashMap.Entry<String, Student> student : course.getStudents().entrySet()) {
			result = student.getValue().getResults().get(courseName);
			System.out.printf("%s: %s:\n", courseName, student.getValue().getName());
			for (HashMap.Entry<String, Component> component : course.getComponents().entrySet()) {
				componentName = component.getValue().getName();
				if (!componentName.equals("ExamMainComponent")) {
					System.out.printf("Score obtained for %s:", componentName);
					score = getInputInteger(0, 100);
					result.enterScore(componentName, score);
				}
			}
		}

		System.out.println("Coursework score assigned successfully!");
	}

	public void enterExamScore() {
		Course course;
		String courseName;
		Result result;
		String componentName;
		int score;

		System.out.println("Enter the name of the course you'd like to enter exam score to:");
		courseName = scanner.nextLine();
		if (!this.courses.containsKey(courseName)) { // Course by that name does not exist
			System.out.printf("Sorry, course %s does not exist!\n", courseName);
			return;
		}
		course = this.courses.get(courseName);

		if (course.getComponents() == null) {
			System.out.println("Components for this course must be set prior to entering score.");
			return;
		}
		System.out.printf("Setting score for %s...\n", courseName);

		for (HashMap.Entry<String, Student> student : course.getStudents().entrySet()) {
			result = student.getValue().getResults().get(courseName);
			System.out.printf("%s: %s:\n", courseName, student.getValue().getName());

			componentName = "ExamMainComponent";
			System.out.printf("Score obtained for %s:", componentName);
			score = getInputInteger(0, 100);
			result.enterScore(componentName, score);
		}

		System.out.println("Coursework score assigned successfully!");
	}

	public void printCourseStatistics() {
		Course course;
		String courseName;
		Result result;
		int numStudents = 0;
		float score;
		float totalScorePercentage = 0;
		float courseworkScorePercentage = 0;
		float examScorePercentage = 0;

		System.out.println("Enter the name of the course you'd like to assign components weightage to:");
		courseName = scanner.nextLine();
		if (!this.courses.containsKey(courseName)) { // Course by that name does not exist
			System.out.printf("Sorry, course %s does not exist!\n", courseName);
			return;
		}
		course = this.courses.get(courseName);

		for (HashMap.Entry<String, Student> student : course.getStudents().entrySet()) {
			numStudents++;
			result = student.getValue().getResults().get(courseName);
			score = result.calculateOverallCourseworkScore("ExamMainComponent");
			if (Math.abs(score + 1) < 0.1) {
				System.out.println(
						"Not all students have received their scores for this course. Please enter their scores first and try again.");
			}
			courseworkScorePercentage = courseworkScorePercentage + score;

			score = result.calculateOverallExamScore("ExamMainComponent");
			if (Math.abs(score + 1) < 0.1) {
				System.out.println(
						"Not all students have received their scores for this course. Please enter their scores first and try again.");
			}
			examScorePercentage = examScorePercentage + score;
		}

		examScorePercentage = examScorePercentage / numStudents;
		courseworkScorePercentage = courseworkScorePercentage / numStudents;
		totalScorePercentage = examScorePercentage + courseworkScorePercentage;

		System.out.println("|--------------------------------------------------------|");
		System.out.println("| Here are the overall statistics for this course        |");
		System.out.println("|--------------------------------------------------------|");
		System.out.println(String.format("| OVERALL SCORE            | %-" + 55 + "f" + "|", totalScorePercentage));
		System.out.println(String.format("| OVERALL EXAM SCORE       | %-" + 55 + "f" + "|", examScorePercentage));
		System.out
				.println(String.format("| OVERALL COURSEWORK SCORE | %-" + 55 + "f" + "|", courseworkScorePercentage));
		System.out.println("|--------------------------------------------------------|");
	}

	public void printStudentTranscript() {
		Student student;
		String studentName;
		String courseName;
		float score;
		
		// Print list of students for user's reference
		this.printStudents();
		System.out.println("Enter the name of the student you'd like to register for course:");
		studentName = getInputString(30);
		if (!this.students.containsKey(studentName)) { // Student by that name does not exist
			System.out.printf("Sorry, student %s does not exist!\n", studentName);
			return;
		}
		student = this.students.get(studentName);
		
		System.out.println("|--------------------------------------------------------|");
		System.out.println(String.format("| TRANSCRIPT FOR %-55s |", studentName));
		System.out.println("|--------------------------------------------------------|");
		for (HashMap.Entry<String, Result> result : student.getResults().entrySet()) {
			courseName = result.getKey();
			score = result.getValue().calculateOverallTotalScore();
			System.out.println(String.format("| %-32s | %-20d |", courseName, score));
		}
		System.out.println("|--------------------------------------------------------|");
	}

	public void printStudents() {
		System.out.println("|--------------------------------------------------------|");
		System.out.println("| Here's a list of all students registered under SCRAME  |");
		System.out.println("|--------------------------------------------------------|");
		for (HashMap.Entry<String, Student> entry : students.entrySet()) {
			System.out.println(String.format("| %-" + 55 + "s" + "|", entry.getKey()));
		}
		System.out.println("|--------------------------------------------------------|");
	}

	public void printCourses() {
		System.out.println("|--------------------------------------------------------|");
		System.out.println("| Here's a list of all courses registered under SCRAME   |");
		System.out.println("|--------------------------------------------------------|");
		for (HashMap.Entry<String, Course> entry : courses.entrySet()) {
			System.out.println(String.format("| %-" + 55 + "s" + "|", entry.getKey()));
		}
		System.out.println("|--------------------------------------------------------|");
	}

	public String getInputString(int maxLength) {
		String input;

		input = scanner.nextLine();
		while (input.length() > maxLength) {
			System.out.printf("Your input exceeded the maximum number of chars (%d)! Please try again.\n", maxLength);
			input = scanner.nextLine();
		}
		return input;
	}

	public int getInputInteger(int lower, int upper) {
		int input;
		input = scanner.nextInt();
		scanner.nextLine();
		while ((input < lower) || (input > upper)) {
			System.out.printf("Your input must be between %d - %d. Please try again.\n", lower, upper);
			input = scanner.nextInt();
			scanner.nextLine();
		}
		return input;
	}
}
