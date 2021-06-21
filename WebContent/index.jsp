<%@ page language="java" errorPage="modules/common/error404.jsp" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html>
	<head>
		<title>Dompé Search Engine</title>
<%-- //includes all css' and js' stuffs --%>
		<jsp:include page="modules/common/head.jsp"  flush="true"/>
	</head>
	<body ng-app="Moki">
<%-- //includes the header --%>
		<jsp:include page="modules/common/header.jsp"  flush="true"/>

<%-- //includes the notification manager --%>
		<jsp:include page="modules/common/notifications.jsp"  flush="true"/>

<%-- //this takes the page required --%>
		<jsp:include page="modules/${param.module == undefined ? \"\" : param.module}/${param.page == undefined ? \"welcome\" : param.page}.jsp" flush="true" />
		
<%-- //includes the footer --%>
		<%--<jsp:include page="modules/common/footer.jsp"  flush="true"/>--%>
	</body>
</html>