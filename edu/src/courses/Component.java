package courses;

import java.io.Serializable;

/**
 * The abstract class of course component provides methods for getting and
 * setting weightage etc.
 * 
 * @author jordan396
 */
public abstract class Component implements Serializable {

	private static final long serialVersionUID = -607043353018049439L;

	String name;
	float weightage;

	/**
	 * Constructor for Component object.
	 * 
	 * @param name      Name of the component (e.g. Exam)
	 * @param weightage Weightage relative to the overall course.
	 */
	public Component(String name, float weightage) {
		this.name = name;
		this.weightage = weightage;
	}

	/**
	 * Getter method for this component's weightage.
	 * 
	 * @return Weightage of this component
	 */
	public float getWeightage() {
		return this.weightage;
	}

	/**
	 * Getter method for this component's name.
	 * 
	 * @return Name of this component
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Setter method for this component's name.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Setter method for this component's weightage.
	 */
	public void setWeightage(float weightage) {
		this.weightage = weightage;
	}

}
