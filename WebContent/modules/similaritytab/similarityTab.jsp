<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<meta charset="UTF-8">
<style>
#search{
width: 80%;
display:flex;
flex-direction: column;
margin-bottom: 20px;
margin-left: ;
}
.container{
margin-top: 20px;
width: auto;
}

.w3l_search input[type="search"] {
width: 30%;
margin-right:10px
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
</style>
<link rel="stylesheet" href="css/scrolling-tabs.css">
<br><br>
<div ng-controller="getSimilarityController as demo" class="container" style="margin-top:0px">
	<h2 align="center">SOP Similarity</h2>
	<br/>
	<br/>	        
	 <!-- Tab of the table -->
	 <div class="tab-content clearfix">	 
		<div class="tab-pane" ng-class="{ 'active': lista.active }">
		
			<table ng-table="demo.tableParams" class="table table-condensed table-bordered table-striped" show-filter="true">
			    <tr ng-repeat="element in $data">
			        <td title="'SOP#1'" filter="{ sop1: 'text'}" sortable="'sop1'">
			            <span ng-bind="element.sop1"></span></td>
			        <td title="'SOP#2'" filter="{ sop2: 'text'}" sortable="'sop2'">
			            <span ng-bind="element.sop2"></span></td>
			        <td title="'Similarità'">
			            <div ng-bind="element.totalsim" style="white-space:pre-wrap"></div></td>
			    </tr>
			</table>
			
		</div>
	 	
	</div> 
	
</div>


<script src="https://d3js.org/d3.v4.js"></script>
<script src="js/similaritytab/similarityTab.js"></script>
