<%@page import="pg.share.ItemType"%>
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
				<strong>Success!</strong> Accessories Item Name Save Successfully..
			</p>
		</div>
		<div class="alert alert-warning alert-dismissible fade show"
			style="display: none;">
			<p id="warningAlert" class="mb-0">
				<strong>Warning!</strong> Accessories Item Name Empty.Please Enter
				Accessories Item Name...
			</p>
		</div>
		<div class="alert alert-danger alert-dismissible fade show"
			style="display: none;">
			<p id="dangerAlert" class="mb-0">
				<strong>Wrong!</strong> Something Wrong...
			</p>
		</div>
		<input type="hidden" id="userId" value="<%=lg.get(0).getId()%>">
		<input type="hidden" id="itemType" value="<%=ItemType.ACCESSORIES.getType()%>">
		<input type="hidden" id="accessoriesItemId" value="0">

		<div class="row">
			<div class="col-sm-12 col-md-12 col-lg-12">
				<div class="card-box">
					<div class="row">
						<div class="col-sm-6 col-md-6 col-lg-6">

							<div class="row ">
								<h2>
									<b>Accessories Item Create</b>
								</h2>
							</div>
							<hr>

							<div class="form-group">
								<label for="accessoriesItemName">Accessories Item Name:</label>
								<input type="text" class="form-control" id="accessoriesItemName"
									name="text">
							</div>


							<div class="form-group">
								<label for="accessoriesItemCode">Accessories Item Code:</label>
								<input type="text" class="form-control" id="accessoriesItemCode"
									name="text">
							</div>

							<div class="row">

								<div class="col-md-6 form-group mb-0 row">

									<label for="unit" class="col-md-3 col-form-label-sm pr-0">Unit</label>
									<select id="unit" class="selectpicker col-md-9 px-0"
										data-live-search="true"
										data-style="btn-light btn-sm border-light-gray">
										<option id="unit" value="0">Select Unit</option>
										<c:forEach items="${unitList}" var="unit">
											<option id="unit" value="${unit.unitId}">${unit.unitName}</option>
										</c:forEach>
									</select>
								</div>



								<div class="col-md-6">
									<div class="input-group input-group-sm my-0">
										<input type="text" class="form-control"
											placeholder="Unit Qty" aria-describedby="addUnit"
											id="unitQty">
										<div class="input-group-append">
											<button class="btn btn-primary" type="button" id="addUnit" onclick="unitAddAction()">
												<i class="fas fa-plus-circle"></i>
											</button>
										</div>
									</div>
									<table class="table table-hover table-bordered table-sm">
										<thead>
											<tr>
												<th scope="col">Unit</th>
												<th scope="col">Minimum Qty</th>
											</tr>
										</thead>
										<tbody id="unitList">
											
										</tbody>
									</table>
								</div>
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
									placeholder="Search Accessories Item"
									aria-describedby="findButton" id="search" name="search">
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
												<th scope="col">Accessories Item Name</th>
												<th scope="col">Accessories Item Code</th>
												<th scope="col">edit</th>
											</tr>
										</thead>
										<tbody id="dataList">
											<c:forEach items="${accessoriesItemList}"
												var="accessoriesItem" varStatus="counter">
												<tr>
													<td>${accessoriesItem.accessoriesItemId}</td>
													<td
														id='accessoriesItemName${accessoriesItem.accessoriesItemId}'>${accessoriesItem.accessoriesItemName}</td>
													<td
														id='accessoriesItemCode${accessoriesItem.accessoriesItemId}'>${accessoriesItem.accessoriesItemCode}</td>
													<td><i class="fa fa-edit"
														onclick="setData(${accessoriesItem.accessoriesItemId})" style="cursor:pointer;">
													</i></td>
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
	src="${pageContext.request.contextPath}/assets/js/register/accessories-create.js"></script>