<%@page import="pg.share.ItemType"%>
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
		<input type="hidden" id="userId" value="<%=userId%>">
		<input type="hidden" id="departmentId"
			value="<%=departmentId%>"> <input
			type="hidden" id="itemAutoId" value="0">
		<div class="card-box pt-1">
			<header class="d-flex justify-content-between">
				<h5 class="text-center" style="display: inline;">Pending
					Transaction Receive</h5>

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
										<option value="1">Fabrics</option>
										<option value="2">Accessories</option>
										<option value="3">Catton</option>
										<option value="allItem">All Item</option>
									</select> <select id="approveType" class="col-md-5 px-0 form-control-sm">
										<option value="1">Pending</option>
										<option value="2">Approved</option>
									</select>
								</div>
							</div>
							<div class="col-md-2">
								<button id="btnSearch" type="button"
									class="btn btn-outline-dark btn-sm" title="Search">
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
										<th>Department Name</th>
										<th>Item Type</th>
										<th>Date</th>
										<th><i class="fa fa-eye"></i> Preview</th>
										<th><i class="fas fa-check-square"></i> Receive</th>
									</tr>
								</thead>
								<tbody id="pendingInvoiceList">
									<c:forEach items="${pendingList}" var="invoice">
										<tr>
											<td>${invoice.transactionId }</td>
											<td>${invoice.departmentName }</td>
											<td>${invoice.itemType }</td>
											<td>${invoice.date }</td>
											<td><button class="form-control-sm btn" onclick="printFabricsIssue('${invoice.transactionId }')"><i class="fa fa-eye"></i> Preview</button></td>
											<td><button class="form-control-sm btn" onclick="receiveFabricsIssue('${invoice.transactionId }')"> Receive</button></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
					<div class="row mt-1">
						<div class="col-md-12 d-flex justify-content-end">
							<button id="btnConfirm" type="button" accesskey="C"
								class="btn btn-primary btn-sm ml-1">
								<i class="fa fa-check"></i><span style="text-decoration:underline;"> Confirm</span>
							</button>
							<button id="btnRefresh" type="button"
								class="btn btn-primary btn-sm ml-1">
								<i class="fa fa-refresh"></i> Refresh
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
	src="${pageContext.request.contextPath}/assets/js/store/fabrics-pending-transaction.js"></script>
