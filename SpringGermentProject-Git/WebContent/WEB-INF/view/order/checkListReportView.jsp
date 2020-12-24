<%@page import="pg.orderModel.ParcelModel"%>
<%@ page contentType="application/pdf" %>

<%@ page trimDirectiveWhitespaces="true"%>


<%@ page import="net.sf.jasperreports.engine.design.JRDesignQuery" %>
<%@ page import="net.sf.jasperreports.engine.design.JasperDesign" %>
<%@ page import="net.sf.jasperreports.engine.xml.JRXmlLoader" %>
<%@page import="net.sf.jasperreports.engine.JRExporterParameter"%>
<%@page import="net.sf.jasperreports.engine.data.JRBeanCollectionDataSource"%>
<%@page import="net.sf.jasperreports.engine.JasperCompileManager"%>

<%@ page import="net.sf.jasperreports.engine.*" %>

<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.HashMap"%>
<%@ page import="pg.config.*" %>
<%@page import="org.hibernate.Session"%>
<%@page import="org.hibernate.Transaction"%>
<%@page import="pg.share.HibernateUtil"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>

<%@ page import="java.io.File" %>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="java.io.FileNotFoundException" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.SQLException" %>




<%
	ArrayList<ParcelModel> list=new ArrayList<ParcelModel>();
	
	String id=(String) request.getAttribute("id");
		
	
	
	
	
	
    try {
    	
    	SpringRootConfig sp=new SpringRootConfig();
    	
    
    	List<HashMap<String,String>> datalist=new ArrayList<HashMap<String,String>>();
    	
    	Session dbsession=HibernateUtil.openSession();
		Transaction tx=null;
        
	
		String Sql="select ac.autoId,ac.buyerId,ac.remarks,b.name as buyerName,acd.styleId,acd.purchaseOrderId,acd.colorId,isnull(c.Colorname,'')as colorName,acd.sizeId,isnull(ss.sizeName,'')as sizeName,acd.sampleId,sti.Name as sampleType,acd.itemType,acd.itemId,isnull(fi.ItemName,'') as fabricsName,isnull(ai.ItemName,'') as accessoriesName,acd.quantity,acd.status,ac.remarks \r\n"+ 
				"from tbAccCheck ac \r\n"+
				"left join tbAccCheckDetails acd \r\n"+
				"on ac.autoId = acd.checkListId \r\n"+
				"left join tbBuyer b \r\n"+
				"on ac.buyerId = b.id \r\n"+
				"left join TbFabricsItem fi \r\n"+
				"on acd.itemId = fi.id and acd.itemType='1' \r\n"+
				"left join TbAccessoriesItem ai \r\n"+
				"on acd.itemId = ai.itemid and acd.itemType='2' \r\n"+
				"left join tbColors c \r\n"+
				"on acd.colorId = c.ColorId \r\n"+
				"left join tbStyleSize ss \r\n"+
				"on acd.sizeId = ss.id \r\n"+ 
				"left join TbSampleTypeInfo sti \r\n"+
				"on acd.sampleId = sti.AutoId \r\n"+
				"where ac.autoId= '"+id+"'";
    	System.out.println("Query "+Sql);
    	
        
		String jrxmlFile = session.getServletContext().getRealPath("WEB-INF/jasper/order/CheckListReport.jrxml");
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
		
		
		
        //Generating the report
      
		
		

    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (JRException e) {
        e.printStackTrace();
    }
%>