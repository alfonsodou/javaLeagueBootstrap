package org.javahispano.javaleague.shared;

import java.io.Serializable;

/**
 * 
 * @author adou
 * 
 */
public class TacticClassDTO implements Serializable {

	private String id;
	private String packageName;
	private boolean isTacticClass;
	private String className;

	public TacticClassDTO() {

	}

	public TacticClassDTO(String id, String packageName, boolean isTacticClass,
			String className) {
		this();
		this.setId(id);

		this.setPackageName(packageName);
		this.setTacticClass(isTacticClass);
		this.setClassName(className);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public boolean isTacticClass() {
		return isTacticClass;
	}

	public void setTacticClass(boolean isTacticClass) {
		this.isTacticClass = isTacticClass;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

}
