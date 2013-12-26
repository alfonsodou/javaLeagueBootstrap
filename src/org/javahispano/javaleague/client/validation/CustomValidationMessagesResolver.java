/**
 * 
 */
package org.javahispano.javaleague.client.validation;

import org.hibernate.validator.ValidationMessages;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.ConstantsWithLookup;
import com.google.gwt.validation.client.AbstractValidationMessageResolver;
import com.google.gwt.validation.client.UserValidationMessagesResolver;

/**
 * @author adou
 *
 */
/**
 * {@link UserValidationMessageResolver} that uses the
 * {@link ValidationMessages}.
 */
public class CustomValidationMessagesResolver extends
		AbstractValidationMessageResolver implements
		UserValidationMessagesResolver {

	// TODO(nchalko) implement this as part of the GWtValidation annotation
	// instead of a separate class.
	protected CustomValidationMessagesResolver() {
		super((ConstantsWithLookup) GWT.create(ValidationMessages.class));
	}
}