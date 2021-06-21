<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="eu.fbk.ict.pdi.moki.requestmanager.ReturnMessage"%>


<div class="container">
  <div class="jumbotron">
  	<div class="text-center"><i class="fa fa-5x fa-frown-o" style="color:#d9534f;"></i></div>
    <h1 class="text-center">Error!<p> </p><p><small class="text-center">in service: <c:out value="${returnmessage.name_service}"></c:out>, module: <c:out value="${returnmessage.type_service}"></c:out></small></p></h1>
    <p class="text-center"><c:out value="${returnmessage.message_text}"></c:out></p>
    <p class="text-center"><a class="btn btn-primary" href="index.jsp" target="_self"><i class="fa fa-home"></i> Take Me Home</a></p>
  </div>
  </div>