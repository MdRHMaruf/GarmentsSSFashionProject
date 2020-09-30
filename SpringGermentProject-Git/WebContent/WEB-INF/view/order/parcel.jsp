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
	<input type="hidden" id="userId" value="<%=lg.get(0).getId()%>">

	<div class="card-box">
		<!-- <div class="row">
			<div class="col-md-12"> -->
			<header class="d-flex justify-content-between">
			<h5 class="text-center" style="display: inline;">Parcel</h5>
			<button type="button" class="btn btn-outline-dark btn-sm"
				data-toggle="modal" data-target="#exampleModal">
				<i class="fa fa-search"></i> Parcel List</button>
			</header>
		<hr class="my-1">
		<div class="row">
			<!-- <div class="col-sm-4">

				<label for="id" class="form-label mb-0">ID</label>
				<div class="row">
					<input class="form-control-sm" type="text" id="id">
				</div>

			</div> -->

			<div class="col-sm-4">
				<label for="styleNo" class="form-label mb-0">Style No</label>
				<div class="row">
				
					<select id="styleNo" onchange="styleWiseItemLoad()"
					class="selectpicker col-md-12 px-0" data-live-search="true"
					data-style="btn-light btn-sm border-light-gray">
					<option id="styleNo" value="0">Select Style</option>
						<c:forEach items="${StyleList}" var="po">
						<option id="styleNo" value="${po.styleId}">${po.styleNo}</option>
						</c:forEach>
					</select>
				
					
				</div>

			</div>

			<div class="col-sm-4">
				<label for="item" class="form-label-sm mb-0">Item</label>
				<div class="row">
					<select id="itemName" class="selectpicker col-md-12" onchange=""
						data-live-search="true"
						data-style="btn-light btn-sm border-light-gray">
						<option id="itemName" value="0">Select Item</option>
					</select>
				</div>

			</div>
		</div>

		<div class="row">

			<div class="col-sm-4">
				<label for="sample" class="form-label-sm mb-0">Sample</label>
				<div class="row">
					<select id="sampletype" onchange=""
					class="selectpicker col-md-12 px-0" data-live-search="true"
					data-style="btn-light btn-sm border-light-gray">
					<option id="sampletype" value="0">Select Sample</option>
						<c:forEach items="${sampletype}" var="po">
						<option id="sampletype" value="${po.id}">${po.name}</option>
						</c:forEach>
					</select>
				</div>


			</div>

			<div class="col-sm-4 pr-4 pl-4">
				<label for="dispatchedDate" class="form-label-sm mb-0">Dispatched
					Date</label>
				<div class="row">
					<input class="form-control-sm col-md-12" type="date"
						id="dispatchedDate">
				</div>


			</div>

			<div class="col-md-4">
				<label for="courierName" class="form-label-sm mb-0">Courier
					Name</label>
				<div class="row">
					<select id="courierName" class="selectpicker col-md-12" onchange=""
						data-live-search="true"
						data-style="btn-light btn-sm border-light-gray">
						<option id="courierName" value="0">Select Courier</option>
						<c:forEach items="${courierList}" var="po">
						<option id="courierName" value="${po.courierid}">${po.couriername}</option>
						</c:forEach>
					</select>
				</div>

			</div>

		</div>

		<div class="row mt-1">

			<div class="col-sm-4">
				<label for="trackingNo" class="form-label-sm mb-0 pb-0">Tracking
					No</label>
				<div class="row">

					<input type="text" class="form-control-sm" id="trackingNo">
				</div>
			</div>

			<div class="col-sm-4">
				<label for="grossWeight" class="form-label-sm mb-0 pb-0">Gross
					Weight</label>

				<div class="row">
					<input type="text" value="0" class="form-control-sm ml-2" id="grossWeight" onkeyup="amountCalculate()">
				</div>

			</div>


			<div class="col-sm-4">
				<label for="unit" class="form-label mb-0">Unit</label>
				<div class="row">
					<select id="unit" class="selectpicker col-md-12" onchange=""
						data-live-search="true"
						data-style="btn-light btn-sm border-light-gray">
						<option id="unit" value="0">Select Unit</option>
						<c:forEach items="${unitList}" var="po">
						<option id="unit" value="${po.unitId}">${po.unitName}</option>
						</c:forEach>
					</select>
				</div>

			</div>

		</div>

		<div class="row">





			<div class="col-sm-4 mb-0 pb-0">
				<label for="totalQty" class="form-label-sm pr-0 pb-0">Total
					QTY</label>
				<div class="row">
					<input type="text" class="form-control-sm" id="totalQty">
				</div>
			</div>

			<div class="col-sm-4 mb-0 pb-0">
				<label for="parcel" class="form-label-sm pr-0 pb-0">Parcel</label>
				<div class="row">
					<input type="text" class="form-control-sm ml-2" id="parcel">
				</div>
			</div>

			<div class="col-sm-4 mb-0 pb-0">
				<label for="grossWeight" class="form-label-sm pr-0 pb-0">Rate</label>
				<div class="row">
					<input type="text" value="0" class="form-control-sm  ml-2" id="rate" onkeyup="amountCalculate()">
				</div>
			</div>

		</div>

		<div class="row">

			<div class="col-sm-4 mb-0 pb-0">
				<label for="amount" class="form-label-sm pr-0 pb-0">Amount</label>
				<div class="row">
					<input type="text" class="form-control-sm" id="amount">
				</div>

			</div>

			<div class="col-sm-2 mb-0 pb-0">
				<label for="deiveryDate" class="form-label-sm pr-0 pb-0">Delivery
					Date</label>
				<div class="row">
					<input type="date" class="form-control-sm col-sm-12 ml-2"
						id="deiveryDate">
				</div>
			</div>
			
			<div class="col-sm-2 mb-0 pb-0 mr-2">
				<label for="delieryTime" class="form-label-sm pr-0 pb-0">Delivery
					Time</label>
				<div class="row">
					<input type="time" class="form-control-sm col-sm-12 ml-3"
						id="delieryTime">
				</div>
			</div>
			
			<div class="col-sm-3 mb-0 pb-0">
				<label for="deliveryTo" class="form-label-sm pr-0 pb-0">Delivery
					To</label>
				<div class="row">
					<input type="text" class="form-control-sm ml-3" id="deliveryTo">
				</div>

			</div>
			
		</div>

		<div class="row mt-1">
			<div class="col-md-12 d-flex justify-content-end">
				<button type="button" id="save" class="btn btn-primary btn-sm"
					onclick="insertParcel()">
					<i class="fa fa-plus-circle"></i> Save
				</button>
				<button  type="button"
					class="btn btn-primary btn-sm ml-1" id="edit" onclick="editParcel()" disabled>
					<i class="fa fa-pencil-square"></i> Edit
				</button>
				<button id="btnRefresh" type="button"
					class="btn btn-primary btn-sm ml-1" onclick="">
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
						placeholder="Search ParcelF"
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
							<th>Style No</th>
							<th>Item Name</th>
							<th>Tracking No</th>
							<th><span><i class="fa fa-search"></i></span></th>
							<th><span><i class="fa fa-print"></i></span></th>
						</tr>
					</thead>
					<tbody id="poList">
						<c:forEach items="${parcelList}" var="po" varStatus="counter">
							<tr>
								<td>${counter.count}</td>
								<td id='buyerName${po.styleNo}'>${po.styleNo}</td>
								<td >${po.itemName}</td>
								<td >${po.trackingNo}</td>
								 <td><i class="fa fa-search"
									onclick="getParcelDetails(${po.id})">
								</i>
								</td> 
								<td><i class="fa fa-print"
									onclick="parcelReport(${po.id})">
								</i>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>

		</div>
	</div>
</div>
<jsp:include page="../include/footer.jsp" />

<script src="${pageContext.request.contextPath}/assets/js/order/parcel.js"></script>
