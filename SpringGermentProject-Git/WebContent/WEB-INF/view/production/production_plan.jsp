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
	<input type="hidden" id="userId" value="<%=userId%>">
	<input type="hidden" id="itemAutoId" value="0">

	<div class="card-box">
		<header class="d-flex justify-content-between">
			<h5 class="text-center" style="display: inline;">Production Plan</h5>
			<button type="button" class="btn btn-outline-dark btn-sm"
				data-toggle="modal" data-target="#exampleModal">
				<i class="fa fa-search"></i>
			</button>
		</header>
		<hr class="my-1">
		<div class="row">
			<div class="col-md-10 mb-1 pr-1">
				<div class="row">
					<div class="form-group col-md-4 mb-1" style="padding-right: 1px;">
						<label for="buyerName" class="col-form-label-sm mb-0 pb-0">Buyer
							Name:</label>
						<div class="row">
							<select id="buyerId" class="selectpicker col-md-12"
								onchange="buyerWisePoLoad()" data-live-search="true"
								data-style="btn-light btn-sm border-light-gray">
								<option id="buyerId" value="0">Select Buyer</option>
								<c:forEach items="${buyerList}" var="buyer">
									<option id="buyerId" value="${buyer.buyerid}">${buyer.buyername}</option>
								</c:forEach>
							</select>
						</div>


					</div>
					<div class="form-group col-md-2 mb-1"
						style="padding-left: 1px; padding-right: 1px;">
						<label for="purchaseOrder" class="col-form-label-sm mb-0 pb-0">Purchase Order:</label>
						<div class="row">
							<select id="purchaseOrder" class="selectpicker col-md-12"
								onchange="poWiseStyles()" data-live-search="true"
								data-style="btn-light btn-sm border-light-gray">
								<option id="purchaseOrder" value="0">Select Purchase Order</option>
							</select>
						</div>

					</div>
					<div class="form-group col-md-2 mb-1"
						style="padding-left: 1px; padding-right: 1px;">
						<label for="styleNo" class="col-form-label-sm mb-0 pb-0">Style
							No:</label>
						<div class="row">
							<select id="styleNo" class="selectpicker col-md-12"
								onchange="styleWiseItems()" data-live-search="true"
								data-style="btn-light btn-sm border-light-gray">
								<option id="styleNo" value="0">Select Style</option>
							</select>
						</div>

					</div>
					<div class="form-group col-md-4 mb-1" style="padding-left: 1px;">
						<label for="itemType" class="col-form-label-sm mb-0 pb-0">Item
							Description:</label>
						<div class="row">
							<select id="itemName" class="selectpicker col-md-12"
								data-live-search="true"
								data-style="btn-light btn-sm border-light-gray" onchange="styleItemsWiseColor()">
								<option id="itemName" value="0">Select Item Description</option>
							</select>
						</div>

					</div>
				</div>
				
				<div class="row">
					<div class="form-group col-md-2 mb-1" style="padding-left: 30px;">
						<label for="buyerName" class="col-form-label-sm mb-0 pb-0">Order Qty</label>
						<div class="row">
								<input type="text" readonly id="orderQty" class="col-md-12 form-control-sm" />
						</div>
					</div>
					<div class="form-group col-md-2 mb-1"
						style="padding-left: 15px; padding-right: 16px;padding-left: 18px;">
						<label for="sample" class="col-form-label-sm mb-0 pb-0">Plan Qty</label>
						<div class="row">
								<input type="text" id="planQty" class="col-md-12 form-control-sm" />
						</div>
					</div>					
					
					
					<div class="form-group col-md-2 mb-1" style="padding-right: 16px;">
						<label for="buyerName" class="col-form-label-sm mb-0 pb-0 ">Ship
							Date</label>
						<div class="row">
							<input type="date" id="shipDate"
								class="col-md-12 form-control-sm" />
						</div>
					</div>
					<div class="form-group col-md-4 mb-1" style="padding-left: 1px;">
						<label for="itemType" class="col-form-label-sm mb-0 pb-0">Merchendizer
							Name:</label>
						<div class="row">
							<select id="merchendizerId" class="selectpicker col-md-12"
								data-live-search="true"
								data-style="btn-light btn-sm border-light-gray">
								<option id="merchendizerId" value="0">Select Merchendizer</option>
								<c:forEach items="${merchendizerList}" var="list">
									<option id="merchendizerId" value="${list.id}">${list.name}</option>
								</c:forEach>
							</select>
						</div>

					</div>
					
					<div class="form-group col-md-2 mb-1" style="padding-right: 30px;">
						<label for="buyerName" class="col-form-label-sm mb-0 pb-0 ">File
							Sample</label>
						<div class="row">
							<input type="text" id="fileSample"
								class="col-md-12 form-control-sm" />
						</div>
					</div>
				</div>
	

				<div class="row">
					<div class="form-group col-md-2 mb-1" style="padding-left: 30px;padding-right: 15px;">
						<label for="buyerName" class="col-form-label-sm mb-0 pb-0 ">PP Status
							</label>
						<div class="row">
							<input type="text" id="ppStatus"
								class="col-md-12 form-control-sm" />
						</div>
					</div>
					<div class="form-group col-md-3 mb-1" style="padding-left: 18px;">
						<label for="buyerName" class="col-form-label-sm mb-0 pb-0 ">Accessories In house
							</label>
						<div class="row">
							<input type="text" id="accessoriesInhouse"
								class="col-md-12 form-control-sm" />
						</div>
					</div>
					<div class="form-group col-md-3 mb-1" style="padding-left: 20px;">
						<label for="buyerName" class="col-form-label-sm mb-0 pb-0 ">Fabrics In house
							</label>
						<div class="row">
							<input type="text" id="fabricsInhouse"
								class="col-md-12 form-control-sm" />
						</div>
					</div>	
					<div class="form-group col-md-2 mb-1" style="padding-right: 16px;padding-left: 18px;">
						<label for="buyerName" class="col-form-label-sm mb-0 pb-0 ">Start Date</label>
						<div class="row">
							<input type="date" id="startDate"
								class="col-md-12 form-control-sm" />
						</div>
					</div>			
					<div class="form-group col-md-2 mb-1" style="padding-right: 16px;padding-left: 18px;">
						<label for="buyerName" class="col-form-label-sm mb-0 pb-0 ">End</label>
						<div class="row">
							<input type="date" id="endDate"
								class="col-md-12 form-control-sm" />
						</div>
					</div>							
				</div>


			</div>

		</div>
		
		
		
		
		<div id="tableList">
 				<table class="table table-hover table-bordered table-sm mb-0">
					<thead>
						<tr>
							<th>SL#</th>
							<th>Buyer</th>
							<th>Purchase Order</th>
							<th>Style No</th>
							<th>Item Description</th>
							<th>Order Qty</th>
							<th>Plan Qty</th>
							<th>PP Status</th>
							<th>File Sample</th>
							<th>Start Date</th>
							<th>End Date</th>
						</tr>
					</thead>
					<tbody id="production_plan">
							
					</tbody>	

				</table>	 
		
		</div>
		
			<div class="row" style="margin-top: 15px;">
			<div class="col-md-12">
				<button id="btnPOSubmit" type="button"
					class="btn btn-primary btn-sm" onclick="saveAction()" accesskey="S">
					<i class="fas fa-save"></i><span style="text-decoration:underline;"> Save</span>
				</button>
				<button id="btnPOEdit" type="button"
					class="btn btn-primary btn-sm ml-1" onclick = "buyerPoEditAction()" accesskey="E" disabled>
					<i class="fa fa-pencil-square" ></i><span style="text-decoration:underline;"> Edit</span>
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
<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog"
	aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<div class="input-group">
					<input id="search" type="text" class="form-control"
						placeholder="Search Sample Requisition"
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
							<th>Search</th>
							<th>Print</th>
						</tr>
					</thead>
					<tbody id="poList">
						<c:forEach items="${productionPlanList}" var="list" varStatus="counter">
							<tr>
								<td>${counter.count}</td>
								<td id='buyerId${list.buyerId}'>${list.buyerName}</td>
								<td >${list.purchaseOrder}</td>
								<td id='styleId${list.styleId}'>${list.styleNo}</td>
								<td><i class="fa fa-search"
									onclick="searchProductPlan(${list.buyerId},${list.styleId},${list.buyerorderId})">
								</i></td>
								<td><i class="fa fa-search"
									onclick="printProductPlan(${list.buyerId},${list.styleId},${list.buyerorderId})">
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
	src="${pageContext.request.contextPath}/assets/js/production/production_plan.js"></script>
