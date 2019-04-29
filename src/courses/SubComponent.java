package courses;

/**
 * The implementation class of Component for subcomponents within a main component.
 * 
 * @author jordan396
 */
public class SubComponent extends Component {

	private static final long serialVersionUID = 2228645651751652606L;
	
	/**
	 * Constructor for SubComponent object.
	 * 
	 * @param name      Name of the component (e.g. Exam)
	 * @param weightage Weightage relative to the overall course.
	 */
	public SubComponent(String name, float weightage) {
		super(name, weightage);
	}
}
