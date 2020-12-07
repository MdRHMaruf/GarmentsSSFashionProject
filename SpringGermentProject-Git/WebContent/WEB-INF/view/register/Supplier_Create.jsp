<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="pg.model.wareinfo"%>
<%@page import="pg.model.module"%>
<%@page import="pg.model.login"%>
<%@page import="java.util.List"%>
<jsp:include page="../include/header.jsp" />

<%List<login> lg = (List<login>) session.getAttribute("pg_admin"); %>

<div class="page-wrapper">
	<div class="content container-fluid">
		<div class="row">
			<div class="col-md-12">
				<div class="card-box">

					<div class="row">
						<div class="col-sm-12 col-md-6 col-lg-6 ">
							<h3>Supplier List</h3>
						</div>

						<div class="col-sm-12 col-md-6 col-lg-6">

							<div class="input-group my-2">
								<input type="text" class="form-control"
									placeholder="Search Supplier Name"
									aria-describedby="findButton" id="search" name="search">
								<div class="input-group-append">
									<button class="btn btn-primary" type="button" id="findButton">
										<i class="fa fa-search"></i>
									</button>
								</div>

								<div class="col-sm-12 col-md-5 col-lg-5">
									<button style="height: 38px;" type="button"
										class="btn btn-primary" data-toggle="modal"
										data-target="#exampleModal">Create Supplier</button>
								</div>

							</div>


						</div>

					</div>

					<hr>

					<div class="modal fade" id="exampleModal" tabindex="-1"
						role="dialog" aria-labelledby="exampleModalLabel"
						aria-hidden="true">
						<div class="modal-dialog modal-lg" role="document">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title" id="exampleModalLabel">Supplier
										Create</h5>
									<button type="button" class="close" data-dismiss="modal"
										aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
								</div>
								<div class="modal-body">


									<div style="margin-top: 5px;" class="row">
										<label style="text-align: left;" class="col-sm-2">Supplier
											Id</label>
										<div class="col-sm-4">
											<input id="suppier_id" type="text" class="form-control">
										</div>

										<label style="text-align: left;" class="col-sm-2">Supplier
											Code</label>
										<div class="col-sm-4">
											<input id="suppier_code" type="text" class="form-control">
										</div>
									</div>

									<div style="margin-top: 5px;" class="row">
										<label style="text-align: left;" class="col-sm-2"><span id="redwarning" style="color:red">*</span>Supplier
											Name</label>
										<div class="col-sm-4">
											<input id="suppier_name" type="text" class="form-control">
										</div>

										<label style="text-align: left;" class="col-sm-2">Contact
											Person</label>
										<div class="col-sm-4">
											<input id="contact_person" type="text" class="form-control">
										</div>
									</div>

									<div style="margin-top: 5px;" class="row">
										<label style="text-align: left;" class="col-sm-2"><span id="redwarning" style="color:red">*</span>Supplier
											Address</label>
										<div class="col-sm-4">
											<textarea rows="2" cols="2" id="suppier_address"
												class="form-control" placeholder="Enter text here"></textarea>
										</div>

										<label style="text-align: left;" class="col-sm-2">Consignee
											Address</label>
										<div class="col-sm-4">
											<textarea rows="2" cols="2" id="consignee_address"
												class="form-control" placeholder="Enter text here"></textarea>
										</div>
									</div>

									<div style="margin-top: 5px;" class="row">
										<label style="text-align: left;" class="col-sm-2">Notify
											Address</label>
										<div class="col-sm-4">
											<textarea rows="2" cols="2" id="notify_address"
												class="form-control" placeholder="Enter text here"></textarea>
										</div>

										<label class="col-sm-2"><span id="redwarning" style="color:red">*</span>Country</label>
										<div class="col-sm-4">

											<input id="countries1" type="text" class="form-control"
												onkeyup="CountriesSearch(this)">
										</div>

									</div>

									<div style="margin-top: 5px;" class="row">
										<label style="text-align: left;" class="col-sm-2"><span id="redwarning" style="color:red">*</span>Telphone</label>
										<div class="col-sm-4">
											<input id="telphone" type="text" class="form-control">
										</div>

										<label style="text-align: left;" class="col-sm-2">Mobile</label>
										<div class="col-sm-4">
											<input id="mobile" type="text" class="form-control">
										</div>
									</div>

									<div style="margin-top: 5px;" class="row">
										<label style="text-align: left;" class="col-sm-2">Fax</label>
										<div class="col-sm-3">
											<input id="fax" type="text" class="form-control">
										</div>

										<label style="text-align: left;" class="col-sm-2"><span id="redwarning" style="color:red">*</span>E-mail</label>
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
									</div>

									<div style="margin-top: 5px;" class="row">
										<label style="text-align: left; color: red;" class="col-sm-2">Bank
											Information</label>
									</div>

									<div style="margin-top: 5px;" class="row">
										<label style="text-align: left;" class="col-sm-2">Bank
											Name</label>
										<div class="col-sm-4">
											<input id="bank_name" type="text" class="form-control">
										</div>

										<label style="text-align: left;" class="col-sm-2">Accounts
											No</label>
										<div class="col-sm-4">
											<input id="accounts_no" type="text" class="form-control">
										</div>
									</div>

									<div style="margin-top: 5px;" class="row">
										<label style="text-align: left;" class="col-sm-2">Bank
											Address</label>
										<div class="col-sm-4">
											<textarea rows="2" cols="2" id="bank_address"
												class="form-control" placeholder="Enter text here"></textarea>
										</div>

										<label style="text-align: left;" class="col-sm-2">Accounts
											Name</label>
										<div class="col-sm-4">
											<input id="accounts_name" type="text" class="form-control">
										</div>
									</div>

									<div style="margin-top: 5px;" class="row">
										<label style="text-align: left;" class="col-sm-2">Swift
											Code</label>
										<div class="col-sm-3">
											<input id="swift_code" type="text" class="form-control">
										</div>

										<label style="text-align: left;" class="col-sm-2">Country</label>
										<div class="col-sm-5">

											<input id="bankCOuntry" type="text" class="form-control"
												onkeyup="CountriesSearch(this)">

										</div>
									</div>


								</div>

								<div class="modal-footer">
									<button type="button" class="btn btn-secondary"
										data-dismiss="modal">Close</button>
									<button class="btn btn-primary" id="save" type="button"
										onclick="insertSupplier()">Save</button>
									<button class="btn btn-secondary" id="edit" type="button"
										onclick="editSupplier(this)">Edit</button>
									<button class="btn btn-danger" type="button">Refresh</button>
								</div>
							</div>
						</div>
					</div>



					<div style="margin-top: 5px;" class="row">
						<div style="overflow: auto; max-height: 500px;" class="col-sm-12">
							<table class="table table-bordered">
								<thead>
									<tr>
										<th>ID</th>
										<th>Supplier's Name</th>
										<th>Supplier's Code</th>
										<th>edit</th>
									</tr>
								</thead>
								<tbody id="supplierTable">

								</tbody>
							</table>
						</div>
					</div>

				</div>
			</div>
		</div>
	</div>
</div>

<jsp:include page="../include/footer.jsp" />

<script
	src="${pageContext.request.contextPath}/assets/js/register/CreateSupplier.js"></script>


