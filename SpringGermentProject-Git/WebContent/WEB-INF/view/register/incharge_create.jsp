<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="pg.model.WareInfo"%>
<%@page import="pg.model.Module"%>
<%@page import="pg.model.Login"%>
<%@page import="java.util.List"%>
<jsp:include page="../include/header.jsp" />
<%
	List<Login> lg = (List<Login>) session.getAttribute("pg_admin");
%>
<div class="page-wrapper">
	<div class="content container-fluid">
		<div class="alert alert-success alert-dismissible fade show"
			style="display: none;">
			<p id="successAlert" class="mb-0"><strong>Success!</strong> Incharge Name Save Successfully..</p>
		</div>
		<div class="alert alert-warning alert-dismissible fade show"
			style="display: none;">
			<p id="warningAlert" class="mb-0"><strong>Warning!</strong> Incharge Name Empty.Please Enter Incharge Name...</p>
		</div>
		<div class="alert alert-danger alert-dismissible fade show"
			style="display: none;">
			<p id="dangerAlert" class="mb-0"><strong>Wrong!</strong> Something Wrong...</p>
		</div>
		<input type="hidden" id="userId" value="<%=lg.get(0).getId()%>">
		<input type="hidden" id="inchargeId" value="0">

		<div class="row">
			<div class="col-sm-12 col-md-12 col-lg-12">
				<div class="card-box">
					<div class="row">
						<div class="col-sm-6 col-md-6 col-lg-6">

							<div class="row ">
								<h2>
									<b>Incharge Create</b>
								</h2>
							</div>
							<hr>

							<div class="form-group">
								<label for="fabricsItemName"><span style="color:red;">*</span>Name:</label> <input type="text"
									class="form-control" id="name" name="text">
							</div>
							<div class="form-group">
								<label for="fabricsName"><span style="color:red;">*</span>Factory Name:</label> 
								    <select class="form-control" id="factoryName" onchange="factoryWiseDepartmentLoad()">
								   		 <option >Select Factory</option>
								    	<c:forEach items="${factoryList}" var="factoryinfo" varStatus="counter">
													
										  <option  id="factoryId" value="${factoryinfo.factoryid}">${factoryinfo.factoryname}</option>

										</c:forEach>
								    </select>
							</div>
							<div class="form-group">
								<label for="departmentName"><span style="color:red;">*</span>Department:</label>
								    <select class="form-control" id="departmentName">
								   		 <option>Select Department</option>
								    </select>
							</div>
							<div class="form-group">
								<label for="reference">Telephone:</label> <input type="text"
									class="form-control" id="telephone" name="text">
							</div>
							<div class="form-group">
								<label for="reference">Mobile:</label> <input type="text"
									class="form-control" id="mobile" name="text">
							</div>
							<div class="form-group">
								<label for="reference">Fax:</label> <input type="text"
									class="form-control" id="fax" name="text">
							</div>
							<div class="form-group">
								<label for="reference">Email:</label> <input type="text"
									class="form-control" id="email" name="text">
							</div>
							<div class="form-group">
								<label for="reference">Skype Id:</label> <input type="text"
									class="form-control" id="skype" name="text">
							</div>
							<div class="form-group">
								<label for="reference">Address:</label> <input type="text"
									class="form-control" id="address" name="text">
							</div>
							<button type="button" id="btnSave" class="btn btn-primary btn-sm"
								onclick="saveAction()">Save</button>

							<button type="button" id="btnEdit" class="btn btn-success btn-sm" onclick="editAction()"
								style="display: none;">Edit</button>
							<button type="button" id="btnRefresh"
								class="btn btn-secondary btn-sm" onclick="refreshAction()">Refresh</button>

						</div>
						<div class="col-sm-6 col-md-6 col-lg-6 shadow ">
							<div class="input-group my-2">
								<input type="text" class="form-control"
									placeholder="Search Incharge Name" aria-describedby="findButton"
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
										<th scope="col">#</th>
										<th scope="col">Incharge Name</th>
										<th scope="col">Telephone</th>
										<th scope="col">edit</th>
									</tr>
								</thead>
								<tbody id="dataList">
									<c:forEach items="${inchargeList}" var="inchargeinfo"
													varStatus="counter">
										<tr>
											<td>${inchargeinfo.sl}</td>
											<td id='name${inchargeinfo.inchargeId}'>${inchargeinfo.name}</td>
											<td id='telephone${inchargeinfo.inchargeId}'>${inchargeinfo.telephone}</td>
											<td><input type="hidden"
														id='mobile${inchargeinfo.inchargeId}'
														value="${inchargeinfo.mobile}" /><input type="hidden"
														id='fax${inchargeinfo.inchargeId}'
														value="${inchargeinfo.fax}" /><input type="hidden"
														id='email${inchargeinfo.inchargeId}'
														value="${inchargeinfo.email}" /><input type="hidden"
														id='address${inchargeinfo.inchargeId}'
														value="${inchargeinfo.address}" /> <input type="hidden"
														id='skype${inchargeinfo.inchargeId}'
														value="${inchargeinfo.skype}" /> <input type="hidden"
														id='factory${inchargeinfo.inchargeId}'
														value="${inchargeinfo.factoryId}" /> <input type="hidden"
														id='depId${inchargeinfo.inchargeId}'
														value="${inchargeinfo.depId}" /><i class="fa fa-edit"  onclick="setData(${inchargeinfo.inchargeId})" style="cursor: pointer;"> </i></td>
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
	src="${pageContext.request.contextPath}/assets/js/register/incharge_create.js"></script>