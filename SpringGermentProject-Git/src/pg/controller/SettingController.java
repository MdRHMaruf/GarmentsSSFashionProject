package pg.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.servlet.http.HttpSession;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.json.JSONException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;


import pg.model.menu;
import pg.model.menuinfo;
import pg.model.module;
import pg.model.moduleinfo;
import pg.model.modulewisemenu;
import pg.model.modulewisemenusubmenu;
import pg.model.password;
import pg.model.submenuinfo;
import pg.model.useraccessmodule;
import pg.model.ware;
import pg.model.wareinfo;
import pg.services.SettingService;


@Controller
@SessionAttributes({"pg_admin","storelist","warelist","modulelist","menulist","accountdetailslist"})
public class SettingController {



	@Autowired
	private SettingService settingService;
	


	@RequestMapping(value = "user_management_menu",method=RequestMethod.GET)
	public ModelAndView create_menu() {

		List<modulewisemenu> modulewisemenulist=settingService.getAllModuleWiseMenu();
		List<module> modulelist=settingService.getAllModuleName();
		List<modulewisemenusubmenu> modulewisesubmenulist=settingService.getAllModuleWiseSubmenu();

		ModelAndView view = new ModelAndView("admin/menu");
		view.addObject("modulewisemenulist", modulewisemenulist);
		view.addObject("modulelist", modulelist);
		view.addObject("modulewisesubmenulist", modulewisesubmenulist);


		return view; //JSP - /WEB-INF/view/index.jsp
	}




	@RequestMapping(value = "/showModuleWiseMenu/{id}",method=RequestMethod.GET)
	public @ResponseBody JSONObject getmodulewisemenu(@PathVariable ("id") int id) {

		List<menu> menulist=settingService.getAllModuleWiseMenu(id);

		JSONObject objmain = new JSONObject();
		JSONArray mainarray = new JSONArray();

		for(int a=0;a<menulist.size();a++) {
			JSONObject obj = new JSONObject();

			obj.put("id", menulist.get(a).getId());
			obj.put("menuname", menulist.get(a).getName());

			mainarray.add(obj);
		}

		objmain.put("result", mainarray);

		System.out.println(objmain.toString());

		return objmain;

	}

	@ResponseBody
	@RequestMapping(value = {"/addWare"},method=RequestMethod.POST)
	public void addWare(ware ware) {

		String msg = "Error occured while updating ware: ,  please try again";
		try {
			if(ware != null) {
				boolean flag = settingService.addWare(ware);

				if(flag) {
					msg = "Ware: "+ ware.getName() + " details updated successfully...";
					System.out.println("flag "+flag);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = {"/user_access_menu"},method=RequestMethod.GET)
	public @ResponseBody JSONObject  user_access_menu_post(useraccessmodule um) throws JSONException {

		String ModuleList=um.getCombineModuleList();

		String moduleout=ModuleList.replace("[", "");

		moduleout=moduleout.replace("]", "");
		moduleout=moduleout.replace("\"", "");

		
		List<modulewisemenusubmenu> moduleaccesslist=settingService.getAllModuleWiseMenuSubMenuName(um.getUser(), moduleout);


		JSONObject objmain = new JSONObject();
		JSONArray mainarray = new JSONArray();




		String head="";

		for(int a=0;a<moduleaccesslist.size();a++) {
			head=moduleaccesslist.get(a).getMenu().toString();

			JSONObject obj = new JSONObject();

			obj.put("moduleid", moduleaccesslist.get(a).getModuleid());
			obj.put("id", moduleaccesslist.get(a).getMenuid());
			obj.put("head", moduleaccesslist.get(a).getMenu());

			JSONArray list = new JSONArray();

			if(head.equals(moduleaccesslist.get(a).getMenu().toString())){



				for(int b=a;b<moduleaccesslist.size();b++){
					JSONObject obj1 = new JSONObject();
					if(!moduleaccesslist.get(b).getMenu().toString().equals(head)){
						a--;
						break;
					}
					obj1.put("id", moduleaccesslist.get(a).getSubmenuid());
					obj1.put("sub",  moduleaccesslist.get(a).getSubMenu());

					list.add(obj1);
					a++;	
				}

				obj.put("sub", list);

			}

			mainarray.add(obj);

		}



		objmain.put("result", mainarray);

		System.out.println(objmain.toString());

		return objmain;


	}



	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getNewStore",method=RequestMethod.GET)
	public ModelAndView loadWare(ware ware,HttpSession session,ModelMap modelmap) {

		List<wareinfo> warelist = (List<wareinfo>) session.getAttribute("storelist");
		warelist.add(new wareinfo(ware.getId(),ware.getName()));

		ModelAndView view = new ModelAndView("admin/create_user");
		view.addObject("warelist", warelist);

		modelmap.put("storelist", warelist);

		return view; //JSP - /WEB-INF/view/index.jsp
	}



	@ResponseBody
	@RequestMapping(value = {"/addModule"},method=RequestMethod.POST)
	public void addModule(moduleinfo module) {

		String msg = "Error occured while updating ware: ,  please try again";
		try {
			if(module != null) {
				boolean flag = settingService.addModule(module);

				if(flag) {
					msg = "Module: "+ module.getName() + " details updated successfully...";
					System.out.println("flag "+flag);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@ResponseBody
	@RequestMapping(value = {"/addMenu"},method=RequestMethod.POST)
	public void addMenu(menuinfo m) {

		String msg = "Error occured while updating ware: ,  please try again";
		try {
			if(m != null) {
				boolean flag = settingService.addMenu(m);

				if(flag) {
					msg = "Menu: "+ m.getName() + " details updated successfully...";
					System.out.println("flag "+flag);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	@ResponseBody
	@RequestMapping(value = {"/addSubMenu"},method=RequestMethod.POST)
	public void addSubMenu(submenuinfo m) {

		System.out.println("submenu");
		String msg = "Error occured while updating ware: ,  please try again";
		try {
			if(m != null) {
				boolean flag = settingService.addSubMenu(m);

				if(flag) {
					msg = "SubMenu: "+ m.getName() + " details updated successfully...";
					System.out.println("flag "+flag);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	@ResponseBody
	@RequestMapping(value = {"/addUser"},method=RequestMethod.POST)
	public String addUser(password pass) {
		System.out.println("adminuser");
		String msg = "Error occured while creating user: ,  please try again";
		try {
			if(pass != null) {
				boolean flag = settingService.addUser(pass);

				if(flag) {
					msg = "User: "+ pass.getUser() + " details updated successfully...";
					System.out.println("flag "+flag);
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msg;
	}

}
