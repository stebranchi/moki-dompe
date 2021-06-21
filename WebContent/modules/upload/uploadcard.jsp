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
		    <label for="date">Date:</label>
			<input type="date" id="date" name="date">
			<br/>
			<c:when test="${type == Paper}">
			<input type="text" name="pages" ng-model="pages" placeholder="Enter number of pages of the article"/>
			<br/>
			<input type="text" name="isbn" ng-model="isbn" placeholder="Enter ISBN/ISSN of the article"/>
			<br/>
			<input type="text" name="doi" ng-model="doi" placeholder="Enter DOI of the article"/>
			<br/>
			<input type="text" name="requester" ng-model="requester" placeholder="Enter Requester of the article"/>
			<br/>
			<input type="text" name="pmcid" ng-model="pmcid" placeholder="Enter PMCID of the article"/>
			<br/>
			</c:when>
			<c:otherwise>
			<input type="text" name="cro" ng-model="cro" placeholder="Enter Institution/CRO name"/>
			<br/>
			<input type="text" name="keywords" ng-model="keywords" placeholder="Enter keywords"/>
			<br/>
			<input type="text" name="material" ng-model="material" placeholder="Enter material"/>
			<br/>
			<input type="text" name="documentno" ng-model="documentno" placeholder="Enter document's number"/>
			<br/>
			<input type="text" name="project" ng-model="project" placeholder="Enter Project/Product"/>
			<br/>
			<input type="text" name="glp" ng-model="glp" placeholder="Enter GLP/GCP code"/>
			<br/>
			<input type="text" name="essay" ng-model="essay" placeholder="Enter an essay of the document"/>
			<br/>
			<input type="text" name="administration" ng-model="administration" placeholder="Enter Administration type"/>
			<br/>
			<input type="text" name="location" ng-model="location" placeholder="Enter alphanumeric code for locating the document"/>
			<br/>
			</c:otherwise>
			<input type="text" name="notes" ng-model="notes" placeholder="Enter Notes"/>
			<br/>
			<input type="text" name="author_address" ng-model="author_address" placeholder="Enter optional institutions references"/>
			<br/>
			<input type="text" name="pmcid" ng-model="pmcid" placeholder="Enter PMCID of the article"/>
			<br/>
			<input type="text" name="language" ng-model="language" placeholder="Enter the language of the document's content"/>
			<br/>
		    <label>Attachment:</label>
		    <input type="file" name="file_pdf" fileread="pdf"/>
		</div>
	</div>
</div>

<script src="js/upload/uploadcard.js"></script>