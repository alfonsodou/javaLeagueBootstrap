package org.javahispano.javaleague.server.domain;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.javahispano.javaleague.shared.LeagueDTO;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * 
 * @author adou
 *
 */
@Entity
public class League {
	/**
	 * 
	 */
	private static final Logger log = Logger.getLogger(League.class.getName());
	
	public static Integer PUBLIC = 1, PRIVATE = 2;
	  
	@Id
	private Long id;
	
	private Long managerId;
	
	private String name;
	
	private String description;
	
	private List<Long> users;
	
	private List<Long> matchs;
	
	private Date creation;
	
	private Date updated;
	
	private Date startSignIn;
	
	private Date endSignIn;
	
	private String password;
	
	private Integer type;
	
	public League() {
		super();
		this.creation = new Date();
		this.updated = new Date();		
	}
	
	public League(LeagueDTO leagueDTO) {
		this.id = leagueDTO.getId();
		this.name = leagueDTO.getName();
		this.managerId = leagueDTO.getManagerId();
		this.creation = new Date();
		this.updated = new Date();
		this.description = leagueDTO.getDescription();
		this.startSignIn = leagueDTO.getStartSignIn();
		this.endSignIn = leagueDTO.getEndSignIn();
		this.password = leagueDTO.getPassword();
		this.type = leagueDTO.getType();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
	 * @return the users
	 */
	public List<Long> getUsers() {
		return users;
	}

	/**
	 * @param users the users to set
	 */
	public void setUsers(List<Long> users) {
		this.users = users;
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
	 * @return the matchs
	 */
	public List<Long> getMatchs() {
		return matchs;
	}

	/**
	 * @param matchs the matchs to set
	 */
	public void setMatchs(List<Long> matchs) {
		this.matchs = matchs;
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

	public boolean isPublic() {
		return (this.type == PUBLIC);
	}
	
	public LeagueDTO toDTO() {
		LeagueDTO leagueDTO = new LeagueDTO();
		
		leagueDTO.setId(this.id);
		leagueDTO.setCreation(this.creation);
		leagueDTO.setDescription(this.description);
		leagueDTO.setEndSignIn(this.endSignIn);
		leagueDTO.setManagerId(this.managerId);
		leagueDTO.setName(this.name);
		leagueDTO.setPassword(this.password);
		leagueDTO.setStartSignIn(this.startSignIn);
		leagueDTO.setType(this.type);
		leagueDTO.setUpdated(this.updated);
		
		return leagueDTO;
	}


}
