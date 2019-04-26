package students;

import java.io.Serializable;

public class Student implements Serializable {

	private static final long serialVersionUID = 4L;

	private String name;

	public Student(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
}
