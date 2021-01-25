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
		<input type="hidden" id="userId" value="<%=userId%>">
		<input type="hidden" id="purchaseOrder" value="">
		<input type="hidden" id="styleId" value="">
		<input type="hidden" id="itemId" value="">
		<input type="hidden" id="colorId" value="">
		<input type="hidden" id="POStatus" value="">

		<div class="row">
			<div class="col-sm-12 col-md-12 col-lg-12">
				<div class="card-box">

					<div class="row">

						<div class="col-md-12">

							<header class="d-flex justify-content-between">
								<h5 class="text-center" style="display: inline;">
									Sample CAD</h5>
								<button type="button" class="btn btn-outline-dark btn-sm"
									data-toggle="modal" data-target="#exampleModal">
									<i class="fa fa-search"></i>Sample Requisition List
								</button>
							</header>
							<hr class="my-1">
							<div class="row mt-1">

								<div class="col-sm-3">

									<h5>Pattern Making</h5>

									<div class="row">
										<label for="makeingDate" class="col-sm-2">Date</label>
										<div class="col-sm-10">
											<input class="form-control-sm col-sm-12" type="datetime-local"
												id="patternmakingdate">
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
											<input class="form-control-sm" type="text" id="patternmakingreceivedby">
										</div>
									</div>
									<h6>Production Feedback</h6>
									<div class="row mt-1">
										<div class="col-sm-12">
											<textarea rows="2" class="form-control" id="feedback"></textarea>
										</div>
									</div>

								</div>

								<div class="col-sm-3">

									<h5>Pattern Correction</h5>

									<div class="row">
										<label for="correctionDate" class="col-sm-2">Date</label>
										<div class="col-sm-10">
											<input class="form-control-sm col-sm-12" type="datetime-local"
												id="patterncorrectiondate">
										</div>
									</div>

									<div class="row mt-1">
										<label for="correctionDate" class="col-sm-2">Dispatch</label>
										<div class="col-sm-10">
											<select id="patterncorrectiondispatch" class="selectpicker form-control"
												data-live-search="false"
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
											<input class="form-control-sm" type="text" id="correctionReceviedBy">
										</div>
									</div>

								</div>
								
								
								<div class="col-sm-3">

									<h5>Pattern Grading</h5>

									<div class="row">
										<label for="gradingDate" class="col-sm-2">Date</label>
										<div class="col-sm-10">
											<input class="form-control-sm col-sm-12" type="datetime-local"
												id="gradingDate">
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
											<input class="form-control-sm" type="text" id="gradingdispatchreceivedby">
										</div>
									</div>

								</div>								

								<div class="col-sm-3">

									<h5>Pattern Marking</h5>

									<div class="row">
										<label for="markingDate" class="col-sm-2">Date</label>
										<div class="col-sm-10">
											<input class="form-control-sm col-sm-12" type="datetime-local"
												id="markingDate">
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

						<div id="samplecadtableList">
						
						
						</div>
						<div class="row mt-1">
						<div style="width: 55%">
							<div class="progress">
								<div id="bar" class="progress-bar" style="width: 0%"></div>
							</div>

							<div class="input-group mt-2">
								<div class="custom-file">
									<input type="file" id="files" multiple="">
								</div>
								<div class="input-group-append">
									<button class="btn btn-sm btn-primary" type="button" id="uploadButton" value="Upload">Upload</button>
								</div>
							</div>
						</div>
					</div>
						</div>

					</div>

					<div class="row mt-1">
						<div class="col-sm-12 p-0">
							<button type="button" id="save" onclick="insertSample()" class="btn btn-warning btn-sm"	onclick="">Save</button>
							<button type="button" id="edit" class="btn btn-warning btn-sm"	onclick="editSmapleCad()">Edit</button>
							<button type="button" id="btnRefresh" class="btn btn-dark btn-sm"
								onclick="">Refresh</button>
								



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


				</div>
			</div>
		</div>
	</div>
</div>
<jsp:include page="../include/footer.jsp" />

<script src="${pageContext.request.contextPath}/assets/js/order/sampleCad.js"></script>