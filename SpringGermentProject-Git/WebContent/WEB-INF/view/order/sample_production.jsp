<%@page import="pg.share.ProductionType"%>
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
		<input type="hidden" id="sampleCommentsId" value=""> <input
			type="hidden" id="productionType"
			value="<%=ProductionType.SAMPLE_PRODUCTION.getType()%>"> <input
			type="hidden" id="passType"
			value="<%=ProductionType.SAMPLE_PASS.getType()%>">
		<div class="row">
			<div class="col-sm-12 col-md-12 col-lg-12">
				<div class="card-box">

					<div class="row">
						<div class="col-md-5">
							<div class="row">
								<div class="col-sm-5 p-0">
									<label for="purchaseOrder" class="mb-0">Purchase Order</label>

									<input class="form-control-sm" readonly type="text" id="purchaseOrder">

								</div>

							</div>

							<div class="row mt-1">
								<div class="col-sm-5 p-0">
									<label for="styleNo" class="mb-0">Style No</label> <input
										class="form-control-sm" readonly type="text" id="styleNo">

								</div>
							</div>

							<div class="row mt-1">

								<div class="col-sm-5 p-0">
									<label for="itemName" class="mb-0">Item Name</label> <input
										class="form-control-sm" readonly type="text" id="itemName">

								</div>

								<div class="col-sm-6 ml-1 p-0">
									<label for="sampleCommentsNo" class="mb-0">Sample
										Comment No</label> <input style="background: black; color: white;"
										class="form-control-sm" readonly type="text" id="sampleCommentsNo"
										readonly>

								</div>
							</div>

							<div class="row mt-1">

								<div class="col-sm-5 p-0">
									<label for="color" class="mb-0">Color</label> <input
										class="form-control-sm" readonly type="text" id="color">

								</div>
							</div>

							<div class="row mt-1">

								<div class="col-sm-5 p-0">
									<label for="size" class="mb-0">Size</label> <input
										class="form-control-sm" type="text" id="size">

								</div>

								<div class="col-sm-6 ml-1 p-0">
									<label for="sampleType" class="mb-0">Sample Type</label> <input
										class="form-control-sm" type="text" id="sampleType">

								</div>

							</div>

							<div class="row mt-1">

								<div class="col-sm-5 p-0">
									<label for="cuttingDate" class="mb-0">Cutting</label> <input
										class="form-control-sm col-sm-12" type="date" id="cuttingDate">

								</div>

								<div class="col-sm-3 ml-1 p-0">
									<label for="cuttingQty" class="mb-0">Cutting Qty</label> <input
										class="form-control-sm" type="text" id="cuttingQty">

								</div>

								<div class="col-sm-3 ml-1 p-0">
									<label for="reqQty" class="mb-0">Req. Qty</label> <input
										class="form-control-sm" type="text" id="reqQty" readonly>

								</div>

							</div>

						</div>



						<div class="col-md-7">

							<header class="d-flex justify-content-between">
								<h5 class="text-center" style="display: inline;">Search
									Sample Production</h5>
									<button type="button" class="btn btn-outline-dark btn-sm"
										data-toggle="modal" data-target="#sampleCadModal">
										<i class="fa fa-search"></i>Sample Cad List
									</button>
									<button type="button" class="btn btn-outline-dark btn-sm"
										data-toggle="modal" data-target="#sampleProductionModal">
										<i class="fa fa-search"></i>Sample Production List
									</button>
							</header>
							<hr class="my-1">
							<div class="row mt-1">

								<div class="col-sm-6">

									<h5>Print</h5>

									<div class="row">
										<label for="printSendDate" class="col-sm-4">Send Date</label>
										<div class="col-sm-8">
											<input class="form-control-sm col-sm-12" type="datetime-local"
												id="printSendDate">
										</div>
									</div>

									<div class="row mt-1">
										<label for="printReceivedDate" class="col-sm-4">Received
											Date</label>
										<div class="col-sm-8">
											<input class="form-control-sm col-sm-12" type="datetime-local"
												id="printReceivedDate">
										</div>
									</div>

									<div class="row mt-1">
										<label for="printReceivedQty" class="col-sm-4">Received
											Qty</label>
										<div class="col-sm-8">
											<input class="form-control-sm" type="text"
												id="printReceivedQty">
										</div>
									</div>


								</div>

								<div class="col-sm-6">

									<h5>Embroidery</h5>

									<div class="row">
										<label for="embroiderySendDate" class="col-sm-4">Send
											Date</label>
										<div class="col-sm-8">
											<input class="form-control-sm col-sm-12" type="datetime-local"
												id="embroiderySendDate">
										</div>
									</div>

									<div class="row mt-1">
										<label for="embroideryReceivedDate" class="col-sm-4">Received
											Date</label>
										<div class="col-sm-8">
											<input class="form-control-sm col-sm-12" type="datetime-local"
												id="embroideryReceivedDate">
										</div>
									</div>

									<div class="row mt-1">
										<label for="embroideryReceivedQty" class="col-sm-4">Received
											Qty</label>
										<div class="col-sm-8">
											<input class="form-control-sm" type="text"
												id="embroideryReceivedQty">
										</div>
									</div>

								</div>

							</div>

							<div class="row">

								<div class="col-sm-6">

									<h5>Sewing</h5>

									<div class="row">
										<label for="sewingSendDate" class="col-sm-4">Start Date</label>
										<div class="col-sm-8">
											<input class="form-control-sm col-sm-12" type="datetime-local"
												id="sewingSendDate">
										</div>
									</div>

									<div class="row mt-1">
										<label for="sewingFinishDate" class="col-sm-4">Finish
											Date</label>
										<div class="col-sm-8">
											<input class="form-control-sm col-sm-12" type="datetime-local"
												id="sewingFinishDate">
										</div>
									</div>

								</div>
								<div class="col-sm-6">
									<h5>Other</h5>
									<div class="row">
										<label for="operatorName" class="col-sm-5">Operator
											Name</label>
										<div class="col-sm-7">
											<input class="form-control-sm" type="text" id="operatorName">
										</div>
									</div>

									<div class="row mt-1">
										<label for="quality" class="col-sm-5">Quality</label>
										<div class="col-sm-7">
											<input class="form-control-sm" type="text" id="quality">
										</div>
									</div>


									<div class="row d-flex justify-content-end mt-1">

										<button class="btn btn-sm btn-warning mr-3" id="btnUpload" accesskey="U"><span style="text-decoration:underline;"> Upload</span></button>

									</div>

								</div>

							</div>

						</div>

					</div>
					<div id="tableList" class="my-2">
						<!-- <div class="row">
							<div class="col-md-12 table-responsive">
								<table
									class="table table-hover table-bordered table-sm mb-0 small-font">
									<thead class="no-wrap-text bg-light">
										<tr>
											<th scope="col">Type</th>
											<th scope="col">08-09</th>
											<th scope="col">09-10</th>
											<th scope="col">10-11</th>
											<th scope="col">11-12</th>
											<th scope="col">12-01</th>
											<th scope="col">02-03</th>
											<th scope="col">03-04</th>
											<th scope="col">04-05</th>
											<th scope="col">05-06</th>
											<th scope="col">06-07</th>
											<th scope="col">07-08</th>
											<th scope="col">08-09</th>
											<th scope="col">Total</th>

										</tr>
									</thead>
									<tbody id="dataList">
										<tr class='itemRow' data-id=''>
											<td><p style='color: black; font-weight: bold;'>Production</p>
												<p style='color: green; font-weight: bold;'>Pass</p></td>
											<td><input type='number' class='form-control-sm'
												id='production-h1' onchange='setTotalQty()' value='' /><input
												type='number' onchange='setTotalQty()'
												class='form-control-sm' id='pass-h1' value='' /></td>
											<td><input type='number' class='form-control-sm'
												id='production-h2' onchange='setTotalQty()' value='' /><input
												type='number' onchange='setTotalQty()'
												class='form-control-sm' id='pass-h2' value='' /></td>
											<td><input type='number' class='form-control-sm'
												id='production-h3' onchange='setTotalQty()' value='' /><input
												type='number' onchange='setTotalQty()'
												class='form-control-sm' id='pass-h3' value='' /></td>
											<td><input type='number' class='form-control-sm'
												id='production-h4' onchange='setTotalQty()' value='' /><input
												type='number' onchange='setTotalQty()'
												class='form-control-sm' id='pass-h4' value='' /></td>
											<td><input type='number' class='form-control-sm'
												id='production-h5' onchange='setTotalQty()' value='' /><input
												type='number' onchange='setTotalQty()'
												class='form-control-sm' id='pass-h5' value='' /></td>
											<td><input type='number' class='form-control-sm'
												id='production-h6' onchange='setTotalQty()' value='' /><input
												type='number' onchange='setTotalQty()'
												class='form-control-sm' id='pass-h6' value='' /></td>
											<td><input type='number' class='form-control-sm'
												id='production-h7' onchange='setTotalQty()' value='' /><input
												type='number' onchange='setTotalQty()'
												class='form-control-sm' id='pass-h7' value='' /></td>
											<td><input type='number' class='form-control-sm'
												id='production-h8' onchange='setTotalQty()' value='' /><input
												type='number' onchange='setTotalQty()'
												class='form-control-sm' id='pass-h8' value='' /></td>
											<td><input type='number' class='form-control-sm'
												id='production-h9' onchange='setTotalQty()' value='' /><input
												type='number' onchange='setTotalQty()'
												class='form-control-sm' id='pass-h9' value='' /></td>
											<td><input type='number' class='form-control-sm'
												id='production-h10' onchange='setTotalQty()' value='' /><input
												type='number' onchange='setTotalQty()'
												class='form-control-sm' id='pass-h10' value='' /></td>
											<td><input type='number' class='form-control-sm'
												id='production-h11' onchange='setTotalQty()' value='' /><input
												type='number' onchange='setTotalQty()'
												class='form-control-sm' id='pass-h11' value='' /></td>
											<td><input type='number' class='form-control-sm'
												id='production-h12' onchange='setTotalQty()' value='' /><input
												type='number' onchange='setTotalQty()'
												class='form-control-sm' id='pass-h12' value='' /></td>
											<td><input type='number' class='form-control-sm'
												id='production-total' value='' readonly /><input
												type='number' id='pass-total' readonly
												class='form-control-sm' /></td>
										</tr>
									</tbody>
								</table>
							</div>
						</div> -->
					</div>
					<div class="row mt-1">
						<div class="col-sm-12 p-0">
							<button type="button" id="btnPost" class="btn btn-warning btn-sm"
								onclick="" accesskey="P"><span style="text-decoration:underline;"> Post</span></button>

							<button type="button" id="btnRefresh" class="btn btn-dark btn-sm"
								onclick="refreshAction()">Refresh</button>
							<button type="button" id="btnPreview" class="btn btn-info btn-sm"
								onclick="showPreview()">Preview</button>

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
	src="${pageContext.request.contextPath}/assets/js/order/sample-production.js"></script>