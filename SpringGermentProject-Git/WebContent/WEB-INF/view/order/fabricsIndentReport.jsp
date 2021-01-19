<%@page import="pg.orderModel.FabricsIndent"%>
<%@ page contentType="application/pdf"%>

<%@ page trimDirectiveWhitespaces="true"%>


<%@ page import="net.sf.jasperreports.engine.design.JRDesignQuery"%>
<%@ page import="net.sf.jasperreports.engine.design.JasperDesign"%>
<%@ page import="net.sf.jasperreports.engine.xml.JRXmlLoader"%>
<%@page import="net.sf.jasperreports.engine.JRExporterParameter"%>
<%@page
	import="net.sf.jasperreports.engine.data.JRBeanCollectionDataSource"%>
<%@page import="net.sf.jasperreports.engine.JasperCompileManager"%>

<%@ page import="net.sf.jasperreports.engine.*"%>

<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.HashMap"%>
<%@ page import="pg.config.*"%>
<%@page import="org.hibernate.Session"%>
<%@page import="org.hibernate.Transaction"%>
<%@page import="pg.share.HibernateUtil"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>

<%@ page import="java.io.File"%>
<%@ page import="java.io.FileInputStream"%>
<%@ page import="java.io.FileNotFoundException"%>
<%@ page import="java.io.InputStream"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.DriverManager"%>
<%@ page import="java.sql.SQLException"%>




<%

	
	
	
	ArrayList<FabricsIndent> list=new ArrayList<FabricsIndent>();
	list = (ArrayList<FabricsIndent>) request.getAttribute("list");
	
	String indentId=(String) request.getAttribute("indentId");
    try {
    	SpringRootConfig sp=new SpringRootConfig();
    	List<HashMap<String,String>> datalist=new ArrayList<HashMap<String,String>>();
    	
    	Session dbsession=HibernateUtil.openSession();
		Transaction tx=null;
        
	
		String Sql="select ai.id,isnull(sc.StyleNo,'') as styleno,isnull(id.itemname,'') as itemname,isnull(itemC.Colorname,'') as itemcolor,fi.ItemName as FabricsItemName,fabricsC.Colorname as fabricscolor,b.name as brand,ai.width,ai.GSM,ai.Yard,ai.qty,ai.dozenqty,ai.consumption,ai.TotalQty,ai.RequireUnitQty,u.unitname as unit,mi.Signature as Signature  \r\n"+  
				"from tbFabricsIndent ai "+
				"left join TbStyleCreate sc  "+
				"on ai.styleId = cast(sc.StyleId as varchar) "+
				"left join tbItemDescription id  "+
				"on ai.itemid = cast(id.itemid as varchar) "+
				"left join tbColors itemC  "+
				"on ai.itemcolor = cast(itemC.ColorId as varchar)"+
				"left join TbFabricsItem fi \r\n"+
				"on ai.fabricsid = fi.id \r\n"+
				"left join tbColors fabricsC \r\n"+
				"on ai.fabricscolor = fabricsC.ColorId \r\n"+
				"left join tbbrands b \r\n"+
				"on ai.brand = b.id  \r\n"+
				"left join tbunits u \r\n"+
				"on ai.unitId = u.Unitid \r\n"+
				"left join TbMerchendiserInfo mi \r\n"+
				"on ai.entryby = mi.MUserId \r\n"+
				"where ai.indentId = '"+indentId+"' "+
				"order by ai.fabricsid,ai.fabricscolor";
    	System.out.println("Query "+Sql);
    	
        
		String jrxmlFile = session.getServletContext().getRealPath("WEB-INF/jasper/order/FabricsIndent.jrxml");
        InputStream input = new FileInputStream(new File(jrxmlFile));

    	JasperDesign jd=JRXmlLoader.load(input);
		JRDesignQuery jq=new JRDesignQuery();
		
		jq.setText(Sql);
		jd.setQuery(jq);
        JasperReport jr = JasperCompileManager.compileReport(jd);  
        JasperPrint jp = JasperFillManager.fillReport(jr, null, sp.getDataSource().getConnection());
        JasperExportManager.exportReportToPdfStream(jp, response.getOutputStream());
		
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (JRException e) {
        e.printStackTrace();
    } 
    


%>