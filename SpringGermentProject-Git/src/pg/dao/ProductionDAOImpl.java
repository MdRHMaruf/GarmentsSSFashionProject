package pg.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.springframework.stereotype.Repository;

import pg.orderModel.SampleRequisitionItem;
import pg.orderModel.Style;
import pg.proudctionModel.CuttingInformation;
import pg.proudctionModel.SewingLinesModel;
import pg.proudctionModel.ProductionPlan;
import pg.proudctionModel.cuttingRequsition;
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

	private String getMaxCuttingEntryId() {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		String query="";

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select isnull(max(CuttingEntryId),0)+1 from TbCuttingInformationSummary";

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

			String sql="select (select name from tbBuyer where id=a.BuyerId) as BuyerName,a.BuyerId,a.BuyerOrderId,a.PoNo,(select StyleNo from TbStyleCreate where StyleId=a.StyleId) as StyleNo,a.styleId,(select ItemName from tbItemDescription where itemid=a.itemId) as ItemName,a.ItemId,a.PlanQty from TbProductTargetPlan a group by a.BuyerId,a.BuyerOrderId,a.PoNo,a.StyleId,a.ItemId,a.PlanQty";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();

				dataList.add(new ProductionPlan(element[0].toString(), element[1].toString(),element[2].toString(),element[3].toString(),element[4].toString(),element[5].toString(),element[6].toString(),element[7].toString(),element[8].toString()));
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

	@Override
	public boolean cuttingInformationEnty(CuttingInformation v) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();

			String cuttingEntryId=getMaxCuttingEntryId();
			//System.out.println("v "+v.getResultvalue());
			String resultValue=v.getResultvalue().substring(v.getResultvalue().indexOf("[")+1, v.getResultvalue().indexOf("]"));
			System.out.println("resultValue "+resultValue);


			String sizegroupValue=v.getSizegroupvalue().substring(v.getSizegroupvalue().indexOf("[")+1, v.getSizegroupvalue().indexOf("]"));
			//System.out.println("sizegroupValue "+sizegroupValue);

			String colorValue=v.getColorlistvalue().substring(v.getColorlistvalue().indexOf("[")+1, v.getColorlistvalue().indexOf("]"));
			System.out.println("colorValue "+colorValue);

			String cuttinglistvalue=v.getCuttinglistvalue().substring(v.getCuttinglistvalue().indexOf("[")+1, v.getCuttinglistvalue().indexOf("]"));
			System.out.println("cuttinglistvalue "+cuttinglistvalue);

			int colorsave=0;
			String sql="";
			String colorId="",sizeGroupId="";
			String colorArrElement="";
			
			System.out.println("colorValue "+colorValue);
			
			StringTokenizer colortarroken=new StringTokenizer(colorValue,",");
			while(colortarroken.hasMoreElements()) {
				colorArrElement=colortarroken.nextToken();


				StringTokenizer colorArrToken=new StringTokenizer(colorArrElement,":");
				while(colorArrToken.hasMoreTokens()) {
					colorId=colorArrToken.nextToken();


					sizeGroupId=colorArrToken.nextToken();
					String ratiototalpcs=colorArrToken.nextToken();
					String ratiototalbox=colorArrToken.nextToken();
					String ratiototalexcess=colorArrToken.nextToken();
					String ratiototalshort=colorArrToken.nextToken();
					String ratiototalusedfabrics=colorArrToken.nextToken();
					String cuttingtotalpcs=colorArrToken.nextToken();
					String cuttingtotaldozen=colorArrToken.nextToken();
					String cuttingtotalexcess=colorArrToken.nextToken();
					String cuttingtotalshort=colorArrToken.nextToken();
					String cuttingtotalusedfabrics=colorArrToken.nextToken();
					
					System.out.println("cuttingtotalusedfabrics "+cuttingtotalusedfabrics);


					sql="insert into TbCuttingInformationDetails (ColorId,SizeGroupId,TotalQty,DozenQty,ExcessQty,ShortQty,UsedFabrics,Type,Date,EntryTime,UserId) "
							+ "values('"+colorId+"','"+sizeGroupId+"','"+ratiototalpcs+"','"+ratiototalbox+"','"+ratiototalexcess+"','"+ratiototalshort+"','0','Ratio',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'"+v.getUserId()+"');";
					//System.out.println("sql "+sql);
					session.createSQLQuery(sql).executeUpdate();

					String sqlcutting="insert into TbCuttingInformationDetails (ColorId,SizeGroupId,TotalQty,DozenQty,ExcessQty,ShortQty,UsedFabrics,Type,Date,EntryTime,UserId) "
							+ "values('"+colorId+"','"+sizeGroupId+"','"+cuttingtotalpcs+"','"+cuttingtotaldozen+"','"+cuttingtotalexcess+"','"+cuttingtotalshort+"','"+cuttingtotalusedfabrics+"','Cutting',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'"+v.getUserId()+"');";
					//System.out.println("sqlcutting "+sqlcutting);
					session.createSQLQuery(sqlcutting).executeUpdate();


					colorsave++;
				}



			}



			//Ratio
			int i=0;
			String tokenResult="";
			StringTokenizer firstToken=new StringTokenizer(resultValue,",");
			while(firstToken.hasMoreTokens()) {



				String itemAutoId ="";


				tokenResult=firstToken.nextToken();

				String holdcolorId="";
				String sizegroupId,sizeId="",ratioQty="";
				StringTokenizer token=new StringTokenizer(tokenResult,":");
				while(token.hasMoreTokens()) {
					colorId=token.nextToken();
					sizegroupId=token.nextToken();
					sizeId=token.nextToken();
					ratioQty=token.nextToken();

					sql="select cuttingAutoId from TbCuttingInformationDetails where CuttingEntryId IS NULL and userId='"+v.getUserId()+"' and ColorId='"+colorId+"' and SizeGroupId='"+sizeGroupId+"' and type='Ratio'";
					System.out.println(sql);
					List<?> list = session.createSQLQuery(sql).list();
					for(Iterator<?> iter = list.iterator(); iter.hasNext();)
					{	
						itemAutoId =  iter.next().toString();	
					}


					sql = "insert into tbSizeValues (linkedAutoId,sizeGroupId,sizeId,sizeQuantity,type,entryTime,userId) values('"+itemAutoId+"','"+sizegroupId+"','"+sizeId+"','"+ratioQty+"','"+SizeValuesType.CUTTING_RATIO.getType()+"',CURRENT_TIMESTAMP,'"+v.getUserId()+"');";
					session.createSQLQuery(sql).executeUpdate();

				}

			}

			//Cutting
			String tokenCuttingResult="";
			StringTokenizer firstCuttingToken=new StringTokenizer(cuttinglistvalue,",");
			while(firstCuttingToken.hasMoreTokens()) {



				String itemAutoId ="";


				tokenCuttingResult=firstCuttingToken.nextToken();

				String holdcolorId="";
				String sizegroupId,sizeId="",ratioQty="";
				StringTokenizer token=new StringTokenizer(tokenCuttingResult,":");
				while(token.hasMoreTokens()) {
					colorId=token.nextToken();
					sizegroupId=token.nextToken();
					sizeId=token.nextToken();
					ratioQty=token.nextToken();

					sql="select cuttingAutoId from TbCuttingInformationDetails where CuttingEntryId IS NULL and userId='"+v.getUserId()+"' and ColorId='"+colorId+"' and SizeGroupId='"+sizeGroupId+"' and type='Cutting'";
					System.out.println(sql);
					List<?> list = session.createSQLQuery(sql).list();
					for(Iterator<?> iter = list.iterator(); iter.hasNext();)
					{	
						itemAutoId =  iter.next().toString();	
					}


					sql = "insert into tbSizeValues (linkedAutoId,sizeGroupId,sizeId,sizeQuantity,type,entryTime,userId) values('"+itemAutoId+"','"+sizegroupId+"','"+sizeId+"','"+ratioQty+"','"+SizeValuesType.CUTTING_QTY.getType()+"',CURRENT_TIMESTAMP,'"+v.getUserId()+"');";
					session.createSQLQuery(sql).executeUpdate();

				}

			}

			if(colorsave!=0) {
				sql = "insert into TbCuttingInformationSummary ("
						+ "CuttingEntryId,"
						+ "BuyerId,"
						+ "purchaseOrder,"
						+ "StyleId,"
						+ "ItemId,"
						+ "CuttingNo,"
						+ "CuttingDate,"
						+ "FactoryId,"
						+ "DepartmentId,"
						+ "LineId,"
						+ "InchargeId,"
						+ "MarkingLayer,"
						+ "MarkingLenght,"
						+ "MarkingWidth,"
						+ "Date,"
						+ "entryTime,"
						+ "userId) values('"+cuttingEntryId+"',"
						+ "'"+v.getBuyerId()+"',"
						+ "'"+v.getPurchaseOrder()+"',"
						+ "'"+v.getStyleId()+"',"
						+ "'"+v.getItemId()+"',"
						+ "'"+v.getCuttingNo()+"',"
						+ "'"+v.getCuttingDate()+"',"
						+ "'"+v.getFactoryId()+"',"
						+ "'"+v.getDepartmentId()+"',"
						+ "'"+v.getLineId()+"',"
						+ "'"+v.getInchargeId()+"',"
						+ "'"+v.getMarkingLayer()+"',"
						+ "'"+v.getMarkingLength()+"',"
						+ "'"+v.getMarkingWidth()+"',"
						+ "CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'"+v.getUserId()+"');";
				session.createSQLQuery(sql).executeUpdate();

				String updateSql="update TbCuttingInformationDetails set CuttingEntryId='"+cuttingEntryId+"' where UserId='"+v.getUserId()+"' and CuttingEntryId IS NULL";
				session.createSQLQuery(updateSql).executeUpdate();
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
	public List<CuttingInformation> getCuttingInformationList() {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<CuttingInformation> dataList=new ArrayList<CuttingInformation>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select a.CuttingEntryId,(select Name from tbBuyer where id=a.BuyerId) as BuyerName,a.purchaseOrder,(select StyleNo from TbStyleCreate where StyleId=a.StyleId) as StyleName,(select ItemName from tbItemDescription where ItemId=a.ItemId) as ItemName,a.CuttingNo,convert(varchar,a.CuttingDate,23) as CuttingDate from TbCuttingInformationSummary a";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();

				dataList.add(new CuttingInformation(element[0].toString(), element[1].toString(),element[2].toString(),element[3].toString(),element[4].toString(),element[5].toString(),element[6].toString()));
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
	public List<Style> stylename() {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<Style> countrylist=new ArrayList<>();

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select a.StyleId, a.StyleNo from TbStyleCreate a";
			System.out.println(" countries ");

			List<?> list = session.createSQLQuery(sql).list();


			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				countrylist.add(new Style(element[0].toString(),element[1].toString()));

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

		return countrylist;
	}

	@Override
	public List<Line> getLineNames() {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<Line> lineList=new ArrayList<>();

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select Lineid, LineName from tblinecreate where FactoryId=2";
			System.out.println(" countries ");

			List<?> list = session.createSQLQuery(sql).list();


			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				lineList.add(new Line(element[0].toString(),element[1].toString()));

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

		return lineList;
	}


	@Override
	public String InserLines(SewingLinesModel linemodels) {
		String Status="";
		String success="";
		int failcount=0;

		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();

			int count=0;
			for (int i = 0; i < linemodels.getLine().length; i++) {

				//boolean s=checkLineStatus(linemodels.getStart(), linemodels.getEnd(), linemodels.getLine()[i]);

				boolean occupied=false;

				String sql="select lineid from tbsewinglinesetup where lineid='"+linemodels.getLine()[i]+"' and (startdate between '"+linemodels.getStart()+"' and '"+linemodels.getEnd()+"' or enddate between '"+linemodels.getStart()+"' and '"+linemodels.getEnd()+"') and selectUnselect='1'";
				//	String sql="select lineid from tbsewinglinesetup where lineid='"+linemodels.getLine()[i]+"' and startdate between '"+linemodels.getStart()+"' and '"+linemodels.getEnd()+"'";


				List<?> list = session.createSQLQuery(sql).list();


				for(Iterator<?> iter = list.iterator(); iter.hasNext();)
				{	
					//Object[] element = (Object[]) iter.next();

					//lineList.add(new Line(element[0].toString(),element[1].toString()));
					occupied=true;
					System.out.println("occupied "+occupied);
					break;

				}

				System.out.println(linemodels.getLine()[i]+" is free "+occupied);


				//boolean occupied=checkLineStatus(linemodels.getStart(), linemodels.getEnd(), linemodels.getLine()[i]);
				System.out.println(" occupied "+occupied);

				if (!occupied) {
					int selectionid=0;
					sql="select isnull(max(selectionId),0)+1 from tbSewingLineSetup";
					System.out.println(" Line status ");

					List<?> list1 = session.createSQLQuery(sql).list();


					for(Iterator<?> iter = list1.iterator(); iter.hasNext();)
					{	
						//Object[] element = (Object[]) iter.next();

						//lineList.add(new Line(element[0].toString(),element[1].toString()));
						selectionid=Integer.parseInt(iter.next().toString());


					}

					System.out.println("selectionid");
					sql="insert into tbsewinglinesetup (selectionId,BuyerOrderId,PoNo,styleid,itemId,startdate,enddate,duration,lineId,selectUnselect,entryby,entrytime) values ('"+selectionid+"','"+linemodels.getBuyerOrderId()+"','"+linemodels.getPoNo()+"','"+linemodels.getStyle()+"','"+linemodels.getItemId()+"','"+linemodels.getStart()+"', '"+linemodels.getEnd()+"','"+linemodels.getDuration()+"','"+linemodels.getLine()[i]+"','1','"+linemodels.getUser()+"', GETDATE())";
					session.createSQLQuery(sql).executeUpdate();

					count++;

				}else if(occupied){
					if (Status.isEmpty()) {
						Status=linemodels.getLine()[i];
						failcount++;
					}else {
						Status=Status+", "+linemodels.getLine()[i];
						failcount++;
					}


				}
				//occupied=false;
			}

			System.out.println(" status "+Status);

			if (count>0) {
				if (failcount==0) {
					success="Successful";
				}else {
					success="Successful"+" and "+Status;
				}
			}
			tx.commit();
		}
		catch(Exception ee){

			if (tx != null) {
				tx.rollback();
				return Status;
			}
			ee.printStackTrace();
		}

		finally {
			session.close();
		}

		return success;
	}

	@Override
	public List<SewingLinesModel> Lines() {
		List<SewingLinesModel> ListData=new ArrayList<SewingLinesModel>();

		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();



			String sql="select   selectionId,(select a.StyleNo from TbStyleCreate a where a.StyleId=t1.styleid) as stylie,  stuff((select ',' + (select LineName from TbLineCreate where LineId=t.lineId) from tbSewingLineSetup t  where t.selectionId = t1.selectionId  order by t.[selectionId]  for xml path('')),1,1,'') as lines, startdate, enddate, selectUnselect  from tbSewingLineSetup t1 group by selectionId, styleid,startdate, enddate,selectUnselect";

			List<?> list = session.createSQLQuery(sql).list();


			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				ListData.add(new SewingLinesModel(element[0].toString(),element[1].toString(),element[2].toString(),element[3].toString(),element[4].toString(),element[5].toString(),""));


			}


			System.out.println(" list size "+ListData.size());



			tx.commit();
		}
		catch(Exception ee){

			if (tx != null) {
				tx.rollback();
				return ListData;
			}
			ee.printStackTrace();
		}

		finally {
			session.close();
		}

		return ListData;
	}

	@Override
	public List<SewingLinesModel> getSewingProductionLines() {
		List<SewingLinesModel> ListData=new ArrayList<SewingLinesModel>();

		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();



			String sql="select a.StyleId,(select StyleNo from TbStyleCreate where styleId=a.styleId) as StyleNo,STUFF((SELECT '-'+(select LineName from tbLineCreate where LineId=b.LineId) FROM tbSewingLineSetup b WHERE b.styleId = a.styleId order by styleId FOR XML PATH('')),1,2,' ') as AllLineList,convert(varchar,a.startdate,23) as startdate,convert(varchar,a.enddate,23) as enddate from tbSewingLineSetup a group by a.styleId,a.startdate,a.enddate";

			List<?> list = session.createSQLQuery(sql).list();
			System.out.println("list");

			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				System.out.println("value");

				ListData.add(new SewingLinesModel(element[0].toString(),element[1].toString(),element[2].toString(),element[3].toString(),element[4].toString()));


			}





			tx.commit();
		}
		catch(Exception ee){

			if (tx != null) {
				ee.printStackTrace();
			}
			ee.printStackTrace();
		}

		finally {
			session.close();
		}

		return ListData;
	}

	@Override
	public List<ProductionPlan> getSewingLineSetupinfo(ProductionPlan v) {
		List<ProductionPlan> ListData=new ArrayList<ProductionPlan>();

		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();



			String sql="select a.styleid,(select StyleNo from TbStyleCreate where styleId=a.styleid) as StyleNo,a.itemId,(select ItemName from tbItemDescription where ItemId=a.itemId) as ItemName,a.id,a.duration,a.lineId,(select LineName from TbLineCreate where LineId=a.lineId) as LineName,(select isnull(sum(PlanQty),0)  from TbProductTargetPlan b where b.BuyerOrderId=a.BuyerOrderId and b.PoNo=a.PoNo and b.styleid=a.styleid and b.itemId=a.itemId) as PlanQty from tbSewingLineSetup a where a.BuyerOrderId='"+v.getBuyerorderId()+"' and a.PoNo='"+v.getPurchaseOrder()+"' and a.styleid='"+v.getStyleId()+"' and a.itemId='"+v.getItemId()+"'";

			List<?> list = session.createSQLQuery(sql).list();
			System.out.println("list"+list.size());

			int lineCount=list.size();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				System.out.println("value");

				ListData.add(new ProductionPlan(element[0].toString(),element[1].toString(),element[2].toString(),element[3].toString(),element[4].toString(),element[5].toString(),element[6].toString(),element[7].toString(),element[8].toString(),lineCount));

			}





			tx.commit();
		}
		catch(Exception ee){

			if (tx != null) {
				ee.printStackTrace();
			}
			ee.printStackTrace();
		}

		finally {
			session.close();
		}

		return ListData;
	}

	@Override
	public boolean saveSewingLayoutDetails(ProductionPlan v) {


		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();

			int temp=0;
			
			String checksql="select StyleId from tbSewingProductionDetails where PurchaseOrder='"+v.getPurchaseOrder()+"' and StyleId='"+v.getStyleId()+"' and ItemId='"+v.getItemId()+"' and LineId='"+v.getLineId()+"' and date='"+v.getLayoutDate()+"' and Type='1'";

			List<?> list = session.createSQLQuery(checksql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				temp=1;
				break;
			}
			
			if(temp==0) {
				String resultValue=v.getResultlist().substring(v.getResultlist().indexOf("[")+1, v.getResultlist().indexOf("]"));
				System.out.println("resultValue "+resultValue);
				StringTokenizer firstToken=new StringTokenizer(resultValue,",");
				while(firstToken.hasMoreTokens()) {
					String secondToken=firstToken.nextToken();
					StringTokenizer thirdToken=new StringTokenizer(secondToken,"*");
					while(thirdToken.hasMoreTokens()) {
						String machineId=thirdToken.nextToken();
						String employeeId=thirdToken.nextToken();
						String colorId=thirdToken.nextToken();
						String sizeId=thirdToken.nextToken();
						String totalQty=thirdToken.nextToken();
						String planvalue=thirdToken.nextToken();
						

							//Passed
							StringTokenizer layoutToken=new StringTokenizer(planvalue, ":");
							while(layoutToken.hasMoreTokens()) {
								String h1=layoutToken.nextToken();
								String h2=layoutToken.nextToken();
								String h3=layoutToken.nextToken();
								String h4=layoutToken.nextToken();
								String h5=layoutToken.nextToken();
								String h6=layoutToken.nextToken();
								String h7=layoutToken.nextToken();
								String h8=layoutToken.nextToken();
								String h9=layoutToken.nextToken();
								String h10=layoutToken.nextToken();



								String productionSql="insert into tbSewingProductionDetails ("
										+ "BuyerId,"
										+ "BuyerOrderId,"
										+ "PurchaseOrder,"
										+ "StyleId,"
										+ "ItemId,"
										+ "ColorId,"
										+ "LineId,"
										+ "MachineId,"
										+ "EmployeeId,"
										+ "SizeId,"
										+ "Type,"
										+ "DailyTarget,"
										+ "LineTarget,"
										+ "HourlyTarget,"
										+ "Hours,"
										+ "hour1,"
										+ "hour2,"
										+ "hour3,"
										+ "hour4,"
										+ "hour5,"
										+ "hour6,"
										+ "hour7,"
										+ "hour8,"
										+ "hour9,"
										+ "hour10,"
										+ "total,"
										+ "date,"
										+ "entrytime,"
										+ "userId) values ("
										+ "'"+v.getBuyerId()+"',"
										+ "'"+v.getBuyerorderId()+"',"
										+ "'"+v.getPurchaseOrder()+"',"
										+ "'"+v.getStyleId()+"',"
										+ "'"+v.getItemId()+"',"
										+ "'"+colorId+"',"
										+ "'"+v.getLineId()+"',"
										+ "'"+machineId+"',"
										+ "'"+employeeId+"',"
										+ "'"+sizeId+"',"
										+ "'1',"
										+ "'"+v.getDailyTarget()+"',"
										+ "'"+v.getDailyLineTarget()+"',"
										+ "'"+v.getHourlyTarget()+"',"
										+ "'10',"
										+ "'"+h1+"',"
										+ "'"+h2+"',"
										+ "'"+h3+"',"
										+ "'"+h4+"',"
										+ "'"+h5+"',"				
										+ "'"+h6+"',"
										+ "'"+h7+"',"
										+ "'"+h8+"',"
										+ "'"+h9+"',"
										+ "'"+h10+"',"
										+ "'"+totalQty+"','"+v.getLayoutDate()+"',CURRENT_TIMESTAMP,'"+v.getUserId()+"'"
										+ ")";
								session.createSQLQuery(productionSql).executeUpdate();
							}
			




					}
				}
			}
			else {
				return false;
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

	private boolean checkExistDataForSameLineDate(String purchaseOrder, String styleId, String itemId, String Date,
			String lineId) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<ProductionPlan> dataList=new ArrayList<ProductionPlan>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select StyleId from tbSewingProductionDetails where PurchaseOrder='"+purchaseOrder+"' and StyleId='"+styleId+"' and ItemId='"+itemId+"' and LineId='"+lineId+"' and date='"+Date+"'";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				return true;	
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
		return false;
	}

	@Override
	public List<ProductionPlan> getSewingProductionReport(String Type) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<ProductionPlan> dataList=new ArrayList<ProductionPlan>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select (select name from tbBuyer where id=a.BuyerId) as BuyerName,a.BuyerId,a.BuyerOrderId,a.PurchaseOrder,(select StyleNo from TbStyleCreate where StyleId=a.StyleId) as StyleNo,a.styleId,(select ItemName from tbItemDescription where itemid=a.itemId) as ItemName,a.ItemId,a.LineId,(select LineName from TbLineCreate where LineId=a.LineId) as LineName,convert(varchar,a.date,23) as Date from tbSewingProductionDetails a where a.Type='"+Type+"' group by a.BuyerId,a.BuyerOrderId,a.PurchaseOrder,a.StyleId,a.ItemId,a.LineId,a.date";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();

				dataList.add(new ProductionPlan(element[0].toString(), element[1].toString(),element[2].toString(),element[3].toString(),element[4].toString(),element[5].toString(),element[6].toString(),element[7].toString(),element[8].toString(),element[9].toString(),element[10].toString()));
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
	public List<ProductionPlan> viewSewingProduction(String buyerId, String buyerorderId, String styleId, String itemId,
			String productionDate) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<ProductionPlan> dataList=new ArrayList<ProductionPlan>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select AutoId,BuyerId,BuyerOrderId,PurchaseOrder,StyleId,ItemId,LineId,proudctionType,DailyTarget,HourlyTarget,Hours,hour1,hour2,hour3,hour4,hour5,hour6,hour7,hour8,hour9,hour10,total,convert(varchar,a.date,23) as Date,(select name from tbBuyer where id=a.BuyerId) as BuyerName,(select StyleNo from TbStyleCreate where StyleId=a.StyleId) as StyleNo,(select ItemName from tbItemDescription where itemid=a.itemId) as ItemName,(select LineName from TbLineCreate where LineId=a.LineId) as LineName from tbSewingProductionDetails a where a.BuyerId='"+buyerId+"' and a.BuyerOrderId='"+buyerorderId+"' and a.StyleId='"+styleId+"' and a.ItemId='"+itemId+"' and a.date='"+productionDate+"' and proudctionType='Sewing Production'";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();

				dataList.add(new ProductionPlan(element[0].toString(), element[1].toString(),element[2].toString(),element[3].toString(),element[4].toString(),element[5].toString(),element[6].toString(),element[7].toString(),element[8].toString(),element[9].toString(),element[10].toString(),element[11].toString(),element[12].toString(),element[13].toString(),element[14].toString(),element[15].toString(),element[16].toString(),element[17].toString(),element[18].toString(),element[19].toString(),element[20].toString(),element[21].toString(),element[22].toString(),element[23].toString(),element[24].toString(),element[25].toString(),element[26].toString()));
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
	public boolean saveFinishProductionDetails(ProductionPlan v) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();

			String resultValue=v.getResultlist().substring(v.getResultlist().indexOf("[")+1, v.getResultlist().indexOf("]"));
			System.out.println("resultValue "+resultValue);
			StringTokenizer firstToken=new StringTokenizer(resultValue,",");
			while(firstToken.hasMoreTokens()) {
				String secondToken=firstToken.nextToken();
				StringTokenizer thirdToken=new StringTokenizer(secondToken,"*");
				while(thirdToken.hasMoreTokens()) {
					String lineId=thirdToken.nextToken();
					String dailyTarget=thirdToken.nextToken();
					String sewinghourlytarget=thirdToken.nextToken();
					String passedvalue=thirdToken.nextToken();
					String rejectvalue=thirdToken.nextToken();

					//Passed
					StringTokenizer passedToken=new StringTokenizer(passedvalue, ":");
					while(passedToken.hasMoreTokens()) {
						String h1=passedToken.nextToken();
						String h2=passedToken.nextToken();
						String h3=passedToken.nextToken();
						String h4=passedToken.nextToken();
						String h5=passedToken.nextToken();
						String h6=passedToken.nextToken();
						String h7=passedToken.nextToken();
						String h8=passedToken.nextToken();
						String h9=passedToken.nextToken();
						String h10=passedToken.nextToken();

						String total="0";

						String productionSql="insert into tbSewingProductionDetails ("
								+ "BuyerId,"
								+ "BuyerOrderId,"
								+ "PurchaseOrder,"
								+ "StyleId,"
								+ "ItemId,"
								+ "LineId,"
								+ "proudctionType,"
								+ "DailyTarget,"
								+ "HourlyTarget,"
								+ "Hours,"
								+ "hour1,"
								+ "hour2,"
								+ "hour3,"
								+ "hour4,"
								+ "hour5,"
								+ "hour6,"
								+ "hour7,"
								+ "hour8,"
								+ "hour9,"
								+ "hour10,"
								+ "total,"
								+ "date,"
								+ "entrytime,"
								+ "userId) values ("
								+ "'"+v.getBuyerId()+"',"
								+ "'"+v.getBuyerorderId()+"',"
								+ "'"+v.getPurchaseOrder()+"',"
								+ "'"+v.getStyleId()+"',"
								+ "'"+v.getItemId()+"',"
								+ "'"+lineId+"',"
								+ "'Finish Passed',"
								+ "'"+dailyTarget+"',"
								+ "'"+sewinghourlytarget+"',"
								+ "'10',"
								+ "'"+h1+"',"
								+ "'"+h2+"',"
								+ "'"+h3+"',"
								+ "'"+h4+"',"
								+ "'"+h5+"',"				
								+ "'"+h6+"',"
								+ "'"+h7+"',"
								+ "'"+h8+"',"
								+ "'"+h9+"',"
								+ "'"+h10+"',"
								+ "'"+total+"',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'"+v.getUserId()+"'"
								+ ")";
						session.createSQLQuery(productionSql).executeUpdate();
					}

					//Reject
					StringTokenizer rejectToken=new StringTokenizer(rejectvalue, ":");
					while(rejectToken.hasMoreTokens()) {
						String h1=rejectToken.nextToken();
						String h2=rejectToken.nextToken();
						String h3=rejectToken.nextToken();
						String h4=rejectToken.nextToken();
						String h5=rejectToken.nextToken();
						String h6=rejectToken.nextToken();
						String h7=rejectToken.nextToken();
						String h8=rejectToken.nextToken();
						String h9=rejectToken.nextToken();
						String h10=rejectToken.nextToken();

						String total="0";

						String productionSql="insert into tbSewingProductionDetails ("
								+ "BuyerId,"
								+ "BuyerOrderId,"
								+ "PurchaseOrder,"
								+ "StyleId,"
								+ "ItemId,"
								+ "LineId,"
								+ "proudctionType,"
								+ "DailyTarget,"
								+ "HourlyTarget,"
								+ "Hours,"
								+ "hour1,"
								+ "hour2,"
								+ "hour3,"
								+ "hour4,"
								+ "hour5,"
								+ "hour6,"
								+ "hour7,"
								+ "hour8,"
								+ "hour9,"
								+ "hour10,"
								+ "total,"
								+ "date,"
								+ "entrytime,"
								+ "userId) values ("
								+ "'"+v.getBuyerId()+"',"
								+ "'"+v.getBuyerorderId()+"',"
								+ "'"+v.getPurchaseOrder()+"',"
								+ "'"+v.getStyleId()+"',"
								+ "'"+v.getItemId()+"',"
								+ "'"+lineId+"',"
								+ "'Finish Reject',"
								+ "'"+dailyTarget+"',"
								+ "'"+sewinghourlytarget+"',"
								+ "'10',"
								+ "'"+h1+"',"
								+ "'"+h2+"',"
								+ "'"+h3+"',"
								+ "'"+h4+"',"
								+ "'"+h5+"',"				
								+ "'"+h6+"',"
								+ "'"+h7+"',"
								+ "'"+h8+"',"
								+ "'"+h9+"',"
								+ "'"+h10+"',"
								+ "'"+total+"',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'"+v.getUserId()+"'"
								+ ")";
						session.createSQLQuery(productionSql).executeUpdate();
					}

				}
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
	public List<ProductionPlan> viewSewingFinishingProduction(String buyerId, String buyerorderId, String styleId,
			String itemId, String productionDate) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<ProductionPlan> dataList=new ArrayList<ProductionPlan>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select AutoId,BuyerId,BuyerOrderId,PurchaseOrder,StyleId,ItemId,LineId,proudctionType,DailyTarget,HourlyTarget,Hours,hour1,hour2,hour3,hour4,hour5,hour6,hour7,hour8,hour9,hour10,total,convert(varchar,a.date,23) as Date,(select name from tbBuyer where id=a.BuyerId) as BuyerName,(select StyleNo from TbStyleCreate where StyleId=a.StyleId) as StyleNo,(select ItemName from tbItemDescription where itemid=a.itemId) as ItemName,(select LineName from TbLineCreate where LineId=a.LineId) as LineName from tbSewingProductionDetails a where a.BuyerId='"+buyerId+"' and a.BuyerOrderId='"+buyerorderId+"' and a.StyleId='"+styleId+"' and a.ItemId='"+itemId+"' and a.date='"+productionDate+"'  order by lineId";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();

				dataList.add(new ProductionPlan(element[0].toString(), element[1].toString(),element[2].toString(),element[3].toString(),element[4].toString(),element[5].toString(),element[6].toString(),element[7].toString(),element[8].toString(),element[9].toString(),element[10].toString(),element[11].toString(),element[12].toString(),element[13].toString(),element[14].toString(),element[15].toString(),element[16].toString(),element[17].toString(),element[18].toString(),element[19].toString(),element[20].toString(),element[21].toString(),element[22].toString(),element[23].toString(),element[24].toString(),element[25].toString(),element[26].toString()));
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
	public boolean saveInceptionLayoutDetails(ProductionPlan v) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();

			String resultValue=v.getResultlist().substring(v.getResultlist().indexOf("[")+1, v.getResultlist().indexOf("]"));
			System.out.println("resultValue "+resultValue);
			StringTokenizer firstToken=new StringTokenizer(resultValue,",");
			while(firstToken.hasMoreTokens()) {
				String secondToken=firstToken.nextToken();
				StringTokenizer thirdToken=new StringTokenizer(secondToken,"*");
				while(thirdToken.hasMoreTokens()) {
					String employyeId=thirdToken.nextToken();
					String lineId=thirdToken.nextToken();
					String totalQty=thirdToken.nextToken();
					String layoutvalue=thirdToken.nextToken();


					//Passed
					StringTokenizer layoutToken=new StringTokenizer(layoutvalue, ":");
					while(layoutToken.hasMoreTokens()) {
						String h1=layoutToken.nextToken();
						String h2=layoutToken.nextToken();
						String h3=layoutToken.nextToken();
						String h4=layoutToken.nextToken();
						String h5=layoutToken.nextToken();
						String h6=layoutToken.nextToken();
						String h7=layoutToken.nextToken();
						String h8=layoutToken.nextToken();
						String h9=layoutToken.nextToken();
						String h10=layoutToken.nextToken();



						String productionSql="insert into tbLayoutPlanDetails ("
								+ "BuyerId,"
								+ "BuyerOrderId,"
								+ "PurchaseOrder,"
								+ "StyleId,"
								+ "ItemId,"
								+ "LineId,"
								+ "EmployeeId,"
								+ "Type,"
								+ "DailyTarget,"
								+ "LineTarget,"
								+ "HourlyTarget,"
								+ "Hours,"
								+ "hour1,"
								+ "hour2,"
								+ "hour3,"
								+ "hour4,"
								+ "hour5,"
								+ "hour6,"
								+ "hour7,"
								+ "hour8,"
								+ "hour9,"
								+ "hour10,"
								+ "total,"
								+ "date,"
								+ "entrytime,"
								+ "userId) values ("
								+ "'"+v.getBuyerId()+"',"
								+ "'"+v.getBuyerorderId()+"',"
								+ "'"+v.getPurchaseOrder()+"',"
								+ "'"+v.getStyleId()+"',"
								+ "'"+v.getItemId()+"',"
								+ "'"+lineId+"',"
								+ "'"+employyeId+"',"
								+ "'"+v.getLayoutName()+"',"
								+ "'"+v.getDailyTarget()+"',"
								+ "'"+v.getDailyLineTarget()+"',"
								+ "'"+v.getHourlyTarget()+"',"
								+ "'10',"
								+ "'"+h1+"',"
								+ "'"+h2+"',"
								+ "'"+h3+"',"
								+ "'"+h4+"',"
								+ "'"+h5+"',"				
								+ "'"+h6+"',"
								+ "'"+h7+"',"
								+ "'"+h8+"',"
								+ "'"+h9+"',"
								+ "'"+h10+"',"
								+ "'"+totalQty+"','"+v.getLayoutDate()+"',CURRENT_TIMESTAMP,'"+v.getUserId()+"'"
								+ ")";
						session.createSQLQuery(productionSql).executeUpdate();
					}

				}
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
	public List<ProductionPlan> getLayoutPlanDetails(String type) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<ProductionPlan> dataList=new ArrayList<ProductionPlan>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select (select name from tbBuyer where id=a.BuyerId) as BuyerName,a.BuyerId,a.BuyerOrderId,a.PurchaseOrder,(select StyleNo from TbStyleCreate where StyleId=a.StyleId) as StyleNo,a.styleId,(select ItemName from tbItemDescription where itemid=a.itemId) as ItemName,a.ItemId,convert(varchar,a.date,23) as Date from tbLayoutPlanDetails a  where a.Type='"+type+"' group by a.BuyerId,a.BuyerOrderId,a.PurchaseOrder,a.StyleId,a.ItemId,a.date";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();

				dataList.add(new ProductionPlan(element[0].toString(), element[1].toString(),element[2].toString(),element[3].toString(),element[4].toString(),element[5].toString(),element[6].toString(),element[7].toString(),element[8].toString(),""));
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
	public List<ProductionPlan> getLineWiseMachineList(ProductionPlan v) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<ProductionPlan> dataList=new ArrayList<ProductionPlan>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select MachineId,MachineName,OperatorId,(select Name from TbEmployeeInfo where AutoId=TbMachineInfo.OperatorId) as OperatorName from TbMachineInfo where LineId='"+v.getLineId()+"'";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();

				dataList.add(new ProductionPlan(element[0].toString(), element[1].toString(),element[2].toString(),element[3].toString()));
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
	public List<ProductionPlan> getSizeListForProduction(ProductionPlan v) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<ProductionPlan> dataList=new ArrayList<ProductionPlan>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select a.ColorId,(select colorName from tbColors where ColorId=a.ColorId) as ColorName,a.sizeGroupId,b.sizeId,(select sizename from tbStyleSize  where id=b.sizeId and groupId=b.sizeGroupId) as sizeName from TbBuyerOrderEstimateDetails a join tbSizeValues b on b.linkedAutoId=a.autoId where a.BuyerOrderId='"+v.getBuyerorderId()+"' and a.PurchaseOrder='"+v.getPurchaseOrder()+"' and a.styleid='"+v.getStyleId()+"' and a.itemId='"+v.getItemId()+"' order by a.ItemId,a.ColorId,a.sizeGroupId";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();


				dataList.add(new ProductionPlan(element[0].toString(), element[1].toString(),element[2].toString(),element[3].toString(),element[4].toString()));
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
	public List<ProductionPlan> getSewingLayoutLineProduction(ProductionPlan v) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<ProductionPlan> dataList=new ArrayList<ProductionPlan>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select a.AutoId,a.BuyerId,a.BuyerOrderId,a.PurchaseOrder,a.StyleId,a.ItemId,a.ColorId,a.LineId,a.MachineId,a.EmployeeId,a.SizeId,a.DailyTarget,a.LineTarget,a.HourlyTarget,(select Name from tbBuyer where id=a.BuyerId) as BuyerName,(select StyleNo from TbStyleCreate where StyleId=a.StyleId) as StyleNo,(select ItemName from tbItemDescription where ItemId=a.ItemId) as ItemName,(select LineName from TbLineCreate where LineId=a.LineId) as LineName,(select sizeName from tbStyleSize where id=a.SizeId ) as SizeName,(select ColorName from tbColors where ColorId=a.ColorId ) as ColorName,(select MachineName from TbMachineInfo where MachineId=a.MachineId ) as MachineName,(select isnull(sum(PlanQty),0)  from TbProductTargetPlan b where b.BuyerOrderId=a.BuyerOrderId and b.PoNo=a.PurchaseOrder and b.styleid=a.styleid and b.itemId=a.itemId) as PlanQty from tbSewingProductionDetails a where a.BuyerId='"+v.getBuyerId()+"' and a.BuyerOrderId='"+v.getBuyerorderId()+"' and a.StyleId='"+v.getStyleId()+"' and a.ItemId='"+v.getItemId()+"' and a.LineId='"+v.getLineId()+"' and a.date='"+v.getLayoutDate()+"'";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();


				dataList.add(new ProductionPlan(element[0].toString(), element[1].toString(),element[2].toString(),element[3].toString(),element[4].toString(),element[5].toString(),element[6].toString(),element[7].toString(),element[8].toString(),element[9].toString(),element[10].toString(),element[11].toString(),element[12].toString(),element[13].toString(),element[14].toString(),element[15].toString(),element[16].toString(),element[17].toString(),element[18].toString(),element[19].toString(),element[20].toString(),element[21].toString()));
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
	public boolean saveSewingProductionDetails(ProductionPlan v) {

		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();

			int temp=0;
			
			String checksql="select StyleId from tbSewingProductionDetails where PurchaseOrder='"+v.getPurchaseOrder()+"' and StyleId='"+v.getStyleId()+"' and ItemId='"+v.getItemId()+"' and LineId='"+v.getLineId()+"' and date='"+v.getLayoutDate()+"' and Type='2'";

			List<?> list = session.createSQLQuery(checksql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				temp=1;
				break;
			}
			
			if(temp==0) {
				String resultValue=v.getResultlist().substring(v.getResultlist().indexOf("[")+1, v.getResultlist().indexOf("]"));
				System.out.println("resultValue "+resultValue);
				StringTokenizer firstToken=new StringTokenizer(resultValue,",");
				while(firstToken.hasMoreTokens()) {
					String secondToken=firstToken.nextToken();
					StringTokenizer thirdToken=new StringTokenizer(secondToken,"*");
					while(thirdToken.hasMoreTokens()) {
						String machineId=thirdToken.nextToken();
						String employeeId=thirdToken.nextToken();
						String colorId=thirdToken.nextToken();
						String sizeId=thirdToken.nextToken();
						String totalQty=thirdToken.nextToken();
						String planvalue=thirdToken.nextToken();
						

							//Passed
							StringTokenizer layoutToken=new StringTokenizer(planvalue, ":");
							while(layoutToken.hasMoreTokens()) {
								String h1=layoutToken.nextToken();
								String h2=layoutToken.nextToken();
								String h3=layoutToken.nextToken();
								String h4=layoutToken.nextToken();
								String h5=layoutToken.nextToken();
								String h6=layoutToken.nextToken();
								String h7=layoutToken.nextToken();
								String h8=layoutToken.nextToken();
								String h9=layoutToken.nextToken();
								String h10=layoutToken.nextToken();



								String productionSql="insert into tbSewingProductionDetails ("
										+ "BuyerId,"
										+ "BuyerOrderId,"
										+ "PurchaseOrder,"
										+ "StyleId,"
										+ "ItemId,"
										+ "ColorId,"
										+ "LineId,"
										+ "MachineId,"
										+ "EmployeeId,"
										+ "SizeId,"
										+ "Type,"
										+ "DailyTarget,"
										+ "LineTarget,"
										+ "HourlyTarget,"
										+ "Hours,"
										+ "hour1,"
										+ "hour2,"
										+ "hour3,"
										+ "hour4,"
										+ "hour5,"
										+ "hour6,"
										+ "hour7,"
										+ "hour8,"
										+ "hour9,"
										+ "hour10,"
										+ "total,"
										+ "date,"
										+ "entrytime,"
										+ "userId) values ("
										+ "'"+v.getBuyerId()+"',"
										+ "'"+v.getBuyerorderId()+"',"
										+ "'"+v.getPurchaseOrder()+"',"
										+ "'"+v.getStyleId()+"',"
										+ "'"+v.getItemId()+"',"
										+ "'"+colorId+"',"
										+ "'"+v.getLineId()+"',"
										+ "'"+machineId+"',"
										+ "'"+employeeId+"',"
										+ "'"+sizeId+"',"
										+ "'2',"
										+ "'"+v.getDailyTarget()+"',"
										+ "'"+v.getDailyLineTarget()+"',"
										+ "'"+v.getHourlyTarget()+"',"
										+ "'10',"
										+ "'"+h1+"',"
										+ "'"+h2+"',"
										+ "'"+h3+"',"
										+ "'"+h4+"',"
										+ "'"+h5+"',"				
										+ "'"+h6+"',"
										+ "'"+h7+"',"
										+ "'"+h8+"',"
										+ "'"+h9+"',"
										+ "'"+h10+"',"
										+ "'"+totalQty+"','"+v.getLayoutDate()+"',CURRENT_TIMESTAMP,'"+v.getUserId()+"'"
										+ ")";
								session.createSQLQuery(productionSql).executeUpdate();
							}
			




					}
				}
			}
			else {
				return false;
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


}
