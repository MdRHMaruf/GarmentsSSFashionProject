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
String userId=(String)session.getAttribute("userId");
String userName=(String)session.getAttribute("userName");
String departmentId=(String)session.getAttribute("departmentId");

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
	<input type="hidden" id="departmentId" value="<%=departmentId%>">
	<input type="hidden" id="poNo" value="0">
		<input type="hidden" id="cuttingEntryId" value="0">
	<input type="hidden"
		id="indentId" value="0"> 
		<input type="hidden" id="styleId" value="0">
		<input type="hidden" id="styleItemId" value="0">
		<input type="hidden" id="itemColorId" value="0">
		<input type="hidden" id="fabricsColorId" value="0">
		<input type="hidden" id="fabricsId" value="0"> 
		<input type="hidden" id="unitId" value="0"> 
		
		<input type="hidden" id="fabricsRate" value="0">

	<div class="card-box">
		<header class="d-flex justify-content-between">
			<h5 class="text-center" style="display: inline;">Cutting Fabrics Requisition</h5>
		</header>
		<hr class="my-1">
		<div class="row">
			
			<div class="col-md-5">
				<div class="row">
					<div class="col-md-6 px-1">
						<u><h5>Plan Requisition </h5></u>
					</div>
					<div class="col-md-6">
						<button id="itemSearchBtn" type="button"
							class="btn btn-outline-dark btn-sm form-control-sm"
							data-toggle="modal" data-target="#itemSearchModal">
							<i class="fa fa-search"></i>
						</button>
						<button id="itemSearchBtn" type="button"
							class="btn btn-outline-dark btn-sm form-control-sm"
							data-toggle="modal" data-target="#requisitionlist">
							<i class="fa fa-search">Requisition List</i>
						</button>
					</div>
				</div>
				<div class="row">
					<div class="col-md-3 px-1">
						<label for="purchaseOrder">Purchase Order:</label>
					</div>
					<div class="col-md-9 px-1">
						<b><label id="purchaseOrder"></label></b>
					</div>
				</div>

				<div class="row">
					<div class="col-md-3 px-1">
						<label for="styleNo">Style No:</label>
					</div>
					<div class="col-md-9 px-1">
						<b><label id="styleNo"></label></b>
					</div>
				</div>

				<div class="row">
					<div class="col-md-3 px-1">
						<label for="itemName">Item Name:</label>
					</div>
					<div class="col-md-9 px-1">
						<b><label id="itemName"></label></b>
					</div>
				</div>
	

			</div>
		</div>

		<hr class="my-1">
			<div id="tableList">

			</div>
		<div class="row">
			<div class="col-md-12 d-flex justify-content-end">
				<button id="btnSendRequisition" type="button" class="btn btn-primary btn-sm"
					onclick="submitAction()">
					<i class="fas fa-save"></i> Send Requisition
				</button>
				<button id="btnRefresh" type="button"
					class="btn btn-primary btn-sm ml-1" onclick="refreshAction()">
					<i class="fa fa-refresh"></i> Refresh
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
							<th>Transaction Id</th>
							<th>GRN No</th>
							<th>GRN Date</th>
							<th><span><i class="fa fa-search"></i></span></th>
						</tr>
					</thead>
					<tbody id="fabricsReceiveList">

					</tbody>
				</table>
			</div>

		</div>
	</div>
</div>

<!-- Item Search Modal -->
<div class="modal fade" id="itemSearchModal" tabindex="-1" role="dialog"
	aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
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
						<c:forEach items="${cuttingInformationList}" var="list"
							varStatus="counter">
							<tr>
								<td>${counter.count}</td>
								<td id='buyerId${list.buyerId}'>${list.buyerName}</td>
								<td>${list.purchaseOrder}</td>
								<td id='styleId${list.styleId}'>${list.styleNo}</td>
								<td id='itemId${list.itemId}'>${list.itemName}</td>
								<td><i class="fa fa-search"
									onclick="searchCuttingUsedFabrics(${list.cuttingEntryId})">
								</i></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>

		</div>
	</div>
</div>

<div class="modal fade" id="requisitionlist" tabindex="-1" role="dialog"
	aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
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
		
			<div class="modal-body">
				<table class="table table-hover table-bordered table-sm mb-0">
					<thead>
						<tr>
							<th>SL#</th>
							<th>Buyer</th>
							<th>Purchase Order</th>
							<th>Style No</th>
							<th>Item Name</th>
							<th><span><i class="fa fa-print"></i></span>Print</th>
						</tr>
					</thead>
					<tbody id="poList">
						<c:forEach items="${cuttingReqList}" var="list"
							varStatus="counter">
							<tr>
								<td>${counter.count}</td>
								<td id='buyerId${list.buyerId}'>${list.buyerName}</td>
								<td>${list.purchaseOrder}</td>
								<td id='styleId${list.styleId}'>${list.styleNo}</td>
								<td id='itemId${list.itemId}'>${list.itemName}</td>
								<td><i class="fa fa-print"
									onclick="printCuttingUsedFabricsRequisition(${list.cuttingEntryId})">
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
	src="${pageContext.request.contextPath}/assets/js/store/cutting-fabrics-requisition.js"></script>
