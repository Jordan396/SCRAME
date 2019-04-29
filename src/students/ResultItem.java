package students;

import java.io.Serializable;
import java.util.HashMap;

/**
 * ResultItem class describing the results obtained for a particular course
 * component for a particular course by a particular student.
 * 
 * @author jordan396
 */
public class ResultItem implements Serializable {

	private static final long serialVersionUID = -6823947969289844658L;
	private HashMap<String, ResultItem> resultItems = null;
	private String name;
	private float value;
	private boolean hasLatestValue = false;

	/**
	 * Constructor for ResultItem object.
	 * 
	 * @param name Name of the resultItem
	 */
	public ResultItem(String name) {
		this.name = name;
		this.hasLatestValue = false;
	}

	/**
	 * Constructor for ResultItem object.
	 * 
	 * @param name  Name of the resultItem
	 * @param value Overall score of this ResultItem inclusive of its child
	 *              ResultItems, if any.
	 */
	public ResultItem(String name, float value) {
		this.name = name;
		this.value = value;
		this.hasLatestValue = true;
	}

	/**
	 * Getter method for this ResultItem's value.
	 * 
	 * @return value
	 */
	public float getValue() {
		return this.value;
	}

	/**
	 * Getter method for this ResultItem's child resultItems.
	 * 
	 * @return HashMap resultItems
	 */
	public HashMap<String, ResultItem> getResultItems() {
		return this.resultItems;
	}

	/**
	 * This method sets the value of the current instance of resultItem.
	 * 
	 * @param value Overall score of this ResultItem inclusive of its child
	 *              ResultItems, if any.
	 */
	public void setValue(float value) {
		this.value = value;
		this.hasLatestValue = true;
	}

	/**
	 * This method adds a ResultItem to the current instance of ResultItem's HashMap
	 * resultItems.
	 * 
	 * @param name Name of the ResultItem
	 */
	public void addResultItem(String name) {
		if (this.resultItems == null) {
			this.resultItems = new HashMap<String, ResultItem>();
		}
		this.resultItems.put(name, new ResultItem(name));
	}

	/**
	 * This method adds a ResultItem to the current instance of ResultItem's HashMap
	 * resultItems.
	 * 
	 * @param name  Name of the ResultItem
	 * @param value Overall score of this ResultItem inclusive of its child
	 *              ResultItems, if any.
	 */
	public void addResultItem(String name, float value) {
		if (this.resultItems == null) {
			this.resultItems = new HashMap<String, ResultItem>();
		}
		this.resultItems.put(name, new ResultItem(name, value));
	}

	/**
	 * This method calculates the score of the current instance of ResultItem by
	 * recursively calculating the score of all resultItems it contains, summing
	 * them up and returning that sum.
	 * 
	 * @return Overall score of this ResultItem inclusive of its child ResultItems,
	 *         if any.
	 */
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
