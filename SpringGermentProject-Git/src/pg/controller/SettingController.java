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
import pg.model.Ware;
import pg.model.WareInfo;
import pg.services.SettingService;


@Controller
@SessionAttributes({"pg_admin","storelist","warelist","modulelist","menulist","accountdetailslist"})
public class SettingController {



	@Autowired
	private SettingService settingService;
	


	@RequestMapping(value = "user_management_menu",method=RequestMethod.GET)
	public ModelAndView create_menu() {

		List<ModuleWiseMenu> modulewisemenulist=settingService.getAllModuleWiseMenu();
		List<Module> modulelist=settingService.getAllModuleName();
		List<ModuleWiseMenuSubMenu> modulewisesubmenulist=settingService.getAllModuleWiseSubmenu();

		ModelAndView view = new ModelAndView("admin/menu");
		view.addObject("modulewisemenulist", modulewisemenulist);
		view.addObject("modulelist", modulelist);
		view.addObject("modulewisesubmenulist", modulewisesubmenulist);


		return view; //JSP - /WEB-INF/view/index.jsp
	}




	@RequestMapping(value = "/showModuleWiseMenu/{id}",method=RequestMethod.GET)
	public @ResponseBody JSONObject getmodulewisemenu(@PathVariable ("id") int id) {

		List<Menu> menulist=settingService.getAllModuleWiseMenu(id);

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
	public void addWare(Ware ware) {

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
	public @ResponseBody JSONObject  user_access_menu_post(UserAccessModule um) throws JSONException {

		String ModuleList=um.getCombineModuleList();

		String moduleout=ModuleList.replace("[", "");

		moduleout=moduleout.replace("]", "");
		moduleout=moduleout.replace("\"", "");

		
		List<ModuleWiseMenuSubMenu> moduleaccesslist=settingService.getAllModuleWiseMenuSubMenuName(um.getUser(), moduleout);


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
	public ModelAndView loadWare(Ware ware,HttpSession session,ModelMap map) {

		List<WareInfo> warelist = (List<WareInfo>) session.getAttribute("storelist");
		warelist.add(new WareInfo(ware.getId(),ware.getName()));

		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");
		
		ModelAndView view = new ModelAndView("admin/create_user");
		view.addObject("warelist", warelist);

		map.put("storelist", warelist);
		
		map.addAttribute("userId",userId);
		map.addAttribute("userName",userName);

		return view; //JSP - /WEB-INF/view/index.jsp
	}



	@ResponseBody
	@RequestMapping(value = {"/addModule"},method=RequestMethod.POST)
	public void addModule(ModuleInfo module) {

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
	public void addMenu(MenuInfo m) {

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
	public void addSubMenu(SubMenuInfo m) {

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
	public String addUser(Password pass) {
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

	
	@RequestMapping(value = "organization_create",method=RequestMethod.GET)
	public ModelAndView organization_create(ModelMap map,HttpSession session) {
		
		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");
		ModelAndView view = new ModelAndView("setting/organization-create");
		map.addAttribute("userId",userId);
		map.addAttribute("userName",userName);
		
		return view; //JSP - /WEB-INF/view/index.jsp
		
	}
	@RequestMapping(value = "/getOrganizationName", method = RequestMethod.POST)
	public @ResponseBody List<OrganizationInfo> getOrganizationName(OrganizationInfo v) {
		
		List<OrganizationInfo> OrganizationCreate = settingService.getOrganization();
		return OrganizationCreate;

	}
	@RequestMapping(value = "/saveOrganizationName", method = RequestMethod.POST)
	public @ResponseBody String saveOrganizationName(OrganizationInfo v) {

			boolean saveOrganizationName = settingService.editOrganization(v);
			return "success";

	}


}
