package pg.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import pg.orderModel.FabricsIndent;
import pg.orderModel.PurchaseOrder;
import pg.orderModel.PurchaseOrderItem;
import pg.registerModel.Department;
import pg.registerModel.MerchandiserInfo;
import pg.registerModel.SupplierModel;
import pg.registerModel.Unit;
import pg.services.OrderService;
import pg.services.RegisterService;
import pg.services.StoreService;
import pg.storeModel.FabricsIssue;
import pg.storeModel.FabricsIssueReturn;
import pg.storeModel.FabricsQualityControl;
import pg.storeModel.FabricsReceive;
import pg.storeModel.FabricsReturn;
import pg.storeModel.FabricsRoll;
import pg.storeModel.StoreGeneralCategory;
import pg.storeModel.StoreGeneralReceived;

@Controller
@RestController
public class StoreController {

	@Autowired
	private RegisterService registerService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private StoreService storeService;

	String InvoiceNo="";
	String Type="";

	//Fabrics Receive 
	@RequestMapping(value = "/fabrics_receive",method=RequestMethod.GET)
	public ModelAndView fabrics_receive(ModelMap map,HttpSession session) {
		ModelAndView view = new ModelAndView("store/fabrics-receive");
		List<Unit> unitList= registerService.getUnitList();
		List<SupplierModel> supplierList = registerService.getAllSupplier();
		view.addObject("unitList", unitList);
		view.addObject("supplierList",supplierList);
		return view; 
	}

	//Fabrics Receive 
	@RequestMapping(value = "/fabrics_receive_new",method=RequestMethod.GET)
	public ModelAndView fabrics_receive_new(ModelMap map,HttpSession session) {
		ModelAndView view = new ModelAndView("store/fabrics-receive-new");
		List<Unit> unitList= registerService.getUnitList();
		List<SupplierModel> supplierList = registerService.getAllSupplier();
		view.addObject("unitList", unitList);
		view.addObject("supplierList",supplierList);
		return view; 
	}

	@RequestMapping(value = "/getFabricsPurchaseOrderIndentList",method = RequestMethod.GET)
	public JSONObject getFabricsPurchaseOrdeIndentrList() {
		JSONObject mainObject = new JSONObject();	
		List<FabricsIndent> purchaseOrderList = storeService.getFabricsPurchaseOrdeIndentrList();	
		mainObject.put("purchaseOrderList", purchaseOrderList);
		return mainObject;
	}


	@RequestMapping(value = "/getFabricsIndentInfo", method = RequestMethod.GET)
	public JSONObject getFabricsIndentInfo(String autoId) {
		JSONObject mainObject = new JSONObject();
		FabricsIndent fabricsIndentInfo = storeService.getFabricsIndentInfo(autoId);
		mainObject.put("fabricsInfo",fabricsIndentInfo);
		return mainObject;
	}
	@RequestMapping(value = "/submitFabricsReceive",method=RequestMethod.POST)
	public @ResponseBody JSONObject submitFabricsReceive(FabricsReceive	fabricsReceive) {
		System.out.println("it'Execute");
		JSONObject objmain = new JSONObject();
		if(storeService.submitFabricsReceive(fabricsReceive)) {
			objmain.put("result", "successfull");
		}else {
			objmain.put("result", "duplicate");
		}
		return objmain;
	}

	@RequestMapping(value = "/editFabricsReceive",method=RequestMethod.POST)
	public @ResponseBody JSONObject editFabricsReceive(FabricsReceive	fabricsReceive) {
		System.out.println("it'Execute");
		JSONObject objmain = new JSONObject();
		if(storeService.editFabricsReceive(fabricsReceive)) {
			objmain.put("result", "successfull");
		}else {
			objmain.put("result", "duplicate");
		}
		return objmain;
	}

	@RequestMapping(value = "/deleteReceiveRollFromTransaction",method=RequestMethod.GET)
	public @ResponseBody JSONObject deleteReceiveRollFromTransaction(FabricsRoll fabricsRoll) {
		System.out.println("it'Execute");
		JSONObject objmain = new JSONObject();

		objmain.put("result", storeService.deleteReceiveRollFromTransaction(fabricsRoll));

		return objmain;
	}

	@RequestMapping(value = "/editReceiveRollInTransaction",method=RequestMethod.GET)
	public @ResponseBody JSONObject editReceiveRollInTransaction(FabricsRoll fabricsRoll) {
		System.out.println("it'Execute");
		JSONObject objmain = new JSONObject();

		objmain.put("result", storeService.editReceiveRollInTransaction(fabricsRoll));

		return objmain;
	}
	@RequestMapping(value = "/getFabricsReceiveList", method = RequestMethod.GET)
	public JSONObject getFabricsReceiveList() {
		JSONObject mainObject = new JSONObject();
		List<FabricsReceive> fabricsReceiveList = storeService.getFabricsReceiveList();
		mainObject.put("fabricsReceiveList",fabricsReceiveList);
		return mainObject;
	}

	@RequestMapping(value = "/getFabricsReceiveInfo", method = RequestMethod.GET)
	public JSONObject getFabricsReceiveInfo(String transactionId) {
		JSONObject mainObject = new JSONObject();
		FabricsReceive fabricsReceive = storeService.getFabricsReceiveInfo(transactionId);
		mainObject.put("fabricsReceive",fabricsReceive);
		return mainObject;
	}


	//Fabrics Quality Control
	@RequestMapping(value = "/fabrics_quality_control",method=RequestMethod.GET)
	public ModelAndView fabrics_quality_control(ModelMap map,HttpSession session) {
		ModelAndView view = new ModelAndView("store/fabrics-quality-control");
		List<SupplierModel> supplierList = registerService.getAllSupplier();
		view.addObject("supplierList",supplierList);
		return view; 
	}

	@RequestMapping(value = "/submitFabricsQC",method=RequestMethod.POST)
	public @ResponseBody JSONObject submitFabricsQC(FabricsQualityControl	fabricsQualityControl) {
		System.out.println("it'Execute");
		JSONObject objmain = new JSONObject();
		if(storeService.submitFabricsQC(fabricsQualityControl)) {
			objmain.put("result", "successfull");
		}else {
			objmain.put("result", "duplicate");
		}
		return objmain;
	}

	@RequestMapping(value = "/editFabricsQC",method=RequestMethod.POST)
	public @ResponseBody JSONObject editFabricsQC(FabricsQualityControl	fabricsQualityControl) {
		System.out.println("it'Execute");
		JSONObject objmain = new JSONObject();
		if(storeService.editFabricsQC(fabricsQualityControl)) {
			objmain.put("result", "successfull");
		}else {
			objmain.put("result", "duplicate");
		}
		return objmain;
	}
	@RequestMapping(value = "/getFabricsQCList", method = RequestMethod.GET)
	public JSONObject getFabricsQCList() {
		JSONObject mainObject = new JSONObject();
		List<FabricsQualityControl> fabricsQCList = storeService.getFabricsQCList();
		mainObject.put("fabricsQCList",fabricsQCList);
		return mainObject;
	}
	@RequestMapping(value = "/getFabricsQCInfo", method = RequestMethod.GET)
	public JSONObject getFabricsQCInfo(String qcTransactionId) {
		JSONObject mainObject = new JSONObject();
		FabricsQualityControl fabricsQC = storeService.getFabricsQCInfo(qcTransactionId);
		mainObject.put("fabricsQC",fabricsQC);
		return mainObject;
	}


	//Fabrics Return
	@RequestMapping(value = "/fabrics_return",method=RequestMethod.GET)
	public ModelAndView fabrics_return(ModelMap map,HttpSession session) {
		ModelAndView view = new ModelAndView("store/fabrics-return");
		List<SupplierModel> supplierList = registerService.getAllSupplier();
		view.addObject("supplierList",supplierList);
		return view; 
	}

	@RequestMapping(value = "/getFabricsRollList", method = RequestMethod.GET)
	public JSONObject getFabricsRollList(String supplierId) {
		JSONObject mainObject = new JSONObject();
		List<FabricsRoll> fabricsRollList = storeService.getFabricsRollListBySupplier(supplierId);
		mainObject.put("fabricsRollList",fabricsRollList);
		return mainObject;
	}

	@RequestMapping(value = "/submitFabricsReturn",method=RequestMethod.POST)
	public @ResponseBody JSONObject submitFabricsReturn(FabricsReturn	fabricsReturn) {
		System.out.println("it'Execute");
		JSONObject objmain = new JSONObject();
		if(storeService.submitFabricsReturn(fabricsReturn)) {
			objmain.put("result", "successfull");
		}else {
			objmain.put("result", "duplicate");
		}
		return objmain;
	}

	@RequestMapping(value = "/editFabricsReturn",method=RequestMethod.POST)
	public @ResponseBody JSONObject editFabricsReturn(FabricsReturn	fabricsReturn) {
		System.out.println("it'Execute");
		JSONObject objmain = new JSONObject();
		if(storeService.editFabricsReturn(fabricsReturn)) {
			objmain.put("result", "successfull");
		}else {
			objmain.put("result", "duplicate");
		}
		return objmain;
	}

	@RequestMapping(value = "/deleteReturnRollFromTransaction",method=RequestMethod.GET)
	public @ResponseBody JSONObject deleteReturnRollFromTransaction(FabricsRoll fabricsRoll) {
		System.out.println("it'Execute");
		JSONObject objmain = new JSONObject();

		objmain.put("result", storeService.deleteReturnRollFromTransaction(fabricsRoll));

		return objmain;
	}

	@RequestMapping(value = "/editReturnRollInTransaction",method=RequestMethod.GET)
	public @ResponseBody JSONObject editReturnRollInTransaction(FabricsRoll fabricsRoll) {
		System.out.println("it'Execute");
		JSONObject objmain = new JSONObject();

		objmain.put("result", storeService.editReturnRollInTransaction(fabricsRoll));

		return objmain;
	}
	@RequestMapping(value = "/getFabricsReturnList", method = RequestMethod.GET)
	public JSONObject getFabricsReturnList() {
		JSONObject mainObject = new JSONObject();
		List<FabricsReturn> fabricsReturnList = storeService.getFabricsReturnList();
		mainObject.put("fabricsReturnList",fabricsReturnList);
		return mainObject;
	}
	@RequestMapping(value = "/getFabricsReturnInfo", method = RequestMethod.GET)
	public JSONObject getFabricsReturnInfo(String returnTransactionId) {
		JSONObject mainObject = new JSONObject();
		FabricsReturn fabricsReturn = storeService.getFabricsReturnInfo(returnTransactionId);
		mainObject.put("fabricsReturn",fabricsReturn);
		return mainObject;
	}

	@RequestMapping(value = "/getFabricsReceiveInfoForReturn", method = RequestMethod.GET)
	public JSONObject getFabricsReceiveInfoForReturn(String transactionId) {
		JSONObject mainObject = new JSONObject();
		FabricsReceive fabricsReceive = storeService.getFabricsReceiveInfoForReturn(transactionId);
		mainObject.put("fabricsReceive",fabricsReceive);
		return mainObject;
	}


	//Fabrics Issue
	@RequestMapping(value = "/fabrics_issue",method=RequestMethod.GET)
	public ModelAndView fabrics_issue(ModelMap map,HttpSession session) {
		ModelAndView view = new ModelAndView("store/fabrics-issue");
		List<Department> departmentList = registerService.getDepartmentList();
		map.addAttribute("departmentList",departmentList);
		return view; 
	}

	@RequestMapping(value = "/getAvailableFabricsRollList", method = RequestMethod.GET)
	public JSONObject getAvailableFabricsRollList(String departmentId) {
		JSONObject mainObject = new JSONObject();
		List<FabricsRoll> fabricsRollList = storeService.getAvailableFabricsRollListInDepartment(departmentId);
		mainObject.put("fabricsRollList",fabricsRollList);
		return mainObject;
	}

	@RequestMapping(value = "/submitFabricsIssue",method=RequestMethod.POST)
	public @ResponseBody JSONObject submitFabricsIssue(FabricsIssue	fabricsIssue) {
		System.out.println("it'Execute");
		JSONObject objmain = new JSONObject();
		if(storeService.submitFabricsIssue(fabricsIssue)) {
			objmain.put("result", "successfull");
		}else {
			objmain.put("result", "duplicate");
		}
		return objmain;
	}

	@RequestMapping(value = "/editFabricsIssue",method=RequestMethod.POST)
	public @ResponseBody JSONObject editFabricsIssue(FabricsIssue	fabricsIssue) {
		System.out.println("it'Execute");
		JSONObject objmain = new JSONObject();
		if(storeService.editFabricsIssue(fabricsIssue)) {
			objmain.put("result", "successfull");
		}else {
			objmain.put("result", "duplicate");
		}
		return objmain;
	}

	@RequestMapping(value = "/deleteIssueRollFromTransaction",method=RequestMethod.GET)
	public @ResponseBody JSONObject deleteIssueRollFromTransaction(FabricsRoll fabricsRoll) {
		System.out.println("it'Execute");
		JSONObject objmain = new JSONObject();

		objmain.put("result", storeService.deleteIssuedRollFromTransaction(fabricsRoll));

		return objmain;
	}

	@RequestMapping(value = "/editIssueRollInTransaction",method=RequestMethod.GET)
	public @ResponseBody JSONObject editIssueRollInTransaction(FabricsRoll fabricsRoll) {
		System.out.println("it'Execute");
		JSONObject objmain = new JSONObject();

		objmain.put("result", storeService.editIssuedRollInTransaction(fabricsRoll));

		return objmain;
	}
	@RequestMapping(value = "/getFabricsIssueList", method = RequestMethod.GET)
	public JSONObject getFabricsIssueList() {
		JSONObject mainObject = new JSONObject();
		List<FabricsIssue> fabricsIssueList = storeService.getFabricsIssueList();
		mainObject.put("fabricsIssueList",fabricsIssueList);
		return mainObject;
	}
	@RequestMapping(value = "/getFabricsIssueInfo", method = RequestMethod.GET)
	public JSONObject getFabricsIssueInfo(String transactionId) {
		JSONObject mainObject = new JSONObject();
		FabricsIssue fabricsIssue = storeService.getFabricsIssueInfo(transactionId);
		mainObject.put("fabricsIssue",fabricsIssue);
		return mainObject;
	}



	//Fabrics IssueReturn
	@RequestMapping(value = "/fabrics_issue_return",method=RequestMethod.GET)
	public ModelAndView fabrics_issue_return(ModelMap map,HttpSession session) {
		ModelAndView view = new ModelAndView("store/fabrics-issue-return");
		List<Department> departmentList = registerService.getDepartmentList();
		map.addAttribute("departmentList",departmentList);
		return view; 
	}

	@RequestMapping(value = "/getIssuedFabricsRollList", method = RequestMethod.GET)
	public JSONObject getIssuedFabricsRollList(String departmentId,String returnDepartmentId) {
		JSONObject mainObject = new JSONObject();
		List<FabricsRoll> fabricsRollList = storeService.getIssuedFabricsRollListInDepartment(departmentId,returnDepartmentId);
		mainObject.put("fabricsRollList",fabricsRollList);
		return mainObject;
	}


	@RequestMapping(value = "/submitFabricsIssueReturn",method=RequestMethod.POST)
	public @ResponseBody JSONObject submitFabricsIssueReturn(FabricsIssueReturn	fabricsIssueReturn) {
		System.out.println("it'Execute");
		JSONObject objmain = new JSONObject();
		if(storeService.submitFabricsIssueReturn(fabricsIssueReturn)) {
			objmain.put("result", "successfull");
		}else {
			objmain.put("result", "duplicate");
		}
		return objmain;
	}

	@RequestMapping(value = "/editFabricsIssueReturn",method=RequestMethod.POST)
	public @ResponseBody JSONObject editFabricsIssueReturn(FabricsIssueReturn	fabricsIssueReturn) {
		System.out.println("it'Execute");
		JSONObject objmain = new JSONObject();
		if(storeService.editFabricsIssueReturn(fabricsIssueReturn)) {
			objmain.put("result", "successfull");
		}else {
			objmain.put("result", "duplicate");
		}
		return objmain;
	}

	@RequestMapping(value = "/deleteIssueReturnRollFromTransaction",method=RequestMethod.GET)
	public @ResponseBody JSONObject deleteIssueReturnRollFromTransaction(FabricsRoll fabricsRoll) {
		System.out.println("it'Execute");
		JSONObject objmain = new JSONObject();

		objmain.put("result", storeService.deleteIssueReturndRollFromTransaction(fabricsRoll));

		return objmain;
	}

	@RequestMapping(value = "/editIssueReturnRollInTransaction",method=RequestMethod.GET)
	public @ResponseBody JSONObject editIssueReturnRollInTransaction(FabricsRoll fabricsRoll) {
		System.out.println("it'Execute");
		JSONObject objmain = new JSONObject();

		objmain.put("result", storeService.editIssueReturndRollInTransaction(fabricsRoll));

		return objmain;
	}
	@RequestMapping(value = "/getFabricsIssueReturnList", method = RequestMethod.GET)
	public JSONObject getFabricsIssueReturnList() {
		JSONObject mainObject = new JSONObject();
		List<FabricsIssueReturn> fabricsIssueReturnList = storeService.getFabricsIssueReturnList();
		mainObject.put("fabricsIssueReturnList",fabricsIssueReturnList);
		return mainObject;
	}
	@RequestMapping(value = "/getFabricsIssueReturnInfo", method = RequestMethod.GET)
	public JSONObject getFabricsIssueReturnInfo(String transactionId) {
		JSONObject mainObject = new JSONObject();
		FabricsIssueReturn fabricsIssueReturn = storeService.getFabricsIssueReturnInfo(transactionId);
		mainObject.put("fabricsIssueReturn",fabricsIssueReturn);
		return mainObject;
	}

	@RequestMapping(value = "/general_item_create",method=RequestMethod.GET)
	public ModelAndView general_item_create(ModelMap map,HttpSession session) {
		ModelAndView view = new ModelAndView("store/general_item_create");
		List<Unit> unitList= registerService.getUnitList();
		List<StoreGeneralCategory> catList= registerService.getStoreCategoryList();
		List<StoreGeneralCategory> List= storeService.getStoreGeneralItemList();
		view.addObject("unitList", unitList);
		view.addObject("catList", catList);
		view.addObject("itemList", List);

		return view; 
	}

	@RequestMapping(value = "/saveGeneralItem",method=RequestMethod.POST)
	public @ResponseBody JSONObject saveGeneralItem(StoreGeneralCategory v) {
		JSONObject objmain = new JSONObject();
		if(!storeService.isStoreGenralItemExist(v)) {
			if(storeService.saveGeneralItem(v)) {

				JSONArray mainarray = new JSONArray();

				List<StoreGeneralCategory> List= storeService.getStoreGeneralItemList();

				for(int a=0;a<List.size();a++) {
					JSONObject obj = new JSONObject();
					obj.put("itemId", List.get(a).getItemId());
					obj.put("itemName", List.get(a).getItemName());
					obj.put("categoryName", List.get(a).getCategoryName());

					mainarray.add(obj);
				}


				objmain.put("result", mainarray);

			}else {
				objmain.put("result", "Something Wrong");
			}	
		}else {
			objmain.put("result", "duplicate");
		}
		return objmain;
	}

	@RequestMapping(value = "/editGeneralItem",method=RequestMethod.POST)
	public @ResponseBody JSONObject editGeneralItem(StoreGeneralCategory v) {
		JSONObject objmain = new JSONObject();
		if(!storeService.isStoreGenralItemExist(v)) {
			if(storeService.editGeneralItem(v)) {

				JSONArray mainarray = new JSONArray();

				List<StoreGeneralCategory> List= storeService.getStoreGeneralItemList();

				for(int a=0;a<List.size();a++) {
					JSONObject obj = new JSONObject();
					obj.put("itemId", List.get(a).getItemId());
					obj.put("itemName", List.get(a).getItemName());
					obj.put("categoryName", List.get(a).getCategoryName());

					mainarray.add(obj);
				}


				objmain.put("result", mainarray);

			}else {
				objmain.put("result", "Something Wrong");
			}	
		}else {
			objmain.put("result", "duplicate");
		}
		return objmain;
	}

	@RequestMapping(value = "/general_received",method=RequestMethod.GET)
	public ModelAndView general_received(ModelMap map,HttpSession session) {
		ModelAndView view = new ModelAndView("store/general_received");




		String maxInvoice=storeService.getMaxInvoiceId("1");
		List<Unit> unitList= registerService.getUnitList();
		List<SupplierModel> supList= registerService.getAllSupplier();
		List<StoreGeneralCategory> List= storeService.getStoreGeneralItemList();
		List<StoreGeneralReceived> addList= storeService.getStoreGeneralReceivedItemList(maxInvoice,"1");
		List<StoreGeneralReceived> receivedInvoiceList= storeService.getStoreGeneralReceivedIList("1");
		System.out.println("size "+receivedInvoiceList.size());
		view.addObject("unitList", unitList);
		view.addObject("supList", supList);
		view.addObject("itemList", List);
		view.addObject("addList", addList);
		view.addObject("receivedInvoiceList", receivedInvoiceList);
		map.addAttribute("InvoiceId", maxInvoice);

		return view; 
	}



	@RequestMapping(value = "/addGeneralReceivedItem",method=RequestMethod.POST)
	public @ResponseBody JSONObject addGeneralReceivedItem(StoreGeneralReceived v) {
		JSONObject objmain = new JSONObject();

		if(storeService.addGeneralReceivedItem(v)) {

			JSONArray mainarray = new JSONArray();

			List<StoreGeneralReceived> List= storeService.getStoreGeneralReceivedItemList(v.getInvoiceNo(),v.getType());

			objmain.put("result", List);

		}else {
			objmain.put("result", "Something Wrong");
		}	

		return objmain;
	}

	@RequestMapping(value = "/confrimGeneralReceivedItem",method=RequestMethod.POST)
	public @ResponseBody String confrimGeneralReceivedItem(StoreGeneralReceived v) {
		String msg="Create occure while confrim General Item Received";

		if(storeService.confrimtoreGeneralReceivedItemt(v)) {
			msg="General Item Received Confrim Successfully";

		}

		return msg;
	}
	

	@RequestMapping(value = "/GeneralReceivedInvoiceInfo",method=RequestMethod.POST)
	public @ResponseBody String GeneralReceivedInvoiceInfo(String invoiceNo,String type) {
		this.InvoiceNo=invoiceNo;
		this.Type=type;
		return "Success";
	}
	
	@RequestMapping(value = "/printGeneralReceivedInvoiceReportt",method=RequestMethod.GET)
	public @ResponseBody ModelAndView printSewingHourlyReport(ModelMap map) {
		
	
		ModelAndView view=new ModelAndView("store/printGeneralReceivedInvoiceReportt");
		
		
		map.addAttribute("InvoiceNo", InvoiceNo);
		map.addAttribute("Type", Type);
	
		return view;
	}
}
