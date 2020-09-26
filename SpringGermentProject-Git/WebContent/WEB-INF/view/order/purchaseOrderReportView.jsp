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
	String poNo = session.getAttribute("poNo").toString();
	String supplierId = session.getAttribute("supplierId").toString();
	String type = session.getAttribute("type").toString();
	
	
    try {

    	
        SpringRootConfig sp=new SpringRootConfig();
        
		String Sql="select b.BuyerId,b.PurchaseOrder,(select StyleNo from TbStyleCreate where StyleId=b.StyleId) as StyleNo,(select ItemName from tbItemDescription where itemid=b.ItemId) as ItemName,b.DepartmentId,b.LineId,b.CuttingNo,b.CuttingDate,a.CuttingEntryId,a.SizeGroupId from TbCuttingInformationDetails a join TbCuttingInformationSummary b on b.CuttingEntryId=a.CuttingEntryId where a.CuttingEntryId='5' group by a.SizeGroupId,b.BuyerId,b.PurchaseOrder,b.StyleId,b.ItemId,b.DepartmentId,b.LineId,b.CuttingNo,b.CuttingDate,a.CuttingEntryId";
       	String jrxmlFile = session.getServletContext().getRealPath("WEB-INF/jasper/production/CuttingInformationRpt.jrxml");
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