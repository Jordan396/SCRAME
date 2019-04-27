package assessments;

import java.io.Serializable;

public class Component implements Serializable{
	
	private static final long serialVersionUID = 10L;
	
	private String name;
	private int weightage;
	
	public Component(String name, int weightage) {
		this.name = name;
		this.weightage = weightage;
	}
	
	public int getWeightage() {
		return this.weightage;
	}
	
	public void setWeightage(int weightage) {
		this.weightage = weightage;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
