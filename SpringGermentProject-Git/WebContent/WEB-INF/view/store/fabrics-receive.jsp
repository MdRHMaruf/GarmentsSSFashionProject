<%@page import="pg.share.Currency"%>
<%@page import="pg.share.PaymentType"%>
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
	<div class="alert alert-success alert-dismissible fade show"
		style="display: none;">
		<p id="successAlert" class="mb-0">
			<strong>Success!</strong> Unit Name Save Successfully..
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
	<input type="hidden" id="poNo" value="0">
	<input type="hidden"
		id="indentId" value="0"> 
		<input type="hidden" id="styleId" value="0">
		<input type="hidden" id="styleItemId" value="0">
		<input type="hidden" id="itemColorId" value="0">
		<input type="hidden" id="fabricsColorId" value="0">
		<input type="hidden" id="fabricsId" value="0"> 
		<input type="hidden" id="unitId" value="0"> 
		
		<input type="hidden" id="fabricsRate" value="0">

	<div class="card-box">
		<header class="d-flex justify-content-between">
			<h5 class="text-center" style="display: inline;">Fabrics Receive</h5>
			<button type="button" class="btn btn-outline-dark btn-sm"
				data-toggle="modal" data-target="#searchModal">
				<i class="fa fa-search"></i>
			</button>
		</header>
		<hr class="my-1">
		<div class="row">
			<div class="col-md-4">
				<div class="form-group mb-0  row">
					<label for="transactionId"
						class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Transaction
						ID</label>
					<div class="input-group col-md-8 px-0">
						<div class="input-group-append">
							<input id="transactionId" type="text" class=" form-control-sm"
								readonly>
							<button id="newTransactionBtn" type="button"
								class="btn btn-outline-dark btn-sm form-control-sm">
								<i class="fa fa-file-text-o"></i>
							</button>
							<button id="findTransactionBtn" type="button"
								class="btn btn-outline-dark btn-sm form-control-sm"
								data-toggle="modal" data-target="#searchModal">
								<i class="fa fa-search"></i>
							</button>

						</div>
					</div>


				</div>
				<div class="form-group mb-0  row">
					<label for="grnNo"
						class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">GRN No:</label>
					<input id="grnNo" type="text" class="col-md-8 form-control-sm">

				</div>
				<div class="form-group mb-0  row">
					<label for="grnDate"
						class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">GRN
						Date:</label> <input id="grnDate" type="date"
						class="col-md-8 form-control-sm">

				</div>
				<div class="form-group mb-0  row">
					<label for="location"
						class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Location</label>
					<select id="location" onchange="poWiseStyleLoad()"
						class="selectpicker col-md-8 px-0" data-live-search="true"
						data-style="btn-light btn-sm border-light-gray">
						<option id="location" value="0">Select Purchase Order</option>
					</select>

				</div>

				<div class="row">
					<div class="col-md-6 pr-0">
						<div class="form-group mb-0  row">
							<label for="receiveQty"
								class="col-md-5 col-form-label-sm pr-0 mb-1 pb-1">Receive
								Qty</label> <input id="receiveQty" type="number"
								class="col-md-7 form-control-sm">
						</div>
					</div>
					<div class="col-md-6">
						<div class="form-group mb-0  row">
							<label for="noOfRoll"
								class="col-md-6 col-form-label-sm pr-0 mb-1 pb-1">No Of
								Roll</label> <input id="noOfRoll" type="number"
								class="col-md-6 form-control-sm" placeholder="">

						</div>
					</div>
				</div>

			</div>
			<div class="col-md-5">
				<div class="row">
					<div class="col-md-10 px-1">
						<u><h5>Fabrics Purchase Order Information</h5></u>
					</div>
					<div class="col-md-2">
						<button id="itemSearchBtn" type="button"
							class="btn btn-outline-dark btn-sm form-control-sm"
							data-toggle="modal" data-target="#itemSearchModal">
							<i class="fa fa-search"></i>
						</button>
					</div>
				</div>
				<div class="row">
					<div class="col-md-3 px-1">
						<label for="purchaseOrder">Purchase Order:</label>
					</div>
					<div class="col-md-9 px-1">
						<b><label id="purchaseOrder"></label></b>
					</div>
				</div>

				<div class="row">
					<div class="col-md-3 px-1">
						<label for="styleNo">Style No:</label>
					</div>
					<div class="col-md-9 px-1">
						<b><label id="styleNo"></label></b>
					</div>
				</div>

				<div class="row">
					<div class="col-md-3 px-1">
						<label for="itemName">Item Name:</label>
					</div>
					<div class="col-md-9 px-1">
						<b><label id="itemName"></label></b>
					</div>
				</div>

				<div class="row">
					<div class="col-md-3 px-1">
						<label for="itemColor">Item Color:</label>
					</div>
					<div class="col-md-9 px-1">
						<b><label id="itemColor"></label></b>
					</div>
				</div>

				<div class="row">
					<div class="col-md-3 px-1">
						<label for="fabricsItem">Fabrics Item:</label>
					</div>
					<div class="col-md-9 px-1">
						<b><label id="fabricsItem"></label></b>
					</div>
				</div>

				<div class="row">
					<div class="col-md-3 px-1">
						<label for="fabricsColor">Fabrics Color:</label>
					</div>
					<div class="col-md-9 px-1">
						<b><label id="fabricsColor"></label></b>
					</div>
				</div>

				<div class="row">
					<div class="col-md-1 px-1">
						<label for="unit">Unit:</label>
					</div>
					<div class="col-md-2 px-1">
						<b><label id="unit"></label></b>
					</div>

					<div class="col-md-2 px-1">
						<label for="totalPoQty">T. PO Qty:</label>
					</div>
					<div class="col-md-2 px-1">
						<b><label id="totalPoQty"></label></b>
					</div>
					
					<div class="col-md-2 px-1">
						<label for="previousReceive">Prev. Rec.:</label>
					</div>
					<div class="col-md-2 px-1">
						<b><label id="previousReceive"></label></b>
					</div>
				</div>




				

			</div>
			<div class="col-md-3">
				<div class="form-group mb-0  row">
					<label for="supplier"
						class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Supplier</label>
					<select id="supplier" class="selectpicker col-md-8 px-0"
						data-live-search="true"
						data-style="btn-light btn-sm border-light-gray">
						<option id="supplier" value="0">--- Select ---</option>
						<c:forEach items="${supplierList}" var="supplier">
							<option id="supplier" value="${supplier.supplierid}">${supplier.suppliername}</option>
						</c:forEach>
					</select>

				</div>
				<div class="form-group mb-0  row">
					<label for="challanNo"
						class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Challan
						No</label> <input id="challanNo" type="text"
						class="col-md-8 form-control-sm">

				</div>
				<div class="form-group mb-0  row">
					<label for="challanDate"
						class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Challan
						Date</label> <input id="challanDate" type="date"
						class="col-md-8 form-control-sm">

				</div>

				<div class="form-group mb-0  row">
					<label for="remarks"
						class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Remarks</label>
					<textarea id="remarks" type="text" class="col-md-8 form-control"></textarea>

				</div>


			</div>
		</div>

		<hr class="my-1">
		<div class="row mt-1">
			<div style="overflow: auto; max-height: 300px;"
				class="col-sm-12 px-1 table-responsive">
				<table
					class="table table-hover table-bordered table-sm mb-0 small-font table-expandable">
					<thead class="no-wrap-text">
						<tr>
							<th>Fabrics Name</th>
							<th>Fabrics Color</th>
							<th>Unit</th>
							<th>Order Qty</th>
							<th>Previous Rec.</th>
							<th>Receive Qty</th>
						</tr>
					</thead>
					<tbody id="rollList">

					</tbody>
				</table>
			</div>
		</div>


		<div class="row mt-1">
			<div class="col-md-6">
				<div class="input-group input-group-sm mb-1">
					<div class="input-group-prepend">
						<span class="input-group-text" id="preparedBy">Prepared By</span>
					</div>
					<select id="preparedBy" class="selectpicker "
						data-live-search="true"
						data-style="btn-light btn-sm border-light-gray">
						<option id="preparedBy" value="0">--- Select ---</option>

					</select>
				</div>
			</div>

		</div>
		<div class="row">
			<div class="col-md-12 d-flex justify-content-end">
				<button id="btnSubmit" type="button" class="btn btn-primary btn-sm"
					onclick="submitAction()">
					<i class="fas fa-save"></i> Submit
				</button>
				<button id="btnEdit" type="button"
					class="btn btn-primary btn-sm ml-1" onclick="editAction()" disabled>
					<i class="fa fa-pencil-square"></i> Edit
				</button>
				<button id="btnRefresh" type="button"
					class="btn btn-primary btn-sm ml-1" onclick="refreshAction()">
					<i class="fa fa-refresh"></i> Refresh
				</button>
				<button id="btnPreview" type="button"
					class="btn btn-primary btn-sm ml-1" disabled>
					<i class="fa fa-print"></i> Preview
				</button>
			</div>
		</div>
	</div>
</div>
<!-- Modal -->
<!-- search modal -->
<div class="modal fade" id="searchModal" tabindex="-1" role="dialog"
	aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<div class="input-group">
					<input id="search" type="text" class="form-control"
						placeholder="Search Purchase Order"
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
							<th>Transaction Id</th>
							<th>GRN No</th>
							<th>GRN Date</th>
							<th><span><i class="fa fa-search"></i></span></th>
						</tr>
					</thead>
					<tbody id="fabricsReceiveList">

					</tbody>
				</table>
			</div>

		</div>
	</div>
</div>

<!-- Item Search Modal -->
<div class="modal fade" id="itemSearchModal" tabindex="-1" role="dialog"
	aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-xl">
		<div class="modal-content">
			<div class="modal-header py-2">
				<div class="input-group input-group-sm">

					<input id="searchEverything" type="text" class="form-control"
						placeholder="Search Every Thing" aria-label="Recipient's username"
						aria-describedby="basic-addon2">
					<div class="input-group-append">
						<button class="form-control-sm" id="searchRefreshBtn">
							<i class="fa fa-refresh" style="cursor: pointer;"></i>
						</button>
					</div>


				</div>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="row px-3">
				<div class="col-md-2 px-1">
					<input type="text" class="form-control-sm" id="purchaseOrderSearch"
						placeholder="Purchase Order">
				</div>
				<div class="col-md-2 px-1">
					<input type="text" class="form-control-sm" id="styleNoSearch"
						placeholder="Style No">
				</div>
				<div class="col-md-3 px-1">
					<input type="text" class="form-control-sm" id="itemNameSearch"
						placeholder="Item Name">
				</div>
				<div class="col-md-3 px-1">
					<input type="text" class="form-control-sm" id="fabricsItemSearch"
						placeholder="Fabrics Item">
				</div>
				<div class="col-md-2 px-1">
					<input type="text" class="form-control-sm" id="colorSearch"
						placeholder="Color">
				</div>
			</div>
			<div class="modal-body">
				<table class="table table-hover table-bordered table-sm mb-0">
					<thead>
						<tr>
							<th>Purchase Order No</th>
							<th>Style No</th>
							<th>Item Name</th>
							<th>Item Color</th>
							<th>Fabrics Name</th>
							<th>Fabrics Color</th>
							<th><span><i class="fa fa-search"></i></span></th>
						</tr>
					</thead>
					<tbody id="purchaseOrderList">

					</tbody>
				</table>
			</div>

		</div>
	</div>
</div>

<!-- Fabrics Search Modal -->
<div class="modal fade" id="fabricsModal" tabindex="-1" role="dialog"
	aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<div class="input-group">
					<input id="itemSearch" type="text" class="form-control"
						placeholder="Fabrics Search" aria-label="Recipient's username"
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
							<th>Item Name</th>
							<th>Item Color</th>
							<th>Fabrics</th>
							<th><span><i class="fa fa-search"></i></span></th>
						</tr>
					</thead>
					<tbody id="fabricsList">

					</tbody>
				</table>
			</div>

		</div>
	</div>
</div>

<jsp:include page="../include/footer.jsp" />

<script
	src="${pageContext.request.contextPath}/assets/js/store/fabrics-receive.js"></script>
