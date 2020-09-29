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
    	String styleId=(String)request.getAttribute("styleId");
    	String buyerorderId=(String)request.getAttribute("buyerorderId");

        SpringRootConfig sp=new SpringRootConfig();
        
		String Sql="select (select name from tbBuyer where id=a.BuyerId) as BuyerName,a.BuyerId,a.BuyerOrderId,a.PoNo,(select StyleNo from TbStyleCreate where StyleId=a.StyleId) as StyleNo,a.styleId,(select ItemName from tbItemDescription where ItemId=a.ItemId) as ItemName,a.ItemId,convert(varchar,a.shipDate,23) as shipDate,a.OrderQty,a.PlanQty,a.FileSample,a.PPStatus,a.AccessoriesInhouseStatus,a.FabricsInhouseStatus,convert(varchar,a.StartDate,23) as StartDate,convert(varchar,a.EndDate,23) as EndDate from TbProductTargetPlan a where a.BuyerId='"+buyerId+"' and a.BuyerOrderId='"+buyerorderId+"' and a.StyleId='"+styleId+"'";
      	System.out.println("sql "+Sql);
      	
		String jrxmlFile = session.getServletContext().getRealPath("WEB-INF/jasper/production/ProductionPlanReport.jrxml");
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