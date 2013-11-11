/**
 * 
 */
package org.javahispano.javaleague.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.ImageResource.ImageOptions;
import com.google.gwt.resources.client.ImageResource.RepeatStyle;

/**
 * @author adou
 *
 */
public interface Globalresources extends ClientBundle {
	/**
	 * For access the bundle items from application.
	 */
	Globalresources RESOURCE =
			GWT.create(Globalresources.class);	

	@Source("org/javahispano/javaleague/client/resources/images/ad180.png")
	ImageResource ad180();

	@Source("org/javahispano/javaleague/client/resources/images/arrow1.gif")
	ImageResource arrow1();

	@Source("org/javahispano/javaleague/client/resources/images/colbg.png")
	ImageResource colbg();

	@Source("org/javahispano/javaleague/client/resources/images/content-bg.gif")
	ImageResource contentBg();

	@Source("org/javahispano/javaleague/client/resources/images/divider.gif")
	ImageResource divider();

	@Source("org/javahispano/javaleague/client/resources/images/footer-bg.jpg")
	ImageResource footerBg();

	@Source("org/javahispano/javaleague/client/resources/images/footer-tail.gif")
	ImageResource footerTail();

	@Source("org/javahispano/javaleague/client/resources/images/header-bg-java.jpg")
	ImageResource headerBgJava();

	@Source("org/javahispano/javaleague/client/resources/images/header-bg.jpg")
	@ImageOptions(repeatStyle=RepeatStyle.Both)
	ImageResource headerBg();

	@Source("org/javahispano/javaleague/client/resources/images/header-tail.gif")
	ImageResource headerTail();

	@Source("org/javahispano/javaleague/client/resources/images/headerimg.png")
	ImageResource headerimg();

	@Source("org/javahispano/javaleague/client/resources/images/icon-home-act.gif")
	ImageResource iconHomeAct();

	@Source("org/javahispano/javaleague/client/resources/images/icon-home-sprite.gif")
	ImageResource iconHomeSprite();

	@Source("org/javahispano/javaleague/client/resources/images/icon-home.gif")
	ImageResource iconHome();

	@Source("org/javahispano/javaleague/client/resources/images/icon-mail-act.gif")
	ImageResource iconMailAct();

	@Source("org/javahispano/javaleague/client/resources/images/icon-mail-sprite.gif")
	ImageResource iconMailSprite();

	@Source("org/javahispano/javaleague/client/resources/images/icon-mail.gif")
	ImageResource iconMail();

	@Source("org/javahispano/javaleague/client/resources/images/icon-sitemap-act.gif")
	ImageResource iconSitemapAct();

	@Source("org/javahispano/javaleague/client/resources/images/icon-sitemap-sprite.gif")
	ImageResource iconSitemapSprite();

	@Source("org/javahispano/javaleague/client/resources/images/icon-sitemap.gif")
	ImageResource iconSitemap();

	@Source("org/javahispano/javaleague/client/resources/images/img1.jpg")
	ImageResource img1();

	@Source("org/javahispano/javaleague/client/resources/images/img2.jpg")
	ImageResource img2();

	@Source("org/javahispano/javaleague/client/resources/images/img3.jpg")
	ImageResource img3();

	@Source("org/javahispano/javaleague/client/resources/images/img4.jpg")
	ImageResource img4();

	@Source("org/javahispano/javaleague/client/resources/images/img5.jpg")
	ImageResource img5();

	@Source("org/javahispano/javaleague/client/resources/images/java_cup.gif")
	ImageResource java_cup();

	@Source("org/javahispano/javaleague/client/resources/images/javacup.logo.png")
	ImageResource javacupLogo();

	@Source("org/javahispano/javaleague/client/resources/images/logo_old.jpg")
	ImageResource logo_old();

	@Source("org/javahispano/javaleague/client/resources/images/logo.png")
	ImageResource logo();

	@Source("org/javahispano/javaleague/client/resources/images/logoJavaHispano.png")
	ImageResource logoJavaHispano();

	@Source("org/javahispano/javaleague/client/resources/images/me.png")
	ImageResource me();

	@Source("org/javahispano/javaleague/client/resources/images/playing1.jpg")
	ImageResource playing1();

	@Source("org/javahispano/javaleague/client/resources/images/playing2.jpg")
	ImageResource playing2();

	@Source("org/javahispano/javaleague/client/resources/images/playing3.jpg")
	ImageResource playing3();

	@Source("org/javahispano/javaleague/client/resources/images/playing4.jpg")
	ImageResource playing4();

	@Source("org/javahispano/javaleague/client/resources/images/playing5.jpg")
	ImageResource playing5();

	@Source("org/javahispano/javaleague/client/resources/images/playing6.jpg")
	ImageResource playing6();

	@Source("org/javahispano/javaleague/client/resources/images/sidebar-bg.gif")
	ImageResource sidebarBg();

	@Source("org/javahispano/javaleague/client/resources/images/sidebar-extra-bg.jpg")
	ImageResource sidebarExtraBg();

	@Source("org/javahispano/javaleague/client/resources/images/slogan.jpg")
	ImageResource slogan();

	@Source("org/javahispano/javaleague/client/resources/images/submit-button.jpg")
	ImageResource submitButton();


	
	public interface Styles extends CssResource {

		@ClassName("border-right")
		String borderRight();

		@ClassName("border-bot")
		String borderBot();

		String p3();

		String last();

		String p2();

		String inner();

		String p1();

		String inner_copy();

		@ClassName("line-ver")
		String lineVer();

		@ClassName("row-3")
		String row3();

		String fleft();

		String wrapper();

		String button();

		@ClassName("right-bot-corner")
		String rightBotCorner();

		String bg();

		String text();

		String submit();

		String indent();

		@ClassName("col-3")
		String col3();

		@ClassName("col-1")
		String col1();

		@ClassName("col-2")
		String col2();

		String link1();

		@ClassName("home-current")
		String homeCurrent();

		@ClassName("img-list-alt")
		String imgListAlt();

		String txt1();

		@ClassName("left-top-corner")
		String leftTopCorner();

		String txt2();

		String sitemap();

		@ClassName("mail-current")
		String mailCurrent();

		String container();

		String mainContent();

		@ClassName("right-top-corner")
		String rightTopCorner();

		@ClassName("border-left")
		String borderLeft();

		String home();

		String aside();

		@ClassName("extra-wrap")
		String extraWrap();

		String clear();

		String title();

		@ClassName("tail-bottom")
		String tailBottom();

		String current();

		@ClassName("border-top")
		String borderTop();

		String fright();

		@ClassName("top-links")
		String topLinks();

		String mail();

		@ClassName("img-list")
		String imgList();

		String alignright();

		@ClassName("line-hor")
		String lineHor();

		String news();

		@ClassName("img-box")
		String imgBox();

		@ClassName("img-indent")
		String imgIndent();

		String list();

		String section();

		@ClassName("row-2")
		String row2();

		@ClassName("row-1")
		String row1();

		String field();

		@ClassName("sitemap-current")
		String sitemapCurrent();

		String address();

		String nav();

		@ClassName("tail-top")
		String tailTop();

		@ClassName("left-bot-corner")
		String leftBotCorner();

		String box();

		String aligncenter();

		String first();

	}

	@Source("org/javahispano/javaleague/client/resources/images/facebooklogo.jpeg")
	DataResource facebooklogo();

	@Source("org/javahispano/javaleague/client/resources/images/googlelogo.gif")
	ImageResource googlelogo();

	@Source("org/javahispano/javaleague/client/resources/images/twitterlogo.gif")
	ImageResource twitterlogo();
	
	
}
