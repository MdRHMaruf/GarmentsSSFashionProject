<%@page import="java.util.ArrayList"%>
<%@page import="pg.model.Login"%>
<%@page import="pg.model.Menu"%>
<%@page import="java.util.List"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
    <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/assets/images/favicon.png">
    <title>Admin</title>
    <link href="https://fonts.googleapis.com/css?family=Fira+Sans:400,500,600,700" rel="stylesheet">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/font-awesome-4.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/icofont.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/fullcalendar.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/dataTables.bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/select2.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/bootstrap-datetimepicker.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/jquery-ui.css">
   	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/bootstrap-select.css">
   	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/bootstrap-select.min.css">
   	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/bootstrap-table-expandable.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/style.css">
    
    <!--[if lt IE 9]>
    <script src="assets/js/html5shiv.min.js"></script>
    <script src="assets/js/respond.min.js"></script>
    <![endif]-->


    <!--Load the AJAX API-->
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/google-chart.js"></script>

</head>
<%
String userId=(String)session.getAttribute("userId");
String userName=(String)session.getAttribute("userName");
		List<Menu> list = (List<Menu>) session.getAttribute("menulist");
%>

<body>
<div class="main-wrapper slide-nav">
    <div class="header">
        <div class="container-fluid">
            <div class="header-left">
                <a id="toggle-menu" href="#sidebar" class="logo">
                    <i class="icofont-navigation-menu"></i>
                </a>
            </div>
            <div class="header-right">
                <div class="page-title-box pull-left">
                    <h3><%=userName%></h3>
                </div>
                <a id="mobile_btn" class="mobile_btn pull-left" href="#sidebar">
                    <i class="fa fa-bars" aria-hidden="true"></i>
                </a>
                <ul class="nav navbar-nav navbar-right user-menu pull-right">
                    <div class="dropdown">
                        <a class="dropdown-toggle" href="#" role="button" id="profileLinkDropdown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <span><i class="far fa-user"></i></span> <%=userName%>
                        </a>

                        <div class="dropdown-menu" aria-labelledby="profileLinkDropdown">
     
                            <li><a href="${url_login}">Profile</a></li>
                            					<s:url var="url_login"
									value="/loginout" />
						<li><a href="${url_login}">Logout</a></li>
                        </div>
                    </div>

                </ul>

                <div class="dropdown mobile-user-menu pull-right">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false"><i class="fa fa-ellipsis-v"></i></a>
                    <ul class="dropdown-menu pull-right">
                        <li><a href="login.php">Logout</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <div class="sidebar opened" id="sidebar">
        <div class="sidebar-inner slimscroll">
            <div id="sidebar-menu" class="sidebar-menu">
                <ul>
                    <li>
                        <a href="dashboard"><i class="icofont-dashboard"></i> Dashboard</a>
                    </li>
                    
                    
                    
                    
                    <%
							//List<menu> list = (List<menu>) session.getAttribute("menulist");
							int i = 0;
							String head = "";

							for (int a = 0; a < list.size(); a++) {
								head = list.get(a).getName().toString();
						%>
                    <li class="submenu">
                        <a href="#"><i class="icofont-code"></i><span> <%=head%></span> <span class="menu-arrow"><i class="icofont-simple-right"></i></span></a>
                        <ul class="list-unstyled" style="display: none;">
                        <%
									if (head.equals(list.get(a).getName().toString())) {
											for (int b = a; b < list.size(); b++) {
												if (!list.get(b).getName().toString().equals(head)) {
													a--;
													break;
												}
								%>
								<s:url var="url_form" value="<%=list.get(a).getLinks()%>" />
                            <li><a href="${url_form}"><%=list.get(a).getSub()%></a></li>
                            <%
									a++;
											}
										}
								%>
                        </ul>
                    </li>
                   
                   <%
							}
						%>
                   
                   
                   
                    <%-- <li class="submenu">
                        <a href="#"><i class="icofont-code"></i><span> Components</span> <span class="menu-arrow"><i class="icofont-simple-right"></i></span></a>
                        <ul class="list-unstyled" style="display: none;">
                            <li><a href="uikit">UI Kit</a></li>
                            <li><a href="tabs">Tabs</a></li>
                        </ul>
                    </li>
                    
                    <li class="submenu">
                        <a href="javascript:void(0);"><i class="icofont-chart-flow"></i> <span>Multi Level</span> <span class="menu-arrow"><i class="icofont-simple-right"></i></span></a>
                        <ul style="display: none;">
                            <li class="submenu">
                                <a href="javascript:void(0);"><span>Level 1</span> <span class="menu-arrow"><i class="icofont-simple-right"></i></span></a>
                                <ul style="display: none;">
                                    <li><a href="javascript:void(0);"><span>Level 2</span></a></li>
                                    <li class="submenu">
                                        <a href="javascript:void(0);"> <span> Level 2</span> <span class="menu-arrow"><i class="icofont-simple-right"></i></span></a>
                                        <ul class="list-unstyled" style="display: none;">
                                            <li><a href="javascript:void(0);">Level 3</a></li>
                                            <li><a href="javascript:void(0);">Level 3</a></li>
                                        </ul>
                                    </li>
                                    <li><a href="javascript:void(0);"><span>Level 2</span></a></li>
                                </ul>
                            </li>
                            <li>
                                <a href="javascript:void(0);"><span>Level 1</span></a>
                            </li>
                        </ul>
                    </li>
                     --%>
                </ul>
            </div>
        </div>
    </div>