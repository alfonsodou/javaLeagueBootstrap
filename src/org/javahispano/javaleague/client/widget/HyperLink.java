/**
 * 
 */
package org.javahispano.javaleague.client.widget;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;

/**
 * @author adou
 * 
 */
public class HyperLink extends Hyperlink {

	public HyperLink() {
	}

	public void setResource(ImageResource imageResource) {
		Image img = new Image(imageResource);
		img.setStyleName("navbarimg");
		DOM.insertBefore(getElement(), img.getElement(),
				DOM.getFirstChild(getElement()));
	}
}
