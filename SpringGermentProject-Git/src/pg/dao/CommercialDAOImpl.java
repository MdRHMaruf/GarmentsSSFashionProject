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
import org.springframework.stereotype.Repository;

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
			return "";

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

			String sql=" select autoId,masterLCNo,amendmentNo,convert(varchar,amendmentDate,25) as amendmentDate from tbMasterLC where masterLCNo='"+masterLCNo+"' and buyerId = '"+buyerId+"'";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();

				dataList.add(new MasterLC(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString()));
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
					"group by mlc.masterLCNo,mlc.buyerId,mlc.amendmentNo,mlc.date,b.name";

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
