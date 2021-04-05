<%@page import="pg.share.PaymentType"%>
<%@page import="pg.share.Currency"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="pg.model.WareInfo"%>
<%@page import="pg.model.Module"%>
<%@page import="pg.model.Login"%>
<%@page import="java.util.List"%>


<%
	String userId = (String) session.getAttribute("userId");
	String userName = (String) session.getAttribute("userName");
%>

<jsp:include page="../include/header.jsp" />

<div class="page-wrapper">

	<input type="hidden" id="userId" value="<%=userId%>"> <input
		type="hidden" id="buyerPOId" value="0"> <input type="hidden"
		id="itemAutoId" value="0">

	<div class="card-box">
		<header class="d-flex justify-content-between">
			<h5 class="text-center" style="display: inline;">Buyer Purchase
				Order <span class="badge badge-primary" id='buyerPOIdTitle'>New</span></h5>
			<button type="button" class="btn btn-outline-dark btn-sm"
				data-toggle="modal" data-target="#exampleModal">
				<i class="fa fa-search"></i>
			</button>
		</header>
		<hr class="my-1">
		<div class="row">
			<div class="col-md-9 mb-1 pr-1">
				<div class="row">
					<div class="form-group col-md-4 mb-1" style="padding-right: 1px;">
						<label for="buyerName" class="col-form-label-sm mb-0 pb-0">Buyer
							Name<span style="color: red">*</span>
						</label>
						<div class="row">
							<select id="buyerName" class="selectpicker col-md-12"
								onchange="buyerWiseStyleLoad()" data-live-search="true"
								data-style="btn-light btn-sm border-light-gray">
								<option value="0">Select Buyer</option>
								<c:forEach items="${buyerList}" var="buyer">
									<option value="${buyer.buyerid}">${buyer.buyername}</option>
								</c:forEach>
							</select>
						</div>


					</div>
					<div class="form-group col-md-4 mb-1"
						style="padding-left: 1px; padding-right: 1px;">
						<label for="styleNo" class="col-form-label-sm mb-0 pb-0">Style
							No<span style="color: red">*</span>
						</label>
						<div class="row">
							<select id="styleNo" class="selectpicker col-md-12"
								onchange="styleWiseItemLoad()" data-live-search="true"
								data-style="btn-light btn-sm border-light-gray">
								<option value="0">Select Style</option>
							</select>
						</div>

					</div>
					<div class="form-group col-md-4 mb-1" style="padding-left: 1px;">
						<label for="itemType" class="col-form-label-sm mb-0 pb-0">Item
							Type<span style="color: red">*</span>
						</label>
						<div class="row">
							<select id="itemType" class="selectpicker col-md-12"
								data-live-search="true"
								data-style="btn-light btn-sm border-light-gray">
								<option value="0">Select Item Type</option>
							</select>
						</div>

					</div>
				</div>
				<div class="row mt-1">
					<div class='col-md-4 px-1'>
						<div class="input-group input-group-sm mb-1">
							<div class="input-group-prepend">
								<span class="input-group-text" id="inputGroup-sizing-sm"><label
									class='my-0' for="factory">Factory<span
										style="color: red">*</span></label></span>
							</div>
							<select id="factory" class="form-control selectpicker"
								aria-label="Sizing example input"
								aria-describedby="inputGroup-sizing-sm" data-live-search="true"
								data-style="btn-light btn-sm border-light-gray form-control-sm">
								<option value="0">Select Factory</option>
								<c:forEach items="${factoryList}" var="factory">
									<option value="${factory.factoryid}">${factory.factoryname}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class='col-md-4 px-1'>
						<div class="input-group input-group-sm mb-1">
							<div class="input-group-prepend">
								<span class="input-group-text" id="inputGroup-sizing-sm"><label
									class='my-0' for="color">Color<span style="color: red">*</span></label></span>
							</div>
							<select id="color" class="form-control selectpicker"
								aria-label="Sizing example input"
								aria-describedby="inputGroup-sizing-sm" data-live-search="true"
								data-style="btn-light btn-sm border-light-gray form-control-sm">
								<option value="0">Select Color</option>
								<c:forEach items="${colorList}" var="color">
									<option value="${color.colorId}">${color.colorName}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<%-- <div class="col-md-7">
						<div class="form-group mb-0  row">
							<label for="factory" class="col-md-2 col-form-label-sm pr-0 pb-0">Factory</label>

							<select id="factory" class="selectpicker col-sm-10"
								data-live-search="true"
								data-style="btn-light btn-sm border-light-gray">

								<option value="0">Select Factory</option>
								<c:forEach items="${factoryList}" var="factory">
									<option value="${factory.factoryid}">${factory.factoryname}</option>
								</c:forEach>
							</select>

						</div>
					</div> --%>

					<%-- <div class="col-md-5">

						<div class="form-group mb-0 row">
							<label for="color" class="col-md-2 col-form-label-sm pr-0 pb-0">Color<span
								style="color: red">*</span></label> <select id="color"
								class="selectpicker col-md-10" data-live-search="true"
								data-style="btn-light btn-sm border-light-gray">

								<option value="0">Select Color</option>
								<c:forEach items="${colorList}" var="color">
									<option value="${color.colorId}">${color.colorName}</option>
								</c:forEach>
							</select>
						</div>
					</div> --%>
				</div>
				<div class="row mt-1">
					<div class="col-md-4 px-1">
						<div class="input-group input-group-sm mb-1">
							<div class="input-group-prepend">
								<span class="input-group-text" id="inputGroup-sizing-sm"><label
									class="my-0" for="customerOrder">Customer Order</label></span>
							</div>
							<input id="customerOrder" type="text" class="form-control"
								aria-label="Sizing example input"
								aria-describedby="inputGroup-sizing-sm">
						</div>
					</div>
					<div class="col-md-4 px-1">
						<div class="input-group input-group-sm mb-1">
							<div class="input-group-prepend">
								<span class="input-group-text" id="inputGroup-sizing-sm"><label
									class="my-0" for="purchaseOrder">Purchase Order<span
										style="color: red">*</span></label></span>
							</div>
							<input id="purchaseOrder" type="text" class="form-control"
								aria-label="Sizing example input"
								aria-describedby="inputGroup-sizing-sm">
						</div>
					</div>
				</div>
				<%-- <div class="row">
					<div class="col-md-6">
						<div class="form-group mb-0  row">
							<label for="customerOrder "
								class="col-md-4 col-form-label-sm pr-0 pb-0">Customer
								Order<span style="color: red">*</span>
							</label>
							<div class="col-sm-8">
								<input type="text" class="form-control-sm" id="customerOrder">
							</div>
						</div>
					</div>

					<div class="col-md-6">

						<div class="form-group mb-0 row">
							<label for="purchaseOrder"
								class="col-md-4 col-form-label-sm pr-0 pb-0">Purchase
								Order<span style="color: red">*</span>
							</label>
							<div class="col-md-8">
								<input type="text" class="form-control-sm" id="purchaseOrder">
							</div>
						</div>


					</div>
				</div> --%>
				<div class="row mt-1">
					<div class="col-md-4 px-1">
						<div class="input-group input-group-sm mb-1">
							<div class="input-group-prepend">
								<span class="input-group-text" id="inputGroup-sizing-sm"><label
									class="my-0" for="shippingMark">Shipping Mark</label></span>
							</div>
							<input id="shippingMark" type="text" class="form-control"
								aria-label="Sizing example input"
								aria-describedby="inputGroup-sizing-sm">
						</div>
					</div>
					<div class="col-md-4 px-1">
						<div class="input-group input-group-sm mb-1">
							<div class="input-group-prepend">
								<span class="input-group-text" id="inputGroup-sizing-sm"><label
									class="my-0" for="shipmentDate">Shipment Date<span
										style="color: red">*</span></label></span>
							</div>
							<input id="shipmentDate" type="date" class="form-control" placeholder="dd/mm/yyyy" data-date-format="dd/mm/yyyy"
								aria-label="Sizing example input"
								aria-describedby="inputGroup-sizing-sm">
						</div>
					</div>
					
					<div class="col-md-4 px-1">
						<div class="input-group input-group-sm mb-1">
							<div class="input-group-prepend">
								<span class="input-group-text" id="inputGroup-sizing-sm"><label
									class="my-0" for="inspectionDate">Inspection Date<span
										style="color: red">*</span></label></span>
							</div>
							<input id="inspectionDate" type="date" class="form-control"
								aria-label="Sizing example input"
								aria-describedby="inputGroup-sizing-sm">
						</div>
					</div>
				</div>


				<div class="row ">
					<div class="col-md-8">
						<div class="row">
							<div class="col-md-5">
								<div class="form-group mb-0  row">
									<label for="paymentTerm"
										class="col-md-6 col-form-label-sm pr-0">Payment Term</label> <select
										id="paymentTerm" class="form-control-sm col-md-5">
										<%
											for (PaymentType pType : PaymentType.values()) {
										%>
										<option value="<%=pType.name()%>"><%=pType.name()%></option>
										<%
											}
										%>
									</select>

								</div>
							</div>

							<div class="col-md-5">
								<div class="form-group mb-0  row">
									<label for="currency" class="col-md-4 col-form-label-sm pr-0">Currency</label>
									<select id="currency" class="form-control-sm col-md-6">
										<option value="0">Select Currency</option>
										<%
											for (Currency currency : Currency.values()) {
										%>
										<option value="<%=currency.name()%>"><%=currency.name()%></option>
										<%
											}
										%>
									</select>

								</div>
							</div>
						</div>

					</div>
					<div class="col-md-4">
						<div class="row">
							<div class="col-md-12 d-flex justify-content-end">
								<button id="btnAdd" type="button" class="btn btn-primary btn-sm"
									onclick="itemSizeAdd()" accesskey="A">
									<i class="fa fa-plus-circle"></i><span style="text-decoration:underline;"> Add</span>
								</button>
								<button id="btnEdit" type="button"
									class="btn btn-success btn-sm ml-1" onclick="itemSizeEdit()"
									style="display: none;">
									<i class="fa fa-pencil-square"></i> Edit
								</button>
								<button id="btnReset" type="button"
									class="btn btn-secondary btn-sm ml-1" onclick="reset()">
									<i class="fa fa-refresh"></i> Reset
								</button>
							</div>
						</div>
					</div>

				</div>

			</div>
			<div class="col-md-3 mb-1 pl-1">
				<div class="row">
					<div class="col-md-12">
						<select id="sizeGroup" class="form-control-sm border-secondary"
							style="width: 100%;" onchange="sizeLoadByGroup()">
							<option value="0">Select Size Group</option>
							<c:forEach items="${groupList}" var="group" varStatus="counter">
								<option value="${group.groupId}">${group.groupName}</option>
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
		<div id="tableList"></div>



		<div class="row mt-1">
			<div class="col-md-6">
				<div class="input-group input-group-sm mb-1">
					<div class="input-group-prepend">
						<span class="input-group-text" id="inputGroup-sizing-sm">Note</span>
					</div>
					<textarea id="note" class="form-control form-control-sm"
						aria-label="Sizing example input"
						aria-describedby="inputGroup-sizing-sm"></textarea>
				</div>
			</div>
			<div class="col-md-6">
				<div class="input-group input-group-sm mb-1">
					<div class="input-group-prepend">
						<span class="input-group-text" id="inputGroup-sizing-sm">Remarks</span>
					</div>
					<textarea id="remarks" class="form-control form-control-sm"
						aria-label="Sizing example input"
						aria-describedby="inputGroup-sizing-sm"></textarea>
				</div>
			</div>
		</div>
		<div class="row mt-1">
			<div class="col-md-7">
				<div class="progress">
					<div id="bar" class="progress-bar" style="width: 0%"></div>
				</div>

				<div class="input-group mt-2">
					<div class="custom-file">
						<input type="file" id="files" multiple="">
					</div>
					<div class="input-group-append">
						<button class="btn btn-sm btn-primary" type="button"
							id="uploadButton" value="Upload" onclick="uploadNext()">Upload</button>
					</div>
				</div>
				<table class="table table-hover table-bordered table-sm mb-0">
					<thead>
						<tr>
							<th>File Name</th>
							<th>Upload By</th>
							<th><span><i class='fa fa-download'></i></span></th>
							<th><span><i class='fa fa-trash'></i></span></th>
							<th><span><i class='fa fa-edit'></i></span></th>
						</tr>
					</thead>
					<tbody id="fileList">

					</tbody>
				</table>
			</div>
			<div class="col-md-5 d-flex justify-content-end">
				<div>
				<button id="btnPOSubmit" type="button" accesskey="S"
					class="btn btn-primary btn-sm" onclick="submitAction()">
					<i class="fas fa-save"></i><span style="text-decoration:underline;"> Submit</span>
				</button>
				<button id="btnPOEdit" type="button"
					class="btn btn-success btn-sm ml-1" onclick="buyerPoEditAction()"
					style='display: none'>
					<i class="fa fa-pencil-square"></i> Edit PO
				</button>
				<button id="btnRefresh" type="button"
					class="btn btn-secondary btn-sm ml-1" onclick="refreshAction()">
					<i class="fa fa-refresh"></i> Refresh
				</button>
				
				<div class="btn-group ml-1" role="group" id="btnPreview"
							aria-label="Button group with nested dropdown" style="display: none;">
							<button type="button" class="btn btn-sm btn-info" onclick="previewAction('primary')"><i class="fa fa-print"></i> Preview</button>
							<div class="btn-group" role="group">
								<button id="btnGroupDrop1" type="button"
									class="btn btn-sm btn-info dropdown-toggle"
									data-toggle="dropdown" aria-haspopup="true"
									aria-expanded="false"></button>
								<div class="dropdown-menu" aria-labelledby="btnGroupDrop1">
									<a class="dropdown-item" onclick="previewAction('withPcs')" href="#">With Pcs</a>
									<a class="dropdown-item" onclick="previewAction('withOutPcs')" href="#">Without Pcs</a>
									<a class="dropdown-item" onclick="previewAction()" href="#">Zipper Preview</a> <a
										class="dropdown-item" onclick="previewAction('general')" href="#">General Preview</a>
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
	<div class="modal-dialog modal-xl">
		<div class="modal-content">
			<div class="modal-header">
				<div class="input-group">
					<input id="search" type="text" class="form-control"
						placeholder="Search Buyer Purchase Order"
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
							<th>Order Id</th>
							<th>Buyer Name</th>
							<th>Purchase Order</th>
							<th>Style No</th>
							<th>Date</th>
							<th><span><i class="fa fa-search"></i></span></th>
							<th><span><i class="fa fa-print"></i></span></th>
							<th><span><i class="fa fa-download"></i></span></th>
						</tr>
					</thead>
					<tbody id="poList">
						<c:forEach items="${buyerPoList}" var="po" varStatus="counter">
							<tr>
								<td id="id-${counter.count}" data-po="${po.buyerPoId}">${po.buyerPoId}</td>
								<td id='buyerName${po.buyerPoId}'>${po.buyerName}</td>
								<td>${po.purchaseOrder}</td>
								<td>${po.styleNo}</td>
								<td>${po.date}</td>
								<td><i class="fa fa-search"
									onclick="searchBuyerPO(${po.buyerPoId})"
									style='cursor: pointer;'> </i></td>
								<td><i class="fa fa-print"
									onclick="printBuyerPO(${po.buyerPoId})"
									style='cursor: pointer;'> </i></td>
									<td><i class="fa fa-download" onclick="multidownload(this)" style='cursor: pointer;'></i></td>
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
	src="${pageContext.request.contextPath}/assets/js/order/buyer-purchase-order.js"></script>
