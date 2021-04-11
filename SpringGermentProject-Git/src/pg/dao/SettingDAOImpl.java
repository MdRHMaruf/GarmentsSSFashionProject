package pg.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.management.Query;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import noticeModel.noticeModel;
import pg.model.Ware;
import pg.model.WareInfo;
import pg.model.roleManagement;
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
import pg.share.FormId;
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


			List<?> list = session.createSQLQuery("select id,name from Tbmodule").list();

			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				query.add(new Module(Integer.parseInt(element[0].toString()),element[1].toString(),0));
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
					+ "fullname,"
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
					+ "'"+v.getFullName()+"',"
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
				String sql = "insert into tbnotice(noticeno, noticeheader,noticebody, filenames, accessabledepartments, entryby, entrytime) values('"+maxnoticeno+"','"+heading+"','"+textbody+"','"+filename+"','"+depts[i]+"','"+userid+"', CURRENT_TIMESTAMP)";
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



	@Override
	public List<noticeModel> getAllNoitice(String deptid,noticeModel nm) {

		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<noticeModel>query=new ArrayList<>();
		try{
			tx=session.getTransaction();
			tx.begin();

			//String sql="select isnull(max(CuttingReqId),0)+1 from TbCuttingRequisitionDetails";
			String sql="select distinct(noticeno) as id,(noticeheader) as header ,noticebody as body,filenames,CONVERT(varchar,CONVERT(DATE, entrytime))  from tbnotice where  (accessabledepartments='"+deptid+"' or accessabledepartments=0) ";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				query.add(new noticeModel(element[0].toString(),element[1].toString(),element[2].toString(),element[3].toString(),element[4].toString()));
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
	public List<noticeModel> getAllnoticesforSearch() {

		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<noticeModel>query=new ArrayList<>();
		try{
			tx=session.getTransaction();
			tx.begin();

			//String sql="select isnull(max(CuttingReqId),0)+1 from TbCuttingRequisitionDetails";
			String sql="select noticeno, noticeheader, noticebody from tbnotice group by noticeno, noticeheader, noticebody";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				query.add(new noticeModel(element[0].toString(),element[1].toString(),element[2].toString(),""));
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
	public JSONArray getNotificationList(String targetId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		JSONArray array=new JSONArray();
		JSONObject object;
		try{
			tx=session.getTransaction();
			tx.begin();

			//String sql="select isnull(max(CuttingReqId),0)+1 from TbCuttingRequisitionDetails";
			String sql="select n.id,l.username,n.subject,n.type,n.notificationContent,n.issueLinkId,nt.targetUserId,nt.targetSeen,n.createdTime  \r\n" + 
					"from tbNotificationTargets nt\r\n" + 
					"join tbNotification n\r\n" + 
					"on nt.notificationId = n.id\r\n"
					+ "left join Tblogin l\r\n" + 
					"on n.createdBy = l.id\r\n" + 
					"where nt.targetUserId= '"+2+"'";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				object = new JSONObject();
				object.put("notificationId", element[0].toString());
				object.put("createdBy", element[1].toString());
				object.put("subject", element[2].toString());
				object.put("type", element[3].toString());
				object.put("content", element[4].toString());
				object.put("issueLinkId", element[5].toString());
				object.put("targetUserId", element[6].toString());
				object.put("targetSeen", element[7].toString());
				object.put("createdTime", element[8].toString());
				array.add(object);
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

		return array;

	}

	@Override
	public JSONArray getUserList() {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		JSONArray array=new JSONArray();
		JSONObject object;
		try{
			tx=session.getTransaction();
			tx.begin();

			//String sql="select isnull(max(CuttingReqId),0)+1 from TbCuttingRequisitionDetails";
			String sql="select id,fullname,type,factoryId,departmentId from Tblogin";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				object = new JSONObject();
				object.put("id", element[0].toString());
				object.put("fullname", element[1].toString());
				object.put("type", element[2].toString());
				object.put("factoryId", element[3].toString());
				object.put("departmentId", element[4].toString());
				array.add(object);
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

		return array;

	}


	@Override
	public String saveGroup(String group) {
		// TODO Auto-generated method stub
		Session session = HibernateUtil.openSession();
		Transaction tx = null;
		try {
			tx = session.getTransaction();
			tx.begin();
			
			JSONParser jsonParser = new JSONParser();
			System.out.println(group);
			JSONObject groupObject = (JSONObject)jsonParser.parse(group);;
			
			String sql="select groupId,groupName from tbGroups where memberId in("+groupObject.get("members")+")";
			List<?> list = session.createSQLQuery(sql).list();
			
			if(list.size()==0) {
				sql="select isnull(max(groupId),0)+1 as maxGroupId from tbGroups";
				list = session.createSQLQuery(sql).list();
				
				int groupId = (int)list.get(0);
				
				
				String[] idList = groupObject.get("members").toString().split(",");
				for (String id: idList) {
					sql = "insert into tbGroups (groupId,groupName,memberId,entryTime,entryBy) values ('"+groupId+"','"+groupObject.get("groupName")+"','"+id+"',CURRENT_TIMESTAMP,'"+groupObject.get("userId")+"');";
					session.createSQLQuery(sql).executeUpdate();
				}
			}else {
				return "Members Already have another group";
			}
			
			tx.commit();
			return "success";

		} catch (Exception ee) {
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
	public String editGroup(String group) {
		// TODO Auto-generated method stub
		Session session = HibernateUtil.openSession();
		Transaction tx = null;
		try {
			tx = session.getTransaction();
			tx.begin();
			
			JSONParser jsonParser = new JSONParser();
			System.out.println(group);
			JSONObject groupObject = (JSONObject)jsonParser.parse(group);;
			
			String sql="select groupId,groupName from tbGroups where memberId in("+groupObject.get("members")+") and groupId != '"+groupObject.get("groupId")+"'";
			List<?> list = session.createSQLQuery(sql).list();
			
			if(list.size()==0) {
				sql = "delete from tbGroups where groupId = '"+groupObject.get("groupId")+"'";
				session.createSQLQuery(sql).executeUpdate();
				
				sql="select isnull(max(groupId),0)+1 as maxGroupId from tbGroups";
				list = session.createSQLQuery(sql).list();
				
				int groupId = (int)list.get(0);
				
				
				String[] idList = groupObject.get("members").toString().split(",");
				for (String id: idList) {
					sql = "insert into tbGroups (groupId,groupName,memberId,entryTime,entryBy) values ('"+groupId+"','"+groupObject.get("groupName")+"','"+id+"',CURRENT_TIMESTAMP,'"+groupObject.get("userId")+"');";
					session.createSQLQuery(sql).executeUpdate();
				}
			}else {
				return "Members Already have another group";
			}
			
			tx.commit();
			return "success";

		} catch (Exception ee) {
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
	public JSONArray getGroupList() {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		JSONArray array=new JSONArray();
		JSONObject object;
		try{
			tx=session.getTransaction();
			tx.begin();
			
			//String sql="select isnull(max(CuttingReqId),0)+1 from TbCuttingRequisitionDetails";
			String sql="select groupId,groupName from tbGroups group by groupId,groupName";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				object = new JSONObject();
				object.put("groupId", element[0].toString());
				object.put("groupName", element[1].toString());
				array.add(object);
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
		return array;
	}
	
	@Override
	public JSONArray getGroupMembers(String groupId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		JSONArray array=new JSONArray();
		JSONObject object;
		try{
			tx=session.getTransaction();
			tx.begin();
			
			//String sql="select isnull(max(CuttingReqId),0)+1 from TbCuttingRequisitionDetails";
			String sql="select g.autoId,groupId,groupName,memberId,l.fullname\r\n" + 
					"from tbGroups g\r\n" + 
					"left join Tblogin l\r\n" + 
					"on g.memberId = l.id where groupId = '"+groupId+"'";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				object = new JSONObject();
				object.put("autoId", element[0].toString());
				object.put("groupId", element[1].toString());
				object.put("groupName", element[2].toString());
				object.put("memberId", element[3].toString());
				object.put("memberName", element[4].toString());
				array.add(object);
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
		return array;
	}
	
	
	


	@Override
	public JSONArray getFormPermitInvoiceList(String formId,String ownerId,String permittedUserId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		JSONArray array=new JSONArray();
		JSONObject object;
		try{
			tx=session.getTransaction();
			tx.begin();
			
			//String sql="select isnull(max(CuttingReqId),0)+1 from TbCuttingRequisitionDetails";
			String sql = "";
			
			if(formId.equals(String.valueOf(FormId.BUYER_CREATE.getId()))) {
				sql = "select b.id,(select name from TbSubMenu where id= '"+formId+"') as FromName,b.name as IdNo,isnull(ap.autoId,0) as isPermitted \r\n" + 
						"  from tbBuyer b\r\n" + 
						"left join tbFileAccessPermission ap\r\n" + 
						"on ap.ownerId = '"+ownerId+"' and b.id = ap.resourceId and ap.permittedUserId = '"+permittedUserId+"'\r\n" + 
						" where b.UserId = '"+ownerId+"'\r\n" + 
						" group by b.id,b.name,ap.autoId";
			}
			else if(formId.equals(String.valueOf(FormId.STYLE_CREATE.getId()))) {
				sql="select sc.styleId,(select name from TbSubMenu where id= '"+formId+"') as FromName,styleNo  as IdNo,isnull(ap.autoId,0) as isPermitted from TbStyleCreate sc\r\n" + 
						"left join tbFileAccessPermission ap\r\n" + 
						"on ap.ownerId = '"+ownerId+"' and sc.styleId = ap.resourceId and ap.permittedUserId = '"+permittedUserId+"'\r\n" + 
						" where UserId = '"+ownerId+"'";
			
			}else if(formId.equals(String.valueOf(FormId.COSTING_CREATE.getId()))) {
				sql="select cc.costingNo,(select name from TbSubMenu where id= '"+formId+"') as FromName,cc.costingNo  as IdNo,isnull(ap.autoId,0) as isPermitted \r\n" + 
						"  from TbCostingCreate cc\r\n" + 
						"left join tbFileAccessPermission ap\r\n" + 
						"on ap.ownerId = '"+ownerId+"' and cc.costingNo = ap.resourceId and ap.permittedUserId = '"+permittedUserId+"'\r\n" + 
						" where UserId = '"+ownerId+"'\r\n" + 
						" group by cc.costingNo,ap.autoId";
			}
			else if(formId.equals(String.valueOf(FormId.BUYER_PO.getId()))) {
				sql=" select boes.autoId,(select name from TbSubMenu where id= '"+formId+"') as FromName,boes.autoId  as IdNo,isnull(ap.autoId,0) as isPermitted \r\n" + 
						"  from TbBuyerOrderEstimateSummary boes\r\n" + 
						"left join tbFileAccessPermission ap\r\n" + 
						"on ap.ownerId = '"+ownerId+"' and boes.autoId = ap.resourceId and ap.permittedUserId = '"+permittedUserId+"'\r\n" + 
						" where UserId = '"+ownerId+"'\r\n" + 
						" group by boes.autoId,ap.autoId";
			}else if(formId.equals(String.valueOf(FormId.ACCESSORIES_INDENT.getId()))) {
				sql = " select ai.AINo,(select name from TbSubMenu where id= '"+formId+"') as FromName,ai.AINo  as IdNo,isnull(ap.autoId,0) as isPermitted \r\n" + 
						"  from tbAccessoriesIndent ai\r\n" + 
						"left join tbFileAccessPermission ap\r\n" + 
						"on ap.ownerId = '"+ownerId+"' and ai.AINo = ap.resourceId and ap.permittedUserId = '"+permittedUserId+"'\r\n" + 
						" where ai.IndentPostBy = '"+ownerId+"'\r\n" + 
						" group by ai.AINo,ap.autoId";
			}
			else if(formId.equals(String.valueOf(FormId.ZIPPER_INDENT.getId()))) {
				sql = " select zi.AINo,(select name from TbSubMenu where id= '"+formId+"') as FromName,zi.AINo  as IdNo,isnull(ap.autoId,0) as isPermitted \r\n" + 
						"  from tbZipperIndent zi\r\n" + 
						"left join tbFileAccessPermission ap\r\n" + 
						"on ap.ownerId = '"+ownerId+"' and zi.AINo = ap.resourceId and ap.permittedUserId = '"+permittedUserId+"'\r\n" + 
						" where zi.IndentPostBy = '"+ownerId+"'\r\n" + 
						" group by zi.AINo,ap.autoId";
			}
			else if(formId.equals(String.valueOf(FormId.FABRICS_INDENT.getId()))) {
				sql = " select fi.indentId,(select name from TbSubMenu where id= '"+formId+"') as FromName,fi.indentId  as IdNo,isnull(ap.autoId,0) as isPermitted \r\n" + 
						"  from tbFabricsIndent fi\r\n" + 
						"left join tbFileAccessPermission ap\r\n" + 
						"on ap.ownerId = '"+ownerId+"' and fi.indentId = ap.resourceId and ap.permittedUserId = '"+permittedUserId+"'\r\n" + 
						" where fi.entryby = '"+ownerId+"'\r\n" + 
						" group by fi.indentId,ap.autoId";
			}else if(formId.equals(String.valueOf(FormId.CARTON_INDENT.getId()))) {
				sql = " select ci.indentId,(select name from TbSubMenu where id= '"+formId+"') as FromName,ci.indentId  as IdNo,isnull(ap.autoId,0) as isPermitted \r\n" + 
						"  from tbAccessoriesIndentForCarton ci\r\n" + 
						"left join tbFileAccessPermission ap\r\n" + 
						"on ap.ownerId = '"+ownerId+"' and ci.indentId = ap.resourceId and ap.permittedUserId = '"+permittedUserId+"'\r\n" + 
						" where ci.IndentPostBy = '"+ownerId+"'\r\n" + 
						" group by ci.indentId,ap.autoId";
			}else if(formId.equals(String.valueOf(FormId.PURCHASE_ORDER.getId()))) {
				sql = " select pos.pono,(select name from TbSubMenu where id= '"+formId+"') as FromName,pos.pono as IdNo,isnull(ap.autoId,0) as isPermitted \r\n" + 
						"  from tbPurchaseOrderSummary pos\r\n" + 
						"left join tbFileAccessPermission ap\r\n" + 
						"on ap.ownerId = '"+ownerId+"' and pos.pono = ap.resourceId and ap.permittedUserId = '"+permittedUserId+"'\r\n" + 
						" where pos.entryBy = '"+ownerId+"'\r\n" + 
						" group by pos.pono,ap.autoId";
			}else if(formId.equals(String.valueOf(FormId.SAMPLE_REQUISITION.getId()))) {
				sql = " select sr.sampleReqId,(select name from TbSubMenu where id= '"+formId+"') as FromName,sr.sampleReqId as IdNo,isnull(ap.autoId,0) as isPermitted \n" + 
						"  from tbSampleRequisition sr\n" + 
						"left join tbFileAccessPermission ap\n" + 
						"on ap.ownerId = '"+ownerId+"' and sr.sampleReqId = ap.resourceId and ap.permittedUserId = '"+permittedUserId+"'\n" + 
						" where sr.UserId = '"+ownerId+"'\n" + 
						" group by sr.sampleReqId,ap.autoId";
			}
			

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				object = new JSONObject();
				object.put("id", element[0].toString());
				object.put("formName", element[1].toString());
				object.put("fileNo", element[2].toString());
				object.put("permit", element[3].toString());
				array.add(object);
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
		return array;
	}

	@Override
	public JSONArray getFormPermitedUsers(String formId, String ownerId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		JSONArray array=new JSONArray();
		JSONObject object;
		try{
			tx=session.getTransaction();
			tx.begin();
			
			//String sql="select isnull(max(CuttingReqId),0)+1 from TbCuttingRequisitionDetails";
			String sql = "";
			
			sql="select permittedUserId,l.fullname from tbFileAccessPermission fap\r\n" + 
					" join Tblogin l\r\n" + 
					" on fap.permittedUserId = l.id\r\n" + 
					" where ownerId = '"+ownerId+"' and resourceType = '"+formId+"'\r\n" + 
					" group by permittedUserId,l.fullname";
			
			
			

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				object = new JSONObject();
				object.put("id", element[0].toString());
				object.put("fullName", element[1].toString());
				array.add(object);
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
		return array;
	}

	@Override
	public String submitFileAccessPermit(String fileAccessPermit) {
		// TODO Auto-generated method stub
		Session session = HibernateUtil.openSession();
		Transaction tx = null;
		try {
			tx = session.getTransaction();
			tx.begin();
			
			JSONParser jsonParser = new JSONParser();
			System.out.println(fileAccessPermit);
			JSONObject permitObject = (JSONObject)jsonParser.parse(fileAccessPermit);
			
			JSONArray filePermitArray = (JSONArray)jsonParser.parse(permitObject.get("permittedFileList").toString());
			
			String sql="delete from tbFileAccessPermission where resourceType = '"+permitObject.get("resourceType")+"' and ownerId = '"+permitObject.get("ownerId")+"' and permittedUserId = '"+permitObject.get("permittedUserId")+"'";
			session.createSQLQuery(sql).executeUpdate();
			
			
			for(int i = 0; i<filePermitArray.size(); i++) {
				JSONObject tempObject = (JSONObject)filePermitArray.get(i);
				
				sql = "insert into tbFileAccessPermission (resourceType,resourceId,ownerId,permittedUserId,entryTime,entryBy) values('"+tempObject.get("resourceType")+"','"+tempObject.get("resourceId")+"','"+tempObject.get("ownerId")+"','"+tempObject.get("permittedUserId")+"',CURRENT_TIMESTAMP,'"+tempObject.get("ownerId")+"')";
				session.createSQLQuery(sql).executeUpdate();
			}	
			
			tx.commit();
			return "success";

		} catch (Exception ee) {
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
	public JSONArray getMenus(String userId) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		JSONArray array=new JSONArray();
		JSONObject object;
		try{
			tx=session.getTransaction();
			tx.begin();
			
			//String sql="select isnull(max(CuttingReqId),0)+1 from TbCuttingRequisitionDetails";
			String sql="select sm.id,sm.module,sm.root,sm.name \r\n" + 
					"from Tbuseraccess ua \r\n" + 
					"join TbSubMenu sm\r\n" + 
					"on ua.sub = sm.id\r\n" + 
					"where ua.userId = '"+userId+"' \r\n" + 
					"order by sm.module,sm.ordering";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				object = new JSONObject();
				object.put("id", element[0].toString());
				object.put("module", element[1].toString());
				object.put("root", element[2].toString());
				object.put("name", element[3].toString());
				array.add(object);
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
		return array;
	}


	@Override
	public List<roleManagement> getSubmenu(String moduleId) {
		// TODO Auto-generated method stub
		String sql = "";
		Session session = HibernateUtil.openSession();
		Transaction tx = null;
		List<roleManagement> dataList = new ArrayList<roleManagement>();
		try {
			tx = session.getTransaction();
			tx.begin();
			sql = "select (a.id) as module, (a.name) as moduleNmae, (b.id) as head,c.id,c.name from TbModule a join TbMenu b on b.module=a.id join TbSubMenu c on c.root=b.id where a.id in ("+moduleId+") and b.module=a.id and c.root=b.id order by a.id";
			List<?> list = session.createSQLQuery(sql).list();
			for (Iterator<?> iter = list.iterator(); iter.hasNext();) {
				Object[] element = (Object[]) iter.next();
				dataList.add(new roleManagement(element[0].toString(),element[1].toString(),Integer.parseInt(element[2].toString()),Integer.parseInt(element[3].toString()), element[4].toString()));
			}
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return dataList;
	}



	@Override
	public boolean saveRolePermission(roleManagement v) {
		// TODO Auto-generated method stub
		String sql="";
		Session session = HibernateUtil.openSession();
		Transaction tx = null;
		try {
			tx = session.getTransaction();
			tx.begin();

			String accesslit=v.getAccesslist();
			accesslit=accesslit.replace("[", "");
			accesslit=accesslit.replace("]", "");
			int x=0;
			sql="select rulename from tbRoleInfo where rulename='"+v.getRoleName()+"'";
			List<?> list1 = session.createSQLQuery(sql).list();
			if(list1.size()==0) {

				sql = "select (isnull(max(ruleid),0)+1) as id from tbRoleInfo";
				List<?> list = session.createSQLQuery(sql).list();
				String maxRuleId = list.get(0).toString();

				sql="insert into tbRoleInfo (ruleid, rulename, userid, entrytime) values ('"+maxRuleId+"', '"+v.getRoleName()+"', '"+v.getUserId()+"', CURRENT_TIMESTAMP) ";
				//			System.err.println("sql : "+sql);
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
						String view=s2.nextToken();
						String delete=s2.nextToken();

						sql="insert into tbRolePermission (ruleid, moduleid, head, sub, entry, edit, [view], clear, entryby) values ('"+maxRuleId+"','"+moduleId+"','"+headId+"','"+subId+"','"+add+"','"+edit+"','"+view+"','"+delete+"','"+v.getUserId()+"')";
						//					System.err.println("sql 2 : "+sql);
						session.createSQLQuery(sql).executeUpdate();

					}
				}
				x++;
			}
			tx.commit();
			if(x>0) {
				return true;
			}else {
				return false;
			}

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
	public List<roleManagement> getAllRoleName() {
		// TODO Auto-generated method stub
		String sql = "";
		Session session = HibernateUtil.openSession();
		Transaction tx = null;
		List<roleManagement> dataList = new ArrayList<roleManagement>();
		try {
			tx = session.getTransaction();
			tx.begin();
			sql = "select ruleid,rulename from tbRoleInfo";
			List<?> list = session.createSQLQuery(sql).list();
			for (Iterator<?> iter = list.iterator(); iter.hasNext();) {
				Object[] element = (Object[]) iter.next();
				dataList.add(new roleManagement(element[0].toString(),element[1].toString()));
			}
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return dataList;
	}



	@Override
	public List<roleManagement> getAllPermissions(String id) {
		// TODO Auto-generated method stub
		String sql = "";
		Session session = HibernateUtil.openSession();
		Transaction tx = null;
		List<roleManagement> dataList = new ArrayList();
		try {
			tx = session.getTransaction();
			tx.begin();
			sql = "select moduleid,head,sub,clear,entry,edit,[view] from tbRolePermission where ruleid='"+id+"'";

			List<?> list = session.createSQLQuery(sql).list();
			for (Iterator<?> iter = list.iterator(); iter.hasNext();) {
				Object[] element = (Object[]) iter.next();
				dataList.add(new roleManagement(element[0].toString(), Integer.parseInt(element[1].toString()),
						Integer.parseInt(element[2].toString()),element[3].toString(),element[4].toString(),
						element[5].toString(),element[6].toString()));
			}
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return dataList;
	}



	@Override
	public boolean editRolePermission(roleManagement v) {
		// TODO Auto-generated method stub
		String sql="";
		Session session = HibernateUtil.openSession();
		Transaction tx = null;
		try {
			tx = session.getTransaction();
			tx.begin();

			String accesslit=v.getAccesslist();
			accesslit=accesslit.replace("[", "");
			accesslit=accesslit.replace("]", "");
			int x=0;
			sql="select rulename from tbRoleInfo where rulename='"+v.getRoleName()+"' and ruleid!='"+v.getRoleId()+"' ";
			List<?> list1 = session.createSQLQuery(sql).list();
			if(list1.size()==0) {
				
				sql="update tbRoleInfo set rulename='"+v.getRoleName()+"' where ruleid='"+v.getRoleId()+"'";
				session.createSQLQuery(sql).executeUpdate();
				
				sql="delete from tbRolePermission where ruleid='"+v.getRoleId()+"'";
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
						String view=s2.nextToken();
						String delete=s2.nextToken();

						sql="insert into tbRolePermission (ruleid, moduleid, head, sub, entry, edit, [view], clear, entryby) values ('"+v.getRoleId()+"','"+moduleId+"','"+headId+"','"+subId+"','"+add+"','"+edit+"','"+view+"','"+delete+"','"+v.getUserId()+"')";
						session.createSQLQuery(sql).executeUpdate();

					}
				}
				x++;
			}
			tx.commit();
			if(x>0) {
				return true;
			}else {
				return false;
			}

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


}
