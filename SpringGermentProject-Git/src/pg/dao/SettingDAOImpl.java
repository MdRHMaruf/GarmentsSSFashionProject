package pg.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pg.model.Ware;
import pg.model.WareInfo;
import pg.OrganizationModel.OrganizationInfo;
import pg.model.Menu;
import pg.model.MenuInfo;
import pg.model.Module;
import pg.model.ModuleInfo;
import pg.model.ModuleWiseMenu;
import pg.model.ModuleWiseMenuSubMenu;
import pg.model.Password;
import pg.model.SubMenuInfo;
import pg.model.UserAccessModule;
import pg.share.HibernateUtil;

@Repository
public class SettingDAOImpl implements SettingDAO {


	@Override
	public List<Module> getAllModuleName() {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<Module> query=new ArrayList<Module>();
		try{
			tx=session.getTransaction();
			tx.begin();


			List<?> list = session.createSQLQuery("select id,name,ware from Tbmodule").list();

			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				query.add(new Module(Integer.parseInt(element[0].toString()),element[1].toString(),Integer.parseInt(element[2].toString())));
			}

			tx.commit();
		}
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
	public List<WareInfo> getAllWareName() {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<WareInfo> query=new ArrayList<WareInfo>();
		try{
			tx=session.getTransaction();
			tx.begin();


			List<?> list = session.createSQLQuery("select id,name from ware").list();

			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				query.add(new WareInfo(Integer.parseInt(element[0].toString()),element[1].toString()));
			}

			tx.commit();
		}
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

	public boolean  addWare(Ware w) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<Menu> query=new ArrayList<Menu>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="insert into ware ("
					+ "name,"
					+ "phone,"
					+ "email,"
					+ "vat,"
					+ "theme,"
					+ "user,"
					+ "entrytime"
					+ ") values ("
					+ "'"+w.getName()+"',"
					+ "'"+w.getPhone()+"',"
					+ "'"+w.getEmail()+"',"
					+ "'"+w.getVat()+"',"
					+ "'"+w.getTheme()+"',"
					+ "'"+w.getUser()+"',NOW()"
					+ ")";

			session.createSQLQuery(sql).executeUpdate();
			tx.commit();
			return true;
		}
		catch(Exception e){

			if (tx != null) {
				tx.rollback();
				return false;
			}
			e.printStackTrace();
		}

		finally {
			session.close();
		}

		return false;
	}



	public boolean  addModule(ModuleInfo v) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="insert into Tbmodule ("
					+ "name,"
					+ "ware,"
					+ "user,"
					+ "status,"
					+ "trash,"
					+ "entrytime"
					+ ") values ("
					+ "'"+v.getName()+"',"
					+ "'"+v.getWare()+"',"
					+ "'"+v.getUser()+"',"
					+ "'"+v.getActive()+"','0',"
					+ "NOW()"
					+ ")";

			session.createSQLQuery(sql).executeUpdate();
			tx.commit();
			return true;
		}
		catch(Exception e){

			if (tx != null) {
				tx.rollback();
				return false;
			}
			e.printStackTrace();
		}

		finally {
			session.close();
		}

		return false;
	}

	public boolean  addMenu(MenuInfo v) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="insert into Tbmenu ("
					+ "name,"
					+ "module,"
					+ "ware,"
					+ "user,"
					+ "trash,"
					+ "entrytime"
					+ ") values ("
					+ "'"+v.getName()+"',"
					+ "'"+v.getModule()+"',"
					+ "(select ware from module where id='"+v.getModule()+"'),"
					+ "'"+v.getUser()+"','0',"
					+ "NOW()"
					+ ")";

			session.createSQLQuery(sql).executeUpdate();
			tx.commit();
			return true;
		}
		catch(Exception e){

			if (tx != null) {
				tx.rollback();
				return false;
			}
			e.printStackTrace();
		}

		finally {
			session.close();
		}

		return false;
	}


	public boolean  addSubMenu(SubMenuInfo v) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="insert into TbSubMenu ("
					+ "name,"
					+ "links,"
					+ "root,"
					+ "module,"
					+ "ware,"
					+ "user,"
					+ "trash,"
					+ "entrytime"
					+ ") values ("
					+ "'"+v.getName()+"',"
					+ "'"+v.getLinks()+"',"
					+ "'"+v.getMenu()+"',"
					+ "'"+v.getModule()+"',"
					+ "(select ware from Tbmodule where id='"+v.getModule()+"'),"
					+ "'"+v.getUser()+"','0',"
					+ "NOW()"
					+ ")";

			session.createSQLQuery(sql).executeUpdate();
			tx.commit();
			return true;
		}
		catch(Exception e){

			if (tx != null) {
				tx.rollback();
				return false;
			}
			e.printStackTrace();
		}

		finally {
			session.close();
		}

		return false;
	}

	@Override
	public List<ModuleWiseMenuSubMenu> getAllModuleWiseMenuSubMenuName(int user,String menulist) {

		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<ModuleWiseMenuSubMenu> query=new ArrayList<ModuleWiseMenuSubMenu>();
		try{
			tx=session.getTransaction();
			tx.begin();


			//String sql="select a.module as moduleid,a.id as menuId,b.id as submenuId,a.name,b.name as SubMenu from Tbmenu a join TbSubMenu b on a.id=b.root where b.module in("+menulist+")  group by b.module,a.name,b.name";
			String sql="select a.module as moduleid,a.id as menuId,b.id as submenuId,a.name,b.name as SubMenu from Tbmenu a join TbSubMenu b on a.id=b.root where a.module in("+menulist+")  order by a.module ";
			List<?> list = session.createSQLQuery(sql).list();

			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				query.add(new ModuleWiseMenuSubMenu(Integer.parseInt(element[0].toString()),Integer.parseInt(element[1].toString()),Integer.parseInt(element[2].toString()), "",element[3].toString(),element[4].toString(),""));
			}

			tx.commit();
		}
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
	public List<ModuleWiseMenuSubMenu> getAllModuleWiseSubmenu() {

		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<ModuleWiseMenuSubMenu> query=new ArrayList<ModuleWiseMenuSubMenu>();
		try{
			tx=session.getTransaction();
			tx.begin();


			String sql="select module,root,id,(select name from Tbmodule where id=TbSubMenu.module) as ModuleName,(select name from Tbmenu where id=root) as head,name,links from TbSubMenu order by root,module";
			List<?> list = session.createSQLQuery(sql).list();

			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				query.add(new ModuleWiseMenuSubMenu(Integer.parseInt(element[0].toString()),Integer.parseInt(element[1].toString()),Integer.parseInt(element[2].toString()), element[3].toString(),element[4].toString(),element[5].toString(),element[6].toString()));
			}

			tx.commit();
		}
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

	public boolean  addUser(Password v) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();

			String newUserId=getMaxId();

			String modulelist=v.getSelectedItemsModule();
			modulelist=modulelist.replace("[", "");
			modulelist=modulelist.replace("]", "");
			System.out.println("modulelist "+modulelist);
			String accesslit=v.getAccesslist();
			accesslit=accesslit.replace("[", "");
			accesslit=accesslit.replace("]", "");
			System.out.println("list "+accesslit);

			String warelist=v.getSelectedItemsWare();
			warelist=warelist.replace("[", "");
			warelist=warelist.replace("]", "");
			System.out.println("warelist "+warelist);

			String sql="insert into Tblogin ("
					+ "id,"
					+ "password,"
					+ "username,"
					+ "type,"
					+ "factoryId,"
					+ "departmentId,"
					+ "active,"
					+ "createby,"
					+ "entrytime"
					+ ") values ("
					+ "'"+newUserId+"',"
					+ "'"+v.getPassword()+"',"
					+ "'"+v.getUser()+"',"
					+ "'"+v.getType()+"',"
					+ "'"+v.getFactoryId()+"',"
					+ "'"+v.getDepartmentId()+"',"
					+ "'"+v.getActive()+"',"
					+ "'"+v.getUserId()+"',"
					+ "CURRENT_TIMESTAMP"
					+ ")";

			session.createSQLQuery(sql).executeUpdate();


			StringTokenizer s=new StringTokenizer(accesslit,",");
			while(s.hasMoreElements()) {
				String a=s.nextToken().trim();
				StringTokenizer s2=new StringTokenizer(a,":");
				while(s2.hasMoreElements()) {
					String moduleId=s2.nextToken();
					String headId=s2.nextToken();
					String subId=s2.nextToken();
					String add=s2.nextToken();
					String edit=s2.nextToken();
					String delete=s2.nextToken();
					String sqlpass="insert into Tbuseraccess ("
							+ "userId,"
							+ "head,"
							+ "sub,"
							+ "module,"
							+ "entry,"
							+ "modifty,"
							+ "del,"
							+ "trash,"
							+ "entrytime"
							+ ") "
							+ "values ("
							+ "'"+newUserId+"',"
							+ "'"+headId+"',"
							+ "'"+subId+"',"
							+ "'"+moduleId+"',"
							+ "'"+add+"',"
							+ "'"+edit+"',"
							+ "'"+delete+"',"
							+ "'0',"
							+ "CURRENT_TIMESTAMP"
							+ ")";

					session.createSQLQuery(sqlpass).executeUpdate();

				}
			}

			StringTokenizer s2=new StringTokenizer(modulelist,",");
			while(s2.hasMoreElements()) {
				String moduletoken=s2.nextToken();

				StringTokenizer s3=new StringTokenizer(moduletoken,":");
				while(s3.hasMoreElements()) {
					String wareId=s3.nextToken();
					String moduleId=s3.nextToken();
					String sqlpass="insert into Tbuser_access_module ("

						+ "userId,"
						+ "module_id,"
						+ "trash,"
						+ "entrytime"
						+ ") "
						+ "values ("
						+ "'"+newUserId+"',"
						+ "'"+moduleId+"',"
						+ "'0',"
						+ "CURRENT_TIMESTAMP"
						+ ")";

					session.createSQLQuery(sqlpass).executeUpdate();
				}


			}

			StringTokenizer s3=new StringTokenizer(warelist,",");
			while(s3.hasMoreElements()) {
				String wareId=s3.nextToken();
				String sqlware="insert into tbuser_access_ware ("
						+ "user,"
						+ "ware,"
						+ "trash,"
						+ "entrytime"
						+ ") "
						+ "values ("
						+ "'"+newUserId+"',"
						+ "'"+wareId+"',"
						+ "'0',"
						+ "NOW()"
						+ ")";

				session.createSQLQuery(sqlware).executeUpdate();
			}

			tx.commit();
			return true;
		}
		catch(Exception e){

			if (tx != null) {
				tx.rollback();
				return false;
			}
			e.printStackTrace();
		}

		finally {
			session.close();
		}

		return false;
	}

	private String getMaxId() {
		String id="";

		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();


			String sql="select (ISNULL(max(id),0)+1)as id from Tblogin ";
			List<?> list = session.createSQLQuery(sql).list();

			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				id = iter.next().toString();
				System.out.println("id "+id);

			}

			tx.commit();
		}
		catch(Exception e){

			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		}
		finally {
			session.close();
		}

		return id;
	}

	@Override
	public List<Menu> getAllModuleWiseMenu(int i) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<Menu> query=new ArrayList<Menu>();
		try{
			tx=session.getTransaction();
			tx.begin();


			List<?> list = session.createSQLQuery("select id,name from Tbmenu where module='"+i+"'").list();

			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				query.add(new Menu(Integer.parseInt(element[0].toString()),element[1].toString(),"",""));
			}

			tx.commit();
		}
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
	public List<ModuleWiseMenu> getAllModuleWiseMenu() {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<ModuleWiseMenu> query=new ArrayList<ModuleWiseMenu>();
		try{
			tx=session.getTransaction();
			tx.begin();


			List<?> list = session.createSQLQuery("select id,(select name from Tbmodule where id=Tbmenu.module) as Module,name from Tbmenu order by module").list();

			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				query.add(new ModuleWiseMenu(Integer.parseInt(element[0].toString()),element[1].toString(),element[2].toString()));
			}

			tx.commit();
		}
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
	public List<OrganizationInfo> getOrganization() {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<OrganizationInfo> dataList=new ArrayList<OrganizationInfo>();

		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select organizationId, organizationName, organizationContact, organizationAddress from tbOrganizationInfo";
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();

				dataList.add(new OrganizationInfo(element[0].toString(),element[1].toString(),element[2].toString(),element[3].toString()));
			}
			tx.commit();
		}
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
	public boolean editOrganization(OrganizationInfo v) {
		// TODO Auto-generated method stub
		Session session = HibernateUtil.openSession();
		Transaction tx = null;
		try {
			tx = session.getTransaction();
			tx.begin();
			
			String sql = "update tbOrganizationInfo set organizationName='"+v.getOrganizationName()+"', organizationContact='"+v.getOrganizationContact()+"', organizationAddress='"+v.getOrganizationAddress()+"' where organizationId='"+v.getOrganizationId()+"'";
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
	public boolean savenotice(String heading, String departs, String textbody, String filename,String userid) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		Session session = HibernateUtil.openSession();
		Transaction tx = null;
		try {
			tx = session.getTransaction();
			tx.begin();
			String depts[]=departs.split(",");
			
			int maxnoticeno=getMaxNoticeNo();
			
			for (int i = 0; i < depts.length; i++) {
				String sql = "insert into tbnotice(noticeno, noticeheader,noticebody, filename, accessabledepartments, entryby, entrytime) values('"+maxnoticeno+"','"+heading+"','"+textbody+"','"+filename+"','"+depts[i]+"','"+userid+"', CURRENT_TIMESTAMP)";
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
		}
		finally {
			session.close();
		}
		return false;
	
	}



	
	public int getMaxNoticeNo() {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<OrganizationInfo> dataList=new ArrayList<OrganizationInfo>();
		int maxno=0;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select isnull(max(noticeno),0)+1 as noticeno from tbnotice";
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				//Object[] element = (Object[]) iter.next();

				//dataList.add(new OrganizationInfo(element[0].toString(),element[1].toString(),element[2].toString(),element[3].toString()));
				maxno= Integer.parseInt(iter.next().toString());
			}
			tx.commit();
		}
		catch(Exception e){
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		}
		finally {
			session.close();
		}
		return maxno;
	}
	

}
