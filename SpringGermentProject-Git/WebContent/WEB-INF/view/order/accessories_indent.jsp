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


<input type="hidden" id="user_hidden" value="<%=lg.get(0).getId()%>">
<input type="hidden" id="accIndentId" value="0">

<div class="page-wrapper">
	<div class="m-2">
		<div class="row">
			<div class="col-md-12">
				<div class="card-box">
					<div class="d-flex justify-content-end">
						<div class="mr-auto">
							<h4 style="text-align: left;" class="font-weight-bold">
								Accessories Indent <span class="badge badge-primary">New</span>
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
										Order</label>
									<div class="form-check-inline">
										<label class="form-check-label"> <input
											id="checkPurchaseOrder" type="checkbox"
											class="form-check-input" checked="checked"> Combined
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
												<option name="purchaseOrder" id='purchaseOrder'
													value="${acc.name}">${acc.name}</option>
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
										No</label>
									<div class="form-check-inline">
										<label class="form-check-label"> <input
											id="checkStyleNo" type="checkbox" class="form-check-input"
											checked="checked"> Combined
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
										Name</label>
									<div class="form-check-inline">
										<label class="form-check-label"> <input
											id="checkItemName" type="checkbox" class="form-check-input"
											checked="checked"> Combined
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
											id="checkColor" type="checkbox" class="form-check-input"
											checked="checked"> Combined
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
													class="form-check-input" checked="checked">Combined
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
									</div>
								</div>
							</div>

						</div>
						<div class="col-md-4">
							<div class="form-group  mb-1"
								style="padding-left: 1px; padding-right: 1px;">
								<div class="d-flex justify-content-between">
									<label for="accessoriesItem"
										class="col-form-label-sm mb-0 py-0">Accessories Item</label>
								</div>
								<div class="row">
									<div class="col-md-12 input-group-append">
										<select id="accessoriesItem" class="selectpicker w-100"
											multiple data-selected-text-format="count > 4"
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
											<label for="unit" class="col-form-label-sm mb-0 py-0">Unit</label>
										</div>
										<div class="row">
											<div class="col-md-12">
												<select id="unit" class="selectpicker form-control"
													data-live-search="true"
													data-style="btn-light btn-sm border-light-gray">

													<c:forEach items="${unit}" var="unit" varStatus="counter">
														<option value="${unit.id}">${unit.name}</option>
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
											<label for="grandQty" class="col-form-label-sm mb-0 py-0"><strong>Grand
													Qty</strong></label>
										</div>
										<input type="number" class='form-control-sm' id="grandQty"
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
										<input type="number" class='form-control-sm' id="reqPerPcs">
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
										<input type="number" class='form-control-sm' id="divideBy">
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
										<input type="number" class='form-control-sm' id="inPercent">
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
										<input type="number" class='form-control-sm' id="totalQty"
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
					
						<div id="tableList">
						</div>
					
					<div class="row mt-1">

						<div class="col-sm-4">
							<div class="row">
								<div class="col-sm-5 p-0">
									<label class="form-label">Indent No</label>
								</div>
								<div class="col-sm-7 p-0">
									<input id="aiNo" type="text" class="form-control-sm"></input>
								</div>
							</div>
						</div>

						<div class="col-sm-4">
							<div class="row">
								<div class="col-sm-5 p-0">
									<label class="form-label ml-1">Purchase Order</label>
								</div>
								<div class="col-sm-7 p-0">

									<select name="purchaseOrder" id="purchaseOrder"
										class="selectpicker form-control" data-live-search="true"
										data-style="btn-light border-secondary form-control-sm"
										onchange="poWiseStyles()">
										<option name="purchaseOrder" id="purchaseOrder" value="0">Select
											Purchase Order</option>

										<c:forEach items="${purchaseorders}" var="acc"
											varStatus="counter">
											<option name="purchaseOrder" id='purchaseOrder'
												value="${acc.id}">${acc.name}</option>
										</c:forEach>
									</select>
								</div>


							</div>
						</div>

						<div class="col-sm-4">
							<div class="row">
								<div class="col-sm-5 p-0">
									<label class="form-label ml-1">Style No</label>
								</div>
								<div class="col-sm-7 p-0">
									<select id="styleNo2" class="selectpicker form-control"
										data-live-search="true"
										data-style="btn-light border-secondary form-control-sm"
										onchange="styleWiseItems()">
									</select>
								</div>


							</div>
						</div>

					</div>


					<div class="row mt-1">

						<div class="col-sm-4">
							<div class="row">
								<div class="col-sm-5 p-0">
									<label class="form-label">Item Name</label>
								</div>
								<div class="col-sm-7 p-0">
									<select id="itemName" class="selectpicker form-control"
										data-live-search="true"
										data-style="btn-light border-secondary form-control-sm"
										onchange="styleItemsWiseColor()">
									</select>
								</div>


							</div>
						</div>

						<div class="col-sm-4">
							<div class="row">
								<div class="col-sm-5 p-0">
									<div style="width: 108px;" class="form-check form-check-inline">
										<input class="form-check-input ml-1" type="checkbox"
											id="shippingCheck" value="option1" onclick="shipping()">
										<label class="form-check-label" for="inlineCheckbox1">Shipping</label>
									</div>
								</div>
								<div class="col-sm-7 p-0">
									<select id="shippingmark" class="selectpicker form-control"
										data-live-search="true"
										data-style="btn-light border-secondary form-control-sm">
									</select>
								</div>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="row">
								<div class="col-sm-5 p-0">
									<label style="width: 120px;" class="form-label ml-1">Color</label>
								</div>
								<div class="col-sm-7 p-0">


									<select id="colorName" class="selectpicker form-control"
										data-live-search="true"
										data-style="btn-light border-secondary form-control-sm"
										onchange="sizeReqCheck()">
										<option id="colorName" value="0">Select Color</option>

										<c:forEach items="${colors}" var="color" varStatus="counter">
											<option id='colorName' value="${color.id}">${color.name}</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>
					</div>


					<div class="row mt-1">

						<div class="col-sm-4">

							<div class="row">
								<div class="col-sm-5 p-0">
									<label style="width: 150px;" class="form-label">Accessories
										Item</label>
								</div>
								<div class="col-sm-7 p-0">
									<select id="accessoriesItem" class="selectpicker form-control"
										data-live-search="true"
										data-style="btn-light border-secondary form-control-sm">
										<option id="accessoriesItem" value="0">Select Item</option>
										<c:forEach items="${accessories}" var="acc"
											varStatus="counter">
											<option id='accessoriesItem' value="${acc.id}">${acc.name}</option>
										</c:forEach>
									</select>
								</div>

							</div>

						</div>

						<div class="col-sm-4">

							<div class="row">
								<div class="col-sm-5 p-0">
									<div class="form-check form-check-inline">
										<input class="form-check-input ml-1" type="checkbox"
											id="sameAsCheck" value="option1"> <label
											class="form-check-label" for="inlineCheckbox1">Same
											As</label>
									</div>
								</div>
								<div class="col-sm-7 p-0">
									<select id="sameAsAccessories"
										class="selectpicker form-control" data-live-search="true"
										data-style="btn-light border-secondary form-control-sm">
										<option id="sameAsAccessories" value="0">Select Item</option>
										<c:forEach items="${accessories}" var="acc"
											varStatus="counter">
											<option id='sameAsAccessories' value="${acc.id}">${acc.name}</option>
										</c:forEach>
									</select>
								</div>

							</div>

						</div>



						<div class="col-sm-4 pl-0 pr-1">
							<div class="row">
								<div class="col-sm-5"></div>
								<div class="col-sm-7">
									<button height: 30px;" onclick="btnInstallEvent()"
										class="btn btn-info btn-block btn-sm">Install From</button>
								</div>
							</div>
						</div>

					</div>


					<div class="row mt-1">

						<!-- <div class="col-sm-4">
							<div class="row">
								<div class="col-sm-5 p-0">
									<label class="form-label">Accessories Size</label>
								</div>
								<div class="col-sm-7 p-0">
									<input id="accessoriesSize" type="text" class="form-control-sm"></input>
								</div>
							</div>
						</div> -->

						<div class="col-sm-4">
							<div class="row">
								<div class="col-sm-5 p-0">
									<div class="form-check form-check-inline">
										<input class="form-check-input input-sm ml-1"
											onclick="sizeReqCheck()" type="checkbox" id="sizeReqCheck"
											value="option1"> <label class="form-check-label"
											for="inlineCheckbox1">Size Req. Size</label>
									</div>
								</div>

								<div class="col-sm-7 p-0">
									<select style="margin-left: 1px;" id="size"
										class="selectpicker form-control" data-live-search="true"
										data-style="btn-light border-secondary form-control-sm"
										onchange="sizeWiseOrderQty()">

									</select>
								</div>
							</div>
						</div>

						<!-- <div class="col-sm-4">
							<div class="row">
								
							</div>
						</div> -->
					</div>



					<div class="row mt-1">

						<div class="col-sm-4">
							<div class="row">
								<div class="col-sm-5 p-0">
									<label class="form-label">Order Qty</label>
								</div>
								<div class="col-sm-7 p-0">
									<input id="orderQty" readonly type="text"
										class="form-control-sm"></input>
								</div>
							</div>
						</div>

						<div class="col-sm-3">
							<div class="row">
								<div class="col-sm-6 p-0">
									<label type="text" class="form-label ml-1">Req. Per Pcs</label>
								</div>
								<div class="col-sm-6 p-0">
									<input id="reqPerPcs" type="text" class="form-control-sm"></input>
								</div>
							</div>
						</div>

						<div class="col-sm-2">
							<div class="row">
								<div class="col-sm-5 p-0">
									<label class="form-label ml-1">Per Unit</label>
								</div>
								<div class="col-sm-7 p-0">
									<input id="perUnit" type="text" class="form-control-sm"></input>
								</div>
							</div>
						</div>

						<div class="col-sm-3">
							<div class="row">
								<div class="col-sm-5 p-0">
									<label class="form-label ml-1">Total Qty</label>
								</div>
								<div class="col-sm-7 p-0">
									<input id="totalQty" readonly type="text"
										class="form-control-sm"></input>
								</div>
							</div>
						</div>

					</div>

					<div class="row mt-1">

						<div class="col-sm-4">
							<div class="row">
								<div class="col-sm-5 p-0">
									<label class="form-label">Qty In Dozen</label>
								</div>
								<div class="col-sm-7 p-0">
									<input id="qtyInDozen" readonly type="text"
										class="form-control-sm"></input>
								</div>
							</div>
						</div>

						<div class="col-sm-3">
							<div class="row">
								<div class="col-sm-6 p-0">
									<label style="width: 120px;" class="form-label ml-1">Req.
										Per Dozen</label>
								</div>
								<div class="col-sm-6 p-0">
									<input id="reqPerDozen" type="text" class="form-control-sm"></input>
								</div>
							</div>
						</div>

						<div class="col-sm-2">
							<div class="row">
								<div class="col-sm-5 p-0">
									<label style="width: 110px;" class="form-label ml-1">Total
										Box</label>
								</div>
								<div class="col-sm-7 p-0">
									<input id="totalBox" readonly type="text"
										class="form-control-sm"></input>
								</div>
							</div>
						</div>

						<div class="col-sm-3">
							<div class="row">
								<div class="col-sm-5 p-0">
									<label class="form-label ml-1">Grand Qty</label>
								</div>
								<div class="col-sm-7 p-0">
									<input id="grandQty" readonly type="text"
										class="form-control-sm"></input>
								</div>
							</div>
						</div>

					</div>


					<div class="row mt-1">

						<div class="col-sm-4">
							<div class="row">
								<div class="col-sm-5 p-0">
									<label class="form-label">Divided By</label>
								</div>
								<div class="col-sm-7 p-0">
									<input id="dividedBy" onkeyup="dividedBy()" type="text"
										class="form-control-sm"></input>
								</div>
							</div>
						</div>

						<div class="col-sm-3">
							<div class="row">
								<div class="col-sm-6 p-0">
									<label class="form-label ml-1">Extra In %</label>
								</div>
								<div class="col-sm-6 p-0">
									<input id="extraIn" onkeyup="percentageQty()" type="text"
										class="form-control-sm"></input>
								</div>
							</div>
						</div>

						<div class="col-sm-2">
							<div class="row">
								<div class="col-sm-5 p-0">
									<label class="form-label ml-1">%Qty</label>
								</div>
								<div class="col-sm-7 p-0">
									<input id="percentQty" readonly type="text"
										class="form-control-sm"></input>
								</div>
							</div>
						</div>

						<div class="col-sm-3">
							<div class="row">
								<div class="col-sm-5 p-0">
									<label style="width: 120px;" class="form-label ml-1">Color</label>
								</div>
								<div class="col-sm-7 p-0">
									<select id="color" class="selectpicker form-control"
										data-live-search="true"
										data-style="btn-light border-secondary form-control-sm">
										<option id="color" value="0">Select Color</option>

										<c:forEach items="${color}" var="acc" varStatus="counter">
											<option id='color' value="${acc.id}">${acc.name}</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>

					</div>


					<div class="row mt-1">

						<div class="col-sm-4">
							<div class="row">
								<div class="col-sm-5 p-0">
									<label class="form-label">Unit</label>
								</div>
								<div class="col-sm-7 p-0">
									<select id="unit" class="selectpicker form-control"
										data-live-search="true"
										data-style="btn-light border-secondary form-control-sm">

										<c:forEach items="${unit}" var="acc" varStatus="counter">
											<option value="${acc.id}">${acc.name}</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>

						<div class="col-sm-3">
							<div class="row">
								<div class="col-sm-6 p-0">
									<label class="form-label ml-1">Brand</label>
								</div>
								<div class="col-sm-6 p-0">
									<select id="brand" class="selectpicker form-control"
										data-live-search="true"
										data-style="btn-light border-secondary form-control-sm">
										<option id="brand" value="0">Select Brand</option>

										<c:forEach items="${brand}" var="acc" varStatus="counter">
											<option id='brand' value="${acc.id}">${acc.name}</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>

						<div class="col-sm-5">
							<div class="d-flex justify-content-end">
								<div class="row">
									<div class="ml-auto pr-1">
										<button class="btn btn-primary btn-sm " id="btnSave"
											onclick="saveEvent()">Save</button>
									</div>
									<div class="pr-1">
										<button class="btn btn-primary btn-sm" id="btnEdit"
											onclick="editEvent()">Edit</button>
									</div>
									<div class="pr-1">
										<button class="btn btn-primary btn-sm">Refresh</button>
									</div>
									<div class="pr-1">
										<button class="btn btn-primary btn-sm">Preview</button>
									</div>
									<div class="pr-1">
										<button class="btn btn-primary btn-sm"
											onclick="confrimEvent()">Confirm</button>
									</div>
								</div>
							</div>
						</div>
					</div>

					<div class="row mt-1">
						<div style="overflow: auto; max-height: 300px;"
							class="col-sm-12 p-0">
							<table class="table table-bordered">
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
										<th>Edit</th>
									</tr>
								</thead>
								<tbody id="dataList">

									<c:forEach items="${listAccPending}" var="listItem"
										varStatus="counter">
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
											<td><i class="fa fa-edit"
												onclick="accessoriesItemSet(${listItem.autoid})"> </i></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
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
							<th>Style No</th>
							<th>Item Name</th>
							<th><span><i class="fa fa-search"></i></span></th>
						</tr>
					</thead>
					<tbody id="poList">
						<c:forEach items="${listAccPostedData}" var="list"
							varStatus="counter">
							<tr>
								<td>${counter.count}</td>
								<td>${list.aiNo}</td>
								<td>${list.purchaseOrder}</td>
								<td>${list.styleNo}</td>
								<td>${list.itemname}</td>
								<td><i class="fa fa-search"
									onclick="searchAccessoriesIndent(${list.aiNo})"> </i></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>

		</div>
	</div>
</div>
<%-- <script>
				$('.bsdatepicker').datepicker({

				});
			</script> --%>
<jsp:include page="../include/footer.jsp" />

<script
	src="${pageContext.request.contextPath}/assets/js/order/accessories_indent.js"></script>