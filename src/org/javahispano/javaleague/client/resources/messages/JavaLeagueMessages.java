/**
 * 
 */
package org.javahispano.javaleague.client.resources.messages;

import com.google.gwt.i18n.client.Messages;

/**
 * @author adou
 * 
 */
public interface JavaLeagueMessages extends Messages {
	@DefaultMessage("Administrador javaLeague")
	String adminJavaLeague();

	@DefaultMessage("<p>¡ Bienvenido [0] !</p><p>Gracias por registrarte en javaLeague. Para terminar el registro es necesario que valides tu dirección de correo visitando el siguiente enlace <a href='[1]'>[1]</a>.</p><p>Si has recibido este correo por error, por favor, ignóralo y envía un correo avisando a <a href='mailto:[2]'>javaleague@gmail.com</a></p>")
	String bodyEmailRegisterUser();
	
	@DefaultMessage("¡ Bienvenido a javaLeague !")
	String subjectEmailRegisterUser();

}
