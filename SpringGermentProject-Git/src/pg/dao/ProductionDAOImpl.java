
package pg.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import org.springframework.stereotype.Repository;

import pg.orderModel.PurchaseOrder;
import pg.orderModel.SampleRequisitionItem;
import pg.orderModel.Style;
import pg.proudctionModel.CuttingInformation;
import pg.proudctionModel.SewingLinesModel;
import pg.proudctionModel.ProductionPlan;
import pg.proudctionModel.cuttingRequsition;
import pg.registerModel.Department;
import pg.registerModel.ItemDescription;
import pg.registerModel.Line;
import pg.registerModel.Machine;
import pg.registerModel.Size;
import pg.registerModel.SizeGroup;
import pg.share.HibernateUtil;
import pg.share.ProductionType;
import pg.share.SizeValuesType;
import pg.storeModel.AccessoriesSize;

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
			String resultValue=v.getResultvalue().substring(v.getResultvalue().indexOf("[")+1, v.getResultvalue().indexOf("]"));
			String sizegroupValue=v.getSizegroupvalue().substring(v.getSizegroupvalue().indexOf("[")+1, v.getSizegroupvalue().indexOf("]"));
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
	public List<ProductionPlan> getProductionPlanFromCutting() {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<ProductionPlan> dataList=new ArrayList<ProductionPlan>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select b.name as buyerName,cis.buyerId,boed.buyerOrderId,cis.purchaseOrder,sc.StyleNo,cis.StyleId,id.itemname,cis.itemId,(select ISNULL(sum(PlanQty),0) as PlanQty from TbProductTargetPlan where BuyerOrderId=boed.BuyerOrderId and StyleId=cis.StyleId and ItemId=cis.ItemId ) as PlanQty \n" + 
					"from TbCuttingInformationSummary cis\n" + 
					"left join TbBuyerOrderEstimateDetails boed\n" + 
					"on cis.BuyerId = boed.buyerId and cis.purchaseOrder = boed.PurchaseOrder and cis.StyleId = boed.StyleId and cis.ItemId = boed.ItemId\n" + 
					"left join tbBuyer b\n" + 
					"on cis.BuyerId = b.id\n" + 
					"left join TbStyleCreate sc \n" + 
					"on cis.StyleId = sc.StyleId\n" + 
					"left join tbItemDescription id\n" + 
					"on cis.ItemId = id.itemid\n" + 
					"group by b.name,cis.buyerId,boed.buyerOrderId,cis.purchaseOrder,sc.StyleNo,cis.StyleId,id.itemname,cis.itemId";

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
			
			String resultValue=v.getResultvalue().substring(v.getResultvalue().indexOf("[")+1, v.getResultvalue().indexOf("]"));
			String sizegroupValue=v.getSizegroupvalue().substring(v.getSizegroupvalue().indexOf("[")+1, v.getSizegroupvalue().indexOf("]"));		
			String colorValue=v.getColorlistvalue().substring(v.getColorlistvalue().indexOf("[")+1, v.getColorlistvalue().indexOf("]"));
			String cuttinglistvalue=v.getCuttinglistvalue().substring(v.getCuttinglistvalue().indexOf("[")+1, v.getCuttinglistvalue().indexOf("]"));
			

			int colorsave=0;
			String sql="";
			String colorId="",sizeGroupId="";
			String colorArrElement="";

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

					sql="insert into TbCuttingInformationDetails (ColorId,SizeGroupId,TotalQty,DozenQty,ExcessQty,ShortQty,UsedFabrics,Type,Date,EntryTime,UserId) "
							+ "values('"+colorId+"','"+sizeGroupId+"','"+ratiototalpcs+"','"+ratiototalbox+"','"+ratiototalexcess+"','"+ratiototalshort+"','0','Ratio',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'"+v.getUserId()+"');";
					
					session.createSQLQuery(sql).executeUpdate();

					String sqlcutting="insert into TbCuttingInformationDetails (ColorId,SizeGroupId,TotalQty,DozenQty,ExcessQty,ShortQty,UsedFabrics,Type,Date,EntryTime,UserId) "
							+ "values('"+colorId+"','"+sizeGroupId+"','"+cuttingtotalpcs+"','"+cuttingtotaldozen+"','"+cuttingtotalexcess+"','"+cuttingtotalshort+"','"+cuttingtotalusedfabrics+"','Cutting',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'"+v.getUserId()+"');";
					
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
					break;

				}

				//boolean occupied=checkLineStatus(linemodels.getStart(), linemodels.getEnd(), linemodels.getLine()[i]);
				if (!occupied) {
					int selectionid=0;
					sql="select isnull(max(selectionId),0)+1 from tbSewingLineSetup";

					List<?> list1 = session.createSQLQuery(sql).list();


					for(Iterator<?> iter = list1.iterator(); iter.hasNext();)
					{	
						//Object[] element = (Object[]) iter.next();
						//lineList.add(new Line(element[0].toString(),element[1].toString()));
						selectionid=Integer.parseInt(iter.next().toString());
					}

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
			
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

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
		ProductionPlan tempPlan = null;
		try{
			tx=session.getTransaction();
			tx.begin();



			String sql="select a.styleid,(select StyleNo from TbStyleCreate where styleId=a.styleid) as StyleNo,a.itemId,\n" + 
					"(select ItemName from tbItemDescription where ItemId=a.itemId) as ItemName,a.id,a.duration,a.lineId,\n" + 
					"(select LineName from TbLineCreate where LineId=a.lineId) as LineName,\n" + 
					"(select isnull(sum(PlanQty),0)from TbProductTargetPlan b where b.BuyerOrderId=a.BuyerOrderId and b.PoNo=a.PoNo and b.styleid=a.styleid and b.itemId=a.itemId) as PlanQty,\n" + 
					"isnull(sum(lpd.hour1),0) as hour1,isnull(sum(lpd.hour2),0) as hour2,isnull(sum(lpd.hour3),0) as hour3,isnull(sum(lpd.hour4),0) as hour4,isnull(sum(lpd.hour5),0) as hour5,isnull(sum(lpd.hour6),0) as hour6,isnull(sum(lpd.hour7),0) as hour7,isnull(sum(lpd.hour8),0) as hour8,isnull(sum(lpd.hour9),0) as hour9,isnull(sum(lpd.hour10),0) as hour10,isnull(lpd.EmployeeId,'') as employeeId \n" + 
					"from tbSewingLineSetup a \n" + 
					"left join tbLayoutPlanDetails lpd\n" + 
					"on a.BuyerOrderId = lpd.BuyerOrderId and a.StyleId = lpd.StyleId and a.ItemId = lpd.ItemId and a.lineId = lpd.LineId  and lpd.type='"+ProductionType.LINE_INSPECTION_LAYOUT.getType()+"' \n"
					+ "where a.BuyerOrderId='"+v.getBuyerorderId()+"' and a.PoNo='"+v.getPurchaseOrder()+"' and a.styleid='"+v.getStyleId()+"' and a.itemId='"+v.getItemId()+"' \n"
					+ "group by a.styleid,a.itemId,a.id,a.duration,a.lineId,a.BuyerOrderId,a.PoNo,lpd.EmployeeId";

			List<?> list = session.createSQLQuery(sql).list();
			
			int lineCount=list.size();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				tempPlan = new ProductionPlan(element[0].toString(),element[1].toString(),element[2].toString(),element[3].toString(),element[4].toString(),element[5].toString(),element[6].toString(),element[7].toString(),element[8].toString(),lineCount);
				tempPlan.setHour1(element[9].toString());
				tempPlan.setHour2(element[10].toString());
				tempPlan.setHour3(element[11].toString());
				tempPlan.setHour4(element[12].toString());
				tempPlan.setHour5(element[13].toString());
				tempPlan.setHour6(element[14].toString());
				tempPlan.setHour7(element[15].toString());
				tempPlan.setHour8(element[16].toString());
				tempPlan.setHour9(element[17].toString());
				tempPlan.setHour10(element[18].toString());
				tempPlan.setEmployeeId(element[19].toString());

				ListData.add(tempPlan);

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

			String checksql="select StyleId from tbSewingProductionDetails where PurchaseOrder='"+v.getPurchaseOrder()+"' and StyleId='"+v.getStyleId()+"' and ItemId='"+v.getItemId()+"'  and date='"+v.getLayoutDate()+"' and Type='"+v.getLayoutName()+"'";

			List<?> list = session.createSQLQuery(checksql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				temp=1;
				break;
			}

			if(temp==0) {
				String resultValue=v.getResultlist().substring(v.getResultlist().indexOf("[")+1, v.getResultlist().indexOf("]"));
				
				StringTokenizer firstToken=new StringTokenizer(resultValue,",");
				while(firstToken.hasMoreTokens()) {
					String secondToken=firstToken.nextToken();
					StringTokenizer thirdToken=new StringTokenizer(secondToken,"*");
					while(thirdToken.hasMoreTokens()) {
						String clineId=thirdToken.nextToken();
						String machineId=thirdToken.nextToken();
						String employeeId=thirdToken.nextToken();
						String colorId=thirdToken.nextToken();
						String sizeId=thirdToken.nextToken();
						String totalQty=thirdToken.nextToken();
						String planvalue=thirdToken.nextToken();


						//Passed
						StringTokenizer layoutToken=new StringTokenizer(planvalue, ":");
						while(layoutToken.hasMoreTokens()) {
							String pcsQty=layoutToken.nextToken();
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


							if(v.getLayoutName().equals("1") || v.getLayoutName().equals("2")) {
								clineId=v.getLineId();
							}
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
									+ "pcsQty,"
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
									+ "'"+clineId+"',"
									+ "'"+machineId+"',"
									+ "'"+employeeId+"',"
									+ "'"+sizeId+"',"
									+ "'"+v.getLayoutName()+"',"
									+ "'"+v.getDailyTarget()+"',"
									+ "'"+v.getDailyLineTarget()+"',"
									+ "'"+v.getHourlyTarget()+"',"
									+ "'10',"
									+ "'"+pcsQty+"',"
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
			
			StringTokenizer firstToken=new StringTokenizer(resultValue,",");
			while(firstToken.hasMoreTokens()) {
				String secondToken=firstToken.nextToken();
				StringTokenizer thirdToken=new StringTokenizer(secondToken,"*");
				while(thirdToken.hasMoreTokens()) {

					String employyeId=thirdToken.nextToken();
					String lineId=thirdToken.nextToken();
					String totalProdcutionQty=thirdToken.nextToken();
					String totalQty=thirdToken.nextToken();
					String totalRejectQty=thirdToken.nextToken();
					String prodcutionValue=thirdToken.nextToken();
					String passValue=thirdToken.nextToken();
					String rejectValue=thirdToken.nextToken();

					//Production
					StringTokenizer productionToken=new StringTokenizer(prodcutionValue, ":");
					while(productionToken.hasMoreTokens()) {
						String type=productionToken.nextToken();
						String h1=productionToken.nextToken();
						String h2=productionToken.nextToken();
						String h3=productionToken.nextToken();
						String h4=productionToken.nextToken();
						String h5=productionToken.nextToken();
						String h6=productionToken.nextToken();
						String h7=productionToken.nextToken();
						String h8=productionToken.nextToken();
						String h9=productionToken.nextToken();
						String h10=productionToken.nextToken();



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
								+ "'"+type+"',"
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
								+ "'"+totalProdcutionQty+"','"+v.getLayoutDate()+"',CURRENT_TIMESTAMP,'"+v.getUserId()+"'"
								+ ")";
						session.createSQLQuery(productionSql).executeUpdate();
					}

					
					//Passed
					StringTokenizer layoutToken=new StringTokenizer(passValue, ":");
					while(layoutToken.hasMoreTokens()) {
						String type=layoutToken.nextToken();
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
								+ "'"+type+"',"
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

					
					if(!rejectValue.equals("0")) {

						//Reject
						StringTokenizer rejectToken=new StringTokenizer(rejectValue, ":");
						while(rejectToken.hasMoreTokens()) {
							String type=rejectToken.nextToken();
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
									+ "'"+type+"',"
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
									+ "'"+totalRejectQty+"','"+v.getLayoutDate()+"',CURRENT_TIMESTAMP,'"+v.getUserId()+"'"
									+ ")";
							session.createSQLQuery(productionSql).executeUpdate();
						}
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
	public boolean saveLineProductionDetails(ProductionPlan v) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();

			String resultValue=v.getResultlist().substring(v.getResultlist().indexOf("[")+1, v.getResultlist().indexOf("]"));

			StringTokenizer firstToken=new StringTokenizer(resultValue,",");
			while(firstToken.hasMoreTokens()) {
				String secondToken=firstToken.nextToken();
				StringTokenizer thirdToken=new StringTokenizer(secondToken,"*");
				while(thirdToken.hasMoreTokens()) {

					String employyeId = thirdToken.nextToken();
					String lineId = thirdToken.nextToken();
					String totalProdcutionQty = thirdToken.nextToken();
					String totalQty = thirdToken.nextToken();
					String totalRejectQty = thirdToken.nextToken();
					String prodcutionValue = thirdToken.nextToken();
					String passValue = thirdToken.nextToken();
					String rejectValue = thirdToken.nextToken();

					//Production
					StringTokenizer productionToken=new StringTokenizer(prodcutionValue, ":");
					while(productionToken.hasMoreTokens()) {
						String type=productionToken.nextToken();
						String h1=productionToken.nextToken();
						String h2=productionToken.nextToken();
						String h3=productionToken.nextToken();
						String h4=productionToken.nextToken();
						String h5=productionToken.nextToken();
						String h6=productionToken.nextToken();
						String h7=productionToken.nextToken();
						String h8=productionToken.nextToken();
						String h9=productionToken.nextToken();
						String h10=productionToken.nextToken();


						String sql="select buyerId,buyerOrderId from tbLayoutPlanDetails where buyerId='"+v.getBuyerId()+"' and buyerOrderId='"+v.getBuyerorderId()+"' and purchaseOrder='"+v.getPurchaseOrder()+"' and styleId='"+v.getStyleId()+"' and itemId='"+v.getItemId()+"' and lineId='"+lineId+"' and type='"+type+"' and date='"+v.getLayoutDate()+"'";

						List<?> list = session.createSQLQuery(sql).list();

						if(list.size()>0) {
							sql ="update tbLayoutPlanDetails set "
									+ "hour1='"+h1+"',"
									+ "hour2='"+h2+"',"
									+ "hour3='"+h3+"',"
									+ "hour4='"+h4+"',"
									+ "hour5='"+h5+"',"
									+ "hour6='"+h6+"',"
									+ "hour7='"+h7+"',"
									+ "hour8='"+h8+"',"
									+ "hour9='"+h9+"',"
									+ "hour10='"+h10+"',"
									+ "total='"+totalProdcutionQty+"' where buyerId='"+v.getBuyerId()+"' and buyerOrderId='"+v.getBuyerorderId()+"' and purchaseOrder='"+v.getPurchaseOrder()+"' and styleId='"+v.getStyleId()+"' and itemId='"+v.getItemId()+"' and lineId='"+lineId+"' and type='"+type+"'"; 

						}else {
							sql="insert into tbLayoutPlanDetails ("
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
									+ "'"+type+"',"
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
									+ "'"+totalProdcutionQty+"','"+v.getLayoutDate()+"',CURRENT_TIMESTAMP,'"+v.getUserId()+"'"
									+ ")";
						}
						session.createSQLQuery(sql).executeUpdate();
					}

					
					//Passed
					StringTokenizer layoutToken=new StringTokenizer(passValue, ":");
					while(layoutToken.hasMoreTokens()) {
						String type=layoutToken.nextToken();
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

						String sql="select buyerId,buyerOrderId from tbLayoutPlanDetails where buyerId='"+v.getBuyerId()+"' and buyerOrderId='"+v.getBuyerorderId()+"' and purchaseOrder='"+v.getPurchaseOrder()+"' and styleId='"+v.getStyleId()+"' and itemId='"+v.getItemId()+"' and lineId='"+lineId+"' and type='"+type+"' and date='"+v.getLayoutDate()+"'";

						List<?> list = session.createSQLQuery(sql).list();

						if(list.size()>0) {
							sql ="update tbLayoutPlanDetails set "
									+ "hour1='"+h1+"',"
									+ "hour2='"+h2+"',"
									+ "hour3='"+h3+"',"
									+ "hour4='"+h4+"',"
									+ "hour5='"+h5+"',"
									+ "hour6='"+h6+"',"
									+ "hour7='"+h7+"',"
									+ "hour8='"+h8+"',"
									+ "hour9='"+h9+"',"
									+ "hour10='"+h10+"',"
									+ "total='"+totalProdcutionQty+"' where buyerId='"+v.getBuyerId()+"' and buyerOrderId='"+v.getBuyerorderId()+"' and purchaseOrder='"+v.getPurchaseOrder()+"' and styleId='"+v.getStyleId()+"' and itemId='"+v.getItemId()+"' and lineId='"+lineId+"' and type='"+type+"'"; 

						}else {
							sql = "insert into tbLayoutPlanDetails ("
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
									+ "'"+type+"',"
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
							
						}
						session.createSQLQuery(sql).executeUpdate();
					}
					
					if(!rejectValue.equals("0")) {

						//Reject
						StringTokenizer rejectToken=new StringTokenizer(rejectValue, ":");
						while(rejectToken.hasMoreTokens()) {
							String type=rejectToken.nextToken();
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

							String sql="select buyerId,buyerOrderId from tbLayoutPlanDetails where buyerId='"+v.getBuyerId()+"' and buyerOrderId='"+v.getBuyerorderId()+"' and purchaseOrder='"+v.getPurchaseOrder()+"' and styleId='"+v.getStyleId()+"' and itemId='"+v.getItemId()+"' and lineId='"+lineId+"' and type='"+type+"' and date='"+v.getLayoutDate()+"'";

							List<?> list = session.createSQLQuery(sql).list();

							if(list.size()>0) {
								sql ="update tbLayoutPlanDetails set "
										+ "hour1='"+h1+"',"
										+ "hour2='"+h2+"',"
										+ "hour3='"+h3+"',"
										+ "hour4='"+h4+"',"
										+ "hour5='"+h5+"',"
										+ "hour6='"+h6+"',"
										+ "hour7='"+h7+"',"
										+ "hour8='"+h8+"',"
										+ "hour9='"+h9+"',"
										+ "hour10='"+h10+"',"
										+ "total='"+totalProdcutionQty+"' where buyerId='"+v.getBuyerId()+"' and buyerOrderId='"+v.getBuyerorderId()+"' and purchaseOrder='"+v.getPurchaseOrder()+"' and styleId='"+v.getStyleId()+"' and itemId='"+v.getItemId()+"' and lineId='"+lineId+"' and type='"+type+"'"; 
							}else {
								sql="insert into tbLayoutPlanDetails ("
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
										+ "'"+type+"',"
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
										+ "'"+totalRejectQty+"','"+v.getLayoutDate()+"',CURRENT_TIMESTAMP,'"+v.getUserId()+"'"
										+ ")";
								
							}
							session.createSQLQuery(sql).executeUpdate();
						}
					}
				}
			}

			JSONParser jsonParser = new JSONParser();
			JSONObject processValues = (JSONObject)jsonParser.parse(v.getProcessValues());
			Object lineIdList[] =  processValues.keySet().toArray();
			int type = ProductionType.LINE_REJECT.getType();
			for(int i=0;i<lineIdList.length;i++) {
				String lineId = lineIdList[i].toString();
				JSONObject lineObject = (JSONObject) processValues.get(lineIdList[i]);
				Object hourIdList[] = lineObject.keySet().toArray();

				for(int j=0;j<hourIdList.length;j++) {
					String hourId = hourIdList[j].toString();
					JSONObject hourObject = (JSONObject) lineObject.get(hourIdList[j]);
					Object processIdList[] = hourObject.keySet().toArray();
					for(int k=0; k < processIdList.length ; k++) {
						String processId = processIdList[k].toString();
						processId = processId.substring(processId.indexOf('-')+1);
						JSONObject process = (JSONObject) hourObject.get(processIdList[k]);

						String sql="select buyerId,buyerOrderId from tbProcessValues where buyerId='"+v.getBuyerId()+"' and buyerOrderId='"+v.getBuyerorderId()+"' and purchaseOrder='"+v.getPurchaseOrder()+"' and styleId='"+v.getStyleId()+"' and itemId='"+v.getItemId()+"' and lineId='"+lineId+"'and hourId='"+hourId+"' and processId='"+processId+"' and type='"+type+"' and date='"+v.getLayoutDate()+"' ;";

						List<?> list = session.createSQLQuery(sql).list();
						if(list.size()>0) {
							sql = "update tbProcessValues set processValue='"+process.get("qty")+"',remarks='"+process.get("remarks")+"',isPass='"+(Boolean.valueOf(process.get("isReIssuePass").toString())?1:0)+"',entryTime=CURRENT_TIMESTAMP,userId='"+v.getUserId()+"' where buyerId='"+v.getBuyerId()+"' and buyerOrderId='"+v.getBuyerId()+"' and purchaseOrder='"+v.getPurchaseOrder()+"' and styleId='"+v.getStyleId()+"' and itemId='"+v.getItemId()+"' and lineId='"+lineId+"' and hourId='"+hourId+"' and processId='"+processId+"' and date='"+v.getLayoutDate()+"'; " ;
							session.createSQLQuery(sql).executeUpdate();
						}else {
							
							sql = "insert into tbProcessValues (buyerId,buyerOrderId,purchaseOrder,styleId,itemId,lineId,hourId,processId,processValue,remarks,isPass,date,type,entryTime,userId) \n" + 
									"values('"+v.getBuyerId()+"','"+v.getBuyerorderId()+"','"+v.getPurchaseOrder()+"','"+v.getStyleId()+"','"+v.getItemId()+"','"+lineId+"','"+hourId+"','"+processId+"','"+process.get("qty")+"','"+process.get("remarks")+"','"+(Boolean.valueOf(process.get("isReIssuePass").toString())?1:0)+"','"+v.getLayoutDate()+"','"+type+"',CURRENT_TIMESTAMP,'"+v.getUserId()+"')";

							session.createSQLQuery(sql).executeUpdate();
						}
					}
				}
			}

			tx.commit();
			return true;
		}
		catch(Exception ee){
			ee.printStackTrace();
			if (tx != null) {
				tx.rollback();
				return false;
			}

		}
		finally {
			session.close();
		}

		return false;
	}


	@Override
	public boolean saveInceptionLayoutLineDetails(ProductionPlan v) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();

			String resultValue=v.getResultlist().substring(v.getResultlist().indexOf("[")+1, v.getResultlist().indexOf("]"));
			
			StringTokenizer firstToken=new StringTokenizer(resultValue,",");
			while(firstToken.hasMoreTokens()) {
				String secondToken=firstToken.nextToken();
				StringTokenizer thirdToken=new StringTokenizer(secondToken,"*");
				while(thirdToken.hasMoreTokens()) {
					String machineId=thirdToken.nextToken();
					String processId=thirdToken.nextToken();
					String totalQty=thirdToken.nextToken();
					String totalRejectQty=thirdToken.nextToken();
					String layoutvalue=thirdToken.nextToken();
					String rejectvalue=thirdToken.nextToken();


					//Passed
					StringTokenizer layoutToken=new StringTokenizer(layoutvalue, ":");
					while(layoutToken.hasMoreTokens()) {
						String type=layoutToken.nextToken();
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
								+ "machineId,"
								+ "processId,"
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
								+ "'"+v.getLineId()+"',"
								+ "'"+v.getEmployeeId()+"',"
								+ "'"+type+"',"
								+ "'"+v.getDailyTarget()+"',"
								+ "'"+v.getDailyLineTarget()+"',"
								+ "'"+v.getHourlyTarget()+"',"
								+ "'"+machineId+"',"
								+ "'"+processId+"',"
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

			String sql="select (select name from tbBuyer where id=a.BuyerId) as BuyerName,a.BuyerId,a.BuyerOrderId,a.PurchaseOrder,(select StyleNo from TbStyleCreate where StyleId=a.StyleId) as StyleNo,a.styleId,(select ItemName from tbItemDescription where itemid=a.itemId) as ItemName,a.ItemId,convert(varchar,a.date,23) as Date,(select ISNULL(sum(PlanQty),0) as PlanQty from TbProductTargetPlan where BuyerOrderId=a.BuyerOrderId and StyleId=a.StyleId and ItemId=a.ItemId ) as PlanQty  from tbLayoutPlanDetails a  where a.Type='"+type+"' group by a.BuyerId,a.BuyerOrderId,a.PurchaseOrder,a.StyleId,a.ItemId,a.date";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();

				dataList.add(new ProductionPlan(element[0].toString(), element[1].toString(),element[2].toString(),element[3].toString(),element[4].toString(),element[5].toString(),element[6].toString(),element[7].toString(),element[8].toString(),element[9].toString()));
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
	public List<ProductionPlan> getInspectionLayoutList(String type) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<ProductionPlan> dataList=new ArrayList<ProductionPlan>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select (select name from tbBuyer where id=a.BuyerId) as BuyerName,a.BuyerId,a.BuyerOrderId,a.PurchaseOrder,(select StyleNo from TbStyleCreate where StyleId=a.StyleId) as StyleNo,a.styleId,(select ItemName from tbItemDescription where itemid=a.itemId) as ItemName,a.ItemId,convert(varchar,a.date,23) as Date,(select ISNULL(sum(PlanQty),0) as PlanQty from TbProductTargetPlan where BuyerOrderId=a.BuyerOrderId and StyleId=a.StyleId and ItemId=a.ItemId ) as PlanQty,a.lineId,lc.LineName  \n" + 
					"from tbLayoutPlanDetails a \n" + 
					"left join TbLineCreate lc \n" + 
					"on a.LineId = lc.LineId "
					+ "  where a.Type='"+type+"' group by a.BuyerId,a.BuyerOrderId,a.PurchaseOrder,a.StyleId,a.ItemId,a.date,a.lineId,lc.LineName";
			ProductionPlan temp = null;
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();
				temp = new ProductionPlan(element[0].toString(), element[1].toString(),element[2].toString(),element[3].toString(),element[4].toString(),element[5].toString(),element[6].toString(),element[7].toString(),element[8].toString(),element[9].toString());
				temp.setLineId(element[10].toString());
				temp.setLineName(element[11].toString());
				dataList.add(temp);
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
	public List<Machine> getLineWiseMachineListByLineId(String  lineId) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<Machine> dataList=new ArrayList<Machine>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select MachineId,MachineName,OperatorId,(select Name from TbEmployeeInfo where AutoId=TbMachineInfo.OperatorId) as OperatorName from TbMachineInfo where LineId='"+lineId+"'";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();

				dataList.add(new Machine(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString()));
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

	@Override
	public List<ProductionPlan> getLayoutData(ProductionPlan productionPlan) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<ProductionPlan> dataList=new ArrayList<ProductionPlan>();
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="select a.AutoId,a.BuyerId,b.name buyerName,a.BuyerOrderId,a.PurchaseOrder,a.StyleId,sc.StyleNo,a.ItemId,id.itemname,a.LineId,lc.LineName,a.EmployeeId,isnull(e.name,'') as employeeName,a.Type,isnull(a.proudctionType,'') as productionType,ptp.OrderQty,ptp.PlanQty,a.DailyTarget,a.LineTarget,a.HourlyTarget,a.Hours,a.hour1,a.hour2,a.hour3,a.hour4,a.hour5,a.hour6,a.hour7,a.hour8,a.hour9,a.hour10,a.total,(select convert(varchar,a.date,103))as date,a.userId\r\n" + 
					"from tbLayoutPlanDetails a\r\n" + 
					"left join TbProductTargetPlan ptp\r\n" + 
					"on a.BuyerId = ptp.BuyerId and a.BuyerOrderId = ptp.BuyerOrderId and a.StyleId = ptp.StyleId and a.ItemId = ptp.ItemId\r\n" + 
					"left join tbBuyer b\r\n" + 
					"on a.BuyerId = b.id\r\n" + 
					"left join TbStyleCreate sc\r\n" + 
					"on a.StyleId = sc.StyleId\r\n" + 
					"left join tbItemDescription id\r\n" + 
					"on a.ItemId = id.itemid\r\n" + 
					"left join TbLineCreate lc\r\n" + 
					"on a.LineId = lc.LineId\r\n" + 
					"left join TbEmployeeInfo e \r\n" + 
					"on a.EmployeeId = e.AutoId \r\n" + 
					"where a.BuyerId='"+productionPlan.getBuyerId()+"' and a.BuyerOrderId='"+productionPlan.getBuyerorderId()+"' and a.StyleId='"+productionPlan.getStyleId()+"' and a.ItemId='"+productionPlan.getItemId()+"' and a.date='"+productionPlan.getLayoutDate()+"' and type='"+productionPlan.getLayoutName()+"'";


			List<?> list = session.createSQLQuery(sql).list();
			int lineCount=list.size();
			
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				dataList.add(new ProductionPlan(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString(), element[7].toString(), element[8].toString(), element[9].toString(), element[10].toString(), element[11].toString(), element[12].toString(), element[13].toString(), element[14].toString(), element[15].toString(), element[16].toString(), element[17].toString(), element[18].toString(), element[19].toString(), element[20].toString(), element[21].toString(), element[22].toString(), element[23].toString(), element[24].toString(), element[25].toString(), element[26].toString(), element[27].toString(), element[28].toString(), element[29].toString(), element[30].toString(), element[31].toString(),element[32].toString(),element[33].toString(),String.valueOf(lineCount)));
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
	public List<ProductionPlan> getLayoutLineData(ProductionPlan productionPlan) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<ProductionPlan> dataList=new ArrayList<ProductionPlan>();
		ProductionPlan tempProduction= null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="select a.AutoId,a.BuyerId,b.name buyerName,a.BuyerOrderId,a.PurchaseOrder,a.StyleId,sc.StyleNo,a.ItemId,id.itemname,a.LineId,lc.LineName,a.EmployeeId,isnull(e.name,'') as employeeName,a.Type,isnull(a.proudctionType,'') as productionType,ptp.OrderQty,ptp.PlanQty,a.DailyTarget,a.LineTarget,a.HourlyTarget,a.Hours,a.hour1,a.hour2,a.hour3,a.hour4,a.hour5,a.hour6,a.hour7,a.hour8,a.hour9,a.hour10,a.total,(select convert(varchar,a.date,103))as date,a.userId,a.machineId,mi.MachineName,mi.OperatorId,ei.Name as operatorName,a.processId\r\n" + 
					"from tbLayoutPlanDetails a\r\n" + 
					"left join TbProductTargetPlan ptp\r\n" + 
					"on a.BuyerId = ptp.BuyerId and a.BuyerOrderId = ptp.BuyerOrderId and a.StyleId = ptp.StyleId and a.ItemId = ptp.ItemId\r\n" + 
					"left join tbBuyer b\r\n" + 
					"on a.BuyerId = b.id\r\n" + 
					"left join TbStyleCreate sc\r\n" + 
					"on a.StyleId = sc.StyleId\r\n" + 
					"left join tbItemDescription id\r\n" + 
					"on a.ItemId = id.itemid\r\n" + 
					"left join TbLineCreate lc\r\n" + 
					"on a.LineId = lc.LineId\r\n" + 
					"left join TbEmployeeInfo e \r\n" + 
					"on a.EmployeeId = e.AutoId \r\n"+
					"left join TbMachineInfo mi\n" + 
					"on a.machineId = mi.MachineId\n" + 
					"left join TbEmployeeInfo ei\n" + 
					"on mi.OperatorId = ei.AutoId \r\n" + 
					"where a.BuyerId='"+productionPlan.getBuyerId()+"' and a.BuyerOrderId='"+productionPlan.getBuyerorderId()+"' and a.StyleId='"+productionPlan.getStyleId()+"' and a.ItemId='"+productionPlan.getItemId()+"' and a.date='"+productionPlan.getLayoutDate()+"' and a.lineId='"+productionPlan.getLineId()+"' and type='"+productionPlan.getLayoutName()+"'";


			List<?> list = session.createSQLQuery(sql).list();
			int lineCount=list.size();
			if(lineCount>0) {
				for(Iterator<?> iter = list.iterator(); iter.hasNext();)
				{	
					Object[] element = (Object[]) iter.next();
					tempProduction = new ProductionPlan(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString(), element[7].toString(), element[8].toString(), element[9].toString(), element[10].toString(), element[11].toString(), element[12].toString(), element[13].toString(), element[14].toString(), element[15].toString(), element[16].toString(), element[17].toString(), element[18].toString(), element[19].toString(), element[20].toString(), element[21].toString(), element[22].toString(), element[23].toString(), element[24].toString(), element[25].toString(), element[26].toString(), element[27].toString(), element[28].toString(), element[29].toString(), element[30].toString(), element[31].toString(),element[32].toString(),element[33].toString(),String.valueOf(lineCount));
					tempProduction.setMachineId(element[34].toString());
					tempProduction.setMachineName(element[35].toString());
					tempProduction.setOperatorId(element[36].toString());
					tempProduction.setOperatorName(element[37].toString());
					tempProduction.setProcessId(element[38].toString());
					dataList.add(tempProduction);
				}
			}else {
				sql = "select MachineId,MachineName,OperatorId,isnull(ei.Name,'')as operatorName \n" + 
						"from TbMachineInfo mi\n" + 
						"left join TbEmployeeInfo ei\n" + 
						"on mi.OperatorId = ei.AutoId where mi.LineId = '"+productionPlan.getLineId()+"'" ;
				list = session.createSQLQuery(sql).list();
				for(Iterator<?> iter = list.iterator(); iter.hasNext();) {
					Object[] element = (Object[]) iter.next();
					dataList.add(new ProductionPlan(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString()));
				}
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
	public String editLayoutLineData(ProductionPlan productionPlan) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		String result = "";
		try{
			tx=session.getTransaction();
			tx.begin();


			String productionSql="update tbLayoutPlanDetails set "
					+ "processId = '"+productionPlan.getProcessId()+"',"
					+ "hour1 = '"+productionPlan.getHour1()+"',"
					+ "hour2 = '"+productionPlan.getHour2()+"',"
					+ "hour3 = '"+productionPlan.getHour3()+"',"
					+ "hour4 = '"+productionPlan.getHour4()+"',"
					+ "hour5 = '"+productionPlan.getHour5()+"',"
					+ "hour6 = '"+productionPlan.getHour6()+"',"
					+ "hour7 = '"+productionPlan.getHour7()+"',"
					+ "hour8 = '"+productionPlan.getHour8()+"',"
					+ "hour9 = '"+productionPlan.getHour9()+"',"
					+ "hour10 = '"+productionPlan.getHour10()+"',"
					+ "total = '"+productionPlan.getTotal()+"',"
					+ "userId = '"+productionPlan.getUserId()+"' where autoId= '"+productionPlan.getAutoId()+"';";

			session.createSQLQuery(productionSql).executeUpdate();

			result = "Successful";
			tx.commit();
			return result;
		}
		catch(Exception ee){

			if (tx != null) {
				tx.rollback();
				return result="Something wrong";
			}
			ee.printStackTrace();
		}

		finally {
			session.close();
		}

		return result="Something wrong";
	}

	@Override
	public List<ProductionPlan> getProductionData(ProductionPlan v) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<ProductionPlan> dataList=new ArrayList<ProductionPlan>();
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="select a.AutoId,a.BuyerId,b.name buyerName,a.BuyerOrderId,a.PurchaseOrder,a.StyleId,sc.StyleNo,a.ItemId,id.itemname,a.LineId,lc.LineName,a.EmployeeId,isnull(e.name,'') as employeeName,a.Type,isnull(a.proudctionType,'') as productionType,ptp.OrderQty,ptp.PlanQty,a.DailyTarget,a.LineTarget,a.HourlyTarget,a.Hours,a.hour1,a.hour2,a.hour3,a.hour4,a.hour5,a.hour6,a.hour7,a.hour8,a.hour9,a.hour10,a.total,(select convert(varchar,a.date,103))as date,a.userId\r\n" + 
					"from tbLayoutPlanDetails a\r\n" + 
					"left join TbProductTargetPlan ptp\r\n" + 
					"on a.BuyerId = ptp.BuyerId and a.BuyerOrderId = ptp.BuyerOrderId and a.StyleId = ptp.StyleId and a.ItemId = ptp.ItemId\r\n" + 
					"left join tbBuyer b\r\n" + 
					"on a.BuyerId = b.id\r\n" + 
					"left join TbStyleCreate sc\r\n" + 
					"on a.StyleId = sc.StyleId\r\n" + 
					"left join tbItemDescription id\r\n" + 
					"on a.ItemId = id.itemid\r\n" + 
					"left join TbLineCreate lc\r\n" + 
					"on a.LineId = lc.LineId\r\n" + 
					"left join TbEmployeeInfo e \r\n" + 
					"on a.EmployeeId = e.AutoId \r\n" + 
					"where a.BuyerId='"+v.getBuyerId()+"' and a.BuyerOrderId='"+v.getBuyerorderId()+"' and a.StyleId='"+v.getStyleId()+"' and a.ItemId='"+v.getItemId()+"' and a.date='"+v.getLayoutDate()+"' and a.type in ("+ProductionType.LINE_PRODUCTION.getType()+","+ProductionType.LINE_PASS.getType()+","+ProductionType.LINE_REJECT.getType()+") order by a.LineId,a.autoId";


			List<?> list = session.createSQLQuery(sql).list();
			int lineCount=list.size();
			
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				dataList.add(new ProductionPlan(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString(), element[7].toString(), element[8].toString(), element[9].toString(), element[10].toString(), element[11].toString(), element[12].toString(), element[13].toString(), element[14].toString(), element[15].toString(), element[16].toString(), element[17].toString(), element[18].toString(), element[19].toString(), element[20].toString(), element[21].toString(), element[22].toString(), element[23].toString(), element[24].toString(), element[25].toString(), element[26].toString(), element[27].toString(), element[28].toString(), element[29].toString(), element[30].toString(), element[31].toString(),element[32].toString(),element[33].toString(),String.valueOf(lineCount)));
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
	public boolean savePolyPackingDetails(ProductionPlan v) {

		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();

			int temp=0;

			String checksql="select StyleId from tbPolyPackingDetails where PurchaseOrder='"+v.getPurchaseOrder()+"' and StyleId='"+v.getStyleId()+"' and ItemId='"+v.getItemId()+"'  and date='"+v.getLayoutDate()+"' and Type='"+v.getLayoutName()+"'";

			List<?> list = session.createSQLQuery(checksql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				temp=1;
				break;
			}

			if(temp==0) {
				
				String resultValue=v.getResultlist().substring(v.getResultlist().indexOf("[")+1, v.getResultlist().indexOf("]"));
				
				StringTokenizer firstToken=new StringTokenizer(resultValue,",");
				while(firstToken.hasMoreTokens()) {
					String secondToken=firstToken.nextToken();
					StringTokenizer thirdToken=new StringTokenizer(secondToken,"*");
					while(thirdToken.hasMoreTokens()) {
						String clineId=thirdToken.nextToken();
						String employeeId=thirdToken.nextToken();
						String colorId=thirdToken.nextToken();
						String sizeId=thirdToken.nextToken();
						String polytype=thirdToken.nextToken();
						String pcsQty=thirdToken.nextToken();
						String unitqty=thirdToken.nextToken();
						String totalQty=thirdToken.nextToken();
						String totalPoly=thirdToken.nextToken();


						String productionSql="insert into tbPolyPackingDetails ("
								+ "BuyerId,"
								+ "BuyerOrderId,"
								+ "PurchaseOrder,"
								+ "StyleId,"
								+ "ItemId,"
								+ "ColorId,"
								+ "LineId,"
								+ "EmployeeId,"
								+ "SizeId,"
								+ "Type,"
								+ "DailyTarget,"
								+ "LineTarget,"
								+ "HourlyTarget,"
								+ "Hours,"
								+ "packetType,"
								+ "pcsQty,"
								+ "unitQty,"
								+ "total,"
								+ "totalPoly,"
								+ "date,"
								+ "entrytime,"
								+ "userId) values ("
								+ "'"+v.getBuyerId()+"',"
								+ "'"+v.getBuyerorderId()+"',"
								+ "'"+v.getPurchaseOrder()+"',"
								+ "'"+v.getStyleId()+"',"
								+ "'"+v.getItemId()+"',"
								+ "'"+colorId+"',"
								+ "'"+clineId+"',"
								+ "'"+employeeId+"',"
								+ "'"+sizeId+"',"
								+ "'"+v.getLayoutName()+"',"
								+ "'"+v.getDailyTarget()+"',"
								+ "'"+v.getDailyLineTarget()+"',"
								+ "'"+v.getHourlyTarget()+"',"
								+ "'10',"
								+ "'"+polytype+"',"
								+ "'"+pcsQty+"',"
								+ "'"+unitqty+"',"
								+ "'"+totalQty+"',"
								+ "'"+totalPoly+"',"
								+ "'"+v.getLayoutDate()+"',CURRENT_TIMESTAMP,'"+v.getUserId()+"'"
								+ ")";
						session.createSQLQuery(productionSql).executeUpdate();

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

	@Override
	public List<ProductionPlan> getPolyPackingDetails(String type) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<ProductionPlan> dataList=new ArrayList<ProductionPlan>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select (select name from tbBuyer where id=a.BuyerId) as BuyerName,a.BuyerId,a.BuyerOrderId,a.PurchaseOrder,(select StyleNo from TbStyleCreate where StyleId=a.StyleId) as StyleNo,a.styleId,(select ItemName from tbItemDescription where itemid=a.itemId) as ItemName,a.ItemId,convert(varchar,a.date,23) as Date,(select ISNULL(sum(PlanQty),0) as PlanQty from TbProductTargetPlan where BuyerOrderId=a.BuyerOrderId and StyleId=a.StyleId and ItemId=a.ItemId ) as PlanQty  from tbPolyPackingDetails a  where a.Type='"+type+"' group by a.BuyerId,a.BuyerOrderId,a.PurchaseOrder,a.StyleId,a.ItemId,a.date";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();

				dataList.add(new ProductionPlan(element[0].toString(), element[1].toString(),element[2].toString(),element[3].toString(),element[4].toString(),element[5].toString(),element[6].toString(),element[7].toString(),element[8].toString(),element[9].toString()));
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
	public List<CuttingInformation> getCuttingRequisitionList() {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<CuttingInformation> dataList=new ArrayList<CuttingInformation>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select a.CuttingEntryId,(select Name from tbBuyer where id=a.BuyerId) as BuyerName,a.purchaseOrder,(select StyleNo from TbStyleCreate where StyleId=a.StyleId) as StyleName,(select ItemName from tbItemDescription where ItemId=a.ItemId) as ItemName,a.CuttingNo,convert(varchar,a.CuttingDate,23) as CuttingDate from TbCuttingInformationSummary a where a.RequistionFlag='1'";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();
				CuttingInformation cutting = new CuttingInformation(element[0].toString(), element[1].toString(),element[2].toString(),element[3].toString(),element[4].toString(),element[5].toString(),element[6].toString());
				dataList.add(cutting);
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
	public List<ProductionPlan> searchCuttingPlanQuantity(String cuttingEntryId, String sizeGroupId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<ProductionPlan> dataList=new ArrayList<ProductionPlan>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select a.cuttingAutoId,c.BuyerId,c.purchaseOrder,c.StyleId,sc.StyleNo,c.ItemId,id.itemname,a.ColorId, color.Colorname,b.SizeGroupId,b.sizeId,ss.sizeName,b.sizeQuantity,isnull(b.status,'') as status\r\n" + 
					"from TbCuttingInformationDetails a\r\n" + 
					"join tbSizeValues b \r\n" + 
					"on a.cuttingAutoId=b.linkedAutoId and b.type = '"+SizeValuesType.CUTTING_QTY.getType()+"'\r\n" + 
					"join TbCuttingInformationSummary c \r\n" + 
					"on a.CuttingEntryId=c.CuttingEntryId\r\n" + 
					"join tbstyleSize ss \r\n" + 
					"on b.sizeid= ss.id\r\n" + 
					"left join TbStyleCreate sc\r\n" + 
					"on c.StyleId = sc.StyleId\r\n" + 
					"left join tbItemDescription id\r\n" + 
					"on c.ItemId = id.itemid\r\n" + 
					"left join tbColors color\r\n" + 
					"on a.ColorId = color.ColorId\r\n" + 
					"where a.CuttingEntryId='"+cuttingEntryId+"' and a.type = 'Cutting' and (a.status is null or a.status = 0 or a.status = 1 or a.status = 2) \r\n" + 
					"order by a.cuttingAutoId,a.sizeGroupId,ss.sortingNo";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();
				dataList.add(new ProductionPlan(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString(), element[7].toString(), element[8].toString(), element[9].toString(), element[10].toString(), element[11].toString(), element[12].toString(),element[13].toString()));
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
	public List<ProductionPlan> getSendCuttingBodyList(String cuttingEntryId, String sizeGroupId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<ProductionPlan> dataList=new ArrayList<ProductionPlan>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select a.cuttingAutoId,c.BuyerId,c.purchaseOrder,c.StyleId,sc.StyleNo,c.ItemId,id.itemname,a.ColorId, color.Colorname,b.SizeGroupId,b.sizeId,ss.sizeName,b.sizeQuantity,isnull(b.status,'') as status\r\n" + 
					"from TbCuttingInformationDetails a\r\n" + 
					"join tbSizeValues b \r\n" + 
					"on a.cuttingAutoId=b.linkedAutoId and b.type = '"+SizeValuesType.CUTTING_QTY.getType()+"'\r\n" + 
					"join TbCuttingInformationSummary c \r\n" + 
					"on a.CuttingEntryId=c.CuttingEntryId\r\n" + 
					"join tbstyleSize ss \r\n" + 
					"on b.sizeid= ss.id\r\n" + 
					"left join TbStyleCreate sc\r\n" + 
					"on c.StyleId = sc.StyleId\r\n" + 
					"left join tbItemDescription id\r\n" + 
					"on c.ItemId = id.itemid\r\n" + 
					"left join tbColors color\r\n" + 
					"on a.ColorId = color.ColorId\r\n" + 
					"where a.CuttingEntryId='"+cuttingEntryId+"' and a.type = 'Cutting' and (a.status = 2 or a.status = 1) \r\n" + 
					"order by a.cuttingAutoId,a.sizeGroupId,ss.sortingNo";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();
				dataList.add(new ProductionPlan(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString(), element[7].toString(), element[8].toString(), element[9].toString(), element[10].toString(), element[11].toString(), element[12].toString(),element[13].toString()));
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
	public List<CuttingInformation> getSendCuttingBodyInfoList() {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<CuttingInformation> dataList=new ArrayList<CuttingInformation>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select a.CuttingEntryId,(select Name from tbBuyer where id=a.BuyerId) as BuyerName,a.purchaseOrder,(select StyleNo from TbStyleCreate where StyleId=a.StyleId) as StyleName,(select ItemName from tbItemDescription where ItemId=a.ItemId) as ItemName,a.CuttingNo,convert(varchar,a.CuttingDate,23) as CuttingDate,count(a.CuttingEntryId) as cnt\r\n" + 
					"from TbCuttingInformationSummary a\r\n" + 
					"join TbCuttingInformationDetails cd\r\n" + 
					"on a.CuttingEntryId = cd.CuttingEntryId and (cd.status = 1 or cd.status = 2)\r\n" + 
					"group by a.CuttingEntryId,a.buyerId,a.purchaseOrder,a.StyleId,a.ItemId,a.CuttingNo,a.CuttingDate";

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
	public String sendCuttingPlanBodyQuantity(String sendItemList,String userId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();

			if(sendItemList.trim().length()>0) {
				String[] sizeLists = sendItemList.split("#");

				String id,linkAutoId,sizeId,sizeStatus;
				String prevLinkAutoId="";
				for (String item : sizeLists) {

					String[] itemProperty = item.split(",");
					id = itemProperty[0].substring(itemProperty[0].indexOf(":")+1).trim();
					linkAutoId = itemProperty[1].substring(itemProperty[1].indexOf(":")+1).trim();
					sizeId = itemProperty[2].substring(itemProperty[2].indexOf(":")+1).trim();
					sizeStatus = itemProperty[3].substring(itemProperty[3].indexOf(":")+1).trim();
					String productionSql="update tbSizeValues set status = '"+sizeStatus+"',sendEntryTime=CURRENT_TIMESTAMP,senderUserId = '"+userId+"' where sizeId = '"+sizeId+"' and linkedAutoId = '"+linkAutoId+"' and type='"+SizeValuesType.CUTTING_QTY.getType()+"' and (status is null or status = 0 or status = '1')";
					session.createSQLQuery(productionSql).executeUpdate();

					if(!prevLinkAutoId.equals(linkAutoId)) {
						productionSql= "update TbCuttingInformationDetails  set status = '1' where cuttingAutoId='"+linkAutoId+"' and type = 'Cutting' and (status is null or status = 0 or status = 1)";
						session.createSQLQuery(productionSql).executeUpdate();
						prevLinkAutoId = linkAutoId;
					}

				}		


			}	
			tx.commit();
			return "Successful";
		}
		catch(Exception ee){

			if (tx != null) {
				tx.rollback();
				return "Something Wrong";
			}
			ee.printStackTrace();
		}

		finally {
			session.close();
		}

		return "Something Wrong";
	}

	@Override
	public List<ProductionPlan> searchSendCuttingBodyQuantity(String cuttingEntryId, String sizeGroupId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<ProductionPlan> dataList=new ArrayList<ProductionPlan>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select a.cuttingAutoId,c.BuyerId,c.purchaseOrder,c.StyleId,sc.StyleNo,c.ItemId,id.itemname,a.ColorId, color.Colorname,b.SizeGroupId,b.sizeId,ss.sizeName,b.sizeQuantity,isnull(b.status,'') as status\r\n" + 
					"from TbCuttingInformationDetails a\r\n" + 
					"join tbSizeValues b \r\n" + 
					"on a.cuttingAutoId=b.linkedAutoId and b.type = '"+SizeValuesType.CUTTING_QTY.getType()+"'\r\n" + 
					"join TbCuttingInformationSummary c \r\n" + 
					"on a.CuttingEntryId=c.CuttingEntryId\r\n" + 
					"join tbstyleSize ss \r\n" + 
					"on b.sizeid= ss.id\r\n" + 
					"left join TbStyleCreate sc\r\n" + 
					"on c.StyleId = sc.StyleId\r\n" + 
					"left join tbItemDescription id\r\n" + 
					"on c.ItemId = id.itemid\r\n" + 
					"left join tbColors color\r\n" + 
					"on a.ColorId = color.ColorId\r\n" + 
					"where a.CuttingEntryId='"+cuttingEntryId+"' and a.type = 'Cutting' and ( a.status = 2 or a.status = 1) \r\n" + 
					"order by a.cuttingAutoId,a.sizeGroupId,ss.sortingNo";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();
				dataList.add(new ProductionPlan(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString(), element[7].toString(), element[8].toString(), element[9].toString(), element[10].toString(), element[11].toString(), element[12].toString(),element[13].toString()));
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
	public List<ProductionPlan> getReceiveCuttingBodyList(String cuttingEntryId, String sizeGroupId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<ProductionPlan> dataList=new ArrayList<ProductionPlan>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select a.cuttingAutoId,c.BuyerId,c.purchaseOrder,c.StyleId,sc.StyleNo,c.ItemId,id.itemname,a.ColorId, color.Colorname,b.SizeGroupId,b.sizeId,ss.sizeName,b.sizeQuantity,isnull(b.status,'') as status\r\n" + 
					"from TbCuttingInformationDetails a\r\n" + 
					"join tbSizeValues b \r\n" + 
					"on a.cuttingAutoId=b.linkedAutoId and b.type = '"+SizeValuesType.CUTTING_QTY.getType()+"'\r\n" + 
					"join TbCuttingInformationSummary c \r\n" + 
					"on a.CuttingEntryId=c.CuttingEntryId\r\n" + 
					"join tbstyleSize ss \r\n" + 
					"on b.sizeid= ss.id\r\n" + 
					"left join TbStyleCreate sc\r\n" + 
					"on c.StyleId = sc.StyleId\r\n" + 
					"left join tbItemDescription id\r\n" + 
					"on c.ItemId = id.itemid\r\n" + 
					"left join tbColors color\r\n" + 
					"on a.ColorId = color.ColorId\r\n" + 
					"where a.CuttingEntryId='"+cuttingEntryId+"' and a.type = 'Cutting' and (a.status = 2 or a.status = 1) \r\n" + 
					"order by a.cuttingAutoId,a.sizeGroupId,ss.sortingNo";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();
				dataList.add(new ProductionPlan(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString(), element[7].toString(), element[8].toString(), element[9].toString(), element[10].toString(), element[11].toString(), element[12].toString(),element[13].toString()));
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
	public List<CuttingInformation> getReceiveCuttingBodyInfoList() {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<CuttingInformation> dataList=new ArrayList<CuttingInformation>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select a.CuttingEntryId,(select Name from tbBuyer where id=a.BuyerId) as BuyerName,a.purchaseOrder,(select StyleNo from TbStyleCreate where StyleId=a.StyleId) as StyleName,(select ItemName from tbItemDescription where ItemId=a.ItemId) as ItemName,a.CuttingNo,convert(varchar,a.CuttingDate,23) as CuttingDate,count(a.CuttingEntryId) as cnt\r\n" + 
					"from TbCuttingInformationSummary a\r\n" + 
					"join TbCuttingInformationDetails cd\r\n" + 
					"on a.CuttingEntryId = cd.CuttingEntryId and  cd.status = 2\r\n" + 
					"group by a.CuttingEntryId,a.buyerId,a.purchaseOrder,a.StyleId,a.ItemId,a.CuttingNo,a.CuttingDate";

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
	public String receiveCuttingPlanBodyQuantity(String sendItemList,String userId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();

			if(sendItemList.trim().length()>0) {
				String[] sizeLists = sendItemList.split("#");

				String id,linkAutoId,sizeId,sizeStatus;
				String prevLinkAutoId="";
				for (String item : sizeLists) {

					String[] itemProperty = item.split(",");
					id = itemProperty[0].substring(itemProperty[0].indexOf(":")+1).trim();
					linkAutoId = itemProperty[1].substring(itemProperty[1].indexOf(":")+1).trim();
					sizeId = itemProperty[2].substring(itemProperty[2].indexOf(":")+1).trim();
					sizeStatus = itemProperty[3].substring(itemProperty[3].indexOf(":")+1).trim();
					String productionSql="update tbSizeValues set status = '"+sizeStatus+"',sendEntryTime=CURRENT_TIMESTAMP,senderUserId = '"+userId+"' where sizeId = '"+sizeId+"' and linkedAutoId = '"+linkAutoId+"' and type='"+SizeValuesType.CUTTING_QTY.getType()+"' and ( status = 2 or status = 1)";
					session.createSQLQuery(productionSql).executeUpdate();

					if(!prevLinkAutoId.equals(linkAutoId)) {
						productionSql= "update TbCuttingInformationDetails  set status = '2' where cuttingAutoId='"+linkAutoId+"' and type = 'Cutting' and ( status = 2 or status = 1)";
						session.createSQLQuery(productionSql).executeUpdate();
						prevLinkAutoId = linkAutoId;
					}

				}		


			}	
			tx.commit();
			return "Successful";
		}
		catch(Exception ee){

			if (tx != null) {
				tx.rollback();
				return "Something Wrong";
			}
			ee.printStackTrace();
		}

		finally {
			session.close();
		}

		return "Something Wrong";
	}



}

