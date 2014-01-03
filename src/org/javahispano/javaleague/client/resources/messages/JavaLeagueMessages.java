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

	@DefaultMessage("Bienvenido #0# !\n Gracias por registrarte en javaLeague. Para terminar el registro es necesario que valides tu dirección de correo visitando el siguiente enlace #1#.\nSi has recibido este correo por error, por favor, ignóralo y envía un correo avisando a #2#")
	String bodyEmailRegisterUser();
	
	@DefaultMessage("¡ Bienvenido a javaLeague !")
	String subjectEmailRegisterUser();

}