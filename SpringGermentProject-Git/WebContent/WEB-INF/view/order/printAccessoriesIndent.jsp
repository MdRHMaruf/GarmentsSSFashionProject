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
        
		String Sql="select ai.AINo,ai.PurchaseOrder,ai.ShippingMarks,isnull(sc.StyleNo,'')as StyleNo,ISNULL(id.itemname,'') as ItemName,ISNULL(c.colorName,'')as Color, "+
				"ISNULL(b.name,'') as BrandName,ISNULL(aItem.itemname,'') as AccessoriesName,ai.accessoriesSize,ISNULL(ss.sizeName,'') as SizeName,ISNULL(u.unitname,'') as UnitName,ai.TotalQty "+
				"from tbAccessoriesIndent ai  "+
				"left join TbStyleCreate sc "+
				"on ai.styleid = cast(sc.StyleId as varchar) "+
				"left join tbItemDescription id "+
				"on ai.Itemid = cast(id.itemid as varchar) "+
				"left join tbColors c "+
				"on ai.ColorId = cast(c.colorId as varchar) "+
				"left join tbbrands b "+
				"on ai.IndentBrandId = b.id "+
				"left join TbAccessoriesItem aItem "+
				"on ai.accessoriesItemId = aItem.itemid "+
				"left join tbStyleSize ss "+
				"on ai.size = ss.id "+
				"left join tbunits u "+
				"on ai.UnitId = u.Unitid "+
				"where ai.AINo = '"+AiNo+"' "+
				"order by ai.ColorId,ai.accessoriesItemId,ai.size";
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