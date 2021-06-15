
<%@page import="java.awt.Image"%>
<%@page import="java.util.Iterator"%>
<%@page import="pg.share.HibernateUtil"%>
<%@page import="org.hibernate.Transaction"%>
<%@page import="org.hibernate.Session"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
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
    	
    	String masterLC=(String)request.getAttribute("masterLC");
    	
        SpringRootConfig sp=new SpringRootConfig();
        
        String Sql= "";
        String jrxmlFile="";
        
        
        HashMap map=new HashMap();
    	List<HashMap<String,String>> datalist=new ArrayList<HashMap<String,String>>();
    	Session dbsession=HibernateUtil.openSession();
		Transaction tx=null;
		
		/* Sql="select pbs.*,pbd.col1,pbd.col2,pbd.col3,pbd.col4,pbd.col5,pbd.col6,pbd.col7,pbd.col8,pbd.col9,pbd.col10,pbd.col11,pbd.col12,pbd.col13,pbd.col14,pbd.col15,pbd.col16,pbd.col17,pbd.col18,pbd.col19 \r\n"+ 
				"from tbPassBookSummary pbs \r\n"+ 
				"join tbPassBookData pbd \r\n"+ 
				"on pbs.masterLCNo = pbd.masterLc \r\n"+  
				"where pbs.masterLCNo = '"+masterLC+"'"; */
		Sql = "select * from tbPassBookSummary where masterLCNo = '"+masterLC+"'";
      	System.out.println("sql "+Sql);
      	
		jrxmlFile = session.getServletContext().getRealPath("WEB-INF/jasper/commercial/PassBookSummaryReportNew.jrxml");
       
        
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