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

<input type="hidden" id="user_hidden" value="<%=lg.get(0).getId()%>">
<input type="hidden" id="accIndentId" value="0">

<div class="page-wrapper">

	<div class="card-box m-2">
		<div class="d-flex">
			<div class="mr-auto">
				<h4 style="text-align: left;" class="font-weight-bold">
					Accessories Indent Carton<span class="badge badge-primary">New</span>
				</h4>
			</div>

			<div class="p-0">
				<div class="input-group">
					<div class="input-group-append">
						<input type="text"
							class="form-control mdb-autocomplete input-sm ml-1"
							placeholder="Search" aria-label="Search"><span
							style="height: 30px;" class="input-group-text" id="search"><i
							class="fa fa-cog" aria-hidden="true"></i></span>
					</div>
				</div>
			</div>
			<div class="p-0">
				<div class="col-sm-3 col-md-3 col-lg-3">
					<button style="height: 35px;" id="find" type="button"
						class="btn btn-primary btn-sm">Find</button>
				</div>
			</div>

		</div>
		<div class="row mt-1">
			<label for="purchaseOrder" style="width: 120px;"
				class="form-label ml-1">Purchase Order</label>
			<div class="col-sm-9 col-md-9 col-lg-3">
				<select name="purchaseOrder" id="purchaseOrder"
					class="selectpicker form-control" data-live-search="true"
					data-style="btn-light btn-sm border-secondary form-control-sm"
					onchange="poWiseStyles()">
					<option value="0">Select Purchase Order</option>

					<c:forEach items="${purchaseorders}" var="acc" varStatus="counter">
						<option value="${acc.id}">${acc.name}</option>
					</c:forEach>
				</select>
			</div>

			<label style="width: 100px;" class="form-label ml-1" for="styleNo">Style
				No</label>
			<div class="col-sm-9 col-md-9 col-lg-3">
				<select id="styleNo" class="selectpicker form-control"
					data-live-search="true"
					data-style="btn-light btn-sm border-secondary form-control-sm"
					onchange="styleWiseItems()">
				</select>
			</div>

			<label style="width: 30px;" class="form-label ml-1" for="itemName">Item</label>
			<div class="col-sm-9 col-md-9 col-lg-3">
				<select id="itemName" class="selectpicker form-control"
					data-live-search="true"
					data-style="btn-light btn-sm border-secondary form-control-sm"
					onchange="styleItemsWiseColor()">
				</select>
			</div>
		</div>

		<div class="row mt-1">
			<label style="width: 120px;" class="form-label ml-1"
				for="accessoriesItem">Accessories Item</label>
			<div class="col-sm-9 col-md-9 col-lg-3">
				<select id="accessoriesItem" class="selectpicker form-control"
					data-live-search="true"
					data-style="btn-light btn-sm border-secondary form-control-sm">
					<option value="0">Select Item</option>
					<c:forEach items="${accessories}" var="acc" varStatus="counter">
						<option value="${acc.id}">${acc.name}</option>
					</c:forEach>
				</select>
			</div>

			<label style="width: 100px;" class="form-label ml-1"
				for="shippingmark">Shipping Mark</label>
			<div class="col-sm-9 col-md-9 col-lg-3">
				<select id="shippingmark" class="selectpicker form-control"
					data-live-search="true"
					data-style="btn-light btn-sm border-secondary form-control-sm">
				</select>
			</div>

			<label style="width: 30px;" class="form-label ml-1" for="colorName">Color</label>
			<div class="col-sm-9 col-md-9 col-lg-3">
				<select id="colorName" class="selectpicker form-control"
					data-live-search="true"
					data-style="btn-light btn-sm border-secondary form-control-sm"
					onchange="sizeReqCheck()">
					<option value="0">Select Color</option>

					<c:forEach items="${colors}" var="acc" varStatus="counter">
						<option value="${acc.id}">${acc.name}</option>
					</c:forEach>
				</select>
			</div>

		</div>

		<div class="row mt-1">

			<div style="width: 120px;" class="form-check ml-1">
				<div class="form-check-inline">
					<label class="form-check-label"> <input id="sizeReqCheck"
						type="checkbox" class="form-check-input" onclick="sizeReqCheck()">Size
						Req.
					</label>
				</div>
			</div>
			<div class="col-sm-9 col-md-9 col-lg-3">
				<select style="margin-left: 1px;" id="size"
					class="selectpicker form-control" data-live-search="true"
					data-style="btn-light btn-sm border-secondary form-control-sm"
					onchange="sizeWiseOrderQty()">

				</select>
			</div>

			<label style="width: 100px;" class="form-label ml-1">Order
				QTY</label>
			<div class="col-sm-9 col-md-9 col-lg-3">
				<input readonly id="orderQty" type="number" class="form-control-sm">
			</div>
		</div>

		<div class="row mt-2">
			<label style="width: 80px; margin-left: 43px;" class="form-label">Length</label>
			<div class="col-sm-2">
				<input id="length1" onkeyup="setTotalQtyForCurton()" type="text"
					class="form-control-sm">
			</div>

			<label style="width: 40px;" class="form-label">Width</label>
			<div class="col-sm-2">
				<input id="width1" onkeyup="setTotalQtyForCurton()" type="text"
					class="form-control-sm">
			</div>

			<label style="width: 40px; margin-left: 25px;" class="form-label">Height</label>
			<div class="col-sm-2">
				<input id="height1" onkeyup="setTotalQtyForCurton()" type="text"
					class="form-control-sm">
			</div>

			<label style="width: 33px;" class="form-label">Add</label>
			<div class="col-sm-2">
				<input id="add1" onkeyup="setTotalQtyForCurton()" type="text"
					class="form-control-sm">
			</div>
		</div>

		<div class="row mt-1">
			<label style="width: 80px; margin-left: 43px;" class="form-label">Length</label>
			<div class="col-sm-2">
				<input id="length2" onkeyup="setTotalQtyForCurton()" type="text"
					class="form-control-sm">
			</div>

			<label style="width: 40px;" class="form-label">Width</label>
			<div class="col-sm-2">
				<input id="width2" onkeyup="setTotalQtyForCurton()" type="text"
					class="form-control-sm">
			</div>

			<label style="width: 40px; margin-left: 25px;" class="form-label">Height</label>
			<div class="col-sm-2">
				<input id="height2" onkeyup="setTotalQtyForCurton()" type="text"
					class="form-control-sm">
			</div>

			<label style="width: 33px;" class="form-label">Add</label>
			<div class="col-sm-2">
				<input id="add2" onkeyup="setTotalQtyForCurton()" type="text"
					class="form-control-sm">
			</div>
		</div>

		<div class="row mt-1">
			<label style="width: 80px; margin-left: 43px;" class="form-label">Divide
				By</label>
			<div class="col-sm-2">
				<input readonly value="10000" id="devideBy" type="text"
					class="form-control-sm">
			</div>
			<label style="width: 40px;" class="form-label">Ply</label>
			<div class="col-sm-2">
				<input id="ply" type="text" class="form-control-sm">
			</div>
			<label style="width: 40px; margin-left: 25px;" class="form-label">QTY</label>
			<div class="col-sm-2">
				<input readonly id="qty" type="text" class="form-control-sm">
			</div>
		</div>

		<div class="row mt-1">

			<label style="width: 80px; margin-left: 43px;" class="form-label">Carton
				Size</label>
			<div class="col-sm-2">
				<input id="cartonSize" type="text" class="form-control-sm">
			</div>

			<label style="width: 40px;" class="form-label" for="unit">Unit</label>
			<div class="col-sm-2">
				<select id="unit" class="selectpicker form-control"
					data-live-search="true"
					data-style="btn-light border-secondary form-control-sm">

					<c:forEach items="${unit}" var="acc" varStatus="counter">
						<option value="${acc.id}">${acc.name}</option>
					</c:forEach>
				</select>
			</div>
		</div>

		<div class="row mt-1">
			<div class="col-sm-12">
				<button style="background: green;" id="btnSave"
					class="btn btn-primary btn-sm" onclick="saveAccessoriesCurton()">Save</button>
				<button id="btnEdit" class="btn btn-secondary btn-sm"
					onclick="editAccessoriesCurton()">Edit</button>
				<button id="refresh" class="btn btn-danger btn-sm">Refresh</button>
				<button id="pereview" class="btn btn-primary btn-sm">Preview</button>
				<button id="cartonindent" class="btn btn-primary btn-sm"
					onclick="btnAllCartonIndent()">All Carton Indent</button>
			</div>
		</div>





		<div class="row mt-3">
			<div style="overflow: auto; max-height: 300px;" class="col-sm-12">
				<table class="table table-hover table-bordered table-sm mb-0 small-font">
					<thead>
						<tr>
							<th style="width: 15px;">Sl#</th>
							<th>ID</th>
							<th>Style no</th>
							<th>Item Name</th>
							<th>Color Name</th>
							<th>Shipping Marks</th>
							<th>Accessories Name</th>
							<th>Size</th>
							<th>Total Qty</th>
							<th>Edit</th>
						</tr>
					</thead>
					<tbody id="dataList">

						<%-- 									<c:forEach items="${listAccPending}" var="listItem" varStatus="counter">
										<tr>
											<td>${counter.count}</td>
											<td>${listItem.po}</td>
											<td id='name${listItem.autoid}'>${listItem.style}</td>
											<td id='telephone${listItem.autoid}'>${listItem.itemname}</td>
											<td id='telephone${listItem.autoid}'>${listItem.itemcolor}</td>
											<td id='telephone${listItem.autoid}'>${listItem.shippingmark}</td>
											<td id='telephone${listItem.autoid}'>${listItem.accessoriesName}</td>
											<td id='telephone${listItem.autoid}'>${listItem.sizeName}</td>
											<td id='telephone${listItem.autoid}'>${listItem.requiredUnitQty}</td>
											<td><i class="fa fa-edit"  onclick="accessoriesItemSet(${listItem.autoid})"> </i></td>
										</tr>
									</c:forEach> --%>
					</tbody>
				</table>
			</div>
		</div>

	</div>
</div>




<jsp:include page="../include/footer.jsp" />
<script
	src="${pageContext.request.contextPath}/assets/js/order/accessories-indent-carton.js"></script>
