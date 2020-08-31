package pg.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import pg.orderModel.SampleRequisitionItem;
import pg.proudctModel.CuttingInformation;
import pg.proudctModel.ProductionPlan;
import pg.proudctModel.cuttingRequsition;
import pg.registerModel.Department;
import pg.registerModel.ItemDescription;
import pg.registerModel.Line;
import pg.registerModel.Size;
import pg.registerModel.SizeGroup;
import pg.share.HibernateUtil;
import pg.share.SizeValuesType;

@Repository
public class ProductionDAOImpl implements ProductionDAO{

	@Override
	public boolean cuttingRequisitionEnty(cuttingRequsition v) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();

			String cuttingReqId=getMaxCuttingReqId();
			//System.out.println("v "+v.getResultvalue());
			String resultValue=v.getResultvalue().substring(v.getResultvalue().indexOf("[")+1, v.getResultvalue().indexOf("]"));
			//System.out.println("resultValue "+resultValue);

			String sizegroupValue=v.getSizegroupvalue().substring(v.getSizegroupvalue().indexOf("[")+1, v.getSizegroupvalue().indexOf("]"));
			//System.out.println("sizegroupValue "+sizegroupValue);

			String sizeGroupId="";
			StringTokenizer sizeToken=new StringTokenizer(sizegroupValue,",");
			while(sizeToken.hasMoreTokens()) {
				sizeGroupId=sizeToken.nextToken();
				String sql="insert into TbCuttingRequisitionDetails (BuyerId,purchaseOrder,StyleId,ItemId,ColorId,fabricsId,CuttingNo,CuttingDate,sizeGroupId,EntryTime,Date,UserId) "
						+ "values('"+v.getBuyerId()+"','"+v.getPurchaseOrder()+"','"+v.getStyleId()+"','"+v.getItemName()+"','"+v.getColorName()+"','"+v.getFabricsId()+"','"+v.getCuttingno()+"','"+v.getCuttingDate()+"','"+sizeGroupId+"',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'"+v.getUserId()+"');";
				session.createSQLQuery(sql).executeUpdate();

				String itemAutoId ="";
				sql="select max(cuttingAutoId) as itemAutoId from TbCuttingRequisitionDetails where CuttingReqId IS NULL and userId='"+v.getUserId()+"'";
				List<?> list = session.createSQLQuery(sql).list();
				for(Iterator<?> iter = list.iterator(); iter.hasNext();)
				{	
					itemAutoId =  iter.next().toString();	
				}

				//System.out.println("resultValue "+resultValue);

				double totalWardQty=0;
				String tokenResult="";
				StringTokenizer firstToken=new StringTokenizer(resultValue,",");
				while(firstToken.hasMoreTokens()) {
					tokenResult=firstToken.nextToken();
					String sizegroupId,sizeId="",orderQty="",wardQty="";
					StringTokenizer token=new StringTokenizer(tokenResult,":");
					while(token.hasMoreTokens()) {
						sizegroupId=token.nextToken();
						sizeId=token.nextToken();
						orderQty=token.nextToken();
						wardQty=token.nextToken();

						if(sizegroupId.equals(sizeGroupId)) {
							totalWardQty=totalWardQty+Double.parseDouble(wardQty);
							sql = "insert into tbSizeValues (linkedAutoId,sizeGroupId,sizeId,sizeQuantity,type,entryTime,userId) values('"+itemAutoId+"','"+sizeGroupId+"','"+sizeId+"','"+wardQty+"','"+SizeValuesType.CUTTING.getType()+"',CURRENT_TIMESTAMP,'"+v.getUserId()+"');";
							session.createSQLQuery(sql).executeUpdate();
						}

					}
				}


				sql="update TbCuttingRequisitionDetails set TotalWardQty='"+totalWardQty+"',CuttingReqId='"+cuttingReqId+"' where CuttingReqId IS NULL and UserId='"+v.getUserId()+"'";
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

	private String getMaxCuttingReqId() {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		String query="";

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select isnull(max(CuttingReqId),0)+1 from TbCuttingRequisitionDetails";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				query=iter.next().toString();
			}

			tx.commit();
			return query;
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

		return query;
	}

	@Override
	public boolean productionEnty(cuttingRequsition v) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getOrderQty(String buyerorderid, String style, String item) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		String query="0";

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select ISNULL(sum(TotalUnit),0) as TotalQty from TbBuyerOrderEstimateDetails where BuyerOrderId='"+buyerorderid+"' and StyleId='"+style+"' and ItemId='"+item+"'";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				query=iter.next().toString();
			}

			tx.commit();
			return query;
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

		return query;
	}

	@Override
	public boolean checkDoplicationPlanSet(ProductionPlan v) {
		

		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select PlanQty from TbProductTargetPlan where BuyerOrderId='"+v.getBuyerorderId()+"' and StyleId='"+v.getStyleId()+"' and ItemId='"+v.getItemId()+"'";
			System.out.println("sql "+sql);
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				return true;
			}
			
		}
		catch(Exception e){

			e.printStackTrace();
		}

		finally {
			session.close();
		}

		return false;
	}

	@Override
	public boolean productionPlanSave(ProductionPlan v) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="insert into TbProductTargetPlan (BuyerId,MerchendizerId,BuyerOrderId,PoNo,StyleId,ItemId,OrderQty,PlanQty,ShipDate,AccessoriesInhouseStatus,FabricsInhouseStatus,FileSample,PPStatus,StartDate,EndDate,Date,EntryTime,UserId) "
					+ "values("
					+ "'"+v.getBuyerId()+"',"
					+ "'"+v.getMerchendizerId()+"',"
					+ "'"+v.getBuyerorderId()+"',"
					+ "'"+v.getPurchaseOrder()+"',"
					+ "'"+v.getStyleId()+"',"
					+ "'"+v.getItemId()+"',"
					+ "'"+v.getOrderQty()+"',"
					+ "'"+v.getPlanQty()+"',"
					+ "'"+v.getShipDate()+"',"
					+ "'"+v.getAccessoriesInhouse()+"',"
					+ "'"+v.getFabricsInhouse()+"',"
					+ "'"+v.getFileSample()+"',"
					+ "'"+v.getPpStatus()+"',"
					+ "'"+v.getStartDate()+"',"
					+ "'"+v.getEndDate()+"',"
					+ "CURRENT_TIMESTAMP,"
					+ "CURRENT_TIMESTAMP,"
					+ "'"+v.getUserId()+"');";
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
	public List<ProductionPlan> getProductionPlanList() {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<ProductionPlan> dataList=new ArrayList<ProductionPlan>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select (select name from tbBuyer where id=a.BuyerId) as BuyerName,a.BuyerId,a.BuyerOrderId,a.PoNo,(select StyleNo from TbStyleCreate where StyleId=a.StyleId) as StyleNo,a.styleId from TbProductTargetPlan a group by a.BuyerId,a.BuyerOrderId,a.PoNo,a.StyleId\r\n" + 
					"";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();

				dataList.add(new ProductionPlan(element[0].toString(), element[1].toString(),element[2].toString(),element[3].toString(),element[4].toString(),element[5].toString()));
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
	public List<ProductionPlan> getProductionPlan(String buyerId, String buyerorderId, String styleId) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<ProductionPlan> dataList=new ArrayList<ProductionPlan>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select (select name from tbBuyer where id=a.BuyerId) as BuyerName,a.BuyerId,a.BuyerOrderId,a.PoNo,(select StyleNo from TbStyleCreate where StyleId=a.StyleId) as StyleNo,a.styleId,(select ItemName from tbItemDescription where ItemId=a.ItemId) as ItemName,a.ItemId,convert(varchar,a.shipDate,23) as shipDate,a.OrderQty,a.PlanQty,a.FileSample,a.PPStatus,a.AccessoriesInhouseStatus,a.FabricsInhouseStatus,convert(varchar,a.StartDate,23) as StartDate,convert(varchar,a.EndDate,23) as EndDate from TbProductTargetPlan a where a.BuyerId='"+buyerId+"' and a.BuyerOrderId='"+buyerorderId+"' and a.StyleId='"+styleId+"'";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				
				Object[] element = (Object[]) iter.next();
				
				dataList.add(new ProductionPlan(element[0].toString(), element[1].toString(),element[2].toString(),element[3].toString(),element[4].toString(),element[5].toString(),element[6].toString(),element[7].toString(),element[8].toString(),Double.toString(Double.parseDouble(element[9].toString())),Double.toString(Double.parseDouble(element[10].toString())),element[11].toString(),element[12].toString(),element[13].toString(),element[14].toString(),element[15].toString(),element[16].toString()));
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
	public List<ProductionPlan> getProductionPlanForCutting() {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<ProductionPlan> dataList=new ArrayList<ProductionPlan>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select (select name from tbBuyer where id=a.BuyerId) as BuyerName,a.BuyerId,a.BuyerOrderId,a.PoNo,(select StyleNo from TbStyleCreate where StyleId=a.StyleId) as StyleNo,a.styleId,(select ItemName from tbItemDescription where itemid=a.itemId) as ItemName,a.ItemId from TbProductTargetPlan a group by a.BuyerId,a.BuyerOrderId,a.PoNo,a.StyleId,a.ItemId";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();

				dataList.add(new ProductionPlan(element[0].toString(), element[1].toString(),element[2].toString(),element[3].toString(),element[4].toString(),element[5].toString(),element[6].toString(),element[7].toString()));
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
	public List<Department> getFactoryWiseDepartmentLoad(String factoryId) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<Department> dataList=new ArrayList<Department>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select DepartmentId,DepartmentName from TbDepartmentInfo where FactoryId='"+factoryId+"' order by DepartmentName";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();

				dataList.add(new Department(element[0].toString(), element[1].toString()));
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
	public List<Line> getFactoryDepartmentWiseLineLoad(String factoryId, String departmentId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<Line> dataList=new ArrayList<Line>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select LineId,LineName from TbLineCreate where FactoryId='"+factoryId+"' and DepartmentId='"+departmentId+"' order by LineName";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();

				dataList.add(new Line(element[0].toString(), element[1].toString()));
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
	public List<CuttingInformation> getBuyerPoDetails(String buyerId, String buyerorderId, String styleId,
			String itemId) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<CuttingInformation> dataList=new ArrayList<CuttingInformation>();
		try{
			tx=session.getTransaction();
			tx.begin();
			
			String sql="select boed.autoId,boed.BuyerId,boed.BuyerOrderId,boed.StyleId,sc.StyleNo,boed.ItemId,id.itemname,boed.ColorId,c.Colorname,boed.PurchaseOrder,boed.sizeGroupId,boed.userId\r\n" + 
					"										from TbBuyerOrderEstimateDetails boed \r\n" + 
					"										left join TbBuyerOrderEstimateSummary boesr\r\n" + 
					"										on boesr.autoId=boed.BuyerOrderId\r\n" + 
					"										left join TbStyleCreate sc\r\n" + 
					"										on boed.StyleId = sc.StyleId\r\n" + 
					"										left join tbItemDescription id\r\n" + 
					"										on boed.ItemId = id.itemid\r\n" + 
					"										left join tbColors c\r\n" + 
					"										on boed.ColorId = c.ColorId\r\n" + 
					"										where boed.buyerId='"+buyerId+"' and boed.BuyerOrderId='"+buyerorderId+"' and boed.StyleId='"+styleId+"' and boed.ItemId='"+itemId+"' order by sizeGroupId";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();

				dataList.add(new CuttingInformation(element[0].toString(),element[1].toString(),element[2].toString(),element[3].toString(),element[4].toString(),element[5].toString(),element[6].toString(),element[7].toString(),element[8].toString(),element[9].toString(),element[10].toString(),element[11].toString()));
			}
			
			for (CuttingInformation cuttingItem : dataList) {
				sql = "select bs.sizeGroupId,bs.sizeId,ss.sizeName,bs.sizeQuantity from tbSizeValues bs\r\n" + 
						"join tbStyleSize ss \r\n" + 
						"on ss.id = bs.sizeId \r\n" + 
						"where bs.linkedAutoId = '"+cuttingItem.getAutoId()+"' and bs.type='"+SizeValuesType.BUYER_PO.getType()+"' and bs.sizeGroupId = '"+cuttingItem.getSizeGroupId()+"' \r\n" + 
						"order by ss.sortingNo";
				System.out.println(sql);
				List<?> list2 = session.createSQLQuery(sql).list();
				ArrayList<Size> sizeList=new ArrayList<Size>();
				for(Iterator<?> iter = list2.iterator(); iter.hasNext();)
				{	
					Object[] element = (Object[]) iter.next();	
					sizeList.add(new Size(element[0].toString(),element[1].toString(), element[3].toString()));
				}
				cuttingItem.setSizeList(sizeList);
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


}
