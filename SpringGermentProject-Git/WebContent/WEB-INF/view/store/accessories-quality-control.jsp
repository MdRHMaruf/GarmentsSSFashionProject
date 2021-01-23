<%@page import="pg.share.Currency"%>
<%@page import="pg.share.PaymentType"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="pg.model.WareInfo"%>
<%@page import="pg.model.Module"%>
<%@page import="pg.model.Login"%>
<%@page import="java.util.List"%>

<%
String userId=(String)session.getAttribute("userId");
String userName=(String)session.getAttribute("userName");
String departmentId=(String)session.getAttribute("departmentId");

%>

<jsp:include page="../include/header.jsp" />

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
	<input type="hidden" id="userId" value="<%=userId%>">
	<input type="hidden" id="departmentId" value="<%=departmentId%>">
	<input type="hidden" id="poNo" value="0">

	<div class="card-box">
		<header class="d-flex justify-content-between">
			<h5 class="text-center" style="display: inline;">Accessories QC</h5>
		</header>
		<hr class="my-1">
		<div class="row">
			<div class="col-md-8">
				<div class="row">
					<div class="col-md-6">
						<div class="form-group mb-0  row">
							<label for="qcTransactionId"
								class="col-md-3 col-form-label-sm pr-0 mb-1 pb-1">QC
								Tran ID</label>
							<div class="input-group col-md-9 px-0">
								<div class="input-group-append width-100">
									<input id="qcTransactionId" type="text"
										class=" form-control-sm" readonly>
									<button id="newAccessoriesQCBtn" type="button"
										class="btn btn-outline-dark btn-sm form-control-sm">
										<i class="fa fa-file-text-o"></i>
									</button>
									<button id="findAccessoriesQCBtn" type="button"
										class="btn btn-outline-dark btn-sm form-control-sm"
										data-toggle="modal" data-target="#qcSearchModal">
										<i class="fa fa-search"></i>
									</button>

								</div>
							</div>
						</div>
						<div class="form-group mb-0  row">
							<label for="qcDate"
								class="col-md-3 col-form-label-sm pr-0 mb-1 pb-1">QC
								Date:</label> <input id="qcDate" type="date"
								class="col-md-9 form-control-sm">
						</div>

					</div>
					<div class="col-md-6">

						<div class="form-group mb-0  row">
							<label for="grnNo"
								class="col-md-3 col-form-label-sm pr-0 mb-1 pb-1">GRN
								No:</label>
							<div class="input-group col-md-9 px-0">
								<div class="input-group-append width-100">
									<input id="grnNo" type="text" class=" form-control-sm" readonly>
									<button id="grnSearchBtn" type="button"
										class="btn btn-outline-dark btn-sm form-control-sm"
										data-toggle="modal" data-target="#grnSearchModal">
										<i class="fa fa-search"></i>
									</button>
								</div>
							</div>
						</div>

						<div class="form-group mb-0  row">
							<label for="receiveDate"
								class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Accessories
								Rec.Date</label> <input id="receiveDate" type="date"
								class="col-md-8 form-control-sm">
						</div>

					</div>
				</div>
			</div>




			<div class="col-md-4">
				<div class="form-group mb-0  row">
					<label for="supplier"
						class="col-md-3 col-form-label-sm pr-0 mb-1 pb-1">Supplier</label>
					<select id="supplier" class="selectpicker col-md-9 px-0"
						data-live-search="true"
						data-style="btn-light btn-sm border-light-gray">
						<option id="supplier" value="0">--- Select ---</option>
						<c:forEach items="${supplierList}" var="supplier">
							<option id="supplier" value="${supplier.supplierid}">${supplier.suppliername}</option>
						</c:forEach>
					</select>

				</div>

				<div class="form-group mb-0  row">
					<label for="remarks"
						class="col-md-3 col-form-label-sm pr-0 mb-1 pb-1">Remarks</label>
					<input id="remarks" type="text" class="col-md-9 form-control-sm">

				</div>

				<div class="form-group mb-0  row">
					<label for="checkBy"
						class="col-md-3 col-form-label-sm pr-0 mb-1 pb-1">Check By</label>
					<select id="checkBy" class="selectpicker col-md-9 px-0"
						data-live-search="true"
						data-style="btn-light btn-sm border-light-gray">
						<option value="0">--- Select ---</option>

					</select>


				</div>
			</div>
		</div>


		<hr class="my-1">
		<div class="row mt-1">
			<div style="overflow: auto; max-height: 300px;"
				class="col-sm-12 px-1 table-responsive">
				<table
					class="table table-hover table-bordered table-sm mb-0 small-font">
					<thead class="no-wrap-text">
						<tr>
							<th>Accessories Name</th>
							<th>Accessories Color</th>
							<th>Size Name</th>
							<th>Unit Qty</th>
							<th>QC Passed Qty</th>
							<th>Check Qty</th>
							<th>UOM</th>
							<th>Shade</th>
							<th>Shrinkage</th>
							<th>GSM</th>
							<th>Width</th>
							<th>Defect</th>
							<th>Rack Name</th>
							<th>Bin name</th>
							<th>QC Passed</th>
							<th><label class="form-check-label" for="allCheck">Check
									<input id="allCheck" type="checkbox">
							</label></th>
						</tr>
					</thead>
					<tbody id="sizeList">

					</tbody>
				</table>
			</div>
		</div>

		<div class="row mt-1">
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
<!--QC search modal -->
<div class="modal fade" id="qcSearchModal" tabindex="-1" role="dialog"
	aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<div class="input-group">
					<input type="text" class="form-control"
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
							<th>QC Transaction Id</th>
							<th>QC Date</th>
							<th>GRN No</th>
							<th><span><i class="fa fa-search"></i></span></th>
						</tr>
					</thead>
					<tbody id="accessoriesQCList">

					</tbody>
				</table>
			</div>

		</div>
	</div>
</div>

<!--grn search modal -->
<div class="modal fade" id="grnSearchModal" tabindex="-1" role="dialog"
	aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<div class="input-group">
					<input type="text" class="form-control"
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
					<tbody id="accessoriesReceiveList">

					</tbody>
				</table>
			</div>

		</div>
	</div>
</div>
<jsp:include page="../include/footer.jsp" />

<script
	src="${pageContext.request.contextPath}/assets/js/store/accessories-quality-control.js"></script>
