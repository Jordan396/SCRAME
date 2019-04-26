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
import students.Student;

public class StudentCourseRegistrationAndMarkEntryApplication implements Serializable {

	private static final long serialVersionUID = 1L;

	// Variables to store objects in current instance of SCRAME
	HashMap<String, Student> students;
	HashMap<String, Course> courses;

	public static void main(String[] args) {
		// Variables for saving/loading SCRAME
		String filename = "scrame.ser";
		StudentCourseRegistrationAndMarkEntryApplication SCRAME = null;

		// Variables to get user input
		int userCommand;
		Scanner scanner = new Scanner(System.in);

		// Local variables
		String studentName;
		int studentNameLength;
		String courseName;
		int courseNameLength;
		String professorName;
		int professorNameLength;
		int numTutorialGroups;
		int numVacanciesPerTutorialGroup;
		int numLaboratoryGroups;
		int numVacanciesPerLaboratoryGroup;
		int tutorialGroupId;
		int laboratoryGroupId;

		System.out.println("Checking for existing applications...");

		try {
			// Reading the object from a file
			FileInputStream file = new FileInputStream(filename);
			ObjectInputStream in = new ObjectInputStream(file);

			// Method for deserialization of object
			SCRAME = (StudentCourseRegistrationAndMarkEntryApplication) in.readObject();

			in.close();
			file.close();

			System.out.println("Application found! Loading application...");
		}

		catch (IOException ex) {
			System.out.println("No application found! Creating a new application...");
			SCRAME = new StudentCourseRegistrationAndMarkEntryApplication();
		} catch (ClassNotFoundException ex) {
			System.out.println("ClassNotFoundException caught.");
		}

		System.out.println("Welcome to SCRAME!");

		while (true) {
			System.out.println("|--------------------------------------------------------|");
			System.out.println("| STUDENT COURSE REGISTRATION AND MARK ENTRY APPLICATION |");
			System.out.println("|--------------------------------------------------------|");
			System.out.println("| Please select a command below                          |");
			System.out.println("| 1. Add a student                                       |");
			System.out.println("| 2. Add a course                                        |");
			System.out.println("| 3. Register student for course                         |");
			System.out.println("| 4. Check available slots for class                     |");
			System.out.println("| 5. Print student list                                  |");
			System.out.println("| 6. Enter course assessment components weightage        |");
			System.out.println("| 7. Enter coursework marks                              |");
			System.out.println("| 8. Enter exam marks                                    |");
			System.out.println("| 9. Print course statistics                             |");
			System.out.println("| 10. Print student transcript                           |");
			System.out.println("| 11. Exit SCRAME                                        |");
			System.out.println("|--------------------------------------------------------|");

			userCommand = scanner.nextInt();
			scanner.nextLine();

			switch (userCommand) {
			case 1:
				// Add student
				System.out.println("Enter the name of the student you'd like to add:");
				studentName = scanner.nextLine();
				studentNameLength = studentName.length();
				if (studentNameLength > 30) {
					System.out.println("Sorry, student's name exceeded the maximum number of chars (30)!");
					break;
				}
				if (SCRAME.students.containsKey(studentName)) { // Student by that name already exists
					System.out.printf("Sorry, student %s already exists!\n", studentName);
					break;
				}
				SCRAME.students.put(studentName, new Student(studentName));
				System.out.println("Student added successfully!");
				break;
			case 2:
				// Add course
				System.out.println("Enter the name of the course you'd like to add:");
				courseName = scanner.nextLine();
				courseNameLength = courseName.length();
				if (courseNameLength > 30) {
					System.out.println("Sorry, course's name exceeded the maximum number of chars (30)!");
					break;
				}
				if (SCRAME.courses.containsKey(courseName)) { // Student by that name already exists
					System.out.printf("Sorry, course %s already exists!\n", courseName);
					break;
				}

				// Get professor for course
				System.out.println("Enter the name of the professor coordinating this course:");
				professorName = scanner.nextLine();
				professorNameLength = professorName.length();
				if (professorNameLength > 30) {
					System.out.println("Sorry, course's name exceeded the maximum number of chars (30)!");
					break;
				}

				// Get tutorials for course
				numTutorialGroups = 0;
				numVacanciesPerTutorialGroup = 0;
				System.out.println("How many tutorial groups does this course have?");
				numTutorialGroups = scanner.nextInt();
				scanner.nextLine();
				if (numTutorialGroups != 0) {
					System.out.println("How many vacancies are there in each tutorial group?");
					numVacanciesPerTutorialGroup = scanner.nextInt();
					scanner.nextLine();
				}

				// Get laboratory for course
				numLaboratoryGroups = 0;
				numVacanciesPerLaboratoryGroup = 0;
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

				SCRAME.courses.put(courseName, new Course(courseName, professorName, numTutorialGroups,
						numVacanciesPerTutorialGroup, numLaboratoryGroups, numVacanciesPerLaboratoryGroup));
				break;
			case 3:
				// Register student for course
				SCRAME.printStudents();
				System.out.println("Enter the name of the student you'd like to register for course:");
				studentName = scanner.nextLine();
				if (!SCRAME.students.containsKey(studentName)) { // Student by that name does not exist
					System.out.printf("Sorry, student %s does not exist!\n", studentName);
					break;
				}
				SCRAME.printCourses();
				System.out.println("Enter the name of the course you'd like to register student to:");
				courseName = scanner.nextLine();
				if (!SCRAME.courses.containsKey(courseName)) { // Course by that name does not exist
					System.out.printf("Sorry, course %s does not exist!\n", courseName);
					break;
				}
				if (SCRAME.courses.get(courseName).hasTutorial()) {
					while (true) {
						SCRAME.courses.get(courseName).printTutorials();
						System.out.println("Enter a tutorial group number to register student under:\n");
						tutorialGroupId = scanner.nextInt();
						scanner.nextLine();
						if (tutorialGroupId > (SCRAME.courses.get(courseName).getNumTutorialGroups())) {
							System.out.println("That tutorial group number does not exist!");
						}
						if (SCRAME.courses.get(courseName).tutorials.get(tutorialGroupId)
								.getAvailableVacancies() == 0) {
							System.out.println("That tutorial group is full!");
						}
						SCRAME.courses.get(courseName).tutorials.get(tutorialGroupId)
								.registerStudent(SCRAME.students.get(studentName));
						break;
					}
				}
				if (SCRAME.courses.get(courseName).hasLaboratory()) {
					while (true) {
						SCRAME.courses.get(courseName).printLaboratories();
						System.out.println("Enter a laboratory group number to register student under:\n");
						laboratoryGroupId = scanner.nextInt();
						scanner.nextLine();
						if (laboratoryGroupId > (SCRAME.courses.get(courseName).getNumLaboratoryGroups())) {
							System.out.println("That laboratory group number does not exist!");
						}
						if (SCRAME.courses.get(courseName).laboratories.get(laboratoryGroupId)
								.getAvailableVacancies() == 0) {
							System.out.println("That laboratory group is full!");
						}
						SCRAME.courses.get(courseName).laboratories.get(laboratoryGroupId)
								.registerStudent(SCRAME.students.get(studentName));
						break;
					}
				}
				System.out.println("Student registered for course successfully!");
				break;
			case 4:
				// Check available slots for class
				System.out.println("Enter the name of the course you'd like to check vacancies for:");
				courseName = scanner.nextLine();
				if (SCRAME.courses.get(courseName).hasTutorial()) {
					SCRAME.courses.get(courseName).printTutorials();
				}
				if (SCRAME.courses.get(courseName).hasLaboratory()) {
					SCRAME.courses.get(courseName).printLaboratories();
				}
				break;
			case 5:
				SCRAME.printStudents();
				break;
			case 6:
				break;
			case 7:
				break;
			case 8:
				break;
			case 9:
				break;
			case 10:
				break;
			case 11:
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
				scanner.close();
				return;
			}

		}

	}

	public StudentCourseRegistrationAndMarkEntryApplication() {
		this.students = new HashMap<String, Student>();
		this.courses = new HashMap<String, Course>();
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
