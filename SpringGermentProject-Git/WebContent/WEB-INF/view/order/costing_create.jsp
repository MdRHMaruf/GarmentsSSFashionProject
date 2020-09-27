<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="pg.model.wareinfo"%>
<%@page import="pg.model.module"%>
<%@page import="pg.model.login"%>
<%@page import="java.util.List"%>
<jsp:include page="../include/header.jsp" />
<%
	List<login> lg = (List<login>) session.getAttribute("pg_admin");
%>

<div class="page-wrapper">
	<div class="m-2">
		<div class="alert alert-success alert-dismissible fade show"
			style="display: none;">
			<p id="successAlert" class="mb-0">
				<strong>Success!</strong> Costing Save Successfully..
			</p>
		</div>
		<div class="alert alert-warning alert-dismissible fade show"
			style="display: none;">
			<p id="warningAlert" class="mb-0">
				<strong>Warning!</strong> Unit Name Empty.Please Enter Unit Name...
			</p>
		</div>
		<div class="alert alert-danger alert-dismissible fade show"
			style="display: none;">
			<p id="dangerAlert" class="mb-0">
				<strong>Wrong!</strong> Something Wrong...
			</p>
		</div>
		<input type="hidden" id="userId" value="<%=lg.get(0).getId()%>">
		<input type="hidden" id="itemAutoId" value="0">
		<div class="card-box pt-1">
			<header class="d-flex justify-content-between">
				<h5 class="text-center" style="display: inline;">Costing Create</h5>
				<div class="row">
					<div class="col-md-12">
						<button type="button" class="btn btn-outline-dark btn-sm"
							data-toggle="modal" data-target="#searchModal" title="Search">
							<i class="fa fa-search"></i>
						</button>
						<button type="button" class="btn btn-outline-dark btn-sm"
							data-toggle="modal" data-target="#styleModal" title="View Style">
							<i class="fas fa-tshirt"></i>
						</button>
					</div>
				</div>


			</header>
			<hr class="my-1">
			<div class="row">
				<div class="col-md-12">
					<div class="card shadow  p-2 mb-3 ">
						<div class="row">
							<div class="col-md-5">
								<div class="form-group mb-0  row">
									<label for="styleName" class="col-md-3 col-form-label-sm pr-0">Style
										Name</label> <select id="styleName" onchange="styleWiseItemLoad()"
										class="selectpicker col-md-9 px-0" data-live-search="true"
										data-style="btn-light btn-sm border-light-gray">
										<option id="styleName" value="0">Select Style Name</option>
										<c:forEach items="${styleList}" var="style">
											<option id="styleName" value="${style.styleId}">${style.styleNo}</option>
										</c:forEach>
									</select>

								</div>
							</div>

							<div class="col-md-5">
								<div class="form-group mb-0  row">
									<label for="itemName" class="col-md-3 col-form-label-sm pr-0">Item
										Name</label> <select id="itemName" class="selectpicker col-md-9 px-0"
										data-live-search="true"
										data-style="btn-light btn-sm border-light-gray">

										<option id="itemName" value="0">Select Item Name</option>

									</select>

								</div>
							</div>
							<div class="col-md-1">
								<button type="button" class="btn btn-outline-dark btn-sm"
									onclick="cloneButtonAction()"
									title="Copy Costing">
									<i class="fas fa-copy"></i>
								</button>
							</div>


						</div>
						<div class="row">
							<div class="form-group col-md-2 mb-1 pr-1">
								<label for="particularType" class="col-form-label-sm mb-0 pb-0">Particular
									Type</label> <select id="particularType"
									class="form-control-sm col-md-12"
									onchange="typeWiseParticularLoad()" data-live-search="true"
									data-style="btn-light btn-sm border-light-gray">
									<option id="particularType" value="1">Fabrics</option>
									<option id="particularType" value="2">Other</option>
								</select>
							</div>
							<div class="form-group col-md-5 mb-1 px-1">
								<label for="particularName" class="col-form-label-sm mb-0 pb-0">Particular
									Name</label>
								<div class="row">
									<select id="particularName" class="selectpicker col-md-12"
										data-live-search="true"
										data-style="btn-light btn-sm border-light-gray">
										<option id="particularName" value="0">Select
											Particular Name</option>
										<c:forEach items="${particularList}" var="particular">
											<option id="particularName"
												value="${particular.particularItemId}">${particular.particularItemName}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="form-group col-md-2 mb-1 px-1">
								<label for="unit" class="col-form-label-sm mb-0 pb-0">Unit</label>
								<div class="row">
									<select id="unit" class="selectpicker col-md-12"
										data-live-search="true"
										data-style="btn-light btn-sm border-light-gray"
										onchange="inputSetByUnit()">
										<option id="unit" value="0">Select Unit</option>
										<c:forEach items="${unitList}" var="unit">
											<option id="unit" value="${unit.unitId}">${unit.unitName}</option>
										</c:forEach>
									</select>
								</div>

							</div>
							<div class="form-group col-md-1 mb-1 px-1"
								style="padding-left: 1px;">
								<label for="commission" class="col-form-label-sm mb-0 pb-0">Commission</label>
								<input id="commission" type="number" class="form-control-sm"
									value="0">

							</div>
							<div class="form-group col-md-2 mb-1 pl-1"
								style="padding-left: 1px;">
								<label for="submissionDate" class="col-form-label-sm mb-0 pb-0">Submission
									Date</label> <input id="submissionDate" type="date"
									class="form-control-sm">

							</div>
						</div>
						<div class="row">
							<div class="col-md-2">
								<div class="form-group mb-0 row">
									<label for="width" class="col-md-5 mb-0 pr-1 col-form-label-sm">Width</label>
									<div class="col-md-7 px-1">
										<input type="number" class="form-control-sm " id="width">
									</div>
								</div>
							</div>
							<div id="yardDiv" class="col-md-2" style="display: none;">
								<div class="form-group mb-0 row">
									<label for="yard" class="col-md-5 mb-0 pr-1 col-form-label-sm">Yard</label>
									<div class="col-md-7 px-1">
										<input type="number" class="form-control-sm " id="yard">
									</div>
								</div>
							</div>
							<div id="gsmDiv" class="col-md-2" style="display: none;">
								<div class="form-group mb-0 row">
									<label for="gsm" class="col-md-5 mb-0 pr-1 col-form-label-sm">GSM</label>
									<div class="col-md-7 px-1">
										<input type="number" class="form-control-sm " id="gsm">
									</div>
								</div>
							</div>
							<div class="col-md-2 ml-2">
								<div class="form-group mb-0 row">
									<label for="consumption"
										class="col-md-6 mb-0 px-0 col-form-label-sm">Consumption</label>
									<div class="col-md-6 px-1">
										<input type="number" class="form-control-sm " id="consumption">
									</div>
								</div>
							</div>
							<div class="col-md-2">
								<div class="form-group mb-0 row">
									<label for="unitPrice"
										class="col-md-6 mb-0 pr-1 col-form-label-sm">Unit
										Price</label>
									<div class="col-md-6 px-1">
										<input type="number" class="form-control-sm " id="unitPrice">
									</div>
								</div>
							</div>
							<button id="btnSave" type="button" class="btn btn-primary btn-sm"
								onclick="saveAction()">
								<i class="fas fa-save"></i> Save
							</button>
							<button id="btnEdit" type="button"
								class="btn btn-primary btn-sm ml-1" onclick="editAction()"
								disabled>
								<i class="fa fa-pencil-square"></i> Edit
							</button>
						</div>
					</div>


					<div class="row">
						<div style="overflow: auto; max-height: 300px;" class="col-sm-12">
							<table class="table table-hover table-bordered table-sm mb-0">
								<thead>
									<tr>
										<th>Style</th>
										<th>Fabrication</th>
										<th>Size</th>
										<th>Width</th>
										<th>Yard</th>
										<th>GSM</th>
										<th>CONS/DOZEN</th>
										<th>Rate</th>
										<th>Amount</th>
										<th><i class="fa fa-edit"></i></th>
										<th><i class="fa fa-trash"></i></th>
									</tr>
								</thead>
								<tbody id="dataList">

								</tbody>
							</table>
						</div>
					</div>
					<div class="row mt-1">
						<div class="col-md-12 d-flex justify-content-end">
							<button id="btnRefresh" type="button"
								class="btn btn-primary btn-sm ml-1" onclick="refreshAction()">
								<i class="fa fa-refresh"></i> Refresh
							</button>
<!-- 							<button id="btnPreview" type="button"
								class="btn btn-primary btn-sm ml-1" onclick="confrimAction()">
								<i class="fa fa-print"></i> Preview
							</button> -->
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!--Search Modal -->
<div class="modal fade" id="searchModal" tabindex="-1" role="dialog"
	aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<div class="input-group">
					<input id="searchCosting" type="text" class="form-control"
						placeholder="Search Costing" aria-label="Recipient's username"
						aria-describedby="basic-addon2">
					<div class="input-group-append">
						<span class="input-group-text"><i class="fa fa-search"></i></span>
					</div>
				</div>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<table class="table table-hover table-bordered table-sm mb-0">
					<thead>
						<tr>
							<th>Style</th>
							<th>Item Name</th>
							<th>Date</th>
							<th><span><i class="fa fa-search"></i></span></th>
							<th>Print</th>
						</tr>
					</thead>
					<tbody id="costingListTable">
						<c:forEach items="${costingList}" var="costing"
							varStatus="counter">
							<tr>
								<td>${costing.styleName}</td>
								<td>${costing.itemName}</td>
								<td>${costing.date}</td>
								<td><i class="fa fa-search"
									onclick="searchCosting(${costing.styleId}, ${costing.itemId})"></i></td>
								<td><i class="fa fa-print"
									onclick="itemWiseCostingReport(${costing.styleId}, ${costing.itemId})"></i></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>

		</div>
	</div>
</div>

<!--Clone Modal -->
<div class="modal fade" id="cloneModal" tabindex="-1" role="dialog"
	aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<div class="input-group">
					<input id="cloneCostingSearch" type="text" class="form-control"
						placeholder="Search Style Or Item Name"
						aria-label="Recipient's username" aria-describedby="basic-addon2">
					<div class="input-group-append">
						<span class="input-group-text"><i class="fa fa-search"></i></span>
					</div>
				</div>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<table class="table table-hover table-bordered table-sm mb-0">
					<thead>
						<tr>
							<th>Style</th>
							<th>Item Name</th>
							<th><span><i class="fa fa-search"></i></span></th>
						</tr>
					</thead>
					<tbody id="cloneCostingTable">
						<c:forEach items="${costingList}" var="costing"
							varStatus="counter">
							<tr>
								<td>${costing.styleName}</td>
								<td>${costing.itemName}</td>
								<td><i class="fas fa-copy"
									onclick="cloningCosting(${costing.styleId}, ${costing.itemId})"></i></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>

		</div>
	</div>
</div>

<!--Style Modal -->
<div class="modal fade" id="styleModal" tabindex="-1" role="dialog"
	aria-hidden="true">
	<div class="modal-dialog modal-md" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="exampleModalLongTitle">All Costing
					Style</h5>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>


			<div class="modal-body">
				<div class="row">
					<div style="overflow: auto; max-height: 300px;" class="col-sm-12">
						<table class="table table-bordered">
							<thead>
								<tr>
									<th style="width: 15px;">Sl#</th>
									<th>Style</th>
									<th>Check</th>
								</tr>
							</thead>
						</table>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
			</div>
		</div>
	</div>
</div>
<jsp:include page="../include/footer.jsp" />

<script
	src="${pageContext.request.contextPath}/assets/js/order/costing_create.js"></script>
