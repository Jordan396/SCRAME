package assessments;

import java.io.Serializable;

public abstract class Component implements Serializable {

	private static final long serialVersionUID = -607043353018049439L;

	String name;
	float weightage;

	public float getWeightage() {
		return this.weightage;
	}

	public void setWeightage(float weightage) {
		this.weightage = weightage;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
