<%@page import="net.sf.jasperreports.engine.export.JRXlsExporter"%>
<%@ page contentType="application/pdf" %>

<%@ page trimDirectiveWhitespaces="true"%>


<%@ page import="net.sf.jasperreports.engine.design.JRDesignQuery" %>
<%@ page import="net.sf.jasperreports.engine.design.JasperDesign" %>
<%@ page import="net.sf.jasperreports.engine.xml.JRXmlLoader" %>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="org.hibernate.Session"%>
<%@page import="org.hibernate.Transaction"%>
<%@page import="pg.share.HibernateUtil"%>
<%@page import="java.util.Iterator"%>
<%@ page import="net.sf.jasperreports.engine.*" %>
<%@ page import="java.io.File" %>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="java.io.FileNotFoundException" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="pg.config.*" %>

<%

    try {
    	
    	HashMap map=new HashMap();
		
    	String BuyerPoId=(String)request.getAttribute("BuyerPoId");

	
    	
        SpringRootConfig sp=new SpringRootConfig();
        
    	Session dbsession=HibernateUtil.openSession();
        
		String Sql="select name,Telephone,MobileNo,Fax,Email,BuyerAddress,NutifyAddress from tbBuyer where id=(select BuyerId from TbBuyerOrderEstimateSummary where AutoId='"+BuyerPoId+"')";
		
		List<?> list = dbsession.createSQLQuery(Sql).list();
		
		for(Iterator<?> iter = list.iterator(); iter.hasNext();)
		{
			Object[] element = (Object[]) iter.next();
			
			map.put("BuyerName",element[0].toString());
			map.put("BuyerTelePhone",element[1].toString());
			map.put("BuyerMobile",element[2].toString());
			map.put("BuyerFax",element[3].toString());
			map.put("BuyerEmail",element[4].toString());
			map.put("BuyerAddress",element[5].toString());
			map.put("NotifyAddress",element[6].toString());
		}
        
		Sql="select FactoryName,TelePhone,Mobile,Fax,Email,Address from TbFactoryInfo where FactoryId=(select FactoryId from TbBuyerOrderEstimateDetails where BuyerOrderId='"+BuyerPoId+"' group by BuyerOrderId,FactoryId)";
		
		List<?> list1 = dbsession.createSQLQuery(Sql).list();

		for(Iterator<?> iter = list.iterator(); iter.hasNext();)
		{
			Object[] element = (Object[]) iter.next();
			
			map.put("FactoryName",element[0].toString());
			map.put("FactoryTelePhone",element[1].toString());
			map.put("FactoryMobile",element[2].toString());
			map.put("FactoryFax",element[3].toString());
			map.put("FactoryEmail",element[4].toString());
			map.put("FactoryAddress",element[5].toString());
		}
		

        
		Sql="select (select organizationLogo from tbOrganizationInfo where organizationId='1') as OrgLogo,b.shipmentDate,b.PaymentTerm,b.Currency,b.note,b.QCFor,b.remarks,a.BuyerOrderId,a.PurchaseOrder,a.CustomerOrder,g.name,a.ShippingMarks,a.SizeReg,d.StyleNo,e.ItemId,e.itemname,a.sizeGroupId,sum(a.TotalUnit) as TotalUnit  from TbBuyerOrderEstimateDetails a join TbBuyerOrderEstimateSummary b on a.BuyerOrderId=b.autoId join TbStyleCreate d on a.StyleId=d.StyleId join tbItemDescription e on a.ItemId=e.itemid join tbBuyer g on a.buyerId=g.id where a.BuyerOrderId='"+BuyerPoId+"' group by b.shipmentDate,b.PaymentTerm,b.Currency,b.note,b.QCFor,b.remarks,e.ItemId,g.name,a.BuyerOrderId,a.PurchaseOrder,a.CustomerOrder,d.StyleNo,a.ShippingMarks,a.SizeReg,e.itemname,a.sizeGroupId   order by a.BuyerOrderId,a.sizeGroupId";
      	System.out.println("sql "+Sql);
      	
		String jrxmlFile = session.getServletContext().getRealPath("WEB-INF/jasper/order/BuyerPoReport.jrxml");
        InputStream input = new FileInputStream(new File(jrxmlFile));

        
    	JasperDesign jd=JRXmlLoader.load(input);
		JRDesignQuery jq=new JRDesignQuery();
		
		jq.setText(Sql);
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