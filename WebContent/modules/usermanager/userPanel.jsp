<%@ page language="java" contentType="text/html; charset=UTF-8"%>


					<hr class="">
					<div class="container target" ng-controller="userPanelController">
					    <div class="row">
					        <div class="col-sm-5">
					            <h1 id="name"><span ng-bind="title_name"></span></h1>
					        </div>
					        <div class="col-sm-10">
					          <button type="button" class="btn btn-info editbtn" ng-class="not_editing" id="edit_btn" ng-click="edit()">Edit</button>
					          <button type="button" class="btn btn-info editbtn hidden" ng-class="editing" id="save_btn" ng-click="save()">Save</button>
					          <button type="button" class="btn btn-cancel editbtn hidden" ng-class="editing" id="cancel_edit_btn" ng-click="cancel()">Cancel</button>
					<br>
					        </div>
					    </div>
					  <br>
					    <div class="row">
					        <div class="col-sm-7">
					            <!--left col-->
					            <h1 class="editable"></h1>
					            
					            
					            
					            <ul class="list-group">
					                <li class="list-group-item text-muted list-group-header" contenteditable="false">Profile</li>
					                <li class="list-group-item text-right"><span class="pull-left"><strong class="">Name</strong></span><span ng-bind="name"></span><moki-input ng-show="show_inpt" model="name_inpt" type="text"></moki-input></li>
					                <li class="list-group-item text-right"><span class="pull-left"><strong class="">Email</strong></span><span ng-bind="email"></span><moki-input ng-show="show_inpt" model="email_inpt" type="text"></moki-input></li>
					                <li class="list-group-item text-right"><span class="pull-left"><strong class="">Organization</strong></span><span ng-bind="organization"></span><moki-input ng-show="show_inpt" model="organization_inpt" type="text"></moki-input></li>
					                <li class="list-group-item text-right"><span class="pull-left"><strong class="">Date of birth</strong></span><span ng-bind="birthdate"></span><moki-input ng-show="show_inpt" model="birthdate_inpt" type="date"></moki-input></li>
					             	<li class="list-group-item text-right"><span class="pull-left"><strong class="">Role: </strong></span> <span ng-bind="role | array"></span>
					                <li class="list-group-item text-right" ng-class="not_editing" id="pw_list"><span class="pull-left"><strong class="">Password: </strong></span> &#8226;&#8226;&#8226;
					            </ul>
					           <div class="panel panel-default hidden" id="edit_pw_panel" ng-class="editing">
					             <div class="panel-heading">Edit Password</div>
					                <div class="panel-body">
					                	<div class="row">
					                	<div class="col-sm-6">
					                	<span>Old password:</span> <br>
					                	<input type="password" style="margin-top:10px" id="oldpw" ng-model="oldpw"></input> <br>
					                	</div>
					                	<div class="col-sm-6">
					                	<span style="margin-top:20px">New password:</span> <br>
					                	<input type="password" style="margin-top:10px" id="newpw" ng-model="newpw"></input> <br>
					                	</div>
					                	</div>
					                </div>
					            </div>
					            
					        </div>
					        <!-- right part of the page -->
					        <div class="col-sm-5" style="" contenteditable="false">
					            
					            <div class="panel panel-default">
					                <div class="panel-heading">Your Rights as <span ng-bind="role | array"></span></div>
					                <div class="panel-body"> 
										You can <b><span ng-bind="can" ></span></b><br>
										<span ng-show="can_not">but you can not</span> <b><span ng-bind="can_not"></span></b>
					                </div>
					            </div>
					                     
					            </div>
					                 
					        </div>
					              
					    </div>
					<script src="js/usermanager/userPanel.js"></script>