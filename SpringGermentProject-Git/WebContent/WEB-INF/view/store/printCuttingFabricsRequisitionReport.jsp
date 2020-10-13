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
		
    	String cuttingEntryId=(String)request.getAttribute("cuttingEntryId");
	
    	
        SpringRootConfig sp=new SpringRootConfig();
        
		String Sql="select a.CuttingEntryId,(select Name from tbBuyer where id=a.BuyerId) as BuyerName,a.purchaseOrder,(select StyleNo from TbStyleCreate where StyleId=a.StyleId) as StyleName,(select ItemName from tbItemDescription where ItemId=a.ItemId) as ItemName,(select ItemName from TbFabricsItem where id=b.fabricsId) as FabricsItem,(select colorName from tbColors where colorId=b.colorId) as ColorName,b.requisitionQuantity,(select unitName from tbunits where unitId=b.unitId) as UnitName,a.CuttingNo,convert(varchar,a.Date,23) as Date from TbCuttingInformationSummary a join TbCuttingRequisitionDetailsv1 b on a.CuttingEntryId=b.CuttingEntryId where a.CuttingEntryId='"+cuttingEntryId+"'";
      	System.out.println("sql "+Sql);
      	
		String jrxmlFile = session.getServletContext().getRealPath("WEB-INF/jasper/store/CuttingFabricsRequisitionReport.jrxml");
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