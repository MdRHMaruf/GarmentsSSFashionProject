<%@page import="org.hibernate.Transaction"%>
<%@page import="pg.share.StoreTransaction"%>
<%@ page contentType="application/pdf" %>

<%@ page trimDirectiveWhitespaces="true"%>


<%@ page import="net.sf.jasperreports.engine.design.JRDesignQuery" %>
<%@ page import="net.sf.jasperreports.engine.design.JasperDesign" %>
<%@ page import="net.sf.jasperreports.engine.xml.JRXmlLoader" %>

<%@ page import="net.sf.jasperreports.engine.*" %>
<%@ page import="java.io.File" %>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="java.io.FileNotFoundException" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="pg.config.*" %>

<%

    try {
		
    	String transactionId=(String)request.getAttribute("transactionId");
    	String transactionType=(String)request.getAttribute("transactionType");
	
    	
        SpringRootConfig sp=new SpringRootConfig();
        
		String Sql="select fii.transactionId,fii.date,fii.issuedTo,issuedDI.DepartmentName issuedDepartmentName,fii.receiveBy,fii.remarks,fii.departmentId,di.DepartmentName as departmentName,fat.purchaseOrder,fat.styleId,sc.StyleNo,fat.styleItemId,id.itemname,fat.colorId,c.Colorname,fat.itemColorId,itemColor.Colorname as itemColorName,fat.rollId,fri.supplierRollId,fat.unitId,u.unitname,fat.unitQty,fat.cItemId,fi.ItemName as fabricsName "+
				"from tbFabricsIssueInfo fii "+
				"left join tbFabricsAccessoriesTransaction fat "+
				"on fii.transactionId = fat.transactionId and fat.transactionType = '"+StoreTransaction.FABRICS_ISSUE.getType()+"' "+
				"left join TbDepartmentInfo issuedDI "+
				"on fii.issuedTo = issuedDI.DepartmentId "+
				"left join TbDepartmentInfo di "+
				"on fii.departmentId = di.DepartmentId "+
				"left join TbStyleCreate sc "+
				"on fat.styleId = sc.StyleId "+
				"left join tbItemDescription id "+
				"on fat.styleItemId = id.itemid "+
				"left join tbColors c "+
				"on fat.colorId = c.ColorId "+
				"left join tbColors itemColor "+
				"on fat.itemColorId = itemColor.ColorId "+
				"left join tbFabricsRollInfo fri "+
				"on fat.rollId = fri.rollId "+
				"left join tbunits u "+
				"on fat.unitId = u.Unitid "+
				"left join TbFabricsItem fi "+
				"on fat.cItemId = fi.id "+
				"where fii.transactionId = '"+transactionId+"' ";
      	System.out.println("sql "+Sql);
      	
		String jrxmlFile = session.getServletContext().getRealPath("WEB-INF/jasper/store/fabricsIssue.jrxml");
        InputStream input = new FileInputStream(new File(jrxmlFile));

        
    	JasperDesign jd=JRXmlLoader.load(input);
		JRDesignQuery jq=new JRDesignQuery();
		
		jq.setText(Sql);
		jd.setQuery(jq);
		
        //Generating the report
        JasperReport jr = JasperCompileManager.compileReport(jd);
      
        JasperPrint jp = JasperFillManager.fillReport(jr, null, sp.getDataSource().getConnection());

        //Exporting the report as a PDF
        JasperExportManager.exportReportToPdfStream(jp, response.getOutputStream());
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (JRException e) {
        e.printStackTrace();
    }  catch (SQLException e) {
        e.printStackTrace();
    }


%>