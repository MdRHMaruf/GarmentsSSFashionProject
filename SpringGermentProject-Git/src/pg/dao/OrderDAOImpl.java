package pg.dao;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import pg.registerModel.Unit;
import pg.config.SpringRootConfig;
import pg.model.commonModel;
import pg.orderModel.BuyerPO;
import pg.orderModel.BuyerPoItem;
import pg.orderModel.Costing;
import pg.orderModel.FabricsIndent;
import pg.orderModel.PurchaseOrder;
import pg.orderModel.PurchaseOrderItem;
import pg.orderModel.SampleRequisitionItem;
import pg.orderModel.Style;
import pg.orderModel.AccessoriesIndent;
import pg.orderModel.accessoriesindentcarton;
import pg.registerModel.Color;
import pg.registerModel.ItemDescription;
import pg.registerModel.ParticularItem;
import pg.registerModel.Size;
import pg.registerModel.SizeGroup;
import pg.registerModel.StyleItem;
import pg.share.HibernateUtil;
import pg.share.SizeValuesType;
import pg.registerModel.AccessoriesItem;
@Repository
public class OrderDAOImpl implements OrderDAO{


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
	public boolean SaveStyleCreate(String user, String buyerId, String itemId, String styleNo,String size, String date,
			String frontimg, String backimg) throws SQLException{
		SpringRootConfig sp=new SpringRootConfig();


		try {
			boolean frontimgexists=false;
			boolean backimgexists=false;
			File frontimgfile =new File(frontimg);
			File backimgfile =new File(frontimg);

			if(frontimgfile.exists()) {
				frontimgexists=true;
			}

			if(backimgfile.exists()) {
				backimgexists=true;
			}


			String StyleId="";



			if(!CheckStyleAlreadyExist(buyerId,styleNo)) {
				StyleId=getMaxStyleId();
				String sql="insert into TbStyleCreate (StyleId,BuyerId,StyleNo,Finished,date,EntryTime,UserId) values('"+StyleId+"','"+buyerId+"','"+styleNo+"','0',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'"+user+"');";
				System.out.println(sql);
				sp.getDataSource().getConnection().createStatement().executeUpdate(sql);

				String sqlStyleItem="insert into tbStyleWiseItem (StyleId,BuyerId,ItemId,size,EntryTime,UserId) values('"+StyleId+"','"+buyerId+"','"+itemId+"','"+size+"',CURRENT_TIMESTAMP,'"+user+"');";
				System.out.println(sqlStyleItem);
				sp.getDataSource().getConnection().createStatement().executeUpdate(sqlStyleItem);
			}
			else {
				StyleId=getStyleId(buyerId,styleNo);
				String sqlStyleItem="insert into tbStyleWiseItem (StyleId,BuyerId,ItemId,size,EntryTime,UserId) values('"+StyleId+"','"+buyerId+"','"+itemId+"','"+size+"',CURRENT_TIMESTAMP,'"+user+"');";
				System.out.println(sqlStyleItem);
				sp.getDataSource().getConnection().createStatement().executeUpdate(sqlStyleItem);
			}


			if(frontimgexists) {

				BufferedImage bufferedImage=ImageIO.read(frontimgfile);
				ByteArrayOutputStream byteOutStream=new ByteArrayOutputStream();
				ImageIO.write(bufferedImage, "png", byteOutStream);


				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				//use another encoding if JPG is innappropriate for you
				ImageIO.write(bufferedImage, "jpg", baos );
				baos.flush();
				byte[] immAsBytes = baos.toByteArray();
				baos.close();

				java.sql.PreparedStatement pstmt=sp.getDataSource().getConnection().prepareStatement("update tbStyleWiseItem set frontpic = ? where StyleId= '"+StyleId+"' and BuyerId='"+buyerId+"'");
				ByteArrayInputStream bais = new ByteArrayInputStream(immAsBytes);
				//pstmt.setString(1, txtSl.getText().trim());
				pstmt.setBinaryStream(1, bais, immAsBytes.length);
				pstmt.executeUpdate();
				pstmt.close();
			}

			if(backimgexists) {

				BufferedImage bufferedImage=ImageIO.read(backimgfile);
				ByteArrayOutputStream byteOutStream=new ByteArrayOutputStream();
				ImageIO.write(bufferedImage, "png", byteOutStream);


				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				//use another encoding if JPG is innappropriate for you
				ImageIO.write(bufferedImage, "jpg", baos );
				baos.flush();
				byte[] immAsBytes = baos.toByteArray();
				baos.close();

				java.sql.PreparedStatement pstmt=sp.getDataSource().getConnection().prepareStatement("update tbStyleWiseItem set backpic = ? where StyleId= '"+StyleId+"' and BuyerId='"+buyerId+"'");
				ByteArrayInputStream bais = new ByteArrayInputStream(immAsBytes);
				//pstmt.setString(1, txtSl.getText().trim());
				pstmt.setBinaryStream(1, bais, immAsBytes.length);
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

	private boolean CheckStyleAlreadyExist(String buyerId, String styleNo) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		String query="";

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select StyleId from TbStyleCreate where StyleNo='"+styleNo+"' and BuyerId='"+buyerId+"' and Finished='0'";

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
	public List<Style> getStyleList() {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<Style> datalist=new ArrayList<Style>();	
		try{	
			tx=session.getTransaction();
			tx.begin();		
			String sql="select StyleId,StyleNo from TbStyleCreate order by StyleNo";		
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
	public List<Style> getStyleWiseItemList() {

		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<Style> datalist=new ArrayList<Style>();

		try{	
			tx=session.getTransaction();
			tx.begin();



			String sql="select a.Id,(select StyleNo from TbStyleCreate where StyleId=a.StyleId) as StyleNo,(select itemname from tbItemDescription where itemid=a.ItemId) as ItemName,a.ItemId from tbStyleWiseItem a order by StyleId,BuyerId";

			List<?> list = session.createSQLQuery(sql).list();


			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				datalist.add(new Style(element[0].toString(),element[1].toString(),element[2].toString(),element[3].toString()));

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
	public List<Costing> getCostingList(String styleId, String itemId) {
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
					"where cc.styleId='"+styleId+"' and cc.itemId='"+itemId+"'";

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
	public List<Costing> getCostingList() {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<Costing> datalist=new ArrayList<Costing>();	
		try{	
			tx=session.getTransaction();
			tx.begin();	
			String sql="select cc.StyleId,sc.StyleNo,cc.ItemId,id.itemname,sum(cc.amount) as amount,(select convert(varchar,cc.SubmissionDate,103))as date \r\n" + 
					"from TbCostingCreate cc\r\n" + 
					"left join TbStyleCreate sc\r\n" + 
					"on cc.StyleId = sc.StyleId\r\n" + 
					"left join tbItemDescription id\r\n" + 
					"on cc.ItemId = id.itemid\r\n" + 
					"group by cc.StyleId,sc.StyleNo,cc.ItemId,id.itemname,cc.SubmissionDate";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				datalist.add(new Costing("0", element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), "","","", "", "",0.0, 0.0, 0.0, 0.0, 0.0, Double.valueOf(element[4].toString()), 0.0, element[5].toString(), "0"));				
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



			String sql="insert into TbBuyerOrderEstimateDetails (buyerId,BuyerOrderId,CustomerOrder,PurchaseOrder,ShippingMarks,FactoryId,StyleId,ItemId,ColorId,SizeReg,sizeGroupId,TotalUnit,UnitCmt,TotalPrice,UnitFob,TotalAmount,EntryTime,UserId) "
					+ "values('"+buyerPoItem.getBuyerId()+"','"+buyerPoItem.getBuyerPOId()+"','"+buyerPoItem.getCustomerOrder()+"','"+buyerPoItem.getPurchaseOrder()+"','"+buyerPoItem.getShippingMark()+"','"+buyerPoItem.getFactoryId()+"','"+buyerPoItem.getStyleId()+"','"+buyerPoItem.getItemId()+"','"+buyerPoItem.getColorId()+"','','"+buyerPoItem.getSizeGroupId()+"','"+buyerPoItem.getTotalUnit()+"','"+buyerPoItem.getUnitCmt()+"','"+buyerPoItem.getTotalPrice()+"','"+buyerPoItem.getUnitFob()+"','"+buyerPoItem.getTotalAmount()+"',CURRENT_TIMESTAMP,'"+buyerPoItem.getUserId()+"');";
			session.createSQLQuery(sql).executeUpdate();

			String itemAutoId ="";
			sql="select max(autoId) as itemAutoId from TbBuyerOrderEstimateDetails where BuyerOrderId='"+buyerPoItem.getBuyerPOId()+"' and customerOrder='"+buyerPoItem.getCustomerOrder()+"' and purchaseOrder='"+buyerPoItem.getPurchaseOrder()+"' and userId='"+buyerPoItem.getUserId()+"'";
			List<?> list = session.createSQLQuery(sql).list();
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

			String sql="update TbBuyerOrderEstimateDetails set buyerId='"+buyerPoItem.getBuyerId()+"',BuyerOrderId='"+buyerPoItem.getBuyerPOId()+"',CustomerOrder='"+buyerPoItem.getCustomerOrder()+"',PurchaseOrder='"+buyerPoItem.getPurchaseOrder()+"',ShippingMarks='"+buyerPoItem.getShippingMark()+"',FactoryId='"+buyerPoItem.getShippingMark()+"',StyleId='"+buyerPoItem.getStyleId()+"',ItemId='"+buyerPoItem.getItemId()+"',ColorId='"+buyerPoItem.getColorId()+"',SizeReg='',sizeGroupId='"+buyerPoItem.getSizeGroupId()+"',TotalUnit='"+buyerPoItem.getTotalUnit()+"',UnitCmt='"+buyerPoItem.getUnitCmt()+"',TotalPrice='"+buyerPoItem.getTotalPrice()+"',UnitFob='"+buyerPoItem.getUnitFob()+"',TotalAmount='"+buyerPoItem.getTotalAmount()+"',EntryTime=CURRENT_TIMESTAMP,UserId='"+buyerPoItem.getUserId()+"' where autoId='"+buyerPoItem.getAutoId()+"'";		
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
	public List<BuyerPoItem> getBuyerPOItemList(String buyerPOId) {
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
					"where BuyerOrderId='"+buyerPOId+"' order by sizeGroupId";
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
						"where bs.linkedAutoId = '"+buyerPoItem.getAutoId()+"' and bs.sizeGroupId = '"+buyerPoItem.getSizeGroupId()+"' \r\n" + 
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
					"where bs.linkedAutoId = '"+buyerPoItem.getAutoId()+"' and bs.sizeGroupId = '"+buyerPoItem.getSizeGroupId()+"' \r\n" + 
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

			sql="delete from  tbSizeValues where linkedAutoId='"+itemAutoId+"';";
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
	public boolean editBuyerPO(BuyerPO buyerPo) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="update TbBuyerOrderEstimateSummary set BuyerId='"+buyerPo.getBuyerId()+"',Period='',PaymentTerm='"+buyerPo.getPaymentTerm()+"',Incoterm='',Currency='"+buyerPo.getCurrency()+"',QCFor='',TotalUnit='"+buyerPo.getTotalUnit()+"',UnitCmt='"+buyerPo.getUnitCmt()+"',TotalPrice='"+buyerPo.getTotalPrice()+"',UnitFob='"+buyerPo.getUnitFob()+"',TotalAmount='"+buyerPo.getTotalAmount()+"',note='"+buyerPo.getNote()+"',remarks='"+buyerPo.getRemarks()+"',EntryTime=CURRENT_TIMESTAMP,UserId='"+buyerPo.getUserId()+"' where autoId='"+buyerPo.getBuyerPoId()+"'";		
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
	public List<BuyerPO> getBuyerPoList() {
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
					"order by bos.autoId desc";
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
			buyerPo.setItemList(getBuyerPOItemList(buyerPoNo));

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
	public List<commonModel> PurchaseOrders() {
		// TODO Auto-generated method stub

		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<commonModel> query=new ArrayList<commonModel>();

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select BuyerOrderId,PurchaseOrder from TbBuyerOrderEstimateDetails group by BuyerOrderId,PurchaseOrder";
			System.out.println(" max ");

			List<?> list = session.createSQLQuery(sql).list();


			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				query.add(new commonModel(element[0].toString(),element[1].toString()));

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
	public List<commonModel> Colors(String style, String item) {
		// TODO Auto-generated method stub

		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<commonModel> query=new ArrayList<commonModel>();

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

				query.add(new commonModel(element[0].toString(),element[1].toString()));

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
	public List<commonModel> Items(String buyerorderid,String style) {
		// TODO Auto-generated method stub

		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<commonModel> query=new ArrayList<commonModel>();

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select itemid, isnull((select itemname from tbItemDescription where itemid=a.ItemId),'') as itemname from TbBuyerOrderEstimateDetails a where a.BuyerOrderId='"+buyerorderid+"' and a.styleid='"+style+"' group by a.ItemId";
			//System.out.println(" max ");

			List<?> list = session.createSQLQuery(sql).list();


			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				query.add(new commonModel(element[0].toString(),element[1].toString()));

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
	public List<commonModel> AccessoriesItem(String type) {
		// TODO Auto-generated method stub

		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<commonModel> query=new ArrayList<commonModel>();

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

				query.add(new commonModel(element[0].toString(),element[1].toString()));

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
	public List<commonModel> Size(String buyerorderid, String style, String item, String color) {
		// TODO Auto-generated method stub

		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<commonModel> query=new ArrayList<commonModel>();

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select (select id from tbStyleSize where id=a.sizeId) as id,(select sizename from tbStyleSize where id=a.sizeId) as name from TbBuyerOrderEstimateDetails b, tbSizeValues  a where a.linkedAutoId=b.autoId and b.buyerorderid='"+buyerorderid+"' and b.StyleId='"+style+"' and b.ItemId='"+item+"' and b.colorId='"+color+"'";
			System.out.println(" max ");

			List<?> list = session.createSQLQuery(sql).list();


			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				query.add(new commonModel(element[0].toString(),element[1].toString()));

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
	public List<commonModel> Unit() {
		// TODO Auto-generated method stub

		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<commonModel> query=new ArrayList<commonModel>();

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select cast(unitvalue as Integer) as unitValue,unitName from tbunits";
			System.out.println(" max ");

			List<?> list = session.createSQLQuery(sql).list();


			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				query.add(new commonModel(element[0].toString(),element[1].toString()));

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
	public List<commonModel> Brands() {
		// TODO Auto-generated method stub

		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<commonModel> query=new ArrayList<commonModel>();

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select id, name from tbbrands";
			System.out.println(" max ");

			List<?> list = session.createSQLQuery(sql).list();


			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				query.add(new commonModel(element[0].toString(),element[1].toString()));

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
	public List<commonModel> ShippingMark(String po, String style, String item) {
		// TODO Auto-generated method stub

		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<commonModel> query=new ArrayList<commonModel>();

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select shippingmarks from TbBuyerOrderEstimateDetails where BuyerOrderId='"+po+"' and StyleId='"+style+"' and ItemId='"+item+"' group by shippingmarks";
			System.out.println(" max ");

			List<?> list = session.createSQLQuery(sql).list();


			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				//Object[] element = (Object[]) iter.next();

				query.add(new commonModel("",iter.next().toString()));

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
	public List<commonModel> AllColors() {
		// TODO Auto-generated method stub

		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<commonModel> query=new ArrayList<commonModel>();

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select ColorId, Colorname from tbColors";
			System.out.println(" all colors ");

			List<?> list = session.createSQLQuery(sql).list();


			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				query.add(new commonModel(element[0].toString(),element[1].toString()));

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
	public List<commonModel> SizewiseQty(String buyerorderid,String style,String item,String color,String size) {
		// TODO Auto-generated method stub

		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<commonModel> query=new ArrayList<commonModel>();

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
				query.add(new commonModel(iter.next().toString()));
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
	public boolean insertaccessoriesIndent(AccessoriesIndent ai) {
		// TODO Auto-generated method stub

		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		boolean inserted=false;
		List<commonModel> query=new ArrayList<commonModel>();

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

				//	query.add(new accessorieIndent(element[0].toString(),element[1].toString(),element[2].toString(),element[3].toString(),element[4].toString(),element[5].toString(),element[6].toString(),element[7].toString()));

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
	public List<commonModel> Styles(String po) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<commonModel> query=new ArrayList<commonModel>();

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select styleid, (select StyleNo from TbStyleCreate where StyleId=a.StyleId) as stylename from TbBuyerOrderEstimateDetails a where BuyerOrderId='"+po+"' group by StyleId";
			System.out.println(" max ");

			List<?> list = session.createSQLQuery(sql).list();


			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				query.add(new commonModel(element[0].toString(),element[1].toString()));

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
	public boolean cloningCosting(String oldStyleId, String oldItemId, String newStyleId, String newItemId,
			String userId) {
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
					"where cc.styleId='"+oldStyleId+"' and cc.itemId='"+oldItemId+"'";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				String date[] = element[17].toString().split("/");

				datalist.add(new Costing(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(),element[5].toString(), element[6].toString(), element[7].toString(), element[8].toString(), element[9].toString(), Double.valueOf(element[10].toString()), Double.valueOf(element[11].toString()), Double.valueOf(element[12].toString()),Double.valueOf(element[13].toString()), Double.valueOf(element[14].toString()), Double.valueOf(element[15].toString()), Double.valueOf(element[16].toString()), date[2]+"-"+date[1]+"-"+date[1], element[18].toString()));				
			}

			for (Costing costing : datalist) {
				sql="insert into TbCostingCreate ("
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
						+ "'"+newStyleId+"',"
						+ "'"+newItemId+"',"
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
						+ "'"+userId+"')";
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
	public List<commonModel> styleItemsWiseColor(String buyerorderid,String style,String item) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<commonModel> query=new ArrayList<commonModel>();

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select a.ColorId,(select Colorname from tbColors where ColorId=a.ColorId) as ColorName from TbBuyerOrderEstimateDetails a where a.BuyerOrderId='"+buyerorderid+"' and a.StyleId='"+style+"' and a.ItemId='"+item+"' group by a.ColorId";
			System.out.println(" max ");

			List<?> list = session.createSQLQuery(sql).list();


			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				query.add(new commonModel(element[0].toString(),element[1].toString()));

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

			String sql="select a.AccIndentId,a.PurchaseOrder,(select StyleNo from TbStyleCreate where StyleId=a.StyleId) as StyleNo,(select ItemName from tbItemDescription where itemid=a.ItemId) as ItemName,(select ColorName from tbColors where ColorId=a.ColorId) as ColorName,a.ShippingMarks,(select itemname from TbAccessoriesItem where itemid=a.accessoriesItemId) as AccessoriesName,(select sizeName from tbStyleSize where id=a.size) as SizeName,a.RequireUnitQty from tbAccessoriesIndent a where a.PurchaseOrder='"+po+"' and a.StyleId='"+style+"' and a.ItemId='"+itemname+"' and a.ColorId='"+itemcolor+"'";
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

			String sql="select a.AccIndentId,a.PurchaseOrder,(select StyleNo from TbStyleCreate where StyleId=a.StyleId) as StyleNo,(select ItemName from tbItemDescription where itemid=a.ItemId) as ItemName,(select ColorName from tbColors where ColorId=a.ColorId) as ColorName,a.ShippingMarks,(select itemname from TbAccessoriesItem where itemid=a.accessoriesItemId) as AccessoriesName,(select sizeName from tbStyleSize where id=a.size) as SizeName,a.RequireUnitQty from tbAccessoriesIndent a where a.AINo IS NULL";


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
	public List<AccessoriesIndent> getAccessoriesIndentItemDetails(String id) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<AccessoriesIndent> query=new ArrayList<AccessoriesIndent>();

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select a.AccIndentId,a.PurchaseOrder,a.StyleId,a.ItemId,a.ColorId,a.ShippingMarks,(select itemname from TbAccessoriesItem where itemid=a.accessoriesItemId) as AccessoriesName,a.size,a.accessoriesSize,a.PerUnit,a.TotalBox,a.OrderQty,a.QtyInDozen,a.ReqPerPices,a.ReqPerDoz,a.DividedBy,a.PercentageExtra,a.PercentageExtraQty,a.TotalQty,(select UnitName from tbunits where UnitId=a.UnitId) as UnitName,a.RequireUnitQty,a.IndentBrandId,a.IndentColorId from tbAccessoriesIndent a where a.AccIndentId='"+id+"'";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				query.add(new AccessoriesIndent(element[0].toString(),element[1].toString(),element[2].toString(),element[3].toString(),element[4].toString(),element[5].toString(),element[6].toString(),element[7].toString(),element[8].toString(),element[9].toString(),element[10].toString(),element[11].toString(),element[12].toString(),element[13].toString(),element[14].toString(),element[15].toString(),element[16].toString(),element[17].toString(),element[18].toString(),element[19].toString(),element[20].toString(),element[21].toString(),element[22].toString()));

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
	public boolean editaccessoriesIndent(AccessoriesIndent ai) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<commonModel> query=new ArrayList<commonModel>();

		try{
			tx=session.getTransaction();
			tx.begin();


			String sql="update tbAccessoriesIndent set  PerUnit='"+ai.getPerunit()+"',TotalBox='"+ai.getTotalbox()+"',OrderQty='"+ai.getOrderqty()+"',QtyInDozen='"+ai.getQtyindozen()+"',"
					+ "ReqPerPices='"+ai.getReqperpcs()+"',ReqPerDoz='"+ai.getReqperdozen()+"',DividedBy='"+ai.getDividedby()+"',PercentageExtra='"+ai.getExtrainpercent()+"',PercentageExtraQty='"+ai.getPercentqty()+"',"
					+ "TotalQty='"+ai.getTotalqty()+"',RequireUnitQty='"+ai.getGrandqty()+"',IndentColorId='"+ai.getAccessoriescolor()+"',IndentBrandId='"+ai.getBrand()+"',IndentDate=GETDATE(),IndentTime=GETDATE(),IndentPostBy='"+ai.getUser()+"' where AccIndentId='"+ai.getAutoid()+"'";

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
	public boolean confrimAccessoriesIndent(String user, String aiNo) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<commonModel> query=new ArrayList<commonModel>();

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="update tbAccessoriesIndent set  AINo='"+aiNo+"',IndentPostBy='"+user+"' where AINo IS NULL";
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
	public boolean saveAccessoriesCurton(accessoriesindentcarton v) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		boolean inserted=false;
		List<commonModel> query=new ArrayList<commonModel>();

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
	public List<accessoriesindentcarton> getAccessoriesIndentCarton(String poNo, String style, String item, String itemColor) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<accessoriesindentcarton> query=new ArrayList<accessoriesindentcarton>();

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select a.AccIndentId,a.PurchaseOrder,(select StyleNo from TbStyleCreate where StyleId=a.StyleId) as StyleNo,(select ItemName from tbItemDescription where itemid=a.ItemId) as ItemName,(select ColorName from tbColors where ColorId=a.ColorId) as ColorName,a.ShippingMarks,(select itemname from TbAccessoriesItem where itemid=a.accessoriesItemId) as AccessoriesName, cartonSize,a.Qty from tbAccessoriesIndentForCarton a where a.PurchaseOrder='"+poNo+"' and a.StyleId='"+style+"' and a.ItemId='"+item+"' and a.ColorId='"+itemColor+"'";
			System.out.println(" max ");

			List<?> list = session.createSQLQuery(sql).list();


			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				query.add(new accessoriesindentcarton(element[0].toString(),element[1].toString(),element[2].toString(),element[3].toString(),element[4].toString(),element[5].toString(),element[6].toString(),element[7].toString(),element[8].toString()));

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
	public List<accessoriesindentcarton> getAllAccessoriesCartonData() {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<accessoriesindentcarton> query=new ArrayList<accessoriesindentcarton>();

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select a.AccIndentId,a.PurchaseOrder,(select StyleNo from TbStyleCreate where StyleId=a.StyleId) as StyleNo,(select ItemName from tbItemDescription where itemid=a.ItemId) as ItemName,(select ColorName from tbColors where ColorId=a.ColorId) as ColorName,a.ShippingMarks,(select itemname from TbAccessoriesItem where itemid=a.accessoriesItemId) as AccessoriesName, cartonSize,a.Qty from tbAccessoriesIndentForCarton a order by a.PurchaseOrder,a.StyleId,a.ItemId,a.ColorId";
			List<?> list = session.createSQLQuery(sql).list();
			
			
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				query.add(new accessoriesindentcarton(element[0].toString(),element[1].toString(),element[2].toString(),element[3].toString(),element[4].toString(),element[5].toString(),element[6].toString(),element[7].toString(),element[8].toString()));

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
	public List<accessoriesindentcarton> getAccessoriesIndentCartonItemDetails(String id) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<accessoriesindentcarton> query=new ArrayList<accessoriesindentcarton>();

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select a.AccIndentId,a.PurchaseOrder,a.StyleId,a.ItemId,a.ColorId,a.ShippingMarks,a.accessoriesItemId,a.cartonSize,a.UnitId,a.OrderQty,a.Qty,a.Length1,a.Width1,a.Height1,a.Add1,a.Length2,a.Width2,a.Height2,a.Add2,a.DivideBy,a.Ply from tbAccessoriesIndentForCarton a where a.AccIndentId='"+id+"'";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				query.add(new accessoriesindentcarton(element[0].toString(),element[1].toString(),element[2].toString(),element[3].toString(),element[4].toString(),element[5].toString(),element[6].toString(),element[7].toString(),element[8].toString(),element[9].toString(),element[10].toString(),element[11].toString(),element[12].toString(),element[13].toString(),element[14].toString(),element[15].toString(),element[16].toString(),element[17].toString(),element[18].toString(),element[19].toString(),element[20].toString()));

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
	public boolean editAccessoriesCurton(accessoriesindentcarton v) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		boolean inserted=false;
		List<commonModel> query=new ArrayList<commonModel>();

		try{
			tx=session.getTransaction();
			tx.begin();


			String sql="update tbAccessoriesIndentForCarton  set "

					+ "cartonSize='"+v.getAccessoriesSize()+"',"
					+ "OrderQty='"+v.getLength1()+"',"
					+ "Width1='"+v.getWidth1()+"',"
					+ "Height1='"+v.getHeight1()+"',"
					+ "Add1='"+v.getAdd1()+"',"
					+ "Length2='"+v.getLength2()+"',"
					+ "Width2='"+v.getWidth2()+"',"
					+ "Height2='"+v.getHeight2()+"',"
					+ "Add2='"+v.getAdd2()+"',"
					+ "DivideBy='"+v.getDevideBy()+"',"
					+ "Ply='"+v.getPly()+"',"
					+ "Qty='"+v.getTotalQty()+"',IndentDate=CURRENT_TIMESTAMP,IndentTime=CURRENT_TIMESTAMP,IndentPostBy='"+v.getUser()+"'"
					+ " where AccIndentId='"+v.getAutoid()+"'";

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
	public List<String> getPurchaseOrderList() {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<String> dataList=new ArrayList<String>();
		try{
			tx=session.getTransaction();
			tx.begin();
			
			

			String sql="select PurchaseOrder from TbBuyerOrderEstimateDetails where PurchaseOrder != '' group by PurchaseOrder order by PurchaseOrder";
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
	public boolean saveFabricsIndent(FabricsIndent fabricsIndent) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="insert into tbFabricsIndent  "
					+ "(PurchaseOrder,"
					+ "styleId,"
					+ "itemid,"
					+ "itemcolor,"
					+ "fabricsid,"
					+ "fabricscolor,"
					+ "brand,"
					+ "width,"
					+ "Yard,"
					+ "GSM,"
					+ "qty,"
					+ "dozenqty,"
					+ "consumption,"
					+ "inPercent,"
					+ "PercentQty,"
					+ "TotalQty,"
					+ "unitId,"
					+ "RequireUnitQty,"
					+ "mdapproval,"
					+ "entrytime,"
					+ "entryby) values ("
					+ "'"+fabricsIndent.getPurchaseOrder()+"',"
					+ "'"+fabricsIndent.getStyleId()+"',"
					+ "'"+fabricsIndent.getItemId()+"',"
					+ "'"+fabricsIndent.getItemColorId()+"',"
					+ "'"+fabricsIndent.getFabricsId()+"',"
					+ "'"+fabricsIndent.getFabricsColorId()+"',"
					+ "'"+fabricsIndent.getBrandId()+"',"
					+ "'"+fabricsIndent.getWidth()+"',"
					+ "'"+fabricsIndent.getYard()+"',"
					+ "'"+fabricsIndent.getGsm()+"',"
					+ "'"+fabricsIndent.getQty()+"',"
					+ "'"+fabricsIndent.getDozenQty()+"',"
					+ "'"+fabricsIndent.getConsumption()+"',"
					+ "'"+fabricsIndent.getInPercent()+"',"
					+ "'"+fabricsIndent.getPercentQty()+"',"
					+ "'"+fabricsIndent.getTotalQty()+"',"
					+ "'"+fabricsIndent.getUnitId()+"',"
					+ "'"+fabricsIndent.getGrandQty()+"','0',"
					+ "CURRENT_TIMESTAMP,"
					+ "'"+fabricsIndent.getUserId()+"'"
					+ ") ";
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
	public List<FabricsIndent> getFabricsIndentList() {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<FabricsIndent> datalist=new ArrayList<FabricsIndent>();	
		try{	
			tx=session.getTransaction();
			tx.begin();	
			String sql="select rf.id,rf.PurchaseOrder,rf.styleId,sc.StyleNo,rf.itemId,id.itemname,rf.itemcolor,c.Colorname,rf.fabricsid,fi.ItemName,rf.qty,rf.dozenqty,rf.consumption,rf.inPercent,rf.PercentQty,TotalQty,rf.unitId,u.unitname  \r\n" + 
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
				datalist.add(new FabricsIndent(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString(), element[7].toString(), element[8].toString(), element[9].toString(), Double.valueOf(element[10].toString()),  Double.valueOf(element[11].toString()),  Double.valueOf(element[12].toString()),  Double.valueOf(element[13].toString()),  Double.valueOf(element[14].toString()),  Double.valueOf(element[15].toString()), element[16].toString(), element[17].toString()));		
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
	public FabricsIndent getFabricsIndent(String indentId) {
			// TODO Auto-generated method stub
			Session session=HibernateUtil.openSession();
			Transaction tx=null;

			FabricsIndent indent= null;
			try{	
				tx=session.getTransaction();
				tx.begin();	
				String sql="select id,PurchaseOrder,styleId,itemid,itemcolor,fabricsid,(select ItemName from TbFabricsItem where id=rf.fabricsid) as FabricsName,qty,dozenqty,consumption,inPercent,PercentQty,TotalQty,unitId,width,Yard,GSM,RequireUnitQty,fabricscolor,brand,entryby from tbFabricsIndent rf where id = '"+indentId+"'";

				List<?> list = session.createSQLQuery(sql).list();
				for(Iterator<?> iter = list.iterator(); iter.hasNext();)
				{	
					Object[] element = (Object[]) iter.next();
					
					indent = new FabricsIndent(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString(),  Double.valueOf(element[7].toString()),  Double.valueOf(element[8].toString()),  Double.valueOf(element[9].toString()),  Double.valueOf(element[10].toString()),  Double.valueOf(element[11].toString()), Double.valueOf(element[12].toString()),  element[13].toString(),  Double.valueOf(element[14].toString()),  Double.valueOf(element[15].toString()),  Double.valueOf(element[16].toString()), Double.valueOf(element[17].toString()), element[18].toString()  ,element[19].toString(),element[20].toString());

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
	public List<commonModel> BuyerWisePo(String buyerId) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<commonModel> dataList=new ArrayList<commonModel>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select BuyerOrderId,PurchaseOrder from TbBuyerOrderEstimateDetails where buyerId='"+buyerId+"' group by BuyerOrderId,PurchaseOrder";
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{		
				Object[] element = (Object[]) iter.next();
				dataList.add(new commonModel(element[0].toString(), element[1].toString()));
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
	public List<commonModel> getSampleList() {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<commonModel> dataList=new ArrayList<commonModel>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select AutoId,Name from TbSampleTypeInfo order by Name";
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{		
				Object[] element = (Object[]) iter.next();
				dataList.add(new commonModel(element[0].toString(), element[1].toString()));
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
	public List<commonModel> getInchargeList() {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<commonModel> dataList=new ArrayList<commonModel>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select InchargeId,InchargeName from TbInchargeInfo order by InchargeName";
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{		
				Object[] element = (Object[]) iter.next();
				dataList.add(new commonModel(element[0].toString(), element[1].toString()));
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
	public List<commonModel> getMerchendizerList() {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<commonModel> dataList=new ArrayList<commonModel>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select MerchendiserId,MerchendiserName from TbMerchendiserInfo order by MerchendiserName";
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{		
				Object[] element = (Object[]) iter.next();
				dataList.add(new commonModel(element[0].toString(), element[1].toString()));
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
			
			String sql="insert into TbSampleRequisitionDetails (BuyerId,purchaseOrder,StyleId,ItemId,ColorId,SampleTypeId,sizeGroupId,EntryTime,UserId) "
					+ "values('"+v.getBuyerId()+"','"+v.getPurchaseOrder()+"','"+v.getStyleId()+"','"+v.getItemId()+"','"+v.getColorId()+"','"+v.getSampleId()+"','"+v.getSizeGroupId()+"',CURRENT_TIMESTAMP,'"+v.getUserId()+"');";
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

			String sql="select sr.InchargeId,sr.MerchendizerId,sr.Instruction,srd.SampleTypeId,srd.BuyerId,srd.sampleAutoId,srd.StyleId,sc.StyleNo,srd.ItemId,id.itemname,srd.ColorId,c.Colorname,srd.PurchaseOrder,srd.sizeGroupId,srd.userId \r\n" + 
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
				dataList.add(new SampleRequisitionItem(element[0].toString(),element[1].toString(),element[2].toString(), element[3].toString(),element[4].toString(), element[5].toString(), element[6].toString(), element[7].toString(), element[8].toString(), element[9].toString(),element[10].toString(),element[11].toString(),element[12].toString(),element[13].toString(),element[14].toString()));
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
	public boolean confrimItemToSampleRequisition(SampleRequisitionItem v) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			
			
			String sampleReqId=getMaxSampleReqId();
			
			String sql="insert into tbSampleRequisition (sampleReqId,InchargeId,MerchendizerId,Instruction,dateLine,samplerequestdate,EntryTime,UserId) "
					+ "values('"+sampleReqId+"','"+v.getInchargeId()+"','"+v.getMarchendizerId()+"','"+v.getInstruction()+"','"+v.getSampleDeadline()+"',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'"+v.getUserId()+"');";
			session.createSQLQuery(sql).executeUpdate();
			
			String sqlupdate="update TbSampleRequisitionDetails set sampleReqId='"+sampleReqId+"',SampleTypeId='"+v.getSampleId()+"' where purchaseOrder='"+v.getPurchaseOrder()+"' and StyleId='"+v.getStyleId()+"' and ItemId='"+v.getItemId()+"' and ColorId='"+v.getColorId()+"' ";
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
	public List<SampleRequisitionItem> getSampleRequisitionList() {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<SampleRequisitionItem> dataList=new ArrayList<SampleRequisitionItem>();
		try{
			tx=session.getTransaction();
			tx.begin();
			
			String sql="select a.sampleReqId,a.purchaseOrder,(select StyleNo from TbStyleCreate where StyleId=a.StyleId) as StyleNO,a.StyleId,(SELECT CONVERT(varchar, a.Date, 101)) as Date from TbSampleRequisitionDetails a group by a.sampleReqId,a.purchaseOrder,a.StyleId,a.Date";
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{		
				Object[] element = (Object[]) iter.next();
				dataList.add(new SampleRequisitionItem(element[0].toString(), element[1].toString(),element[2].toString(),element[3].toString(),element[4].toString()));
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

			String sql="select sr.InchargeId,sr.MerchendizerId,sr.Instruction,srd.SampleTypeId,srd.BuyerId,srd.sampleAutoId,srd.StyleId,sc.StyleNo,srd.ItemId,id.itemname,srd.ColorId,c.Colorname,srd.PurchaseOrder,srd.sizeGroupId,srd.userId \r\n" + 
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
				dataList.add(new SampleRequisitionItem(element[0].toString(),element[1].toString(),element[2].toString(), element[3].toString(),element[4].toString(), element[5].toString(), element[6].toString(), element[7].toString(), element[8].toString(), element[9].toString(),element[10].toString(),element[11].toString(),element[12].toString(),element[13].toString(),element[14].toString()));
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
				sql = "select fi.id,fi.ItemName \r\n" + 
						"from tbFabricsIndent rf \r\n" + 
						"left join TbFabricsItem fi \r\n" + 
						"on rf.fabricsid = fi.id \r\n" + 
						"where styleid='"+styleId+"'  group by fi.id,fi.ItemName";
			}else if(type.equals("2")) {
				sql = "select a.itemid,a.itemname,a.unitId \r\n" + 
						"from tbAccessoriesIndent ai \r\n" + 
						"left join TbAccessoriesItem a \r\n" + 
						"on ai.accessoriesItemId = a.itemid \r\n" + 
						"where styleid='"+styleId+"'  group by a.itemid,a.itemname";
			}else {

				return null;
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
	public boolean submitPurchaseOrder(PurchaseOrder purchaseOrder) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<commonModel> query=new ArrayList<commonModel>();

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
					+ "orderDate,"
					+ "deliveryDate,"
					+ "orderby,"
					+ "billto,"
					+ "deliveryto,"
					+ "paymentTerm,"
					+ "Note,"
					+ "Subject,"
					+ "ManualPo,"
					+ "entryBy,"
					+ "entryTime) "
					+ " values "
					+ "("
					+ "'"+poId+"',"
					+ "'"+purchaseOrder.getOrderDate()+"',"
					+ "'"+purchaseOrder.getDeliveryDate()+"',"
					+ "'"+purchaseOrder.getOrderBy()+"',"
					+ "'"+purchaseOrder.getBillTo()+"',"
					+ "'"+purchaseOrder.getDeliveryTo()+"',"
					+ "'"+purchaseOrder.getPaymentType()+"',"
					+ "'"+purchaseOrder.getNote()+"',"
					+ "'"+purchaseOrder.getSubject()+"',"
					+ "'"+purchaseOrder.getManualPO()+"',"
					+ "'"+purchaseOrder.getUserId()+"',"
					+ "CURRENT_TIMESTAMP ) ";
			session.createSQLQuery(sql).executeUpdate();

			int length = purchaseOrder.getItemList().size();
			for (PurchaseOrderItem item : purchaseOrder.getItemList()) {
				if(item.getType().equals("1")) {
					if(item.isCheck()) {
						sql="Update tbFabricsIndent set pono='"+poId+"',poapproval='1',supplierid='"+item.getSupplierId()+"',dolar='"+item.getDollar()+"',rate='"+item.getRate()+"',amount='"+item.getAmount()+"',currency='"+item.getCurrency()+"',poManual='"+purchaseOrder.getManualPO()+"' where id='"+item.getAutoId()+"'";		
					}else {
						sql="Update tbFabricsIndent set poapproval='0',supplierid='"+item.getSupplierId()+"',dolar='"+item.getDollar()+"',rate='"+item.getRate()+"',amount='"+item.getAmount()+"',currency='"+item.getCurrency()+"',poManual='"+purchaseOrder.getManualPO()+"' where id='"+item.getAutoId()+"'";		
					}
				}else if(item.getType().equals("2")) {
					if(item.isCheck()) {
						sql="Update tbAccessoriesIndent set pono='"+poId+"',poapproval='1',supplierid='"+item.getSupplierId()+"',dolar='"+item.getDollar()+"',rate='"+item.getRate()+"',amount='"+item.getAmount()+"',currency='"+item.getCurrency()+"',poManual='"+purchaseOrder.getManualPO()+"' where AccIndentId='"+item.getAutoId()+"'";		
					}else {
						sql="Update tbAccessoriesIndent set poapproval='0',supplierid='"+item.getSupplierId()+"',dolar='"+item.getDollar()+"',rate='"+item.getRate()+"',amount='"+item.getAmount()+"',currency='"+item.getCurrency()+"',poManual='"+purchaseOrder.getManualPO()+"' where AccIndentId='"+item.getAutoId()+"'";		
					}

				}else if(item.getType().equals("3")) {
					if(item.isCheck()) {
						sql="Update tbAccessoriesIndentForCarton set pono='"+poId+"',poapproval='1',supplierid='"+item.getSupplierId()+"',dolar='"+item.getDollar()+"',rate='"+item.getRate()+"',amount='"+item.getAmount()+"',currency='"+item.getCurrency()+"',poManual='"+purchaseOrder.getManualPO()+"' where AccIndentId='"+item.getAutoId()+"'";		
					}else {
						sql="Update tbAccessoriesIndentForCarton set poapproval='0',supplierid='"+item.getSupplierId()+"',dolar='"+item.getDollar()+"',rate='"+item.getRate()+"',amount='"+item.getAmount()+"',currency='"+item.getCurrency()+"',poManual='"+purchaseOrder.getManualPO()+"' where AccIndentId='"+item.getAutoId()+"'";		
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

		List<commonModel> query=new ArrayList<commonModel>();

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select (isnull(max(poNo),0)+1) as maxId from tbPurchaseOrderSummary";
			List<?> list = session.createSQLQuery(sql).list();
			String poId="0";
			if(list.size()>0) {
				poId = list.get(0).toString();
			}

			sql="update tbPurchaseOrderSummary set "
					+ "orderDate = '"+purchaseOrder.getOrderDate()+"',"
					+ "deliveryDate = '"+purchaseOrder.getDeliveryDate()+"',"
					+ "orderby = '"+purchaseOrder.getOrderBy()+"',"
					+ "billto = '"+purchaseOrder.getBillTo()+"',"
					+ "deliveryto = '"+purchaseOrder.getDeliveryTo()+"',"
					+ "paymentTerm = '"+purchaseOrder.getPaymentType()+"',"
					+ "Note = '"+purchaseOrder.getNote()+"',"
					+ "Subject = '"+purchaseOrder.getSubject()+"',"
					+ "ManualPo = '"+purchaseOrder.getManualPO()+"',"
					+ "entryBy = '"+purchaseOrder.getUserId()+"',"
					+ "entryTime = CURRENT_TIMESTAMP where poNo= '"+purchaseOrder.getPoNo()+"'";
			session.createSQLQuery(sql).executeUpdate();

			int length = purchaseOrder.getItemList().size();
			for (PurchaseOrderItem item : purchaseOrder.getItemList()) {
				if(item.getType().equals("1")) {
					if(item.isCheck()) {
						sql="Update tbFabricsIndent set pono='"+poId+"',poapproval='1',supplierid='"+item.getSupplierId()+"',dolar='"+item.getDollar()+"',rate='"+item.getRate()+"',amount='"+item.getAmount()+"',currency='"+item.getCurrency()+"',poManual='"+purchaseOrder.getManualPO()+"' where id='"+item.getAutoId()+"'";		
					}else {
						sql="Update tbFabricsIndent set poapproval='0',supplierid='"+item.getSupplierId()+"',dolar='"+item.getDollar()+"',rate='"+item.getRate()+"',amount='"+item.getAmount()+"',currency='"+item.getCurrency()+"',poManual='"+purchaseOrder.getManualPO()+"' where id='"+item.getAutoId()+"'";		
					}
				}else if(item.getType().equals("2")) {
					if(item.isCheck()) {
						sql="Update tbAccessoriesIndent set pono='"+poId+"',poapproval='1',supplierid='"+item.getSupplierId()+"',dolar='"+item.getDollar()+"',rate='"+item.getRate()+"',amount='"+item.getAmount()+"',currency='"+item.getCurrency()+"',poManual='"+purchaseOrder.getManualPO()+"' where AccIndentId='"+item.getAutoId()+"'";		
					}else {
						sql="Update tbAccessoriesIndent set poapproval='0',supplierid='"+item.getSupplierId()+"',dolar='"+item.getDollar()+"',rate='"+item.getRate()+"',amount='"+item.getAmount()+"',currency='"+item.getCurrency()+"',poManual='"+purchaseOrder.getManualPO()+"' where AccIndentId='"+item.getAutoId()+"'";		
					}

				}else if(item.getType().equals("3")) {
					if(item.isCheck()) {
						sql="Update tbAccessoriesIndentForCarton set pono='"+poId+"',poapproval='1',supplierid='"+item.getSupplierId()+"',dolar='"+item.getDollar()+"',rate='"+item.getRate()+"',amount='"+item.getAmount()+"',currency='"+item.getCurrency()+"',poManual='"+purchaseOrder.getManualPO()+"' where AccIndentId='"+item.getAutoId()+"'";		
					}else {
						sql="Update tbAccessoriesIndentForCarton set poapproval='0',supplierid='"+item.getSupplierId()+"',dolar='"+item.getDollar()+"',rate='"+item.getRate()+"',amount='"+item.getAmount()+"',currency='"+item.getCurrency()+"',poManual='"+purchaseOrder.getManualPO()+"' where AccIndentId='"+item.getAutoId()+"'";		
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
	public List<PurchaseOrder> getPurchaseOrderSummeryList() {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<PurchaseOrder> dataList=new ArrayList<PurchaseOrder>();
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql;

			sql=" select pono,(select convert(varchar,orderDate,103))as orderDate from tbPurchaseOrderSummary order by pono desc";
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				dataList.add(new PurchaseOrder(element[0].toString(),element[1].toString()));
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
	public PurchaseOrder getPurchaseOrder(String poNo) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		PurchaseOrder purchaseOrder = null;
		List<PurchaseOrderItem> dataList=new ArrayList<PurchaseOrderItem>();
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql;



			sql="select fi.id,style.StyleNo,f.ItemName as accessoriesname,fi.supplierId,fi.rate,fi.dolar,c.Colorname,'' as size,fi.TotalQty,fi.RequireUnitQty, unit.unitname\r\n" + 
					"from tbFabricsIndent fi \r\n" + 
					"left join TbStyleCreate style\r\n" + 
					"on fi.styleId = style.StyleId \r\n" + 
					"left join tbColors c\r\n" + 
					"on fi.itemcolor = c.ColorId \r\n" + 
					"left join TbFabricsItem f\r\n" + 
					"on fi.fabricsid = f.id\r\n" + 
					"left join tbunits unit\r\n" + 
					"on fi.UnitId = unit.Unitid\r\n" + 
					"where fi.poNo='"+poNo+"' and poapproval='1'   order by fi.id";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				dataList.add(new PurchaseOrderItem(element[0].toString(), element[1].toString(),"1", element[2].toString(), element[3].toString(), Double.valueOf(element[4].toString()), element[5].toString(), element[6].toString(), element[7].toString(), Double.valueOf(element[8].toString()), Double.valueOf(element[9].toString()), element[10].toString(),"",true));				
			}

			sql=" select ai.AccIndentId,style.StyleNo,accItem.itemname as accessoriesname,ai.supplierId,ai.rate,ai.dolar,c.Colorname as itemcolor,ai.size,ai.OrderQty,ai.TotalQty,unit.unitname\r\n" + 
					" from tbAccessoriesIndent ai \r\n" + 
					" left join TbStyleCreate style\r\n" + 
					" on ai.styleid = style.StyleId \r\n" + 
					" left join tbColors c\r\n" + 
					" on ai.ColorId = c.ColorId \r\n" + 
					" left join TbAccessoriesItem accItem\r\n" + 
					" on ai.accessoriesItemId = accItem.itemid\r\n" + 
					" left join tbunits unit\r\n" + 
					" on ai.UnitId = unit.Unitid "
					+ "where ai.poNo='"+poNo+"' and ai.poapproval='1'  order by ai.AccIndentId";
			list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				dataList.add(new PurchaseOrderItem(element[0].toString(), element[1].toString(),"2", element[2].toString(), element[3].toString(), Double.valueOf(element[4].toString()), element[5].toString(), element[6].toString(), element[7].toString(), Double.valueOf(element[8].toString()), Double.valueOf(element[9].toString()), element[10].toString(),"",true));
			}

			sql="select aic.AccIndentId ,style.StyleNo,'Curton' as accessoriesname,aic.supplierId,aic.rate,aic.dolar,'' as color,aic.size,aic.OrderQty,aic.Qty,unit.unitname\r\n" + 
					" from tbAccessoriesIndentForCarton aic\r\n" + 
					" left join TbStyleCreate style\r\n" + 
					" on aic.styleid = style.StyleId \r\n" + 
					" left join tbunits unit\r\n" + 
					" on aic.UnitId = unit.Unitid \r\n" + 
					" where aic.poNo = '"+poNo+"' and  poapproval='1'  order by aic.AccIndentId";
			list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				dataList.add(new PurchaseOrderItem(element[0].toString(), element[1].toString(),"3", element[2].toString(), element[3].toString(), Double.valueOf(element[4].toString()), element[5].toString(), element[6].toString(), element[7].toString(), Double.valueOf(element[8].toString()), Double.valueOf(element[9].toString()), element[10].toString(),"",true));
			}


			sql = "select poNo,(select convert(varchar,orderDate,103))as orderDate,(select convert(varchar,deliveryDate,103))as deliveryDate,deliveryto,orderby,billto,ManualPo,paymentTerm,Note,Subject,entryBy from tbPurchaseOrderSummary";
			list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{
				Object[] element = (Object[]) iter.next();
				purchaseOrder = new PurchaseOrder(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString(), element[7].toString(), "0", element[8].toString(), element[9].toString(), dataList, element[10].toString());
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
		return purchaseOrder;
	}

	@Override
	public List<PurchaseOrderItem> getPurchaseOrderItemList(PurchaseOrderItem purchaseOrderItem) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<PurchaseOrderItem> dataList=new ArrayList<PurchaseOrderItem>();
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql;
			if(purchaseOrderItem.getType().equals("1")) {
				sql="select fi.id,style.StyleNo,f.ItemName as accessoriesname,c.Colorname,'' as size,fi.TotalQty,fi.RequireUnitQty, unit.unitname\r\n" + 
						"from tbFabricsIndent fi \r\n" + 
						"left join TbStyleCreate style\r\n" + 
						"on fi.styleId = style.StyleId \r\n" + 
						"left join tbColors c\r\n" + 
						"on fi.itemcolor = c.ColorId \r\n" + 
						"left join TbFabricsItem f\r\n" + 
						"on fi.fabricsid = f.id\r\n" + 
						"left join tbunits unit\r\n" + 
						"on fi.UnitId = unit.Unitid\r\n" + 
						"where fi.PurchaseOrder='"+purchaseOrderItem.getPurchaseOrder()+"' and fi.styleid='"+purchaseOrderItem.getStyleId()+"' and fabricsid='"+purchaseOrderItem.getIndentItemId()+"' and (poapproval IS NULL or poapproval='0')  order by fi.id";

				List<?> list = session.createSQLQuery(sql).list();
				for(Iterator<?> iter = list.iterator(); iter.hasNext();)
				{	
					Object[] element = (Object[]) iter.next();
					dataList.add(new PurchaseOrderItem(element[0].toString(), element[1].toString(),purchaseOrderItem.getType(), element[2].toString(), purchaseOrderItem.getSupplierId(), purchaseOrderItem.getRate(), purchaseOrderItem.getDollar(), element[3].toString(), element[4].toString(), Double.valueOf(element[5].toString()), Double.valueOf(element[6].toString()), element[7].toString(),"",false));
				}
			}else if(purchaseOrderItem.getType().equals("2")) {
				sql=" select ai.AccIndentId,style.StyleNo,accItem.itemname as accessoriesname,c.Colorname as itemcolor,ai.size,ai.OrderQty,ai.TotalQty,unit.unitname\r\n" + 
						" from tbAccessoriesIndent ai \r\n" + 
						" left join TbStyleCreate style\r\n" + 
						" on ai.styleid = style.StyleId \r\n" + 
						" left join tbColors c\r\n" + 
						" on ai.ColorId = c.ColorId \r\n" + 
						" left join TbAccessoriesItem accItem\r\n" + 
						" on ai.accessoriesItemId = accItem.itemid\r\n" + 
						" left join tbunits unit\r\n" + 
						" on ai.UnitId = unit.Unitid "
						+ "where ai.PurchaseOrder='"+purchaseOrderItem.getPurchaseOrder()+"' and ai.styleid='"+purchaseOrderItem.getStyleId()+"' and ai.accessoriesItemId='"+purchaseOrderItem.getIndentItemId()+"' and (poapproval IS NULL or poapproval='0')  order by ai.AccIndentId";
				List<?> list = session.createSQLQuery(sql).list();
				for(Iterator<?> iter = list.iterator(); iter.hasNext();)
				{	
					Object[] element = (Object[]) iter.next();
					dataList.add(new PurchaseOrderItem(element[0].toString(), element[1].toString(),purchaseOrderItem.getType(), element[2].toString(), purchaseOrderItem.getSupplierId(), purchaseOrderItem.getRate(), purchaseOrderItem.getDollar(), element[3].toString(), element[4].toString(), Double.valueOf(element[5].toString()), Double.valueOf(element[6].toString()), element[7].toString(),"",false));
				}
			}else {
				sql="select aic.AccIndentId ,style.StyleNo,'Curton' as accessoriesname,'' as color,aic.size,aic.OrderQty,aic.Qty,unit.unitname\r\n" + 
						" from tbAccessoriesIndentForCarton aic\r\n" + 
						" left join TbStyleCreate style\r\n" + 
						"on aic.styleid = style.StyleId \r\n" + 
						" left join tbunits unit\r\n" + 
						" on aic.UnitId = unit.Unitid \r\n" + 
						" where aic.styleid = '"+purchaseOrderItem.getStyleId()+"' and aic.PurchaseOrder = '"+purchaseOrderItem.getPurchaseOrder()+"' and aic.accessoriesItemId = '"+purchaseOrderItem.getIndentItemId()+"' and aic.poapproval IS NULL or poapproval='0'  order by aic.AccIndentId";
				List<?> list = session.createSQLQuery(sql).list();
				for(Iterator<?> iter = list.iterator(); iter.hasNext();)
				{	
					Object[] element = (Object[]) iter.next();
					dataList.add(new PurchaseOrderItem(element[0].toString(), element[1].toString(),purchaseOrderItem.getType(), element[2].toString(), purchaseOrderItem.getSupplierId(), purchaseOrderItem.getRate(), purchaseOrderItem.getDollar(), element[3].toString(), element[4].toString(), Double.valueOf(element[5].toString()), Double.valueOf(element[6].toString()), element[7].toString(),"",false));
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

			String sql="select srd.SampleTypeId,srd.BuyerId,srd.sampleAutoId,srd.StyleId,sc.StyleNo,srd.ItemId,id.itemname,srd.ColorId,c.Colorname,srd.PurchaseOrder,srd.sizeGroupId,srd.userId \r\n" + 
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
				dataList.add(new SampleRequisitionItem(element[0].toString(),element[1].toString(),element[2].toString(), element[3].toString(),element[4].toString(), element[5].toString(), element[6].toString(), element[7].toString(), element[8].toString(), element[9].toString(),element[10].toString(),element[11].toString()));
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
	
}
