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
import pg.model.ware;
import pg.model.wareinfo;
import pg.model.login;
import pg.model.menu;
import pg.model.module;
import pg.services.PasswordService;
import pg.services.PasswordServiceImpl;



@Controller

public class HintsController {
	
	@RequestMapping(value = "/uikit",method=RequestMethod.GET)
	public ModelAndView uikit(ModelMap map) {
			
		ModelAndView view = new ModelAndView("hints/uikit");
			
		return view; //JSP - /WEB-INF/view/index.jsp
	}
	
	@RequestMapping(value = "/tabs",method=RequestMethod.GET)
	public ModelAndView tabs(ModelMap map) {
		
			
		ModelAndView view = new ModelAndView("hints/tabs");
			
		return view; //JSP - /WEB-INF/view/index.jsp
	}
	
	@RequestMapping(value = "/form-basic-inputs",method=RequestMethod.GET)
	public ModelAndView formBasicInputs(ModelMap map) {
		
			
		ModelAndView view = new ModelAndView("hints/form-basic-inputs");
			
		return view; //JSP - /WEB-INF/view/index.jsp
	}
	
	@RequestMapping(value = "/form-input-groups",method=RequestMethod.GET)
	public ModelAndView formInputGroups(ModelMap map) {
		
			
		ModelAndView view = new ModelAndView("hints/form-input-groups");
			
		return view; //JSP - /WEB-INF/view/index.jsp
	}
	
	@RequestMapping(value = "/form-horizontal",method=RequestMethod.GET)
	public ModelAndView formHorizontal(ModelMap map) {
		
			
		ModelAndView view = new ModelAndView("hints/form-horizontal");
			
		return view; //JSP - /WEB-INF/view/index.jsp
	}
	
	@RequestMapping(value = "/form-vertical",method=RequestMethod.GET)
	public ModelAndView formVertical(ModelMap map) {
		
			
		ModelAndView view = new ModelAndView("hints/form-vertical");
			
		return view; //JSP - /WEB-INF/view/index.jsp
	}
	
	
	
	@RequestMapping(value = "/tables-basic",method=RequestMethod.GET)
	public ModelAndView tablesBasic(ModelMap map) {
		
			
		ModelAndView view = new ModelAndView("hints/tables-basic");
			
		return view; //JSP - /WEB-INF/view/index.jsp
	}
	
	
	@RequestMapping(value = "/tables-datatables",method=RequestMethod.GET)
	public ModelAndView tablesDatatables(ModelMap map) {
		
			
		ModelAndView view = new ModelAndView("hints/tables-datatables");
			
		return view; //JSP - /WEB-INF/view/index.jsp
	}
	
	
	@RequestMapping(value = "/forgot-password",method=RequestMethod.GET)
	public ModelAndView forgotPassword(ModelMap map) {
		
			
		ModelAndView view = new ModelAndView("hints/forgot-password");
			
		return view; //JSP - /WEB-INF/view/index.jsp
	}
	
	
	@RequestMapping(value = "/blank-page",method=RequestMethod.GET)
	public ModelAndView blankPage(ModelMap map) {
		
			
		ModelAndView view = new ModelAndView("hints/blank-page");
			
		return view; //JSP - /WEB-INF/view/index.jsp
	}
}
