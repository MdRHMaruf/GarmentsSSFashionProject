package pg.controller;

import java.text.DecimalFormat;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import pg.Commercial.BillOfEntry;
import pg.Commercial.ExportLC;
import pg.Commercial.ImportLC;
import pg.Commercial.MasterLC;
import pg.Commercial.MasterLC.StyleInfo;
import pg.Commercial.deedOfContacts;

import pg.orderModel.FabricsIndent;
import pg.registerModel.AccessoriesItem;
import pg.registerModel.BuyerModel;
import pg.registerModel.Color;
import pg.registerModel.FabricsItem;
import pg.registerModel.Notifyer;
import pg.registerModel.SupplierModel;
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
		List<SupplierModel> supplierList =  registerService.getAllSupplier();
		List<MasterLC> masterLCList= commercialService.getMasterLCList();
		List<Color> colorList = registerService.getColorList();
		view.addObject("buyerList",buyerList);
		view.addObject("masterLCList",masterLCList);
		view.addObject("supplierList",supplierList);
		view.addObject("colorList",colorList);
		view.addObject("unitList",registerService.getUnitList());
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
		List<ImportLC> importInvoiceList = commercialService.getImportLCList(masterLCNo);
		List<Notifyer> notifyerList = registerService.getNotifyerListByBuyerId(buyerId);
		
		objmain.put("masterLCInfo",masterLCInfo);
		objmain.put("masterLCStyles", masterLCStyles);
		objmain.put("amendmentList", ammendmentList);
		objmain.put("importInvoiceList", importInvoiceList);
		objmain.put("exportInvoiceList", commercialService.getExportInvoiceList(masterLCNo));
		objmain.put("notifyerList", notifyerList);
		return objmain;
	}
	
	@RequestMapping(value = "/getTypeWiseItems",method=RequestMethod.GET)
	public @ResponseBody JSONObject getTypeWiseItems(String type) {
		JSONObject objmain = new JSONObject();
		JSONArray mainArray = new JSONArray();
		
		if(type.equals("1")) {
			for(FabricsItem item:  registerService.getFabricsItemList()) {
				JSONObject object = new JSONObject();
				object.put("id", item.getFabricsItemId());
				object.put("name", item.getFabricsItemName());
				mainArray.add(object);
			}
			objmain.put("result",mainArray);
		}else if(type.equals("2")) {
			for(AccessoriesItem item:  registerService.getAccessoriesItemList()) {
				JSONObject object = new JSONObject();
				object.put("id", item.getAccessoriesItemId());
				object.put("name", item.getAccessoriesItemName());
				mainArray.add(object);
			}
			objmain.put("result",mainArray);
		}
		return objmain;
	}
	
	
	@RequestMapping(value = "/importLCSubmit",method=RequestMethod.POST)
	public @ResponseBody JSONObject importLCSubmit(ImportLC importLC) {
		JSONObject objmain = new JSONObject();

		if(commercialService.importLCSubmit(importLC)) {
			objmain.put("result", "success");
			objmain.put("importInvoiceList", commercialService.getImportLCList(importLC.getMasterLCNo()));
			objmain.put("amendmentList", commercialService.getImportLCAmendmentList(importLC.getMasterLCNo(), importLC.getInvoiceNo()));
		}else {
			objmain.put("result", "something wrong");
		}
		return objmain;
	}
	
	@RequestMapping(value = "/importLCEdit",method=RequestMethod.POST)
	public @ResponseBody JSONObject importLCEdit(ImportLC importLC) {
		JSONObject objmain = new JSONObject();
		if(commercialService.importLCEdit(importLC).equals("success")) {
			objmain.put("result", "success");
			objmain.put("amendmentList", commercialService.getImportLCAmendmentList(importLC.getMasterLCNo(), importLC.getInvoiceNo()));
		}else {
			objmain.put("result", "something wrong");
		}
		return objmain;
	}

	@RequestMapping(value = "/importLCAmendment",method=RequestMethod.POST)
	public @ResponseBody JSONObject importLCAmendment(ImportLC importLC) {
		JSONObject objmain = new JSONObject();

		if(commercialService.importLCAmendment(importLC).equals("success")) {
			objmain.put("result", "success");
			objmain.put("amendmentList", commercialService.getImportLCAmendmentList(importLC.getMasterLCNo(), importLC.getInvoiceNo()));
		}else {
			objmain.put("result", "something wrong");
		}
		return objmain;
	}
	@RequestMapping(value = "/searchImportLCInvoice",method=RequestMethod.GET)
	public @ResponseBody JSONObject searchImportLCInvoice(String masterLCNo,String invoiceNo,String amendmentNo) {
		JSONObject objmain = new JSONObject();
		//MasterLC masterLCInfo = commercialService.getMasterLCInfo(masterLCNo, buyerId, amendmentNo);
		// = commercialService.getImportLCStyles(masterLCNo, invoiceNo, amendmentNo);
		List<ImportLC> ammendmentList = commercialService.getImportLCAmendmentList(masterLCNo, invoiceNo);
		ImportLC importLC = commercialService.getImportLCInfo(masterLCNo, invoiceNo, amendmentNo);
		JSONArray itemList = commercialService.getImportInvoiceItems(importLC.getAutoId());
		
		//objmain.put("masterLCInfo",masterLCInfo);
		//sobjmain.put("masterLCStyles", masterLCStyles);
		objmain.put("amendmentList", ammendmentList);
		objmain.put("importLCInfo", importLC);
		objmain.put("importItemList", itemList);
		objmain.put("importUDList",commercialService.getImportINvoiceUDList(invoiceNo));
		objmain.put("billEntryList", commercialService.getBillOfEntryList(masterLCNo,invoiceNo));
		return objmain;
	}
	
	@RequestMapping(value = "/importInvoiceUDAdd",method=RequestMethod.POST)
	public @ResponseBody JSONObject importInvoiceUDAdd(String udInfo) {
		JSONObject objmain = new JSONObject();
		try{
			if(commercialService.importInvoiceUdAdd(udInfo)) {
				JSONParser jsonParser = new JSONParser();
				JSONObject udObject = (JSONObject)jsonParser.parse(udInfo);
				objmain.put("result", "success");
				objmain.put("udList", commercialService.getImportINvoiceUDList(udObject.get("importInvoiceNo").toString()));
			}else {
				objmain.put("result", "something wrong");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
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

	@RequestMapping(value = "/billOfEntrySubmit",method=RequestMethod.POST)
	public @ResponseBody JSONObject billOfEntrySubmit(BillOfEntry billOfEntry) {
		JSONObject objmain = new JSONObject();

		if(commercialService.billOfEntrySubmit(billOfEntry)) {
			objmain.put("result", "success");
			objmain.put("billEntryList", commercialService.getBillOfEntryList(billOfEntry.getMasterLCNo(), billOfEntry.getInvoiceNo()));
		}else {
			objmain.put("result", "something wrong");
		}
		return objmain;
	}
	
	@RequestMapping(value = "/billOfEntryEdit",method=RequestMethod.POST)
	public @ResponseBody JSONObject billOfEntryEdit(BillOfEntry billOfEntry) {
		JSONObject objmain = new JSONObject();
		if(commercialService.billOfEntryEdit(billOfEntry).equals("success")) {
			objmain.put("result", "success");
			objmain.put("billItemList", commercialService.getBillOfEntryItems(billOfEntry.getAutoId(), billOfEntry.getBillEntryNo()));
		}else {
			objmain.put("result", "something wrong");
		}
		return objmain;
	}
	
	@RequestMapping(value = "/searchBillOfEntry",method=RequestMethod.GET)
	public @ResponseBody JSONObject searchBillOfEntry(String masterLCNo,String invoiceNo,String billNo) {
		JSONObject objmain = new JSONObject();
		//MasterLC masterLCInfo = commercialService.getMasterLCInfo(masterLCNo, buyerId, amendmentNo);
		// = commercialService.getImportLCStyles(masterLCNo, invoiceNo, amendmentNo);
		BillOfEntry billOfEntry = commercialService.getBillOfEntryInfo(masterLCNo, invoiceNo, billNo);
		JSONArray itemList = commercialService.getBillOfEntryItems(billOfEntry.getAutoId(), billNo);
		
		//objmain.put("masterLCInfo",masterLCInfo);
		//sobjmain.put("masterLCStyles", masterLCStyles);
		objmain.put("billOfEntry", billOfEntry);
		objmain.put("itemList", itemList);
		return objmain;
	}
	
	@RequestMapping(value = "/insert", method=RequestMethod.POST)
	public String insert(deedOfContacts deedcontact) {
		boolean insert=commercialService.insertDeedOfContact(deedcontact);
		if(insert) {
			return "success" ;
		}
		

		return "fail"; //JSP - /WEB-INF/view/index.jsp
	}
	
	
	@RequestMapping(value = "/exportLCSubmit",method=RequestMethod.POST)
	public @ResponseBody JSONObject exportLCSubmit(ExportLC exportLC) {
		JSONObject objmain = new JSONObject();

		if(commercialService.exportLCSubmit(exportLC)) {
			objmain.put("result", "success");
			objmain.put("exportInvoiceList", commercialService.getExportInvoiceList(exportLC.getMasterLCNo()));
		}else {
			objmain.put("result", "something wrong");
		}
		return objmain;
	}
	
	@RequestMapping(value = "/exportLCEdit",method=RequestMethod.POST)
	public @ResponseBody JSONObject exportLCEdit(ExportLC exportLC) {
		JSONObject objmain = new JSONObject();
		if(commercialService.exportLCEdit(exportLC).equals("success")) {
			objmain.put("result", "success");
		}else {
			objmain.put("result", "something wrong");
		}
		return objmain;
	}
	
	@RequestMapping(value = "/searchExportLC",method=RequestMethod.GET)
	public @ResponseBody JSONObject searchExportLC(String masterLCNo,String invoiceNo) {
		JSONObject objmain = new JSONObject();

		
		ExportLC exportLCInfo = commercialService.getExportLCInfo(masterLCNo, invoiceNo);
		JSONArray exportLCStyles = commercialService.getExportStyleItems(exportLCInfo.getAutoId());
		
		objmain.put("exportLCInfo",exportLCInfo);
		objmain.put("exportLCStyles", exportLCStyles);
		
		return objmain;
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
