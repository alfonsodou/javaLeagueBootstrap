/**
 * 
 */
package org.javahispano.javaleague.shared;

import java.io.Serializable;
import java.util.Date;

/**
 * @author adou
 *
 */
public class LeagueDTO implements Serializable {
	private Long id;
	private Long managerId;
	private String name;
	private String description;
	private Date startSignIn;
	private Date endSignIn;
	private Date creation;
	private Date updated;
	private String password;
	private Integer type;
	
	public static Integer PUBLIC = 1, PRIVATE = 2;
	
	public LeagueDTO() {
		
	}
	
	public LeagueDTO(Long id, String name, Long managerId, Date creation, Date updated) {
		this.id = id;
		this.name = name;
		this.managerId = managerId;
		this.creation = creation;
		this.updated = updated;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the managerId
	 */
	public Long getManagerId() {
		return managerId;
	}

	/**
	 * @param managerId the managerId to set
	 */
	public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}

	/**
	 * @return the creation
	 */
	public Date getCreation() {
		return creation;
	}

	/**
	 * @param creation the creation to set
	 */
	public void setCreation(Date creation) {
		this.creation = creation;
	}

	/**
	 * @return the updated
	 */
	public Date getUpdated() {
		return updated;
	}

	/**
	 * @param updated the updated to set
	 */
	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the startSignIn
	 */
	public Date getStartSignIn() {
		return startSignIn;
	}

	/**
	 * @param startSignIn the startSignIn to set
	 */
	public void setStartSignIn(Date startSignIn) {
		this.startSignIn = startSignIn;
	}

	/**
	 * @return the endSignIn
	 */
	public Date getEndSignIn() {
		return endSignIn;
	}

	/**
	 * @param endSignIn the endSignIn to set
	 */
	public void setEndSignIn(Date endSignIn) {
		this.endSignIn = endSignIn;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	
	
}
