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
		<input type="hidden" id="itemAutoId" value="0">
		<input type="hidden" id="itemType" value="new">
		<input type="hidden" id="costingNo" value="new">

		<div class="card-box pt-1">
			<header class="d-flex justify-content-between">
				<h5 class="text-center" style="display: inline;">
					Costing Create <span class="badge badge-primary"
						id='badgeCostingNo'>New</span>
				</h5>
				<div class="row">
					<div class="col-md-12">
						<button type="button" class="btn btn-outline-dark btn-sm"
							data-toggle="modal" data-target="#searchModal" title="Search">
							<i class="fa fa-search"></i>Search Costing
						</button>
						<button type="button" class="btn btn-outline-dark btn-sm"
							data-toggle="modal" data-target="#styleModal" title="View Style">
							<i class="fas fa-tshirt"></i> Group Costing
						</button>
					</div>
				</div>


			</header>
			<hr class="my-1">
			<div class="row">
				<div class="col-md-12">
					<div class="card shadow  p-2 mb-3 ">
						<div class="row">
							<div class="col-md-12">
								<div class="form-group mb-0  row">
									   <label for="styleName" style="padding:5px;width:90px;" >Style No</label> 
										<input id="styleNo" type="text" class="col-md-2 form-control-sm" >
										
									
										<label for="itemName" style="padding:5px;width:90px;" >Item Name</label> 
									<input id="itemName" type="text" class="col-md-3 form-control-sm" >
										
									<label for="itemName" style="padding:5px;width:120px;">Commission %</label> 
									<input id="commission" type="text" class="col-md-1 form-control-sm" >
									
								<button style="margin-left:10px;height:40px;" type="button" class="btn btn-outline-dark btn-sm" id="copyCosting"
									onclick="cloneButtonAction()" title="Copy Costing">
									<i class="fas fa-copy"></i> Copy Costing
								</button>
								
								<button style="margin-left:10px;height:40px;" id="btnAdd" type="button" class="btn btn-primary btn-sm" accesskey="S"
										onclick="addNewRow()" accesskey="A">
										<i class="fa fa-plus-circle"></i><span style="text-decoration:underline;"> Add Row</span>
								</button>
									
								</div>
							</div>


						</div>

					</div>


					<div class="row">
						<div style="overflow: auto; max-height: 470px;" class="col-sm-12">
							<table class="table table-hover table-bordered table-sm mb-0">
								<thead>
									<tr>
										<th>SL#</th>
										<th style="width:460px;">Particular Item</th>
										<th style="width:120px;">Unit</th>
										<th style="width:60px;">Width</th>
										<th style="width:80px;">Yard</th>
										<th style="width:80px;">GSM</th>
										<th style="width:80px;">CONS/ </br>DOZEN</th>
										<th style="width:80px;">Rate</th>
										<th style="width:80px;">Amount</th>
										<th><i class="fa fa-edit"></i>Edit</th>
										<th><i class="fa fa-trash"></i>Delete</th>
									</tr>
								</thead>
								<tbody id="dataList_costing">

								</tbody>
							</table>
						</div>
					</div>
					<div class="row mt-1">
						<div class="col-md-12 d-flex justify-content-end">
							<button id="btnNewCosting" type="button" onclick="CostingConfrim()" accesskey="N"
								class="btn btn-primary btn-sm ml-1" title="Save As New Costing">
								<i class="fas fa-save"></i><span style="text-decoration:underline;"> New Costing</span>
							</button>
							<button id="btnEditCosting" type="button"
								class="btn btn-success btn-sm ml-1" style="display: none;">
								<i class="fas fa-pencil-square"></i> Edit Costing
							</button>
							<button id="btnRefresh" type="button"
								class="btn btn-secondary btn-sm ml-1" onclick="refreshAction()">
								<i class="fa fa-refresh"></i> Refresh
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!--Search Modal -->
<div class="modal fade" id="searchModal" tabindex="-1" role="dialog"
	aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<div class="input-group">
					<input id="searchCosting" type="text" class="form-control"
						placeholder="Search Costing" aria-label="Recipient's username"
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
							<th>Sl</th>
							<th>Costing No</th>
							<th>Date</th>
							<th>Style</th>
							<th>Item Name</th>
							<th><span><i class="fa fa-search"></i></span></th>
							<th>Print</th>
						</tr>
					</thead>
					<tbody id="costingListTable">
						<c:forEach items="${costingList}" var="costing"
							varStatus="counter">
							<tr>
								<td>${counter.count}</td>
								<td>${costing.costingNo}</td>
								<td>${costing.date}</td>
								<td>${costing.styleName}</td>
								<td>${costing.itemName}</td>

								<td><i class="fa fa-search"
									onclick="searchCosting('${costing.styleId}', '${costing.itemId}','${costing.costingNo}')"
									style='cursor: pointer;'></i></td>
								<td><i class="fa fa-print"
									onclick="itemWiseCostingReport('${costing.styleId}', '${costing.itemId}','${costing.costingNo}')"
									style='cursor: pointer;'></i></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>

		</div>
	</div>
</div>

<!--Clone Modal -->
<div class="modal fade" id="cloneModal" tabindex="-1" role="dialog"
	aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<div class="input-group">
					<input id="cloneCostingSearch" type="text" class="form-control"
						placeholder="Search Style Or Item Name"
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
							<th>Costing No</th>
							<th>Style</th>
							<th>Item Name</th>
							<th><span><i class="fa fa-search"></i></span></th>
						</tr>
					</thead>
					<tbody id="cloneCostingTable">
						<c:forEach items="${costingList}" var="costing"
							varStatus="counter">
							<tr>
								<td>${costing.costingNo	}</td>
								<td>${costing.styleName}</td>
								<td>${costing.itemName}</td>
								<td><i class="fas fa-copy"
									onclick="cloningCosting('${costing.costingNo}','${costing.styleId}', '${costing.itemId}')"
									style="cursor: pointer;"></i></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>

		</div>
	</div>
</div>

<!--Style Modal -->
<div class="modal fade" id="styleModal" tabindex="-1" role="dialog"
	aria-hidden="true">
	<div class="modal-dialog modal-md" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="exampleModalLongTitle">All Costing
					Style</h5>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>


			<div class="modal-body">
				<div class="row">
					<select id="buyerName" class="selectpicker col-md-12"
						onchange="buyerWiseCostingLoad()" data-live-search="true"
						data-style="btn-light btn-sm border-light-gray">
						<option value="0">Select Buyer</option>
						<c:forEach items="${buyerList}" var="buyer">
							<option value="${buyer.buyerid}">${buyer.buyername}</option>
						</c:forEach>
					</select>
				</div>
				<div class="row">
					<div style="overflow: auto; max-height: 300px;" class="col-sm-12">
						<table class="table table-hover table-bordered table-sm mb-0">
							<thead>
								<tr>
									<th style="width: 15px;">Costing No</th>
									<th>Date</th>
									<th>Style</th>
									<th>Item Name</th>
									<th>Check</th>
								</tr>
							</thead>
							<tbody id="groupCostingList">
								<c:forEach items="${costingList}" var="costing"
									varStatus="counter">
									<tr id='groupRow-${costing.costingNo }' data-id='${costing.costingNo}'>
										<td>${costing.costingNo}</td>
										<td>${costing.date}</td>
										<td>${costing.styleName}</td>
										<td>${costing.itemName}</td>
										<td><input id='groupCheck-${costing.costingNo }' type="checkbox" /></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button id="btnGroupPreview" type="button"
					class="btn btn-sm btn-info" onclick="printGroupCosting()">Preview</button>
				<button type="button" class="btn btn-sm btn-secondary"
					data-dismiss="modal">Close</button>
			</div>
		</div>
	</div>
</div>
<jsp:include page="../include/footer.jsp" />

<script
	src="${pageContext.request.contextPath}/assets/js/order/costing_create_new.js"></script>
