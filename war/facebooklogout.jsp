<%@ page import="java.util.List,org.javahispano.javaleague.shared.*,org.javahispano.javaleague.server.utils.*"%>

<html>
<title>Logging out of Facebook</title>
<body>
<p>Please wait...</p>
<%
	session.invalidate();
%>
<div id="fb-root"></div>
<script src="http://connect.facebook.net/en_US/all.js"></script>
<script>
FB.init({ apiKey: '<%=AuthenticationProvider.fb_api_key()%>' });
FB.getLoginStatus(onResponse);
function onResponse(response) {
    if (!response.session) {
    	   window.location = "http://javaleague.appspot.com/";
    	   return;
    }
    FB.logout(onResponse);
}
</script>

</body>
</html>
