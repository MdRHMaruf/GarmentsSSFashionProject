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
			<h5 class="text-center" style="display: inline;">File Upload & Download<span class="badge badge-primary" id='buyerPOIdTitle'>New</span></h5>
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
								data-live-search="true"
								data-style="btn-light btn-sm border-light-gray" onchange="buyerStyleWisePurchaseOrder()">
								<option value="0" >Select Style</option>
							</select>
						</div>

					</div>
					

				</div>

			</div>

		</div>
		
		
				<div class="row">
			<div class="col-md-9 mb-1 pr-1">
				<div class="row">
					<div class="form-group col-md-4 mb-1" style="padding-right: 1px;">
						<label for="p" class="col-form-label-sm mb-0 pb-0">Purchase Order<span style="color: red">*</span>
						</label>
						<div class="row">
							<select id="purchaseOrder" class="selectpicker col-md-12"
								data-live-search="true"
								data-style="btn-light btn-sm border-light-gray">

							</select>
						</div>


					</div>
	
					
					<div class="form-group col-md-4 mb-1"
						style="padding-left: 15px; padding-right: 15px;">
						<label for="styleNo" class="col-form-label-sm mb-0 pb-0">Factory
							No<span style="color: red">*</span>
						</label>
						<div class="row">
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
		



		<div class="row mt-1">
			<div class="col-md-9">
				<div class="input-group input-group-sm mb-1">
					<div class="input-group-prepend">
						<span class="input-group-text" id="inputGroup-sizing-sm">Note</span>
					</div>
					<textarea id="note" class="form-control form-control-sm"
						aria-label="Sizing example input"
						aria-describedby="inputGroup-sizing-sm"></textarea>
				</div>
			</div>

		</div>
		<div class="row mt-1">
			<div class="col-md-9">
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
						</tr>
					</thead>
					<tbody id="fileList">

					</tbody>
				</table>
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
							<th>SL</th>
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
								<td>${counter.count}</td>
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
	src="${pageContext.request.contextPath}/assets/js/store/store_file_resource.js"></script>
