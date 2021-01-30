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
        
	
		String Sql="select a.indentId,a.buyerId,a.PurchaseOrder,a.StyleId,sc.StyleNo,a.ItemId,id.itemname,a.ColorId,c.Colorname,a.ShippingMarks,a.sizeId,isnull(ss.sizeName,'')as sizeName,a.accessoriesItemId,ai.itemname as accessoriesName,a.cartonSize,a.Ply,a.type,a.OrderQty,a.Length1,a.Width1,a.Height1,a.Add1,a.Add2,a.UnitId,u.unitname,a.DivideBy,a.cbm,a.Qty,a.IndentPostBy,a.autoId \r\n"+ 
				"from tbAccessoriesIndentForCarton a  \r\n"+
				"left join TbStyleCreate sc \r\n"+
				"on a.styleid = sc.StyleId \r\n"+ 
				"left join tbItemDescription id \r\n"+
				"on a.Itemid = id.itemid \r\n"+
				"left join tbColors c \r\n"+
				"on a.ColorId = c.ColorId \r\n"+
				"left join tbStyleSize ss \r\n"+
				"on a.sizeId = ss.id \r\n"+
				"left join TbAccessoriesItem ai \r\n"+
				"on a.accessoriesItemId = ai.itemid \r\n"+
				"left join tbunits u \r\n"+
				"on a.UnitId = u.Unitid \r\n"+
				"where a.indentId='"+indentId+"'";
    	System.out.println("Query "+Sql);
    	
        
		String jrxmlFile = session.getServletContext().getRealPath("WEB-INF/jasper/order/CartonIndent.jrxml");
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