package students;

import java.io.Serializable;
import java.util.HashMap;

import assessments.Component;
import courses.Course;

public class Result implements Serializable {

	private static final long serialVersionUID = 9L;

	private Course course;
	private HashMap<String, Float> rawScores;

	public Result(Course course) {
		this.course = course;
		this.rawScores = new HashMap<String, Float>();
	}

	public HashMap<String, Float> getRawScores() {
		return this.rawScores;
	}

	public boolean enterScore(String componentName, float score) {
		if ((score < 0) || (score > 100)) {
			return false;
		} else {
			if (rawScores.containsKey(componentName)) {
				rawScores.replace(componentName, score);
			} else {
				rawScores.put(componentName, score);
			}
			return true;
		}
	}

	public float calculateOverallTotalScore() {
		float overallScore = 0;

		if (this.rawScores.isEmpty()) {
			return -1;
		}

		for (HashMap.Entry<String, Component> entry : this.course.getComponents().entrySet()) {
			if (this.rawScores.containsKey(entry.getKey())) {
				overallScore = overallScore + ((entry.getValue().getWeightage() * this.rawScores.get(entry.getKey())));
			} else {
				return -1;
			}
		}
		return overallScore;
	}

	public float calculateOverallExamScore(String examComponentName) {

		if (this.rawScores.isEmpty()) {
			return -1;
		}

		if (this.rawScores.containsKey(examComponentName)) {
			return (this.rawScores.get(examComponentName)
					* this.course.getComponents().get(examComponentName).getWeightage());
		} else {
			return -1;
		}
	}

	public float calculateOverallCourseworkScore(String examComponentName) {
		float overallCourseworkScore = 0;
		String componentName;

		if (this.rawScores.isEmpty()) {
			return -1;
		}

		for (HashMap.Entry<String, Component> entry : this.course.getComponents().entrySet()) {
			componentName = entry.getValue().getName();
			if (this.rawScores.containsKey(componentName)){
				if (!componentName.equals(examComponentName)) {
					overallCourseworkScore = overallCourseworkScore
							+ this.rawScores.get(componentName) * entry.getValue().getWeightage();
				}
			} else {
				return -1;
			}
		}
		return overallCourseworkScore;
	}

}
