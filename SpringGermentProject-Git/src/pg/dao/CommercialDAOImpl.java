package pg.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.format.datetime.standard.TemporalAccessorPrinter;
import org.springframework.stereotype.Repository;

import pg.Commercial.BillOfEntry;
import pg.Commercial.ExportLC;
import pg.Commercial.ImportLC;
import pg.Commercial.MasterLC;
import pg.Commercial.MasterLC.StyleInfo;
import pg.Commercial.deedOfContacts;
import pg.config.SpringRootConfig;
import pg.orderModel.Style;
import pg.share.HibernateUtil;

@Repository
public class CommercialDAOImpl implements CommercialDAO{
	
	
	@Override
	public boolean masterLCSubmit(MasterLC masterLC) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			
			String sql="insert into tbMasterLC (masterLCNo,amendmentNo,amendmentDate,buyerId,senderBankId,receiverBankId,beneficiaryBankId,throughBankId,date,totalValue,currency,shipmentDate,expiryDate,remarks,entryTime,userId) "
					+ "values('"+masterLC.getMasterLCNo()+"','"+masterLC.getAmendmentNo()+"','"+masterLC.getAmendmentDate()+"','"+masterLC.getBuyerId()+"','"+masterLC.getSenderBankId()+"','"+masterLC.getReceiverBankId()+"','"+masterLC.getBeneficiaryBankId()+"','"+masterLC.getThroughBankId()+"','"+masterLC.getDate()+"','"+masterLC.getTotalValue()+"','"+masterLC.getCurrencyId()+"','"+masterLC.getShipmentDate()+"','"+masterLC.getExpiryDate()+"','"+masterLC.getRemarks()+"',CURRENT_TIMESTAMP,'"+masterLC.getUserId()+"')";
			session.createSQLQuery(sql).executeUpdate();

			sql = "select isnull(max(autoid),0) as maxId from tbMasterLC where masterLCNo='"+masterLC.getMasterLCNo()+"' and buyerId = '"+masterLC.getBuyerId()+"'";
			List<?> list = session.createSQLQuery(sql).list();
			String masterLCId="0";
			if(list.size()>0) {
				masterLCId = list.get(0).toString();
			}
			
			JSONParser jsonParser = new JSONParser();
			JSONObject styleObject = (JSONObject)jsonParser.parse(masterLC.getStyleList());
			JSONArray styleList = (JSONArray) styleObject.get("list");
			
			for(int i=0;i<styleList.size();i++) {
				JSONObject style = (JSONObject) styleList.get(i);
				sql="insert into tbMasterLCDetails (masterLCId,amendmentNo,styleId,styleItemId,purchaseOrderId,quantity,unitPrice,amount,entryTime,userId) \r\n" + 
						"values ('"+masterLCId+"','"+masterLC.getAmendmentNo()+"','"+style.get("styleId")+"','"+style.get("itemId")+"','"+style.get("purchaseOrderId")+"','"+style.get("quantity")+"','"+style.get("unitPrice")+"','"+style.get("amount")+"',CURRENT_TIMESTAMP,'"+style.get("userId")+"');";
				session.createSQLQuery(sql).executeUpdate();
			}
			
			tx.commit();
			return true;

		}
		catch(Exception ee){
			ee.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			
		}

		finally {
			session.close();
		}

		return false;
	}
	
	@Override
	public String masterLCEdit(MasterLC masterLC) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			
			String sql="update tbMasterLC set senderBankId='"+masterLC.getSenderBankId()+"',receiverBankId='"+masterLC.getReceiverBankId()+"',beneficiaryBankId='"+masterLC.getBeneficiaryBankId()+"',throughBankId='"+masterLC.getThroughBankId()+"',totalValue='"+masterLC.getTotalValue()+"',currency='"+masterLC.getCurrencyId()+"',shipmentDate='"+masterLC.getShipmentDate()+"',expiryDate='"+masterLC.getExpiryDate()+"',remarks='"+masterLC.getRemarks()+"' "
					+ " where masterLcNo='"+masterLC.getMasterLCNo()+"' and amendmentNo='"+masterLC.getAmendmentNo()+"'";
			session.createSQLQuery(sql).executeUpdate();

			/*String sql = "select isnull(max(autoid),0) as maxId from tbMasterLC where masterLCNo='"+masterLC.getMasterLCNo()+"' and buyerId = '"+masterLC.getBuyerId()+"'";
			List<?> list = session.createSQLQuery(sql).list();
			String masterLCId="0";
			if(list.size()>0) {
				masterLCId = list.get(0).toString();
			}*/
			
			JSONParser jsonParser = new JSONParser();
			JSONObject styleObject = (JSONObject)jsonParser.parse(masterLC.getStyleList());
			JSONArray styleList = (JSONArray) styleObject.get("list");
			
			for(int i=0;i<styleList.size();i++) {
				JSONObject style = (JSONObject) styleList.get(i);
				sql="insert into tbMasterLCDetails (masterLCId,amendmentNo,styleId,styleItemId,purchaseOrderId,quantity,unitPrice,amount,entryTime,userId) \r\n" + 
						"values ('"+masterLC.getAutoId()+"','"+masterLC.getAmendmentNo()+"','"+style.get("styleId")+"','"+style.get("itemId")+"','"+style.get("purchaseOrderId")+"','"+style.get("quantity")+"','"+style.get("unitPrice")+"','"+style.get("amount")+"',CURRENT_TIMESTAMP,'"+style.get("userId")+"');";
				session.createSQLQuery(sql).executeUpdate();
			}
			
			styleObject = (JSONObject)jsonParser.parse(masterLC.getEditedStyleList());
			styleList = (JSONArray) styleObject.get("list");
			
			for(int i=0;i<styleList.size();i++) {
				JSONObject style = (JSONObject) styleList.get(i);
				sql="update tbMasterLCDetails set quantity='"+style.get("quantity")+"',unitPrice='"+style.get("unitPrice")+"',amount='"+style.get("amount")+"' where autoId = '"+style.get("autoId")+"' " ;
				session.createSQLQuery(sql).executeUpdate();
			}
			
			tx.commit();
			return "success";

		}
		catch(Exception ee){
			ee.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			
		}

		finally {
			session.close();
		}

		return null;
	}
	
	@Override
	public String masterLCAmendment(MasterLC masterLC) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			
			
			String sql = "select (isnull(max(amendmentNo),0))+1 as maxAmendmentNo from tbMasterLC where masterLCNo='"+masterLC.getMasterLCNo()+"' and buyerId = '"+masterLC.getBuyerId()+"'";
			List<?> list = session.createSQLQuery(sql).list();
			String amendtmentNo="0";
			if(list.size()>0){
				amendtmentNo = list.get(0).toString();
			}
			
			
			 sql="insert into tbMasterLC (masterLCNo,amendmentNo,amendmentDate,buyerId,senderBankId,receiverBankId,beneficiaryBankId,throughBankId,date,totalValue,currency,shipmentDate,expiryDate,remarks,entryTime,userId) "
					+ "values('"+masterLC.getMasterLCNo()+"','"+amendtmentNo+"','"+masterLC.getAmendmentDate()+"','"+masterLC.getBuyerId()+"','"+masterLC.getSenderBankId()+"','"+masterLC.getReceiverBankId()+"','"+masterLC.getBeneficiaryBankId()+"','"+masterLC.getThroughBankId()+"','"+masterLC.getDate()+"','"+masterLC.getTotalValue()+"','"+masterLC.getCurrencyId()+"','"+masterLC.getShipmentDate()+"','"+masterLC.getExpiryDate()+"','"+masterLC.getRemarks()+"',CURRENT_TIMESTAMP,'"+masterLC.getUserId()+"')";
			session.createSQLQuery(sql).executeUpdate();

			sql = "select isnull(max(autoid),0) as maxId from tbMasterLC where masterLCNo='"+masterLC.getMasterLCNo()+"' and buyerId = '"+masterLC.getBuyerId()+"'";
			list = session.createSQLQuery(sql).list();
			String masterLCId="0";
			if(list.size()>0){
				masterLCId = list.get(0).toString();
			}
			
			JSONParser jsonParser = new JSONParser();
			JSONObject styleObject = (JSONObject)jsonParser.parse(masterLC.getStyleList());
			JSONArray styleList = (JSONArray) styleObject.get("list");
			
			for(int i=0;i<styleList.size();i++) {
				JSONObject style = (JSONObject) styleList.get(i);
				sql="insert into tbMasterLCDetails (masterLCId,amendmentNo,styleId,styleItemId,purchaseOrderId,quantity,unitPrice,amount,entryTime,userId) \r\n" + 
						"values ('"+masterLCId+"','"+amendtmentNo+"','"+style.get("styleId")+"','"+style.get("itemId")+"','"+style.get("purchaseOrderId")+"','"+style.get("quantity")+"','"+style.get("unitPrice")+"','"+style.get("amount")+"',CURRENT_TIMESTAMP,'"+style.get("userId")+"');";
				session.createSQLQuery(sql).executeUpdate();
			}
			
			tx.commit();
			return "success";

		}
		catch(Exception ee){
			ee.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			
		}

		finally {
			session.close();
		}

		return "success";
	}

	
	@Override
	public List<MasterLC> getMasterLCAmendmentList(String masterLCNo, String buyerId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<MasterLC> dataList=new ArrayList<MasterLC>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql=" select autoId,masterLCNo,buyerId,amendmentNo,convert(varchar,amendmentDate,25) as amendmentDate from tbMasterLC where masterLCNo='"+masterLCNo+"' and buyerId = '"+buyerId+"'";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();

				dataList.add(new MasterLC(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString()));
			}
			tx.commit();
		}
		catch(Exception e){
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		}
		finally {
			session.close();
		}
		return dataList;
	}

	

	@Override
	public List<MasterLC> getMasterLCList() {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<MasterLC> dataList=new ArrayList<MasterLC>();
		MasterLC tempMasterLc = null;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql=" select mlc.masterLCNo,mlc.buyerId,b.name as buyerName,max(mlc.amendmentNo) as maxAmendmentNo,convert(varchar,mlc.date,25)as lcDate \r\n" + 
					"from tbMasterLC mlc\r\n" + 
					"left join tbBuyer b\r\n" + 
					"on mlc.buyerId = b.id\r\n" + 
					"group by mlc.masterLCNo,mlc.buyerId,mlc.date,b.name";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();
				tempMasterLc = new MasterLC();
				tempMasterLc.setMasterLCNo(element[0].toString());
				tempMasterLc.setBuyerId(element[1].toString());
				tempMasterLc.setBuyerName(element[2].toString());
				tempMasterLc.setAmendmentNo(element[3].toString());
				tempMasterLc.setDate(element[4].toString());
				dataList.add(tempMasterLc);
			}
			tx.commit();
		}
		catch(Exception e){
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		}
		finally {
			session.close();
		}
		return dataList;
	}

	@Override
	public MasterLC getMasterLCInfo(String masterLCNo, String buyerId, String amendmentNo) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		MasterLC tempMasterLc = null;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select m.autoId,m.masterLCNo,m.amendmentNo,convert(varchar,m.amendmentDate,25)as amendmentDate,m.buyerId,b.name as buyerName,senderBankId,receiverBankId,beneficiaryBankId,throughBankId,convert(varchar,date,25) as date,totalValue,currency,shipmentDate,expiryDate,m.remarks,m.userId \r\n" + 
					"from tbMasterLC  m \r\n" + 
					"left join tbBuyer b \r\n" + 
					"on m.buyerId = b.id \r\n"
					+ "where masterLCNo = '"+masterLCNo+"' and buyerId = '"+buyerId+"' and amendmentNo='"+amendmentNo+"'";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();
				tempMasterLc = new MasterLC();
				tempMasterLc.setAutoId(element[0].toString());
				tempMasterLc.setMasterLCNo(element[1].toString());
				tempMasterLc.setAmendmentNo(element[2].toString());
				tempMasterLc.setAmendmentDate(element[3].toString());
				tempMasterLc.setBuyerId(element[4].toString());
				tempMasterLc.setBuyerName(element[5].toString());
				tempMasterLc.setSenderBankId(element[6].toString());
				tempMasterLc.setReceiverBankId(element[7].toString());
				tempMasterLc.setBeneficiaryBankId(element[8].toString());
				tempMasterLc.setThroughBankId(element[9].toString());
				tempMasterLc.setDate(element[10].toString());
				tempMasterLc.setTotalValue(element[11].toString());
				tempMasterLc.setCurrencyId(element[12].toString());
				tempMasterLc.setShipmentDate(element[13].toString());
				tempMasterLc.setExpiryDate(element[14].toString());
				tempMasterLc.setRemarks(element[15].toString());
			}
			tx.commit();
		}
		catch(Exception e){
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		}
		finally {
			session.close();
		}
		return tempMasterLc;
	}

	@Override
	public List<StyleInfo> getMasterLCStyles(String masterLCNo, String buyerId, String amendmentNo) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<StyleInfo> dataList=new ArrayList<StyleInfo>();
		StyleInfo tempStyleInfo = null;
		MasterLC master = new MasterLC();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql=" select mld.autoId,mld.masterLCId,mld.amendmentNo,mld.styleId,sc.StyleNo,mld.styleItemId,id.itemname,mld.purchaseOrderId,(select top 1 PurchaseOrder from TbBuyerOrderEstimateDetails boed where boed.BuyerOrderId = mld.purchaseOrderId) as purchaseOrder,mld.quantity,mld.unitPrice,mld.amount from tbMasterLC mlc\r\n" + 
					"left join tbMasterLCDetails mld\r\n" + 
					"on mlc.autoId = mld.masterLCId and mlc.amendmentNo = mld.amendmentNo\r\n" + 
					"left join TbStyleCreate sc\r\n" + 
					"on mld.styleId = sc.StyleId\r\n" + 
					"left join tbItemDescription id\r\n" + 
					"on mld.styleItemId = id.itemid\r\n" + 
					"where mlc.masterLCNo = '"+masterLCNo+"' and mlc.buyerId = '"+buyerId+"' and mlc.amendmentNo='"+amendmentNo+"'";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				tempStyleInfo = master.new StyleInfo(element[0].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString(), element[7].toString(), element[8].toString(), element[9].toString(), element[10].toString(), element[11].toString());
				//tempMasterLc = new StyleInfo(autoId, styleId, styleNo, itemId, itemName, purchaseOrderId, purchaseOrder, quantity, unitPrice, amount);
				
				dataList.add(tempStyleInfo);
			}
			tx.commit();
		}
		catch(Exception e){
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		}
		finally {
			session.close();
		}
		return dataList;
	}
	
	
	@Override
	public boolean importLCSubmit(ImportLC importLC) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			
			String sql="insert into tbimportLC (masterLCNo,invoiceNo,invoiceType,invoiceDate,amendmentNo,amendmentDate,senderBankId,receiverBankId,supplierId,draftAt,maturityDate,proformaInvoiceNo,proformaInvoiceDate,entryTime,entryBy)\r\n" + 
					"values ('"+importLC.getMasterLCNo()+"','"+importLC.getInvoiceNo()+"','"+importLC.getImportLCType()+"','"+importLC.getInvoiceDate()+"','"+importLC.getAmendmentNo()+"','"+importLC.getAmendmentDate()+"','"+importLC.getSenderBank()+"','"+importLC.getReceiverBank()+"','"+importLC.getSupplierId()+"','"+importLC.getDraftAt()+"','"+importLC.getMaturityDate()+"','"+importLC.getProformaInvoiceNo()+"','"+importLC.getProformaInvoiceDate()+"',CURRENT_TIMESTAMP,'"+importLC.getUserId()+"')";
			session.createSQLQuery(sql).executeUpdate();

			sql = "select isnull(max(autoid),0) as maxId from tbimportLC where invoiceNo='"+importLC.getInvoiceNo()+"' and masterLCNo='"+importLC.getMasterLCNo()+"' ";
			List<?> list = session.createSQLQuery(sql).list();
			String importLCId="0";
			if(list.size()>0) {
				importLCId = list.get(0).toString();
			}
			
			JSONParser jsonParser = new JSONParser();
			JSONObject itemObject = (JSONObject)jsonParser.parse(importLC.getItemList());
			JSONArray itemList = (JSONArray) itemObject.get("list");
			
			for(int i=0;i<itemList.size();i++) {
				JSONObject item = (JSONObject) itemList.get(i);
				sql="insert into tbImportLCDetails (importInvoiceAutoId,importInvoiceNo,amendmentNo,styleId,poNo,accessoriesItemId,accessoriesItemType,colorId,size,unitId,width,gsm,totalQty,price,totalValue,entryTime,entryBy)\r\n" + 
						"values ('"+importLCId+"','"+importLC.getInvoiceNo()+"','"+importLC.getAmendmentNo()+"','"+item.get("styleId")+"','"+item.get("poNo")+"','"+item.get("accessoriesItemId")+"','"+item.get("accessoriesItemType")+"','"+item.get("colorId")+"','"+item.get("size")+"','"+item.get("unitId")+"','"+item.get("width")+"','"+item.get("gsm")+"','"+item.get("totalQty")+"','"+item.get("price")+"','"+item.get("totalValue")+"',CURRENT_TIMESTAMP,'"+importLC.getUserId()+"')";
				session.createSQLQuery(sql).executeUpdate();
			}
			
			tx.commit();
			return true;

		}
		catch(Exception ee){
			ee.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			
		}

		finally {
			session.close();
		}

		return false;
	}
	
	@Override
	public String importLCEdit(ImportLC importLC) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			
			String sql="update tbImportLC set invoiceType='"+importLC.getImportLCType()+"',invoiceDate='"+importLC.getInvoiceDate()+"',senderBankId='"+importLC.getSenderBank()+"',receiverBankId='"+importLC.getReceiverBank()+"',supplierId='"+importLC.getSupplierId()+"',draftAt='"+importLC.getDraftAt()+"',maturityDate = '"+importLC.getMaturityDate()+"',proformaInvoiceNo='"+importLC.getProformaInvoiceNo()+"',proformaInvoiceDate='"+importLC.getProformaInvoiceDate()+"' \r\n"
					+ "where autoId = '"+importLC.getAutoId()+"' and masterLCNo = '"+importLC.getMasterLCNo()+"' and invoiceNo = '"+importLC.getInvoiceNo()+"' and amendmentNo = '"+importLC.getAmendmentNo()+"'";
			session.createSQLQuery(sql).executeUpdate();

			
			JSONParser jsonParser = new JSONParser();
			JSONObject itemObject = (JSONObject)jsonParser.parse(importLC.getItemList());
			JSONArray itemList = (JSONArray) itemObject.get("list");
			
			for(int i=0;i<itemList.size();i++) {
				JSONObject item = (JSONObject) itemList.get(i);
				sql="insert into tbImportLCDetails (importInvoiceAutoId,importInvoiceNo,amendmentNo,styleId,poNo,accessoriesItemId,accessoriesItemType,colorId,size,unitId,width,gsm,totalQty,price,totalValue,entryTime,entryBy)\r\n" + 
						"values ('"+importLC.getAutoId()+"','"+importLC.getInvoiceNo()+"','"+importLC.getAmendmentNo()+"','"+item.get("styleId")+"','"+item.get("poNo")+"','"+item.get("accessoriesItemId")+"','"+item.get("accessoriesItemType")+"','"+item.get("colorId")+"','"+item.get("size")+"','"+item.get("unitId")+"','"+item.get("width")+"','"+item.get("gsm")+"','"+item.get("totalQty")+"','"+item.get("price")+"','"+item.get("totalValue")+"',CURRENT_TIMESTAMP,'"+importLC.getUserId()+"')";
				session.createSQLQuery(sql).executeUpdate();
			}
			
			itemObject = (JSONObject)jsonParser.parse(importLC.getEditedItemList());
			itemList = (JSONArray) itemObject.get("list");
			
			for(int i=0;i<itemList.size();i++) {
				JSONObject item = (JSONObject) itemList.get(i);
				sql="update tbImportLCDetails set totalQty='"+item.get("totalQty")+"',price='"+item.get("price")+"',totalValue='"+item.get("totalValue")+"' where autoId ='"+item.get("autoId")+"';";
				session.createSQLQuery(sql).executeUpdate();
			}
			
			tx.commit();
			return "success";

		}
		catch(Exception ee){
			ee.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			
		}

		finally {
			session.close();
		}

		return null;
	}
	
	@Override
	public String importLCAmendment(ImportLC importLC) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			
			
			String sql = "select (isnull(max(amendmentNo),0))+1 as maxAmendmentNo from tbImportLC where masterLCNo='"+importLC.getMasterLCNo()+"' and invoiceNo = '"+importLC.getInvoiceNo()+"'";
			List<?> list = session.createSQLQuery(sql).list();
			String amendtmentNo="0";
			if(list.size()>0){
				amendtmentNo = list.get(0).toString();
			}
			
			
			sql="insert into tbimportLC (masterLCNo,invoiceNo,invoiceType,invoiceDate,amendmentNo,amendmentDate,senderBankId,receiverBankId,supplierId,draftAt,maturityDate,proformaInvoiceNo,proformaInvoiceDate,entryTime,entryBy)\r\n" + 
					"values ('"+importLC.getMasterLCNo()+"','"+importLC.getInvoiceNo()+"','"+importLC.getImportLCType()+"','"+importLC.getInvoiceDate()+"','"+amendtmentNo+"','"+importLC.getAmendmentDate()+"','"+importLC.getSenderBank()+"','"+importLC.getReceiverBank()+"','"+importLC.getSupplierId()+"','"+importLC.getDraftAt()+"','"+importLC.getMaturityDate()+"','"+importLC.getProformaInvoiceNo()+"','"+importLC.getProformaInvoiceDate()+"',CURRENT_TIMESTAMP,'"+importLC.getUserId()+"')";
			session.createSQLQuery(sql).executeUpdate();

			sql = "select isnull(max(autoid),0) as maxId from tbimportLC where invoiceNo='"+importLC.getInvoiceNo()+"' and masterLCNo='"+importLC.getMasterLCNo()+"' and amendmentNo='"+amendtmentNo+"'";
			list = session.createSQLQuery(sql).list();
			String importLCId="0";
			if(list.size()>0) {
				importLCId = list.get(0).toString();
			}
			
			JSONParser jsonParser = new JSONParser();
			JSONObject itemObject = (JSONObject)jsonParser.parse(importLC.getItemList());
			JSONArray itemList = (JSONArray) itemObject.get("list");
			
			for(int i=0;i<itemList.size();i++) {
				JSONObject item = (JSONObject) itemList.get(i);
				sql="insert into tbImportLCDetails (importInvoiceAutoId,importInvoiceNo,amendmentNo,styleId,poNo,accessoriesItemId,accessoriesItemType,colorId,size,unitId,width,gsm,totalQty,price,totalValue,entryTime,entryBy)\r\n" + 
						"values ('"+importLCId+"','"+importLC.getInvoiceNo()+"','"+amendtmentNo+"','"+item.get("styleId")+"','"+item.get("poNo")+"','"+item.get("accessoriesItemId")+"','"+item.get("accessoriesItemType")+"','"+item.get("colorId")+"','"+item.get("size")+"','"+item.get("unitId")+"','"+item.get("width")+"','"+item.get("gsm")+"','"+item.get("totalQty")+"','"+item.get("price")+"','"+item.get("totalValue")+"',CURRENT_TIMESTAMP,'"+importLC.getUserId()+"')";
				session.createSQLQuery(sql).executeUpdate();
			}
			
			tx.commit();
			return "success";

		}
		catch(Exception ee){
			ee.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			
		}

		finally {
			session.close();
		}

		return "success";
	}
	
	@Override
	public List<ImportLC> getImportLCAmendmentList(String masterLCNo, String invoiceNo) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<ImportLC> dataList=new ArrayList<ImportLC>();
		ImportLC tempImport = null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="select autoId,masterLCNo,invoiceNo,amendmentNo,convert(varchar,amendmentDate,25) as amendmentDate  from tbImportLC where masterLCNo='"+masterLCNo+"' and invoiceNo = '"+invoiceNo+"'";
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();
				tempImport = new ImportLC();
				tempImport.setAutoId(element[0].toString());
				tempImport.setMasterLCNo(element[1].toString());
				tempImport.setInvoiceNo(element[2].toString());
				tempImport.setAmendmentNo(element[3].toString());
				tempImport.setAmendmentDate(element[4].toString());
				dataList.add(tempImport);
			}
			tx.commit();
		}
		catch(Exception e){
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		}
		finally {
			session.close();
		}
		return dataList;
	}

	

	@Override
	public List<ImportLC> getImportLCList(String masterLCNo) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<ImportLC> dataList=new ArrayList<ImportLC>();
		ImportLC tempImport = null;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select ilc.masterLCNo,ilc.invoiceNo,max(ilc.amendmentNo) as maxAmendmentNo,convert(varchar,ilc.invoiceDate,25)as lcDate \r\n" + 
					"					from tbImportLC ilc\r\n" + 
					"					where ilc.masterLCNo = '"+masterLCNo+"'\r\n" + 
					"					group by ilc.masterLCNo,ilc.invoiceNo,ilc.invoiceDate ";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();
				tempImport = new ImportLC();
				tempImport.setMasterLCNo(element[0].toString());
				tempImport.setInvoiceNo(element[1].toString());
				tempImport.setAmendmentNo(element[2].toString());
				tempImport.setInvoiceDate(element[3].toString());
				dataList.add(tempImport);
			}
			tx.commit();
		}
		catch(Exception e){
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		}
		finally {
			session.close();
		}
		return dataList;
	}
	
	@Override
	public ImportLC getImportLCInfo(String masterLCNo, String invoiceNo, String amendmentNo) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		ImportLC tempImportLc = null;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select ilc.autoId,ilc.masterLCNo,ilc.invoiceNo,ilc.invoiceType,convert(varchar,ilc.invoiceDate,25) as invoiceDate,ilc.amendmentNo,convert(varchar,ilc.amendmentDate,25) as amendmentDate,ilc.senderBankId,ilc.receiverBankId,ilc.supplierId,ilc.draftAt,convert(varchar,ilc.maturityDate,25) as maturityDate,ilc.proformaInvoiceNo,convert(varchar,ilc.proformaInvoiceDate,25) as proformaInvoiceDate from tbImportLC ilc where masterLCNo='"+masterLCNo+"' and invoiceNo='"+invoiceNo+"' and amendmentNo = '"+amendmentNo+"'";
			

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				tempImportLc = new ImportLC();
				tempImportLc.setAutoId(element[0].toString());
				tempImportLc.setMasterLCNo(element[1].toString());
				tempImportLc.setInvoiceNo(element[2].toString());
				tempImportLc.setImportLCType(element[3].toString());
				tempImportLc.setInvoiceDate(element[4].toString());
				tempImportLc.setAmendmentNo(element[5].toString());
				tempImportLc.setAmendmentDate(element[6].toString());
				tempImportLc.setSenderBank(element[7].toString());
				tempImportLc.setReceiverBank(element[8].toString());
				tempImportLc.setSupplierId(element[9].toString());
				tempImportLc.setDraftAt(element[10].toString());
				tempImportLc.setMaturityDate(element[11].toString());
				tempImportLc.setProformaInvoiceNo(element[12].toString());
				tempImportLc.setProformaInvoiceDate(element[13].toString());
			}
			tx.commit();
		}
		catch(Exception e){
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		}
		finally {
			session.close();
		}
		return tempImportLc;
	}
	
	@Override
	public JSONArray getImportInvoiceItems(String importInvoiceAutoId) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		JSONArray itemArray = new JSONArray();
		JSONObject itemObject;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select ild.autoId,ild.importInvoiceAutoId,ild.importInvoiceNo,ild.amendmentNo,ild.styleId,sc.StyleNo,ild.poNo,ild.accessoriesItemId,isnull(ai.itemname,fi.ItemName) as accessoriesName,ild.accessoriesItemType,ild.colorId,c.Colorname,ild.size,ild.unitId,u.unitname,ild.width,ild.gsm,ild.totalQty,ild.price,ild.totalValue \r\n" + 
					"from tbImportLCDetails ild\r\n" + 
					"left join TbStyleCreate sc\r\n" + 
					"on ild.styleId = sc.StyleId\r\n" + 
					"left join TbAccessoriesItem ai\r\n" + 
					"on ild.accessoriesItemId = ai.itemid and ild.accessoriesItemType = '2'\r\n" + 
					"left join TbFabricsItem fi\r\n" + 
					"on ild.accessoriesItemId = fi.id and ild.accessoriesItemType = '1'\r\n" + 
					"left join tbColors c\r\n" + 
					"on ild.colorId = c.ColorId\r\n"
					+ "left join tbunits u\r\n" + 
					"on ild.unitId = u.Unitid \r\n" + 
					"where ild.importInvoiceAutoId = '"+importInvoiceAutoId+"'";
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				itemObject = new JSONObject();
				itemObject.put("autoId", element[0].toString());
				itemObject.put("importInvoiceItemAutoId", element[1].toString());
				itemObject.put("importInvoiceNo", element[2].toString());
				itemObject.put("amendmentNo", element[3].toString());
				itemObject.put("styleId", element[4].toString());
				itemObject.put("styleNo", element[5].toString());
				itemObject.put("poNo", element[6].toString());
				itemObject.put("accessoriesItemId", element[7].toString());
				itemObject.put("accessoriesName", element[8].toString());
				itemObject.put("accessoriesItemType", element[9].toString());
				itemObject.put("colorId", element[10].toString());
				itemObject.put("colorName", element[11].toString());
				itemObject.put("size", element[12].toString());
				itemObject.put("unitId", element[13].toString());
				itemObject.put("unitName", element[14].toString());
				itemObject.put("width", element[15].toString());
				itemObject.put("gsm", element[16].toString());
				itemObject.put("totalQty", element[17].toString());
				itemObject.put("price", element[18].toString());
				itemObject.put("totalValue", element[19].toString());
				itemArray.add(itemObject);
			}
			tx.commit();
		}
		catch(Exception e){
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		}
		finally {
			session.close();
		}
		return itemArray;
		
	}


	@Override
	public boolean importInvoiceUdAdd(String udInfo) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			
			
			
			JSONParser jsonParser = new JSONParser();
			JSONObject udObject = (JSONObject)jsonParser.parse(udInfo);
			//JSONArray itemList = (JSONArray) itemObject.get("list");
			String sql="insert into tbImportUD (importLCAutoId,importInvoiceNo,udNo,udDate,entryTime,entryBy) values('"+udObject.get("importLCAutoId")+"','"+udObject.get("importInvoiceNo")+"','"+udObject.get("importUDNo")+"','"+udObject.get("importUdDate")+"',CURRENT_TIMESTAMP,'"+udObject.get("userId")+"');";
			session.createSQLQuery(sql).executeUpdate();
			
			
			tx.commit();
			return true;

		}
		catch(Exception ee){
			ee.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			
		}

		finally {
			session.close();
		}

		return false;
	}
	
	@Override
	public JSONArray getImportINvoiceUDList(String importInvoiceNo) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		JSONArray itemArray = new JSONArray();
		JSONObject itemObject;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select autoId,importInvoiceNo,importLCAutoId,udNo,convert(varchar,udDate,25) as udDate from tbImportUD where importInvoiceNo = '"+importInvoiceNo+"';";
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				itemObject = new JSONObject();
				itemObject.put("autoId", element[0].toString());
				itemObject.put("importInvoiceNo", element[1].toString());
				itemObject.put("importLCAutoId", element[2].toString());
				itemObject.put("udNo", element[3].toString());
				itemObject.put("udDate", element[4].toString());
				itemArray.add(itemObject);
			}
			tx.commit();
		}
		catch(Exception e){
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		}
		finally {
			session.close();
		}
		return itemArray;
		
	}
	
	@Override
	public boolean billOfEntrySubmit(BillOfEntry billOfEntry) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			
			String sql="insert into tbBillEntry (masterLC,invoiceNo,billEntryNo,billEntryDate,vesselNo,etaDate,clearingDate,billNo,shippedOnBoardDate,telexReleasedDate,containerNo,documentReceiveDate,stuffingDate,entryTime,entryBy) \r\n" + 
					"values('"+billOfEntry.getMasterLCNo()+"','"+billOfEntry.getInvoiceNo()+"','"+billOfEntry.getBillEntryNo()+"','"+billOfEntry.getBillEntryDate()+"','"+billOfEntry.getVesselNo()+"','"+billOfEntry.getEtaDate()+"','"+billOfEntry.getClearingDate()+"','"+billOfEntry.getBillNo()+"','"+billOfEntry.getShippedOnBoardDate()+"','"+billOfEntry.getTelexReleaseDate()+"','"+billOfEntry.getContainerNo()+"','"+billOfEntry.getDocumentReceiveDate()+"','"+billOfEntry.getStuffingDate()+"',CURRENT_TIMESTAMP,'"+billOfEntry.getUserId()+"');";
			session.createSQLQuery(sql).executeUpdate();

			sql = "select isnull(max(autoid),0) as maxId from tbBillEntry where invoiceNo='"+billOfEntry.getInvoiceNo()+"' and masterLC='"+billOfEntry.getMasterLCNo()+"' ";
			List<?> list = session.createSQLQuery(sql).list();
			String billAutoId="0";
			if(list.size()>0) {
				billAutoId = list.get(0).toString();
			}
			
			JSONParser jsonParser = new JSONParser();
			JSONObject itemObject = (JSONObject)jsonParser.parse(billOfEntry.getItemList());
			JSONArray itemList = (JSONArray) itemObject.get("list");
			
			for(int i=0;i<itemList.size();i++) {
				JSONObject item = (JSONObject) itemList.get(i);
				sql="insert into tbBillEntryDetails (billEntryAutoId,billEntryNo,styleId,poNo,accessoriesItemId,accessoriesItemType,colorId,size,unitId,width,gsm,totalQty,cartonQty,price,totalValue,entryTime,entryBy)\r\n" + 
						"values ('"+billAutoId+"','"+item.get("billEntryNo")+"','"+item.get("styleId")+"','"+item.get("poNo")+"','"+item.get("accessoriesItemId")+"','"+item.get("accessoriesItemType")+"','"+item.get("colorId")+"','"+item.get("size")+"','"+item.get("unitId")+"','"+item.get("width")+"','"+item.get("gsm")+"','"+item.get("totalQty")+"','"+item.get("price")+"','"+item.get("cartonQty")+"','"+item.get("totalValue")+"',CURRENT_TIMESTAMP,'"+billOfEntry.getUserId()+"')";
				session.createSQLQuery(sql).executeUpdate();
			}
			
			tx.commit();
			return true;

		}
		catch(Exception ee){
			ee.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			
		}

		finally {
			session.close();
		}

		return false;
	}

	@Override
	public String billOfEntryEdit(BillOfEntry billOfEntry) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			
			String sql="update tbBillEntry set billNo = '"+billOfEntry.getBillNo()+"', shippedOnBoardDate = '"+billOfEntry.getShippedOnBoardDate()+"',telexReleasedDate = '"+billOfEntry.getTelexReleaseDate()+"',containerNo = '"+billOfEntry.getContainerNo()+"',vesselNo = '"+billOfEntry.getVesselNo()+"', documentReceiveDate = '"+billOfEntry.getDocumentReceiveDate()+"',etaDate = '"+billOfEntry.getEtaDate()+"',stuffingDate = '"+billOfEntry.getStuffingDate()+"',clearingDate = '"+billOfEntry.getClearingDate()+"' where autoId = '"+billOfEntry.getAutoId()+"'  and masterLC = '"+billOfEntry.getMasterLCNo()+"' and invoiceNo = '"+billOfEntry.getInvoiceNo()+"'";
			session.createSQLQuery(sql).executeUpdate();

			JSONParser jsonParser = new JSONParser();
			JSONObject itemObject = (JSONObject)jsonParser.parse(billOfEntry.getItemList());
			JSONArray itemList = (JSONArray) itemObject.get("list");
			
			for(int i=0;i<itemList.size();i++) {
				JSONObject item = (JSONObject) itemList.get(i);
				sql="insert into tbBillEntryDetails (billEntryAutoId,billEntryNo,styleId,poNo,accessoriesItemId,accessoriesItemType,colorId,size,unitId,width,gsm,totalQty,cartonQty,price,totalValue,entryTime,entryBy)\r\n" + 
						"values ('"+billOfEntry.getAutoId()+"','"+item.get("billEntryNo")+"','"+item.get("styleId")+"','"+item.get("poNo")+"','"+item.get("accessoriesItemId")+"','"+item.get("accessoriesItemType")+"','"+item.get("colorId")+"','"+item.get("size")+"','"+item.get("unitId")+"','"+item.get("width")+"','"+item.get("gsm")+"','"+item.get("totalQty")+"','"+item.get("price")+"','"+item.get("cartonQty")+"','"+item.get("totalValue")+"',CURRENT_TIMESTAMP,'"+billOfEntry.getUserId()+"')";
				session.createSQLQuery(sql).executeUpdate();
			}
			
			itemObject = (JSONObject)jsonParser.parse(billOfEntry.getEditedItemList());
			itemList = (JSONArray) itemObject.get("list");
			
			for(int i=0;i<itemList.size();i++) {
				JSONObject item = (JSONObject) itemList.get(i);
				sql="update tbBillEntryDetails set totalQty='"+item.get("totalQty")+"',price='"+item.get("price")+"',totalValue='"+item.get("totalValue")+"',cartonQty='"+item.get("cartonQty")+"' where autoId ='"+item.get("autoId")+"';";
				session.createSQLQuery(sql).executeUpdate();
			}
			
			tx.commit();
			return "success";

		}
		catch(Exception ee){
			ee.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			
		}

		finally {
			session.close();
		}

		return null;
	}

	@Override
	public List<BillOfEntry> getBillOfEntryList(String masterLCNo, String invoiceNo) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<BillOfEntry> dataList=new ArrayList<BillOfEntry>();
		BillOfEntry temBilEntry = null;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select masterLC,invoiceNo,billEntryNo,convert(varchar,billEntryDate,25) as billEntryDate from tbBillEntry where masterLC = '"+masterLCNo+"' and invoiceNo = '"+invoiceNo+"'";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();
				temBilEntry = new BillOfEntry();
				temBilEntry.setMasterLCNo(element[0].toString());
				temBilEntry.setInvoiceNo(element[1].toString());
				temBilEntry.setBillEntryNo(element[2].toString());
				temBilEntry.setBillEntryDate(element[3].toString());
				dataList.add(temBilEntry);
			}
			tx.commit();
		}
		catch(Exception e){
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		}
		finally {
			session.close();
		}
		return dataList;
	}

	@Override
	public BillOfEntry getBillOfEntryInfo(String masterLCNo, String invoiceNo, String billEntryNo) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		BillOfEntry billOfEntry = null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="select bill.autoId,bill.masterLC,bill.invoiceNo,bill.billEntryNo,convert(varchar,billEntryDate,25) as billEntryDate,billNo,vesselNo,etaDate,clearingDate,shippedOnBoardDate,telexReleasedDate,containerNo,documentReceiveDate,stuffingDate \r\n" + 
					"from tbBillEntry bill \r\n" + 
					"where bill.masterLC = '"+masterLCNo+"' and bill.invoiceNo = '"+invoiceNo+"' and bill.billEntryNo = '"+billEntryNo+"'";
			

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				billOfEntry = new BillOfEntry();
				billOfEntry.setAutoId(element[0].toString());
				billOfEntry.setMasterLCNo(element[1].toString());
				billOfEntry.setInvoiceNo(element[2].toString());
				billOfEntry.setBillEntryNo(element[3].toString());
				billOfEntry.setBillEntryDate(element[4].toString());
				billOfEntry.setBillNo(element[5].toString());
				billOfEntry.setVesselNo(element[6].toString());
				billOfEntry.setEtaDate(element[7].toString());
				billOfEntry.setClearingDate(element[8].toString());
				billOfEntry.setShippedOnBoardDate(element[9].toString());
				billOfEntry.setTelexReleaseDate(element[10].toString());
				billOfEntry.setContainerNo(element[11].toString());
				billOfEntry.setDocumentReceiveDate(element[12].toString());
				billOfEntry.setStuffingDate(element[13].toString());
			}
			tx.commit();
		}
		catch(Exception e){
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		}
		finally {
			session.close();
		}
		return billOfEntry;
	}

	@Override
	public JSONArray getBillOfEntryItems(String billEntryAutoId, String billEntryNo) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		JSONArray itemArray = new JSONArray();
		JSONObject itemObject;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select ild.autoId,ild.billEntryAutoId,ild.billEntryNo,ild.styleId,sc.StyleNo,ild.poNo,ild.accessoriesItemId,isnull(ai.itemname,fi.ItemName) as accessoriesName,ild.accessoriesItemType,ild.colorId,c.Colorname,ild.size,ild.unitId,u.unitname,ild.width,ild.gsm,ild.totalQty,ild.cartonQty,ild.price,ild.totalValue \r\n" + 
					"from tbBillEntryDetails ild\r\n" + 
					"left join TbStyleCreate sc\r\n" + 
					"on ild.styleId = sc.StyleId\r\n" + 
					"left join TbAccessoriesItem ai\r\n" + 
					"on ild.accessoriesItemId = ai.itemid and ild.accessoriesItemType = '2'\r\n" + 
					"left join TbFabricsItem fi\r\n" + 
					"on ild.accessoriesItemId = fi.id and ild.accessoriesItemType = '1'\r\n" + 
					"left join tbColors c\r\n" + 
					"on ild.colorId = c.ColorId\r\n"
					+ "left join tbunits u\r\n" + 
					"on ild.unitId = u.Unitid \r\n" + 
					"where ild.billEntryAutoId = '"+billEntryAutoId+"' and ild.billEntryNo='"+billEntryNo+"'";
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				itemObject = new JSONObject();
				itemObject.put("autoId", element[0].toString());
				itemObject.put("billEntryAutoId", element[1].toString());
				itemObject.put("billEntryNo", element[2].toString());
				itemObject.put("styleId", element[3].toString());
				itemObject.put("styleNo", element[4].toString());
				itemObject.put("poNo", element[5].toString());
				itemObject.put("accessoriesItemId", element[6].toString());
				itemObject.put("accessoriesName", element[7].toString());
				itemObject.put("accessoriesItemType", element[8].toString());
				itemObject.put("colorId", element[9].toString());
				itemObject.put("colorName", element[10].toString());
				itemObject.put("size", element[1].toString());
				itemObject.put("unitId", element[12].toString());
				itemObject.put("unitName", element[13].toString());
				itemObject.put("width", element[14].toString());
				itemObject.put("gsm", element[15].toString());
				itemObject.put("totalQty", element[16].toString());
				itemObject.put("cartonQty", element[17].toString());
				itemObject.put("price", element[18].toString());
				itemObject.put("totalValue", element[19].toString());
				itemArray.add(itemObject);
			}
			tx.commit();
		}
		catch(Exception e){
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		}
		finally {
			session.close();
		}
		return itemArray;
	}


	@Override
	public boolean exportLCSubmit(ExportLC exportLc) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			
			String sql="insert into tbExportLC (masterLCNo,buyerId,notifyTo,invoiceNo,invoiceDate,contractNo,contractDate,expNo,expDate,billEntryNo,billEntryDate,blNo,blDate,shippingMark,shippingDate,entryTime,entryBy)\r\n" + 
					"values ('"+exportLc.getMasterLCNo()+"','"+exportLc.getBuyerId()+"','"+exportLc.getNotifyTo()+"','"+exportLc.getInvoiceNo()+"','"+exportLc.getInvoiceDate()+"','"+exportLc.getContractNo()+"','"+exportLc.getContractDate()+"','"+exportLc.getExpNo()+"','"+exportLc.getExpDate()+"','"+exportLc.getBillEntryNo()+"','"+exportLc.getBillEntryDate()+"','"+exportLc.getBlNo()+"','"+exportLc.getBlDate()+"','"+exportLc.getShippingMark()+"','"+exportLc.getShippingDate()+"',CURRENT_TIMESTAMP,'"+exportLc.getUserId()+"')";
			session.createSQLQuery(sql).executeUpdate();

			sql = "select isnull(max(autoid),0) as maxId from tbExportLC where masterLCNo='"+exportLc.getMasterLCNo()+"' and invoiceNo = '"+exportLc.getInvoiceNo()+"'";
			List<?> list = session.createSQLQuery(sql).list();
			String exportLCId="0";
			if(list.size()>0) {
				exportLCId = list.get(0).toString();
			}
			
			JSONParser jsonParser = new JSONParser();
			JSONObject styleObject = (JSONObject)jsonParser.parse(exportLc.getStyleList());
			JSONArray styleList = (JSONArray) styleObject.get("list");
			
			for(int i=0;i<styleList.size();i++) {
				JSONObject style = (JSONObject) styleList.get(i);
				sql="insert into tbExportLCDetails (exportLCAutoId,exportInvoiceNo,styleId,styleItemId,purchaseOrderId,quantity,unitPrice,amount,cartonQty,entryTime,userId) \r\n" + 
						"values ('"+exportLCId+"','"+exportLc.getInvoiceNo()+"','"+style.get("styleId")+"','"+style.get("itemId")+"','"+style.get("purchaseOrderId")+"','"+style.get("quantity")+"','"+style.get("unitPrice")+"','"+style.get("amount")+"','"+style.get("cartonQty")+"',CURRENT_TIMESTAMP,'"+style.get("userId")+"');";
				session.createSQLQuery(sql).executeUpdate();
			}
			
			tx.commit();
			return true;

		}
		catch(Exception ee){
			ee.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			
		}

		finally {
			session.close();
		}

		return false;
	}
	
	
	@Override
	public String exportLCEdit(ExportLC exportLc) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			
			String sql="update tbExportLC set notifyTo = '"+exportLc.getNotifyTo()+"', invoiceDate = '"+exportLc.getInvoiceDate()+"', contractNo = '"+exportLc.getContractNo()+"', contractDate='"+exportLc.getContractDate()+"', expNo = '"+exportLc.getExpNo()+"',expDate = '"+exportLc.getExpDate()+"', billEntryNo = '"+exportLc.getBillEntryNo()+"',billEntryDate = '"+exportLc.getBillEntryDate()+"', blNo = '"+exportLc.getBlNo()+"', blDate = '"+exportLc.getBlDate()+"',shippingMark = '"+exportLc.getShippingMark()+"' , shippingDate = '"+exportLc.getShippingDate()+"' where autoId = '"+exportLc.getAutoId()+"' ";
			session.createSQLQuery(sql).executeUpdate();

			/*String sql = "select isnull(max(autoid),0) as maxId from tbMasterLC where masterLCNo='"+masterLC.getMasterLCNo()+"' and buyerId = '"+masterLC.getBuyerId()+"'";
			List<?> list = session.createSQLQuery(sql).list();
			String masterLCId="0";
			if(list.size()>0) {
				masterLCId = list.get(0).toString();
			}*/
			
			JSONParser jsonParser = new JSONParser();
			JSONObject styleObject = (JSONObject)jsonParser.parse(exportLc.getStyleList());
			JSONArray styleList = (JSONArray) styleObject.get("list");
			
			for(int i=0;i<styleList.size();i++) {
				JSONObject style = (JSONObject) styleList.get(i);
				sql="insert into tbExportLCDetails (exportLCAutoId,exportInvoiceNo,styleId,styleItemId,purchaseOrderId,quantity,unitPrice,amount,cartonQty,entryTime,userId) \r\n" + 
						"values ('"+exportLc.getAutoId()+"','"+exportLc.getInvoiceNo()+"','"+style.get("styleId")+"','"+style.get("itemId")+"','"+style.get("purchaseOrderId")+"','"+style.get("quantity")+"','"+style.get("unitPrice")+"','"+style.get("amount")+"','"+style.get("cartonQty")+"',CURRENT_TIMESTAMP,'"+style.get("userId")+"');";
				session.createSQLQuery(sql).executeUpdate();
			}
			
			styleObject = (JSONObject)jsonParser.parse(exportLc.getEditedStyleList());
			styleList = (JSONArray) styleObject.get("list");
			
			for(int i=0;i<styleList.size();i++) {
				JSONObject style = (JSONObject) styleList.get(i);
				sql="update tbExportLCDetails set quantity='"+style.get("quantity")+"',unitPrice='"+style.get("unitPrice")+"',amount='"+style.get("amount")+"',cartonQty='"+style.get("cartonQty")+"' where autoId = '"+style.get("autoId")+"' " ;
				session.createSQLQuery(sql).executeUpdate();
			}
			
			tx.commit();
			return "success";

		}
		catch(Exception ee){
			ee.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			
		}

		finally {
			session.close();
		}

		return null;
	}

	@Override
	public List<ExportLC> getExportInvoiceList(String masterLCNo) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<ExportLC> dataList=new ArrayList<ExportLC>();
		ExportLC exportLc = null;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select masterLCNo,invoiceNo,invoiceDate ,shippingMark,shippingDate from tbExportLC where masterLCNo = '"+masterLCNo+"'";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();
				exportLc = new ExportLC();
				exportLc.setMasterLCNo(element[0].toString());
				exportLc.setInvoiceNo(element[1].toString());
				exportLc.setInvoiceDate(element[2].toString());
				exportLc.setShippingMark(element[3].toString());
				exportLc.setShippingDate(element[4].toString());
				
				dataList.add(exportLc);
			}
			tx.commit();
		}
		catch(Exception e){
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		}
		finally {
			session.close();
		}
		return dataList;
	}

	@Override
	public ExportLC getExportLCInfo(String masterLCNo, String exportInvoiceNo) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		ExportLC tempExportLc = null;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select autoId,masterLCNo,buyerId,notifyTo,invoiceNo,invoiceDate,contractNo,contractDate,expNo,expDate,billEntryNo,billEntryDate,blNo,blDate,shippingMark,shippingDate from tbExportLC where masterLCNo = '"+masterLCNo+"' and invoiceNo = '"+exportInvoiceNo+"'";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();
				tempExportLc = new ExportLC();
				tempExportLc.setAutoId(element[0].toString());
				tempExportLc.setMasterLCNo(element[1].toString());
				tempExportLc.setBuyerId(element[2].toString());
				tempExportLc.setNotifyTo(element[3].toString());
				tempExportLc.setInvoiceNo(element[4].toString());
				tempExportLc.setInvoiceDate(element[5].toString());
				tempExportLc.setContractNo(element[6].toString());
				tempExportLc.setContractDate(element[7].toString());
				tempExportLc.setExpNo(element[8].toString());
				tempExportLc.setExpDate(element[9].toString());
				tempExportLc.setBillEntryNo(element[10].toString());
				tempExportLc.setBillEntryDate(element[11].toString());
				tempExportLc.setBlNo(element[12].toString());
				tempExportLc.setBlDate(element[13].toString());
				tempExportLc.setShippingMark(element[14].toString());
				tempExportLc.setShippingDate(element[15].toString());
			}
			tx.commit();
		}
		catch(Exception e){
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		}
		finally {
			session.close();
		}
		return tempExportLc;
	}

	@Override
	public JSONArray getExportStyleItems(String exportInvoiceAutoId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		JSONArray itemArray = new JSONArray();
		JSONObject itemObject;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select ed.autoId,exportLCAutoId,exportInvoiceNo,ed.styleId,sc.StyleNo,styleItemId,id.itemname,purchaseOrderId,quantity,unitPrice,amount,cartonQty \r\n" + 
					"from tbExportLCDetails ed\r\n" + 
					"left join TbStyleCreate sc\r\n" + 
					"on ed.styleId = sc.StyleId\r\n" + 
					"left join tbItemDescription id\r\n" + 
					"on ed.styleItemId = id.itemid\r\n" + 
					"  where exportLCAutoId = '"+exportInvoiceAutoId+"'";
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				itemObject = new JSONObject();
				itemObject.put("autoId", element[0].toString());
				itemObject.put("exportLCAutoId", element[1].toString());
				itemObject.put("exportInvoiceNo", element[2].toString());
				itemObject.put("styleId", element[3].toString());
				itemObject.put("StyleNo", element[4].toString());
				itemObject.put("styleItemId", element[5].toString());
				itemObject.put("itemname", element[6].toString());
				itemObject.put("purchaseOrder", element[7].toString());
				itemObject.put("quantity", element[8].toString());
				itemObject.put("unitPrice", element[9].toString());
				itemObject.put("amount", element[10].toString());
				itemObject.put("cartonQty", element[11].toString());
				
				itemArray.add(itemObject);
			}
			tx.commit();
		}
		catch(Exception e){
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		}
		finally {
			session.close();
		}
		return itemArray;
	}

	@Override
	public boolean insertDeedOfContact(deedOfContacts v) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();


			if(v.getValue().equals("1")) {

				String sql="insert into tbDEEDOfContract "
						+ "(PONumber, "
						+ "StyleNo,"
						+ " ItemDescription,"
						+ " goodsDescription, "
						+ "color, "
						+ "rollQty,"
						+ " ctnQty, "
						+ "grossWeight, "
						+ "netWeight, "
						+ "unit, "
						+ "unitPrice,"
						+ " currency,"
						+ " amount, "
						+ "ETDDate,"
						+ " ETADate, "
						+ "ETCDate, "
						+ "ClearDate, "
						+ "ContractNo,"
						+ " ReadyDate, "
						+ "SubmitDate, "
						+ "ReceivedDate,"
						+ " ExpireyDate,"
						+ " AmmendmentDate,"
						+ " ExtendedDate, "
						+ "ExportDate, "
						+ "InvoiceNumber, "
						+ "InvoiceDate, "
						+ "AWBNumber,"
						+ " BLDate, "
						+ "TrachingNumber,"
						+ " ShipperAddress,"
						+ " consignAddress, "
						+ "CNFHandoverDate,"
						+ " CNFAddress,"
						+ " Telephone, "
						+ "Mobile, "
						+ "FaxNo, "
						+ "ContactPerson,"
						+ " CouirerName,"
						+ " ForwardAddress,"
						+ " UdMakingDate, "
						+ "UdAmmendmentDate,"
						+ "UdSubmitDate, "
						+ "UdReceivedDate, "
						+ "UdHoverDate, "
						+ "BirthingDate, "
						+ "BuyerId, "
						+ "MasterLc, "
						+ "BBLc,   "
						+ "VesselName, "
						+ "InvoiceQty,"
						+ " OnBoardDate, "
						+ "entryTime, "
						+ "UserId) values"
						+ "('"+v.getPurchaseOrder()+"',"
						+ "'"+v.getStyleNo()+"',"
						+ "'"+v.getItemName()+"',"
						+ "'"+v.getGoodsDescription()+"',"
						+ "'"+v.getItemColor()+"',"
						+ "'"+v.getRollQty()+"',"
						+ "'"+v.getCtnQty()+"',"
						+ "'"+v.getGrossWeight()+"',"
						+ "'"+v.getNetWeight()+"',"
						+ "'"+v.getUnit()+"',"
						+ "'"+v.getUnitPrice()+"',"
						+ "'"+v.getCurrency()+"',"
						+ "'"+v.getAmount()+"',"
						+ "'"+v.getEtdDate()+"',"
						+ "'"+v.getEtaDate()+"',"
						+ "'"+v.getEtcDate()+"',"
						+ "'"+v.getClearDate()+"',"
						+ "'"+v.getContactNo()+"',"
						+ "'"+v.getReadyDate()+"',"
						+ "'"+v.getSubmitDate()+"',"
						+ "'"+v.getReceivedDate()+"',"
						+ "'"+v.getExpiryDate()+"',"
						+ "'"+v.getAmmendmentDate()+"',"
						+ "'"+v.getExtendedDate()+"',"
						+ "'"+v.getExportDate()+"',"
						+ "'"+v.getInvoiceNumber()+"',"
						+ "'"+v.getInvoiceDate()+"',"
						+ "'"+v.getAwbNumber()+"',"
						+ "'"+v.getBlDate()+"',"
						+ "'"+v.getTrackingNumber()+"',"
						+ "'"+v.getShipperAddress()+"',"
						+ "'"+v.getConsignAddress()+"',"
						+ "'"+v.getCfHandoverDate()+"',"
						+ "'"+v.getCfAddress()+"',"
						+ "'"+v.getTelephone()+"',"
						+ "'"+v.getMobile()+"',"
						+ "'"+v.getFaxNo()+"',"
						+ "'"+v.getContactPerson()+"',"
						+ "'"+v.getCourieer()+"',"
						+ "'"+v.getForwardAddress()+"',"
						+ "'"+v.getUnMakeingDate()+"',"
						+ "'"+v.getAmmendmentDate()+"',"
						+ "'"+v.getUnSubmitDate()+"',"
						+ "'"+v.getuNReceivedDate()+"',"
						+ "'"+v.getUnHoverDate()+"',"
						+ "'"+v.getBirthingDate()+"',"
						+ "'"+v.getBuyerId()+"',"
						+ "'"+v.getMasterLC()+"',"
						+ "'"+v.getBblc()+"',"
						+ "'"+v.getVvsselName()+"',"
						+ "'"+v.getInvoiceQty()+"'"
						+ ",'"+v.getOnBoardDate()+"'"
						+ ",CURRENT_TIMESTAMP,"
						+ "'"+v.getUserid()+"')";
				session.createSQLQuery(sql).executeUpdate();	

				tx.commit();
				return true;
			}else {
				String sql="update  tbDEEDOfContract set "
						+ "PONumber='"+v.getPurchaseOrder()+"', "
						+ "StyleNo='"+v.getStyleNo()+"',"
						+ " ItemDescription='"+v.getItemName()+"',"
						+ " goodsDescription='"+v.getGoodsDescription()+"', "
						+ "color='"+v.getItemColor()+"', "
						+ "rollQty='"+v.getRollQty()+"',"
						+ " ctnQty='"+v.getCtnQty()+"', "
						+ "grossWeight='"+v.getGrossWeight()+"', "
						+ "netWeight='"+v.getNetWeight()+"', "
						+ "unit='"+v.getUnit()+"', "
						+ "unitPrice='"+v.getUnitPrice()+"',"
						+ " currency='"+v.getCurrency()+"',"
						+ " amount='"+v.getAmount()+"', "
						+ "ETDDate='"+v.getEtdDate()+"',"
						+ " ETADate='"+v.getEtaDate()+"', "
						+ "ETCDate='"+v.getEtcDate()+"', "
						+ "ClearDate='"+v.getClearDate()+"', "
						+ "ContractNo='"+v.getContactNo()+"',"
						+ " ReadyDate='"+v.getReadyDate()+"', "
						+ "SubmitDate='"+v.getSubmitDate()+"', "
						+ "ReceivedDate='"+v.getReceivedDate()+"',"
						+ " ExpireyDate='"+v.getExpiryDate()+"',"
						+ " AmmendmentDate='"+v.getAmmendmentDate()+"',"
						+ " ExtendedDate='"+v.getExtendedDate()+"', "
						+ "ExportDate='"+v.getExportDate()+"', "
						+ "InvoiceNumber='"+v.getInvoiceNumber()+"', "
						+ "InvoiceDate='"+v.getInvoiceDate()+"', "
						+ "AWBNumber='"+v.getAwbNumber()+"',"
						+ " BLDate='"+v.getBlDate()+"', "
						+ "TrachingNumber='"+v.getTrackingNumber()+"',"
						+ " ShipperAddress='"+v.getShipperAddress()+"',"
						+ " consignAddress='"+v.getConsignAddress()+"', "
						+ "CNFHandoverDate='"+v.getCfHandoverDate()+"',"
						+ " CNFAddress='"+v.getCfAddress()+"',"
						+ " Telephone='"+v.getTelephone()+"', "
						+ "Mobile='"+v.getMobile()+"', "
						+ "FaxNo='"+v.getFaxNo()+"', "
						+ "ContactPerson='"+v.getContactPerson()+"',"
						+ " CouirerName='"+v.getCourieer()+"',"
						+ " ForwardAddress='"+v.getForwardAddress()+"',"
						+ " UdMakingDate='"+v.getUnMakeingDate()+"', "
						+ "UdAmmendmentDate='"+v.getAmmendmentDate()+"',"
						+ "UdSubmitDate='"+v.getUnSubmitDate()+"', "
						+ "UdReceivedDate='"+v.getuNReceivedDate()+"', "
						+ "UdHoverDate='"+v.getUnHoverDate()+"', "
						+ "BirthingDate='"+v.getBirthingDate()+"', "
						+ "BuyerId='"+v.getBuyerId()+"', "
						+ "MasterLc='"+v.getMasterLC()+"', "
						+ "BBLc='"+v.getBblc()+"',   "
						+ "VesselName='"+v.getVvsselName()+"', "
						+ "InvoiceQty='"+v.getInvoiceQty()+"',"
						+ " OnBoardDate='"+v.getOnBoardDate()+"' where ContractId='"+v.getContractId()+"' ";
						
						
						
				session.createSQLQuery(sql).executeUpdate();	

				tx.commit();
				return true;
			}
		}
		catch(Exception ee){

			if (tx != null) {
				tx.rollback();
				return false;
			}
			ee.printStackTrace();
		}

		finally {
			session.close();
		}

		return false;
	}

	@Override
	public List<deedOfContacts> deedOfContractsList() {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<deedOfContacts> dataList=new ArrayList<deedOfContacts>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql=" select a.ContractId, (select purchaseorder from TbBuyerOrderEstimateDetails where buyerorderid=a.PONumber group by PurchaseOrder) as po,(select styleno from tbstylecreate where styleid=a.StyleNo) as style,(select itemname from tbItemDescription b where b.itemid=a.ItemDescription ) as item from tbDEEDOfContract a";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();

				dataList.add(new deedOfContacts(element[0].toString(),element[1].toString(),element[2].toString(),element[3].toString()));
			}
			tx.commit();
		}
		catch(Exception e){
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		}
		finally {
			session.close();
		}
		return dataList;
	}

	@Override
	public List<deedOfContacts> deedOfContractDetails(String id) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<deedOfContacts> dataList=new ArrayList<deedOfContacts>();
		try{
			SpringRootConfig sp=new SpringRootConfig();
			Connection con = null;
			PreparedStatement ps = null;
			con = sp.getConnection();
			String sql="select * FROM tbDEEDOfContract b where b.ContractId='"+id+"'";

			System.out.println(" merchediser list query "+sql);
			ps =con.prepareStatement(sql)  ;

			int a=0;
			ResultSet result = ps.executeQuery();


			ResultSetMetaData metaData = ps.getMetaData();
			while (result.next()) {
				a++;
				System.out.println(" a increament "+a);

				//fileBytes = result.getBytes("signature");

				int rowCount = metaData.getColumnCount();

				/*	System.out.println(" column count"+rowCount);


				System.out.println(" data "+metaData.getColumnName(1));

				for (int i = 0; i < rowCount; i++) {
		                System.out.print("String "+result.getString(1) + ", \t");
		               // System.out.println(metaData.getColumnTypeName(i + 1));
		            }

				 */				

				dataList.add(new deedOfContacts(result.getString(1),result.getString(2),result.getString(3),result.getString(4),
						result.getString(5),result.getString(6),result.getString(7),result.getString(8),result.getString(9),result.getString(10),result.getString(11),
						result.getString(12),result.getString(13),result.getString(14),result.getString(15),result.getString(16),result.getString(17),result.getString(18),
						result.getString(19),result.getString(20),result.getString(21),result.getString(22),result.getString(23),result.getString(24),result.getString(25),
						result.getString(26),result.getString(27),
						result.getString(28),result.getString(29),result.getString(30),result.getString(31),result.getString(32),result.getString(33),
						result.getString(34),result.getString(35),result.getString(36),result.getString(37),result.getString(38),result.getString(39),
						result.getString(40),result.getString(41),result.getString(42),result.getString(43),result.getString(44),result.getString(45),
						result.getString(46),result.getString(47),result.getString(48),result.getString(49),result.getString(50),result.getString(51),result.getString(52),result.getString(53)));



				/* dataList.add(new deedOfContacts(metaData.getColumnName(1),metaData.getColumnName(2),metaData.getColumnName(3),metaData.getColumnName(4),
							metaData.getColumnName(5),metaData.getColumnName(6),metaData.getColumnName(7),metaData.getColumnName(8),metaData.getColumnName(9),metaData.getColumnName(10),metaData.getColumnName(11),
							metaData.getColumnName(12),metaData.getColumnName(13),metaData.getColumnName(14),metaData.getColumnName(15),metaData.getColumnName(16),metaData.getColumnName(17),metaData.getColumnName(18),
							metaData.getColumnName(19),metaData.getColumnName(20),metaData.getColumnName(21),metaData.getColumnName(22),metaData.getColumnName(23),metaData.getColumnName(24),metaData.getColumnName(25),
							metaData.getColumnName(26),metaData.getColumnName(27),
							metaData.getColumnName(28),metaData.getColumnName(29),metaData.getColumnName(30),metaData.getColumnName(31),metaData.getColumnName(32),metaData.getColumnName(33),
							metaData.getColumnName(34),metaData.getColumnName(35),metaData.getColumnName(36),metaData.getColumnName(37),metaData.getColumnName(38),metaData.getColumnName(39),
							metaData.getColumnName(40),metaData.getColumnName(41),metaData.getColumnName(42),metaData.getColumnName(43),metaData.getColumnName(44),metaData.getColumnName(45),
							metaData.getColumnName(46),metaData.getColumnName(47),metaData.getColumnName(48),metaData.getColumnName(49),metaData.getColumnName(50),metaData.getColumnName(51),metaData.getColumnName(52),metaData.getColumnName(53)));
				 */


				//System.out.println(" results "+result.getString(0)+" "+result.getString(1)+" "+result.getString(2)+" "+result.getString(3)+" "+result.getString(4));

				/*dataList.add(new deedOfContacts(result.getString(0),result.getString(1),result.getString(2),result.getString(3),result.getString(4),
						result.getString(5),result.getString(6),result.getString(7),result.getString(8),result.getString(9),result.getString(10),result.getString(11),
						result.getString(12),result.getString(13),result.getString(14),result.getString(15),result.getString(16),result.getString(17),result.getString(18),
						result.getString(19),result.getString(20),result.getString(21),result.getString(22),result.getString(23),result.getString(24),result.getString(25),
						result.getString(26),result.getString(27),
						result.getString(28),result.getString(29),result.getString(30),result.getString(31),result.getString(32),result.getString(33),
						result.getString(34),result.getString(35),result.getString(36),result.getString(37),result.getString(38),result.getString(39),
						result.getString(40),result.getString(41),result.getString(42),result.getString(43),result.getString(44),result.getString(45),
						result.getString(46),result.getString(47),result.getString(48),result.getString(49),result.getString(50),result.getString(51),""));
				 */




			}
		}
		catch(Exception e){
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		}
		finally {
			session.close();
		}
		return dataList;
	}

	

		

	

}
