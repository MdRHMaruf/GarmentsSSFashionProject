<%@page import="pg.orderModel.FabricsIndent"%>
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

	
	
	
	ArrayList<FabricsIndent> list=new ArrayList<FabricsIndent>();
	list = (ArrayList<FabricsIndent>) request.getAttribute("list");
	
	String poid=(String) request.getAttribute("poid");
	String styleid=(String) request.getAttribute("styleid");
	String itemid=(String) request.getAttribute("itemid");
	
	
	
	
	
	
	
    try {
    	
    	SpringRootConfig sp=new SpringRootConfig();
    	
    
    	List<HashMap<String,String>> datalist=new ArrayList<HashMap<String,String>>();
    	
    	Session dbsession=HibernateUtil.openSession();
		Transaction tx=null;
        
	
		String Sql="select a.id,(select styleno from TbStyleCreate where styleid=a.styleId) as styleno,(select itemname from tbitemdescription where itemid=a.itemid) as itemname,(select colorname from tbcolors where ColorId=a.itemcolor) as itemcolor,(select ItemName from tbfabricsItem where id=a.fabricsid) as FabricsItemName,(select colorname from tbcolors where ColorId=a.fabricscolor) as fabricscolor,(select name from tbbrands where id=a.brand) as brand,a.width,a.GSM,a.Yard,a.qty,a.dozenqty,a.consumption,a.TotalQty,a.RequireUnitQty,(select unitname from tbunits where Unitid=a.unitId) as unit,(select Signature from TbMerchendiserInfo where MUserId=a.entryby) as Signature  from tbFabricsIndent a where a.PurchaseOrder=(select PurchaseOrder from TbBuyerOrderEstimateDetails where BuyerOrderId='"+poid+"' group by PurchaseOrder) and  a.styleid='"+styleid+"' and a.itemid='"+itemid+"'";
    	System.out.println("Query "+Sql);
    	
        
		String jrxmlFile = session.getServletContext().getRealPath("WEB-INF/jasper/order/FabricsIndent.jrxml");
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