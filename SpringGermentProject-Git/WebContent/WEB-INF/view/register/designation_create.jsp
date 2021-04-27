<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="pg.model.WareInfo"%>
<%@page import="pg.model.Module"%>
<%@page import="pg.model.Login"%>
<%@page import="java.util.List"%>
<jsp:include page="../include/header.jsp" />

<%
String userId=(String) request.getAttribute("userId");	
String userName=(String) request.getAttribute("userName");	
%>
<div class="page-wrapper">
	<div class="content container-fluid">
		<div class="alert alert-success alert-dismissible fade show"
			style="display: none;">
			<p id="successAlert" class="mb-0">
				<strong>Success!</strong> Designation Save Successfully..
			</p>
		</div>
		<div class="alert alert-warning alert-dismissible fade show"
			style="display: none;">
			<p id="warningAlert" class="mb-0">
				<strong>Warning!</strong> Designation Empty.Please Enter
				Designation...
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
									<b>Designation Create</b>
								</h2>
							</div>
							<hr>

							<div class="form-group">
								<label for="departmentName">Department Name</label> <select
									id="departmentName" class="selectpicker form-control"
									data-live-search="true"
									data-style="btn-light btn-sm border-secondary form-control-sm">
									<option>Select Department</option>

									<c:forEach items="${department}" var="department"
										varStatus="counter">
										<option id='departmentName' value="${department.departmentId}">${department.departmentName}</option>
									</c:forEach>

								</select>
							</div>

							<div class="form-group">
								<label for="designation">Designation</label> <input type="text"
									class="form-control-sm" id="designation" name="text">
							</div>
							<button type="button" id="btnSave" class="btn btn-primary btn-sm"
								onclick="saveAction()" accesskey="S"><span style="text-decoration:underline;"> Save</span></button>

							<button type="button" id="btnEdit" class="btn btn-success btn-sm"
								onclick="editAction()" style="display: none;">Edit</button>
							<button type="button" id="btnRefresh"
								class="btn btn-secondary btn-sm" onclick="refreshAction()">Refresh</button>

						</div>
						<div class="col-sm-6 col-md-6 col-lg-6 shadow ">
							<div class="input-group my-2">
								<input type="text" class="form-control"
									placeholder="Search Designation" aria-describedby="findButton"
									id="search" name="search">
								<div class="input-group-append">
									<button class="btn btn-primary" type="button" id="findButton">
										<i class="fa fa-search"></i>
									</button>
								</div>
							</div>

							<hr>

							<div class="row">
								<div class="col-sm-12 col-md-12 col-lg-12"
									style="overflow: auto; max-height: 600px;">
									<table class="table table-hover table-bordered table-sm">
										<thead>
											<tr>
												<th scope="col">#</th>
												<th scope="col">Department Name</th>
												<th scope="col">ID</th>
												<th scope="col">Designation</th>
												<th class="text-center" scope="col">Edit</th>
												<th class="text-center" scope="col">Delete</th>
											</tr>
										</thead>
										<tbody id="designationList">
											<tr>
											</tr>
										</tbody>
									</table>
								</div>

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
	src="${pageContext.request.contextPath}/assets/js/register/desgination_create.js"></script>