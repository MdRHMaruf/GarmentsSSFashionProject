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


<body onload="maxFactoryId()"></body>
<div class="page-wrapper">
	<div class="content container-fluid">
		<div class="row">
			<div class="col-md-12">
				<div class="card-box">

					<div class="row">
						<div class="col-sm-12 col-md-6 col-lg-6 ">
							<h3>Factory List</h3>
						</div>

						<div class="col-sm-12 col-md-6 col-lg-6">
						
								<div class="input-group my-2">
								<input type="text" class="form-control"
									placeholder="Search Factory Name" aria-describedby="findButton"
									id="search" name="search">
								<div class="input-group-append">
									<button class="btn btn-primary" type="button" id="findButton">
										<i class="fa fa-search"></i>
									</button>
								</div>
									<div class="col-sm-4 col-md-5 col-lg-6">
									<button style="height: 38px;" type="button"
										class="btn btn-primary" data-toggle="modal"
										data-target="#exampleModalCenter">Create Factory</button>
								</div>
							</div>
	

						</div>

					</div>

					<hr>

					<div class="modal fade bd-example-modal-lg" id="exampleModalCenter"
						tabindex="-1" role="dialog"
						aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
						<div class="modal-dialog modal-xl" role="document">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title" id="exampleModalLongTitle">Create
										New Factory</h5>
									<button type="button" class="close" data-dismiss="modal"
										aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
								</div>


								<div class="modal-body">


									<div style="margin-top: 5px;" class="row">
										<label style="text-align: left;" class="col-sm-2">ID</label>
										<div class="col-sm-4">
											<input id="id" type="text" class="form-control">
										</div>

									</div>

									<div style="margin-top: 5px;" class="row">
										<label style="text-align: left;" class="col-sm-2"><span style="color:red;">*</span>Factory
											Name</label>
										<div class="col-sm-8">
											<input id="factory_name" type="text" class="form-control">
										</div>
									</div>

									<div style="margin-top: 5px;" class="row">
										<label style="text-align: left;" class="col-sm-2"><span style="color:red;">*</span>Telphone</label>
										<div class="col-sm-4">
											<input id="telphone" type="text" class="form-control">
										</div>

										<label style="text-align: left;" class="col-sm-2"><span style="color:red;">*</span>Mobile</label>
										<div class="col-sm-4">
											<input id="mobile" type="text" class="form-control">
										</div>
									</div>

									<div style="margin-top: 5px;" class="row">
										<label style="text-align: left;" class="col-sm-2">Fax</label>
										<div class="col-sm-3">
											<input id="fax" type="text" class="form-control">
										</div>

										<label style="text-align: left;" class="col-sm-2">E-mail</label>
										<div class="col-sm-5">
											<input id="e_mail" type="text" class="form-control">
										</div>
									</div>

									<div style="margin-top: 5px;" class="row">
										<label style="text-align: left;" class="col-sm-2">Skype
											Id</label>
										<div class="col-sm-4">
											<input id="skype_id" type="text" class="form-control">
										</div>

										<label style="text-align: left;" class="col-sm-2">Bond
											License</label>
										<div class="col-sm-4">
											<input id="bond_license" type="text" class="form-control">
										</div>

									</div>

									<div style="margin-top: 5px;" class="row">
										<label style="text-align: left;" class="col-sm-2"><span style="color:red;">*</span>Address</label>
										<div class="col-sm-4">
											<textarea rows="2" cols="2" id="address" class="form-control"
												placeholder="Enter text here"></textarea>
										</div>
									</div>

									<div style="margin-top: 5px;" class="row">
										<label style="text-align: left; color: red;" class="col-sm-2">Bank
											Information</label>
									</div>

									<div style="margin-top: 5px;" class="row">
										<label style="text-align: left;" class="col-sm-2">Bank
											Name</label>
										<div class="col-sm-3">
											<input id="bank_name" type="text" class="form-control">
										</div>

										<label style="text-align: left;" class="col-sm-2">Bank
											Address</label>
										<div class="col-sm-5">
											<input id="bank_address" type="text" class="form-control">
										</div>
									</div>

									<div style="margin-top: 5px;" class="row">
										<label style="text-align: left;" class="col-sm-2">Account
											No</label>
										<div class="col-sm-3">
											<input id="account_no" type="text" class="form-control">
										</div>

										<label style="text-align: left;" class="col-sm-2">Swift
											Code</label>
										<div class="col-sm-3">
											<input id="swift_code" type="text" class="form-control">
										</div>

									</div>

									<div style="margin-top: 5px;" class="row">
										<label style="text-align: left;" class="col-sm-2">Account
											Name</label>
										<div class="col-sm-3">
											<input id="account_name" type="text" class="form-control">
										</div>

										<label style="text-align: left;" class="col-sm-2">Country</label>
										<div class="col-sm-5">
											<input id="bankcountry" type="text" class="form-control"
												onkeyup="CountriesSearch(this)">
										</div>
									</div>

								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-secondary"
										data-dismiss="modal">Close</button>
									<button class="btn btn-primary" id="save" type="button"
										onclick="FactoryCreate()">Save</button>
									<button class="btn btn-secondary" id="edit" type="button"
										onclick="editFactory()">Edit</button>
									<button class="btn btn-danger" type="button">Refresh</button>
								</div>
							</div>
						</div>
					</div>

					<div style="margin-top: 5px;" class="row">
						<div style="overflow: auto; max-height: 300px;" class="col-sm-12">
							<table class="table table-bordered">
								<thead>
									<tr>
										<th>ID</th>
										<th>Factory Name</th>
										<th>Address</th>
										<th>Action</th>
									</tr>
								</thead>
								<tbody id="factorytable">

								</tbody>
							</table>
						</div>
					</div>



				</div>
			</div>
		</div>


		<script>
			$('.bsdatepicker').datepicker({

			});
		</script>
		<jsp:include page="../include/footer.jsp" />

		<script
			src="${pageContext.request.contextPath}/assets/js/register/FactroyCreate.js"></script>