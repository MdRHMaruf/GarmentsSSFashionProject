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
import pg.share.StoreTransection;
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
					"(select isnull(sum(qty),0) from tbFabricsAccessoriesTransection fat where fat.purchaseOrder = rf.PurchaseOrder and fat.styleId = rf.styleId and fat.styleItemId = rf.itemid and fat.colorId = rf.itemcolor and fat.dItemId = rf.fabricsId and fat.itemColorId = rf.fabricscolor and fat.transectionType = '1') as previousReceiveQty\r\n" + 
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

			String sql="select (isnull(max(transectionid),0)+1) as maxId from tbfabricsReceiveInfo";
			List<?> list = session.createSQLQuery(sql).list();
			String transectionId="0";
			if(list.size()>0) {
				transectionId = list.get(0).toString();
			}

			sql="insert into tbfabricsReceiveInfo (transectionid,"
					+ "grnNo,"
					+ "grnDate,"
					+ "location,"		
					+ "supplierId,"
					+ "challanNo,"
					+ "challanDate,"
					+ "remarks,"
					+ "preperedBy,"
					+ "entryTime,"
					+ "createBy) \r\n" + 
					"values('"+transectionId+"',"
					+ "'"+fabricsReceive.getGrnNo()+"',"
					+ "'"+fabricsReceive.getGrnDate()+"',"
					+ "'"+fabricsReceive.getLocation()+"',"
					+ "'"+fabricsReceive.getSupplierId()+"',"
					+ "'"+fabricsReceive.getChallanNo()+"',"
					+ "'"+fabricsReceive.getChallanDate()+"',"
					+ "'"+fabricsReceive.getRemarks()+"',"
					+ "'"+fabricsReceive.getPreparedBy()+"',"
					+ "CURRENT_TIMESTAMP,"
					+ "'"+fabricsReceive.getUserId()+"') ;";
			session.createSQLQuery(sql).executeUpdate();
			int departmentId = 1;
			int length = fabricsReceive.getFabricsRollList().size();
			for (FabricsRoll roll : fabricsReceive.getFabricsRollList()) {
				sql="insert into tbFabricsAccessoriesTransection (purchaseOrder,styleId,styleItemId,colorId,itemColorId,transectionId,transectionType,itemType,rollId,unitId,unitQty,qty,dItemId,cItemId,departmentId,rackName,binName,entryTime,userId) \r\n" + 
						"values('"+roll.getPurchaseOrder()+"','"+roll.getStyleId()+"','"+roll.getItemId()+"','"+roll.getItemColorId()+"','"+roll.getFabricsColorId()+"','"+transectionId+"','"+StoreTransection.FABRICS_RECEIVE.getType()+"','"+ItemType.FABRICS.getType()+"','"+roll.getRollId()+"','"+roll.getUnitId()+"','"+roll.getUnitQty()+"','"+roll.getUnitQty()+"','"+roll.getFabricsId()+"','0','"+departmentId+"','"+roll.getRackName()+"','"+roll.getBinName()+"',CURRENT_TIMESTAMP,'"+fabricsReceive.getUserId()+"');";		
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
					+ "preperedBy='"+fabricsReceive.getPreparedBy()+"' where transectionId= '"+fabricsReceive.getTransectionId()+"'";


			session.createSQLQuery(sql).executeUpdate();

			int departmentId = 1;
			int length = fabricsReceive.getFabricsRollList().size();
			for (FabricsRoll roll : fabricsReceive.getFabricsRollList()) {
				sql="insert into tbFabricsAccessoriesTransection (purchaseOrder,styleId,styleItemId,colorId,itemColorId,transectionId,transectionType,itemType,rollId,unitId,unitQty,qty,dItemId,cItemId,departmentId,rackName,binName,entryTime,userId) \r\n" + 
						"values('"+roll.getPurchaseOrder()+"','"+roll.getStyleId()+"','"+roll.getItemId()+"','"+roll.getItemColorId()+"','"+roll.getFabricsColorId()+"','"+fabricsReceive.getTransectionId()+"','"+StoreTransection.FABRICS_RECEIVE.getType()+"','"+ItemType.FABRICS.getType()+"','"+roll.getRollId()+"','"+roll.getUnitId()+"','"+roll.getUnitQty()+"','"+roll.getUnitQty()+"','"+roll.getFabricsId()+"','0','"+departmentId+"','"+roll.getRackName()+"','"+roll.getBinName()+"',CURRENT_TIMESTAMP,'"+fabricsReceive.getUserId()+"');";		
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
	public String editReceiveRollInTransaction(FabricsRoll fabricsRoll) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		try{
			tx=session.getTransaction();
			tx.begin();
			String sql = "select far.autoId,far.purchaseOrder,far.qty from tbFabricsAccessoriesTransection far\r\n" + 
					"join tbFabricsAccessoriesTransection far2\r\n" + 
					"on far.dItemId= far2.cItemId and far2.rollId = far.rollId and far2.itemColorId = far.itemColorId and far2.colorId = far.colorId and far2.styleItemId= far.styleItemId \r\n" + 
					"and far2.styleId = far.styleId and far2.purchaseOrder = far.purchaseOrder and (far.transectionType ='"+StoreTransection.FABRICS_RETURN.getType()+"' or far.transectionType ='"+StoreTransection.FABRICS_ISSUE.getType()+"')\r\n" + 
					"where far.autoId = '"+fabricsRoll.getAutoId()+"'";		
			List<?> list = session.createSQLQuery(sql).list();
			if(list.size()==0) {
				sql = "update tbFabricsAccessoriesTransection set unitQty = '"+fabricsRoll.getUnitQty()+"',qty = '"+fabricsRoll.getUnitQty()+"' where autoId = '"+fabricsRoll.getAutoId()+"'";
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
			String sql = "select far.autoId,far.purchaseOrder,far.qty from tbFabricsAccessoriesTransection far\r\n" + 
					"join tbFabricsAccessoriesTransection far2\r\n" + 
					"on far.dItemId= far2.cItemId and far2.rollId = far.rollId and far2.itemColorId = far.itemColorId and far2.colorId = far.colorId and far2.styleItemId= far.styleItemId \r\n" + 
					"and far2.styleId = far.styleId and far2.purchaseOrder = far.purchaseOrder and (far.transectionType ='"+StoreTransection.FABRICS_RETURN.getType()+"' or far.transectionType ='"+StoreTransection.FABRICS_ISSUE.getType()+"')\r\n" + 
					"where far.autoId = '"+fabricsRoll.getAutoId()+"'";		
			List<?> list = session.createSQLQuery(sql).list();
			if(list.size()==0) {
				sql = "delete from tbFabricsAccessoriesTransection where autoId = '"+fabricsRoll.getAutoId()+"'";
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
	public List<FabricsReceive> getFabricsReceiveList() {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<FabricsReceive> datalist=new ArrayList<FabricsReceive>();	
		try{	
			tx=session.getTransaction();
			tx.begin();		
			String sql = "select autoId,transectionId,grnNo,(select convert(varchar,grnDate,103))as grnDate,location,fri.supplierId,fri.challanNo,fri.challanDate,fri.remarks,fri.preperedBy,fri.createBy \r\n" + 
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
	public FabricsReceive getFabricsReceiveInfo(String transectionId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		FabricsReceive fabricsReceive = null;
		FabricsRoll tempRoll;
		List<FabricsRoll> fabricsRollList = new ArrayList<FabricsRoll>();	
		try{	
			tx=session.getTransaction();
			tx.begin();		
			String sql = "select far.autoId,far.transectionId,far.purchaseOrder,far.styleId,sc.styleNo,far.styleItemId,id.itemname,far.colorId as itemColorId ,ic.Colorname as itemColorName,dItemId as fabricsId,fi.ItemName as fabricsName,far.itemColorId as fabricsColorId,fc.Colorname as fabricsColorName,rollId,far.unitId,u.unitname,unitQty,rackName,BinName,far.userId,\r\n" + 
					"findent.RequireUnitQty as orderQty,(select isnull(sum(qty),0) from tbFabricsAccessoriesTransection t where far.purchaseOrder=t.purchaseOrder and far.styleId = t.styleId and far.styleItemId= t.styleItemId and far.colorId = t.colorId and far.dItemId = t.dItemId and far.itemColorId = t.itemColorId and t.transectionType = '1' and t.departmentId = '1' and far.transectionId != t.transectionId) as previousReceiveQty,\r\n" + 
					"(select isnull(sum(qty),0) from tbFabricsAccessoriesTransection t where far.purchaseOrder=t.purchaseOrder and far.styleId = t.styleId and far.styleItemId= t.styleItemId and far.colorId = t.colorId and far.dItemId = t.cItemId and far.itemColorId = t.itemColorId and far.rollId = t.rollId and t.transectionType = '3' and t.departmentId = '1') as returnQty,\r\n" + 
					"(select isnull(sum(qty),0) from tbFabricsAccessoriesTransection t where far.purchaseOrder=t.purchaseOrder and far.styleId = t.styleId and far.styleItemId= t.styleItemId and far.colorId = t.colorId and far.dItemId = t.cItemId and far.itemColorId = t.itemColorId and far.rollId = t.rollId and t.transectionType = '4' and t.departmentId = '1') as issueQty\r\n" + 
					"from tbFabricsAccessoriesTransection far\r\n" + 
					"left join tbFabricsIndent findent\r\n" + 
					"on far.PurchaseOrder = findent.purchaseOrder and far.styleId = findent.styleId and far.styleItemId= findent.itemId and far.colorId = findent.itemcolor and far.dItemId = findent.fabricsid and far.itemColorId = findent.fabricscolor\r\n" + 
					"left join TbStyleCreate sc\r\n" + 
					"on far.styleId = sc.StyleId\r\n" + 
					"left join tbItemDescription id\r\n" + 
					"on far.styleItemId = id.itemid\r\n" + 
					"left join tbunits u\r\n" + 
					"on far.unitId = u.Unitid\r\n" + 
					"left join TbFabricsItem fi\r\n" + 
					"on far.dItemId = fi.id\r\n" + 
					"left join tbColors ic\r\n" + 
					"on far.colorId = ic.ColorId\r\n" + 
					"left join tbColors fc\r\n" + 
					"on far.itemColorId = fc.ColorId\r\n" + 
					"where transectionId = '"+transectionId+"' and transectionType='"+StoreTransection.FABRICS_RECEIVE.getType()+"'";		
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				tempRoll = new FabricsRoll(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString(),element[7].toString(), element[8].toString(),element[9].toString(), element[10].toString(), element[11].toString(), element[12].toString(), element[13].toString(), element[14].toString(), element[15].toString(),0.0, Double.valueOf(element[16].toString()), element[17].toString(), element[18].toString(),1);
				tempRoll.setOrderQty(Double.valueOf(element[20].toString()));
				tempRoll.setPreviousReceiveQty(Double.valueOf(element[21].toString()));
				tempRoll.setReturnQty(Double.valueOf(element[22].toString()));
				tempRoll.setIssueQty(Double.valueOf(element[23].toString()));
				fabricsRollList.add(tempRoll);				
			}

			sql = "select autoId,transectionId,grnNo,(select convert(varchar,grnDate,103))as grnDate,location,fri.supplierId,fri.challanNo,fri.challanDate,fri.remarks,fri.preperedBy,fri.createBy \r\n" + 
					"from tbFabricsReceiveInfo fri\r\n" + 
					"where fri.transectionId = '"+transectionId+"'";		
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
		StoreTransection transection = StoreTransection.FABRICS_QC;
		ItemType itemType = ItemType.FABRICS;
		

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select (isnull(max(transectionId),0)+1) as maxId from tbfabricsQualityControlInfo";
			List<?> list = session.createSQLQuery(sql).list();
			String transectionId="0";
			if(list.size()>0) {
				transectionId = list.get(0).toString();
			}

			sql="insert into tbfabricsQualityControlInfo (transectionId"
					+ ",date"
					+ ",grnNo"
					+ ",remarks"
					+ ",checkBy"
					+ ",entryTime"
					+ ",createBy) values("
					+ "'"+transectionId+"'"
					+ ",'"+fabricsQC.getQcDate()+"'"
					+ ",'"+fabricsQC.getGrnNo()+"'"
					+ ",'"+fabricsQC.getRemarks()+"'"
					+ ",'"+fabricsQC.getCheckBy()+"',"
					+ "CURRENT_TIMESTAMP,'"+fabricsQC.getUserId()+"');";
			session.createSQLQuery(sql).executeUpdate();

			int length = fabricsQC.getFabricsRollList().size();
			int departmentId = 1;
			for (FabricsRoll roll : fabricsQC.getFabricsRollList()) {
				sql="insert into tbFabricsAccessoriesTransection (purchaseOrder,styleId,styleItemId,colorId,itemColorId,transectionId,transectionType,itemType,rollId,unitId,unitQty,qty,dItemId,cItemId,departmentId,rackName,binName,entryTime,userId) \r\n" + 
						"values('"+roll.getPurchaseOrder()+"','"+roll.getStyleId()+"','"+roll.getItemId()+"','"+roll.getItemColorId()+"','"+roll.getFabricsColorId()+"','"+transectionId+"','"+transection.getType()+"','"+itemType.getType()+"','"+roll.getRollId()+"','"+roll.getUnitId()+"','"+roll.getUnitQty()+"','"+roll.getUnitQty()+"','"+roll.getFabricsId()+"','0','"+departmentId+"','"+roll.getRackName()+"','"+roll.getBinName()+"',CURRENT_TIMESTAMP,'"+fabricsQC.getUserId()+"');";		
				session.createSQLQuery(sql).executeUpdate();
			}
			
			for (FabricsRoll roll : fabricsQC.getFabricsRollList()) {
				sql="insert into tbQulityControlDetails (transectionId,transectionType,itemType,itemId,rollId,unitId,QCCheckQty,shade,shrinkage,gsm,width,defect,remarks,qcPassedType,entryTime,userId) \r\n" + 
						"values('"+transectionId+"','"+transection.getType()+"','"+itemType.getType()+"','"+roll.getFabricsId()+"','"+roll.getRollId()+"','"+roll.getUnitId()+"','"+roll.getQcPassedQty()+"','"+roll.getShadeQty()+"','"+roll.getShrinkageQty()+"','"+roll.getGsmQty()+"','"+roll.getWidthQty()+"','"+roll.getDefectQty()+"','"+roll.getRemarks()+"','"+roll.getQcPassedType()+"',CURRENT_TIMESTAMP,'"+fabricsQC.getUserId()+"')";		
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
					+ ",createBy = '"+fabricsQC.getUserId()+"' where qcTransectionId='"+fabricsQC.getQcTransectionId()+"';";

			session.createSQLQuery(sql).executeUpdate();

			int length = fabricsQC.getFabricsRollList().size();
			for (FabricsRoll roll : fabricsQC.getFabricsRollList()) {
				sql="update tbFabricsRollDetails set QCTransectionId='"+fabricsQC.getQcTransectionId()+"',qcPassedQty='"+roll.getQcPassedQty()+"',qcPassedType='"+roll.getQcPassedType()+"' where autoId='"+roll.getAutoId()+"'";		
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
	public List<FabricsQualityControl> getFabricsQCList() {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<FabricsQualityControl> datalist=new ArrayList<FabricsQualityControl>();	
		try{	
			tx=session.getTransaction();
			tx.begin();		
			String sql = "select qci.AutoId,qci.TransectionId,(select convert(varchar,qci.date,103))as qcDate,qci.grnNo,(select convert(varchar,fri.grnDate,103))as receiveDate,qci.remarks,fri.supplierId,qci.checkBy,qci.createBy \r\n" + 
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
	public FabricsQualityControl getFabricsQCInfo(String qcTransectionId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		FabricsQualityControl fabricsQC = null;
		List<FabricsRoll> fabricsRollList = new ArrayList<FabricsRoll>();	
		try{	
			tx=session.getTransaction();
			tx.begin();		
			String sql = "select qcd.autoId,qcd.transectionId,qcd.rollId,fat.purchaseOrder,fat.styleId,fat.styleItemId,fat.colorId,fat.dItemId,fat.itemColorId,qcd.unitId,u.unitname,far.unitQty,QCCheckQty,shade,shrinkage,gsm,width,defect,remarks,fat.rackName,fat.BinName,qcPassedType,qcd.userId \r\n" + 
					"from tbQulityControlDetails qcd\r\n" + 
					"left join tbunits u\r\n" + 
					"on qcd.unitId = u.Unitid\r\n" + 
					"left join tbFabricsAccessoriesTransection far\r\n" + 
					"on qcd.transectionId = far.transectionId and far.transectionType = '"+StoreTransection.FABRICS_RECEIVE.getType()+"' and qcd.itemId = far.dItemId and qcd.rollId = far.rollId\r\n" + 
					"left join tbFabricsAccessoriesTransection fat\r\n" + 
					"on qcd.transectionId = fat.transectionId and qcd.transectionType = fat.transectionType and qcd.itemId = fat.dItemId and qcd.rollId = fat.rollId\r\n" + 
					"where qcd.transectionId = '"+qcTransectionId+"' \r\n" + 
					"";		
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				fabricsRollList.add(new FabricsRoll(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString(), element[7].toString(), "", element[8].toString(), "", element[9].toString(), element[10].toString(), Double.valueOf(element[11].toString()), Double.valueOf(element[12].toString()), Double.valueOf(element[13].toString()), Double.valueOf(element[14].toString()), Double.valueOf(element[15].toString()), Double.valueOf(element[16].toString()),Double.valueOf(element[17].toString()), element[18].toString(), element[19].toString(), element[20].toString(), Integer.valueOf(element[21].toString())));				
			}

			sql = "select qci.AutoId,qci.TransectionId,(select convert(varchar,qci.date,103))as qcDate,qci.grnNo,(select convert(varchar,fri.grnDate,103))as receiveDate,qci.remarks,fri.supplierId,qci.checkBy,qci.createBy \r\n" + 
					"from tbFabricsQualityControlInfo qci\r\n" + 
					"left join tbFabricsReceiveInfo fri\r\n" + 
					"on qci.grnNo = fri.grnNo where qci.transectionId = '"+qcTransectionId+"'";		
			list = session.createSQLQuery(sql).list();
			if(list.size()>0)
			{	
				Object[] element = (Object[]) list.get(0);

				fabricsQC = new FabricsQualityControl(element[0].toString(), qcTransectionId, element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString(), element[7].toString(),element[8].toString());
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
	public List<FabricsRoll> getFabricsRollList(String supplierId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<FabricsRoll> datalist=new ArrayList<FabricsRoll>();	
		try{	
			tx=session.getTransaction();
			tx.begin();		
			String sql = "select fat.autoID,fri.supplierId,s.name as supplierName,fat.purchaseOrder,fat.styleId,sc.StyleNo,fat.styleItemId,id.itemname,fat.colorid,c.colorName,fat.dItemId as fabricsId,fi.ItemName as fabricsName,fat.itemColorId,ic.Colorname as fabricsColor,fat.rollId,fi.unitId,u.unitname,dbo.fabricsBalanceQty(fat.purchaseOrder,fat.styleid,fat.styleItemId,fat.colorId,fat.dItemId,fat.ItemcolorId,fat.rollId) as balanceQty,fat.rackName,fat.binName \r\n" + 
					"from tbFabricsReceiveInfo fri\r\n" + 
					"left join tbSupplier s\r\n" + 
					"on fri.supplierId = s.id\r\n" + 
					"left join tbFabricsAccessoriesTransection fat\r\n" + 
					"on fri.transectionId = fat.transectionId and fat.transectionType = '1'\r\n" + 
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
					"left join tbunits u\r\n" + 
					"on fi.unitId = u.Unitid\r\n " + 
					"where fri.supplierId='"+supplierId+"'";		
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				datalist.add(new FabricsRoll(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString(), element[7].toString(), element[8].toString(), element[9].toString(), element[10].toString(), element[11].toString(), element[12].toString(), element[13].toString(), element[14].toString(),element[15].toString(),element[16].toString(), Double.valueOf(element[17].toString()),element[18].toString(),element[19].toString()));				
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
		StoreTransection transection = StoreTransection.FABRICS_RETURN;
		ItemType itemType = ItemType.FABRICS;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select (isnull(max(transectionId),0)+1) as maxId from tbFabricsReturnInfo";
			List<?> list = session.createSQLQuery(sql).list();
			String transectionId="0";
			if(list.size()>0) {
				transectionId = list.get(0).toString();
			}

			sql="insert into tbFabricsReturnInfo (transectionId"
					+ ",date"
					+ ",supplierId"
					+ ",remarks"
					+ ",entryTime"
					+ ",createBy) values("
					+ "'"+transectionId+"'"
					+ ",'"+fabricsReturn.getReturnDate()+"'"
					+ ",'"+fabricsReturn.getSupplierId()+"'"
					+ ",'"+fabricsReturn.getRemarks()+"',"
					+ "CURRENT_TIMESTAMP,'"+fabricsReturn.getUserId()+"');";
			session.createSQLQuery(sql).executeUpdate();

			int departmentId = 1;
			for (FabricsRoll roll : fabricsReturn.getFabricsRollList()) {
				sql="insert into tbFabricsAccessoriesTransection (purchaseOrder,styleId,styleItemId,colorId,itemColorId,transectionId,transectionType,itemType,rollId,unitId,unitQty,qty,dItemId,cItemId,departmentId,rackName,binName,entryTime,userId) \r\n" + 
						"values('"+roll.getPurchaseOrder()+"','"+roll.getStyleId()+"','"+roll.getItemId()+"','"+roll.getItemColorId()+"','"+roll.getFabricsColorId()+"','"+transectionId+"','"+transection.getType()+"','"+itemType.getType()+"','"+roll.getRollId()+"','"+roll.getUnitId()+"','"+roll.getUnitQty()+"','"+roll.getUnitQty()+"','0','"+roll.getFabricsId()+"','"+departmentId+"','"+roll.getRackName()+"','"+roll.getBinName()+"',CURRENT_TIMESTAMP,'"+fabricsReturn.getUserId()+"');";		
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
		StoreTransection transection = StoreTransection.FABRICS_RETURN;
		ItemType itemType = ItemType.FABRICS;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="update tbfabricsReturnInfo set "
					+ "date = '"+fabricsReturn.getReturnDate()+"'"
					+ ",grnNo = '"+fabricsReturn.getSupplierId()+"'"
					+ ",remarks = '"+fabricsReturn.getRemarks()+"'"
					+ ",entryTime = CURRENT_TIMESTAMP"
					+ ",createBy = '"+fabricsReturn.getUserId()+"' where returnTransectionId='"+fabricsReturn.getReturnTransectionId()+"';";

			session.createSQLQuery(sql).executeUpdate();

			int departmentId = 1;
			/*for (FabricsRoll roll : fabricsReturn.getFabricsRollList()) {
				sql="insert into tbFabricsAccessoriesTransection (purchaseOrder,styleId,styleItemId,colorId,itemColorId,transectionId,transectionType,itemType,rollId,unitId,unitQty,qty,dItemId,cItemId,departmentId,rackName,binName,entryTime,userId) \r\n" + 
						"values('"+roll.getPurchaseOrder()+"','"+roll.getStyleId()+"','"+roll.getItemId()+"','"+roll.getItemColorId()+"','"+roll.getFabricsColorId()+"','"+transectionId+"','"+transection.getType()+"','"+itemType.getType()+"','"+roll.getRollId()+"','"+roll.getUnitId()+"','"+roll.getUnitQty()+"','"+roll.getUnitQty()+"','"+roll.getFabricsId()+"','0','"+departmentId+"','"+roll.getRackName()+"','"+roll.getBinName()+"',CURRENT_TIMESTAMP,'"+fabricsReturn.getUserId()+"');";		
				session.createSQLQuery(sql).executeUpdate();
			}*/

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
	public List<FabricsReturn> getFabricsReturnList() {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<FabricsReturn> datalist=new ArrayList<FabricsReturn>();	
		try{	
			tx=session.getTransaction();
			tx.begin();		
			String sql = "select fri.AutoId,fri.transectionId,(select convert(varchar,fri.date,103))as returnDate,fri.supplierId,s.name as supplierName,fri.remarks,fri.createBy \r\n" + 
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
	public FabricsReturn getFabricsReturnInfo(String returnTransectionId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		FabricsReturn fabricsReturn = null;
		List<FabricsRoll> fabricsRollList = new ArrayList<FabricsRoll>();	
		try{	
			tx=session.getTransaction();
			tx.begin();		
			String sql = "select autoId,transectionId,purchaseOrder,styleId,styleItemId,far.colorId,cItemId,fi.ItemName,far.itemColorId,c.Colorname,rollId,far.unitId,u.unitname,dbo.fabricsBalanceQty(far.purchaseOrder,far.styleId,far.styleItemId,far.colorId,far.cItemId,far.itemColorId,far.rollId),unitQty,rackName,BinName,far.userId \r\n" + 
					"from tbFabricsAccessoriesTransection far\r\n" + 
					"left join tbunits u\r\n" + 
					"on far.unitId = u.Unitid\r\n" + 
					"left join TbFabricsItem fi\r\n" + 
					"on far.cItemId = fi.id\r\n" + 
					"left join tbColors c\r\n" + 
					"on far.itemColorId = c.ColorId\r\n" + 
					"where transectionId = '"+returnTransectionId+"' and transectionType='"+StoreTransection.FABRICS_RETURN.getType()+"'";	
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				fabricsRollList.add(new FabricsRoll(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString(),element[7].toString(), element[8].toString(),element[9].toString(), element[10].toString(), element[11].toString(), element[12].toString(), element[13].toString(), element[14].toString(), element[15].toString(),0.0, Double.valueOf(element[16].toString()), element[17].toString(), element[18].toString(),1));				
			}

			sql = "select fri.autoId,fri.transectionId,(select convert(varchar,fri.date,103))as date,fri.supplierId,s.name as supplierName,fri.remarks,fri.createBy \r\n" + 
					"from tbFabricsReturnInfo fri\r\n" + 
					"left join tbSupplier s\r\n" + 
					"on fri.supplierId = s.id\r\n" + 
					"where fri.transectionId = '"+returnTransectionId+"'";		
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
	public FabricsReceive getFabricsReceiveInfoForReturn(String transectionId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		FabricsReceive fabricsReceive = null;
		List<FabricsRoll> fabricsRollList = new ArrayList<FabricsRoll>();	
		try{	
			tx=session.getTransaction();
			tx.begin();		
			String sql = "select autoId,isnull(returnTransectionId,'')as returnTransectionId,rollId,supplierRollId,frd.unitId,u.unitname,rollQty,qcPassedQty,rackName,BinName,qcPassedType,isReturn,createBy  \r\n" + 
					"from tbFabricsRollDetails frd\r\n" + 
					"left join tbunits u\r\n" + 
					"on frd.unitId = u.Unitid\r\n" + 
					"where transectionId = '"+transectionId+"'";		
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				boolean isReturn = element[11].toString().equals("1")?true:false;
				//fabricsRollList.add(new FabricsRoll(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), Double.valueOf(element[6].toString()), Double.valueOf(element[7].toString()), element[8].toString(), element[9].toString(), Integer.valueOf(element[10].toString()), isReturn, element[12].toString()));				
			}

			sql = "select autoId,transectionId,grnNo,(select convert(varchar,grnDate,103))as grnDate,location,fIndent.PurchaseOrder,fIndent.styleId,sc.StyleNo,fIndent.itemid,id.itemname,fIndent.fabricscolor as colorId,c.Colorname,fri.fabricsId,ISNULL(fi.ItemName,'')as fabricsName,indentId,fri.unitId,grnQty,noOfRoll,fri.supplierId,fri.buyer,fri.challanNo,fri.challanDate,fri.remarks,fri.preperedBy,fri.createBy \r\n" + 
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
					"on fIndent.fabricsId = fi.id where fri.transectionId = '"+transectionId+"'";		
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

}
