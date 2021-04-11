/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pg.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.itextpdf.awt.geom.gl.Crossing.QuadCurve;

import noticeModel.noticeModel;
import pg.OrganizationModel.OrganizationInfo;
import pg.model.Login;
import pg.model.Menu;
import pg.model.Module;
import pg.model.WareInfo;
import pg.share.FormId;
import pg.share.HibernateUtil;

/**
 * @author Md Nasir Uddin
 */
@Repository
public class PasswordDAOImpl implements PasswordDAO{


	@SuppressWarnings( { "unchecked", "deprecation" } )
	public List<Login> login(String uname, String upwd) {

		Session session = HibernateUtil.openSession();
		Transaction tx = null;
		List<Login> query=new ArrayList<Login>();
		try {

			tx = session.getTransaction();
			tx.begin();
			String sql = "select id,type,factoryId,departmentId,fullname,username,password from tblogin where username='"+uname+"' and password='"+upwd+"' and active='1'";
			List<?> list = session.createSQLQuery(sql).list();

			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				query.add(new Login(Integer.parseInt(element[0].toString()),Integer.parseInt(element[1].toString()),element[2].toString(),element[3].toString(),element[4].toString(),element[5].toString(),element[6].toString()));
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
	public List<WareInfo> getAllStoreName() {
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


	@Override
	public List<Module> getAllModuleName() {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<Module> query=new ArrayList<Module>();
		try{
			tx=session.getTransaction();
			tx.begin();


			List<?> list = session.createSQLQuery("select id,name from module").list();

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
	public List<Menu> getAllMenuName() {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<Menu> query=new ArrayList<Menu>();
		try{
			tx=session.getTransaction();
			tx.begin();


			List<?> list = session.createSQLQuery("select id,name,ware from menu").list();

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



	public List<Module> getUserModule(int i) {

		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<Module> query=new ArrayList<Module>();
		try{
			tx=session.getTransaction();
			tx.begin();


			List<?> list = session.createSQLQuery("select a.module_id,(select name from Tbmodule where id=a.module_id) as ModuleName from Tbuser_access_module a where a.userId='"+i+"' ").list();

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

	@Transactional
	public List<Menu> getUserMenu(int i,int moduleId){


		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<Menu> query=new ArrayList<Menu>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="";
			if(moduleId==0) {
				sql="select a.head, \r\n"
						+ "(select name from Tbmenu where id=a.head) as Menu, \r\n"
						+ "sm.name as SubMenu,\r\n"
						+ "(select links from Tbsubmenu where id=a.sub) as Links \r\n"
						+ "from Tbuseraccess a \r\n"
						+ "join Tbuser_access_module b \r\n"
						+ "on a.userId=b.userId and a.module=b.module_id \r\n"
						+ "join TbSubMenu sm \r\n" + 
						"on a.sub = sm.id \r\n"
						+ "where a.userId='"+i+"' \r\n"
						+ "order by a.module,a.head,sm.ordering ";
			}
			else {
				sql="select a.head,"
						+ "(select name from Tbmenu where id=a.head) as Menu,\r\n"
						+ "sm.name as SubMenu,\r\n"
						+ "(select links from Tbsubmenu where id=a.sub) as Links \r\n"
						+ "from Tbuseraccess a \r\n"
						+ "join Tbuser_access_module b \r\n"
						+ "on a.userId=b.userId and a.module=b.module_id \r\n"
						+ "join TbSubMenu sm \r\n" + 
						"on a.sub = sm.id \r\n"
						+ "where a.userId='"+i+"' and b.module_id='"+moduleId+"' \r\n"
						+ "order by a.module,a.head,sm.ordering ";
			}
			List<?> list = session.createSQLQuery(sql).list();

			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				query.add(new Menu(Integer.parseInt(element[0].toString()),element[1].toString(), element[2].toString(),  element[3].toString()));
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

	@Transactional
	@SuppressWarnings("unchecked")
	public List<Menu> getAdminUserMenu(int i,int moduleId){

		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<Menu> query=new ArrayList<Menu>();
		try{
			tx=session.getTransaction();
			tx.begin();

			System.out.println("user "+i);
			System.out.println("moduleId "+moduleId);

			String sql="";
			if(moduleId==0) {
				//sql="select a.head,(select name from menu where id=a.head) as Menu,(select name from sub_menu where id=a.sub) as SubMenu,(select links from sub_menu where id=a.sub) as Links from useraccess a join user_access_module b on a.user=b.user and a.module=b.module_id where a.user='"+i+"' order by a.module,a.head ";
				sql="select a.head,(select name from Tbmenu where id=a.head) as Menu,\r\n"
						+ "(sm.name) as SubMenu,\r\n"
						+ "(select links from Tbsubmenu where id=a.sub) as Links \r\n"
						+ "from Tbuseraccess a \r\n"
						+ "join Tbuser_access_module b \r\n"
						+ "on a.userId=b.userId and a.module=b.module_id \r\n"
						+ "join TbSubMenu sm \r\n" + 
						"on a.sub = sm.id \r\n"
						+ "where a.userId='"+i+"' order by a.module,a.head,sm.ordering";
			}
			else {
				//sql="select a.head,(select name from menu where id=a.head) as Menu,(select name from sub_menu where id=a.sub) as SubMenu,(select links from sub_menu where id=a.sub) as Links from useraccess a join user_access_module b on a.user=b.user and a.module=b.module_id where a.user='"+i+"' and b.module_id='"+moduleId+"' order by a.module,a.head ";
				sql="select a.head,(select name from Tbmenu where id=a.head) as Menu,\r\n"
						+ "(sm.name) as SubMenu,\r\n"
						+ "(select links from Tbsubmenu where id=a.sub) as Links \r\n"
						+ "from Tbuseraccess a \r\n"
						+ "join Tbuser_access_module b \r\n"
						+ "on a.userId=b.userId and a.module=b.module_id \r\n"
						+ "join TbSubMenu sm \r\n" + 
						"on a.sub = sm.id \r\n"
						+ "where a.userId='"+i+"' and b.module_id='"+moduleId+"' order by a.module,a.head,sm.ordering ";
			}
			List<?> list = session.createSQLQuery(sql).list();

			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				query.add(new Menu(Integer.parseInt(element[0].toString()),element[1].toString(), element[2].toString(),element[3].toString()));
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
	public boolean changePassword(String userId, String userName, String password) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="update Tblogin set password='"+password+"'  where id='"+userId+"' and username='"+userName+"'";
			System.out.println(sql);
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
	public List<OrganizationInfo> getOrganizationInfo() {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<OrganizationInfo> query=new ArrayList<OrganizationInfo>();
		try{
			tx=session.getTransaction();
			tx.begin();


			String sql="select organizationName,organizationContact,organizationAddress from tbOrganizationInfo";
			List<?> list = session.createSQLQuery(sql).list();

			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				System.out.println("orgacon"+element[1].toString());
				System.out.println("orgadd"+element[2].toString());
				query.add(new OrganizationInfo(element[0].toString(),element[1].toString(), element[2].toString()));
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
	public String getUserDepartmentId(String userId) {
		String depId="";
		Session session=HibernateUtil.openSession();
		Transaction tx=null;

		try{
			tx=session.getTransaction();
			tx.begin();

			//String sql="select isnull(max(CuttingReqId),0)+1 from TbCuttingRequisitionDetails";
			String sql="select departmentId from Tblogin where id='"+userId+"'";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				depId=iter.next().toString();
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

		return depId;
	}

	@Override
	public List<noticeModel> getNotice(String depid, noticeModel nm) {
		String depId=depid;
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<noticeModel>query=new ArrayList<>();
		try{
			tx=session.getTransaction();
			tx.begin();
			
			//String sql="select isnull(max(CuttingReqId),0)+1 from TbCuttingRequisitionDetails";
			String sql="select * from tbnotice where noticeno=(select max(noticeno) from tbnotice) and (accessabledepartments='"+depId+"' or accessabledepartments=0)";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				query.add(new noticeModel(element[2].toString(),element[3].toString(),element[4].toString()));
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
	public JSONArray getRolePermissions(String roleIds) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		JSONArray permissionArray = new JSONArray();
		JSONObject permissionObject;
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql="select p.moduleid,m.name as ModuleName,p.sub,sm.name as subMenuName,p.entry,p.edit,p.[view],p.clear from tbRolePermission p\n" + 
					"left join tbsubMenu sm\n" + 
					"on p.sub = sm.id\n" + 
					"left join TbModule m\n" + 
					"on p.moduleid = m.id\n" + 
					"where p.roleId in ("+roleIds+")\n" + 
					"group by  p.moduleid,m.name,p.sub,sm.name,p.entry,p.edit,p.[view],p.clear,sm.ordering\n" + 
					"order by  p.moduleid,p.sub";
			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				permissionObject = new JSONObject();
				permissionObject.put("moduleId", element[0].toString());
				permissionObject.put("moduleName", element[1].toString());
				permissionObject.put("subMenuId", element[2].toString());
				permissionObject.put("subMenuName", element[3].toString());
				permissionObject.put("enter", element[4].toString());
				permissionObject.put("edit", element[5].toString());
				permissionObject.put("view", element[6].toString());
				permissionObject.put("delete", element[7].toString());
				
				permissionArray.add(permissionObject);
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
		return permissionArray;
		
	}

	@Override
	public String saveUserProfile(String userInfo) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();

			JSONParser jsonParser = new JSONParser();
			JSONObject permissionObject = (JSONObject)jsonParser.parse(userInfo);
			//JSONArray itemList = (JSONArray) itemsObject.get("list");

			String sql = "insert into Tblogin (fullname,username,password,active,createby,entrytime) values('"+permissionObject.get("fullName")+"','"+permissionObject.get("userName")+"','"+permissionObject.get("password")+"','"+permissionObject.get("activeStatus")+"','"+permissionObject.get("userId")+"',CURRENT_TIMESTAMP);";
			session.createSQLQuery(sql).executeUpdate();

			sql="select isnull(max(id),0) as maxId from Tblogin ";

			List<?> list = session.createSQLQuery(sql).list();
			String maxUserId = list.get(0).toString();
			
			String roleIds[] = permissionObject.get("userRoles").toString().split(",");
			
			for(String roleId: roleIds) {
				sql = "insert into tbUserRole(userId,roleId,entryTime) values('"+maxUserId+"','"+roleId+"',CURRENT_TIMESTAMP)";
				session.createSQLQuery(sql).executeUpdate();
			}
			
			tx.commit();
			return "successfull";
		}
		catch(Exception ee){
			ee.printStackTrace();
			if (tx != null) {
				tx.rollback();
				return "something wrong";
			}

		}

		finally {
			session.close();
		}

		return "something wrong";
	}
}