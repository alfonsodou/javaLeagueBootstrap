/**
 * 
 */
package org.javahispano.javaleague.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UserDTO implements Serializable {

	private String id;
	private String name;
	private String emailAddress;
	private String channelId;
	private String uniqueId;
	/**
	 * 
	 */
	private String tacticId;

	public UserDTO() {

	}

	public UserDTO(String email, String name, String uniqueId,
			String tacticId) {
		this();
		this.setEmailAddress(email);
		this.setName(name);
		this.setUniqueId(uniqueId);
		this.setTactic(tacticId);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getTactic() {
		return tacticId;
	}

	public void setTactic(String tacticId) {
		this.tacticId = tacticId;
	}

}