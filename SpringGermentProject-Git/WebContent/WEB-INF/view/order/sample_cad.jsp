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
	<div class="container-fluid mt-2">
		<div class="alert alert-success alert-dismissible fade show"
			style="display: none;">
			<p id="successAlert" class="mb-0">
				<strong>Success!</strong> Save Successfully..
			</p>
		</div>
		<div class="alert alert-warning alert-dismissible fade show"
			style="display: none;">
			<p id="warningAlert" class="mb-0">
				<strong>Warning!</strong> Empty.Please Enter
			</p>
		</div>
		<div class="alert alert-danger alert-dismissible fade show"
			style="display: none;">
			<p id="dangerAlert" class="mb-0">
				<strong>Wrong!</strong> Something Wrong...
			</p>
		</div>
		<input type="hidden" id="userId" value="<%=userId%>"> <input
			type="hidden" id="buyerOrderId" value="0"> <input
			type="hidden" id="purchaseOrder" value="0"> <input
			type="hidden" id="styleId" value=""> <input type="hidden"
			id="itemId" value=""> <input type="hidden" id="colorId"
			value=""> <input type="hidden" id="POStatus" value="">
		<input type="hidden" id="sampleReqId" value="0"> <input
			type="hidden" id="sampleCommentId" value="0">

		<div class="row">
			<div class="col-sm-12 col-md-12 col-lg-12">
				<div class="card-box">

					<div class="row">

						<div class="col-md-12">

							<header class="d-flex justify-content-between">
								<h5 class="text-center" style="display: inline;">Sample CAD</h5>
								<div>
									<button type="button" class="btn btn-outline-dark btn-sm"
										data-toggle="modal" data-target="#exampleModal">
										<i class="fa fa-search"></i>Sample Requisition List
									</button>
									<button type="button" class="btn btn-outline-dark btn-sm"
										data-toggle="modal" data-target="#sampleCadModal">
										<i class="fa fa-search"></i>Sample Cad List
									</button>
								</div>
							</header>
							<hr class="my-1">
							<div class="row mt-1">

								<div class="col-sm-3 px-1">

									<h5>Pattern Making</h5>

									<div class="row">
										<label for="makeingDate" class="col-sm-2">Date</label>
										<div class="col-sm-10">
											<input class="form-control-sm col-sm-12"
												type="datetime-local" id="patternmakingdate">
										</div>
									</div>

									<div class="row mt-1">
										<label for="makeingdespatch" class="col-sm-2">Dispatch</label>
										<div class="col-sm-10">
											<select id="makeingDespatch"
												class="selectpicker form-control" data-live-search="false"
												data-style="btn-light btn-sm border-secondary form-control-sm">

												<option value='1'>NO</option>
												<option value='2'>Yes</option>

											</select>
										</div>
									</div>

									<div class="row mt-1">
										<label for="makeingReceivedBy" class="col-sm-2">Recevied
											By</label>
										<div class="col-sm-10">
											<input class="form-control-sm" type="text"
												id="patternmakingreceivedby">
										</div>
									</div>


								</div>

								<div class="col-sm-3 px-1">

									<h5>Pattern Correction</h5>

									<div class="row">
										<label for="correctionDate" class="col-sm-2">Date</label>
										<div class="col-sm-10">
											<input class="form-control-sm col-sm-12"
												type="datetime-local" id="patterncorrectiondate">
										</div>
									</div>

									<div class="row mt-1">
										<label for="correctionDate" class="col-sm-2">Dispatch</label>
										<div class="col-sm-10">
											<select id="patterncorrectiondispatch"
												class="selectpicker form-control" data-live-search="false"
												data-style="btn-light btn-sm border-secondary form-control-sm">
												<option value='1'>NO</option>
												<option value='2'>Yes</option>

											</select>
										</div>
									</div>

									<div class="row mt-1">
										<label for="correctionReceviedBy" class="col-sm-2">Recevied
											By</label>
										<div class="col-sm-10">
											<input class="form-control-sm" type="text"
												id="correctionReceviedBy">
										</div>
									</div>

								</div>

								<div class="col-sm-3 px-1">

									<h5>Pattern Grading</h5>

									<div class="row">
										<label for="gradingDate" class="col-sm-2">Date</label>
										<div class="col-sm-10">
											<input class="form-control-sm col-sm-12"
												type="datetime-local" id="gradingDate">
										</div>
									</div>

									<div class="row mt-1">
										<label for="gradingDespatch" class="col-sm-2">Despatch</label>
										<div class="col-sm-10">
											<select id="gradingDespatch"
												class="selectpicker form-control" data-live-search="true"
												data-style="btn-light btn-sm border-secondary form-control-sm">
												<option value='1'>No</option>
												<option value='2'>Yes</option>

											</select>
										</div>
									</div>

									<div class="row mt-1">
										<label for="gradingReceviedBy" class="col-sm-2">Recevied
											By</label>
										<div class="col-sm-10">
											<input class="form-control-sm" type="text"
												id="gradingdispatchreceivedby">
										</div>
									</div>

								</div>

								<div class="col-sm-3 px-1">

									<h5>Mini Marking</h5>

									<div class="row">
										<label for="markingDate" class="col-sm-2">Date</label>
										<div class="col-sm-10">
											<input class="form-control-sm col-sm-12"
												type="datetime-local" id="markingDate">
										</div>
									</div>

									<div class="row mt-1">
										<label for="markingDespatch" class="col-sm-2">Dispatch</label>
										<div class="col-sm-10">
											<select id="markingDespatch"
												class="selectpicker form-control" data-live-search="false"
												data-style="btn-light btn-sm border-secondary form-control-sm">
												<option value='1'>NO</option>
												<option value='2'>Yes</option>

											</select>
										</div>
									</div>

									<div class="row mt-1">
										<label for="markingReceviedBy" class="col-sm-2">Recevied
											By</label>
										<div class="col-sm-10">
											<input class="form-control-sm" type="text"
												id="markingReceviedBy">
										</div>
									</div>

								</div>

							</div>

							<div class="row mt-1">
								<div class="row">
									<label for="markingDate" class="col-sm-3">Sample Type</label> <select
										id="sampleId" class="selectpicker col-md-9"
										data-live-search="true"
										data-style="btn-light btn-sm border-light-gray">

										<option id="sampleId" value="0">Select Sample</option>
										<c:forEach items="${sampleList}" var="list">
											<option id="sampleId" value="${list.id}">${list.name}</option>
										</c:forEach>
									</select>

								</div>

								<div class="row mt-1">
									<label for="markingDate" class="col-sm-1">Feedback</label>
									<div class="col-sm-12">
										<textarea rows="2" class="form-control" id="feedback"></textarea>
									</div>

								</div>

								<div class="row mt-1">
									<label for="markingDate" style="width: 70px;">Buyer:</label> <input
										class="form-control" readonly style="width: 210px;"
										type="text" id="buyerName"> <label for="markingDate"
										style="width: 70px;">Purchase Order:</label> <input
										class="form-control" readonly style="width: 210px;"
										type="text" id="vPurchaseOrder">
								</div>

								<div class="row mt-1">

									<label for="markingDate" style="width: 70px;">Style No:</label>
									<input class="form-control" readonly style="width: 200px;"
										type="text" id="styleNo"> <label for="markingDate"
										style="width: 70px;">Item Description:</label> <input
										class="form-control" readonly style="width: 250px;"
										type="text" id="itemName">
								</div>
							</div>

							<div id="samplecadtableList"></div>
							<div class="row mt-1">
								<div class="col-md-6">
									<div class="progress">
										<div id="bar" class="progress-bar" style="width: 0%"></div>
									</div>

									<div class="input-group mt-2">
										<div class="custom-file">
											<input type="file" id="files" multiple="">
										</div>
										<div class="input-group-append">
											<button class="btn btn-sm btn-primary" type="button"
												id="uploadButton" value="Upload">Upload</button>
										</div>
									</div>
								</div>

								<div class="col-md-6">

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
							</div>
						</div>

					</div>

					<div class="row mt-1">
						<div class="col-sm-12 p-0">
							<button type="button" id="save" onclick="insertSample()" accesskey="S"
								class="btn btn-warning btn-sm" onclick=""><span style="text-decoration:underline;"> Save</span></button>
							<button type="button" id="edit" class="btn btn-warning btn-sm" accesskey="E"
								onclick="editSmapleCad()"><span style="text-decoration:underline;"> Edit</span></button>
							<button type="button" id="btnRefresh" class="btn btn-dark btn-sm"
								onclick="">Refresh</button>


						</div>
					</div>


					<!-- Modal -->
					<div class="modal fade" id="exampleModal" tabindex="-1"
						role="dialog" aria-labelledby="exampleModalLabel"
						aria-hidden="true">
						<div class="modal-dialog modal-lg">
			<div class="modal-content">
			
			
		
			<div class="modal-header">
				<h5 class="modal-title" id="exampleModalLabel">Sample Requisition List</h5>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			

			
				<div class="input-group">

					<input id="search" type="text" class="form-control"
						placeholder="Search Sample Requisition"
						aria-label="Recipient's username" aria-describedby="basic-addon2">
					<div class="input-group-append">
						<span class="input-group-text"><i class="fa fa-search"></i></span>
					</div>
				</div>

				<div class="input-group">
					<label class="col-sm-1">Date</label>
					<div class="col-sm-8 eventInsForm_Ledger">
						<input class="form-control-sm" id='sampleSearchDate' type="date">
						<button type="button" class="btn btn-primary" onclick="previewSampleRequsition()" >Preview</button>
					</div>
				</div>
			
			
			<div class="modal-body">
				<table class="table table-hover table-bordered table-sm mb-0">
					<thead>
						<tr>
							<th>SL#</th>
							<th>Buyer</th>
							<th>PO Id</th>
							<th>Style No</th>
							<th>Date</th>
							<th><span>Search</th>
							<th><span>Print</th>
						</tr>
					</thead>
					<tbody id="datalist">
						<c:forEach items="${sampleReqList}" var="po" varStatus="counter">
							<tr>
								<td>${counter.count}</td>
								<td >${po.buyerOrderId}</td>
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

					<!-- Modal -->
					<div class="modal fade" id="sampleCadModal" tabindex="-1"
						role="dialog" aria-labelledby="sampleCadModal" aria-hidden="true">
						<div class="modal-dialog modal-lg">
							<div class="modal-content">
	<div class="modal-header">
				<h5 class="modal-title" id="exampleModalLabel">Sample Cad List</h5>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			

			
				<div class="input-group">

					<input id="search" type="text" class="form-control"
						placeholder="Search Sample Cad"
						aria-label="Recipient's username" aria-describedby="basic-addon2">
					<div class="input-group-append">
						<span class="input-group-text"><i class="fa fa-search"></i></span>
					</div>
				</div>
				
				<hr>

				<div class="input-group">
					
			
						<label class="col-sm-1">Date</label>
						<input class="form-control-sm" id='sampleCadSearchDate' type="date">
						
						<label for="makeingdespatch" style="width:50px;">Type</label>
						
							<select id="ReportType" style="width:280px;"
												class="selectpicker" data-live-search="false"
												data-style="btn-light btn-sm border-secondary ">

												<option id="ReportType" value='1'>Pattern Making</option>
												<option id="ReportType" value='2'>Pattern Correction</option>
												<option id="ReportType" value='3'>Pattern Grading</option>
												<option id="ReportType" value='4'>Mini Marking</option>

							</select>
	
										
						<button type="button" class="btn btn-primary" onclick="sampleCadDateWiseReport()" >Preview</button>
		
				</div>
			
								<div class="modal-body">
									<table class="table table-hover table-bordered table-sm mb-0">
										<thead>
											<tr>
												<th>SL#</th>
												<th>Sample Cad No</th>
												<th>Buyer Name</th>
												<th>Purchase Order</th>
												<th>Style No</th>
												<th>Item No</th>
												<th>Sample Type</th>
												<td><i class="fa fa-search"></i></td>
												<td><i class="fa fa-print"></i></td>
											</tr>
										</thead>
										<tbody id="sampleCadList">
											<c:forEach items="${sampleCadList}" var="po"
												varStatus="counter">
												<tr>
													<td>${counter.count}</td>
													<td>${po.sampleCommentId}</td>
													<td>${po.buyername}</td>
													<td>${po.purchaseOrder}</td>
													<td>${po.styleNo}</td>
													<td>${po.itemName}</td>
													<td>${po.sampleTypeId}</td>
													<td><i class="fa fa-search" style="cursor: pointer;"
														onclick="searchSampleCad(${po.sampleCommentId},${po.sampleReqId})">
													</i></td>
													<td><i class="fa fa-print" style="cursor: pointer;"
														onclick="sampleCadReport(${po.sampleCommentId})"> </i></td>
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
	</div>
</div>
<jsp:include page="../include/footer.jsp" />

<script
	src="${pageContext.request.contextPath}/assets/js/order/sampleCad.js"></script>