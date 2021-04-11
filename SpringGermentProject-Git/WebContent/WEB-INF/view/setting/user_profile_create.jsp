<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="pg.services.SettingServiceImpl"%>
<%@page import="pg.model.WareInfo"%>
<%@page import="pg.model.Module"%>
<%@page import="pg.model.Login"%>
<%@page import="java.util.List"%>

<%
	String userId = (String) session.getAttribute("userId");
	String userName = (String) session.getAttribute("userName");
%>

<jsp:include page="../include/header.jsp" />

<script type="text/javascript"> var contexPath = "<%=request.getContextPath()%>";
</script>


<div class="page-wrapper">
	<div class="container-fluid">
		<div class="row mt-1">
			<div class="col-lg-12 d-flex justify-content-between">
				<h2 class="page-header">Users Panel</h2>
				<button type="button" class="btn btn-outline-dark btn-sm" data-toggle="modal" data-target="#exampleModal" title="Search">
								User List<i class="fa fa-search"></i>
							</button>
			</div>
		</div>

		<input type="hidden" id="userId" value="<%=userId%>"> <input
			type="hidden" id="employeeAutoId" value="0">
		<div class="row mt-2">
			<div class="col-lg-12">
				<div class="card">

					<div class="card-body">
						<div class="row">
							<div class="col-sm-5">
								<div class="input-group my-2">
									<input type="text" class="form-control form-control-sm"
										placeholder="Search Employee" aria-describedby="findButton"
										id="employeeId" name="employeeId">
									<div class="input-group-append">
										<button class="btn btn-sm btn-primary" type="button"
											id="employeeSearch" onclick="employeeSearch()">
											<i class="fa fa-search"></i>
										</button>
									</div>
								</div>
								<div class="input-group input-group-sm mb-1">
									<div class="input-group-prepend">
										<span class="input-group-text" id="inputGroup-sizing-sm"><label
											class="my-0" for="name">Name<span style="color: red">*</span></label></span>
									</div>
									<input id="name" type="text" class="form-control"
										aria-label="Sizing example input"
										aria-describedby="inputGroup-sizing-sm" readonly>
								</div>

								<div class="input-group input-group-sm mb-1">
									<div class="input-group-prepend">
										<span class="input-group-text" id="inputGroup-sizing-sm"><label
											class="my-0" for="userName">User Name<span
												style="color: red">*</span></label></span>
									</div>
									<input id="userName" type="text" class="form-control"
										aria-label="Sizing example input"
										aria-describedby="inputGroup-sizing-sm">
								</div>
								<div class="input-group input-group-sm mb-1">
									<div class="input-group-prepend">
										<span class="input-group-text" id="inputGroup-sizing-sm"><label
											class="my-0" for="password" style="width: 120px;">Password<span
												style="color: red">*</span></label></span>
									</div>
									<input id="password" type="password" class="form-control"
										aria-label="Sizing example input"
										aria-describedby="inputGroup-sizing-sm">
								</div>

								<div class="input-group input-group-sm mb-1">
									<div class="input-group-prepend">
										<span class="input-group-text" id="inputGroup-sizing-sm"><label
											class="my-0" for="confirmPassword">Confirm Password<span
												style="color: red">*</span></label></span>
									</div>
									<input id="confirmPassword" type="password"
										class="form-control" aria-label="Sizing example input"
										aria-describedby="inputGroup-sizing-sm">
								</div>

								<div class="input-group input-group-sm mb-1">


									<div class="input-group-prepend">
										<span class="input-group-text" id="inputGroup-sizing-sm"><label
											class='my-0' for="userRole">User Role<span
												style="color: red">*</span></label></span>
									</div>
									<select id="userRole" class="form-control selectpicker"
										aria-label="Sizing example input"
										aria-describedby="inputGroup-sizing-sm"
										data-live-search="true"
										data-style="btn-light btn-sm border-light-gray form-control-sm"
										multiple onchange="loadRolePermissions()">
										<option value="0">Select Role</option>
										<c:forEach items="${roleList}" var="role">
											<option value="${role.roleId}">${role.roleName}</option>
										</c:forEach>
									</select>

								</div>

								<div class="input-group input-group-sm mb-1">
									<div class="input-group-prepend">
										<span class="input-group-text" id="inputGroup-sizing-sm"><label
											class='my-0' for="activeStatus">Active Status<span
												style="color: red">*</span></label></span>
									</div>
									<select id="activeStatus" class="form-control-sm">
										<option value="1">Active</option>

										<option value="0">Inactive</option>
									</select>
								</div>

								<button id="btnRefresh" type="button"
									class="btn btn-secondary btn-sm ml-1" onclick="refreshAction()">
									<i class="fa fa-refresh"></i> Refresh
								</button>
							</div>

							<div class="col-sm-7">
								<div class="card">
									<div class="card-header">
										<strong> User Access </strong>
									</div>
									<div class="card-body py-1">
										<div class="row">
											<div class="col-sm-12 col-md-12 col-lg-12 p-0 m-0"
												style="overflow: auto; max-height: 250px;">
												<table class="table table-hover table-bordered table-sm">
													<thead>
														<tr>
															<th class="text-center">#</th>
															<th>Module</th>
															<th>Sub Name</th>
															<th class="text-center">Add <input class="checkItem"
																type="checkbox" id="checkAllAdd"></th>
															<th class="text-center">Edit <input
																class="checkItem" type="checkbox" id="checkAllEdit"></th>
															<th class="text-center">View <input
																class="checkItem" type="checkbox" id="checkAllView"></th>
															<th class="text-center">Delete <input
																class="checkItem" type="checkbox" id="checkAllDelete"></i></th>

														</tr>
													</thead>
													<tbody id="permissionList">

													</tbody>
												</table>
											</div>
										</div>

									</div>
								</div>
							</div>

							<label for="extraPermissionCheck"><input
								id="extraPermissionCheck" type="checkbox"
								onchange="toggleExtraDiv()"> Extra Permission/Limitation</label>
						</div>

						<div id="extraDiv" class="row" style="display: none;">

							<div class="col-md-12">
								<div class="row">
									<div class="col-md-9">
										<select id="moduleName" class="form-control selectpicker"
											aria-label="Sizing example input" data-size="5"
											data-selected-text-format="count>2" data-actions-box="true"
											aria-describedby="inputGroup-sizing-sm"
											data-live-search="true" onchange="loadExtraPermissionInTable()"
											data-style="btn-light btn-sm border-secondary form-control-sm"
											multiple>
											<option value="0">Select Module</option>
											<c:forEach items="${allModule}" var="allModule">
												<option id="moduleName" value="${allModule.id}">${allModule.modulename}</option>
											</c:forEach>
										</select>
									</div>
								</div>

								<div class="row">
									<div class="col-sm-12 col-md-9 col-lg-9"
										style="overflow: auto; max-height: 250px;">
										<table class="table table-hover table-bordered table-sm">
											<thead>
												<tr>
													<th class="text-center">#</th>
													<th>Module</th>
													<th>Sub Name</th>
													<th class="text-center">Add <input class="checkItem"
														type="checkbox" id="checkAllAdd"></th>
													<th class="text-center">Edit <input class="checkItem"
														type="checkbox" id="checkAllEdit"></th>
													<th class="text-center">View <input class="checkItem"
														type="checkbox" id="checkAllView"></th>
													<th class="text-center">Delete <input
														class="checkItem" type="checkbox" id="checkAllDelete"></i></th>
													<th class="text-center">Permission <input
														class="checkItem" type="checkbox" id="checkAll"></th>
												</tr>
											</thead>
											<tbody id="extraPermissionList">

											</tbody>
										</table>
									</div>
								</div>
							</div>



						</div>

						<div class="row">
							<div class="col-sm-12 col-md-12 text-right">
								<button type="button" id="btnSave"
									class="btn btn-primary btn-sm" onclick="saveAction()"
									accesskey="S">
									<span style="text-decoration: underline;"> Save</span>
								</button>

								<button type="button" id="btnEdit"
									class="btn btn-success btn-sm" onclick="editAction()"
									accesskey="E" hidden="">
									<span style="text-decoration: underline;"> Edit</span>
								</button>
								<button type="button" id="btnRefresh"
									class="btn btn-secondary btn-sm" onclick="refreshAction()">Refresh</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<jsp:include page="../include/footer.jsp" />
<script
	src="${pageContext.request.contextPath}/assets/js/custom/link.js"></script>
<%-- <script
	src="${pageContext.request.contextPath}/assets/js/custom/user.js"></script> --%>
<script
	src="${pageContext.request.contextPath}/assets/js/settings/user-profile-create.js"></script>




