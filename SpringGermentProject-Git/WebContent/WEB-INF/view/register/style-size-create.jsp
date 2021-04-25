
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
String linkName=(String) request.getAttribute("linkName");
%>

<jsp:include page="../include/header.jsp" />

<div class="page-wrapper">
	<div class="content container-fluid">
		<div class="alert alert-success alert-dismissible fade show"
			style="display: none;">
			<p id="successAlert" class="mb-0">
				<strong>Success!</strong> Style Size Name Save Successfully..
			</p>
		</div>
		<div class="alert alert-warning alert-dismissible fade show"
			style="display: none;">
			<p id="warningAlert" class="mb-0">
				<strong>Warning!</strong> Style Size Name Empty.Please Enter Style
				Size Name...
			</p>
		</div>
		<div class="alert alert-danger alert-dismissible fade show"
			style="display: none;">
			<p id="dangerAlert" class="mb-0">
				<strong>Wrong!</strong> Something Wrong...
			</p>
		</div>
		<input type="hidden" id="userId" value="<%=userId%>">
		<input type="hidden" id="sizeId" value="0">
		<input type="hidden" id="sizeGroupId" value="0">
		<input type="hidden" id="groupId" value="0">
		<input type="hidden" id="linkName" value="<%=linkName%>">
		<div class="row">
			<div class="col-sm-12 col-md-12 col-lg-12">
				<div class="card-box">
					<div class="row">
						<div class="col-sm-5 col-md-5 col-lg-5">

							<div class="row ">
								<h2>
									<b>Style Size Create</b>
								</h2>
							</div>
							<hr>

							<div class="form-group">
								<label for="sizeGroupName">Group Name:</label>
								<div class="input-group">
									<input id="sizeGroupName" type="text" class="form-control"
										placeholder="Select Group Name" onfocus="setSizeGroupId('0')">
									<div class="input-group-append">
										<button class="btn btn-outline-secondary" type="button"
											id="groupSettingButton" data-toggle="modal"
											data-target="#exampleModal">
											<i class="fa fa-gear"></i>
										</button>
									</div>
								</div>

							</div>

							<div class="row">
								<div class="col-md-9">
									<div class="form-group">
										<label for="sizeName">Style Size Name:</label> <input
											type="text" class="form-control-sm inputs" id="sizeName"
											name="sizeName">
									</div>
								</div>
								<div class="col-md-3">
									<div class="form-group">
										<label for="sorting">Sorting</label> <input type="number"
											class="form-control-sm inputs" id="sorting" name="text">

									</div>
								</div>

							</div>

							<button type="button" id="btnSave" class="btn btn-primary btn-sm inputs" accesskey="S"
								onclick="saveAction()"><i class="fas fa-save"></i><span style="text-decoration:underline;"> Save</span></button>

							<button type="button" id="btnEdit" class="btn btn-success btn-sm"
								onclick="editAction()" style='display: none;'><i class="fa fa-pencil-square"></i> Edit</button>
							<button type="button" id="btnRefresh"
								class="btn btn-secondary btn-sm" onclick="refreshAction()"><i class="fa fa-refresh"></i> Refresh</button>

						</div>
						<div class="col-sm-7 col-md-7 col-lg-7 shadow ">
							<div class="input-group my-2">
								<input type="text" class="form-control"
									placeholder="Search Style Size"
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
												<th scope="col">Size Group Name</th>
												<th scope="col">Size Name</th>
												<th scope="col">Size Shorting</th>
												<th scope="col">Edit</th>
												<th scope="col">Delete</th>
											</tr>
										</thead>
										<tbody id="dataList">
											<c:forEach items="${sizeList}" var="size" varStatus="counter">
												<tr>
													<td>${counter.count}</td>
													<td id='sizeGroup${size.sizeId}'>${size.groupName}</td>
													<td id='sizeName${size.sizeId}'>${size.sizeName}</td>
													<td id='sizeSorting${size.sizeId}'>${size.sizeSorting}</td>
													<td><i class="fa fa-edit" onclick="setData(${size.sizeId},${size.groupId})" style="cursor: pointer;"> </i></td>
													<td><i class="fa fa-trash" onclick="deleteSize(${size.sizeId})" style="cursor: pointer;"> </i></td>
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
	<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div
			class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Style Size Group</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close" onclick="groupModalCloseAction()">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				
				<div class="modal-body">
				
					<div class="row">
						<div class="col-md-12">
							
							<input type="text" id="groupName" class="form-control" placeholder="Enter Group Name">
						</div>
					</div>
					<hr>
					<div class="row">
						<div class="col-md-12">
						<table class="table table-hover table-bordered table-sm">
						<thead>
							<tr>
								<th>Group Name</th>
								<th>Edit</th>
							</tr>
						</thead>
						<tbody id="groupTableList">
							<c:forEach items="${groupList}" var="group" varStatus="counter">
								<tr>									
									<td id='groupName${group.groupId}'>${group.groupName}</td>
									<td><i class="fa fa-edit"
										onclick="setGroupData(${group.groupId})"> </i></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
						</div>
					</div>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary btn-sm"
						data-dismiss="modal" onclick="groupModalCloseAction()"><i class="fa fa-close"></i> Close</button>
					<button type="button" id="btnGroupSave" class="btn btn-primary btn-sm" onclick="groupSaveAction()"><i class="fas fa-save"></i> Save as New Group </button>
					<button type="button" id="btnGroupEdit" class="btn btn-success btn-sm" onclick="groupEditAction()" style="display: none;"><i class="fa fa-pencil-square"></i> Edit Group Name</button>
				</div>
			</div>
		</div>
	</div>
</div>

<jsp:include page="../include/footer.jsp" />

<script
	src="${pageContext.request.contextPath}/assets/js/register/style-size-create.js"></script>