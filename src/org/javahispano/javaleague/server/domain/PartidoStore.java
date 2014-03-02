package org.javahispano.javaleague.server.domain;

import java.util.Date;

import com.google.appengine.api.users.User;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class PartidoStore {

	@Id
	private Long id;
	private User owner;
	private int golesLocal;
	private int golesVisitante;
	private Date creationTime;
	private byte[] partido;
	
	public PartidoStore() {
		
	}
	
	public PartidoStore(User owner, int local, int visitante, Date creation, byte[] p) {
		this.owner = owner;
		this.golesLocal = local;
		this.golesVisitante = visitante;
		this.creationTime = creation;
		this.partido = p;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public int getGolesLocal() {
		return golesLocal;
	}

	public void setGolesLocal(int golesLocal) {
		this.golesLocal = golesLocal;
	}

	public int getGolesVisitante() {
		return golesVisitante;
	}

	public void setGolesVisitante(int golesVisitante) {
		this.golesVisitante = golesVisitante;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public byte[] getPartido() {
		return partido;
	}

	public void setPartido(byte[] partido) {
		this.partido = partido;
	}
	
	
}
