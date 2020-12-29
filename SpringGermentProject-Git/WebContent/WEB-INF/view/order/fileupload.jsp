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
		<div class="row">
			<div class="col-md-12">
				<div class="card-box">
						<input type="hidden" id="userId" value="<%=lg.get(0).getId()%>">
					<div class="row">
						<div class="col-sm-4">
							<div class="row">
								<label class="form-label">Purpose</label>
								<div class="col-sm-9">
									<input class="form-control-sm" type="text" id="purpose">
								</div>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="row">
								<label class="form-label">Form Date</label>
								<div class="col-sm-9">
									<input class="form-control-sm" type="date" id="formDate">
								</div>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="row">
								<label class="form-label">End Date</label>
								<div class="col-sm-9">
									<input class="form-control-sm" type="date" id="endDate">
								</div>
							</div>
						</div>
					</div>


					<div class="row mb-2 mt-2">

						<div class="col-sm-3"></div>
						<div class="col-sm-3"></div>
						<div class="col-sm-3"></div>

						<div class="col-sm-3">
							<div class="col-sm-12">
								<button class="btn btn-primary btn-sm" id="find">Find</button>

								<button onclick="getFileDownload('attachment.png')" class="btn btn-secondary btn-sm">Preview</button>
							</div>
						</div>
					</div>

					<div class="row">
						<table class="table table-responsive table-stripped" style="height:200px;  overflow:scroll;">
							<thead>
								<tr>
									<th style="width:60px" class="text-center">File ID#.</th>
									<th style="width:20%"  class="text-center">File Name</th>
									<th style="width:15%"  class="text-center">Uploaded by</th>
									<th style="width:150px"  class="text-center">Uploaded Machine</th>
									<th style="width:150px" class="text-center">Date/Time</th>
									<th style="width:20%" class="text-center">Purpose</th>
									<th style="width:150px" class="text-center">Download by</th>
									<th  style="width:150px" class="text-center">Download Machine</th>
									<th   style="width:150px" class="text-center">Date/Time</th>
									<th  style="width:150px" class="text-center">Download</th>
									<th  style="width:150px" class="text-center">Del</th>
								</tr>
								<tbody id="filetable" >
									
								</tbody>
							</thead>
						</table>
					</div>

					<div class="row">
						<div style="width: 55%">
							<!-- <div id='progressBar'
								style='height: 20px; border: 2px solid green; margin-bottom: 20px'>
								<div id='bar'
									style='height: 100%; background: #33dd33; width: 0%'></div>
							</div> -->
							<div   class="progress">
								<div id='bar' class="progress-bar" style="width: 0%"></div>
							</div>

					
							<div class="input-group mt-2">
								<div class="custom-file">
									  <!-- <input type="file"  id="files" multiple style="margin-bottom: 20px"/><br/>  -->
									 <input type="file" 	id="files"  multiple> <!-- <label class="custom-file-label" for="inputGroupFile04">Choose file</label> -->
									 <!-- <output id="selectedFiles"></output>  -->
								</div>
								<div class="input-group-append">
									<button class="btn btn-outline-secondary" type="button"  id="uploadButton" value="Upload">Upload</button>
								</div>
							</div>
							
							<div class="row">
								<!-- <div id='debug' type="textarea" style='height: 100px; border: 2px solid #ccc; overflow: auto'></div>   -->
								
								<!-- <textarea id='debug' class="form-control"  style='height: 100px; border: 2px solid #ccc; overflow: auto'></textarea> -->
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
	function getFileDownload(file_name){
		window.location.href = '${pageContext.request.contextPath}/assets/images/'
            + file_name;
	}
</script>
<script>
	$('.bsdatepicker').datepicker({

	});
</script>
<script
	src="${pageContext.request.contextPath}/assets/js/custom/link.js"></script>
	
	<script
	src="${pageContext.request.contextPath}/assets/js/order/fileUpload.js"></script>

