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
	System.out.println("report = "+request.getAttribute("poNo"));
	String poNo = request.getAttribute("poNo").toString();
	String supplierId = request.getAttribute("supplierId").toString();
	String type = request.getAttribute("type").toString();
	
    try {

    	HashMap map=new HashMap();
		Session dbsession=HibernateUtil.openSession();
		
		
		String sql = "select name,Telephone,SupplierAddress,ContactPerson from tbSupplier where id='"+supplierId+"'";
		List<?> list = dbsession.createSQLQuery(sql).list();
		for(Iterator<?> iter = list.iterator(); iter.hasNext();)
		{	
			Object[] element = (Object[]) iter.next();
			map.put("supName",element[0].toString());
			map.put("supPhone",element[1].toString());
			map.put("supAddress",element[2].toString());
			map.put("SupplierContactPerson",element[3].toString());
		}

		sql = "select FactoryName,TelePhone,Address from TbFactoryInfo where Factoryid=(select billTo from tbPurchaseOrderSummary  where pono='"+poNo+"')";
		list = dbsession.createSQLQuery(sql).list();
		for(Iterator<?> iter = list.iterator(); iter.hasNext();)
		{	
			Object[] element = (Object[]) iter.next();
			map.put("headFactoryName",element[0].toString());
			map.put("headFactoryPhone",element[1].toString());
			map.put("headFactoryAddress",element[2].toString());
		}

		sql = "select FactoryName,TelePhone,Address from TbFactoryInfo where Factoryid=(select deliveryto from tbPurchaseOrderSummary  where pono='"+poNo+"')";
		list = dbsession.createSQLQuery(sql).list();
		for(Iterator<?> iter = list.iterator(); iter.hasNext();){
			Object[] element = (Object[]) iter.next();
			map.put("FactoryName",element[0].toString());
			map.put("FactoryPhone",element[1].toString());
			map.put("FactoryAddress",element[2].toString());
		}


		
		
		String report="";
		if(type.equalsIgnoreCase("Accessories")) {
			
			sql="select 'only' as Taka,b.ShippingMarks, "+
					"(select StyleNo from  TbStyleCreate where StyleId=b.styleid) as StyleNo, "+
					"(select ItemName from TbAccessoriesItem where itemid=b.accessoriesItemId) as AccessorisItem, "+
					"(select colorName from tbColors where ColorId=b.ColorId) as ColorName,b.accessoriesSize,b.size, "+
					"(select unitname from tbunits where UnitId=b.UnitId) as UnitName, "+
					"b.TotalQty,b.RequireUnitQty,b.rate,b.dolar,b.amount ,b.currency,b.poManual,a.orderDate,deliveryDate, "+
					"(select MerchendiserName from TbMerchendiserInfo  where MerchendiserId=a.orderby)  "+
					"OrderBy,(select Mobile from TbMerchendiserInfo  where MerchendiserId=a.orderby) as MerMobile, "+
					"(select Email from TbMerchendiserInfo  where MerchendiserId=a.orderby) as MerEmail,a.Note,a.Subject,a.ManualPo, "+
					"(select Signature from TbMerchendiserInfo where MUserId=b.IndentPostBy) as Signature, "+
					"(select Signature from TbMerchendiserInfo where MUserId='1') as MdSignature,b.mdapproval "+ 
					"from tbPurchaseOrderSummary a  "+
					"join tbAccessoriesIndent b  "+
					"on a.pono=b.pono   "+
					"where  a.pono='"+poNo+"' and b.supplierid = '"+supplierId+"'  "+
					"order by b.styleid,b.PurchaseOrder,b.Itemid,b.accessoriesItemId,b.ColorId,b.ShippingMarks,b.SizeSorting asc";
			
		}else if(type.equalsIgnoreCase("Fabrics")) {
			sql="select 'only' as Taka ,' ' as ShippingMarks,(select StyleNo from  TbStyleCreate where StyleId=b.styleid) as StyleNo, "+
					"(select ItemName from TbFabricsItem where id=b.fabricsid) as AccessorisItem, "+
					"(select colorName from tbColors where ColorId=b.fabricscolor) as ColorName,'' as accessoriesSize,'' as size, "+
					"(select unitname from tbunits where UnitId=b.UnitId) as UnitName,b.TotalQty,b.TotalQty as RequireUnitQty,b.dolar,b.rate,b.dolar,b.amount,b.currency,b.poManual,a.orderDate,deliveryDate, "+
					"(select MerchendiserName from TbMerchendiserInfo  where MerchendiserId=a.orderby) OrderBy, "+
					"(select Mobile from TbMerchendiserInfo  where MerchendiserId=a.orderby) as MerMobile, "+
					"(select Email from TbMerchendiserInfo  where MerchendiserId=a.orderby) as MerEmail,a.Note,a.Subject,a.ManualPo, "+
					"(select Signature from TbMerchendiserInfo where MUserId=b.entryby) as Signature, "+
					"(select Signature from TbMerchendiserInfo where MUserId='1011') as MdSignature,b.mdapproval "+ 
					"from tbPurchaseOrderSummary a  "+
					"join tbFabricsIndent b on a.pono=b.pono "+  
					"where a.pono='"+poNo+"' and b.supplierid='"+supplierId+"' "+
					"order by  b.styleid,b.PurchaseOrder,b.Itemid,b.fabricsid asc ";
			

		}
    	
    	System.out.println(sql);
        SpringRootConfig sp=new SpringRootConfig();
        
       	String jrxmlFile = session.getServletContext().getRealPath("WEB-INF/jasper/order/SupplierWisePurchaseOrder.jrxml");
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