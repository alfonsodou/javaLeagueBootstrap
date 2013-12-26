/**
 * 
 */
package org.javahispano.javaleague.client.validation;

/**
 * @author adou
 *
 */
/**
 * Custom validation messages. Interface to represent the constants contained in
 * resource bundle:
 * 'com/google/gwt/sample/validation/client/ValidationMessages.properties'
 * TODO(nchalko) move this to the root package so client and server can share
 * the same properties files.
 */
public interface ValidationMessages extends
		com.google.gwt.i18n.client.ConstantsWithLookup {

	/**
	 * Translated "Name must be at least {size} characters long.".
	 * 
	 * @return translated "Name must be at least {min} characters long."
	 */
	@DefaultStringValue("Name must be at least {min} characters long. Ok?")
	@Key("custom.name.size.message")
	String custom_name_size_message();
}
