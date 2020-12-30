package pg.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import pg.exception.UserBlockedException;
import pg.model.Ware;
import pg.model.WareInfo;
import pg.registerModel.FactoryModel;
import pg.model.Login;
import pg.model.Menu;
import pg.model.Module;
import pg.services.PasswordService;
import pg.services.PasswordServiceImpl;
import pg.services.RegisterService;



@Controller
@SessionAttributes({"pg_admin","storelist","warelist","modulelist","menulist"})

public class PasswordController {

	String userName="",passWord="";


	@Autowired
	private PasswordService passService;
	@Autowired
	private RegisterService registerService;

	
	@RequestMapping(value = {"/","/login"},method=RequestMethod.GET)
	public String login(Model m,HttpSession session) {
		
		System.out.println("log");
		List<Login> pass=(List<Login>)session.getAttribute("pg_admin");
		if(pass!=null) {
			return "redirect:dashboard";
		}
		
		//m.addAttribute("command", new LoginCommand());
		return "login"; //JSP - /WEB-INF/view/login.jsp
	}

	@RequestMapping(value = {"/loginout"},method=RequestMethod.GET)
	public String loginout() {
		
		return "login"; //JSP - /WEB-INF/view/login.jsp
	}
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(@RequestParam String name,@RequestParam String password,ModelMap modelmap,HttpServletResponse response) throws UserBlockedException	
	{	

		System.out.println("Log In execute");
		List<Login> lg=passService.login(name, password);
		
		this.userName=name;
		this.passWord=password;
		
		if(lg.size()>0) {

			
			List<Module> modulelist=passService.getUserModule(lg.get(0).getId());
			modelmap.put("modulelist", modulelist);
			
			System.out.println("type "+lg.get(0).getType());
			
			if(lg.get(0).getType()==1 || lg.get(0).getType()==2) {

				if(modulelist.size()>0) {
					List<Menu> menulist=passService.getAdminUserMenu(lg.get(0).getId(),modulelist.get(0).getId());
					modelmap.put("menulist", menulist);
					modelmap.put("pg_admin", lg);
					
					Cookie ck=new Cookie("username", lg.get(0).getUser());
					ck.setMaxAge(3600);
					response.addCookie(ck);

					return "redirect:dashboard";
				}
				else {
					return "login";
				}

			}
			else {
				if(modulelist.size()>0) {
					List<Menu> menulist=passService.getUserMenu(lg.get(0).getId(),modulelist.get(0).getId());
					modelmap.put("menulist", menulist);
					modelmap.put("pg_admin", lg);
					
					Cookie ck=new Cookie("username", lg.get(0).getUser());
					ck.setMaxAge(3600);
					response.addCookie(ck);

					return "redirect:dashboard";
				}
				else {
					return "login";
				}

			}

			

		}
		else {
			return "login";
		}


	}

	@RequestMapping(value = {"dashboard"})
	public String adminDashboard(ModelMap modelmap,HttpServletResponse response) {
		try {
			List<Login> lg=passService.login(userName, passWord);
			List<Module> modulelist=passService.getUserModule(lg.get(0).getId());
			modelmap.put("modulelist", modulelist);
			
			//List<menu> menulist=passService.getAdminUserMenu(lg.get(0).getId(),modulelist.get(0).getId());
			//modelmap.put("menulist", menulist);
			modelmap.put("pg_admin", lg);
			
			Cookie ck=new Cookie("username", lg.get(0).getUser());
			ck.setMaxAge(3600);
			response.addCookie(ck);
		} catch (UserBlockedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("dashboad Execute");
		return "index"; //JSP - /WEB-INF/view/index.jsp
	}

	
	@RequestMapping(value = "/modulewisemenu/{id}",method=RequestMethod.GET)
	public @ResponseBody String modulewisemenu(@PathVariable ("id") int id,ModelMap modelmap,HttpSession session) {
		List<Login> lg=(List<Login>)session.getAttribute("pg_admin");
		List<Menu> menulist=passService.getUserMenu(lg.get(0).getId(),id);
		modelmap.put("menulist", menulist);

		
		return "index"; //JSP - /WEB-INF/view/index.jsp
	}
	

	
	@RequestMapping(value = "user_create",method=RequestMethod.GET)
	public ModelAndView create_user(ModelMap modelmap,HttpSession session) {
		
		List<Module> modulelist=(List<Module>)session.getAttribute("modulelist");
		modelmap.put("modulelist", modulelist);
		
		List<Menu> menulist=(List<Menu>)session.getAttribute("menulist");
		List<FactoryModel> factoryList = registerService.getAllFactories();
		
		modelmap.put("menulist", menulist);
		
		ModelAndView view = new ModelAndView("setting/create_user");
		view.addObject("modulelist",modulelist);
		view.addObject("menulist",menulist);
		view.addObject("factoryList",factoryList);
		
		return view; //JSP - /WEB-INF/view/index.jsp
	}
	
	@RequestMapping(value = "change_password",method=RequestMethod.GET)
	public ModelAndView change_password(ModelMap modelmap,HttpSession session) {
		
		
		ModelAndView view = new ModelAndView("setting/change_password");
		
		
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

}
