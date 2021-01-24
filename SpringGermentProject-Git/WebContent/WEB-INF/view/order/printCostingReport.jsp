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
		
    	String StyleId=(String)request.getAttribute("StyleId");
    	String ItemId=(String)request.getAttribute("ItemId");
    	String costingNo=(String)request.getAttribute("costingNo");
    	
        SpringRootConfig sp=new SpringRootConfig();
        
		String Sql="select *,(select frontpic from tbStyleWiseItem where styleid='"+StyleId+"' and ItemId=(select ItemId from tbItemDescription where ItemName=a.ItemName) )as frontpic,(select backpic from tbStyleWiseItem where styleid='"+StyleId+"' and ItemId=(select ItemId from tbItemDescription where ItemName=a.ItemName)) as backpic from funCostingForStyleWiseItem('"+StyleId+"','"+ItemId+"','"+costingNo+"') a order by a.ItemName desc";
      	System.out.println("sql "+Sql);
      	
		String jrxmlFile = session.getServletContext().getRealPath("WEB-INF/jasper/order/CostingCreateReport.jrxml");
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