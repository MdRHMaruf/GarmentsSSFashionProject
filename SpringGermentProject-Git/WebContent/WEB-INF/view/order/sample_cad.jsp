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
		<input type="hidden" id="userId" value="<%=lg.get(0).getId()%>">

		<div class="row">
			<div class="col-sm-12 col-md-12 col-lg-12">
				<div class="card-box">

					<div class="row">
						<div class="col-md-5">
							<div class="row">
								<div class="col-sm-5 p-0">
									<label for="purchaseOrder" class="mb-0">Purchase Order</label>

									<select id="purchaseOrder" class="selectpicker form-control"  onchange="poWiseStyleLoad()"
										data-live-search="true"
										data-style="btn-light btn-sm border-secondary form-control-sm">
										<option>Select Order</option>
										<c:forEach items="${poList}" var="po">
										<option id="purchaseOrder" value="${po}">${po}</option>
										</c:forEach>

									</select>

								</div>

								<div class="form-check form-check-inline">
									<input class="form-check-input ml-1" type="radio" name="radio"	id="withPO" value="" checked> 
									<label class="form-check-label" for="withPO">With PO</label>
								</div>
								<div class="form-check form-check-inline">
									<input class="form-check-input" type="radio" name="radio"	id="withOutPO" value=""> 
									<label	class="form-check-label" for="withOutPO">With Out PO</label>
								</div>

							</div>

							<div class="row mt-1">
								<div class="col-sm-5 p-0">
									<label for="styleNo" class="mb-0">Style No</label>
									 <select
										id="styleNo" class="selectpicker form-control" onchange="styleWiseItemLoad()"
										data-live-search="true"
										data-style="btn-light btn-sm border-secondary form-control-sm">
										<option>Select Style No</option>

									</select>

								</div>
							</div>

							<div class="row mt-1">

								<div class="col-sm-5 p-0">
									<label for="itemName" class="mb-0">Item Name</label> 
									<select
										id="itemName" class="selectpicker form-control" onchange="styleItemWiseColorLoad()"
										data-live-search="true"
										data-style="btn-light btn-sm border-secondary form-control-sm">
										<option>Select Item Name</option>

									</select>

								</div>

								<div class="col-sm-6 ml-1 p-0">
									<label for="sampleCommentsNo" class="mb-0">Sample
										Comment No</label>
										 <input style=": ; : ;"
										class="form-control-sm" type="text" id="sampleCommentsNo">

								</div>
							</div>

							<div class="row mt-1">

								<div class="col-sm-5 p-0">
									<label for="color" class="mb-0">Color</label> 
									<select
										id="itemColor" class="selectpicker form-control" onchange="styleItemWiseColorsizeLoad()"
										data-live-search="true"
										data-style="btn-light btn-sm border-secondary form-control-sm">
										<option id="itemColor">Select Color</option>

									</select>

								</div>
							</div>

							<div class="row mt-1">

								<div class="col-sm-5 p-0">
									<label for="size" class="mb-0">Size</label> 
									<select id="size"
										class="selectpicker form-control" data-live-search="true" onchange=""
										data-style="btn-light btn-sm border-secondary form-control-sm">
										<option id="size">Select Size</option>

									</select>

								</div>

								<div class="col-sm-6 ml-1 p-0">
									<label for="sampleType" class="mb-0">Sample Type</label>
									
									
									<select id="sampletype" onchange=""
										class="selectpicker col-md-12 px-0" data-live-search="true"
											data-style="btn-light btn-sm border-light-gray">
											<option id="sampletype" value="0">Select Sample</option>
											<c:forEach items="${sampletype}" var="po">
												<option id="sampletype" value="${po.id}">${po.name}</option>
												</c:forEach>
									</select>

								</div>

							</div>

						</div>



						<div class="col-md-7">

							<header class="d-flex justify-content-between">
								<h5 class="text-center" style="display: inline;">Search
									Sample Comments</h5>
								<button type="button" class="btn btn-outline-dark btn-sm"
									data-toggle="modal" data-target="#exampleModal">
									<i class="fa fa-search"></i>
								</button>
							</header>
							<hr class="my-1">
							<div class="row mt-1">

								<div class="col-sm-6">

									<h5>Pattern Making</h5>

									<div class="row">
										<label for="makeingDate" class="col-sm-4">Date</label>
										<div class="col-sm-8">
											<input class="form-control-sm col-sm-12" type="date"
												id="patternmakingdate">
										</div>
									</div>

									<div class="row mt-1">
										<label for="makeingdespatch" class="col-sm-4">Dispatch</label>
										<div class="col-sm-8">
											<select id="makeingDespatch"
												class="selectpicker form-control" data-live-search="false"
												data-style="btn-light btn-sm border-secondary form-control-sm">
												
												<option value='1'>NO</option>
												<option value='2'>Yes</option>

											</select>
										</div>
									</div>

									<div class="row mt-1">
										<label for="makeingReceivedBy" class="col-sm-4">Recevied
											By</label>
										<div class="col-sm-8">
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

								<div class="col-sm-6">

									<h5>Pattern Correction</h5>

									<div class="row">
										<label for="correctionDate" class="col-sm-4">Date</label>
										<div class="col-sm-8">
											<input class="form-control-sm col-sm-12" type="date"
												id="patterncorrectiondate">
										</div>
									</div>

									<div class="row mt-1">
										<label for="correctionDate" class="col-sm-4">Dispatch</label>
										<div class="col-sm-8">
											<select id="patterncorrectiondispatch" class="selectpicker form-control"
												data-live-search="false"
												data-style="btn-light btn-sm border-secondary form-control-sm">
												<option value='1'>NO</option>
												<option value='2'>Yes</option>

											</select>
										</div>
									</div>

									<div class="row mt-1">
										<label for="correctionReceviedBy" class="col-sm-4">Recevied
											By</label>
										<div class="col-sm-8">
											<input class="form-control-sm" type="text" id="correctionReceviedBy">
										</div>
									</div>

								</div>

							</div>

							<div class="row">

								<div class="col-sm-6">

									<h5>Pattern Grading</h5>

									<div class="row">
										<label for="gradingDate" class="col-sm-4">Date</label>
										<div class="col-sm-8">
											<input class="form-control-sm col-sm-12" type="date"
												id="gradingDate">
										</div>
									</div>

									<div class="row mt-1">
										<label for="gradingDespatch" class="col-sm-4">Despatch</label>
										<div class="col-sm-8">
											<select id="gradingDespatch"
												class="selectpicker form-control" data-live-search="true"
												data-style="btn-light btn-sm border-secondary form-control-sm">
												<option value='1'>No</option>
												<option value='2'>Yes</option>

											</select>
										</div>
									</div>

									<div class="row mt-1">
										<label for="gradingReceviedBy" class="col-sm-4">Recevied
											By</label>
										<div class="col-sm-8">
											<input class="form-control-sm" type="text" id="gradingdispatchreceivedby">
										</div>
									</div>

								</div>

								<div class="col-sm-6">

									<h5>Pattern Marking</h5>

									<div class="row">
										<label for="markingDate" class="col-sm-4">Date</label>
										<div class="col-sm-8">
											<input class="form-control-sm col-sm-12" type="date"
												id="markingDate">
										</div>
									</div>

									<div class="row mt-1">
										<label for="markingDespatch" class="col-sm-4">Dispatch</label>
										<div class="col-sm-8">
											<select id="markingDespatch"
												class="selectpicker form-control" data-live-search="false"
												data-style="btn-light btn-sm border-secondary form-control-sm">
												<option value='1'>NO</option>
												<option value='2'>Yes</option>

											</select>
										</div>
									</div>

									<div class="row mt-1">
										<label for="markingReceviedBy" class="col-sm-4">Recevied
											By</label>
										<div class="col-sm-8">
											<input class="form-control-sm" type="text"
												id="markingReceviedBy">
										</div>
									</div>

									<div class="row d-flex justify-content-end mt-1">

										<button class="btn btn-sm btn-warning mr-3" id="btnUpload">Upload</button>

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


					<div class="modal fade" id="exampleModal" tabindex="-1"
						role="dialog" aria-labelledby="exampleModalLabel"
						aria-hidden="true">
						<div class="modal-dialog modal-lg">
							<div class="modal-content">
								<div class="modal-header">
									<div class="input-group">
										<input id="search" type="text" class="form-control"
											placeholder="Search Sample Comments"
											aria-label="Recipient's username"
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
												<th scope="col">#</th>
												<th scope="col">Buyer</th>
												<th scope="col">Purchase</th>
												<th scope="col">Style No</th>												
												<th scope="col">Item Name</th>
												<th scope="col">Sample Type</th>
												<th><span><i class="fa fa-search"></i></span></th>
												<th><span><i class="fa fa-print"></i></span></th>
											</tr>
										</thead>
										<tbody id="">
						<c:forEach items="${SampleList}" var="po" varStatus="counter">
							<tr>
								<td>${counter.count}</td>
								<td> ${po.buyername}</td>
								<td >${po.purchaseOrder}</td>
								<td >${po.styleNo}</td>
								<td >${po.itemName}</td>
								<td >${po.sampleTypeId}</td>
								<td><i class="fa fa-search"
									onclick="getSampleDetails(${po.sampleCommentId})">
								</i>
								</td>
								<td><i class="fa fa-print"
									onclick="sampleCadReport(${po.sampleCommentId})">
								</i>
								</td>
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