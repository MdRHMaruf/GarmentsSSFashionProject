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
	<div class="alert alert-success alert-dismissible fade show"
		style="display: none;">
		<p id="successAlert" class="mb-0">
			<strong>Success!</strong> Parcel Save Successfully..
		</p>
	</div>
	<div class="alert alert-warning alert-dismissible fade show"
		style="display: none;">
		<p id="warningAlert" class="mb-0">
			<strong>Warning!</strong> Parcel Empty.Please Enter Parcel...
		</p>
	</div>
	<div class="alert alert-danger alert-dismissible fade show"
		style="display: none;">
		<p id="dangerAlert" class="mb-0">
			<strong>Wrong!</strong> Something Wrong...
		</p>
	</div>
	<input type="hidden" id="userId" value="<%=userId%>"> <input
		type="hidden" id="parcelId" value=""> <input type="hidden"
		id="parcelItemAutoId" value=""> <input type="hidden"
		id="itemType" value="">

	<div class="card-box">
		<!-- <div class="row">
			<div class="col-md-12"> -->
		<header class="d-flex justify-content-between">
			<h5 class="text-center" style="display: inline;">Pass Book
				Summary Report</h5>
			<button type="button" class="btn btn-outline-dark btn-sm"
				data-toggle="modal" data-target="#searchModal">
				<i class="fa fa-search"></i> Master LC List
			</button>
		</header>
		<hr class="my-1">

		<div class="row">
		
			<div class="col-md-4">
				<div class="form-group mb-0  row">
					<label for="masterLC"
						class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">Master
						LC<span style="color: red">*</span>
					</label> <input id="masterLC" type="input"
						class="col-md-8 form-control-sm">

				</div>
			</div>
			<div class="col-md-3">
				<div class="form-group mb-0  row">
					<label for="fromDate"
						class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">From
						Date<span style="color: red">*</span>
					</label> <input id="fromDate" type="date"
						class="col-md-8 form-control-sm customDate"
						data-date-format="DD MMM YYYY">

				</div>
			</div>
			<div class="col-md-3">
				<div class="form-group mb-0  row">
					<label for="toDate"
						class="col-md-4 col-form-label-sm pr-0 mb-1 pb-1">To
						Date<span style="color: red">*</span>
					</label> <input id="toDate" type="date"
						class="col-md-8 form-control-sm customDate"
						data-date-format="DD MMM YYYY">

				</div>
			</div>
			<div class="col-md-2">
				<button id="btnSearch" type="button"
					class="btn btn-primary btn-sm ml-1" onclick="searchAction()">
					<i class="fa fa-search"></i> Search
				</button>
			</div>
		</div>
<hr class="my-0">
		<div class="row mt-1">
			<div style="overflow: auto; max-height: 420px;"
				class="col-sm-12 px-1 table-responsive">
				<table
					class="table table-hover table-bordered table-sm mb-0 small-font">
					<thead class="wrap-text">
						<tr>
							<th>Sl No</th>
							<th style="min-width:200px">Export L/C No</th>
							<th>Import L/C No</th>
							<th>UD & Amendment No</th>
							<th style="min-width:200px">Description of Fabrics/Access.</th>
							<th>Qty of Fabrics/Access.</th>
							<th>B/E No & Date</th>
							<th>Pass Book Page No</th>
							<th style="min-width:200px">Qty. of fabrics used</th>
							<th style="min-width:150px">Garments Description</th>
							<th>Qty of gmts. exported</th>
							<th>S/B No & Date</th>
							<th>Pass Book Page No</th>
							<th>Invoice Value in US$</th>
							<th>Realised Value in US$</th>
							<th>Exp No & Date</th>
							<th>Total Un-Exported Gmts.</th>
							<th>Invoice value in US$</th>
							<th>Remarks</th>
						</tr>
					</thead>
					<tbody id="dataList">

					</tbody>
				</table>
			</div>
		</div>

<input type="hidden" id="col1Summary" value="">
				<input type="hidden" id="col2Summary" value="">
				<input type="hidden" id="col3Summary" value="">
				<input type="hidden" id="col4Summary" value="">
				<input type="hidden" id="col5Summary" value="">
				<input type="hidden" id="col6Summary" value="">
				<input type="hidden" id="col7Summary" value="">
				<input type="hidden" id="col8Summary" value="">
				<input type="hidden" id="col9Summary" value="">
				<input type="hidden" id="col10Summary" value="">
				<input type="hidden" id="col11Summary" value="">
				<input type="hidden" id="col12Summary" value="">
				<input type="hidden" id="col13Summary" value="">
				<input type="hidden" id="col14Summary" value="">
				<input type="hidden" id="col15Summary" value="">
				<input type="hidden" id="col16Summary" value="">
				<input type="hidden" id="col17Summary" value="">
				<input type="hidden" id="col18Summary" value="">
				<input type="hidden" id="col19Summary" value="">
		<div class="row mt-1">
			<div class="col-md-12 d-flex justify-content-end">
				<button type="button" id="btnConfirm" class="btn btn-primary btn-sm"
					accesskey="C" onclick="confirmAction()">
					<i class="fas fa-save"></i><span
						style="text-decoration: underline;"> Confirm</span>
				</button>

				<button id="btnRefresh" type="button"
					class="btn btn-primary btn-sm ml-1" onclick="refreshAction()">
					<i class="fa fa-refresh"></i> Refresh
				</button>

				<button id="btnPreview" type="button"
					class="btn btn-primary btn-sm ml-1" onclick="previewAction()">
					<i class="fa fa-print"></i> Preview
				</button>

			</div>
		</div>


	</div>
</div>

<!-- search modal -->
<div class="modal fade" id="searchModal" tabindex="-1" role="dialog"
	aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<div class="input-group">
					<input id="search" type="text" class="form-control"
						placeholder="Search Purchase Order"
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
							<th>Buyer Name</th>
							<th>Master LC</th>
							<th>LC Date</th>
							<th><span><i class="fa fa-search"></i></span></th>
						</tr>
					</thead>
					<tbody id="masterLcList">
						<c:forEach items="${masterLCList}" var="master"
							varStatus="counter">
							<tr>
								<td>${master.buyerName}</td>
								<td>${master.masterLCNo}</td>
								<td>${master.date}</td>
								<th><span><i style="cursor: pointer;"
										onclick="searchMasterLc('${master.masterLCNo}','${master.buyerId}','${master.amendmentNo }')"
										class="fa fa-search"></i></span></th>
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
	src="${pageContext.request.contextPath}/assets/js/commercial/pass-book-form.js"></script>
