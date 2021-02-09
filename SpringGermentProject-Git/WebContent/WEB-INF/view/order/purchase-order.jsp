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
%>

<jsp:include page="../include/header.jsp" />

<div class="page-wrapper">

	<input type="hidden" id="userId" value="<%=userId%>"> <input
		type="hidden" id="poNo" value="0"><input
		type="hidden" id="poType" value="">

	<div class="card-box">
		<header class="d-flex justify-content-between">
			<h5 class="text-center" style="display: inline;">Purchase Order <span class="badge badge-primary" id='poNoBadge'>New</span></h5>
			<button type="button" class="btn btn-outline-dark btn-sm"
				data-toggle="modal" data-target="#searchModal">
				<i class="fa fa-search"></i>
			</button>
		</header>
		<hr class="my-1">
		<div class="row">
			<div class="col-md-4">
				<div class="form-group mb-0  row">
					<label for="orderDate"
						class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Order
						Date<span style="color:red">*</span></label> <input id="orderDate" type="date"
						class="col-md-8 form-control-sm">

				</div>
				<div class="form-group mb-0  row">
					<label for="deliveryDate"
						class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Delivery
						Date<span style="color:red">*</span></label> <input id="deliveryDate" type="date"
						class="col-md-8 form-control-sm">

				</div>

				<%-- <div class="form-group col-md-3 mb-1  pr-0 pl-1">
				<label for="supplierName" class="col-form-label-sm my-0 py-0">Supplier Name</label>
				<div class="row">
					<select id="supplierName" class="selectpicker col-md-12"
						data-live-search="true"
						data-style="btn-light btn-sm border-light-gray">
						<option id="supplierName" value="0">--Select
							SupplierName--</option>
						<c:forEach items="${supplierList}" var="supplier">
							<option id="supplierName" value="${supplier.supplierid}">${supplier.suppliername}</option>
						</c:forEach>
					</select>
				</div>

			</div> --%>
				<div class="form-group mb-0  row">
					<label for="supplierName"
						class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Supplier
						Name<span style="color:red">*</span></label> <select id="supplierName" class="selectpicker col-md-8 px-0"
						data-live-search="true"
						data-style="btn-light btn-sm border-light-gray">
						<option id="supplierName" value="0">--Select
							SupplierName--</option>
						<c:forEach items="${supplierList}" var="supplier">
							<option id="supplierName" value="${supplier.supplierid}">${supplier.suppliername}</option>
						</c:forEach>
					</select>

				</div>
			</div>
			<div class="col-md-4">
				<div class="form-group mb-0  row">
					<label for="deliveryTo"
						class="col-md-3 col-form-label-sm pr-0 mb-1 pb-1">Delivery
						To<span style="color:red">*</span></label> <select id="deliveryTo" class="selectpicker col-md-9 px-0"
						data-live-search="true"
						data-style="btn-light btn-sm border-light-gray">
						<option id="deliveryTo" value="0">--- Select ---</option>
						<c:forEach items="${factoryList}" var="factory">
							<option id="deliveryTo" value="${factory.factoryId}">${factory.factoryName}</option>
						</c:forEach>
					</select>

				</div>
				<%-- <div class="form-group mb-0  row">
					<label for="orderBy"
						class="col-md-3 col-form-label-sm pr-0 mb-1 pb-1">Order By<span style="color:red">*</span></label>
					<select id="orderBy" class="selectpicker col-md-9 px-0"
						data-live-search="true"
						data-style="btn-light btn-sm border-light-gray">
						<option id="orderBy" value="0">--- Select ---</option>
						<c:forEach items="${merchendiserList}" var="merchandiser">
							<option id="orderBy" value="${merchandiser.merchendiserId}">${merchandiser.name}</option>
						</c:forEach>
					</select>

				</div> --%>
				<div class="form-group mb-0  row">
					<label for="billTo"
						class="col-md-3 col-form-label-sm pr-0 mb-1 pb-1">Bill To</label>
					<select id="billTo" class="selectpicker col-md-9 px-0"
						data-live-search="true"
						data-style="btn-light btn-sm border-light-gray">
						<option id="billTo" value="0">--- Select ---</option>
						<c:forEach items="${factoryList}" var="factory">
							<option id="billTo" value="${factory.factoryId}">${factory.factoryName}</option>
						</c:forEach>
					</select>

				</div>
			</div>
			<div class="col-md-4">
				<div class="form-group mb-0  row">
					<label for="manualPO"
						class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Manual
						PO</label> <input id="manualPO" type="text"
						class="col-md-8 form-control-sm" disabled>

				</div>
				<div class="form-group mb-0  row">
					<label for="paymentType"
						class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Payment
						Type<span style="color:red">*</span></label> <select id="paymentType"
						class="form-control-sm col-md-8 px-0">
						<option value="0">Select Payment Type</option>
						<%
							
							for (PaymentType payment: PaymentType.values()) {
						%>
						<option value="<%=payment.name()%>"><%=payment.name()%></option>
						<%
							}
						%>
					</select>

				</div>
				<div class="form-group mb-0  row">
					<label for="currency"
						class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Currency<span style="color:red">*</span></label>
					<select id="currency" class="form-control-sm col-md-8 px-0">
						<option value="0">Select Currency</option>
						<%
							
							for (Currency currency: Currency.values()) {
						%>
						<option value="<%=currency.name()%>"><%=currency.name()%></option>
						<%
							}
						%>
					</select>

				</div>
			</div>
		</div>
		<div class="row">
			<%-- <div class="col-md-2 pr-0 pl-1">
				<label for="styleNo" class="col-form-label-sm my-0 py-0">Style
					No</label> <select id="styleNo" onchange="typeWiseIndentItemLoad()"
					class="selectpicker col-md-12 px-0" data-live-search="true"
					data-style="btn-light btn-sm border-light-gray">
					<option id="styleNo" value="0">Select Style</option>
				</select>
			</div>
			
			<div class="col-md-1 pr-0 pl-1">
				<label for="type" class="col-form-label-sm my-0 py-0">Type</label> <select
					id="type" onchange="typeWiseIndentItemLoad()"
					class="form-control-sm col-md-12 px-0">
					<option id="type" value="0">Select Type</option>
					<option id="type" value="1">Fabrics</option>
					<option id="type" value="2">Accessories</option>
					<option id="type" value="3">Curton</option>
				</select>
			</div> --%>


			<div class='col-md-3 px-1'>
				<div class="input-group input-group-sm" style="margin-top: 21px;">
					<input id="indentId" type="text" class="form-control"
						placeholder="Indent Id"
						aria-label="input"
						aria-describedby="button-addon4" readonly="readonly">
						<input id="indentType" type="text" class="form-control"
						placeholder="Indent Type"
						aria-label="input"
						aria-describedby="button-addon4" readonly="readonly">
					<div class="input-group-append" id="button-addon4">
						
						<button type="button" class="btn btn-outline-dark btn-sm"
				data-toggle="modal" data-target="#indentSearchModal">
				<i class="fa fa-search"></i>
			</button>
					</div>
				</div>
			</div>



			<div class="col-md-3 pr-0 pl-1">
				<label for="indentItem" class="col-form-label-sm my-0 py-0">Indent
					Item</label> <select id="indentItem" class="selectpicker col-md-12 px-0"
					data-live-search="true"
					data-style="btn-light btn-sm border-light-gray">
					<option id="indentItem" value="0">--Select Indent Item--</option>
				</select>
			</div>

			<%-- <div class="form-group col-md-3 mb-1  pr-0 pl-1">
				<label for="supplierName" class="col-form-label-sm my-0 py-0">Supplier
					Name</label>
				<div class="row">
					<select id="supplierName" class="selectpicker col-md-12"
						data-live-search="true"
						data-style="btn-light btn-sm border-light-gray">
						<option id="supplierName" value="0">--Select
							SupplierName--</option>
						<c:forEach items="${supplierList}" var="supplier">
							<option id="supplierName" value="${supplier.supplierid}">${supplier.suppliername}</option>
						</c:forEach>
					</select>
				</div>

			</div> --%>

			<div class="col-md-3 pr-0">
				<div class="row">
					<div class="col-md-4 pr-0 pl-1">
						<label for="rate" class="col-form-label-sm my-0 py-0">Rate</label>
						<input id="rate" type="number" class="form-control-sm pr-0 pl-1">
					</div>
					<div class="col-md-4 pr-0 pl-1">
						<label for="dollar" class="col-form-label-sm my-0 py-0">Dollar</label>
						<input id="dollar" type="number" class="form-control-sm pr-0 pl-1">
					</div>
					<div class="col-md-4 pr-0 pl-1">

						<button id="btnAdd" type="button" style="margin-top: 1.3rem;"
							class="btn btn-primary btn-sm" onclick="indentItemAdd()">
							<i class="fa fa-plus-circle"></i> Add
						</button>
					</div>

				</div>

			</div>

		</div>

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
		<hr class="my-1">
		<div class="row mt-1">
			<div style="overflow: auto; max-height: 300px;"
				class="col-sm-12 px-1 table-responsive">
				<table
					class="table table-hover table-bordered table-sm mb-0 small-font">
					<thead class="no-wrap-text">
						<tr>
							<th>Buyer PO</th>
							<th>Style No</th>
							<th>Item Name</th>
							<th>Item Color</th>
							<th>Size</th>
							<th>Grand Qty</th>
							<th>Unit</th>
							<th>Dollar</th>
							<th>Rate</th>
							<th>Amount</th>
							<th><input type="checkbox" id="allCheck"></th>
						</tr>
					</thead>
					<tbody id="dataList">
						<%-- <c:forEach items="${fabricsIndentList}" var="indent"
							varStatus="counter">
							<tr>

								<td>${indent.styleName}</td>
								<td>${indent.itemName}</td>
								<td>${indent.itemColorName}</td>
								<td>${indent.supplierName}</td>
								<td>${indent.dozenQty}</td>
								<td>${indent.consumption}</td>
								<td>${indent.percentQty}</td>
								<td>${indent.totalQty}</td>
								<td>${indent.unit}</td>
								<th><input type="checkbox"></th>
							</tr>
						</c:forEach> --%>
					</tbody>
				</table>
			</div>
		</div>


		<div class="row mt-1">
			<div class="col-md-6">
				<div class="input-group input-group-sm mb-1">
					<div class="input-group-prepend">
						<span class="input-group-text" for="note">Note</span>
					</div>
					<textarea id="note" class="form-control"
						aria-label="Sizing example input"
						aria-describedby="inputGroup-sizing-sm"></textarea>
				</div>
			</div>
			<div class="col-md-6">
				<div class="input-group input-group-sm mb-1">
					<div class="input-group-prepend">
						<span class="input-group-text" for="subject">Subject</span>
					</div>
					<textarea id="subject" class="form-control"
						aria-label="Sizing example input"
						aria-describedby="inputGroup-sizing-sm"></textarea>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12 d-flex justify-content-end">
				<button id="btnPOSubmit" type="button"
					class="btn btn-primary btn-sm" onclick="submitAction()">
					<i class="fas fa-save"></i> Submit
				</button>
				<button id="btnPOEdit" type="button"
					class="btn btn-success btn-sm ml-1" onclick="purchaseOrderEdit()"
					style="display:none;">
					<i class="fa fa-pencil-square"></i> Edit
				</button>
				<button id="btnRefresh" type="button"
					class="btn btn-secondary btn-sm ml-1" onclick="refreshAction()">
					<i class="fa fa-refresh"></i> Refresh
				</button>
				<button id="btnPreview" type="button"
					class="btn btn-info btn-sm ml-1" onclick="previewAction()" style="display:none;">
					<i class="fa fa-print"></i> Preview
				</button>
			</div>
		</div>
	</div>
</div>
<!-- Modal -->
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
							<th>Purchase Order No</th>
							<th>Supplier Name</th>
							<th>Date</th>
							<th>Type</th>
							<th><span><i class="fa fa-search"></i></span></th>
							<th><span><i class="fa fa-print"></i></span></th>
						</tr>
					</thead>
					<tbody id="purchaseOrderList">
						<c:forEach items="${purchaseOrderList}" var="po"
							varStatus="counter">
							<tr id="row-${counter.count}" data-supplierId="${po.supplierId}">
								<td>${po.poNo}</td>
								<td>${po.supplierName}</td>
								<td>${po.orderDate}</td>
								<td>${po.type}</td>
								<td><i class="fa fa-search" style="cursor: pointer;"
									onclick="searchPurchaseOrder('${po.poNo}','${po.type}')"> </i></td>
								<td><i class="fa fa-print" style="cursor: pointer;"
									onclick="showPreview('${po.poNo}','${po.supplierId}','${po.type}')">
								</i></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>

		</div>
	</div>
</div>


<!-- Modal -->
<div class="modal fade" id="indentSearchModal" tabindex="-1" role="dialog"
	aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<div class="input-group">
					<input id="indentSearch" type="text" class="form-control"
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
							<th>Indent Id</th>
							<th>Indent Type</th>
							<th>Date</th>
							<th><span><i class="fa fa-search"></i></span></th>
							
						</tr>
					</thead>
					<tbody id="purchaseOrderList">
						<c:forEach items="${pendingIndentList}" var="indent"
							varStatus="counter">
							<tr id="row-${indent.id}+${indent.type}">
								<td>${indent.id}</td>
								<td>${indent.indentType}</td>
								<td>${indent.date}</td>
								<td><i class="fa fa-search" style="cursor: pointer;"
									onclick="searchIndentItem('${indent.id}','${indent.indentType }')"> </i></td>
								
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
	src="${pageContext.request.contextPath}/assets/js/order/purchase-order.js"></script>
