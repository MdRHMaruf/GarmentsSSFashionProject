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
	<input type="hidden" id="buyerPOId" value="0">
	<input type="hidden" id="itemAutoId" value="0">

	<div class="card-box">
		<header class="d-flex justify-content-between">
			<h5 class="text-center" style="display: inline;">Buyer Purchase
				Order</h5>
			<button type="button" class="btn btn-outline-dark btn-sm"
				data-toggle="modal" data-target="#exampleModal">
				<i class="fa fa-search"></i>
			</button>
		</header>
		<hr class="my-1">
		<div class="row">
			<div class="col-md-9 mb-1 pr-1">
				<div class="row">
					<div class="form-group col-md-4 mb-1" style="padding-right: 1px;">
						<label for="buyerName" class="col-form-label-sm mb-0 pb-0">Buyer
							Name:</label>
						<div class="row">
							<select id="buyerName" class="selectpicker col-md-12"
								onchange="buyerWiseStyleLoad()" data-live-search="true"
								data-style="btn-light btn-sm border-light-gray">
								<option value="0">Select Buyer</option>
								<c:forEach items="${buyerList}" var="buyer">
									<option id="buyerName" value="${buyer.buyerid}">${buyer.buyername}</option>
								</c:forEach>
							</select>
						</div>


					</div>
					<div class="form-group col-md-4 mb-1"
						style="padding-left: 1px; padding-right: 1px;">
						<label for="styleNo" class="col-form-label-sm mb-0 pb-0">Style
							No:</label>
						<div class="row">
							<select id="styleNo" class="selectpicker col-md-12"
								onchange="styleWiseItemLoad()" data-live-search="true"
								data-style="btn-light btn-sm border-light-gray">
								<option id="styleNo" value="0">Select Style</option>
							</select>
						</div>

					</div>
					<div class="form-group col-md-4 mb-1" style="padding-left: 1px;">
						<label for="itemType" class="col-form-label-sm mb-0 pb-0">Item
							Type:</label>
						<div class="row">
							<select id="itemType" class="selectpicker col-md-12"
								data-live-search="true"
								data-style="btn-light btn-sm border-light-gray">
								<option id="itemType" value="0">Select Item Type</option>
							</select>
						</div>

					</div>
				</div>
				<div class="row">
					<div class="col-md-7">
						<div class="form-group mb-0  row">
							<label for="factory" class="col-md-2 col-form-label-sm pr-0 pb-0">Factory</label>

							<select id="factory" class="selectpicker col-sm-10"
								data-live-search="true"
								data-style="btn-light btn-sm border-light-gray">

								<option id="factory" value="0">Select Factory</option>
								<c:forEach items="${factoryList}" var="factory">
									<option id="factory" value="${factory.factoryid}">${factory.factoryname}</option>
								</c:forEach>
							</select>

						</div>
					</div>

					<div class="col-md-5">

						<div class="form-group mb-0 row">
							<label for="color" class="col-md-2 col-form-label-sm pr-0 pb-0">Color</label>

							<select id="color" class="selectpicker col-md-10"
								data-live-search="true"
								data-style="btn-light btn-sm border-light-gray">

								<option id="color" value="0">Select Color</option>
								<c:forEach items="${colorList}" var="color">
									<option id="color" value="${color.colorId}">${color.colorName}</option>
								</c:forEach>
							</select>

						</div>


					</div>
				</div>
				<div class="row">
					<div class="col-md-6">
						<div class="form-group mb-0  row">
							<label for="customerOrder "
								class="col-md-4 col-form-label-sm pr-0 pb-0">Customer Order</label>
							<div class="col-sm-8">
								<input type="text" class="form-control-sm" id="customerOrder">
							</div>
						</div>
					</div>

					<div class="col-md-6">

						<div class="form-group mb-0 row">
							<label for="purchaseOrder"
								class="col-md-4 col-form-label-sm pr-0 pb-0">Purchase Order</label>
							<div class="col-md-8">
								<input type="text" class="form-control-sm" id="purchaseOrder">
							</div>
						</div>


					</div>
				</div>

				<div class="form-group row mb-0">
					<label for="shippingMark" class="col-sm-2 col-form-label-sm pr-0">Shipping
						mark</label>
					<div class="col-sm-10">
						<input type="text" class="form-control-sm" id="shippingMark">
					</div>
				</div>

				<div class="row ">
					<div class="col-md-8">
						<div class="row">
							<div class="col-md-5">
								<div class="form-group mb-0  row">
									<label for="paymentTerm" class="col-md-6 col-form-label-sm pr-0">Payment
										Term</label> <select id="paymentTerm" class="form-control-sm col-md-5">
										<option id="paymentTerm" value="1">TT</option>
										<option id="paymentTerm" value="2">LC</option>
									</select>

								</div>
							</div>

							<div class="col-md-5">
								<div class="form-group mb-0  row">
									<label for="currency" class="col-md-4 col-form-label-sm pr-0">Currency</label>
									<select id="currency" class="form-control-sm col-md-6">
										<option id="currency" value="1">BDT</option>
										<option id="currency" value="2">USD</option>
										<option id="currency" value="3">ERO</option>
									</select>

								</div>
							</div>
						</div>

					</div>
					<div class="col-md-4">
						<div class="row">
							<div class="col-md-12 d-flex justify-content-end">
								<button id="btnAdd" type="button" class="btn btn-primary btn-sm"
									onclick="itemSizeAdd()">
									<i class="fa fa-plus-circle"></i> Add
								</button>
								<button id="btnEdit" type="button"
									class="btn btn-primary btn-sm ml-1" onclick="itemSizeEdit()" disabled>
									<i class="fa fa-pencil-square"></i> Edit
								</button>
								<button id="btnReset" type="button"
									class="btn btn-primary btn-sm ml-1" onclick="reset()">
									<i class="fa fa-refresh"></i> Reset
								</button>
							</div>
						</div>
					</div>

				</div>

			</div>
			<div class="col-md-3 mb-1 pl-1">
				<div class="row">
					<div class="col-md-12">
						<select id="sizeGroup" class="form-control-sm border-secondary"
							style="width: 100%;" onchange="sizeLoadByGroup()">
							<option id="sizeGroup" value="0">Select Size Group</option>
							<c:forEach items="${groupList}" var="group" varStatus="counter">
								<option id="sizeGroup" value="${group.groupId}">${group.groupName}</option>
							</c:forEach>
						</select>
					</div>

				</div>
				<div class="row ">
					<div class="col-md-12">
						<div class="list-group" id="listGroup"></div>
					</div>
				</div>


			</div>
		</div>
		<div id="tableList"></div>



		<div class="row mt-1">
			<div class="col-md-6">
				<div class="input-group input-group-sm mb-1">
					<div class="input-group-prepend">
						<span class="input-group-text" id="inputGroup-sizing-sm">Note</span>
					</div>
					<input id="note" type="text" class="form-control"
						aria-label="Sizing example input"
						aria-describedby="inputGroup-sizing-sm">
				</div>
			</div>
			<div class="col-md-6">
				<div class="input-group input-group-sm mb-1">
					<div class="input-group-prepend">
						<span class="input-group-text" id="inputGroup-sizing-sm">Remarks</span>
					</div>
					<input id="remarks" type="text" class="form-control"
						aria-label="Sizing example input"
						aria-describedby="inputGroup-sizing-sm">
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
					class="btn btn-primary btn-sm ml-1" onclick = "buyerPoEditAction()" disabled>
					<i class="fa fa-pencil-square" ></i> Edit
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
<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog"
	aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<div class="input-group">
					<input id="search" type="text" class="form-control"
						placeholder="Search Buyer Purchase Order"
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
							<th>PO Id</th>
							<th>Buyer Name</th>
							<th>Date</th>
							<th><span><i class="fa fa-search"></i></span></th>
						</tr>
					</thead>
					<tbody id="poList">
						<c:forEach items="${buyerPoList}" var="po" varStatus="counter">
							<tr>
								<td>${po.buyerPoId}</td>
								<td id='buyerName${po.buyerPoId}'>${po.buyerName}</td>
								<td >${po.date}</td>
								<td><i class="fa fa-search"
									onclick="searchBuyerPO(${po.buyerPoId})">
								</i></td>
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
	src="${pageContext.request.contextPath}/assets/js/order/buyer-purchase-order.js"></script>
