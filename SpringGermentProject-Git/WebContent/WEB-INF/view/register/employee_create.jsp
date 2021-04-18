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
	String userId = (String) request.getAttribute("userId");
	String userName = (String) request.getAttribute("userName");
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
		<input type="hidden" id="userId" value="<%=userId%>">

		<div class="row">
			<div class="col-sm-12 col-md-12 col-lg-12">
				<div class="card-box">
					<div class="row">
						<div class="col-sm-6 col-md-6 col-lg-6">

							<div class="row ">
								<h3>
									<b>Employee Create</b>
								</h3>
							</div>
							<hr class="mb-1 mt-1">
							<div class="row">

								<div class="col-md-6">
									<label for="employeeCode" class="mb-0">Employee Code</label> <input
										type="text" class="form-control-sm" id="employeeCode"
										name="text">
								</div>

								<div class="col-md-6">
									<label for="employeeName" class="mb-0">Employee Name</label> <input
										type="text" class="form-control-sm" id="employeeName"
										name="text">
								</div>

							</div>

							<div class="row">

								<div class="col-md-6">
									<label for="cardNo" class="mb-0">Card No</label> <input
										type="text" class="form-control-sm" id="cardNo" name="text">
								</div>

								<div class="col-md-6">
									<label for="department" class="mb-0">Department</label> <select
										id="department" class="selectpicker form-control"
										data-live-search="true"
										data-style="btn-light btn-sm border-secondary form-control-sm">
										<option value="0">Select Department</option>

										<c:forEach items="${department}" var="department"
											varStatus="counter">
											<option value="${department.departmentId}">${department.departmentName}</option>
										</c:forEach>

									</select>
								</div>

							</div>

							<div class="row">

								<div class="col-md-6">
									<label for="designation" class="mb-0">Designation</label> <select
										id="designation" class="selectpicker form-control"
										data-live-search="true"
										data-style="btn-light btn-sm border-secondary form-control-sm">
										<option value="0">Select Designation</option>

										<c:forEach items="${designation}" var="designation"
											varStatus="counter">
											<option value="${designation.designationId}">${designation.designation}</option>
										</c:forEach>

									</select>
								</div>


								<div class="col-md-6">
									<label for="line" class="mb-0">Line</label> <select id="line"
										class="selectpicker form-control" data-live-search="true"
										data-style="btn-light btn-sm border-secondary form-control-sm">
										<option value="0">Select Line</option>

										<c:forEach items="${line}" var="line" varStatus="counter">
											<option value="${line.lineId}">${line.lineName}</option>
										</c:forEach>
									</select>
								</div>

							</div>

							<div class="row">

								<div class="col-md-6">
									<label for="" grade"" class="mb-0">Grade</label> <input
										type="text" class="form-control-sm" id="grade" name="text">
								</div>
								<div class="col-md-6">
									<label for="joinDate" class="mb-0">Joining Date</label> <input
										type="date" class="form-control-sm col-sm-12 customDate" id="joinDate"  data-date-format="DD MMM YYYY"
										name="text">
								</div>
							</div>

							<div class="row">

								<div class="col-md-6">
									<label class="mb-0">Religion</label> <select
										class="form-control form-control-sm" id="religion">
										<option>Islam</option>
										<option>Hinduism</option>
										<option>Buddhism</option>
										<option>Christian</option>
										<option>Other</option>
									</select>
								</div>
								<div class="col-md-6">
									<label class="mb-0">Gender</label> <select
										class="form-control form-control-sm" id="gender">
										<option>Male</option>
										<option>Female</option>
									</select>
								</div>
							</div>

							<div class="row">

								<div class="col-md-6">
									<label class="mb-0">Email</label> <input type="text"
										class="form-control-sm" id="email" name="text">
								</div>
								<div class="col-md-6">
									<label for="joinDate" class="mb-0">Contact No</label> <input
										type="text" class="form-control-sm col-sm-12" id="contact"
										name="text"
										oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');">
								</div>
							</div>

							<div class="row">

								<div class="col-md-6">
									<label class="mb-0">Nationality</label> <input type="text"
										class="form-control-sm col-sm-12" id="nationality" name="text">
								</div>
								<div class="col-md-6">
									<label for="joinDate" class="mb-0">National Id</label> <input
										type="text" class="form-control-sm col-sm-12" id="nationalId"
										name="text">
								</div>
							</div>

							<div class="row">

								<div class="col-md-6">
									<label for="BirthDate" class="mb-0">BirthDate</label> <input
										type="date" class="form-control-sm col-sm-12 customDate" id="birthDate"  data-date-format="DD MMM YYYY"
										name="text">
								</div>
								<div class="col-md-6"></div>
							</div>

							<div class="row mt-1">
								<div class="col-md-12">
									<button type="button" id="btnSave"
										class="btn btn-primary btn-sm" onclick="saveAction()"
										accesskey="S">
										<span style="text-decoration: underline;"> Save</span>
									</button>
									<button type="button" id="btnEdit"
										class="btn btn-success btn-sm" onclick="editAction()"
										style="display: none;">Edit</button>
									<button type="button" id="btnRefresh"
										class="btn btn-secondary btn-sm" onclick="refreshAction()">Refresh</button>
								</div>
							</div>

						</div>
						<div class="col-sm-6 col-md-6 col-lg-6 shadow ">
							<div class="input-group my-2">
								<input type="text" class="form-control form-control-sm"
									placeholder="Search Employee" aria-describedby="findButton"
									id="search" name="search">
								<div class="input-group-append">
									<button class="btn btn-sm btn-primary" type="button"
										id="findButton">
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