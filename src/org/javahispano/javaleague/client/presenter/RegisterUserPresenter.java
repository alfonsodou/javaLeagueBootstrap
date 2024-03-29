/**
 * 
 */
package org.javahispano.javaleague.client.presenter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Form;
import org.gwtbootstrap3.client.ui.Input;
import org.gwtbootstrap3.client.ui.Label;
import org.gwtbootstrap3.client.ui.Paragraph;
import org.gwtbootstrap3.client.ui.TextBox;
import org.javahispano.javaleague.client.event.RegisterUserEvent;
import org.javahispano.javaleague.client.event.ShowHomeEvent;
import org.javahispano.javaleague.client.helper.RPCCall;
import org.javahispano.javaleague.client.resources.messages.JavaLeagueMessages;
import org.javahispano.javaleague.client.service.UserAccountServiceAsync;
import org.javahispano.javaleague.shared.domain.User;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author adou
 * 
 */
public class RegisterUserPresenter implements Presenter {

	public interface Display {
		HasClickHandlers getCancelButton();

		Widget asWidget();

		Button getRegisterButton();

		TextBox getUserName();

		TextBox getTeamName();

		TextBox getEmail();

		Input getPassword();

		Input getRePassword();

		Label getErrorEmail();

		Label getErrorPassword();

		Label getErrorPasswordSize();

		Label getErrorUserName();

		Label getErrorTeamName();

		Label getErrorRegisterEmail();

		Form getFormRegisterUser();

		Paragraph getTextSendEmail();
	}

	private final Display display;
	private final User user;
	private final SimpleEventBus eventBus;
	private final UserAccountServiceAsync userAccountService;

	private JavaLeagueMessages javaLeagueMessages = GWT
			.create(JavaLeagueMessages.class);

	public RegisterUserPresenter(UserAccountServiceAsync userAccountService,
			SimpleEventBus eventBus, Display display) {
		this.display = display;
		this.eventBus = eventBus;
		this.userAccountService = userAccountService;
		user = new User();
		this.display.getTextSendEmail().setVisible(false);

		hideErrorLabel();
	}

	public void bind() {
		this.display.getCancelButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				GWT.log("RegisterUserPresenter: Click on Cancel Button!");
				doCancel();
			}
		});

		this.display.getRegisterButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				GWT.log("RegisterUserPresenter: Click on Register Button!");
				doRegister();
			}
		});

		display.getUserName().setFocus(true);
	}

	@Override
	public void go(HasWidgets container) {
		container.add(display.asWidget());
		bind();
	}

	private void doCancel() {
		GWT.log("RegisterUserPresenter: Firing ShowHomeEvent");
		eventBus.fireEvent(new ShowHomeEvent());
	}

	private void doRegister() {

		boolean error = false;

		hideErrorLabel();

		if (this.display.getUserName().getValue().length() < 4) {
			this.display.getErrorUserName().setVisible(true);
			error = true;
		}

		if (this.display.getTeamName().getValue().isEmpty()) {
			this.display.getErrorTeamName().setVisible(true);
			error = true;
		}

		if (!checkEmail(this.display.getEmail().getValue())) {
			this.display.getErrorEmail().setVisible(true);
			error = true;
		}

		if (this.display.getPassword().getFormValue().length() < 4) {
			this.display.getErrorPasswordSize().setVisible(true);
			error = true;
		}

		if (!this.display.getPassword().getFormValue()
				.equals(this.display.getRePassword().getFormValue())) {
			this.display.getErrorPassword().setVisible(true);
			error = true;
		}

		if (!error) {
			MessageDigest crypt = null;

			try {
				crypt = java.security.MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException e) {
				Window.alert("MD5 not supported");
				return;
			}

			byte[] digested = crypt.digest(display.getPassword().getFormValue()
					.getBytes());
			String crypt_password = new String();

			// Converts bytes to string
			for (byte b : digested)
				crypt_password += Integer.toHexString(0xFF & b);

			user.setEmailAddress(this.display.getEmail().getValue());
			user.setName(this.display.getUserName().getValue());
			user.setPassword(crypt_password);
			this.display.getFormRegisterUser().setVisible(false);
			new RPCCall<User>() {
				@Override
				protected void callService(AsyncCallback<User> cb) {
					display.getRegisterButton().setEnabled(false);
					userAccountService.register(user, display.getTeamName()
							.getText(), javaLeagueMessages.adminJavaLeague(),
							javaLeagueMessages.subjectEmailRegisterUser(),
							javaLeagueMessages.bodyEmailRegisterUser(), cb);
				}

				@Override
				public void onSuccess(User result) {
					if (result != null) {
						GWT.log("RegisterUserPresenter: Firing RegisterUserEvent");
						eventBus.fireEvent(new RegisterUserEvent(result));
						display.getTextSendEmail().setVisible(true);

					} else {
						display.getFormRegisterUser().setVisible(true);
						display.getErrorRegisterEmail().setVisible(true);
					}
				}

				@Override
				public void onFailure(Throwable caught) {
					Window.alert("Error retrieve user...");
				}
			}.retry(3);

		}

	}

	/**
	 * minimum email l@n.co
	 * */
	public static boolean checkEmail(final String emlId) {
		// ex:ak@bv.gh
		if (emlId == null) {
			return false;
		}
		final int lngth = emlId.length();
		if (lngth < 6) {
			return false;
		}
		final int locationAt = emlId.indexOf('@');
		if (locationAt < 1) {
			return false;
		}
		final int postLastPeriod = emlId.lastIndexOf('.');
		if (postLastPeriod < 0) {
			return false;
		}
		if (lngth - postLastPeriod < 3) {
			return false;
		}
		if (postLastPeriod - locationAt < 1) {
			return false;
		}
		return true;

	}

	private void hideErrorLabel() {
		this.display.getErrorUserName().setVisible(false);
		this.display.getErrorEmail().setVisible(false);
		this.display.getErrorPassword().setVisible(false);
		this.display.getErrorPasswordSize().setVisible(false);
		this.display.getErrorRegisterEmail().setVisible(false);
		this.display.getErrorTeamName().setVisible(false);

	}
}
