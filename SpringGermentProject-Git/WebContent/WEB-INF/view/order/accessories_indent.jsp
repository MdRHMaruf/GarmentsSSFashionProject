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



<input type="hidden" id="userId" value="<%=userId%>">
<input type="hidden" id="autoId" value="">
<input type="hidden" id="indentType" value="">
<input type="hidden" id="accessoriesIndentId" value="New">

<div class="page-wrapper">
	<div class="m-2">
		<div class="row">
			<div class="col-md-12">
				<div class="card-box">
					<div class="d-flex justify-content-end">
						<div class="mr-auto">
							<h4 style="text-align: left;" class="font-weight-bold">
								Accessories Indent <span class="badge badge-primary" id='accessoriesId'>New</span>
							</h4>
						</div>

						<div class="p-0">
							<button type="button" class="btn btn-outline-dark btn-sm"
								data-toggle="modal" data-target="#exampleModal" title="Search">
								Accessories Indent List<i class="fa fa-search"></i>
							</button>
						</div>


					</div>

					<div class="row mt-1">

						<div class="col-md-4">
							<div class="form-group  mb-1"
								style="padding-left: 1px; padding-right: 1px;">
								<div class="d-flex justify-content-between">
									<label for="buyerName" class="col-form-label-sm mb-0 py-0">Buyer
										Name</label>
								</div>
								<div class="row">
									<div class="col-md-12 input-group-append">
										<select id="buyerName" class="selectpicker w-100" multiple
											data-selected-text-format="count > 4" data-live-search="true"
											data-style="btn-light btn-sm border-light-gray"
											aria-describedby="findButton">
											<c:forEach items="${buyerList}" var="buyer">
												<option value="${buyer.buyerid}">${buyer.buyername}</option>
											</c:forEach>
										</select>
										<button class="btn btn-sm btn-primary" type="button"
											onclick="refreshBuyerNameList()">
											<i class="fa fa-refresh"></i>
										</button>
									</div>
								</div>
							</div>
							<div class="form-group  mb-1"
								style="padding-left: 1px; padding-right: 1px;">
								<div class="d-flex justify-content-between">
									<label for="purchaseOrder" class="col-form-label-sm mb-0 py-0">Purchase
										Order<span style="color:red">*</span></label>
									<div class="form-check-inline">
										<label class="form-check-label"> <input
											id="checkPurchaseOrder" type="checkbox"
											class="form-check-input"> Combined
										</label>
									</div>
								</div>
								<div class="row">
									<div class="col-md-12 input-group-append">
										<select id="purchaseOrder" class="selectpicker w-100" multiple
											data-selected-text-format="count > 4" data-live-search="true"
											data-style="btn-light btn-sm border-light-gray"
											aria-describedby="findButton">
											<c:forEach items="${purchaseorders}" var="acc"
												varStatus="counter">
												<option value="${acc.name}">${acc.name}</option>
											</c:forEach>
										</select>
										<button class="btn btn-sm btn-primary" type="button"
											onclick="refreshPurchaseOrderList()">
											<i class="fa fa-refresh"></i>
										</button>
									</div>
								</div>
							</div>
							<div class="form-group  mb-1"
								style="padding-left: 1px; padding-right: 1px;">
								<div class="d-flex justify-content-between">
									<label for="styleNo" class="col-form-label-sm mb-0 py-0">Style
										No<span style="color:red">*</span></label>
									<div class="form-check-inline">
										<label class="form-check-label"> <input
											id="checkStyleNo" type="checkbox" class="form-check-input">
											Combined
										</label>
									</div>
								</div>
								<div class="row">
									<div class="col-md-12 input-group-append">
										<select id="styleNo" class="selectpicker w-100" multiple
											data-selected-text-format="count > 4" data-live-search="true"
											data-style="btn-light btn-sm border-light-gray"
											aria-describedby="findButton">
										</select>
										<button class="btn btn-sm btn-primary" type="button"
											onclick="refreshStyleNoList()">
											<i class="fa fa-refresh"></i>
										</button>
									</div>
								</div>
							</div>
							<div class="form-group  mb-1"
								style="padding-left: 1px; padding-right: 1px;">
								<div class="d-flex justify-content-between">
									<label for="itemName" class="col-form-label-sm mb-0 py-0">Item
										Name<span style="color:red">*</span></label>
									<div class="form-check-inline">
										<label class="form-check-label"> <input
											id="checkItemName" type="checkbox" class="form-check-input">
											Combined
										</label>
									</div>
								</div>
								<div class="row">
									<div class="col-md-12 input-group-append">
										<select id="itemName" class="selectpicker w-100" multiple
											data-selected-text-format="count > 4" data-live-search="true"
											data-style="btn-light btn-sm border-light-gray"
											aria-describedby="findButton">
										</select>
										<button class="btn btn-sm btn-primary" type="button"
											onclick="refreshItemNameList()">
											<i class="fa fa-refresh"></i>
										</button>
									</div>
								</div>
							</div>
							<div class="form-group  mb-1"
								style="padding-left: 1px; padding-right: 1px;">
								<div class="d-flex justify-content-between">
									<label for="color" class="col-form-label-sm mb-0 py-0">Color</label>
									<div class="form-check-inline">
										<label class="form-check-label"> <input
											id="checkColor" type="checkbox" class="form-check-input">
											Combined
										</label>
									</div>
								</div>
								<div class="row">
									<div class="col-md-12 input-group-append">
										<select id="color" class="selectpicker w-100" multiple
											data-selected-text-format="count > 4" data-live-search="true"
											data-style="btn-light btn-sm border-light-gray"
											aria-describedby="findButton">

										</select>
										<button class="btn btn-sm btn-primary" type="button"
											onclick="refreshColorList()">
											<i class="fa fa-refresh"></i>
										</button>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group  mb-1"
										style="padding-left: 1px; padding-right: 1px;">
										<div class="d-flex justify-content-between">
											<label for="shippingMark" class="col-form-label-sm mb-0 py-0">Ship.
												Mark</label>
											<div class="form-check-inline">
												<label class="form-check-label"> <input
													id="checkShippingMark" type="checkbox"
													class="form-check-input">Combined
												</label>
											</div>
										</div>
										<div class="row">
											<div class="col-md-12 input-group-append">
												<select id="shippingMark" class="selectpicker w-100"
													multiple data-selected-text-format="count > 4"
													data-live-search="true"
													data-style="btn-light btn-sm border-light-gray"
													aria-describedby="findButton">
												</select>
												<button class="btn btn-sm btn-primary" type="button"
													onclick="refreshShippingMarkList()">
													<i class="fa fa-refresh"></i>
												</button>
											</div>
										</div>
										
										<div class="row">
											<div class="col-md-4 ml-3">
											<label class="form-check-label"> <input
													id="checkSQ" type="checkbox"
													class="form-check-input">SQ
												</label>
											</div>
											
												<div class="col-md-4">
												<label class="form-check-label"> <input
													id="checkSKU" type="checkbox"
													class="form-check-input">SKU
												</label>
												</div>
												
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group  mb-1"
										style="padding-left: 1px; padding-right: 1px;">
										<div class="d-flex justify-content-start">
											<div class="form-check-inline">
												<label class="form-check-label"> <input
													id="checkSizeRequired" type="checkbox"
													class="form-check-input" value=""><strong>Size
														Required</strong></label>
											</div>
										</div>
										<button class="btn btn-sm btn-primary" type="button"
											id="btnRecyclingData">
											<i class="fa fa-recycle"></i> Recycling Data
										</button>
										<h6 id="sizeHeading">Size: <span id="size-name"></span></h6>
									</div>
								</div>
							</div>

						</div>
						<div class="col-md-4">
							<div class="form-group  mb-1"
								style="padding-left: 1px; padding-right: 1px;">
								<div class="d-flex justify-content-between">
									<label for="accessoriesItem"
										class="col-form-label-sm mb-0 py-0">Accessories Item<span style="color:red">*</span></label>
								</div>
								<div class="row">
									<div class="col-md-12 input-group-append">
										<select id="accessoriesItem" class="selectpicker w-100"
											data-live-search="true"
											data-style="btn-light btn-sm border-light-gray"
											aria-describedby="findButton">
											<option value="0">Select Item</option>
											<c:forEach items="${accessories}" var="acc"
												varStatus="counter">
												<option value="${acc.id}">${acc.name}</option>
											</c:forEach>
										</select>
									</div>
								</div>
							</div>
							<div class="form-group  mb-1"
								style="padding-left: 1px; padding-right: 1px;">
								<div class="d-flex justify-content-between">
									<label for="accessoriesSize"
										class="col-form-label-sm mb-0 py-0">Accessories Size</label>
								</div>
								<input type="text" class='form-control-sm' id="accessoriesSize">
							</div>

							<div class="row">
								<div class="col-md-6 pr-1">
									<div class="form-group  mb-1"
										style="padding-left: 1px; padding-right: 1px;">
										<div class="d-flex justify-content-between">
											<label for="accessoriesColor"
												class="col-form-label-sm mb-0 py-0">Accessories
												Color</label>
										</div>
										<div class="row">
											<div class="col-md-12">
												<select id="accessoriesColor"
													class="selectpicker form-control" data-live-search="true"
													data-style="btn-light btn-sm border-light-gray">
													<option value="0">Select Color</option>
													<c:forEach items="${color}" var="color" varStatus="counter">
														<option id='color' value="${color.id}">${color.name}</option>
													</c:forEach>
												</select>
											</div>
										</div>
									</div>
								</div>
								<div class="col-md-6 pl-1">
									<div class="form-group  mb-1"
										style="padding-left: 1px; padding-right: 1px;">
										<div class="d-flex justify-content-between">
											<label for="brand" class="col-form-label-sm mb-0 py-0">Brand</label>
										</div>
										<div class="row">
											<div class="col-md-12">
												<select id="brand" class="selectpicker form-control"
													data-live-search="true"
													data-style="btn-light btn-sm border-light-gray">
													<option value="0">Select Brand</option>
													<c:forEach items="${brand}" var="brand" varStatus="counter">
														<option id='brand' value="${brand.id}">${brand.name}</option>
													</c:forEach>
												</select>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-6 pr-1">
									<div class="form-group  mb-1"
										style="padding-left: 1px; padding-right: 1px;">
										<div class="d-flex justify-content-between">
											<label for="unit" class="col-form-label-sm mb-0 py-0">Unit<span style="color:red">*</span></label>
										</div>
										<div class="row">
											<div class="col-md-12">
												<select id="unit" class="selectpicker form-control"
													data-live-search="true"
													data-style="btn-light btn-sm border-light-gray"
													onchange="setInPercentAndTotalInPreviewTable(),calculateTotalQtyAndUnitQty(),setUnitQty()">
													<option value="0">Select Unit</option>
													<c:forEach items="${unitList}" var="unit" varStatus="counter">
														<option value="${unit.unitId}">${unit.unitName}</option>
													</c:forEach>
												</select>
											</div>
										</div>
									</div>
								</div>
								<div class="col-md-6 pl-1">
									<div class="form-group  mb-1"
										style="padding-left: 1px; padding-right: 1px;">
										<div class="d-flex justify-content-between">
											<label for="unitQty" class="col-form-label-sm mb-0 py-0"><strong>Unit
													Qty</strong></label>
										</div>
										<input type="number" class='form-control-sm' id="unitQty" onkeyup="setTotalByUnitInEditMode()"
											readonly>
									</div>
								</div>
							</div>

						</div>

						<div class="col-md-4">
							<div class="row">
								<div class="col-md-4 px-1">
									<div class="form-group  mb-1"
										style="padding-left: 1px; padding-right: 1px;">
										<div class="d-flex justify-content-between">
											<label for="orderQty" class="col-form-label-sm mb-0 py-0"><strong>Order
													Qty</strong></label>
										</div>
										<input type="number" class='form-control-sm' id="orderQty"
											readonly>
									</div>
								</div>
								<div class="col-md-4 px-1">
									<div class="form-group  mb-1"
										style="padding-left: 1px; padding-right: 1px;">
										<div class="d-flex justify-content-between">
											<label for="dozenQty" class="col-form-label-sm mb-0 py-0"><strong>Dozen
													Qty</strong></label>
										</div>
										<input type="number" class='form-control-sm' id="dozenQty"
											readonly>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-4 px-1">
									<div class="form-group  mb-1"
										style="padding-left: 1px; padding-right: 1px;">
										<div class="d-flex justify-content-between">
											<label for="reqPerPcs" class="col-form-label-sm mb-0 py-0"><strong>Req.Per
													Pcs</strong></label>
										</div>
										<input type="number" class='form-control-sm' id="reqPerPcs"
											onkeyup="setUnitQty(),setInPercentAndTotalInPreviewTable()">
									</div>
								</div>
								<div class="col-md-4 px-1">
									<div class="form-group  mb-1"
										style="padding-left: 1px; padding-right: 1px;">
										<div class="d-flex justify-content-between">
											<label for="reqPerDozen" class="col-form-label-sm mb-0 py-0"><strong>Req.Per
													Dozen</strong></label>
										</div>
										<input type="number" class='form-control-sm' id="reqPerDozen"
											readonly>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-4 px-1">
									<div class="form-group  mb-1"
										style="padding-left: 1px; padding-right: 1px;">
										<div class="d-flex justify-content-between">
											<label for="perUnit" class="col-form-label-sm mb-0 py-0"><strong>Per
													Unit</strong></label>
										</div>
										<input type="number" class='form-control-sm' id="perUnit">
									</div>
								</div>
								<div class="col-md-4 px-1">
									<div class="form-group  mb-1"
										style="padding-left: 1px; padding-right: 1px;">
										<div class="d-flex justify-content-between">
											<label for="totalBox" class="col-form-label-sm mb-0 py-0"><strong>Total
													Box</strong></label>
										</div>
										<input type="number" class='form-control-sm' id="totalBox"
											readonly>
									</div>
								</div>
								<div class="col-md-4 px-1">
									<div class="form-group  mb-1"
										style="padding-left: 1px; padding-right: 1px;">
										<div class="d-flex justify-content-between">
											<label for="divideBy" class="col-form-label-sm mb-0 py-0"><strong>Divide
													By</strong></label>
										</div>
										<input type="number" class='form-control-sm' id="divideBy" onkeyup="setUnitQty(),setInPercentAndTotalInPreviewTable()">
									</div>
								</div>
							</div>
							<div class="mt-1">
								<strong>Extra</strong>
							</div>
							<div class="row">
								<div class="col-md-4 px-1">
									<div class="form-group  mb-1"
										style="padding-left: 1px; padding-right: 1px;">
										<div class="d-flex justify-content-between">
											<label for="inPercent" class="col-form-label-sm mb-0 py-0"><strong>In
													Percent(%)</strong></label>
										</div>
										<input type="number" class='form-control-sm' id="inPercent"
											onkeyup="setUnitQty(),setInPercentAndTotalInPreviewTable()">
									</div>
								</div>
								<div class="col-md-4 px-1">
									<div class="form-group  mb-1"
										style="padding-left: 1px; padding-right: 1px;">
										<div class="d-flex justify-content-between">
											<label for="percentQty" class="col-form-label-sm mb-0 py-0"><strong>Percent
													Qty</strong></label>
										</div>
										<input type="number" class='form-control-sm' id="percentQty"
											readonly>
									</div>
								</div>
								<div class="col-md-4 px-1">
									<div class="form-group  mb-1"
										style="padding-left: 1px; padding-right: 1px;">
										<div class="d-flex justify-content-between">
											<label for="totalQty" class="col-form-label-sm mb-0 py-0"><strong>Total
													Qty</strong></label>
										</div>
										<input type="number" class='form-control-sm' id="totalQty" onkeyup="setUnitByTotalInEditMode()"
											readonly>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="alert alert-success alert-dismissible fade show"
						style="display: none;">
						<p id="successAlert" class="mb-0">
							<strong>Success!</strong> Unit Name Save Successfully..
						</p>
					</div>
					<div class="alert alert-warning alert-dismissible fade show"
						style="display: none;">
						<p id="warningAlert" class="mb-0">
							<strong>Warning!</strong> Unit Name Empty.Please Enter Unit
							Name...
						</p>
					</div>
					<div class="alert alert-danger alert-dismissible fade show"
						style="display: none;">
						<p id="dangerAlert" class="mb-0">
							<strong>Wrong!</strong> Something Wrong...
						</p>
					</div>

					<div id="tableList"></div>
					<div class="row mt-1">
						<div class="col-md-12">
							<div class="d-flex justify-content-between">
							<div class="w-25"> 
									<input id="accessoriesIndentListSearch" type="text" class="form-control-sm" placeholder="Search Here Anything....">
								</div>
								<div class="row">
									<div class="ml-auto pr-1">
										<button class="btn btn-primary btn-sm " id="btnAdd">
											<i class="fa fa-plus-circle"></i> Add
										</button>
									</div>
									<div class="pr-1">
										<button class="btn btn-success btn-sm" id="btnEdit"
											onclick="newEditAction()">
											<i class="fa fa-pencil-square"></i> Edit
										</button>
									</div>
									<div class="pr-1">
										<button class="btn btn-secondary btn-sm"
											onclick="refreshAction()">
											<i class="fa fa-refresh"></i> Refresh
										</button>
									</div>

								</div>
								
							</div>
						</div>
					</div>


					<div class="row mt-1">
						<div style="overflow: auto;"
							class="col-sm-12 p-0 ">
							<table id="dataTable"
								class="table table-hover table-bordered table-sm mb-0 small-font">
								<thead>
									<tr>
										<th style="width: 15px;">Sl#</th>
										<th>Purchase Order</th>
										<th>Style no</th>
										<th>Item Name</th>
										<th>Color Name</th>
										<th>Shipping Marks</th>
										<th>Accssories Name</th>
										<th>Size</th>
										<th>Total Required</th>
										<th><i class="fa fa-edit"></i></th>
										<th><i class="fa fa-trash"></i></th>
									</tr>
								</thead>
								<tbody id="dataList">


								</tbody>
							</table>
						</div>
					</div>
					<div class="row mt-1">
						<div class="col-sm-12">
							<div class="d-flex justify-content-end">
								<div class="row">
									<div class="pr-1">
										<button class="btn btn-primary btn-sm"
											onclick="confirmAction()" accesskey="C">
											<i class="fas fa-save"></i><span style="text-decoration:underline;"> Confirm</span> 
										</button>
									</div>
									<div class="pr-1">
										<button class="btn btn-secondary btn-sm">
											<i class="fa fa-refresh"></i> Refresh
										</button>
									</div>
									<div class="pr-1">
										<button class="btn btn-info btn-sm" onclick="printAccessories()">
											<i class="fas fa-print"></i> Preview
										</button>
									</div>

								</div>
							</div>
						</div>
					</div>
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
						placeholder="Search Accessories Indent"
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
							<th>Indent No</th>
							<th>Purchase Order</th>
							<th>Indent Date</th>						
							<th><span><i class="fa fa-search"></i></span></th>
							<th><span><i class="fa fa-print"></i></span></th>
						</tr>
					</thead>
					<tbody id="poList">
						<c:forEach items="${listAccPostedData}" var="list"
							varStatus="counter">
							<tr>
								<td>${counter.count}</td>
								<td>${list.aiNo}</td>
								<td>${list.purchaseOrder}</td>
								<td>${list.indentDate}</td>
								<td><i class="fa fa-search" style="cursor:pointer;"
									onclick="searchAccessoriesIndent('${list.aiNo}')"> </i></td>
									<td><i class="fa fa-print" style="cursor:pointer;"
									onclick="printAccessoriesIndent('${list.aiNo}')"> </i></td>
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
	src="https://cdn.datatables.net/1.10.23/js/jquery.dataTables.min.js"></script>
	<script
	src="https://cdn.datatables.net/1.10.23/js/dataTables.bootstrap4.min.js"></script>
<script
	src="${pageContext.request.contextPath}/assets/js/order/accessories_indent.js"></script>