package assessments;

import java.io.Serializable;

public class Component implements Serializable{
	
	private static final long serialVersionUID = 10L;
	
	private String name;
	private float weightage;
	
	public Component(String name, float weightage) {
		this.name = name;
		this.weightage = weightage;
	}
	
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
