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

		<div class="content container-fluid">
			<div class="alert alert-success alert-dismissible fade show"
				style="display: none;">
				<p id="successAlert" class="mb-0">
					<strong>Success!</strong> Brand Name Save Successfully..
				</p>
			</div>
			<div class="alert alert-warning alert-dismissible fade show"
				style="display: none;">
				<p id="warningAlert" class="mb-0">
					<strong>Warning!</strong> Brand Name Empty.Please Enter Brand
					Name...
				</p>
			</div>
			<div class="alert alert-danger alert-dismissible fade show"
				style="display: none;">
				<p id="dangerAlert" class="mb-0">
					<strong>Wrong!</strong> Something Wrong...
				</p>
			</div>
			<input type="hidden" id="userId" value="<%=userId%>"> 
			<div class="row">
				<div class="col-sm-12 col-md-12 col-lg-12">
					<div class="card-box">
						<div class="row">
							<div class="col-sm-6 col-md-6 col-lg-6">

								<div class="row ">
									<h2>
										<b>Access Permit Control</b>
									</h2>
								</div>
								<hr>

								<div class="form-group">
									<label for="formName">Form Name:</label> 
									<select id="formName"
												class="form-control selectpicker"
												aria-label="Sizing example input"
												aria-describedby="inputGroup-sizing-sm"
												data-live-search="true"
												data-style="btn-light btn-sm border-secondary form-control-sm" onchange="formSelectChangeAction()">
												<option value="0">Select Form</option>
												<c:forEach items="${menus}" var="menu">
													<option value="${menu.id}">${menu.name}</option>
												</c:forEach>
											</select>
								</div>

								
								<hr>
								<div class="row">
									<div class="col-sm-12 col-md-12">
										<%-- <div class="input-group my-2">
											<select id="permittedUser"
												class="form-control selectpicker"
												aria-label="Sizing example input"
												aria-describedby="inputGroup-sizing-sm"
												data-live-search="true"
												data-style="btn-light btn-sm border-secondary form-control-sm">
												<option value="0">Select Permitted User</option>
												<c:forEach items="${users}" var="user">
													<option value="${user.id}">${user.fullname}</option>
												</c:forEach>
											</select>
											<div class="input-group-append">
												<button class="btn btn-sm btn-primary" type="button" onclick="memberAddAction()">
													<i class="fa fa-plus-square"></i>
												</button>
												<!-- <button class="btn btn-sm btn-primary" type="button"
													data-toggle="modal" data-target="#exampleModal">
													<i class="fa fa-cog"></i>
												</button> -->
											</div>
										</div> --%>

										<div class="row">
											<div class="col-sm-12 col-md-12 col-lg-12"
												style="overflow: auto; max-height: 400px;">
												<table class="table table-hover table-bordered table-sm">
													<thead>
														<tr>
															<th>#</th>
															<th>User Name</th>
															
														</tr>
													</thead>
													<tbody id="permittedUserList">
														<c:forEach items="${permittedUserList}" var="resourceGroup"
															varStatus="counter">
															<tr>
																<td>${counter.count}</td>
																<td>${resource.userName}</td>
															</tr>
														</c:forEach>
													</tbody>
												</table>
											</div>
										</div>
									</div>
								</div>	
							</div>
							<div class="col-sm-6 col-md-6 col-lg-6 shadow ">
								<div class="input-group my-2">
									<input type="text" class="form-control"
										placeholder="Search Group" aria-describedby="findButton"
										id="search" name="search">
									<div class="input-group-append">
										<button class="btn btn-primary" type="button" id="findButton">
											<i class="fa fa-search"></i>
										</button>
									</div>
								</div>
								<hr>
								<div>
									<div class="input-group my-2">
											<select id="permittedUser"
												class="form-control selectpicker"
												aria-label="Sizing example input"
												aria-describedby="inputGroup-sizing-sm"
												data-live-search="true"
												data-style="btn-light btn-sm border-secondary form-control-sm" onchange="formSelectChangeAction()">
												<option value="0">Select Permitted User</option>
												<c:forEach items="${users}" var="user">
													<option value="${user.id}">${user.fullname}</option>
												</c:forEach>
											</select>
											<!-- <div class="input-group-append">
												<button class="btn btn-sm btn-primary" type="button" onclick="memberAddAction()">
													<i class="fa fa-plus-square"></i>
												</button>
												<button class="btn btn-sm btn-primary" type="button"
													data-toggle="modal" data-target="#exampleModal">
													<i class="fa fa-cog"></i>
												</button>
											</div> -->
										</div>
									
								</div>
								<div class="row">
									<div class="col-sm-12 col-md-12 col-lg-12"
										style="overflow: auto; max-height: 400px;">
										<table class="table table-hover table-bordered table-sm">
											<thead>
												<tr>
													<th>#</th>
															<th>Form Name</th>
															<th>ID/No</th>
															<th><input type="checkbox" id="permitAll" onclick="checkAllAction()"></th>
												</tr>
											</thead>
											<tbody id="fileList">
												<c:forEach items="${fileList}" var="file"
													varStatus="counter">
													<tr>
														<td>${counter.count}</td>
														<td>${file.formName}</td>
														<td>${file.fileNo}</td>
														<td><input type="checkbox" id="permit-${file.id}"></td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>

								</div>
								
								<div>
								<button type="button" id="btnSave"
									class="btn btn-primary btn-sm" onclick="saveAction()">Save</button>

								<button type="button" id="btnEdit"
									class="btn btn-success btn-sm" onclick="editAction()"
									style="display: none;">Edit</button>
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
</div>

<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog"
	aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div
		class="modal-dialog modal-lg modal-dialog-centered modal-dialog-scrollable">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title">Resources</h5>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close" onclick="resourceModalCloseAction()">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>

			<div class="modal-body">

				<div class="row">
					<div class="col-md-12">

						<div class="input-group">
							<input id="resourceName" type="text" aria-label="First name"
								class="form-control" placeholder="*Resource Name"> <input
								id="resourceLink" type="text" aria-label="Last name"
								class="form-control" placeholder="*Resource Link">
						</div>
					</div>
				</div>
				<hr>
				<div class="row">
					<div class="col-md-12">
						<table class="table table-hover table-bordered table-sm">
							<thead>
								<tr>
									<th>Resource Name</th>
									<th>Resource Link</th>
									<th><i class="fa fa-edit"> </i></th>
								</tr>
							</thead>
							<tbody id="resourceTableList">
								<c:forEach items="${resourceList}" var="resource"
									varStatus="counter">
									<tr>
										<td id='resourceName${resource.id}'>${resource.resourceName}</td>
										<td id='resourceLink${resource.id}'>${resource.resourceLink}</td>
										`
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>

			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary btn-sm"
					data-dismiss="modal" onclick="resourceModalCloseAction()">
					<i class="fa fa-close"></i> Close
				</button>
				<button type="button" id="btnResourceSave"
					class="btn btn-primary btn-sm" onclick="resourceSaveAction()">
					<i class="fas fa-save"></i> Save as Resource
				</button>
				<button type="button" id="btnResourceEdit"
					class="btn btn-success btn-sm" onclick="resourceEditAction()"
					style="display: none;">
					<i class="fa fa-pencil-square"></i> Edit Resource Name
				</button>
			</div>
		</div>
	</div>
</div>
<jsp:include page="../include/footer.jsp" />
<script
	src="${pageContext.request.contextPath}/assets/js/settings/access-permit.js"></script>




