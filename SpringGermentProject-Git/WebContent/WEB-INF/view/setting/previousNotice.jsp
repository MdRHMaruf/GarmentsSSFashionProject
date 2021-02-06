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
	String userId = (String) session.getAttribute("userId");
	String userName = (String) session.getAttribute("userName");
%>

<jsp:include page="../include/header.jsp" />




<%-- <style>
#mydiv {
transform: rotate(0deg);
transition: 1s;
}
.tablebody:hover > #mydiv {
	/*  width: 100px;
  background: red; */
	/*transition: width 2s, height 2s, transform 2s;*/
	
	transform: rotate(360deg);
	color: red;
}
/* 
#p:hover {
	/* width: 300px;
  height: 300px; */
	transform: rotate(360deg);
} */
</style> --%>

<form>
	<div class="page-wrapper">
		<div class="container-fluid">
			<div class="card-box">
				<h4>Create Notice</h4>
				<div class="row">
					<table class="table table-hover table-bordered table-sm mb-0 ">
						<thead>
							<tr>
								<th style="width:150px;" >Date</th>
								<th class="w-2">Notice</th>
								<th  style="width:150px;">Attachment</th>

							</tr>
						</thead>
						<tbody id="poList">
							<c:forEach items="${notice}" var="no" varStatus="counter">
								<tr name="tr" id="${counter.count}"
									data-body="${no.noticeBody}" data-header="${no.noticeHeader}"
									data-id="${no.id}">
									<td style="font-size:18px;">${no.date}</td>
									<td class="tablebody">
									
									<h6 style="font-size:18px;" id="header-${counter.count}"><b>${no.noticeHeader}</b></h6>
									<div class="mydiv" id="mydiv-${counter.count}" data-id="${counter.count}" hidden>

											
											<p style="font-size:25px;" id="bodytext-${counter.count}"></p>

										</div> 
										</td>
									<td><button type="button" id="attachmentlink"
											data-file="${no.filename}" class="btn btn-link text-center"
											onclick="download(this)">
											<i class="fa fa-download"></i>
										</button></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>

				<!-- <div class="mydiv" id="mydiv">

					<h3 id="header">Header</h3>
					<p id="bodytext">body</p>

				</div> -->

			</div>
		</div>
	</div>


</form>



<jsp:include page="../include/footer.jsp" />
<script
	src="${pageContext.request.contextPath}/assets/js/settings/settings.js"></script>


<script>
	

	
</script>


