package pg.controller;

import java.text.DecimalFormat;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import pg.Commercial.MasterLC;
import pg.Commercial.MasterLC.StyleInfo;
import pg.Commercial.deedOfContacts;
import pg.orderModel.FabricsIndent;
import pg.registerModel.BuyerModel;
import pg.registerModel.Unit;
import pg.services.CommercialService;
import pg.services.OrderService;
import pg.services.PasswordService;
import pg.services.RegisterService;


@Controller
@RestController
public class CommercialController {


	@Autowired
	private PasswordService passService;
	
	private static final String UPLOAD_FILE_SAVE_FOLDER = "E:/uploadspringfiles/";

	private static final String UPLOAD_DIRECTORY ="/WEB-INF/upload";  
	
	 private static final String DIRECTORY="E:/uploadspringfiles/";
	 
	 
	 String contractId;
	 String styleid;
	 String itemid;
	 

	DecimalFormat df = new DecimalFormat("#.00");

	@Autowired
	private OrderService orderService;
	@Autowired
	private RegisterService registerService;
	@Autowired
	CommercialService commercialService;

	//Style Create 
	
	String styleNo="",date="";
	
	String FrontImg="",BackImg;
	
	String StyleId="",ItemId="";
	
	@RequestMapping(value = "/master_lc")
	public ModelAndView master_lc(ModelMap map,HttpSession session) {

		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");
		
		String departmentId=passService.getUserDepartmentId(userId);
		
		ModelAndView view = new ModelAndView("commercial/master-lc");
		List<BuyerModel> buyerList= registerService.getAllBuyers(userId);
		List<MasterLC> masterLCList= commercialService.getMasterLCList();
		view.addObject("buyerList",buyerList);
		view.addObject("masterLCList",masterLCList);
		
		map.addAttribute("userId",userId);
		map.addAttribute("userName",userName);
		map.addAttribute("departmentId",departmentId);
		return view; //JSP - /WEB-INF/view/index.jsp
	}

	@RequestMapping(value = "/masterLCSubmit",method=RequestMethod.POST)
	public @ResponseBody JSONObject masterLCSubmit(MasterLC masterLC) {
		JSONObject objmain = new JSONObject();

		if(commercialService.masterLCSubmit(masterLC)) {
			objmain.put("result", "success");
			objmain.put("amendmentList", commercialService.getMasterLCAmendmentList(masterLC.getMasterLCNo(), masterLC.getBuyerId()));
		}else {
			objmain.put("result", "something wrong");
		}
		return objmain;
	}
	
	@RequestMapping(value = "/masterLCEdit",method=RequestMethod.POST)
	public @ResponseBody JSONObject masterLCEdit(MasterLC masterLC) {
		JSONObject objmain = new JSONObject();
		if(commercialService.masterLCEdit(masterLC).equals("success")) {
			objmain.put("result", "success");
			objmain.put("amendmentList", commercialService.getMasterLCAmendmentList(masterLC.getMasterLCNo(), masterLC.getBuyerId()));
		}else {
			objmain.put("result", "something wrong");
		}
		return objmain;
	}
	
	@RequestMapping(value = "/masterLCAmendment",method=RequestMethod.POST)
	public @ResponseBody JSONObject masterLCAmendment(MasterLC masterLC) {
		JSONObject objmain = new JSONObject();
		if(commercialService.masterLCAmendment(masterLC).equals("success")) {
			objmain.put("result", "success");
			objmain.put("amendmentList", commercialService.getMasterLCAmendmentList(masterLC.getMasterLCNo(), masterLC.getBuyerId()));
		}else {
			objmain.put("result", "something wrong");
		}
		return objmain;
	}	
	
	@RequestMapping(value = "/searchMasterLC",method=RequestMethod.GET)
	public @ResponseBody JSONObject searchMasterLC(String masterLCNo,String buyerId,String amendmentNo) {
		JSONObject objmain = new JSONObject();

		JSONArray mainArray = new JSONArray();
		MasterLC masterLCInfo = commercialService.getMasterLCInfo(masterLCNo, buyerId, amendmentNo);
		List<StyleInfo> masterLCStyles = commercialService.getMasterLCStyles(masterLCNo, buyerId, amendmentNo);
		List<MasterLC> ammendmentList = commercialService.getMasterLCAmendmentList(masterLCNo, buyerId);
		objmain.put("masterLCInfo",masterLCInfo);
		objmain.put("masterLCStyles", masterLCStyles);
		objmain.put("amendmentList", ammendmentList);
		return objmain;
	}

	@RequestMapping(value = "deed_of_contact")
	public ModelAndView deed_of_contact(ModelMap map,HttpSession session) {

		
		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");
		
		ModelAndView view = new ModelAndView("commercial/deedOfContact");

		
		List<String> poList = orderService.getPurchaseOrderList(userId);
		List<Unit> unitList = registerService.getUnitList();
		List<BuyerModel> buyerList= registerService.getAllBuyers(userId);
		List<deedOfContacts>ContractsList=commercialService.deedOfContractsList();
		
		view.addObject("Lists",ContractsList);
		view.addObject("unitList",unitList);
		view.addObject("buyerList", buyerList);
		
		map.addAttribute("userId",userId);
		map.addAttribute("userName",userName);
		
		

		return view; //JSP - /WEB-INF/view/index.jsp
	}

	
	
	@RequestMapping(value = "/insert", method=RequestMethod.POST)
	public String insert(deedOfContacts deedcontact) {
		boolean insert=commercialService.insertDeedOfContact(deedcontact);
		if(insert) {
			return "success" ;
		}
		

		return "fail"; //JSP - /WEB-INF/view/index.jsp
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/deedofcontract/{contractid}",method=RequestMethod.POST)
	public String deedofcontractreport(@PathVariable ("contractid") String contractid) {
		System.out.println(" Open Ooudoor sales report 1");
		
		this.contractId=contractid;
		
		
		return "yes";
		
	}
	
	
	
	@ResponseBody
	@RequestMapping(value = "/deedofcontractview",method=RequestMethod.GET)
	public ModelAndView department_medicine_delvierOpen(ModelMap map, FabricsIndent p,HttpSession session) {
		
		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");
		ModelAndView view = new ModelAndView("commercial/deedOfContactReport");
	
		view.addObject("contractId",contractId);
		map.addAttribute("userId",userId);
		map.addAttribute("userName",userName);
		 	
			
			
			return view;
		
	}
	
	
	@RequestMapping(value = "/deedOfContratDetails/{id}", method=RequestMethod.POST)
	public List<deedOfContacts> deedOfContratDetails(@PathVariable ("id") String id) {

		List<deedOfContacts> details=commercialService.deedOfContractDetails(id);
		
		System.out.println(" ud receive date "+details.get(0).getuNReceivedDate());
		

		return details; //JSP - /WEB-INF/view/index.jsp
	}
	



}
