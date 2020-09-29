<%@page import="pg.orderModel.SampleCadAndProduction"%>
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

	
	
	
	ArrayList<SampleCadAndProduction> list=new ArrayList<SampleCadAndProduction>();
	list = (ArrayList<SampleCadAndProduction>) request.getAttribute("list");
	
	String id=(String) request.getAttribute("id");
		
	
	
	
	
	
    try {
    	
    	SpringRootConfig sp=new SpringRootConfig();
    	
    
    	List<HashMap<String,String>> datalist=new ArrayList<HashMap<String,String>>();
    	
    	Session dbsession=HibernateUtil.openSession();
		Transaction tx=null;
        
	
		String Sql="select a.SampleCommentId,(select StyleNo from TbStyleCreate where StyleId=a.StyleId) as StyleNo,a.PurchaseOrder,(select Itemname from tbItemDescription where itemid=a.ItemId) as ItemName,(select ColorName from tbColors where ColorId=a.ColorId) as ColorName,a.Size,(select Name from TbSampleTypeInfo where AutoId=a.SampleTypeId) as SampleType,a.FeedbackComments,a.PatternMakingDate,a.PatternMakingDespatch,a.PatternMakingReceived,a.PatternCorrectionDate,a.PatternCorrectionDespatch,a.PatternCorrectionReceived,a.PatternGradingDate,a.PatternGradingDespatch,a.PatternGradingReceived,a.PatternMarkingDate,a.PatternMarkingDespatch,a.PatternMarkingReceived from TbSampleCadInfo a where a.SampleCommentId='"+id+"' order by PurchaseOrder,StyleId,ItemId,ColorId,SampleTypeId";
    	System.out.println("Query "+Sql);
    	
        
		String jrxmlFile = session.getServletContext().getRealPath("WEB-INF/jasper/order/SampleCad.jrxml");
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