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
		int userInputLength;
		String userInput;
		Scanner scanner = new Scanner(System.in);

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
			System.out.println("| Please select a command below by entering its number:  |");
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
				System.out.println("Enter the name of the student you'd like to add:");
				
				userInput = scanner.nextLine();
				userInputLength = userInput.length();

				if (userInputLength > 30) {
					System.out.println("Sorry, student's name exceeded the maximum number of chars (30)!");
					break;
				}

				if (SCRAME.students.containsKey(userInput)) { // Student by that name already exists
					System.out.printf("Sorry, student %s already exists!\n", userInput);
					break;
				}

				SCRAME.students.put(userInput, new Student(userInput));
				System.out.println("Student added successfully!");
				break;
			case 2:
				break;
			case 3:
				break;
			case 4:
				break;
			case 5:
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

}
