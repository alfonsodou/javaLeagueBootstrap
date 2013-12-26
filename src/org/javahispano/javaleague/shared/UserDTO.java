/**
 * 
 */
package org.javahispano.javaleague.shared;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Pattern;

@SuppressWarnings("serial")
public class UserDTO implements Serializable {

	private String id;

	@NotNull
	@Size(min = 4, message = "El nombre debe tener una longitud de 4 caracteres como mínimo\n")
	private String name;

	@NotNull(message = "La dirección de correo no puede estar vacía\n")
	private String emailAddress;

	@NotNull
	@Size(min = 4, message = "La contraseña debe tener una longitud de 4 caracteres como mínimo\n")
	@Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message = "Debes introducir una dirección de correo válida\n")
	private String password;

	private String channelId;

	private String uniqueId;
	/**
	 * 
	 */
	private String tacticId;

	public UserDTO() {

	}

	public UserDTO(String email, String name, String uniqueId, String tacticId) {
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
	public String getTacticId() {
		return tacticId;
	}

	/**
	 * @param tacticId
	 *            the tacticId to set
	 */
	public void setTacticId(String tacticId) {
		this.tacticId = tacticId;
	}

}
