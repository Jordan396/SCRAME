package students;

import java.io.Serializable;
import java.util.HashMap;

import courses.Component;
import courses.Course;

/**
 * Result class describing the results obtained for a particular course by a
 * particular student.
 * 
 * @author jordan396
 */
public class Result implements Serializable {

	private static final long serialVersionUID = 6920288657302284965L;
	private Course course;
	private HashMap<String, ResultItem> resultItems;

	/**
	 * Constructor for Result object.
	 * 
	 * Each Result object is designed to correspond to a specific student and course
	 * combination. A Result object holds several ResultItems, which values can be
	 * summed to obtain the overall result value obtained by the student.
	 * 
	 * @param course Course to be assigned to the Result
	 * 
	 * @return A Result object
	 */
	public Result(Course course) {
		this.course = course;
		this.resultItems = new HashMap<String, ResultItem>();
	}

	/**
	 * This method adds a ResultItem to the resultItems HashMap.
	 * 
	 * @param name Name of the ResultItem
	 */
	public void addResultItem(String name) {
		this.resultItems.put(name, new ResultItem(name));
	}

	/**
	 * This method adds a ResultItem to the resultItems HashMap.
	 * 
	 * @param name  Name of the ResultItem
	 * @param value Overall score of the ResultItem inclusive of its child
	 *              ResultItems, if any.
	 */
	public void addResultItem(String name, float value) {
		this.resultItems.put(name, new ResultItem(name, value));
	}

	/**
	 * Getter method for this Result's resultItems.
	 * 
	 * @return HashMap resultItems
	 */
	public HashMap<String, ResultItem> getResultItems() {
		return this.resultItems;
	}

	/**
	 * This method calculates the overall result obtained by student for course.
	 * 
	 * Calculation is done by traversing the set of resultItems and summing its
	 * values.
	 * 
	 * @return Overall result obtained by student for course.
	 */
	public float calculateOverallResult() {
		float overallResult = 0;
		for (HashMap.Entry<String, ResultItem> resultItem : this.resultItems.entrySet()) {
			overallResult = overallResult + resultItem.getValue().calculateScore();
		}
		return overallResult;
	}

	/**
	 * This method calculates the result obtained by student for a specified
	 * component of the course.
	 * 
	 * @param componentName Name of the component
	 * 
	 * @return Result obtained by student for a course component.
	 */
	public float calculateResultForComponent(String componentName) {
		if (!this.resultItems.containsKey(componentName)) {
			return -1;
		}
		return this.resultItems.get(componentName).calculateScore();
	}

}
