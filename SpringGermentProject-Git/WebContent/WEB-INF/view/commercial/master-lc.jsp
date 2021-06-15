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
	String userId = (String) session.getAttribute("userId");
	String userName = (String) session.getAttribute("userName");
	String departmentId = (String) session.getAttribute("departmentId");
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
	<input type="hidden" id="userId" value="<%=userId%>"> <input
		type="hidden" id="departmentId" value="<%=departmentId%>"> <input
		type="hidden" id="poNo" value="0"> <input type="hidden"
		id="indentId" value="0"> <input type="hidden" id="styleId"
		value="0"> <input type="hidden" id="styleItemId" value="0">
	<input type="hidden" id="itemColorId" value="0"> <input
		type="hidden" id="fabricsColorId" value="0"> <input
		type="hidden" id="fabricsId" value="0"> <input type="hidden"
		id="unitId" value="0"> <input type="hidden" id="fabricsRate"
		value="0"> <input type="hidden" id="masterLCAutoId" value="0">
	<input type="hidden" id='masterAmendmentNo'> <input
		type="hidden" id="importLCAutoId" value="0"> <input
		type="hidden" id='importAmendmentNo'> <input type="hidden"
		id='billOfEntryAutoId'> <input type="hidden"
		id='exportLCAutoId'> <input type="hidden" id="masterUdAutoId">
		<input type="hidden" id="previousUdNo">
		<input type="hidden" id="previousMasterLCNo"> 



	<div class="card-box">
		<nav>
			<div class="nav nav-tabs" id="nav-tab" role="tablist">
				<a class="nav-link active" id="nav-master-tab" data-toggle="tab"
					href="#nav-master" role="tab" aria-controls="nav-master"
					aria-selected="true">Master LC</a> <a class="nav-link"
					id="nav-import-tab" data-toggle="tab" href="#nav-import" role="tab"
					aria-controls="nav-import" aria-selected="false">Import/Local
					LC</a> <a class="nav-link" id="nav-export-tab" data-toggle="tab"
					href="#nav-export" role="tab" aria-controls="nav-export"
					aria-selected="false">Export</a>
			</div>
		</nav>
		<div class="tab-content" id="nav-tabContent">
			<div class="tab-pane fade show active" id="nav-master"
				role="tabpanel" aria-labelledby="nav-master-tab">
				<div class="row">
					<div class="col-md-4">
						<div class="form-group mb-0  row">
							<label for="masterLCNo"
								class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">L/C No</label>
							<div class="input-group col-md-8 px-0">
								<div class="input-group-append">
									<input id="masterLCNo" type="text" class=" form-control-sm">
									<button id="findLCBtn" type="button"
										class="btn btn-outline-dark btn-sm form-control-sm"
										data-toggle="modal" data-target="#searchModal">
										<i class="fa fa-search"></i>
									</button>
								</div>
							</div>
						</div>
						<div class="form-group mb-0  row">
							<label for="masterBuyerName"
								class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Buyer
								Name</label> <select id="masterBuyerName"
								onchange="buyerWiseStyleLoad()"
								class="selectpicker col-md-8 px-0" data-live-search="true"
								data-style="btn-light btn-sm border-light-gray">
								<option value="0">Select Buyer</option>
								<c:forEach items="${buyerList}" var="buyer">
									<option value="${buyer.buyerid}">${buyer.buyername}</option>
								</c:forEach>
							</select>
						</div>
						<div class="form-group mb-0  row">
							<label for="masterSendBankName"
								class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Send
								Bank</label> <select id="masterSendBankName" onchange=""
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
							<label for="masterReceiveBankName"
								class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Receive
								Bank</label> <select id="masterReceiveBankName" onchange=""
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
							<label for="masterDate"
								class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Date</label> <input
								id="masterDate" type="date" class="col-md-8 form-control-sm">
						</div>
						<div class="form-group mb-0  row">
							<label for="masterTotalValue"
								class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Total
								Value</label> <input id="masterTotalValue" type="text"
								class="col-md-8 form-control-sm" value="0" readonly>
						</div>

						<div class="form-group mb-0  row">
							<label for="masterCurrency"
								class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Currency</label>
							<select id="masterCurrency" onchange=""
								class="selectpicker col-md-8 px-0" data-live-search="true"
								data-style="btn-light btn-sm border-light-gray">
								<option id="currency" value="0">Select Currency</option>
								<%
									for (Currency currency : Currency.values()) {
								%>
								<option id="currency" value="<%=currency.name()%>"><%=currency.name()%></option>
								<%
									}
								%>
							</select>
						</div>
						<div class="form-group mb-0  row">
							<label for="masterShipmentDate"
								class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Shipment
								Date</label> <input id="masterShipmentDate" type="date"
								class="col-md-8 form-control-sm">
						</div>

						<div class="form-group mb-0  row">
							<label for="masterExpiryDate"
								class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Expiry
								Date</label> <input id="masterExpiryDate" type="date"
								class="col-md-8 form-control-sm">
						</div>

						<div class="form-group mb-0  row">
							<label for="masterRemarks"
								class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Remarks</label>
							<textarea id="masterRemarks" type="text"
								class="col-md-8 form-control"></textarea>
						</div>


					</div>

					<div class="col-md-5">
						<div class="row  px-2">
							<table
								class="table table-hover table-bordered table-sm mb-0 small-font">
								<thead class="no-wrap-text">
									<tr>
										<th>Amendment No</th>
										<th>Amendment Date</th>
									</tr>
								</thead>
								<tbody id="masterAmendmentList">
								</tbody>
							</table>
						</div>


						<div class="row mt-3">
							<div class="col-md-12 pr-1">
								<div class="input-group input-group-sm mb-1">
									<div class="input-group-prepend">
										<span class="input-group-text" id="inputGroup-sizing-sm"><label
											class="my-0" for="masterUDNo">UD No:<span
												style="color: red">*</span></label></span>
									</div>
									<input id="masterUDNo" type="text" class="form-control"
										aria-label="Sizing example input"
										aria-describedby="inputGroup-sizing-sm"> <input
										id="masterUdDate" type="date" class="form-control"
										aria-label="Sizing example input"
										aria-describedby="inputGroup-sizing-sm"
										onkeyup="setTotalQtyForCarton()">
									<!-- <button id="importUDAdd" type="button"
										class="btn btn-primary btn-sm"
										aria-label="Sizing example input"
										aria-describedby="inputGroup-sizing-sm"
										onclick="importUDAddAction()">Add</button> -->
								</div>
							</div>
						</div>

						<table
							class="table table-hover table-bordered table-sm mb-2 small-font">
							<thead class="no-wrap-text">
								<tr>
									<th>UD Amendment No</th>
									<th>UD Amendment Date</th>
								</tr>
							</thead>
							<tbody id="masterUdAmendmentList">

							</tbody>
						</table>
					</div>

				</div>
				<div class="row">
					<div class="col-md-2 pr-0 pl-1">
						<label for="masterStyleNo" class="col-form-label-sm my-0 py-0">Style
							No</label> <select id="masterStyleNo"
							onchange="styleWiseItemLoad(this),styleWiseBuyerPOLoad(this)"
							class="selectpicker col-md-12 px-0" data-live-search="true"
							data-style="btn-light btn-sm border-light-gray">
							<option value="0">Select Style</option>
						</select>
					</div>

					<div class="col-md-3 pr-0 pl-1">
						<label for="masterItemName" class="col-form-label-sm my-0 py-0">style
							Item Description</label> <select id="masterItemName"
							class="selectpicker col-md-12 px-0" data-live-search="true"
							data-style="btn-light btn-sm border-light-gray">
							<option value="0">--Select Indent Item--</option>
						</select>
					</div>

					<div class="col-md-2 pr-0 pl-1">
						<label for="masterPurchaseOrder"
							class="col-form-label-sm my-0 py-0">Purchase Order</label> <select
							id="masterPurchaseOrder" onchange="styleWiseItemLoad(this)"
							class="selectpicker col-md-12 px-0" data-live-search="true"
							data-style="btn-light btn-sm border-light-gray">
							<option value="0">Select Style</option>
						</select>
					</div>

					<div class="col-md-3 pr-0">
						<div class="row">
							<div class="col-md-4 pr-0 pl-1">
								<label for="masterQuantity" class="col-form-label-sm my-0 py-0">Quantity</label>
								<input id="masterQuantity" type="number"
									class="form-control-sm pr-0 pl-1">
							</div>
							<div class="col-md-4 pr-0 pl-1">
								<label for="masterUnitPrice" class="col-form-label-sm my-0 py-0">Unit
									Price</label> <input id="masterUnitPrice" type="number"
									class="form-control-sm pr-0 pl-1">
							</div>
							<div class="col-md-4 pr-0 pl-1">
								<button id="masterAddBtn" type="button"
									style="margin-top: 1.3rem;" class="btn btn-primary btn-sm"
									onclick="masterStyleAddAction()">
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
									<th>P/O No</th>
									<th>Quantity</th>
									<th>Unit Price</th>
									<th>Amount</th>
								</tr>
							</thead>
							<tbody id="masterStyleList">

							</tbody>
						</table>
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
									<th>P/O No</th>
									<th>UD Quantity</th>
									<th>UD Unit Price</th>
									<th>UD Amount</th>
								</tr>
							</thead>
							<tbody id="masterUDStyleList">

							</tbody>
						</table>
					</div>
				</div>

				<div class="row mt-1">
					<div class="col-md-12 d-flex justify-content-end">
						<button id="masterSubmitBtn" type="button" accesskey="S"
							class="btn btn-primary btn-sm" onclick="masterSubmitAction()">
							<i class="fas fa-save"></i><span
								style="text-decoration: underline;"> Submit</span>
						</button>
						<button id="masterAmendmentBtn" type="button"
							class="btn btn-primary btn-sm ml-1"
							onclick="masterAmendmentAction()" style="display: none">
							<i class="fas fa-save"></i> Master Amendment
						</button>
						<button id="masterEditBtn" type="button"
							class="btn btn-primary btn-sm ml-1" onclick="masterEditAction()"
							style="display: none">
							<i class="fa fa-pencil-square"></i> Master Edit
						</button>
						
						<button id="masterUdAmendmentBtn" type="button"
							class="btn btn-primary btn-sm ml-1"
							onclick="masterUdAmendmentAction()" style="display: none">
							<i class="fas fa-save"></i> UD Amendment
						</button>
						
						<button id="masterUdEditBtn" type="button"
							class="btn btn-primary btn-sm ml-1" onclick="masterUdEditAction()"
							style="display: none">
							<i class="fa fa-pencil-square"></i> UD Edit
						</button>
						
						<button id="masterRefreshBtn" type="button"
							class="btn btn-primary btn-sm ml-1" onclick="refreshAction()">
							<i class="fa fa-refresh"></i> Refresh
						</button>
						<button id="masterPreviewBtn" type="button"
							class="btn btn-primary btn-sm ml-1" style="display: none">
							<i class="fa fa-print"></i> Preview
						</button>
					</div>
				</div>
			</div>
			<div class="tab-pane fade" id="nav-import" role="tabpanel"
				aria-labelledby="nav-import-tab">
				<div class="row">
					<div class="col-md-7">
						<div class="row">
							<div class="col-md-6">
								<div class="form-group mb-0  row">
									<label for="importMasterLcNo"
										class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Master
										LC No</label>
									<div class="input-group col-md-8 px-0">
										<div class="input-group-append">
											<input id="importMasterLcNo" type="text"
												class="form-control-sm" readonly>
											<button id="findLCBtn" type="button"
												class="btn btn-outline-dark btn-sm form-control-sm"
												data-toggle="modal" data-target="#searchModal">
												<i class="fa fa-search"></i>
											</button>
										</div>
									</div>


								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group mb-0  row">
									<label for="importLCType"
										class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Imp.
										LC Type</label> <select id="importLCType"
										class="col-md-8 form-control-sm">
										<option value="1">Invoice</option>
										<option value="2">BTB LC</option>
										<option value="3">TT LC</option>
									</select>
								</div>
							</div>
						</div>
						
						<div class="row">
							<div class="col-md-6">
								<div class="form-group mb-0  row">
									<label for="importUdAmendmentNo"
										class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">UD Amend No</label> <select id="importUdAmendmentNo" onchange=""
										class="selectpicker col-md-8 px-0" data-live-search="true"
										data-style="btn-light btn-sm border-light-gray">
										<option value="0">Select UD Amendment</option>
									</select>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6">
								<div class="form-group mb-0  row">
									<label for="importInvoiceNo"
										class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Invoice
										No</label> <input id="importInvoiceNo" type="text"
										class="col-md-8 form-control-sm">
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group mb-0  row">
									<label for="importInvoiceDate"
										class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Date:</label>
									<input id="importInvoiceDate" type="date"
										class="col-md-8 form-control-sm">
								</div>
							</div>
						</div>



						<div class="row">
							<div class="col-md-6">
								<div class="form-group mb-0  row">
									<label for="importSenderBankName"
										class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Sender
										Bank</label> <select id="importSenderBankName" onchange=""
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
						</div>

						<div class="row">
							<div class="col-md-6">
								<div class="form-group mb-0  row">
									<label for="importReceiverBankName"
										class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Receiver
										Bank</label> <select id="importReceiverBankName" onchange=""
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
						</div>
						<div class="row">
							<div class="col-md-6">
								<div class="form-group mb-0  row">
									<label for="importSupplierName"
										class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Supplier
										Name</label> <select id="importSupplierName"
										class="selectpicker col-md-8 px-0" data-live-search="true"
										data-style="btn-light btn-sm border-light-gray">
										<option value="0">--Select SupplierName--</option>
										<c:forEach items="${supplierList}" var="supplier">
											<option value="${supplier.supplierid}">${supplier.suppliername}</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6">
								<div class="form-group mb-0  row">
									<label for="importDraftAt"
										class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Draft
										At</label> <input id=importDraftAt type="text"
										class="col-md-8 form-control-sm">
								</div>
							</div>

						</div>
						<div class="row">
							<div class="col-md-6">
								<div class="form-group mb-0  row">
									<label for="importMaturityDate"
										class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Maturity
										Date</label> <input id=importMaturityDate type="date"
										class="col-md-8 form-control-sm">
								</div>
							</div>

						</div>

						<div class="row">
							<div class="col-md-6">
								<div class="form-group mb-0  row">
									<label for="importProformaInvoiceNo"
										class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Proforma
										I.No.</label> <input id="importProformaInvoiceNo" type="text"
										class="col-md-8 form-control-sm">
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group mb-0  row">
									<label for="importProformaInvoiceDate"
										class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Date:</label>
									<input id="importProformaInvoiceDate" type="date"
										class="col-md-8 form-control-sm">
								</div>
							</div>
						</div>
					</div>

					<div class="col-md-5">
						<table
							class="table table-hover table-bordered table-sm mb-2 small-font">
							<thead class="no-wrap-text">
								<tr>
									<th>Invoice No</th>
									<th>Invoice Date</th>
								</tr>
							</thead>
							<tbody id="importInvoiceList">

							</tbody>
						</table>
						<table
							class="table table-hover table-bordered table-sm mb-2 small-font">
							<thead class="no-wrap-text">
								<tr>
									<th>Amendment No</th>
									<th>Amendment Date</th>
								</tr>
							</thead>
							<tbody id="importAmendmentList">

							</tbody>
						</table>


					</div>
				</div>
				<div class="row">
					<div class="col-md-2 pr-0 pl-1">
						<label for="importStyleNo" class="col-form-label-sm my-0 py-0">Style
							No</label> <select id="importStyleNo"
							onchange="styleWiseBuyerPOLoad(this)"
							class="selectpicker col-md-12 px-0" data-live-search="true"
							data-style="btn-light btn-sm border-light-gray">
							<option id="styleNo" value="0">Select Style</option>
						</select>
					</div>



					<div class="col-md-2 pr-0 pl-1">
						<label for="importPurchaseOrder"
							class="col-form-label-sm my-0 py-0">Purchase Order</label> <select
							id="importPurchaseOrder" onchange=""
							class="selectpicker col-md-12 px-0" data-live-search="true"
							data-style="btn-light btn-sm border-light-gray">
							<option id="styleNo" value="0">Select Style</option>
						</select>
					</div>

					<div class="col-md-1 pr-0 pl-1">
						<label for="importItemType" class="col-form-label-sm my-0 py-0">Item
							Type</label> <select id="importItemType"
							class="col-md-12 form-control-sm px-0"
							onchange='itemTypeChangeAction()'>
							<option value="0">--Select Item Type--</option>
							<option value="1">Fabrics</option>
							<option value="2">Accessories</option>
						</select>
					</div>

					<div class="col-md-3 pr-0 pl-1">
						<label for="importFabricsAccessoriesItem"
							class="col-form-label-sm my-0 py-0">Fabrics/Accessories
							Item</label> <select id="importFabricsAccessoriesItem"
							class="selectpicker col-md-12 px-0" data-live-search="true"
							data-style="btn-light btn-sm border-light-gray">
							<option value="0">--Select Indent Item--</option>
						</select>
					</div>

					<div class="col-md-2 pr-0 pl-1">
						<label for="importColor" class="col-form-label-sm my-0 py-0">Color</label>
						<select id="importColor" class="selectpicker col-md-12 px-0"
							data-live-search="true"
							data-style="btn-light btn-sm border-light-gray">
							<option value="0">--Select Color--</option>
							<c:forEach items="${colorList}" var="color">
								<option value="${color.colorId}">${color.colorName}</option>
							</c:forEach>
						</select>
					</div>

					<div class="col-md-1 pr-0 pl-1">
						<label for="importSize" class="col-form-label-sm my-0 py-0">Size</label>
						<input id="importSize" type="text"
							class="form-control-sm pr-0 pl-1">
					</div>
					<div class="col-md-1 pr-0 pl-1">
						<label for="importUnit" class="col-form-label-sm my-0 py-0">Unit</label>
						<select id="importUnit" class="selectpicker col-md-12 px-0"
							data-live-search="true"
							data-style="btn-light btn-sm border-light-gray">
							<option value="0">Select Unit</option>
							<c:forEach items="${unitList}" var="unit" varStatus="counter">
								<option value="${unit.unitId}">${unit.unitName}</option>
							</c:forEach>
						</select>
					</div>

					<div class="col-md-1 pr-0 pl-1">
						<label for="importConsumption" class="col-form-label-sm my-0 py-0">Consumption</label>
						<input id="importConsumption" type="number"
							class="form-control-sm pr-0 pl-1">
					</div>
					<div class="col-md-1 pr-0 pl-1">
						<label for="importWidth" class="col-form-label-sm my-0 py-0">Width</label>
						<input id="importWidth" type="number"
							class="form-control-sm pr-0 pl-1">
					</div>
					<div class="col-md-1 pr-0 pl-1">
						<label for="importGSM" class="col-form-label-sm my-0 py-0">GSM</label>
						<input id="importGSM" type="number"
							class="form-control-sm pr-0 pl-1">
					</div>

					<div class="col-md-1 pr-0 pl-1">
						<label for="importTotalQty" class="col-form-label-sm my-0 py-0">Total
							Qty</label> <input id="importTotalQty" type="number"
							class="form-control-sm pr-0 pl-1"
							onkeyup="importItemTotalValueCalculate()">
					</div>

					<div class="col-md-1 pr-0 pl-1">
						<label for="importPrice" class="col-form-label-sm my-0 py-0">Price</label>
						<input id="importPrice" type="number"
							class="form-control-sm pr-0 pl-1"
							onkeyup="importItemTotalValueCalculate()">
					</div>

					<div class="col-md-1 pr-0 pl-1">
						<label for="importTotalValue" class="col-form-label-sm my-0 py-0">Total
							Value</label> <input id="importTotalValue" type="number"
							class="form-control-sm pr-0 pl-1" readonly>
					</div>
					<div class="col-md-1 pr-0 pl-1">
						<button id="importAddBtn" type="button"
							style="margin-top: 1.3rem;" class="btn btn-primary btn-sm"
							onclick="importItemAddAction()">
							<i class="fa fa-plus-circle"></i> Add
						</button>
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
									<th>P/O No</th>
									<th>Fabrics/Accessories Item</th>
									<th>Color</th>
									<th>Size</th>
									<th>Unit</th>
									<th>Width</th>
									<th>GSM</th>
									<th>Total Qty</th>
									<th>Price</th>
									<th>Total Value</th>
								</tr>
							</thead>
							<tbody id="importItemList">

							</tbody>
						</table>
					</div>
				</div>
				<div class="row mt-1">
					<div class="col-md-12 d-flex justify-content-end">
						<button id="importSubmitButton" type="button"
							class="btn btn-primary btn-sm" onclick="importSubmitAction()">
							<i class="fas fa-save"></i> Submit
						</button>
						<button id="importAmendmentButton" type="button"
							class="btn btn-primary btn-sm ml-1"
							onclick="importAmendmentAction()" style="">
							<i class="fas fa-save"></i> Amendment
						</button>
						<button id="importEditButton" type="button"
							class="btn btn-primary btn-sm ml-1" onclick="importEditAction()"
							style="display: none;">
							<i class="fa fa-pencil-square"></i> Edit
						</button>
						<button id="importRefreshBtn" type="button"
							class="btn btn-primary btn-sm ml-1"
							onclick="importRefreshAction()">
							<i class="fa fa-refresh"></i> Refresh
						</button>
						<button id="importPreviewBtn" type="button"
							class="btn btn-primary btn-sm ml-1"
							onclick="importPreviewAction()" style="display: none;">
							<i class="fa fa-print"></i> Preview
						</button>
					</div>
				</div>
				<br>
				<hr class="my-0">
				<h3>Bill Of Entry</h3>
				<div class="row">
					<div class="col-md-7">
						<div class="row">
							<div class="col-md-6">
								<div class="form-group mb-0  row">
									<label for="billOfEntryNo"
										class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Bill
										Entry No</label> <input id="billOfEntryNo" type="text"
										class="col-md-8 form-control-sm">
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group mb-0  row">
									<label for="billOfEntryDate"
										class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Date:</label>
									<input id="billOfEntryDate" type="date"
										class="col-md-8 form-control-sm">
								</div>
							</div>
						</div>

						<div class="row">
							<div class="col-md-6">
								<div class="form-group mb-0  row">
									<label for="billOfEntryInvoiceNo"
										class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Invoice
										No</label> <input id="billOfEntryInvoiceNo" type="text"
										class="col-md-8 form-control-sm" readonly>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group mb-0  row">
									<label for="billOfEntryInvoiceDate"
										class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Date:</label>
									<input id="billOfEntryInvoiceDate" type="date"
										class="col-md-8 form-control-sm">
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-5">
						<table
							class="table table-hover table-bordered table-sm mb-2 small-font">
							<thead class="no-wrap-text">
								<tr>
									<th>Bill No</th>
									<th>Bill Date</th>

								</tr>
							</thead>
							<tbody id="billOfEntryList">

							</tbody>
						</table>
					</div>
				</div>
				<div class="row">
					<div class="col-md-2 pr-0 pl-1">
						<label for="billStyleNo" class="col-form-label-sm my-0 py-0">Style
							No</label> <select id="billStyleNo" onchange="styleWiseBuyerPOLoad(this)"
							class="selectpicker col-md-12 px-0" data-live-search="true"
							data-style="btn-light btn-sm border-light-gray">
							<option id="styleNo" value="0">Select Style</option>
						</select>
					</div>



					<div class="col-md-2 pr-0 pl-1">
						<label for="billPurchaseOrder" class="col-form-label-sm my-0 py-0">Purchase
							Order</label> <select id="billPurchaseOrder"
							onchange="styleWiseItemLoad(this)"
							class="selectpicker col-md-12 px-0" data-live-search="true"
							data-style="btn-light btn-sm border-light-gray">
							<option id="styleNo" value="0">Select Style</option>
						</select>
					</div>

					<div class="col-md-3 pr-0 pl-1">
						<label for="billFabricsAccessoriesItem"
							class="col-form-label-sm my-0 py-0">Fabrics/Accessories
							Item</label> <select id="billFabricsAccessoriesItem"
							class="selectpicker col-md-12 px-0" data-live-search="true"
							data-style="btn-light btn-sm border-light-gray">
							<option value="0">--Select Indent Item--</option>
						</select>
					</div>

					<div class="col-md-2 pr-0 pl-1">
						<label for="billColor" class="col-form-label-sm my-0 py-0">Color</label>
						<select id="billColor" class="selectpicker col-md-12 px-0"
							data-live-search="true"
							data-style="btn-light btn-sm border-light-gray">
							<option value="0">--Select Color--</option>
							<c:forEach items="${colorList}" var="color">
								<option value="${color.colorId}">${color.colorName}</option>
							</c:forEach>
						</select>
					</div>

					<div class="col-md-1 pr-0 pl-1">
						<label for="billSize" class="col-form-label-sm my-0 py-0">Size</label>
						<input id="billSize" type="text" class="form-control-sm pr-0 pl-1">
					</div>
					<div class="col-md-1 pr-0 pl-1">
						<label for="billUnit" class="col-form-label-sm my-0 py-0">Unit</label>
						<select id="billUnit" class="selectpicker col-md-12 px-0"
							data-live-search="true"
							data-style="btn-light btn-sm border-light-gray">
							<option value="0">Select Unit</option>
							<c:forEach items="${unitList}" var="unit" varStatus="counter">
								<option value="${unit.unitId}">${unit.unitName}</option>
							</c:forEach>
						</select>
					</div>

					<div class="col-md-1 pr-0 pl-1">
						<label for="billWidth" class="col-form-label-sm my-0 py-0">Width</label>
						<input id="billWidth" type="number"
							class="form-control-sm pr-0 pl-1">
					</div>
					<div class="col-md-1 pr-0 pl-1">
						<label for="billGSM" class="col-form-label-sm my-0 py-0">GSM</label>
						<input id="billGSM" type="number"
							class="form-control-sm pr-0 pl-1">
					</div>

					<div class="col-md-1 pr-0 pl-1">
						<label for="billTotalQty" class="col-form-label-sm my-0 py-0">Total
							Qty</label> <input id="billTotalQty" type="number"
							class="form-control-sm pr-0 pl-1">
					</div>

					<div class="col-md-1 pr-0 pl-1">
						<label for="billPrice" class="col-form-label-sm my-0 py-0">Price</label>
						<input id="billPrice" type="number"
							class="form-control-sm pr-0 pl-1">
					</div>

					<div class="col-md-1 pr-0 pl-1">
						<label for="billTotalValue" class="col-form-label-sm my-0 py-0">Total
							Value</label> <input id="billTotalValue" type="number"
							class="form-control-sm pr-0 pl-1">
					</div>

					<div class="col-md-1 pr-0 pl-1">
						<button id="bollAddBtn" type="button" style="margin-top: 1.3rem;"
							class="btn btn-primary btn-sm" onclick="styleAddAction()">
							<i class="fa fa-plus-circle"></i> Add
						</button>
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
									<th>P/O No</th>
									<th>Fabrics/Accessories Item</th>
									<th>Color</th>
									<th>Size</th>
									<th>Unit</th>
									<th>Width</th>
									<th>GSM</th>
									<th>Total Qty</th>
									<th>Curton Qty</th>
									<th>Price</th>
									<th>Total Value</th>
								</tr>
							</thead>
							<tbody id="billItemList">

							</tbody>
						</table>
					</div>
				</div>
				
				<div class="row mt-1">
					<div class="col-md-6">
						<div class="form-group mb-0  row">
							<label for="billBillNo"
								class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Bill
								No</label> <input id="billBillNo" type="text"
								class="col-md-8 form-control-sm">
						</div>
					</div>
					<div class="col-md-6">
						<div class="form-group mb-0  row">
							<label for="billShippedOnBoardDate"
								class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Shipped
								On Board Date:</label> <input id="billShippedOnBoardDate" type="date"
								class="col-md-8 form-control-sm">
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-6">
						<div class="form-group mb-0  row">
							<label for="billTelexReleaseDate"
								class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Telex
								Release Date:</label> <input id="billTelexReleaseDate" type="date"
								class="col-md-8 form-control-sm">
						</div>
					</div>
					<div class="col-md-6">
						<div class="form-group mb-0  row">
							<label for="billContainerNo"
								class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Container
								No</label> <input id="billContainerNo" type="text"
								class="col-md-8 form-control-sm">
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-6">
						<div class="form-group mb-0  row">
							<label for="billVesselNo"
								class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Vessel
								No:</label> <input id="billVesselNo" type="text"
								class="col-md-8 form-control-sm">
						</div>
					</div>
					<div class="col-md-6">
						<div class="form-group mb-0  row">
							<label for="billDocumentReceiveDate"
								class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Document
								Receive Date</label> <input id="billDocumentReceiveDate" type="Date"
								class="col-md-8 form-control-sm">
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-6">
						<div class="form-group mb-0  row">
							<label for="billEtaDate"
								class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Eta
								Date</label> <input id="billEtaDate" type="Date"
								class="col-md-8 form-control-sm">
						</div>
					</div>
					<div class="col-md-6">
						<div class="form-group mb-0  row">
							<label for="billStuffingDate"
								class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Stuffing
								Date</label> <input id="billStuffingDate" type="Date"
								class="col-md-8 form-control-sm">
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-6">
						<div class="form-group mb-0  row">
							<label for="billClearingDate"
								class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Clearing
								Date</label> <input id="billClearingDate" type="Date"
								class="col-md-8 form-control-sm">
						</div>
					</div>

				</div>
				<div class="row mt-1">
					<div class="col-md-12 d-flex justify-content-end">
						<button id="billSubmitButton" type="button"
							class="btn btn-primary btn-sm" onclick="billSubmitAction()">
							<i class="fas fa-save"></i> Submit
						</button>
						<button id="billEditButton" type="button"
							class="btn btn-primary btn-sm ml-1" style="display: none;"
							onclick="billEditAction()">
							<i class="fa fa-pencil-square"></i> Edit
						</button>
						<button id="billRefreshBtn" type="button"
							class="btn btn-primary btn-sm ml-1" onclick="billRefreshAction()">
							<i class="fa fa-refresh"></i> Refresh
						</button>
						<button id="billPreviewBtn" type="button"
							class="btn btn-primary btn-sm ml-1" style="display: none;">
							<i class="fa fa-print"></i> Preview
						</button>
					</div>
				</div>
			</div>
			<div class="tab-pane fade" id="nav-export" role="tabpanel"
				aria-labelledby="nav-export-tab">
				<div class="row">
					<div class="col-md-4">

						<div class="form-group mb-0  row">
							<label for="exportMasterLcNo"
								class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Master
								LC</label>
							<div class="input-group col-md-8 px-0">
								<div class="input-group-append">
									<input id="exportMasterLcNo" type="text"
										class="form-control-sm" readonly>

									<button id="findLCBtn" type="button"
										class="btn btn-outline-dark btn-sm form-control-sm"
										data-toggle="modal" data-target="#searchModal">
										<i class="fa fa-search"></i>
									</button>
								</div>
							</div>

						</div>

						<div class="form-group mb-0  row">
							<label for="exportBuyerName"
								class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Buyer
								Name</label> <input id="exportBuyerName" type="text"
								class="col-md-8 form-control-sm" readonly>
						</div>

						<div class="form-group mb-0  row">
							<label for="exportNotifyTo"
								class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Notify
								To</label> <select id="exportNotifyTo"
								class="selectpicker col-md-8 px-0" data-live-search="true"
								data-style="btn-light btn-sm border-light-gray">
								<option value="0">Select Buyer</option>
								<c:forEach items="${notifyList}" var="notify">
									<option value="${notify.notifyId}">${notify.notifyName}</option>
								</c:forEach>
							</select>
						</div>

						<table
							class="table table-hover table-bordered table-sm mb-0 small-font">
							<thead class="no-wrap-text">
								<tr>
									<th>Invoice No</th>
									<th>Shipment Mark</th>
									<th>Shipment Date</th>
								</tr>
							</thead>
							<tbody id="exportShipmentList">

							</tbody>
						</table>
					</div>

					<div class="col-md-6">
						<div class="row">
							<div class="col-md-6">
								<div class="form-group mb-0  row">
									<label for="exportInvoiceNo"
										class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Invoice
										No</label> <input id="exportInvoiceNo" type="text"
										class="col-md-8 form-control-sm">
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group mb-0  row">
									<label for="exportInvoiceDate"
										class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Date:</label>
									<input id="exportInvoiceDate" type="date"
										class="col-md-8 form-control-sm">
								</div>
							</div>
						</div>

						<div class="row">
							<div class="col-md-6">
								<div class="form-group mb-0  row">
									<label for="exportConractNo"
										class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Contract
										No</label> <input id="exportContractNo" type="text"
										class="col-md-8 form-control-sm">
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group mb-0  row">
									<label for="exportContractDate"
										class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Date:</label>
									<input id="exportContractDate" type="date"
										class="col-md-8 form-control-sm">
								</div>
							</div>
						</div>

						<div class="row">
							<div class="col-md-6">
								<div class="form-group mb-0  row">
									<label for="exportExpNo"
										class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Exp
										No</label> <input id="exportExpNo" type="text"
										class="col-md-8 form-control-sm">
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group mb-0  row">
									<label for="exportExpDate"
										class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Date:</label>
									<input id="exportExpDate" type="date"
										class="col-md-8 form-control-sm">
								</div>
							</div>
						</div>

						<div class="row">
							<div class="col-md-6">
								<div class="form-group mb-0  row">
									<label for="exportBillEntryNo"
										class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Bill
										Entry No</label> <input id="exportBillEntryNo" type="text"
										class="col-md-8 form-control-sm">
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group mb-0  row">
									<label for="exportBillEntryDate"
										class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Date:</label>
									<input id="exportBillEntryDate" type="date"
										class="col-md-8 form-control-sm">
								</div>
							</div>
						</div>

						<div class="row">
							<div class="col-md-6">
								<div class="form-group mb-0  row">
									<label for="exportBLNo"
										class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">B/L
										No</label> <input id="exportBLNo" type="text"
										class="col-md-8 form-control-sm">
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group mb-0  row">
									<label for="exportBLDate"
										class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Date:</label>
									<input id="exportBLDate" type="date"
										class="col-md-8 form-control-sm">
								</div>
							</div>
						</div>

						<div class="row">
							<div class="col-md-6">
								<div class="form-group mb-0  row">
									<label for="exportShippingMark"
										class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Shipping
										Mark</label> <input id="exportShippingMark" type="text"
										class="col-md-8 form-control-sm">
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group mb-0  row">
									<label for="exportShippingDate"
										class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Date:</label>
									<input id="exportShippingDate" type="date"
										class="col-md-8 form-control-sm">
								</div>
							</div>
						</div>
					</div>


				</div>

				<div class="row">
					<div class="col-md-2 pr-0 pl-1">
						<label for="exportStyleNo" class="col-form-label-sm my-0 py-0">Style
							No</label> <select id="exportStyleNo"
							onchange="styleWiseItemLoad(this),styleWiseBuyerPOLoad(this)"
							class="selectpicker col-md-12 px-0" data-live-search="true"
							data-style="btn-light btn-sm border-light-gray">
							<option id="styleNo" value="0">Select Style</option>
						</select>
					</div>

					<div class="col-md-3 pr-0 pl-1">
						<label for="exportItemName" class="col-form-label-sm my-0 py-0">style
							Item Description</label> <select id="exportItemName"
							class="selectpicker col-md-12 px-0" data-live-search="true"
							data-style="btn-light btn-sm border-light-gray">
							<option value="0">--Select Indent Item--</option>
						</select>
					</div>

					<div class="col-md-2 pr-0 pl-1">
						<label for="exportPurchaseOrder"
							class="col-form-label-sm my-0 py-0">Purchase Order</label> <select
							id="exportPurchaseOrder" onchange=""
							class="selectpicker col-md-12 px-0" data-live-search="true"
							data-style="btn-light btn-sm border-light-gray">
							<option id="styleNo" value="0">Select Style</option>
						</select>
					</div>

					<div class="col-md-4 pr-0">
						<div class="row">
							<div class="col-md-4 pr-0 pl-1">
								<label for="exportQuantity" class="col-form-label-sm my-0 py-0">Quantity</label>
								<input id="exportQuantity" type="number"
									class="form-control-sm pr-0 pl-1">
							</div>
							<div class="col-md-4 pr-0 pl-1">
								<label for="exportUnitPrice" class="col-form-label-sm my-0 py-0">Unit
									Price</label> <input id="exportUnitPrice" type="number"
									class="form-control-sm pr-0 pl-1">
							</div>
							<div class="col-md-2 pr-0 pl-1">
								<label for="exportCartonQty" class="col-form-label-sm my-0 py-0">Ctns
									Qty</label> <input id="exportCartonQty" type="number"
									class="form-control-sm pr-0 pl-1">
							</div>
							<div class="col-md-2 pr-0 pl-1">
								<button id="exportAddBtn" type="button"
									style="margin-top: 1.3rem;" class="btn btn-primary btn-sm"
									onclick="exportStyleAddAction()">
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
									<th>P/O No</th>
									<th>Quantity</th>
									<th>Unit Price</th>
									<th>Amount</th>
									<th>CuttonQty</th>
									<th>Delete</th>
								</tr>
							</thead>
							<tbody id="exportStyleList">

							</tbody>
						</table>
					</div>
				</div>

				<div class="row mt-1">
					<div class="col-md-12 d-flex justify-content-end">
						<button id="exportSubmitBtn" type="button"
							class="btn btn-primary btn-sm" onclick="exportSubmitAction()">
							<i class="fas fa-save"></i> Submit
						</button>
						<button id="exportEditBtn" type="button"
							class="btn btn-primary btn-sm ml-1" onclick="exportEditAction()"
							style="display: none;">
							<i class="fa fa-pencil-square"></i> Edit
						</button>
						<button id="exportRefreshBtn" type="button"
							class="btn btn-primary btn-sm ml-1"
							onclick="exportRefreshAction()">
							<i class="fa fa-refresh"></i> Refresh
						</button>
						<button id="exportPreviewBtn" type="button"
							class="btn btn-primary btn-sm ml-1" style="display: none;">
							<i class="fa fa-print"></i> Preview
						</button>
					</div>
				</div>
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
							<th>Buyer Name</th>
							<th>Master LC</th>
							<th>LC Date</th>
							<th><span><i class="fa fa-search"></i></span></th>
						</tr>
					</thead>
					<tbody id="masterLcList">
						<c:forEach items="${masterLCList}" var="master"
							varStatus="counter">
							<tr>
								<td>${master.buyerName}</td>
								<td>${master.masterLCNo}</td>
								<td>${master.date}</td>
								<th><span><i style="cursor: pointer;"
										onclick="searchMasterLc('${master.masterLCNo}','${master.buyerId}','${master.amendmentNo }')"
										class="fa fa-search"></i></span></th>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>

		</div>
	</div>
</div>


<jsp:include page="../include/footer.jsp" />

<script
	src="${pageContext.request.contextPath}/assets/js/commercial/master-lc.js"></script>
