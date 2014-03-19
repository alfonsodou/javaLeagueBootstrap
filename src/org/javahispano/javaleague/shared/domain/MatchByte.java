/**
 * 
 */
package org.javahispano.javaleague.shared.domain;

import java.io.Serializable;
import java.util.logging.Logger;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * @author adou
 *
 */
@Entity
public class MatchByte implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(MatchByte.class.getName());	
	
	@Id
	private Long id;
	
	private byte[] jvc;
	
	private byte[] bin;
	
	private long[] timeLocal;
	
	private long[] timeVisita;
	
	public MatchByte() {
		super();
		
		jvc = null;
		bin = null;
		timeLocal = null;
		timeVisita = null;
	}

	/**
	 * @return the jvc
	 */
	public byte[] getJvc() {
		return jvc;
	}

	/**
	 * @param jvc the jvc to set
	 */
	public void setJvc(byte[] jvc) {
		this.jvc = jvc;
	}

	/**
	 * @return the bin
	 */
	public byte[] getBin() {
		return bin;
	}

	/**
	 * @param bin the bin to set
	 */
	public void setBin(byte[] bin) {
		this.bin = bin;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the timeLocal
	 */
	public long[] getTimeLocal() {
		return timeLocal;
	}

	/**
	 * @param timeLocal the timeLocal to set
	 */
	public void setTimeLocal(long[] timeLocal) {
		this.timeLocal = timeLocal;
	}

	/**
	 * @return the timeVisita
	 */
	public long[] getTimeVisita() {
		return timeVisita;
	}

	/**
	 * @param timeVisita the timeVisita to set
	 */
	public void setTimeVisita(long[] timeVisita) {
		this.timeVisita = timeVisita;
	}
	
	

}
