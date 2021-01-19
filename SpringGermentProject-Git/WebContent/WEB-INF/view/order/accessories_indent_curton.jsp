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

<input type="hidden" id="userId" value="<%=lg.get(0).getId()%>">
<input type="hidden" id="indentId" value="0">
<input type="hidden" id="indentAutoId" value="0">

<div class="page-wrapper">

	<div class="card-box m-2">
		<div class="d-flex">
			<div class="mr-auto">
				<h4 style="text-align: left;" class="font-weight-bold">
					Carton Indent <span id='cartonIndentId' class="badge badge-primary">New</span>
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
		<%-- <div class="row">
					<label for="buyerName" class="col-form-label-sm mb-0 pr-0 col-md-2">Buyer
						Name:</label> <select id="buyerName" class="selectpicker col-md-9"
						onchange="buyerWisePoLoad()" data-live-search="true"
						data-style="btn-light btn-sm border-light-gray">
						<option value="0">Select Buyer</option>
						<c:forEach items="${buyerList}" var="buyer">
							<option value="${buyer.buyerid}">${buyer.buyername}</option>
						</c:forEach>
					</select>

				</div> --%>
		<div class="row mt-1">
			<div class='col-md-4 px-1'>
				<div class="input-group input-group-sm mb-1">
					<div class="input-group-prepend">
						<span class="input-group-text" id="inputGroup-sizing-sm"><label
							class='my-0' for="buyerName">Buyer Name</label></span>
					</div>
					<select id="buyerName" class="form-control selectpicker"
						aria-label="Sizing example input"
						aria-describedby="inputGroup-sizing-sm" data-live-search="true"
						data-style="btn-light btn-sm border-secondary form-control-sm"
						onchange="buyerWisePoLoad()">
						<option value="0">Select Buyer</option>
						<c:forEach items="${buyerList}" var="buyer">
							<option value="${buyer.buyerid}">${buyer.buyername}</option>
						</c:forEach>
					</select>

				</div>
			</div>

			<div class='col-md-4 px-1'>
				<div class="input-group input-group-sm mb-1">
					<div class="input-group-prepend">
						<span class="input-group-text" id="inputGroup-sizing-sm"><label
							class='my-0' for="purchaseOrder">Purchase Order</label></span>
					</div>
					<select id="purchaseOrder" class="form-control selectpicker"
						aria-label="Sizing example input"
						aria-describedby="inputGroup-sizing-sm" data-live-search="true"
						data-style="btn-light btn-sm border-secondary form-control-sm"
						onchange="poWiseStyles()">
						<option value="0">Select Purchase Order</option>
						<c:forEach items="${purchaseorders}" var="acc" varStatus="counter">
							<option value="${acc.id}">${acc.name}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<%-- <label for="purchaseOrder" style="width: 120px;"
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
			</div> --%>

			<div class='col-md-4 px-1'>
				<div class="input-group input-group-sm mb-1">
					<div class="input-group-prepend">
						<span class="input-group-text" id="inputGroup-sizing-sm"><label
							class='my-0' for="styleNo">Style No</label></span>
					</div>
					<select id="styleNo" class="form-control selectpicker"
						aria-label="Sizing example input"
						aria-describedby="inputGroup-sizing-sm" data-live-search="true"
						data-style="btn-light btn-sm border-secondary form-control-sm"
						onchange="styleWiseItems()">

					</select>
				</div>
			</div>



			<div class='col-md-4 px-1'>
				<div class="input-group input-group-sm mb-1">
					<div class="input-group-prepend">
						<span class="input-group-text" id="inputGroup-sizing-sm"><label
							class='my-0' for="itemName">Item</label></span>
					</div>
					<select id="itemName" class="form-control selectpicker"
						aria-label="Sizing example input"
						aria-describedby="inputGroup-sizing-sm" data-live-search="true"
						data-style="btn-light btn-sm border-secondary form-control-sm"
						onchange='styleItemsWiseColor()'>

					</select>
				</div>
			</div>

			<div class='col-md-4 px-1'>
				<div class="input-group input-group-sm mb-1">
					<div class="input-group-prepend">
						<span class="input-group-text" id="inputGroup-sizing-sm"><label
							class='my-0' for="colorName">Color</label></span>
					</div>
					<select id="colorName" class="form-control selectpicker"
						aria-label="Sizing example input"
						aria-describedby="inputGroup-sizing-sm" data-live-search="true"
						data-style="btn-light btn-sm border-secondary form-control-sm"
						onchange="sizeReqCheck()">
						<option value="0">Select Color</option>

						<c:forEach items="${colors}" var="acc" varStatus="counter">
							<option value="${acc.id}">${acc.name}</option>
						</c:forEach>
					</select>

				</div>
			</div>

			<div class='col-md-4 px-1'>
				<div class="input-group input-group-sm mb-1">
					<div class="input-group-prepend">
						<span class="input-group-text" id="inputGroup-sizing-sm"><label
							class='my-0' for="shippingMark">Shipp. Mark</label></span>
					</div>
					<select id="shippingMark" class="form-control selectpicker"
						aria-label="Sizing example input"
						aria-describedby="inputGroup-sizing-sm" data-live-search="true"
						data-style="btn-light btn-sm border-secondary form-control-sm">

					</select>
				</div>
			</div>

			<div class='col-md-4 px-1'>
				<div class="input-group input-group-sm mb-1">
					<div class="input-group-prepend">
						<span class="input-group-text" id="inputGroup-sizing-sm"><label
							class='my-0 form-check-label' for="sizeReqCheck"><input
								id="sizeReqCheck" type="checkbox" onclick="sizeReqCheck()">
								Size Rq.</label></span>
					</div>
					<select id="size" class="form-control selectpicker"
						aria-label="Sizing example input"
						aria-describedby="inputGroup-sizing-sm" data-live-search="true"
						data-style="btn-light btn-sm border-secondary form-control-sm"
						onchange="sizeWiseOrderQty()">

					</select>
				</div>
			</div>

			<div class='col-md-4 px-1'>
				<div class="input-group input-group-sm mb-1">
					<div class="input-group-prepend">
						<span class="input-group-text" id="inputGroup-sizing-sm"><label
							class='my-0' for="orderQty">Order Qty.</label></span>
					</div>
					<input readonly id="orderQty" type="number" title="Order Qty"
						class="form-control"> <input type="number"
						placeholder="Pcs.Per Carton" title="Pcs.Per Carton"
						id="pcsPerCarton" class="form-control" onkeyup="setQty()">
				</div>
			</div>


			<div class='col-md-4 px-1'>
				<div class="input-group input-group-sm mb-1">
					<div class="input-group-prepend">
						<span class="input-group-text" id="inputGroup-sizing-sm"><label
							class='my-0' for="accessoriesItem">Acc. Item</label></span>
					</div>
					<select id="accessoriesItem" class="form-control selectpicker"
						aria-label="Sizing example input"
						aria-describedby="inputGroup-sizing-sm" data-live-search="true"
						data-style="btn-light btn-sm border-secondary form-control-sm"
						onchange="sizeWiseOrderQty()">
						<option value="52" selected>CARTON</option>
						<option value="53">DIVIDER / CENTRE PAD</option>
					</select>
				</div>
			</div>
			<%-- <label style="width: 100px;" class="form-label ml-1">Order
				QTY</label>
			<div class="col-sm-9 col-md-9 col-lg-3">

				<div class="input-group input-group-sm">
					<div class="input-group-prepend">
						<span class="input-group-text">First and last name</span>
					</div>
					<input readonly id="orderQty" type="number" title="Order Qty"
						class="form-control"> <input type="number"
						placeholder="Pcs.Per Carton" title="Pcs.Per Carton"
						id="pcsPerCarton" class="form-control" onkeyup="setQty()">
				</div>
			</div> --%>

			<%-- <label style="width: 120px;" class="form-label ml-1"
				for="accessoriesItem">Acc. Item</label>
			<div class="col-sm-9 col-md-9 col-lg-3">
				<select id="accessoriesItem" class="form-control-sm w-100">
					<option value="52" selected>CARTON</option>
					<c:forEach items="${accessories}" var="acc" varStatus="counter">
						<option value="${acc.id}">${acc.name}</option>
					</c:forEach>
				</select>
			</div> --%>
			<%-- <div style="width: 120px;" class="form-check ml-1">
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
			</div> --%>
			<%-- <label style="width: 30px;" class="form-label ml-1" for="colorName">Color</label>
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
			</div> --%>

			<%-- <label style="width: 100px;" class="form-label ml-1"
				for="shippingmark">Shipp. Mark</label>
			<div class="col-sm-9 col-md-9 col-lg-3">
				<select id="shippingmark" class="selectpicker form-control"
					data-live-search="true"
					data-style="btn-light btn-sm border-secondary form-control-sm">
				</select>
			</div> --%>
			<%-- <label style="width: 100px;" class="form-label ml-1" for="styleNo">Style
				No</label>
			<div class="col-sm-9 col-md-9 col-lg-3">
				<select id="styleNo" class="selectpicker form-control"
					data-live-search="true"
					data-style="btn-light btn-sm border-secondary form-control-sm"
					onchange="styleWiseItems()">
				</select>
			</div> --%>

			<%-- <label style="width: 30px;" class="form-label ml-1" for="itemName">Item</label>
			<div class="col-sm-9 col-md-9 col-lg-3">
				<select id="itemName" class="selectpicker form-control"
					data-live-search="true"
					data-style="btn-light btn-sm border-secondary form-control-sm"
					onchange="styleItemsWiseColor()">
				</select>
			</div> --%>


		</div>


		<div class="row mt-1">

			<%-- <div style="width: 120px;" class="form-check ml-1">
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
			</div> --%>


		</div>

		<div class="row mt-1">
			<div class='col-md-2 px-1'>
				<div class="input-group input-group-sm mb-1">
					<div class="input-group-prepend">
						<span class="input-group-text" id="inputGroup-sizing-sm"><label
							class='my-0' for="ply">Ply</label></span>
					</div>
					<input id="ply" type="text" class="form-control"
						aria-label="Sizing example input"
						aria-describedby="inputGroup-sizing-sm">
				</div>
			</div>
			<div class='col-md-8 px-1'>
				<div class="input-group input-group-sm mb-1">
					<div class="input-group-prepend">
						<span class="input-group-text" id="inputGroup-sizing-sm"><label
							class='my-0' for="type">Type</label></span>
					</div>
					<input id="type" type="text" class="form-control"
						aria-label="Sizing example input"
						aria-describedby="inputGroup-sizing-sm">
				</div>
			</div>
		</div>
		<div class="row mt-1">
			<div class='col-md-2 px-1'>
				<div class="input-group input-group-sm mb-1">
					<div class="input-group-prepend">
						<span class="input-group-text" id="inputGroup-sizing-sm"><label
							class='my-0' for="length1">Length</label></span>
					</div>
					<input id="length1" type="number" class="form-control"
						aria-label="Sizing example input"
						aria-describedby="inputGroup-sizing-sm">
				</div>
			</div>
			<div class='col-md-2 px-1'>
				<div class="input-group input-group-sm mb-1">
					<div class="input-group-prepend">
						<span class="input-group-text" id="inputGroup-sizing-sm"><label
							class='my-0' for="width1">Width</label></span>
					</div>
					<input id="width1" type="number" class="form-control"
						aria-label="Sizing example input"
						aria-describedby="inputGroup-sizing-sm"
						onkeyup="setTotalQtyForCarton()">
				</div>
			</div>

			<div class='col-md-2 px-1'>
				<div class="input-group input-group-sm mb-1">
					<div class="input-group-prepend">
						<span class="input-group-text" id="inputGroup-sizing-sm"><label
							class='my-0' for="height1">Height</label></span>
					</div>
					<input id="height1" type="number" class="form-control"
						aria-label="Sizing example input"
						aria-describedby="inputGroup-sizing-sm"
						onkeyup="setTotalQtyForCarton()">
				</div>
			</div>

			<div class='col-md-2 px-1'>
				<div class="input-group input-group-sm mb-1">
					<div class="input-group-prepend">
						<span class="input-group-text" id="inputGroup-sizing-sm"><label
							class='my-0' for="add1">Add(1)</label></span>
					</div>
					<input id="add1" type="number" class="form-control"
						aria-label="Sizing example input"
						aria-describedby="inputGroup-sizing-sm"
						onkeyup="setTotalQtyForCarton()">
				</div>
			</div>

			<div class='col-md-2 px-1'>
				<div class="input-group input-group-sm mb-1">
					<div class="input-group-prepend">
						<span class="input-group-text" id="inputGroup-sizing-sm"><label
							class='my-0' for="add2">Add(2)</label></span>
					</div>
					<input id="add2" type="number" class="form-control"
						aria-label="Sizing example input"
						aria-describedby="inputGroup-sizing-sm"
						onkeyup="setTotalQtyForCarton()">
				</div>
			</div>
		</div>

		<!-- <div class="row mt-1">
			<label style="width: 80px; margin-left: 43px;" class="form-label">Length</label>
			<div class="col-sm-2">
				<input id="length2" onkeyup="setTotalQtyForCarton()" type="text"
					class="form-control-sm">
			</div>

			<label style="width: 40px;" class="form-label">Width</label>
			<div class="col-sm-2">
				<input id="width2" onkeyup="setTotalQtyForCarton()" type="text"
					class="form-control-sm">
			</div>

			<label style="width: 40px; margin-left: 25px;" class="form-label">Height</label>
			<div class="col-sm-2">
				<input id="height2" onkeyup="setTotalQtyForCarton()" type="text"
					class="form-control-sm">
			</div>

			<label style="width: 33px;" class="form-label">Add</label>
			<div class="col-sm-2">
				<input id="add2" onkeyup="setTotalQtyForCarton()" type="text"
					class="form-control-sm">
			</div>
		</div> -->

		<div class="row mt-1">
			<div class='col-md-2 px-1'>
				<div class="input-group input-group-sm mb-1">
					<div class="input-group-prepend">
						<span class="input-group-text" id="inputGroup-sizing-sm"><label
							class='my-0' for="unit">Unit</label></span>
					</div>
					<select id="unit" class="form-control-sm"
						aria-label="Sizing example input"
						aria-describedby="inputGroup-sizing-sm"
						onchange="setDivideByValue()">
						<option value="11" data-divide-value='1550'>Inch</option>
						<option value="14" data-divide-value='10000' selected>CM</option>
						<%-- <c:forEach items="${unit}" var="acc" varStatus="counter">
							<option value="${acc.id}">${acc.name}</option>
						</c:forEach> --%>
					</select>

				</div>
			</div>
			<div class='col-md-2 px-1'>
				<div class="input-group input-group-sm mb-1">
					<div class="input-group-prepend">
						<span class="input-group-text" id="inputGroup-sizing-sm"><label
							class='my-0' for="divideBy">Divide By</label></span>
					</div>
					<input id="divideBy" type="text" class="form-control"
						aria-label="Sizing example input"
						aria-describedby="inputGroup-sizing-sm"
						onkeyup="setTotalQtyForCarton()">
				</div>
			</div>

			<div class='col-md-2 px-1'>
				<div class="input-group input-group-sm mb-1">
					<div class="input-group-prepend">
						<span class="input-group-text" id="inputGroup-sizing-sm"><label
							class='my-0' for="cbm">CBM</label></span>
					</div>
					<input id="cbm" type="text" class="form-control"
						aria-label="Sizing example input"
						aria-describedby="inputGroup-sizing-sm" readonly>
				</div>
			</div>
			<div class='col-md-2 px-1'>
				<div class="input-group input-group-sm mb-1">
					<div class="input-group-prepend">
						<span class="input-group-text" id="inputGroup-sizing-sm"><label
							class='my-0' for="qty">Qty</label></span>
					</div>
					<input id="qty" type="text" class="form-control"
						aria-label="Sizing example input"
						aria-describedby="inputGroup-sizing-sm"
						>
				</div>
			</div>

			<div class='col-md-2 px-1'>
				<div class="input-group input-group-sm mb-1">
					<div class="input-group-prepend">
						<span class="input-group-text" id="inputGroup-sizing-sm"><label
							class='my-0' for="cartonSize">Crt. Size</label></span>
					</div>
					<input id="cartonSize" type="text" class="form-control"
						aria-label="Sizing example input"
						aria-describedby="inputGroup-sizing-sm"
						onkeyup="setTotalQtyForCarton()">
				</div>
			</div>

		</div>


		<div class="row mt-1">
			<div class="col-sm-12">
				<button id="btnAdd" class="btn btn-primary btn-sm"
					onclick="addCartonIndent()">Add</button>
				<button id="btnEdit" class="btn btn-success btn-sm"
					onclick="editAccessoriesCarton()" style="display: none;">Edit</button>
				<button id="btnFieldRefresh" class="btn btn-secondary btn-sm" onclick='fieldRefresh()'>Refresh</button>
				
				<button id="cartonindent" class="btn btn-primary btn-sm"
					onclick="btnAllCartonIndent()">All Carton Indent</button>
			</div>
		</div>

		<div class="mt-1">
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
		</div>


		<div class="row mt-3">
			<div style="overflow: auto; max-height: 300px;" class="col-sm-12">
				<table
					class="table table-hover table-bordered table-sm mb-0 small-font">
					<thead>
						<tr>
							
							<th>Purchase Order</th>
							<th>Style No</th>
							<th>Color Name</th>
							<th>Accessories Name</th>
							<th>PLY</th>
							<th>Length</th>
							<th>Width</th>
							<th>Height</th>
							<th>Unit</th>
							<th>Size</th>
							<th>CBM</th>
							<th>Total Qty</th>
							<th><i class="fa fa-edit"></i></th>
							<th><i class="fa fa-trash"></i></th>
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
		<div class="row mt-1">
			<div class="col-sm-12">
				<div class="d-flex justify-content-end">
					<div class="row">
						<div class="pr-1">
							<button class="btn btn-primary btn-sm" onclick="confirmAction()">
								<i class="fas fa-save"></i> Confirm
							</button>
						</div>
						<div class="pr-1">
							<button class="btn btn-secondary btn-sm" onclick="refreshAction()">
								<i class="fa fa-refresh"></i> Refresh
							</button>
						</div>
						<div class="pr-1">
							<button id="btnPreview" class="btn btn-info btn-sm" onclick="fabricIndentReport()">
								<i class="fas fa-print"></i> Preview
							</button>
						</div>

					</div>
				</div>
			</div>
		</div>
	</div>
</div>




<jsp:include page="../include/footer.jsp" />
<script
	src="${pageContext.request.contextPath}/assets/js/order/accessories-indent-carton.js"></script>
