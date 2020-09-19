package pg.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.catalina.Store;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.barcodelib.barcode.a.c.f;

import pg.model.commonModel;
import pg.orderModel.FabricsIndent;
import pg.orderModel.PurchaseOrderItem;
import pg.orderModel.Style;
import pg.share.HibernateUtil;
import pg.share.ItemType;
import pg.share.StoreTransaction;
import pg.storeModel.FabricsIssue;
import pg.storeModel.FabricsIssueReturn;
import pg.storeModel.FabricsQualityControl;
import pg.storeModel.FabricsReceive;
import pg.storeModel.FabricsReturn;
import pg.storeModel.FabricsRoll;

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
		return false;
	}

	@Override
	public boolean editFabricsIssueReturn(FabricsIssueReturn fabricsIssueReturn) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String editIssueReturndRollInTransaction(FabricsRoll fabricsRoll) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteIssueReturndRollFromTransaction(FabricsRoll fabricsRoll) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FabricsIssueReturn> getFabricsIssueReturnList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FabricsIssueReturn getFabricsIssueReturnInfo(String issueReturnTransectionId) {
		// TODO Auto-generated method stub
		return null;
	}

}
