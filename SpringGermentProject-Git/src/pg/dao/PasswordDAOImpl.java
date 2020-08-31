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
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pg.model.login;
import pg.model.menu;
import pg.model.module;
import pg.model.wareinfo;
import pg.share.HibernateUtil;

/**
 * @author Md Nasir Uddin
 */
@Repository
public class PasswordDAOImpl implements PasswordDAO{

	
	@SuppressWarnings( { "unchecked", "deprecation" } )
	public List<login> login(String uname, String upwd) {
		
		Session session = HibernateUtil.openSession();
		Transaction tx = null;
		List<login> query=new ArrayList<login>();
		try {

			tx = session.getTransaction();
			tx.begin();
			String sql = "select id,type,username,password from tblogin where username='"+uname+"' and password='"+upwd+"' and active='1'";
			List<?> list = session.createSQLQuery(sql).list();

			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				query.add(new login(Integer.parseInt(element[0].toString()),Integer.parseInt(element[1].toString()),element[2].toString(),element[3].toString()));
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
	public List<wareinfo> getAllStoreName() {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<wareinfo> query=new ArrayList<wareinfo>();
		try{
			tx=session.getTransaction();
			tx.begin();

			
			List<?> list = session.createSQLQuery("select id,name from ware").list();

			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				query.add(new wareinfo(Integer.parseInt(element[0].toString()),element[1].toString()));
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
	public List<module> getAllModuleName() {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<module> query=new ArrayList<module>();
		try{
			tx=session.getTransaction();
			tx.begin();

			
			List<?> list = session.createSQLQuery("select id,name from module").list();

			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				query.add(new module(Integer.parseInt(element[0].toString()),element[1].toString()));
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
	public List<menu> getAllMenuName() {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<menu> query=new ArrayList<menu>();
		try{
			tx=session.getTransaction();
			tx.begin();

			
			List<?> list = session.createSQLQuery("select id,name,ware from menu").list();

			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				query.add(new menu(Integer.parseInt(element[0].toString()),element[1].toString(),"",""));
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
	

	
	public List<module> getUserModule(int i) {
		
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<module> query=new ArrayList<module>();
		try{
			tx=session.getTransaction();
			tx.begin();

			
			List<?> list = session.createSQLQuery("select a.module_id,(select name from Tbmodule where id=a.module_id) as ModuleName from Tbuser_access_module a where a.userId='"+i+"' ").list();

			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				query.add(new module(Integer.parseInt(element[0].toString()),element[1].toString()));
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
	public List<menu> getUserMenu(int i,int moduleId){
		
		
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<menu> query=new ArrayList<menu>();
		try{
			tx=session.getTransaction();
			tx.begin();
			
			String sql="";
			if(moduleId==0) {
				sql="select a.head,(select name from Tbmenu where id=a.head) as Menu,(select name from Tbsubmenu where id=a.sub) as SubMenu,(select links from Tbsubmenu where id=a.sub) as Links from Tbuseraccess a join Tbuser_access_module b on a.userId=b.userId and a.module=b.module_id where a.userId='"+i+"' order by a.module,a.head,a.sub ";
			}
			else {
				sql="select a.head,(select name from Tbmenu where id=a.head) as Menu,(select name from Tbsubmenu where id=a.sub) as SubMenu,(select links from Tbsubmenu where id=a.sub) as Links from Tbuseraccess a join Tbuser_access_module b on a.userId=b.userId and a.module=b.module_id where a.userId='"+i+"' and b.module_id='"+moduleId+"' order by a.module,a.head,a.sub ";
			}
			List<?> list = session.createSQLQuery(sql).list();

			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				query.add(new menu(Integer.parseInt(element[0].toString()),element[1].toString(), element[2].toString(),  element[3].toString()));
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
	public List<menu> getAdminUserMenu(int i,int moduleId){
	
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<menu> query=new ArrayList<menu>();
		try{
			tx=session.getTransaction();
			tx.begin();

			System.out.println("user "+i);
			System.out.println("moduleId "+moduleId);

			String sql="";
			if(moduleId==0) {
				//sql="select a.head,(select name from menu where id=a.head) as Menu,(select name from sub_menu where id=a.sub) as SubMenu,(select links from sub_menu where id=a.sub) as Links from useraccess a join user_access_module b on a.user=b.user and a.module=b.module_id where a.user='"+i+"' order by a.module,a.head ";
				sql="select a.head,(select name from Tbmenu where id=a.head) as Menu,(select name from Tbsubmenu where id=a.sub) as SubMenu,(select links from Tbsubmenu where id=a.sub) as Links from Tbuseraccess a join Tbuser_access_module b on a.userId=b.userId and a.module=b.module_id where a.userId='"+i+"' order by a.module,a.head,a.sub ";
			}
			else {
				//sql="select a.head,(select name from menu where id=a.head) as Menu,(select name from sub_menu where id=a.sub) as SubMenu,(select links from sub_menu where id=a.sub) as Links from useraccess a join user_access_module b on a.user=b.user and a.module=b.module_id where a.user='"+i+"' and b.module_id='"+moduleId+"' order by a.module,a.head ";
				sql="select a.head,(select name from Tbmenu where id=a.head) as Menu,(select name from Tbsubmenu where id=a.sub) as SubMenu,(select links from Tbsubmenu where id=a.sub) as Links from Tbuseraccess a join Tbuser_access_module b on a.userId=b.userId and a.module=b.module_id where a.userId='"+i+"' and b.module_id='"+moduleId+"' order by a.module,a.head,a.sub ";
			}
			List<?> list = session.createSQLQuery(sql).list();

			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	
				Object[] element = (Object[]) iter.next();
				query.add(new menu(Integer.parseInt(element[0].toString()),element[1].toString(), element[2].toString(),element[3].toString()));
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
}
