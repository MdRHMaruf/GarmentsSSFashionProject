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
<jsp:include page="../include/header.jsp" />
<%
	List<Login> lg = (List<Login>) session.getAttribute("pg_admin");
	String invoiceId=(String) request.getAttribute("InvoiceId");
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
	<input type="hidden" id="departmentId" value="<%=lg.get(0).getDepartmentId()%>">


	<div class="card-box">
		<header class="d-flex justify-content-between">
			<h5 class="text-center" style="display: inline;">General Item Receive</h5>
			<button type="button" class="btn btn-outline-dark btn-sm"
				data-toggle="modal" data-target="#searchModal">
				<i class="fa fa-search"></i>
			</button>
		</header>
		<hr class="my-1">
		<div class="row">

			<div class="col-md-4">
				<div class="form-group mb-0  row">
					<label for="supplier"
						class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Invoice
						No</label> <input id="invoiceNo" readonly type="text" value="<%=invoiceId %>"
						class="col-md-8 form-control-sm">

				</div>
				<div class="form-group mb-0  row">
					<label for="supplier"
						class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Supplier</label>
					<select id="supplierId" class="selectpicker col-md-8 px-0"
						data-live-search="true"
						data-style="btn-light btn-sm border-light-gray">
						<option id="supplierId" value="0">--- Select ---</option>
						<c:forEach items="${supList}" var="list">
							<option id="supplierId" value="${list.supplierid}">${list.suppliername}</option>
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
						class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1"> Date</label> <input
						id="challanDate" type="date" class="col-md-8 form-control-sm">

				</div>



			</div>

		</div>

		<div class="row">
			<div class="col-md-4">
				<div class="form-group mb-0  row">
					<label for="ItemId"
						class="col-md-4 col-form-label-sm">Product</label>
					<div class="col-md-8 pl-0 pr-0">
						<select id="ItemId" class="selectpicker" data-live-search="true"
							data-style="btn-light btn-sm border-light-gray">
							<option id="ItemId" value="0">--- Select ---</option>
							<c:forEach items="${itemList}" var="list">
								<option id="ItemId" value="${list.itemId}">${list.itemName}</option>
							</c:forEach>
						</select>
					</div>
				</div>
			</div>
			
			<div class="col-md-4">
				<div class="form-group mb-0  row">
					<label for="ItemId"
						class="col-md-2 col-form-label-sm">Unit</label>
					<div class="col-md-8">
						<select id="unitId" class="selectpicker" data-live-search="true"
							data-style="btn-light btn-sm border-light-gray">
							<option id="unitId" value="0">--- Select ---</option>
							<c:forEach items="${unitList}" var="list">
								<option id="unitId" value="${list.unitId}">${list.unitName}</option>
							</c:forEach>
						</select>
					</div>
				</div>
			</div>
			
			
			<div class="col-md-4">
				<div class="form-group mb-0  row">
					<label for="ItemId"
						class="col-md-1 col-form-label-sm">Qty</label>
					<div class="col-md-3">
						 <input id="qty" type="text" class="col-md-8 form-control-sm">
					</div>

					<div class="col-md-7">
										<label for="ItemId"
						class="col-md-1 col-form-label">Price</label>
						 <input id="pirce" type="text" class="col-md-4 form-control-sm">
						<button id="btnSubmit" type="button" class="btn btn-primary btn-sm"
						onclick="submitAction()">
						<i class="fas fa-save"></i> Submit
						</button>
					</div>

				</div>
			</div>
			
		</div>

		<hr class="my-1">
		<div class="row mt-1">
			<div class="col-md-8">
			<div style="overflow: auto; max-height: 300px;"
				class="col-sm-12 px-1 table-responsive">
				<table
					class="table table-hover table-bordered table-sm mb-0 small-font ">
					<thead class="no-wrap-text">
						<tr>
							<th>SL#</th>
							<th>Name</th>
							<th>Qty</th>
							<th>Price</th>
							<th>Total Price</th>
							<th>Del</th>
							<th>Edit</th>
						</tr>
					</thead>
					<tbody id="dataList">
								<c:forEach items="${addList}" var="list"
													varStatus="counter">
										<tr>
											<td>${counter.count}</td>
											<td id='itemName${list.autoId}'>${list.itemName}</td>
											<td id='Category${list.autoId}'>${list.qty}</td>
											<td id='Category${list.autoId}'>${list.pirce}</td>
											<td id='Category${list.autoId}'>${list.totalPrice}</td>
											<td><i class="fa fa-remove"  onclick="setData(${list.autoId})"> </i></td>
											<td><i class="fa fa-remove"  onclick="setData(${list.autoId})"> </i></td>
										</tr>
									</c:forEach>	
					</tbody>
				</table>
			</div>			
			</div>
			<div class="col-md-4">
				<div class="form-group mb-0  row">
					<label for="grossamountlb"
						class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Gross Amount</label> 
						<input id="grossAmount" readonly type="text"
						class="col-md-8 form-control-sm">

				</div>
				<div class="form-group mb-0  row">
					<label for="grossamountlb"
						class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Discount %</label> 
						<input id="discountPercent" readonly type="text"
						class="col-md-8 form-control-sm">

				</div>
				<div class="form-group mb-0  row">
					<label for="grossamountlb"
						class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Manual Discount</label> 
						<input id="manualDiscount" readonly type="text"
						class="col-md-8 form-control-sm">
				</div>
				<div class="form-group mb-0  row">
					<label for="grossamountlb"
						class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Net Amount</label> 
						<input id="netAmount" readonly type="text"
						class="col-md-8 form-control-sm">
				</div>	
				<div class="form-group mb-0  row">
					<label for="grossamountlb"
						class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Payment Type</label> 
						<select id="paymentType" class="selectpicker" data-live-search="true"
							data-style="btn-light btn-sm border-light-gray">
							<option id="paymentType" value="0">--- Select ---</option>

						    <option id="paymentType" value="1">Cash</option>
						    <option id="paymentType" value="2">Card</option>

						</select>						
				</div>		
				<div class="form-group mb-0  row">
					<label for="grossamountlb"
						class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Amount</label> 
						<input id="amount" readonly type="text"
						class="col-md-8 form-control-sm">
				</div>	
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
				
				<button id="btnEdit" type="button"
					class="btn btn-primary btn-sm ml-1" onclick="confrimAction()">
					<i class="fa fa-pencil-square"></i> Confrim
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
						placeholder="Search General Received Invoice="
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
							<th>SL#</th>
							<th>Invoice No</th>
							<th>Supplier Name</th>
							<th>Challan No</th>
							<th>Date</th>
							<th><span><i class="fa fa-print"></i></span></th>
						</tr>
					</thead>
					<tbody id="fabricsReceiveList">
								<c:forEach items="${receivedInvoiceList}" var="list"
													varStatus="counter">
										<tr>
											<td>${counter.count}</td>
											<td id='Invoice${list.invoiceNo}'>${list.invoiceNo}</td>
											<td id='Supplier${list.invoiceNo}'>${list.supplierName}</td>
											<td id='Challan${list.invoiceNo}'>${list.challanNo}</td>
											<td id='Date${list.invoiceNo}'>${list.date}</td>
											<td><i class="fa fa-print"  onclick="setGeneralReceivedInvoice(${list.invoiceNo},1)"> </i></td>
										</tr>
									</c:forEach>	
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
	src="${pageContext.request.contextPath}/assets/js/store/general-receive.js"></script>
