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
import assessments.SubComponent;
import courses.Course;
import professors.Professor;
import students.Result;
import students.Student;

public class StudentCourseRegistrationAndMarkEntryApplication implements Serializable {

	private static final long serialVersionUID = 1L;
	static Scanner scanner = new Scanner(System.in);
	private static final String filename = "scrame.ser";
	private static final String examMainComponentName = "ExamMainComponent";
	private static final String courseworkMainComponentName = "CourseworkMainComponent";
	private static final String lectureSessionName = "lecture";
	private static final String tutorialSessionName = "tutorial";
	private static final String laboratorySessionName = "lab";

	// Variables to store objects in current instance of SCRAME
	HashMap<String, Student> students;
	HashMap<String, Course> courses;
	HashMap<String, Professor> professors;

	public static void main(String[] args) {
		int menuSelection;
		StudentCourseRegistrationAndMarkEntryApplication SCRAME = loadSCRAME();

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

			menuSelection = SCRAME.getInputInteger(-1, 10);
			switch (menuSelection) {
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
				SCRAME.checkAvailableSlotForCourse();
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
				SCRAME.exitApplication();
				break;
			case 0:
				SCRAME.saveSCRAME();
				break;
			default:
				System.out.println("Command not recognized. Please try again.");
				break;
			}

		}

	}

	public StudentCourseRegistrationAndMarkEntryApplication() {
		this.students = new HashMap<String, Student>();
		this.courses = new HashMap<String, Course>();
		this.professors = new HashMap<String, Professor>();
	}

	public static StudentCourseRegistrationAndMarkEntryApplication loadSCRAME() {
		StudentCourseRegistrationAndMarkEntryApplication SCRAME = null;

		System.out.println("Checking for existing SCRAME application...");
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
		return SCRAME;
	}

	public void saveSCRAME() {
		System.out.println("Saving application...");
		try {
			// Saving of object in a file
			FileOutputStream file = new FileOutputStream(filename);
			ObjectOutputStream out = new ObjectOutputStream(file);

			// Method for serialization of object
			out.writeObject(this);
			out.close();
			file.close();
		} catch (IOException ex) {
			System.out.println("IOException caught");
		}
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
		Professor professor = null;
		int vacancies;
		int groups;

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
		if (this.professors.containsKey(professorName)) {
			professor = this.professors.get(professorName);
		} else {
			professor = new Professor(professorName);
			this.professors.put(professorName, professor);
		}

		Course course = new Course(courseName, professor);
		this.courses.put(courseName, course);
		professor.addCourseToProfessor(course);

		// Get lecture for course
		System.out.println("How many vacancies are there in this lecture group?");
		vacancies = getInputInteger(1, 1000);
		course.insertSessions(lectureSessionName);
		course.getSessions().get(lectureSessionName).insertGroup(0, vacancies);

		// Get tutorial for course
		System.out.println("How many tutorial groups does this course have?");
		groups = getInputInteger(0, 10);
		if (groups != 0) {
			course.insertSessions(tutorialSessionName);
			System.out.println("How many vacancies are there in each tutorial group?");
			vacancies = getInputInteger(1, 1000);
			for (int groupId = 0; groupId < groups; groupId++) {
				course.getSessions().get(tutorialSessionName).insertGroup(groupId, vacancies);
			}
		}

		// Get laboratory for course
		if (groups != 0) {
			System.out.println("How many laboratory groups does this course have?");
			groups = getInputInteger(0, 10);
			if (groups != 0) {
				course.insertSessions(laboratorySessionName);
				System.out.println("How many vacancies are there in each laboratory group?");
				vacancies = getInputInteger(1, 1000);
				for (int groupId = 0; groupId < groups; groupId++) {
					course.getSessions().get(laboratorySessionName).insertGroup(groupId, vacancies);
				}
			}
		}

		System.out.println("Course added successfully!");
	}

	public void registerStudentForCourse() {
		String studentName;
		String courseName;
		int groupId;
		Course course;
		Student student;

		// Check if any students exist
		if (students.isEmpty()) {
			System.out.println("There are no students available. Please add a student first.");
			return;
		}

		// Check if any courses exist
		if (courses.isEmpty()) {
			System.out.println("There are no courses available. Please add a course first.");
			return;
		}

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
		course.getSessions().get(lectureSessionName).getGroups().get(0).registerStudent(student);

		// Assign student to tutorial group
		if (course.getSessions().containsKey(tutorialSessionName)) {
			while (true) {
				course.printVacanciesForSession(tutorialSessionName);
				System.out.println("Enter a tutorial group number to register student under:");

				groupId = getInputInteger(0, course.getSessions().get(tutorialSessionName).getGroups().size() - 1);
				if (course.getSessions().get(tutorialSessionName).getGroups().get(groupId).getVacancies() == 0) {
					System.out.println("That tutorial group is full. Please try again.");
				} else {
					course.getSessions().get(tutorialSessionName).getGroups().get(groupId).registerStudent(student);
					break;
				}
			}
		}

		// Assign student to laboratory group
		if (course.getSessions().containsKey(laboratorySessionName)) {
			while (true) {
				course.printVacanciesForSession(laboratorySessionName);
				System.out.println("Enter a laboratory group number to register student under:");
				groupId = getInputInteger(0, course.getSessions().get(laboratorySessionName).getGroups().size() - 1);
				System.out.printf("Group capacity: %d\n",
						course.getSessions().get(laboratorySessionName).getGroups().get(groupId).getCapacity());
				if (course.getSessions().get(laboratorySessionName).getGroups().get(groupId).getVacancies() == 0) {
					System.out.println("That laboratory group is full. Please try again.");
				} else {
					course.getSessions().get(laboratorySessionName).getGroups().get(groupId).registerStudent(student);
					break;
				}
			}
		}
		System.out.println("Student registered for course successfully!");
	}

	public void checkAvailableSlotForCourse() {
		String courseName;
		Course course;

		// Check if any courses exist
		if (courses.isEmpty()) {
			System.out.println("There are no courses available. Please add a course first.");
			return;
		}

		this.printCourses();
		System.out.println("Enter the name of the course you'd like to check vacancies for:");
		courseName = getInputString(30);
		if (!this.courses.containsKey(courseName)) { // Course by that name does not exist
			System.out.printf("Sorry, course %s does not exist!\n", courseName);
			return;
		}
		course = this.courses.get(courseName);

		course.printVacanciesForSession(lectureSessionName);
		course.printVacanciesForSession(tutorialSessionName);
		course.printVacanciesForSession(laboratorySessionName);
	}

	public void printStudentListForCourse() {
		String courseName;
		Course course;
		int groupId;

		// Check if any courses exist
		if (courses.isEmpty()) {
			System.out.println("There are no courses available. Please add a course first.");
			return;
		}

		this.printCourses();
		System.out.println("Enter the name of the course you'd like to print student list from:");
		courseName = getInputString(30);
		if (!this.courses.containsKey(courseName)) { // Course by that name does not exist
			System.out.printf("Sorry, course %s does not exist!\n", courseName);
			return;
		}
		course = this.courses.get(courseName);

		course.printVacanciesForSession(lectureSessionName);
		course.printStudentListForSessionGroup(lectureSessionName, 0);

		if (course.getSessions().containsKey(tutorialSessionName)) {
			course.printVacanciesForSession(tutorialSessionName);
			System.out.println("Enter a tutorial group to print student list from:");
			groupId = getInputInteger(0, course.getSessions().get(tutorialSessionName).getGroups().size() - 1);
			course.printStudentListForSessionGroup(tutorialSessionName, groupId);
		}

		if (course.getSessions().containsKey(laboratorySessionName)) {
			course.printVacanciesForSession(laboratorySessionName);
			System.out.println("Enter a laboratory group to print student list from:");
			groupId = getInputInteger(0, course.getSessions().get(laboratorySessionName).getGroups().size() - 1);
			course.printStudentListForSessionGroup(laboratorySessionName, groupId);
		}
	}

	public void enterCourseComponentsWeightage() {
		Course course;
		String courseName;
		float examWeightage;
		float courseworkWeightage;

		int numSubcomponents;
		String subcomponentName = null;
		float subcomponentWeightage;
		float totalSubcomponentWeightage;
		boolean subcomponentExists;

		// Check if any courses exist
		if (courses.isEmpty()) {
			System.out.println("There are no courses available. Please add a course first.");
			return;
		}

		this.printCourses();
		System.out.println("Enter the name of the course you'd like to assign components weightage to:");
		courseName = scanner.nextLine();
		if (!this.courses.containsKey(courseName)) { // Course by that name does not exist
			System.out.printf("Sorry, course %s does not exist!\n", courseName);
			return;
		}
		course = this.courses.get(courseName);

		while (true) {
			course.clearComponents();

			System.out.println("Enter the weightage for this course's exam:");
			examWeightage = getInputFloat(0, 100);
			course.setComponentWeightage(examMainComponentName, examWeightage);

			courseworkWeightage = 100 - examWeightage;
			course.setComponentWeightage(courseworkMainComponentName, courseworkWeightage);

			totalSubcomponentWeightage = 0;
			System.out.println("How many coursework components does this course have?");
			numSubcomponents = getInputInteger(0, 10);

			System.out.println("You are about to enter the weightage for each coursework component.");
			System.out.println("Bear in mind that total coursework component weightage must sum to 100.");

			if (numSubcomponents == 0) {
				break;
			}

			for (int componentId = 0; componentId < numSubcomponents; componentId++) {

				subcomponentExists = true;
				while (subcomponentExists) {
					System.out.printf("Enter the name of coursework subcomponent %d:\n", componentId + 1);
					subcomponentName = getInputString(30);
					if (course.getComponents().get(courseworkMainComponentName).getSubcomponents() == null) {
						subcomponentExists = false;
					} else {
						subcomponentExists = course.getComponents().get(courseworkMainComponentName).getSubcomponents()
								.containsKey(subcomponentName);
						System.out.println("Repeated subcomponent names are not allowed!");
					}
				}

				System.out.printf("Enter the weightage for subcomponent %s:\n", subcomponentName);
				subcomponentWeightage = getInputFloat(0, 100);
				totalSubcomponentWeightage = totalSubcomponentWeightage + subcomponentWeightage;

				course.getComponents().get(courseworkMainComponentName).setSubcomponentWeightage(subcomponentName,
						subcomponentWeightage);
			}
			if (!(Math.abs(totalSubcomponentWeightage - 100) < 0.1)) {
				System.out.printf(
						"Total weightage for coursework components was %.2f which is invalid. Please try again.\n",
						totalSubcomponentWeightage);
			} else {
				break;
			}
		}
		System.out.println("Course components weightage assigned successfully!");
	}

	public void enterCourseworkScore() {
		Course course;
		String courseName;
		Result result;
		String componentName;
		float score;
		float mainComponentWeightage;
		float subComponentWeightage;

		// Check if any courses exist
		if (courses.isEmpty()) {
			System.out.println("There are no courses available. Please add a course first.");
			return;
		}

		this.printCourses();
		System.out.println("Enter the name of the course you'd like to enter coursework score to:");
		courseName = scanner.nextLine();
		if (!this.courses.containsKey(courseName)) { // Course by that name does not exist
			System.out.printf("Sorry, course %s does not exist!\n", courseName);
			return;
		}
		course = this.courses.get(courseName);

		if ((!course.getComponents().containsKey(courseworkMainComponentName)
				|| (!course.getComponents().containsKey(examMainComponentName)))) {
			System.out.println("Components for this course must be set prior to entering score.");
			return;
		}

		System.out.printf("Setting score for %s...\n", courseName);

		for (HashMap.Entry<String, Student> student : course.getStudents().entrySet()) {
			result = student.getValue().getResults().get(courseName);
			System.out.printf("%s: %s:\n", courseName, student.getValue().getName());

			result.addResultItem(courseworkMainComponentName);
			mainComponentWeightage = course.getComponents().get(courseworkMainComponentName).getWeightage() / 100;

			if (course.getComponents().get(courseworkMainComponentName).hasSubcomponents()) {
				for (HashMap.Entry<String, SubComponent> subcomponent : course.getComponents()
						.get(courseworkMainComponentName).getSubcomponents().entrySet()) {
					componentName = subcomponent.getKey();
					subComponentWeightage = subcomponent.getValue().getWeightage() / 100;
					System.out.printf("Score obtained for %s:\n", componentName);
					score = getInputFloat(0, 100);
					result.getResultItems().get(courseworkMainComponentName).addResultItem(componentName,
							subComponentWeightage * score * mainComponentWeightage);
				}
			} else {
				System.out.printf("Score obtained for %s:\n", courseworkMainComponentName);
				score = getInputFloat(0, 100);
				result.getResultItems().get(courseworkMainComponentName).setValue(score);
			}

		}

		System.out.println("Coursework score assigned successfully!");
	}

	public void enterExamScore() {
		Course course;
		String courseName;
		Result result;
		float score;
		float weightage;

		// Check if any courses exist
		if (courses.isEmpty()) {
			System.out.println("There are no courses available. Please add a course first.");
			return;
		}

		this.printCourses();
		System.out.println("Enter the name of the course you'd like to enter exam score to:");
		courseName = getInputString(30);
		if (!this.courses.containsKey(courseName)) { // Course by that name does not exist
			System.out.printf("Sorry, course %s does not exist!\n", courseName);
			return;
		}
		course = this.courses.get(courseName);

		if ((!course.getComponents().containsKey(courseworkMainComponentName)
				|| (!course.getComponents().containsKey(examMainComponentName)))) {
			System.out.println("Components for this course must be set prior to entering score.");
			return;
		}

		System.out.printf("Setting score for %s...\n", courseName);

		for (HashMap.Entry<String, Student> student : course.getStudents().entrySet()) {
			result = student.getValue().getResults().get(courseName);
			System.out.printf("%s: %s:\n", courseName, student.getValue().getName());

			result.addResultItem(examMainComponentName);

			weightage = course.getComponents().get(examMainComponentName).getWeightage() / 100;
			System.out.printf("Marks obtained for %s:\n", examMainComponentName);
			score = getInputFloat(0, 100);
			result.getResultItems().get(examMainComponentName).setValue(score * weightage);

		}

		System.out.println("Exam score assigned successfully!");
	}

	public void printCourseStatistics() {
		Course course;
		String courseName;
		Result result;
		int numStudents;
		float score;
		float totalScorePercentage = 0;
		float courseworkScorePercentage = 0;
		float examScorePercentage = 0;

		// Check if any courses exist
		if (courses.isEmpty()) {
			System.out.println("There are no courses available. Please add a course first.");
			return;
		}

		this.printCourses();
		System.out.println("Enter the name of the course you'd like to print course statistics of:");
		courseName = getInputString(30);
		if (!this.courses.containsKey(courseName)) { // Course by that name does not exist
			System.out.printf("Sorry, course %s does not exist!\n", courseName);
			return;
		}
		course = this.courses.get(courseName);

		numStudents = course.getStudents().size();
		if (numStudents == 0) {
			System.out.println("No students registered for this course. Printing course statistics failed!");
			return;
		}

		for (HashMap.Entry<String, Student> student : course.getStudents().entrySet()) {
			result = student.getValue().getResults().get(courseName);
			score = result.calculateResultForComponent(courseworkMainComponentName);
			if (score < 0) {
				System.out.println("Scores have not been assigned yet. Please try again after assigning scores.");
				return;
			}
			courseworkScorePercentage = courseworkScorePercentage + score;
			score = result.calculateResultForComponent(examMainComponentName);
			if (score < 0) {
				System.out.println("Scores have not been assigned yet. Please try again after assigning scores.");
				return;
			}
			examScorePercentage = examScorePercentage + score;
		}

		examScorePercentage = examScorePercentage / numStudents;
		courseworkScorePercentage = courseworkScorePercentage / numStudents;
		totalScorePercentage = examScorePercentage + courseworkScorePercentage;

		System.out.println("|----------------------------------------------------------|");
		System.out.println("| Here are the overall statistics for this course          |");
		System.out.println("|----------------------------------------------------------|");
		System.out.println(String.format("| OVERALL SCORE            | %.2f", totalScorePercentage));
		System.out.println(String.format("| OVERALL EXAM SCORE       | %.2f", examScorePercentage));
		System.out.println(String.format("| OVERALL COURSEWORK SCORE | %.2f", courseworkScorePercentage));
		System.out.println("|----------------------------------------------------------|");
	}

	public void printStudentTranscript() {
		Student student;
		String studentName;

		// Check if any students exist
		if (students.isEmpty()) {
			System.out.println("There are no students available. Please add a student first.");
			return;
		}

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
		System.out.println(String.format("| TRANSCRIPT: %-42s |", studentName));
		System.out.println("|--------------------------------------------------------|");
		for (HashMap.Entry<String, Result> result : student.getResults().entrySet()) {
			System.out.println(String.format("| %-32s | %.2f ", result.getValue().calculateOverallResult()));
		}
		System.out.println("|--------------------------------------------------------|");
		System.out.println("| Score of -1 means:                                     |");
		System.out.println("| -> Either student has not been assigned score or;      |");
		System.out.println("| -> Course has not been assigned component weightages   |");
		System.out.println("|--------------------------------------------------------|");
	}

	public void exitApplication() {
		System.out.println("Exiting application...");
		scanner.close();
		System.exit(0);
	}

	public void printStudents() {
		System.out.println("|----------------------------------------------------------|");
		System.out.println("|                     Student List                         |");
		System.out.println("|----------------------------------------------------------|");
		for (HashMap.Entry<String, Student> entry : students.entrySet()) {
			System.out.println(String.format("| %-56s |", entry.getKey()));
		}
		System.out.println("|----------------------------------------------------------|");
	}

	public void printCourses() {
		System.out.println("|----------------------------------------------------------|");
		System.out.println("|                     Course List                          |");
		System.out.println("|----------------------------------------------------------|");
		for (HashMap.Entry<String, Course> entry : courses.entrySet()) {
			System.out.println(String.format("| %-56s |", entry.getKey()));
		}
		System.out.println("|----------------------------------------------------------|");
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

		while (!scanner.hasNextInt()) {
			System.out.println("Please enter an integer.");
			scanner.next();
		}
		input = scanner.nextInt();

		while ((input < lower) || (input > upper)) {
			System.out.printf("Your input must be between %d - %d. Please try again.\n", lower, upper);
			while (!scanner.hasNextInt()) {
				System.out.println("Please enter an integer.");
				scanner.next();
			}
			input = scanner.nextInt();
		}
		scanner.nextLine();
		return input;
	}

	public float getInputFloat(int lower, int upper) {
		float input;

		while (!scanner.hasNextFloat()) {
			System.out.println("Please enter a float.");
			scanner.next();
		}
		input = scanner.nextFloat();

		while ((input < lower) || (input > upper)) {
			System.out.printf("Your input must be between %d - %d. Please try again.\n", lower, upper);
			while (!scanner.hasNextFloat()) {
				System.out.println("Please enter a float.");
				scanner.next();
			}
			input = scanner.nextFloat();
		}
		scanner.nextLine();
		return input;
	}
}
