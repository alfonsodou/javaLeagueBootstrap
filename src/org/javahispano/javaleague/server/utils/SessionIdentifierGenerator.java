/**
 * 
 */
package org.javahispano.javaleague.server.utils;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * @author adou
 * 
 */
public final class SessionIdentifierGenerator {
	private SecureRandom random = new SecureRandom();

	public String nextSessionId() {
		return new BigInteger(130, random).toString(32);
	}
}
