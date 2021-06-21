<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


				<div class="logo_products">
					<div class="container">
					<div class="w3ls_logo_products_left1">
					
					
					<c:choose>
		                <c:when test="${sessionScope.role == undefined}">
			                <!-- if the user isn't logged -->
							<a href="?page=login&module=usermanager" target="_self" style="color:#8989C5"><i class="fa fa-user-o" aria-hidden="true"></i> Login</a> <!-- <a href="?page=register&module=usermanager" target="_self" style="color:#8989C5">Register</a> -->
	                    </c:when>
		                <c:otherwise>
		                	<!-- if a user is logged in -->
							<a href="?page=userPanel&module=usermanager" target="_self" style="color:#8989C5"><i class="fa fa-user-o" aria-hidden="true"></i> Welcome <span style="color:black"> <c:out value="${sessionScope.name}"></c:out> </span></a> | <a href="?page=logout&module=usermanager" target="_self" style="color:#8989C5">Logout</a>
		                </c:otherwise>     
		            </c:choose>
					
					
					
					
					
					</div>
					<div class="w3ls_logo_products_left">
						<h1><a href="index.jsp" target="_self">Dompé Search Engine</a></h1>
						<!-- <h5>The Modelling Wiki</h5> -->
					</div>
				<!-- 
				<div class="w3l_search">
					<form action="#" method="post">
						<input type="search" name="Search" placeholder="Search everything..." style="width:80%">
						<button type="submit" class="btn btn-default search" aria-label="Left Align" style="width: 40px">
							<i class="fa fa-search" aria-hidden="true"> </i>
						</button>
						<div class="clearfix"></div>
					</form>
				</div>
				-->
					<div class="clearfix"> </div>
				</div>
			</div>
			<!-- //header -->
			<!-- navigation menu -->
				<div class="navigation-agileits" ng-controller="headerController">
					<div class="container">
						<nav class="navbar navbar-default">
										<!-- Brand and toggle get grouped for better mobile display -->
										<div class="navbar-header nav_2">
											<button type="button" class="navbar-toggle collapsed navbar-toggle1" data-toggle="collapse" data-target="#bs-megadropdown-tabs">
												<span class="sr-only">Toggle navigation</span>
												<span class="icon-bar"></span>
												<span class="icon-bar"></span>
												<span class="icon-bar"></span>
											</button>
										</div> 
										<div class="collapse navbar-collapse" id="bs-megadropdown-tabs">
											<ul class="nav navbar-nav navcenter">
											
												<li ng-repeat="submenu in menu" ng-class="submenu.sub === 'none' ? 'active' : 'dropdown'" ng-include="submenu.sub === 'none' ? '/menu_active.html' : '/menu_dropdown.html'"></li>
											
											</ul>
									</div>
									</nav>
				</div>
			</div>
			<script src="js/common/header.js"></script>
			
			<!-- types of menù -->
			
			<!-- menù without dropdown -->
			<script type="text/ng-template" id="/menu_active.html">
				<a ng-href="{{submenu.link}}" target="_self" class="act" ng-bind="submenu.label"></a>
			</script>
			
			<!-- menù with dropdown -->
			<script type="text/ng-template" id="/menu_dropdown.html">
				<a href="#" class="dropdown-toggle" data-toggle="dropdown">{{submenu.label}}<b class="caret"></b></a>
					<ul class="dropdown-menu multi-column columns-3">
						<div class="row">
							<div class="multi-gd-img">
								<ul class="multi-column-dropdown">
									<h6 ng-bind="submenu.label"></h6>
									<li ng-repeat="sub in submenu.sub"><a ng-href="{{sub.link}}" target="_self" ng-bind="sub.label"></a></li>
								</ul>
							</div>
						</div>						
					</ul>
			</script>