package courses;

import java.util.HashMap;

/**
 * The implementation class of Component for main course components.
 * 
 * @author jordan396
 */
public class MainComponent extends Component {

	private static final long serialVersionUID = -8645502345727378718L;

	private HashMap<String, SubComponent> subcomponents;

	/**
	 * Constructor for MainComponent object.
	 * 
	 * @param name      Name of the component (e.g. Exam)
	 * @param weightage Weightage relative to the overall course.
	 */
	public MainComponent(String name, float weightage) {
		super(name, weightage);
	}

	/**
	 * This method sets the weightage for a subcomponent under this main component.
	 * 
	 * If the subcomponent does not exists, a new subcomponent is created.
	 * 
	 * @param name      Name of the subcomponent (e.g. Midterms)
	 * @param weightage Weightage relative to the overall course.
	 */
	public void setSubcomponentWeightage(String name, float weightage) {
		if (this.subcomponents == null) {
			this.subcomponents = new HashMap<String, SubComponent>();
		}
		this.subcomponents.put(name, new SubComponent(name, weightage));
	}

	/**
	 * This method clears the HashMap subcomponents.
	 */
	public void clearSubcomponents() {
		if (this.subcomponents != null) {
			this.subcomponents.clear();
			this.subcomponents = null;
		}
	}

	/**
	 * This method checks if this MainComponent contains SubComponents.
	 * 
	 * @return true if MainComponent contains SubComponents; false otherwise.
	 */
	public boolean hasSubcomponents() {
		if (this.subcomponents == null) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Getter method for this component's subcomponents.
	 * 
	 * @return Subcomponents for this component
	 */
	public HashMap<String, SubComponent> getSubcomponents() {
		return this.subcomponents;
	}
}
