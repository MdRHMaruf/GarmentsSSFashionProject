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
				<strong>Success!</strong> Machine Name Save Successfully..
			</p>
		</div>
		<div class="alert alert-warning alert-dismissible fade show"
			style="display: none;">
			<p id="warningAlert" class="mb-0">
				<strong>Warning!</strong> Machine Name Empty.Please Enter Machine
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
						<div class="col-sm-5 col-md-5 col-lg-5">

							<div class="row ">
								<h2>
									<b>Machine Create</b>
								</h2>
							</div>

							<hr class="mb-1 mt-1">

							<div class="form-group mb-1">
								<label for="name" class="mb-0">Name</label>
								<%-- <select id="name"
									class="selectpicker form-control" data-live-search="true"
									data-style="btn-light btn-sm border-secondary form-control-sm">
									<option>Select Machnine</option> --%>
								<input class="form-control-sm" type="text" id="name">
								<%-- <c:forEach items="${shift}" var="shift" varStatus="counter">
										<option id='shift' value="${shift.shiftSl}">${shift.shiftName}</option>
										<option id='shiftName' value="${shift.shiftSl}">${shift.shiftName}</option>
									</c:forEach> --%>

								</select>
							</div>

							<div class="form-group mb-0">
								<label for="brand" class="mb-0">Brand</label> <input type="text"
									class="form-control-sm" id="brand" name="text"> <label
									for="modelNo" class="mb-0">Model No</label> <input type="text"
									class="form-control-sm" id="modelNo" name="text">
							</div>


							<div class="form-group">
								<label for="motor" class="mb-0">Motor</label> <input type="text"
									class="form-control-sm" id="motor" name="text"> <label
									for="department" class="mb-0">Factory</label> <select
									id="factoryId" class="selectpicker form-control"
									data-live-search="true"
									data-style="btn-light btn-sm border-secondary form-control-sm"
									onchange="factoryWiseLine()">
									<option value="0">Select Factory</option>
									<c:forEach items="${factorylist}" var="factory"
										varStatus="counter">
										<option id='factoryId' value="${factory.factoryId}">${factory.factoryName}</option>
									</c:forEach>

								</select> <label for="department" class="mb-0">Department</label> <select
									id="departmentId" class="selectpicker form-control"
									data-live-search="true"
									data-style="btn-light btn-sm border-secondary form-control-sm"
									onchange="departmentWiseLine()">
									<option>Select Department</option>


								</select> <label for="employee" class="mb-0">Employee</label> <select
									id="employee" class="selectpicker form-control"
									data-live-search="true"
									data-style="btn-light btn-sm border-secondary form-control-sm">
									<option>Select Employee</option>

									<c:forEach items="${employee}" var="employee"
										varStatus="counter">
										<option id='employee' value="${employee.employeeId}">${employee.employeeName}</option>
									</c:forEach>

								</select> <label for="LineId" class="mb-0">Line</label> <select
									id="lineId" class="selectpicker form-control"
									data-live-search="true"
									data-style="btn-light btn-sm border-secondary form-control-sm">




								</select>
							</div>


							<button type="button" id="btnSave" class="btn btn-primary btn-sm" accesskey="S"
								onclick="saveAction()"><span style="text-decoration:underline;"> Save</span></button>

							<button type="button" id="btnEdit" class="btn btn-success btn-sm"
								onclick="editAction()" style="display: none;">Edit</button>
							<button type="button" id="btnRefresh"
								class="btn btn-secondary btn-sm" onclick="refreshAction()">Refresh</button>

						</div>
						<div class="col-sm-7 col-md-7 col-lg-7 shadow ">
							<div class="input-group my-2">
								<input type="text" class="form-control"
									placeholder="Search Machine" aria-describedby="findButton"
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
												<th scope="col">Name</th>
												<th scope="col">Model</th>
												<th scope="col">Operator</th>
												<th class="text-center" scope="col">Edit</th>
												<th class="text-center" scope="col">Delete</th>
											</tr>
										</thead>
										<tbody id="machineList">
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
	src="${pageContext.request.contextPath}/assets/js/register/machine_create.js"></script>
