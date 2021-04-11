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
			<div class="col-lg-12">
				<h2 class="page-header">Users Panel</h2>
			</div>
		</div>

		<input type="hidden" id="userId" value="<%=userId%>">
		<input type="hidden" id="employeeAutoId" value="0">
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
											data-style="btn-light btn-sm border-light-gray form-control-sm" multiple>
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
										<strong> User Access <strong>
									</div>
									<div class="card-body" id="per"></div>
								</div>
							</div>
							
							<label for="extraPermissionCheck"><input id="extraPermissionCheck" type="checkbox" onchange="toggleExtraDiv()"> Extra Permission/Limitation</label>
						</div>
						
						<div id="extraDiv" class="row" style="display: none;">
							 <div class="col-sm-12 col-md-6 col-lg-6"
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
															<th class="text-center">Permission <input
																class="checkItem" type="checkbox" id="checkAll"></th>
														</tr>
													</thead>
													<tbody id="roleList">

													</tbody>
												</table>
											</div>
						</div>
					</div>

				</div>

			</div>

		</div>


	</div>


	<%-- <div id="modals">
		<div class="col-sm-4"></div>
		<div class="col-sm-4" style="margin-top: 15%;">
			<img style="display: none;" class="img"
				src="${pageContext.request.contextPath}/assets/img/715.gif"
				title="Loading........" />
		</div>
		<div class="col-sm-4"></div>
	</div> --%>

</div>

<div id="modalWare" class="modal fade bs-example-modal-Popup"
	tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
	<div class="modal-dialog modal-lg" role="document">

		<div class="modal-content">
			<div class="model_header_me modal-header">
				<h4 class="modal-title" id="exampleModalLabel">Ware Information</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-lg-12 col-md-12">
						<form action="#">
							<div class="row">
								<label class="col-sm-2">Ware Name</label>
								<div class="col-sm-4">
									<input type="text" id="wname" class="form-control-sm">
								</div>

								<label class="col-sm-2">Theme</label>
								<div class="col-sm-4">
									<input type="text" id="wtheme" class="form-control-sm">
								</div>
							</div>

							<div class="row mt-1">

								<label class="col-sm-2">Address</label>
								<div class="col-sm-10">
									<textarea id="waddress" class="form-control" rows="2"></textarea>
								</div>
							</div>

							<div class="row mt-1">
								<label class="col-md-2">Phone</label>
								<div class="col-md-4">
									<input type="text" id="wphone" class="form-control-sm">
								</div>
								<label class="col-md-2">Vat</label>
								<div class="col-md-4">
									<input type="text" id="wvat" class="form-control-sm">
								</div>

							</div>

						</form>
					</div>
				</div>

			</div>
			<span class="register_error col-md-12"></span>
			<div class="clearfix"></div>
			<div class="modal-footer">
				<button onclick="addWare()" id="WareSubmitBtn"
					class="btn btn-sm btn-primary">Submit</button>
				<button type="button" class="btn btn-sm btn-danger"
					data-dismiss="modal">Close</button>
			</div>
		</div>

	</div>
</div>


<!-- Store -->
<div id="modalStore" class="modal fade bs-example-modal-Store"
	tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
	<div class="modal-dialog modal-md" role="document">
		<div class="modal-content">
			<div class="model_header_me modal-header">
				<h4 class="modal-title" id="exampleModalLabel">Store ADD</h4>
			</div>

			<div class="modal-body">
				<div class="row">
					<div class="col-lg-12 col-md-12">
						<form action="#" class="form-horizontal">
							<div class="row">
								<label class="col-md-4">Store Name</label>
								<div class="col-md-8">
									<input type="text" id="s_name" class="form-control-sm">
								</div>
							</div>
							<div class="row mt-1">
								<label class="col-md-4">Remarks</label>
								<div class="col-md-8">
									<textarea id="s_remarks" class="form-control" rows="2"></textarea>
								</div>
							</div>
							<div class="row mt-1">
								<label class="col-md-4">Warehouse Name</label>
								<div class="col-md-8">
									<select class="form-control form-control-sm s_ware">
										<option value="0">Select Warehouse</option>
										<c:forEach items="${warelist}" var="ware" varStatus="counter">
											<option id='s_ware' value="${ware.id}">${ware.name}</option>
										</c:forEach>

									</select>
								</div>
							</div>
						</form>
					</div>
				</div>


			</div>

			<span class="register_error col-md-12"></span>
			<div class="clearfix"></div>
			<div class="modal-footer">
				<button onclick="addStore()" id="s_submit"
					class="btn btn-sm btn-success">Submit</button>
				<button type="button" class="btn btn-sm btn-danger"
					data-dismiss="modal">Close</button>
			</div>
		</div>
	</div>
</div>

<!-- Module -->
<div id="modalModule" class="modal fade bs-example-modal-Module"
	tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
	<div class="modal-dialog modal-md" role="document">
		<div class="modal-content">
			<div class="model_header_me modal-header">
				<h4 class="modal-title" id="exampleModalLabel">Module ADD</h4>
			</div>

			<div class="modal-body">
				<div class="row">
					<div class="col-lg-12 col-md-12">
						<form action="#" class="form-horizontal">
							<div class="row">
								<label class="col-md-4">Module Name</label>
								<div class="col-md-8">
									<input type="text" id="s_modulename" class="form-control-sm">
								</div>
							</div>
							<div class="row mt-1">
								<label class="col-sm-4">Permission</label>
								<div class="col-sm-8">
									<input name="module_active" value="1" type="radio" checked>
									Active <input name="module_active" value="0" type="radio">Inactive
								</div>
							</div>
							<div class="row mt-1">
								<label class="col-md-4">Warehouse Name</label>
								<div class="col-md-8">
									<select class="form-control form-control-sm s_ware">
										<option value="0">Select Warehouse</option>
										<c:forEach items="${warelist}" var="ware" varStatus="counter">
											<option id='s_ware' value="${ware.id}">${ware.name}</option>
										</c:forEach>

									</select>
								</div>
							</div>
						</form>
					</div>
				</div>


			</div>

			<span class="register_error col-md-12"></span>
			<div class="clearfix"></div>
			<div class="modal-footer">
				<button onclick="addModule()" id="s_submit"
					class="btn btn-sm btn-success">Submit</button>
				<button type="button" class="btn btn-sm btn-danger"
					data-dismiss="modal">Close</button>
			</div>
		</div>
	</div>
</div>

<!-- Menu -->
<div id="modalMenu" class="modal fade bs-example-modal-Menu"
	tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
	<div class="modal-dialog modal-md" role="document">
		<div class="modal-content">
			<div class="model_header_me modal-header">
				<h4 class="modal-title" id="exampleModalLabel">Menu ADD</h4>
			</div>

			<div class="modal-body">
				<div class="row">
					<div class="col-lg-12 col-md-12">
						<form action="#" class="form-horizontal">
							<div class="row">
								<label class="col-md-4">Menu Name</label>
								<div class="col-md-8">
									<input type="text" id="s_menuname" class="form-control-sm">
								</div>
							</div>
							<div class="row mt-1">
								<label class="col-md-4">Module Name</label>
								<div class="col-md-8">
									<select class="form-control form-control-sm m_module">
										<option value="0">Select Module</option>
										<c:forEach items="${modulelist}" var="module"
											varStatus="counter">
											<option id='m_module' value="${module.id}">${module.modulename}</option>
										</c:forEach>

									</select>
								</div>
							</div>
							<div class="row mt-1">
								<label class="col-md-4">Warehouse Name</label>
								<div class="col-md-8">
									<select class="form-control form-control-sm m_ware">
										<option value="0">Select Warehouse</option>
										<c:forEach items="${warelist}" var="ware" varStatus="counter">
											<option id='m_ware' value="${ware.id}">${ware.name}</option>
										</c:forEach>

									</select>
								</div>
							</div>
						</form>
					</div>
				</div>


			</div>

			<span class="register_error col-md-12"></span>
			<div class="clearfix"></div>
			<div class="modal-footer">
				<button onclick="addMenu()" id="s_submit"
					class="btn btn-sm btn-success">Submit</button>
				<button type="button" class="btn btn-sm btn-danger"
					data-dismiss="modal">Close</button>
			</div>
		</div>
	</div>
</div>


<!--Sub Menu -->
<div id="modalSubMenu" class="modal fade bs-example-modal-SubMenu"
	tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
	<div class="modal-dialog modal-md" role="document">
		<div class="modal-content">
			<div class="model_header_me modal-header">
				<h4 class="modal-title" id="exampleModalLabel">Sub Menu ADD</h4>
			</div>

			<div class="modal-body">
				<div class="row">
					<div class="col-lg-12 col-md-12">
						<form action="#" class="form-horizontal">
							<div class="row">
								<label class="col-md-4">Sub-Menu Name</label>
								<div class="col-md-8">
									<input type="text" id="sb_submenuname" class="form-control-sm">
								</div>
							</div>
							<div class="row mt-1">
								<label class="col-md-4">Links</label>
								<div class="col-md-8">
									<input type="text" id="sb_link" class="form-control-sm">
								</div>
							</div>
							<div class="row mt-1">
								<label class="col-md-4">Menu Name</label>
								<div class="col-md-8">
									<select class="form-control form-control-sm sb_menu">
										<option value="0">Select Module</option>
										<c:forEach items="${menulist}" var="menu" varStatus="counter">
											<option id='sb_menu' value="${menu.id}">${menu.name}</option>
										</c:forEach>

									</select>
								</div>
							</div>
							<div class="row mt-1">
								<label class="col-md-4">Module Name</label>
								<div class="col-md-8">
									<select class="form-control form-control-sm sb_module">
										<option value="0">Select Module</option>
										<c:forEach items="${modulelist}" var="module"
											varStatus="counter">
											<option id='sb_module' value="${module.id}">${module.modulename}</option>
										</c:forEach>

									</select>
								</div>
							</div>
							<div class="row mt-1">
								<label class="col-md-4">Warehouse Name</label>
								<div class="col-md-8">
									<select class="form-control form-control-sm sb_ware">
										<option value="0">Select Warehouse</option>
										<c:forEach items="${warelist}" var="ware" varStatus="counter">
											<option id='sb_ware' value="${ware.id}">${ware.name}</option>
										</c:forEach>

									</select>
								</div>
							</div>
						</form>
					</div>
				</div>


			</div>

			<span class="register_error col-md-12"></span>
			<div class="clearfix"></div>
			<div class="modal-footer">
				<button onclick="addSubMenu()" id="s_submit"
					class="btn btn-sm btn-success">Submit</button>
				<button type="button" class="btn btn-sm btn-danger"
					data-dismiss="modal">Close</button>
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




