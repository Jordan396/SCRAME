package professors;

import java.io.Serializable;

public class Professor implements Serializable {

	private static final long serialVersionUID = 3L;
	
	private String name;
	
	public Professor(String name) {
		this.name = name;
	}
	
}
