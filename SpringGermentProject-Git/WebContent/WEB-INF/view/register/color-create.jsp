<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="pg.model.WareInfo"%>
<%@page import="pg.model.Module"%>
<%@page import="pg.model.Login"%>
<%@page import="java.util.List"%>

<%
String userId=(String) request.getAttribute("userId");	
String userName=(String) request.getAttribute("userName");	
String linkName=(String) request.getAttribute("linkName");	
%>

<jsp:include page="../include/header.jsp" />

<div class="page-wrapper">
	<div class="content container-fluid">
		<div class="alert alert-success alert-dismissible fade show"
			style="display: none;">
			<p id="successAlert" class="mb-0"><strong>Success!</strong> Color Name Save Successfully..</p>
		</div>
		<div class="alert alert-warning alert-dismissible fade show"
			style="display: none;">
			<p id="warningAlert" class="mb-0"><strong>Warning!</strong> Color Name Empty.Please Enter Color Name...</p>
		</div>
		<div class="alert alert-danger alert-dismissible fade show"
			style="display: none;">
			<p id="dangerAlert" class="mb-0"><strong>Wrong!</strong> Something Wrong...</p>
		</div>
		<input type="hidden" id="userId" value="<%=userId%>">
		<input type="hidden" id="colorId" value="0">
		<input type="hidden" id="linkName" value="<%=linkName%>">

		<div class="row">
			<div class="col-sm-12 col-md-12 col-lg-12">
				<div class="card-box">
					<div class="row">
						<div class="col-sm-6 col-md-6 col-lg-6">

							<div class="row ">
								<h2>
									<b>Color Create</b>
								</h2>
							</div>
							<hr>

							<div class="form-group mb-1">
								<label for="colorName">Color Name:</label> <input type="text"
									class="form-control-sm inputs" id="colorName" name="text">
							</div>
							<div class="form-group mb-1">
								<label for="colorCode">Color Code:</label> <input type="text"
									class="form-control-sm inputs" id="colorCode" name="text">
							</div>
							<button type="button" id="btnSave" class="btn btn-primary btn-sm inputs" accesskey="S"
								onclick="saveAction()"><span style="text-decoration:underline;"> Save</span></button>

							<button type="button" id="btnEdit" class="btn btn-success btn-sm" onclick="editAction()"
								style="display: none;">Edit</button>
							<button type="button" id="btnRefresh"
								class="btn btn-secondary btn-sm" onclick="refreshAction()">Refresh</button>

						</div>
						<div class="col-sm-6 col-md-6 col-lg-6 shadow ">
							<div class="input-group my-2">
								<input type="text" class="form-control"
									placeholder="Search Color" aria-describedby="findButton"
									id="search" name="search">
								<div class="input-group-append">
									<button class="btn btn-primary" type="button" id="findButton">
										<i class="fa fa-search"></i>
									</button>
								</div>
							</div>
							<hr>
							<div class="row" >
								<div class="col-sm-12 col-md-12 col-lg-12" style="overflow: auto; max-height: 600px;">
								<table class="table table-hover table-bordered table-sm" >
								<thead>
									<tr>
										<th scope="col">SL#</th>
										<th scope="col">Color Name</th>
										<th scope="col">Color Code</th>
										<th scope="col">Edit</th>
										<th scope="col">Delete</th>
									</tr>
								</thead>
								<tbody id="dataList">
									<c:forEach items="${colorList}" var="color"
													varStatus="counter">
										<tr>
											<td>${counter.count}</td>
											<td id='colorName${color.colorId}'>${color.colorName}</td>
											<td id='colorCode${color.colorId}'>${color.colorCode}</td>
											<td><i class="fa fa-edit" style="cursor: pointer;" onclick="setData(${color.colorId})"> </i></td>
											<td><i class="fa fa-trash" style="cursor: pointer;" onclick="deleteColorCreate(${color.colorId})"> </i></td>
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
	src="${pageContext.request.contextPath}/assets/js/register/color-create.js"></script>