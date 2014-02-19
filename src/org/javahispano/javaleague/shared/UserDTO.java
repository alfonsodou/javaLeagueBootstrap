/**
 * 
 */
package org.javahispano.javaleague.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UserDTO implements Serializable {

	private Long id;

	private String name;

	private String emailAddress;

	private String password;

	private String channelId;

	/**
	 * 
	 */
	private Long tacticId;

	public UserDTO() {

	}

	public UserDTO(Long id, String email, String name, Long tacticId) {
		this();
		this.setId(id);
		this.setEmailAddress(email);
		this.setName(name);
		this.setTactic(tacticId);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public Long getTactic() {
		return tacticId;
	}

	public void setTactic(Long tacticId) {
		this.tacticId = tacticId;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the tacticId
	 */
	public Long getTacticId() {
		return tacticId;
	}

	/**
	 * @param tacticId
	 *            the tacticId to set
	 */
	public void setTacticId(Long tacticId) {
		this.tacticId = tacticId;
	}

}
