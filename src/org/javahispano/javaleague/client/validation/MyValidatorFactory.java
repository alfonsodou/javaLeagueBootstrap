/**
 * 
 */
package org.javahispano.javaleague.client.validation;

import javax.validation.Validator;

import org.javahispano.javaleague.shared.UserDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.validation.client.AbstractGwtValidatorFactory;
import com.google.gwt.validation.client.GwtValidation;
import com.google.gwt.validation.client.impl.AbstractGwtValidator;

/**
 * @author adou
 *
 */
public final class MyValidatorFactory extends AbstractGwtValidatorFactory {

	  /**
	   * Validator marker for the Validation Sample project. Only the classes and groups listed
	   * in the {@link GwtValidation} annotation can be validated.
	   */
	  @GwtValidation(UserDTO.class)
	  public interface GwtValidator extends Validator {
	  }

	  @Override
	  public AbstractGwtValidator createValidator() {
	    return GWT.create(GwtValidator.class);
	  }
	}