<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="pg.model.wareinfo"%>
<%@page import="pg.model.module"%>
<%@page import="pg.model.login"%>
<%@page import="java.util.List"%>
<jsp:include page="../include/header.jsp" />


<body onload="RetrieveLines()">
	<%
		List<login> lg = (List<login>) session.getAttribute("pg_admin");
	%>
	<div class="page-wrapper">
		<div class="content container-fluid">
			<div class="alert alert-success alert-dismissible fade show"
				style="display: none;">
				<p id="successAlert" class="mb-0">
					<strong>Success!</strong> Accessories Item Name Save Successfully..
				</p>
			</div>
			<div class="alert alert-warning alert-dismissible fade show"
				style="display: none;">
				<p id="warningAlert" class="mb-0">
					<strong>Warning!</strong> Accessories Item Name Empty.Please Enter
					Accessories Item Name...
				</p>
			</div>
			<div class="alert alert-danger alert-dismissible fade show"
				style="display: none;">
				<p id="dangerAlert" class="mb-0">
					<strong>Wrong!</strong> Something Wrong...
				</p>
			</div>

			<input type="hidden" id="userId" value="<%=lg.get(0).getId()%>">
			<input type="hidden" id="accessoriesItemId" value="0">

			<div class="row">
				<div class="col-sm-12 col-md-12 col-lg-12">
					<div class="card-box">
						<div class="row">
							<div class="col-sm-6 col-md-6 col-lg-6">

								<div class="row ">
									<h2>
										<b>Sewing Line Setup</b>

									</h2>
									<button type="button" class="btn btn-outline-dark btn-sm"
										data-toggle="modal" data-target="#productionModal">
										Production Plan <i class="fa fa-search"></i>
									</button>

								</div>
								<hr>

								<div class="form-group">
									<div class="row ">
										<label class="col-sm-3">Buyer Name</label>

										<div class="col-sm-8">
											<label class="col-sm-8" id="buyerName"></label>
										</div>
									</div>
								</div>
								<div class="form-group">
									<div class="row ">
										<label class="col-sm-3">Purchase Order</label>

										<div class="col-sm-8">
											<label class="col-sm-8" id="purchaseOrder"></label>
										</div>
									</div>
								</div>
								<div class="form-group">
									<div class="row ">
										<label class="col-sm-3">Style Name</label>

										<div class="col-sm-8">
											<label class="col-sm-8" id="styleNo"></label><input type="hidden" id="styleId"/>
										</div>
									</div>
								</div>

								<div class="form-group">
									<div class="row ">
										<label class="col-sm-3">Item Name</label>

										<div class="col-sm-8">
											<label class="col-sm-8" id="itemName"></label>
										</div>
									</div>
								</div>
								<div class="form-group">
									<div class="row ">
										<label class="col-sm-3" for="accessoriesItemName">Start</label>

										<div class="col-sm-4">
											<input type="date" id="start" onchange="duration()"
												class="form-control-sm col-sm-12">
										</div>

										<label class="col-sm-1" for="accessoriesItemName">End</label>

										<div class="col-sm-4">
											<input type="date" id="end" onchange="duration()"
												class="form-control-sm col-sm-12">
										</div>
									</div>
								</div>


								<div class="form-group">
									<div class="row ">
										<label class="col-sm-3" for="">Duration</label>

										<div class="col-sm-3">
											<input id="duration" type="text" class="form-control-sm">
										</div>

									</div>
								</div>


								<div class="form-group">
									<div class="row ">
										<label class="col-sm-3" for="accessoriesItemName">Line
											No</label>

										<div class="col-sm-8">
											<select id="lines" multiple="multiple"
												class="selectpicker form-control" data-live-search="true"
												data-style="btn-light border-secondary form-control-sm">
												<option id="style" value="0">Select Line Name(s)</option>
												<c:forEach items="${lines}" var="llist" varStatus="counter">
													<option name="stylename" id="style" value="${llist.lineId}">${llist.lineName}</option>
												</c:forEach>
											</select>
										</div>
									</div>
								</div>


								<button type="button" id="btnSave"
									class="btn btn-primary btn-sm" onclick="saveAction()">Save</button>

								<button type="button" id="btnEdit"
									class="btn btn-primary btn-sm" onclick="editAction()" disabled>Edit</button>
								<button type="button" id="btnRefresh"
									class="btn btn-primary btn-sm" onclick="refreshAction()">Refresh</button>

							</div>
							<div class="col-sm-6 col-md-6 col-lg-6 shadow ">
								<div class="input-group my-2">
									<input type="text" class="form-control"
										placeholder="Search Accessories Item"
										aria-describedby="findButton" id="search" name="search">
									<div class="input-group-append">
										<button class="btn btn-primary" type="button" id="findButton">
											<i class="fa fa-search"></i>
										</button>
									</div>
								</div>
								<hr>
								<div class="row">
									<div class="col-sm-12 col-md-12 col-lg-12"
										style="overflow: auto; max-height: 600px;">
										<table class="table table-hover table-bordered table-sm">
											<thead>
												<tr>
													<th scope="col">#</th>
													<th scope="col" style="width: 100px">Style Name</th>
													<th scope="col">Lines</th>
													<th scope="col">Start</th>
													<th scope="col">End</th>
													<th scope="col">Edit</th>

												</tr>
											</thead>
											<tbody id="dataList">
										 	<c:forEach items="${sewingLineList}" var="list"
													varStatus="counter">
										<tr>
											<td>${list.styleNo}</td>
											<td >${list.allLineList}</td>
											<td >${list.startDate}</td>
											<td >${list.endDate}</td>
											<td><i class="fa fa-edit" onclick="setData(${list.endDate})"> </i></td>
										</tr>
									</c:forEach> --
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

	<!-- Modal -->
	<div class="modal fade" id="productionModal" tabindex="-1"
		role="dialog" aria-labelledby="productionModal" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<div class="input-group">
						<input id="search" type="text" class="form-control"
							placeholder="Search Production Plan"
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
								<th>Buyer</th>
								<th>Purchase Order</th>
								<th>Style No</th>
								<th>Item Name</th>
								<th><span><i class="fa fa-search"></i></span></th>
							</tr>
						</thead>
						<tbody id="poList">
							<c:forEach items="${productionPlanList}" var="list"
								varStatus="counter">
								<tr>
									<td>${counter.count}</td>
									<td id='buyerId${list.buyerId}'>${list.buyerName}</td>
									<td id='purchaseOrder${list.buyerorderId}'>${list.purchaseOrder}</td>
									<td id='styleId${list.styleId}'>${list.styleNo}</td>
									<td id='itemId${list.itemId}'>${list.itemName}</td>
									<td><i class="fa fa-search"
										onclick="setProductPlanInfoForSewing(${list.buyerId},${list.buyerorderId},${list.styleId},${list.itemId})">
									</i></td>
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
		src="${pageContext.request.contextPath}/assets/js/production/sewingLineSetup.js"></script>