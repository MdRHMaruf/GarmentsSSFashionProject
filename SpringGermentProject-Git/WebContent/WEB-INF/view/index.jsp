<%@page import="pg.model.Login"%>
<%@page import="pg.model.Module"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Base64"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	String userId = (String) session.getAttribute("userId");
	String userName = (String) session.getAttribute("userName");
	String body = (String) request.getAttribute("body");
	String header = (String) request.getAttribute("header");
	String filename = (String) request.getAttribute("file");
%>

<%  
    String pageName = "previousNotice.jsp";  
%>

<jsp:include page="include/header.jsp" />

<div class="page-wrapper">
	<div class="content container-fluid">
		<div class="row card-box">

			<c:forEach items="${modulelist}" var="v" varStatus="counter">
				<div class="col-sm-6 col-md-6 col-lg-2"
					onclick="change_system(${v.id})">
					<div class="dash-widget2 dash-widget5 btn">
						<span class="dash-widget-icon text-info"> <i
							class="icofont-navigation-menu"></i>
						</span>
						<div class="text-center">
							<h5>${v.modulename}</h5>
						</div>
					</div>
				</div>
			</c:forEach>
			<%-- <div class="col-sm-6 col-md-6 col-lg-2">
                        <div class="dash-widget2 dash-widget5">
                            <span class="dash-widget-icon text-info">
                                <i class="icofont-chart-bar-graph"></i>
                            </span>
                            <div class="text-center">
                                <h5>Register</h5>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-6 col-md-6 col-lg-2">
                        <div class="dash-widget2 dash-widget5">
                            <span class="dash-widget-icon text-secondary">
                                <i class="icofont-ui-user"></i>
                            </span>
                            <div class="text-center">
                                <h5>Order</h5>
                               
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-6 col-md-6 col-lg-2">
                        <div class="dash-widget2 dash-widget5">
                            <span class="dash-widget-icon text-primary">
                                <i class="fa fa-industry" aria-hidden="true"></i>
                            </span>
                            <div class="text-center">
                                <h5>Production</h5>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-6 col-md-6 col-lg-2">
                        <div class="dash-widget dash-widget5">
                            <span class="dash-widget-icon text-success">
                                <i class="icofont-tasks"></i>
                            </span>
                            <div class="text-center">
                                <h5>Store</h5>
                                
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6 col-sm-6 col-lg-2">
                        <div class="dash-widget dash-widget5">
                            <span class="dash-widget-icon text-success">
                                <i class="icofont-tasks"></i>
                            </span>
                            <div class="text-center">
                                <h5>Commercial</h5>
                                
                            </div>
                        </div>
                    </div> --%>
		</div>

		<div class="row ">
			<h4 class="border rounded p-2 bg-success text-white">Notice Board</h4>
		</div>

		<div class="card-box mt-1 pt-1 pb-0">
			<div class="row">
				<h4>Topic: <%=header%></h4>
			</div>
			<div class="row">
				<marquee width="100%" class="ml-2" direction="left" height="50px">
						<h2><%=body%></h2>
				</marquee>
			</div>
			<div class="row">
			<button type="button"  id="attachmentlink" data-file=<%=filename%> class="btn btn-link text-success" style="font-weight:bold;font-size:20px;" onclick="redirectPage('<%=pageName%>')">Previous Notice(s)</button>
				<button type="button" id="attachmentlink" data-file=<%=filename%> class="btn btn-link" onclick="download(this)">Attachment</button>
			</div>

		</div>

	</div>
</div>

<script type="text/javascript">
        google.charts.load('current', {'packages':['corechart']});
        google.charts.setOnLoadCallback(drawChart);

        function drawChart() {
            var data = google.visualization.arrayToDataTable([
                ['Year', 'Sales', 'Expenses'],
                ['2004',  1000,      400],
                ['2005',  1170,      460],
                ['2006',  660,       1120],
                ['2007',  1030,      540]
            ]);

            var options = {
                title: 'Company Performance',
                curveType: 'function',
                legend: { position: 'bottom' }
            };

            var ChartLine = new google.visualization.LineChart(document.getElementById('curve_chart'));
            ChartLine.draw(data, options);

    //    pie chart
            var dataPie = google.visualization.arrayToDataTable([
                ['Task', 'Hours per Day'],
                ['Work',     11],
                ['Eat',      2],
                ['Commute',  2],
                ['Watch TV', 2],
                ['Sleep',    7]
            ]);

            var options = {
                title: 'My Daily Activities',
                pieHole: 0.4,
            };

            var chartPie = new google.visualization.PieChart(document.getElementById('donutchart'));
            chartPie.draw(dataPie, options);
        }
    </script>
<jsp:include page="include/footer.jsp" />
<script
	src="${pageContext.request.contextPath}/assets/js/custom/link.js"></script>
<script
	src="${pageContext.request.contextPath}/assets/js/custom/system_change.js"></script>
	<script
	src="${pageContext.request.contextPath}/assets/js/settings/settings.js"></script>