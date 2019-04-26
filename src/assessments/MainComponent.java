package assessments;

import java.io.Serializable;
import java.util.HashMap;

public class MainComponent implements Serializable{

	private static final long serialVersionUID = 8L;
	
	private int overallWeightage;
	private HashMap<String, SubComponent> subcomponents = null;
	
	public MainComponent(int weightage) {
		this.overallWeightage = weightage;
	}
}
