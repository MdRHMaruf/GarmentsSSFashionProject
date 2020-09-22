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
		
    	String InvoiceNo=(String)request.getAttribute("InvoiceNo");
    	String Type=(String)request.getAttribute("Type");
	
    	
        SpringRootConfig sp=new SpringRootConfig();
        
		String Sql="select (select ItemName from tbStoreItemInformation where ItemId=b.ItemId) as ItemName,(select UnitName from tbunits where UnitId=b.Unit) as UnitName,b.Qty,b.buyPrice,b.totalPrice,a.InvoiceNo,(select Name from tbSupplier where id=a.PersionId)as SupplierName,a.ChallanNo,a.date from TbStoreTransectionInvoice a join TbStoreTransectionDetails b on a.InvoiceNo=b.InvoiceNO and a.Type=b.Type where a.InvoiceNo='"+InvoiceNo+"' and a.type='"+Type+"'";
      	System.out.println("sql "+Sql);
      	
		String jrxmlFile = session.getServletContext().getRealPath("WEB-INF/jasper/store/GeneralReceivedInvoice.jrxml");
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