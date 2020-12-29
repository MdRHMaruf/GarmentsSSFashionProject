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
	List<Login> lg = (List<Login>) session.getAttribute("pg_admin");
%>
<div class="page-wrapper">
	<div class="content container-fluid">
		<div class="alert alert-success alert-dismissible fade show"
			style="display: none;">
			<p id="successAlert" class="mb-0">
				<strong>Success!</strong> Ware House Name Save Successfully..
			</p>
		</div>
		<div class="alert alert-warning alert-dismissible fade show"
			style="display: none;">
			<p id="warningAlert" class="mb-0">
				<strong>Warning!</strong> Ware House Name Empty.Please Enter
				Ware House Name...
			</p>
		</div>
		<div class="alert alert-danger alert-dismissible fade show"
			style="display: none;">
			<p id="dangerAlert" class="mb-0">
				<strong>Wrong!</strong> Something Wrong...
			</p>
		</div>
		<input type="hidden" id="userId" value="<%=lg.get(0).getId()%>">
		<input type="hidden" id="wareHouseId" value="0">

		<div class="row">
			<div class="col-sm-12 col-md-12 col-lg-12">
				<div class="card-box">
					<div class="row">
						<div class="col-sm-6 col-md-6 col-lg-6">

							<div class="row ">
								<h2>
									<b>Ware House Create</b>
								</h2>
							</div>
							<hr>

							<div class="form-group">
								<label for="factoryName">Factory Name:</label> <select
									class="form-control" id="factoryName">
									<option value="0">Select Factory</option>
									<c:forEach items="${factoryList}" var="factory"
										varStatus="counter">
										<option id='factoryName' value="${factory.factoryId}">${factory.factoryName}</option>
									</c:forEach>


								</select>
							</div>
							<div class="form-group">
								<label for="wareHouseName">Ware House Name:</label> <input
									type="text" class="form-control" id="wareHouseName"
									name="text">
							</div>
							<button type="button" id="btnSave" class="btn btn-primary btn-sm"
								onclick="saveAction()">Save</button>

							<button type="button" id="btnEdit" class="btn btn-primary btn-sm"
								onclick="editAction()" disabled>Edit</button>
							<button type="button" id="btnRefresh"
								class="btn btn-primary btn-sm" onclick="refreshAction()">Refresh</button>

						</div>
						<div class="col-sm-6 col-md-6 col-lg-6 shadow ">
							<div class="input-group my-2">
								<input type="text" class="form-control"
									placeholder="Search Ware House" aria-describedby="findButton"
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
												<th scope="col">Factory Name</th>
												<th scope="col">Ware House Name</th>
												<th scope="col">edit</th>
											</tr>
										</thead>
										<tbody id="dataList">
											<c:forEach items="${wareHouseList}" var="wareHouse"
												varStatus="counter">
												<tr>
													<td>${wareHouse.wareHouseId}</td>
													<td id='factoryName${wareHouse.wareHouseId}'>${wareHouse.factoryName}</td>
													<td id='wareHouseName${wareHouse.wareHouseId}'>${wareHouse.wareHouseName}</td>
													<td><i class="fa fa-edit"
														onclick="setData(${wareHouse.wareHouseId},${wareHouse.factoryId})"> </i></td>
												</tr>
											</c:forEach>
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
	src="${pageContext.request.contextPath}/assets/js/register/ware-house-create.js"></script>