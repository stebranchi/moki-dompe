<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />
<link rel="stylesheet" href="css/scrolling-tabs.css">	
<style>

.container{
margin-top: 20px;
width: auto;
}

.row{
display: flex;
flex-direction: row;
justify-content: space-around;
}

.filters{
width: 30%;
display: flex;
flex-direction: row;
margin-top: 10px;
}

label {
   margin:0px 10px 0px 10px;
}
h2 {
  text-align: center;
}
</style>

<div ng-controller="GetUploadListController as ctrl" class="container">
<h2>Storico Upload</h2>
<br/>
<br/>
<span>Numero di documenti caricati dal tuo ultimo login: </span> <span style="color:black"ng-bind="count"></span>
<br/>
<br/>
	<div class="tab-content clearfix">	 
		<table ng-table="ctrl.tableParams" class="table table-condensed table-bordered table-striped" show-filter="true">
		   <tr ng-repeat="element in $data">
		   		<td title="'Titolo'" filter="{ title: 'text'}" style="width: 48%" sortable="'title'">
		        	<span ng-bind="element.title"></span></td>
		       	<td title="'Utente'" filter="{ user: 'text'}" style="width: 40%"  sortable="'user'">
		        	<span ng-bind="element.user"></span></td>
		       	<td title="'Data'" filter="{ date: 'text'}" style="width: 10%" sortable="'date'">
		        	<span ng-bind="element.date"></span></td>
		   </tr>
		</table>
	</div>
</div>


<script src="js/upload/uploadlist.js"></script>