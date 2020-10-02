<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="pg.services.SettingServiceImpl"%>
<%@page import="pg.model.wareinfo"%>
<%@page import="pg.model.module"%>
<%@page import="pg.model.login"%>
<%@page import="java.util.List"%>
<jsp:include page="../include/header.jsp" />
<script type="text/javascript"> var contexPath = "<%=request.getContextPath() %>";
</script>

<div class="page-wrapper">
	<div class="content container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h1 class="page-header">Users Panel</h1>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-12">
				<div class="col-lg-8">

					<button type="button" id="Popup" class="btn btn-primary"
						data-toggle="modal" data-target=".bs-example-modal-Popup"
						data-hd="0" data-id="0">Create Warehouse</button>
					<button type="button" id="Popup" class="btn btn-primary"
						data-toggle="modal" data-target=".bs-example-modal-Store"
						data-hd="0" data-id="0">Create Store</button>
					<button type="button" id="Popup" class="btn btn-primary"
						data-toggle="modal" data-target=".bs-example-modal-Module"
						data-hd="0" data-id="0">Create Module</button>
					<button type="button" id="Popup" class="btn btn-primary"
						data-toggle="modal" data-target=".bs-example-modal-Menu"
						data-hd="0" data-id="0">Create Menu</button>
					<button type="button" id="Popup" class="btn btn-primary"
						data-toggle="modal" data-target=".bs-example-modal-SubMenu"
						data-hd="0" data-id="0">Create Sub Menu</button>


				</div>


			</div>

		</div>



		<div class="row">


			<div class="col-lg-12">

				<div class="panel panel-default">

					<div class="panel-heading">Create User</div>
					<div class="panel-body">
						<div class="col-sm-6">
							<form class="form-horizontal bucket-form" method="post"
								action="<?php echo base_url(); ?>/create_user">
								<div class="form-group">
									<label class="col-sm-2 control-label">Name</label>
									<div class="col-sm-4">
										<input type="text" id="user_title" class="form-control">
									</div>

								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">User Name(Login)</label>
									<div class="col-sm-4">
										<input type="text" id="user" class="form-control">
									</div>

								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">Password</label>
									<div class="col-sm-4">
										<input type="password" id="password" class="form-control">
									</div>
									<div class="col-sm-2">
										<input type="checkbox" id="sp">Show Password
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">Module</label>
									<div class="col-sm-4">
									
									<c:forEach items="${modulelist}" var="v" varStatus="counter">
											<input type="hidden" id="user_hidden"
												value="${v.id}">
											<input id="permissionmodule_${v.id}"
												name="permissionmoduleg" class="permissionmoduleg"
												value="${v.ware}:${v.id}" type="checkbox" />
											<label for="permissionmodule_${v.id}">${v.modulename}</label>

										</c:forEach>	
	
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">Type</label>
									<div class="col-sm-4">
										<select id="type" class="form-control" name="type"
											onchange="type_per()">
											<option value="3">USER</option>
											<option value="1">SUPER ADMIN</option>
											<option value="2">ADMIN</option>
										</select>
										<div class="model"></div>
									</div>
								</div>

 
								<div class="form-group">
									<label class="col-sm-2 control-label">Warehouse</label>
									<div class="col-sm-10">
									
									<c:forEach items="${warelist}" var="v" varStatus="counter">

											<input id="permissionware_${v.id}"
												name="permissionware" class="permissionware"
												value="${v.id}" type="checkbox" />
											<label for="permissionware_${v.id}">${v.name}</label>

										</c:forEach>	
	
									</div>
								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label">Permission</label>
									<div class="col-sm-6">
										<input name="active" value="1" type="radio" checked>
										Active <input name="active" value="0" type="radio">Inactive
									</div>
								</div>
								<div class="col-sm-6" style="text-align: center">
									<div class="btn btn-primary">
										<button type="button" id="con" onclick="create_user()"
											class="btn btn-success">Confirm</button>
									</div>
								</div>
								
							</form>
						</div>

						<div class="col-sm-6">


							<div class="panel panel-default">

								<div class="panel-heading">
									<strong> User Access <strong>
								</div>
								
								<div class="panel-body" id="per" style="padding-left: 20px">
										
	
								</div>

							</div>

						</div>



					</div>


				</div>

			</div>

		</div>


	</div>


	<div id="modals">
		<div class="col-sm-4"></div>
		<div class="col-sm-4" style="margin-top: 15%;">
			<img style="display: none;" class="img"
				src="${pageContext.request.contextPath}/assets/img/715.gif" title="Loading........" />
		</div>
		<div class="col-sm-4"></div>
	</div>

</div>

<div id="modalWare" class="modal fade bs-example-modal-Popup" tabindex="-1"
	role="dialog" aria-labelledby="myLargeModalLabel">
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
								<div class="col-md-6">
									<div class="form-group">
										<label>Ware Name</label> <input type="text" id="wname"
											class="form-control">
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label>Theme</label> <input type="text" id="wtheme"
											class="form-control">
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label>Address</label>
										<textarea id="waddress" class="form-control" rows="9"></textarea>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label>Phone</label> <input type="text" id="wphone"
											class="form-control">
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label>Vat</label> <input type="text" id="wvat"
											class="form-control">
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>

			</div>
			<span class="register_error col-md-12"></span>
			<div class="clearfix"></div>
			<div class="modal-footer">
				<button onclick="addWare()" id="WareSubmitBtn" class="btn btn-primary">Submit</button>
				<button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
			</div>
		</div>

	</div>
</div>


<!-- Store -->
<div id="modalStore" class="modal fade bs-example-modal-Store" tabindex="-1"
	role="dialog" aria-labelledby="mySmallModalLabel">
	<div class="modal-dialog modal-sm" role="document">
		<div class="modal-content">
			<div class="model_header_me modal-header">
				<h4 class="modal-title" id="exampleModalLabel">Store ADD</h4>
			</div>

			<div class="modal-body">
				<div class="row">
					<div class="col-lg-12 col-md-12">
						<form action="#" class="form-horizontal">
							<div class="form-group">
								<label class="col-md-3 control-label">Store Name</label>
								<div class="col-md-9">
									<input type="text" id="s_name" class="form-control">
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label">Remarks</label>
								<div class="col-md-9">
									<textarea id="s_remarks" class="form-control" rows="2"></textarea>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label">Warehouse Name</label>
								<div class="col-md-9">
									<select class="form-control s_ware">
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
				<button onclick="addStore()"  id="s_submit" class="btn btn-success">Submit</button>
				<button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
			</div>
		</div>
	</div>
</div>

<!-- Module -->
<div id="modalModule" class="modal fade bs-example-modal-Module" tabindex="-1"
	role="dialog" aria-labelledby="mySmallModalLabel">
	<div class="modal-dialog modal-sm" role="document">
		<div class="modal-content">
			<div class="model_header_me modal-header">
				<h4 class="modal-title" id="exampleModalLabel">Module ADD</h4>
			</div>

			<div class="modal-body">
				<div class="row">
					<div class="col-lg-12 col-md-12">
						<form action="#" class="form-horizontal">
							<div class="form-group">
								<label class="col-md-3 control-label">Module Name</label>
								<div class="col-md-9">
									<input type="text" id="s_modulename" class="form-control">
								</div>
							</div>
							<div class="form-group">
									<label class="col-sm-3 control-label">Permission</label>
									<div class="col-sm-8">
										<input name="module_active" value="1" type="radio" checked>
										Active <input name="module_active" value="0" type="radio">Inactive
									</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label">Warehouse Name</label>
								<div class="col-md-9">
									<select class="form-control s_ware">
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
				<button onclick="addModule()"  id="s_submit" class="btn btn-success">Submit</button>
				<button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
			</div>
		</div>
	</div>
</div>

<!-- Menu -->
<div id="modalMenu" class="modal fade bs-example-modal-Menu" tabindex="-1"
	role="dialog" aria-labelledby="mySmallModalLabel">
	<div class="modal-dialog modal-sm" role="document">
		<div class="modal-content">
			<div class="model_header_me modal-header">
				<h4 class="modal-title" id="exampleModalLabel">Menu ADD</h4>
			</div>

			<div class="modal-body">
				<div class="row">
					<div class="col-lg-12 col-md-12">
						<form action="#" class="form-horizontal">
							<div class="form-group">
								<label class="col-md-3 control-label">Menu Name</label>
								<div class="col-md-9">
									<input type="text" id="s_menuname" class="form-control">
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label">Module Name</label>
								<div class="col-md-9">
									<select class="form-control m_module">
										<option value="0">Select Module</option>
										<c:forEach items="${modulelist}" var="module" varStatus="counter">
											<option id='m_module' value="${module.id}">${module.modulename}</option>
										</c:forEach>
										
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label">Warehouse Name</label>
								<div class="col-md-9">
									<select class="form-control m_ware">
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
				<button onclick="addMenu()"  id="s_submit" class="btn btn-success">Submit</button>
				<button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
			</div>
		</div>
	</div>
</div>


<!--Sub Menu -->
<div id="modalSubMenu" class="modal fade bs-example-modal-SubMenu" tabindex="-1"
	role="dialog" aria-labelledby="mySmallModalLabel">
	<div class="modal-dialog modal-md" role="document">
		<div class="modal-content">
			<div class="model_header_me modal-header">
				<h4 class="modal-title" id="exampleModalLabel">Sub Menu ADD</h4>
			</div>

			<div class="modal-body">
				<div class="row">
					<div class="col-lg-12 col-md-12">
						<form action="#" class="form-horizontal">
							<div class="form-group">
								<label class="col-md-3 control-label">Sub-Menu Name</label>
								<div class="col-md-9">
									<input type="text" id="sb_submenuname" class="form-control">
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label">Links</label>
								<div class="col-md-9">
									<input type="text" id="sb_link" class="form-control">
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label">Menu Name</label>
								<div class="col-md-9">
									<select class="form-control sb_menu">
										<option value="0">Select Module</option>
										<c:forEach items="${menulist}" var="menu" varStatus="counter">
											<option id='sb_menu' value="${menu.id}">${menu.name}</option>
										</c:forEach>
										
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label">Module Name</label>
								<div class="col-md-9">
									<select class="form-control sb_module">
										<option value="0">Select Module</option>
										<c:forEach items="${modulelist}" var="module" varStatus="counter">
											<option id='sb_module' value="${module.id}">${module.modulename}</option>
										</c:forEach>
										
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label">Warehouse Name</label>
								<div class="col-md-9">
									<select class="form-control sb_ware">
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
				<button onclick="addSubMenu()"  id="s_submit" class="btn btn-success">Submit</button>
				<button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
			</div>
		</div>
	</div>
</div>

<jsp:include page="../include/footer.jsp" />
<script src="${pageContext.request.contextPath}/assets/js/custom/link.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/custom/user.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/custom/setting.js"></script>




