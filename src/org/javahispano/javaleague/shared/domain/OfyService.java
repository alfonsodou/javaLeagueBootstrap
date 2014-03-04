/**
 * 
 */
package org.javahispano.javaleague.shared.domain;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

/**
 * @author adou
 *
 */
public class OfyService {
    static {
        factory().register(CalendarDate.class);
        factory().register(FrameWork.class);
        factory().register(League.class);
        factory().register(Match.class);
        factory().register(PartidoStore.class);
        factory().register(TacticUser.class);
        factory().register(User.class);
     }

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }
}
