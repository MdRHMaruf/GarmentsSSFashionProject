package pg.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.catalina.Store;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.barcodelib.barcode.a.c.f;

import pg.model.commonModel;
import pg.orderModel.AccessoriesIndent;
import pg.orderModel.FabricsIndent;
import pg.orderModel.PurchaseOrderItem;
import pg.orderModel.Style;
import pg.share.HibernateUtil;
import pg.share.ItemType;
import pg.share.StoreTransaction;
import pg.storeModel.AccessoriesIssue;
import pg.storeModel.AccessoriesIssueReturn;
import pg.storeModel.AccessoriesQualityControl;
import pg.storeModel.AccessoriesReceive;
import pg.storeModel.AccessoriesReturn;
import pg.storeModel.AccessoriesSize;
import pg.storeModel.AccessoriesTransferIn;
import pg.storeModel.AccessoriesTransferOut;
import pg.storeModel.CuttingFabricsUsed;
import pg.storeModel.FabricsIssue;
import pg.storeModel.FabricsIssueReturn;
import pg.storeModel.FabricsQualityControl;
import pg.storeModel.FabricsReceive;
import pg.storeModel.FabricsReturn;
import pg.storeModel.FabricsRoll;
import pg.storeModel.FabricsTransferIn;
import pg.storeModel.FabricsTransferOut;
import pg.storeModel.StoreGeneralCategory;
import pg.storeModel.StoreGeneralReceived;
import pg.storeModel.StoreGeneralCategory;
import pg.storeModel.StoreGeneralReceived;


@Repository
public class StoreDAOImpl implements StoreDAO{


	@Override
	public List<FabricsIndent> getFabricsPurchaseOrdeIndentrList() {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;



		List<FabricsIndent> datalist=new ArrayList<FabricsIndent>();	
		try{	
			tx=session.getTransaction();
			tx.begin();		
			String sql="select rf.id,rf.PurchaseOrder,rf.styleId,sc.StyleNo,rf.itemId,id.itemname,rf.itemcolor as itemColorId,c.Colorname as itemColor,rf.fabricsid,fi.ItemName as fabricsName,rf.fabricscolor as fabricsColorId,fc.Colorname as fabricsColor\r\n" + 
					"from tbFabricsIndent rf\r\n" + 
					"left join TbStyleCreate sc\r\n" + 
					"on rf.StyleId = sc.StyleId\r\n" + 
					"left join tbItemDescription id\r\n" + 
					"on rf.itemid = id.itemid\r\n" + 
					"left join tbColors c\r\n" + 
					"on rf.itemcolor = c.ColorId\r\n" + 
					"left join tbColors fc\r\n" + 
					"on rf.fabricscolor = fc.ColorId\r\n" + 
					"left join TbFabricsItem fi\r\n" + 
					"on rf.fabricsid = fi.id\r\n" + 
					"where rf.pono is not null\r\n" + 
					"order by rf.id desc";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				datalist.add(new FabricsIndent(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString(), element[7].toString(), element[8].toString(), element[9].toString(), element[10].toString(),  element[11].toString()));		
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
		return datalist;
	}



	@Override
	public FabricsIndent getFabricsIndentInfo(String autoId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		FabricsIndent indent= null;
		try{	
			tx=session.getTransaction();
			tx.begin();	
			String sql="select rf.id,PurchaseOrder,rf.styleId,sc.StyleNo,rf.itemid,id.itemname,rf.itemcolor,c.colorName,rf.fabricsid,fi.ItemName as fabricsName,rf.fabricscolor,fc.Colorname,rf.unitId,u.unitname,rf.RequireUnitQty,\r\n" + 
					"(select isnull(sum(qty),0) from tbFabricsAccessoriesTransaction fat where fat.purchaseOrder = rf.PurchaseOrder and fat.styleId = rf.styleId and fat.styleItemId = rf.itemid and fat.colorId = rf.itemcolor and fat.dItemId = rf.fabricsId and fat.itemColorId = rf.fabricscolor and fat.transactionType = '1') as previousReceiveQty\r\n" + 
					"from tbFabricsIndent rf\r\n" + 
					"left join TbStyleCreate  sc\r\n" + 
					"on rf.styleId = sc.StyleId\r\n" + 
					"left join tbItemDescription id\r\n" + 
					"on rf.itemid = id.itemid\r\n" + 
					"left join tbColors c\r\n" + 
					"on rf.itemcolor = c.ColorId\r\n" + 
					"left join TbFabricsItem fi\r\n" + 
					"on rf.fabricsid = fi.id\r\n" + 
					"left join tbColors fc\r\n" + 
					"on rf.fabricscolor = fc.ColorId\r\n" + 
					"left join tbunits u\r\n" + 
					"on rf.unitId = u.Unitid\r\n" + 
					" where rf.id = '"+autoId+"'";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				indent = new FabricsIndent(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString(), element[7].toString(), element[8].toString(), element[9].toString(), element[10].toString(), element[11].toString(), element[12].toString(), element[13].toString(), Double.valueOf(element[14].toString()),Double.valueOf(element[15].toString()));
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
		return indent;
	}



	@Override
	public boolean submitFabricsReceive(FabricsReceive fabricsReceive) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select (isnull(max(transactionid),0)+1) as maxId from tbfabricsReceiveInfo";
			List<?> list = session.createSQLQuery(sql).list();
			String transactionId="0";
			if(list.size()>0) {
				transactionId = list.get(0).toString();
			}

			sql="insert into tbfabricsReceiveInfo (transactionid,"
					+ "grnNo,"
					+ "grnDate,"
					+ "location,"		
					+ "supplierId,"
					+ "challanNo,"
					+ "challanDate,"
					+ "remarks,"
					+ "departmentId,"
					+ "preperedBy,"
					+ "entryTime,"
					+ "createBy) \r\n" + 
					"values('"+transactionId+"',"
					+ "'"+fabricsReceive.getGrnNo()+"',"
					+ "'"+fabricsReceive.getGrnDate()+"',"
					+ "'"+fabricsReceive.getLocation()+"',"
					+ "'"+fabricsReceive.getSupplierId()+"',"
					+ "'"+fabricsReceive.getChallanNo()+"',"
					+ "'"+fabricsReceive.getChallanDate()+"',"
					+ "'"+fabricsReceive.getRemarks()+"',"
					+ "'"+fabricsReceive.getDepartmentId()+"',"
					+ "'"+fabricsReceive.getPreparedBy()+"',"
					+ "CURRENT_TIMESTAMP,"
					+ "'"+fabricsReceive.getUserId()+"') ;";
			session.createSQLQuery(sql).executeUpdate();
			int departmentId = 1;
			sql = "select isnull(max(autoId),0) as id from  tbFabricsRollInfo";		
			int rollId = 0;
			list = session.createSQLQuery(sql).list();
			if(list.size()>0)
			{	
				rollId = Integer.valueOf(list.get(0).toString());				
			}	
			for (FabricsRoll roll : fabricsReceive.getFabricsRollList()) {
				rollId++;
				sql="insert into tbFabricsRollInfo (rollId,supplierRollId,entryTime,createBy) values('"+rollId+"','"+roll.getSupplierRollId()+"',CURRENT_TIMESTAMP,'"+fabricsReceive.getUserId()+"');";		
				session.createSQLQuery(sql).executeUpdate();

				sql="insert into tbFabricsAccessoriesTransaction (purchaseOrder,styleId,styleItemId,colorId,itemColorId,transactionId,transactionType,itemType,rollId,unitId,unitQty,qty,dItemId,cItemId,departmentId,rackName,binName,entryTime,userId) \r\n" + 
						"values('"+roll.getPurchaseOrder()+"','"+roll.getStyleId()+"','"+roll.getItemId()+"','"+roll.getItemColorId()+"','"+roll.getFabricsColorId()+"','"+transactionId+"','"+StoreTransaction.FABRICS_RECEIVE.getType()+"','"+ItemType.FABRICS.getType()+"','"+rollId+"','"+roll.getUnitId()+"','"+roll.getUnitQty()+"','"+roll.getUnitQty()+"','"+roll.getFabricsId()+"','0','"+departmentId+"','"+roll.getRackName()+"','"+roll.getBinName()+"',CURRENT_TIMESTAMP,'"+fabricsReceive.getUserId()+"');";		
				session.createSQLQuery(sql).executeUpdate();
			}

			tx.commit();

			return true;
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

		return false;
	}

	@Override
	public boolean editFabricsReceive(FabricsReceive fabricsReceive) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="update tbfabricsReceiveInfo set "
					+ "grnNo='"+fabricsReceive.getGrnNo()+"',"
					+ "grnDate='"+fabricsReceive.getGrnDate()+"',"
					+ "location='"+fabricsReceive.getLocation()+"',"
					+ "supplierId='"+fabricsReceive.getSupplierId()+"',"
					+ "challanNo='"+fabricsReceive.getChallanNo()+"',"
					+ "challanDate='"+fabricsReceive.getChallanDate()+"',"
					+ "remarks='"+fabricsReceive.getRemarks()+"',"
					+ "preperedBy='"+fabricsReceive.getPreparedBy()+"' where transactionId= '"+fabricsReceive.getTransactionId()+"'";


			session.createSQLQuery(sql).executeUpdate();

			sql = "select isnull(max(autoId),0) as id from  tbFabricsRollInfo";		
			int rollId = 0;
			List<?> list = session.createSQLQuery(sql).list();
			if(list.size()>0)
			{	
				rollId = Integer.valueOf(list.get(0).toString());				
			}	
			int departmentId = 1;
			if(fabricsReceive.getFabricsRollList() != null) {
				for (FabricsRoll roll : fabricsReceive.getFabricsRollList()) {
					rollId++;
					sql="insert into tbFabricsRollInfo (rollId,supplierRollId,entryTime,createBy) values('"+rollId+"','"+roll.getSupplierRollId()+"',CURRENT_TIMESTAMP,'"+fabricsReceive.getUserId()+"');";		
					session.createSQLQuery(sql).executeUpdate();

					sql="insert into tbFabricsAccessoriesTransaction (purchaseOrder,styleId,styleItemId,colorId,itemColorId,transactionId,transactionType,itemType,rollId,unitId,unitQty,qty,dItemId,cItemId,departmentId,rackName,binName,entryTime,userId) \r\n" + 
							"values('"+roll.getPurchaseOrder()+"','"+roll.getStyleId()+"','"+roll.getItemId()+"','"+roll.getItemColorId()+"','"+roll.getFabricsColorId()+"','"+fabricsReceive.getTransactionId()+"','"+StoreTransaction.FABRICS_RECEIVE.getType()+"','"+ItemType.FABRICS.getType()+"','"+roll.getRollId()+"','"+roll.getUnitId()+"','"+roll.getUnitQty()+"','"+roll.getUnitQty()+"','"+roll.getFabricsId()+"','0','"+departmentId+"','"+roll.getRackName()+"','"+roll.getBinName()+"',CURRENT_TIMESTAMP,'"+fabricsReceive.getUserId()+"');";		
					session.createSQLQuery(sql).executeUpdate();
				}

			}

			tx.commit();

			return true;
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

		return false;
	}

	@Override
	public String editReceiveRollInTransaction(FabricsRoll fabricsRoll) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		try{
			tx=session.getTransaction();
			tx.begin();
			String sql = "select far.autoId,far.purchaseOrder,far.qty from tbFabricsAccessoriesTransaction far\r\n" + 
					"join tbFabricsAccessoriesTransaction far2\r\n" + 
					"on far.dItemId= far2.cItemId and far2.rollId = far.rollId and far2.itemColorId = far.itemColorId and far2.colorId = far.colorId and far2.styleItemId= far.styleItemId \r\n" + 
					"and far2.styleId = far.styleId and far2.purchaseOrder = far.purchaseOrder and (far.transactionType ='"+StoreTransaction.FABRICS_RETURN.getType()+"' or far.transactionType ='"+StoreTransaction.FABRICS_ISSUE.getType()+"')\r\n" + 
					"where far.autoId = '"+fabricsRoll.getAutoId()+"'";		
			List<?> list = session.createSQLQuery(sql).list();
			if(list.size()==0) {
				sql = "update tbFabricsRollInfo set supplierRollId = '"+fabricsRoll.getSupplierRollId()+"' where rollId = '"+fabricsRoll.getRollId()+"'";
				session.createSQLQuery(sql).executeUpdate();

				sql = "update tbFabricsAccessoriesTransaction set unitQty = '"+fabricsRoll.getUnitQty()+"',qty = '"+fabricsRoll.getUnitQty()+"' where autoId = '"+fabricsRoll.getAutoId()+"'";
				if(session.createSQLQuery(sql).executeUpdate()==1) {
					tx.commit();
					return "Successful";
				}
			}			
			tx.commit();

			return "Used";
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

		return "Something Wrong";
	}



	@Override
	public String deleteReceiveRollFromTransaction(FabricsRoll fabricsRoll) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		try{
			tx=session.getTransaction();
			tx.begin();
			String sql = "select far.autoId,far.purchaseOrder,far.qty from tbFabricsAccessoriesTransaction far\r\n" + 
					"join tbFabricsAccessoriesTransaction far2\r\n" + 
					"on far.dItemId= far2.cItemId and far2.rollId = far.rollId and far2.itemColorId = far.itemColorId and far2.colorId = far.colorId and far2.styleItemId= far.styleItemId \r\n" + 
					"and far2.styleId = far.styleId and far2.purchaseOrder = far.purchaseOrder and (far.transactionType ='"+StoreTransaction.FABRICS_RETURN.getType()+"' or far.transactionType ='"+StoreTransaction.FABRICS_ISSUE.getType()+"')\r\n" + 
					"where far.autoId = '"+fabricsRoll.getAutoId()+"'";		
			List<?> list = session.createSQLQuery(sql).list();
			if(list.size()==0) {
				sql = "delete from tbFabricsAccessoriesTransaction where autoId = '"+fabricsRoll.getAutoId()+"'";
				if(session.createSQLQuery(sql).executeUpdate()==1) {
					sql = "delete from tbFabricsRollInfo where rollId = '"+fabricsRoll.getRollId()+"'";
					session.createSQLQuery(sql).executeUpdate();
					tx.commit();
					return "Successful";
				}
			}			
			tx.commit();

			return "Used";
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

		return "Something Wrong";
	}



	@Override
	public List<FabricsReceive> getFabricsReceiveList() {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<FabricsReceive> datalist=new ArrayList<FabricsReceive>();	
		try{	
			tx=session.getTransaction();
			tx.begin();		
			String sql = "select autoId,transactionId,grnNo,(select convert(varchar,grnDate,103))as grnDate,location,fri.supplierId,fri.challanNo,fri.challanDate,fri.remarks,fri.preperedBy,fri.createBy \r\n" + 
					"from tbFabricsReceiveInfo fri";		
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				datalist.add(new FabricsReceive(element[0].toString(), element[1].toString(),element[2].toString(), element[3].toString(), element[4].toString(), "", element[5].toString(), element[6].toString(), element[7].toString(), element[8].toString(), element[9].toString(), element[10].toString()));				
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
		return datalist;
	}

	@Override
	public FabricsReceive getFabricsReceiveInfo(String transactionId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		FabricsReceive fabricsReceive = null;
		FabricsRoll tempRoll;
		List<FabricsRoll> fabricsRollList = new ArrayList<FabricsRoll>();	
		try{	
			tx=session.getTransaction();
			tx.begin();		
			String sql = "select far.autoId,far.transactionId,far.purchaseOrder,far.styleId,sc.styleNo,far.styleItemId,id.itemname,far.colorId as itemColorId ,ic.Colorname as itemColorName,dItemId as fabricsId,fi.ItemName as fabricsName,far.itemColorId as fabricsColorId,fc.Colorname as fabricsColorName,far.rollId,fri.supplierRollId,far.unitId,u.unitname,unitQty,rackName,BinName,far.userId,\r\n" + 
					"findent.RequireUnitQty as orderQty,(select isnull(sum(qty),0) from tbFabricsAccessoriesTransaction t where far.purchaseOrder=t.purchaseOrder and far.styleId = t.styleId and far.styleItemId= t.styleItemId and far.colorId = t.colorId and far.dItemId = t.dItemId and far.itemColorId = t.itemColorId and t.transactionType = '1' and t.departmentId = '1' and far.transactionId != t.transactionId) as previousReceiveQty,\r\n" + 
					"(select isnull(sum(qty),0) from tbFabricsAccessoriesTransaction t where far.purchaseOrder=t.purchaseOrder and far.styleId = t.styleId and far.styleItemId= t.styleItemId and far.colorId = t.colorId and far.dItemId = t.cItemId and far.itemColorId = t.itemColorId and far.rollId = t.rollId and t.transactionType = '3' and t.departmentId = '1') as returnQty,\r\n" + 
					"(select isnull(sum(qty),0) from tbFabricsAccessoriesTransaction t where far.purchaseOrder=t.purchaseOrder and far.styleId = t.styleId and far.styleItemId= t.styleItemId and far.colorId = t.colorId and far.dItemId = t.cItemId and far.itemColorId = t.itemColorId and far.rollId = t.rollId and t.transactionType = '4' and t.departmentId = '1') as issueQty,\r\n"
					+ "(select isnull(sum(qty),0) from tbFabricsAccessoriesTransaction qc where far.purchaseOrder=qc.purchaseOrder and far.styleId =qc.styleId and far.styleItemId= qc.styleItemId and far.colorId = qc.colorId and far.dItemId = qc.dItemId and far.itemColorId = qc.itemColorId and far.rollId = qc.rollId and qc.transactionType = '2' and qc.departmentId = '1') as qcPassedQty \r\n" + 
					"from tbFabricsAccessoriesTransaction far\r\n" + 
					"left join tbFabricsIndent findent\r\n" + 
					"on far.PurchaseOrder = findent.purchaseOrder and far.styleId = findent.styleId and far.styleItemId= findent.itemId and far.colorId = findent.itemcolor and far.dItemId = findent.fabricsid and far.itemColorId = findent.fabricscolor\r\n" + 
					"left join TbStyleCreate sc\r\n" + 
					"on far.styleId = sc.StyleId\r\n" + 
					"left join tbItemDescription id\r\n" + 
					"on far.styleItemId = id.itemid\r\n"
					+ "left join tbfabricsRollInfo fri\r\n" + 
					"on far.rollId = fri.rollId \r\n" + 
					"left join tbunits u\r\n" + 
					"on far.unitId = u.Unitid\r\n" + 
					"left join TbFabricsItem fi\r\n" + 
					"on far.dItemId = fi.id\r\n" + 
					"left join tbColors ic\r\n" + 
					"on far.colorId = ic.ColorId\r\n" + 
					"left join tbColors fc\r\n" + 
					"on far.itemColorId = fc.ColorId\r\n" + 
					"where transactionId = '"+transactionId+"' and transactionType='"+StoreTransaction.FABRICS_RECEIVE.getType()+"'";		
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				tempRoll = new FabricsRoll(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString(),element[7].toString(), element[8].toString(),element[9].toString(), element[10].toString(), element[11].toString(), element[12].toString(), element[13].toString(), element[14].toString(), element[15].toString(),element[16].toString(),0.0, Double.valueOf(element[17].toString()), element[18].toString(), element[19].toString(),1);
				tempRoll.setOrderQty(Double.valueOf(element[21].toString()));
				tempRoll.setPreviousReceiveQty(Double.valueOf(element[22].toString()));
				tempRoll.setReturnQty(Double.valueOf(element[23].toString()));
				tempRoll.setIssueQty(Double.valueOf(element[24].toString()));
				tempRoll.setQcPassedQty(Double.valueOf(element[25].toString()));
				fabricsRollList.add(tempRoll);				
			}

			sql = "select autoId,transactionId,grnNo,(select convert(varchar,grnDate,103))as grnDate,location,fri.supplierId,fri.challanNo,fri.challanDate,fri.remarks,fri.preperedBy,fri.createBy \r\n" + 
					"from tbFabricsReceiveInfo fri\r\n" + 
					"where fri.transactionId = '"+transactionId+"'";		
			list = session.createSQLQuery(sql).list();
			if(list.size()>0)
			{	
				Object[] element = (Object[]) list.get(0);

				fabricsReceive = new FabricsReceive(element[0].toString(), element[1].toString(),element[2].toString(), element[3].toString(), element[4].toString(), "", element[5].toString(), element[6].toString(), element[7].toString(), element[8].toString(), element[9].toString(), element[10].toString());
				fabricsReceive.setFabricsRollList(fabricsRollList);
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
		return fabricsReceive;
	}


	//Fabrics Quality Control
	@Override
	public boolean submitFabricsQC(FabricsQualityControl fabricsQC) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		StoreTransaction transaction = StoreTransaction.FABRICS_QC;
		ItemType itemType = ItemType.FABRICS;


		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select (isnull(max(transactionId),0)+1) as maxId from tbfabricsQualityControlInfo";
			List<?> list = session.createSQLQuery(sql).list();
			String transactionId="0";
			if(list.size()>0) {
				transactionId = list.get(0).toString();
			}

			sql="insert into tbfabricsQualityControlInfo (transactionId"
					+ ",date"
					+ ",grnNo"
					+ ",remarks"
					+ ",departmentId"
					+ ",checkBy"
					+ ",entryTime"
					+ ",createBy) values("
					+ "'"+transactionId+"'"
					+ ",'"+fabricsQC.getQcDate()+"'"
					+ ",'"+fabricsQC.getGrnNo()+"'"
					+ ",'"+fabricsQC.getRemarks()+"'"
					+ ",'"+fabricsQC.getDepartmentId()+"'"
					+ ",'"+fabricsQC.getCheckBy()+"',"
					+ "CURRENT_TIMESTAMP,'"+fabricsQC.getUserId()+"');";
			session.createSQLQuery(sql).executeUpdate();


			int departmentId = 1;
			for (FabricsRoll roll : fabricsQC.getFabricsRollList()) {
				sql="insert into tbFabricsAccessoriesTransaction (purchaseOrder,styleId,styleItemId,colorId,itemColorId,transactionId,transactionType,itemType,rollId,unitId,unitQty,qty,dItemId,cItemId,departmentId,rackName,binName,entryTime,userId) \r\n" + 
						"values('"+roll.getPurchaseOrder()+"','"+roll.getStyleId()+"','"+roll.getItemId()+"','"+roll.getItemColorId()+"','"+roll.getFabricsColorId()+"','"+transactionId+"','"+transaction.getType()+"','"+itemType.getType()+"','"+roll.getRollId()+"','"+roll.getUnitId()+"','"+roll.getUnitQty()+"','"+roll.getUnitQty()+"','"+roll.getFabricsId()+"','0','"+departmentId+"','"+roll.getRackName()+"','"+roll.getBinName()+"',CURRENT_TIMESTAMP,'"+fabricsQC.getUserId()+"');";		
				session.createSQLQuery(sql).executeUpdate();
			}

			for (FabricsRoll roll : fabricsQC.getFabricsRollList()) {
				sql="insert into tbQualityControlDetails (transactionId,transactionType,itemType,itemId,rollId,unitId,QCCheckQty,shade,shrinkage,gsm,width,defect,remarks,qcPassedType,entryTime,userId) \r\n" + 
						"values('"+transactionId+"','"+transaction.getType()+"','"+itemType.getType()+"','"+roll.getFabricsId()+"','"+roll.getRollId()+"','"+roll.getUnitId()+"','"+roll.getUnitQty()+"','"+roll.getShadeQty()+"','"+roll.getShrinkageQty()+"','"+roll.getGsmQty()+"','"+roll.getWidthQty()+"','"+roll.getDefectQty()+"','"+roll.getRemarks()+"','"+roll.getQcPassedType()+"',CURRENT_TIMESTAMP,'"+fabricsQC.getUserId()+"')";		
				session.createSQLQuery(sql).executeUpdate();
			}

			tx.commit();

			return true;
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

		return false;
	}

	@Override
	public boolean editFabricsQC(FabricsQualityControl fabricsQC) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="update tbfabricsQualityControlInfo set "
					+ "date = '"+fabricsQC.getQcDate()+"'"
					+ ",grnNo = '"+fabricsQC.getGrnNo()+"'"
					+ ",remarks = '"+fabricsQC.getRemarks()+"'"
					+ ",entryTime = CURRENT_TIMESTAMP"
					+ ",createBy = '"+fabricsQC.getUserId()+"' where qcTransactionId='"+fabricsQC.getQcTransactionId()+"';";

			session.createSQLQuery(sql).executeUpdate();

			if(fabricsQC.getFabricsRollList() != null) {
				for (FabricsRoll roll : fabricsQC.getFabricsRollList()) {
					sql="update tbFabricsRollDetails set QCTransactionId='"+fabricsQC.getQcTransactionId()+"',qcPassedQty='"+roll.getQcPassedQty()+"',qcPassedType='"+roll.getQcPassedType()+"' where autoId='"+roll.getAutoId()+"'";		
					session.createSQLQuery(sql).executeUpdate();
				}
			}


			tx.commit();

			return true;
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

		return false;
	}

	@Override
	public List<FabricsQualityControl> getFabricsQCList() {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<FabricsQualityControl> datalist=new ArrayList<FabricsQualityControl>();	
		try{	
			tx=session.getTransaction();
			tx.begin();		
			String sql = "select qci.AutoId,qci.TransactionId,(select convert(varchar,qci.date,103))as qcDate,qci.grnNo,(select convert(varchar,fri.grnDate,103))as receiveDate,qci.remarks,fri.supplierId,qci.checkBy,qci.createBy \r\n" + 
					"from tbFabricsQualityControlInfo qci\r\n" + 
					"left join tbFabricsReceiveInfo fri\r\n" + 
					"on qci.grnNo = fri.grnNo";		
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				datalist.add(new FabricsQualityControl(element[0].toString(),element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString(), element[7].toString(), element[8].toString()));				
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
		return datalist;
	}

	@Override
	public FabricsQualityControl getFabricsQCInfo(String qcTransactionId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		FabricsQualityControl fabricsQC = null;
		List<FabricsRoll> fabricsRollList = new ArrayList<FabricsRoll>();	
		try{	
			tx=session.getTransaction();
			tx.begin();		
			String sql = "select qcd.autoId,qcd.transactionId,qcd.rollId,fri.supplierRollId,fat.purchaseOrder,fat.styleId,fat.styleItemId,fat.colorId,fat.dItemId,fat.itemColorId,qcd.unitId,u.unitname,far.unitQty,QCCheckQty,shade,shrinkage,gsm,width,defect,remarks,fat.rackName,fat.BinName,qcPassedType,qcd.userId \r\n" + 
					"from tbQualityControlDetails qcd\r\n"
					+ "left join tbfabricsRollInfo fri\r\n" + 
					"on qcd.rollId = fri.rollId\r\n" + 
					"left join tbunits u\r\n" + 
					"on qcd.unitId = u.Unitid\r\n" + 
					"left join tbFabricsAccessoriesTransaction far\r\n" + 
					"on qcd.transactionId = far.transactionId and far.transactionType = '"+StoreTransaction.FABRICS_QC.getType()+"' and qcd.itemId = far.dItemId and qcd.rollId = far.rollId\r\n" + 
					"left join tbFabricsAccessoriesTransaction fat\r\n" + 
					"on qcd.transactionId = fat.transactionId and qcd.transactionType = fat.transactionType and qcd.itemId = fat.dItemId and qcd.rollId = fat.rollId\r\n" + 
					"where qcd.transactionId = '"+qcTransactionId+"' \r\n" + 
					"";		
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				fabricsRollList.add(new FabricsRoll(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString(), element[7].toString(), element[8].toString(), "", element[9].toString(), "", element[10].toString(), element[11].toString(), Double.valueOf(element[12].toString()), Double.valueOf(element[13].toString()), Double.valueOf(element[14].toString()), Double.valueOf(element[15].toString()), Double.valueOf(element[16].toString()), Double.valueOf(element[17].toString()),Double.valueOf(element[18].toString()), element[19].toString(), element[20].toString(), element[21].toString(), Integer.valueOf(element[22].toString())));				
			}
			sql = "select qci.AutoId,qci.TransactionId,(select convert(varchar,qci.date,103))as qcDate,qci.grnNo,(select convert(varchar,fri.grnDate,103))as receiveDate,qci.remarks,fri.supplierId,qci.checkBy,qci.createBy \r\n" + 
					"from tbFabricsQualityControlInfo qci\r\n" + 
					"left join tbFabricsReceiveInfo fri\r\n" + 
					"on qci.grnNo = fri.grnNo where qci.transactionId = '"+qcTransactionId+"'";		
			list = session.createSQLQuery(sql).list();
			if(list.size()>0)
			{	
				Object[] element = (Object[]) list.get(0);

				fabricsQC = new FabricsQualityControl(element[0].toString(), qcTransactionId, element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString(), element[7].toString(),element[8].toString());
				fabricsQC.setFabricsRollList(fabricsRollList);
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
		return fabricsQC;
	}

	@Override
	public List<FabricsRoll> getFabricsRollListBySupplier(String supplierId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		FabricsRoll tempRoll;
		List<FabricsRoll> datalist=new ArrayList<FabricsRoll>();	
		try{	
			tx=session.getTransaction();
			tx.begin();		
			String sql = "select fat.autoID,fri.supplierId,s.name as supplierName,fat.purchaseOrder,fat.styleId,sc.StyleNo,fat.styleItemId,id.itemname,fat.colorid,c.colorName,fat.dItemId as fabricsId,fi.ItemName as fabricsName,fat.itemColorId,ic.Colorname as fabricsColor,fat.rollId,frinfo.supplierRollId,fi.unitId,u.unitname,dbo.fabricsBalanceQty(fat.purchaseOrder,fat.styleid,fat.styleItemId,fat.colorId,fat.dItemId,fat.ItemcolorId,fat.rollId,fat.departmentId) as balanceQty,fat.rackName,fat.binName,\r\n" + 
					"(select isnull(sum(qty),0) from tbFabricsAccessoriesTransaction t where t.dItemId = fat.dItemId and t.transactionId = fat.transactionId and t.transactionType = fat.transactionType and t.itemColorId = fat.itemColorId and t.dItemId = fat.dItemId and t.colorId = fat.colorId and t.styleItemId = fat.styleItemId and t.styleId = fat.styleId and t.purchaseOrder = fat.purchaseOrder) as ReceiveQty,\r\n" + 
					"(select isnull(sum(qty),0) from tbFabricsAccessoriesTransaction t where t.cItemId = fat.dItemId and t.transactionId = fat.transactionId and t.transactionType = '"+StoreTransaction.FABRICS_ISSUE.getType()+"' and t.itemColorId = fat.itemColorId and t.cItemId = fat.dItemId and t.colorId = fat.colorId and t.styleItemId = fat.styleItemId and t.styleId = fat.styleId and t.purchaseOrder = fat.purchaseOrder) as IssueQty,\r\n" +
					"(select isnull(sum(qty),0) from tbFabricsAccessoriesTransaction t where t.cItemId = fat.dItemId and t.transactionId = fat.transactionId and t.transactionType = '"+StoreTransaction.FABRICS_RETURN.getType()+"' and t.itemColorId = fat.itemColorId and t.cItemId = fat.dItemId and t.colorId = fat.colorId and t.styleItemId = fat.styleItemId and t.styleId = fat.styleId and t.purchaseOrder = fat.purchaseOrder) as previousReturnQty\r\n" +
					"from tbFabricsReceiveInfo fri\r\n" + 
					"left join tbSupplier s\r\n" + 
					"on fri.supplierId = s.id\r\n" + 
					"left join tbFabricsAccessoriesTransaction fat\r\n" + 
					"on fri.transactionId = fat.transactionId and fat.transactionType = '"+StoreTransaction.FABRICS_RECEIVE.getType()+"'\r\n" + 
					"left join TbStyleCreate sc\r\n" + 
					"on fat.styleId = sc.StyleId\r\n" + 
					"left join tbItemDescription id\r\n" + 
					"on fat.styleItemId = id.itemid\r\n" + 
					"left join tbColors c\r\n" + 
					"on fat.colorId = c.ColorId\r\n" + 
					"left join TbFabricsItem fi\r\n" + 
					"on fat.dItemId = fi.id\r\n" + 
					"left join tbColors ic\r\n" + 
					"on fat.itemColorId = ic.ColorId\r\n"
					+ "left join tbfabricsRollInfo frinfo\r\n" + 
					"on fat.rollId = frinfo.rollId \r\n" + 
					"left join tbunits u\r\n" + 
					"on fi.unitId = u.Unitid\r\n" + 
					" where fri.supplierId='"+supplierId+"'";		
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				tempRoll = new FabricsRoll(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString(), element[7].toString(), element[8].toString(), element[9].toString(), element[10].toString(), element[11].toString(), element[12].toString(), element[13].toString(), element[14].toString(),element[15].toString(),element[16].toString(),element[17].toString(), Double.valueOf(element[18].toString()),element[19].toString(),element[20].toString());
				tempRoll.setPreviousReceiveQty(Double.valueOf(element[21].toString()));
				tempRoll.setIssueQty(Double.valueOf(element[22].toString()));
				tempRoll.setReturnQty(Double.valueOf(element[23].toString()));

				datalist.add(tempRoll);				
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
		return datalist;
	}



	@Override
	public boolean submitFabricsReturn(FabricsReturn fabricsReturn) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		StoreTransaction transaction = StoreTransaction.FABRICS_RETURN;
		ItemType itemType = ItemType.FABRICS;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select (isnull(max(transactionId),0)+1) as maxId from tbFabricsReturnInfo";
			List<?> list = session.createSQLQuery(sql).list();
			String transactionId="0";
			if(list.size()>0) {
				transactionId = list.get(0).toString();
			}

			sql="insert into tbFabricsReturnInfo (transactionId"
					+ ",date"
					+ ",supplierId"
					+ ",remarks"
					+ ",departmentId"
					+ ",entryTime"
					+ ",createBy) values("
					+ "'"+transactionId+"'"
					+ ",'"+fabricsReturn.getReturnDate()+"'"
					+ ",'"+fabricsReturn.getSupplierId()+"'"
					+ ",'"+fabricsReturn.getRemarks()+"'"
					+ ",'"+fabricsReturn.getDepartmentId()+"',"
					+ "CURRENT_TIMESTAMP,'"+fabricsReturn.getUserId()+"');";
			session.createSQLQuery(sql).executeUpdate();

			int departmentId = 1;
			for (FabricsRoll roll : fabricsReturn.getFabricsRollList()) {
				sql="insert into tbFabricsAccessoriesTransaction (purchaseOrder,styleId,styleItemId,colorId,itemColorId,transactionId,transactionType,itemType,rollId,unitId,unitQty,qty,dItemId,cItemId,departmentId,rackName,binName,entryTime,userId) \r\n" + 
						"values('"+roll.getPurchaseOrder()+"','"+roll.getStyleId()+"','"+roll.getItemId()+"','"+roll.getItemColorId()+"','"+roll.getFabricsColorId()+"','"+transactionId+"','"+transaction.getType()+"','"+itemType.getType()+"','"+roll.getRollId()+"','"+roll.getUnitId()+"','"+roll.getUnitQty()+"','"+roll.getUnitQty()+"','0','"+roll.getFabricsId()+"','"+departmentId+"','"+roll.getRackName()+"','"+roll.getBinName()+"',CURRENT_TIMESTAMP,'"+fabricsReturn.getUserId()+"');";		
				session.createSQLQuery(sql).executeUpdate();
			}

			tx.commit();

			return true;
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

		return false;
	}

	@Override
	public boolean editFabricsReturn(FabricsReturn fabricsReturn) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		StoreTransaction transaction = StoreTransaction.FABRICS_RETURN;
		ItemType itemType = ItemType.FABRICS;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="update tbfabricsReturnInfo set "
					+ "date = '"+fabricsReturn.getReturnDate()+"'"
					+ ",supplierId = '"+fabricsReturn.getSupplierId()+"'"
					+ ",remarks = '"+fabricsReturn.getRemarks()+"'"
					+ ",entryTime = CURRENT_TIMESTAMP"
					+ ",createBy = '"+fabricsReturn.getUserId()+"' where transactionId='"+fabricsReturn.getReturnTransactionId()+"';";

			session.createSQLQuery(sql).executeUpdate();

			int departmentId = 1;
			if(fabricsReturn.getFabricsRollList() != null) {
				for (FabricsRoll roll : fabricsReturn.getFabricsRollList()) {
					sql="insert into tbFabricsAccessoriesTransaction (purchaseOrder,styleId,styleItemId,colorId,itemColorId,transactionId,transactionType,itemType,rollId,unitId,unitQty,qty,dItemId,cItemId,departmentId,rackName,binName,entryTime,userId) \r\n" + 
							"values('"+roll.getPurchaseOrder()+"','"+roll.getStyleId()+"','"+roll.getItemId()+"','"+roll.getItemColorId()+"','"+roll.getFabricsColorId()+"','"+fabricsReturn.getReturnTransactionId()+"','"+transaction.getType()+"','"+itemType.getType()+"','"+roll.getRollId()+"','"+roll.getUnitId()+"','"+roll.getUnitQty()+"','"+roll.getUnitQty()+"','0','"+roll.getFabricsId()+"','"+departmentId+"','"+roll.getRackName()+"','"+roll.getBinName()+"',CURRENT_TIMESTAMP,'"+fabricsReturn.getUserId()+"');";		
					session.createSQLQuery(sql).executeUpdate();
				}
			}


			tx.commit();

			return true;
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

		return false;
	}

	@Override
	public String editReturnRollInTransaction(FabricsRoll fabricsRoll) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		try{
			tx=session.getTransaction();
			tx.begin();
			String sql = "select far.autoId,dbo.fabricsBalanceQtyExceptAutoId(far.purchaseOrder,far.styleId,far.styleItemId,far.colorId,far.cItemId,far.itemColorId,far.rollId,far.departmentId,far.autoId) as balanceQty \r\n" + 
					"from tbFabricsAccessoriesTransaction far\r\n" + 
					"where far.autoId = '"+fabricsRoll.getAutoId()+"'";		
			List<?> list = session.createSQLQuery(sql).list();
			if(list.size()>0) {
				Object[] element = (Object[]) list.get(0);
				if(Double.valueOf(element[1].toString())>=fabricsRoll.getUnitQty()) {
					sql = "update tbFabricsAccessoriesTransaction set unitQty = '"+fabricsRoll.getUnitQty()+"',qty = '"+fabricsRoll.getUnitQty()+"' where autoId = '"+fabricsRoll.getAutoId()+"'";
					if(session.createSQLQuery(sql).executeUpdate()==1) {
						tx.commit();
						return "Successful";
					}
				}else {
					tx.commit();
					return "Return Qty Exist";
				}
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

		return "Something Wrong";
	}



	@Override
	public String deleteReturnRollFromTransaction(FabricsRoll fabricsRoll) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql = "delete from tbFabricsAccessoriesTransaction where autoId = '"+fabricsRoll.getAutoId()+"'";
			if(session.createSQLQuery(sql).executeUpdate()==1) {
				tx.commit();
				return "Successful";
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

		return "Something Wrong";
	}



	@Override
	public List<FabricsReturn> getFabricsReturnList() {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<FabricsReturn> datalist=new ArrayList<FabricsReturn>();	
		try{	
			tx=session.getTransaction();
			tx.begin();		
			String sql = "select fri.AutoId,fri.transactionId,(select convert(varchar,fri.date,103))as returnDate,fri.supplierId,s.name as supplierName,fri.remarks,fri.createBy \r\n" + 
					"from tbFabricsReturnInfo fri\r\n" + 
					"left join tbSupplier s\r\n" + 
					"on fri.supplierId = s.id";		
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				datalist.add(new FabricsReturn(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString()));				
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
		return datalist;
	}

	@Override
	public FabricsReturn getFabricsReturnInfo(String returnTransactionId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		FabricsReturn fabricsReturn = null;
		FabricsRoll tempRoll;
		List<FabricsRoll> fabricsRollList = new ArrayList<FabricsRoll>();	
		String departmentId = "1";
		try{	
			tx=session.getTransaction();
			tx.begin();		
			String sql = "select far.autoId,far.transactionId,far.purchaseOrder,far.styleId,sc.styleNo,far.styleItemId,id.itemname,far.colorId as itemColorId ,ic.Colorname as itemColorName,cItemId as fabricsId,fi.ItemName as fabricsName,far.itemColorId as fabricsColorId,fc.Colorname as fabricsColorName,far.rollId,fri.supplierRollId,far.unitId,u.unitname,unitQty,rackName,BinName,far.userId\r\n" + 
					",dbo.fabricsBalanceQty(far.purchaseOrder,far.styleId,far.styleItemId,far.colorId,far.cItemId,far.itemColorId,far.rollId,far.departmentId) as balanceQty \r\n" +
					",(select isnull(sum(qty),0) from tbFabricsAccessoriesTransaction t where far.purchaseOrder=t.purchaseOrder and far.styleId = t.styleId and far.styleItemId= t.styleItemId and far.colorId = t.colorId and far.cItemId = t.dItemId and far.itemColorId = t.itemColorId and t.transactionType = '"+StoreTransaction.FABRICS_RECEIVE.getType()+"' and t.departmentId = '"+departmentId+"') as previousReceiveQty,\r\n" + 
					"(select isnull(sum(qty),0) from tbFabricsAccessoriesTransaction t where far.purchaseOrder=t.purchaseOrder and far.styleId = t.styleId and far.styleItemId= t.styleItemId and far.colorId = t.colorId and far.cItemId = t.cItemId and far.itemColorId = t.itemColorId and t.transactionType = '"+StoreTransaction.FABRICS_RETURN.getType()+"' and t.departmentId = '"+departmentId+"') as returnQty,\r\n" + 
					"(select isnull(sum(qty),0) from tbFabricsAccessoriesTransaction t where far.purchaseOrder=t.purchaseOrder and far.styleId = t.styleId and far.styleItemId= t.styleItemId and far.colorId = t.colorId and far.cItemId = t.cItemId and far.itemColorId = t.itemColorId and t.transactionType = '"+StoreTransaction.FABRICS_ISSUE.getType()+"' and t.departmentId = '"+departmentId+"') as issueQty\r\n" + 
					"from tbFabricsAccessoriesTransaction far\r\n" + 
					"left join TbStyleCreate sc\r\n" + 
					"on far.styleId = sc.StyleId\r\n" + 
					"left join tbItemDescription id\r\n" + 
					"on far.styleItemId = id.itemid\r\n"
					+ "left join tbfabricsRollInfo fri\r\n" + 
					"on far.rollId = fri.rollId \r\n" + 
					"left join tbunits u\r\n" + 
					"on far.unitId = u.Unitid\r\n" + 
					"left join TbFabricsItem fi\r\n" + 
					"on far.cItemId = fi.id\r\n" + 
					"left join tbColors ic\r\n" + 
					"on far.colorId = ic.ColorId\r\n" + 
					"left join tbColors fc\r\n" + 
					"on far.itemColorId = fc.ColorId\r\n" + 
					"where transactionId = '"+returnTransactionId+"' and transactionType='"+StoreTransaction.FABRICS_RETURN.getType()+"'";	
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				tempRoll = new FabricsRoll(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString(),element[7].toString(), element[8].toString(),element[9].toString(), element[10].toString(), element[11].toString(), element[12].toString(), element[13].toString(), element[14].toString(), element[15].toString(),element[16].toString(),0.0, Double.valueOf(element[17].toString()), element[18].toString(), element[19].toString(),1);
				tempRoll.setBalanceQty(Double.valueOf(element[21].toString()));
				tempRoll.setPreviousReceiveQty(Double.valueOf(element[22].toString()));
				tempRoll.setReturnQty(Double.valueOf(element[23].toString()));
				tempRoll.setIssueQty(Double.valueOf(element[24].toString()));

				fabricsRollList.add(tempRoll);				
			}
			sql = "select fri.autoId,fri.transactionId,(select convert(varchar,fri.date,103))as date,fri.supplierId,s.name as supplierName,fri.remarks,fri.createBy \r\n" + 
					"from tbFabricsReturnInfo fri\r\n" + 
					"left join tbSupplier s\r\n" + 
					"on fri.supplierId = s.id\r\n" + 
					"where fri.transactionId = '"+returnTransactionId+"'";		
			list = session.createSQLQuery(sql).list();
			if(list.size()>0)
			{	
				Object[] element = (Object[]) list.get(0);

				fabricsReturn = new FabricsReturn(element[0].toString(),element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString());
				fabricsReturn.setFabricsRollList(fabricsRollList);
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
		return fabricsReturn;
	}

	@Override
	public FabricsReceive getFabricsReceiveInfoForReturn(String transactionId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		FabricsReceive fabricsReceive = null;
		List<FabricsRoll> fabricsRollList = new ArrayList<FabricsRoll>();	
		try{	
			tx=session.getTransaction();
			tx.begin();		
			String sql = "select autoId,isnull(returnTransactionId,'')as returnTransactionId,rollId,supplierRollId,frd.unitId,u.unitname,rollQty,qcPassedQty,rackName,BinName,qcPassedType,isReturn,createBy  \r\n" + 
					"from tbFabricsRollDetails frd\r\n" + 
					"left join tbunits u\r\n" + 
					"on frd.unitId = u.Unitid\r\n" + 
					"where transactionId = '"+transactionId+"'";		
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				boolean isReturn = element[11].toString().equals("1")?true:false;
				//fabricsRollList.add(new FabricsRoll(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), Double.valueOf(element[6].toString()), Double.valueOf(element[7].toString()), element[8].toString(), element[9].toString(), Integer.valueOf(element[10].toString()), isReturn, element[12].toString()));				
			}

			sql = "select autoId,transactionId,grnNo,(select convert(varchar,grnDate,103))as grnDate,location,fIndent.PurchaseOrder,fIndent.styleId,sc.StyleNo,fIndent.itemid,id.itemname,fIndent.fabricscolor as colorId,c.Colorname,fri.fabricsId,ISNULL(fi.ItemName,'')as fabricsName,indentId,fri.unitId,grnQty,noOfRoll,fri.supplierId,fri.buyer,fri.challanNo,fri.challanDate,fri.remarks,fri.preperedBy,fri.createBy \r\n" + 
					"from tbFabricsReceiveInfo fri\r\n" + 
					"left join tbFabricsIndent fIndent\r\n" + 
					"on fri.indentId = fIndent.id\r\n" + 
					"left join TbStyleCreate sc\r\n" + 
					"on fIndent.styleId = sc.StyleId\r\n" + 
					"left join tbItemDescription id\r\n" + 
					"on fIndent.itemid = id.itemid\r\n" + 
					"left join tbColors c\r\n" + 
					"on fIndent.itemcolor = c.ColorId\r\n" + 
					"left join TbFabricsItem fi\r\n" + 
					"on fIndent.fabricsId = fi.id where fri.transactionId = '"+transactionId+"'";		
			list = session.createSQLQuery(sql).list();
			if(list.size()>0)
			{	
				Object[] element = (Object[]) list.get(0);
				boolean  isReturn = element[24].toString()=="1"?true:false;
				fabricsReceive = new FabricsReceive();
				fabricsReceive.setFabricsRollList(fabricsRollList);
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
		return fabricsReceive;

	}

	@Override
	public List<FabricsRoll> getAvailableFabricsRollListInDepartment(String departmentId) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		FabricsRoll tempRoll;
		List<FabricsRoll> datalist=new ArrayList<FabricsRoll>();	
		try{	
			tx=session.getTransaction();
			tx.begin();		
			String sql = "select fat.autoId,'supplierId' as supplierId,'supplierName' as supplierName,fat.purchaseOrder,fat.styleId,sc.StyleNo,fat.styleItemId,id.itemname,fat.colorid,c.colorName,fat.dItemId as fabricsId,fi.ItemName as fabricsName,fat.itemColorId,ic.Colorname as fabricsColor,fat.rollId,frinfo.supplierRollId,fi.unitId,u.unitname,dbo.fabricsBalanceQty(fat.purchaseOrder,fat.styleid,fat.styleItemId,fat.colorId,fat.dItemId,fat.ItemcolorId,fat.rollId,fat.departmentId) as balanceQty,fat.rackName,fat.binName,\r\n" + 
					"(select isnull(sum(qty),0) from tbFabricsAccessoriesTransaction t where t.dItemId = fat.dItemId and t.transactionId = fat.transactionId and t.transactionType = fat.transactionType and t.itemColorId = fat.itemColorId and t.dItemId = fat.dItemId and t.colorId = fat.colorId and t.styleItemId = fat.styleItemId and t.styleId = fat.styleId and t.purchaseOrder = fat.purchaseOrder) as ReceiveQty,\r\n" + 
					"(select isnull(sum(qty),0) from tbFabricsAccessoriesTransaction t where t.cItemId = fat.dItemId and t.transactionId = fat.transactionId and t.transactionType = '"+StoreTransaction.FABRICS_ISSUE.getType()+"' and t.itemColorId = fat.itemColorId and t.cItemId = fat.dItemId and t.colorId = fat.colorId and t.styleItemId = fat.styleItemId and t.styleId = fat.styleId and t.purchaseOrder = fat.purchaseOrder) as IssueQty,\r\n" + 
					"(select isnull(sum(qty),0) from tbFabricsAccessoriesTransaction t where t.cItemId = fat.dItemId and t.transactionId = fat.transactionId and t.transactionType = '"+StoreTransaction.FABRICS_RETURN.getType()+"' and t.itemColorId = fat.itemColorId and t.cItemId = fat.dItemId and t.colorId = fat.colorId and t.styleItemId = fat.styleItemId and t.styleId = fat.styleId and t.purchaseOrder = fat.purchaseOrder) as previousReturnQty\r\n" + 
					"from tbFabricsAccessoriesTransaction as fat\r\n" + 
					"left join TbStyleCreate sc\r\n" + 
					"on fat.styleId = sc.StyleId\r\n" + 
					"left join tbItemDescription id\r\n" + 
					"on fat.styleItemId = id.itemid\r\n" + 
					"left join tbColors c\r\n" + 
					"on fat.colorId = c.ColorId\r\n" + 
					"left join TbFabricsItem fi\r\n" + 
					"on fat.dItemId = fi.id\r\n" + 
					"left join tbColors ic\r\n" + 
					"on fat.itemColorId = ic.ColorId\r\n" + 
					"left join tbfabricsRollInfo frinfo\r\n" + 
					"on fat.rollId = frinfo.rollId \r\n" + 
					"left join tbunits u\r\n" + 
					"on fi.unitId = u.Unitid\r\n" + 
					"where fat.transactionType = '"+StoreTransaction.FABRICS_RECEIVE.getType()+"' and fat.departmentId = '"+departmentId+"' \r\n"+
					"and dbo.fabricsBalanceQty(fat.purchaseOrder,fat.styleid,fat.styleItemId,fat.colorId,fat.dItemId,fat.ItemcolorId,fat.rollId,fat.departmentId) > 0 \r\n"+
					"order by fat.purchaseOrder,fat.styleId,fat.styleItemId,fat.colorId,fat.dItemId,fat.itemColorId";		
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				tempRoll = new FabricsRoll(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString(), element[7].toString(), element[8].toString(), element[9].toString(), element[10].toString(), element[11].toString(), element[12].toString(), element[13].toString(), element[14].toString(),element[15].toString(),element[16].toString(),element[17].toString(), Double.valueOf(element[18].toString()),element[19].toString(),element[20].toString());
				tempRoll.setPreviousReceiveQty(Double.valueOf(element[21].toString()));
				tempRoll.setIssueQty(Double.valueOf(element[22].toString()));
				tempRoll.setReturnQty(Double.valueOf(element[23].toString()));
				datalist.add(tempRoll);				
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
		return datalist;
	}

	@Override
	public boolean submitFabricsIssue(FabricsIssue fabricsIssue) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		StoreTransaction transaction = StoreTransaction.FABRICS_ISSUE;
		ItemType itemType = ItemType.FABRICS;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select (isnull(max(transactionId),0)+1) as maxId from tbFabricsIssueInfo";
			List<?> list = session.createSQLQuery(sql).list();
			String transactionId="0";
			if(list.size()>0) {
				transactionId = list.get(0).toString();
			}

			sql="insert into tbFabricsIssueInfo (transactionId"
					+ ",date"
					+ ",issuedTo"
					+ ",receiveBy"
					+ ",remarks"
					+ ",departmentId"
					+ ",entryTime"
					+ ",createBy) values("
					+ "'"+transactionId+"'"
					+ ",'"+fabricsIssue.getIssueDate()+"'"
					+ ",'"+fabricsIssue.getIssuedTo()+"'"
					+ ",'"+fabricsIssue.getReceiveBy()+"'"
					+ ",'"+fabricsIssue.getRemarks()+"'"
					+ ",'"+fabricsIssue.getDepartmentId()+"',"
					+ "CURRENT_TIMESTAMP,'"+fabricsIssue.getUserId()+"');";
			session.createSQLQuery(sql).executeUpdate();
			int departmentId = 1;
			for (FabricsRoll roll : fabricsIssue.getFabricsRollList()) {
				sql="insert into tbFabricsAccessoriesTransaction (purchaseOrder,styleId,styleItemId,colorId,itemColorId,transactionId,transactionType,itemType,rollId,unitId,unitQty,qty,dItemId,cItemId,departmentId,rackName,binName,entryTime,userId) \r\n" + 
						"values('"+roll.getPurchaseOrder()+"','"+roll.getStyleId()+"','"+roll.getItemId()+"','"+roll.getItemColorId()+"','"+roll.getFabricsColorId()+"','"+transactionId+"','"+transaction.getType()+"','"+itemType.getType()+"','"+roll.getRollId()+"','"+roll.getUnitId()+"','"+roll.getUnitQty()+"','"+roll.getUnitQty()+"','0','"+roll.getFabricsId()+"','"+departmentId+"','"+roll.getRackName()+"','"+roll.getBinName()+"',CURRENT_TIMESTAMP,'"+fabricsIssue.getUserId()+"');";		
				session.createSQLQuery(sql).executeUpdate();
			}

			tx.commit();

			return true;
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

		return false;
	}

	@Override
	public boolean editFabricsIssue(FabricsIssue fabricsIssue) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		StoreTransaction transaction = StoreTransaction.FABRICS_ISSUE;
		ItemType itemType = ItemType.FABRICS;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="update tbfabricsIssueInfo set "
					+ "date = '"+fabricsIssue.getIssueDate()+"'"
					+ ",issuedTo = '"+fabricsIssue.getIssuedTo()+"'"
					+ ",receiveBy = '"+fabricsIssue.getReceiveBy()+"'"
					+ ",remarks = '"+fabricsIssue.getRemarks()+"'"
					+ ",entryTime = CURRENT_TIMESTAMP"
					+ ",createBy = '"+fabricsIssue.getUserId()+"' where transactionId='"+fabricsIssue.getTransactionId()+"';";

			session.createSQLQuery(sql).executeUpdate();

			int departmentId = 1;
			if(fabricsIssue.getFabricsRollList() != null) {
				for (FabricsRoll roll : fabricsIssue.getFabricsRollList()) {
					sql="insert into tbFabricsAccessoriesTransaction (purchaseOrder,styleId,styleItemId,colorId,itemColorId,transactionId,transactionType,itemType,rollId,unitId,unitQty,qty,dItemId,cItemId,departmentId,rackName,binName,entryTime,userId) \r\n" + 
							"values('"+roll.getPurchaseOrder()+"','"+roll.getStyleId()+"','"+roll.getItemId()+"','"+roll.getItemColorId()+"','"+roll.getFabricsColorId()+"','"+fabricsIssue.getTransactionId()+"','"+transaction.getType()+"','"+itemType.getType()+"','"+roll.getRollId()+"','"+roll.getUnitId()+"','"+roll.getUnitQty()+"','"+roll.getUnitQty()+"','0','"+roll.getFabricsId()+"','"+departmentId+"','"+roll.getRackName()+"','"+roll.getBinName()+"',CURRENT_TIMESTAMP,'"+fabricsIssue.getUserId()+"');";		
					session.createSQLQuery(sql).executeUpdate();
				}
			}


			tx.commit();

			return true;
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

		return false;
	}

	@Override
	public String editIssuedRollInTransaction(FabricsRoll fabricsRoll) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		try{
			tx=session.getTransaction();
			tx.begin();
			String sql = "select far.autoId,dbo.fabricsBalanceQtyExceptAutoId(far.purchaseOrder,far.styleId,far.styleItemId,far.colorId,far.cItemId,far.itemColorId,far.rollId,far.departmentId,far.autoId) as balanceQty \r\n" + 
					"from tbFabricsAccessoriesTransaction far\r\n" + 
					"where far.autoId = '"+fabricsRoll.getAutoId()+"'";		
			List<?> list = session.createSQLQuery(sql).list();
			if(list.size()>0) {
				Object[] element = (Object[]) list.get(0);
				if(Double.valueOf(element[1].toString())>=fabricsRoll.getUnitQty()) {
					sql = "update tbFabricsAccessoriesTransaction set unitQty = '"+fabricsRoll.getUnitQty()+"',qty = '"+fabricsRoll.getUnitQty()+"' where autoId = '"+fabricsRoll.getAutoId()+"'";
					if(session.createSQLQuery(sql).executeUpdate()==1) {
						tx.commit();
						return "Successful";
					}
				}else {
					tx.commit();
					return "Return Qty Exist";
				}
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

		return "Something Wrong";
	}

	@Override
	public String deleteIssuedRollFromTransaction(FabricsRoll fabricsRoll) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql = "delete from tbFabricsAccessoriesTransaction where autoId = '"+fabricsRoll.getAutoId()+"'";
			if(session.createSQLQuery(sql).executeUpdate()==1) {
				tx.commit();
				return "Successful";
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

		return "Something Wrong";
	}
	@Override
	public List<FabricsRoll> getIssuedFabricsRollListInDepartment(String departmentId, String returnDepartmentId) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		FabricsRoll tempRoll;
		List<FabricsRoll> datalist=new ArrayList<FabricsRoll>();	
		try{	
			tx=session.getTransaction();
			tx.begin();		
			String sql = "select sil.*,\r\n" + 
					"isnull(dbo.fabricsIssueReturnedQty('"+departmentId+"','"+returnDepartmentId+"',sil.rollId),0) as previousIssueReturnQty\r\n" + 
					"from dbo.issuedItemList('"+departmentId+"','"+returnDepartmentId+"') as sil\r\n"
							+ "where (sil.issuedQty - isnull(dbo.fabricsIssueReturnedQty('"+departmentId+"','"+returnDepartmentId+"',sil.rollId),0))>0";		
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				tempRoll = new FabricsRoll(element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString(), element[7].toString(), element[8].toString(), element[9].toString(), element[10].toString(), element[11].toString(), element[12].toString(), element[13].toString(), element[14].toString(), element[15].toString(), element[16].toString(), Double.valueOf(element[17].toString()), 0, Double.valueOf(element[18].toString()));			
				datalist.add(tempRoll);				
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
		return datalist;
	}



	@Override
	public List<FabricsIssue> getFabricsIssueList() {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		FabricsIssue tempIssue;
		List<FabricsIssue> datalist=new ArrayList<FabricsIssue>();	
		try{	
			tx=session.getTransaction();
			tx.begin();		
			String sql = "select fii.AutoId,fii.transactionId,(select convert(varchar,fii.date,103))as issuedDate,fii.issuedTo,fii.receiveBy,fii.remarks,fii.createBy,di.DepartmentName,fi.FactoryName\r\n" + 
					"from tbFabricsIssueInfo fii\r\n" + 
					"left join TbDepartmentInfo di\r\n" + 
					"on fii.issuedTo = di.DepartmentId\r\n" + 
					"left join TbFactoryInfo fi\r\n" + 
					"on di.FactoryId = fi.FactoryId";		
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				tempIssue = new FabricsIssue(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString());
				tempIssue.setIssuedDepartmentName(element[7].toString()+"("+element[8].toString()+")");
				datalist.add(tempIssue);				
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
		return datalist;
	}
	@Override
	public FabricsIssue getFabricsIssueInfo(String issueTransactionId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		FabricsIssue fabricsIssue = null;
		FabricsRoll tempRoll;
		List<FabricsRoll> fabricsRollList = new ArrayList<FabricsRoll>();	
		String departmentId = "1";
		try{	
			tx=session.getTransaction();
			tx.begin();		
			String sql = "select far.autoId,far.transactionId,far.purchaseOrder,far.styleId,sc.styleNo,far.styleItemId,id.itemname,far.colorId as itemColorId ,ic.Colorname as itemColorName,cItemId as fabricsId,fi.ItemName as fabricsName,far.itemColorId as fabricsColorId,fc.Colorname as fabricsColorName,far.rollId,fri.supplierRollId,far.unitId,u.unitname,unitQty,rackName,BinName,far.userId\r\n" + 
					",dbo.fabricsBalanceQty(far.purchaseOrder,far.styleId,far.styleItemId,far.colorId,far.cItemId,far.itemColorId,far.rollId,far.departmentId) as balanceQty \r\n" +
					",(select isnull(sum(qty),0) from tbFabricsAccessoriesTransaction t where far.purchaseOrder=t.purchaseOrder and far.styleId = t.styleId and far.styleItemId= t.styleItemId and far.colorId = t.colorId and far.cItemId = t.dItemId and far.itemColorId = t.itemColorId and t.transactionType = '"+StoreTransaction.FABRICS_RECEIVE.getType()+"' and t.departmentId = '"+departmentId+"') as previousReceiveQty,\r\n" + 
					"(select isnull(sum(qty),0) from tbFabricsAccessoriesTransaction t where far.purchaseOrder=t.purchaseOrder and far.styleId = t.styleId and far.styleItemId= t.styleItemId and far.colorId = t.colorId and far.cItemId = t.cItemId and far.itemColorId = t.itemColorId and t.transactionType = '"+StoreTransaction.FABRICS_RETURN.getType()+"' and t.departmentId = '"+departmentId+"') as returnQty,\r\n" + 
					"(select isnull(sum(qty),0) from tbFabricsAccessoriesTransaction t where far.purchaseOrder=t.purchaseOrder and far.styleId = t.styleId and far.styleItemId= t.styleItemId and far.colorId = t.colorId and far.cItemId = t.cItemId and far.itemColorId = t.itemColorId and t.transactionType = '"+StoreTransaction.FABRICS_ISSUE.getType()+"' and t.departmentId = '"+departmentId+"') as issueQty\r\n" + 
					"from tbFabricsAccessoriesTransaction far\r\n" + 
					"left join TbStyleCreate sc\r\n" + 
					"on far.styleId = sc.StyleId\r\n" + 
					"left join tbItemDescription id\r\n" + 
					"on far.styleItemId = id.itemid\r\n"
					+ "left join tbfabricsRollInfo fri\r\n" + 
					"on far.rollId = fri.rollId \r\n" + 
					"left join tbunits u\r\n" + 
					"on far.unitId = u.Unitid\r\n" + 
					"left join TbFabricsItem fi\r\n" + 
					"on far.cItemId = fi.id\r\n" + 
					"left join tbColors ic\r\n" + 
					"on far.colorId = ic.ColorId\r\n" + 
					"left join tbColors fc\r\n" + 
					"on far.itemColorId = fc.ColorId\r\n" + 
					"where transactionId = '"+issueTransactionId+"' and transactionType='"+StoreTransaction.FABRICS_ISSUE.getType()+"'";	
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				tempRoll = new FabricsRoll(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString(),element[7].toString(), element[8].toString(),element[9].toString(), element[10].toString(), element[11].toString(), element[12].toString(), element[13].toString(), element[14].toString(), element[15].toString(),element[16].toString(),0.0, Double.valueOf(element[17].toString()), element[18].toString(), element[19].toString(),1);
				tempRoll.setBalanceQty(Double.valueOf(element[21].toString()));
				tempRoll.setPreviousReceiveQty(Double.valueOf(element[22].toString()));
				tempRoll.setReturnQty(Double.valueOf(element[23].toString()));
				tempRoll.setIssueQty(Double.valueOf(element[24].toString()));

				fabricsRollList.add(tempRoll);				
			}
			sql = "select fii.AutoId,fii.transactionId,(select convert(varchar,fii.date,103))as issuedDate,fii.issuedTo,fii.receiveBy,fii.remarks,fii.createBy,di.DepartmentName,fi.FactoryName\r\n" + 
					"from tbFabricsIssueInfo fii\r\n" + 
					"left join TbDepartmentInfo di\r\n" + 
					"on fii.issuedTo = di.DepartmentId\r\n" + 
					"left join TbFactoryInfo fi\r\n" + 
					"on di.FactoryId = fi.FactoryId\r\n" + 
					"where fii.transactionId = '"+issueTransactionId+"'\r\n" + 
					"";		
			list = session.createSQLQuery(sql).list();
			if(list.size()>0)
			{	
				Object[] element = (Object[]) list.get(0);

				fabricsIssue = new FabricsIssue(element[0].toString(), element[1].toString(), element[2].toString(),  element[3].toString(),  element[4].toString(),  element[5].toString(), element[6].toString());
				fabricsIssue.setFabricsRollList(fabricsRollList);
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
		return fabricsIssue;
	}



	@Override
	public boolean submitFabricsIssueReturn(FabricsIssueReturn fabricsIssueReturn) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		StoreTransaction transaction = StoreTransaction.FABRICS_ISSUE_RETURN;
		ItemType itemType = ItemType.FABRICS;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select (isnull(max(transactionId),0)+1) as maxId from tbFabricsIssueReturnInfo";
			List<?> list = session.createSQLQuery(sql).list();
			String transactionId="0";
			if(list.size()>0) {
				transactionId = list.get(0).toString();
			}

			sql="insert into tbFabricsIssueReturnInfo (transactionId"
					+ ",date"
					+ ",issueReturnFrom"
					+ ",receiveFrom"
					+ ",remarks"
					+ ",departmentId"
					+ ",entryTime"
					+ ",createBy) values("
					+ "'"+transactionId+"'"
					+ ",'"+fabricsIssueReturn.getIssueReturnDate()+"'"
					+ ",'"+fabricsIssueReturn.getIssueReturnFrom()+"'"
					+ ",'"+fabricsIssueReturn.getReceiveFrom()+"'"
					+ ",'"+fabricsIssueReturn.getRemarks()+"'"
					+ ",'"+fabricsIssueReturn.getDepartmentId()+"',"
					+ "CURRENT_TIMESTAMP,'"+fabricsIssueReturn.getUserId()+"');";
			session.createSQLQuery(sql).executeUpdate();
			int departmentId = 1;
			for (FabricsRoll roll : fabricsIssueReturn.getFabricsRollList()) {
				sql="insert into tbFabricsAccessoriesTransaction (purchaseOrder,styleId,styleItemId,colorId,itemColorId,transactionId,transactionType,itemType,rollId,unitId,unitQty,qty,dItemId,cItemId,departmentId,rackName,binName,entryTime,userId) \r\n" + 
						"values('"+roll.getPurchaseOrder()+"','"+roll.getStyleId()+"','"+roll.getItemId()+"','"+roll.getItemColorId()+"','"+roll.getFabricsColorId()+"','"+transactionId+"','"+transaction.getType()+"','"+itemType.getType()+"','"+roll.getRollId()+"','"+roll.getUnitId()+"','"+roll.getUnitQty()+"','"+roll.getUnitQty()+"','"+roll.getFabricsId()+"','0','"+departmentId+"','"+roll.getRackName()+"','"+roll.getBinName()+"',CURRENT_TIMESTAMP,'"+fabricsIssueReturn.getUserId()+"');";		
				session.createSQLQuery(sql).executeUpdate();
			}

			tx.commit();

			return true;
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

		return false;
	}

	@Override
	public boolean editFabricsIssueReturn(FabricsIssueReturn fabricsIssueReturn) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		StoreTransaction transaction = StoreTransaction.FABRICS_ISSUE_RETURN;
		ItemType itemType = ItemType.FABRICS;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="update tbfabricsIssueReturnInfo set "
					+ "date = '"+fabricsIssueReturn.getIssueReturnDate()+"'"
					+ ",issueReturnFrom = '"+fabricsIssueReturn.getIssueReturnFrom()+"'"
					+ ",receiveFrom = '"+fabricsIssueReturn.getReceiveFrom()+"'"
					+ ",remarks = '"+fabricsIssueReturn.getRemarks()+"'"
					+ ",entryTime = CURRENT_TIMESTAMP"
					+ ",createBy = '"+fabricsIssueReturn.getUserId()+"' where transactionId='"+fabricsIssueReturn.getTransactionId()+"';";

			session.createSQLQuery(sql).executeUpdate();

			int departmentId = 1;
			if(fabricsIssueReturn.getFabricsRollList() != null) {
				for (FabricsRoll roll : fabricsIssueReturn.getFabricsRollList()) {
					sql="insert into tbFabricsAccessoriesTransaction (purchaseOrder,styleId,styleItemId,colorId,itemColorId,transactionId,transactionType,itemType,rollId,unitId,unitQty,qty,dItemId,cItemId,departmentId,rackName,binName,entryTime,userId) \r\n" + 
							"values('"+roll.getPurchaseOrder()+"','"+roll.getStyleId()+"','"+roll.getItemId()+"','"+roll.getItemColorId()+"','"+roll.getFabricsColorId()+"','"+fabricsIssueReturn.getTransactionId()+"','"+transaction.getType()+"','"+itemType.getType()+"','"+roll.getRollId()+"','"+roll.getUnitId()+"','"+roll.getUnitQty()+"','"+roll.getUnitQty()+"','"+roll.getFabricsId()+"','0','"+departmentId+"','"+roll.getRackName()+"','"+roll.getBinName()+"',CURRENT_TIMESTAMP,'"+fabricsIssueReturn.getUserId()+"');";		
					session.createSQLQuery(sql).executeUpdate();
				}
			}
			

			tx.commit();

			return true;
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

		return false;
	}

	@Override
	public String editIssueReturndRollInTransaction(FabricsRoll fabricsRoll) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		try{
			tx=session.getTransaction();
			tx.begin();
			String sql = "select fat.transactionId,dbo.fabricsIssuedQty(firi.departmentId,firi.issueReturnFrom,fat.rollId) as issuedQty,dbo.fabricsIssueReturnedQty(firi.departmentId,firi.issueReturnFrom,fat.rollId) as returnedQty from tbFabricsAccessoriesTransaction fat\r\n" + 
					"left join tbfabricsIssueReturnInfo firi\r\n" + 
					"on fat.transactionId = firi.transactionId where fat.autoId='"+fabricsRoll.getAutoId()+"' and (dbo.fabricsIssuedQty(firi.departmentId,firi.issueReturnFrom,fat.rollId)-dbo.fabricsIssueReturnedQty(firi.departmentId,firi.issueReturnFrom,fat.rollId)-"+fabricsRoll.getUnitQty()+")>=0";		
			List<?> list = session.createSQLQuery(sql).list();
			if(list.size()>0) {
				Object[] element = (Object[]) list.get(0);
				if(Double.valueOf(element[1].toString())>=fabricsRoll.getUnitQty()) {
					sql = "update tbFabricsAccessoriesTransaction set unitQty = '"+fabricsRoll.getUnitQty()+"',qty = '"+fabricsRoll.getUnitQty()+"',rackName='"+fabricsRoll.getRackName()+"',binName='"+fabricsRoll.getBinName()+"' where autoId = '"+fabricsRoll.getAutoId()+"'";
					if(session.createSQLQuery(sql).executeUpdate()==1) {
						tx.commit();
						return "Successful";
					}
				}else {
					tx.commit();
					return "Return Qty Exist";
				}
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

		return "Something Wrong";
	}

	@Override
	public String deleteIssueReturndRollFromTransaction(FabricsRoll fabricsRoll) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql = "delete from tbFabricsAccessoriesTransaction where autoId = '"+fabricsRoll.getAutoId()+"'";
			if(session.createSQLQuery(sql).executeUpdate()==1) {
				tx.commit();
				return "Successful";
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

		return "Something Wrong";
	}

	@Override
	public List<FabricsIssueReturn> getFabricsIssueReturnList() {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		FabricsIssueReturn tempIssueReturn;
		List<FabricsIssueReturn> datalist=new ArrayList<FabricsIssueReturn>();	
		try{	
			tx=session.getTransaction();
			tx.begin();		
			String sql = "select firi.AutoId,firi.transactionId,(select convert(varchar,firi.date,103))as issuedDate,firi.issueReturnFrom,firi.receiveFrom,firi.remarks,firi.createBy,di.DepartmentName,fi.FactoryName\r\n" + 
					"from tbFabricsIssueReturnInfo firi\r\n" + 
					"left join TbDepartmentInfo di\r\n" + 
					"on firi.issueReturnFrom = di.DepartmentId\r\n" + 
					"left join TbFactoryInfo fi\r\n" + 
					"on di.FactoryId = fi.FactoryId";		
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				tempIssueReturn = new FabricsIssueReturn(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString());
				tempIssueReturn.setIssueReturnDepartmentName(element[7].toString()+"("+element[8].toString()+")");
				datalist.add(tempIssueReturn);				
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
		return datalist;
	}

	@Override
	public FabricsIssueReturn getFabricsIssueReturnInfo(String issueReturnTransectionId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		FabricsIssueReturn fabricsIssueReturn = null;
		FabricsRoll tempRoll;
		List<FabricsRoll> fabricsRollList = new ArrayList<FabricsRoll>();	
		String departmentId = "1";
		try{	
			tx=session.getTransaction();
			tx.begin();		
			String sql = "select far.autoId,far.transactionId,far.purchaseOrder,far.styleId,sc.styleNo,far.styleItemId,id.itemname,far.colorId as itemColorId ,ic.Colorname as itemColorName,cItemId as fabricsId,fi.ItemName as fabricsName,far.itemColorId as fabricsColorId,fc.Colorname as fabricsColorName,far.rollId,fri.supplierRollId,far.unitId,u.unitname,unitQty,rackName,BinName,far.userId,\r\n" + 
					"dbo.fabricsissuedQty(firi.departmentId,firi.issueReturnFrom,far.rollId) as issuedQty,\r\n" + 
					"dbo.fabricsIssueReturnedQty(firi.departmentId,firi.issueReturnFrom,far.rollId) as returnedQty\r\n" + 
					"from tbFabricsAccessoriesTransaction far\r\n" + 
					"left join tbFabricsIssueReturnInfo firi\r\n" + 
					"on far.transactionId = firi.transactionId\r\n" + 
					"left join TbStyleCreate sc\r\n" + 
					"on far.styleId = sc.StyleId\r\n" + 
					"left join tbItemDescription id\r\n" + 
					"on far.styleItemId = id.itemid\r\n" + 
					"left join tbfabricsRollInfo fri\r\n" + 
					"on far.rollId = fri.rollId \r\n" + 
					"left join tbunits u\r\n" + 
					"on far.unitId = u.Unitid\r\n" + 
					"left join TbFabricsItem fi\r\n" + 
					"on far.dItemId = fi.id\r\n" + 
					"left join tbColors ic\r\n" + 
					"on far.colorId = ic.ColorId\r\n" + 
					"left join tbColors fc\r\n" + 
					"on far.itemColorId = fc.ColorId\r\n" + 
					"where far.transactionId = '"+issueReturnTransectionId+"' and far.transactionType='"+StoreTransaction.FABRICS_ISSUE_RETURN.getType()+"'";	
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				tempRoll = new FabricsRoll(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString(),element[7].toString(), element[8].toString(),element[9].toString(), element[10].toString(), element[11].toString(), element[12].toString(), element[13].toString(), element[14].toString(), element[15].toString(),element[16].toString(),0.0, Double.valueOf(element[17].toString()), element[18].toString(), element[19].toString(),1);
				tempRoll.setIssueQty(Double.valueOf(element[21].toString()));
				tempRoll.setPreviousReturnQty(Double.valueOf(element[22].toString()));
				fabricsRollList.add(tempRoll);				
			}
			sql = "select fii.AutoId,fii.transactionId,(select convert(varchar,fii.date,103))as issuedDate,fii.issueReturnFrom,fii.receiveFrom,fii.remarks,fii.createBy,di.DepartmentName,fi.FactoryName\r\n" + 
					"from tbFabricsIssueReturnInfo fii\r\n" + 
					"left join TbDepartmentInfo di\r\n" + 
					"on fii.issueReturnFrom = di.DepartmentId\r\n" + 
					"left join TbFactoryInfo fi\r\n" + 
					"on di.FactoryId = fi.FactoryId\r\n" + 
					"where fii.transactionId = '"+issueReturnTransectionId+"'\r\n" + 
					"";		
			list = session.createSQLQuery(sql).list();
			if(list.size()>0)
			{	
				Object[] element = (Object[]) list.get(0);

				fabricsIssueReturn = new FabricsIssueReturn(element[0].toString(), element[1].toString(), element[2].toString(),  element[3].toString(),  element[4].toString(),  element[5].toString(), element[6].toString());
				fabricsIssueReturn.setFabricsRollList(fabricsRollList);
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
		return fabricsIssueReturn;
	}

	@Override
	public boolean submitFabricsTransferOut(FabricsTransferOut fabricsTransferOut) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		StoreTransaction transaction = StoreTransaction.FABRICS_TRANSFER_OUT;
		ItemType itemType = ItemType.FABRICS;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select (isnull(max(transactionId),0)+1) as maxId from tbFabricsTransferOutInfo";
			List<?> list = session.createSQLQuery(sql).list();
			String transactionId="0";
			if(list.size()>0) {
				transactionId = list.get(0).toString();
			}

			sql="insert into tbFabricsTransferOutInfo (transactionId"
					+ ",date"
					+ ",transferTo"
					+ ",receiveBy"
					+ ",remarks"
					+ ",departmentId"
					+ ",entryTime"
					+ ",createBy) values("
					+ "'"+transactionId+"'"
					+ ",'"+fabricsTransferOut.getTransferDate()+"'"
					+ ",'"+fabricsTransferOut.getTransferTo()+"'"
					+ ",'"+fabricsTransferOut.getReceiveBy()+"'"
					+ ",'"+fabricsTransferOut.getRemarks()+"'"
					+ ",'"+fabricsTransferOut.getDepartmentId()+"',"
					+ "CURRENT_TIMESTAMP,'"+fabricsTransferOut.getUserId()+"');";
			session.createSQLQuery(sql).executeUpdate();
			int departmentId = 1;
			for (FabricsRoll roll : fabricsTransferOut.getFabricsRollList()) {
				sql="insert into tbFabricsAccessoriesTransaction (purchaseOrder,styleId,styleItemId,colorId,itemColorId,transactionId,transactionType,itemType,rollId,unitId,unitQty,qty,dItemId,cItemId,departmentId,rackName,binName,entryTime,userId) \r\n" + 
						"values('"+roll.getPurchaseOrder()+"','"+roll.getStyleId()+"','"+roll.getItemId()+"','"+roll.getItemColorId()+"','"+roll.getFabricsColorId()+"','"+transactionId+"','"+transaction.getType()+"','"+itemType.getType()+"','"+roll.getRollId()+"','"+roll.getUnitId()+"','"+roll.getUnitQty()+"','"+roll.getUnitQty()+"','0','"+roll.getFabricsId()+"','"+departmentId+"','"+roll.getRackName()+"','"+roll.getBinName()+"',CURRENT_TIMESTAMP,'"+fabricsTransferOut.getUserId()+"');";		
				session.createSQLQuery(sql).executeUpdate();
			}

			tx.commit();

			return true;
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

		return false;
	}

	@Override
	public boolean editFabricsTransferOut(FabricsTransferOut fabricsTransferOut) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		StoreTransaction transaction = StoreTransaction.FABRICS_TRANSFER_OUT;
		ItemType itemType = ItemType.FABRICS;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="update tbfabricsTransferOutInfo set "
					+ "date = '"+fabricsTransferOut.getTransferDate()+"'"
					+ ",transferTo = '"+fabricsTransferOut.getTransferTo()+"'"
					+ ",receiveBy = '"+fabricsTransferOut.getReceiveBy()+"'"
					+ ",remarks = '"+fabricsTransferOut.getRemarks()+"'"
					+ ",entryTime = CURRENT_TIMESTAMP"
					+ ",createBy = '"+fabricsTransferOut.getUserId()+"' where transactionId='"+fabricsTransferOut.getTransactionId()+"';";

			session.createSQLQuery(sql).executeUpdate();

			int departmentId = 1;
			if(fabricsTransferOut.getFabricsRollList() != null) {
				for (FabricsRoll roll : fabricsTransferOut.getFabricsRollList()) {
					sql="insert into tbFabricsAccessoriesTransaction (purchaseOrder,styleId,styleItemId,colorId,itemColorId,transactionId,transactionType,itemType,rollId,unitId,unitQty,qty,dItemId,cItemId,departmentId,rackName,binName,entryTime,userId) \r\n" + 
							"values('"+roll.getPurchaseOrder()+"','"+roll.getStyleId()+"','"+roll.getItemId()+"','"+roll.getItemColorId()+"','"+roll.getFabricsColorId()+"','"+fabricsTransferOut.getTransactionId()+"','"+transaction.getType()+"','"+itemType.getType()+"','"+roll.getRollId()+"','"+roll.getUnitId()+"','"+roll.getUnitQty()+"','"+roll.getUnitQty()+"','0','"+roll.getFabricsId()+"','"+departmentId+"','"+roll.getRackName()+"','"+roll.getBinName()+"',CURRENT_TIMESTAMP,'"+fabricsTransferOut.getUserId()+"');";		
					session.createSQLQuery(sql).executeUpdate();
				}
			}
			

			tx.commit();

			return true;
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

		return false;
	}

	@Override
	public String editTransferOutdRollInTransaction(FabricsRoll fabricsRoll) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		try{
			tx=session.getTransaction();
			tx.begin();
			String sql = "select far.autoId,dbo.fabricsBalanceQtyExceptAutoId(far.purchaseOrder,far.styleId,far.styleItemId,far.colorId,far.cItemId,far.itemColorId,far.rollId,far.departmentId,far.autoId) as balanceQty \r\n" + 
					"from tbFabricsAccessoriesTransaction far\r\n" + 
					"where far.autoId = '"+fabricsRoll.getAutoId()+"'";		
			List<?> list = session.createSQLQuery(sql).list();
			if(list.size()>0) {
				Object[] element = (Object[]) list.get(0);
				if(Double.valueOf(element[1].toString())>=fabricsRoll.getUnitQty()) {
					sql = "update tbFabricsAccessoriesTransaction set unitQty = '"+fabricsRoll.getUnitQty()+"',qty = '"+fabricsRoll.getUnitQty()+"' where autoId = '"+fabricsRoll.getAutoId()+"'";
					if(session.createSQLQuery(sql).executeUpdate()==1) {
						tx.commit();
						return "Successful";
					}
				}else {
					tx.commit();
					return "Return Qty Exist";
				}
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

		return "Something Wrong";
	}

	@Override
	public String deleteTransferOutdRollFromTransaction(FabricsRoll fabricsRoll) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql = "delete from tbFabricsAccessoriesTransaction where autoId = '"+fabricsRoll.getAutoId()+"'";
			if(session.createSQLQuery(sql).executeUpdate()==1) {
				tx.commit();
				return "Successful";
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

		return "Something Wrong";
	}

	@Override
	public List<FabricsTransferOut> getFabricsTransferOutList() {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		FabricsTransferOut tempIssue;
		List<FabricsTransferOut> datalist=new ArrayList<FabricsTransferOut>();	
		try{	
			tx=session.getTransaction();
			tx.begin();		
			String sql = "select fii.AutoId,fii.transactionId,(select convert(varchar,fii.date,103))as issuedDate,fii.transferTo,fii.receiveBy,fii.remarks,fii.createBy,di.DepartmentName,fi.FactoryName\r\n" + 
					"from tbFabricsTransferOutInfo fii\r\n" + 
					"left join TbDepartmentInfo di\r\n" + 
					"on fii.transferTo = di.DepartmentId\r\n" + 
					"left join TbFactoryInfo fi\r\n" + 
					"on di.FactoryId = fi.FactoryId";		
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				tempIssue = new FabricsTransferOut(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString());
				tempIssue.setTransferDepartmentName(element[7].toString()+"("+element[8].toString()+")");
				datalist.add(tempIssue);				
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
		return datalist;
	}

	@Override
	public FabricsTransferOut getFabricsTransferOutInfo(String transferOutTransectionId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		FabricsTransferOut fabricsTransfer = null;
		FabricsRoll tempRoll;
		List<FabricsRoll> fabricsRollList = new ArrayList<FabricsRoll>();	
		String departmentId = "1";
		try{	
			tx=session.getTransaction();
			tx.begin();		
			String sql = "select far.autoId,far.transactionId,far.purchaseOrder,far.styleId,sc.styleNo,far.styleItemId,id.itemname,far.colorId as itemColorId ,ic.Colorname as itemColorName,cItemId as fabricsId,fi.ItemName as fabricsName,far.itemColorId as fabricsColorId,fc.Colorname as fabricsColorName,far.rollId,fri.supplierRollId,far.unitId,u.unitname,unitQty,rackName,BinName,far.userId\r\n" + 
					",dbo.fabricsBalanceQty(far.purchaseOrder,far.styleId,far.styleItemId,far.colorId,far.cItemId,far.itemColorId,far.rollId,far.departmentId) as balanceQty \r\n" +
					",(select isnull(sum(qty),0) from tbFabricsAccessoriesTransaction t where far.purchaseOrder=t.purchaseOrder and far.styleId = t.styleId and far.styleItemId= t.styleItemId and far.colorId = t.colorId and far.cItemId = t.dItemId and far.itemColorId = t.itemColorId and t.transactionType = '"+StoreTransaction.FABRICS_RECEIVE.getType()+"' and t.departmentId = '"+departmentId+"') as previousReceiveQty,\r\n" + 
					"(select isnull(sum(qty),0) from tbFabricsAccessoriesTransaction t where far.purchaseOrder=t.purchaseOrder and far.styleId = t.styleId and far.styleItemId= t.styleItemId and far.colorId = t.colorId and far.cItemId = t.cItemId and far.itemColorId = t.itemColorId and t.transactionType = '"+StoreTransaction.FABRICS_RETURN.getType()+"' and t.departmentId = '"+departmentId+"') as returnQty,\r\n" + 
					"(select isnull(sum(qty),0) from tbFabricsAccessoriesTransaction t where far.purchaseOrder=t.purchaseOrder and far.styleId = t.styleId and far.styleItemId= t.styleItemId and far.colorId = t.colorId and far.cItemId = t.cItemId and far.itemColorId = t.itemColorId and t.transactionType = '"+StoreTransaction.FABRICS_ISSUE.getType()+"' and t.departmentId = '"+departmentId+"') as issueQty\r\n" + 
					"from tbFabricsAccessoriesTransaction far\r\n" + 
					"left join TbStyleCreate sc\r\n" + 
					"on far.styleId = sc.StyleId\r\n" + 
					"left join tbItemDescription id\r\n" + 
					"on far.styleItemId = id.itemid\r\n"
					+ "left join tbfabricsRollInfo fri\r\n" + 
					"on far.rollId = fri.rollId \r\n" + 
					"left join tbunits u\r\n" + 
					"on far.unitId = u.Unitid\r\n" + 
					"left join TbFabricsItem fi\r\n" + 
					"on far.cItemId = fi.id\r\n" + 
					"left join tbColors ic\r\n" + 
					"on far.colorId = ic.ColorId\r\n" + 
					"left join tbColors fc\r\n" + 
					"on far.itemColorId = fc.ColorId\r\n" + 
					"where transactionId = '"+transferOutTransectionId+"' and transactionType='"+StoreTransaction.FABRICS_TRANSFER_OUT.getType()+"'";	
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				tempRoll = new FabricsRoll(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString(),element[7].toString(), element[8].toString(),element[9].toString(), element[10].toString(), element[11].toString(), element[12].toString(), element[13].toString(), element[14].toString(), element[15].toString(),element[16].toString(),0.0, Double.valueOf(element[17].toString()), element[18].toString(), element[19].toString(),1);
				tempRoll.setBalanceQty(Double.valueOf(element[21].toString()));
				tempRoll.setPreviousReceiveQty(Double.valueOf(element[22].toString()));
				tempRoll.setReturnQty(Double.valueOf(element[23].toString()));
				tempRoll.setIssueQty(Double.valueOf(element[24].toString()));
				
				fabricsRollList.add(tempRoll);				
			}
			sql = "select fii.AutoId,fii.transactionId,(select convert(varchar,fii.date,103))as issuedDate,fii.transferTo,fii.receiveBy,fii.remarks,fii.createBy,di.DepartmentName,fi.FactoryName\r\n" + 
					"from tbFabricsTransferOutInfo fii\r\n" + 
					"left join TbDepartmentInfo di\r\n" + 
					"on fii.transferTo = di.DepartmentId\r\n" + 
					"left join TbFactoryInfo fi\r\n" + 
					"on di.FactoryId = fi.FactoryId\r\n" + 
					"where fii.transactionId = '"+transferOutTransectionId+"'\r\n" + 
					"";		
			list = session.createSQLQuery(sql).list();
			if(list.size()>0)
			{	
				Object[] element = (Object[]) list.get(0);

				fabricsTransfer = new FabricsTransferOut(element[0].toString(), element[1].toString(), element[2].toString(),  element[3].toString(),  element[4].toString(),  element[5].toString(), element[6].toString());
				fabricsTransfer.setFabricsRollList(fabricsRollList);
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
		return fabricsTransfer;
	}
	
	
	@Override
	public boolean submitFabricsTransferIn(FabricsTransferIn fabricsTransferIn) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		StoreTransaction transaction = StoreTransaction.FABRICS_TRANSFER_IN;
		ItemType itemType = ItemType.FABRICS;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select (isnull(max(transactionId),0)+1) as maxId from tbFabricsTransferInInfo";
			List<?> list = session.createSQLQuery(sql).list();
			String transactionId="0";
			if(list.size()>0) {
				transactionId = list.get(0).toString();
			}

			sql="insert into tbFabricsTransferInInfo (transactionId"
					+ ",date"
					+ ",transferFrom"
					+ ",receiveFrom"
					+ ",remarks"
					+ ",departmentId"
					+ ",entryTime"
					+ ",createBy) values("
					+ "'"+transactionId+"'"
					+ ",'"+fabricsTransferIn.getTransferDate()+"'"
					+ ",'"+fabricsTransferIn.getTransferFrom()+"'"
					+ ",'"+fabricsTransferIn.getReceiveFrom()+"'"
					+ ",'"+fabricsTransferIn.getRemarks()+"'"
					+ ",'"+fabricsTransferIn.getDepartmentId()+"',"
					+ "CURRENT_TIMESTAMP,'"+fabricsTransferIn.getUserId()+"');";
			session.createSQLQuery(sql).executeUpdate();
			int departmentId = 1;
			for (FabricsRoll roll : fabricsTransferIn.getFabricsRollList()) {
				sql="insert into tbFabricsAccessoriesTransaction (purchaseOrder,styleId,styleItemId,colorId,itemColorId,transactionId,transactionType,itemType,rollId,unitId,unitQty,qty,dItemId,cItemId,departmentId,rackName,binName,entryTime,userId) \r\n" + 
						"values('"+roll.getPurchaseOrder()+"','"+roll.getStyleId()+"','"+roll.getItemId()+"','"+roll.getItemColorId()+"','"+roll.getFabricsColorId()+"','"+transactionId+"','"+transaction.getType()+"','"+itemType.getType()+"','"+roll.getRollId()+"','"+roll.getUnitId()+"','"+roll.getUnitQty()+"','"+roll.getUnitQty()+"','"+roll.getFabricsId()+"','0','"+departmentId+"','"+roll.getRackName()+"','"+roll.getBinName()+"',CURRENT_TIMESTAMP,'"+fabricsTransferIn.getUserId()+"');";		
				session.createSQLQuery(sql).executeUpdate();
			}

			tx.commit();

			return true;
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

		return false;
	}

	@Override
	public boolean editFabricsTransferIn(FabricsTransferIn fabricsTransferIn) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		StoreTransaction transaction = StoreTransaction.FABRICS_TRANSFER_IN;
		ItemType itemType = ItemType.FABRICS;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="update tbfabricsTransferInInfo set "
					+ "date = '"+fabricsTransferIn.getTransferDate()+"'"
					+ ",transferFrom = '"+fabricsTransferIn.getTransferFrom()+"'"
					+ ",receiveFrom = '"+fabricsTransferIn.getReceiveFrom()+"'"
					+ ",remarks = '"+fabricsTransferIn.getRemarks()+"'"
					+ ",entryTime = CURRENT_TIMESTAMP"
					+ ",createBy = '"+fabricsTransferIn.getUserId()+"' where transactionId='"+fabricsTransferIn.getTransactionId()+"';";

			session.createSQLQuery(sql).executeUpdate();

			int departmentId = 1;
			if(fabricsTransferIn.getFabricsRollList() != null) {
				for (FabricsRoll roll : fabricsTransferIn.getFabricsRollList()) {
					sql="insert into tbFabricsAccessoriesTransaction (purchaseOrder,styleId,styleItemId,colorId,itemColorId,transactionId,transactionType,itemType,rollId,unitId,unitQty,qty,dItemId,cItemId,departmentId,rackName,binName,entryTime,userId) \r\n" + 
							"values('"+roll.getPurchaseOrder()+"','"+roll.getStyleId()+"','"+roll.getItemId()+"','"+roll.getItemColorId()+"','"+roll.getFabricsColorId()+"','"+fabricsTransferIn.getTransactionId()+"','"+transaction.getType()+"','"+itemType.getType()+"','"+roll.getRollId()+"','"+roll.getUnitId()+"','"+roll.getUnitQty()+"','"+roll.getUnitQty()+"','"+roll.getFabricsId()+"','0','"+departmentId+"','"+roll.getRackName()+"','"+roll.getBinName()+"',CURRENT_TIMESTAMP,'"+fabricsTransferIn.getUserId()+"');";		
					session.createSQLQuery(sql).executeUpdate();
				}
			}
			

			tx.commit();

			return true;
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

		return false;
	}

	@Override
	public String editTransferIndRollInTransaction(FabricsRoll fabricsRoll) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		try{
			tx=session.getTransaction();
			tx.begin();
			String sql = "select far.autoId,dbo.fabricsBalanceQtyExceptAutoId(far.purchaseOrder,far.styleId,far.styleItemId,far.colorId,far.cItemId,far.itemColorId,far.rollId,far.departmentId,far.autoId) as balanceQty \r\n" + 
					"from tbFabricsAccessoriesTransaction far\r\n" + 
					"where far.autoId = '"+fabricsRoll.getAutoId()+"'";		
			List<?> list = session.createSQLQuery(sql).list();
			if(list.size()>0) {
				Object[] element = (Object[]) list.get(0);
				if(Double.valueOf(element[1].toString())>=fabricsRoll.getUnitQty()) {
					sql = "update tbFabricsAccessoriesTransaction set unitQty = '"+fabricsRoll.getUnitQty()+"',qty = '"+fabricsRoll.getUnitQty()+"' where autoId = '"+fabricsRoll.getAutoId()+"'";
					if(session.createSQLQuery(sql).executeUpdate()==1) {
						tx.commit();
						return "Successful";
					}
				}else {
					tx.commit();
					return "Return Qty Exist";
				}
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

		return "Something Wrong";
	}

	@Override
	public String deleteTransferIndRollFromTransaction(FabricsRoll fabricsRoll) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql = "delete from tbFabricsAccessoriesTransaction where autoId = '"+fabricsRoll.getAutoId()+"'";
			if(session.createSQLQuery(sql).executeUpdate()==1) {
				tx.commit();
				return "Successful";
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

		return "Something Wrong";
	}

	@Override
	public List<FabricsTransferIn> getFabricsTransferInList() {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		FabricsTransferIn tempIssue;
		List<FabricsTransferIn> datalist=new ArrayList<FabricsTransferIn>();	
		try{	
			tx=session.getTransaction();
			tx.begin();		
			String sql = "select fii.AutoId,fii.transactionId,(select convert(varchar,fii.date,103))as issuedDate,fii.transferFrom,fii.receiveFrom,fii.remarks,fii.createBy,di.DepartmentName,fi.FactoryName\r\n" + 
					"from tbFabricsTransferInInfo fii\r\n" + 
					"left join TbDepartmentInfo di\r\n" + 
					"on fii.transferFrom = di.DepartmentId\r\n" + 
					"left join TbFactoryInfo fi\r\n" + 
					"on di.FactoryId = fi.FactoryId";		
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				tempIssue = new FabricsTransferIn(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString());
				tempIssue.setTransferDepartmentName(element[7].toString()+"("+element[8].toString()+")");
				datalist.add(tempIssue);				
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
		return datalist;
	}

	@Override
	public FabricsTransferIn getFabricsTransferInInfo(String transferOutTransectionId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		FabricsTransferIn fabricsTransfer = null;
		FabricsRoll tempRoll;
		List<FabricsRoll> fabricsRollList = new ArrayList<FabricsRoll>();	
		String departmentId = "1";
		try{	
			tx=session.getTransaction();
			tx.begin();		
			String sql = "select far.autoId,far.transactionId,far.purchaseOrder,far.styleId,sc.styleNo,far.styleItemId,id.itemname,far.colorId as itemColorId ,ic.Colorname as itemColorName,dItemId as fabricsId,fi.ItemName as fabricsName,far.itemColorId as fabricsColorId,fc.Colorname as fabricsColorName,far.rollId,fri.supplierRollId,far.unitId,u.unitname,unitQty,rackName,BinName,far.userId\r\n" + 
					",dbo.fabricsBalanceQty(far.purchaseOrder,far.styleId,far.styleItemId,far.colorId,far.cItemId,far.itemColorId,far.rollId,far.departmentId) as balanceQty \r\n" +
					",(select isnull(sum(qty),0) from tbFabricsAccessoriesTransaction t where far.purchaseOrder=t.purchaseOrder and far.styleId = t.styleId and far.styleItemId= t.styleItemId and far.colorId = t.colorId and far.cItemId = t.dItemId and far.itemColorId = t.itemColorId and t.transactionType = '"+StoreTransaction.FABRICS_RECEIVE.getType()+"' and t.departmentId = '"+departmentId+"') as previousReceiveQty,\r\n" + 
					"(select isnull(sum(qty),0) from tbFabricsAccessoriesTransaction t where far.purchaseOrder=t.purchaseOrder and far.styleId = t.styleId and far.styleItemId= t.styleItemId and far.colorId = t.colorId and far.cItemId = t.cItemId and far.itemColorId = t.itemColorId and t.transactionType = '"+StoreTransaction.FABRICS_RETURN.getType()+"' and t.departmentId = '"+departmentId+"') as returnQty,\r\n" + 
					"(select isnull(sum(qty),0) from tbFabricsAccessoriesTransaction t where far.purchaseOrder=t.purchaseOrder and far.styleId = t.styleId and far.styleItemId= t.styleItemId and far.colorId = t.colorId and far.cItemId = t.cItemId and far.itemColorId = t.itemColorId and t.transactionType = '"+StoreTransaction.FABRICS_ISSUE.getType()+"' and t.departmentId = '"+departmentId+"') as issueQty\r\n" + 
					"from tbFabricsAccessoriesTransaction far\r\n" + 
					"left join TbStyleCreate sc\r\n" + 
					"on far.styleId = sc.StyleId\r\n" + 
					"left join tbItemDescription id\r\n" + 
					"on far.styleItemId = id.itemid\r\n"
					+ "left join tbfabricsRollInfo fri\r\n" + 
					"on far.rollId = fri.rollId \r\n" + 
					"left join tbunits u\r\n" + 
					"on far.unitId = u.Unitid\r\n" + 
					"left join TbFabricsItem fi\r\n" + 
					"on far.dItemId = fi.id\r\n" + 
					"left join tbColors ic\r\n" + 
					"on far.colorId = ic.ColorId\r\n" + 
					"left join tbColors fc\r\n" + 
					"on far.itemColorId = fc.ColorId\r\n" + 
					"where transactionId = '"+transferOutTransectionId+"' and transactionType='"+StoreTransaction.FABRICS_TRANSFER_IN.getType()+"'";	
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				tempRoll = new FabricsRoll(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString(),element[7].toString(), element[8].toString(),element[9].toString(), element[10].toString(), element[11].toString(), element[12].toString(), element[13].toString(), element[14].toString(), element[15].toString(),element[16].toString(),0.0, Double.valueOf(element[17].toString()), element[18].toString(), element[19].toString(),1);
				tempRoll.setBalanceQty(Double.valueOf(element[21].toString()));
				tempRoll.setPreviousReceiveQty(Double.valueOf(element[22].toString()));
				tempRoll.setReturnQty(Double.valueOf(element[23].toString()));
				tempRoll.setIssueQty(Double.valueOf(element[24].toString()));
				
				fabricsRollList.add(tempRoll);				
			}
			sql = "select fii.AutoId,fii.transactionId,(select convert(varchar,fii.date,103))as issuedDate,fii.transferFrom,fii.receiveFrom,fii.remarks,fii.createBy,di.DepartmentName,fi.FactoryName\r\n" + 
					"from tbFabricsTransferInInfo fii\r\n" + 
					"left join TbDepartmentInfo di\r\n" + 
					"on fii.transferFrom = di.DepartmentId\r\n" + 
					"left join TbFactoryInfo fi\r\n" + 
					"on di.FactoryId = fi.FactoryId\r\n" + 
					"where fii.transactionId = '"+transferOutTransectionId+"'\r\n" + 
					"";		
			list = session.createSQLQuery(sql).list();
			if(list.size()>0)
			{	
				Object[] element = (Object[]) list.get(0);

				fabricsTransfer = new FabricsTransferIn(element[0].toString(), element[1].toString(), element[2].toString(),  element[3].toString(),  element[4].toString(),  element[5].toString(), element[6].toString());
				fabricsTransfer.setFabricsRollList(fabricsRollList);
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
		return fabricsTransfer;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public List<AccessoriesIndent> getAccessoriesPurchaseOrdeIndentrList() {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;



		List<AccessoriesIndent> datalist=new ArrayList<AccessoriesIndent>();	
		try{	
			tx=session.getTransaction();
			tx.begin();		
			String sql="select ai.PurchaseOrder,ai.styleid,sc.StyleNo,ai.Itemid,id.itemname,ai.ColorId,c.Colorname,ai.accessoriesItemId,aItem.itemname as accessoriesName,ai.IndentColorId as accessoriesColorId,isnull(ic.Colorname,'') accessoriesColor,count(ai.PurchaseOrder)\r\n" + 
					" from tbAccessoriesIndent ai \r\n" + 
					" left join TbStyleCreate sc\r\n" + 
					" on ai.styleid = sc.StyleId \r\n" + 
					" left join tbItemDescription id\r\n" + 
					"on ai.itemid = id.itemid\r\n" + 
					"left join tbColors c\r\n" + 
					"on ai.ColorId = c.ColorId\r\n" + 
					"left join tbColors ic\r\n" + 
					"on ai.IndentColorId = ic.ColorId\r\n" + 
					"left join TbAccessoriesItem aItem\r\n" + 
					"on aItem.itemid = ai.accessoriesItemId\r\n" + 
					" where ai.pono is not null\r\n" + 
					" group by ai.PurchaseOrder,ai.styleid,sc.StyleNo,ai.Itemid,id.itemname,ai.ColorId,c.Colorname,ai.accessoriesItemId,aItem.itemname,ai.IndentColorId,ic.Colorname";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				datalist.add(new AccessoriesIndent(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString(), element[7].toString(), element[8].toString(), element[9].toString(), element[10].toString()));		
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
		return datalist;
	}



	@Override
	public AccessoriesIndent getAccessoriesIndentInfo(String autoId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		AccessoriesIndent indent= null;
		try{	
			tx=session.getTransaction();
			tx.begin();	
			String sql="select rf.id,PurchaseOrder,rf.styleId,sc.StyleNo,rf.itemid,id.itemname,rf.itemcolor,c.colorName,rf.accessoriesid,fi.ItemName as accessoriesName,rf.accessoriescolor,fc.Colorname,rf.unitId,u.unitname,rf.RequireUnitQty,\r\n" + 
					"(select isnull(sum(qty),0) from tbAccessoriesAccessoriesTransaction fat where fat.purchaseOrder = rf.PurchaseOrder and fat.styleId = rf.styleId and fat.styleItemId = rf.itemid and fat.colorId = rf.itemcolor and fat.dItemId = rf.accessoriesId and fat.itemColorId = rf.accessoriescolor and fat.transactionType = '1') as previousReceiveQty\r\n" + 
					"from tbAccessoriesIndent rf\r\n" + 
					"left join TbStyleCreate  sc\r\n" + 
					"on rf.styleId = sc.StyleId\r\n" + 
					"left join tbItemDescription id\r\n" + 
					"on rf.itemid = id.itemid\r\n" + 
					"left join tbColors c\r\n" + 
					"on rf.itemcolor = c.ColorId\r\n" + 
					"left join TbAccessoriesItem fi\r\n" + 
					"on rf.accessoriesid = fi.id\r\n" + 
					"left join tbColors fc\r\n" + 
					"on rf.accessoriescolor = fc.ColorId\r\n" + 
					"left join tbunits u\r\n" + 
					"on rf.unitId = u.Unitid\r\n" + 
					" where rf.id = '"+autoId+"'";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				indent = new AccessoriesIndent();
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
		return indent;
	}



	@Override
	public boolean submitAccessoriesReceive(AccessoriesReceive accessoriesReceive) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select (isnull(max(transactionid),0)+1) as maxId from tbaccessoriesReceiveInfo";
			List<?> list = session.createSQLQuery(sql).list();
			String transactionId="0";
			if(list.size()>0) {
				transactionId = list.get(0).toString();
			}

			sql="insert into tbaccessoriesReceiveInfo (transactionid,"
					+ "grnNo,"
					+ "grnDate,"
					+ "location,"		
					+ "supplierId,"
					+ "challanNo,"
					+ "challanDate,"
					+ "remarks,"
					+ "departmentId,"
					+ "preperedBy,"
					+ "entryTime,"
					+ "createBy) \r\n" + 
					"values('"+transactionId+"',"
					+ "'"+accessoriesReceive.getGrnNo()+"',"
					+ "'"+accessoriesReceive.getGrnDate()+"',"
					+ "'"+accessoriesReceive.getLocation()+"',"
					+ "'"+accessoriesReceive.getSupplierId()+"',"
					+ "'"+accessoriesReceive.getChallanNo()+"',"
					+ "'"+accessoriesReceive.getChallanDate()+"',"
					+ "'"+accessoriesReceive.getRemarks()+"',"
					+ "'"+accessoriesReceive.getDepartmentId()+"',"
					+ "'"+accessoriesReceive.getPreparedBy()+"',"
					+ "CURRENT_TIMESTAMP,"
					+ "'"+accessoriesReceive.getUserId()+"') ;";
			session.createSQLQuery(sql).executeUpdate();
			int departmentId = 1;
			sql = "select isnull(max(autoId),0) as id from  tbAccessoriesRollInfo";		
			int rollId = 0;
			list = session.createSQLQuery(sql).list();
			if(list.size()>0)
			{	
				rollId = Integer.valueOf(list.get(0).toString());				
			}	
			for (AccessoriesSize roll : accessoriesReceive.getAccessoriesSizeList()) {
				rollId++;
				sql="insert into tbAccessoriesRollInfo (rollId,supplierRollId,entryTime,createBy) values('"+rollId+"','"+roll.getSupplierSizeId()+"',CURRENT_TIMESTAMP,'"+accessoriesReceive.getUserId()+"');";		
				session.createSQLQuery(sql).executeUpdate();
				
				sql="insert into tbAccessoriesAccessoriesTransaction (purchaseOrder,styleId,styleItemId,colorId,itemColorId,transactionId,transactionType,itemType,rollId,unitId,unitQty,qty,dItemId,cItemId,departmentId,rackName,binName,entryTime,userId) \r\n" + 
						"values('"+roll.getPurchaseOrder()+"','"+roll.getStyleId()+"','"+roll.getItemId()+"','"+roll.getItemColorId()+"','"+roll.getAccessoriesColorId()+"','"+transactionId+"','"+StoreTransaction.FABRICS_RECEIVE.getType()+"','"+ItemType.FABRICS.getType()+"','"+rollId+"','"+roll.getUnitId()+"','"+roll.getUnitQty()+"','"+roll.getUnitQty()+"','"+roll.getAccessoriesId()+"','0','"+departmentId+"','"+roll.getRackName()+"','"+roll.getBinName()+"',CURRENT_TIMESTAMP,'"+accessoriesReceive.getUserId()+"');";		
				session.createSQLQuery(sql).executeUpdate();
			}

			tx.commit();

			return true;
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

		return false;
	}

	@Override
	public boolean editAccessoriesReceive(AccessoriesReceive accessoriesReceive) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="update tbaccessoriesReceiveInfo set "
					+ "grnNo='"+accessoriesReceive.getGrnNo()+"',"
					+ "grnDate='"+accessoriesReceive.getGrnDate()+"',"
					+ "location='"+accessoriesReceive.getLocation()+"',"
					+ "supplierId='"+accessoriesReceive.getSupplierId()+"',"
					+ "challanNo='"+accessoriesReceive.getChallanNo()+"',"
					+ "challanDate='"+accessoriesReceive.getChallanDate()+"',"
					+ "remarks='"+accessoriesReceive.getRemarks()+"',"
					+ "preperedBy='"+accessoriesReceive.getPreparedBy()+"' where transactionId= '"+accessoriesReceive.getTransactionId()+"'";


			session.createSQLQuery(sql).executeUpdate();

			sql = "select isnull(max(autoId),0) as id from  tbAccessoriesRollInfo";		
			int rollId = 0;
			List<?> list = session.createSQLQuery(sql).list();
			if(list.size()>0)
			{	
				rollId = Integer.valueOf(list.get(0).toString());				
			}	
			int departmentId = 1;
			if(accessoriesReceive.getAccessoriesSizeList() != null) {
				for (AccessoriesSize roll : accessoriesReceive.getAccessoriesSizeList()) {
					rollId++;
					sql="insert into tbAccessoriesRollInfo (rollId,supplierRollId,entryTime,createBy) values('"+rollId+"','"+roll.getSupplierSizeId()+"',CURRENT_TIMESTAMP,'"+accessoriesReceive.getUserId()+"');";		
					session.createSQLQuery(sql).executeUpdate();
					
					sql="insert into tbAccessoriesAccessoriesTransaction (purchaseOrder,styleId,styleItemId,colorId,itemColorId,transactionId,transactionType,itemType,rollId,unitId,unitQty,qty,dItemId,cItemId,departmentId,rackName,binName,entryTime,userId) \r\n" + 
							"values('"+roll.getPurchaseOrder()+"','"+roll.getStyleId()+"','"+roll.getItemId()+"','"+roll.getItemColorId()+"','"+roll.getAccessoriesColorId()+"','"+accessoriesReceive.getTransactionId()+"','"+StoreTransaction.FABRICS_RECEIVE.getType()+"','"+ItemType.FABRICS.getType()+"','"+roll.getSizeId()+"','"+roll.getUnitId()+"','"+roll.getUnitQty()+"','"+roll.getUnitQty()+"','"+roll.getAccessoriesId()+"','0','"+departmentId+"','"+roll.getRackName()+"','"+roll.getBinName()+"',CURRENT_TIMESTAMP,'"+accessoriesReceive.getUserId()+"');";		
					session.createSQLQuery(sql).executeUpdate();
				}

			}
			
			tx.commit();

			return true;
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

		return false;
	}

	@Override
	public String editReceiveSizeInTransaction(AccessoriesSize accessoriesRoll) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		try{
			tx=session.getTransaction();
			tx.begin();
			String sql = "select far.autoId,far.purchaseOrder,far.qty from tbAccessoriesAccessoriesTransaction far\r\n" + 
					"join tbAccessoriesAccessoriesTransaction far2\r\n" + 
					"on far.dItemId= far2.cItemId and far2.rollId = far.rollId and far2.itemColorId = far.itemColorId and far2.colorId = far.colorId and far2.styleItemId= far.styleItemId \r\n" + 
					"and far2.styleId = far.styleId and far2.purchaseOrder = far.purchaseOrder and (far.transactionType ='"+StoreTransaction.FABRICS_RETURN.getType()+"' or far.transactionType ='"+StoreTransaction.FABRICS_ISSUE.getType()+"')\r\n" + 
					"where far.autoId = '"+accessoriesRoll.getAutoId()+"'";		
			List<?> list = session.createSQLQuery(sql).list();
			if(list.size()==0) {
				sql = "update tbAccessoriesRollInfo set supplierRollId = '"+accessoriesRoll.getSupplierSizeId()+"' where rollId = '"+accessoriesRoll.getSizeId()+"'";
				session.createSQLQuery(sql).executeUpdate();
				
				sql = "update tbAccessoriesAccessoriesTransaction set unitQty = '"+accessoriesRoll.getUnitQty()+"',qty = '"+accessoriesRoll.getUnitQty()+"' where autoId = '"+accessoriesRoll.getAutoId()+"'";
				if(session.createSQLQuery(sql).executeUpdate()==1) {
					tx.commit();
					return "Successful";
				}
			}			
			tx.commit();

			return "Used";
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

		return "Something Wrong";
	}



	@Override
	public String deleteReceiveSizeFromTransaction(AccessoriesSize accessoriesSize) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		try{
			tx=session.getTransaction();
			tx.begin();
			String sql = "select far.autoId,far.purchaseOrder,far.qty from tbAccessoriesAccessoriesTransaction far\r\n" + 
					"join tbAccessoriesAccessoriesTransaction far2\r\n" + 
					"on far.dItemId= far2.cItemId and far2.rollId = far.rollId and far2.itemColorId = far.itemColorId and far2.colorId = far.colorId and far2.styleItemId= far.styleItemId \r\n" + 
					"and far2.styleId = far.styleId and far2.purchaseOrder = far.purchaseOrder and (far.transactionType ='"+StoreTransaction.FABRICS_RETURN.getType()+"' or far.transactionType ='"+StoreTransaction.FABRICS_ISSUE.getType()+"')\r\n" + 
					"where far.autoId = '"+accessoriesSize.getAutoId()+"'";		
			List<?> list = session.createSQLQuery(sql).list();
			if(list.size()==0) {
				sql = "delete from tbAccessoriesAccessoriesTransaction where autoId = '"+accessoriesSize.getAutoId()+"'";
				if(session.createSQLQuery(sql).executeUpdate()==1) {
					sql = "delete from tbAccessoriesSizeInfo where rollId = '"+accessoriesSize.getSizeId()+"'";
					session.createSQLQuery(sql).executeUpdate();
					tx.commit();
					return "Successful";
				}
			}			
			tx.commit();

			return "Used";
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

		return "Something Wrong";
	}



	@Override
	public List<AccessoriesReceive> getAccessoriesReceiveList() {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<AccessoriesReceive> datalist=new ArrayList<AccessoriesReceive>();	
		try{	
			tx=session.getTransaction();
			tx.begin();		
			String sql = "select autoId,transactionId,grnNo,(select convert(varchar,grnDate,103))as grnDate,location,fri.supplierId,fri.challanNo,fri.challanDate,fri.remarks,fri.preperedBy,fri.createBy \r\n" + 
					"from tbAccessoriesReceiveInfo fri";		
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				datalist.add(new AccessoriesReceive(element[0].toString(), element[1].toString(),element[2].toString(), element[3].toString(), element[4].toString(), "", element[5].toString(), element[6].toString(), element[7].toString(), element[8].toString(), element[9].toString(), element[10].toString()));				
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
		return datalist;
	}

	@Override
	public AccessoriesReceive getAccessoriesReceiveInfo(String transactionId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		AccessoriesReceive accessoriesReceive = null;
		AccessoriesSize tempSize;
		List<AccessoriesSize> accessoriesSizeList = new ArrayList<AccessoriesSize>();	
		try{	
			tx=session.getTransaction();
			tx.begin();		
			String sql = "select far.autoId,far.transactionId,far.purchaseOrder,far.styleId,sc.styleNo,far.styleItemId,id.itemname,far.colorId as itemColorId ,ic.Colorname as itemColorName,dItemId as accessoriesId,fi.ItemName as accessoriesName,far.itemColorId as accessoriesColorId,fc.Colorname as accessoriesColorName,far.rollId,fri.supplierSizeId,far.unitId,u.unitname,unitQty,rackName,BinName,far.userId,\r\n" + 
					"findent.RequireUnitQty as orderQty,(select isnull(sum(qty),0) from tbAccessoriesAccessoriesTransaction t where far.purchaseOrder=t.purchaseOrder and far.styleId = t.styleId and far.styleItemId= t.styleItemId and far.colorId = t.colorId and far.dItemId = t.dItemId and far.itemColorId = t.itemColorId and t.transactionType = '1' and t.departmentId = '1' and far.transactionId != t.transactionId) as previousReceiveQty,\r\n" + 
					"(select isnull(sum(qty),0) from tbAccessoriesAccessoriesTransaction t where far.purchaseOrder=t.purchaseOrder and far.styleId = t.styleId and far.styleItemId= t.styleItemId and far.colorId = t.colorId and far.dItemId = t.cItemId and far.itemColorId = t.itemColorId and far.rollId = t.rollId and t.transactionType = '3' and t.departmentId = '1') as returnQty,\r\n" + 
					"(select isnull(sum(qty),0) from tbAccessoriesAccessoriesTransaction t where far.purchaseOrder=t.purchaseOrder and far.styleId = t.styleId and far.styleItemId= t.styleItemId and far.colorId = t.colorId and far.dItemId = t.cItemId and far.itemColorId = t.itemColorId and far.rollId = t.rollId and t.transactionType = '4' and t.departmentId = '1') as issueQty,\r\n"
					+ "(select isnull(sum(qty),0) from tbAccessoriesAccessoriesTransaction qc where far.purchaseOrder=qc.purchaseOrder and far.styleId =qc.styleId and far.styleItemId= qc.styleItemId and far.colorId = qc.colorId and far.dItemId = qc.dItemId and far.itemColorId = qc.itemColorId and far.rollId = qc.rollId and qc.transactionType = '2' and qc.departmentId = '1') as qcPassedQty \r\n" + 
					"from tbAccessoriesAccessoriesTransaction far\r\n" + 
					"left join tbAccessoriesIndent findent\r\n" + 
					"on far.PurchaseOrder = findent.purchaseOrder and far.styleId = findent.styleId and far.styleItemId= findent.itemId and far.colorId = findent.itemcolor and far.dItemId = findent.accessoriesid and far.itemColorId = findent.accessoriescolor\r\n" + 
					"left join TbStyleCreate sc\r\n" + 
					"on far.styleId = sc.StyleId\r\n" + 
					"left join tbItemDescription id\r\n" + 
					"on far.styleItemId = id.itemid\r\n"
					+ "left join tbaccessoriesSizeInfo fri\r\n" + 
					"on far.rollId = fri.rollId \r\n" + 
					"left join tbunits u\r\n" + 
					"on far.unitId = u.Unitid\r\n" + 
					"left join TbAccessoriesItem fi\r\n" + 
					"on far.dItemId = fi.id\r\n" + 
					"left join tbColors ic\r\n" + 
					"on far.colorId = ic.ColorId\r\n" + 
					"left join tbColors fc\r\n" + 
					"on far.itemColorId = fc.ColorId\r\n" + 
					"where transactionId = '"+transactionId+"' and transactionType='"+StoreTransaction.FABRICS_RECEIVE.getType()+"'";		
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				tempSize = new AccessoriesSize(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString(),element[7].toString(), element[8].toString(),element[9].toString(), element[10].toString(), element[11].toString(), element[12].toString(), element[13].toString(), element[14].toString(), element[15].toString(),element[16].toString(),0.0, Double.valueOf(element[17].toString()), element[18].toString(), element[19].toString(),1);
				tempSize.setOrderQty(Double.valueOf(element[21].toString()));
				tempSize.setPreviousReceiveQty(Double.valueOf(element[22].toString()));
				tempSize.setReturnQty(Double.valueOf(element[23].toString()));
				tempSize.setIssueQty(Double.valueOf(element[24].toString()));
				tempSize.setQcPassedQty(Double.valueOf(element[25].toString()));
				accessoriesSizeList.add(tempSize);				
			}

			sql = "select autoId,transactionId,grnNo,(select convert(varchar,grnDate,103))as grnDate,location,fri.supplierId,fri.challanNo,fri.challanDate,fri.remarks,fri.preperedBy,fri.createBy \r\n" + 
					"from tbAccessoriesReceiveInfo fri\r\n" + 
					"where fri.transactionId = '"+transactionId+"'";		
			list = session.createSQLQuery(sql).list();
			if(list.size()>0)
			{	
				Object[] element = (Object[]) list.get(0);

				accessoriesReceive = new AccessoriesReceive(element[0].toString(), element[1].toString(),element[2].toString(), element[3].toString(), element[4].toString(), "", element[5].toString(), element[6].toString(), element[7].toString(), element[8].toString(), element[9].toString(), element[10].toString());
				accessoriesReceive.setAccessoriesSizeList(accessoriesSizeList);
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
		return accessoriesReceive;
	}


	//Accessories Quality Control
	@Override
	public boolean submitAccessoriesQC(AccessoriesQualityControl accessoriesQC) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		StoreTransaction transaction = StoreTransaction.FABRICS_QC;
		ItemType itemType = ItemType.FABRICS;


		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select (isnull(max(transactionId),0)+1) as maxId from tbaccessoriesQualityControlInfo";
			List<?> list = session.createSQLQuery(sql).list();
			String transactionId="0";
			if(list.size()>0) {
				transactionId = list.get(0).toString();
			}

			sql="insert into tbaccessoriesQualityControlInfo (transactionId"
					+ ",date"
					+ ",grnNo"
					+ ",remarks"
					+ ",departmentId"
					+ ",checkBy"
					+ ",entryTime"
					+ ",createBy) values("
					+ "'"+transactionId+"'"
					+ ",'"+accessoriesQC.getQcDate()+"'"
					+ ",'"+accessoriesQC.getGrnNo()+"'"
					+ ",'"+accessoriesQC.getRemarks()+"'"
					+ ",'"+accessoriesQC.getDepartmentId()+"'"
					+ ",'"+accessoriesQC.getCheckBy()+"',"
					+ "CURRENT_TIMESTAMP,'"+accessoriesQC.getUserId()+"');";
			session.createSQLQuery(sql).executeUpdate();

			
			int departmentId = 1;
			for (AccessoriesSize roll : accessoriesQC.getAccessoriesSizeList()) {
				sql="insert into tbAccessoriesAccessoriesTransaction (purchaseOrder,styleId,styleItemId,colorId,itemColorId,transactionId,transactionType,itemType,rollId,unitId,unitQty,qty,dItemId,cItemId,departmentId,rackName,binName,entryTime,userId) \r\n" + 
						"values('"+roll.getPurchaseOrder()+"','"+roll.getStyleId()+"','"+roll.getItemId()+"','"+roll.getItemColorId()+"','"+roll.getAccessoriesColorId()+"','"+transactionId+"','"+transaction.getType()+"','"+itemType.getType()+"','"+roll.getSizeId()+"','"+roll.getUnitId()+"','"+roll.getUnitQty()+"','"+roll.getUnitQty()+"','"+roll.getAccessoriesId()+"','0','"+departmentId+"','"+roll.getRackName()+"','"+roll.getBinName()+"',CURRENT_TIMESTAMP,'"+accessoriesQC.getUserId()+"');";		
				session.createSQLQuery(sql).executeUpdate();
			}

			for (AccessoriesSize roll : accessoriesQC.getAccessoriesSizeList()) {
				sql="insert into tbQualityControlDetails (transactionId,transactionType,itemType,itemId,rollId,unitId,QCCheckQty,shade,shrinkage,gsm,width,defect,remarks,qcPassedType,entryTime,userId) \r\n" + 
						"values('"+transactionId+"','"+transaction.getType()+"','"+itemType.getType()+"','"+roll.getAccessoriesId()+"','"+roll.getSizeId()+"','"+roll.getUnitId()+"','"+roll.getUnitQty()+"','"+roll.getShadeQty()+"','"+roll.getShrinkageQty()+"','"+roll.getGsmQty()+"','"+roll.getWidthQty()+"','"+roll.getDefectQty()+"','"+roll.getRemarks()+"','"+roll.getQcPassedType()+"',CURRENT_TIMESTAMP,'"+accessoriesQC.getUserId()+"')";		
				session.createSQLQuery(sql).executeUpdate();
			}

			tx.commit();

			return true;
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

		return false;
	}

	@Override
	public boolean editAccessoriesQC(AccessoriesQualityControl accessoriesQC) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="update tbaccessoriesQualityControlInfo set "
					+ "date = '"+accessoriesQC.getQcDate()+"'"
					+ ",grnNo = '"+accessoriesQC.getGrnNo()+"'"
					+ ",remarks = '"+accessoriesQC.getRemarks()+"'"
					+ ",entryTime = CURRENT_TIMESTAMP"
					+ ",createBy = '"+accessoriesQC.getUserId()+"' where qcTransactionId='"+accessoriesQC.getQcTransactionId()+"';";

			session.createSQLQuery(sql).executeUpdate();

			if(accessoriesQC.getAccessoriesSizeList() != null) {
				for (AccessoriesSize roll : accessoriesQC.getAccessoriesSizeList()) {
					sql="update tbAccessoriesSizeDetails set QCTransactionId='"+accessoriesQC.getQcTransactionId()+"',qcPassedQty='"+roll.getQcPassedQty()+"',qcPassedType='"+roll.getQcPassedType()+"' where autoId='"+roll.getAutoId()+"'";		
					session.createSQLQuery(sql).executeUpdate();
				}
			}
			

			tx.commit();

			return true;
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

		return false;
	}

	@Override
	public List<AccessoriesQualityControl> getAccessoriesQCList() {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<AccessoriesQualityControl> datalist=new ArrayList<AccessoriesQualityControl>();	
		try{	
			tx=session.getTransaction();
			tx.begin();		
			String sql = "select qci.AutoId,qci.TransactionId,(select convert(varchar,qci.date,103))as qcDate,qci.grnNo,(select convert(varchar,fri.grnDate,103))as receiveDate,qci.remarks,fri.supplierId,qci.checkBy,qci.createBy \r\n" + 
					"from tbAccessoriesQualityControlInfo qci\r\n" + 
					"left join tbAccessoriesReceiveInfo fri\r\n" + 
					"on qci.grnNo = fri.grnNo";		
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				datalist.add(new AccessoriesQualityControl(element[0].toString(),element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString(), element[7].toString(), element[8].toString()));				
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
		return datalist;
	}

	@Override
	public AccessoriesQualityControl getAccessoriesQCInfo(String qcTransactionId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		AccessoriesQualityControl accessoriesQC = null;
		List<AccessoriesSize> accessoriesSizeList = new ArrayList<AccessoriesSize>();	
		try{	
			tx=session.getTransaction();
			tx.begin();		
			String sql = "select qcd.autoId,qcd.transactionId,qcd.rollId,fri.supplierSizeId,fat.purchaseOrder,fat.styleId,fat.styleItemId,fat.colorId,fat.dItemId,fat.itemColorId,qcd.unitId,u.unitname,far.unitQty,QCCheckQty,shade,shrinkage,gsm,width,defect,remarks,fat.rackName,fat.BinName,qcPassedType,qcd.userId \r\n" + 
					"from tbQualityControlDetails qcd\r\n"
					+ "left join tbaccessoriesSizeInfo fri\r\n" + 
					"on qcd.rollId = fri.rollId\r\n" + 
					"left join tbunits u\r\n" + 
					"on qcd.unitId = u.Unitid\r\n" + 
					"left join tbAccessoriesAccessoriesTransaction far\r\n" + 
					"on qcd.transactionId = far.transactionId and far.transactionType = '"+StoreTransaction.FABRICS_QC.getType()+"' and qcd.itemId = far.dItemId and qcd.rollId = far.rollId\r\n" + 
					"left join tbAccessoriesAccessoriesTransaction fat\r\n" + 
					"on qcd.transactionId = fat.transactionId and qcd.transactionType = fat.transactionType and qcd.itemId = fat.dItemId and qcd.rollId = fat.rollId\r\n" + 
					"where qcd.transactionId = '"+qcTransactionId+"' \r\n" + 
					"";		
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				accessoriesSizeList.add(new AccessoriesSize(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString(), element[7].toString(), element[8].toString(), "", element[9].toString(), "", element[10].toString(), element[11].toString(), Double.valueOf(element[12].toString()), Double.valueOf(element[13].toString()), Double.valueOf(element[14].toString()), Double.valueOf(element[15].toString()), Double.valueOf(element[16].toString()), Double.valueOf(element[17].toString()),Double.valueOf(element[18].toString()), element[19].toString(), element[20].toString(), element[21].toString(), Integer.valueOf(element[22].toString())));				
			}
			sql = "select qci.AutoId,qci.TransactionId,(select convert(varchar,qci.date,103))as qcDate,qci.grnNo,(select convert(varchar,fri.grnDate,103))as receiveDate,qci.remarks,fri.supplierId,qci.checkBy,qci.createBy \r\n" + 
					"from tbAccessoriesQualityControlInfo qci\r\n" + 
					"left join tbAccessoriesReceiveInfo fri\r\n" + 
					"on qci.grnNo = fri.grnNo where qci.transactionId = '"+qcTransactionId+"'";		
			list = session.createSQLQuery(sql).list();
			if(list.size()>0)
			{	
				Object[] element = (Object[]) list.get(0);

				accessoriesQC = new AccessoriesQualityControl(element[0].toString(), qcTransactionId, element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString(), element[7].toString(),element[8].toString());
				accessoriesQC.setAccessoriesSizeList(accessoriesSizeList);
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
		return accessoriesQC;
	}

	@Override
	public List<AccessoriesSize> getAccessoriesSizeListBySupplier(String supplierId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		AccessoriesSize tempSize;
		List<AccessoriesSize> datalist=new ArrayList<AccessoriesSize>();	
		try{	
			tx=session.getTransaction();
			tx.begin();		
			String sql = "select fat.autoID,fri.supplierId,s.name as supplierName,fat.purchaseOrder,fat.styleId,sc.StyleNo,fat.styleItemId,id.itemname,fat.colorid,c.colorName,fat.dItemId as accessoriesId,fi.ItemName as accessoriesName,fat.itemColorId,ic.Colorname as accessoriesColor,fat.rollId,frinfo.supplierSizeId,fi.unitId,u.unitname,dbo.accessoriesBalanceQty(fat.purchaseOrder,fat.styleid,fat.styleItemId,fat.colorId,fat.dItemId,fat.ItemcolorId,fat.rollId,fat.departmentId) as balanceQty,fat.rackName,fat.binName,\r\n" + 
					"(select isnull(sum(qty),0) from tbAccessoriesAccessoriesTransaction t where t.dItemId = fat.dItemId and t.transactionId = fat.transactionId and t.transactionType = fat.transactionType and t.itemColorId = fat.itemColorId and t.dItemId = fat.dItemId and t.colorId = fat.colorId and t.styleItemId = fat.styleItemId and t.styleId = fat.styleId and t.purchaseOrder = fat.purchaseOrder) as ReceiveQty,\r\n" + 
					"(select isnull(sum(qty),0) from tbAccessoriesAccessoriesTransaction t where t.cItemId = fat.dItemId and t.transactionId = fat.transactionId and t.transactionType = '"+StoreTransaction.FABRICS_ISSUE.getType()+"' and t.itemColorId = fat.itemColorId and t.cItemId = fat.dItemId and t.colorId = fat.colorId and t.styleItemId = fat.styleItemId and t.styleId = fat.styleId and t.purchaseOrder = fat.purchaseOrder) as IssueQty,\r\n" +
					"(select isnull(sum(qty),0) from tbAccessoriesAccessoriesTransaction t where t.cItemId = fat.dItemId and t.transactionId = fat.transactionId and t.transactionType = '"+StoreTransaction.FABRICS_RETURN.getType()+"' and t.itemColorId = fat.itemColorId and t.cItemId = fat.dItemId and t.colorId = fat.colorId and t.styleItemId = fat.styleItemId and t.styleId = fat.styleId and t.purchaseOrder = fat.purchaseOrder) as previousReturnQty\r\n" +
					"from tbAccessoriesReceiveInfo fri\r\n" + 
					"left join tbSupplier s\r\n" + 
					"on fri.supplierId = s.id\r\n" + 
					"left join tbAccessoriesAccessoriesTransaction fat\r\n" + 
					"on fri.transactionId = fat.transactionId and fat.transactionType = '"+StoreTransaction.FABRICS_RECEIVE.getType()+"'\r\n" + 
					"left join TbStyleCreate sc\r\n" + 
					"on fat.styleId = sc.StyleId\r\n" + 
					"left join tbItemDescription id\r\n" + 
					"on fat.styleItemId = id.itemid\r\n" + 
					"left join tbColors c\r\n" + 
					"on fat.colorId = c.ColorId\r\n" + 
					"left join TbAccessoriesItem fi\r\n" + 
					"on fat.dItemId = fi.id\r\n" + 
					"left join tbColors ic\r\n" + 
					"on fat.itemColorId = ic.ColorId\r\n"
					+ "left join tbaccessoriesSizeInfo frinfo\r\n" + 
					"on fat.rollId = frinfo.rollId \r\n" + 
					"left join tbunits u\r\n" + 
					"on fi.unitId = u.Unitid\r\n" + 
					" where fri.supplierId='"+supplierId+"'";		
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				tempSize = new AccessoriesSize(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString(), element[7].toString(), element[8].toString(), element[9].toString(), element[10].toString(), element[11].toString(), element[12].toString(), element[13].toString(), element[14].toString(),element[15].toString(),element[16].toString(),element[17].toString(), Double.valueOf(element[18].toString()),element[19].toString(),element[20].toString());
				tempSize.setPreviousReceiveQty(Double.valueOf(element[21].toString()));
				tempSize.setIssueQty(Double.valueOf(element[22].toString()));
				tempSize.setReturnQty(Double.valueOf(element[23].toString()));
				
				datalist.add(tempSize);				
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
		return datalist;
	}



	@Override
	public boolean submitAccessoriesReturn(AccessoriesReturn accessoriesReturn) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		StoreTransaction transaction = StoreTransaction.FABRICS_RETURN;
		ItemType itemType = ItemType.FABRICS;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select (isnull(max(transactionId),0)+1) as maxId from tbAccessoriesReturnInfo";
			List<?> list = session.createSQLQuery(sql).list();
			String transactionId="0";
			if(list.size()>0) {
				transactionId = list.get(0).toString();
			}

			sql="insert into tbAccessoriesReturnInfo (transactionId"
					+ ",date"
					+ ",supplierId"
					+ ",remarks"
					+ ",departmentId"
					+ ",entryTime"
					+ ",createBy) values("
					+ "'"+transactionId+"'"
					+ ",'"+accessoriesReturn.getReturnDate()+"'"
					+ ",'"+accessoriesReturn.getSupplierId()+"'"
					+ ",'"+accessoriesReturn.getRemarks()+"'"
					+ ",'"+accessoriesReturn.getDepartmentId()+"',"
					+ "CURRENT_TIMESTAMP,'"+accessoriesReturn.getUserId()+"');";
			session.createSQLQuery(sql).executeUpdate();

			int departmentId = 1;
			for (AccessoriesSize roll : accessoriesReturn.getAccessoriesSizeList()) {
				sql="insert into tbAccessoriesAccessoriesTransaction (purchaseOrder,styleId,styleItemId,colorId,itemColorId,transactionId,transactionType,itemType,rollId,unitId,unitQty,qty,dItemId,cItemId,departmentId,rackName,binName,entryTime,userId) \r\n" + 
						"values('"+roll.getPurchaseOrder()+"','"+roll.getStyleId()+"','"+roll.getItemId()+"','"+roll.getItemColorId()+"','"+roll.getAccessoriesColorId()+"','"+transactionId+"','"+transaction.getType()+"','"+itemType.getType()+"','"+roll.getSizeId()+"','"+roll.getUnitId()+"','"+roll.getUnitQty()+"','"+roll.getUnitQty()+"','0','"+roll.getAccessoriesId()+"','"+departmentId+"','"+roll.getRackName()+"','"+roll.getBinName()+"',CURRENT_TIMESTAMP,'"+accessoriesReturn.getUserId()+"');";		
				session.createSQLQuery(sql).executeUpdate();
			}

			tx.commit();

			return true;
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

		return false;
	}

	@Override
	public boolean editAccessoriesReturn(AccessoriesReturn accessoriesReturn) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		StoreTransaction transaction = StoreTransaction.FABRICS_RETURN;
		ItemType itemType = ItemType.FABRICS;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="update tbaccessoriesReturnInfo set "
					+ "date = '"+accessoriesReturn.getReturnDate()+"'"
					+ ",supplierId = '"+accessoriesReturn.getSupplierId()+"'"
					+ ",remarks = '"+accessoriesReturn.getRemarks()+"'"
					+ ",entryTime = CURRENT_TIMESTAMP"
					+ ",createBy = '"+accessoriesReturn.getUserId()+"' where transactionId='"+accessoriesReturn.getReturnTransactionId()+"';";

			session.createSQLQuery(sql).executeUpdate();

			int departmentId = 1;
			if(accessoriesReturn.getAccessoriesSizeList() != null) {
				for (AccessoriesSize roll : accessoriesReturn.getAccessoriesSizeList()) {
					sql="insert into tbAccessoriesAccessoriesTransaction (purchaseOrder,styleId,styleItemId,colorId,itemColorId,transactionId,transactionType,itemType,rollId,unitId,unitQty,qty,dItemId,cItemId,departmentId,rackName,binName,entryTime,userId) \r\n" + 
							"values('"+roll.getPurchaseOrder()+"','"+roll.getStyleId()+"','"+roll.getItemId()+"','"+roll.getItemColorId()+"','"+roll.getAccessoriesColorId()+"','"+accessoriesReturn.getReturnTransactionId()+"','"+transaction.getType()+"','"+itemType.getType()+"','"+roll.getSizeId()+"','"+roll.getUnitId()+"','"+roll.getUnitQty()+"','"+roll.getUnitQty()+"','0','"+roll.getAccessoriesId()+"','"+departmentId+"','"+roll.getRackName()+"','"+roll.getBinName()+"',CURRENT_TIMESTAMP,'"+accessoriesReturn.getUserId()+"');";		
					session.createSQLQuery(sql).executeUpdate();
				}
			}
			

			tx.commit();

			return true;
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

		return false;
	}

	@Override
	public String editReturnSizeInTransaction(AccessoriesSize accessoriesSize) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		try{
			tx=session.getTransaction();
			tx.begin();
			String sql = "select far.autoId,dbo.accessoriesBalanceQtyExceptAutoId(far.purchaseOrder,far.styleId,far.styleItemId,far.colorId,far.cItemId,far.itemColorId,far.rollId,far.departmentId,far.autoId) as balanceQty \r\n" + 
					"from tbAccessoriesAccessoriesTransaction far\r\n" + 
					"where far.autoId = '"+accessoriesSize.getAutoId()+"'";		
			List<?> list = session.createSQLQuery(sql).list();
			if(list.size()>0) {
				Object[] element = (Object[]) list.get(0);
				if(Double.valueOf(element[1].toString())>=accessoriesSize.getUnitQty()) {
					sql = "update tbAccessoriesAccessoriesTransaction set unitQty = '"+accessoriesSize.getUnitQty()+"',qty = '"+accessoriesSize.getUnitQty()+"' where autoId = '"+accessoriesSize.getAutoId()+"'";
					if(session.createSQLQuery(sql).executeUpdate()==1) {
						tx.commit();
						return "Successful";
					}
				}else {
					tx.commit();
					return "Return Qty Exist";
				}
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

		return "Something Wrong";
	}



	@Override
	public String deleteReturnSizeFromTransaction(AccessoriesSize accessoriesSize) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql = "delete from tbAccessoriesAccessoriesTransaction where autoId = '"+accessoriesSize.getAutoId()+"'";
			if(session.createSQLQuery(sql).executeUpdate()==1) {
				tx.commit();
				return "Successful";
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

		return "Something Wrong";
	}



	@Override
	public List<AccessoriesReturn> getAccessoriesReturnList() {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<AccessoriesReturn> datalist=new ArrayList<AccessoriesReturn>();	
		try{	
			tx=session.getTransaction();
			tx.begin();		
			String sql = "select fri.AutoId,fri.transactionId,(select convert(varchar,fri.date,103))as returnDate,fri.supplierId,s.name as supplierName,fri.remarks,fri.createBy \r\n" + 
					"from tbAccessoriesReturnInfo fri\r\n" + 
					"left join tbSupplier s\r\n" + 
					"on fri.supplierId = s.id";		
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				datalist.add(new AccessoriesReturn(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString()));				
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
		return datalist;
	}

	@Override
	public AccessoriesReturn getAccessoriesReturnInfo(String returnTransactionId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		AccessoriesReturn accessoriesReturn = null;
		AccessoriesSize tempSize;
		List<AccessoriesSize> accessoriesSizeList = new ArrayList<AccessoriesSize>();	
		String departmentId = "1";
		try{	
			tx=session.getTransaction();
			tx.begin();		
			String sql = "select far.autoId,far.transactionId,far.purchaseOrder,far.styleId,sc.styleNo,far.styleItemId,id.itemname,far.colorId as itemColorId ,ic.Colorname as itemColorName,cItemId as accessoriesId,fi.ItemName as accessoriesName,far.itemColorId as accessoriesColorId,fc.Colorname as accessoriesColorName,far.rollId,fri.supplierSizeId,far.unitId,u.unitname,unitQty,rackName,BinName,far.userId\r\n" + 
					",dbo.accessoriesBalanceQty(far.purchaseOrder,far.styleId,far.styleItemId,far.colorId,far.cItemId,far.itemColorId,far.rollId,far.departmentId) as balanceQty \r\n" +
					",(select isnull(sum(qty),0) from tbAccessoriesAccessoriesTransaction t where far.purchaseOrder=t.purchaseOrder and far.styleId = t.styleId and far.styleItemId= t.styleItemId and far.colorId = t.colorId and far.cItemId = t.dItemId and far.itemColorId = t.itemColorId and t.transactionType = '"+StoreTransaction.FABRICS_RECEIVE.getType()+"' and t.departmentId = '"+departmentId+"') as previousReceiveQty,\r\n" + 
					"(select isnull(sum(qty),0) from tbAccessoriesAccessoriesTransaction t where far.purchaseOrder=t.purchaseOrder and far.styleId = t.styleId and far.styleItemId= t.styleItemId and far.colorId = t.colorId and far.cItemId = t.cItemId and far.itemColorId = t.itemColorId and t.transactionType = '"+StoreTransaction.FABRICS_RETURN.getType()+"' and t.departmentId = '"+departmentId+"') as returnQty,\r\n" + 
					"(select isnull(sum(qty),0) from tbAccessoriesAccessoriesTransaction t where far.purchaseOrder=t.purchaseOrder and far.styleId = t.styleId and far.styleItemId= t.styleItemId and far.colorId = t.colorId and far.cItemId = t.cItemId and far.itemColorId = t.itemColorId and t.transactionType = '"+StoreTransaction.FABRICS_ISSUE.getType()+"' and t.departmentId = '"+departmentId+"') as issueQty\r\n" + 
					"from tbAccessoriesAccessoriesTransaction far\r\n" + 
					"left join TbStyleCreate sc\r\n" + 
					"on far.styleId = sc.StyleId\r\n" + 
					"left join tbItemDescription id\r\n" + 
					"on far.styleItemId = id.itemid\r\n"
					+ "left join tbaccessoriesSizeInfo fri\r\n" + 
					"on far.rollId = fri.rollId \r\n" + 
					"left join tbunits u\r\n" + 
					"on far.unitId = u.Unitid\r\n" + 
					"left join TbAccessoriesItem fi\r\n" + 
					"on far.cItemId = fi.id\r\n" + 
					"left join tbColors ic\r\n" + 
					"on far.colorId = ic.ColorId\r\n" + 
					"left join tbColors fc\r\n" + 
					"on far.itemColorId = fc.ColorId\r\n" + 
					"where transactionId = '"+returnTransactionId+"' and transactionType='"+StoreTransaction.FABRICS_RETURN.getType()+"'";	
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				tempSize = new AccessoriesSize(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString(),element[7].toString(), element[8].toString(),element[9].toString(), element[10].toString(), element[11].toString(), element[12].toString(), element[13].toString(), element[14].toString(), element[15].toString(),element[16].toString(),0.0, Double.valueOf(element[17].toString()), element[18].toString(), element[19].toString(),1);
				tempSize.setBalanceQty(Double.valueOf(element[21].toString()));
				tempSize.setPreviousReceiveQty(Double.valueOf(element[22].toString()));
				tempSize.setReturnQty(Double.valueOf(element[23].toString()));
				tempSize.setIssueQty(Double.valueOf(element[24].toString()));
				
				accessoriesSizeList.add(tempSize);				
			}
			sql = "select fri.autoId,fri.transactionId,(select convert(varchar,fri.date,103))as date,fri.supplierId,s.name as supplierName,fri.remarks,fri.createBy \r\n" + 
					"from tbAccessoriesReturnInfo fri\r\n" + 
					"left join tbSupplier s\r\n" + 
					"on fri.supplierId = s.id\r\n" + 
					"where fri.transactionId = '"+returnTransactionId+"'";		
			list = session.createSQLQuery(sql).list();
			if(list.size()>0)
			{	
				Object[] element = (Object[]) list.get(0);

				accessoriesReturn = new AccessoriesReturn(element[0].toString(),element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString());
				accessoriesReturn.setAccessoriesSizeList(accessoriesSizeList);
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
		return accessoriesReturn;
	}

	@Override
	public AccessoriesReceive getAccessoriesReceiveInfoForReturn(String transactionId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		AccessoriesReceive accessoriesReceive = null;
		List<AccessoriesSize> accessoriesSizeList = new ArrayList<AccessoriesSize>();	
		try{	
			tx=session.getTransaction();
			tx.begin();		
			String sql = "select autoId,isnull(returnTransactionId,'')as returnTransactionId,rollId,supplierSizeId,frd.unitId,u.unitname,rollQty,qcPassedQty,rackName,BinName,qcPassedType,isReturn,createBy  \r\n" + 
					"from tbAccessoriesSizeDetails frd\r\n" + 
					"left join tbunits u\r\n" + 
					"on frd.unitId = u.Unitid\r\n" + 
					"where transactionId = '"+transactionId+"'";		
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				boolean isReturn = element[11].toString().equals("1")?true:false;
				//accessoriesSizeList.add(new AccessoriesSize(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), Double.valueOf(element[6].toString()), Double.valueOf(element[7].toString()), element[8].toString(), element[9].toString(), Integer.valueOf(element[10].toString()), isReturn, element[12].toString()));				
			}

			sql = "select autoId,transactionId,grnNo,(select convert(varchar,grnDate,103))as grnDate,location,fIndent.PurchaseOrder,fIndent.styleId,sc.StyleNo,fIndent.itemid,id.itemname,fIndent.accessoriescolor as colorId,c.Colorname,fri.accessoriesId,ISNULL(fi.ItemName,'')as accessoriesName,indentId,fri.unitId,grnQty,noOfSize,fri.supplierId,fri.buyer,fri.challanNo,fri.challanDate,fri.remarks,fri.preperedBy,fri.createBy \r\n" + 
					"from tbAccessoriesReceiveInfo fri\r\n" + 
					"left join tbAccessoriesIndent fIndent\r\n" + 
					"on fri.indentId = fIndent.id\r\n" + 
					"left join TbStyleCreate sc\r\n" + 
					"on fIndent.styleId = sc.StyleId\r\n" + 
					"left join tbItemDescription id\r\n" + 
					"on fIndent.itemid = id.itemid\r\n" + 
					"left join tbColors c\r\n" + 
					"on fIndent.itemcolor = c.ColorId\r\n" + 
					"left join TbAccessoriesItem fi\r\n" + 
					"on fIndent.accessoriesId = fi.id where fri.transactionId = '"+transactionId+"'";		
			list = session.createSQLQuery(sql).list();
			if(list.size()>0)
			{	
				Object[] element = (Object[]) list.get(0);
				boolean  isReturn = element[24].toString()=="1"?true:false;
				accessoriesReceive = new AccessoriesReceive();
				accessoriesReceive.setAccessoriesSizeList(accessoriesSizeList);
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
		return accessoriesReceive;

	}
	
	@Override
	public List<AccessoriesSize> getAvailableAccessoriesSizeListInDepartment(String departmentId) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		AccessoriesSize tempSize;
		List<AccessoriesSize> datalist=new ArrayList<AccessoriesSize>();	
		try{	
			tx=session.getTransaction();
			tx.begin();		
			String sql = "select fat.autoId,'supplierId' as supplierId,'supplierName' as supplierName,fat.purchaseOrder,fat.styleId,sc.StyleNo,fat.styleItemId,id.itemname,fat.colorid,c.colorName,fat.dItemId as accessoriesId,fi.ItemName as accessoriesName,fat.itemColorId,ic.Colorname as accessoriesColor,fat.rollId,frinfo.supplierSizeId,fi.unitId,u.unitname,dbo.accessoriesBalanceQty(fat.purchaseOrder,fat.styleid,fat.styleItemId,fat.colorId,fat.dItemId,fat.ItemcolorId,fat.rollId,fat.departmentId) as balanceQty,fat.rackName,fat.binName,\r\n" + 
					"(select isnull(sum(qty),0) from tbAccessoriesAccessoriesTransaction t where t.dItemId = fat.dItemId and t.transactionId = fat.transactionId and t.transactionType = fat.transactionType and t.itemColorId = fat.itemColorId and t.dItemId = fat.dItemId and t.colorId = fat.colorId and t.styleItemId = fat.styleItemId and t.styleId = fat.styleId and t.purchaseOrder = fat.purchaseOrder) as ReceiveQty,\r\n" + 
					"(select isnull(sum(qty),0) from tbAccessoriesAccessoriesTransaction t where t.cItemId = fat.dItemId and t.transactionId = fat.transactionId and t.transactionType = '"+StoreTransaction.FABRICS_ISSUE.getType()+"' and t.itemColorId = fat.itemColorId and t.cItemId = fat.dItemId and t.colorId = fat.colorId and t.styleItemId = fat.styleItemId and t.styleId = fat.styleId and t.purchaseOrder = fat.purchaseOrder) as IssueQty,\r\n" + 
					"(select isnull(sum(qty),0) from tbAccessoriesAccessoriesTransaction t where t.cItemId = fat.dItemId and t.transactionId = fat.transactionId and t.transactionType = '"+StoreTransaction.FABRICS_RETURN.getType()+"' and t.itemColorId = fat.itemColorId and t.cItemId = fat.dItemId and t.colorId = fat.colorId and t.styleItemId = fat.styleItemId and t.styleId = fat.styleId and t.purchaseOrder = fat.purchaseOrder) as previousReturnQty\r\n" + 
					"from tbAccessoriesAccessoriesTransaction as fat\r\n" + 
					"left join TbStyleCreate sc\r\n" + 
					"on fat.styleId = sc.StyleId\r\n" + 
					"left join tbItemDescription id\r\n" + 
					"on fat.styleItemId = id.itemid\r\n" + 
					"left join tbColors c\r\n" + 
					"on fat.colorId = c.ColorId\r\n" + 
					"left join TbAccessoriesItem fi\r\n" + 
					"on fat.dItemId = fi.id\r\n" + 
					"left join tbColors ic\r\n" + 
					"on fat.itemColorId = ic.ColorId\r\n" + 
					"left join tbaccessoriesSizeInfo frinfo\r\n" + 
					"on fat.rollId = frinfo.rollId \r\n" + 
					"left join tbunits u\r\n" + 
					"on fi.unitId = u.Unitid\r\n" + 
					"where fat.transactionType = '"+StoreTransaction.FABRICS_RECEIVE.getType()+"' and fat.departmentId = '"+departmentId+"' \r\n"+
					"and dbo.accessoriesBalanceQty(fat.purchaseOrder,fat.styleid,fat.styleItemId,fat.colorId,fat.dItemId,fat.ItemcolorId,fat.rollId,fat.departmentId) > 0 \r\n"+
					"order by fat.purchaseOrder,fat.styleId,fat.styleItemId,fat.colorId,fat.dItemId,fat.itemColorId";		
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				tempSize = new AccessoriesSize(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString(), element[7].toString(), element[8].toString(), element[9].toString(), element[10].toString(), element[11].toString(), element[12].toString(), element[13].toString(), element[14].toString(),element[15].toString(),element[16].toString(),element[17].toString(), Double.valueOf(element[18].toString()),element[19].toString(),element[20].toString());
				tempSize.setPreviousReceiveQty(Double.valueOf(element[21].toString()));
				tempSize.setIssueQty(Double.valueOf(element[22].toString()));
				tempSize.setReturnQty(Double.valueOf(element[23].toString()));
				datalist.add(tempSize);				
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
		return datalist;
	}

	@Override
	public boolean submitAccessoriesIssue(AccessoriesIssue accessoriesIssue) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		StoreTransaction transaction = StoreTransaction.FABRICS_ISSUE;
		ItemType itemType = ItemType.FABRICS;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select (isnull(max(transactionId),0)+1) as maxId from tbAccessoriesIssueInfo";
			List<?> list = session.createSQLQuery(sql).list();
			String transactionId="0";
			if(list.size()>0) {
				transactionId = list.get(0).toString();
			}

			sql="insert into tbAccessoriesIssueInfo (transactionId"
					+ ",date"
					+ ",issuedTo"
					+ ",receiveBy"
					+ ",remarks"
					+ ",departmentId"
					+ ",entryTime"
					+ ",createBy) values("
					+ "'"+transactionId+"'"
					+ ",'"+accessoriesIssue.getIssueDate()+"'"
					+ ",'"+accessoriesIssue.getIssuedTo()+"'"
					+ ",'"+accessoriesIssue.getReceiveBy()+"'"
					+ ",'"+accessoriesIssue.getRemarks()+"'"
					+ ",'"+accessoriesIssue.getDepartmentId()+"',"
					+ "CURRENT_TIMESTAMP,'"+accessoriesIssue.getUserId()+"');";
			session.createSQLQuery(sql).executeUpdate();
			int departmentId = 1;
			for (AccessoriesSize roll : accessoriesIssue.getAccessoriesSizeList()) {
				sql="insert into tbAccessoriesAccessoriesTransaction (purchaseOrder,styleId,styleItemId,colorId,itemColorId,transactionId,transactionType,itemType,rollId,unitId,unitQty,qty,dItemId,cItemId,departmentId,rackName,binName,entryTime,userId) \r\n" + 
						"values('"+roll.getPurchaseOrder()+"','"+roll.getStyleId()+"','"+roll.getItemId()+"','"+roll.getItemColorId()+"','"+roll.getAccessoriesColorId()+"','"+transactionId+"','"+transaction.getType()+"','"+itemType.getType()+"','"+roll.getSizeId()+"','"+roll.getUnitId()+"','"+roll.getUnitQty()+"','"+roll.getUnitQty()+"','0','"+roll.getAccessoriesId()+"','"+departmentId+"','"+roll.getRackName()+"','"+roll.getBinName()+"',CURRENT_TIMESTAMP,'"+accessoriesIssue.getUserId()+"');";		
				session.createSQLQuery(sql).executeUpdate();
			}

			tx.commit();

			return true;
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

		return false;
	}

	@Override
	public boolean editAccessoriesIssue(AccessoriesIssue accessoriesIssue) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		StoreTransaction transaction = StoreTransaction.FABRICS_ISSUE;
		ItemType itemType = ItemType.FABRICS;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="update tbaccessoriesIssueInfo set "
					+ "date = '"+accessoriesIssue.getIssueDate()+"'"
					+ ",issuedTo = '"+accessoriesIssue.getIssuedTo()+"'"
					+ ",receiveBy = '"+accessoriesIssue.getReceiveBy()+"'"
					+ ",remarks = '"+accessoriesIssue.getRemarks()+"'"
					+ ",entryTime = CURRENT_TIMESTAMP"
					+ ",createBy = '"+accessoriesIssue.getUserId()+"' where transactionId='"+accessoriesIssue.getTransactionId()+"';";

			session.createSQLQuery(sql).executeUpdate();

			int departmentId = 1;
			if(accessoriesIssue.getAccessoriesSizeList() != null) {
				for (AccessoriesSize roll : accessoriesIssue.getAccessoriesSizeList()) {
					sql="insert into tbAccessoriesAccessoriesTransaction (purchaseOrder,styleId,styleItemId,colorId,itemColorId,transactionId,transactionType,itemType,rollId,unitId,unitQty,qty,dItemId,cItemId,departmentId,rackName,binName,entryTime,userId) \r\n" + 
							"values('"+roll.getPurchaseOrder()+"','"+roll.getStyleId()+"','"+roll.getItemId()+"','"+roll.getItemColorId()+"','"+roll.getAccessoriesColorId()+"','"+accessoriesIssue.getTransactionId()+"','"+transaction.getType()+"','"+itemType.getType()+"','"+roll.getSizeId()+"','"+roll.getUnitId()+"','"+roll.getUnitQty()+"','"+roll.getUnitQty()+"','0','"+roll.getAccessoriesId()+"','"+departmentId+"','"+roll.getRackName()+"','"+roll.getBinName()+"',CURRENT_TIMESTAMP,'"+accessoriesIssue.getUserId()+"');";		
					session.createSQLQuery(sql).executeUpdate();
				}
			}
			

			tx.commit();

			return true;
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

		return false;
	}

	@Override
	public String editIssuedSizeInTransaction(AccessoriesSize accessoriesSize) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		try{
			tx=session.getTransaction();
			tx.begin();
			String sql = "select far.autoId,dbo.accessoriesBalanceQtyExceptAutoId(far.purchaseOrder,far.styleId,far.styleItemId,far.colorId,far.cItemId,far.itemColorId,far.rollId,far.departmentId,far.autoId) as balanceQty \r\n" + 
					"from tbAccessoriesAccessoriesTransaction far\r\n" + 
					"where far.autoId = '"+accessoriesSize.getAutoId()+"'";		
			List<?> list = session.createSQLQuery(sql).list();
			if(list.size()>0) {
				Object[] element = (Object[]) list.get(0);
				if(Double.valueOf(element[1].toString())>=accessoriesSize.getUnitQty()) {
					sql = "update tbAccessoriesAccessoriesTransaction set unitQty = '"+accessoriesSize.getUnitQty()+"',qty = '"+accessoriesSize.getUnitQty()+"' where autoId = '"+accessoriesSize.getAutoId()+"'";
					if(session.createSQLQuery(sql).executeUpdate()==1) {
						tx.commit();
						return "Successful";
					}
				}else {
					tx.commit();
					return "Return Qty Exist";
				}
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

		return "Something Wrong";
	}

	@Override
	public String deleteIssuedSizeFromTransaction(AccessoriesSize accessoriesSize) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql = "delete from tbAccessoriesAccessoriesTransaction where autoId = '"+accessoriesSize.getAutoId()+"'";
			if(session.createSQLQuery(sql).executeUpdate()==1) {
				tx.commit();
				return "Successful";
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

		return "Something Wrong";
	}
	@Override
	public List<AccessoriesSize> getIssuedAccessoriesSizeListInDepartment(String departmentId, String returnDepartmentId) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		AccessoriesSize tempSize;
		List<AccessoriesSize> datalist=new ArrayList<AccessoriesSize>();	
		try{	
			tx=session.getTransaction();
			tx.begin();		
			String sql = "select sil.*,\r\n" + 
					"isnull(dbo.accessoriesIssueReturnedQty('"+departmentId+"','"+returnDepartmentId+"',sil.rollId),0) as previousIssueReturnQty\r\n" + 
					"from dbo.issuedItemList('"+departmentId+"','"+returnDepartmentId+"') as sil\r\n"
							+ "where (sil.issuedQty - isnull(dbo.accessoriesIssueReturnedQty('"+departmentId+"','"+returnDepartmentId+"',sil.rollId),0))>0";		
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				tempSize = new AccessoriesSize(element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString(), element[7].toString(), element[8].toString(), element[9].toString(), element[10].toString(), element[11].toString(), element[12].toString(), element[13].toString(), element[14].toString(), element[15].toString(), element[16].toString(), Double.valueOf(element[17].toString()), 0, Double.valueOf(element[18].toString()));			
				datalist.add(tempSize);				
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
		return datalist;
	}



	@Override
	public List<AccessoriesIssue> getAccessoriesIssueList() {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		AccessoriesIssue tempIssue;
		List<AccessoriesIssue> datalist=new ArrayList<AccessoriesIssue>();	
		try{	
			tx=session.getTransaction();
			tx.begin();		
			String sql = "select fii.AutoId,fii.transactionId,(select convert(varchar,fii.date,103))as issuedDate,fii.issuedTo,fii.receiveBy,fii.remarks,fii.createBy,di.DepartmentName,fi.FactoryName\r\n" + 
					"from tbAccessoriesIssueInfo fii\r\n" + 
					"left join TbDepartmentInfo di\r\n" + 
					"on fii.issuedTo = di.DepartmentId\r\n" + 
					"left join TbFactoryInfo fi\r\n" + 
					"on di.FactoryId = fi.FactoryId";		
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				tempIssue = new AccessoriesIssue(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString());
				tempIssue.setIssuedDepartmentName(element[7].toString()+"("+element[8].toString()+")");
				datalist.add(tempIssue);				
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
		return datalist;
	}
	@Override
	public AccessoriesIssue getAccessoriesIssueInfo(String issueTransactionId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		AccessoriesIssue accessoriesIssue = null;
		AccessoriesSize tempSize;
		List<AccessoriesSize> accessoriesSizeList = new ArrayList<AccessoriesSize>();	
		String departmentId = "1";
		try{	
			tx=session.getTransaction();
			tx.begin();		
			String sql = "select far.autoId,far.transactionId,far.purchaseOrder,far.styleId,sc.styleNo,far.styleItemId,id.itemname,far.colorId as itemColorId ,ic.Colorname as itemColorName,cItemId as accessoriesId,fi.ItemName as accessoriesName,far.itemColorId as accessoriesColorId,fc.Colorname as accessoriesColorName,far.rollId,fri.supplierSizeId,far.unitId,u.unitname,unitQty,rackName,BinName,far.userId\r\n" + 
					",dbo.accessoriesBalanceQty(far.purchaseOrder,far.styleId,far.styleItemId,far.colorId,far.cItemId,far.itemColorId,far.rollId,far.departmentId) as balanceQty \r\n" +
					",(select isnull(sum(qty),0) from tbAccessoriesAccessoriesTransaction t where far.purchaseOrder=t.purchaseOrder and far.styleId = t.styleId and far.styleItemId= t.styleItemId and far.colorId = t.colorId and far.cItemId = t.dItemId and far.itemColorId = t.itemColorId and t.transactionType = '"+StoreTransaction.FABRICS_RECEIVE.getType()+"' and t.departmentId = '"+departmentId+"') as previousReceiveQty,\r\n" + 
					"(select isnull(sum(qty),0) from tbAccessoriesAccessoriesTransaction t where far.purchaseOrder=t.purchaseOrder and far.styleId = t.styleId and far.styleItemId= t.styleItemId and far.colorId = t.colorId and far.cItemId = t.cItemId and far.itemColorId = t.itemColorId and t.transactionType = '"+StoreTransaction.FABRICS_RETURN.getType()+"' and t.departmentId = '"+departmentId+"') as returnQty,\r\n" + 
					"(select isnull(sum(qty),0) from tbAccessoriesAccessoriesTransaction t where far.purchaseOrder=t.purchaseOrder and far.styleId = t.styleId and far.styleItemId= t.styleItemId and far.colorId = t.colorId and far.cItemId = t.cItemId and far.itemColorId = t.itemColorId and t.transactionType = '"+StoreTransaction.FABRICS_ISSUE.getType()+"' and t.departmentId = '"+departmentId+"') as issueQty\r\n" + 
					"from tbAccessoriesAccessoriesTransaction far\r\n" + 
					"left join TbStyleCreate sc\r\n" + 
					"on far.styleId = sc.StyleId\r\n" + 
					"left join tbItemDescription id\r\n" + 
					"on far.styleItemId = id.itemid\r\n"
					+ "left join tbaccessoriesSizeInfo fri\r\n" + 
					"on far.rollId = fri.rollId \r\n" + 
					"left join tbunits u\r\n" + 
					"on far.unitId = u.Unitid\r\n" + 
					"left join TbAccessoriesItem fi\r\n" + 
					"on far.cItemId = fi.id\r\n" + 
					"left join tbColors ic\r\n" + 
					"on far.colorId = ic.ColorId\r\n" + 
					"left join tbColors fc\r\n" + 
					"on far.itemColorId = fc.ColorId\r\n" + 
					"where transactionId = '"+issueTransactionId+"' and transactionType='"+StoreTransaction.FABRICS_ISSUE.getType()+"'";	
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				tempSize = new AccessoriesSize(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString(),element[7].toString(), element[8].toString(),element[9].toString(), element[10].toString(), element[11].toString(), element[12].toString(), element[13].toString(), element[14].toString(), element[15].toString(),element[16].toString(),0.0, Double.valueOf(element[17].toString()), element[18].toString(), element[19].toString(),1);
				tempSize.setBalanceQty(Double.valueOf(element[21].toString()));
				tempSize.setPreviousReceiveQty(Double.valueOf(element[22].toString()));
				tempSize.setReturnQty(Double.valueOf(element[23].toString()));
				tempSize.setIssueQty(Double.valueOf(element[24].toString()));
				
				accessoriesSizeList.add(tempSize);				
			}
			sql = "select fii.AutoId,fii.transactionId,(select convert(varchar,fii.date,103))as issuedDate,fii.issuedTo,fii.receiveBy,fii.remarks,fii.createBy,di.DepartmentName,fi.FactoryName\r\n" + 
					"from tbAccessoriesIssueInfo fii\r\n" + 
					"left join TbDepartmentInfo di\r\n" + 
					"on fii.issuedTo = di.DepartmentId\r\n" + 
					"left join TbFactoryInfo fi\r\n" + 
					"on di.FactoryId = fi.FactoryId\r\n" + 
					"where fii.transactionId = '"+issueTransactionId+"'\r\n" + 
					"";		
			list = session.createSQLQuery(sql).list();
			if(list.size()>0)
			{	
				Object[] element = (Object[]) list.get(0);

				accessoriesIssue = new AccessoriesIssue(element[0].toString(), element[1].toString(), element[2].toString(),  element[3].toString(),  element[4].toString(),  element[5].toString(), element[6].toString());
				accessoriesIssue.setAccessoriesSizeList(accessoriesSizeList);
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
		return accessoriesIssue;
	}

	
	
	@Override
	public boolean submitAccessoriesIssueReturn(AccessoriesIssueReturn accessoriesIssueReturn) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		StoreTransaction transaction = StoreTransaction.FABRICS_ISSUE_RETURN;
		ItemType itemType = ItemType.FABRICS;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select (isnull(max(transactionId),0)+1) as maxId from tbAccessoriesIssueReturnInfo";
			List<?> list = session.createSQLQuery(sql).list();
			String transactionId="0";
			if(list.size()>0) {
				transactionId = list.get(0).toString();
			}

			sql="insert into tbAccessoriesIssueReturnInfo (transactionId"
					+ ",date"
					+ ",issueReturnFrom"
					+ ",receiveFrom"
					+ ",remarks"
					+ ",departmentId"
					+ ",entryTime"
					+ ",createBy) values("
					+ "'"+transactionId+"'"
					+ ",'"+accessoriesIssueReturn.getIssueReturnDate()+"'"
					+ ",'"+accessoriesIssueReturn.getIssueReturnFrom()+"'"
					+ ",'"+accessoriesIssueReturn.getReceiveFrom()+"'"
					+ ",'"+accessoriesIssueReturn.getRemarks()+"'"
					+ ",'"+accessoriesIssueReturn.getDepartmentId()+"',"
					+ "CURRENT_TIMESTAMP,'"+accessoriesIssueReturn.getUserId()+"');";
			session.createSQLQuery(sql).executeUpdate();
			int departmentId = 1;
			for (AccessoriesSize roll : accessoriesIssueReturn.getAccessoriesSizeList()) {
				sql="insert into tbAccessoriesAccessoriesTransaction (purchaseOrder,styleId,styleItemId,colorId,itemColorId,transactionId,transactionType,itemType,rollId,unitId,unitQty,qty,dItemId,cItemId,departmentId,rackName,binName,entryTime,userId) \r\n" + 
						"values('"+roll.getPurchaseOrder()+"','"+roll.getStyleId()+"','"+roll.getItemId()+"','"+roll.getItemColorId()+"','"+roll.getAccessoriesColorId()+"','"+transactionId+"','"+transaction.getType()+"','"+itemType.getType()+"','"+roll.getSizeId()+"','"+roll.getUnitId()+"','"+roll.getUnitQty()+"','"+roll.getUnitQty()+"','"+roll.getAccessoriesId()+"','0','"+departmentId+"','"+roll.getRackName()+"','"+roll.getBinName()+"',CURRENT_TIMESTAMP,'"+accessoriesIssueReturn.getUserId()+"');";		
				session.createSQLQuery(sql).executeUpdate();
			}

			tx.commit();

			return true;
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

		return false;
	}

	@Override
	public boolean editAccessoriesIssueReturn(AccessoriesIssueReturn accessoriesIssueReturn) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		StoreTransaction transaction = StoreTransaction.FABRICS_ISSUE_RETURN;
		ItemType itemType = ItemType.FABRICS;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="update tbaccessoriesIssueReturnInfo set "
					+ "date = '"+accessoriesIssueReturn.getIssueReturnDate()+"'"
					+ ",issueReturnFrom = '"+accessoriesIssueReturn.getIssueReturnFrom()+"'"
					+ ",receiveFrom = '"+accessoriesIssueReturn.getReceiveFrom()+"'"
					+ ",remarks = '"+accessoriesIssueReturn.getRemarks()+"'"
					+ ",entryTime = CURRENT_TIMESTAMP"
					+ ",createBy = '"+accessoriesIssueReturn.getUserId()+"' where transactionId='"+accessoriesIssueReturn.getTransactionId()+"';";

			session.createSQLQuery(sql).executeUpdate();

			int departmentId = 1;
			if(accessoriesIssueReturn.getAccessoriesSizeList() != null) {
				for (AccessoriesSize roll : accessoriesIssueReturn.getAccessoriesSizeList()) {
					sql="insert into tbAccessoriesAccessoriesTransaction (purchaseOrder,styleId,styleItemId,colorId,itemColorId,transactionId,transactionType,itemType,rollId,unitId,unitQty,qty,dItemId,cItemId,departmentId,rackName,binName,entryTime,userId) \r\n" + 
							"values('"+roll.getPurchaseOrder()+"','"+roll.getStyleId()+"','"+roll.getItemId()+"','"+roll.getItemColorId()+"','"+roll.getAccessoriesColorId()+"','"+accessoriesIssueReturn.getTransactionId()+"','"+transaction.getType()+"','"+itemType.getType()+"','"+roll.getSizeId()+"','"+roll.getUnitId()+"','"+roll.getUnitQty()+"','"+roll.getUnitQty()+"','"+roll.getAccessoriesId()+"','0','"+departmentId+"','"+roll.getRackName()+"','"+roll.getBinName()+"',CURRENT_TIMESTAMP,'"+accessoriesIssueReturn.getUserId()+"');";		
					session.createSQLQuery(sql).executeUpdate();
				}
			}
			

			tx.commit();

			return true;
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

		return false;
	}

	@Override
	public String editIssueReturndSizeInTransaction(AccessoriesSize accessoriesSize) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		try{
			tx=session.getTransaction();
			tx.begin();
			String sql = "select fat.transactionId,dbo.accessoriesIssuedQty(firi.departmentId,firi.issueReturnFrom,fat.rollId) as issuedQty,dbo.accessoriesIssueReturnedQty(firi.departmentId,firi.issueReturnFrom,fat.rollId) as returnedQty from tbAccessoriesAccessoriesTransaction fat\r\n" + 
					"left join tbaccessoriesIssueReturnInfo firi\r\n" + 
					"on fat.transactionId = firi.transactionId where fat.autoId='"+accessoriesSize.getAutoId()+"' and (dbo.accessoriesIssuedQty(firi.departmentId,firi.issueReturnFrom,fat.rollId)-dbo.accessoriesIssueReturnedQty(firi.departmentId,firi.issueReturnFrom,fat.rollId)-"+accessoriesSize.getUnitQty()+")>=0";		
			List<?> list = session.createSQLQuery(sql).list();
			if(list.size()>0) {
				Object[] element = (Object[]) list.get(0);
				if(Double.valueOf(element[1].toString())>=accessoriesSize.getUnitQty()) {
					sql = "update tbAccessoriesAccessoriesTransaction set unitQty = '"+accessoriesSize.getUnitQty()+"',qty = '"+accessoriesSize.getUnitQty()+"',rackName='"+accessoriesSize.getRackName()+"',binName='"+accessoriesSize.getBinName()+"' where autoId = '"+accessoriesSize.getAutoId()+"'";
					if(session.createSQLQuery(sql).executeUpdate()==1) {
						tx.commit();
						return "Successful";
					}
				}else {
					tx.commit();
					return "Return Qty Exist";
				}
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

		return "Something Wrong";
	}

	@Override
	public String deleteIssueReturndSizeFromTransaction(AccessoriesSize accessoriesSize) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql = "delete from tbAccessoriesAccessoriesTransaction where autoId = '"+accessoriesSize.getAutoId()+"'";
			if(session.createSQLQuery(sql).executeUpdate()==1) {
				tx.commit();
				return "Successful";
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

		return "Something Wrong";
	}

	@Override
	public List<AccessoriesIssueReturn> getAccessoriesIssueReturnList() {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		AccessoriesIssueReturn tempIssueReturn;
		List<AccessoriesIssueReturn> datalist=new ArrayList<AccessoriesIssueReturn>();	
		try{	
			tx=session.getTransaction();
			tx.begin();		
			String sql = "select firi.AutoId,firi.transactionId,(select convert(varchar,firi.date,103))as issuedDate,firi.issueReturnFrom,firi.receiveFrom,firi.remarks,firi.createBy,di.DepartmentName,fi.FactoryName\r\n" + 
					"from tbAccessoriesIssueReturnInfo firi\r\n" + 
					"left join TbDepartmentInfo di\r\n" + 
					"on firi.issueReturnFrom = di.DepartmentId\r\n" + 
					"left join TbFactoryInfo fi\r\n" + 
					"on di.FactoryId = fi.FactoryId";		
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				tempIssueReturn = new AccessoriesIssueReturn(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString());
				tempIssueReturn.setIssueReturnDepartmentName(element[7].toString()+"("+element[8].toString()+")");
				datalist.add(tempIssueReturn);				
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
		return datalist;
	}

	@Override
	public AccessoriesIssueReturn getAccessoriesIssueReturnInfo(String issueReturnTransectionId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		AccessoriesIssueReturn accessoriesIssueReturn = null;
		AccessoriesSize tempSize;
		List<AccessoriesSize> accessoriesSizeList = new ArrayList<AccessoriesSize>();	
		String departmentId = "1";
		try{	
			tx=session.getTransaction();
			tx.begin();		
			String sql = "select far.autoId,far.transactionId,far.purchaseOrder,far.styleId,sc.styleNo,far.styleItemId,id.itemname,far.colorId as itemColorId ,ic.Colorname as itemColorName,cItemId as accessoriesId,fi.ItemName as accessoriesName,far.itemColorId as accessoriesColorId,fc.Colorname as accessoriesColorName,far.rollId,fri.supplierSizeId,far.unitId,u.unitname,unitQty,rackName,BinName,far.userId,\r\n" + 
					"dbo.accessoriesissuedQty(firi.departmentId,firi.issueReturnFrom,far.rollId) as issuedQty,\r\n" + 
					"dbo.accessoriesIssueReturnedQty(firi.departmentId,firi.issueReturnFrom,far.rollId) as returnedQty\r\n" + 
					"from tbAccessoriesAccessoriesTransaction far\r\n" + 
					"left join tbAccessoriesIssueReturnInfo firi\r\n" + 
					"on far.transactionId = firi.transactionId\r\n" + 
					"left join TbStyleCreate sc\r\n" + 
					"on far.styleId = sc.StyleId\r\n" + 
					"left join tbItemDescription id\r\n" + 
					"on far.styleItemId = id.itemid\r\n" + 
					"left join tbaccessoriesSizeInfo fri\r\n" + 
					"on far.rollId = fri.rollId \r\n" + 
					"left join tbunits u\r\n" + 
					"on far.unitId = u.Unitid\r\n" + 
					"left join TbAccessoriesItem fi\r\n" + 
					"on far.dItemId = fi.id\r\n" + 
					"left join tbColors ic\r\n" + 
					"on far.colorId = ic.ColorId\r\n" + 
					"left join tbColors fc\r\n" + 
					"on far.itemColorId = fc.ColorId\r\n" + 
					"where far.transactionId = '"+issueReturnTransectionId+"' and far.transactionType='"+StoreTransaction.FABRICS_ISSUE_RETURN.getType()+"'";	
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				tempSize = new AccessoriesSize(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString(),element[7].toString(), element[8].toString(),element[9].toString(), element[10].toString(), element[11].toString(), element[12].toString(), element[13].toString(), element[14].toString(), element[15].toString(),element[16].toString(),0.0, Double.valueOf(element[17].toString()), element[18].toString(), element[19].toString(),1);
				tempSize.setIssueQty(Double.valueOf(element[21].toString()));
				tempSize.setPreviousReturnQty(Double.valueOf(element[22].toString()));
				accessoriesSizeList.add(tempSize);				
			}
			sql = "select fii.AutoId,fii.transactionId,(select convert(varchar,fii.date,103))as issuedDate,fii.issueReturnFrom,fii.receiveFrom,fii.remarks,fii.createBy,di.DepartmentName,fi.FactoryName\r\n" + 
					"from tbAccessoriesIssueReturnInfo fii\r\n" + 
					"left join TbDepartmentInfo di\r\n" + 
					"on fii.issueReturnFrom = di.DepartmentId\r\n" + 
					"left join TbFactoryInfo fi\r\n" + 
					"on di.FactoryId = fi.FactoryId\r\n" + 
					"where fii.transactionId = '"+issueReturnTransectionId+"'\r\n" + 
					"";		
			list = session.createSQLQuery(sql).list();
			if(list.size()>0)
			{	
				Object[] element = (Object[]) list.get(0);

				accessoriesIssueReturn = new AccessoriesIssueReturn(element[0].toString(), element[1].toString(), element[2].toString(),  element[3].toString(),  element[4].toString(),  element[5].toString(), element[6].toString());
				accessoriesIssueReturn.setAccessoriesSizeList(accessoriesSizeList);
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
		return accessoriesIssueReturn;
	}

	@Override
	public boolean submitAccessoriesTransferOut(AccessoriesTransferOut accessoriesTransferOut) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		StoreTransaction transaction = StoreTransaction.FABRICS_TRANSFER_OUT;
		ItemType itemType = ItemType.FABRICS;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select (isnull(max(transactionId),0)+1) as maxId from tbAccessoriesTransferOutInfo";
			List<?> list = session.createSQLQuery(sql).list();
			String transactionId="0";
			if(list.size()>0) {
				transactionId = list.get(0).toString();
			}

			sql="insert into tbAccessoriesTransferOutInfo (transactionId"
					+ ",date"
					+ ",transferTo"
					+ ",receiveBy"
					+ ",remarks"
					+ ",departmentId"
					+ ",entryTime"
					+ ",createBy) values("
					+ "'"+transactionId+"'"
					+ ",'"+accessoriesTransferOut.getTransferDate()+"'"
					+ ",'"+accessoriesTransferOut.getTransferTo()+"'"
					+ ",'"+accessoriesTransferOut.getReceiveBy()+"'"
					+ ",'"+accessoriesTransferOut.getRemarks()+"'"
					+ ",'"+accessoriesTransferOut.getDepartmentId()+"',"
					+ "CURRENT_TIMESTAMP,'"+accessoriesTransferOut.getUserId()+"');";
			session.createSQLQuery(sql).executeUpdate();
			int departmentId = 1;
			for (AccessoriesSize roll : accessoriesTransferOut.getAccessoriesSizeList()) {
				sql="insert into tbAccessoriesAccessoriesTransaction (purchaseOrder,styleId,styleItemId,colorId,itemColorId,transactionId,transactionType,itemType,rollId,unitId,unitQty,qty,dItemId,cItemId,departmentId,rackName,binName,entryTime,userId) \r\n" + 
						"values('"+roll.getPurchaseOrder()+"','"+roll.getStyleId()+"','"+roll.getItemId()+"','"+roll.getItemColorId()+"','"+roll.getAccessoriesColorId()+"','"+transactionId+"','"+transaction.getType()+"','"+itemType.getType()+"','"+roll.getSizeId()+"','"+roll.getUnitId()+"','"+roll.getUnitQty()+"','"+roll.getUnitQty()+"','0','"+roll.getAccessoriesId()+"','"+departmentId+"','"+roll.getRackName()+"','"+roll.getBinName()+"',CURRENT_TIMESTAMP,'"+accessoriesTransferOut.getUserId()+"');";		
				session.createSQLQuery(sql).executeUpdate();
			}

			tx.commit();

			return true;
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

		return false;
	}

	@Override
	public boolean editAccessoriesTransferOut(AccessoriesTransferOut accessoriesTransferOut) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		StoreTransaction transaction = StoreTransaction.FABRICS_TRANSFER_OUT;
		ItemType itemType = ItemType.FABRICS;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="update tbaccessoriesTransferOutInfo set "
					+ "date = '"+accessoriesTransferOut.getTransferDate()+"'"
					+ ",transferTo = '"+accessoriesTransferOut.getTransferTo()+"'"
					+ ",receiveBy = '"+accessoriesTransferOut.getReceiveBy()+"'"
					+ ",remarks = '"+accessoriesTransferOut.getRemarks()+"'"
					+ ",entryTime = CURRENT_TIMESTAMP"
					+ ",createBy = '"+accessoriesTransferOut.getUserId()+"' where transactionId='"+accessoriesTransferOut.getTransactionId()+"';";

			session.createSQLQuery(sql).executeUpdate();

			int departmentId = 1;
			if(accessoriesTransferOut.getAccessoriesSizeList() != null) {
				for (AccessoriesSize roll : accessoriesTransferOut.getAccessoriesSizeList()) {
					sql="insert into tbAccessoriesAccessoriesTransaction (purchaseOrder,styleId,styleItemId,colorId,itemColorId,transactionId,transactionType,itemType,rollId,unitId,unitQty,qty,dItemId,cItemId,departmentId,rackName,binName,entryTime,userId) \r\n" + 
							"values('"+roll.getPurchaseOrder()+"','"+roll.getStyleId()+"','"+roll.getItemId()+"','"+roll.getItemColorId()+"','"+roll.getAccessoriesColorId()+"','"+accessoriesTransferOut.getTransactionId()+"','"+transaction.getType()+"','"+itemType.getType()+"','"+roll.getSizeId()+"','"+roll.getUnitId()+"','"+roll.getUnitQty()+"','"+roll.getUnitQty()+"','0','"+roll.getAccessoriesId()+"','"+departmentId+"','"+roll.getRackName()+"','"+roll.getBinName()+"',CURRENT_TIMESTAMP,'"+accessoriesTransferOut.getUserId()+"');";		
					session.createSQLQuery(sql).executeUpdate();
				}
			}
			

			tx.commit();

			return true;
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

		return false;
	}

	@Override
	public String editTransferOutdSizeInTransaction(AccessoriesSize accessoriesSize) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		try{
			tx=session.getTransaction();
			tx.begin();
			String sql = "select far.autoId,dbo.accessoriesBalanceQtyExceptAutoId(far.purchaseOrder,far.styleId,far.styleItemId,far.colorId,far.cItemId,far.itemColorId,far.rollId,far.departmentId,far.autoId) as balanceQty \r\n" + 
					"from tbAccessoriesAccessoriesTransaction far\r\n" + 
					"where far.autoId = '"+accessoriesSize.getAutoId()+"'";		
			List<?> list = session.createSQLQuery(sql).list();
			if(list.size()>0) {
				Object[] element = (Object[]) list.get(0);
				if(Double.valueOf(element[1].toString())>=accessoriesSize.getUnitQty()) {
					sql = "update tbAccessoriesAccessoriesTransaction set unitQty = '"+accessoriesSize.getUnitQty()+"',qty = '"+accessoriesSize.getUnitQty()+"' where autoId = '"+accessoriesSize.getAutoId()+"'";
					if(session.createSQLQuery(sql).executeUpdate()==1) {
						tx.commit();
						return "Successful";
					}
				}else {
					tx.commit();
					return "Return Qty Exist";
				}
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

		return "Something Wrong";
	}

	@Override
	public String deleteTransferOutdSizeFromTransaction(AccessoriesSize accessoriesSize) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql = "delete from tbAccessoriesAccessoriesTransaction where autoId = '"+accessoriesSize.getAutoId()+"'";
			if(session.createSQLQuery(sql).executeUpdate()==1) {
				tx.commit();
				return "Successful";
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

		return "Something Wrong";
	}

	@Override
	public List<AccessoriesTransferOut> getAccessoriesTransferOutList() {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		AccessoriesTransferOut tempIssue;
		List<AccessoriesTransferOut> datalist=new ArrayList<AccessoriesTransferOut>();	
		try{	
			tx=session.getTransaction();
			tx.begin();		
			String sql = "select fii.AutoId,fii.transactionId,(select convert(varchar,fii.date,103))as issuedDate,fii.transferTo,fii.receiveBy,fii.remarks,fii.createBy,di.DepartmentName,fi.FactoryName\r\n" + 
					"from tbAccessoriesTransferOutInfo fii\r\n" + 
					"left join TbDepartmentInfo di\r\n" + 
					"on fii.transferTo = di.DepartmentId\r\n" + 
					"left join TbFactoryInfo fi\r\n" + 
					"on di.FactoryId = fi.FactoryId";		
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				tempIssue = new AccessoriesTransferOut(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString());
				tempIssue.setTransferDepartmentName(element[7].toString()+"("+element[8].toString()+")");
				datalist.add(tempIssue);				
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
		return datalist;
	}

	@Override
	public AccessoriesTransferOut getAccessoriesTransferOutInfo(String transferOutTransectionId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		AccessoriesTransferOut accessoriesTransfer = null;
		AccessoriesSize tempSize;
		List<AccessoriesSize> accessoriesSizeList = new ArrayList<AccessoriesSize>();	
		String departmentId = "1";
		try{	
			tx=session.getTransaction();
			tx.begin();		
			String sql = "select far.autoId,far.transactionId,far.purchaseOrder,far.styleId,sc.styleNo,far.styleItemId,id.itemname,far.colorId as itemColorId ,ic.Colorname as itemColorName,cItemId as accessoriesId,fi.ItemName as accessoriesName,far.itemColorId as accessoriesColorId,fc.Colorname as accessoriesColorName,far.rollId,fri.supplierSizeId,far.unitId,u.unitname,unitQty,rackName,BinName,far.userId\r\n" + 
					",dbo.accessoriesBalanceQty(far.purchaseOrder,far.styleId,far.styleItemId,far.colorId,far.cItemId,far.itemColorId,far.rollId,far.departmentId) as balanceQty \r\n" +
					",(select isnull(sum(qty),0) from tbAccessoriesAccessoriesTransaction t where far.purchaseOrder=t.purchaseOrder and far.styleId = t.styleId and far.styleItemId= t.styleItemId and far.colorId = t.colorId and far.cItemId = t.dItemId and far.itemColorId = t.itemColorId and t.transactionType = '"+StoreTransaction.FABRICS_RECEIVE.getType()+"' and t.departmentId = '"+departmentId+"') as previousReceiveQty,\r\n" + 
					"(select isnull(sum(qty),0) from tbAccessoriesAccessoriesTransaction t where far.purchaseOrder=t.purchaseOrder and far.styleId = t.styleId and far.styleItemId= t.styleItemId and far.colorId = t.colorId and far.cItemId = t.cItemId and far.itemColorId = t.itemColorId and t.transactionType = '"+StoreTransaction.FABRICS_RETURN.getType()+"' and t.departmentId = '"+departmentId+"') as returnQty,\r\n" + 
					"(select isnull(sum(qty),0) from tbAccessoriesAccessoriesTransaction t where far.purchaseOrder=t.purchaseOrder and far.styleId = t.styleId and far.styleItemId= t.styleItemId and far.colorId = t.colorId and far.cItemId = t.cItemId and far.itemColorId = t.itemColorId and t.transactionType = '"+StoreTransaction.FABRICS_ISSUE.getType()+"' and t.departmentId = '"+departmentId+"') as issueQty\r\n" + 
					"from tbAccessoriesAccessoriesTransaction far\r\n" + 
					"left join TbStyleCreate sc\r\n" + 
					"on far.styleId = sc.StyleId\r\n" + 
					"left join tbItemDescription id\r\n" + 
					"on far.styleItemId = id.itemid\r\n"
					+ "left join tbaccessoriesSizeInfo fri\r\n" + 
					"on far.rollId = fri.rollId \r\n" + 
					"left join tbunits u\r\n" + 
					"on far.unitId = u.Unitid\r\n" + 
					"left join TbAccessoriesItem fi\r\n" + 
					"on far.cItemId = fi.id\r\n" + 
					"left join tbColors ic\r\n" + 
					"on far.colorId = ic.ColorId\r\n" + 
					"left join tbColors fc\r\n" + 
					"on far.itemColorId = fc.ColorId\r\n" + 
					"where transactionId = '"+transferOutTransectionId+"' and transactionType='"+StoreTransaction.FABRICS_TRANSFER_OUT.getType()+"'";	
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				tempSize = new AccessoriesSize(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString(),element[7].toString(), element[8].toString(),element[9].toString(), element[10].toString(), element[11].toString(), element[12].toString(), element[13].toString(), element[14].toString(), element[15].toString(),element[16].toString(),0.0, Double.valueOf(element[17].toString()), element[18].toString(), element[19].toString(),1);
				tempSize.setBalanceQty(Double.valueOf(element[21].toString()));
				tempSize.setPreviousReceiveQty(Double.valueOf(element[22].toString()));
				tempSize.setReturnQty(Double.valueOf(element[23].toString()));
				tempSize.setIssueQty(Double.valueOf(element[24].toString()));
				
				accessoriesSizeList.add(tempSize);				
			}
			sql = "select fii.AutoId,fii.transactionId,(select convert(varchar,fii.date,103))as issuedDate,fii.transferTo,fii.receiveBy,fii.remarks,fii.createBy,di.DepartmentName,fi.FactoryName\r\n" + 
					"from tbAccessoriesTransferOutInfo fii\r\n" + 
					"left join TbDepartmentInfo di\r\n" + 
					"on fii.transferTo = di.DepartmentId\r\n" + 
					"left join TbFactoryInfo fi\r\n" + 
					"on di.FactoryId = fi.FactoryId\r\n" + 
					"where fii.transactionId = '"+transferOutTransectionId+"'\r\n" + 
					"";		
			list = session.createSQLQuery(sql).list();
			if(list.size()>0)
			{	
				Object[] element = (Object[]) list.get(0);

				accessoriesTransfer = new AccessoriesTransferOut(element[0].toString(), element[1].toString(), element[2].toString(),  element[3].toString(),  element[4].toString(),  element[5].toString(), element[6].toString());
				accessoriesTransfer.setAccessoriesSizeList(accessoriesSizeList);
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
		return accessoriesTransfer;
	}
	
	
	@Override
	public boolean submitAccessoriesTransferIn(AccessoriesTransferIn accessoriesTransferIn) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		StoreTransaction transaction = StoreTransaction.FABRICS_TRANSFER_IN;
		ItemType itemType = ItemType.FABRICS;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select (isnull(max(transactionId),0)+1) as maxId from tbAccessoriesTransferInInfo";
			List<?> list = session.createSQLQuery(sql).list();
			String transactionId="0";
			if(list.size()>0) {
				transactionId = list.get(0).toString();
			}

			sql="insert into tbAccessoriesTransferInInfo (transactionId"
					+ ",date"
					+ ",transferFrom"
					+ ",receiveFrom"
					+ ",remarks"
					+ ",departmentId"
					+ ",entryTime"
					+ ",createBy) values("
					+ "'"+transactionId+"'"
					+ ",'"+accessoriesTransferIn.getTransferDate()+"'"
					+ ",'"+accessoriesTransferIn.getTransferFrom()+"'"
					+ ",'"+accessoriesTransferIn.getReceiveFrom()+"'"
					+ ",'"+accessoriesTransferIn.getRemarks()+"'"
					+ ",'"+accessoriesTransferIn.getDepartmentId()+"',"
					+ "CURRENT_TIMESTAMP,'"+accessoriesTransferIn.getUserId()+"');";
			session.createSQLQuery(sql).executeUpdate();
			int departmentId = 1;
			for (AccessoriesSize roll : accessoriesTransferIn.getAccessoriesSizeList()) {
				sql="insert into tbAccessoriesAccessoriesTransaction (purchaseOrder,styleId,styleItemId,colorId,itemColorId,transactionId,transactionType,itemType,rollId,unitId,unitQty,qty,dItemId,cItemId,departmentId,rackName,binName,entryTime,userId) \r\n" + 
						"values('"+roll.getPurchaseOrder()+"','"+roll.getStyleId()+"','"+roll.getItemId()+"','"+roll.getItemColorId()+"','"+roll.getAccessoriesColorId()+"','"+transactionId+"','"+transaction.getType()+"','"+itemType.getType()+"','"+roll.getSizeId()+"','"+roll.getUnitId()+"','"+roll.getUnitQty()+"','"+roll.getUnitQty()+"','"+roll.getAccessoriesId()+"','0','"+departmentId+"','"+roll.getRackName()+"','"+roll.getBinName()+"',CURRENT_TIMESTAMP,'"+accessoriesTransferIn.getUserId()+"');";		
				session.createSQLQuery(sql).executeUpdate();
			}

			tx.commit();

			return true;
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

		return false;
	}

	@Override
	public boolean editAccessoriesTransferIn(AccessoriesTransferIn accessoriesTransferIn) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		StoreTransaction transaction = StoreTransaction.FABRICS_TRANSFER_IN;
		ItemType itemType = ItemType.FABRICS;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="update tbaccessoriesTransferInInfo set "
					+ "date = '"+accessoriesTransferIn.getTransferDate()+"'"
					+ ",transferFrom = '"+accessoriesTransferIn.getTransferFrom()+"'"
					+ ",receiveFrom = '"+accessoriesTransferIn.getReceiveFrom()+"'"
					+ ",remarks = '"+accessoriesTransferIn.getRemarks()+"'"
					+ ",entryTime = CURRENT_TIMESTAMP"
					+ ",createBy = '"+accessoriesTransferIn.getUserId()+"' where transactionId='"+accessoriesTransferIn.getTransactionId()+"';";

			session.createSQLQuery(sql).executeUpdate();

			int departmentId = 1;
			if(accessoriesTransferIn.getAccessoriesSizeList() != null) {
				for (AccessoriesSize roll : accessoriesTransferIn.getAccessoriesSizeList()) {
					sql="insert into tbAccessoriesAccessoriesTransaction (purchaseOrder,styleId,styleItemId,colorId,itemColorId,transactionId,transactionType,itemType,rollId,unitId,unitQty,qty,dItemId,cItemId,departmentId,rackName,binName,entryTime,userId) \r\n" + 
							"values('"+roll.getPurchaseOrder()+"','"+roll.getStyleId()+"','"+roll.getItemId()+"','"+roll.getItemColorId()+"','"+roll.getAccessoriesColorId()+"','"+accessoriesTransferIn.getTransactionId()+"','"+transaction.getType()+"','"+itemType.getType()+"','"+roll.getSizeId()+"','"+roll.getUnitId()+"','"+roll.getUnitQty()+"','"+roll.getUnitQty()+"','"+roll.getAccessoriesId()+"','0','"+departmentId+"','"+roll.getRackName()+"','"+roll.getBinName()+"',CURRENT_TIMESTAMP,'"+accessoriesTransferIn.getUserId()+"');";		
					session.createSQLQuery(sql).executeUpdate();
				}
			}
			

			tx.commit();

			return true;
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

		return false;
	}

	@Override
	public String editTransferIndSizeInTransaction(AccessoriesSize accessoriesSize) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		try{
			tx=session.getTransaction();
			tx.begin();
			String sql = "select far.autoId,dbo.accessoriesBalanceQtyExceptAutoId(far.purchaseOrder,far.styleId,far.styleItemId,far.colorId,far.cItemId,far.itemColorId,far.rollId,far.departmentId,far.autoId) as balanceQty \r\n" + 
					"from tbAccessoriesAccessoriesTransaction far\r\n" + 
					"where far.autoId = '"+accessoriesSize.getAutoId()+"'";		
			List<?> list = session.createSQLQuery(sql).list();
			if(list.size()>0) {
				Object[] element = (Object[]) list.get(0);
				if(Double.valueOf(element[1].toString())>=accessoriesSize.getUnitQty()) {
					sql = "update tbAccessoriesAccessoriesTransaction set unitQty = '"+accessoriesSize.getUnitQty()+"',qty = '"+accessoriesSize.getUnitQty()+"' where autoId = '"+accessoriesSize.getAutoId()+"'";
					if(session.createSQLQuery(sql).executeUpdate()==1) {
						tx.commit();
						return "Successful";
					}
				}else {
					tx.commit();
					return "Return Qty Exist";
				}
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

		return "Something Wrong";
	}

	@Override
	public String deleteTransferIndSizeFromTransaction(AccessoriesSize accessoriesSize) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql = "delete from tbAccessoriesAccessoriesTransaction where autoId = '"+accessoriesSize.getAutoId()+"'";
			if(session.createSQLQuery(sql).executeUpdate()==1) {
				tx.commit();
				return "Successful";
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

		return "Something Wrong";
	}

	@Override
	public List<AccessoriesTransferIn> getAccessoriesTransferInList() {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		AccessoriesTransferIn tempIssue;
		List<AccessoriesTransferIn> datalist=new ArrayList<AccessoriesTransferIn>();	
		try{	
			tx=session.getTransaction();
			tx.begin();		
			String sql = "select fii.AutoId,fii.transactionId,(select convert(varchar,fii.date,103))as issuedDate,fii.transferFrom,fii.receiveFrom,fii.remarks,fii.createBy,di.DepartmentName,fi.FactoryName\r\n" + 
					"from tbAccessoriesTransferInInfo fii\r\n" + 
					"left join TbDepartmentInfo di\r\n" + 
					"on fii.transferFrom = di.DepartmentId\r\n" + 
					"left join TbFactoryInfo fi\r\n" + 
					"on di.FactoryId = fi.FactoryId";		
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				tempIssue = new AccessoriesTransferIn(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString());
				tempIssue.setTransferDepartmentName(element[7].toString()+"("+element[8].toString()+")");
				datalist.add(tempIssue);				
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
		return datalist;
	}

	@Override
	public AccessoriesTransferIn getAccessoriesTransferInInfo(String transferOutTransectionId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		AccessoriesTransferIn accessoriesTransfer = null;
		AccessoriesSize tempSize;
		List<AccessoriesSize> accessoriesSizeList = new ArrayList<AccessoriesSize>();	
		String departmentId = "1";
		try{	
			tx=session.getTransaction();
			tx.begin();		
			String sql = "select far.autoId,far.transactionId,far.purchaseOrder,far.styleId,sc.styleNo,far.styleItemId,id.itemname,far.colorId as itemColorId ,ic.Colorname as itemColorName,dItemId as accessoriesId,fi.ItemName as accessoriesName,far.itemColorId as accessoriesColorId,fc.Colorname as accessoriesColorName,far.rollId,fri.supplierSizeId,far.unitId,u.unitname,unitQty,rackName,BinName,far.userId\r\n" + 
					",dbo.accessoriesBalanceQty(far.purchaseOrder,far.styleId,far.styleItemId,far.colorId,far.cItemId,far.itemColorId,far.rollId,far.departmentId) as balanceQty \r\n" +
					",(select isnull(sum(qty),0) from tbAccessoriesAccessoriesTransaction t where far.purchaseOrder=t.purchaseOrder and far.styleId = t.styleId and far.styleItemId= t.styleItemId and far.colorId = t.colorId and far.cItemId = t.dItemId and far.itemColorId = t.itemColorId and t.transactionType = '"+StoreTransaction.FABRICS_RECEIVE.getType()+"' and t.departmentId = '"+departmentId+"') as previousReceiveQty,\r\n" + 
					"(select isnull(sum(qty),0) from tbAccessoriesAccessoriesTransaction t where far.purchaseOrder=t.purchaseOrder and far.styleId = t.styleId and far.styleItemId= t.styleItemId and far.colorId = t.colorId and far.cItemId = t.cItemId and far.itemColorId = t.itemColorId and t.transactionType = '"+StoreTransaction.FABRICS_RETURN.getType()+"' and t.departmentId = '"+departmentId+"') as returnQty,\r\n" + 
					"(select isnull(sum(qty),0) from tbAccessoriesAccessoriesTransaction t where far.purchaseOrder=t.purchaseOrder and far.styleId = t.styleId and far.styleItemId= t.styleItemId and far.colorId = t.colorId and far.cItemId = t.cItemId and far.itemColorId = t.itemColorId and t.transactionType = '"+StoreTransaction.FABRICS_ISSUE.getType()+"' and t.departmentId = '"+departmentId+"') as issueQty\r\n" + 
					"from tbAccessoriesAccessoriesTransaction far\r\n" + 
					"left join TbStyleCreate sc\r\n" + 
					"on far.styleId = sc.StyleId\r\n" + 
					"left join tbItemDescription id\r\n" + 
					"on far.styleItemId = id.itemid\r\n"
					+ "left join tbaccessoriesSizeInfo fri\r\n" + 
					"on far.rollId = fri.rollId \r\n" + 
					"left join tbunits u\r\n" + 
					"on far.unitId = u.Unitid\r\n" + 
					"left join TbAccessoriesItem fi\r\n" + 
					"on far.dItemId = fi.id\r\n" + 
					"left join tbColors ic\r\n" + 
					"on far.colorId = ic.ColorId\r\n" + 
					"left join tbColors fc\r\n" + 
					"on far.itemColorId = fc.ColorId\r\n" + 
					"where transactionId = '"+transferOutTransectionId+"' and transactionType='"+StoreTransaction.FABRICS_TRANSFER_IN.getType()+"'";	
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				tempSize = new AccessoriesSize(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString(),element[7].toString(), element[8].toString(),element[9].toString(), element[10].toString(), element[11].toString(), element[12].toString(), element[13].toString(), element[14].toString(), element[15].toString(),element[16].toString(),0.0, Double.valueOf(element[17].toString()), element[18].toString(), element[19].toString(),1);
				tempSize.setBalanceQty(Double.valueOf(element[21].toString()));
				tempSize.setPreviousReceiveQty(Double.valueOf(element[22].toString()));
				tempSize.setReturnQty(Double.valueOf(element[23].toString()));
				tempSize.setIssueQty(Double.valueOf(element[24].toString()));
				
				accessoriesSizeList.add(tempSize);				
			}
			sql = "select fii.AutoId,fii.transactionId,(select convert(varchar,fii.date,103))as issuedDate,fii.transferFrom,fii.receiveFrom,fii.remarks,fii.createBy,di.DepartmentName,fi.FactoryName\r\n" + 
					"from tbAccessoriesTransferInInfo fii\r\n" + 
					"left join TbDepartmentInfo di\r\n" + 
					"on fii.transferFrom = di.DepartmentId\r\n" + 
					"left join TbFactoryInfo fi\r\n" + 
					"on di.FactoryId = fi.FactoryId\r\n" + 
					"where fii.transactionId = '"+transferOutTransectionId+"'\r\n" + 
					"";		
			list = session.createSQLQuery(sql).list();
			if(list.size()>0)
			{	
				Object[] element = (Object[]) list.get(0);

				accessoriesTransfer = new AccessoriesTransferIn(element[0].toString(), element[1].toString(), element[2].toString(),  element[3].toString(),  element[4].toString(),  element[5].toString(), element[6].toString());
				accessoriesTransfer.setAccessoriesSizeList(accessoriesSizeList);
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
		return accessoriesTransfer;
	}

	@Override
	public boolean isStoreGenralItemExist(StoreGeneralCategory v) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select ItemName from tbStoreItemInformation where ItemName='"+v.getItemName()+"' and ItemId != '"+v.getItemId()+"'";

			List<?> list = session.createSQLQuery(sql).list();
			if(list.size()>0) return true;
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
		return false;
	}



	@Override
	public boolean saveGeneralItem(StoreGeneralCategory v) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="insert into tbStoreItemInformation(ItemName,CatagoryId,UnitId,PcsBuyPrice,OpeningStock,StockLimit,Date,entrytime,UserId) values('"+v.getItemName()+"','"+v.getCategoryId()+"','"+v.getUnitId()+"','"+v.getBuyPrice()+"','"+v.getOpeningStock()+"','"+v.getStockLimit()+"',current_timestamp,current_timestamp,'"+v.getUserId()+"')";
			session.createSQLQuery(sql).executeUpdate();
			tx.commit();
			return true;
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
	public List<StoreGeneralCategory> getStoreGeneralItemList() {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<StoreGeneralCategory> datalist=new ArrayList<StoreGeneralCategory>();	
		try{	
			tx=session.getTransaction();
			tx.begin();		
			String sql = "select a.ItemId,a.ItemName,(select headTitle from tbStoreItemCatagory where headId=a.CatagoryId) as Category,a.CatagoryId,a.UnitId,a.PcsBuyPrice,a.OpeningStock,a.StockLimit from tbStoreItemInformation a order by Category";		
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				datalist.add(new StoreGeneralCategory(element[0].toString(),element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString(), element[7].toString()));				
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
		return datalist;
	}



	@Override
	public boolean editGeneralItem(StoreGeneralCategory v) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="update tbStoreItemInformation set  ItemName='"+v.getItemName()+"',CatagoryId='"+v.getCategoryId()+"',UnitId='"+v.getUnitId()+"',PcsBuyPrice='"+v.getBuyPrice()+"',OpeningStock='"+v.getOpeningStock()+"',StockLimit='"+v.getStockLimit()+"',UserId='"+v.getUserId()+"',entrytime=current_timestamp where ItemId='"+v.getItemId()+"'";
			session.createSQLQuery(sql).executeUpdate();
			tx.commit();
			return true;
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
	public boolean addGeneralReceivedItem(StoreGeneralReceived v) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();


			int exist=0;
			String sql="select ItemId from TbStoreTransectionDetails where ItemId='"+v.getItemId()+"' and InvoiceNo='"+v.getInvoiceNo()+"'";
			List<?> list = session.createSQLQuery(sql).list();
			if(list.size()>0) {
				exist=1;
			}


			if(exist==0) {
				double TotalPrice=Double.parseDouble(v.getQty())*Double.parseDouble(v.getPirce());
				sql="insert into TbStoreTransectionDetails(itemId,unit,qty,buyPrice,discount,totalPrice,type,invoiceNo,status,Date,entrytime,UserId) "
						+ "values('"+v.getItemId()+"','"+v.getUnitId()+"','"+v.getQty()+"','"+v.getPirce()+"','0','"+TotalPrice+"','"+v.getType()+"','"+v.getInvoiceNo()+"','0',current_timestamp,current_timestamp,'"+v.getUserId()+"')";
				session.createSQLQuery(sql).executeUpdate();
			}
			else {
				double TotalPrice=Double.parseDouble(v.getQty())*Double.parseDouble(v.getPirce());
				sql="update TbStoreTransectionDetails set qty=qty+'"+v.getQty()+"',totalPrice=totalPrice+'"+TotalPrice+"',entryTime=current_timestamp where invoiceNo='"+v.getInvoiceNo()+"' ";
				session.createSQLQuery(sql).executeUpdate();
			}
			tx.commit();
			return true;
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
	public String getMaxInvoiceId(String type) {
		String InvoiceId="";
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select (isnull(max(invoiceNo),0)+1) as maxId from TbStoreTransectionInvoice where Type='"+type+"'";
			List<?> list = session.createSQLQuery(sql).list();
			if(list.size()>0) {
				InvoiceId = list.get(0).toString();
			}

			tx.commit();
		}
		catch(Exception ee){

			if (tx != null) {
				tx.rollback();
			}
			ee.printStackTrace();
		}

		finally {
			session.close();
		}

		return InvoiceId;
	}



	@Override
	public List<StoreGeneralReceived> getStoreGeneralReceivedItemList(String invoiceNo, String type) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<StoreGeneralReceived> datalist=new ArrayList<StoreGeneralReceived>();	
		try{	
			tx=session.getTransaction();
			tx.begin();		
			String sql = "select a.autoId,(select ItemName from tbStoreItemInformation where ItemId=a.ItemId) as ItemName,(select UnitName from tbunits where UnitId=a.unit) as UnitName,a.Qty,a.buyPrice,a.TotalPrice from TbStoreTransectionDetails a where a.invoiceNo='"+invoiceNo+"' and a.type='"+type+"' and a.status='0'";		
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				datalist.add(new StoreGeneralReceived(element[0].toString(),element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString()));				
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
		return datalist;
	}



	@Override
	public boolean confrimtoreGeneralReceivedItemt(StoreGeneralReceived v) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="insert into TbStoreTransectionInvoice"
					+ "("
					+ "invoiceNo,"
					+ "PersionId,"
					+ "PersionName,"
					+ "ChallanNo,"
					+ "type,"
					+ "amount,"
					+ "netAmount,"
					+ "discountPer,"
					+ "discountManual,"
					+ "discount,"
					+ "paid,"
					+ "cash,"
					+ "card,"
					+ "card_type,"
					+ "p_type,"
					+ "remark,"
					+ "Date,"
					+ "entrytime,"
					+ "UserId"
					+ ") "
					+ "values('"+v.getInvoiceNo()+"',"
					+ "'"+v.getSupplierId()+"',"
					+ "'',"
					+ "'"+v.getChallanNo()+"',"
					+ "'"+v.getType()+"',"
					+ "'0',"
					+ "'0',"
					+ "'0',"
					+ "'0',"
					+ "'0',"
					+ "'0',"
					+ "'0',"
					+ "'0',"
					+ "'0',"
					+ "'',"
					+ "'',"
					+"current_timestamp,current_timestamp,'"+v.getUserId()+"')";
			session.createSQLQuery(sql).executeUpdate();
			
			
			sql="update TbStoreTransectionDetails set status='1' where invoiceNo='"+v.getInvoiceNo()+"' ";
			session.createSQLQuery(sql).executeUpdate();

			tx.commit();
			return true;
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
	public List<StoreGeneralReceived> getStoreGeneralReceivedIList(String type) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<StoreGeneralReceived> datalist=new ArrayList<StoreGeneralReceived>();	
		try{	
			tx=session.getTransaction();
			tx.begin();		
			String sql = "select InvoiceNo,(select Name from tbSupplier where id=PersionId)as SupplierName,ChallanNo,(select convert(varchar,date,103))as Date  from TbStoreTransectionInvoice where type='"+type+"'";		
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				datalist.add(new StoreGeneralReceived(element[0].toString(),element[1].toString(), element[2].toString(), element[3].toString()));				
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
		return datalist;
	}



	@Override
	public List<CuttingFabricsUsed> getCuttingUsedFabricsList(String cuttingEntryId) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<CuttingFabricsUsed> datalist=new ArrayList<CuttingFabricsUsed>();	
		try{	
			tx=session.getTransaction();
			tx.begin();		
			String sql = "select a.CuttingEntryId,a.purchaseOrder,a.StyleId,a.ItemId,s.StyleNo,i.itemname,b.ColorId,b.UsedFabrics,c.Colorname,f.fabricsid,fb.ItemName as FabricsItem,f.fabricscolor,fc.Colorname as FabricsItemColor,f.unitId,u.unitname from TbCuttingInformationSummary a join TbCuttingInformationDetails b on a.CuttingEntryId=b.CuttingEntryId join tbFabricsIndent f on f.PurchaseOrder=a.purchaseOrder and f.styleId=a.StyleId and f.itemid=a.ItemId join TbStyleCreate s on a.StyleId=s.StyleId join tbItemDescription i on a.ItemId=i.itemId join tbColors c on c.ColorId=b.ColorId join TbFabricsItem fb on f.fabricsid=fb.id join tbColors fc on fc.ColorId=f.fabricscolor join tbunits u on u.Unitid=f.unitId where a.CuttingEntryId='"+cuttingEntryId+"' and b.Type='Cutting' group by a.CuttingEntryId,b.ColorId,b.UsedFabrics,c.Colorname,a.purchaseOrder,a.StyleId,a.ItemId,s.StyleNo,i.itemname,f.fabricsid,f.unitId,u.unitname,fb.ItemName,f.fabricscolor,fc.Colorname";		
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				
				datalist.add(new CuttingFabricsUsed(element[0].toString(),element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString(), element[7].toString(), element[8].toString(), element[9].toString(), element[10].toString(),element[11].toString(), element[12].toString(),element[13].toString(),element[14].toString()));				
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
		return datalist;
	}



	@Override
	public boolean sendCuttingFabricsRequistion(CuttingFabricsUsed v) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="";
			String resultValue=v.getResultList().substring(v.getResultList().indexOf("[")+1, v.getResultList().indexOf("]"));
			System.out.println("resultValue "+resultValue);
			
			StringTokenizer token=new StringTokenizer(resultValue, ",");
			while(token.hasMoreTokens()) {
				
				String secondToken=token.nextToken();
				StringTokenizer token2=new StringTokenizer(secondToken, "*");
				while(token2.hasMoreTokens()) {
					String fabricsId=token2.nextToken();
					String colorId=token2.nextToken();
					String unitId=token2.nextToken();
					String usedFabrics=token2.nextToken();
					String requistionReq=token2.nextToken();
					
					if(!requistionReq.equals("0")){
						 sql="insert into TbCuttingRequisitionDetailsv1"
									+ "("
									+ "CuttingEntryId,"
									+ "colorId,"
									+ "fabricsId,"
									+ "unitId,"
									+ "requisitionQuantity,"
									+ "requistionFlag,"
									+ "entrytime,"
									+ "UserId"
									+ ") "
									+ "values('"+v.getCuttingEntryId()+"',"
									+ "'"+colorId+"',"
									+ "'"+fabricsId+"',"
									+ "'"+unitId+"',"
									+ "'"+usedFabrics+"',"
									+ "'"+requistionReq+"',"
									+"current_timestamp,'"+v.getUserId()+"')";
							session.createSQLQuery(sql).executeUpdate();
					}

				}
				

			}
			
			
			sql="update TbCuttingInformationSummary set RequistionFlag='1' where CuttingEntryId='"+v.getCuttingEntryId()+"' ";
			session.createSQLQuery(sql).executeUpdate();

			tx.commit();
			return true;
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
	public List<CuttingFabricsUsed> getCuttingUsedFabricsRequisitionList(String cuttingEntryId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<CuttingFabricsUsed> datalist=new ArrayList<CuttingFabricsUsed>();	
		try{	
			tx=session.getTransaction();
			tx.begin();		
			String sql = "\n" + 
					"select a.CuttingEntryId,(select Name from tbBuyer where id=a.BuyerId) as BuyerName,a.purchaseOrder,(select StyleNo from TbStyleCreate where StyleId=a.StyleId) as StyleName,(select ItemName from tbItemDescription where ItemId=a.ItemId) as ItemName,(select ItemName from TbFabricsItem where id=b.fabricsId) as FabricsItem,(select colorName from tbColors where colorId=b.colorId) as ColorName,b.requisitionQuantity,(select unitName from tbunits where unitId=b.unitId) as UnitName,a.CuttingNo,convert(varchar,a.Date,23) as Date from TbCuttingInformationSummary a join TbCuttingRequisitionDetailsv1 b on a.CuttingEntryId=b.CuttingEntryId where a.CuttingEntryId='"+cuttingEntryId+"' and a.RequistionFlag='1'";		
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				
				datalist.add(new CuttingFabricsUsed(element[0].toString(),element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString(), element[7].toString(), element[8].toString(), element[9].toString(), element[10].toString()));				
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
		return datalist;
	}

}
