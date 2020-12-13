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

<body onload="AINO()"></body>

		<input type="hidden" id="user_hidden" value="<%=lg.get(0).getId()%>">
		<input type="hidden" id="accIndentId" value="0">

<div class="page-wrapper">
	<div class="m-2">
		<div class="row">
			<div class="col-md-12">
				<div class="card-box">
					<div class="d-flex justify-content-end">
						<div class="mr-auto">
							<h4 style="text-align: left;" class="font-weight-bold">Accessorics
								Indent</h4>
						</div>

						<div class="p-0">
						<button type="button" class="btn btn-outline-dark btn-sm"
							data-toggle="modal" data-target="#exampleModal" title="Search">
							Accessories Indent List<i class="fa fa-search"></i>
						</button>
						</div>


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

									<select name="purchaseOrder" id="purchaseOrder" class="selectpicker form-control"
										data-live-search="true"
										data-style="btn-light border-secondary form-control-sm"
										onchange="poWiseStyles()">
										<option name="purchaseOrder" id="purchaseOrder" value="0">Select Purchase
											Order</option>

										<c:forEach items="${purchaseorders}" var="acc"
											varStatus="counter">
											<option name="purchaseOrder" id='purchaseOrder' value="${acc.id}">${acc.name}</option>
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
									<select id="styleNo" class="selectpicker form-control"
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
										data-style="btn-light border-secondary form-control-sm" onchange="styleItemsWiseColor()">
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
										data-style="btn-light border-secondary form-control-sm" onchange="sizeReqCheck()" >
										<option id="colorName" value="0">Select Color</option>

										<c:forEach items="${colors}" var="acc" varStatus="counter">
											<option id='colorName' value="${acc.id}">${acc.name}</option>
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
									Item</label></div>
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
										class="form-check-label" for="inlineCheckbox1">Same As</label>
								</div>
								</div>
								<div class="col-sm-7 p-0">
									<select id="sameAsAccessories" class="selectpicker form-control"
										data-live-search="true"
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
									<button height: 30px;" onclick="btnInstallEvent()" class="btn btn-info btn-block btn-sm">Install From</button>
								</div>
							</div>
						</div>

					</div>


					<div class="row mt-1">

						<div class="col-sm-4">
							<div class="row">
							<div class="col-sm-5 p-0">
								<label class="form-label">Accessories
									Size</label></div>
								<div class="col-sm-7 p-0">
									<input id="accessoriesSize" type="text" class="form-control-sm"></input>
								</div>
							</div>
						</div>

						<div class="col-sm-4">
							<div class="row">
								<div class="col-sm-5 p-0">
									<div class="form-check form-check-inline">
										<input class="form-check-input input-sm ml-1" onclick="sizeReqCheck()" type="checkbox"
											id="sizeReqCheck" value="option1"> <label
											class="form-check-label" for="inlineCheckbox1" >Size
											Req. Size</label>
									</div>
								</div>

								<div class="col-sm-7 p-0">
									<select style="margin-left: 1px;" id="size"
										class="selectpicker form-control" data-live-search="true"
										data-style="btn-light border-secondary form-control-sm" onchange="sizeWiseOrderQty()">

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
								<label class="form-label">Order
									Qty</label></div>
								<div class="col-sm-7 p-0">
									<input id="orderQty" readonly type="text" class="form-control-sm"></input>
								</div>
							</div>
						</div>

						<div class="col-sm-3">
							<div class="row">
							<div class="col-sm-6 p-0">
								<label type="text"  class="form-label ml-1">Req. Per
									Pcs</label></div>
								<div class="col-sm-6 p-0">
									<input id="reqPerPcs" type="text" class="form-control-sm"></input>
								</div>
							</div>
						</div>

						<div class="col-sm-2">
							<div class="row">
							<div class="col-sm-5 p-0">
								<label class="form-label ml-1">Per Unit</label></div>
								<div class="col-sm-7 p-0">
									<input id="perUnit" type="text" class="form-control-sm"></input>
								</div>
							</div>
						</div>

						<div class="col-sm-3">
							<div class="row">
							<div class="col-sm-5 p-0">
								<label class="form-label ml-1">Total
									Qty</label></div>
								<div class="col-sm-7 p-0">
									<input id="totalQty" readonly type="text" class="form-control-sm"></input>
								</div>
							</div>
						</div>

					</div>

					<div class="row mt-1">

						<div class="col-sm-4">
							<div class="row">
							<div class="col-sm-5 p-0">
								<label class="form-label">Qty In
									Dozen</label></div>
								<div class="col-sm-7 p-0">
									<input id="qtyInDozen" readonly type="text" class="form-control-sm"></input>
								</div>
							</div>
						</div>

						<div class="col-sm-3">
							<div class="row">
							<div class="col-sm-6 p-0">
								<label style="width: 120px;" class="form-label ml-1">Req. Per
									Dozen</label></div>
								<div class="col-sm-6 p-0">
									<input id="reqPerDozen" type="text" class="form-control-sm"></input>
								</div>
							</div>
						</div>

						<div class="col-sm-2">
							<div class="row">
							<div class="col-sm-5 p-0">
								<label style="width: 110px;" class="form-label ml-1">Total
									Box</label></div>
								<div class="col-sm-7 p-0">
									<input id="totalBox" readonly type="text" class="form-control-sm"></input>
								</div>
							</div>
						</div>

						<div class="col-sm-3">
							<div class="row">
							<div class="col-sm-5 p-0">
								<label class="form-label ml-1">Grand
									Qty</label></div>
								<div class="col-sm-7 p-0">
									<input id="grandQty" readonly type="text" class="form-control-sm"></input>
								</div>
							</div>
						</div>

					</div>


					<div class="row mt-1">

						<div class="col-sm-4">
							<div class="row">
							<div class="col-sm-5 p-0">
								<label class="form-label">Divided
									By</label></div>
								<div class="col-sm-7 p-0">
									<input id="dividedBy" onkeyup="dividedBy()" type="text" class="form-control-sm"></input>
								</div>
							</div>
						</div>

						<div class="col-sm-3">
							<div class="row">
							<div class="col-sm-6 p-0">
								<label class="form-label ml-1">Extra In
									%</label></div>
								<div class="col-sm-6 p-0">
									<input id="extraIn" onkeyup="percentageQty()" type="text" class="form-control-sm"></input>
								</div>
							</div>
						</div>

						<div class="col-sm-2">
							<div class="row">
							<div class="col-sm-5 p-0">
								<label class="form-label ml-1">%Qty</label></div>
								<div class="col-sm-7 p-0">
									<input id="percentQty" readonly type="text" class="form-control-sm"></input>
								</div>
							</div>
						</div>

						<div class="col-sm-3">
							<div class="row">
							<div class="col-sm-5 p-0">
								<label style="width: 120px;" class="form-label ml-1">Color</label></div>
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
								<label class="form-label">Unit</label></div>
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
								<label class="form-label ml-1">Brand</label></div>
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
										<button class="btn btn-primary btn-sm " id="btnSave" onclick="saveEvent()">Save</button>
									</div>
									<div class="pr-1">
										<button class="btn btn-primary btn-sm" id="btnEdit" onclick="editEvent()">Edit</button>
									</div>
									<div class="pr-1">
										<button class="btn btn-primary btn-sm">Refresh</button>
									</div>
									<div class="pr-1">
										<button class="btn btn-primary btn-sm">Preview</button>
									</div>
									<div class="pr-1">
										<button class="btn btn-primary btn-sm" onclick="confrimEvent()">Confirm</button>
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
								
									<c:forEach items="${listAccPending}" var="listItem" varStatus="counter">
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
											<td><i class="fa fa-edit"  onclick="accessoriesItemSet(${listItem.autoid})"> </i></td>
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
						<c:forEach items="${listAccPostedData}" var="list" varStatus="counter">
							<tr>
								<td>${counter.count}</td>
								<td >${list.aiNo}</td>
								<td >${list.purchaseOrder}</td>
								<td>${list.styleNo}</td>
								<td>${list.itemname}</td>
								<td><i class="fa fa-search"
									onclick="searchAccessoriesIndent(${list.aiNo})">
								</i></td>
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