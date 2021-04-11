package pg.controller;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.sun.xml.internal.ws.client.sei.ResponseBuilder.Body;

import noticeModel.noticeModel;
import pg.OrganizationModel.OrganizationInfo;
import pg.exception.UserBlockedException;
import pg.model.Ware;
import pg.model.WareInfo;
import pg.model.roleManagement;
import pg.registerModel.FactoryModel;
import pg.model.Login;
import pg.model.Menu;
import pg.model.Module;
import pg.services.PasswordService;
import pg.services.PasswordServiceImpl;
import pg.services.RegisterService;
import pg.services.SettingService;
import pg.share.SessionBean;

@Controller
@SessionAttributes({"userId","userName","storelist","warelist","modulelist","menulist"})

public class PasswordController {

	List<SessionBean> sessionlist=new ArrayList<SessionBean>();

	static String userName="",passWord="";
	String departmentid="";

	@Autowired
	private SettingService settingService;
	@Autowired
	private PasswordService passService;
	@Autowired
	private RegisterService registerService;


	@RequestMapping(value = {"/","/login"},method=RequestMethod.GET)
	public String login(Model m,HttpSession session) {

		String userId=(String)session.getAttribute("userId");
		if(userId!=null) {
			return "redirect:dashboard";
		}

		//m.addAttribute("command", new LoginCommand());
		return "login"; //JSP - /WEB-INF/view/login.jsp
	}

	@RequestMapping(value = {"/loginout"},method=RequestMethod.GET)
	public String loginout(HttpServletRequest request) {

		HttpSession session=request.getSession();
		session.invalidate();

		return "login"; //JSP - /WEB-INF/view/login.jsp
	}

	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(@RequestParam String name,@RequestParam String password,HttpServletRequest request,ModelMap modelmap) throws UserBlockedException	
	{	


		System.out.println("Log In execute");
		List<Login> lg=passService.login(name, password);

		this.userName=name;
		System.out.println(" login user "+userName);
		this.passWord=password;

		if(lg.size()>0) {



			List<Module> modulelist=passService.getUserModule(lg.get(0).getId());
			//session.put("modulelist", modulelist);
			modelmap.put("modulelist", modulelist);

			if(modulelist.size()>0) {


				modelmap.put("modulelist", modulelist);

				List<Menu> menulist=passService.getAdminUserMenu(lg.get(0).getId(),modulelist.get(0).getId());
				modelmap.put("menulist", menulist);

				return "redirect:dashboard";
			}
			else {
				return "login";
			}



		}
		else {
			return "login";
		}


	}

	@RequestMapping(value = {"dashboard"})
	public String adminDashboard(ModelMap modelmap,HttpServletRequest request,HttpSession sessionnew, noticeModel nm) throws UserBlockedException {
		//noticeModel nm = new noticeModel();
		String uname=userName;
		String pass=passWord;

		userName="";
		passWord="";

		HttpSession session=request.getSession();

		long sessionValue=session.getCreationTime();

		System.out.println("sessionValue "+sessionValue);

		List<Login> lg=passService.login(uname, pass);
		String header="",body="", filename = null;

		if(lg.size()>0) {


			List<Module> modulelist=passService.getUserModule(lg.get(0).getId());
			modelmap.put("modulelist", modulelist);

			List<OrganizationInfo> organizationInfo=passService.getOrganizationInfo();


			modelmap.put("organization_info", organizationInfo);

			sessionlist.add(new SessionBean(sessionValue,Integer.toString(lg.get(0).getId()),lg.get(0).getFullName(),uname,pass));

			String encodedUsername = Base64.getEncoder().encodeToString(lg.get(0).getFullName().getBytes());
			this.departmentid=passService.getUserDepartmentId(Integer.toString(lg.get(0).getId()));
			
			System.out.println(" department id in dashboard 1 "+this.departmentid);
			//passService.getNotice(departmentid, new noticeModel());
			if (passService.getNotice(departmentid, nm).size()>0) {
				header=passService.getNotice(departmentid, nm).get(0).getNoticeHeader();
				body=passService.getNotice(departmentid, nm).get(0).getNoticeBody();
				filename=passService.getNotice(departmentid, nm).get(0).getFilename();


				modelmap.put("header",header);
				modelmap.put("body",body);
				modelmap.put("file",filename);
				
				System.out.println(" body 1 "+body);
			}else {
				modelmap.put("header","");
				modelmap.put("body","");
				modelmap.put("file","");
			}

			modelmap.put("userName", encodedUsername);
			modelmap.put("userId", Integer.toString(lg.get(0).getId()));
			
			modelmap.put("depid", departmentid);




		}else {

			for(int a=0;a<sessionlist.size();a++) {
				if(sessionValue==sessionlist.get(a).getSessionValue()) {
					String encodedString = Base64.getEncoder().encodeToString(sessionlist.get(a).getFullName().getBytes());
					this.departmentid=passService.getUserDepartmentId(sessionlist.get(a).getUserId());
					System.out.println(" department id in dashboard 2 "+this.departmentid);

					if (passService.getNotice(departmentid, nm).size()>0) {
						header=passService.getNotice(departmentid, nm).get(0).getNoticeHeader();
						body=passService.getNotice(departmentid, nm).get(0).getNoticeBody();
						filename=passService.getNotice(departmentid, nm).get(0).getFilename();

						modelmap.put("header",header);
						modelmap.put("body",body);
						modelmap.put("file",filename);
						
						System.out.println(" body 2 "+body);
					}else {
						modelmap.put("header","");
						modelmap.put("body","");
						modelmap.put("file","");
					}

					modelmap.put("userName", encodedString);
					modelmap.put("userId",sessionlist.get(a).getUserId());
					modelmap.put("depid", departmentid);

					//modelmap.put("depid", departmentid);

				}

			}

					

		}
		//System.out.println(" body "+body);
		//modelmap.put("depid", departmentid);
		//System.out.println(" department id in dashboard "+this.departmentid);
		request.getSession().setAttribute("deptid", departmentid);

		return "index"; //JSP - /WEB-INF/view/index.jsp
	}


	@RequestMapping(value = "/modulewisemenu/{id}",method=RequestMethod.GET)
	public @ResponseBody String modulewisemenu(@PathVariable ("id") int id,ModelMap modelmap,HttpSession session) {
		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");



		System.out.println("userId "+userId);
		List<Menu> menulist=passService.getUserMenu(Integer.parseInt(userId),id);
		modelmap.put("menulist", menulist);

		modelmap.put("userName", userName);
		modelmap.put("userId", userId);


		return "index"; //JSP - /WEB-INF/view/index.jsp
	}



	@RequestMapping(value = "user_create",method=RequestMethod.GET)
	public ModelAndView create_user(ModelMap map,HttpSession session) {

		List<Module> modulelist=(List<Module>)session.getAttribute("modulelist");
		map.put("modulelist", modulelist);

		List<Menu> menulist=(List<Menu>)session.getAttribute("menulist");
		List<FactoryModel> factoryList = registerService.getAllFactories();

		map.put("menulist", menulist);

		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");

		ModelAndView view = new ModelAndView("setting/create_user");
		view.addObject("modulelist",modulelist);
		view.addObject("menulist",menulist);
		view.addObject("factoryList",factoryList);

		map.addAttribute("userId",userId);
		map.addAttribute("userName",userName);

		return view; //JSP - /WEB-INF/view/index.jsp
	}

	@RequestMapping(value = "change_password",method=RequestMethod.GET)
	public ModelAndView change_password(ModelMap map,HttpSession session) {

		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");

		ModelAndView view = new ModelAndView("setting/change_password");

		map.addAttribute("userId",userId);
		map.addAttribute("userName",userName);


		return view; //JSP - /WEB-INF/view/index.jsp
	}

	@RequestMapping(value = "changePassword",method=RequestMethod.POST)
	public @ResponseBody String changePassword(String userId,String userName,String password) {

		String msg="Create Occure while change password";	

		boolean flag=passService.changePassword(userId,userName,password);
		if(flag) {
			msg="Password Change Successfully";
		}

		return msg; //JSP - /WEB-INF/view/index.jsp
	}
	
	
	@RequestMapping(value = "user_profile_create",method=RequestMethod.GET)
	public ModelAndView user_profile_create(ModelMap map,HttpSession session) {

		

		//List<Menu> menulist=(List<Menu>)session.getAttribute("menulist");
		//List<FactoryModel> factoryList = registerService.getAllFactories();
		List<roleManagement> roleList = settingService.getAllRoleName();
		map.put("roleList", roleList);

		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");

		
		ModelAndView view = new ModelAndView("setting/user_profile_create");
		view.addObject("allModule",settingService.getAllModuleName());
		map.addAttribute("userId",userId);
		map.addAttribute("userName",userName);

		return view; //JSP - /WEB-INF/view/index.jsp
	}
	
	
	@RequestMapping(value = "/getRolePermissions",method=RequestMethod.GET)
	public @ResponseBody JSONObject getRolePermissions(String roleIds) {
		JSONObject obj = new JSONObject();
		System.out.println("roleIds = "+roleIds);
		JSONArray rolePermissions = passService.getRolePermissions(roleIds);
		obj.put("permissionList", rolePermissions);
		return obj;
	}
	
	@RequestMapping(value= "/saveUserProfile",method=RequestMethod.POST)
	public @ResponseBody JSONObject saveUser(String userInfo) {
		JSONObject obj = new JSONObject();
		
		obj.put("result", passService.saveUserProfile(userInfo));
		
		return obj;
	}

}
