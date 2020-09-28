<%@page import="java.util.Iterator"%>
<%@page import="pg.share.HibernateUtil"%>
<%@page import="org.hibernate.Session"%>
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
	
	String sampleCommentId = request.getAttribute("sampleCommentId").toString();
	String purchaseOrder = request.getAttribute("purchaseOrder").toString();
	String styleId = request.getAttribute("styleId").toString();
	String itemId = request.getAttribute("itemId").toString();
	String sampleTypeId = request.getAttribute("sampleTypeId").toString();
	String printType = request.getAttribute("printType").toString();
	
    try {
    	
    	System.out.println(sampleCommentId+" "+purchaseOrder+" "+styleId + " "+itemId+" "+sampleTypeId + " "+ printType );

    	HashMap map=new HashMap();
		Session dbsession=HibernateUtil.openSession();
		
		
		String sql="";
		if(printType.equals("styleWise")){
			sql="select a.SampleCommentId,(select StyleNo from TbStyleCreate where StyleId=a.StyleId) as StyleNo,a.PurchaseOrder,(select Itemname from tbItemDescription where itemid=a.ItemId) as ItemName,(select ColorName from tbColors where ColorId=a.ColorId) as ColorName,a.Size,(select Name from TbSampleTypeInfo where AutoId=a.SampleTypeId) as SampleType,a.FeedbackComments,a.CuttingDate,a.CuttingQty,a.PrintSendDate,a.PrintReceivedDate,a.EmbroiderySendDate,a.EmbroideryReceivedDate,a.SewingSendDate,a.SewingFinishedDate,a.OperatorName from TbSampleCadInfo a where a.StyleId='"+styleId+"' and a.PurchaseOrder='"+purchaseOrder+"' order by PurchaseOrder,StyleId,ItemId,ColorId,SampleTypeId";
		}
		else if(printType.equals("itemWise")){
			sql="select a.SampleCommentId,(select StyleNo from TbStyleCreate where StyleId=a.StyleId) as StyleNo,a.PurchaseOrder,(select Itemname from tbItemDescription where itemid=a.ItemId) as ItemName,(select ColorName from tbColors where ColorId=a.ColorId) as ColorName,a.Size,(select Name from TbSampleTypeInfo where AutoId=a.SampleTypeId) as SampleType,a.FeedbackComments,a.CuttingDate,a.CuttingQty,a.PrintSendDate,a.PrintReceivedDate,a.EmbroiderySendDate,a.EmbroideryReceivedDate,a.SewingSendDate,a.SewingFinishedDate,a.OperatorName from TbSampleCadInfo a where a.StyleId='"+styleId+"' and a.PurchaseOrder='"+purchaseOrder+"' and a.ItemId='"+itemId+"' order by PurchaseOrder,StyleId,ItemId,ColorId,SampleTypeId";
		}
		else if(printType.equals("sampleWise")){
			sql="select a.SampleCommentId,(select StyleNo from TbStyleCreate where StyleId=a.StyleId) as StyleNo,a.PurchaseOrder,(select Itemname from tbItemDescription where itemid=a.ItemId) as ItemName,(select ColorName from tbColors where ColorId=a.ColorId) as ColorName,a.Size,(select Name from TbSampleTypeInfo where AutoId=a.SampleTypeId) as SampleType,a.FeedbackComments,a.CuttingDate,a.CuttingQty,a.PrintSendDate,a.PrintReceivedDate,a.EmbroiderySendDate,a.EmbroideryReceivedDate,a.SewingSendDate,a.SewingFinishedDate,a.OperatorName from TbSampleCadInfo a where a.StyleId='"+styleId+"' and a.PurchaseOrder='"+purchaseOrder+"' and a.ItemId='"+itemId+"' and a.SampleTypeId='"+sampleTypeId+"' order by PurchaseOrder,StyleId,ItemId,ColorId,SampleTypeId";
		}
		else if(printType.equals("sizeWise")){
			sql="select a.SampleCommentId,(select StyleNo from TbStyleCreate where StyleId=a.StyleId) as StyleNo,a.PurchaseOrder,(select Itemname from tbItemDescription where itemid=a.ItemId) as ItemName,(select ColorName from tbColors where ColorId=a.ColorId) as ColorName,a.Size,(select Name from TbSampleTypeInfo where AutoId=a.SampleTypeId) as SampleType,a.FeedbackComments,a.CuttingDate,a.CuttingQty,a.PrintSendDate,a.PrintReceivedDate,a.EmbroiderySendDate,a.EmbroideryReceivedDate,a.SewingSendDate,a.SewingFinishedDate,a.OperatorName from TbSampleCadInfo a where a.SampleCommentId='"+sampleCommentId+"' order by PurchaseOrder,StyleId,ItemId,ColorId,SampleTypeId";
		}


		
		
		
		
    	
    	System.out.println(sql);
        SpringRootConfig sp=new SpringRootConfig();
        
       	String jrxmlFile = session.getServletContext().getRealPath("WEB-INF/jasper/order/SampleProduction.jrxml");
        InputStream input = new FileInputStream(new File(jrxmlFile));

        
    	JasperDesign jd=JRXmlLoader.load(input);
		JRDesignQuery jq=new JRDesignQuery();
		
		jq.setText(sql);
		jd.setQuery(jq);
		
        //Generating the report
        JasperReport jr = JasperCompileManager.compileReport(jd);
      
        JasperPrint jp = JasperFillManager.fillReport(jr, map, sp.getDataSource().getConnection());

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