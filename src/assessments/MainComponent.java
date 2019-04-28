package assessments;

import java.util.HashMap;

public class MainComponent extends Component{

	private static final long serialVersionUID = -8645502345727378718L;
	
	private HashMap<String, SubComponent> subcomponents;
	
	public MainComponent(String name, float weightage) {
		this.name = name;
		this.weightage = weightage;
	}
	
	public void setSubcomponentWeightage(String name, float weightage) {
		if (this.subcomponents == null) {
			this.subcomponents = new HashMap<String, SubComponent>();
		}
		this.subcomponents.put(name, new SubComponent(name, weightage));
	}
	
	public void clearSubcomponents() {
		if (this.subcomponents != null) {
			this.subcomponents.clear();
			this.subcomponents = null;
		}
	}
	
	public boolean hasSubcomponents() {
		if (this.subcomponents == null) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public HashMap<String, SubComponent> getSubcomponents(){
		return this.subcomponents;
	}
}
