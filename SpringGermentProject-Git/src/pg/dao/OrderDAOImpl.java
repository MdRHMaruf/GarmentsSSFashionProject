package pg.dao;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import pg.config.SpringRootConfig;
import pg.model.CommonModel;
import pg.orderModel.AccessoriesIndent;
import pg.orderModel.AccessoriesIndentCarton;
import pg.orderModel.BuyerPO;
import pg.orderModel.BuyerPoItem;
import pg.orderModel.CheckListModel;
import pg.orderModel.Costing;
import pg.orderModel.FabricsIndent;
import pg.orderModel.FileUpload;
import pg.orderModel.ParcelModel;
import pg.orderModel.PurchaseOrder;
import pg.orderModel.PurchaseOrderItem;
import pg.orderModel.SampleCadAndProduction;
import pg.orderModel.SampleRequisitionItem;
import pg.orderModel.Style;
import pg.proudctionModel.ProductionPlan;
import pg.registerModel.AccessoriesItem;
import pg.registerModel.Color;
import pg.registerModel.CourierModel;
import pg.registerModel.ItemDescription;
import pg.registerModel.MerchandiserInfo;
import pg.registerModel.ParticularItem;
import pg.registerModel.Size;
import pg.share.HibernateUtil;
import pg.share.ItemType;
import pg.share.ProductionType;
import pg.share.SizeValuesType;
import pg.share.StateStatus;
@Repository
public class OrderDAOImpl implements OrderDAO{

	DecimalFormat df = new DecimalFormat("#00");
	@Override
	public List<ItemDescription> getItemDescriptionList() {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<ItemDescription> dataList=new ArrayList<ItemDescription>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select ItemId,ItemName from tbItemDescription order by ItemName";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();

				dataList.add(new ItemDescription(element[0].toString(), element[1].toString()));
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
	public List<Style> getBuyerWiseStylesItem(String buyerId) {
		// TODO Auto-generated method stub

		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<Style> dataList=new ArrayList<Style>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select styleId,styleNo from tbstyleCreate where buyerId='"+buyerId+"'";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();

				dataList.add(new Style(element[0].toString(), buyerId, "", element[1].toString(),"", ""));
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
	public List<CommonModel> getPurchaseOrderListByMultipleBuyers(String buyersId) {
		// TODO Auto-generated method stub

		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<CommonModel> dataList=new ArrayList<CommonModel>();

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select  boes.BuyerOrderId,boes.buyerId,boes.PurchaseOrder\n" + 
					"from TbBuyerOrderEstimateDetails boes\n" + 
					"where boes.buyerId in ("+buyersId+") and boes.stateStatus != '"+StateStatus.END.getType()+"'\n" + 
					"group by boes.buyerId,boes.BuyerOrderId,boes.PurchaseOrder\n" + 
					"order by boes.buyerId,boes.BuyerOrderId,boes.PurchaseOrder;";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();

				dataList.add(new CommonModel(element[0].toString(),element[2].toString()));
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
	public List<Style> getBuyerPOStyleListByMultipleBuyers(String buyersId) {
		// TODO Auto-generated method stub

		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<Style> dataList=new ArrayList<Style>();

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select  boes.StyleId,sc.StyleNo\n" + 
					"from TbBuyerOrderEstimateDetails boes\n" + 
					"left join TbStyleCreate sc\n" + 
					"on boes.StyleId = sc.StyleId\n" + 
					"where boes.buyerId in ("+buyersId+") and boes.stateStatus != '"+StateStatus.END.getType()+"'\n" + 
					"group by boes.StyleId,sc.StyleNo\n" + 
					"order by boes.StyleId,sc.StyleNo;";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();

				dataList.add(new Style(element[0].toString(), element[1].toString()));
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
	public List<Style> getBuyerPOStyleListByMultiplePurchaseOrders(String purchaseOrders) {
		// TODO Auto-generated method stub

		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<Style> dataList=new ArrayList<Style>();

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select  boes.StyleId,sc.StyleNo\n" + 
					"from TbBuyerOrderEstimateDetails boes\n" + 
					"left join TbStyleCreate sc\n" + 
					"on boes.StyleId = sc.StyleId\n" + 
					"where boes.purchaseOrder in ("+purchaseOrders+") and boes.stateStatus != '"+StateStatus.END.getType()+"'\n" + 
					"group by boes.StyleId,sc.StyleNo\n" + 
					"order by boes.StyleId,sc.StyleNo;";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();

				dataList.add(new Style(element[0].toString(), element[1].toString()));
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
	public List<CommonModel> getStyleWiseBuyerPO(String styleId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<CommonModel> dataList=new ArrayList<CommonModel>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select BuyerOrderId,PurchaseOrder \n" + 
					"from TbBuyerOrderEstimateDetails boed\n" + 
					"where boed.StyleId ='"+styleId+"' \n" + 
					"group by boed.BuyerOrderId , boed.PurchaseOrder";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();

				dataList.add(new CommonModel(element[0].toString(),element[1].toString()));
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
	public List<CommonModel> getPurchaseOrderByMultipleStyle(String styleIdList) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<CommonModel> dataList=new ArrayList<CommonModel>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select BuyerOrderId,PurchaseOrder \n" + 
					"from TbBuyerOrderEstimateDetails boed\n" + 
					"where boed.StyleId in ("+styleIdList+") \n" + 
					"group by boed.BuyerOrderId , boed.PurchaseOrder";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();

				dataList.add(new CommonModel(element[0].toString(),element[1].toString()));
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
	public List<ItemDescription> getStyleWiseItem(String styleId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<ItemDescription> dataList=new ArrayList<ItemDescription>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select itemId,(select itemName from tbItemDescription where itemid = si.itemId) as itemName from tbStyleWiseItem si where styleId='"+styleId+"'";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();

				dataList.add(new ItemDescription(element[0].toString(), element[1].toString()));
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
	public List<ItemDescription> getItemListByMultipleStyleId(String styleIdList) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<ItemDescription> dataList=new ArrayList<ItemDescription>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select itemId,(select itemName from tbItemDescription where itemid = si.itemId) as itemName from tbStyleWiseItem si where styleId in ("+styleIdList+")";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();

				dataList.add(new ItemDescription(element[0].toString(), element[1].toString()));
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
	public List<Color> getColorListByMultiplePoAndStyle(String purchaseOrders,String styleIdList) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<Color> dataList=new ArrayList<Color>();
		try{

			tx=session.getTransaction();
			tx.begin();
			String sql="select  boes.ColorId,c.Colorname\r\n" + 
					"from TbBuyerOrderEstimateDetails boes\r\n" + 
					"left join tbColors c\r\n" + 
					"on boes.ColorId = c.ColorId\r\n" + 
					"where boes.purchaseOrder in ("+purchaseOrders+") and boes.StyleId in ("+styleIdList+") and boes.stateStatus != '8'\r\n" + 
					"group by boes.ColorId,c.Colorname\r\n" + 
					"order by boes.ColorId,c.Colorname";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();

				dataList.add(new Color(element[0].toString(), element[1].toString(), "", ""));
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
	public List<String> getShippingMarkListByMultiplePoAndStyle(String purchaseOrders,String styleIdList) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<String> dataList=new ArrayList<String>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select  boed.ShippingMarks\r\n" + 
					"from TbBuyerOrderEstimateDetails boed\r\n" + 
					"where boed.purchaseOrder in ("+purchaseOrders+") and boed.StyleId in ("+styleIdList+") and boed.stateStatus != '8'\r\n" + 
					"group by boed.ShippingMarks\r\n" + 
					"order by boed.ShippingMarks";

			List<?> list = session.createSQLQuery(sql).list();
			for(Object shipping:list)
			{	
				dataList.add(shipping.toString());
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



	private String getStyleId(String buyerId, String styleNo) {

		String Id="";
		Session session=HibernateUtil.openSession();
		Transaction tx=null;



		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select StyleId from TbStyleCreate where StyleNo='"+styleNo+"' and BuyerId='"+buyerId+"' and Finished='0'";
			System.out.println("sql "+sql);
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Id=iter.next().toString();
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

		return Id;
	}

	private boolean CheckStyleAlreadyExist(String buyerId, String styleNo,String styleId) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		String query="";

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select StyleId from TbStyleCreate where StyleNo='"+styleNo+"' and BuyerId='"+buyerId+"' and styleId != '"+styleId+"' and Finished='0'";

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

	public String getMaxStyleId() {
		// TODO Auto-generated method stub

		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		String query="";

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select isnull(max(StyleId),0)+1 from TbStyleCreate";

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
	public List<Style> getStyleList(String userId) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<Style> datalist=new ArrayList<Style>();	
		try{	
			tx=session.getTransaction();
			tx.begin();		
			String sql="select StyleId,StyleNo from TbStyleCreate where userId='"+userId+"' order by StyleNo";		
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				datalist.add(new Style(element[0].toString(),element[1].toString()));				
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
	public List<Style> getStyleWiseItemList(String userId) {

		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<Style> datalist=new ArrayList<Style>();

		try{	
			tx=session.getTransaction();
			tx.begin();



			//String sql="select a.Id,(select StyleNo from TbStyleCreate where StyleId=a.StyleId) as StyleNo,(select itemname from tbItemDescription where itemid=a.ItemId) as ItemName,a.ItemId from tbStyleWiseItem a order by StyleId,BuyerId";
			String sql="select a.Id,a.buyerid as buyer,(select styleid from TbStyleCreate where StyleId=a.StyleId) as StyleId,(select StyleNo from TbStyleCreate where StyleId=a.StyleId) as StyleNo, convert(varchar,(select date from TbStyleCreate where StyleId=a.StyleId)) as date,(select itemname from tbItemDescription where itemid=a.ItemId) as ItemName,a.ItemId, a.size,a.frontpic, a.backpic from tbStyleWiseItem a where a.UserId='"+userId+"' order by StyleId,BuyerId";
			List<?> list = session.createSQLQuery(sql).list();

			String StyleNo="",PerStyle="";
			int i=0;
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				if(i==0) {
					StyleNo=element[1].toString();
					PerStyle=StyleNo;
				}

				if(i!=0 && PerStyle.equals(element[1].toString())) {
					StyleNo="";

				}
				else{
					StyleNo=element[1].toString();
					PerStyle=StyleNo;
				}



				datalist.add(new Style(element[0].toString(),element[1].toString(),element[2].toString(),element[3].toString(),element[4].toString(),element[5].toString(),element[6].toString(),element[7].toString()));
				i++;
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
	public List<Style> getStyleAndItem(String value) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<Style> datalist=new ArrayList<Style>();

		try{	
			tx=session.getTransaction();
			tx.begin();


			String sql="select a.StyleId,a.ItemId,(select itemname from tbItemDescription where itemid=a.ItemId) as ItemName from tbStyleWiseItem a where a.StyleId='"+value+"'";

			List<?> list = session.createSQLQuery(sql).list();


			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				datalist.add(new Style(element[0].toString(),element[1].toString(),element[2].toString()));

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
	public List<ParticularItem> getTypeWiseParticularList(String type) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<ParticularItem> datalist=new ArrayList<ParticularItem>();	
		try{	
			tx=session.getTransaction();
			tx.begin();	
			String sql="";
			if(type.equals("1")) {
				sql="select id,ItemName,UserId from TbFabricsItem where ItemName IS NOT NULL order by ItemName ";
			}else {
				sql="select AutoId,Name,UserId from TbParticularItemInfo order by Name";
			}

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				datalist.add(new ParticularItem(element[0].toString(), element[1].toString(), element[2].toString()));				
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
	public boolean saveCosting(Costing costing) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="insert into TbCostingCreate ("
					+ "StyleId,"
					+ "ItemId,"
					+ "GroupType,"
					+ "FabricationItemId,"
					+ "size,"
					+ "UnitId,"
					+ "width,"
					+ "yard,"
					+ "gsm,"
					+ "consumption,"
					+ "UnitPrice,"
					+ "Amount,"
					+ "Comission,"
					+ "SubmissionDate,"
					+ "EntryTime,"
					+ "UserId)"
					+ " values "
					+ "("
					+ "'"+costing.getStyleId()+"',"
					+ "'"+costing.getItemId()+"',"
					+ "'"+costing.getParticularType()+"',"
					+ "'"+costing.getParticularId()+"',"
					+ "'"+costing.getSize()+"',"
					+ "'"+costing.getUnitId()+"',"
					+ "'"+costing.getWidth()+"',"
					+ "'"+costing.getYard()+"',"
					+ "'"+costing.getGsm()+"',"
					+ "'"+costing.getConsumption()+"',"
					+ "'"+costing.getUnitPrice()+"',"
					+ "'"+costing.getAmount()+"',"
					+ "'"+costing.getCommission()+"',"
					+ "'"+costing.getDate()+"',"
					+ "CURRENT_TIMESTAMP,"
					+ "'"+costing.getUserId()+"')";
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
	public String confirmCosting(List<Costing> costingList) {

		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="";

			sql = "select (isnull(max(cast(costingNo as int)),0)+1) as maxId from TbCostingCreate";
			List<?> list = session.createSQLQuery(sql).list();
			String costingNo="0";
			if(list.size()>0) {
				costingNo = list.get(0).toString();
			}
			for(Costing costing:costingList) {
				sql="insert into TbCostingCreate ("
						+ "costingNo,"
						+ "StyleId,"
						+ "ItemId,"
						+ "GroupType,"
						+ "FabricationItemId,"
						+ "size,"
						+ "UnitId,"
						+ "width,"
						+ "yard,"
						+ "gsm,"
						+ "consumption,"
						+ "UnitPrice,"
						+ "Amount,"
						+ "Comission,"
						+ "SubmissionDate,"
						+ "EntryTime,"
						+ "UserId)"
						+ " values "
						+ "("
						+ "'"+costingNo+"',"
						+ "'"+costing.getStyleId()+"',"
						+ "'"+costing.getItemId()+"',"
						+ "'"+costing.getParticularType()+"',"
						+ "'"+costing.getParticularId()+"',"
						+ "'"+costing.getSize()+"',"
						+ "'"+costing.getUnitId()+"',"
						+ "'"+costing.getWidth()+"',"
						+ "'"+costing.getYard()+"',"
						+ "'"+costing.getGsm()+"',"
						+ "'"+costing.getConsumption()+"',"
						+ "'"+costing.getUnitPrice()+"',"
						+ "'"+costing.getAmount()+"',"
						+ "'"+costing.getCommission()+"',"
						+ "'"+costing.getDate()+"',"
						+ "CURRENT_TIMESTAMP,"
						+ "'"+costing.getUserId()+"')";
				session.createSQLQuery(sql).executeUpdate();
			}

			tx.commit();
			return costingNo;
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
	public String editCostingNo(List<Costing> costingList) {

		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="";
			for(Costing costing:costingList) {
				sql="insert into TbCostingCreate ("
						+ "costingNo,"
						+ "StyleId,"
						+ "ItemId,"
						+ "GroupType,"
						+ "FabricationItemId,"
						+ "size,"
						+ "UnitId,"
						+ "width,"
						+ "yard,"
						+ "gsm,"
						+ "consumption,"
						+ "UnitPrice,"
						+ "Amount,"
						+ "Comission,"
						+ "SubmissionDate,"
						+ "EntryTime,"
						+ "UserId)"
						+ " values "
						+ "("
						+ "'"+costing.getCostingNo()+"',"
						+ "'"+costing.getStyleId()+"',"
						+ "'"+costing.getItemId()+"',"
						+ "'"+costing.getParticularType()+"',"
						+ "'"+costing.getParticularId()+"',"
						+ "'"+costing.getSize()+"',"
						+ "'"+costing.getUnitId()+"',"
						+ "'"+costing.getWidth()+"',"
						+ "'"+costing.getYard()+"',"
						+ "'"+costing.getGsm()+"',"
						+ "'"+costing.getConsumption()+"',"
						+ "'"+costing.getUnitPrice()+"',"
						+ "'"+costing.getAmount()+"',"
						+ "'"+costing.getCommission()+"',"
						+ "'"+costing.getDate()+"',"
						+ "CURRENT_TIMESTAMP,"
						+ "'"+costing.getUserId()+"')";
				session.createSQLQuery(sql).executeUpdate();
			}

			tx.commit();
			return "Successfull";
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
	public boolean editCosting(Costing costing) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="update TbCostingCreate set "
					+ "StyleId ='"+costing.getStyleId()+"',"
					+ "ItemId ='"+costing.getItemId()+"',"
					+ "GroupType ='"+costing.getParticularType()+"',"
					+ "FabricationItemId ='"+costing.getParticularId()+"',"
					+ "size = '"+costing.getSize()+"',"
					+ "UnitId = '"+costing.getUnitId()+"',"
					+ "width = '"+costing.getWidth()+"',"
					+ "yard = '"+costing.getYard()+"',"
					+ "gsm = '"+costing.getGsm()+"',"
					+ "consumption = '"+costing.getConsumption()+"',"
					+ "UnitPrice = '"+costing.getUnitPrice()+"',"
					+ "Amount = '"+costing.getAmount()+"',"
					+ "Comission = '"+costing.getCommission()+"',"
					+ "SubmissionDate = '"+costing.getDate()+"',"
					+ "EntryTime = CURRENT_TIMESTAMP,"
					+ "UserId='"+costing.getUserId()+"' where autoId='"+costing.getAutoId()+"';";

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
	public boolean deleteCosting(String autoId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="delete from TbCostingCreate where autoId='"+autoId+"';";

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
	public List<Costing> getCostingList(String styleId, String itemId,String costingNo) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<Costing> datalist=new ArrayList<Costing>();	
		try{	
			tx=session.getTransaction();
			tx.begin();	
			String sql="select cc.AutoId,cc.StyleId,sc.StyleNo,cc.ItemId,id.itemname,cc.GroupType,cc.FabricationItemId,ISNULL(fi.ItemName,pi.Name) as particula,isnull(si.size,'')as size,cc.UnitId,cc.width,cc.yard,cc.gsm,cc.consumption,cc.UnitPrice,cc.amount,cc.Comission,(select convert(varchar,cc.SubmissionDate,103))as date,cc.UserId \r\n" + 
					"from TbCostingCreate cc\r\n" + 
					"left join TbStyleCreate sc\r\n" + 
					"on cc.StyleId = sc.StyleId\r\n" + 
					"left join tbItemDescription id\r\n" + 
					"on cc.ItemId = id.itemid\r\n" + 
					"left join tbStyleWiseItem si\r\n" + 
					"on cc.ItemId = si.ItemId and cc.StyleId=si.styleid \r\n" + 
					"left join TbFabricsItem fi\r\n" + 
					"on cc.FabricationItemId = fi.id and cc.GroupType='1'\r\n" + 
					"left join TbParticularItemInfo pi\r\n" + 
					"on cc.FabricationItemId = pi.AutoId and cc.GroupType='2' \r\n"+
					"where cc.styleId='"+styleId+"' and cc.itemId='"+itemId+"' and cc.costingNo='"+costingNo+"'";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				datalist.add(new Costing(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(),element[5].toString(), element[6].toString(), element[7].toString(), element[8].toString(), element[9].toString(), Double.valueOf(element[10].toString()), Double.valueOf(element[11].toString()), Double.valueOf(element[12].toString()),Double.valueOf(element[13].toString()), Double.valueOf(element[14].toString()), Double.valueOf(element[15].toString()), Double.valueOf(element[16].toString()), element[17].toString(), element[18].toString()));				
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
	public List<Costing> getBuyerWiseCostingList(String buyerId,String userId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<Costing> datalist=new ArrayList<Costing>();	
		Costing tempCosting = null;
		try{	
			tx=session.getTransaction();
			tx.begin();	
			String sql="select cc.costingNo,sc.StyleId,styleC.StyleNo,cc.ItemId,id.itemname\n" + 
					"from TbStyleCreate sc\n" + 
					"inner join TbCostingCreate cc\n" + 
					"on sc.StyleId = cc.StyleId\n" + 
					"left join TbStyleCreate styleC\n" + 
					"on sc.StyleId = styleC.StyleId\n" + 
					"left join tbItemDescription id\n" + 
					"on cc.ItemId = id.itemid\n" + 
					"where sc.BuyerId = '"+buyerId+"' and cc.userId='"+userId+"' \n" + 
					"group by cc.costingNo,sc.StyleId,styleC.StyleNo,cc.ItemId,id.itemname\n" + 
					"order by cc.costingNo desc";
			if(buyerId.equals("0")) { 
				sql="select cc.costingNo,sc.StyleId,styleC.StyleNo,cc.ItemId,id.itemname\n" + 
						"from TbStyleCreate sc\n" + 
						"inner join TbCostingCreate cc\n" + 
						"on sc.StyleId = cc.StyleId\n" + 
						"left join TbStyleCreate styleC\n" + 
						"on sc.StyleId = styleC.StyleId\n" + 
						"left join tbItemDescription id\n" + 
						"on cc.ItemId = id.itemid\n" + 
						"where  cc.userId='\"+userId+\"' \n" + 
						"group by cc.costingNo,sc.StyleId,styleC.StyleNo,cc.ItemId,id.itemname\n" + 
						"order by cc.costingNo desc";
			}
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				tempCosting = new Costing();
				tempCosting.setCostingNo(element[0].toString());
				tempCosting.setStyleId(element[1].toString());
				tempCosting.setStyleName(element[2].toString());
				tempCosting.setItemId(element[3].toString());
				tempCosting.setItemName(element[4].toString());
				datalist.add(tempCosting);				
			}			
			tx.commit();			
		}	
		catch(Exception e){
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}

		}
		finally {
			session.close();
		}
		return datalist;
	}

	@Override
	public List<Costing> getCostingList(String userId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<Costing> datalist=new ArrayList<Costing>();
		Costing temp = null;
		try{	
			tx=session.getTransaction();
			tx.begin();	
			String sql="select cc.costingNo,cc.StyleId,sc.StyleNo,cc.ItemId,id.itemname,sum(cc.amount) as amount \r\n" + 
					"from TbCostingCreate cc\r\n" + 
					"left join TbStyleCreate sc\r\n" + 
					"on cc.StyleId = sc.StyleId\r\n" + 
					"left join tbItemDescription id\r\n" + 
					"on cc.ItemId = id.itemid\r\n" + 
					"where cc.userId='"+userId+"' group by cc.costingNo,cc.StyleId,sc.StyleNo,cc.ItemId,id.itemname";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				temp = new Costing("0", element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), "","","", "", "",0.0, 0.0, 0.0, 0.0, 0.0, Double.valueOf(element[5].toString()), 0.0,"", "0");
				temp.setCostingNo(element[0].toString());
				datalist.add(temp);				
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
	public List<Costing> cloningCosting(String costingNo,String oldStyleId, String oldItemId) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<Costing> datalist=new ArrayList<Costing>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select cc.AutoId,cc.StyleId,sc.StyleNo,cc.ItemId,id.itemname,cc.GroupType,cc.FabricationItemId,ISNULL(fi.ItemName,pi.Name) as particula,isnull(si.size,'')as size,cc.UnitId,cc.width,cc.yard,cc.gsm,cc.consumption,cc.UnitPrice,cc.amount,cc.Comission,(select convert(varchar,cc.SubmissionDate,103))as date,cc.UserId \r\n" + 
					"from TbCostingCreate cc\r\n" + 
					"left join TbStyleCreate sc\r\n" + 
					"on cc.StyleId = sc.StyleId\r\n" + 
					"left join tbItemDescription id\r\n" + 
					"on cc.ItemId = id.itemid\r\n" + 
					"left join tbStyleWiseItem si\r\n" + 
					"on cc.ItemId = si.ItemId and cc.StyleId=si.styleid \r\n" + 
					"left join TbFabricsItem fi\r\n" + 
					"on cc.FabricationItemId = fi.id and cc.GroupType='1'\r\n" + 
					"left join TbParticularItemInfo pi\r\n" + 
					"on cc.FabricationItemId = pi.AutoId and cc.GroupType='2' \r\n"+
					"where cc.costingNo='"+costingNo+"' and cc.styleId='"+oldStyleId+"' and cc.itemId='"+oldItemId+"'";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				String date[] = element[17].toString().split("/");	
				datalist.add(new Costing(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(),element[5].toString(), element[6].toString(), element[7].toString(), element[8].toString(), element[9].toString(), Double.valueOf(element[10].toString()), Double.valueOf(element[11].toString()), Double.valueOf(element[12].toString()),Double.valueOf(element[13].toString()), Double.valueOf(element[14].toString()), Double.valueOf(element[15].toString()), Double.valueOf(element[16].toString()), date[2]+"-"+date[1]+"-"+date[1], element[18].toString()));				
			}

			tx.commit();
			return datalist;
		}
		catch(Exception ee){

			if (tx != null) {
				tx.rollback();
				return datalist;
			}
			ee.printStackTrace();
		}

		finally {
			session.close();
		}

		return datalist;
	}

	@Override
	public Costing getCostingItem(String autoId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		Costing costing=null;	
		try{	
			tx=session.getTransaction();
			tx.begin();	
			String sql="select cc.AutoId,cc.StyleId,sc.StyleNo,cc.ItemId,id.itemname,cc.GroupType,cc.FabricationItemId,ISNULL(fi.ItemName,pi.Name) as particula,isnull(si.size,'')as size,cc.UnitId,cc.width,cc.yard,cc.gsm,cc.consumption,cc.UnitPrice,cc.amount,cc.Comission,(select convert(varchar,cc.SubmissionDate,103))as date,cc.UserId \r\n" + 
					"from TbCostingCreate cc\r\n" + 
					"left join TbStyleCreate sc\r\n" + 
					"on cc.StyleId = sc.StyleId\r\n" + 
					"left join tbItemDescription id\r\n" + 
					"on cc.ItemId = id.itemid\r\n" + 
					"left join tbStyleWiseItem si\r\n" + 
					"on cc.ItemId = si.ItemId \r\n" + 
					"left join TbFabricsItem fi\r\n" + 
					"on cc.FabricationItemId = fi.id and cc.GroupType='1'\r\n" + 
					"left join TbParticularItemInfo pi\r\n" + 
					"on cc.FabricationItemId = pi.AutoId and cc.GroupType='2' \r\n"+
					"where cc.autoId='"+autoId+"'";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				costing = new Costing(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(),element[5].toString(), element[6].toString(), element[7].toString(), element[8].toString(), element[9].toString(), Double.valueOf(element[10].toString()), Double.valueOf(element[11].toString()), Double.valueOf(element[12].toString()),Double.valueOf(element[13].toString()), Double.valueOf(element[14].toString()), Double.valueOf(element[15].toString()), Double.valueOf(element[16].toString()), element[17].toString(), element[18].toString());				
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
		return costing;
	}

	@Override
	public boolean addBuyerPoItem(BuyerPoItem buyerPoItem) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();

			double unitCmt=0,unitFob=0,comissionAmt;



			String sql="select * from funFOBorCMTPrice('"+buyerPoItem.getStyleId()+"','"+buyerPoItem.getItemId()+"')";
			List list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				unitFob = Double.valueOf(element[0].toString());
				unitCmt = Double.valueOf(element[1].toString());
			}

			buyerPoItem.setUnitCmt(unitCmt);
			buyerPoItem.setUnitFob(unitFob);
			buyerPoItem.setTotalPrice( buyerPoItem.getTotalUnit()*buyerPoItem.getUnitCmt());
			buyerPoItem.setTotalAmount(buyerPoItem.getTotalUnit()*buyerPoItem.getUnitFob());

			sql="insert into TbBuyerOrderEstimateDetails (buyerId,BuyerOrderId,CustomerOrder,PurchaseOrder,ShippingMarks,FactoryId,StyleId,ItemId,ColorId,SizeReg,sizeGroupId,TotalUnit,UnitCmt,TotalPrice,UnitFob,TotalAmount,EntryTime,UserId) "
					+ "values('"+buyerPoItem.getBuyerId()+"','"+buyerPoItem.getBuyerPOId()+"','"+buyerPoItem.getCustomerOrder()+"','"+buyerPoItem.getPurchaseOrder()+"','"+buyerPoItem.getShippingMark()+"','"+buyerPoItem.getFactoryId()+"','"+buyerPoItem.getStyleId()+"','"+buyerPoItem.getItemId()+"','"+buyerPoItem.getColorId()+"','','"+buyerPoItem.getSizeGroupId()+"','"+buyerPoItem.getTotalUnit()+"','"+buyerPoItem.getUnitCmt()+"','"+df.format(buyerPoItem.getTotalPrice())+"','"+buyerPoItem.getUnitFob()+"','"+df.format(buyerPoItem.getTotalAmount())+"',CURRENT_TIMESTAMP,'"+buyerPoItem.getUserId()+"');";
			session.createSQLQuery(sql).executeUpdate();

			String itemAutoId ="";
			sql="select max(autoId) as itemAutoId from TbBuyerOrderEstimateDetails where BuyerOrderId='"+buyerPoItem.getBuyerPOId()+"' and customerOrder='"+buyerPoItem.getCustomerOrder()+"' and purchaseOrder='"+buyerPoItem.getPurchaseOrder()+"' and userId='"+buyerPoItem.getUserId()+"'";
			list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				itemAutoId =  iter.next().toString();	
			}

			int listSize=buyerPoItem.getSizeList().size();
			for(int i=0;i<listSize;i++) {
				sql = "insert into tbSizeValues (LinkedAutoId,sizeGroupId,sizeId,sizeQuantity,type,entryTime,userId) values('"+itemAutoId+"','"+buyerPoItem.getSizeGroupId()+"','"+buyerPoItem.getSizeList().get(i).getSizeId()+"','"+buyerPoItem.getSizeList().get(i).getSizeQuantity()+"','"+SizeValuesType.BUYER_PO.getType()+"',CURRENT_TIMESTAMP,'"+buyerPoItem.getUserId()+"');";
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
	public boolean editBuyerPoItem(BuyerPoItem buyerPoItem) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="update TbBuyerOrderEstimateDetails set buyerId='"+buyerPoItem.getBuyerId()+"',BuyerOrderId='"+buyerPoItem.getBuyerPOId()+"',CustomerOrder='"+buyerPoItem.getCustomerOrder()+"',PurchaseOrder='"+buyerPoItem.getPurchaseOrder()+"',ShippingMarks='"+buyerPoItem.getShippingMark()+"',FactoryId='"+buyerPoItem.getFactoryId()+"',StyleId='"+buyerPoItem.getStyleId()+"',ItemId='"+buyerPoItem.getItemId()+"',ColorId='"+buyerPoItem.getColorId()+"',SizeReg='',sizeGroupId='"+buyerPoItem.getSizeGroupId()+"',TotalUnit='"+buyerPoItem.getTotalUnit()+"',UnitCmt='"+buyerPoItem.getUnitCmt()+"',TotalPrice='"+df.format(buyerPoItem.getTotalPrice())+"',UnitFob='"+buyerPoItem.getUnitFob()+"',TotalAmount='"+df.format(buyerPoItem.getTotalAmount())+"',EntryTime=CURRENT_TIMESTAMP,UserId='"+buyerPoItem.getUserId()+"' where autoId='"+buyerPoItem.getAutoId()+"'";		
			session.createSQLQuery(sql).executeUpdate();

			sql = "delete from tbSizeValues where LinkedAutoId='"+buyerPoItem.getAutoId()+"'";
			session.createSQLQuery(sql).executeUpdate();

			int listSize=buyerPoItem.getSizeList().size();
			for(int i=0;i<listSize;i++) {
				sql = "insert into tbSizeValues (LinkedAutoId,sizeGroupId,sizeId,sizeQuantity,type,entryTime,userId) values('"+buyerPoItem.getAutoId()+"','"+buyerPoItem.getSizeGroupId()+"','"+buyerPoItem.getSizeList().get(i).getSizeId()+"','"+buyerPoItem.getSizeList().get(i).getSizeQuantity()+"','"+SizeValuesType.BUYER_PO.getType()+"',CURRENT_TIMESTAMP,'"+buyerPoItem.getUserId()+"');";
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
	public List<BuyerPoItem> getBuyerPOItemList(String buyerPOId,String userId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<BuyerPoItem> dataList=new ArrayList<BuyerPoItem>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select autoId,BuyerOrderId,bo.StyleId,sc.StyleNo,bo.ItemId,id.itemname,FactoryId,bo.ColorId,c.Colorname,CustomerOrder,PurchaseOrder,ShippingMarks,SizeReg,sizeGroupId,TotalUnit,UnitCmt,TotalPrice,UnitFob,TotalAmount,bo.userId \r\n" + 
					"from TbBuyerOrderEstimateDetails bo\r\n" + 
					"left join TbStyleCreate sc \r\n" + 
					"on bo.StyleId = sc.StyleId\r\n" + 
					"left join tbItemDescription id\r\n" + 
					"on bo.ItemId = id.itemid\r\n" + 
					"left join tbColors c\r\n" + 
					"on bo.ColorId = c.ColorId\r\n" + 
					"where BuyerOrderId='"+buyerPOId+"' and bo.userId='"+userId+"' order by sizeGroupId";
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();							
				dataList.add(new BuyerPoItem(element[0].toString(), buyerPOId, "0", element[2].toString(), element[3].toString(),element[4].toString(), element[5].toString(), element[6].toString(), element[7].toString(), element[8].toString(), element[9].toString(), element[10].toString(), element[11].toString(), element[12].toString(), element[13].toString(), Double.valueOf(element[14].toString()), Double.valueOf(element[15].toString()), Double.valueOf(element[16].toString()), Double.valueOf(element[17].toString()), Double.valueOf(element[18].toString()), element[19].toString()));
			}

			for (BuyerPoItem buyerPoItem : dataList) {
				sql = "select bs.sizeId,ss.sizeName,bs.sizeQuantity from tbSizeValues bs\r\n" + 
						"join tbStyleSize ss \r\n" + 
						"on ss.id = bs.sizeId \r\n" + 
						"where bs.type='"+SizeValuesType.BUYER_PO.getType()+"' and bs.linkedAutoId = '"+buyerPoItem.getAutoId()+"' and bs.sizeGroupId = '"+buyerPoItem.getSizeGroupId()+"' \r\n" + 
						"order by ss.sortingNo";
				List<?> list2 = session.createSQLQuery(sql).list();
				ArrayList<Size> sizeList=new ArrayList<Size>();
				for(Iterator<?> iter = list2.iterator(); iter.hasNext();)
				{	
					Object[] element = (Object[]) iter.next();	
					sizeList.add(new Size(element[0].toString(), element[2].toString()));
				}
				buyerPoItem.setSizeList(sizeList);
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
	public BuyerPoItem getBuyerPOItem(String itemAutoId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		BuyerPoItem buyerPoItem = null;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select bod.autoId,BuyerOrderId,isnull(bod.BuyerId,'')as buyerId,bod.StyleId,sc.StyleNo,bod.ItemId,id.itemname,FactoryId,bod.ColorId,c.Colorname,CustomerOrder,PurchaseOrder,ShippingMarks,SizeReg,sizeGroupId,bod.TotalUnit,bod.UnitCmt,bod.TotalPrice,bod.UnitFob,bod.TotalAmount,bod.userId \r\n" + 
					"from TbBuyerOrderEstimateDetails bod\r\n" + 
					"left join TbStyleCreate sc \r\n" + 
					"on bod.StyleId = sc.StyleId\r\n" + 
					"left join tbItemDescription id\r\n" + 
					"on bod.ItemId = id.itemid\r\n" + 
					"left join tbColors c\r\n" + 
					"on bod.ColorId = c.ColorId\r\n" + 
					"where bod.autoId='"+itemAutoId+"' order by sizeGroupId";
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();							
				buyerPoItem = new BuyerPoItem(element[0].toString(), "0", element[2].toString(), element[3].toString(),element[4].toString(), element[5].toString(), element[6].toString(), element[7].toString(), element[8].toString(), element[9].toString(), element[10].toString(), element[11].toString(), element[12].toString(), element[13].toString(),element[14].toString(), Double.valueOf(element[15].toString()), Double.valueOf(element[16].toString()), Double.valueOf(element[17].toString()), Double.valueOf(element[18].toString()), Double.valueOf(element[19].toString()), element[20].toString());
			}


			sql = "select bs.sizeId,ss.sizeName,bs.sizeQuantity from tbSizeValues bs\r\n" + 
					"join tbStyleSize ss \r\n" + 
					"on ss.id = bs.sizeId \r\n" + 
					"where bs.linkedAutoId = '"+buyerPoItem.getAutoId()+"' and bs.sizeGroupId = '"+buyerPoItem.getSizeGroupId()+"' and bs.type='"+SizeValuesType.BUYER_PO.getType()+"' \r\n" + 
					"order by ss.sortingNo";
			List<?> list2 = session.createSQLQuery(sql).list();
			ArrayList<Size> sizeList=new ArrayList<Size>();
			for(Iterator<?> iter = list2.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();	
				sizeList.add(new Size(element[0].toString(), element[2].toString()));
			}
			buyerPoItem.setSizeList(sizeList);

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
		return buyerPoItem;
	}

	@Override
	public boolean deleteBuyerPoItem(String itemAutoId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();



			String sql="delete from  TbBuyerOrderEstimateDetails where autoId='"+itemAutoId+"';";
			session.createSQLQuery(sql).executeUpdate();

			sql="delete from  tbSizeValues where linkedAutoId='"+itemAutoId+"' and type='"+SizeValuesType.BUYER_PO.getType()+"';";
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
	public boolean submitBuyerPO(BuyerPO buyerPo) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="insert into TbBuyerOrderEstimateSummary (BuyerId,Period,PaymentTerm,Incoterm,Currency,QCFor,TotalUnit,UnitCmt,TotalPrice,UnitFob,TotalAmount,note,remarks,EntryTime,UserId) "
					+ "values('"+buyerPo.getBuyerId()+"','','"+buyerPo.getPaymentTerm()+"','','"+buyerPo.getCurrency()+"','','"+buyerPo.getTotalUnit()+"','"+buyerPo.getUnitCmt()+"','"+buyerPo.getTotalPrice()+"','"+buyerPo.getUnitFob()+"','"+buyerPo.getTotalAmount()+"','"+buyerPo.getNote()+"','"+buyerPo.getRemarks()+"',CURRENT_TIMESTAMP,'"+buyerPo.getUserId()+"');";
			session.createSQLQuery(sql).executeUpdate();

			String buyerPoId ="";
			sql="select max(autoId) as buyerPoId from TbBuyerOrderEstimateSummary where BuyerId='"+buyerPo.getBuyerId()+"'  and userId='"+buyerPo.getUserId()+"'";
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				buyerPoId =  iter.next().toString();	
			}

			sql = "update TbBuyerOrderEstimateDetails set BuyerOrderId='"+buyerPoId+"' where buyerOrderId='"+buyerPo.getBuyerPoId()+"' and buyerId='"+buyerPo.getBuyerId()+"' and userId='"+buyerPo.getUserId()+"'";
			session.createSQLQuery(sql).executeUpdate();
			
			JSONParser jsonParser = new JSONParser();
			JSONObject itemsObject = (JSONObject)jsonParser.parse(buyerPo.getChangedItemsList());
			JSONArray itemList = (JSONArray) itemsObject.get("list");

			for(int i=0;i<itemList.size();i++) {
				JSONObject item = (JSONObject) itemList.get(i);
				sql="update TbBuyerOrderEstimateDetails set UnitCmt='"+item.get("unitCmt")+"',TotalPrice='"+df.format(item.get("totalPrice"))+"',UnitFob='"+item.get("unitFob")+"',TotalAmount='"+df.format(item.get("totalAmount"))+"',EntryTime=CURRENT_TIMESTAMP,UserId='"+buyerPo.getUserId()+"' where autoId='"+item.get("autoId")+"'";		
				session.createSQLQuery(sql).executeUpdate();
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
	public boolean editBuyerPO(BuyerPO buyerPo) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="update TbBuyerOrderEstimateSummary set BuyerId='"+buyerPo.getBuyerId()+"',Period='',PaymentTerm='"+buyerPo.getPaymentTerm()+"',Incoterm='',Currency='"+buyerPo.getCurrency()+"',QCFor='',TotalUnit='"+buyerPo.getTotalUnit()+"',UnitCmt='"+buyerPo.getUnitCmt()+"',TotalPrice='"+buyerPo.getTotalPrice()+"',UnitFob='"+buyerPo.getUnitFob()+"',TotalAmount='"+buyerPo.getTotalAmount()+"',note='"+buyerPo.getNote()+"',remarks='"+buyerPo.getRemarks()+"',EntryTime=CURRENT_TIMESTAMP,UserId='"+buyerPo.getUserId()+"' where autoId='"+buyerPo.getBuyerPoId()+"'";		
			session.createSQLQuery(sql).executeUpdate();



			JSONParser jsonParser = new JSONParser();
			JSONObject itemsObject = (JSONObject)jsonParser.parse(buyerPo.getChangedItemsList());
			JSONArray itemList = (JSONArray) itemsObject.get("list");

			for(int i=0;i<itemList.size();i++) {
				JSONObject item = (JSONObject) itemList.get(i);
				sql="update TbBuyerOrderEstimateDetails set UnitCmt='"+item.get("unitCmt")+"',TotalPrice='"+df.format(item.get("totalPrice"))+"',UnitFob='"+item.get("unitFob")+"',TotalAmount='"+df.format(item.get("totalAmount"))+"',EntryTime=CURRENT_TIMESTAMP,UserId='"+buyerPo.getUserId()+"' where autoId='"+item.get("autoId")+"'";		
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
	public List<BuyerPO> getBuyerPoList(String userId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<BuyerPO> dataList=new ArrayList<BuyerPO>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select autoId,BuyerId,b.name,(select convert(varchar,bos.EntryTime,103))as date from TbBuyerOrderEstimateSummary bos\r\n" + 
					"join tbBuyer b\r\n" + 
					"on b.id = bos.BuyerId\r\n" + 
					" where bos.userId='"+userId+"' order by bos.autoId desc";
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();	
				dataList.add(new BuyerPO(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString()));
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
	public BuyerPO getBuyerPO(String buyerPoNo) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		BuyerPO buyerPo = null;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select autoId,BuyerId,b.name,PaymentTerm,Currency,note,remarks,bos.UserId\r\n" + 
					"from TbBuyerOrderEstimateSummary bos\r\n" + 
					"join tbBuyer b\r\n" + 
					"on bos.BuyerId = b.id\r\n" + 
					"where autoId = '"+buyerPoNo+"'";
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				buyerPo= new BuyerPO(element[0].toString(), element[1].toString(), element[3].toString(), element[4].toString(), 0.0, 0.0, 0.0,0.0,0.0, element[5].toString(),element[6].toString(), element[7].toString());
				buyerPo.setBuyerName(element[2].toString());
			}
			buyerPo.setItemList(getBuyerPOItemList(buyerPoNo,buyerPo.getUserId()));

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
		return buyerPo;
	}



	@Override
	public String maxAIno() {
		// TODO Auto-generated method stub

		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		String query="";

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select isnull(max(AINo),0)+1 as AINo from tbAccessoriesIndent";
			System.out.println(" max ");

			List<?> list = session.createSQLQuery(sql).list();


			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				//Object[] element = (Object[]) iter.next();

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
	public List<CommonModel> PurchaseOrders(String userId) {
		// TODO Auto-generated method stub

		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<CommonModel> query=new ArrayList<CommonModel>();

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select BuyerOrderId,PurchaseOrder from TbBuyerOrderEstimateDetails where userId='"+userId+"' group by BuyerOrderId,PurchaseOrder";
			System.out.println(" max ");

			List<?> list = session.createSQLQuery(sql).list();


			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				query.add(new CommonModel(element[0].toString(),element[1].toString()));

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
	public List<CommonModel> Colors(String style, String item) {
		// TODO Auto-generated method stub

		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<CommonModel> query=new ArrayList<CommonModel>();

		try{
			tx=session.getTransaction();
			tx.begin();

			//String sql="select ColorId, Colorname from tbColors";
			String sql="SELECT ColorId, (select colorname from tbColors b where b.colorid=a.ColorId) FROM  TbBuyerOrderEstimateDetails a WHERE  StyleId = '"+style+"' and itemid='"+item+"'";
			System.out.println(" max ");

			List<?> list = session.createSQLQuery(sql).list();


			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				query.add(new CommonModel(element[0].toString(),element[1].toString()));

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
	public List<CommonModel> Items(String buyerorderid,String style) {
		// TODO Auto-generated method stub

		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<CommonModel> query=new ArrayList<CommonModel>();

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select itemid, isnull((select itemname from tbItemDescription where itemid=a.ItemId),'') as itemname from TbBuyerOrderEstimateDetails a where a.BuyerOrderId='"+buyerorderid+"' and a.styleid='"+style+"' group by a.ItemId";
			//System.out.println(" max ");

			List<?> list = session.createSQLQuery(sql).list();


			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				query.add(new CommonModel(element[0].toString(),element[1].toString()));

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
	public List<CommonModel> AccessoriesItem(String type) {
		// TODO Auto-generated method stub

		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<CommonModel> query=new ArrayList<CommonModel>();

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="";
			if(type.equals("1")) {
				sql="select itemid, itemname from TbAccessoriesItem";
			}
			else if(type.equals("2")) {
				sql="select itemid, itemname from TbAccessoriesItem where itemname like '%carto%' " ;
			}
			System.out.println(" max ");

			List<?> list = session.createSQLQuery(sql).list();


			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				query.add(new CommonModel(element[0].toString(),element[1].toString()));

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
	public List<CommonModel> Size(String buyerorderid, String style, String item, String color) {
		// TODO Auto-generated method stub

		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<CommonModel> query=new ArrayList<CommonModel>();

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select (select id from tbStyleSize where id=a.sizeId) as id,(select sizename from tbStyleSize where id=a.sizeId) as name from TbBuyerOrderEstimateDetails b, tbSizeValues  a where a.linkedAutoId=b.autoId and b.buyerorderid='"+buyerorderid+"' and b.StyleId='"+style+"' and b.ItemId='"+item+"' and b.colorId='"+color+"'";
			System.out.println(" max ");

			List<?> list = session.createSQLQuery(sql).list();


			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				query.add(new CommonModel(element[0].toString(),element[1].toString()));

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
	public List<CommonModel> Unit() {
		// TODO Auto-generated method stub

		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<CommonModel> query=new ArrayList<CommonModel>();

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select Unitid,unitName,cast(unitvalue as Integer) as unitValue from tbunits";
			System.out.println(" max ");

			List<?> list = session.createSQLQuery(sql).list();


			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				query.add(new CommonModel(element[0].toString(),element[1].toString(),element[2].toString()));
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
	public List<CommonModel> Brands() {
		// TODO Auto-generated method stub

		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<CommonModel> query=new ArrayList<CommonModel>();

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select id, name from tbbrands";
			System.out.println(" max ");

			List<?> list = session.createSQLQuery(sql).list();


			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				query.add(new CommonModel(element[0].toString(),element[1].toString()));

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
	public List<CommonModel> ShippingMark(String po, String style, String item) {
		// TODO Auto-generated method stub

		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<CommonModel> query=new ArrayList<CommonModel>();

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select shippingmarks from TbBuyerOrderEstimateDetails where BuyerOrderId='"+po+"' and StyleId='"+style+"' and ItemId='"+item+"' group by shippingmarks";
			System.out.println(" max ");

			List<?> list = session.createSQLQuery(sql).list();


			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				//Object[] element = (Object[]) iter.next();

				query.add(new CommonModel("",iter.next().toString()));

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
	public List<CommonModel> AllColors() {
		// TODO Auto-generated method stub

		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<CommonModel> query=new ArrayList<CommonModel>();

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select ColorId, Colorname from tbColors";
			System.out.println(" all colors ");

			List<?> list = session.createSQLQuery(sql).list();


			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				query.add(new CommonModel(element[0].toString(),element[1].toString()));

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
	public List<CommonModel> SizewiseQty(String buyerorderid,String style,String item,String color,String size) {
		// TODO Auto-generated method stub

		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<CommonModel> query=new ArrayList<CommonModel>();

		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="";
			if (size.equals("None")) {
				sql="select isnull(sum(sizeQuantity),0) as qty from TbBuyerOrderEstimateDetails b, tbSizeValues  a where a.linkedAutoId=b.autoid and b.buyerorderid='"+buyerorderid+"' and b.StyleId='"+style+"' and b.ItemId='"+item+"' and b.colorId='"+color+"' ";

			}
			else {
				sql="select isnull(sum(sizeQuantity),0) as qty from TbBuyerOrderEstimateDetails b, tbSizeValues  a where a.linkedAutoId=b.autoid and b.buyerorderid='"+buyerorderid+"' and b.StyleId='"+style+"' and b.ItemId='"+item+"' and b.colorId='"+color+"' and a.sizeId='"+size+"'";
			}

			System.out.println(sql);
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				query.add(new CommonModel(iter.next().toString()));
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
	public List<AccessoriesIndent> getAccessoriesRecyclingData(String query) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<AccessoriesIndent> dataList=new ArrayList<AccessoriesIndent>();
		AccessoriesIndent tempAccessories = null;

		try{
			tx=session.getTransaction();
			tx.begin();
			List<?> list = session.createSQLQuery(query).list();


			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				tempAccessories = new AccessoriesIndent();
				tempAccessories.setPurchaseOrder(element[0].toString());
				tempAccessories.setStyleId(element[1].toString());
				tempAccessories.setStyleNo(element[2].toString());
				tempAccessories.setItemId(element[3].toString());
				tempAccessories.setItemname(element[4].toString());
				tempAccessories.setItemColorId(element[5].toString());
				tempAccessories.setItemcolor(element[6].toString());
				tempAccessories.setShippingmark(element[7].toString());
				tempAccessories.setOrderqty(element[8].toString());
				//tempAccessories.setOrderqty(element[9].toString());

				dataList.add(tempAccessories);

			}



			tx.commit();

			return dataList;
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
	public List<AccessoriesIndent> getAccessoriesRecyclingDataWithSize(String query,String query2) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<AccessoriesIndent> dataList=new ArrayList<AccessoriesIndent>();
		AccessoriesIndent tempAccessories = null;
		ArrayList<Size> sizeList = null;

		try{
			tx=session.getTransaction();
			tx.begin();
			List<?> list = session.createSQLQuery(query).list();


			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				tempAccessories = new AccessoriesIndent();
				tempAccessories.setPurchaseOrder(element[0].toString());
				tempAccessories.setStyleId(element[1].toString());
				tempAccessories.setStyleNo(element[2].toString());
				tempAccessories.setItemId(element[3].toString());
				tempAccessories.setItemname(element[4].toString());
				tempAccessories.setItemColorId(element[5].toString());
				tempAccessories.setItemcolor(element[6].toString());
				tempAccessories.setShippingmark(element[7].toString());
				tempAccessories.setOrderqty(element[8].toString());
				tempAccessories.setSizeGroupId(element[9].toString());

				dataList.add(tempAccessories);

			}

			for(int i = 0;i< dataList.size();i++){
				query = query2.replace("SIZEGROUPID", dataList.get(i).getSizeGroupId());
				list = session.createSQLQuery(query).list();
				sizeList = new ArrayList<>();
				for(Iterator<?> iter = list.iterator(); iter.hasNext();)
				{	
					Object[] element = (Object[]) iter.next();
					sizeList.add(new Size(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString()));
				}
				dataList.get(i).setSizeList(sizeList);
			}


			tx.commit();

			return dataList;
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
	public boolean insertAccessoriesIndent(AccessoriesIndent ai) {
		// TODO Auto-generated method stub

		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		boolean inserted=false;
		List<CommonModel> query=new ArrayList<CommonModel>();

		try{
			tx=session.getTransaction();
			tx.begin();


			String sql="insert into tbAccessoriesIndent (styleid, PurchaseOrder, "
					+ "Itemid, ColorId, "
					+ "ShippingMarks, accessoriesItemId, "
					+ "accessoriesSize, "
					+ "size, PerUnit, TotalBox,"
					+ " OrderQty, QtyInDozen, "
					+ "ReqPerPices, ReqPerDoz, "
					+ "DividedBy, PercentageExtra, "
					+ "PercentageExtraQty, TotalQty, "
					+ "UnitId, RequireUnitQty, "
					+ "IndentColorId, IndentBrandId, IndentDate, "
					+ " IndentTime, IndentPostBy) values('"+ai.getStyle()+"','"+ai.getPo()+"','"+ai.getItemname()+"',"
					+ "'"+ai.getItemcolor()+"','"+ai.getShippingmark()+"','"+ai.getAccessoriesname()+"','"+ai.getAccessoriessize()+"',"
					+ "'"+ai.getSize()+"','"+ai.getPerunit()+"','"+ai.getTotalbox()+"','"+ai.getOrderqty()+"','"+ai.getQtyindozen()+"',"
					+ "'"+ai.getReqperpcs()+"','"+ai.getReqperdozen()+"','"+ai.getDividedby()+"','"+ai.getExtrainpercent()+"','"+ai.getPercentqty()+"',"
					+ "'"+ai.getTotalqty()+"','"+ai.getUnit()+"','"+ai.getGrandqty()+"','"+ai.getAccessoriescolor()+"','"+ai.getBrand()+"',GETDATE(),GETDATE(),'"+ai.getUser()+"')";

			System.out.println(" all colors ");

			session.createSQLQuery(sql).executeUpdate();
			inserted=true;






			tx.commit();

			return inserted;
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

		return inserted;

	}

	@Override
	public List<AccessoriesIndent> PendingList() {
		// TODO Auto-generated method stub

		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<AccessoriesIndent> query=new ArrayList<AccessoriesIndent>();

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select AccIndentId,styleid,PurchaseOrder,(select itemname from tbItemDescription where itemid=a.Itemid) as itemname,(select colorname from tbColors where ColorId=a.ColorId) as color, (select itemname from TbAccessoriesItem where itemid=a.accessoriesItemId) as accessoriesitem,(select b.sizeName from tbStyleSize b where b.id=a.accessoriesSize) as accessoriessize, a.RequireUnitQty  from tbAccessoriesIndent a where AINo is null";

			System.out.println(" all colors ");

			List<?> list = session.createSQLQuery(sql).list();


			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				//	query.add(new AccessoriesIndent(element[0].toString(),element[1].toString(),element[2].toString(),element[3].toString(),element[4].toString(),element[5].toString(),element[6].toString(),element[7].toString()));

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
	public List<AccessoriesIndent> getPostedAccessoriesIndent(String userId) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<AccessoriesIndent> query=new ArrayList<AccessoriesIndent>();
		AccessoriesIndent tempAccIndent;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select a.AINo,a.PurchaseOrder,(SELECT CONVERT(varchar, a.IndentDate, 25)) indentDate\r\n" + 
					"from tbAccessoriesIndent a \r\n" + 
					"where IndentPostBy='"+userId+"' and AiNo IS NOT NULL \r\n" + 
					"group by a.AINo,a.PurchaseOrder,a.IndentDate";


			List<?> list = session.createSQLQuery(sql).list();


			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				tempAccIndent = new AccessoriesIndent();
				tempAccIndent.setAiNo(element[0].toString());
				tempAccIndent.setPurchaseOrder(element[1].toString());
				tempAccIndent.setIndentDate(element[2].toString());
				query.add(tempAccIndent);

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
	public List<CommonModel> Styles(String po) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<CommonModel> query=new ArrayList<CommonModel>();

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select styleid, (select StyleNo from TbStyleCreate where StyleId=a.StyleId) as stylename from TbBuyerOrderEstimateDetails a where BuyerOrderId='"+po+"' group by StyleId";
			System.out.println(" max ");

			List<?> list = session.createSQLQuery(sql).list();


			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				query.add(new CommonModel(element[0].toString(),element[1].toString()));

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
	public List<CommonModel> styleItemsWiseColor(String buyerorderid,String style,String item) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<CommonModel> query=new ArrayList<CommonModel>();

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select a.ColorId,(select Colorname from tbColors where ColorId=a.ColorId) as ColorName from TbBuyerOrderEstimateDetails a where a.BuyerOrderId='"+buyerorderid+"' and a.StyleId='"+style+"' and a.ItemId='"+item+"' group by a.ColorId";
			System.out.println(" max ");

			List<?> list = session.createSQLQuery(sql).list();


			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				query.add(new CommonModel(element[0].toString(),element[1].toString()));

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
	public List<AccessoriesIndent> getAccessoriesIndent(String po, String style, String itemname, String itemcolor) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<AccessoriesIndent> query=new ArrayList<AccessoriesIndent>();

		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="select a.AccIndentId,a.PurchaseOrder,(select StyleNo from TbStyleCreate where StyleId=a.StyleId) as StyleNo,(select ItemName from tbItemDescription where itemid=a.ItemId) as ItemName,(select ColorName from tbColors where ColorId=a.ColorId) as ColorName,a.ShippingMarks,(select itemname from TbAccessoriesItem where itemid=a.accessoriesItemId) as AccessoriesName,isnull((select sizeName from tbStyleSize where id=a.size),'') as SizeName,a.RequireUnitQty from tbAccessoriesIndent a where a.PurchaseOrder='"+po+"' and a.StyleId='"+style+"' and a.ItemId='"+itemname+"' and a.ColorId='"+itemcolor+"'";
			System.out.println(" max ");

			List<?> list = session.createSQLQuery(sql).list();


			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				query.add(new AccessoriesIndent(element[0].toString(),element[1].toString(),element[2].toString(),element[3].toString(),element[4].toString(),element[5].toString(),element[6].toString(),element[7].toString(),element[8].toString()));

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
	public List<AccessoriesIndent> getPendingAccessoriesIndent() {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<AccessoriesIndent> query=new ArrayList<AccessoriesIndent>();

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select a.AccIndentId,a.PurchaseOrder,(select StyleNo from TbStyleCreate where StyleId=a.StyleId) as StyleNo,(select ItemName from tbItemDescription where itemid=a.ItemId) as ItemName,(select ColorName from tbColors where ColorId=a.ColorId) as ColorName,a.ShippingMarks,(select itemname from TbAccessoriesItem where itemid=a.accessoriesItemId) as AccessoriesName,isnull((select sizeName from tbStyleSize where id=a.size),'') as SizeName,a.RequireUnitQty from tbAccessoriesIndent a where a.AINo IS NULL";

			List<?> list = session.createSQLQuery(sql).list();


			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				query.add(new AccessoriesIndent(element[0].toString(),element[1].toString(),element[2].toString(),element[3].toString(),element[4].toString(),element[5].toString(),element[6].toString(),element[7].toString(),element[8].toString()));

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
	public List<AccessoriesIndent> getAccessoriesIndentItemList(String accessoriesIndentId) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		AccessoriesIndent tempIndent = null;
		List<AccessoriesIndent> dataList=new ArrayList<AccessoriesIndent>();

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select ai.AINo,ai.AccIndentId,ai.PurchaseOrder,ai.styleid,isnull(sc.StyleNo,'')as StyleNo,ai.Itemid,ISNULL(id.itemname,'') as ItemName,ai.ColorId,ISNULL(c.colorName,'')as Color,ai.ShippingMarks,ai.size,ISNULL(ss.sizeName,'') as SizeName,ai.OrderQty,ai.QtyInDozen,ai.ReqPerPices,ai.ReqPerDoz,ai.PerUnit,ai.TotalBox,ai.DividedBy,ai.PercentageExtra,ai.PercentageExtraQty,ai.TotalQty,ai.accessoriesItemId,ISNULL(aItem.itemname,'') as AccessoriesName,ai.accessoriesSize,ai.IndentColorId,isnull(ic.Colorname,'') as indentColor,ai.IndentBrandId ,ISNULL(b.name,'') as BrandName,ai.UnitId,ISNULL(u.unitname,'') as UnitName,ai.RequireUnitQty \r\n" + 
					"from tbAccessoriesIndent ai  \r\n" + 
					"left join TbStyleCreate sc \r\n" + 
					"on ai.styleid = cast(sc.StyleId as varchar) \r\n" + 
					"left join tbItemDescription id \r\n" + 
					"on ai.Itemid = cast(id.itemid as varchar) \r\n" + 
					"left join tbColors c \r\n" + 
					"on ai.ColorId = cast(c.colorId as varchar) \r\n" + 
					"left join tbbrands b \r\n" + 
					"on ai.IndentBrandId = b.id \r\n" + 
					"left join TbAccessoriesItem aItem \r\n" + 
					"on ai.accessoriesItemId = aItem.itemid \r\n" + 
					"left join tbStyleSize ss \r\n" + 
					"on ai.size = ss.id \r\n" + 
					"left join tbColors ic\r\n" + 
					"on ai.IndentColorId = ic.ColorId\r\n" + 
					"left join tbunits u \r\n" + 
					"on ai.UnitId = u.Unitid \r\n" + 
					"where ai.AINo = '"+accessoriesIndentId+"' order by ai.AccIndentId,ai.ColorId,ai.accessoriesItemId,ss.sortingNo";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				tempIndent = new AccessoriesIndent();
				tempIndent.setAiNo(element[0].toString());
				tempIndent.setAutoid(element[1].toString());
				tempIndent.setPurchaseOrder(element[2].toString());
				tempIndent.setStyleId(element[3].toString());
				tempIndent.setStyleNo(element[4].toString());
				tempIndent.setItemId(element[5].toString());
				tempIndent.setItemname(element[6].toString());
				tempIndent.setItemColorId(element[7].toString());
				tempIndent.setItemcolor(element[8].toString());
				tempIndent.setShippingmark(element[9].toString());
				tempIndent.setSize(element[10].toString());
				tempIndent.setSizeName(element[11].toString());
				tempIndent.setOrderqty(element[12].toString());
				tempIndent.setQtyindozen(element[13].toString());
				tempIndent.setReqperpcs(element[14].toString());
				tempIndent.setReqperdozen(element[15].toString());
				tempIndent.setPerunit(element[16].toString());
				tempIndent.setTotalbox(element[17].toString());
				tempIndent.setDividedby(element[18].toString());
				tempIndent.setExtrainpercent(element[19].toString());
				tempIndent.setPercentqty(element[20].toString());
				tempIndent.setTotalqty(element[21].toString());
				tempIndent.setAccessoriesId(element[22].toString());
				tempIndent.setAccessoriesName(element[23].toString());
				tempIndent.setAccessoriessize(element[24].toString());
				tempIndent.setAccessoriesColorId(element[25].toString());
				tempIndent.setAccessoriescolor(element[26].toString());
				tempIndent.setIndentBrandId(element[27].toString());
				tempIndent.setBrand(element[28].toString());
				tempIndent.setUnitId(element[29].toString());
				tempIndent.setUnit(element[30].toString());
				tempIndent.setRequiredUnitQty(element[31].toString());
				dataList.add(tempIndent);
			}

			tx.commit();

			return dataList;
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
	public boolean editAccessoriesIndent(AccessoriesIndent ai) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<CommonModel> query=new ArrayList<CommonModel>();

		try{
			tx=session.getTransaction();
			tx.begin();


			String sql="update tbAccessoriesIndent set  accessoriesItemId='"+ai.getAccessoriesId()+"',accessoriesSize='"+ai.getAccessoriessize()+"',indentColorId='"+ai.getAccessoriesColorId()+"',indentBrandId='"+ai.getIndentBrandId()+"',unitId='"+ai.getUnitId()+"',PerUnit='"+ai.getPerunit()+"',TotalBox='"+ai.getTotalbox()+"',OrderQty='"+ai.getOrderqty()+"',QtyInDozen='"+ai.getQtyindozen()+"',"
					+ "ReqPerPices='"+ai.getReqperpcs()+"',ReqPerDoz='"+ai.getReqperdozen()+"',DividedBy='"+ai.getDividedby()+"',PercentageExtra='"+ai.getExtrainpercent()+"',PercentageExtraQty='"+ai.getPercentqty()+"',"
					+ "TotalQty='"+ai.getTotalqty()+"',RequireUnitQty='"+ai.getGrandqty()+"',IndentDate=GETDATE(),IndentTime=GETDATE(),IndentPostBy='"+ai.getUser()+"' where AccIndentId='"+ai.getAutoid()+"' and aino='"+ai.getAiNo()+"'";

			session.createSQLQuery(sql).executeUpdate();


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
	public boolean deleteAccessoriesIndent(String accessorienIndentId,String indentAutoId) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<CommonModel> query=new ArrayList<CommonModel>();

		try{
			tx=session.getTransaction();
			tx.begin();


			String sql="delete from tbAccessoriesIndent where AccIndentId='"+indentAutoId+"' and aino='"+accessorienIndentId+"'";

			session.createSQLQuery(sql).executeUpdate();


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
	public String confirmAccessoriesIndent(String accessoriesIndentId, String accessoriesItems) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<CommonModel> query=new ArrayList<CommonModel>();

		try{
			tx=session.getTransaction();
			tx.begin();
			if(accessoriesIndentId.equals("New")) {
				String sql="select (isnull(max(AINo),0)+1) as maxId from tbAccessoriesIndent";
				List<?> list = session.createSQLQuery(sql).list();
				if(list.size()>0) {		
					accessoriesIndentId = list.get(0).toString();
				}
			}

			JSONParser jsonParser = new JSONParser();
			System.out.println(accessoriesItems);
			JSONObject indentObject = (JSONObject)jsonParser.parse(accessoriesItems);
			JSONArray indentList = (JSONArray) indentObject.get("list");

			for(int i=0;i<indentList.size();i++) {
				JSONObject indent = (JSONObject) indentList.get(i);
				String sql="insert into tbAccessoriesIndent (AINo,styleid, PurchaseOrder, "
						+ "Itemid, ColorId, "
						+ "ShippingMarks, accessoriesItemId, "
						+ "accessoriesSize, "
						+ "size, PerUnit, TotalBox,"
						+ " OrderQty, QtyInDozen, "
						+ "ReqPerPices, ReqPerDoz, "
						+ "DividedBy, PercentageExtra, "
						+ "PercentageExtraQty, TotalQty, "
						+ "UnitId, RequireUnitQty, "
						+ "IndentColorId, IndentBrandId, IndentDate, "
						+ " IndentTime, IndentPostBy) values('"+accessoriesIndentId+"','"+indent.get("styleId")+"','"+indent.get("purchaseOrder")+"','"+indent.get("itemId")+"',"
						+ "'"+indent.get("colorId")+"','"+indent.get("shippingMark")+"','"+indent.get("accessoriesItemId")+"','"+indent.get("accessoriesSize")+"',"
						+ "'"+indent.get("sizeId")+"','"+indent.get("perUnit")+"','"+indent.get("totalBox")+"','"+indent.get("orderQty")+"','"+indent.get("dozenQty")+"',"
						+ "'"+indent.get("reqPerPcs")+"','"+indent.get("reqPerDozen")+"','"+indent.get("divideBy")+"','"+indent.get("inPercent")+"','"+indent.get("percentQty")+"',"
						+ "'"+indent.get("totalQty")+"','"+indent.get("unitId")+"','"+indent.get("unitQty")+"','"+indent.get("accessoriesColorId")+"','"+indent.get("accessoriesBrandId")+"',GETDATE(),GETDATE(),'"+indent.get("userId")+"')";

				session.createSQLQuery(sql).executeUpdate();
			}

			tx.commit();

			return accessoriesIndentId;
		}
		catch(Exception e){
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}

		}

		finally {
			session.close();
		}

		return "something wrong";

	}

	@Override
	public String confirmCartonIndent(String cartonIndentId,String cartonItems) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();

			if(cartonIndentId.equals("New")) {
				String sql="select (isnull(max(indentId),0)+1) as maxId from tbAccessoriesIndentForCarton";
				List<?> list = session.createSQLQuery(sql).list();
				if(list.size()>0) {		
					cartonIndentId = list.get(0).toString();
				}
			}

			JSONParser jsonParser = new JSONParser();
			System.out.println(cartonItems);
			JSONObject indentObject = (JSONObject)jsonParser.parse(cartonItems);
			JSONArray indentList = (JSONArray) indentObject.get("list");

			for(int i=0;i<indentList.size();i++) {
				JSONObject indent = (JSONObject) indentList.get(i);
				String sql="insert into tbAccessoriesIndentForCarton ("
						+ "indentId,"
						+ "buyerId,"
						+ "styleid,"
						+ "PurchaseOrder,"
						+ "Itemid,"
						+ "ColorId,"
						+ "ShippingMarks,"
						+ "sizeId,"					
						+ "accessoriesItemId,"
						+ "cartonSize,"
						+ "OrderQty,"
						+ "Length1,"
						+ "Width1,"
						+ "Height1,"
						+ "Add1,"		
						+ "Add2,"
						+ "DivideBy,"
						+ "Ply,"
						+ "type,"
						+ "cbm,"
						+ "Qty,"
						+ "unitId,"
						+ "IndentDate,"
						+ "IndentTime,"
						+ "IndentPostBy) values ("
						+ "'"+cartonIndentId+"',"
						+ "'"+indent.get("buyerId")+"',"
						+ "'"+indent.get("styleId")+"',"
						+ "'"+indent.get("purchaseOrder")+"',"
						+ "'"+indent.get("itemId")+"',"
						+ "'"+indent.get("colorId")+"',"
						+ "'"+indent.get("shippingMark")+"',"
						+ "'"+indent.get("sizeId")+"',"
						+ "'"+indent.get("accessoriesItemId")+"',"
						+ "'"+indent.get("cartonSize")+"',"
						+ "'"+indent.get("orderQty")+"',"
						+ "'"+indent.get("length")+"',"
						+ "'"+indent.get("width")+"',"
						+ "'"+indent.get("height")+"',"
						+ "'"+indent.get("add1")+"',"
						+ "'"+indent.get("add2")+"',"
						+ "'"+indent.get("divideBy")+"',"
						+ "'"+indent.get("ply")+"',"
						+ "'"+indent.get("type")+"',"
						+ "'"+indent.get("cbm")+"',"
						+ "'"+indent.get("totalQty")+"',"
						+ "'"+indent.get("unitId")+"',"
						+ "CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'"+indent.get("userId")+"'"
						+ ")";


				session.createSQLQuery(sql).executeUpdate();
			}
			tx.commit();
			return cartonIndentId;
		}
		catch(Exception ee){

			if (tx != null) {
				tx.rollback();
				return "something wrong";
			}
			ee.printStackTrace();
		}

		finally {
			session.close();
		}

		return "something wrong";
	}

	@Override
	public boolean saveAccessoriesCurton(AccessoriesIndentCarton v) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		boolean inserted=false;
		List<CommonModel> query=new ArrayList<CommonModel>();

		try{
			tx=session.getTransaction();
			tx.begin();


			String sql="insert into tbAccessoriesIndentForCarton ("
					+ "styleid,"
					+ "PurchaseOrder,"
					+ "Itemid,"
					+ "ColorId,"
					+ "ShippingMarks,"
					+ "accessoriesItemId,"
					+ "cartonSize,"
					+ "OrderQty,"
					+ "Length1,"
					+ "Width1,"
					+ "Height1,"
					+ "Add1,"
					+ "Length2,"
					+ "Width2,"
					+ "Height2,"
					+ "Add2,"
					+ "DivideBy,"
					+ "Ply,"
					+ "Qty,"
					+ "rate,"
					+ "UnitPrice,"
					+ "PurchaseQty,"
					+ "amount,"
					+ "UnitId,"
					+ "supplierid,"
					+ "dolar,"
					+ "currency,"
					+ "pono,"
					+ "poapproval,"
					+ "poManual,"
					+ "IndentDate,"
					+ "IndentTime,"
					+ "IndentPostBy) values ("
					+ "'"+v.getStyle()+"',"
					+ "'"+v.getPoNo()+"',"
					+ "'"+v.getItem()+"',"
					+ "'"+v.getItemColor()+"',"
					+ "'"+v.getShippingMark()+"',"
					+ "'"+v.getAccessoriesItem()+"',"
					+ "'"+v.getAccessoriesSize()+"',"
					+ "'"+v.getOrderqty()+"',"
					+ "'"+v.getLength1()+"',"
					+ "'"+v.getWidth1()+"',"
					+ "'"+v.getHeight1()+"',"
					+ "'"+v.getAdd1()+"',"
					+ "'"+v.getLength2()+"',"
					+ "'"+v.getWidth2()+"',"
					+ "'"+v.getHeight2()+"',"
					+ "'"+v.getAdd2()+"',"
					+ "'"+v.getDevideBy()+"',"
					+ "'"+v.getPly()+"',"
					+ "'"+v.getTotalQty()+"',"
					+ "'0',"
					+ "'0',"
					+ "'0',"
					+ "'0',"
					+ "'"+v.getUnit()+"',"
					+ "'',"
					+ "'0',"
					+ "'',"
					+ "'0',"
					+ "'0',"
					+ "'',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'"+v.getUser()+"'"
					+ ")";

			System.out.println(sql);

			session.createSQLQuery(sql).executeUpdate();
			inserted=true;

			tx.commit();

			return inserted;
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

		return inserted;
	}

	@Override
	public List<AccessoriesIndentCarton> getAccessoriesIndentCarton(String poNo, String style, String item, String itemColor) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<AccessoriesIndentCarton> query=new ArrayList<AccessoriesIndentCarton>();

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select a.AccIndentId,a.PurchaseOrder,(select StyleNo from TbStyleCreate where StyleId=a.StyleId) as StyleNo,(select ItemName from tbItemDescription where itemid=a.ItemId) as ItemName,(select ColorName from tbColors where ColorId=a.ColorId) as ColorName,a.ShippingMarks,(select itemname from TbAccessoriesItem where itemid=a.accessoriesItemId) as AccessoriesName, cartonSize,a.Qty from tbAccessoriesIndentForCarton a where a.PurchaseOrder='"+poNo+"' and a.StyleId='"+style+"' and a.ItemId='"+item+"' and a.ColorId='"+itemColor+"'";
			System.out.println(" max ");

			List<?> list = session.createSQLQuery(sql).list();


			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				query.add(new AccessoriesIndentCarton(element[0].toString(),element[1].toString(),element[2].toString(),element[3].toString(),element[4].toString(),element[5].toString(),element[6].toString(),element[7].toString(),element[8].toString()));

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
	public List<AccessoriesIndentCarton> getAllAccessoriesCartonData() {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<AccessoriesIndentCarton> query=new ArrayList<AccessoriesIndentCarton>();

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select indentId,(SELECT CONVERT(varchar, indentDate, 25)) as indentDate from  tbAccessoriesIndentForCarton group by indentId,IndentDate";
			List<?> list = session.createSQLQuery(sql).list();
			AccessoriesIndentCarton tempAcc = null;

			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				tempAcc = new AccessoriesIndentCarton();
				tempAcc.setIndentId(element[0].toString());
				tempAcc.setIndentDate(element[1].toString());
				query.add(tempAcc);

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
	public List<AccessoriesIndentCarton> getAccessoriesIndentCartonItemDetails(String id) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<AccessoriesIndentCarton> query=new ArrayList<AccessoriesIndentCarton>();
		AccessoriesIndentCarton tempIndent = null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="select a.indentId,a.buyerId,a.PurchaseOrder,a.StyleId,sc.StyleNo,a.ItemId,id.itemname,a.ColorId,c.Colorname,a.ShippingMarks,a.sizeId,isnull(ss.sizeName,'')as sizeName,a.accessoriesItemId,ai.itemname as accessoriesName,a.cartonSize,a.Ply,a.type,a.OrderQty,a.Length1,a.Width1,a.Height1,a.Add1,a.Add2,a.UnitId,u.unitname,a.DivideBy,a.cbm,a.Qty,a.IndentPostBy,a.autoId \n" + 
					"from tbAccessoriesIndentForCarton a \n" + 
					"left join TbStyleCreate sc\n" + 
					"on a.styleid = sc.StyleId\n" + 
					"left join tbItemDescription id\n" + 
					"on a.Itemid = id.itemid\n" + 
					"left join tbColors c\n" + 
					"on a.ColorId = c.ColorId\n" + 
					"left join tbStyleSize ss\n" + 
					"on a.sizeId = ss.id\n" + 
					"left join TbAccessoriesItem ai\n" + 
					"on a.accessoriesItemId = ai.itemid\n" + 
					"left join tbunits u\n" + 
					"on a.UnitId = u.Unitid\n" + 
					"where a.indentId='"+id+"'";
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				tempIndent = new AccessoriesIndentCarton();
				Object[] element = (Object[]) iter.next();
				System.out.println(element[3].toString());
				tempIndent.setAutoId(element[29].toString());
				tempIndent.setIndentId(element[0].toString());
				tempIndent.setBuyerId(element[1].toString());
				tempIndent.setPoNo(element[2].toString());
				tempIndent.setStyleId(element[3].toString());
				tempIndent.setStyle(element[4].toString());
				tempIndent.setItemId(element[5].toString());
				tempIndent.setItem(element[6].toString());
				tempIndent.setItemColorId(element[7].toString());
				tempIndent.setItemColor(element[8].toString());
				tempIndent.setShippingMark(element[9].toString());
				tempIndent.setAccessoriesSizeId(element[10].toString());
				tempIndent.setAccessoriesSize(element[11].toString());
				tempIndent.setAccessoriesItemId(element[12].toString());
				tempIndent.setAccessoriesItem(element[13].toString());
				tempIndent.setCatronSize(element[14].toString());
				tempIndent.setPly(element[15].toString());
				tempIndent.setType(element[16].toString());
				tempIndent.setOrderqty(element[17].toString());
				tempIndent.setLength1(element[18].toString());
				tempIndent.setWidth1(element[19].toString());
				tempIndent.setHeight1(element[20].toString());
				tempIndent.setAdd1(element[21].toString());
				tempIndent.setAdd2(element[22].toString());
				tempIndent.setUnitId(element[23].toString());
				tempIndent.setUnit(element[24].toString());
				tempIndent.setDevideBy(element[25].toString());
				tempIndent.setCbm(element[26].toString());
				tempIndent.setTotalQty(element[27].toString());
				tempIndent.setUser(element[28].toString());
				query.add(tempIndent);
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
	public boolean editAccessoriesCarton(AccessoriesIndentCarton cartonIndent) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		boolean inserted=false;


		try{
			tx=session.getTransaction();
			tx.begin();


			String sql="update tbAccessoriesIndentForCarton set buyerId='"+cartonIndent.getBuyerId()+"',"
					+ "PurchaseOrder='"+cartonIndent.getPoNo()+"',styleid='"+cartonIndent.getStyleId()+"',"
					+ "Itemid='"+cartonIndent.getItemId()+"',ColorId='"+cartonIndent.getItemColorId()+"',"
					+ "ShippingMarks='"+cartonIndent.getShippingMark()+"',sizeId='"+cartonIndent.getAccessoriesSizeId()+"',"
					+ "accessoriesItemId='"+cartonIndent.getAccessoriesItemId()+"',cartonSize='"+cartonIndent.getCatronSize()+"',"
					+ "orderQty='"+cartonIndent.getOrderqty()+"',Ply='"+cartonIndent.getPly()+"',type='"+cartonIndent.getType()+"',"
					+ "Length1='"+cartonIndent.getLength1()+"',Width1='"+cartonIndent.getWidth1()+"',Height1='"+cartonIndent.getHeight1()+"',"
					+ "Add1='"+cartonIndent.getAdd1()+"',Add2='"+cartonIndent.getAdd2()+"',DivideBy='"+cartonIndent.getDevideBy()+"',"
					+ "cbm='"+cartonIndent.getCbm()+"',Qty='"+cartonIndent.getTotalQty()+"',indentPostBy='"+cartonIndent.getUser()+"' "
					+ "where autoId = '"+cartonIndent.getAutoId()+"'";

			System.out.println(sql);

			session.createSQLQuery(sql).executeUpdate();
			inserted=true;

			tx.commit();

			return inserted;
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

		return inserted;
	}

	@Override
	public boolean deleteAccessoriesCarton(String autoId,String indentId) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<CommonModel> query=new ArrayList<CommonModel>();

		try{
			tx=session.getTransaction();
			tx.begin();


			String sql="delete from tbAccessoriesIndentForCarton where autoId='"+autoId+"' and indentId='"+indentId+"'";

			session.createSQLQuery(sql).executeUpdate();


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
	public boolean InstallDataAsSameParticular(String userId,String purchaseOrder, String styleId, String itemId, String colorId,
			String installAccessories, String forAccessories) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		boolean inserted=false;


		try{
			tx=session.getTransaction();
			tx.begin();


			String sql="insert into tbAccessoriesIndent (styleid,PurchaseOrder,ItemId,ColorId,ShippingMarks,accessoriesItemId,accessoriesSize,SizeSorting,size,PerUnit,TotalBox,OrderQty,QtyInDozen,ReqPerPices,ReqPerDoz,DividedBy,PercentageExtra,PercentageExtraQty,TotalQty,UnitId,RequireUnitQty,IndentColorId,IndentBrandId) select styleid,PurchaseOrder,ItemId,ColorId,ShippingMarks,'"+forAccessories+"',accessoriesSize,SizeSorting,size,PerUnit,TotalBox,OrderQty,QtyInDozen,ReqPerPices,ReqPerDoz,DividedBy,PercentageExtra,PercentageExtraQty,TotalQty,UnitId,RequireUnitQty,IndentColorId,IndentBrandId from tbAccessoriesIndent where styleid='"+styleId+"' and PurchaseOrder='"+purchaseOrder+"' and Itemid='"+itemId+"' and ColorId='"+colorId+"' and accessoriesItemId='"+installAccessories+"'";

			System.out.println(sql);
			session.createSQLQuery(sql).executeUpdate();

			String updateSql="update tbAccessoriesIndent set IndentDate=CURRENT_TIMESTAMP,IndentTime=CURRENT_TIMESTAMP,IndentPostBy='"+userId+"' where styleid='"+styleId+"' and PurchaseOrder='"+purchaseOrder+"' and Itemid='"+itemId+"' and ColorId='"+colorId+"' and accessoriesItemId='"+forAccessories+"'";
			System.out.println(updateSql);
			session.createSQLQuery(updateSql).executeUpdate();

			inserted=true;

			tx.commit();

			return inserted;
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
		return inserted;
	}

	@Override
	public List<String> getPurchaseOrderList(String userId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<String> dataList=new ArrayList<String>();
		try{
			tx=session.getTransaction();
			tx.begin();



			String sql="select PurchaseOrder from TbBuyerOrderEstimateDetails where PurchaseOrder != '' and userId='"+userId+"' group by PurchaseOrder order by PurchaseOrder";
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{		
				dataList.add(iter.next().toString());
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
	public List<Color> getStyleItemWiseColor(String styleId, String itemId) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<Color> dataList=new ArrayList<Color>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select a.colorId,(select ColorName from tbColors where ColorId=a.ColorId) as ColorName \r\n" + 
					"from TbBuyerOrderEstimateDetails a \r\n" + 
					"where a.StyleId='"+styleId+"' and a.ItemId='"+itemId+"'  group by a.ColorId";
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{		
				Object[] element = (Object[]) iter.next();
				dataList.add(new Color(element[0].toString(), element[1].toString(), "", ""));
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
	public List<Style> getPOWiseStyleList(String purchaseOrder) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<Style> dataList=new ArrayList<Style>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql=" select a.StyleId,sc.StyleNo \r\n" + 
					" from TbBuyerOrderEstimateDetails a \r\n" + 
					" left join TbStyleCreate sc\r\n" + 
					" on a.StyleId = sc.StyleId\r\n" + 
					" where a.PurchaseOrder='"+purchaseOrder+"' group by a.StyleId,sc.StyleNo";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();

				dataList.add(new Style(element[0].toString(),element[1].toString()));
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
	public String confirmFabricsIndent(String fabricsIndentId,String fabricsItems) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();

			if(fabricsIndentId.equals("New")) {
				String sql="select (isnull(max(indentId),0)+1) as maxId from tbFabricsIndent";
				List<?> list = session.createSQLQuery(sql).list();
				if(list.size()>0) {		
					fabricsIndentId = list.get(0).toString();
				}
			}

			JSONParser jsonParser = new JSONParser();
			System.out.println(fabricsItems);
			JSONObject indentObject = (JSONObject)jsonParser.parse(fabricsItems);
			JSONArray indentList = (JSONArray) indentObject.get("list");

			for(int i=0;i<indentList.size();i++) {
				JSONObject indent = (JSONObject) indentList.get(i);
				String sql="insert into tbFabricsIndent  "
						+ "(indentId,BuyerOrderId,PurchaseOrder,"
						+ "styleId,"
						+ "itemid,"
						+ "itemcolor,"
						+ "fabricsid,"
						+ "fabricscolor,"
						+ "brand,"
						+ "width,"
						+ "Yard,"
						+ "GSM,"
						+ "markingWidth,"
						+ "qty,"
						+ "dozenqty,"
						+ "consumption,"
						+ "inPercent,"
						+ "PercentQty,"
						+ "TotalQty,"
						+ "unitId,"
						+ "RequireUnitQty,"
						+ "mdapproval,"
						+ "indentDate,"
						+ "entrytime,"
						+ "entryby) values ("
						+ "'"+fabricsIndentId+"',"
						+ "'"+indent.get("buyerOrderId")+"',"
						+ "'"+indent.get("purchaseOrder")+"',"
						+ "'"+indent.get("styleId")+"',"
						+ "'"+indent.get("itemId")+"',"
						+ "'"+indent.get("itemColorId")+"',"
						+ "'"+indent.get("fabricsId")+"',"
						+ "'"+indent.get("fabricsColorId")+"',"
						+ "'"+indent.get("brandId")+"',"
						+ "'"+indent.get("width")+"',"
						+ "'"+indent.get("yard")+"',"
						+ "'"+indent.get("gsm")+"',"
						+ "'"+indent.get("markingWidth")+"',"
						+ "'"+indent.get("orderQty")+"',"
						+ "'"+indent.get("dozenQty")+"',"
						+ "'"+indent.get("consumption")+"',"
						+ "'"+indent.get("inPercent")+"',"
						+ "'"+indent.get("percentQty")+"',"
						+ "'"+indent.get("totalQty")+"',"
						+ "'"+indent.get("unitId")+"',"
						+ "'"+indent.get("grandQty")+"','0',"
						+ "GETDATE(),"
						+ "CURRENT_TIMESTAMP,"
						+ "'"+indent.get("userId")+"'"
						+ ") ";
				session.createSQLQuery(sql).executeUpdate();
			}
			tx.commit();
			return fabricsIndentId;
		}
		catch(Exception ee){

			if (tx != null) {
				tx.rollback();
				return "something wrong";
			}
			ee.printStackTrace();
		}

		finally {
			session.close();
		}

		return "something wrong";
	}

	@Override
	public boolean editFabricsIndent(FabricsIndent fabricsIndent) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="update tbFabricsIndent set "
					+ "PurchaseOrder='"+fabricsIndent.getPurchaseOrder()+"',"
					+ "styleId='"+fabricsIndent.getStyleId()+"',"
					+ "itemid='"+fabricsIndent.getItemId()+"',"
					+ "itemcolor='"+fabricsIndent.getItemColorId()+"',"
					+ "fabricsid='"+fabricsIndent.getFabricsId()+"',"
					+ "fabricscolor='"+fabricsIndent.getFabricsColorId()+"',"
					+ "brand='"+fabricsIndent.getBrandId()+"',"
					+ "width='"+fabricsIndent.getWidth()+"',"
					+ "Yard='"+fabricsIndent.getYard()+"',"
					+ "GSM='"+fabricsIndent.getGsm()+"',"
					+ "markingWidth='"+fabricsIndent.getMarkingWidth()+"',"
					+ "qty='"+fabricsIndent.getQty()+"',"
					+ "dozenqty='"+fabricsIndent.getDozenQty()+"',"
					+ "consumption='"+fabricsIndent.getConsumption()+"',"
					+ "inPercent='"+fabricsIndent.getInPercent()+"',"
					+ "PercentQty='"+fabricsIndent.getPercentQty()+"',"
					+ "TotalQty='"+fabricsIndent.getTotalQty()+"',"
					+ "unitId='"+fabricsIndent.getUnitId()+"',"
					+ "RequireUnitQty='"+fabricsIndent.getGrandQty()+"',"
					+ "entrytime=CURRENT_TIMESTAMP,"
					+ "entryby='"+fabricsIndent.getUserId()+"' where id = '"+fabricsIndent.getAutoId()+"' ";
			session.createSQLQuery(sql).executeUpdate();
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
	public boolean isFabricsIndentExist(FabricsIndent fabricsIndent) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		boolean exist = false;
		try{	
			tx=session.getTransaction();
			tx.begin();	
			String sql="select id,PurchaseOrder,styleId,itemid,itemcolor,fabricsid,qty,dozenqty,consumption from tbFabricsIndent rf where PurchaseOrder='"+fabricsIndent.getPurchaseOrder()+"' and styleId='"+fabricsIndent.getStyleId()+"' and itemid='"+fabricsIndent.getItemId()+"' and itemcolor = '"+fabricsIndent.getItemColorId()+"' and fabricsid='"+fabricsIndent.getFabricsId()+"' and id != '"+fabricsIndent.getAutoId()+"'";

			List<?> list = session.createSQLQuery(sql).list();
			if(list.size()>0) {
				exist = true;
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
		return exist;
	}

	@Override
	public boolean deleteFabricsIndent(String autoId,String indentId) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<CommonModel> query=new ArrayList<CommonModel>();

		try{
			tx=session.getTransaction();
			tx.begin();


			String sql="delete from tbFabricsIndent where id='"+autoId+"' and indentId='"+indentId+"'";

			session.createSQLQuery(sql).executeUpdate();


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
	public List<FabricsIndent> getFabricsIndentList() {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		FabricsIndent tempFabrics = null;
		List<FabricsIndent> datalist=new ArrayList<FabricsIndent>();	
		try{	
			tx=session.getTransaction();
			tx.begin();	
			String sql="select rf.id,rf.PurchaseOrder,rf.styleId,sc.StyleNo,rf.itemId,id.itemname,rf.itemcolor,c.Colorname,rf.fabricsid,fi.ItemName,rf.qty,rf.dozenqty,rf.consumption,rf.inPercent,rf.PercentQty,TotalQty,rf.unitId,u.unitname,rf.markingWidth  \r\n" + 
					"from tbFabricsIndent rf\r\n" + 
					"left join TbStyleCreate sc\r\n" + 
					"on rf.StyleId = sc.StyleId\r\n" + 
					"left join tbItemDescription id\r\n" + 
					"on rf.itemid = id.itemid\r\n" + 
					"left join tbColors c\r\n" + 
					"on rf.itemcolor = c.ColorId\r\n" + 
					"left join TbFabricsItem fi\r\n" + 
					"on rf.fabricsid = fi.id\r\n" + 
					"left join tbunits u\r\n" + 
					"on rf.unitId = u.Unitid\r\n" + 
					"order by rf.id desc";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				tempFabrics = new FabricsIndent(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString(), element[7].toString(), element[8].toString(), element[9].toString(), Double.valueOf(element[10].toString()),  Double.valueOf(element[11].toString()),  Double.valueOf(element[12].toString()),  Double.valueOf(element[13].toString()),  Double.valueOf(element[14].toString()),  Double.valueOf(element[15].toString()), element[16].toString(), element[17].toString());
				tempFabrics.setMarkingWidth(element[18].toString());
				datalist.add(tempFabrics);		
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
	public List<FabricsIndent> getStyleDetailsForFabricsIndent(String userId) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		FabricsIndent tempFabrics = null;
		List<FabricsIndent> dataList=new ArrayList<FabricsIndent>();
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="select a.indentId,(SELECT CONVERT(varchar, a.indentDate, 25)) as indentDate  \r\n" + 
					"from tbFabricsIndent a \r\n" + 
					"where a.entryby='"+userId+"' group by a.indentId,a.indentDate  order by a.indentDate desc";
			session.createSQLQuery(sql).list();

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				tempFabrics = new FabricsIndent();
				Object[] element = (Object[]) iter.next();							
				tempFabrics.setIndentId(element[0].toString());
				tempFabrics.setIndentDate(element[1].toString());
				dataList.add(tempFabrics);
			}
			tx.commit();
			return dataList;
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

		return dataList;
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
			String sql="select id,PurchaseOrder,styleId,itemid,itemcolor,fabricsid,(select ItemName from TbFabricsItem where id=rf.fabricsid) as FabricsName,qty,dozenqty,consumption,inPercent,PercentQty,TotalQty,unitId,width,Yard,GSM,RequireUnitQty,fabricscolor,brand,entryby,markingWidth from tbFabricsIndent rf where id = '"+autoId+"'";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				indent = new FabricsIndent(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString(),  Double.valueOf(element[7].toString()),  Double.valueOf(element[8].toString()),  Double.valueOf(element[9].toString()),  Double.valueOf(element[10].toString()),  Double.valueOf(element[11].toString()), Double.valueOf(element[12].toString()),  element[13].toString(),  Double.valueOf(element[14].toString()),  Double.valueOf(element[15].toString()),  Double.valueOf(element[16].toString()), Double.valueOf(element[17].toString()), element[18].toString()  ,element[19].toString(),element[20].toString());
				indent.setMarkingWidth(element[21].toString());
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
	public List<FabricsIndent> getFabricsIndent(String indentId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<FabricsIndent> indentList = new ArrayList<FabricsIndent>();
		FabricsIndent indent= null;
		try{	
			tx=session.getTransaction();
			tx.begin();	
			String sql="select fi.id,fi.indentId,fi.PurchaseOrder,fi.styleId,isnull(sc.StyleNo,'') as styleNo,fi.itemid,isnull(id.itemname,'')as itemName,fi.itemcolor,isnull(itemColor.Colorname,'') as itemColorName,fabricsid,\r\n" + 
					"fabricsI.ItemName as fabricsName,fi.fabricscolor,fabricsColor.Colorname as fabricsColorName,fi.qty,fi.dozenqty,fi.consumption,fi.inPercent,fi.PercentQty,fi.TotalQty,fi.unitId,u.unitname,fi.width,fi.Yard,fi.GSM,fi.RequireUnitQty,fi.brand,fi.entryby,fi.markingWidth \r\n" + 
					"from tbFabricsIndent fi\r\n" + 
					"left join TbStyleCreate sc\r\n" + 
					"on fi.styleId = cast(sc.StyleId as varchar)\r\n" + 
					"left join tbItemDescription id\r\n" + 
					"on fi.itemid = cast(id.itemid as varchar)\r\n" + 
					"left join tbColors itemColor\r\n" + 
					"on fi.itemcolor = cast(itemColor.ColorId as varchar)\r\n" + 
					"left join TbFabricsItem fabricsI\r\n" + 
					"on fi.fabricsid = fabricsI.id\r\n" + 
					"left join tbColors fabricsColor\r\n "+
					"on fi.fabricscolor = fabricsColor.ColorId "
					+ "left join tbunits u \r\n" + 
					"on fi.unitId = u.Unitid where indentId = '"+indentId+"'";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				indent = new FabricsIndent();
				indent.setAutoId(element[0].toString());
				indent.setIndentId(element[1].toString());
				indent.setPurchaseOrder(element[2].toString());
				indent.setStyleId(element[3].toString());
				indent.setStyleName(element[4].toString());
				indent.setItemId(element[5].toString());
				indent.setItemName(element[6].toString());
				indent.setItemColorId(element[7].toString());
				indent.setItemColorName(element[8].toString());
				indent.setFabricsId(element[9].toString());
				indent.setFabricsName(element[10].toString());
				indent.setFabricsColorId(element[11].toString());
				indent.setFabricsColor(element[12].toString());
				indent.setQty(Double.valueOf(element[13].toString()));
				indent.setDozenQty(Double.valueOf(element[14].toString()));
				indent.setConsumption(Double.valueOf(element[15].toString()));
				indent.setInPercent(Double.valueOf(element[16].toString()));
				indent.setPercentQty(Double.valueOf(element[17].toString()));
				indent.setTotalQty(Double.valueOf(element[18].toString()));
				indent.setUnitId(element[19].toString());
				indent.setUnit(element[20].toString());
				indent.setWidth(Double.valueOf(element[21].toString()));
				indent.setYard(Double.valueOf(element[22].toString()));
				indent.setGsm(Double.valueOf(element[23].toString()));
				indent.setGrandQty(Double.valueOf(element[24].toString()));
				indent.setBrandId(element[25].toString());
				indent.setUserId(element[26].toString());
				indent.setMarkingWidth(element[27].toString());
				indentList.add(indent);
			}			
			tx.commit();			
		}	
		catch(Exception e){
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
		}
		finally {
			session.close();
		}
		return indentList;
	}

	@Override
	public double getOrderQuantity(String purchaseOrder, String styleId, String itemId, String colorId) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		double qty=0;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="SELECT sum(ISNULL(TotalUnit,0)) as TotalUnit FROM TbBuyerOrderEstimateDetails where PurchaseOrder='"+purchaseOrder+"' and styleid='"+styleId+"' and itemid='"+itemId+"' and colorid='"+colorId+"' group by ColorId";

			List<?> list = session.createSQLQuery(sql).list();
			if(list.iterator().hasNext())
			{	
				qty = Double.valueOf(list.iterator().next().toString());
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
		return qty;
	}

	@Override
	public double getOrderQuantityByMultipleId(String purchaseOrder, String styleId, String itemId, String colorId) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		double qty=0;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="SELECT sum(ISNULL(TotalUnit,0)) as TotalUnit FROM TbBuyerOrderEstimateDetails where PurchaseOrder in ("+purchaseOrder+") and styleid in ("+styleId+") and itemid in ("+itemId+") and colorid in ("+colorId+")";

			List<?> list = session.createSQLQuery(sql).list();
			if(list.iterator().hasNext())
			{	
				qty = Double.valueOf(list.iterator().next().toString());
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
		return qty;
	}

	@Override
	public List<CommonModel> BuyerWisePo(String buyerId) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<CommonModel> dataList=new ArrayList<CommonModel>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select BuyerOrderId,PurchaseOrder from TbBuyerOrderEstimateDetails where buyerId='"+buyerId+"' group by BuyerOrderId,PurchaseOrder";
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{		
				Object[] element = (Object[]) iter.next();
				dataList.add(new CommonModel(element[0].toString(), element[1].toString()));
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
	public List<CommonModel> getSampleList() {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<CommonModel> dataList=new ArrayList<CommonModel>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select AutoId,Name from TbSampleTypeInfo order by Name";
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{		
				Object[] element = (Object[]) iter.next();
				dataList.add(new CommonModel(element[0].toString(), element[1].toString()));
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
	public List<CommonModel> getInchargeList() {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<CommonModel> dataList=new ArrayList<CommonModel>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select InchargeId,InchargeName from TbInchargeInfo order by InchargeName";
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{		
				Object[] element = (Object[]) iter.next();
				dataList.add(new CommonModel(element[0].toString(), element[1].toString()));
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
	public List<CommonModel> getMerchendizerList() {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<CommonModel> dataList=new ArrayList<CommonModel>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select MerchendiserId,MerchendiserName from TbMerchendiserInfo order by MerchendiserName";
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{		
				Object[] element = (Object[]) iter.next();
				dataList.add(new CommonModel(element[0].toString(), element[1].toString()));
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
	public boolean addItemToSampleRequisition(SampleRequisitionItem v) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="insert into TbSampleRequisitionDetails (BuyerId,buyerOrderId,purchaseOrder,StyleId,ItemId,ColorId,SampleTypeId,sizeGroupId,Date,EntryTime,UserId) "
					+ "values('"+v.getBuyerId()+"','"+v.getBuyerOrderId()+"','"+v.getPurchaseOrder()+"','"+v.getStyleId()+"','"+v.getItemId()+"','"+v.getColorId()+"','"+v.getSampleId()+"','"+v.getSizeGroupId()+"',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'"+v.getUserId()+"');";
			session.createSQLQuery(sql).executeUpdate();

			String itemAutoId ="";
			sql="select max(sampleAutoId) as itemAutoId from TbSampleRequisitionDetails where sampleReqId IS NULL and userId='"+v.getUserId()+"'";
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				itemAutoId =  iter.next().toString();	
			}

			int listSize=v.getSizeList().size();
			for(int i=0;i<listSize;i++) {
				sql = "insert into tbSizeValues (linkedAutoId,sizeGroupId,sizeId,sizeQuantity,type,entryTime,userId) values('"+itemAutoId+"','"+v.getSizeGroupId()+"','"+v.getSizeList().get(i).getSizeId()+"','"+v.getSizeList().get(i).getSizeQuantity()+"','"+SizeValuesType.SAMPLE.getType()+"',CURRENT_TIMESTAMP,'"+v.getUserId()+"');";
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
	public List<SampleRequisitionItem> getSampleRequisitionItemList(String userId) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<SampleRequisitionItem> dataList=new ArrayList<SampleRequisitionItem>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select ISNULL(srd.sampleReqId,0) as sampleReqId,sr.InchargeId,sr.MerchendizerId,sr.Instruction,srd.SampleTypeId,srd.BuyerId,srd.sampleAutoId,srd.StyleId,sc.StyleNo,srd.ItemId,id.itemname,srd.ColorId,c.Colorname,srd.buyerOrderId,srd.PurchaseOrder,srd.sizeGroupId,srd.userId \r\n" + 
					"					from TbSampleRequisitionDetails srd\r\n" + 
					"					left join tbSampleRequisition sr\r\n" + 
					"					on sr.sampleReqId=srd.sampleReqId\r\n" + 
					"					left join TbStyleCreate sc\r\n" + 
					"					on srd.StyleId = sc.StyleId\r\n" + 
					"					left join tbItemDescription id\r\n" + 
					"					on srd.ItemId = id.itemid\r\n" + 
					"					left join tbColors c\r\n" + 
					"					on srd.ColorId = c.ColorId\r\n" + 
					"					where sr.sampleReqId IS NULL and sr.UserId='"+userId+"' order by sizeGroupId";
			System.out.println(sql);
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();							
				dataList.add(new SampleRequisitionItem(element[0].toString(),element[1].toString(),element[2].toString(), element[3].toString(),element[4].toString(), element[5].toString(), element[6].toString(), element[7].toString(), element[8].toString(), element[9].toString(),element[10].toString(),element[11].toString(),element[12].toString(),element[13].toString(),element[14].toString(),element[15].toString(),element[16].toString()));
			}

			for (SampleRequisitionItem sampleReqItem : dataList) {
				sql = "select bs.sizeGroupId,bs.sizeId,ss.sizeName,bs.sizeQuantity from tbSizeValues bs\r\n" + 
						"join tbStyleSize ss \r\n" + 
						"on ss.id = bs.sizeId \r\n" + 
						"where bs.linkedAutoId = '"+sampleReqItem.getAutoId()+"' and bs.type='"+SizeValuesType.SAMPLE.getType()+"' and bs.sizeGroupId = '"+sampleReqItem.getSizeGroupId()+"' \r\n" + 
						"order by ss.sortingNo";
				System.out.println(sql);
				List<?> list2 = session.createSQLQuery(sql).list();
				ArrayList<Size> sizeList=new ArrayList<Size>();
				for(Iterator<?> iter = list2.iterator(); iter.hasNext();)
				{	
					Object[] element = (Object[]) iter.next();	
					sizeList.add(new Size(element[0].toString(),element[1].toString(), element[2].toString()));
				}
				sampleReqItem.setSizeList(sizeList);
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
	public boolean confirmItemToSampleRequisition(SampleRequisitionItem v) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();


			String sampleReqId=getMaxSampleReqId();

			String sql="insert into tbSampleRequisition (sampleReqId,InchargeId,MerchendizerId,Instruction,dateLine,samplerequestdate,EntryTime,UserId) "
					+ "values('"+sampleReqId+"','"+v.getInchargeId()+"','"+v.getMarchendizerId()+"','"+v.getInstruction()+"','"+v.getSampleDeadline()+"',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'"+v.getUserId()+"');";
			session.createSQLQuery(sql).executeUpdate();

			//String sqlupdate="update TbSampleRequisitionDetails set sampleReqId='"+sampleReqId+"',SampleTypeId='"+v.getSampleId()+"' where purchaseOrder='"+v.getPurchaseOrder()+"' and StyleId='"+v.getStyleId()+"' and ItemId='"+v.getItemId()+"' and ColorId='"+v.getColorId()+"' ";
			String sqlupdate="update TbSampleRequisitionDetails set sampleReqId='"+sampleReqId+"',SampleTypeId='"+v.getSampleId()+"' where buyerOrderId='"+v.getBuyerOrderId()+"' and purchaseOrder='"+v.getPurchaseOrder()+"' and StyleId='"+v.getStyleId()+"' and sampleReqId IS NULL";

			session.createSQLQuery(sqlupdate).executeUpdate();

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

	private String getMaxSampleReqId() {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		String query="";

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select isnull(max(sampleReqId),0)+1 from tbSampleRequisition";

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
	public List<SampleRequisitionItem> getSampleRequisitionList(String userId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<SampleRequisitionItem> dataList=new ArrayList<SampleRequisitionItem>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select ISNULL(a.sampleReqId,0) as sampleReqId,ISNULL((select name from tbBuyer where id=a.buyerId),'') as BuyerName,a.purchaseOrder,(select StyleNo from TbStyleCreate where StyleId=a.StyleId) as StyleNO,a.StyleId,(SELECT CONVERT(varchar, a.Date, 101)) as Date from TbSampleRequisitionDetails a where a.UserId='"+userId+"' group by a.sampleReqId,a.BuyerId,a.purchaseOrder,a.StyleId,a.Date";
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{		
				Object[] element = (Object[]) iter.next();
				dataList.add(new SampleRequisitionItem(element[0].toString(), element[1].toString(),element[2].toString(),element[3].toString(),element[4].toString(),element[5].toString()));
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
	public List<SampleRequisitionItem> getSampleRequisitionDetails(String sampleReqId) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<SampleRequisitionItem> dataList=new ArrayList<SampleRequisitionItem>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select sr.InchargeId,sr.MerchendizerId,sr.Instruction,srd.SampleTypeId,srd.BuyerId,srd.sampleAutoId,srd.StyleId,sc.StyleNo,srd.ItemId,id.itemname,srd.ColorId,c.Colorname,srd.buyerOrderId,srd.PurchaseOrder,srd.sizeGroupId,srd.userId \r\n" + 
					"					from TbSampleRequisitionDetails srd\r\n" + 
					"					left join tbSampleRequisition sr\r\n" + 
					"					on sr.sampleReqId=srd.sampleReqId\r\n" + 
					"					left join TbStyleCreate sc\r\n" + 
					"					on srd.StyleId = sc.StyleId\r\n" + 
					"					left join tbItemDescription id\r\n" + 
					"					on srd.ItemId = id.itemid\r\n" + 
					"					left join tbColors c\r\n" + 
					"					on srd.ColorId = c.ColorId\r\n" + 
					"					where srd.sampleReqId='"+sampleReqId+"' order by sizeGroupId";
			System.out.println(sql);
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();							
				dataList.add(new SampleRequisitionItem(sampleReqId,element[0].toString(),element[1].toString(),element[2].toString(), element[3].toString(),element[4].toString(), element[5].toString(), element[6].toString(), element[7].toString(), element[8].toString(), element[9].toString(),element[10].toString(),element[11].toString(),element[12].toString(),element[13].toString(),element[14].toString(),element[15].toString()));
			}

			for (SampleRequisitionItem sampleReqItem : dataList) {
				sql = "select bs.sizeGroupId,bs.sizeId,ss.sizeName,bs.sizeQuantity from tbSizeValues bs\r\n" + 
						"join tbStyleSize ss \r\n" + 
						"on ss.id = bs.sizeId \r\n" + 
						"where bs.linkedAutoId = '"+sampleReqItem.getAutoId()+"' and bs.type='"+SizeValuesType.SAMPLE.getType()+"' and bs.sizeGroupId = '"+sampleReqItem.getSizeGroupId()+"' \r\n" + 
						"order by ss.sortingNo";
				System.out.println(sql);
				List<?> list2 = session.createSQLQuery(sql).list();
				ArrayList<Size> sizeList=new ArrayList<Size>();
				for(Iterator<?> iter = list2.iterator(); iter.hasNext();)
				{	
					Object[] element = (Object[]) iter.next();	
					sizeList.add(new Size(element[0].toString(),element[1].toString(), element[3].toString()));
				}
				sampleReqItem.setSizeList(sizeList);
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
	public List<pg.registerModel.AccessoriesItem> getTypeWiseIndentItems(String purchaseOrder, String styleId,
			String type) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<AccessoriesItem> dataList=new ArrayList<AccessoriesItem>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql = "";
			if(type.equals("1")) {
				sql = "select fi.id,fi.ItemName,fi.unitId \r\n" + 
						"from tbFabricsIndent rf \r\n" + 
						"left join TbFabricsItem fi \r\n" + 
						"on rf.fabricsid = fi.id \r\n" + 
						"where fi.purchaseOrder='"+purchaseOrder+"' and styleid='"+styleId+"'  group by fi.id,fi.ItemName,fi.unitId";
			}else if(type.equals("2")) {
				sql = "select a.itemid,a.itemname,a.unitId \r\n" + 
						"from tbAccessoriesIndent ai \r\n" + 
						"left join TbAccessoriesItem a \r\n" + 
						"on ai.accessoriesItemId = a.itemid \r\n" + 
						"where ai.purchaseOrder='"+purchaseOrder+"' and styleid='"+styleId+"'  group by a.itemid,a.itemname,a.unitId";
			}else {
				sql = "select aic.accessoriesItemId,ai.itemname,aic.UnitId\r\n" + 
						"from tbAccessoriesIndentForCarton aic \r\n" + 
						"left join TbAccessoriesItem ai \r\n" + 
						"on aic.accessoriesItemId = ai.itemid\r\n" + 
						"where aic.PurchaseOrder = '"+purchaseOrder+"' and aic.styleid='"+styleId+"' ";
			}

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				dataList.add(new AccessoriesItem(element[0].toString(), element[1].toString(), "",element[2].toString(), ""));
			}
			tx.commit();
		}
		catch(Exception e){
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}

		}
		finally {
			session.close();
		}
		return dataList;
	}
	
	@Override
	public List<pg.registerModel.AccessoriesItem> getIndentItems(String indentId, String indentType) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<AccessoriesItem> dataList=new ArrayList<AccessoriesItem>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql = "";
			if(indentType.equals("Fabrics")) {
				sql = "select fi.id,fi.ItemName,fi.unitId \r\n" + 
						"from tbFabricsIndent rf \r\n" + 
						"left join TbFabricsItem fi \r\n" + 
						"on rf.fabricsid = fi.id \r\n" + 
						"where rf.indentId='"+indentId+"' and rf.pono is null group by fi.id,fi.ItemName,fi.unitId";
			}else if(indentType.equals("Accessories")) {
				sql = "select a.itemid,a.itemname,a.unitId \r\n" + 
						"from tbAccessoriesIndent ai \r\n" + 
						"left join TbAccessoriesItem a \r\n" + 
						"on ai.accessoriesItemId = a.itemid \r\n" + 
						"where ai.aiNo='"+indentId+"' and ai.pono is null  group by a.itemid,a.itemname,a.unitId";
			}else {
				sql = "select aic.accessoriesItemId,ai.itemname,aic.UnitId\r\n" + 
						"from tbAccessoriesIndentForCarton aic \r\n" + 
						"left join TbAccessoriesItem ai \r\n" + 
						"on aic.accessoriesItemId = ai.itemid\r\n" + 
						"where aic.indentId = '"+indentId+"' and aic.pono is null group by aic.accessoriesItemId,ai.itemname,aic.UnitId ";
			}

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				dataList.add(new AccessoriesItem(element[0].toString(), element[1].toString(), "",element[2].toString(), ""));
			}
			tx.commit();
		}
		catch(Exception e){
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}

		}
		finally {
			session.close();
		}
		return dataList;
	}


	@Override
	public boolean submitPurchaseOrder(PurchaseOrder purchaseOrder) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<CommonModel> query=new ArrayList<CommonModel>();

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select (isnull(max(poNo),0)+1) as maxId from tbPurchaseOrderSummary";
			List<?> list = session.createSQLQuery(sql).list();
			String poId="0";
			if(list.size()>0) {
				poId = list.get(0).toString();
			}

			sql="insert into tbPurchaseOrderSummary "
					+ " ("
					+ "poNo,"
					+ "type,"
					+ "orderDate,"
					+ "deliveryDate,"
					+ "supplierId,"
					+ "orderby,"
					+ "billto,"
					+ "deliveryto,"
					+ "paymentTerm,"
					+ "currency,"
					+ "Note,"
					+ "Subject,"
					+ "ManualPo,"
					+ "entryBy,"
					+ "entryTime) "
					+ " values "
					+ "("
					+ "'"+poId+"',"
					+ "'"+purchaseOrder.getType()+"',"
					+ "'"+purchaseOrder.getOrderDate()+"',"
					+ "'"+purchaseOrder.getDeliveryDate()+"',"
					+ "'"+purchaseOrder.getSupplierId()+"',"
					+ "'"+purchaseOrder.getOrderBy()+"',"
					+ "'"+purchaseOrder.getBillTo()+"',"
					+ "'"+purchaseOrder.getDeliveryTo()+"',"
					+ "'"+purchaseOrder.getPaymentType()+"',"
					+ "'"+purchaseOrder.getCurrency()+"',"
					+ "'"+purchaseOrder.getNote()+"',"
					+ "'"+purchaseOrder.getSubject()+"',"
					+ "'"+purchaseOrder.getManualPO()+"',"
					+ "'"+purchaseOrder.getUserId()+"',"
					+ "CURRENT_TIMESTAMP ) ";
			System.out.println(sql);
			session.createSQLQuery(sql).executeUpdate();

			int length = purchaseOrder.getItemList().size();
			for (PurchaseOrderItem item : purchaseOrder.getItemList()) {
				if(item.getType().equals("Fabrics")) {
					if(item.isCheck()) {
						sql="Update tbFabricsIndent set pono='"+poId+"',poapproval='1',supplierid='"+item.getSupplierId()+"',dolar='"+item.getDollar()+"',rate='"+item.getRate()+"',amount='"+item.getAmount()+"',currency='"+item.getCurrency()+"',poManual='"+purchaseOrder.getManualPO()+"' where id='"+item.getAutoId()+"'";		
					}else {
						sql="Update tbFabricsIndent set poapproval='0',supplierid='"+item.getSupplierId()+"',dolar='"+item.getDollar()+"',rate='"+item.getRate()+"',amount='"+item.getAmount()+"',currency='"+item.getCurrency()+"',poManual='"+purchaseOrder.getManualPO()+"' where id='"+item.getAutoId()+"'";		
					}
				}else if(item.getType().equals("Accessories")) {
					if(item.isCheck()) {
						sql="Update tbAccessoriesIndent set pono='"+poId+"',poapproval='1',supplierid='"+item.getSupplierId()+"',dolar='"+item.getDollar()+"',rate='"+item.getRate()+"',amount='"+item.getAmount()+"',currency='"+item.getCurrency()+"',poManual='"+purchaseOrder.getManualPO()+"' where AccIndentId='"+item.getAutoId()+"'";		
					}else {
						sql="Update tbAccessoriesIndent set poapproval='0',supplierid='"+item.getSupplierId()+"',dolar='"+item.getDollar()+"',rate='"+item.getRate()+"',amount='"+item.getAmount()+"',currency='"+item.getCurrency()+"',poManual='"+purchaseOrder.getManualPO()+"' where AccIndentId='"+item.getAutoId()+"'";		
					}
				}else if(item.getType().equals("Carton")) {
					if(item.isCheck()) {
						sql="Update tbAccessoriesIndentForCarton set pono='"+poId+"',poapproval='1',supplierid='"+item.getSupplierId()+"',dolar='"+item.getDollar()+"',rate='"+item.getRate()+"',amount='"+item.getAmount()+"',currency='"+item.getCurrency()+"',poManual='"+purchaseOrder.getManualPO()+"' where autoId='"+item.getAutoId()+"'";		
					}else {
						sql="Update tbAccessoriesIndentForCarton set poapproval='0',supplierid='"+item.getSupplierId()+"',dolar='"+item.getDollar()+"',rate='"+item.getRate()+"',amount='"+item.getAmount()+"',currency='"+item.getCurrency()+"',poManual='"+purchaseOrder.getManualPO()+"' where autoId='"+item.getAutoId()+"'";		
					}
				}
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
	public boolean editPurchaseOrder(PurchaseOrder purchaseOrder) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<CommonModel> query=new ArrayList<CommonModel>();

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="";

			sql="update tbPurchaseOrderSummary set "
					+ "orderDate = '"+purchaseOrder.getOrderDate()+"',"
					+ "deliveryDate = '"+purchaseOrder.getDeliveryDate()+"',"
					+ "supplierId = '"+purchaseOrder.getSupplierId()+"',"
					+ "orderby = '"+purchaseOrder.getOrderBy()+"',"
					+ "billto = '"+purchaseOrder.getBillTo()+"',"
					+ "deliveryto = '"+purchaseOrder.getDeliveryTo()+"',"
					+ "paymentTerm = '"+purchaseOrder.getPaymentType()+"',"
					+ "currency = '"+purchaseOrder.getCurrency()+"',"
					+ "Note = '"+purchaseOrder.getNote()+"',"
					+ "Subject = '"+purchaseOrder.getSubject()+"',"
					+ "ManualPo = '"+purchaseOrder.getManualPO()+"',"
					+ "entryBy = '"+purchaseOrder.getUserId()+"',"
					+ "entryTime = CURRENT_TIMESTAMP where poNo= '"+purchaseOrder.getPoNo()+"'";
			session.createSQLQuery(sql).executeUpdate();

			int length = purchaseOrder.getItemList().size();
			for (PurchaseOrderItem item : purchaseOrder.getItemList()) {
				if(item.getType().equals("Fabrics")) {
					if(item.isCheck()) {
						sql="Update tbFabricsIndent set pono='"+purchaseOrder.getPoNo()+"',poapproval='1',supplierid='"+item.getSupplierId()+"',dolar='"+item.getDollar()+"',rate='"+item.getRate()+"',amount='"+item.getAmount()+"',currency='"+item.getCurrency()+"',poManual='"+purchaseOrder.getManualPO()+"' where id='"+item.getAutoId()+"'";		
					}else {
						sql="Update tbFabricsIndent set poapproval='0',supplierid='"+item.getSupplierId()+"',dolar='"+item.getDollar()+"',rate='"+item.getRate()+"',amount='"+item.getAmount()+"',currency='"+item.getCurrency()+"',poManual='"+purchaseOrder.getManualPO()+"' where id='"+item.getAutoId()+"'";		
					}
				}else if(item.getType().equals("Accessories")) {
					if(item.isCheck()) {
						sql="Update tbAccessoriesIndent set pono='"+purchaseOrder.getPoNo()+"',poapproval='1',supplierid='"+item.getSupplierId()+"',dolar='"+item.getDollar()+"',rate='"+item.getRate()+"',amount='"+item.getAmount()+"',currency='"+item.getCurrency()+"',poManual='"+purchaseOrder.getManualPO()+"' where AccIndentId='"+item.getAutoId()+"'";		
					}else {
						sql="Update tbAccessoriesIndent set poapproval='0',supplierid='"+item.getSupplierId()+"',dolar='"+item.getDollar()+"',rate='"+item.getRate()+"',amount='"+item.getAmount()+"',currency='"+item.getCurrency()+"',poManual='"+purchaseOrder.getManualPO()+"' where AccIndentId='"+item.getAutoId()+"'";		
					}

				}else if(item.getType().equals("Carton")) {
					if(item.isCheck()) {
						sql="Update tbAccessoriesIndentForCarton set pono='"+purchaseOrder.getPoNo()+"',poapproval='1',supplierid='"+item.getSupplierId()+"',dolar='"+item.getDollar()+"',rate='"+item.getRate()+"',amount='"+item.getAmount()+"',currency='"+item.getCurrency()+"',poManual='"+purchaseOrder.getManualPO()+"' where autoId='"+item.getAutoId()+"'";		
					}else {
						sql="Update tbAccessoriesIndentForCarton set poapproval='0',supplierid='"+item.getSupplierId()+"',dolar='"+item.getDollar()+"',rate='"+item.getRate()+"',amount='"+item.getAmount()+"',currency='"+item.getCurrency()+"',poManual='"+purchaseOrder.getManualPO()+"' where autoId='"+item.getAutoId()+"'";		
					}
				}
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
	public List<PurchaseOrder> getPurchaseOrderSummeryList(String userId) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		PurchaseOrder tempPo;
		List<PurchaseOrder> dataList=new ArrayList<PurchaseOrder>();
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql;

			sql=" select pono,(select convert(varchar,orderDate,25))as orderDate,supplierId,isnull(s.name,'') as supplierName,type from tbPurchaseOrderSummary pos\r\n" + 
					" left join tbSupplier s \r\n" + 
					" on pos.supplierId = s.id\r\n" + 
					" where pos.entryBy = '"+userId+"'\r\n" + 
					" order by pos.pono desc";
					
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				tempPo = new PurchaseOrder(element[0].toString(),element[1].toString());
				tempPo.setSupplierId(element[2].toString());
				tempPo.setSupplierName(element[3].toString());
				tempPo.setType(element[4].toString());
				dataList.add(tempPo);
			}
			
			/*sql=" select pos.pono,(select convert(varchar,orderDate,103))as orderDate,fi.supplierid,isnull(s.name,'') as suppilerName  \r\n" + 
					" from tbPurchaseOrderSummary pos\r\n" + 
					"join tbFabricsIndent fi\r\n" + 
					" on pos.pono = fi.pono \r\n" + 
					" left join tbSupplier s\r\n" + 
					" on fi.supplierid = s.id\r\n" + 
					" where pos.entryBy='"+userId+"' \r\n"+
					" order by pos.pono desc ";
					
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				tempPo = new PurchaseOrder(element[0].toString(),element[1].toString());
				tempPo.setSupplierId(element[2].toString());
				tempPo.setSupplierName(element[3].toString());
				tempPo.setType("Fabrics");
				dataList.add(tempPo);
			}

			sql=" select pos.pono,(select convert(varchar,orderDate,103))as orderDate,ai.supplierid,isnull(s.name,'') as suppilerName  \r\n" + 
					" from tbPurchaseOrderSummary pos\r\n" + 
					"join tbAccessoriesIndent ai\r\n" + 
					" on pos.pono = ai.pono \r\n" + 
					" left join tbSupplier s\r\n" + 
					" on ai.supplierid = s.id\r\n"
					+ "where pos.entryBy='"+userId+"' \r\n" + 
					" group by pos.pono,orderDate,ai.supplierid,s.name\r\n" + 
					" order by pos.pono desc";
			list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				tempPo = new PurchaseOrder(element[0].toString(),element[1].toString());
				tempPo.setSupplierId(element[2].toString());
				tempPo.setSupplierName(element[3].toString());
				tempPo.setType("Accessories");
				dataList.add(tempPo);
			}

			sql=" select pos.pono,(select convert(varchar,orderDate,103))as orderDate,aif.supplierid,isnull(s.name,'') as suppilerName  \r\n" + 
					" from tbPurchaseOrderSummary pos\r\n" + 
					"join tbAccessoriesIndentForCarton aif\r\n" + 
					" on pos.pono = aif.pono \r\n" + 
					" left join tbSupplier s\r\n" + 
					" on aif.supplierid = s.id\r\n"
					+ "where pos.entryBy='"+userId+"' \r\n" + 
					" group by pos.pono,orderDate,aif.supplierid,s.name\r\n" + 
					" order by pos.pono desc";
			list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				tempPo = new PurchaseOrder(element[0].toString(),element[1].toString());
				tempPo.setSupplierId(element[2].toString());
				tempPo.setSupplierName(element[3].toString());
				tempPo.setType("curton");
				dataList.add(tempPo);
			}*/

			tx.commit();
		}
		catch(Exception e){
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			
		}
		finally {
			session.close();
		}
		return dataList;
	}
	
	@Override
	public List<CommonModel> getPendingIndentList(String userId) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		CommonModel tempCommon;
		List<CommonModel> dataList=new ArrayList<CommonModel>();
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql;

			sql="select AINo,'Accessories' as type,(select convert(varchar,IndentDate,25))as IndentDate from tbAccessoriesIndent ai\r\n" + 
					"where ai.IndentPostBy = '"+userId+"' and ai.pono is null\r\n" + 
					"group by ai.AINo,ai.IndentDate\r\n" + 
					"order by ai.AINo desc";
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				tempCommon = new CommonModel();
				tempCommon.setId(element[0].toString());
				tempCommon.setIndentType(element[1].toString());
				tempCommon.setDate(element[2].toString());
				dataList.add(tempCommon);
			}

			sql="select fi.indentId,'Fabrics' as type,(select convert(varchar,IndentDate,25))as IndentDate from tbFabricsIndent fi\r\n" + 
					"where fi.entryby = '"+userId+"' and fi.pono is null\r\n" + 
					"group by fi.indentId,fi.IndentDate\r\n" + 
					"order by fi.indentId desc";
			list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				tempCommon = new CommonModel();
				tempCommon.setId(element[0].toString());
				tempCommon.setIndentType(element[1].toString());
				tempCommon.setDate(element[2].toString());
				dataList.add(tempCommon);
			}

			sql="select indentId,'Carton' as type,(select convert(varchar,IndentDate,25))as IndentDate from tbAccessoriesIndentForCarton ai\r\n" + 
					"where ai.IndentPostBy = '"+userId+"' and ai.pono is null\r\n" + 
					"group by ai.indentId,ai.IndentDate\r\n" + 
					"order by ai.indentId desc";
			list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				tempCommon = new CommonModel();
				tempCommon.setId(element[0].toString());
				tempCommon.setIndentType(element[1].toString());
				tempCommon.setDate(element[2].toString());
				dataList.add(tempCommon);
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
	public PurchaseOrder getPurchaseOrder(String poNo,String poType) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		PurchaseOrder purchaseOrder = null;
		List<PurchaseOrderItem> dataList=new ArrayList<PurchaseOrderItem>();
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql;


			if(poType.equalsIgnoreCase("Fabrics")) {
				sql="select fi.id,isnull(fi.purchaseOrder,'')as purchaseOrder,isnull(style.StyleNo,'')as styleNo,isnull(f.ItemName,'') as accessoriesname,fi.supplierId,fi.rate,fi.dolar,isnull(c.Colorname,'') as colorName,'' as size,fi.TotalQty,fi.RequireUnitQty, unit.unitname,isnull(fi.currency,'') as currency\r\n" + 
						"from tbFabricsIndent fi \r\n" + 
						"left join TbStyleCreate style\r\n" + 
						"on fi.styleId = cast(style.StyleId as varchar)\r\n" + 
						"left join tbColors c\r\n" + 
						"on fi.itemcolor = cast(c.ColorId as varchar)\r\n" + 
						"left join TbFabricsItem f\r\n" + 
						"on fi.fabricsid = f.id\r\n" + 
						"left join tbunits unit\r\n" + 
						"on fi.UnitId = unit.Unitid\r\n" + 
						"where fi.poNo='"+poNo+"' and poapproval='1'   order by fi.id";

				List<?> list = session.createSQLQuery(sql).list();
				for(Iterator<?> iter = list.iterator(); iter.hasNext();)
				{	
					Object[] element = (Object[]) iter.next();
					dataList.add(new PurchaseOrderItem(element[0].toString(), element[1].toString(), element[2].toString(),"Fabrics", element[3].toString(), element[4].toString(), Double.valueOf(element[5].toString()), element[6].toString(), element[7].toString(), element[8].toString(), Double.valueOf(element[9].toString()), Double.valueOf(element[10].toString()), element[11].toString(),element[12].toString(),true));				
				}
			}
			if(poType.equalsIgnoreCase("Accessories")) {
				sql=" select ai.AccIndentId,isnull(ai.purchaseOrder,'')as purchaseOrder,isnull(style.StyleNo,'') as styleNo,isnull(accItem.itemname,'') as accessoriesname,ai.supplierId,ai.rate,ai.dolar,isnull(c.Colorname,'') as itemcolor,ai.size,ai.OrderQty,ai.TotalQty,isnull(unit.unitname,'') as unitName,isnull(ai.currency,'') as currency\r\n" + 
						" from tbAccessoriesIndent ai \r\n" + 
						" left join TbStyleCreate style\r\n" + 
						" on ai.styleid = cast(style.StyleId as varchar)\r\n" + 
						" left join tbColors c\r\n" + 
						" on ai.ColorId = cast(c.ColorId as varchar)\r\n" + 
						" left join TbAccessoriesItem accItem\r\n" + 
						" on ai.accessoriesItemId = accItem.itemid\r\n" + 
						" left join tbunits unit\r\n" + 
						" on ai.UnitId = unit.Unitid "
						+ "where ai.poNo='"+poNo+"' and ai.poapproval='1'  order by ai.AccIndentId";
				List<?> list = session.createSQLQuery(sql).list();
				for(Iterator<?> iter = list.iterator(); iter.hasNext();)
				{	
					Object[] element = (Object[]) iter.next();
					dataList.add(new PurchaseOrderItem(element[0].toString(), element[1].toString(), element[2].toString(),"Accessories", element[3].toString(), element[4].toString(), Double.valueOf(element[5].toString()), element[6].toString(), element[7].toString(), element[8].toString(), Double.valueOf(element[9].toString()), Double.valueOf(element[10].toString()), element[11].toString(),element[12].toString(),true));
				}
			}
			if(poType.equalsIgnoreCase("Carton")) {
				sql="select aic.indentId,isnull(aic.purchaseOrder,'') as purchaseOrder ,isnull(style.StyleNo,'') as styleNo,isnull(ai.itemname,'') as accessoriesname,aic.supplierId,aic.rate,aic.dolar,'' as color,isnull(ss.sizeName,'') as sizeName,aic.OrderQty,aic.Qty,unit.unitname,isnull(aic.currency,'') as currency\n" + 
						" from tbAccessoriesIndentForCarton aic\n" + 
						" left join TbStyleCreate style\n" + 
						" on aic.styleid = cast(style.StyleId as varchar)\n" + 
						" left join tbunits unit\n" + 
						" on aic.UnitId = unit.Unitid \n"
						+ "left join tbStyleSize ss\r\n" + 
						"on aic.sizeId = ss.id \r\n" + 
						" left join TbAccessoriesItem ai\n" + 
						" on aic.accessoriesItemId = ai.itemid \r\n" + 
						" where aic.poNo = '"+poNo+"' and  poapproval='1'  order by aic.indentId";
				List<?> list = session.createSQLQuery(sql).list();
				for(Iterator<?> iter = list.iterator(); iter.hasNext();)
				{	
					Object[] element = (Object[]) iter.next();
					dataList.add(new PurchaseOrderItem(element[0].toString(),element[1].toString(), element[2].toString(),"Carton", element[2].toString(), element[3].toString(), Double.valueOf(element[4].toString()), element[5].toString(), element[6].toString(), element[7].toString(), Double.valueOf(element[8].toString()), Double.valueOf(element[9].toString()), element[10].toString(),element[10].toString(),true));
				}
			}
			


			sql = "select poNo,(select convert(varchar,orderDate,103))as orderDate,(select convert(varchar,deliveryDate,103))as deliveryDate,supplierId,deliveryto,orderby,billto,ManualPo,paymentTerm,Note,Subject,entryBy from tbPurchaseOrderSummary where poNo='"+poNo+"'";
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{
				Object[] element = (Object[]) iter.next();
				purchaseOrder = new PurchaseOrder(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString(), element[7].toString(), "0", element[8].toString(), element[9].toString(), element[10].toString(), dataList,poType, element[11].toString());
			}

			tx.commit();
		}
		catch(Exception e){
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			
		}
		finally {
			session.close();
		}
		return purchaseOrder;
	}

	@Override
	public List<PurchaseOrderItem> getPurchaseOrderItemList(AccessoriesIndent accessoriesIndent) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<PurchaseOrderItem> dataList=new ArrayList<PurchaseOrderItem>();
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql;
			if(accessoriesIndent.getIndentType().equals("Fabrics")) {
				sql="select fi.id,isnull(fi.purchaseOrder,'')as purchaseOrder,isnull(style.StyleNo,'')as styleNo,isnull(f.ItemName,'') as accessoriesname,isnull(c.Colorname,'') as colorName,'' as size,fi.TotalQty,fi.RequireUnitQty, unit.unitname\r\n" + 
						"from tbFabricsIndent fi \r\n" + 
						"left join TbStyleCreate style\r\n" + 
						"on fi.styleId = cast(style.StyleId as varchar) \r\n" + 
						"left join tbColors c\r\n" + 
						"on fi.itemcolor = cast(c.ColorId as varchar)\r\n" + 
						"left join TbFabricsItem f\r\n" + 
						"on fi.fabricsid = f.id\r\n" + 
						"left join tbunits unit\r\n" + 
						"on fi.UnitId = unit.Unitid\r\n" + 
						"where fi.indentId='"+accessoriesIndent.getAiNo()+"' and fabricsid='"+accessoriesIndent.getAccessoriesId()+"' and (poapproval IS NULL or poapproval='0')  order by fi.id";

				List<?> list = session.createSQLQuery(sql).list();
				for(Iterator<?> iter = list.iterator(); iter.hasNext();)
				{	
					Object[] element = (Object[]) iter.next();
					dataList.add(new PurchaseOrderItem(element[0].toString(),element[1].toString(), element[2].toString(),accessoriesIndent.getIndentType(), element[3].toString(),"", 0, "0", element[4].toString(), element[5].toString(), Double.valueOf(element[6].toString()), Double.valueOf(element[7].toString()), element[8].toString(),"",false));
				}
			}else if(accessoriesIndent.getIndentType().equals("Accessories")) {
				sql=" select ai.AccIndentId,isnull(ai.purchaseOrder,'')as purchaseOrder,isnull(style.StyleNo,'') as styleNo,isnull(accItem.itemname,'') as accessoriesname,isnull(c.Colorname,'') as itemcolor,isnull(ss.sizeName,'') as sizeName,ai.TotalQty,ai.requireUnitQty,isnull(unit.unitname,'') as unitName\r\n" + 
						" from tbAccessoriesIndent ai \r\n" + 
						" left join TbStyleCreate style\r\n" + 
						" on ai.styleid = cast(style.styleId as varchar) \r\n" + 
						" left join tbColors c\r\n" + 
						" on ai.ColorId = cast(c.colorId as varchar) \r\n" + 
						" left join TbAccessoriesItem accItem\r\n" + 
						" on ai.accessoriesItemId = accItem.itemid\r\n"+
						"left join tbStyleSize ss \r\n"
						+ "on ai.size = ss.id" + 
						" left join tbunits unit\r\n" + 
						" on ai.UnitId = unit.Unitid "
						+ "where ai.aiNo='"+accessoriesIndent.getAiNo()+"' and ai.accessoriesItemId='"+accessoriesIndent.getAccessoriesId()+"' and (poapproval IS NULL or poapproval='0')  order by ai.AccIndentId";
				List<?> list = session.createSQLQuery(sql).list();
				for(Iterator<?> iter = list.iterator(); iter.hasNext();)
				{	
					Object[] element = (Object[]) iter.next();
					dataList.add(new PurchaseOrderItem(element[0].toString(),element[1].toString(), element[2].toString(),accessoriesIndent.getIndentType(), element[3].toString(), "", 0, "0", element[4].toString(), element[5].toString(), Double.valueOf(element[6].toString()), Double.valueOf(element[7].toString()), element[8].toString(),"",false));
				}
			}else {
				sql="select aic.autoId,isnull(aic.purchaseOrder,'')as purchaseOrder ,isnull(style.StyleNo,'') as styleNo,isnull(ai.itemname,'') as accessoriesname,isnull(c.Colorname,'') as color,isnull(ss.sizeName,'') as sizeName,aic.OrderQty,aic.Qty,unit.unitname\r\n" + 
						" from tbAccessoriesIndentForCarton aic\r\n" + 
						" left join TbStyleCreate style\r\n" + 
						"on aic.styleid = cast(style.StyleId as varchar)\r\n" + 
						"left join TbAccessoriesItem ai\r\n" + 
						"on aic.accessoriesItemId = ai.itemid\r\n" + 
						"left join tbColors c\r\n" + 
						"on aic.ColorId = c.ColorId\r\n" + 
						"left join tbStyleSize ss\r\n" + 
						"on aic.sizeId = ss.id\r\n" + 
						" left join tbunits unit\r\n" + 
						" on aic.UnitId = unit.Unitid \r\n" + 
						" where aic.indentId = '"+accessoriesIndent.getAiNo()+"' and aic.accessoriesItemId = '"+accessoriesIndent.getAccessoriesId()+"' and aic.poapproval IS NULL or poapproval='0'  order by aic.autoId";
				List<?> list = session.createSQLQuery(sql).list();
				for(Iterator<?> iter = list.iterator(); iter.hasNext();)
				{	
					Object[] element = (Object[]) iter.next();
					dataList.add(new PurchaseOrderItem(element[0].toString(),element[1].toString(), element[2].toString(),accessoriesIndent.getIndentType(), element[3].toString(),"", 0, "0", element[4].toString(), element[5].toString(), Double.valueOf(element[6].toString()), Double.valueOf(element[7].toString()), element[8].toString(),"",false));
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
	public List<SampleRequisitionItem> getIncomepleteSampleRequisitionItemList(String userId) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<SampleRequisitionItem> dataList=new ArrayList<SampleRequisitionItem>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select ISNULL(srd.sampleReqId,0) as sampleReqId,srd.SampleTypeId,srd.BuyerId,srd.sampleAutoId,srd.StyleId,sc.StyleNo,srd.ItemId,id.itemname,srd.ColorId,c.Colorname,srd.PurchaseOrder,srd.sizeGroupId,srd.userId \r\n" + 
					"					from TbSampleRequisitionDetails srd\r\n" + 
					"					left join TbStyleCreate sc\r\n" + 
					"					on srd.StyleId = sc.StyleId\r\n" + 
					"					left join tbItemDescription id\r\n" + 
					"					on srd.ItemId = id.itemid\r\n" + 
					"					left join tbColors c\r\n" + 
					"					on srd.ColorId = c.ColorId\r\n" + 
					"					where srd.sampleReqId IS NULL and srd.UserId='"+userId+"' order by sizeGroupId";
			System.out.println(sql);
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();							
				dataList.add(new SampleRequisitionItem(element[0].toString(),element[1].toString(),element[2].toString(), element[3].toString(),element[4].toString(), element[5].toString(), element[6].toString(), element[7].toString(), element[8].toString(), element[9].toString(),element[10].toString(),element[11].toString(),element[12].toString()));
			}

			for (SampleRequisitionItem sampleReqItem : dataList) {
				sql = "select bs.sizeGroupId,bs.sizeId,ss.sizeName,bs.sizeQuantity from tbSizeValues bs\r\n" + 
						"join tbStyleSize ss \r\n" + 
						"on ss.id = bs.sizeId \r\n" + 
						"where bs.linkedAutoId = '"+sampleReqItem.getAutoId()+"' and bs.type='"+SizeValuesType.SAMPLE.getType()+"' and bs.sizeGroupId = '"+sampleReqItem.getSizeGroupId()+"' \r\n" + 
						"order by ss.sortingNo";
				System.out.println(sql);
				List<?> list2 = session.createSQLQuery(sql).list();
				ArrayList<Size> sizeList=new ArrayList<Size>();
				for(Iterator<?> iter = list2.iterator(); iter.hasNext();)
				{	
					Object[] element = (Object[]) iter.next();	
					sizeList.add(new Size(element[0].toString(),element[1].toString(), element[3].toString()));
				}
				sampleReqItem.setSizeList(sizeList);
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
	public boolean fileUpload(String Filename, String pcname, String ipaddress,String purpose,String user,String buyerName, String purchaseOrder) {
		Session session=HibernateUtil.openSession();
		boolean fileinsert=false;
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();

			if (!duplicateFile(user, Filename)) {
				String sql="insert into TbUploadFileLogInfo ( FileName, UploadBy, UploadIp, UploadMachine, Purpose, UploadDate, UploadEntryTime, buyerid, purchaseorder) values('"+Filename+"','"+user+"','"+ipaddress+"','"+pcname+"','"+purpose+"',convert(varchar, getdate(), 23),CURRENT_TIMESTAMP,'"+buyerName+"','"+purchaseOrder+"')";
				session.createSQLQuery(sql).executeUpdate();
				tx.commit();
				fileinsert= true;
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
		session.close();
		return fileinsert;
	}


	public boolean duplicateFile(String user, String filename) {

		boolean exists=false;
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<SampleRequisitionItem> dataList=new ArrayList<SampleRequisitionItem>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select filename from TbUploadFileLogInfo where FileName like '"+filename+"' and uploadby='"+user+"'";
			System.out.println(sql);
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				//Object[] element = (Object[]) iter.next();							
				//dataList.add(new SampleRequisitionItem(element[0].toString(),element[1].toString(),element[2].toString(), element[3].toString(),element[4].toString(), element[5].toString(), element[6].toString(), element[7].toString(), element[8].toString(), element[9].toString(),element[10].toString(),element[11].toString(),element[12].toString(),element[13].toString(),element[14].toString()));
				exists=true;
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
		return exists;


	}

	@Override
	public List<pg.orderModel.FileUpload> findfiles(String start, String end, String user) {
		boolean exists=false;
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<pg.orderModel.FileUpload> dataList=new ArrayList<pg.orderModel.FileUpload>();
		int userwiseupload=0;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="SELECT  autoid, FileName,(select username from Tblogin where id=a.UploadBy) as UploadBy, UploadIp, UploadMachine, Purpose,convert(varchar, UploadDate, 23) as UploadDate, convert(varchar, UploadEntryTime, 25) as uploaddatetime ,isnull((select username from Tblogin where id=a.DownloadBy),'') as DownloadBy, isnull(DownloadIp,'') as downloadip,isnull( DownloadMachine,'') as downloadmachine,isnull(convert(varchar, DownloadDate, 23),'') as DownloadDate, isnull(convert(varchar, DownloadEntryTime, 25),'') as downloaddatetime  FROM  TbUploadFileLogInfo a where UploadBy='"+user+"' and a.UploadDate between '"+start+"' and '"+end+"'";
			System.out.println(sql);
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();							
				dataList.add(new FileUpload(element[0].toString(),element[1].toString(),element[2].toString(), element[3].toString(),element[4].toString(), element[5].toString(), element[6].toString(), element[7].toString(), element[8].toString(), element[9].toString(),element[10].toString(),element[11].toString(),element[12].toString()));
				exists=true;
			}

			if (list.size()==0) {
				String fileid="";

				sql="select uploadedfileid from tbuploadfilelogdetails where alloweduser='"+user+"'";
				System.out.println(sql);
				List<?> list1 = session.createSQLQuery(sql).list();
				/*fileid=list1.get(0).toString();
				System.out.println(" found file id 1 "+fileid);*/
				for(Iterator<?> iter = list1.iterator(); iter.hasNext();)
				{	
					fileid=iter.next().toString();

				}	
				System.out.println(" found file id for user "+fileid);

				sql="SELECT  autoid, FileName,(select username from Tblogin where id=a.UploadBy) as UploadBy, UploadIp, UploadMachine, Purpose,convert(varchar, UploadDate, 23) as UploadDate, convert(varchar, UploadEntryTime, 25) as uploaddatetime ,isnull((select username from Tblogin where id=a.DownloadBy),'') as DownloadBy, isnull(DownloadIp,'') as downloadip,isnull( DownloadMachine,'') as downloadmachine,isnull(convert(varchar, DownloadDate, 23),'') as DownloadDate, isnull(convert(varchar, DownloadEntryTime, 25),'') as downloaddatetime  FROM  TbUploadFileLogInfo a where autoid='"+fileid+"' and a.UploadDate between '"+start+"' and '"+end+"'";
				System.out.println(sql);
				List<?> list2 = session.createSQLQuery(sql).list();
				for(Iterator<?> iter = list2.iterator(); iter.hasNext();)
				{	
					Object[] element = (Object[]) iter.next();							
					dataList.add(new FileUpload(element[0].toString(),element[1].toString(),element[2].toString(), element[3].toString(),element[4].toString(), element[5].toString(), element[6].toString(), element[7].toString(), element[8].toString(), element[9].toString(),element[10].toString(),element[11].toString(),element[12].toString()));
					exists=true;
				}

				if (list2.size()==0) {
					String deptid="";
					sql="select departmentid from tblogin where id='"+user+"'";
					System.out.println(sql);
					List<?> list3 = session.createSQLQuery(sql).list();

					for(Iterator<?> iter = list3.iterator(); iter.hasNext();)
					{	
						deptid=iter.next().toString();

					}	

					sql="select uploadedfileid from tbuploadfilelogdetails where dept='"+deptid+"'";
					System.out.println(sql);
					List<?> list4 = session.createSQLQuery(sql).list();

					System.out.println(" found file id 1 "+fileid);
					for(Iterator<?> iter = list4.iterator(); iter.hasNext();)
					{	
						fileid=iter.next().toString();

					}	
					System.out.println(" found file id for dept "+fileid);


					sql="SELECT  autoid, FileName,(select username from Tblogin where id=a.UploadBy) as UploadBy, UploadIp, UploadMachine, Purpose,convert(varchar, UploadDate, 23) as UploadDate, convert(varchar, UploadEntryTime, 25) as uploaddatetime ,isnull((select username from Tblogin where id=a.DownloadBy),'') as DownloadBy, isnull(DownloadIp,'') as downloadip,isnull( DownloadMachine,'') as downloadmachine,isnull(convert(varchar, DownloadDate, 23),'') as DownloadDate, isnull(convert(varchar, DownloadEntryTime, 25),'') as downloaddatetime  FROM  TbUploadFileLogInfo a where autoid='"+fileid+"' and a.UploadDate between '"+start+"' and '"+end+"'";
					System.out.println(sql);
					List<?> list5 = session.createSQLQuery(sql).list();
					for(Iterator<?> iter = list5.iterator(); iter.hasNext();)
					{	
						Object[] element = (Object[]) iter.next();							
						dataList.add(new FileUpload(element[0].toString(),element[1].toString(),element[2].toString(), element[3].toString(),element[4].toString(), element[5].toString(), element[6].toString(), element[7].toString(), element[8].toString(), element[9].toString(),element[10].toString(),element[11].toString(),element[12].toString()));
						exists=true;
					}
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
	public List<pg.orderModel.FileUpload> findfiles(String buyerId, String purchaseOrder, int fileUploadType) {
		boolean exists=false;
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		FileUpload tempFileUpload = null;
		List<pg.orderModel.FileUpload> dataList=new ArrayList<pg.orderModel.FileUpload>();
		int userwiseupload=0;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select ufl.AutoId,ufl.FileName,l.fullname from TbUploadFileLogInfo ufl\n" + 
					"left join Tblogin l\n" + 
					"on ufl.UploadBy = l.id\n" + 
					"where buyerid = '"+buyerId+"' and purchaseorder = '"+purchaseOrder+"'";
			System.out.println(sql);
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				tempFileUpload = new FileUpload();
				tempFileUpload.setAutoid(element[0].toString());
				tempFileUpload.setFilename(element[1].toString());
				tempFileUpload.setUploadby(element[2].toString());
				dataList.add(tempFileUpload);
				exists=true;
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
	public boolean fileDownload(String filename, String user, String ip,String computername) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();


			String sql="update TbUploadFileLogInfo set DownloadBy='"+user+"',DownloadIp='"+ip+"',DownloadMachine='"+computername+"',DownloadDate=convert(varchar, getdate(), 23),DownloadEntryTime=CURRENT_TIMESTAMP where filename like '%"+filename+"%'";
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
	public boolean deletefile(String filename,String id) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="";

			sql="delete from TbUploadFileLogInfo where filename like '%"+filename+"%' and DownloadBy is null and DownloadIp is null and DownloadMachine is null and DownloadDate is null and DownloadEntryTime is null";
			session.createSQLQuery(sql).executeUpdate();

			sql="delete from tbuploadfilelogdetails where uploadedfileid='"+id+"'";
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
	public List<SampleCadAndProduction> getSampleCommentsList() {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<SampleCadAndProduction> dataList=new ArrayList<SampleCadAndProduction>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select sampleCommentId,PurchaseOrder,sc.StyleId,sc.StyleNo,sci.ItemId,id.itemname,c.ColorId,c.Colorname,isnull(SampleTypeId,'')as SampleTypeId,isnull(sti.name ,'') as name  \r\n" + 
					"from TbSampleCadInfo sci\r\n" + 
					"left join TbStyleCreate sc\r\n" + 
					"on sci.StyleId = sc.StyleId\r\n" + 
					"left join tbItemDescription id\r\n" + 
					"on sci.ItemId = id.itemid\r\n" + 
					"left join tbColors c\r\n" + 
					"on sci.ColorId = c.ColorId\r\n" + 
					"left join TbSampleTypeInfo sti\r\n" + 
					"on sci.SampleTypeId = sti.AutoId";
			System.out.println(sql);
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();							
				dataList.add(new SampleCadAndProduction(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString(), element[7].toString(), element[8].toString(), element[9].toString()));
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
	public SampleCadAndProduction getSampleProductionInfo(String sampleCommentsId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		SampleCadAndProduction sampleCadAndProduction= null;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select sampleCommentId,sci.PurchaseOrder,sc.StyleId,sc.StyleNo,sci.ItemId,id.itemname,c.ColorId,c.Colorname,isnull(sci.SampleTypeId,'')as SampleTypeId,isnull(sti.name,'') as sampleTypeName,isnull(sci.CuttingQty,'') cuttingQty,isnull(sci.CuttingDate,'') cuttingDate, isnull(sv.sizeQuantity,'0') as requisitionQty,isnull(sci.PrintSendDate,'') printSendDate,isnull(sci.PrintReceivedDate,'') printReceiveDate,isnull(sci.PrintReceivedQty,'') printReceiveQty,isnull(sci.EmbroiderySendDate,'') embroiderySendDate,isnull(sci.EmbroideryReceivedDate,'') embroideryReceiveDate,isnull(sci.EmbroideryReceivedQty,'') embroideryReceiveQty,isnull(sci.SewingSendDate,'') sewingSendDate,isnull(sci.SewingFinishedDate,'') sewingFinishDate,isnull(sci.OperatorName,'') operatorName,isnull(sci.quality,'') quality\r\n" + 
					"from TbSampleCadInfo sci\r\n" + 
					"left join TbStyleCreate sc\r\n" + 
					"on sci.StyleId = sc.StyleId\r\n" + 
					"left join tbItemDescription id\r\n" + 
					"on sci.ItemId = id.itemid\r\n" + 
					"left join tbColors c\r\n" + 
					"on sci.ColorId = c.ColorId\r\n" + 
					"left join TbSampleTypeInfo sti\r\n" + 
					"on sci.SampleTypeId = sti.AutoId\r\n" + 
					"left join TbSampleRequisitionDetails srd\r\n" + 
					"on sci.PurchaseOrder = srd.purchaseOrder and sci.StyleId = srd.StyleId and sci.itemId = srd.ItemId and sci.ColorId = srd.ColorId and sci.SampleTypeId = srd.SampleTypeId\r\n" + 
					"left join tbSizeValues sv\r\n" + 
					"on srd.sampleAutoId = sv.linkedAutoId and sv.type = '"+SizeValuesType.SAMPLE.getType()+"'\r\n" + 
					"where sci.sampleCommentId = '"+sampleCommentsId+"'";
			System.out.println(sql);
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();							
				sampleCadAndProduction =  new SampleCadAndProduction(element[0].toString(), element[1].toString(),element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString(), element[7].toString(), element[8].toString(), element[9].toString(), element[10].toString(), element[11].toString(), element[12].toString(), element[13].toString(), element[14].toString(), element[15].toString(), element[16].toString(), element[17].toString(), element[18].toString(), element[19].toString(), element[20].toString(), element[21].toString(), element[22].toString());
			}
			tx.commit();
		}
		catch(Exception e){
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}		
		}
		finally {
			session.close();
		}
		return sampleCadAndProduction;
	}

	@Override
	public boolean postSampleProductionInfo(SampleCadAndProduction sampleCadAndProduction) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<CommonModel> query=new ArrayList<CommonModel>();

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="update TbSampleCadInfo set "
					+ "CuttingQty='"+sampleCadAndProduction.getCuttingQty()+"',"
					+ " CuttingDate='"+sampleCadAndProduction.getCuttingDate()+"',"
					+ "PrintSendDate='"+sampleCadAndProduction.getPrintSendDate()+"',"
					+ "PrintReceivedDate='"+sampleCadAndProduction.getPrintReceivedDate()+"',"
					+ "PrintReceivedQty='"+sampleCadAndProduction.getPrintReceivedQty()+"',"
					+ "EmbroiderySendDate='"+sampleCadAndProduction.getEmbroiderySendDate()+"',"
					+ "EmbroideryReceivedDate='"+sampleCadAndProduction.getEmbroideryReceivedDate()+"',"
					+ "EmbroideryReceivedQty='"+sampleCadAndProduction.getEmbroideryReceivedQty()+"',"
					+ "SewingSendDate='"+sampleCadAndProduction.getSewingSendDate()+"',"
					+ "SewingFinishedDate='"+sampleCadAndProduction.getSewingFinishDate()+"',"
					+ "SampleProductionUserId='"+sampleCadAndProduction.getSampleProductionUserId()+"',"
					+ "SampleProductionUserIp='"+sampleCadAndProduction.getSampleProductionUserIp()+"',"
					+ "SampleCommentFlag='"+sampleCadAndProduction.getSampleCommentFlag()+"',"
					+ "SampleProductionDate=CURRENT_TIMESTAMP,"
					+ "SampleProductionEntryTime=CURRENT_TIMESTAMP,"
					+ "OperatorName='"+sampleCadAndProduction.getOperatorName()+"',"
					+ "quality='"+sampleCadAndProduction.getQuality()+"'"
					+ " where SampleCommentId='"+sampleCadAndProduction.getSampleCommentId()+"'";
			session.createSQLQuery(sql).executeUpdate();


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
	public List<CourierModel> getcourierList() {
		String countryname="";
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<CourierModel> Buyers=new ArrayList<>();

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select * from tbCourier";
			System.out.println(" check duplicate buyer query ");

			List<?> list = session.createSQLQuery(sql).list();


			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				Buyers.add(new CourierModel(element[0].toString(),element[1].toString()));

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
		return Buyers;
	}

	@Override
	public boolean ConfirmParcel(ParcelModel parcel) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="insert into tbparcel (buyerid,courierId,trackingNo,dispatchedDate,deliveryBy,deliveryTo,mobileNo,unitId,grossWeight,rate,amount,remarks,entrytime,userId) \n" + 
					"values ('"+parcel.getBuyerId()+"',"
					+ "'"+parcel.getCourierId()+"',"
					+ "'"+parcel.getTrackingNo()+"',"
					+ "'"+parcel.getDispatchedDate()+"',"
					+ "'"+parcel.getDeliveryBy()+"',"
					+ "'"+parcel.getDeliveryTo()+"',"
					+ "'"+parcel.getMobileNo()+"',"
					+ "'"+parcel.getUnitId()+"',"
					+ "'"+parcel.getGrossWeight()+"',"
					+ "'"+parcel.getRate()+"',"
					+ "'"+parcel.getAmount()+"',"
					+ "'"+parcel.getRemarks()+"',"
					+ "CURRENT_TIMESTAMP,"
					+ "'"+parcel.getUserId()+"');";
			session.createSQLQuery(sql).executeUpdate();

			sql = "select isnull(max(autoid),0) as maxId from tbparcel";
			List<?> list = session.createSQLQuery(sql).list();
			String parcelId="0";
			if(list.size()>0) {
				parcelId = list.get(0).toString();
			}
			JSONParser jsonParser = new JSONParser();
			JSONObject itemsObject = (JSONObject)jsonParser.parse(parcel.getParcelItems());
			JSONArray itemList = (JSONArray) itemsObject.get("list");

			for(int i=0;i<itemList.size();i++) {
				JSONObject item = (JSONObject) itemList.get(i);
				sql="insert into tbParcelDetails(parcelId,buyerId,styleId,purchaseOrderId,purchaseOrder,sizeId,colorId,sampleId,quantity,entryTime,userId) \n" + 
						"values('"+parcelId+"','"+item.get("buyerId")+"','"+item.get("styleId")+"','"+item.get("purchaseOrderId")+"','"+item.get("purchaseOrder")+"','"+item.get("sizeId")+"','"+item.get("colorId")+"','"+item.get("sampleId")+"','"+item.get("quantity")+"',CURRENT_TIMESTAMP,'"+item.get("userId")+"');";
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
	public List<ParcelModel> parcelList() {
		String countryname="";
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<ParcelModel> Buyers=new ArrayList<>();

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select autoId,buyerId,b.name as buyerName,a.courierId,c.name as courierName,a.trackingNo,convert(varchar,a.dispatchedDate,25)as dispachedDate \n" + 
					"from tbparcel a \n" + 
					"left join tbBuyer b \n" + 
					"on a.buyerId = b.id \n" + 
					"left join tbCourier c \n" + 
					"on a.courierId = c.id";
			List<?> list = session.createSQLQuery(sql).list();

			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				Buyers.add(new ParcelModel(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString()));

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
		return Buyers;
	}

	@Override
	public ParcelModel getParcelInfo(String autoId) {
		String countryname="";
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		ParcelModel parcel = null;

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select p.autoId,p.buyerId,p.courierId,p.trackingNo,convert(varchar,p.dispatchedDate,25)as dispachedDate,p.deliveryBy,p.deliveryTo,p.mobileNo,p.unitId,p.grossWeight,p.rate,p.amount,p.remarks,p.userId from tbparcel p where p.autoId = '"+autoId+"'";
			List<?> list = session.createSQLQuery(sql).list();

			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				parcel = new ParcelModel(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString(), element[7].toString(), "", element[8].toString(), element[9].toString(), element[10].toString(), element[11].toString(), element[12].toString(), element[13].toString());
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
		return parcel;
	}

	@Override
	public List<ParcelModel> getParcelItems(String autoId) {
		String countryname="";
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		ParcelModel parcel = null;
		List<ParcelModel> itemList=new ArrayList<>();

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select pd.autoId,pd.buyerId,b.name as buyerName,pd.styleId,sc.StyleNo,pd.purchaseOrderId,pd.purchaseOrder,pd.colorId,c.Colorname,pd.sizeId,ss.sizeName,pd.sampleId,sti.Name as sampleType,pd.quantity \n" + 
					"from tbParcelDetails pd \n" + 
					"left join tbBuyer b \n" + 
					"on pd.buyerId = b.id \n" + 
					"left join TbStyleCreate sc \n" + 
					"on pd.styleId = sc.StyleId \n" + 
					"left join tbColors c \n" + 
					"on pd.colorId = c.ColorId \n" + 
					"left join tbStyleSize ss \n" + 
					"on pd.sizeId = ss.id \n" + 
					"left join TbSampleTypeInfo sti \n" + 
					"on pd.sampleId = sti.AutoId \n" + 
					"where pd.parcelId = '"+autoId+"'";

			List<?> list = session.createSQLQuery(sql).list();


			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				itemList.add(new ParcelModel(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString(), element[7].toString(), element[8].toString(), element[9].toString(), element[10].toString(), element[11].toString(), element[12].toString(), element[13].toString()));

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
		return itemList;
	}

	@Override
	public boolean editParecel(ParcelModel parcel) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql = "update tbparcel set buyerId = '"+parcel.getBuyerId()+"' ,courierId='"+parcel.getCourierId()+"',trackingNo='"+parcel.getTrackingNo()+"',dispatchedDate='"+parcel.getDispatchedDate()+"',deliveryBy='"+parcel.getDeliveryBy()+"',deliveryTo='"+parcel.getDeliveryTo()+"',mobileNo='"+parcel.getMobileNo()+"',unitId='"+parcel.getUnitId()+"',grossWeight='"+parcel.getGrossWeight()+"',rate='"+parcel.getRate()+"',amount='"+parcel.getAmount()+"',remarks='"+parcel.getRemarks()+"',entrytime=CURRENT_TIMESTAMP,userId='"+parcel.getUserId()+"' where autoId ='"+parcel.getParcelId()+"'";	
			session.createSQLQuery(sql).executeUpdate();

			JSONParser jsonParser = new JSONParser();
			JSONObject itemsObject = (JSONObject)jsonParser.parse(parcel.getParcelItems());
			JSONArray itemList = (JSONArray) itemsObject.get("list");

			for(int i=0;i<itemList.size();i++) {
				JSONObject item = (JSONObject) itemList.get(i);
				sql="insert into tbParcelDetails(parcelId,buyerId,styleId,purchaseOrderId,purchaseOrder,sizeId,colorId,sampleId,quantity,entryTime,userId) \n" + 
						"values('"+parcel.getParcelId()+"','"+item.get("buyerId")+"','"+item.get("styleId")+"','"+item.get("purchaseOrderId")+"','"+item.get("purchaseOrder")+"','"+item.get("sizeId")+"','"+item.get("colorId")+"','"+item.get("sampleId")+"','"+item.get("quantity")+"',CURRENT_TIMESTAMP,'"+item.get("userId")+"');";
				session.createSQLQuery(sql).executeUpdate();
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
	public boolean editParecelItem(ParcelModel parcelItem) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql = "update tbParcelDetails set styleId = '"+parcelItem.getStyleId()+"',purchaseOrderId= '"+parcelItem.getPurchaseOrderId()+"',purchaseOrder='"+parcelItem.getPurchaseOrder()+"',colorId='"+parcelItem.getColorId()+"',sizeId='"+parcelItem.getSizeId()+"',sampleId='"+parcelItem.getSampleId()+"',quantity='"+parcelItem.getQuantity()+"',entryTime=CURRENT_TIMESTAMP,userId='"+parcelItem.getUserId()+"' where autoId= '"+parcelItem.getAutoId()+"'";	
			session.createSQLQuery(sql).executeUpdate();
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



	public String POId(String buyerOrderId) {
		String countryname="";
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		String POID="";
		List<ParcelModel> Buyers=new ArrayList<>();

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="SELECT  PurchaseOrder FROM  TbBuyerOrderEstimateDetails where buyerOrderId='"+buyerOrderId+"'";

			List<?> list = session.createSQLQuery(sql).list();


			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				//Object[] element = (Object[]) iter.next();
				POID=iter.next().toString();
				break;

			}
			tx.commit();
			return POID;

		}
		catch(Exception e){
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}

		}

		finally {
			session.close();
		}
		return POID;
	}


	@Override
	public boolean sampleCadInsert(SampleCadAndProduction s) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="insert into TbSampleCadInfo  (sampleReqId,StyleId, "
					+ "PurchaseOrder, "
					+ "BuyerOrderId, "
					+ "ItemId, "
					+ "ColorId, "
					+ "SampleTypeId, "
					+ "totalRequisitionQty, "
					+ " PatternMakingDate, "
					+ "PatternMakingDespatch,"
					+ " PatternMakingReceived,"
					+ " PatternCorrectionDate, "
					+ "PatternCorrectionDespatch, "
					+ " PatternCorrectionReceived,"
					+ " PatternGradingDate, "
					+ "PatternGradingDespatch, "
					+ "PatternGradingReceived,"
					+ " PatternMarkingDate, "
					+ "PatternMarkingDespatch, "
					+ "PatternMarkingReceived, "
					+ " FeedbackComments,"
					+ " POStatus, "
					+ "SampleCommentUserId,"
					+ "entryTime) values('"+s.getSampleReqId()+"','"+s.getStyleId()+"',"
					+ "'"+s.getPurchaseOrder()+"',"
					+ "'"+s.getBuyerOrderId()+"',"
					+ "'"+s.getItemId()+"',"
					+ "'"+s.getColorId()+"',"
					+ "'"+s.getSampleTypeId()+"',"
					+ "'"+s.getSampleRequistionQty()+"',"
					+ "'"+s.getPatternMakingDate()+"',"
					+ "'"+s.getPatternMakingDespatch()+"',"
					+ "'"+s.getPatternMadingReceived()+"',"
					+ "'"+s.getPatternCorrectionDate()+"',"
					+ "'"+s.getPatternCorrectionDespatch()+"',"
					+ "'"+s.getPatternCorrectionReceived()+"',"
					+ "'"+s.getPatternGradingDate()+"',"
					+ "'"+s.getPatternGradingDespatch()+"',"
					+ "'"+s.getPatternGradingReceived()+"',"
					+ "'"+s.getPatternMarkingDate()+"',"
					+ "'"+s.getPatternMarkingDespatch()+"',"
					+ "'"+s.getPatternMarkingReceived()+"',"
					+ "'"+s.getFeedbackComments()+"',"
					+ "'"+s.getPOStatus()+"',"
					+ "'"+s.getUser()+"',GETDATE())";

			session.createSQLQuery(sql).executeUpdate();


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
	public List<SampleCadAndProduction> getSampleComments(String userId) {
		String countryname="";
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<SampleCadAndProduction> Buyers=new ArrayList<>();

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select a.samplecommentid,a.sampleReqId, ISNULL((select (select name from tbBuyer where id=b.buyerId) from TbBuyerOrderEstimateDetails b where b.BuyerOrderId=a.BuyerOrderID group by buyerid),'') as buyername,a.PurchaseOrder,ISNULL((select styleno from TbStyleCreate where StyleId=a.StyleId),'') as styleno,ISNULL((select b.itemname from tbItemDescription b where b.itemid=a.ItemId),'') as itemname,isnull((select b.Name from TbSampleTypeInfo b where b.AutoId=a.SampleTypeId),'') as sampleType from TbSampleCadInfo a where a.SampleCommentUserId='"+userId+"'\n" + 
					"";
			System.out.println(" check duplicate buyer query ");

			List<?> list = session.createSQLQuery(sql).list();


			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				Buyers.add(new SampleCadAndProduction(element[0].toString(),element[1].toString(),element[2].toString(),element[3].toString(),element[4].toString(),element[5].toString(),element[6].toString()));

			}
			


			tx.commit();


		}
		catch(Exception e){
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}

		}

		finally {
			session.close();
		}
		return Buyers;
	}

	@Override
	public List<SampleCadAndProduction> getSampleDetails(String id) {
		String countryname="";
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<SampleCadAndProduction> sampless=new ArrayList<>();

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select a.samplecommentid, (select PurchaseOrder from TbBuyerOrderEstimateDetails where BuyerOrderId=a.purchaseorder group by PurchaseOrder) as purchaseorder,a.styleid,a.ItemId, a.ColorId, a.Size, a.SampleTypeId, convert(varchar,a.patternmakingdate,10) as makingdate,a.PatternMakingDespatch, a.PatternMakingReceived, convert(varchar,a.PatternCorrectionDate,10) as correctiondate,a.PatternCorrectionDespatch, a.PatternCorrectionReceived, convert(varchar,a.PatternGradingDate,10) as gradingdate, a.PatternGradingDespatch, a.PatternGradingReceived,convert(varchar,a.PatternMarkingDate,10) as marking, a.PatternMarkingDespatch, a.PatternMarkingReceived, a.FeedbackComments,a.POStatus from TbSampleCadInfo a where a.sampleCommentId='"+id+"'";
			System.out.println(" check duplicate buyer query ");

			List<?> list = session.createSQLQuery(sql).list();


			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				sampless.add(new SampleCadAndProduction(element[0].toString(),element[1].toString(),element[2].toString(),element[3].toString(),element[4].toString(),element[5].toString(),element[6].toString(),element[7].toString(),element[8].toString(),element[9].toString(),element[10].toString(),element[11].toString(),element[12].toString(),element[13].toString(),element[14].toString(),element[15].toString(),element[16].toString(),element[17].toString(),element[18].toString(),element[19].toString(),element[20].toString()));

			}



			tx.commit();


		}
		catch(Exception e){
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}

		}

		finally {
			session.close();
		}
		return sampless;
	}

	@Override
	public boolean editSampleCad(SampleCadAndProduction s) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();


			String sql="update TbSampleCadInfo set StyleId='"+s.getStyleId()+"', PurchaseOrder='"+POId(s.getPurchaseOrder())+"', "
					+ "ItemId='"+s.getItemId()+"', "
					+ "ColorId='"+s.getColorId()+"', "
					+ "Size='"+s.getSizeid()+"', "
					+ "SampleTypeId='"+s.getSampleTypeId()+"',"
					+ " PatternMakingDate='"+s.getPatternMakingDate()+"', "
					+ "PatternMakingDespatch='"+s.getPatternMakingDespatch()+"',"
					+ " PatternMakingReceived='"+s.getPatternMadingReceived()+"',"
					+ " PatternCorrectionDate='"+s.getPatternCorrectionDate()+"', "
					+ "PatternCorrectionDespatch='"+s.getPatternCorrectionDespatch()+"', "
					+ " PatternCorrectionReceived='"+s.getPatternCorrectionReceived()+"',"
					+ " PatternGradingDate='"+s.getPatternGradingDate()+"', "
					+ "PatternGradingDespatch='"+s.getPatternGradingDespatch()+"', "
					+ "PatternGradingReceived='"+s.getPatternGradingReceived()+"',"
					+ " PatternMarkingDate='"+s.getPatternMarkingDate()+"', "
					+ "PatternMarkingDespatch='"+s.getPatternMarkingDespatch()+"', "
					+ "PatternMarkingReceived='"+s.getPatternMarkingReceived()+"', "
					+ " FeedbackComments='"+s.getFeedbackComments()+"',"
					+ " POStatus='"+s.getPOStatus()+"' where samplecommentid='"+s.getSampleCommentId()+"'";

			session.createSQLQuery(sql).executeUpdate();


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
	public List<PurchaseOrder> getPurchaseOrderApprovalList(String fromDate, String toDate,String itemType,String approveType) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		PurchaseOrder tempPo;
		List<PurchaseOrder> dataList=new ArrayList<PurchaseOrder>();
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="";

			if(approveType.equals("0")) {
				if(itemType.equals(String.valueOf(ItemType.FABRICS.getType()))) {
					sql=" select purchaseOrder,fabI.styleId,sc.StyleNo,supplierid,s.name,fabI.pono,'Fabrics' as type,(select convert(varchar,pos.orderDate,103))as orderDate,'0' as mdapproval,count(purchaseOrder) as qty \r\n" + 
							"from tbFabricsIndent fabI\r\n" + 
							"left join TbStyleCreate sc\r\n" + 
							"on fabI.styleId =sc.StyleId\r\n" + 
							"left join tbSupplier s\r\n" + 
							"on fabi.supplierid = s.id\r\n" + 
							"left join tbPurchaseOrderSummary pos\r\n" + 
							"on fabI.pono = pos.pono \r\n" + 
							"where fabI.pono is not null and pos.orderDate between '"+fromDate+"' and '"+toDate+"' and (mdapproval is null or mdapproval=0)\r\n" + 
							"group by purchaseOrder,fabI.styleId,styleNo,supplierId,name,fabI.pono,pos.orderDate\r\n" + 
							"order by fabI.pono desc";
				}else if(itemType.equals(String.valueOf(ItemType.ACCESSORIES.getType()))) {
					sql = "select purchaseOrder,accI.styleId,sc.StyleNo,supplierid,s.name,accI.pono,'Accessories' as type,(select convert(varchar,pos.orderDate,103))as orderDate,'0' as mdapproval,count(purchaseOrder) as qty \r\n" + 
							"from tbAccessoriesIndent accI\r\n" + 
							"left join TbStyleCreate sc\r\n" + 
							"on acci.styleId =sc.StyleId\r\n" + 
							"left join tbSupplier s\r\n" + 
							"on acci.supplierid = s.id\r\n" + 
							"left join tbPurchaseOrderSummary pos\r\n" + 
							"on accI.pono = pos.pono \r\n" + 
							"where accI.pono is not null and pos.orderDate between '"+fromDate+"' and '"+toDate+"' and (mdapproval is null or mdapproval=0)\r\n" + 
							"group by purchaseOrder,acci.styleId,styleNo,supplierId,name,accI.pono,pos.orderDate\r\n" + 
							"order by accI.pono desc";
				}
			}else {
				if(itemType.equals(String.valueOf(ItemType.FABRICS.getType()))) {
					sql=" select purchaseOrder,fabI.styleId,sc.StyleNo,supplierid,s.name,fabI.pono,'Fabrics' as type,(select convert(varchar,pos.orderDate,103))as orderDate,'1' as mdapproval,count(purchaseOrder) as qty \r\n" + 
							"from tbFabricsIndent fabI\r\n" + 
							"left join TbStyleCreate sc\r\n" + 
							"on fabI.styleId =sc.StyleId\r\n" + 
							"left join tbSupplier s\r\n" + 
							"on fabi.supplierid = s.id\r\n" + 
							"left join tbPurchaseOrderSummary pos\r\n" + 
							"on fabI.pono = pos.pono \r\n" + 
							"where fabI.pono is not null and pos.orderDate between '"+fromDate+"' and '"+toDate+"' and  mdapproval = 1 \r\n" + 
							"group by purchaseOrder,fabI.styleId,styleNo,supplierId,name,fabI.pono,pos.orderDate\r\n" + 
							"order by fabI.pono desc";
				}else if(itemType.equals(String.valueOf(ItemType.ACCESSORIES.getType()))) {
					sql = "select purchaseOrder,accI.styleId,sc.StyleNo,supplierid,s.name,accI.pono,'Accessories' as type,(select convert(varchar,pos.orderDate,103))as orderDate,'1' as mdapproval,count(purchaseOrder) as qty \r\n" + 
							"from tbAccessoriesIndent accI\r\n" + 
							"left join TbStyleCreate sc\r\n" + 
							"on acci.styleId =sc.StyleId\r\n" + 
							"left join tbSupplier s\r\n" + 
							"on acci.supplierid = s.id\r\n" + 
							"left join tbPurchaseOrderSummary pos\r\n" + 
							"on accI.pono = pos.pono \r\n" + 
							"where accI.pono is not null and pos.orderDate between '"+fromDate+"' and '"+toDate+"' and  mdapproval=1 \r\n" + 
							"group by purchaseOrder,acci.styleId,styleNo,supplierId,name,accI.pono,pos.orderDate\r\n" + 
							"order by accI.pono desc";
				}
			}


			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				tempPo = new PurchaseOrder(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString(), element[7].toString(), Integer.valueOf(element[8].toString()));
				dataList.add(tempPo);
			}

			tx.commit();
		}
		catch(Exception e){
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}

		}
		finally {
			session.close();
		}
		return dataList;
	}

	@Override
	public boolean purchaseOrderApproveConfirm(List<PurchaseOrder> purchaseOrderList) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;



		try{
			tx=session.getTransaction();
			tx.begin();

			String sql = "";
			int length = purchaseOrderList.size();
			for (PurchaseOrder purchaseOrder : purchaseOrderList) {
				if(purchaseOrder.getType().equals("Fabrics")) {
					sql="update tbFabricsIndent set mdapproval='"+purchaseOrder.getMdApproval()+"' where pono='"+purchaseOrder.getPoNo()+"' and PurchaseOrder='"+purchaseOrder.getPurchaseOrder()+"' and styleid='"+purchaseOrder.getStyleId()+"' and supplierid='"+purchaseOrder.getSupplierId()+"'";
				}else if(purchaseOrder.getType().equals("Accessories")) {
					sql="update tbAccessoriesIndent set mdapproval='"+purchaseOrder.getMdApproval()+"' where pono='"+purchaseOrder.getPoNo()+"' and PurchaseOrder='"+purchaseOrder.getPurchaseOrder()+"' and styleid='"+purchaseOrder.getStyleId()+"' and supplierid='"+purchaseOrder.getSupplierId()+"'";
				}
				session.createSQLQuery(sql).executeUpdate();
			}

			tx.commit();

			return true;
		}
		catch(Exception e){
			e.printStackTrace();
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
	public List<ProductionPlan> getSampleProduction(String sampleCommentId, String operatorId,String date) {
		List<ProductionPlan> ListData=new ArrayList<ProductionPlan>();

		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		ProductionPlan tempPlan = null;
		try{
			tx=session.getTransaction();
			tx.begin();
			System.out.println("it's ok DAO");

			String sql="select AutoId,BuyerId,BuyerOrderId,PurchaseOrder,StyleId,LineId,EmployeeId,Type,Hours,hour1,hour2,hour3,hour4,hour5,hour6,hour7,hour8,hour9,hour10,hour11,hour12 "
					+ "from tbLayoutPlanDetails lpd "
					+ "where lpd.lineId='"+sampleCommentId+"' and lpd.date = '"+date+"'  and (lpd.Type = '"+ProductionType.SAMPLE_PRODUCTION.getType()+"' or lpd.Type = '"+ProductionType.SAMPLE_PASS.getType()+"') ";

			List<?> list = session.createSQLQuery(sql).list();

			int lineCount=list.size();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				tempPlan = new ProductionPlan();
				tempPlan.setProudctionType(element[7].toString());
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
				tempPlan.setHour11(element[19].toString());
				tempPlan.setHour12(element[20].toString());


				ListData.add(tempPlan);

			}







			tx.commit();
		}
		catch(Exception ee){
			ee.printStackTrace();
			if (tx != null) {
				ee.printStackTrace();
			}

		}

		finally {
			session.close();
		}

		return ListData;
	}

	@Override
	public boolean ConfirmCheckList(CheckListModel checkList) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="insert into tbAccCheck (buyerid,sampleType,remarks,entrytime,userId) \n" + 
					"values ('"+checkList.getBuyerId()+"',"
					+ "'"+checkList.getSampleId()+"',"
					+ "'"+checkList.getRemarks()+"',"
					+ "CURRENT_TIMESTAMP,"
					+ "'"+checkList.getUserId()+"');";
			session.createSQLQuery(sql).executeUpdate();

			sql = "select isnull(max(autoid),0) as maxId from tbAccCheck";
			List<?> list = session.createSQLQuery(sql).list();
			String checkId="0";
			if(list.size()>0) {
				checkId = list.get(0).toString();
			}
			JSONParser jsonParser = new JSONParser();
			JSONObject itemsObject = (JSONObject)jsonParser.parse(checkList.getCheckListItems());
			JSONArray itemList = (JSONArray) itemsObject.get("list");

			for(int i=0;i<itemList.size();i++) {
				JSONObject item = (JSONObject) itemList.get(i);
				sql="insert into tbAccCheckDetails(checkListId,buyerId,styleId,purchaseOrderId,sizeId,colorId,sampleId,itemType,itemId,quantity,status,entryTime,userId) \n" + 
						"values('"+checkId+"','"+item.get("buyerId")+"','"+item.get("styleId")+"','"+item.get("purchaseOrderId")+"','"+item.get("sizeId")+"','"+item.get("colorId")+"','"+item.get("sampleId")+"','"+item.get("itemType")+"','"+item.get("accItemId")+"','"+item.get("quantity")+"','"+item.get("status")+"',CURRENT_TIMESTAMP,'"+item.get("userId")+"');";
				session.createSQLQuery(sql).executeUpdate();
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
	public List<CheckListModel> getChekList() {
		// TODO Auto-generated method stub
		String countryname="";
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<CheckListModel> checkList=new ArrayList<>();

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select ac.autoId,ac.buyerId,b.name as buyerName,ac.sampleType,sti.Name sampeTypeName,ac.userid \n" + 
					"from tbAccCheck ac \n" + 
					"left join tbBuyer b \n" + 
					"on ac.buyerId = b.id\n" + 
					"left join TbSampleTypeInfo sti\n" + 
					"on ac.sampleType = sti.AutoId"; 

			List<?> list = session.createSQLQuery(sql).list();

			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				checkList.add(new CheckListModel(element[0].toString(), element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(),  element[4].toString(), "","", element[5].toString()));

			}
			tx.commit();
		}
		catch(Exception e){
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}

		}

		finally {
			session.close();
		}
		return checkList;
	}

	@Override
	public CheckListModel getCheckListInfo(String autoId) {
		// TODO Auto-generated method stub
		String countryname="";
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		CheckListModel checkList = null;

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select ac.autoId,ac.buyerId,ac.sampleType,ac.remarks\n" + 
					"from tbAccCheck ac \n" + 
					"where ac.autoId='"+autoId+"'";
			List<?> list = session.createSQLQuery(sql).list();

			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				checkList = new CheckListModel(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString());
			}
			tx.commit();
		}
		catch(Exception e){
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}

		}

		finally {
			session.close();
		}
		return checkList;
	}

	@Override
	public List<CheckListModel> getCheckListItems(String autoId) {
		// TODO Auto-generated method stub
		String countryname="";
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		CheckListModel checkListItem = null;
		List<CheckListModel> itemList=new ArrayList<>();

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select acd.autoId,ac.buyerId,b.name as buyerName,acd.styleId,acd.purchaseOrderId,acd.colorId,isnull(c.Colorname,'')as colorName,acd.sizeId,isnull(ss.sizeName,'')as sizeName,acd.sampleId,acd.itemType,acd.itemId,isnull(fi.ItemName,'') as ItemName,acd.quantity,acd.status,ac.remarks \n" + 
					"from tbAccCheck ac\n" + 
					"left join tbAccCheckDetails acd\n" + 
					"on ac.autoId = acd.checkListId\n" + 
					"left join tbBuyer b\n" + 
					"on ac.buyerId = b.id\n" + 
					"left join TbFabricsItem fi\n" + 
					"on acd.itemId = fi.id\n" + 
					"left join tbColors c\n" + 
					"on acd.colorId = c.ColorId\n" + 
					"left join tbStyleSize ss\n" + 
					"on acd.sizeId = ss.id\n" + 
					"where acd.itemType='1' and ac.autoId= '"+autoId+"'";		
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				checkListItem =  new CheckListModel(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), "", element[4].toString(), "", element[5].toString(), element[6].toString(), element[7].toString(), element[8].toString(), element[9].toString(), "", element[10].toString(), element[11].toString(), element[12].toString(), element[13].toString(), element[14].toString());

				itemList.add(checkListItem);
			}

			sql="select ac.autoId,ac.buyerId,b.name,acd.styleId,acd.purchaseOrderId,acd.colorId,isnull(c.Colorname,'') as colorName,acd.sizeId,isnull(ss.sizeName,'') as sizeName,acd.sampleId,acd.itemType,acd.itemId,isnull(ai.itemname,'')as itemName,acd.quantity,acd.status,ac.remarks \n" + 
					"from tbAccCheck ac\n" + 
					"left join tbAccCheckDetails acd\n" + 
					"on ac.autoId = acd.checkListId\n" + 
					"left join tbBuyer b\n" + 
					"on ac.buyerId = b.id\n" + 
					"left join TbAccessoriesItem ai\n" + 
					"on acd.itemId = ai.itemid\n" + 
					"left join tbColors c\n" + 
					"on acd.colorId = c.ColorId\n" + 
					"left join tbStyleSize ss\n" + 
					"on acd.sizeId = ss.id\n" + 
					"where acd.itemType='2' and ac.autoId= '"+autoId+"'";		
			list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				checkListItem =  new CheckListModel(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), "", element[4].toString(), "", element[5].toString(), element[6].toString(), element[7].toString(), element[8].toString(), element[9].toString(), "", element[10].toString(), element[11].toString(), element[12].toString(), element[13].toString(), element[14].toString());

				itemList.add(checkListItem);
			}
			tx.commit();
		}
		catch(Exception e){
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}		
		}

		finally {
			session.close();
		}
		return itemList;
	}

	@Override
	public boolean editCheckList(CheckListModel checkList) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql = "update tbAccCheck set buyerId='"+checkList.getBuyerId()+"',sampleType='"+checkList.getSampleId()+"',remarks='"+checkList.getRemarks()+"',entryTime=CURRENT_TIMESTAMP,userId='"+checkList.getUserId()+"' where autoId='"+checkList.getCheckListId()+"';";	
			session.createSQLQuery(sql).executeUpdate();

			JSONParser jsonParser = new JSONParser();
			JSONObject itemsObject = (JSONObject)jsonParser.parse(checkList.getCheckListItems());
			JSONArray itemList = (JSONArray) itemsObject.get("list");

			for(int i=0;i<itemList.size();i++) {
				JSONObject item = (JSONObject) itemList.get(i);
				sql="insert into tbAccCheckDetails(checkListId,buyerId,styleId,purchaseOrderId,sizeId,colorId,sampleId,itemType,itemId,quantity,status,entryTime,userId) \n" + 
						"values('"+checkList.getCheckListId()+"','"+item.get("buyerId")+"','"+item.get("styleId")+"','"+item.get("purchaseOrderId")+"','"+item.get("sizeId")+"','"+item.get("colorId")+"','"+item.get("sampleId")+"','"+item.get("itemType")+"','"+item.get("accItemId")+"','"+item.get("quantity")+"','"+item.get("status")+"',CURRENT_TIMESTAMP,'"+item.get("userId")+"');";
				session.createSQLQuery(sql).executeUpdate();
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
	public boolean editCheckListItem(CheckListModel checkList) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql = "update tbAccCheckDetails set buyerId='"+checkList.getBuyerId()+"',styleId='"+checkList.getStyleId()+"',purchaseOrderId='"+checkList.getPurchaseOrderId()+"',sizeId = '"+checkList.getSizeId()+"',colorId='"+checkList.getColorId()+"',sampleId ='"+checkList.getSampleId()+"',itemType='"+checkList.getItemType()+"',itemId='"+checkList.getAccItemId()+"',quantity='"+checkList.getQuantity()+"',status='"+checkList.getStatus()+"' where autoId = '"+checkList.getAutoId()+"'";	
			session.createSQLQuery(sql).executeUpdate();
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


	// Create by Arman

	@Override
	public List<CommonModel> departmentWiseReceiver(String deptId) {
		// TODO Auto-generated method stub
		String sql = "";
		Session session = HibernateUtil.openSession();
		Transaction tx = null;
		List<CommonModel> dataList = new ArrayList<CommonModel>();
		try {

			tx = session.getTransaction();
			tx.begin();

			//sql = "select EmployeeCode,Name from tbemployeeinfo where departmentid='"+deptId+"' order by EmployeeCode";
			sql="select id,username from tblogin where departmentId='"+deptId+"' order by id";
			List<?> list = session.createSQLQuery(sql).list();
			for (Iterator<?> iter = list.iterator(); iter.hasNext();) {

				Object[] element = (Object[]) iter.next();

				dataList.add(new CommonModel(element[0].toString(), element[1].toString(),"",""));

			}
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		return dataList;
	}

	@Override
	public boolean saveFileAccessDetails(CommonModel v) {
		// TODO Auto-generated method stub
		System.err.println("hi : "+v.getType());
		Session session = HibernateUtil.openSession();
		Transaction tx = null;
		String sql = "";
		String slno="";
		try {

			tx = session.getTransaction();
			tx.begin();

			sql="select max(autoid) as uploadfileid from tbuploadfileloginfo";
			List<?> list = session.createSQLQuery(sql).list();
			String uploadfileid = list.get(0).toString();

			sql="delete from tbuploadfilelogdetails where uploadedfileid='"+uploadfileid+"' ";
			session.createSQLQuery(sql).executeUpdate();

			if(v.getType()!=0) {
				for (int i = 0; i < v.getEmpCode().length; i++) {
					sql = "insert into tbuploadfilelogdetails (uploadedfileid, dept, alloweduser, datetime, entryby) values ('"+uploadfileid+"','"+v.getDept()+"','"+v.getEmpCode()[i]+"',CURRENT_TIMESTAMP,'"+v.getUserId()+"')";
					session.createSQLQuery(sql).executeUpdate();
				}
			}else {
				sql = "insert into tbuploadfilelogdetails (uploadedfileid, dept, alloweduser, datetime, entryby) values ('"+uploadfileid+"','"+v.getDept()+"','',CURRENT_TIMESTAMP,'"+v.getUserId()+"')";
				session.createSQLQuery(sql).executeUpdate();
			}

			tx.commit();
			return true;

		} catch (Exception ee) {
			if (tx != null) {
				tx.rollback();
				return false;
			}
			ee.printStackTrace();
		} finally {
			session.close();
		}
		return false;
	}

	@Override
	public List<CommonModel> getAllFromFileLogDetails(CommonModel v) {
		// TODO Auto-generated method stub
		String sql = "";
		Session session = HibernateUtil.openSession();
		Transaction tx = null;
		List<CommonModel> dataList = new ArrayList<CommonModel>();
		try {

			tx = session.getTransaction();
			tx.begin();

			//sql = "select dept, (select isnull((alloweduser+','),'') from tbuploadfilelogdetails FOR XML PATH('')) as emp from tbuploadfilelogdetails GROUP BY dept";
			//sql = " select dept, (select isnull(CAST((alloweduser+',') as VARCHAR(50)),'') from tbuploadfilelogdetails FOR XML PATH('')) as emp from tbuploadfilelogdetails GROUP BY dept ";
			//			sql="select dept,alloweduser from tbuploadfilelogdetails where uploadedfileid='"+v.getId()+"'";
			sql="select a.dept,a.alloweduser,(select b.DepartmentName from TbDepartmentInfo b where b.departmentid=a.dept) as DepartmentName,isnull((select b.username from Tblogin b where b.id=a.alloweduser),'') as username from tbuploadfilelogdetails a where a.uploadedfileid='"+v.getId()+"' ";
			List<?> list = session.createSQLQuery(sql).list();
			for (Iterator<?> iter = list.iterator(); iter.hasNext();) {

				Object[] element = (Object[]) iter.next();

				dataList.add(new CommonModel(element[0].toString(), element[1].toString(),element[2].toString(),element[3].toString(),""));

			}
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		return dataList;
	}

	@Override
	public boolean addNewPermission(CommonModel v) {
		// TODO Auto-generated method stub
		Session session = HibernateUtil.openSession();
		Transaction tx = null;
		String sql = "";
		try {

			tx = session.getTransaction();
			tx.begin();

			sql="delete from tbuploadfilelogdetails where uploadedfileid='"+v.getId()+"' ";
			session.createSQLQuery(sql).executeUpdate();

			if(v.getType()!=0) {
				for (int i = 0; i < v.getEmpCode().length; i++) {
					sql = "insert into tbuploadfilelogdetails (uploadedfileid, dept, alloweduser, datetime, entryby) values ('"+v.getId()+"','"+v.getDept()+"','"+v.getEmpCode()[i]+"',CURRENT_TIMESTAMP,'"+v.getUserId()+"')";
					session.createSQLQuery(sql).executeUpdate();
				}
			}else {

				sql = "insert into tbuploadfilelogdetails (uploadedfileid, dept, alloweduser, datetime, entryby) values ('"+v.getId()+"','"+v.getDept()+"','',CURRENT_TIMESTAMP,'"+v.getUserId()+"')";
				session.createSQLQuery(sql).executeUpdate();
			}

			tx.commit();
			return true;

		} catch (Exception ee) {
			if (tx != null) {
				tx.rollback();
				return false;
			}
			ee.printStackTrace();
		} finally {
			session.close();
		}
		return false;
	}

	@Override
	public List<Style> images(Style style) {
		//ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] fileBytes = null;
		List<Style>ImageList=new ArrayList<>();
		List<MerchandiserInfo>list=new ArrayList<>();

		try {

			//Response response=null;
			SpringRootConfig sp=new SpringRootConfig();
			Connection con = null;
			PreparedStatement ps = null;
			con = sp.getConnection();
			String sql="select frontpic, backpic from tbStyleWiseItem where id='"+style.getStyleItemAutoId()+"' ";

			System.out.println(" merchediser list query "+sql);
			ps =con.prepareStatement(sql)  ;

			int a=0;
			ResultSet result = ps.executeQuery();
			if (result.next()) {
				a++;
				System.out.println(" a increament "+a);

				//fileBytes = result.getBytes("signature");
				ImageList.add(new Style(result.getBytes("frontpic"),result.getBytes("backpic")));





			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ImageList;
	}

	@Override
	public boolean editStyle(String styleItemAutoId, String buyerId, String itemId, String styleid, String styleNo,
			String size, String date, MultipartFile frontImage, MultipartFile backImage) {

		SpringRootConfig sp=new SpringRootConfig();
		try {
			boolean frontimgexists=false;
			boolean backimgexists=false;

			if(!CheckStyleAlreadyExist(buyerId,styleNo,styleid)) {

				String sql="update TbStyleCreate set BuyerId='"+buyerId+"',StyleNo='"+styleNo+"',date='"+date+"' where styleid='"+styleid+"'";
				System.out.println(sql);
				sp.getDataSource().getConnection().createStatement().executeUpdate(sql);


				String sqlStyleItem="update tbStyleWiseItem set BuyerId='"+buyerId+"',ItemId='"+itemId+"',size='"+size+"' where styleid='"+styleid+"' and id='"+styleItemAutoId+"'";
				System.out.println(sqlStyleItem);
				sp.getDataSource().getConnection().createStatement().executeUpdate(sqlStyleItem);


				byte[] photoBytes = frontImage.getBytes();

				if(photoBytes.length!=0) {
					String sql1 = "update tbStyleWiseItem set frontpic = ? where StyleId= '"+styleid+"'  and id='"+styleItemAutoId+"'";
					System.out.println(" frontimage update "+sql1);
					java.sql.PreparedStatement pstmt=sp.getDataSource().getConnection().prepareStatement(sql1);
					ByteArrayInputStream bais = new ByteArrayInputStream(photoBytes);
					pstmt.setBinaryStream(1, bais, photoBytes.length);
					pstmt.executeUpdate();
					pstmt.close();

				}

				photoBytes = backImage.getBytes();

				if(photoBytes.length!=0) {
					String sql1 = "update tbStyleWiseItem set backpic = ? where StyleId= '"+styleid+"'  and id='"+styleItemAutoId+"'";
					System.out.println(" backimage update "+sql1);
					java.sql.PreparedStatement pstmt=sp.getDataSource().getConnection().prepareStatement(sql1);
					ByteArrayInputStream  bais = new ByteArrayInputStream(photoBytes);
					pstmt.setBinaryStream(1, bais, photoBytes.length);
					pstmt.executeUpdate();
					pstmt.close();
				}
			}

			sp.getDataSource().close();

			return true;


		} catch (Exception e) {
			System.out.println("Error,"+e.getMessage());
		}

		return false;
	}

	@Override
	public boolean SaveStyleCreate(String user, String buyerId, String itemId, String styleNo,String size, String date,
			MultipartFile frontimg, MultipartFile backimg) throws SQLException{
		SpringRootConfig sp=new SpringRootConfig();
		try {
			boolean frontimgexists=false;
			boolean backimgexists=false;
			String StyleId="";
			if(!CheckStyleAlreadyExist(buyerId,styleNo,StyleId)) {
				StyleId=getMaxStyleId();
				String sql="insert into TbStyleCreate (StyleId,BuyerId,StyleNo,Finished,date,EntryTime,UserId) values('"+StyleId+"','"+buyerId+"','"+styleNo+"','0',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'"+user+"');";
				System.out.println(sql);
				sp.getDataSource().getConnection().createStatement().executeUpdate(sql);

				StringTokenizer token=new StringTokenizer(itemId,",");
				while(token.hasMoreTokens()) {
					String itemIdValue=token.nextToken();
					String sqlStyleItem="insert into tbStyleWiseItem (StyleId,BuyerId,ItemId,size,EntryTime,UserId) values('"+StyleId+"','"+buyerId+"','"+itemIdValue+"','"+size+"',CURRENT_TIMESTAMP,'"+user+"');";
					System.out.println(sqlStyleItem);
					sp.getDataSource().getConnection().createStatement().executeUpdate(sqlStyleItem);
				}

			}


			byte[] photoBytes = frontimg.getBytes();
			if(photoBytes.length!=0) {		

				String sql1 = "update tbStyleWiseItem set frontpic = ? where StyleId= '"+StyleId+"' and BuyerId='"+buyerId+"'";
				System.out.println(" frontimage update "+sql1);
				java.sql.PreparedStatement pstmt=sp.getDataSource().getConnection().prepareStatement(sql1);
				ByteArrayInputStream bais = new ByteArrayInputStream(photoBytes);
				//pstmt.setString(1, txtSl.getText().trim());
				pstmt.setBinaryStream(1, bais, photoBytes.length);
				pstmt.executeUpdate();
				pstmt.close();


			}

			photoBytes = backimg.getBytes();
			if(photoBytes.length!=0) {

				String sql1 = "update tbStyleWiseItem set backpic = ? where StyleId= '"+StyleId+"' and BuyerId='"+buyerId+"'";
				System.out.println(" backimage update "+sql1);
				java.sql.PreparedStatement pstmt=sp.getDataSource().getConnection().prepareStatement(sql1);
				ByteArrayInputStream bais = new ByteArrayInputStream(photoBytes);
				//pstmt.setString(1, txtSl.getText().trim());
				pstmt.setBinaryStream(1, bais, photoBytes.length);
				pstmt.executeUpdate();
				pstmt.close();

			}

			sp.getDataSource().close();

			return true;


		} catch (Exception e) {
			System.out.println("Error,"+e.getMessage());
		}


		return false;
	}

	@Override
	public boolean deleteSampleRequisitionItem(String sapleAutoId) {
		Session session = HibernateUtil.openSession();
		Transaction tx = null;
		String sql = "";
		try {

			tx = session.getTransaction();
			tx.begin();

			int confrim=0;
			sql="select sampleAutoId from TbSampleRequisitionDetails where sampleAutoId='"+sapleAutoId+"' and sampleReqId IS NOT NULL";
			List<?> list = session.createSQLQuery(sql).list();
			for (Iterator<?> iter = list.iterator(); iter.hasNext();) {
				confrim=1;
				break;
			}
			
			if(confrim==0) {
				sql="delete from TbSampleRequisitionDetails where sampleAutoId='"+sapleAutoId+"' ";
				session.createSQLQuery(sql).executeUpdate();
				
				sql="delete from tbSizeValues where type='2' and linkedAutoId='"+sapleAutoId+"'";
				session.createSQLQuery(sql).executeUpdate();
				tx.commit();
				return true;

			}


		} catch (Exception ee) {
			if (tx != null) {
				tx.rollback();
				return false;
			}
			ee.printStackTrace();
		} finally {
			session.close();
		}
		return false;
	}

	@Override
	public List<SampleRequisitionItem> getSampleRequistionItemData(String SampleAutoId) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<SampleRequisitionItem> dataList=new ArrayList<SampleRequisitionItem>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select ISNULL(srd.SampleReqId,0) as SampleReqId,srd.SampleTypeId,srd.BuyerId,srd.sampleAutoId,srd.StyleId,sc.StyleNo,srd.ItemId,id.itemname,srd.ColorId,c.Colorname,srd.PurchaseOrder,srd.sizeGroupId,srd.userId \r\n" + 
					"					from TbSampleRequisitionDetails srd\r\n" + 
					"					left join TbStyleCreate sc\r\n" + 
					"					on srd.StyleId = sc.StyleId\r\n" + 
					"					left join tbItemDescription id\r\n" + 
					"					on srd.ItemId = id.itemid\r\n" + 
					"					left join tbColors c\r\n" + 
					"					on srd.ColorId = c.ColorId\r\n" + 
					"					where srd.sampleAutoId='"+SampleAutoId+"' order by sizeGroupId";
			System.out.println(sql);
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();							
				dataList.add(new SampleRequisitionItem(element[0].toString(),element[1].toString(),element[2].toString(), element[3].toString(),element[4].toString(), element[5].toString(), element[6].toString(), element[7].toString(), element[8].toString(), element[9].toString(),element[10].toString(),element[11].toString(),element[12].toString()));
			}

			for (SampleRequisitionItem sampleReqItem : dataList) {
				sql = "select bs.sizeGroupId,bs.sizeId,ss.sizeName,bs.sizeQuantity from tbSizeValues bs\r\n" + 
						"join tbStyleSize ss \r\n" + 
						"on ss.id = bs.sizeId \r\n" + 
						"where bs.linkedAutoId = '"+sampleReqItem.getAutoId()+"' and bs.type='"+SizeValuesType.SAMPLE.getType()+"' and bs.sizeGroupId = '"+sampleReqItem.getSizeGroupId()+"' \r\n" + 
						"order by ss.sortingNo";
				System.out.println(sql);
				List<?> list2 = session.createSQLQuery(sql).list();
				ArrayList<Size> sizeList=new ArrayList<Size>();
				for(Iterator<?> iter = list2.iterator(); iter.hasNext();)
				{	
					Object[] element = (Object[]) iter.next();	
					sizeList.add(new Size(element[0].toString(),element[1].toString(), element[3].toString()));
				}
				sampleReqItem.setSizeList(sizeList);
			}
			tx.commit();
		}
		catch(Exception e){
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			
		}
		finally {
			session.close();
		}
		return dataList;
		
	}

	@Override
	public boolean editItemToSampleRequisition(SampleRequisitionItem v) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="";
			sql="delete from tbSizeValues where type='2' and linkedAutoId='"+v.getAutoId()+"'";
			session.createSQLQuery(sql).executeUpdate();
			
			sql="update TbSampleRequisitionDetails set BuyerId='"+v.getBuyerId()+"',buyerOrderId='"+v.getBuyerOrderId()+"' ,purchaseOrder='"+v.getPurchaseOrder()+"',StyleId='"+v.getStyleId()+"',ItemId='"+v.getItemId()+"',ColorId='"+v.getColorId()+"',SampleTypeId='"+v.getSampleId()+"',sizeGroupId='"+v.getSizeGroupId()+"',EntryTime=CURRENT_TIMESTAMP,UserId='"+v.getUserId()+"' where sampleAutoId='"+v.getAutoId()+"';";
			session.createSQLQuery(sql).executeUpdate();
			
			int listSize=v.getSizeList().size();
			for(int i=0;i<listSize;i++) {
				sql = "insert into tbSizeValues (linkedAutoId,sizeGroupId,sizeId,sizeQuantity,type,entryTime,userId) values('"+v.getAutoId()+"','"+v.getSizeGroupId()+"','"+v.getSizeList().get(i).getSizeId()+"','"+v.getSizeList().get(i).getSizeQuantity()+"','"+SizeValuesType.SAMPLE.getType()+"',CURRENT_TIMESTAMP,'"+v.getUserId()+"');";
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
	public List<SampleCadAndProduction> getSampleCadDetails(String sampleCommentId) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<SampleCadAndProduction> dataList=new ArrayList<SampleCadAndProduction>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select a.SampleCommentId,a.sampleReqId,a.FeedbackComments,a.PatternMakingDate,a.PatternMakingDespatch,a.PatternMakingReceived,a.PatternCorrectionDate,a.PatternCorrectionDespatch,a.PatternCorrectionReceived,a.PatternGradingDate,a.PatternGradingDespatch,a.PatternGradingReceived,a.PatternMarkingDate,a.PatternMarkingDespatch,a.PatternMarkingReceived from TbSampleCadInfo a where a.SampleCommentId='"+sampleCommentId+"' order by PurchaseOrder,StyleId,ItemId,ColorId,SampleTypeId";
			System.out.println(sql);
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();							
				dataList.add(new SampleCadAndProduction(element[0].toString(),element[1].toString(),element[2].toString(), element[3].toString(),element[4].toString(), element[5].toString(), element[6].toString(), element[7].toString(), element[8].toString(), element[9].toString(),element[10].toString(),element[11].toString(),element[12].toString(),element[13].toString(),element[14].toString()));
			}

	
			tx.commit();
		}
		catch(Exception e){
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			
		}
		finally {
			session.close();
		}
		return dataList;
	}


}
