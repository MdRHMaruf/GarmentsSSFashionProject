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
	<div class="content container-fluid">
		<input type="hidden" id="fileId">
		<div class="row">
			<div class="col-md-12">
				<div class="card-box">
					<input type="hidden" id="userId" value="<%=userId%>">
					<div class="row">
						<div class="col-sm-3">
							<div class="row">
								<label class="form-label">Purpose</label>
								<div class="col-sm-9">
									<input class="form-control-sm" type="text" id="purpose">
								</div>
							</div>
						</div>
						<div class="col-sm-3">
							<div class="row">
								<label class="form-label">Form Date</label>
								<div class="col-sm-9">
									<input class="form-control-sm col-md-12" type="date"
										id="formDate">
								</div>
							</div>
						</div>
						<div class="col-sm-3">
							<div class="row">
								<label class="form-label">End Date</label>
								<div class="col-sm-9">
									<input class="form-control-sm col-md-12" type="date"
										id="endDate">
								</div>
							</div>
						</div>
						<div class="col-sm-3">
							<button class="btn btn-primary btn-sm" id="find">Find</button>

							<button onclick="getFileDownload('attachment.png')"
								class="btn btn-secondary btn-sm">Preview</button>
						</div>
					</div>

					<div class="row mt-1">

						<div class="col-sm-4">
							<div class="row">
								<label for="BuyerName" class="col-md-4">Buyer Name</label>
								<div class="col-sm-8">
									<select id="buyerName" class="selectpicker form-control"
										onchange="buyerWisePoLoad()" data-live-search="true"
										data-style="btn-light btn-sm border-light-gray">
										<option value="0">Select Buyer</option>
										<c:forEach items="${buyer}" var="buyer">
											<option id="buyerName" value="${buyer.buyerid}">${buyer.buyername}</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="row">
								<label for="purchaseOrder" class="col-md-4">Purchase
									Order</label>
								<div class="col-md-8">
									<select id="purchaseOrder" class="selectpicker form-control"
										onchange="poWiseStyles()" data-live-search="true"
										data-style="btn-light btn-sm border-light-gray">
										<option id="purchaseOrder" value="0">Select Purchase
											Order</option>
									</select>
								</div>
							</div>
						</div>
					</div>

					<div class="row mt-1">

						<div class="col-sm-4">
							<div class="row">
								<label for="dept" class="col-md-4">Department</label>
								<div class="col-sm-8">
									<select id="dept" class="selectpicker form-control"
										onchange="departmentWiseReceiver()" data-live-search="true"
										data-style="btn-light btn-sm border-light-gray">
										<option id="dept" value="0">Select Department</option>
										<c:forEach items="${dept}" var="dept" varStatus="counter">
											<option id='dept' value="${dept.departmentId}">${dept.departmentName}</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="row">
								<label for="User" class="col-md-4">Receiver</label>
								<div class="col-sm-8">
									<select id="receiver" name="receive"
										class="selectpicker form-control" data-live-search="true"
										data-style="btn-light btn-sm border-secondary form-control-sm"
										multiple title='Choose User' data-size="5"
										data-selected-text-format="count>2" data-actions-box="true"
										multiple>
										<option value="0">Select User</option>
									</select>
								</div>
							</div>
						</div>
						<div class="col-sm-4">
							<button class="btn btn-sm btn-danger" type="button" id="btnAdd"
								value="Upload" onclick="addNew()">Add New</button>
						</div>
					</div>

					<div class="row mt-1">
						<table class="table table-responsive table-sm table-stripped"
							style="height: 200px; overflow: scroll;">
							<thead>
								<tr>
									<th style="width: 60px" class="text-center">File ID#.</th>
									<th style="width: 20%" class="text-center">File Name</th>
									<th style="width: 15%" class="text-center">Uploaded by</th>
									<th style="width: 150px" class="text-center">Uploaded
										Machine</th>
									<th style="width: 150px" class="text-center">Date/Time</th>
									<th style="width: 20%" class="text-center">Purpose</th>
									<th style="width: 150px" class="text-center">Download by</th>
									<th style="width: 150px" class="text-center">Download
										Machine</th>
									<th style="width: 150px" class="text-center">Date/Time</th>
									<th style="width: 150px" class="text-center">Download</th>
									<th style="width: 150px" class="text-center">Del</th>
									<th style="width: 150px" class="text-center">Edit</th>
								</tr>

							</thead>
							<tbody id="filetable">

							</tbody>
						</table>
					</div>

					<div class="row mt-1">
						<div style="width: 55%">
							<div class="progress">
								<div id='bar' class="progress-bar" style="width: 0%"></div>
							</div>

							<div class="input-group mt-2">
								<div class="custom-file">
									<input type="file" id="files" multiple onchange="onFileSelect()">
								</div>
								<div class="input-group-append">
									<button class="btn btn-sm btn-primary" type="button"
										id="uploadButton" value="Upload">Upload</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<!-- Modal Create by Arman -->
<div class="modal fade" id="largeModal" tabindex="-1" role="dialog"
	aria-labelledby="largeModal" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="table-responsive">
						<table class="table table-sm table-bordered"
							style="height: 200px; overflow: scroll;">
							<thead>
								<tr>
									<th class="text-center">Dept.</th>
									<th class="text-center">Code</th>
									<th class="text-center">Edit</th>
								</tr>
							</thead>
							<tbody id="accessTable">

							</tbody>
						</table>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm btn-dark"
					data-dismiss="modal">Close</button>
			</div>
		</div>
	</div>
</div>

<jsp:include page="../include/footer.jsp" />
<script>
	function getFileDownload(file_name) {
		window.location.href = '${pageContext.request.contextPath}/assets/images/'
				+ file_name;
	}
</script>
<script>
	$('.bsdatepicker').datepicker({

	});
</script>
<script
	src="${pageContext.request.contextPath}/assets/js/custom/link.js"></script>

<script
	src="${pageContext.request.contextPath}/assets/js/order/fileUpload.js"></script>

