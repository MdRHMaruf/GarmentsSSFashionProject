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
	<input type="hidden" id="fabricsIndentAutoId" value="0">
	<div class="card-box m-2">
		<header >
			<h5 class="text-center" style="display: inline;">Fabrics Indent</h5>
		</header>

		<hr class="my-1">

		<div class="row ">
			<div class="form-group col-md-3 mb-1 px-1">
				<label for="purchaseOrder" class="col-form-label-sm mb-0 pb-0">Purchase
					Order</label> <select id="purchaseOrder" onchange="poWiseStyleLoad()"
					class="selectpicker col-md-12 px-0" data-live-search="true"
					data-style="btn-light btn-sm border-light-gray">
					<option id="purchaseOrder" value="0">Select Purchase Order</option>
					<c:forEach items="${poList}" var="po">
						<option id="purchaseOrder" value="${po}">${po}</option>
					</c:forEach>
				</select>
			</div>
			<div class="form-group col-md-3 mb-1 px-1">
				<label for="styleNo" class="col-form-label-sm mb-0 pb-0">Style
					No</label> <select id="styleNo" onchange="styleWiseItemLoad()"
					class="selectpicker col-md-12 px-0" data-live-search="true"
					data-style="btn-light btn-sm border-light-gray">
					<option id="styleNo" value="0">Select Style</option>

				</select>
			</div>

			<div class="form-group col-md-4 mb-1 px-1">
				<label for="itemName" class="col-form-label-sm mb-0 pb-0">Item
					Name</label> <select id="itemName" class="selectpicker col-md-12 px-0"
					data-live-search="true"
					data-style="btn-light btn-sm border-light-gray"
					onchange="styleItemWiseColorLoad()">
					<option id="itemName" value="0">Select Item Name</option>

				</select>
			</div>



			<div class="form-group col-md-2 mb-1 pl-1">
				<label for="itemColor" class="col-form-label-sm mb-0 pb-0">Color</label>
				<select id="itemColor" class="selectpicker col-md-12 px-0"
					data-live-search="true"
					data-style="btn-light btn-sm border-light-gray"
					onchange="setOrderQtyByPOStyleItemColor()">
					<option id="itemColor" value="0">Select Item Color</option>

				</select>
			</div>

		</div>

		<div class="row">
			<div class="col-md-5 pl-1">
				<div class="form-group mb-0  row">
					<label for="fabricsItem" class="col-md-3 col-form-label-sm pr-0">Fabrics
						Item</label> <select id="fabricsItem" class="selectpicker col-md-9 px-0"
						data-live-search="true"
						data-style="btn-light btn-sm border-light-gray">
						<option id="fabricsItem" value="0">Select Fabrics Name</option>
						<c:forEach items="${fabricsList}" var="fabrics">
							<option id="fabricsItem" value="${fabrics.fabricsItemId}">${fabrics.fabricsItemName}</option>
						</c:forEach>
					</select>

				</div>
			</div>

			<div class="col-md-2 ml-2">
				<div class="form-group mb-0 row">
					<label for="consumption"
						class="col-md-6 mb-0 px-0 col-form-label-sm">Consumption</label>
					<div class="col-md-6 px-1">
						<input type="number" onkeyup="totalQuantityCalculate()"
							class="form-control-sm " id="consumption">
					</div>
				</div>
			</div>

			<div class="col-md-2 ml-2">
				<div class="form-group mb-0 row">
					<label for="quantity" class="col-md-4 mb-0 px-0 col-form-label-sm">Quantity</label>
					<div class="col-md-8 px-1">
						<input type="number" class="form-control-sm " id="quantity"
							readonly>
					</div>
				</div>
			</div>

			<div class="col-md-2 ml-2">
				<div class="form-group mb-0 row">
					<label for="dozen" class="col-md-3 mb-0 px-0 col-form-label-sm">Dozen</label>
					<div class="col-md-8 px-1">
						<input type="number" class="form-control-sm " id="dozen" readonly>
					</div>
				</div>
			</div>

		</div>

		<div class="row">
			<div class="col-sm-9 col-md-9 col-lg-2 pr-0 pl-1">
				<label for="inPercent" class="col-form-label-sm mb-0 pb-0">In%</label>
				<input id="inPercent" onkeyup="totalQuantityCalculate()"
					type="number" class="form-control-sm  pr-0 pl-1">
			</div>

			<div class="col-sm-9 col-md-9 col-lg-2 pr-0 pl-1">
				<label for="percentQuantity" class="col-form-label-sm mb-0 pb-0">%QTY</label>
				<input id="percentQuantity" type="number"
					class="form-control-sm pr-0 pl-1" readonly>
			</div>

			<div class="col-sm-9 col-md-9 col-lg-2 pr-0 pl-1">
				<label for="total" class="col-form-label-sm mb-0 pb-0">Total</label>
				<input id="total" type="number" class="form-control-sm pr-0 pl-1"
					readonly>
			</div>

			<div class="form-group col-md-2 mb-1  pr-0 pl-1">
				<label for="unit" class="col-form-label-sm mb-0 pb-0">Unit</label>
				<div class="row">
					<select id="unit" class="selectpicker col-md-12"
						data-live-search="true"
						data-style="btn-light btn-sm border-light-gray"
						onchange="unitChangeAction()">
						<option id="unit" value="0">Select Unit</option>
						<c:forEach items="${unitList}" var="unit">
							<option id="unit" value="${unit.unitId}">${unit.unitName}</option>
						</c:forEach>
					</select>
				</div>

			</div>

			<div class="col-sm-9 col-md-9 col-lg-2 pr-0 pl-1">
				<label for="width" class="col-form-label-sm mb-0 pb-0">Width</label>
				<input id="width" type="number" class="form-control-sm pr-0 pl-1"
					onkeyup="gsmCalculation()">
			</div>

		</div>

		<div class="row">

			<div id="yardDiv" class="col-sm-2 pr-0 pl-1">
				<label for="yard" class="col-form-label-sm mb-0 pb-0">Yard</label> <input
					id="yard" type="number" class="form-control-sm"
					onkeyup="gsmCalculation()" disabled>
			</div>

			<div id="gsmDiv" class="col-sm-2 pr-0 pl-1">
				<label for="gsm" class="col-form-label-sm mb-0 pb-0">GSM</label> <input
					id="gsm" type="number" class="form-control-sm"
					onkeyup="gsmCalculation()" disabled>
			</div>

			<div class="col-sm-2 pr-0 pl-1">
				<label for="grandQuantity" class="col-form-label-sm mb-0 pb-0">Grand
					QTY</label> <input id="grandQuantity" type="number" class="form-control-sm"
					readonly>
			</div>

			<div class="col-sm-2 pr-0 pl-1">
				<label for="fabricsColor" class="col-form-label-sm mb-0 pb-0">Color</label>
				<div class="row">
					<select id="fabricsColor" class="selectpicker col-md-12"
						data-live-search="true"
						data-style="btn-light btn-sm border-light-gray">
						<option id="fabricsColor" value="0">Select Color</option>
						<c:forEach items="${colorList}" var="color">
							<option id="fabricsColor" value="${color.colorId}">${color.colorName}</option>
						</c:forEach>
					</select>
				</div>
			</div>


			<div class="col-sm-2 pr-0 pl-1">
				<label for="brand" class="col-form-label-sm mb-0 pb-0">Brand</label>
				<div class="row">
					<select id="brand" class="selectpicker col-md-12"
						data-live-search="true"
						data-style="btn-light btn-sm border-light-gray">
						<option id="brand" value="0">Select Brand</option>
						<c:forEach items="${brandList}" var="brand">
							<option id="brand" value="${brand.brandId}">${brand.brandName}</option>
						</c:forEach>
					</select>
				</div>
			</div>

		</div>

		<div class="row mt-1 d-flex justify-content-between">
			<div class="col-sm-6 pl-1">
				<div>
					<button id="btnSave" class="btn btn-primary btn-sm"
						onclick="saveAction()">Save</button>
					<button id="btnEdit" class="btn btn-primary btn-sm"
						onclick="editAction()" disabled>Edit</button>
					<button id="btnRefresh" class="btn btn-secondary btn-sm"
						onclick="refreshAction()">Refresh</button>
					<button id="btnPereview" class="btn btn-success btn-sm">Preview</button>
				</div>
			</div>
			
			<div class="col-sm-5 pl-1">
				<div class="input-group input-group-sm">
					<input id="search" type="text" class="form-control"
						placeholder="Search Fabrics Indent" aria-label="Recipient's username"
						aria-describedby="basic-addon2">
					<div class="input-group-append">
						<span class="input-group-text"><i class="fa fa-search"
							style="cursor: pointer;"></i></span>
					</div>
				</div>

			</div>
		</div>
		<hr class="my-1">
		<div class="row mt-1">
			<div style="overflow: auto; max-height: 300px;"
				class="col-sm-12 px-1 table-responsive">
				<table class="table table-hover table-bordered table-sm mb-0 small-font">
					<thead class="no-wrap-text">
						<tr>

							<th>P.O.</th>
							<th>Style</th>
							<th>Item Name</th>
							<th>Color Name</th>
							<th>Fabrices Item</th>
							<th>Dozen</th>
							<th>Consumption</th>
							<th>%QTY</th>
							<th>Total QTY</th>
							<th>Unit</th>
							<th><i class="fa fa-info-circle"></i></th>
						</tr>
					</thead>
					<tbody id="dataList">
						<c:forEach items="${fabricsIndentList}" var="indent"
							varStatus="counter">
							<tr>
								<td>${indent.purchaseOrder}</td>
								<td>${indent.styleName}</td>
								<td>${indent.itemName}</td>
								<td>${indent.itemColorName}</td>
								<td>${indent.fabricsName}</td>
								<td>${indent.dozenQty}</td>
								<td>${indent.consumption}</td>
								<td>${indent.percentQty}</td>
								<td>${indent.totalQty}</td>
								<td>${indent.unit}</td>
								<td><i class="fa fa-info-circle"
									onclick="viewFabricsIndent(${indent.autoId})"
									style="cursor: pointer;"></i></td>
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
	src="${pageContext.request.contextPath}/assets/js/order/fabrics_indent.js"></script>
