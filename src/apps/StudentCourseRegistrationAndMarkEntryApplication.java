package apps;

import java.io.Serializable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Scanner;

import courses.Course;
import professors.Professor;
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
			System.out.println("| 7. Enter coursework marks                                |");
			System.out.println("| 8. Enter exam marks                                      |");
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
				break;
			case 8:
				break;
			case 9:
				break;
			case 10:
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
		studentName = scanner.nextLine();
		if (studentName.length() > 30) {
			System.out.println("Sorry, student's name exceeded the maximum number of chars (30)!");
			return;
		}
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
		courseName = scanner.nextLine();
		if (courseName.length() > 30) {
			System.out.println("Sorry, course's name exceeded the maximum number of chars (30)!");
			return;
		}
		if (this.courses.containsKey(courseName)) { // Student by that name already exists
			System.out.printf("Sorry, course %s already exists!\n", courseName);
			return;
		}

		// Get professor for course
		System.out.println("Enter the name of the professor coordinating this course:");
		professorName = scanner.nextLine();
		if (professorName.length() > 30) {
			System.out.println("Sorry, course's name exceeded the maximum number of chars (30)!");
			return;
		}

		// Get tutorial for course
		System.out.println("How many tutorial groups does this course have?");
		numTutorialGroups = scanner.nextInt();
		scanner.nextLine();
		if (numTutorialGroups != 0) {
			System.out.println("How many vacancies are there in each tutorial group?");
			numVacanciesPerTutorialGroup = scanner.nextInt();
			scanner.nextLine();
		}

		// Get laboratory for course
		if (numTutorialGroups != 0) {
			System.out.println("How many laboratory groups does this course have?");
			numLaboratoryGroups = scanner.nextInt();
			scanner.nextLine();
			if (numLaboratoryGroups != 0) {
				System.out.println("How many vacancies are there in each laboratory group?");
				numVacanciesPerLaboratoryGroup = scanner.nextInt();
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

		// Print list of students for user's reference
		this.printStudents();
		System.out.println("Enter the name of the student you'd like to register for course:");
		studentName = scanner.nextLine();
		if (!this.students.containsKey(studentName)) { // Student by that name does not exist
			System.out.printf("Sorry, student %s does not exist!\n", studentName);
			return;
		}

		// Print list of courses for user's reference
		this.printCourses();
		System.out.println("Enter the name of the course you'd like to register student to:");
		courseName = scanner.nextLine();
		if (!this.courses.containsKey(courseName)) { // Course by that name does not exist
			System.out.printf("Sorry, course %s does not exist!\n", courseName);
			return;
		}

		// Check if course has vacancies
		if (!this.courses.get(courseName).hasVacancies()) {
			System.out.println("Sorry, this course has no more vacancies.");
			return;
		}
		// Check if student is already registered
		if (this.students.get(studentName).getRegisteredCourses().containsKey(courseName)) {
			System.out.println("Sorry, student is already registered under this course.");
			return;
		}
		
		// Assign student to course and vice versa
		this.courses.get(courseName).getStudents().put(studentName, this.students.get(studentName));
		this.students.get(studentName).getRegisteredCourses().put(courseName, this.courses.get(courseName));

		// Assign student to lecture group
		this.courses.get(courseName).getLecture().registerStudent(this.students.get(studentName));

		// Assign student to tutorial group
		if (this.courses.get(courseName).hasTutorial()) {
			while (true) {
				this.courses.get(courseName).printTutorialVacancies();
				System.out.println("Enter a tutorial group number to register student under:\n");
				tutorialGroupId = scanner.nextInt();
				scanner.nextLine();
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
				laboratoryGroupId = scanner.nextInt();
				scanner.nextLine();
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
		courseName = scanner.nextLine();
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
		courseName = scanner.nextLine();
		if (!this.courses.containsKey(courseName)) { // Course by that name does not exist
			System.out.printf("Sorry, course %s does not exist!\n", courseName);
			return;
		}

		System.out.printf("Main lecture group:");
		this.courses.get(courseName).getLecture().printRegisteredStudents();

		if (this.courses.get(courseName).hasTutorial()) {
			this.courses.get(courseName).printTutorialVacancies();
			System.out.printf("Enter a tutorial group to print student list from:");
			tutorialGroupId = scanner.nextInt();
			scanner.nextLine();
			if (tutorialGroupId > (this.courses.get(courseName).getNumTutorialGroups())) {
				System.out.println("That tutorial group number does not exist.");
				return;
			}
			this.courses.get(courseName).getTutorials().get(tutorialGroupId).printRegisteredStudents();
		}

		if (this.courses.get(courseName).hasLaboratory()) {
			this.courses.get(courseName).printLaboratoryVacancies();
			System.out.printf("Enter a laboratory group to print student list from:");
			laboratoryGroupId = scanner.nextInt();
			scanner.nextLine();
			if (laboratoryGroupId > (this.courses.get(courseName).getNumLaboratoryGroups())) {
				System.out.println("That laboratory group number does not exist.");
				return;
			}
			this.courses.get(courseName).getLaboratories().get(laboratoryGroupId).printRegisteredStudents();
		}
	}
	
	public void enterCourseComponentsWeightage() {
		String courseName;
		int examWeightage;
		int courseworkWeightage;
		
		System.out.println("Enter the name of the course you'd like to enter components weightage to:");
		courseName = scanner.nextLine();
		if (!this.courses.containsKey(courseName)) { // Course by that name does not exist
			System.out.printf("Sorry, course %s does not exist!\n", courseName);
			return;
		}
		
		System.out.println("Enter the weightage for exam:");
		examWeightage = scanner.nextInt();
		scanner.nextLine();
		while ((examWeightage < 0) || (examWeightage > 100)) {
			System.out.println("Exam weightage must fall between 0 - 100. Please try again.");
			examWeightage = scanner.nextInt();
			scanner.nextLine();
		}
		
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
}
