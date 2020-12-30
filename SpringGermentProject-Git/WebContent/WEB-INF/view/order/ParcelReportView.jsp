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
	list = (ArrayList<ParcelModel>) request.getAttribute("list");
	
	String id=(String) request.getAttribute("id");
		
	
	
	
	
	
    try {
    	
    	SpringRootConfig sp=new SpringRootConfig();
    	
    
    	List<HashMap<String,String>> datalist=new ArrayList<HashMap<String,String>>();
    	
    	Session dbsession=HibernateUtil.openSession();
		Transaction tx=null;
        
	
		String Sql="select p.autoId,b.name as buyerName,c.name as courierName,c.Telephone,c.MobileNo,c.Fax,c.Email,c.SkypeId,c.CourierAddress,p.trackingNo,p.dispatchedDate,p.deliveryBy,p.deliveryTo,p.mobileNo,u.unitname,p.grossWeight,p.rate,p.amount,sc.StyleNo,pd.purchaseOrder,color.Colorname,ss.sizeName,sti.Name as sampleType,pd.quantity "+
				"from tbparcel p "+
				"left join tbParcelDetails pd "+
				"on p.autoId = pd.parcelId "+
				"left join tbBuyer b "+
				"on p.buyerId = b.id "+
				"left join tbCourier c "+
				"on p.courierId = c.id "+
				"left join tbunits u "+
				"on p.unitId = u.Unitid "+ 
				"left join TbStyleCreate sc "+
				"on pd.styleId = sc.StyleId "+
				"left join tbColors color "+
				"on pd.colorId = color.ColorId "+
				"left join tbStyleSize ss "+
				"on pd.sizeId = ss.id "+
				"left join TbSampleTypeInfo sti "+
				"on pd.sampleId = sti.AutoId "+
				"where p.autoId = '"+id+"'";
    	System.out.println("Query "+Sql);
    	
        
		String jrxmlFile = session.getServletContext().getRealPath("WEB-INF/jasper/order/PercelReport.jrxml");
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