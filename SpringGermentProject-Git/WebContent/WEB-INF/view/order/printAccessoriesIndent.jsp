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
		
    	String AiNo=(String)request.getAttribute("AiNo");

	
    	
        SpringRootConfig sp=new SpringRootConfig();
        
		String Sql="select a.AINo,a.PurchaseOrder,a.ShippingMarks,(select StyleNo from TbStyleCreate where StyleId=a.styleid) as StyleNo,(select ItemName from tbItemDescription where ItemId=a.Itemid) as ItemName,(select colorName from tbColors where ColorId=a.ColorId) as Color,ISNULL((select name from tbbrands where id=a.IndentBrandId),'') as BrandName,(select ItemName from TbAccessoriesItem where Itemid=a.accessoriesItemId) as AccessoriesName,a.accessoriesSize,(select SizeName from tbStyleSize where id=a.size) as SizeName,(select UnitName from tbunits where UnitId=a.UnitId) as UnitName,a.TotalQty from tbAccessoriesIndent a where AiNo ='"+AiNo+"' order by a.ColorId,a.accessoriesItemId,a.size";
      	System.out.println("sql "+Sql);
      	
		String jrxmlFile = session.getServletContext().getRealPath("WEB-INF/jasper/order/AccessoriesIndent.jrxml");
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