<%@page import="pg.share.ItemType"%>
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
	<div class="m-2">
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
		<input type="hidden" id="itemAutoId" value="0">
		<div class="card-box pt-1">
			<header class="d-flex justify-content-between">
				<h5 class="text-center" style="display: inline;">Purchase Order
					Approval Form</h5>
				<!-- <div class="row">
					<div class="col-md-12">
						<button type="button" class="btn btn-outline-dark btn-sm"
							data-toggle="modal" data-target="#searchModal" title="Search">
							<i class="fa fa-search"></i>
						</button>
						<button type="button" class="btn btn-outline-dark btn-sm"
							data-toggle="modal" data-target="#styleModal" title="View Style">
							<i class="fas fa-tshirt"></i>
						</button>
					</div>
				</div> -->
			</header>
			<hr class="my-1">
			<div class="row">
				<div class="col-md-12">
					<div class="card shadow  p-2 mb-3 ">
						<div class="row">
							<div class="col-md-3">
								<div class="form-group mb-0  row">
									<label for="fromDate" class="col-md-4 col-form-label-sm pr-0">From
										Date</label> <input id="fromDate" type="date"
										class="col-md-7 form-control-sm px-0">
								</div>
							</div>

							<div class="col-md-3">
								<div class="form-group mb-0  row">
									<label for="toDate"
										class="col-md-3 col-form-label-sm pr-0 pr-0">To Date</label> <input
										id="toDate" type="date" class="col-md-7 form-control-sm px-0">
								</div>
							</div>

							<div class="col-md-4">
								<div class="form-group mb-0  row">
									<label for="itemType" class="col-md-2 col-form-label-sm pr-0">type</label>
									<select id="itemType" class="col-md-5 px-0 form-control-sm">
										<option value="0">Select Type</option>
										<%
											int length = ItemType.values().length;
											for (int i = 0; i < length; i++) {
										%>
										<option 
											value="<%=ItemType.values()[i].getType()%>"><%=ItemType.values()[i].name()%></option>
										<%
											}
										%>
										<option value="allItem">All Item</option>
									</select>
									<select id="approveType" class="col-md-5 px-0 form-control-sm">
										<option value="0">Pending</option>
										<option value="1">Approved</option>
									</select>
								</div>
							</div>
							<div class="col-md-2">
								<button id="btnSearch" type="button" class="btn btn-outline-dark btn-sm" title="Search">
									<i class="fas fa-search"></i> Search
								</button>
							</div>
						</div>
					</div>


					<div class="row">
						<div style="overflow: auto; max-height: 300px;" class="col-sm-12">
							<table class="table table-hover table-bordered table-sm mb-0">
								<thead>
									<tr>
										<th>Sl</th>
										<th>Purchase Order</th>
										<th>Style No</th>
										<th>Supplier Name</th>
										<th>Po No</th>
										<th><input type="checkbox" id="checkAll"><span><label for="checkAll">Approve</label></span></th>
										<th>Item Type</th>
										<th>Date</th>
										<th><i class="fa fa-eye"></i>Preview</th>
									</tr>
								</thead>
								<tbody id="purchaseOrderList">

								</tbody>
							</table>
						</div>
					</div>
					<div class="row mt-1">
						<div class="col-md-12 d-flex justify-content-end">
							<button id="btnConfirm" type="button"
								class="btn btn-primary btn-sm ml-1">
								<i class="fa fa-check"></i> Confirm
							</button>
							<button id="btnRefresh" type="button"
								class="btn btn-primary btn-sm ml-1">
								<i class="fa fa-refresh"></i> Refresh
							</button>
							<!-- 							<button id="btnPreview" type="button"
								class="btn btn-primary btn-sm ml-1" onclick="confrimAction()">
								<i class="fa fa-print"></i> Preview
							</button> -->
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<jsp:include page="../include/footer.jsp" />

<script
	src="${pageContext.request.contextPath}/assets/js/order/purchase-order-approval-from-md.js"></script>
