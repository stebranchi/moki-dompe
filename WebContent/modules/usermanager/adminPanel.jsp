<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

		<div ng-controller="adminPanelController">
			<hr class="">
			<div class="container"><h1 class="text-center">Administration panel</h1></div>
			<hr class="">
			<div id="exTab1" class="container container-small">
			   <ul  class="nav nav-pills">
			       <li ng-repeat="role in user_per_role" ng-class="{'active': $index == 0}"><a ng-href="\#{{$index}}" target="_self" data-toggle="tab"><span ng-bind="role.role"></span><span class="badge" ng-bind="role.list.length"></span></a></li>
			   </ul>
			   <div class="tab-content clearfix">
					<!-- //loop for the roles -->
					
			   	  	<div ng-repeat="role in user_per_role" ng-init='parentIndex=$index' class="tab-pane" ng-class="{'active': $index == 0}" id="{{$index}}">
			   	  	
			   	  	<div class="panel-group">
			   	  	
			   	  	<!-- //loop for the users of that role -->
			   	  	
			   	  	<div ng-repeat="user in role.list | filter:{name: search}" class="panel panel-default">
								    <div class="panel-heading">
								    	<div class="row">
								    		<div class="col-md-6 text-left panel-title">
								    			<span data-toggle="collapse" id="{{parentIndex}}{{$index}}_show" ng-href="\#{{parentIndex}}{{$index}}_collapse" class="searchable collapsed" ng-bind="user.name"></span>
								    		</div>
	            							<div class="col-md-6 text-right">
										        <button type="button" class="btn btn-info editbtn-admin" ng-class="inputs[parentIndex][$index].show_inpt ? 'hidden' : ''" ng-click="edit(parentIndex, $index)">Edit</button>
										        <button type="button" class="btn btn-info editbtn-admin hidden" ng-class="!inputs[parentIndex][$index].show_inpt ? 'hidden' : ''" ng-click="save(parentIndex, $index)">Save</button>
												<button type="button" class="btn btn-cancel editbtn-admin hidden" ng-class="!inputs[parentIndex][$index].show_inpt ? 'hidden' : ''" ng-click="cancel(parentIndex, $index)">Cancel</button>
												<!-- //if banned -->
										        	<button type="button" class="btn btn-danger editbtn-admin" ng-class="user.active === '1' ? '' : 'hidden'" ng-click="setActive(parentIndex, $index)">Ban</button>
										        <!-- //else -->
										        	<button type="button" class="btn btn-success editbtn-admin"  ng-class="user.active === '0' ? '' : 'hidden'" ng-click="setActive(parentIndex, $index)">Activate</button>
	            							</div>
								    	</div>
								      
								    </div>
								    <div id="{{parentIndex}}{{$index}}_collapse" class="panel-collapse collapse">
								      <ul class="list-group">
								      	<li class="list-group-item text-right"><span class="pull-left"><strong class="">Name</strong></span><span><moki-input ng-show="inputs[parentIndex][$index].show_inpt" model="inputs[parentIndex][$index].name" type="text"></moki-input><span ng-show="!inputs[parentIndex][$index].show_inpt" ng-bind="user.name"></span></span></li>
								        <li class="list-group-item text-right"><span class="pull-left"><strong class="">Email</strong></span><span><moki-input ng-show="inputs[parentIndex][$index].show_inpt" model="inputs[parentIndex][$index].email" type="text"></moki-input><span ng-show="!inputs[parentIndex][$index].show_inpt" ng-bind="user.email"></span></span></li>
						                <li class="list-group-item text-right"><span class="pull-left"><strong class="">Email Confirm</strong></span><span><moki-input ng-show="inputs[parentIndex][$index].show_inpt" model="inputs[parentIndex][$index].email_confirm" type="text"></moki-input><span ng-show="!inputs[parentIndex][$index].show_inpt" ng-bind="user.email_confirm"></span></span></li>
						                <li class="list-group-item text-right"><span class="pull-left"><strong class="">Organization</strong></span><span><moki-input ng-show="inputs[parentIndex][$index].show_inpt" model="inputs[parentIndex][$index].organization" type="text"></moki-input><span ng-show="!inputs[parentIndex][$index].show_inpt" ng-bind="user.organization"></span></span></li>
						                <li class="list-group-item text-right"><span class="pull-left"><strong class="">Date of birth</strong></span><span><moki-input ng-show="inputs[parentIndex][$index].show_inpt" model="inputs[parentIndex][$index].birthdate" type="date"></moki-input><span ng-show="!inputs[parentIndex][$index].show_inpt" ng-bind="user.birthdate"></span></span></li>
						             	<li class="list-group-item text-right"><span class="pull-left"><strong class="">Password</strong></span><span><moki-input ng-show="inputs[parentIndex][$index].show_inpt" model="inputs[parentIndex][$index].password" type="text"></moki-input><span ng-show="!inputs[parentIndex][$index].show_inpt" ng-bind="user.password"></span></span></li>
						             	<li class="list-group-item text-right"><span class="pull-left"><strong class="">Role</strong></span><span><div ng-dropdown-multiselect="" ng-show="inputs[parentIndex][$index].show_inpt" options="roles" selected-model="inputs[parentIndex][$index].role" extra-settings="stringSettings"></div><span ng-show="!inputs[parentIndex][$index].show_inpt" ng-bind="user.role | array"></span></span></li>
						             	
								      </ul>
								    </div>
								</div>
			   	  		<!-- //end of the loop -->
			   	  		</div>
			   	  	</div>
			   	  	
			   </div>
			</div>
			<div class="container"><input type="text" class="form-control" id="searchbox" placeholder="Type to search.." ng-model="search"></div>
			
		</div>
			<script src="js/usermanager/adminPanel.js"></script>