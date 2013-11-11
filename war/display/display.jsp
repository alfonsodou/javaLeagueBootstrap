<%@ page import="com.google.appengine.api.users.User" %>
<%
	String partidoId = (String) request.getParameter("partidoId");
	String authURL = (String) request.getParameter("authURL");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es" lang="es">
  <head>
    <title>javaLeague</title>  
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="Content-Style-Type" content="text/css" />
	<link href="/../stylesheets/style.css" rel="stylesheet" type="text/css" />
	<script src="/../js/maxheight.js" type="text/javascript"></script>
	<script src="/../js/jquery-1.4.2.min.js" type="text/javascript"></script>
	<script src="/../js/cufon-yui.js" type="text/javascript"></script>
	<script src="/../js/cufon-replace.js" type="text/javascript"></script>
	<script src="/../js/Myriad_Pro_300.font.js" type="text/javascript"></script>
	<script src="/../js/Myriad_Pro_400.font.js" type="text/javascript"></script>
  </head>
<body id="page1" onload="new ElementMaxHeight();">
<!-- header -->
	<div id="header">
		<div class="bg">
			<div class="container">
				<div class="row-1">
					<div class="wrapper">
						<div class="fleft"><a href="/"><img src="/../images/logo.jpg" alt="" /></a></div>
						<ul class="top-links">
							<li class="first"><a href="/" class="home-current"></a></li>
							<li><a href="/" class="mail"></a></li>
							<li class="last"><a href="/" class="sitemap"></a></li>
						</ul>
					</div>
				</div>
				<div class="row-2">
					<!-- .nav -->
					<ul class="nav">
						<li><a href="/" class="current">Inicio</a></li>
						<li><a href="/tactic">T&aacute;ctica</a></li>
						<li><a href="/games">Partidos</a></li>
						<li><a href="<%= authURL %>">Logout</a></li>
					</ul>
					<!-- /.nav -->
				</div>
				<div class="row-3">
					<a href="http://www.javahispano.org"><img src="/../images/logoJavaHispano.png" alt="" class="slogan" /></a><br />
					<p>El torneo de programaci&oacute;n de javaHispano. Descarga el framework y programa tu equipo, compite contra otros jugadores.</p>
					<!-- 
					<form action="" id="search-form">
						<fieldset><input type="text" class="text" value="" /><input type="submit" class="submit" value="" /></fieldset>
					</form>
					 -->
				</div>
			</div>
		</div>
	</div>
<!-- content -->
	<div id="content"><div class="inner_copy"></div>
		<div class="container">
			<div class="wrapper">
				<div class="mainContent maxheight">
					<div class="indent">
						<div class="section">
							<h2>Bienvenido a javaLeague!</h2>
 <applet code="org.lwjgl.util.applet.AppletLoader" archive="lwjgl_util_applet.jar" codebase="." width="800" height="600">
  
    <!-- The following tags are mandatory -->
    
    <!-- Name of Applet, will be used as name of directory it is saved in, and will uniquely identify it in cache -->
    <param name="al_title" value="javaleagueapplet">
    
    <!-- Main Applet Class -->
    <param name="al_main" value="org.javahispano.javacup.applet.JavacupApplet">
    
    <!-- List of Jars to add to classpath -->
    <param name="al_jars" value="javacup2012.jar, slick.jar, slf4j-api-1.6.4.jar, slf4j-simple-1.6.4.jar, jorbis-0.0.15.jar, jogg-0.0.7.jar, lwjgl_applet.jar, lwjgl.jar, jinput.jar, lwjgl_util.jar">
    
    <!-- signed windows natives jar in a jar --> 
    <param name="al_windows" value="windows_natives.jar">
    
    <!-- signed linux natives jar in a jar --> 
    <param name="al_linux" value="linux_natives.jar">
    
    <!-- signed mac osx natives jar in a jar --> 
    <param name="al_mac" value="macosx_natives.jar">

    <!-- signed solaris natives jar in a jar --> 
    <param name="al_solaris" value="solaris_natives.jar">
    
    <!-- Tags under here are optional -->
    
    <!-- whether to use cache - defaults to true -->
    <!--  <param name="al_cache" value="true"> -->
    
    <!-- Version of Applet (case insensitive String), applet files not redownloaded if same version already in cache -->
    <!-- <param name="al_version" value="0.1"> -->
    
    <!-- Specify the minimum JRE version required by your applet, defaults to "1.5" -->
    <!-- <param name="al_min_jre" value="1.6"> -->
    
    <!-- background color to paint with, defaults to white -->
    <!-- <param name="boxbgcolor" value="#000000"> -->
    
    <!-- foreground color to paint with, defaults to black -->
    <!-- <param name="boxfgcolor" value="#ffffff"> -->
    
    <!-- logo to paint while loading, will be centered, defaults to "appletlogo.gif" -->
    <param name="al_logo" value="voleaquini-5258-640x640x80.jpg">
    
    <!-- progressbar to paint while loading. Will be painted on top of logo, width clipped to percentage done, defaults to "appletprogress.gif" -->
    <param name="al_progressbar" value="javacup_logo.png">
    
    <!-- whether to run in debug mode -->
    <!-- <param name="al_debug" value="true"> -->
    
    <!-- whether to prepend host to cache path - defaults to true -->
    <!-- <param name="al_prepend_host" value="true"> -->
  	
    <param name="separate_jvm" value="true">
    
    <param name="URL" value="http://localhost:8888/serve?id=<%= partidoId %>">
  </applet>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
<!-- footer -->
	<div id="footer">
		<div class="bg">
			<div class="container">
				<div class="indent">
					javaLeague - 2012<br />
					Professional free web templates <a href="http://www.websitetemplatesonline.com" target="_blank">at www.websitetemplatesonline.com</a>. Interactive <a href="http://www.flashtemplates.com/flash-templates/" title="Flash Templates for Websites">Flash Templates for Websites</a>.
				</div>	
			</div>
		</div>
	</div>
	<script type="text/javascript"> Cufon.now(); </script>
  </body>
</html>
