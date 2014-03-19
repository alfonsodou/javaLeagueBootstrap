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
public class MatchByteBin implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(MatchByteBin.class.getName());	
	
	@Id
	private Long id;
	
	private byte[] bin;
	
	public MatchByteBin() {
		super();
		
		bin = null;
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


}
