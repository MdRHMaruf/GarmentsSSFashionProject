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
			<div>
				<h5 class="text-center" style="display: inline;">Sample
					Requisition</h5>
				<div class="form-check form-check-inline">
					<input class="form-check-input ml-1" type="radio" name="radio"
						id="withPO" value="" checked> <label
						class="form-check-label" for="withPO">With PO</label>
				</div>
				<div class="form-check form-check-inline">
					<input class="form-check-input" type="radio" name="radio"
						id="withOutPO" value=""> <label class="form-check-label"
						for="withOutPO">With Out PO</label>
				</div>
			</div>

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
						<label for="purchaseOrder" class="col-form-label-sm mb-0 pb-0">Purchase
							Order:</label>
						<div class="row">
							<select id="purchaseOrder" class="selectpicker col-md-12"
								onchange="poWiseStyles()" data-live-search="true"
								data-style="btn-light btn-sm border-light-gray">
								<option id="purchaseOrder" value="0">Select Purchase
									Order</option>
							</select>
						</div>

					</div>
					<div class="form-group col-md-2 mb-1"
						style="padding-left: 1px; padding-right: 1px;">
						<label for="styleNo" class="col-form-label-sm mb-0 pb-0">Style
							No:</label>
						<div class="row">
							<select id="styleNo" class="selectpicker col-md-12"
								onchange="styleWiseItemLoad()" data-live-search="true"
								data-style="btn-light btn-sm border-light-gray">
								<option  value="0">Select Style</option>
										<c:forEach items="${styleList}" var="style">
											<option  value="${style.styleId}">${style.styleNo}</option>
										</c:forEach>
							</select>
						</div>

					</div>
					<div class="form-group col-md-4 mb-1" style="padding-left: 1px;">
						<label for="itemType" class="col-form-label-sm mb-0 pb-0">Item
							Description:</label>
						<div class="row">
							<select id="itemName" class="selectpicker col-md-12"
								data-live-search="true"
								data-style="btn-light btn-sm border-light-gray"
								onchange="styleItemsWiseColor()">
								<option id="itemName" value="0">Select Item Description</option>
							</select>
						</div>

					</div>
				</div>

				<div class="row">
					<div class="form-group col-md-4 mb-1" style="padding-right: 1px;">
						<label for="buyerName" class="col-form-label-sm mb-0 pb-0">Color</label>
						<div class="row">
							<select id="colorName" class="selectpicker col-md-12"
								data-live-search="true"
								data-style="btn-light btn-sm border-light-gray">
								<option value="0">Select Color</option>
								<c:forEach items="${colorList}" var="color">
									<option value="${color.colorId}">${color.colorName}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group col-md-2 mb-1"
						style="padding-left: 1px; padding-right: 1px;">
						<label for="sample" class="col-form-label-sm mb-0 pb-0">Sample
							Type</label>
						<div class="row">
							<select id="sampleId" class="selectpicker col-md-12"
								data-live-search="true"
								data-style="btn-light btn-sm border-light-gray">

								<option id="sampleId" value="0">Select Sample</option>
								<c:forEach items="${sampleList}" var="list">
									<option id="sampleId" value="${list.id}">${list.name}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<%-- 					<div class="form-group col-md-2 mb-1" style="padding-right: 1px;">
						<label for="buyerName" class="col-form-label-sm mb-0 pb-0">Sample Type</label>
						<div class="row">
							<select id="color" class="selectpicker col-md-12"
								data-live-search="true"
								data-style="btn-light btn-sm border-light-gray">

								<option id="color" value="0">Select Sample</option>
								<c:forEach items="${sampleList}" var="list">
									<option id="sample" value="${list.id}">${list.name}</option>
								</c:forEach>
							</select>
						</div>
					</div> --%>

					<div class="form-group col-md-2 mb-1" style="padding-right: 16px;">
						<label for="buyerName" class="col-form-label-sm mb-0 pb-0 ">Sample
							Deadline</label>
						<div class="row">
							<input type="date" id="sampleDeadline"
								class="col-md-12 form-control-sm" />
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
					<div class="form-group col-md-4 mb-1" style="padding-right: 1px;">
						<label for="marchendizerId" class="col-form-label-sm mb-0 pb-0">Merchendizer</label>
						<div class="row">
							<select id="marchendizerId" class="selectpicker col-md-12"
								data-live-search="true"
								data-style="btn-light btn-sm border-light-gray">
								<option id="marchendizerId" value="0">Select
									Merchendizer</option>
								<c:forEach items="${merchendizerList}" var="list">
									<option id="marchendizerId" value="${list.id}">${list.name}</option>
								</c:forEach>
							</select>
						</div>

					</div>
					<div class="form-group col-md-8 mb-1" style="padding-right: 29px;">
						<label for="buyerName" class="col-form-label-sm mb-0 pb-0 ">Instruction/Remark</label>
						<div class="row">
							<input type="text" id="instruction"
								class="col-md-12 form-control-sm" />
						</div>
					</div>
				</div>

				<div class="row">
					<div class="col-md-12 d-flex justify-content-end">
						<button id="btnAdd" type="button" class="btn btn-primary btn-sm"
							onclick="itemSizeAdd()">
							<i class="fa fa-plus-circle"></i> Add
						</button>
						<button id="btnEdit" type="button"
							class="btn btn-primary btn-sm ml-1" onclick="itemSizeEdit()"
							disabled>
							<i class="fa fa-pencil-square"></i> Edit
						</button>
						<button id="btnReset" type="button"
							class="btn btn-primary btn-sm ml-1" onclick="reset()">
							<i class="fa fa-refresh"></i> Reset
						</button>
					</div>
				</div>


			</div>
			<div class="col-md-2 mb-1 pl-1">
				<div class="row">
					<div class="col-md-12">
						<select id="sizeGroup" class="form-control-sm border-secondary"
							style="width: 100%;" onchange="sizeLoadByGroup()">
							<option id="sizeGroup" value="0">Select Size Group</option>
							<c:forEach items="${groupList}" var="group" varStatus="counter">
								<option id="sizeGroup" value="${group.groupId}">${group.groupName}</option>
							</c:forEach>
						</select>
					</div>

				</div>
				<div class="row ">
					<div class="col-md-12">
						<div class="list-group" id="listGroup"></div>
					</div>
				</div>


			</div>
		</div>




		<div id="tableList"></div>


		<div class="row">
			<div class="col-md-6">
				<div style="overflow: auto; max-height: 300px;" class="col-sm-12">
					<table
						class="table table-hover table-bordered table-sm mb-0 small-font">
						<thead>
							<tr>
								<th style="width: 15px;">Sl#</th>
								<th>Fabrics Name</th>
							</tr>
						</thead>
						<tbody id="dataList">


						</tbody>
					</table>
				</div>
			</div>

			<div class="col-md-6">

				<div style="overflow: auto; max-height: 300px;" class="col-sm-12">
					<table
						class="table table-hover table-bordered table-sm mb-0 small-font">
						<thead>
							<tr>
								<th style="width: 15px;">Sl#</th>
								<th>Accessories Name</th>
							</tr>
						</thead>
						<tbody id="dataList">


						</tbody>
					</table>
				</div>
			</div>
		</div>

	</div>
	<div class="row">
		<div class="col-md-12 d-flex justify-content-end">
			<button id="btnPOSubmit" type="button" class="btn btn-primary btn-sm"
				onclick="confrimAction()">
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
							<th>PO Id</th>
							<th>Style No</th>
							<th>Date</th>
							<th><span>Search</th>
							<th><span>Print</th>
						</tr>
					</thead>
					<tbody id="poList">
						<c:forEach items="${sampleReqList}" var="po" varStatus="counter">
							<tr>
								<td>${counter.count}</td>
								<td id='buyerName${po.purchaseOrder}'>${po.purchaseOrder}</td>
								<td>${po.styleNo}</td>
								<td>${po.sampleDeadline}</td>
								<td><i class="fa fa-search"
									onclick="searchSampleRequisition(${po.autoId})"> </i></td>
								<td><i class="fa fa-print"
									onclick="printSampleRequisition(${po.autoId})"> </i></td>
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
	src="${pageContext.request.contextPath}/assets/js/order/sample_requisition.js"></script>
