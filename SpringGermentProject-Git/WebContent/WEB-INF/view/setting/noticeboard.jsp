<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="pg.services.SettingServiceImpl"%>
<%@page import="pg.model.WareInfo"%>
<%@page import="pg.model.Module"%>
<%@page import="pg.model.Login"%>
<%@page import="java.util.List"%>

<%
String userId=(String)session.getAttribute("userId");
String userName=(String)session.getAttribute("userName");
%>

<jsp:include page="../include/header.jsp" />

<script type="text/javascript"> var contexPath = "<%=request.getContextPath()%>";
</script>

<form>
	<div class="page-wrapper">
		<div class="container-fluid">
			<div class="card-box">


				<div class="row">
					<div class="col-md-8">
						<label>Search</label> <select id="searchnotice"
							class="selectpicker form-control" data-live-search="true"
							data-style="btn-light btn-sm border-light-gray">
							<option value="0">Select</option>
							<c:forEach items="${notices}" var="notices" varStatus="counter">
								<option id="${counter.count}"
									data-details="${notices.noticeBody}" value="${notices.id}">${notices.noticeHeader}</option>
							</c:forEach>
						</select>
					</div>

					<div class="col-md-4">
						<button class="btn btn-sm btn-primary mt-4" type="button"
							id="Search" value="Search" onclick="search()">Search</button>
					</div>
				</div>


				<h4>Create Notice</h4>
				<div class="row">
					<div class="col-md-8">
						<label>Notice Heading</label> <input type="hidden" id="userId"
							value="<%=userId%>"> <input type="text" id="heading"
							class="form-control-sm">
					</div>

					<div class="col-md-4">
						<label>For Departments</label> <select id="sectionSearch"
							class="selectpicker form-control" onchange=""
							data-live-search="true"
							data-style="btn-light btn-sm border-secondary form-control-sm"
							multiple title='Choose Department' data-size="5"
							data-selected-text-format="count>2" data-actions-box="true"
							multiple>
							<c:forEach items="${departments}" var="section"
								varStatus="counter">
								<option id='sectionSearch' value="${section.departmentId}">${section.departmentName}</option>
							</c:forEach>
						</select>
					</div>
				</div>


				<div class="row">


					<div class="col-md-12">
						<label>Notice Heading</label>
						<textarea type="text" id="textbody"
							class="form-control form-control-sm" rows="20" col="5"></textarea>


					</div>
				</div>

				<div class="row mt-1">
					<div class="col-md-6">
						<div class="progress">
							<div id='bar' class="progress-bar" style="width: 0%"></div>
						</div>

						<div class="input-group mt-2">
							<div class="custom-file">
								<input type="file" id="files">
							</div>

						</div>
					</div>
					<div class="col-md-6">

						<button class="btn btn-sm btn-primary mt-4" type="button"
							id="uploadButton" value="Upload">Save</button>
						<button class="btn btn-sm btn-primary  mt-4" type="button"
							id="edit" value="" hidden>Edit</button>

					</div>
				</div>
			</div>


		</div>


	</div>
</form>



<jsp:include page="../include/footer.jsp" />
<script
	src="${pageContext.request.contextPath}/assets/js/settings/settings.js"></script>





