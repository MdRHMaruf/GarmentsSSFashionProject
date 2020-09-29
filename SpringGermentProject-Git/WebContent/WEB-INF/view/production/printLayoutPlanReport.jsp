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
		
    	String buyerId=(String)request.getAttribute("buyerId");
    	String buyerorderId=(String)request.getAttribute("buyerorderId");
    	String styleId=(String)request.getAttribute("styleId");
    	String itemId=(String)request.getAttribute("itemId");
    	String layoutDate=(String)request.getAttribute("layoutDate");
    	String layoutType=(String)request.getAttribute("layoutType");
    	String layoutName=(String)request.getAttribute("layoutName");
    	
        SpringRootConfig sp=new SpringRootConfig();
        
		String Sql="select *,(select Name from tbBuyer where id=a.BuyerId) as BuyerName,(select StyleNo from TbStyleCreate where StyleId=a.StyleId) as StyleNo,(select ItemName from tbItemDescription where ItemId=a.ItemId) as ItemName,(select LineName from TbLineCreate where LineId=a.LineId) as LineName,'"+layoutName+"' as layoutName,(select Name from TbEmployeeInfo where AutoId=a.EmployeeId) as EmployeeName from tbLayoutPlanDetails a where a.BuyerId='"+buyerId+"' and a.BuyerOrderId='"+buyerorderId+"' and a.StyleId='"+styleId+"' and a.ItemId='"+itemId+"' and a.date='"+layoutDate+"' and type='"+layoutType+"'";
      	System.out.println("sql "+Sql);
      	
		String jrxmlFile = session.getServletContext().getRealPath("WEB-INF/jasper/production/LayoutPlanDetailsReport.jrxml");
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