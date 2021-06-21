<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />
<link rel="stylesheet" href="css/scrolling-tabs.css">	

<div class="login">	
	<div ng-controller="GetUploadController as demo" class="container">
		<h2>Upload</h2>
		<div class="login-form-grids animated wow slideInUp" data-wow-delay=".5s">
			<form class="form" name="upload" ng-submit="uploadFile()">
			<input type="radio" name="type" value="Paper" ng-model="type" required>
	    	<label for="typeChoice1">Paper</label>
	    	<input type="radio" name="type" value="Preclinical_Trial" ng-model="type" required>
	    	<label for="typeChoice2">Trial Preclinico</label>
	    	<input type="radio" name="type" value="Clinical_Trial" ng-model="type" required>
	    	<label for="typeChoice3">Trial Clinico</label>
	    	<c:set var="type" value="${param.type}" />
	    	<br/>
	    	<label>XML File:</label>
		    <input type="file" name="file_xml" fileread="xml" label="File xml"/>
	    	<br/>
	    	<input type="text" name="title" ng-model="filename" placeholder="Enter the title of the document" required/>
	    	<br/>
		    <input type="text" name="author" ng-model="author" placeholder="Enter authors of the document"/>
		    <br/>
		    <label>Attachment:</label>
		    <input type="file" name="file_pdf" fileread="pdf"/>
		</div>
	</div>
</div>

<script src="js/upload/uploadcard.js"></script>