/**
 * 
 */
package org.javahispano.javaleague.client.resources;


import com.github.gwtbootstrap.client.ui.config.Configurator;
import com.github.gwtbootstrap.client.ui.resources.Resources;
import com.google.gwt.core.client.GWT;

/**
 * @author adou
 *
 */
public class ConfiguratorJavaLeague implements Configurator {
	public Resources getResources() {
		return GWT.create(ResourcesJavaLeague.class);
	}

	public boolean hasResponsiveDesign() {
		return true;
	}
}