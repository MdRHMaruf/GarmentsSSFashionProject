package pg.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import pg.registerModel.AccessoriesItem;
import pg.registerModel.Bank;
import pg.registerModel.Brand;
import pg.registerModel.BuyerModel;
import pg.registerModel.Color;
import pg.registerModel.Country;
import pg.registerModel.CourierModel;
import pg.registerModel.Department;
import pg.registerModel.Designation;
import pg.registerModel.Employee;
import pg.registerModel.FabricsItem;
import pg.registerModel.Factory;
import pg.registerModel.FactoryModel;
import pg.registerModel.InchargeInfo;
import pg.registerModel.Line;
import pg.registerModel.LocalItem;
import pg.registerModel.Machine;
import pg.registerModel.MerchandiserInfo;
import pg.registerModel.Notifyer;
import pg.registerModel.ParticularItem;
import pg.registerModel.ProcessInfo;
import pg.registerModel.SampleType;
import pg.registerModel.Size;
import pg.registerModel.SizeGroup;
import pg.registerModel.StyleItem;
import pg.registerModel.SupplierModel;
import pg.registerModel.Unit;
import pg.registerModel.WareHouse;
import pg.share.FormId;
import pg.share.HibernateUtil;
import pg.share.ItemType;
import pg.storeModel.StoreGeneralCategory;

@Repository
public class RegisterDaoImpl implements RegisterDao{

	boolean existsbuyer;
	final String MD_ID = "9";


	@Override
	public String maxBuyerId() {
		// TODO Auto-generated method stub

		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		String query="";

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select isnull(max(id),0)+1 from tbbuyer";
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
	public List<String> countries(String value) {

		// TODO Auto-generated method stub

		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<String> countrylist=new ArrayList<>();

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select concat(countryname,'*',autoid) as countryname from tbcountryinfo where countryname like '%"+value+"%'";
			System.out.println(" countries ");

			List<?> list = session.createSQLQuery(sql).list();


			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				//Object[] element = (Object[]) iter.next();

				countrylist.add(iter.next().toString());

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


	public boolean checkDuplicateBuyer(String buyername) {
		boolean exists=false;
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<String> countrylist=new ArrayList<>();

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select * from tbBuyer where name='"+buyername+"'";
			System.out.println(" buyername "+sql);

			List<?> list = session.createSQLQuery(sql).list();


			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				//Object[] element = (Object[]) iter.next();

				exists=true;
				existsbuyer=true;
				break;

			}



			tx.commit();
			return exists;


		}
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


	public String getCountryname(String countryid) {
		String countryname="";
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<String> countrylist=new ArrayList<>();

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select CountryName from TbCountryInfo where autoId='"+countryid+"'";
			System.out.println(" country name ");

			List<?> list = session.createSQLQuery(sql).list();


			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				//Object[] element = (Object[]) iter.next();

				countryname=iter.next().toString();

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
		return countryname;
	}


	@Override
	public boolean insertBuyer(BuyerModel buyer) {

		// TODO Auto-generated method stub

		Session session=HibernateUtil.openSession();
		Transaction tx=null;



		try{
			tx=session.getTransaction();
			tx.begin();

			if (checkDuplicateBuyer(buyer.getBuyername())) {
				return false;
			}else {
				String sql="insert into tbbuyer ( name, BuyerCode, BuyerAddress, ConsigneeAddress, NutifyAddress, BuyerCountry, Telephone, MobileNo, Fax, Email, SkypeId, BankName, BankAddress, SwiftCode, BankCountry, EntryTime, UserId ) "
						+ "values('"+buyer.getBuyername()+"','"+buyer.getBuyercode()+"','"+buyer.getBuyerAddress()+"','"+buyer.getConsigneeAddress()+"','"+buyer.getNotifyAddress()+"','"+getCountryname(buyer.getCountry())+"','"+buyer.getTelephone()+"','"+buyer.getMobile()+"','"+buyer.getFax()+"','"+buyer.getEmail()+"','"+buyer.getSkypeid()+"','"+buyer.getBankname()+"','"+buyer.getBankaddress()+"','"+buyer.getSwiftcode()+"','"+getCountryname(buyer.getBankcountry())+"',GETDATE(),'"+buyer.getUser()+"')";

				session.createSQLQuery(sql).executeUpdate();
				
				sql="select max(id) as maxId from tbBuyer";
				List<?> list = session.createSQLQuery(sql).list();
				String buyerId = list.get(0).toString();
				
				sql="select g2.groupId,g2.groupName,g2.memberId \r\n" + 
						"from tbGroups g1\r\n" + 
						"join tbGroups g2\r\n" + 
						"on g1.groupId = g2.groupId \r\n" + 
						"where g1.memberId = '"+buyer.getUser()+"' and g2.memberId != '"+buyer.getUser()+"'";
				list = session.createSQLQuery(sql).list();
				for(Iterator<?> iter = list.iterator(); iter.hasNext();)
				{	
					Object[] element = (Object[]) iter.next();
					sql = "insert into tbFileAccessPermission (resourceType,resourceId,ownerId,permittedUserId,entryTime,entryBy) values('"+FormId.BUYER_CREATE.getId()+"','"+buyerId+"','"+buyer.getUser()+"','"+element[2].toString()+"',CURRENT_TIMESTAMP,'"+buyer.getUser()+"')";
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
	public List<String> BuyersList(String value) {
		String countryname="";
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<String> Buyers=new ArrayList<>();

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select concat(name, '*',id) as buyername from tbBuyer where name like '%"+value+"%'";
			System.out.println(" check duplicate buyer query ");

			List<?> list = session.createSQLQuery(sql).list();


			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				//Object[] element = (Object[]) iter.next();

				Buyers.add(iter.next().toString());

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
	public List<BuyerModel> BuyersDetails(String value) {
		String countryname="";
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<BuyerModel> Buyers=new ArrayList<BuyerModel>();

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select id, name, BuyerCode, BuyerAddress, ConsigneeAddress, NutifyAddress,isnull((select concat(countryname,'*',autoId) from TbCountryInfo where CountryName=BuyerCountry),'') as BuyerCountry, Telephone, MobileNo, Fax, Email, SkypeId, BankName, BankAddress, SwiftCode,isnull((select concat(countryname,'*',autoId) from TbCountryInfo where CountryName=BankCountry),'') as  BankCount from tbBuyer where id='"+value+"'";
			System.out.println(" buyer details query ");

			List<?> list = session.createSQLQuery(sql).list();


			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				Buyers.add(new BuyerModel(element[0].toString(),element[1].toString(),element[2].toString(),element[3].toString(),element[4].toString(),element[5].toString(),element[6].toString(),element[7].toString(),element[8].toString(),element[9].toString(),element[10].toString(),element[11].toString(),element[12].toString(),element[13].toString(),element[14].toString(),element[15].toString()));

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
	public boolean editBuyer(BuyerModel buyer) {

		// TODO Auto-generated method stub
		boolean success=false;
		//success=checkDuplicateBuyer(buyer.getBuyername());


		Session session=HibernateUtil.openSession();
		Transaction tx=null;


		String sql="";


		try{
			tx=session.getTransaction();
			tx.begin();




			if (checkDuplicateBuyer(buyer.getBuyername())) {
				sql="update  tbbuyer set  BuyerCode='"+buyer.getBuyercode()+"', BuyerAddress='"+buyer.getBuyerAddress()+"', ConsigneeAddress='"+buyer.getConsigneeAddress()+"', NutifyAddress='"+buyer.getNotifyAddress()+"', BuyerCountry='"+getCountryname(buyer.getCountry())+"', Telephone='"+buyer.getTelephone()+"', MobileNo='"+buyer.getMobile()+"', Fax='"+buyer.getFax()+"', Email='"+buyer.getEmail()+"', SkypeId='"+buyer.getSkypeid()+"', BankName='"+buyer.getBankname()+"', BankAddress='"+buyer.getBankaddress()+"', SwiftCode='"+buyer.getSwiftcode()+"',bankcountry='"+getCountryname(buyer.getBankcountry())+"' where id='"+buyer.getBuyerid()+"'";
				System.out.println(" edit buyer query ");

				session.createSQLQuery(sql).executeUpdate();

				success= true;
				System.out.println("duplicat t buyer check "+success);


			}else if(!checkDuplicateBuyer(buyer.getBuyername())) {
				sql="update  tbbuyer set name='"+buyer.getBuyername()+"', BuyerCode='"+buyer.getBuyercode()+"', BuyerAddress='"+buyer.getBuyerAddress()+"', ConsigneeAddress='"+buyer.getConsigneeAddress()+"', NutifyAddress='"+buyer.getNotifyAddress()+"', BuyerCountry='"+getCountryname(buyer.getCountry())+"', Telephone='"+buyer.getTelephone()+"', MobileNo='"+buyer.getMobile()+"', Fax='"+buyer.getFax()+"', Email='"+buyer.getEmail()+"', SkypeId='"+buyer.getSkypeid()+"', BankName='"+buyer.getBankname()+"', BankAddress='"+buyer.getBankaddress()+"', SwiftCode='"+buyer.getSwiftcode()+"',bankcountry='"+getCountryname(buyer.getBankcountry())+"' where id='"+buyer.getBuyerid()+"'";
				System.out.println(" edit buyer query ");

				session.createSQLQuery(sql).executeUpdate();


				success= true;
				System.out.println("duplicat t buyer check "+success);

			}

			tx.commit();


			System.out.println("duplicat t buyer check "+success);


			return success;


		}
		catch(Exception e){

			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		}

		finally {
			session.close();
		}

		return success;


	}

	@Override
	public List<BuyerModel> getAllBuyers(String userId) {
		String countryname="";
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<BuyerModel> Buyers=new ArrayList<BuyerModel>();

		try{
			tx=session.getTransaction();
			tx.begin();

			
			String sql="select id, name, BuyerCode, BuyerAddress, ConsigneeAddress, NutifyAddress,isnull((select concat(countryname,'*',autoId) from TbCountryInfo where CountryName=BuyerCountry),'') as BuyerCountry, Telephone, MobileNo, Fax, Email, SkypeId, BankName, BankAddress, SwiftCode,isnull((select concat(countryname,'*',autoId) from TbCountryInfo where CountryName=BankCountry),'') as  BankCount from tbBuyer where userId='"+userId+"' "
					+ "union\r\n" + 
					"select b.id, b.name, b.BuyerCode, b.BuyerAddress, b.ConsigneeAddress, b.NutifyAddress,isnull((select concat(countryname,'*',autoId) from TbCountryInfo where CountryName=b.BuyerCountry),'') as BuyerCountry, b.Telephone, b.MobileNo, b.Fax, b.Email, b.SkypeId, b.BankName, b.BankAddress, b.SwiftCode,isnull((select concat(countryname,'*',autoId) from TbCountryInfo where CountryName=b.BankCountry),'') as  BankCount \r\n" + 
					"from tbFileAccessPermission fap\r\n" + 
					" inner join tbBuyer b\r\n" + 
					" on fap.ownerId = b.UserId and b.id = fap.resourceId\r\n" + 
					"where fap.permittedUserId = '"+userId+"' and fap.resourceType = '"+FormId.BUYER_CREATE.getId()+"'\r\n" + 
					" order by id";
			
			if(userId.equals(MD_ID)) {
				sql="select id, name, BuyerCode, BuyerAddress, ConsigneeAddress, NutifyAddress,isnull((select concat(countryname,'*',autoId) from TbCountryInfo where CountryName=BuyerCountry),'') as BuyerCountry, Telephone, MobileNo, Fax, Email, SkypeId, BankName, BankAddress, SwiftCode,isnull((select concat(countryname,'*',autoId) from TbCountryInfo where CountryName=BankCountry),'') as  BankCount from tbBuyer order by id";
			}
			
			System.out.println(" buyer details query ");

			List<?> list = session.createSQLQuery(sql).list();


			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				Buyers.add(new BuyerModel(element[0].toString(),element[1].toString(),element[2].toString(),element[3].toString(),element[4].toString(),element[5].toString(),element[6].toString(),element[7].toString(),element[8].toString(),element[9].toString(),element[10].toString(),element[11].toString(),element[12].toString(),element[13].toString(),element[14].toString(),element[15].toString()));

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
	public boolean saveNotifyer(Notifyer notifyer) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="insert into tbNotifyer(buyerId,notifyName,notifyAddress,country,telephone,email,entryTime,entryBy)\r\n" + 
					"values('"+notifyer.getBuyerId()+"','"+notifyer.getName()+"','"+notifyer.getAddress()+"','"+notifyer.getCountry()+"','"+notifyer.getTelephone()+"','"+notifyer.getEmail()+"',CURRENT_TIMESTAMP,'"+notifyer.getUserId()+"')";

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
	public boolean editNotifyer(Notifyer notifyer) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="update tbNotifyer set buyerId='"+notifyer.getBuyerId()+"',notifyName='"+notifyer.getName()+"',notifyAddress='"+notifyer.getAddress()+"',country='"+notifyer.getCountry()+"',telephone='"+notifyer.getTelephone()+"',email='"+notifyer.getEmail()+"' where id='"+notifyer.getId()+"'";

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
	public Notifyer getNotifyerInfo(String id) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		Notifyer notifyer=null;

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select n.id,n.buyerId,notifyName,notifyAddress,country,telephone,email,entryBy from tbnotifyer n where n.id='"+id+"'";
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				notifyer = new Notifyer(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString(), element[7].toString());
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
		return notifyer;
	}

	@Override
	public List<Notifyer> getNotifyerList() {
		// TODO Auto-generated method stub
		
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<Notifyer> notifyers=new ArrayList<>();

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select n.id,n.buyerId,notifyName,notifyAddress,country,telephone,email,entryBy from tbnotifyer n";
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				notifyers.add(new Notifyer(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString(), element[7].toString()));
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
		return notifyers;
	}

	@Override
	public List<Notifyer> getNotifyerListByBuyerId(String buyerId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<Notifyer> notifyers=new ArrayList<>();
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="select n.id,n.buyerId,notifyName,notifyAddress,country,telephone,email,entryBy from tbnotifyer n where n.buyerId='"+buyerId+"'";
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				notifyers.add(new Notifyer(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString(), element[7].toString()));
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
		return notifyers;
	}


	//Supplier create
	@Override
	public String maxSupplierId() {
		// TODO Auto-generated method stub

		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		String query="";

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select isnull(max(id),0)+1 from tbSupplier";
			System.out.println(" max supplier id");

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

	public boolean checkDuplicateSupplier(String supplier) {
		boolean exists=false;
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<String> countrylist=new ArrayList<>();

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select * from tbsupplier where name='"+supplier+"'";
			System.out.println(" buyername "+sql);

			List<?> list = session.createSQLQuery(sql).list();


			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				//Object[] element = (Object[]) iter.next();

				exists=true;
				existsbuyer=true;
				break;

			}



			tx.commit();
			return exists;


		}
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
	public boolean insertSupplier(SupplierModel supplier) {

		// TODO Auto-generated method stub

		Session session=HibernateUtil.openSession();
		Transaction tx=null;



		try{
			tx=session.getTransaction();
			tx.begin();

			if (checkDuplicateBuyer(supplier.getSuppliername())) {
				return false;
			}else {
				String sql="insert into tbsupplier (  name, SupplierCode,"
						+ " ContactPerson, SupplierAddress, "
						+ "ConsigneeAddress, NutifyAddress, "
						+ "SupplierCountry, Telephone, MobileNo, "
						+ "Fax, Email, SkypeId, BankName, "
						+ "BankAddress, AccountsNo, AccountsName,  "
						+ " SwiftCode, BankCountry, EntryTime, UserId  ) "
						+ "values('"+supplier.getSuppliername()+"',"
						+ "'"+supplier.getSuppliercode()+"','"+supplier.getContcatPerson()+"',"
						+ "'"+supplier.getSupplieraddress()+"','"+supplier.getConsigneeAddress()+"',"
						+ "'"+supplier.getNotifyAddress()+"','"+getCountryname(supplier.getCountry())+"',"
						+ "'"+supplier.getTelephone()+"','"+supplier.getMobile()+"',"
						+ "'"+supplier.getFax()+"','"+supplier.getEmail()+"','"+supplier.getSkypeid()+"',"
						+ "'"+supplier.getBankname()+"','"+supplier.getBankaddress()+"',"
						+ "'"+supplier.getAccountno()+"','"+supplier.getAccountname()+"','"+supplier.getSwiftcode()+"',"
						+ "'"+getCountryname(supplier.getBankcountry())+"',GETDATE(),'"+supplier.getUser()+"')";

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
	public List<String> supplierList(String value) {
		String countryname="";
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<String> suppliers=new ArrayList<>();

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select concat(name, '*',id) as buyername from tbsupplier where name like '%"+value+"%'";
			System.out.println(" check duplicate buyer query ");

			List<?> list = session.createSQLQuery(sql).list();


			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				//Object[] element = (Object[]) iter.next();

				suppliers.add(iter.next().toString());

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
		return suppliers;
	}

	@Override
	public List<SupplierModel> SupplierDetails(String value) {
		String countryname="";
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<SupplierModel> supplier=new ArrayList<SupplierModel>();

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="SELECT id, name, SupplierCode, ContactPerson, SupplierAddress, ConsigneeAddress, NutifyAddress, isnull((select concat(countryname,'*',autoId) from TbCountryInfo where CountryName=Suppliercountry),'') as suppliercountry, Telephone, MobileNo, Fax, Email, SkypeId, BankName, BankAddress, AccountsNo, AccountsName, SwiftCode, isnull((select concat(countryname,'*',autoId) from TbCountryInfo where CountryName=Bankcountry),'') as bankcountry  FROM   tbSupplier where id='"+value+"'";
			System.out.println(" buyer details query ");

			List<?> list = session.createSQLQuery(sql).list();


			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				supplier.add(new SupplierModel(element[0].toString(),element[1].toString(),element[2].toString(),element[3].toString(),element[4].toString(),element[5].toString(),element[6].toString(),element[7].toString(),element[8].toString(),element[9].toString(),element[10].toString(),element[11].toString(),element[12].toString(),element[13].toString(),element[14].toString(),element[15].toString(),element[16].toString(),element[17].toString(),element[18].toString()));

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
		return supplier;
	}

	@Override
	public boolean editSupplier(SupplierModel supplier) {

		// TODO Auto-generated method stub
		boolean success=false;
		//success=checkDuplicateBuyer(buyer.getBuyername());


		Session session=HibernateUtil.openSession();
		Transaction tx=null;


		String sql="";


		try{
			tx=session.getTransaction();
			tx.begin();




			if (checkDuplicateBuyer(supplier.getSuppliername())) {
				sql="update  tbsupplier set  suppliercode='"+supplier.getSuppliercode()+"', SupplierAddress='"+supplier.getSupplieraddress()+"', ConsigneeAddress='"+supplier.getConsigneeAddress()+"', ContactPerson='"+supplier.getContcatPerson()+"', NutifyAddress='"+supplier.getNotifyAddress()+"', SupplierCountry='"+getCountryname(supplier.getCountry())+"', Telephone='"+supplier.getTelephone()+"', MobileNo='"+supplier.getMobile()+"', Fax='"+supplier.getFax()+"', Email='"+supplier.getEmail()+"', SkypeId='"+supplier.getSkypeid()+"', BankName='"+supplier.getBankname()+"', BankAddress='"+supplier.getBankaddress()+"',AccountsNo='"+supplier.getAccountno()+"',AccountsName='"+supplier.getAccountname()+"', SwiftCode='"+supplier.getSwiftcode()+"',bankcountry='"+getCountryname(supplier.getBankcountry())+"' where id='"+supplier.getSupplierid()+"'";
				System.out.println(" edit buyer query ");

				session.createSQLQuery(sql).executeUpdate();

				success= true;
				System.out.println("duplicat t buyer check "+success);


			}else if(!checkDuplicateBuyer(supplier.getSuppliername())) {
				sql="update  tbsupplier set name='"+supplier.getSuppliername()+"', suppliercode='"+supplier.getSuppliercode()+"', SupplierAddress='"+supplier.getSupplieraddress()+"', ConsigneeAddress='"+supplier.getConsigneeAddress()+"', ContactPerson='"+supplier.getContcatPerson()+"', NutifyAddress='"+supplier.getNotifyAddress()+"', SupplierCountry='"+getCountryname(supplier.getCountry())+"', Telephone='"+supplier.getTelephone()+"', MobileNo='"+supplier.getMobile()+"', Fax='"+supplier.getFax()+"', Email='"+supplier.getEmail()+"', SkypeId='"+supplier.getSkypeid()+"', BankName='"+supplier.getBankname()+"', BankAddress='"+supplier.getBankaddress()+"',AccountsNo='"+supplier.getAccountno()+"',AccountsName='"+supplier.getAccountname()+"', SwiftCode='"+supplier.getSwiftcode()+"',bankcountry='"+getCountryname(supplier.getBankcountry())+"' where id='"+supplier.getSupplierid()+"'";
				System.out.println(" edit buyer query ");

				session.createSQLQuery(sql).executeUpdate();


				success= true;
				System.out.println("duplicat t buyer check "+success);

			}

			tx.commit();


			System.out.println("duplicat t buyer check "+success);


			return success;


		}
		catch(Exception e){

			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		}

		finally {
			session.close();
		}

		return success;


	}

	@Override
	public List<SupplierModel> getAllSupplier() {
		String countryname="";
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<SupplierModel> supplier=new ArrayList<SupplierModel>();

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select id, name, suppliercode, supplieraddress, ConsigneeAddress, NutifyAddress,isnull((select concat(countryname,'*',autoId) from TbCountryInfo where CountryName=SupplierCountry),'') as SupplierCountry, Telephone, MobileNo, Fax, Email, SkypeId, BankName, BankAddress, SwiftCode,isnull((select concat(countryname,'*',autoId) from TbCountryInfo where CountryName=BankCountry),'') as  BankCount from tbsupplier order by id";
			System.out.println(" buyer details query ");

			List<?> list = session.createSQLQuery(sql).list();


			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				supplier.add(new SupplierModel(element[0].toString(),element[1].toString(),element[2].toString(),element[3].toString(),element[4].toString(),element[5].toString(),element[6].toString(),element[7].toString(),element[8].toString(),element[9].toString(),element[10].toString(),element[11].toString(),element[12].toString(),element[13].toString(),element[14].toString(),element[15].toString(),"","",""));

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
		return supplier;
	}


	//Factory Create

	@Override
	public String maxFactoryId() {
		// TODO Auto-generated method stub

		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		String query="";

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select isnull(max(FactoryId),0)+1 from TbFactoryInfo";
			System.out.println(" max supplier id");

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
	public boolean insertFactory(FactoryModel factory) {

		// TODO Auto-generated method stub

		Session session=HibernateUtil.openSession();
		Transaction tx=null;



		try{
			tx=session.getTransaction();
			tx.begin();

			if (checkDuplicateBuyer(factory.getFactoryname())) {
				return false;
			}else {
				String sql="insert into TbFactoryInfo (FactoryId, FactoryName, "
						+ "TelePhone, Mobile, Fax,"
						+ " Email, SkypeId, Address, "
						+ "BankName, BankAddress, SwiftCode, "
						+ "BankCountry, AccountsName, AccountsNo, "
						+ "BondLicense, EntryTime, UserId) values ('"+factory.getFactoryid()+"','"+factory.getFactoryname()+"',"
						+ "'"+factory.getTelephone()+"','"+factory.getMobile()+"',"
						+ "'"+factory.getFax()+"','"+factory.getEmail()+"','"+factory.getSkypeid()+"',"
						+ "'"+factory.getFactoryaddress()+"','"+factory.getBankname()+"','"+factory.getBankaddress()+"',"
						+ "'"+factory.getSwiftcode()+"','"+getCountryname(factory.getBankcountry())+"','"+factory.getAccountname()+"',"
						+ "'"+factory.getAccountno()+"','"+factory.getBondlicense()+"',GETDATE(),'"+factory.getUser()+"')";

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


	public boolean checkDuplicateFactory(String factory) {
		boolean exists=false;
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<String> countrylist=new ArrayList<>();

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select * from TbFactoryInfo where FactoryName='"+factory+"'";
			System.out.println(" buyername "+sql);

			List<?> list = session.createSQLQuery(sql).list();


			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				//Object[] element = (Object[]) iter.next();

				exists=true;

				break;

			}



			tx.commit();
			return exists;


		}
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
	public List<FactoryModel> FactoryDetails(String value) {
		String countryname="";
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<FactoryModel> supplier=new ArrayList<FactoryModel>();

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select FactoryId, FactoryName, TelePhone, Mobile, Fax, Email, SkypeId, Address, BankName, BankAddress, SwiftCode,isnull((select concat(countryname,'*',autoId) from TbCountryInfo where CountryName=BankCountry),'')  as BankCountry, AccountsName, AccountsNo, BondLicense FROM  TbFactoryInfo where FactoryId='"+value+"'";
			System.out.println(" buyer details query ");

			List<?> list = session.createSQLQuery(sql).list();


			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				supplier.add(new FactoryModel(element[0].toString(),element[1].toString(),element[2].toString(),element[3].toString(),element[4].toString(),element[5].toString(),element[6].toString(),element[7].toString(),element[8].toString(),element[9].toString(),element[10].toString(),element[11].toString(),element[12].toString(),element[13].toString(),element[14].toString()));

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
		return supplier;
	}

	@Override
	public List<String> factorylist(String value) {
		String countryname="";
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<String> suppliers=new ArrayList<>();

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select concat(factoryname, '*',factoryid) as factoryname from tbfactoryinfo where factoryname like '%"+value+"%'";
			System.out.println(" check duplicate buyer query ");

			List<?> list = session.createSQLQuery(sql).list();


			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				//Object[] element = (Object[]) iter.next();

				suppliers.add(iter.next().toString());

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
		return suppliers;
	}

	@Override
	public boolean editFactory(FactoryModel factory) {

		// TODO Auto-generated method stub
		boolean success=false;
		//success=checkDuplicateBuyer(buyer.getBuyername());


		Session session=HibernateUtil.openSession();
		Transaction tx=null;


		String sql="";


		try{
			tx=session.getTransaction();
			tx.begin();




			if (checkDuplicateBuyer(factory.getFactoryname())) {
				sql="update  tbfactoryinfo set    TelePhone='"+factory.getTelephone()+"', Mobile='"+factory.getMobile()+"', Fax='"+factory.getFax()+"', Email='"+factory.getEmail()+"', SkypeId='"+factory.getSkypeid()+"', Address='"+factory.getFactoryaddress()+"', BankName='"+factory.getBankname()+"', BankAddress='"+factory.getBankaddress()+"', SwiftCode='"+factory.getSwiftcode()+"', BankCountry='"+getCountryname(factory.getBankcountry())+"', AccountsName='"+factory.getAccountname()+"', AccountsNo='"+factory.getAccountno()+"', BondLicense='"+factory.getBondlicense()+"' where FactoryId='"+factory.getFactoryid()+"'";
				System.out.println(" edit buyer query ");

				session.createSQLQuery(sql).executeUpdate();

				success= true;
				System.out.println("duplicat t buyer check "+success);


			}else if(!checkDuplicateBuyer(factory.getFactoryname())) {
				sql="update  tbfactoryinfo set   FactoryName='"+factory.getFactoryname()+"', TelePhone='"+factory.getTelephone()+"', Mobile='"+factory.getMobile()+"', Fax='"+factory.getFax()+"', Email='"+factory.getEmail()+"', SkypeId='"+factory.getSkypeid()+"', Address='"+factory.getFactoryaddress()+"', BankName='"+factory.getBankname()+"', BankAddress='"+factory.getBankaddress()+"', SwiftCode='"+factory.getSwiftcode()+"', BankCountry='"+getCountryname(factory.getBankcountry())+"', AccountsName='"+factory.getAccountname()+"', AccountsNo='"+factory.getAccountno()+"', BondLicense='"+factory.getBondlicense()+"' where FactoryId='"+factory.getFactoryid()+"'";
				System.out.println(" edit buyer query ");

				session.createSQLQuery(sql).executeUpdate();


				success= true;
				System.out.println("duplicat t buyer check "+success);

			}

			tx.commit();


			System.out.println("duplicat t buyer check "+success);


			return success;


		}
		catch(Exception e){

			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		}

		finally {
			session.close();
		}

		return success;


	}

	@Override
	public List<FactoryModel> getAllFactories() {
		String countryname="";
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<FactoryModel> factory=new ArrayList<FactoryModel>();

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="SELECT FactoryId, FactoryName, TelePhone, Mobile, Fax, Email, SkypeId, Address, BankName, BankAddress, SwiftCode, BankCountry, AccountsName, AccountsNo, BondLicense  FROM  TbFactoryInfo";
			System.out.println(" buyer details query ");

			List<?> list = session.createSQLQuery(sql).list();


			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				factory.add(new FactoryModel(element[0].toString(),element[1].toString(),element[2].toString(),element[3].toString(),element[4].toString(),element[5].toString(),element[6].toString(),element[7].toString(),element[8].toString(),element[9].toString(),element[10].toString(),element[11].toString(),element[12].toString(),element[13].toString(),element[14].toString()));

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
		return factory;
	}

	@Override
	public String maxCourierId() {
		// TODO Auto-generated method stub

		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		String query="";

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select isnull(max(id),0)+1 from tbCourier";
			System.out.println(" max supplier id");

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
	public boolean insertCourier(CourierModel courier) {

		// TODO Auto-generated method stub

		Session session=HibernateUtil.openSession();
		Transaction tx=null;



		try{
			tx=session.getTransaction();
			tx.begin();

			if (checkDuplicateCourier(courier.getCouriername())) {
				return false;
			}else {
				String sql="insert into tbCourier (name, CourierCode, CourierAddress, "
						+ "ConsigneeAddress, NutifyAddress, CourierCountry, "
						+ "Telephone, MobileNo, Fax, Email, SkypeId, BankName,"
						+ " BankAddress, SwiftCode, BankCountry, EntryTime, UserId) values"
						+ " ('"+courier.getCouriername()+"',"
						+ "'"+courier.getCouriercode()+"','"+courier.getCourierAddress()+"',"
						+ "'"+courier.getConsigneeAddress()+"','"+courier.getNotifyAddress()+"',"
						+ "'"+getCountryname(courier.getCountry())+"','"+courier.getTelephone()+"',"
						+ "'"+courier.getMobile()+"','"+courier.getFax()+"','"+courier.getEmail()+"',"
						+ "'"+courier.getSkypeid()+"','"+courier.getBankname()+"','"+courier.getBankaddress()+"',"
						+ "'"+courier.getSwiftcode()+"','"+getCountryname(courier.getBankcountry())+"',GETDATE(),'"+courier.getUser()+"')";

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


	public boolean checkDuplicateCourier(String buyername) {
		boolean exists=false;
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<String> countrylist=new ArrayList<>();

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select * from tbCourier where name='"+buyername+"'";
			System.out.println(" buyername "+sql);

			List<?> list = session.createSQLQuery(sql).list();


			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				//Object[] element = (Object[]) iter.next();

				exists=true;
				existsbuyer=true;
				break;

			}



			tx.commit();
			return exists;


		}
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
	public List<String> courierList(String value) {
		String countryname="";
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<String> Buyers=new ArrayList<>();

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select concat(name, '*',id) as buyername from tbCourier where name like '%"+value+"%'";
			System.out.println(" check duplicate buyer query ");

			List<?> list = session.createSQLQuery(sql).list();


			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				//Object[] element = (Object[]) iter.next();

				Buyers.add(iter.next().toString());

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
	public List<CourierModel> CourierDetails(String value) {
		String countryname="";
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<CourierModel> Buyers=new ArrayList<CourierModel>();

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select id, name, couriercode, courierAddress, ConsigneeAddress, NutifyAddress,isnull((select concat(countryname,'*',autoId) from TbCountryInfo where CountryName=courierCountry),'') as BuyerCountry, Telephone, MobileNo, Fax, Email, SkypeId, BankName, BankAddress, SwiftCode,isnull((select concat(countryname,'*',autoId) from TbCountryInfo where CountryName=BankCountry),'') as  BankCount from tbcourier where id='"+value+"'";
			System.out.println(" buyer details query ");

			List<?> list = session.createSQLQuery(sql).list();


			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				Buyers.add(new CourierModel(element[0].toString(),element[1].toString(),element[2].toString(),element[3].toString(),element[4].toString(),element[5].toString(),element[6].toString(),element[7].toString(),element[8].toString(),element[9].toString(),element[10].toString(),element[11].toString(),element[12].toString(),element[13].toString(),element[14].toString(),element[15].toString()));

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
	public boolean editCourier(CourierModel courier) {

		// TODO Auto-generated method stub
		boolean success=false;
		//success=checkDuplicateBuyer(buyer.getBuyername());


		Session session=HibernateUtil.openSession();
		Transaction tx=null;


		String sql="";


		try{
			tx=session.getTransaction();
			tx.begin();




			if (checkDuplicateCourier(courier.getCouriername())) {
				sql="update  tbcourier set  couriercode='"+courier.getCouriercode()+"', courieraddress='"+courier.getCourierAddress()+"', ConsigneeAddress='"+courier.getConsigneeAddress()+"', NutifyAddress='"+courier.getNotifyAddress()+"', couriercountry='"+getCountryname(courier.getCountry())+"', Telephone='"+courier.getTelephone()+"', MobileNo='"+courier.getMobile()+"', Fax='"+courier.getFax()+"', Email='"+courier.getEmail()+"', SkypeId='"+courier.getSkypeid()+"', BankName='"+courier.getBankname()+"', BankAddress='"+courier.getBankaddress()+"', SwiftCode='"+courier.getSwiftcode()+"',bankcountry='"+getCountryname(courier.getBankcountry())+"' where id='"+courier.getCourierid()+"'";
				System.out.println(" edit courier query ");

				session.createSQLQuery(sql).executeUpdate();

				success= true;
				System.out.println("duplicat t buyer check "+success);


			}else if(!checkDuplicateCourier(courier.getCouriername())) {
				sql="update  tbcourier set name='"+courier.getCouriername()+"', couriercode='"+courier.getCouriercode()+"', courieraddress='"+courier.getCourierAddress()+"', ConsigneeAddress='"+courier.getConsigneeAddress()+"', NutifyAddress='"+courier.getNotifyAddress()+"', couriercountry='"+getCountryname(courier.getCountry())+"', Telephone='"+courier.getTelephone()+"', MobileNo='"+courier.getMobile()+"', Fax='"+courier.getFax()+"', Email='"+courier.getEmail()+"', SkypeId='"+courier.getSkypeid()+"', BankName='"+courier.getBankname()+"', BankAddress='"+courier.getBankaddress()+"', SwiftCode='"+courier.getSwiftcode()+"',bankcountry='"+getCountryname(courier.getBankcountry())+"' where id='"+courier.getCourierid()+"'";
				System.out.println(" edit courier query ");

				session.createSQLQuery(sql).executeUpdate();


				success= true;
				System.out.println("duplicat t buyer check "+success);

			}

			tx.commit();


			System.out.println("duplicat t buyer check "+success);


			return success;


		}
		catch(Exception e){

			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		}

		finally {
			session.close();
		}

		return success;


	}

	@Override
	public List<CourierModel> getAllCouriers() {
		String countryname="";
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		List<CourierModel> Buyers=new ArrayList<CourierModel>();

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select id, name, couriercode, courierAddress, ConsigneeAddress, NutifyAddress,isnull((select concat(countryname,'*',autoId) from TbCountryInfo where CountryName=courierCountry),'') as CourierCountry, Telephone, MobileNo, Fax, Email, SkypeId, BankName, BankAddress, SwiftCode,isnull((select concat(countryname,'*',autoId) from TbCountryInfo where CountryName=BankCountry),'') as  BankCount from tbCourier order by id";
			System.out.println(" buyer details query ");

			List<?> list = session.createSQLQuery(sql).list();


			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				Buyers.add(new CourierModel(element[0].toString(),element[1].toString(),element[2].toString(),element[3].toString(),element[4].toString(),element[5].toString(),element[6].toString(),element[7].toString(),element[8].toString(),element[9].toString(),element[10].toString(),element[11].toString(),element[12].toString(),element[13].toString(),element[14].toString(),element[15].toString()));

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



	//Brand Create
	@Override
	public boolean saveBrand(Brand brand) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="insert into tbbrands (name,brandCode,entrytime,UserId) values('"+brand.getBrandName()+"','"+brand.getBrandCode()+"', CURRENT_TIMESTAMP,'"+brand.getUserId()+"')";
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
	public boolean editBrand(Brand brand) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="update tbbrands set name='"+brand.getBrandName()+"',brandCode='"+brand.getBrandCode()+"',entrytime=CURRENT_TIMESTAMP,UserId='"+brand.getUserId()+"' where id= '"+brand.getBrandId()+"'";
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
	public List<Brand> getBrandList() {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<Brand> dataList=new ArrayList<Brand>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select id,name,brandCode,UserId from tbbrands where trash='0' order by name";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();

				dataList.add(new Brand(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString()));
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
	public boolean isBrandExist(Brand brand) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select id,name,brandCode,UserId from tbbrands where name='"+brand.getBrandName()+"' and id !='"+brand.getBrandId()+"'";

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


	//Fabrics Item Create
	@Override
	public boolean saveFabricsItem(FabricsItem fabricsItem) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="insert into TbFabricsItem(ItemName,refferance,unitId,entrytime,UserId) values('"+fabricsItem.getFabricsItemName()+"','"+fabricsItem.getReference()+"','"+fabricsItem.getUnitId()+"',current_timestamp,'"+fabricsItem.getUserId()+"')";
			session.createSQLQuery(sql).executeUpdate();

			String itemId = "0";
			sql = "select max(id) from TbFabricsItem where itemName='"+fabricsItem.getFabricsItemName()+"'";
			List<?> list = session.createSQLQuery(sql).list();
			if(list.size()>0) {
				itemId = list.get(0).toString();
			}
			sql = "insert into tbItemUnits (unitId,unitQty,itemId,itemType,entryTime,createBy) values('"+fabricsItem.getUnitId()+"','1','"+itemId+"','"+ItemType.FABRICS.getType()+"',current_timestamp,'"+fabricsItem.getUserId()+"');";
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
	public boolean editFabricsItem(FabricsItem fabricsItem) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="update TbFabricsItem set itemName='"+fabricsItem.getFabricsItemName()+"',refferance='"+fabricsItem.getReference()+"',unitId='"+fabricsItem.getUnitId()+"' where id='"+fabricsItem.getFabricsItemId()+"'";
			session.createSQLQuery(sql).executeUpdate();

			sql="update tbItemUnits set unitId='"+fabricsItem.getUnitId()+"' where itemId='"+fabricsItem.getFabricsItemId()+"' and unitQty='1' and itemType='"+ItemType.FABRICS.getType()+"'";
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
	public FabricsItem getFabricsItem(String fabricsItemId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		FabricsItem fabricsItem= null;
		List<Unit> unitList = new ArrayList<>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select id,ItemName,refferance,unitId,UserId from TbFabricsItem where id='"+fabricsItemId+"' order by ItemName";
			List<?> list = session.createSQLQuery(sql).list();
			if(list.size()>0)
			{	
				Object[] element = (Object[]) list.get(0);
				fabricsItem = new FabricsItem(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(),element[4].toString());
			}

			sql="select iu.unitId,u.unitname,unitQty \r\n" + 
					"from tbItemUnits iu\r\n" + 
					"left join tbunits u\r\n" + 
					"on iu.unitId = u.Unitid\r\n" + 
					"where itemId='"+fabricsItemId+"' and itemType='"+ItemType.FABRICS.getType()+"' \r\n" + 
					"order by iu.autoId";
			list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();
				unitList.add(new Unit(element[0].toString(), element[1].toString(), Double.valueOf(element[2].toString())));
			}

			fabricsItem.setUnitList(unitList);
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
		return fabricsItem;
	}

	@Override
	public List<FabricsItem> getFabricsItemList() {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<FabricsItem> dataList=new ArrayList<FabricsItem>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select id,ItemName,refferance,unitId,UserId from TbFabricsItem where trash='0' order by ItemName";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();

				dataList.add(new FabricsItem(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(),element[4].toString()));
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
	public boolean isFabricsItemExist(FabricsItem fabricsItem) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select id,ItemName,refferance,UserId from TbFabricsItem where ItemName='"+fabricsItem.getFabricsItemName()+"' and id !='"+fabricsItem.getFabricsItemId()+"'";

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
	public boolean addItemUnits(Unit unit, String itemId, String itemType) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="delete from tbItemUnits where itemId='"+itemId+"' and itemType='"+itemType+"' and unitId='"+unit.getUnitId()+"'";
			session.createSQLQuery(sql).executeUpdate();

			sql = "insert into tbItemUnits (unitId,unitQty,itemId,itemType,entryTime,createBy) values('"+unit.getUnitId()+"','"+unit.getUnitQty()+"','"+itemId+"','"+itemType+"',current_timestamp,'"+unit.getUserId()+"');";
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
	public List<Unit> getItemUnitsList(String itemId, String itemType) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<Unit> dataList=new ArrayList<Unit>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select iu.unitId,u.unitname,unitQty \r\n" + 
					"from tbItemUnits iu\r\n" + 
					"left join tbunits u\r\n" + 
					"on iu.unitId = u.Unitid\r\n" + 
					"where itemId='"+itemId+"' and itemType='"+itemType+"' \r\n" + 
					"order by iu.autoId";
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();
				dataList.add(new Unit(element[0].toString(), element[1].toString(), Double.valueOf(element[2].toString())));
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

	//Accessories Item Create
	@Override
	public boolean saveAccessoriesItem(AccessoriesItem accessoriesItem) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="insert into TbAccessoriesItem (Itemname,ItemCode,unitId,entrytime,UserId) values('"+accessoriesItem.getAccessoriesItemName()+"','"+accessoriesItem.getAccessoriesItemCode()+"','"+accessoriesItem.getUnitId()+"', CURRENT_TIMESTAMP,'"+accessoriesItem.getUserId()+"')";
			session.createSQLQuery(sql).executeUpdate();

			String itemId = "0";
			sql = "select max(itemId) from TbAccessoriesItem where itemName='"+accessoriesItem.getAccessoriesItemName()+"'";
			List<?> list = session.createSQLQuery(sql).list();
			if(list.size()>0) {
				itemId = list.get(0).toString();
			}
			sql = "insert into tbItemUnits (unitId,unitQty,itemId,itemType,entryTime,createBy) values('"+accessoriesItem.getUnitId()+"','1','"+itemId+"','"+ItemType.ACCESSORIES.getType()+"',current_timestamp,'"+accessoriesItem.getUserId()+"');";
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
	public boolean editAccessoriesItem(AccessoriesItem accessoriesItem) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="update TbAccessoriesItem set Itemname='"+accessoriesItem.getAccessoriesItemName()+"',Itemcode='"+accessoriesItem.getAccessoriesItemCode()+"',unitId='"+accessoriesItem.getUnitId()+"' where itemid='"+accessoriesItem.getAccessoriesItemId()+"'";
			session.createSQLQuery(sql).executeUpdate();

			sql="update tbItemUnits set unitId='"+accessoriesItem.getUnitId()+"' where itemId='"+accessoriesItem.getAccessoriesItemId()+"' and unitQty='1' and itemType='"+ItemType.ACCESSORIES.getType()+"'";
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
	public AccessoriesItem getAccessoriesItem(String accessoriesItemId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		AccessoriesItem accessoriesItem= null;
		List<Unit> unitList = new ArrayList<>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select itemId,itemName,itemCode,unitId,userid from TbAccessoriesItem where itemId='"+accessoriesItemId+"' order by ItemName";
			List<?> list = session.createSQLQuery(sql).list();
			if(list.size()>0)
			{	
				Object[] element = (Object[]) list.get(0);
				accessoriesItem = new AccessoriesItem(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString());
			}

			sql="select iu.unitId,u.unitname,unitQty \r\n" + 
					"from tbItemUnits iu\r\n" + 
					"left join tbunits u\r\n" + 
					"on iu.unitId = u.Unitid\r\n" + 
					"where itemId='"+accessoriesItemId+"' and itemType='"+ItemType.ACCESSORIES.getType()+"' \r\n" + 
					"order by iu.autoId";
			list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();
				unitList.add(new Unit(element[0].toString(), element[1].toString(), Double.valueOf(element[2].toString())));
			}

			accessoriesItem.setUnitList(unitList);
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
		return accessoriesItem;
	}

	@Override
	public List<AccessoriesItem> getAccessoriesItemList() {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<AccessoriesItem> dataList=new ArrayList<AccessoriesItem>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select itemId,itemName,itemCode,unitId,userid from TbAccessoriesItem where trash='0' order by itemName";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();

				dataList.add(new AccessoriesItem(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString()));
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
	public boolean isAccessoriesItemExist(AccessoriesItem accessoriesItem) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select itemId,itemName,itemCode,userid from TbAccessoriesItem where itemName='"+accessoriesItem.getAccessoriesItemName()+"' and itemId !='"+accessoriesItem.getAccessoriesItemId()+"'";

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
	public boolean saveStyleItem(StyleItem styleItem) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="insert into tbItemDescription (Itemname,ItemCode,entrytime,UserId) values('"+styleItem.getStyleItemName()+"','"+styleItem.getStyleItemCode()+"', CURRENT_TIMESTAMP,'"+styleItem.getUserId()+"')";
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
	public boolean editStyleItem(StyleItem styleItem) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="update tbItemDescription set Itemname='"+styleItem.getStyleItemName()+"',Itemcode='"+styleItem.getStyleItemCode()+"' where itemid='"+styleItem.getStyleItemId()+"'";
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
	public List<StyleItem> getStyleItemList() {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<StyleItem> dataList=new ArrayList<StyleItem>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select itemId,itemName,itemCode,userid from tbItemDescription where trash='0' order by itemName";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();

				dataList.add(new StyleItem(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString()));
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
	public boolean isStyleItemExist(StyleItem styleItem) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select itemId,itemName,itemCode,userid from tbItemDescription where itemName='"+styleItem.getStyleItemName()+"' and itemId !='"+styleItem.getStyleItemId()+"'";

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


	//Unit Create
	@Override
	public boolean saveUnit(Unit unit) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="insert into tbUnits(unitname, unitvalue,entryTime,userId) values('"+unit.getUnitName()+"','"+unit.getUnitValue()+"',CURRENT_TIMESTAMP,'"+unit.getUserId()+"');";
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
	public boolean editUnit(Unit unit) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="update tbUnits set unitname = '"+unit.getUnitName()+"', unitvalue='"+unit.getUnitValue()+"',entryTime=CURRENT_TIMESTAMP,userId='"+unit.getUserId()+"' where unitId='"+unit.getUnitId()+"';";
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
	public List<Unit> getUnitList() {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<Unit> dataList=new ArrayList<Unit>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select unitId,unitName,unitValue,UserId from tbUnits where trash='0' order by unitName";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();

				dataList.add(new Unit(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString()));
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
	public boolean isUnitExist(Unit unit) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select unitId,unitName,unitValue,UserId from tbUnits where unitName='"+unit.getUnitName()+"' and unitId != '"+unit.getUnitId()+"'";

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
	public boolean saveColor(Color color) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="insert into tbColors (colorName,colorCode,entrytime,UserId) values('"+color.getColorName()+"','"+color.getColorCode()+"', CURRENT_TIMESTAMP,'"+color.getUserId()+"')";
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
	public boolean editColor(Color color) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="update tbColors set colorName='"+color.getColorName()+"',colorCode='"+color.getColorCode()+"',entrytime=CURRENT_TIMESTAMP,UserId='"+color.getUserId()+"' where colorId= '"+color.getColorId()+"'";
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
	public List<Color> getColorList() {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<Color> dataList=new ArrayList<Color>();
		try{
			tx=session.getTransaction();
			tx.begin();
			
			String sql="select colorId,colorName,colorCode,UserId from tbColors where trash='0' order by colorName";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();

				dataList.add(new Color(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString()));
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
	public boolean isColorExist(Color color) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select colorId,colorName,colorCode,UserId from tbColors where colorName='"+color.getColorName()+"' and colorId !='"+color.getColorId()+"'";

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
	public boolean saveLocalItem(LocalItem localItem) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="insert into TbLocalItem (Itemname,ItemCode,entrytime,UserId) values('"+localItem.getLocalItemName()+"','"+localItem.getLocalItemCode()+"', CURRENT_TIMESTAMP,'"+localItem.getUserId()+"')";
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
	public boolean editLocalItem(LocalItem localItem) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="update TbLocalItem set Itemname='"+localItem.getLocalItemName()+"',ItemCode='"+localItem.getLocalItemCode()+"',entrytime=CURRENT_TIMESTAMP,UserId='"+localItem.getUserId()+"' where itemId= '"+localItem.getLocalItemId()+"'";
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
	public List<LocalItem> getLocalItemList() {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<LocalItem> dataList=new ArrayList<LocalItem>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select itemId,Itemname,ItemCode,UserId from TbLocalItem order by itemName";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();

				dataList.add(new LocalItem(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString()));
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
	public boolean isLocalItemExist(LocalItem localItem) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select itemId,Itemname,ItemCode,UserId from TbLocalItem where Itemname='"+localItem.getLocalItemName()+"' and itemId !='"+localItem.getLocalItemId()+"'";

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
	public boolean saveParticularItem(ParticularItem particularItem) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="insert into TbParticularItemInfo (name,entrytime,UserId) values('"+particularItem.getParticularItemName()+"', CURRENT_TIMESTAMP,'"+particularItem.getUserId()+"')";
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
	public boolean editParticularItem(ParticularItem particularItem) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="update TbParticularItemInfo set name='"+particularItem.getParticularItemName()+"',entrytime=CURRENT_TIMESTAMP,UserId='"+particularItem.getUserId()+"' where AutoId= '"+particularItem.getParticularItemId()+"'";
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
	public List<ParticularItem> getParticularItemList() {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<ParticularItem> dataList=new ArrayList<ParticularItem>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select autoId,name,UserId from TbParticularItemInfo where trash='0'  order by name";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();

				dataList.add(new ParticularItem(element[0].toString(), element[1].toString(), element[2].toString()));
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
	public boolean isParticularItemExist(ParticularItem particularItem) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select autoId,name,UserId from TbParticularItemInfo where name='"+particularItem.getParticularItemName()+"' and autoId !='"+particularItem.getParticularItemId()+"'";

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
	public boolean saveCountry(Country country) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="insert into TbCountryInfo (countryName,entrytime,UserId) values('"+country.getCountryName()+"', CURRENT_TIMESTAMP,'"+country.getUserId()+"')";
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
	public boolean editCountry(Country country) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="update TbCountryInfo set countryName='"+country.getCountryName()+"',entrytime=CURRENT_TIMESTAMP,UserId='"+country.getUserId()+"' where AutoId= '"+country.getCountryId()+"'";
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
	public List<Country> getCountryList() {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<Country> dataList=new ArrayList<Country>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select autoId,countryName,UserId from TbCountryInfo order by countryName";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();

				dataList.add(new Country(element[0].toString(), element[1].toString(), element[2].toString()));
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
	public boolean isCountryExist(Country country) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select autoId,countryName,UserId from TbCountryInfo where countryName='"+country.getCountryName()+"' and autoId !='"+country.getCountryId()+"'";

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
	public boolean saveSampleType(SampleType sampleType) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="insert into TbSampleTypeInfo (name,entrytime,UserId,trash) values('"+sampleType.getSampleTypeName()+"', CURRENT_TIMESTAMP,'"+sampleType.getUserId()+"',0)";
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
	public boolean editSampleType(SampleType sampleType) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="update TbSampleTypeInfo set name='"+sampleType.getSampleTypeName()+"',entrytime=CURRENT_TIMESTAMP,UserId='"+sampleType.getUserId()+"' where AutoId= '"+sampleType.getSampleTypeId()+"'";
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
	public List<SampleType> getSampleTypeList() {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<SampleType> dataList=new ArrayList<SampleType>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select AutoId,name,UserId from TbSampleTypeInfo where trash=0 order by name";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();

				dataList.add(new SampleType(element[0].toString(), element[1].toString(), element[2].toString()));
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
	public boolean isSampleTypeExist(SampleType sampleType) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select AutoId,name,UserId from TbSampleTypeInfo where name='"+sampleType.getSampleTypeName()+"' and autoId !='"+sampleType.getSampleTypeId()+"'";

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
	public boolean saveDepartment(Department department) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="insert into TbDepartmentInfo (factoryId,departmentName,entrytime,UserId) values('"+department.getFactoryId()+"','"+department.getDepartmentName()+"', CURRENT_TIMESTAMP,'"+department.getUserId()+"')";
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
	public boolean editDepartment(Department department) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="update TbDepartmentInfo set factoryId ='"+department.getFactoryId()+"',departmentName='"+department.getDepartmentName()+"',entrytime=CURRENT_TIMESTAMP,UserId='"+department.getUserId()+"' where DepartmentId='"+department.getDepartmentId()+"'";
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
	public List<Department> getDepartmentList() {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<Department> dataList=new ArrayList<Department>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select departmentId,factoryId,(select factoryName from tbfactoryInfo where factoryId=di.factoryId) as factoryName,departmentName,userId from TbDepartmentInfo di order by factoryName,departmentName";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();

				dataList.add(new Department(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString()));
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
	public List<Department> getFactoryWiseDepartment(String factoryId) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<Department> dataList=new ArrayList<Department>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select DepartmentId,DepartmentName from TbDepartmentInfo where FactoryId='"+factoryId+"'";

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
	public boolean isDepartmentExist(Department department) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select departmentId,factoryId,departmentName,userId from TbDepartmentInfo where departmentName='"+department.getDepartmentName()+"' and departmentId != '"+department.getDepartmentId()+"' and factoryId = '"+department.getFactoryId()+"'";

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
	public boolean isMerchandiserExist(MerchandiserInfo v) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select * from TbMerchendiserInfo where MerchendiserName='"+v.getName()+"' and MerchendiserId != '"+v.getMerchendiserId()+"'";

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
	public boolean saveMerchandiser(MerchandiserInfo v) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="insert into TbMerchendiserInfo(MerchendiserName,TelePhone,Mobile,Fax,Email,SkypeId,Address,entrytime,UserId) values('"+v.getName()+"','"+v.getTelephone()+"','"+v.getMobile()+"','"+v.getFax()+"','"+v.getEmail()+"','"+v.getSkype()+"','"+v.getAddress()+"',current_timestamp,'"+v.getUserId()+"')";
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
	public List<MerchandiserInfo> getMerchandiserList() {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<MerchandiserInfo> dataList=new ArrayList<MerchandiserInfo>();
		try{
			tx=session.getTransaction();
			tx.begin();

			int i=1;
			String sql="select MerchendiserId,MerchendiserName,TelePhone,Mobile,Fax,Email,SkypeId,Address from TbMerchendiserInfo order by MerchendiserName";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();

				dataList.add(new MerchandiserInfo(Integer.toString(i),element[0].toString(), element[1].toString(), element[2].toString(),element[3].toString(),element[4].toString(),element[5].toString(),element[6].toString(),element[7].toString()));
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
		return dataList;
	}

	@Override
	public boolean editMerchandiser(MerchandiserInfo v) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="update TbMerchendiserInfo set MerchendiserName='"+v.getName()+"',TelePhone='"+v.getTelephone()+"',Mobile='"+v.getMobile()+"',Fax='"+v.getFax()+"',Email='"+v.getEmail()+"',SkypeId='"+v.getSkype()+"',Address='"+v.getAddress()+"',entrytime=current_timestamp where MerchendiserId='"+v.getMerchendiserId()+"'";
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
	public boolean isInchargeExist(InchargeInfo v) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select * from TbInchargeInfo where InchargeName='"+v.getName()+"' and InchargeId != '"+v.getInchargeId()+"'";

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
	public boolean saveIncharge(InchargeInfo v) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="insert into TbInchargeInfo(InchargeName,FactoryId,DepId,TelePhone,Mobile,Fax,Email,SkypeId,Address,entrytime,UserId) values('"+v.getName()+"','"+v.getFactoryId()+"','"+v.getDepId()+"','"+v.getTelephone()+"','"+v.getMobile()+"','"+v.getFax()+"','"+v.getEmail()+"','"+v.getSkype()+"','"+v.getAddress()+"',current_timestamp,'"+v.getUserId()+"')";
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
	public boolean editIncharge(InchargeInfo v) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="update TbInchargeInfo set InchargeName='"+v.getName()+"',FactoryId='"+v.getFactoryId()+"',DepId='"+v.getDepId()+"',TelePhone='"+v.getTelephone()+"',Mobile='"+v.getMobile()+"',Fax='"+v.getFax()+"',Email='"+v.getEmail()+"',SkypeId='"+v.getSkype()+"',Address='"+v.getAddress()+"',entrytime=current_timestamp,UserId='"+v.getUserId()+"' where InchargeId='"+v.getInchargeId()+"'";
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
	public List<InchargeInfo> getInchargeList() {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<InchargeInfo> dataList=new ArrayList<InchargeInfo>();
		try{
			tx=session.getTransaction();
			tx.begin();

			int i=1;
			String sql="select InchargeId,InchargeName,FactoryId,DepId,TelePhone,Mobile,Fax,Email,SkypeId,Address from TbInchargeInfo order by InchargeName";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();

				dataList.add(new InchargeInfo(Integer.toString(i),element[0].toString(), element[1].toString(), element[2].toString(),element[3].toString(),element[4].toString(),element[5].toString(),element[6].toString(),element[7].toString(),element[8].toString(),element[9].toString()));
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
		return dataList;
	}



	@Override
	public boolean saveLine(Line line) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="insert into tbLineCreate (factoryId,DepartmentId,lineName,entrytime,UserId) values('"+line.getFactoryId()+"','"+line.getDepartmentId()+"','"+line.getLineName()+"', CURRENT_TIMESTAMP,'"+line.getUserId()+"')";
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
	public boolean editLine(Line line) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="update tbLineCreate set factoryId ='"+line.getFactoryId()+"',departmentId='"+line.getDepartmentId()+"',lineName='"+line.getLineName()+"',entrytime=CURRENT_TIMESTAMP,UserId='"+line.getUserId()+"' where lineId='"+line.getLineId()+"'";
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
	public List<Line> getLineList() {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<Line> dataList=new ArrayList<Line>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select lineId,factoryId,(select factoryName from tbfactoryInfo where factoryId=lc.factoryId) as factoryName,departmentId,(select departmentName from TbDepartmentInfo where DepartmentId = lc.departmentId) as departmentName,lineName,userId from tblineCreate lc order by factoryName,departmentName,lineName";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();

				dataList.add(new Line(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString()));
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
	public List<Line> getDepartmentWiseLine(String departmentId) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<Line> dataList=new ArrayList<Line>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select lineId,LineName,DepartmentId from TbLineCreate where DepartmentId='"+departmentId+"'";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();

				dataList.add(new Line(element[0].toString(),element[1].toString(),element[2].toString()));
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
	public boolean isLineExist(Line line) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select departmentId,lineId,factoryId,lineName,userId from tbLineCreate where lineName='"+line.getLineName()+"' and lineId != '"+line.getLineId()+"' and factoryId = '"+line.getFactoryId()+"' and departmentId='"+line.getDepartmentId()+"'";

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
	public boolean saveStyleSize(Size size) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="insert into tbStyleSize (groupId,sizeName,sortingNo,entryTime,userid) values('"+size.getGroupId()+"','"+size.getSizeName()+"','"+size.getSizeSorting()+"', CURRENT_TIMESTAMP,'"+size.getUserId()+"')";
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
	public boolean editStyleSize(Size size) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="update tbStyleSize set groupId='"+size.getGroupId()+"',sizeName='"+size.getSizeName()+"',sortingNo='"+size.getSizeSorting()+"',entryTime= CURRENT_TIMESTAMP,userid='"+size.getUserId()+"' where id='"+size.getSizeId()+"'";
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
	public List<Size> getStyleSizeList() {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<Size> dataList=new ArrayList<Size>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select id,groupId,(select groupName from tbSizeGroup where id = s.groupId)as groupName,sizeName,sortingNo,userId from tbStyleSize s where s.trash='0' order by groupName,sortingNo";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();

				dataList.add(new Size(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString()));
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
	public boolean isStyleSizeExist(Size size) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select id,groupId,sizeName,sortingNo,userId from tbStyleSize where sizeName='"+size.getSizeName()+"' and groupId='"+size.getGroupId()+"' and id !='"+size.getSizeId()+"'";

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
	public boolean saveStyleSizeGroup(SizeGroup sizeGroup) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="insert into tbSizeGroup (groupName,entryTime,userid) values('"+sizeGroup.getGroupName()+"', CURRENT_TIMESTAMP,'"+sizeGroup.getUserId()+"')";
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
	public boolean editStyleSizeGroup(SizeGroup sizeGroup) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="update tbSizeGroup set groupName ='"+sizeGroup.getGroupName()+"',entrytime=CURRENT_TIMESTAMP,UserId='"+sizeGroup.getUserId()+"' where id='"+sizeGroup.getGroupId()+"'";
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
	public List<SizeGroup> getStyleSizeGroupList() {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<SizeGroup> dataList=new ArrayList<SizeGroup>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select id,groupName,userId from tbSizeGroup order by groupName";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();

				dataList.add(new SizeGroup(element[0].toString(), element[1].toString(), element[2].toString()));
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
	public boolean isStyleSizeGroupExist(SizeGroup sizeGroup) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select id,groupName,userId from tbSizeGroup where groupName='"+sizeGroup.getGroupName()+"' and id != '"+sizeGroup.getGroupId()+"'";

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
	public boolean saveWareHouse(WareHouse wareHouse) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="insert into TbWareHouseInfo (factoryId,wareHouseName,entrytime,UserId) values('"+wareHouse.getFactoryId()+"','"+wareHouse.getWareHouseName()+"', CURRENT_TIMESTAMP,'"+wareHouse.getUserId()+"')";
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
	public boolean editWareHouse(WareHouse wareHouse) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="update TbWareHouseInfo set factoryId ='"+wareHouse.getFactoryId()+"',wareHouseName='"+wareHouse.getWareHouseName()+"',entrytime=CURRENT_TIMESTAMP,UserId='"+wareHouse.getUserId()+"' where wareHouseId='"+wareHouse.getWareHouseId()+"'";
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
	public List<WareHouse> getWareHouseList() {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<WareHouse> dataList=new ArrayList<WareHouse>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select wareHouseId,factoryId,(select factoryName from tbfactoryInfo where factoryId=di.factoryId) as factoryName,wareHouseName,userId from TbWareHouseInfo di order by factoryName,wareHouseName";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();

				dataList.add(new WareHouse(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString()));
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
	public boolean isWareHouseExist(WareHouse wareHouse) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select wareHouseId,factoryId,wareHouseName,userId from TbWareHouseInfo where wareHouseName='"+wareHouse.getWareHouseName()+"' and wareHouseId != '"+wareHouse.getWareHouseId()+"' and factoryId = '"+wareHouse.getFactoryId()+"'";

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

	//Common get Methods
	@Override
	public List<Factory> getFactoryNameList(){
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<Factory> dataList=new ArrayList<Factory>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select FactoryId,FactoryName from TbFactoryInfo order by FactoryName";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();

				dataList.add(new Factory(element[0].toString().trim(), element[1].toString().trim()));
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
	public Unit getUnit(String unitId) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		Unit unit= null;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select unitId,unitName,unitValue,UserId from tbUnits where unitId= '"+unitId+"'";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();

				unit = new Unit(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString());
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
		return unit;
	}

	@Override
	public List<StoreGeneralCategory> getStoreCategoryList() {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<StoreGeneralCategory> dataList=new ArrayList<StoreGeneralCategory>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select headId,headTitle from tbStoreItemCatagory order by headTitle";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();

				dataList.add(new StoreGeneralCategory(element[0].toString().trim(), element[1].toString().trim()));
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

	// Designation create

	@Override
	public boolean saveDesignation(Designation designation) {
		Session session = HibernateUtil.openSession();
		Transaction tx = null;
		try {
			tx = session.getTransaction();
			tx.begin();
			String sql = "insert into TbDesignationInfo (DepartmentId, DesignationName, EntryTime, UserId) values ('" + designation.getDepartmentId()+ "','" + designation.getDesignation()+ "', CURRENT_TIMESTAMP,'"+ designation.getUserId() + "')";

			session.createSQLQuery(sql).executeUpdate();
			tx.commit();
			return true;

		} catch (Exception ee) {
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
	public List<Designation> getDesignationList() {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<Designation> dataList=new ArrayList<Designation>();
		try{
			tx=session.getTransaction();
			tx.begin();

			int i=1;
			String sql="select DepartmentId,(select b.DepartmentName from TbDepartmentInfo b where b.DepartmentId=a.DepartmentId) as DepartmentName, a.DesignationId, a.DesignationName from TbDesignationInfo a where trash=0 order by DepartmentId";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();

				dataList.add(new Designation(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString()));
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
		return dataList;
	}

	@Override
	public boolean editDesignation(Designation designation) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="update TbDesignationInfo set DepartmentId='"+designation.getDepartmentId()+"', DesignationName='"+designation.getDesignation()+"', EntryTime=current_timestamp, UserId='"+designation.getUserId()+"' where DesignationId='"+designation.getId()+"' ";
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
	public boolean isDesignationExist(Designation v) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select * from TbDesignationInfo where DepartmentId='"+v.getDepartmentId()+"' and DesignationName='"+v.getDesignation()+"'";

			List<?> list = session.createSQLQuery(sql).list();
			if(list.size()>0)
				return true;
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
	public boolean saveEmployee(Employee saveEmployee) {
		// TODO Auto-generated method stub
		Session session = HibernateUtil.openSession();
		Transaction tx = null;
		try {
			tx = session.getTransaction();
			tx.begin();
			String sql = "insert into TbEmployeeInfo (EmployeeCode, Name, CardNo, DesginationId, DepartmentId, LineId, Grade, JoinDate,"
					+ " EntryTime, UserId,religion, gender, email, contact, nationality, nationalid, birthdate) values"
					+ " ('" + saveEmployee.getEmployeeCode()+ "','" + saveEmployee.getEmployeeName()+ "','"+saveEmployee.getCardNo()+"',"
					+ "'"+saveEmployee.getDesignation()+"','"+saveEmployee.getDepartment()+"','"+saveEmployee.getLine()+"',"
					+ "'"+saveEmployee.getGrade()+"','"+saveEmployee.getJoinDate()+"', CURRENT_TIMESTAMP,'"+ saveEmployee.getUserId() + "',"
					+ "'"+saveEmployee.getReligion()+"','"+saveEmployee.getGender()+"','"+saveEmployee.getEmail()+"',"
					+ "'"+saveEmployee.getContact()+"','"+saveEmployee.getNationality()+"','"+saveEmployee.getNationalId()+"',"
					+ "'"+saveEmployee.getBirthDate()+"')";
//			System.err.println("sql : "+sql);
			session.createSQLQuery(sql).executeUpdate();
			tx.commit();
			return true;

		} catch (Exception ee) {
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
	public List<Employee> getEmployeeList() {
		String sql="";
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<Employee> dataList=new ArrayList<Employee>();
		try{
			tx=session.getTransaction();
			tx.begin();

			int i=1;
			//String sql="select a.AutoId,a.EmployeeCode, a.Name, a.CardNo, a.DepartmentId, (select b.DepartmentName from TbDepartmentInfo b where b.DepartmentId=a.DepartmentId) as DepartmentName, a.DesginationId, (select c.DesignationName from TbDesignationInfo c where c.DesignationId=a.DesginationId) as Designation, a.LineId, a.Grade, isnull(CONVERT(VARCHAR(50),JoinDate),'') as JoinDate from TbEmployeeInfo a";
			sql="select a.AutoId,a.EmployeeCode, a.Name, a.CardNo, a.DepartmentId, isnull((select b.DepartmentName from TbDepartmentInfo b where b.DepartmentId=a.DepartmentId),'') as DepartmentName, a.DesginationId, isnull((select c.DesignationName from TbDesignationInfo c where c.DesignationId=a.DesginationId),'') as Designation, a.LineId, a.Grade, isnull(CONVERT(VARCHAR(50),JoinDate),'') as JoinDate,a.religion,a.gender, a.email, a.contact, a.nationality, a.nationalid, isnull(CONVERT(VARCHAR(50),a.birthdate),'') as birthdate from TbEmployeeInfo a where trash=0";
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();

				dataList.add(new Employee(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(),
						element[4].toString(), element[5].toString(), element[6].toString(), element[7].toString(), element[8].toString(),
						element[9].toString(),element[10].toString(),element[11].toString(),element[12].toString(),element[13].toString(),
						element[14].toString(),element[15].toString(),element[16].toString(),element[17].toString()));
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
		return dataList;
	}

	@Override
	public boolean editEmployee(Employee editEmployee) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="update TbEmployeeInfo set Name='"+editEmployee.getEmployeeName()+"', CardNo='"+editEmployee.getCardNo()+"',"
					+ " DepartmentId='"+editEmployee.getDepartment()+"', DesginationId='"+editEmployee.getDesignation()+"',"
					+ " LineId='"+editEmployee.getLine()+"', Grade='"+editEmployee.getGrade()+"', JoinDate='"+editEmployee.getJoinDate()+"',"
					+ " EntryTime=current_timestamp, UserId='"+editEmployee.getUserId()+"',religion='"+editEmployee.getReligion()+"',"
					+ " gender='"+editEmployee.getGender()+"', email='"+editEmployee.getEmail()+"', contact='"+editEmployee.getContact()+"',"
					+ " nationality='"+editEmployee.getNationality()+"', nationalid='"+editEmployee.getNationalId()+"',"
					+ " birthdate='"+editEmployee.getBirthDate()+"' where EmployeeCode='"+editEmployee.getEmployeeCode()+"'";
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
	public Employee getEmployeeInfoByEmployeeCode(String employeeCode) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		Employee employeeInfo = null;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select AutoId,EmployeeCode,CardNo,Name,DepartmentId,DesginationId,LineId,Grade,isnull(CONVERT(VARCHAR(50),JoinDate),'') as JoinDate from TbEmployeeInfo where EmployeeCode= '"+employeeCode+"'";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				employeeInfo = new Employee(element[0].toString(), element[1].toString(), element[3].toString(), element[2].toString(), element[4].toString(), "", element[5].toString(), "", element[6].toString(), element[7].toString(), element[8].toString());
				
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
		return employeeInfo;
	}

	@Override
	public Employee getEmployeeInfo(String id) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		Employee employeeInfo = null;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select AutoId,EmployeeCode,CardNo,Name,DepartmentId,DesginationId,LineId,Grade,isnull(CONVERT(VARCHAR(50),JoinDate),'') as JoinDate from TbEmployeeInfo where AutoId= '"+id+"'";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				employeeInfo = new Employee(element[0].toString(), element[1].toString(), element[3].toString(), element[2].toString(), element[4].toString(), "", element[5].toString(), "", element[6].toString(), element[7].toString(), element[8].toString());
				
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
		return employeeInfo;
	}

	@Override
	public boolean isEmployeeExist(Employee v) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select EmployeeCode from TbEmployeeInfo where EmployeeCode='"+v.getEmployeeCode()+"'";

			List<?> list = session.createSQLQuery(sql).list();
			if(list.size()>0)
				return true;
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
	public boolean saveMachine(Machine saveMachine) {
		// TODO Auto-generated method stub
		Session session = HibernateUtil.openSession();
		Transaction tx = null;
		try {
			tx = session.getTransaction();
			tx.begin();
			String sql = "insert into TbMachineInfo (MachineName, Brand, ModelNo, Motor, OperatorId,FactoryId,DepartmentId,LineId,EntryTime, UserId) values ('" + saveMachine.getName()+ "','" + saveMachine.getBrand()+ "','"+saveMachine.getModelNo()+"','"+saveMachine.getMotor()+"','"+saveMachine.getEmployeeId()+"','"+saveMachine.getFactoryId()+"','"+saveMachine.getDepartmentId()+"','"+saveMachine.getLineId()+"', CURRENT_TIMESTAMP,'"+ saveMachine.getUserId() + "')";

			session.createSQLQuery(sql).executeUpdate();
			tx.commit();
			return true;

		} catch (Exception ee) {
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
	public List<Machine> getMachineList() {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<Machine> dataList=new ArrayList<Machine>();
		try{
			tx=session.getTransaction();
			tx.begin();

			int i=1;
			String sql="select a.MachineId, a.MachineName, a.Brand, a.ModelNo, a.Motor, a.OperatorId, (select b.Name from TbEmployeeInfo b where b.AutoId=a.OperatorId) as EmployeeName from TbMachineInfo a where trash=0";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();

				dataList.add(new Machine(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString(), element[4].toString(), element[5].toString(), element[6].toString()));
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
		return dataList;
	}

	@Override
	public boolean editMachine(Machine editMachine) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="update TbMachineInfo set MachineName='"+editMachine.getName()+"', Brand='"+editMachine.getBrand()+"', ModelNo='"+editMachine.getModelNo()+"', Motor='"+editMachine.getMotor()+"', OperatorId='"+editMachine.getEmployeeId()+"', EntryTime=current_timestamp, UserId='"+editMachine.getUserId()+"' where MachineId='"+editMachine.getMachineId()+"'";
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
	public boolean isMachineExist(Machine v) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select MachineName from TbMachineInfo where MachineName='"+v.getName()+"'";

			List<?> list = session.createSQLQuery(sql).list();
			if(list.size()>0)
				return true;
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
	public boolean isProcessExist(ProcessInfo v) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select ProcessName from TbProcessInfo where ProcessName='"+v.getProcessName()+"' and ProcessId!='"+v.getProcessId()+"'";

			List<?> list = session.createSQLQuery(sql).list();
			if(list.size()>0)
				return true;
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
	public boolean saveProcess(ProcessInfo v) {
		// TODO Auto-generated method stub
		Session session = HibernateUtil.openSession();
		Transaction tx = null;
		try {
			tx = session.getTransaction();
			tx.begin();
			String sql = "insert into TbProcessInfo (ProcessName,EntryTime, UserId) values ('" + v.getProcessName()+ "',CURRENT_TIMESTAMP,'"+ v.getUserId() + "')";

			session.createSQLQuery(sql).executeUpdate();
			tx.commit();
			return true;

		} catch (Exception ee) {
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
	public List<ProcessInfo> getProcessList() {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<ProcessInfo> dataList=new ArrayList<ProcessInfo>();
		try{
			tx=session.getTransaction();
			tx.begin();

			int i=1;
			String sql="select ProcessId,ProcessName from TbProcessInfo where trash=0 order by ProcessId";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();

				dataList.add(new ProcessInfo(element[0].toString(), element[1].toString()));
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
		return dataList;
	}

	@Override
	public boolean editProcess(ProcessInfo v) {
		Session session = HibernateUtil.openSession();
		Transaction tx = null;
		try {
			tx = session.getTransaction();
			tx.begin();
			String sql = "update TbProcessInfo set ProcessName='" + v.getProcessName()+ "',EntryTime=CURRENT_TIMESTAMP,UserId='"+ v.getUserId() + "' where ProcessId='"+v.getProcessId()+"'";

			session.createSQLQuery(sql).executeUpdate();
			tx.commit();
			return true;

		} catch (Exception ee) {
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
	public boolean saveBank(Bank bank) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="insert into tbBankInfo(bankName,branch,address,entryTime,entryBy) values('"+bank.getBankName()+"','"+bank.getBranchName()+"','"+bank.getAddress()+"',CURRENT_TIMESTAMP,'"+bank.getUserId()+"')";
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
	public boolean editBank(Bank bank) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="update tbBankInfo set bankName='"+bank.getBankName()+"',branch='"+bank.getBranchName()+"',address='"+bank.getAddress()+"' where id='"+bank.getId()+"'";
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
	public Bank getBankInfo(String id) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		Bank bank= null;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select id,bankName,branch,address,entryBy from tbBankInfo where id = '"+id+"'";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();

				bank = new Bank(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString());
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
		return bank;
	}

	@Override
	public List<Bank> getBankList() {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<Bank> dataList=new ArrayList<Bank>();
		try{
			tx=session.getTransaction();
			tx.begin();

			int i=1;
			String sql="select id,bankName,branch,address,entryBy from tbBankInfo";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();

				
				dataList.add(new Bank(element[0].toString(), element[1].toString(), element[2].toString(), element[3].toString()));
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
		return dataList;
	}

	@Override
	public boolean deleteFabricsItem(String fabricsId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="update TbFabricsItem set trash='1' where id='"+fabricsId+"'";
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
	public boolean hasDeletePermission(String userId,String linkName) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="select entry from Tbuseraccess where userId='"+userId+"' and sub=(select id from TbSubMenu where links='"+linkName+"') and entry='1'";
			System.out.println(sql);
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
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
	public boolean deleteAccessoriesItem(String itemId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="update TbAccessoriesItem set trash='1' where itemid='"+itemId+"'";
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
	public boolean deleteGermentsItem(String itemId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="update tbItemDescription set trash='1' where itemid='"+itemId+"'";
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
	public boolean deleteUnitItem(String itemId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="update tbunits set trash='1' where unitId='"+itemId+"'";
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
	public boolean deleteColorItem(String itemId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="update tbColors set trash='1' where colorId='"+itemId+"'";
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
	public boolean deleteCostingItem(String itemId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="update TbParticularItemInfo set trash='1' where autoId='"+itemId+"'";
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
	public boolean deleteBrandItem(String itemId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="update tbbrands set trash='1' where id='"+itemId+"'";
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
	public boolean deleteSize(String itemId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			String sql="update tbStyleSize set trash='1' where id='"+itemId+"'";
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
	public boolean deleteSampleItem(String sampleItemId) {
		// TODO Auto-generated method stub
		String sql="";
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			sql="update TbSampleTypeInfo set trash='1' where AutoId='"+sampleItemId+"'";
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
	public boolean deleteDesignition(String deptId) {
		// TODO Auto-generated method stub
		String sql="";
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			sql="update TbDesignationInfo set trash='1' where DesignationId='"+deptId+"'";
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
	public boolean deleteMachine(String machineId) {
		// TODO Auto-generated method stub
		String sql="";
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			sql="update TbMachineInfo set trash='1' where MachineId='"+machineId+"'";
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
	public boolean deleteEmployee(String empcode) {
		// TODO Auto-generated method stub
		String sql="";
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			sql="update TbEmployeeInfo set trash='1' where EmployeeCode='"+empcode+"'";
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
	public boolean deleteProcess(String processId) {
		// TODO Auto-generated method stub
		String sql="";
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			sql="update TbProcessInfo set trash='1' where ProcessId='"+processId+"'";
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



}
