package students;

import java.io.Serializable;
import java.util.HashMap;

public class ResultItem implements Serializable {

	private static final long serialVersionUID = -6823947969289844658L;

	private HashMap<String, ResultItem> resultItems = null;
	private String name;
	private float value;
	private boolean hasLatestValue = false;

	public ResultItem(String name) {
		this.name = name;
		this.hasLatestValue = false;
	}

	public ResultItem(String name, float value) {
		this.name = name;
		this.value = value;
		this.hasLatestValue = true;
	}

	public void setValue(float value) {
		this.value = value;
		this.hasLatestValue = true;
	}

	public float getValue() {
		return this.value;
	}

	public HashMap<String, ResultItem> getResultItems() {
		return this.resultItems;
	}
	
	public void addResultItem(String name) {
		if (this.resultItems == null) {
			this.resultItems = new HashMap<String, ResultItem>();
		}
		this.resultItems.put(name, new ResultItem(name));
	}
	
	public void addResultItem(String name, float value) {
		if (this.resultItems == null) {
			this.resultItems = new HashMap<String, ResultItem>();
		}
		this.resultItems.put(name, new ResultItem(name, value));
	}

	public float calculateScore() {
		float totalScore = 0;
		
		if (this.hasLatestValue) {
			return this.value;
		}
		
		if ((resultItems == null) && (!this.hasLatestValue)) {
			return totalScore;
		}
		
		if (resultItems.isEmpty() && (!this.hasLatestValue)) {
			return totalScore;
		}

		for (HashMap.Entry<String, ResultItem> resultItem : resultItems.entrySet()) {
			if (resultItem.getValue().hasLatestValue) {
				totalScore = totalScore + resultItem.getValue().getValue();
			} else {
				if (resultItem.getValue().getResultItems() != null) {
					totalScore = totalScore + resultItem.getValue().calculateScore();
				} else {
				}
			}
		}

		this.hasLatestValue = true;
		this.value = totalScore;
		return totalScore;
	}

}
