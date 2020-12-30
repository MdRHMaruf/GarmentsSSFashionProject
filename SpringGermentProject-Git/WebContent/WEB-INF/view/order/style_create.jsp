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
			<p id="successAlert" class="mb-0"><strong>Success!</strong> Unit Name Save Successfully..</p>
		</div>
		<div class="alert alert-warning alert-dismissible fade show"
			style="display: none;">
			<p id="warningAlert" class="mb-0"><strong>Warning!</strong> Unit Name Empty.Please Enter Unit Name...</p>
		</div>
		<div class="alert alert-danger alert-dismissible fade show"
			style="display: none;">
			<p id="dangerAlert" class="mb-0"><strong>Wrong!</strong> Something Wrong...</p>
		</div>
		<input type="hidden" id="userId" value="<%=lg.get(0).getId()%>">
		<input type="hidden" id="unitId" value="0">
	 	<input type="hidden" id="itemDescriptionId" value="0">
	 	<input type="hidden" id="buyerid" value="0">
	 	<input type="hidden" id="styleItemAutoId" value="0">

		<div class="row">
			<div class="col-sm-12 col-md-12 col-lg-12">
				<div class="card-box">
					<div class="row">
						<div class="col-sm-6 col-md-6 col-lg-6">

							<div class="row ">
								<h2>
									<b>Style Create</b>
								</h2>
							</div>
							<hr>
								<%-- <form  id="myForm" method="POST" enctype="multipart/form-data"> --%>
							<form  id="myForm" action="submitStyleFiles" method="POST" enctype="multipart/form-data">
								<div class="row">
									<select id="buyerId" name="buyerId" class="col-md-12 selectpicker "  data-live-search="true" data-style="btn-light border-secondary">
										<option name="buyerId" id="buyerId" value="0">Select Buyer Name</option>
										<c:forEach items="${buyerList}" var="blist" varStatus="counter">
											<option name="buyerId"  id="buyerId" value="${blist.buyerid}">${blist.buyername}</option>
										</c:forEach>
									</select>		
	
								</div>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label for="styleno">Style No:</label> 
										<input type="text" name="styleNo" class="form-control" id="styleNo" >
									</div>							
								</div>							
								<div class="col-md-6">
									<div class="form-group">
										<label for="unitValue">Date:</label> 									
										<div class="input-group date" data-provide="datepicker">
												    <input id="date" name="date"  type="date" class="form-control">
										</div>
									</div>						
								</div>									
							</div>	

							<div class="col-md-12 row">
									<div class="form-group">
										<label for="Size">Size:</label> 
										<input type="text" name="size" class="form-control" id="size" >
									</div>									
							</div>	

								<div class="row">
									<select id="itemId" name="itemId" multiple="multiple" class="col-md-12 selectpicker "  data-live-search="true" data-style="btn-light border-secondary">
										<option id="itemId" name="itemId" value="0">Select Item Name</option>
										<c:forEach items="${itemList}" var="list" varStatus="counter">
											<option id="itemId" name="itemId" value="${list.itemId}">${list.itemName}</option>
										</c:forEach>
									</select>		
	
								</div>

							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label for="unitValue">Front Image</label> 
			
									     <input type="file" name="frontImage" onchange="readFrontURL(this);" accept=".png" />
										
										 <img id="blahFront" src="" alt="Preadmin">
									</div>								
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label for="unitValue">Back Image</label> 
										
									     <input type="file" name="backImage" onchange="readBackURL(this);" id accept=".png" />
										
										 <img id="blahBack" src="" alt="Preadmin">
									</div>								
								</div>
							</div>		
							<button type="submit" id="btnSave" class="btn btn-primary btn-sm"  onclick="btnsaveAction()"
								>Save</button>

							<button type="submit" id="btnEdit" class="btn btn-primary btn-sm" onclick="btneditAction()"
								disabled>Edit</button>
							<button type="submit" id="btnRefresh"
								class="btn btn-primary btn-sm" onclick="refreshAction()">Refresh</button>							
							</form>										


						</div>
						<div class="col-sm-6 col-md-6 col-lg-6 shadow ">
							<div class="input-group my-2">
								<input type="text" class="form-control"
									placeholder="Search Style" aria-describedby="findButton"
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
										<th scope="col">Style No</th>
										<th scope="col">Item Description</th>
										<th scope="col">Edit</th>
									</tr>
								</thead>
								<tbody id="dataList">
									<c:forEach items="${styleList}" var="slist"
													varStatus="counter">
										<tr>
											<td>${counter.count}</td>
											<td id='styleNo${slist.styleItemAutoId}'>${slist.styleNo}</td>
											<td> <input id='itemId${slist.styleItemAutoId}' type="hidden" value='1'/> ${slist.itemName}</td>
											<td><i class="fa fa-edit" onclick="setData(${slist.styleItemAutoId})"> </i></td>
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

			<script>
			$('.datepicker').datepicker({
			    format: 'mm/dd/yyyy',
			    startDate: '-3d'
			});
			</script>
<script
	src="${pageContext.request.contextPath}/assets/js/order/style-create.js"></script>