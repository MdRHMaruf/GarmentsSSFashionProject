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
%>

<jsp:include page="../include/header.jsp" />

<div class="page-wrapper">
	<div class="alert alert-success alert-dismissible fade show"
		style="display: none;">
		<p id="successAlert" class="mb-0">
			<strong>Success!</strong> Parcel Save Successfully..
		</p>
	</div>
	<div class="alert alert-warning alert-dismissible fade show"
		style="display: none;">
		<p id="warningAlert" class="mb-0">
			<strong>Warning!</strong> Parcel Empty.Please Enter Parcel...
		</p>
	</div>
	<div class="alert alert-danger alert-dismissible fade show"
		style="display: none;">
		<p id="dangerAlert" class="mb-0">
			<strong>Wrong!</strong> Something Wrong...
		</p>
	</div>
	<input type="hidden" id="userId" value="<%=userId%>">
	<input type="hidden" id="checkListId" value=""> <input
		type="hidden" id="checkListItemAutoId" value=""> <input
		type="hidden" id="checkItemType" value="">

	<div class="card-box">
		<!-- <div class="row">
			<div class="col-md-12"> -->
		<header class="d-flex justify-content-between">
			<h5 class="text-center" style="display: inline;">Accessories
				Check List</h5>
			<button type="button" class="btn btn-outline-dark btn-sm"
				data-toggle="modal" data-target="#exampleModal">
				<i class="fa fa-search"></i>Check List
			</button>
		</header>
		<hr class="my-1">
		<div class="row">
			<div class="col-md-7">
				<div class="row">
					<label for="buyerName" class="col-form-label-sm mb-0 pr-0 col-md-2">Buyer
						Name:</label> <select id="buyerName" class="selectpicker col-md-9"
						onchange="buyerWisePoLoad()" data-live-search="true"
						data-style="btn-light btn-sm border-light-gray">
						<option value="0">Select Buyer</option>
						<c:forEach items="${buyerList}" var="buyer">
							<option value="${buyer.buyerid}">${buyer.buyername}</option>
						</c:forEach>
					</select>

				</div>
				<div class="row">
					<label for="purchaseOrder"
						class="col-form-label-sm mb-0 pr-0 col-md-2">Purchase
						Order:</label> <select id="purchaseOrder" class="selectpicker col-md-9"
						onchange="poWiseStyles()" data-live-search="true"
						data-style="btn-light btn-sm border-light-gray">
						<option id="purchaseOrder" value="0">Select Purchase
							Order</option>
					</select>

				</div>
				<div class="row">
					<label for="styleNo" class="col-form-label-sm mb-0 pr-0 col-md-2">Style
						No:</label> <select id="styleNo" class="selectpicker col-md-9"
						onchange="styleWiseItemLoad()" data-live-search="true"
						data-style="btn-light btn-sm border-light-gray">
						<option id="styleNo" value="0">Select Style</option>
					</select>
				</div>
				<div class="row">
					<label for="itemName" class="col-form-label-sm mb-0 pr-0 col-md-2">Item
						Desc.:</label> <select id="itemName" class="selectpicker col-md-9"
						data-live-search="true"
						data-style="btn-light btn-sm border-light-gray"
						onchange="styleItemsWiseColor()">
						<option id="itemName" value="0">Select Item Description</option>
					</select>
				</div>

				<div class="row">
					<label for="sampletype"
						class="col-form-label-sm mb-0 pr-0 col-md-2">Sample Type:</label>
					<select id="sampleType" onchange="" class="selectpicker col-md-4"
						data-live-search="true"
						data-style="btn-light btn-sm border-light-gray">
						<option id="sampletype" value="0">Select Sample</option>
						<c:forEach items="${sampletype}" var="po">
							<option id="sampletype" value="${po.id}">${po.name}</option>
						</c:forEach>
					</select>
				</div>


			</div>
		</div>
		<div class="row">
			<div class="col-md-1 pr-0 pl-1">
				<label for="itemType" class="col-form-label-sm my-0 py-0">Item
					Type</label> <select id="itemType" onchange="typeWiseIndentItemLoad()"
					class="form-control-sm col-md-12 px-0">
					<option value="1">Fabrics</option>
					<option value="2">Accessories</option>
				</select>
			</div>

			<div class="col-md-3 pr-0 pl-1">
				<label for="fabricsAccessoriesItem"
					class="col-form-label-sm my-0 py-0">Fabrics/Accessories
					Item</label> <select id="fabricsAccessoriesItem"
					class="selectpicker col-md-12 px-0" data-live-search="true"
					data-style="btn-light btn-sm border-light-gray">
					<option id="fabricsAccessoriesItem" value="0">--Select Item--</option>
						<c:forEach items="${fabricsList}" var="fabrics">
							<option id="fabricsItem" value="${fabrics.fabricsItemId}">${fabrics.fabricsItemName}</option>
						</c:forEach>
				</select>
			</div>

			<div class="form-group col-md-2 pl-1">
				<label for="colorName" class="col-form-label-sm my-0 py-0">Color
					Name</label>
				<div class="row">
					<select id="colorName" class="selectpicker col-md-12"
						onchange="styleItemWiseColorSizeLoad()" data-live-search="true"
						data-style="btn-light btn-sm border-light-gray">
					</select>
				</div>
			</div>

			<div class="form-group col-md-1 pl-1">
				<label for="size" class="col-form-label-sm my-0 py-0">Size:</label>
				<div class="row">
					<select id="size" class="selectpicker col-md-12 px-0" onchange=""
						data-live-search="true"
						data-style="btn-light btn-sm border-light-gray">
					</select>
				</div>
			</div>

			<div class="form-group col-md-1 mb-1  pr-0 pl-1">
				<label for="quantity" class="col-form-label-sm my-0 py-0">Quantity:</label>
				<input id="quantity" type="number" class="form-control-sm col-md-12">
			</div>

			<div class="form-group col-md-1 mb-1  pr-0 pl-1">
				<label for="status" class="col-form-label-sm my-0 py-0">Status</label>
				<select id="status" class="form-control-sm col-md-12 col-md-12">
					<option value="1">Ok</option>
					<option value="0">Missing</option>
				</select>
			</div>

			<div class='col-md-3'>
				<div class="row" style="margin-top: 20px; margin-left: 5px;">
					<div class="col-md-12">
						<button id="btnAdd" type="button" class="btn btn-primary btn-sm"
							onclick="itemAddAction()">
							<i class="fa fa-plus-circle"></i> Add
						</button>
						<button type="button" class="btn btn-primary btn-sm ml-1"
							id="btnEdit" onclick="itemEditAction()" disabled>
							<i class="fa fa-pencil-square"></i> Edit
						</button>
						<button id="btnItemRefresh" type="button"
							class="btn btn-primary btn-sm ml-1" onclick="itemRefreshAction()">
							<i class="fa fa-refresh"></i> Refresh
						</button>
					</div>

				</div>
			</div>
		</div>

		<div class="row mt-1">
			<div style="overflow: auto; max-height: 300px;"
				class="col-sm-12 px-1 table-responsive">
				<table
					class="table table-hover table-bordered table-sm mb-0 small-font">
					<thead class="no-wrap-text">
						<tr>
							<th>Accessories/Fabrics Item</th>
							<th>Color</th>
							<th>Size</th>
							<th>Quantity</th>
							<th>Status</th>
							<th>Edit</th>
							<th>Delete</th>
						</tr>
					</thead>
					<tbody id="dataList">

					</tbody>
				</table>
			</div>
		</div>
		<div class="row mt-1">

			<div class="col-md-6">

				<div class="row">
					<div class="col-md-12">
						<label for="remarks" class="col-form-label-sm mb-0 pr-0 py-0">Remarks:</label>
						<br>
						<textarea class="form-control-sm w-100" id="remarks"></textarea>
					</div>

				</div>
			</div>
		</div>

		<div class="row mt-1">
			<div class="col-md-12 d-flex justify-content-end">
				<button type="button" id="btnConfirm" class="btn btn-primary btn-sm">
					<i class="fas fa-save"></i> Confirm
				</button>

				<button id="btnRefresh" type="button"
					class="btn btn-primary btn-sm ml-1" onclick="refreshAction()">
					<i class="fa fa-refresh"></i> Refresh
				</button>

			</div>
		</div>


	</div>
</div>

<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog"
	aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<div class="input-group">
					<input id="search" type="text" class="form-control"
						placeholder="Search ParcelF" aria-label="Recipient's username"
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
							<th>Auto Id</th>
							<th>Buyer Name</th>
							<th>Sample Type</th>
							<th><span><i class="fa fa-search"></i></span></th>
							<th><span><i class="fa fa-print"></i></span></th>
						</tr>
					</thead>
					<tbody id="poList">
						<c:forEach items="${parcelList}" var="po" varStatus="counter">
							<tr>
								<td>${po.autoId}</td>
								<td>${po.buyerName}</td>
								<td>${po.sampleType}</td>
								<td><i class="fa fa-search"
									onclick="getCheckListDetails(${po.autoId})"> </i></td>
								<td><i class="fa fa-print"
									onclick="checkListReport(${po.autoId})"> </i></td>
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
	src="${pageContext.request.contextPath}/assets/js/order/accessories-check-list.js"></script>
