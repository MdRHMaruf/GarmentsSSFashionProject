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
	<input type="hidden" id="hid_userId" value="<%=userId%>">
	<input type="hidden" id="itemAutoId" value="0"> <input
		type="hidden" id="hid_buyerId" value="0"> <input type="hidden"
		id="hid_buyerOrderId" value="0"> <input type="hidden"
		id="hid_styleId" value="0"> <input type="hidden"
		id="hid_itemId" value="0"> <input type="hidden"
		id="hid_purchaseOrder" value="0">

	<div class="card-box">
		<header class="d-flex justify-content-between">
			<h5 class="text-center" style="display: inline;">Cutting
				Information</h5>
			<div>
				<button type="button" class="btn btn-outline-dark btn-sm"
					data-toggle="modal" data-target="#exampleModal">
					<i class="fa fa-search"></i>
				</button>

				<button type="button" class="btn btn-outline-dark btn-sm"
					data-toggle="modal" data-target="#exampleCuttingModal">
					<i class="fa fa-search"></i> All Cutting List
				</button>
			</div>
		</header>
		<hr class="my-1">
		<div class="row">
			<div class="col-md-10 mb-1 pr-1">
				<div class="row">
					<div class="form-group col-md-4 mb-1" style="padding-right: 1px;">
						<label for="buyerName" class="col-form-label-sm mb-0 pb-0">Factory
							Name:</label>
						<div class="row">
							<select id="factoryId" class="selectpicker col-md-12"
								onchange="FactoryWiseDepartmentLoad()" data-live-search="true"
								data-style="btn-light btn-sm border-light-gray">
								<option id="factoryId" value="0">Select Factory</option>
								<c:forEach items="${factoryList}" var="list">
									<option id="factoryId" value="${list.factoryId}">${list.factoryName}</option>
								</c:forEach>
							</select>
						</div>


					</div>
					<div class="form-group col-md-2 mb-1"
						style="padding-left: 1px; padding-right: 1px;">
						<label for="purchaseOrder" class="col-form-label-sm mb-0 pb-0">Department
							Name:</label>
						<div class="row">
							<select id="departmentId" class="selectpicker col-md-12"
								onchange="FactoryDepartmentWiseLine()" data-live-search="true"
								data-style="btn-light btn-sm border-light-gray">
								<option id="departmentId" value="0">Select Department</option>
							</select>
						</div>

					</div>
					<div class="form-group col-md-2 mb-1"
						style="padding-left: 1px; padding-right: 1px;">
						<label for="styleNo" class="col-form-label-sm mb-0 pb-0">Line
							No:</label>
						<div class="row">
							<select id="lineId" class="selectpicker col-md-12"
								data-live-search="true"
								data-style="btn-light btn-sm border-light-gray">
								<option id="lineId" value="0">Select Line</option>
							</select>
						</div>

					</div>
					<div class="form-group col-md-4 mb-1" style="padding-left: 1px;">
						<label for="itemType" class="col-form-label-sm mb-0 pb-0">Incharge
							Name:</label>
						<div class="row">
							<select id="inchargeId" class="selectpicker col-md-12"
								data-live-search="true"
								data-style="btn-light btn-sm border-light-gray">
								<option id="inchargeId" value="0">Select Incharge</option>
								<c:forEach items="${inchargeList}" var="list">
									<option id="inchargeId" value="${list.id}">${list.name}</option>
								</c:forEach>
							</select>
						</div>

					</div>
				</div>

				<div class="row">
					<div class="form-group col-md-2 mb-1"
						style="padding-left: 30px; padding-right: 16px;">
						<label for="sample" class="col-form-label-sm mb-0 pb-0">Cutting
							No</label>
						<div class="row">
							<input type="text" id="cuttingNo"
								class="col-md-12 form-control-sm" />
						</div>
					</div>

					<div class="form-group col-md-2 mb-1" style="padding-right: 16px;">
						<label for="buyerName" class="col-form-label-sm mb-0 pb-0 ">Cutting
							Date</label>
						<div class="row">
							<input type="date" id="cuttingDate"
								class="col-md-12 form-control-sm" />
						</div>
					</div>

					<div class="form-group col-md-2 mb-1"
						style="padding-left: 15px; padding-right: 16px;">
						<label for="sample" class="col-form-label-sm mb-0 pb-0">Marking
							Layer</label>
						<div class="row">
							<input type="text" id="markingLayer"
								class="col-md-12 form-control-sm" />
						</div>
					</div>

					<div class="form-group col-md-2 mb-1"
						style="padding-left: 15px; padding-right: 16px;">
						<label for="sample" class="col-form-label-sm mb-0 pb-0">Marking
							Length</label>
						<div class="row">
							<input type="text" id="markingLength"
								class="col-md-12 form-control-sm" />
						</div>
					</div>

					<div class="form-group col-md-2 mb-1"
						style="padding-left: 15px; padding-right: 16px;">
						<label for="sample" class="col-form-label-sm mb-0 pb-0">Marking
							Width</label>
						<div class="row">
							<input type="text" id="markingWidth"
								class="col-md-12 form-control-sm" />
						</div>
					</div>
					<div class="form-group col-md-2 mb-1" style="padding-right: 30px;">
						<label for="sample" class="col-form-label-sm mb-0 pb-0">Before
							The Cut</label>
						<div class="row">
							<input type="text" id="beforeTheCut"
								class="col-md-12 form-control-sm" />
						</div>
					</div>

				</div>


			</div>

		</div>




		<div id="tableList"></div>

		<div class="row">


			<div class="form-group col-md-5 mb-1"
				style="padding-left: 30px; padding-right: 10px;">
				<label for="buyerName" class="col-form-label-sm mb-0 pb-0 ">Description</label>
				<div class="row">
					<input type="text" id="description"
						class="col-md-12 form-control-sm" />
				</div>
			</div>

			<div class="form-group col-md-3 mb-2"
				style="padding-left: 25px; padding-right: 16px;">
				<label for="sample" class="col-form-label-sm mb-0 pb-0">Total
					Pcs Cut On the Date</label>
				<div class="row">
					<input type="text" id="markingLayer"
						class="col-md-12 form-control-sm" />
				</div>
			</div>



			<div class="form-group col-md-2 mb-1"
				style="padding-left: 15px; padding-right: 16px;">
				<label for="sample" class="col-form-label-sm mb-0 pb-0">Balance</label>
				<div class="row">
					<input type="text" id="markingWidth"
						class="col-md-12 form-control-sm" />
				</div>
			</div>


		</div>

		<div class="row" style="margin-top: 15px;">

			<div class="col-md-12">
				<button id="btnPOSubmit" type="button"
					class="btn btn-primary btn-sm" onclick="confrimAction()">
					<i class="fas fa-save"></i> Confrim
				</button>
				<button id="btnPOEdit" type="button"
					class="btn btn-primary btn-sm ml-1" onclick="buyerPoEditAction()"
					disabled>
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
	</div>

</div>
</div>
<!-- Modal -->
<div class="modal fade" id="exampleCuttingModal" tabindex="-1"
	role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<div class="input-group">
					<input id="search" type="text" class="form-control"
						placeholder="Search Production Plan"
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
							<th>Buyer</th>
							<th>Purchase Order</th>
							<th>Style No</th>
							<th>Item Name</th>
							<th>Cutting No</th>
							<th>Cutting Date</th>
							<th><span><i class="fa fa-search"></i></span></th>
						</tr>
					</thead>
					<tbody id="poList">
						<c:forEach items="${cuttingInformationList}" var="list"
							varStatus="counter">
							<tr>
								<td>${counter.count}</td>
								<td>${list.buyerName}</td>
								<td>${list.purchaseOrder}</td>
								<td>${list.styleNo}</td>
								<td>${list.itemName}</td>
								<td>${list.cuttingNo}</td>
								<td>${list.cuttingDate}</td>
								<td><i class="fa fa-search"
									onclick="searchCuttingInformation(${list.cuttingEntryId})">
								</i></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
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
						placeholder="Search Production Plan"
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
							<th>Buyer</th>
							<th>Purchase Order</th>
							<th>Style No</th>
							<th>Item Name</th>
							<th><span><i class="fa fa-search"></i></span></th>
						</tr>
					</thead>
					<tbody id="poList">
						<c:forEach items="${productionPlanList}" var="list"
							varStatus="counter">
							<tr>
								<td>${counter.count}</td>
								<td id='buyerId${list.buyerId}'>${list.buyerName}</td>
								<td>${list.purchaseOrder}</td>
								<td id='styleId${list.styleId}'>${list.styleNo}</td>
								<td id='itemId${list.itemId}'>${list.itemName}</td>
								<td><i class="fa fa-search"
									onclick="searchProductPlan(${list.buyerId},${list.buyerorderId},${list.styleId},${list.itemId})">
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
	src="${pageContext.request.contextPath}/assets/js/production/cutting_plan.js"></script>
