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

	@DefaultMessage("<p>Bienvenido <b>#0#</b> !</p><p>Gracias por registrarte en <b>javaLeague</b>. Para terminar el registro es necesario que valides tu dirección de correo visitando el siguiente <a href='#1#'>enlace</a>.</p><p>Si has recibido este correo por error, por favor, ignóralo y envía un correo avisando a <a href='mailto:#2#'>#2#.</p><p>#3#</p>")
	String bodyEmailRegisterUser();

	@DefaultMessage("¡ Bienvenido a javaLeague !")
	String subjectEmailRegisterUser();

	@DefaultMessage("Usuario y/o contraseña incorrectos !")
	String errorEmailPassword();

	@DefaultMessage("Nombre")
	String nameFrameWork();

	@DefaultMessage("Versión")
	String versionFrameWork();

	@DefaultMessage("Descripción")
	String summaryFrameWork();

	@DefaultMessage("Creado")
	String creationFrameWork();

	@DefaultMessage("Actualizado")
	String updatedFrameWork();

	@DefaultMessage("Descargar")
	String urlDownloadFrameWork();

	@DefaultMessage(":: Sin táctica ::")
	String emptyUserTactic();

	@DefaultMessage("Nombre")
	String nameLeague();

	@DefaultMessage("Manager")
	String manager();

	@DefaultMessage("Inicio inscripción")
	String startSignIn();

	@DefaultMessage("Fin inscripción")
	String endSignIn();

	@DefaultMessage("¿ Estás seguro ?")
	String confirm();
	
	@DefaultMessage("Todas las clases de tu táctica deben estar incluidas en el package {0}")
	String packagePath(String path);
	
	@DefaultMessage("Jornada")
	String round();
	
	@DefaultMessage("Pública")
	String publicLeague();
	
	@DefaultMessage("Privada")
	String privateLeague();
	
	@DefaultMessage("En DIRECTO!")
	String live();
	
	@DefaultMessage("ERROR!")
	String matchError();
	
	@DefaultMessage("Amistoso")
	String friendly();
	
	@DefaultMessage("Liga")
	String league();
	
	@DefaultMessage("Esperando")
	String waiting();
	
	@DefaultMessage("Palabra clave:")
	String passwordLeaguePrivate();
	
	@DefaultMessage("Palabra clave incorrecta!")
	String wrongPasswordLeaguePrivate();
}