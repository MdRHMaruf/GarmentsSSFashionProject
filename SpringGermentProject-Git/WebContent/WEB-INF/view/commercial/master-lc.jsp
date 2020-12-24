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
	<input type="hidden" id="departmentId"
		value="<%=lg.get(0).getDepartmentId()%>"> <input type="hidden"
		id="poNo" value="0"> <input type="hidden" id="indentId"
		value="0"> <input type="hidden" id="styleId" value="0">
	<input type="hidden" id="styleItemId" value="0"> <input
		type="hidden" id="itemColorId" value="0"> <input type="hidden"
		id="fabricsColorId" value="0"> <input type="hidden"
		id="fabricsId" value="0"> <input type="hidden" id="unitId"
		value="0"> <input type="hidden" id="fabricsRate" value="0">

	<div class="card-box">
		<header class="d-flex justify-content-between">
			<h5 class="text-center" style="display: inline;">Master L/C</h5>
		</header>
		<hr class="my-1">
		<div class="row">
			<div class="col-md-4">
				<div class="form-group mb-0  row">
					<label for="transactionId"
						class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">L/C No</label>
					<div class="input-group col-md-8 px-0">
						<div class="input-group-append">
							<input id="lcNo" type="text" class=" form-control-sm">
							<button id="newLCBtn" type="button"
								class="btn btn-outline-dark btn-sm form-control-sm">
								<i class="fa fa-file-text-o"></i>
							</button>
							<button id="findLCBtn" type="button"
								class="btn btn-outline-dark btn-sm form-control-sm"
								data-toggle="modal" data-target="#searchModal">
								<i class="fa fa-search"></i>
							</button>

						</div>
					</div>


				</div>
				<div class="form-group mb-0  row">
					<label for="buyerName"
						class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Buyer
						Name</label> <select id="buyerName" onchange="buyerWiseStyleLoad()"
						class="selectpicker col-md-8 px-0" data-live-search="true"
						data-style="btn-light btn-sm border-light-gray">
						<option value="0">Select Buyer</option>
						<c:forEach items="${buyerList}" var="buyer">
							<option value="${buyer.buyerid}">${buyer.buyername}</option>
						</c:forEach>
					</select>
				</div>
				<div class="form-group mb-0  row">
					<label for="sendBankName"
						class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Send
						Bank</label> <select id="sendBankName" onchange=""
						class="selectpicker col-md-8 px-0" data-live-search="true"
						data-style="btn-light btn-sm border-light-gray">
						<option value="0">Select Bank</option>
						<option value="1">Bangladesh Bank</option>
						<option value="2">Bank of china</option>
						<option value="3">Agrani Bank</option>
						<option value="4">Asia Bank</option>
						<option value="5">UCC Bank</option>
					</select>
				</div>

				<div class="form-group mb-0  row">
					<label for="receiveBankName"
						class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Receive
						Bank</label> <select id="receiveBankName" onchange=""
						class="selectpicker col-md-8 px-0" data-live-search="true"
						data-style="btn-light btn-sm border-light-gray">
						<option value="0">Select Bank</option>
						<option value="1">Bangladesh Bank</option>
						<option value="2">Bank of china</option>
						<option value="3">Agrani Bank</option>
						<option value="4">Asia Bank</option>
						<option value="5">UCC Bank</option>
					</select>
				</div>

				<div class="form-group mb-0  row">
					<label for="beneficiaryBankName"
						class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Beneficiary
						Bank</label> <select id="beneficiaryBankName" onchange=""
						class="selectpicker col-md-8 px-0" data-live-search="true"
						data-style="btn-light btn-sm border-light-gray">
						<option value="0">Select Bank</option>
						<option value="1">Bangladesh Bank</option>
						<option value="2">Bank of china</option>
						<option value="3">Agrani Bank</option>
						<option value="4">Asia Bank</option>
						<option value="5">UCC Bank</option>
					</select>
				</div>

				<div class="form-group mb-0  row">
					<label for="throughBankName"
						class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Through
						Bank</label> <select id="throughBankName" onchange=""
						class="selectpicker col-md-8 px-0" data-live-search="true"
						data-style="btn-light btn-sm border-light-gray">
						<option value="0">Select Bank</option>
						<option value="1">Bangladesh Bank</option>
						<option value="2">Bank of china</option>
						<option value="3">Agrani Bank</option>
						<option value="4">Asia Bank</option>
						<option value="5">UCC Bank</option>
					</select>
				</div>
			</div>
			<div class="col-md-3">
				<div class="form-group mb-0  row">
					<label for="date" class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Date</label>
					<input id="date" type="date" class="col-md-8 form-control-sm">
				</div>
				<div class="form-group mb-0  row">
					<label for="totalValue"
						class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Total
						Value</label> <input id="totalValue" type="text"
						class="col-md-8 form-control-sm" value="0" readonly>
				</div>

				<div class="form-group mb-0  row">
					<label for="currency"
						class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Currency</label>
					<select id="currency" onchange=""
						class="selectpicker col-md-8 px-0" data-live-search="true"
						data-style="btn-light btn-sm border-light-gray">
						<option id="currency" value="0">Select Currency</option>
						<%
							int length = Currency.values().length;
							for (int i = 0; i < length; i++) {
						%>
						<option id="currency" value="<%=Currency.values()[i].getType()%>"><%=Currency.values()[i].name()%></option>
						<%
							}
						%>
					</select>
				</div>
				<div class="form-group mb-0  row">
					<label for="maturityDate"
						class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Maturity
						Date</label> <input id="maturityDate" type="date"
						class="col-md-8 form-control-sm">

				</div>

				<div class="form-group mb-0  row">
					<label for="remarks"
						class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Remarks</label>
					<textarea id="remarks" type="text" class="col-md-8 form-control"></textarea>

				</div>


			</div>
			
			<div class="col-md-3 ml-2">
				<div class="row">
					<table
						class="table table-hover table-bordered table-sm mb-0 small-font">
						<thead class="no-wrap-text">
							<tr>
								<th>Amendment No</th>
								<th>Amendment Date</th>
							</tr>
						</thead>
						<tbody id="amendmentList">

						</tbody>
					</table>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-2 pr-0 pl-1">
				<label for="styleNo" class="col-form-label-sm my-0 py-0">Style
					No</label> <select id="styleNo"onchange="styleWiseItemLoad()"
					class="selectpicker col-md-12 px-0" data-live-search="true"
					data-style="btn-light btn-sm border-light-gray">
					<option id="styleNo" value="0">Select Style</option>
				</select>
			</div>

			<div class="col-md-3 pr-0 pl-1">
				<label for="itemName" class="col-form-label-sm my-0 py-0">style Item Description</label>
				<select id="itemName" class="selectpicker col-md-12 px-0"
					data-live-search="true"
					data-style="btn-light btn-sm border-light-gray">
					<option value="0">--Select Indent Item--</option>
				</select>
			</div>

			<div class="col-md-3 pr-0">
				<div class="row">
					<div class="col-md-4 pr-0 pl-1">
						<label for="quantity" class="col-form-label-sm my-0 py-0">Quantity</label>
						<input id="quantity" type="number" class="form-control-sm pr-0 pl-1">
					</div>
					<div class="col-md-4 pr-0 pl-1">
						<label for="unitPrice" class="col-form-label-sm my-0 py-0">Unit Price</label>
						<input id="unitPrice" type="number" class="form-control-sm pr-0 pl-1">
					</div>
					<div class="col-md-4 pr-0 pl-1">

						<button id="btnAdd" type="button" style="margin-top: 1.3rem;"
							class="btn btn-primary btn-sm" onclick="styleAddAction()">
							<i class="fa fa-plus-circle"></i> Add
						</button>
					</div>

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
							<th>Style No</th>
							<th>Quantity</th>
							<th>Unit Price</th>
							<th>Amount</th>
							<th>Delete</th>
							<th>Shipped</th>
						</tr>
					</thead>
					<tbody id="dataList">

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
		<div class="row mt-1">

			<fieldset
				style="border: 1px solid #e0e0e0; padding: 10px; width: 100%;">
				<legend>Shipment Details</legend>
				<div class="row">
					<div class="col-md-4">
						<table
							class="table table-hover table-bordered table-sm mb-0 small-font">
							<thead class="no-wrap-text">
								<tr>
									<th>Shipment Mark</th>
									<th>Shipment Date</th>
								</tr>
							</thead>
							<tbody id="shipmentList">

							</tbody>
						</table>
					</div>
					<div class="col-md-6">
						<div class="form-group mb-0  row">
							<label for="shipmentDate"
								class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Shipment
								Date:</label> <input id="shipmentDate" type="date"
								class="col-md-8 form-control-sm">
						</div>
						<div class="form-group mb-0  row">
							<label for="shippingMark"
								class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Shipping
								Mark:</label> <input id="shippingMark" type="text"
								class="col-md-8 form-control-sm">
						</div>
						<table
							class="table table-hover table-bordered table-sm mb-0 small-font">
							<thead class="no-wrap-text">
								<tr>
									<th>Style No</th>
									<th>Qty</th>
								</tr>
							</thead>
							<tbody id="shippingStyleList">

							</tbody>
						</table>
					</div>
					<div class="col-md-2">
						<div class="d-flex flex-column">
							<button id="btnSaveShipment" type="button"
								class="btn btn-primary btn-sm" onclick="saveShipment()">
								<i class="fas fa-save"></i> Save Shipment
							</button>
							<button id="btnEditShipment" type="button"
								class="btn btn-primary btn-sm mt-1" onclick="editShipment()">
								<i class="fas fa-pencil-square"></i> Edit Shipment
							</button>
						</div>
					</div>


				</div>
			</fieldset>
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
							<th>Buyer Name</th>
							<th>Master LC</th>
							<th>LC Date</th>
							<th><span><i class="fa fa-search"></i></span></th>
						</tr>
					</thead>
					<tbody id="masterLcList">
						<tr>
							<td>GROUP TRIUM INC</td>
							<td>MLC-38394394</td>
							<td>02-10-2020</td>
							<th><span><i style="cursor:pointer;" onclick="searchMasterLc(0)" class="fa fa-search"></i></span></th>
						</tr>
					</tbody>
				</table>
			</div>

		</div>
	</div>
</div>


<jsp:include page="../include/footer.jsp" />

<script
	src="${pageContext.request.contextPath}/assets/js/commercial/master-lc.js"></script>
