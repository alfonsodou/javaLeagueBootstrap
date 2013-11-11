package org.javahispano.javaleague.shared;

import java.io.Serializable;

public class MatchSummaryDTO implements Serializable {

	private String id;
	private int localGoals;
	private int foreignGoals;
	private float localPosession;
	private float foreignPosession;
	
	public MatchSummaryDTO() {
		
	}
}
