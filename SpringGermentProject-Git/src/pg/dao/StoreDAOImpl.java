package pg.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.barcodelib.barcode.a.c.f;

import pg.model.commonModel;
import pg.orderModel.FabricsIndent;
import pg.orderModel.PurchaseOrderItem;
import pg.orderModel.Style;
import pg.share.HibernateUtil;
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
	public List<FabricsIndent> getFabricsListByItemId(String purchaseOrder, String styleId, String itemId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<FabricsIndent> datalist=new ArrayList<FabricsIndent>();	
		try{	
			tx=session.getTransaction();
			tx.begin();		
			String sql="select fi.id,id.itemname,c.Colorname,f.ItemName \r\n" + 
					"from tbFabricsIndent fi\r\n" + 
					"left join tbItemDescription id\r\n" + 
					"on fi.itemid = id.itemid\r\n" + 
					"left join tbColors c\r\n" + 
					"on fi.itemcolor = c.ColorId\r\n" + 
					"left join TbFabricsItem f\r\n" + 
					"on fi.fabricsid = f.id\r\n" + 
					"where fi.PurchaseOrder = '"+purchaseOrder+"' and fi.styleId = '"+styleId+"' and fi.itemid = '"+itemId+"'";		
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				datalist.add(new FabricsIndent(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString()));				
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
					+ "fabricsId,"
					+ "indentId,"
					+ "unitId,"
					+ "grnQty,"
					+ "noOfRoll,"
					+ "supplierId,"
					+ "buyer,"
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
					+ "'"+fabricsReceive.getFabricsId()+"',"
					+ "'"+fabricsReceive.getIndentId()+"',"
					+ "'"+fabricsReceive.getUnitId()+"',"
					+ "'"+fabricsReceive.getGrnQty()+"',"
					+ "'"+fabricsReceive.getNoOfRoll()+"',"
					+ "'"+fabricsReceive.getSupplierId()+"',"
					+ "'"+fabricsReceive.getBuyer()+"',"
					+ "'"+fabricsReceive.getChallanNo()+"',"
					+ "'"+fabricsReceive.getChallanDate()+"',"
					+ "'"+fabricsReceive.getRemarks()+"',"
					+ "'"+fabricsReceive.getPreparedBy()+"',"
					+ "CURRENT_TIMESTAMP,"
					+ "'"+fabricsReceive.getUserId()+"') ;";
			session.createSQLQuery(sql).executeUpdate();

			int length = fabricsReceive.getFabricsRollList().size();
			for (FabricsRoll roll : fabricsReceive.getFabricsRollList()) {
				sql="insert into tbFabricsRollDetails (transectionId,rollId,supplierRollId,unitId,rollQty,qcPassedQty,issueQty,balanceQty,rate,totalAmount,remarks,rackName,binName,entryTime,createBy) \r\n" + 
						"values('"+transectionId+"','"+roll.getRollId()+"','"+roll.getSupplierRollId()+"','"+fabricsReceive.getUnitId()+"','"+roll.getRollQty()+"','"+roll.getQcPassedQty()+"','"+roll.getIssueQty()+"','"+roll.getBalanceQty()+"','"+roll.getRate()+"','"+roll.getTotalAmount()+"','"+roll.getRemarks()+"','"+roll.getRackName()+"','"+roll.getBinName()+"',CURRENT_TIMESTAMP,'"+fabricsReceive.getUserId()+"');";		
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



			String sql="update tbfabricsReceiveInfo "
					+ "grnNo='"+fabricsReceive.getGrnNo()+"',"
					+ "grnDate='"+fabricsReceive.getGrnDate()+"',"
					+ "location='"+fabricsReceive.getLocation()+"',"
					+ "fabricsId='"+fabricsReceive.getFabricsId()+"',"
					+ "indentId='"+fabricsReceive.getIndentId()+"',"
					+ "grnQty='"+fabricsReceive.getGrnQty()+"',"
					+ "noOfRoll='"+fabricsReceive.getNoOfRoll()+"',"
					+ "supplierId='"+fabricsReceive.getSupplierId()+"',"
					+ "buyer='"+fabricsReceive.getBuyer()+"',"
					+ "challanNo='"+fabricsReceive.getChallanNo()+"',"
					+ "challanDate='"+fabricsReceive.getChallanDate()+"',"
					+ "remarks='"+fabricsReceive.getRemarks()+"',"
					+ "preperedBy='"+fabricsReceive.getPreparedBy()+"' where transectionId= '"+fabricsReceive.getTransectionId()+"'";


			session.createSQLQuery(sql).executeUpdate();

			sql = "delete from tbFabricsRollDetails where transetionId = '"+fabricsReceive.getTransectionId()+"'";
			session.createSQLQuery(sql).executeUpdate();

			int length = fabricsReceive.getFabricsRollList().size();
			for (FabricsRoll roll : fabricsReceive.getFabricsRollList()) {
				sql="insert into tbFabricsRollDetails (transectionId,rollId,supplierRollId,rollQty,qcPassedQty,issueQty,balanceQty,rate,totalAmount,remarks,rackName,binName,entryTime,createBy) \r\n" + 
						"values('"+fabricsReceive.getFabricsId()+"','"+roll.getRollId()+"','"+roll.getSupplierRollId()+"','"+roll.getRollQty()+"','"+roll.getQcPassedQty()+"','"+roll.getIssueQty()+"','"+roll.getBalanceQty()+"','"+roll.getRate()+"','"+roll.getTotalAmount()+"','"+roll.getRemarks()+"','"+roll.getRackName()+"','"+roll.getBinName()+"',CURRENT_TIMESTAMP,'"+fabricsReceive.getUserId()+"');";		
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
	public List<FabricsReceive> getFabricsReceiveList() {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<FabricsReceive> datalist=new ArrayList<FabricsReceive>();	
		try{	
			tx=session.getTransaction();
			tx.begin();		
			String sql = "select autoId,transectionId,grnNo,(select convert(varchar,grnDate,103))as grnDate,location,fIndent.PurchaseOrder,fIndent.styleId,sc.StyleNo,fIndent.itemid,id.itemname,fIndent.fabricscolor as colorId,c.Colorname,fri.fabricsId,ISNULL(fi.ItemName,'')as fabricsName,indentId,fri.unitId,grnQty,noOfRoll,fri.supplierId,fri.buyer,fri.challanNo,fri.challanDate,fri.remarks,fri.preperedBy,fri.createBy \r\n" + 
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
					"on fIndent.fabricsId = fi.id";		
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				datalist.add(new FabricsReceive(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString(), element[7].toString(), element[8].toString(), element[9].toString(), element[10].toString(), element[11].toString(), element[12].toString(), element[13].toString(), element[14].toString(), element[15].toString(), Double.valueOf(element[16].toString()), Double.valueOf(element[17].toString()), element[18].toString(),element[19].toString(), element[20].toString(), element[21].toString(), element[22].toString(), element[23].toString(), element[24].toString()));				
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
		List<FabricsRoll> fabricsRollList = new ArrayList<FabricsRoll>();	
		try{	
			tx=session.getTransaction();
			tx.begin();		
			String sql = "select autoId,transectionId,rollId,supplierRollId,frd.unitId,u.unitname,rollQty,qcPassedQty,issueQty,balanceQty,rate,totalAmount,remarks,rackName,BinName,createBy \r\n" + 
					"from tbFabricsRollDetails frd\r\n" + 
					"left join tbunits u\r\n" + 
					"on frd.unitId = u.Unitid\r\n" + 
					"where transectionId = '"+transectionId+"'";		
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				fabricsRollList.add(new FabricsRoll(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(),element[4].toString(),element[5].toString(), Double.valueOf(element[6].toString()), Double.valueOf(element[7].toString()), Double.valueOf(element[8].toString()), Double.valueOf(element[9].toString()), Double.valueOf(element[10].toString()), Double.valueOf(element[11].toString()), element[12].toString(), element[13].toString(), element[14].toString(), element[15].toString()));				
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

				fabricsReceive = new FabricsReceive(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString(), element[7].toString(), element[8].toString(), element[9].toString(), element[10].toString(), element[11].toString(), element[12].toString(), element[13].toString(), element[14].toString(), element[15].toString(), Double.valueOf(element[16].toString()), Double.valueOf(element[17].toString()), element[18].toString(),element[19].toString(), element[20].toString(), element[21].toString(), element[22].toString(), element[23].toString(), element[24].toString());
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

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select (isnull(max(qcTransectionId),0)+1) as maxId from tbfabricsQualityControlInfo";
			List<?> list = session.createSQLQuery(sql).list();
			String transectionId="0";
			if(list.size()>0) {
				transectionId = list.get(0).toString();
			}

			sql="insert into tbfabricsQualityControlInfo (qcTransectionId"
					+ ",date"
					+ ",grnNo"
					+ ",remarks"
					+ ",entryTime"
					+ ",createBy) values("
					+ "'"+transectionId+"'"
					+ ",'"+fabricsQC.getQcDate()+"'"
					+ ",'"+fabricsQC.getGrnNo()+"'"
					+ ",'"+fabricsQC.getRemarks()+"',"
					+ "CURRENT_TIMESTAMP,'"+fabricsQC.getUserId()+"');";
			session.createSQLQuery(sql).executeUpdate();

			int length = fabricsQC.getFabricsRollList().size();
			for (FabricsRoll roll : fabricsQC.getFabricsRollList()) {
				sql="update tbFabricsRollDetails set QCTransectionId='"+transectionId+"',qcPassedQty='"+roll.getQcPassedQty()+"',qcPassedType='"+roll.getQcPassedType()+"' where autoId='"+roll.getAutoId()+"'";		
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
			String sql = "select qci.AutoId,qci.qcTransectionId,(select convert(varchar,qci.date,103))as qcDate,qci.grnNo,(select convert(varchar,fri.grnDate,103))as receiveDate,qci.remarks,f.ItemName,fri.supplierId,qci.createBy \r\n" + 
					"from tbFabricsQualityControlInfo qci\r\n" + 
					"left join tbFabricsReceiveInfo fri\r\n" + 
					"on qci.grnNo = fri.grnNo\r\n" + 
					"left join tbFabricsIndent fi\r\n" + 
					"on fri.indentId = fi.id\r\n" + 
					"left join TbFabricsItem f\r\n" + 
					"on fi.fabricsid = f.id";		
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
			String sql = "select autoId,transectionId,rollId,supplierRollId,frd.unitId,u.unitname,rollQty,qcPassedQty,issueQty,balanceQty,rate,totalAmount,remarks,rackName,BinName,QCTransectionId,qcPassedType,createBy \r\n" + 
					"from tbFabricsRollDetails frd\r\n" + 
					"left join tbunits u\r\n" + 
					"on frd.unitId = u.Unitid\r\n" + 
					"where QCTransectionId = '"+qcTransectionId+"'";		
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				fabricsRollList.add(new FabricsRoll(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(),element[4].toString(),element[5].toString(), Double.valueOf(element[6].toString()), Double.valueOf(element[7].toString()), Double.valueOf(element[8].toString()), Double.valueOf(element[9].toString()), Double.valueOf(element[10].toString()), Double.valueOf(element[11].toString()), element[12].toString(), element[13].toString(), element[14].toString(), element[15].toString(),Integer.valueOf(element[16].toString()),element[17].toString()));				
			}

			sql = "select qci.AutoId,qci.qcTransectionId,(select convert(varchar,qci.date,103))as qcDate,qci.grnNo,(select convert(varchar,fri.grnDate,103))as receiveDate,qci.remarks,f.ItemName,fri.supplierId,qci.createBy \r\n" + 
					"from tbFabricsQualityControlInfo qci\r\n" + 
					"left join tbFabricsReceiveInfo fri\r\n" + 
					"on qci.grnNo = fri.grnNo\r\n" + 
					"left join tbFabricsIndent fi\r\n" + 
					"on fri.indentId = fi.id\r\n" + 
					"left join TbFabricsItem f\r\n" + 
					"on fi.fabricsid = f.id where qci.qcTransectionId = '"+qcTransectionId+"'";		
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
	public boolean submitFabricsReturn(FabricsReturn fabricsReturn) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select (isnull(max(returnTransectionId),0)+1) as maxId from tbFabricsReturnInfo";
			List<?> list = session.createSQLQuery(sql).list();
			String transectionId="0";
			if(list.size()>0) {
				transectionId = list.get(0).toString();
			}

			sql="insert into tbFabricsReturnInfo (returnTransectionId"
					+ ",date"
					+ ",grnNo"
					+ ",remarks"
					+ ",entryTime"
					+ ",createBy) values("
					+ "'"+transectionId+"'"
					+ ",'"+fabricsReturn.getReturnDate()+"'"
					+ ",'"+fabricsReturn.getGrnNo()+"'"
					+ ",'"+fabricsReturn.getRemarks()+"',"
					+ "CURRENT_TIMESTAMP,'"+fabricsReturn.getUserId()+"');";
			session.createSQLQuery(sql).executeUpdate();

			int length = fabricsReturn.getFabricsRollList().size();
			for (FabricsRoll roll : fabricsReturn.getFabricsRollList()) {
				sql="update tbFabricsRollDetails set returnTransectionId='"+transectionId+"',isReturn='"+(roll.isReturn()?1:0)+"' where autoId='"+roll.getAutoId()+"'";		
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

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="update tbfabricsReturnInfo set "
					+ "date = '"+fabricsReturn.getReturnDate()+"'"
					+ ",grnNo = '"+fabricsReturn.getGrnNo()+"'"
					+ ",remarks = '"+fabricsReturn.getRemarks()+"'"
					+ ",entryTime = CURRENT_TIMESTAMP"
					+ ",createBy = '"+fabricsReturn.getUserId()+"' where returnTransectionId='"+fabricsReturn.getReturnTransectionId()+"';";

			session.createSQLQuery(sql).executeUpdate();

			int length = fabricsReturn.getFabricsRollList().size();
			for (FabricsRoll roll : fabricsReturn.getFabricsRollList()) {
				sql="update tbFabricsRollDetails set returnTransectionId='"+fabricsReturn.getReturnTransectionId()+"',isReturn='"+(roll.isReturn()?1:0)+"' where autoId='"+roll.getAutoId()+"'";		
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
	public List<FabricsReturn> getFabricsReturnList() {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<FabricsReturn> datalist=new ArrayList<FabricsReturn>();	
		try{	
			tx=session.getTransaction();
			tx.begin();		
			String sql = "select qci.AutoId,qci.returnTransectionId,(select convert(varchar,qci.date,103))as qcDate,qci.grnNo,(select convert(varchar,fri.grnDate,103))as receiveDate,qci.remarks,f.ItemName,fri.supplierId,qci.createBy \r\n" + 
					"from tbFabricsReturnInfo qci\r\n" + 
					"left join tbFabricsReceiveInfo fri\r\n" + 
					"on qci.grnNo = fri.grnNo\r\n" + 
					"left join tbFabricsIndent fi\r\n" + 
					"on fri.indentId = fi.id\r\n" + 
					"left join TbFabricsItem f\r\n" + 
					"on fi.fabricsid = f.id";		
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				datalist.add(new FabricsReturn(element[0].toString(),element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString(), element[7].toString(), element[8].toString()));				
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
			String sql = "select autoId,isnull(returnTransectionId,'')as returnTransectionId,rollId,supplierRollId,frd.unitId,u.unitname,rollQty,qcPassedQty,rackName,BinName,qcPassedType,isReturn,createBy \r\n" + 
					"from tbFabricsRollDetails frd\r\n" + 
					"left join tbunits u\r\n" + 
					"on frd.unitId = u.Unitid\r\n" + 
					"where returnTransectionId = '"+returnTransectionId+"'";		
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				boolean isReturn = element[11].toString().equals("1")?true:false;
				fabricsRollList.add(new FabricsRoll(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), Double.valueOf(element[6].toString()), Double.valueOf(element[7].toString()), element[8].toString(), element[9].toString(), Integer.valueOf(element[10].toString()), isReturn, element[12].toString()));
				//fabricsRollList.add(new FabricsRoll(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(),element[4].toString(),element[5].toString(), Double.valueOf(element[6].toString()), Double.valueOf(element[7].toString()), Double.valueOf(element[8].toString()), Double.valueOf(element[9].toString()), Double.valueOf(element[10].toString()), Double.valueOf(element[11].toString()), element[12].toString(), element[13].toString(), element[14].toString(), element[15].toString(),Integer.valueOf(element[16].toString()),,element[17].toString()));				
			}

			sql = "select qci.AutoId,qci.returnTransectionId,(select convert(varchar,qci.date,103))as qcDate,qci.grnNo,(select convert(varchar,fri.grnDate,103))as receiveDate,qci.remarks,f.ItemName,fri.supplierId,qci.createBy \r\n" + 
					"from tbFabricsReturnInfo qci\r\n" + 
					"left join tbFabricsReceiveInfo fri\r\n" + 
					"on qci.grnNo = fri.grnNo\r\n" + 
					"left join tbFabricsIndent fi\r\n" + 
					"on fri.indentId = fi.id\r\n" + 
					"left join TbFabricsItem f\r\n" + 
					"on fi.fabricsid = f.id where qci.returnTransectionId = '"+returnTransectionId+"'";		
			list = session.createSQLQuery(sql).list();
			if(list.size()>0)
			{	
				Object[] element = (Object[]) list.get(0);

				fabricsReturn = new FabricsReturn(element[0].toString(), returnTransectionId, element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString(), element[7].toString(),element[8].toString());
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
				fabricsRollList.add(new FabricsRoll(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), Double.valueOf(element[6].toString()), Double.valueOf(element[7].toString()), element[8].toString(), element[9].toString(), Integer.valueOf(element[10].toString()), isReturn, element[12].toString()));				
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
				fabricsReceive = new FabricsReceive(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString(), element[7].toString(), element[8].toString(), element[9].toString(), element[10].toString(), element[11].toString(), element[12].toString(), element[13].toString(), element[14].toString(), element[15].toString(), Double.valueOf(element[16].toString()), Double.valueOf(element[17].toString()), element[18].toString(),element[19].toString(), element[20].toString(), element[21].toString(), element[22].toString(), element[23].toString(),element[24].toString());
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
