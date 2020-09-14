<%@page import="java.util.ArrayList"%>
<%@page import="pg.model.login"%>
<%@page import="pg.model.menu"%>
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
				List<login> lg = (List<login>) session.getAttribute("pg_admin");
				List<menu> list = (List<menu>) session.getAttribute("menulist");
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
                    <h3><%=lg.get(0).getUser()%></h3>
                </div>
                <a id="mobile_btn" class="mobile_btn pull-left" href="#sidebar">
                    <i class="fa fa-bars" aria-hidden="true"></i>
                </a>
                <ul class="nav navbar-nav navbar-right user-menu pull-right">
                    <div class="dropdown">
                        <a class="dropdown-toggle" href="#" role="button" id="profileLinkDropdown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <span><i class="far fa-user"></i></span> <%=lg.get(0).getUser()%>
                        </a>

                        <div class="dropdown-menu" aria-labelledby="profileLinkDropdown">
                            <a class="dropdown-item" href="#">Profile</a>
                            <a class="dropdown-item" href="#">Setting</a>
                            <a class="dropdown-item" href="login.php">Logout</a>
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
                    
                    
                     <%-- <li class="submenu">
                        <a href="#"><i class="icofont-list"></i> <span> Register</span> <span class="menu-arrow"><i class="icofont-simple-right"></i></span></a>
                        <ul class="list-unstyled" style="display: none;">
                            <li><a href="buyer_create">Buyer Create</a></li>
                            <li><a href="brand_create">Brand Create</a></li>
                            <li><a href="fabrics_create">Fabrics Create</a></li>
                            <li><a href="accessories_create">Accessories Create</a></li>
                            <li><a href="local_item_create">Local Item Create</a></li>
                            <li><a href="supplier_create">Supplier Create</a></li>
                            <li><a href="item_description_create">Item Description Create</a></li>
                            <li><a href="unit_create">Unit Create</a></li>
                            <li><a href="color_create">Color Create</a></li>
                            <li><a href="costing_details_create">Costing Details Create</a></li>
                            <li><a href="factory_create">Factory Create</a></li>
                            <li><a href="department_create">Department Create</a></li>
                            <li><a href="ware_house_create">Ware House Create</a></li>
                            <li><a href="line_create">Line Create</a></li>
                            <li><a href="check_hour_plan">Check Hour Plan</a></li>
                            <li><a href="country_create">Country Create</a></li>
                            <li><a href="courier_create">Courier Create</a></li>
                            <li><a href="check_dolar_rate">Check Dolar Rate</a></li>
                            <li><a href="merchandiser_create">Merchendiser Create</a></li>
                            <li><a href="incharge_create">Incharge Create</a></li>
                            <li><a href="style_size_create">Style Size Create</a></li>
                            <li><a href="sample_type_create">Sample Type Create</a></li>
                        </ul>
                    </li>
                    
                    <li class="submenu">
                        <a href="#"><i class="icofont-list"></i> <span> Order</span> <span class="menu-arrow"><i class="icofont-simple-right"></i></span></a>
                        <ul class="list-unstyled" style="display: none;">
                            <li><a href="style_create">Style Create</a></li>
                            <li><a href="costing_create">Costing Create</a></li>
                            <li><a href="buyer_purchase_order">Buyer PO</a></li>
                            <li><a href="file_upload">File Upload</a></li>
                            <li><a href="sample_requisition">Sample Requisition</a></li>
                            <li><a href="parcel">Parcel</a></li>
                            <li><a href="sample_cad">Sample CAD</a></li>
                            <li><a href="sample_production">Sample Production</a></li>
                            <li><a href="factory_sample">Factory Sample</a></li>
                            <li><a href="accessories_indent">Acessories Indent</a></li>
                            <li><a href="fabrics_indent">Fabrics Indent</a></li>
                            <li><a href="purchase_requisition">Purchase Requisition</a></li>
                            <li><a href="purchase_order">Purchase Order</a></li>
                            <li><a href="purchase_order_approve_from_md">Purchase Order Approve From MD</a></li>
                            <li><a href="shipping">Shipping</a></li>
                        </ul>
                    </li>
                    
                    <li class="submenu">
                        <a href="#"><i class="icofont-list"></i> <span> Production</span> <span class="menu-arrow"><i class="icofont-simple-right"></i></span></a>
                        <ul class="list-unstyled" style="display: none;">
                            <li><a href="cutting_plan">Cutting Plan</a></li>
                            <li><a href="cutting_plan_2">Cutting Plan 2</a></li>
                            <li><a href="sewing_plan">Sewing Plan</a></li>
                            <li><a href="issue_requsition">Issue Requsition</a></li>
                            <li><a href="cutting_plan_report">Cutting Plan Report</a></li>
                            <li><a href="sewing_plan_report">Sewing Plan Report</a></li>
                            <li><a href="sewing_out_to_qc">Sewing Out To QC</a></li>
                            <li><a href="qc_in_form_sewing">QC In Form Sewing</a></li>
                        </ul>
                    </li>
                    
                    <li class="submenu">
                        <a href="#"><i class="icofont-list"></i> <span> Store</span> <span class="menu-arrow"><i class="icofont-simple-right"></i></span></a>
                        <ul class="list-unstyled" style="display: none;">
                            <li><a href="cutting_plan">Store In</a></li>
                            <li><a href="cutting_plan_2">Store Out To Department</a></li>
                            <li><a href="sewing_plan">Section Wise Use Quantity Entry</a></li>
                            <li><a href="issue_requsition">Style Wise Store In</a></li>
                            <li><a href="cutting_plan_report">Style Wise Store Out</a></li>
                        </ul>
                    </li>
                    
                    <li class="submenu">
                        <a href="#"><i class="icofont-list"></i> <span> Commercial</span> <span class="menu-arrow"><i class="icofont-simple-right"></i></span></a>
                        <ul class="list-unstyled" style="display: none;">
                            <li><a href="master_lc">Master LC</a></li>
                            <li><a href="local_lc">Local LC</a></li>
                            <li><a href="deed_of_contact">Deed Of Contact</a></li>
                            <li><a href="ud">UD</a></li>
                            <li><a href="summery_of_import_detais_report">Summary Of Import Details Report</a></li>
                        </ul>
                    </li> --%>
                    
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
                   
                   
                   
                    <li class="submenu">
                        <a href="#"><i class="icofont-code"></i><span> Components</span> <span class="menu-arrow"><i class="icofont-simple-right"></i></span></a>
                        <ul class="list-unstyled" style="display: none;">
                            <li><a href="uikit">UI Kit</a></li>
                            <li><a href="tabs">Tabs</a></li>
                        </ul>
                    </li>
                    <li class="submenu">
                        <a href="#"><i class="icofont-list"></i> <span> Forms</span> <span class="menu-arrow"><i class="icofont-simple-right"></i></span></a>
                        <ul class="list-unstyled" style="display: none;">
                            <li><a href="form-basic-inputs">Basic Inputs</a></li>
                            <li><a href="form-input-groups">Input Groups</a></li>
                            <li><a href="form-horizontal">Horizontal Form</a></li>
                            <li><a href="form-vertical">Vertical Form</a></li>
                        </ul>
                    </li>
                    <li class="submenu">
                        <a href="#"><i class="icofont-table"></i> <span> Tables</span> <span class="menu-arrow"><i class="icofont-simple-right"></i></span></a>
                        <ul class="list-unstyled" style="display: none;">
                            <li><a href="tables-basic">Basic Tables</a></li>
                            <li><a href="tables-datatables">Data Table</a></li>
                        </ul>
                    </li>
                    <li class="submenu">
                        <a href="#"><i class="icofont-listing-box"></i> <span>Pages</span> <span class="menu-arrow"><i class="icofont-simple-right"></i></span></a>
                        <ul class="list-unstyled" style="display: none;">
                            <li><a href="login"> Login </a></li>
                            <li><a href="forgot-password"> Forgot Password </a></li>
                            <li><a href="blank-page"> Blank Page </a></li>
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
                    
                </ul>
            </div>
        </div>
    </div>