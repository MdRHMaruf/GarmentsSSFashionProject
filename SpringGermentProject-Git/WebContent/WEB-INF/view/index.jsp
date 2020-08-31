<%@page import="pg.model.login"%>
<%@page import="pg.model.module"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="include/header.jsp" />
        <div class="page-wrapper">
            <div class="content container-fluid">
            	<div class="row card-box">
            	
            		<c:forEach items="${modulelist}" var="v" varStatus="counter">
            			<div class="col-sm-6 col-md-6 col-lg-2" onclick="change_system(${v.id})">
                        <div class="dash-widget2 dash-widget5 btn">
                            <span class="dash-widget-icon text-info">
                               <i class="icofont-navigation-menu"></i>
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
            
                <div class="row">
                    <div class=" col-lg-3 col-md-6 col-sm-6 col">
                        <div class="dash-widget2 dash-widget5">
                            <span class="dash-widget-icon text-info">
                                <i class="icofont-chart-bar-graph"></i>
                            </span>
                            <div class="text-center">
                                <h5>Revenue</h5>
                                <h4>998</h4>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6 col-sm-6 col-lg-3">
                        <div class="dash-widget2 dash-widget5">
                            <span class="dash-widget-icon text-secondary">
                                <i class="icofont-ui-user"></i>
                            </span>
                            <div class="text-center">
                                <h5>Users</h5>
                                <h4>1072</h4>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6 col-sm-6 col-lg-3">
                        <div class="dash-widget2 dash-widget5">
                            <span class="dash-widget-icon text-primary">
                                <i class="icofont-files-stack"></i>
                            </span>
                            <div class="text-center">
                                <h5>Projects</h5>
                                <h4>72</h4>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6 col-sm-6 col-lg-3">
                        <div class="dash-widget dash-widget5">
                            <span class="dash-widget-icon text-success">
                                <i class="icofont-tasks"></i>
                            </span>
                            <div class="text-center">
                                <h5>Tasks</h5>
                                <h4>618</h4>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6">
                        <div class="card">
                            <div class="card-header bg-info text-white">
                                <h4>Line Chart</h4>
                            </div>
                            <div id="curve_chart" style="width: 100%; height: 500px"></div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="card">
                            <div class="card-header bg-info text-white">
                                <h4>Pie Chart</h4>
                            </div>
                            <div id="donutchart" style="width: 100%; height: 500px"></div>
                        </div>
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
<script src="${pageContext.request.contextPath}/assets/js/custom/link.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/custom/system_change.js"></script>