package students;

import java.io.Serializable;
import java.util.HashMap;

import assessments.Component;
import courses.Course;

public class Result implements Serializable {

	private static final long serialVersionUID = 9L;

	private Course course;
	private HashMap<String, ResultItem> resultItems;

	public Result(Course course) {
		this.course = course;
		this.resultItems = new HashMap<String, ResultItem>();
	}

	public void addResultItem(String name) {
		this.resultItems.put(name, new ResultItem(name));
	}

	public void addResultItem(String name, float value) {
		this.resultItems.put(name, new ResultItem(name, value));
	}

	public HashMap<String, ResultItem> getResultItems() {
		return this.resultItems;
	}

	public float calculateOverallResult() {
		float overallResult = 0;
		for (HashMap.Entry<String, ResultItem> resultItem : this.resultItems.entrySet()) {
			overallResult = overallResult + resultItem.getValue().calculateScore();
		}
		return overallResult;
	}

	public float calculateResultForComponent(String componentName) {
		if (!this.resultItems.containsKey(componentName)) {
			return -1;
		}
		return this.resultItems.get(componentName).calculateScore();
	}

}
