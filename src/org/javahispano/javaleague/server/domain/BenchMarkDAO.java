/**
 * 
 */
package org.javahispano.javaleague.server.domain;

import static org.javahispano.javaleague.server.domain.OfyService.ofy;

import java.util.List;

import org.javahispano.javaleague.shared.domain.BenchMark;

/**
 * @author adou
 *
 */
public class BenchMarkDAO {
	public BenchMarkDAO() {
		super();
	}
	
	public BenchMark save(BenchMark benchMark) {
		ofy().save().entity(benchMark).now();
		return benchMark;
	}
	
	public BenchMark findById(Long id) {
		return ofy().load().type(BenchMark.class).id(id).now();
	}

	public List<BenchMark> getAllBenchMark() {
		return ofy().load().type(BenchMark.class).list();
	}
}
