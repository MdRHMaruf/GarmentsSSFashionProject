<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="pg.model.wareinfo"%>
<%@page import="pg.model.module"%>
<%@page import="pg.model.login"%>
<%@page import="java.util.List"%>
<jsp:include page="../include/header.jsp" />

<body onload="allEmployee()">

	<%
		List<login> lg = (List<login>) session.getAttribute("pg_admin");
	%>
	<div class="page-wrapper">
		<div class="content container-fluid">
			<div class="alert alert-success alert-dismissible fade show"
				style="display: none;">
				<p id="successAlert" class="mb-0">
					<strong>Success!</strong> Employee Name Save Successfully..
				</p>
			</div>
			<div class="alert alert-warning alert-dismissible fade show"
				style="display: none;">
				<p id="warningAlert" class="mb-0">
					<strong>Warning!</strong> Employee Name Empty.Please Enter Employee
					Name...
				</p>
			</div>
			<div class="alert alert-danger alert-dismissible fade show"
				style="display: none;">
				<p id="dangerAlert" class="mb-0">
					<strong>Wrong!</strong> Something Wrong...
				</p>
			</div>
			<input type="hidden" id="userId" value="<%=lg.get(0).getId()%>">

			<div class="row">
				<div class="col-sm-12 col-md-12 col-lg-12">
					<div class="card-box">
						<div class="row">
							<div class="col-sm-5 col-md-5 col-lg-5">

								<div class="row ">
									<h3>
										<b>Employee Create</b>
									</h3>
								</div>
								<hr class="mb-1 mt-1">

								<div class="form-group mb-1">
									<label for="employeeCode" class="mb-0">Employee Code</label> <input
										type="text" class="form-control-sm" id="employeeCode"
										name="text">
								</div>

								<div class="form-group mb-1">
									<label for="employeeName" class="mb-0">Employee Name</label> <input
										type="text" class="form-control-sm" id="employeeName"
										name="text">
								</div>

								<div class="form-group mb-1">
									<label for="cardNo" class="mb-0">Card No</label> <input
										type="text" class="form-control-sm" id="cardNo" name="text">
								</div>

								<div class="form-group mb-1">
									<label for="department" class="mb-0">Department</label> <select
										id="department" class="selectpicker form-control"
										data-live-search="true"
										data-style="btn-light btn-sm border-secondary form-control-sm">
										<option>Select Department</option>

										<c:forEach items="${department}" var="department"
											varStatus="counter">
											<option id='departmentName'
												value="${department.departmentId}">${department.departmentName}</option>
										</c:forEach>

									</select>
								</div>
								<div class="form-group mb-1">
									<label for="designation" class="mb-0">Designation</label> <select
										id="designation" class="selectpicker form-control"
										data-live-search="true"
										data-style="btn-light btn-sm border-secondary form-control-sm">
										<option>Select Designation</option>

										<c:forEach items="${designation}" var="designation"
											varStatus="counter">
											<option id='designation' value="${designation.designationId}">${designation.designation}</option>
										</c:forEach>

									</select>
								</div>


								<div class="form-group mb-1">
									<label for="line" class="mb-0">Line</label> <select id="line"
										class="selectpicker form-control" data-live-search="true"
										data-style="btn-light btn-sm border-secondary form-control-sm">
										<option>Select Line</option>

										<c:forEach items="${line}" var="line" varStatus="counter">
											<option id='line' value="${line.lineId}">${line.lineName}</option>
										</c:forEach>

									</select>

									<!-- <input type="text"
									class="form-control-sm" id="line" name="text"> -->
								</div>
								<div class="form-group mb-1">
									<label for="" grade"" class="mb-0">Grade</label> <input
										type="text" class="form-control-sm" id="grade" name="text">
								</div>
								<div class="form-group mb-1">
									<label for="joinDate" class="mb-0">Joining Date</label> <input
										type="date" class="form-control-sm col-sm-12" id="joinDate"
										name="text">
								</div>

								<button type="button" id="btnSave"
									class="btn btn-primary btn-sm" onclick="saveAction()">Save</button>

								<button type="button" id="btnEdit"
									class="btn btn-primary btn-sm" onclick="editAction()" disabled>Edit</button>
								<button type="button" id="btnRefresh"
									class="btn btn-primary btn-sm" onclick="refreshAction()">Refresh</button>

							</div>
							<div class="col-sm-7 col-md-7 col-lg-7 shadow ">
								<div class="input-group my-2">
									<input type="text" class="form-control"
										placeholder="Search Employee" aria-describedby="findButton"
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
													<th scope="col">Employee Name</th>
													<th scope="col">Department</th>
													<th scope="col">Designation</th>
													<th scope="col">edit</th>
												</tr>
											</thead>
											<tbody id="empList">
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
		src="${pageContext.request.contextPath}/assets/js/register/employee_create.js"></script>