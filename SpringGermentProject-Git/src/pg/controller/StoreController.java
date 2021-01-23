package pg.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import pg.orderModel.FabricsIndent;
import pg.orderModel.PurchaseOrder;
import pg.orderModel.PurchaseOrderItem;
import pg.model.Login;
import pg.orderModel.AccessoriesIndent;
import pg.orderModel.AccessoriesIndentCarton;
import pg.proudctionModel.CuttingInformation;
import pg.registerModel.AccessoriesItem;
import pg.registerModel.Department;
import pg.registerModel.MerchandiserInfo;
import pg.registerModel.SupplierModel;
import pg.registerModel.Unit;
import pg.services.OrderService;
import pg.services.PasswordService;
import pg.services.ProductionService;
import pg.services.RegisterService;
import pg.services.StoreService;
import pg.storeModel.AccessoriesIssue;
import pg.storeModel.AccessoriesIssueReturn;
import pg.storeModel.AccessoriesQualityControl;
import pg.storeModel.AccessoriesReceive;
import pg.storeModel.AccessoriesReturn;
import pg.storeModel.AccessoriesSize;
import pg.storeModel.AccessoriesTransferIn;
import pg.storeModel.AccessoriesTransferOut;
import pg.storeModel.CuttingFabricsUsed;
import pg.storeModel.FabricsIssue;
import pg.storeModel.FabricsIssueReturn;
import pg.storeModel.FabricsQualityControl;
import pg.storeModel.FabricsReceive;
import pg.storeModel.FabricsReturn;
import pg.storeModel.FabricsRoll;
import pg.storeModel.FabricsTransferIn;
import pg.storeModel.FabricsTransferOut;
import pg.storeModel.PendingTransaction;
import pg.storeModel.StockItem;
import pg.storeModel.StoreGeneralCategory;
import pg.storeModel.StoreGeneralReceived;

@Controller
@RestController
public class StoreController {

	@Autowired
	private ProductionService productionService;

	@Autowired
	private RegisterService registerService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private StoreService storeService;
	
	@Autowired
	private PasswordService passService;

	String InvoiceNo="";
	String Type="";

	//Fabrics Receive 
	@RequestMapping(value = "/fabrics_receive",method=RequestMethod.GET)
	public ModelAndView fabrics_receive(ModelMap map,HttpSession session) {
		
		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");
		
		ModelAndView view = new ModelAndView("store/fabrics-receive");
		List<Unit> unitList= registerService.getUnitList();
		List<SupplierModel> supplierList = registerService.getAllSupplier();
		view.addObject("unitList", unitList);
		view.addObject("supplierList",supplierList);
		map.addAttribute("userId",userId);
		map.addAttribute("userName",userName);
		
		return view; 
	}

	@RequestMapping(value = "/getFabricsPurchaseOrderIndentList",method = RequestMethod.GET)
	public JSONObject getFabricsPurchaseOrdeIndentrList(String supplierId) {
		JSONObject mainObject = new JSONObject();	
		List<FabricsIndent> purchaseOrderList = storeService.getFabricsPurchaseOrdeIndentrList(supplierId);	
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
		
		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");
		String departmentId=passService.getUserDepartmentId(userId);
		
		ModelAndView view = new ModelAndView("store/fabrics-quality-control");
		List<SupplierModel> supplierList = registerService.getAllSupplier();
		view.addObject("supplierList",supplierList);
		map.addAttribute("userId",userId);
		map.addAttribute("userName",userName);
		map.addAttribute("departmentId",departmentId);
		
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
		
		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");
		String departmentId=passService.getUserDepartmentId(userId);
		
		ModelAndView view = new ModelAndView("store/fabrics-return");
		List<SupplierModel> supplierList = registerService.getAllSupplier();
		view.addObject("supplierList",supplierList);
		
		map.addAttribute("userId",userId);
		map.addAttribute("userName",userName);
		map.addAttribute("departmentId",departmentId);
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
		
		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");
		String departmentId=passService.getUserDepartmentId(userId);
		
		ModelAndView view = new ModelAndView("store/fabrics-issue");
		List<Department> departmentList = registerService.getDepartmentList();
		List<CuttingInformation> cuttingReqList = productionService.getCuttingRequisitionList();
		map.addAttribute("departmentList",departmentList);
		view.addObject("cuttingReqList",cuttingReqList);
		
		map.addAttribute("userId",userId);
		map.addAttribute("userName",userName);
		map.addAttribute("departmentId",departmentId);
		
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
	public @ResponseBody JSONObject submitFabricsIssue(FabricsIssue	fabricsIssue,String requisitionNo,String requisitionStatus) {
		System.out.println("it'Execute");
		JSONObject objmain = new JSONObject();
		if(storeService.submitFabricsIssue(fabricsIssue)) {
			if(!requisitionNo.equals("0")) {
				storeService.updateFabricRequisitionStatus(requisitionNo,requisitionStatus);
			}
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
		
		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");
		String departmentId=passService.getUserDepartmentId(userId);
		
		ModelAndView view = new ModelAndView("store/fabrics-issue-return");
		List<Department> departmentList = registerService.getDepartmentList();
		map.addAttribute("departmentList",departmentList);
		map.addAttribute("userId",userId);
		map.addAttribute("userName",userName);
		map.addAttribute("departmentId",departmentId);
		
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


	@RequestMapping(value = "/fabrics_transfer_out",method=RequestMethod.GET)
	public ModelAndView fabrics_transer_out(ModelMap map,HttpSession session) {
		
		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");
		String departmentId=passService.getUserDepartmentId(userId);
		
		ModelAndView view = new ModelAndView("store/fabrics-transfer-out");
		List<Department> departmentList = registerService.getDepartmentList();
		map.addAttribute("departmentList",departmentList);
		
		map.addAttribute("userId",userId);
		map.addAttribute("userName",userName);
		map.addAttribute("departmentId",departmentId);
		return view; 
	}

	@RequestMapping(value = "/submitFabricsTransferOut",method=RequestMethod.POST)
	public @ResponseBody JSONObject submitFabricsTransferOut(FabricsTransferOut	fabricsTransferOut) {
		System.out.println("it'Execute");
		JSONObject objmain = new JSONObject();
		if(storeService.submitFabricsTransferOut(fabricsTransferOut)) {
			objmain.put("result", "successfull");
		}else {
			objmain.put("result", "duplicate");
		}
		return objmain;
	}

	@RequestMapping(value = "/editFabricsTransferOut",method=RequestMethod.POST)
	public @ResponseBody JSONObject editFabricsTransferOut(FabricsTransferOut	fabricsTransferOut) {
		System.out.println("it'Execute");
		JSONObject objmain = new JSONObject();
		if(storeService.editFabricsTransferOut(fabricsTransferOut)) {
			objmain.put("result", "successfull");
		}else {
			objmain.put("result", "duplicate");
		}
		return objmain;
	}

	@RequestMapping(value = "/deleteTransferOutRollFromTransaction",method=RequestMethod.GET)
	public @ResponseBody JSONObject deleteTransferOutRollFromTransaction(FabricsRoll fabricsRoll) {
		System.out.println("it'Execute");
		JSONObject objmain = new JSONObject();

		objmain.put("result", storeService.deleteTransferOutdRollFromTransaction(fabricsRoll));

		return objmain;
	}

	@RequestMapping(value = "/editTransferOutRollInTransaction",method=RequestMethod.GET)
	public @ResponseBody JSONObject editTransferOutRollInTransaction(FabricsRoll fabricsRoll) {
		System.out.println("it'Execute");
		JSONObject objmain = new JSONObject();

		objmain.put("result", storeService.editTransferOutdRollInTransaction(fabricsRoll));

		return objmain;
	}
	@RequestMapping(value = "/getFabricsTransferOutList", method = RequestMethod.GET)
	public JSONObject getFabricsTransferOutList() {
		JSONObject mainObject = new JSONObject();
		List<FabricsTransferOut> fabricsTransferOutList = storeService.getFabricsTransferOutList();
		mainObject.put("fabricsTransferOutList",fabricsTransferOutList);
		return mainObject;
	}
	@RequestMapping(value = "/getFabricsTransferOutInfo", method = RequestMethod.GET)
	public JSONObject getFabricsTransferOutInfo(String transactionId) {
		JSONObject mainObject = new JSONObject();
		FabricsTransferOut fabricsTransferOut = storeService.getFabricsTransferOutInfo(transactionId);
		mainObject.put("fabricsTransferOut",fabricsTransferOut);
		return mainObject;
	}



	@RequestMapping(value = "/fabrics_transfer_in",method=RequestMethod.GET)
	public ModelAndView fabrics_transfer_in(ModelMap map,HttpSession session) {
		
		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");
		String departmentId=passService.getUserDepartmentId(userId);
		ModelAndView view = new ModelAndView("store/fabrics-transfer-in");
		List<Department> departmentList = registerService.getDepartmentList();
		map.addAttribute("departmentList",departmentList);
		map.addAttribute("userId",userId);
		map.addAttribute("userName",userName);
		map.addAttribute("departmentId",departmentId);
		return view; 
	}
	
	@RequestMapping(value = "/getTransferInFabricsRollList", method = RequestMethod.GET)
	public JSONObject getTransferInFabricsRollList(String departmentId,String transferDepartmentId) {
		JSONObject mainObject = new JSONObject();
		List<FabricsRoll> fabricsRollList = storeService.getTransferInFabricsRollList(departmentId,transferDepartmentId);
		mainObject.put("fabricsRollList",fabricsRollList);
		return mainObject;
	}

	@RequestMapping(value = "/submitFabricsTransferIn",method=RequestMethod.POST)
	public @ResponseBody JSONObject submitFabricsTransferIn(FabricsTransferIn	fabricsTransferIn) {
		System.out.println("it'Execute");
		JSONObject objmain = new JSONObject();
		if(storeService.submitFabricsTransferIn(fabricsTransferIn)) {
			objmain.put("result", "successfull");
		}else {
			objmain.put("result", "duplicate");
		}
		return objmain;
	}

	@RequestMapping(value = "/editFabricsTransferIn",method=RequestMethod.POST)
	public @ResponseBody JSONObject editFabricsTransferIn(FabricsTransferIn	fabricsTransferIn) {
		System.out.println("it'Execute");
		JSONObject objmain = new JSONObject();
		if(storeService.editFabricsTransferIn(fabricsTransferIn)) {
			objmain.put("result", "successfull");
		}else {
			objmain.put("result", "duplicate");
		}
		return objmain;
	}

	@RequestMapping(value = "/deleteTransferInRollFromTransaction",method=RequestMethod.GET)
	public @ResponseBody JSONObject deleteTransferInRollFromTransaction(FabricsRoll fabricsRoll) {
		System.out.println("it'Execute");
		JSONObject objmain = new JSONObject();

		objmain.put("result", storeService.deleteTransferIndRollFromTransaction(fabricsRoll));

		return objmain;
	}

	@RequestMapping(value = "/editTransferInRollInTransaction",method=RequestMethod.GET)
	public @ResponseBody JSONObject editTransferInRollInTransaction(FabricsRoll fabricsRoll) {
		System.out.println("it'Execute");
		JSONObject objmain = new JSONObject();

		objmain.put("result", storeService.editTransferIndRollInTransaction(fabricsRoll));

		return objmain;
	}
	@RequestMapping(value = "/getFabricsTransferInList", method = RequestMethod.GET)
	public JSONObject getFabricsTransferInList() {
		JSONObject mainObject = new JSONObject();
		List<FabricsTransferIn> fabricsTransferInList = storeService.getFabricsTransferInList();
		mainObject.put("fabricsTransferInList",fabricsTransferInList);
		return mainObject;
	}
	@RequestMapping(value = "/getFabricsTransferInInfo", method = RequestMethod.GET)
	public JSONObject getFabricsTransferInInfo(String transactionId) {
		JSONObject mainObject = new JSONObject();
		FabricsTransferIn fabricsTransferIn = storeService.getFabricsTransferInInfo(transactionId);
		mainObject.put("fabricsTransferIn",fabricsTransferIn);
		return mainObject;
	}



	//Accessories Receive 
	@RequestMapping(value = "/accessories_receive",method=RequestMethod.GET)
	public ModelAndView accessories_receive(ModelMap map,HttpSession session) {
		
		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");
		String departmentId=passService.getUserDepartmentId(userId);
		
		ModelAndView view = new ModelAndView("store/accessories-receive");
		List<Unit> unitList= registerService.getUnitList();
		List<SupplierModel> supplierList = registerService.getAllSupplier();
		view.addObject("unitList", unitList);
		view.addObject("supplierList",supplierList);
		
		map.addAttribute("userId",userId);
		map.addAttribute("userName",userName);
		map.addAttribute("departmentId",departmentId);
		return view; 
	}


	@RequestMapping(value = "/getAccessoriesPurchaseOrderIndentList",method = RequestMethod.GET)
	public JSONObject getAccessoriesPurchaseOrdeIndentrList(String supplierId) {
		JSONObject mainObject = new JSONObject();	
		List<AccessoriesIndent> purchaseOrderList = storeService.getAccessoriesPurchaseOrdeIndentrList(supplierId);	
		mainObject.put("purchaseOrderList", purchaseOrderList);
		return mainObject;
	}

	@RequestMapping(value = "/getAccessoriesSizeList",method = RequestMethod.GET)
	public JSONObject getAccessoriesSizeList(String accessoriesList) {
		JSONObject mainObject = new JSONObject();	

		List<AccessoriesIndent> list = new ArrayList<>();
		try {
			String[] rollLists = accessoriesList.split("#");	
			String autoId,transectionId,purchaseOrder,styleId,styleName,itemId,itemName,itemColorId,itemColorName,accessoriesId,accessoriesName,accessoriesColorId,accessoriesColorName;
			double qcReturnQty,unitQty,balanceQty; int qcPassedType;
			boolean isReturn;
			for (String item : rollLists) {
				String[] itemProperty = item.split(",");
				purchaseOrder = itemProperty[0].substring(itemProperty[0].indexOf(":")+1).trim();
				styleId = itemProperty[1].substring(itemProperty[1].indexOf(":")+1).trim();
				styleName = itemProperty[2].substring(itemProperty[2].indexOf(":")+1).trim();
				itemId = itemProperty[3].substring(itemProperty[3].indexOf(":")+1).trim();
				itemName = itemProperty[4].substring(itemProperty[4].indexOf(":")+1).trim();
				itemColorId = itemProperty[5].substring(itemProperty[5].indexOf(":")+1).trim();
				itemColorName = itemProperty[6].substring(itemProperty[6].indexOf(":")+1).trim();
				accessoriesId = itemProperty[7].substring(itemProperty[7].indexOf(":")+1).trim();
				accessoriesName = itemProperty[8].substring(itemProperty[8].indexOf(":")+1).trim();
				accessoriesColorId = itemProperty[9].substring(itemProperty[9].indexOf(":")+1).trim();
				accessoriesColorName = itemProperty[10].substring(itemProperty[10].indexOf(":")+1).trim();;
				list.add(new AccessoriesIndent(purchaseOrder, styleId, styleName, itemId, itemName, itemColorId, itemColorName, accessoriesId, accessoriesName, accessoriesColorId, accessoriesColorName));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		List<AccessoriesSize> accessoriesSizeList = storeService.getAccessoriesSizeListByAccessories(list);	
		mainObject.put("accessoriesSizeList", accessoriesSizeList);
		return mainObject;
	}


	@RequestMapping(value = "/getAccessoriesIndentInfo", method = RequestMethod.GET)
	public JSONObject getAccessoriesIndentInfo(String autoId) {
		JSONObject mainObject = new JSONObject();
		AccessoriesIndent accessoriesIndentInfo = storeService.getAccessoriesIndentInfo(autoId);
		mainObject.put("accessoriesInfo",accessoriesIndentInfo);
		return mainObject;
	}
	@RequestMapping(value = "/submitAccessoriesReceive",method=RequestMethod.POST)
	public @ResponseBody JSONObject submitAccessoriesReceive(AccessoriesReceive	accessoriesReceive) {
		System.out.println("it'Execute");
		JSONObject objmain = new JSONObject();
		if(storeService.submitAccessoriesReceive(accessoriesReceive)) {
			objmain.put("result", "successfull");
		}else {
			objmain.put("result", "duplicate");
		}
		return objmain;
	}

	@RequestMapping(value = "/editAccessoriesReceive",method=RequestMethod.POST)
	public @ResponseBody JSONObject editAccessoriesReceive(AccessoriesReceive	accessoriesReceive) {
		System.out.println("it'Execute");
		JSONObject objmain = new JSONObject();
		if(storeService.editAccessoriesReceive(accessoriesReceive)) {
			objmain.put("result", "successfull");
		}else {
			objmain.put("result", "duplicate");
		}
		return objmain;
	}

	@RequestMapping(value = "/deleteReceiveSizeFromTransaction",method=RequestMethod.GET)
	public @ResponseBody JSONObject deleteReceiveSizeFromTransaction(AccessoriesSize accessoriesRoll) {
		System.out.println("it'Execute");
		JSONObject objmain = new JSONObject();

		objmain.put("result", storeService.deleteReceiveSizeFromTransaction(accessoriesRoll));

		return objmain;
	}

	@RequestMapping(value = "/editReceiveSizeInTransaction",method=RequestMethod.GET)
	public @ResponseBody JSONObject editReceiveSizeInTransaction(AccessoriesSize accessoriesRoll) {
		System.out.println("it'Execute");
		JSONObject objmain = new JSONObject();

		objmain.put("result", storeService.editReceiveSizeInTransaction(accessoriesRoll));

		return objmain;
	}
	@RequestMapping(value = "/getAccessoriesReceiveList", method = RequestMethod.GET)
	public JSONObject getAccessoriesReceiveList() {
		JSONObject mainObject = new JSONObject();
		List<AccessoriesReceive> accessoriesReceiveList = storeService.getAccessoriesReceiveList();
		mainObject.put("accessoriesReceiveList",accessoriesReceiveList);
		return mainObject;
	}

	@RequestMapping(value = "/getAccessoriesReceiveInfo", method = RequestMethod.GET)
	public JSONObject getAccessoriesReceiveInfo(String transactionId) {
		JSONObject mainObject = new JSONObject();
		AccessoriesReceive accessoriesReceive = storeService.getAccessoriesReceiveInfo(transactionId);
		mainObject.put("accessoriesReceive",accessoriesReceive);
		return mainObject;
	}

	//Accessories Quality Control
	@RequestMapping(value = "/accessories_quality_control",method=RequestMethod.GET)
	public ModelAndView accessories_quality_control(ModelMap map,HttpSession session) {
		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");
		String departmentId=passService.getUserDepartmentId(userId);
		ModelAndView view = new ModelAndView("store/accessories-quality-control");
		List<SupplierModel> supplierList = registerService.getAllSupplier();
		view.addObject("supplierList",supplierList);
		
		map.addAttribute("userId",userId);
		map.addAttribute("userName",userName);
		map.addAttribute("departmentId",departmentId);
		return view; 
	}

	@RequestMapping(value = "/submitAccessoriesQC",method=RequestMethod.POST)
	public @ResponseBody JSONObject submitAccessoriesQC(AccessoriesQualityControl	accessoriesQualityControl) {
		System.out.println("it'Execute");
		JSONObject objmain = new JSONObject();
		if(storeService.submitAccessoriesQC(accessoriesQualityControl)) {
			objmain.put("result", "successfull");
		}else {
			objmain.put("result", "duplicate");
		}
		return objmain;
	}

	@RequestMapping(value = "/editAccessoriesQC",method=RequestMethod.POST)
	public @ResponseBody JSONObject editAccessoriesQC(AccessoriesQualityControl	accessoriesQualityControl) {
		System.out.println("it'Execute");
		JSONObject objmain = new JSONObject();
		if(storeService.editAccessoriesQC(accessoriesQualityControl)) {
			objmain.put("result", "successfull");
		}else {
			objmain.put("result", "duplicate");
		}
		return objmain;
	}
	@RequestMapping(value = "/getAccessoriesQCList", method = RequestMethod.GET)
	public JSONObject getAccessoriesQCList() {
		JSONObject mainObject = new JSONObject();
		List<AccessoriesQualityControl> accessoriesQCList = storeService.getAccessoriesQCList();
		mainObject.put("accessoriesQCList",accessoriesQCList);
		return mainObject;
	}
	@RequestMapping(value = "/getAccessoriesQCInfo", method = RequestMethod.GET)
	public JSONObject getAccessoriesQCInfo(String qcTransactionId) {
		JSONObject mainObject = new JSONObject();
		AccessoriesQualityControl accessoriesQC = storeService.getAccessoriesQCInfo(qcTransactionId);
		mainObject.put("accessoriesQC",accessoriesQC);
		return mainObject;
	}


	//Accessories Return
	@RequestMapping(value = "/accessories_return",method=RequestMethod.GET)
	public ModelAndView accessories_return(ModelMap map,HttpSession session) {
		
		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");
		String departmentId=passService.getUserDepartmentId(userId);
		
		ModelAndView view = new ModelAndView("store/accessories-return");
		List<SupplierModel> supplierList = registerService.getAllSupplier();
		view.addObject("supplierList",supplierList);
		
		map.addAttribute("userId",userId);
		map.addAttribute("userName",userName);
		map.addAttribute("departmentId",departmentId);
		return view; 
	}

	@RequestMapping(value = "/getAccessoriesSizeListBySupplierId", method = RequestMethod.GET)
	public JSONObject getAccessoriesSizeListBySupplier(String supplierId) {
		JSONObject mainObject = new JSONObject();
		List<AccessoriesSize> accessoriesSizeList = storeService.getAccessoriesSizeListBySupplier(supplierId);
		mainObject.put("accessoriesSizeList",accessoriesSizeList);
		return mainObject;
	}

	@RequestMapping(value = "/submitAccessoriesReturn",method=RequestMethod.POST)
	public @ResponseBody JSONObject submitAccessoriesReturn(AccessoriesReturn	accessoriesReturn) {
		System.out.println("it'Execute");
		JSONObject objmain = new JSONObject();
		if(storeService.submitAccessoriesReturn(accessoriesReturn)) {
			objmain.put("result", "successfull");
		}else {
			objmain.put("result", "duplicate");
		}
		return objmain;
	}

	@RequestMapping(value = "/editAccessoriesReturn",method=RequestMethod.POST)
	public @ResponseBody JSONObject editAccessoriesReturn(AccessoriesReturn	accessoriesReturn) {
		System.out.println("it'Execute");
		JSONObject objmain = new JSONObject();
		if(storeService.editAccessoriesReturn(accessoriesReturn)) {
			objmain.put("result", "successfull");
		}else {
			objmain.put("result", "duplicate");
		}
		return objmain;
	}

	@RequestMapping(value = "/deleteReturnSizeFromTransaction",method=RequestMethod.GET)
	public @ResponseBody JSONObject deleteReturnSizeFromTransaction(AccessoriesSize accessoriesSize) {
		System.out.println("it'Execute");
		JSONObject objmain = new JSONObject();

		objmain.put("result", storeService.deleteReturnSizeFromTransaction(accessoriesSize));

		return objmain;
	}

	@RequestMapping(value = "/editReturnSizeInTransaction",method=RequestMethod.GET)
	public @ResponseBody JSONObject editReturnSizeInTransaction(AccessoriesSize accessoriesSize) {
		System.out.println("it'Execute");
		JSONObject objmain = new JSONObject();

		objmain.put("result", storeService.editReturnSizeInTransaction(accessoriesSize));

		return objmain;
	}
	@RequestMapping(value = "/getAccessoriesReturnList", method = RequestMethod.GET)
	public JSONObject getAccessoriesReturnList() {
		JSONObject mainObject = new JSONObject();
		List<AccessoriesReturn> accessoriesReturnList = storeService.getAccessoriesReturnList();
		mainObject.put("accessoriesReturnList",accessoriesReturnList);
		return mainObject;
	}
	@RequestMapping(value = "/getAccessoriesReturnInfo", method = RequestMethod.GET)
	public JSONObject getAccessoriesReturnInfo(String returnTransactionId) {
		JSONObject mainObject = new JSONObject();
		AccessoriesReturn accessoriesReturn = storeService.getAccessoriesReturnInfo(returnTransactionId);
		mainObject.put("accessoriesReturn",accessoriesReturn);
		return mainObject;
	}

	@RequestMapping(value = "/getAccessoriesReceiveInfoForReturn", method = RequestMethod.GET)
	public JSONObject getAccessoriesReceiveInfoForReturn(String transactionId) {
		JSONObject mainObject = new JSONObject();
		AccessoriesReceive accessoriesReceive = storeService.getAccessoriesReceiveInfoForReturn(transactionId);
		mainObject.put("accessoriesReceive",accessoriesReceive);
		return mainObject;
	}


	//Accessories Issue
	@RequestMapping(value = "/accessories_issue",method=RequestMethod.GET)
	public ModelAndView accessories_issue(ModelMap map,HttpSession session) {
		
		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");
		String departmentId=passService.getUserDepartmentId(userId);
		
		ModelAndView view = new ModelAndView("store/accessories-issue");
		List<Department> departmentList = registerService.getDepartmentList();
		List<CuttingInformation> requisitionList =  productionService.getReceiveCuttingBodyInfoList();
		map.addAttribute("departmentList",departmentList);
		map.addAttribute("requisitionList",requisitionList);
		
		map.addAttribute("userId",userId);
		map.addAttribute("userName",userName);
		map.addAttribute("departmentId",departmentId);
		
		return view; 
	}


	@RequestMapping(value = "/getRequisitionAccessoriesList", method = RequestMethod.GET)
	public JSONObject getRequisitionAccessoriesList(String cuttingEntryId,String departmentId) {
		JSONObject mainObject = new JSONObject();
		List<AccessoriesSize> accessoriesList = storeService.getRequisitionAccessoriesList(cuttingEntryId,departmentId);
		mainObject.put("accessoriesList",accessoriesList);
		return mainObject;
	}

	@RequestMapping(value = "/getRequisitionAccessoriesSizeList", method = RequestMethod.GET)
	public JSONObject getRequisitionAccessoriesSizeList(String cuttingEntryId,String accessoriesIdList,String departmentId) {
		JSONObject mainObject = new JSONObject();
		List<AccessoriesSize> accessoriesSizeList = storeService.getRequisitionAccessoriesSizeList(cuttingEntryId,accessoriesIdList,departmentId);
		mainObject.put("accessoriesSizeList",accessoriesSizeList);
		return mainObject;
	}

	@RequestMapping(value = "/getAvailableAccessoriesSizeList", method = RequestMethod.GET)
	public JSONObject getAvailableAccessoriesSizeList(String departmentId) {
		JSONObject mainObject = new JSONObject();
		List<AccessoriesSize> accessoriesSizeList = storeService.getAvailableAccessoriesSizeListInDepartment(departmentId);
		mainObject.put("accessoriesSizeList",accessoriesSizeList);
		return mainObject;
	}

	@RequestMapping(value = "/submitAccessoriesIssue",method=RequestMethod.POST)
	public @ResponseBody JSONObject submitAccessoriesIssue(AccessoriesIssue	accessoriesIssue) {
		System.out.println("it'Execute");
		JSONObject objmain = new JSONObject();
		if(storeService.submitAccessoriesIssue(accessoriesIssue)) {
			objmain.put("result", "successfull");
		}else {
			objmain.put("result", "duplicate");
		}
		return objmain;
	}

	@RequestMapping(value = "/editAccessoriesIssue",method=RequestMethod.POST)
	public @ResponseBody JSONObject editAccessoriesIssue(AccessoriesIssue	accessoriesIssue) {
		System.out.println("it'Execute");
		JSONObject objmain = new JSONObject();
		if(storeService.editAccessoriesIssue(accessoriesIssue)) {
			objmain.put("result", "successfull");
		}else {
			objmain.put("result", "duplicate");
		}
		return objmain;
	}

	@RequestMapping(value = "/deleteIssueSizeFromTransaction",method=RequestMethod.GET)
	public @ResponseBody JSONObject deleteIssueSizeFromTransaction(AccessoriesSize accessoriesSize) {
		System.out.println("it'Execute");
		JSONObject objmain = new JSONObject();

		objmain.put("result", storeService.deleteIssuedSizeFromTransaction(accessoriesSize));

		return objmain;
	}

	@RequestMapping(value = "/editIssueSizeInTransaction",method=RequestMethod.GET)
	public @ResponseBody JSONObject editIssueSizeInTransaction(AccessoriesSize accessoriesSize) {
		System.out.println("it'Execute");
		JSONObject objmain = new JSONObject();

		objmain.put("result", storeService.editIssuedSizeInTransaction(accessoriesSize));

		return objmain;
	}
	@RequestMapping(value = "/getAccessoriesIssueList", method = RequestMethod.GET)
	public JSONObject getAccessoriesIssueList() {
		JSONObject mainObject = new JSONObject();
		List<AccessoriesIssue> accessoriesIssueList = storeService.getAccessoriesIssueList();
		mainObject.put("accessoriesIssueList",accessoriesIssueList);
		return mainObject;
	}
	@RequestMapping(value = "/getAccessoriesIssueInfo", method = RequestMethod.GET)
	public JSONObject getAccessoriesIssueInfo(String transactionId) {
		JSONObject mainObject = new JSONObject();
		AccessoriesIssue accessoriesIssue = storeService.getAccessoriesIssueInfo(transactionId);
		mainObject.put("accessoriesIssue",accessoriesIssue);
		return mainObject;
	}



	//Accessories IssueReturn
	@RequestMapping(value = "/accessories_issue_return",method=RequestMethod.GET)
	public ModelAndView accessories_issue_return(ModelMap map,HttpSession session) {
		
		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");
		String departmentId=passService.getUserDepartmentId(userId);
		
		ModelAndView view = new ModelAndView("store/accessories-issue-return");
		List<Department> departmentList = registerService.getDepartmentList();
		map.addAttribute("departmentList",departmentList);
		
		map.addAttribute("userId",userId);
		map.addAttribute("userName",userName);
		map.addAttribute("departmentId",departmentId);
		
		return view; 
	}

	@RequestMapping(value = "/getIssuedAccessoriesSizeList", method = RequestMethod.GET)
	public JSONObject getIssuedAccessoriesSizeList(String departmentId,String returnDepartmentId) {
		JSONObject mainObject = new JSONObject();
		List<AccessoriesSize> accessoriesSizeList = storeService.getIssuedAccessoriesSizeListInDepartment(departmentId,returnDepartmentId);
		mainObject.put("accessoriesSizeList",accessoriesSizeList);
		return mainObject;
	}


	@RequestMapping(value = "/submitAccessoriesIssueReturn",method=RequestMethod.POST)
	public @ResponseBody JSONObject submitAccessoriesIssueReturn(AccessoriesIssueReturn	accessoriesIssueReturn) {

		JSONObject objmain = new JSONObject();
		if(storeService.submitAccessoriesIssueReturn(accessoriesIssueReturn)) {
			objmain.put("result", "successfull");
		}else {
			objmain.put("result", "duplicate");
		}
		return objmain;
	}

	@RequestMapping(value = "/editAccessoriesIssueReturn",method=RequestMethod.POST)
	public @ResponseBody JSONObject editAccessoriesIssueReturn(AccessoriesIssueReturn	accessoriesIssueReturn) {
		System.out.println("it'Execute");
		JSONObject objmain = new JSONObject();
		if(storeService.editAccessoriesIssueReturn(accessoriesIssueReturn)) {
			objmain.put("result", "successfull");
		}else {
			objmain.put("result", "duplicate");
		}
		return objmain;
	}

	@RequestMapping(value = "/deleteIssueReturnSizeFromTransaction",method=RequestMethod.GET)
	public @ResponseBody JSONObject deleteIssueReturnSizeFromTransaction(AccessoriesSize accessoriesSize) {
		System.out.println("it'Execute");
		JSONObject objmain = new JSONObject();

		objmain.put("result", storeService.deleteIssueReturndSizeFromTransaction(accessoriesSize));

		return objmain;
	}

	@RequestMapping(value = "/editIssueReturnSizeInTransaction",method=RequestMethod.GET)
	public @ResponseBody JSONObject editIssueReturnSizeInTransaction(AccessoriesSize accessoriesSize) {
		System.out.println("it'Execute");
		JSONObject objmain = new JSONObject();

		objmain.put("result", storeService.editIssueReturndSizeInTransaction(accessoriesSize));

		return objmain;
	}
	@RequestMapping(value = "/getAccessoriesIssueReturnList", method = RequestMethod.GET)
	public JSONObject getAccessoriesIssueReturnList() {
		JSONObject mainObject = new JSONObject();
		List<AccessoriesIssueReturn> accessoriesIssueReturnList = storeService.getAccessoriesIssueReturnList();
		mainObject.put("accessoriesIssueReturnList",accessoriesIssueReturnList);
		return mainObject;
	}
	@RequestMapping(value = "/getAccessoriesIssueReturnInfo", method = RequestMethod.GET)
	public JSONObject getAccessoriesIssueReturnInfo(String transactionId) {
		JSONObject mainObject = new JSONObject();
		AccessoriesIssueReturn accessoriesIssueReturn = storeService.getAccessoriesIssueReturnInfo(transactionId);
		mainObject.put("accessoriesIssueReturn",accessoriesIssueReturn);
		return mainObject;
	}


	@RequestMapping(value = "/accessories_transfer_out",method=RequestMethod.GET)
	public ModelAndView accessories_transer_out(ModelMap map,HttpSession session) {
		
		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");
		String departmentId=passService.getUserDepartmentId(userId);
		
		ModelAndView view = new ModelAndView("store/accessories-transfer-out");
		List<Department> departmentList = registerService.getDepartmentList();
		map.addAttribute("departmentList",departmentList);
		
		map.addAttribute("userId",userId);
		map.addAttribute("userName",userName);
		map.addAttribute("departmentId",departmentId);
		
		return view; 
	}

	@RequestMapping(value = "/submitAccessoriesTransferOut",method=RequestMethod.POST)
	public @ResponseBody JSONObject submitAccessoriesTransferOut(AccessoriesTransferOut	accessoriesTransferOut) {
		System.out.println("it'Execute");
		JSONObject objmain = new JSONObject();
		if(storeService.submitAccessoriesTransferOut(accessoriesTransferOut)) {
			objmain.put("result", "successfull");
		}else {
			objmain.put("result", "duplicate");
		}
		return objmain;
	}

	@RequestMapping(value = "/editAccessoriesTransferOut",method=RequestMethod.POST)
	public @ResponseBody JSONObject editAccessoriesTransferOut(AccessoriesTransferOut	accessoriesTransferOut) {
		System.out.println("it'Execute");
		JSONObject objmain = new JSONObject();
		if(storeService.editAccessoriesTransferOut(accessoriesTransferOut)) {
			objmain.put("result", "successfull");
		}else {
			objmain.put("result", "duplicate");
		}
		return objmain;
	}

	@RequestMapping(value = "/deleteTransferOutSizeFromTransaction",method=RequestMethod.GET)
	public @ResponseBody JSONObject deleteTransferOutSizeFromTransaction(AccessoriesSize accessoriesSize) {
		System.out.println("it'Execute");
		JSONObject objmain = new JSONObject();

		objmain.put("result", storeService.deleteTransferOutdSizeFromTransaction(accessoriesSize));

		return objmain;
	}

	@RequestMapping(value = "/editTransferOutSizeInTransaction",method=RequestMethod.GET)
	public @ResponseBody JSONObject editTransferOutSizeInTransaction(AccessoriesSize accessoriesSize) {
		System.out.println("it'Execute");
		JSONObject objmain = new JSONObject();

		objmain.put("result", storeService.editTransferOutdSizeInTransaction(accessoriesSize));

		return objmain;
	}
	@RequestMapping(value = "/getAccessoriesTransferOutList", method = RequestMethod.GET)
	public JSONObject getAccessoriesTransferOutList() {
		JSONObject mainObject = new JSONObject();
		List<AccessoriesTransferOut> accessoriesTransferOutList = storeService.getAccessoriesTransferOutList();
		mainObject.put("accessoriesTransferOutList",accessoriesTransferOutList);
		return mainObject;
	}
	@RequestMapping(value = "/getAccessoriesTransferOutInfo", method = RequestMethod.GET)
	public JSONObject getAccessoriesTransferOutInfo(String transactionId) {
		JSONObject mainObject = new JSONObject();
		AccessoriesTransferOut accessoriesTransferOut = storeService.getAccessoriesTransferOutInfo(transactionId);
		mainObject.put("accessoriesTransferOut",accessoriesTransferOut);
		return mainObject;
	}



	@RequestMapping(value = "/accessories_transfer_in",method=RequestMethod.GET)
	public ModelAndView accessories_transfer_in(ModelMap map,HttpSession session) {
		
		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");
		String departmentId=passService.getUserDepartmentId(userId);
		ModelAndView view = new ModelAndView("store/accessories-transfer-in");
		List<Department> departmentList = registerService.getDepartmentList();
		map.addAttribute("departmentList",departmentList);
		map.addAttribute("userId",userId);
		map.addAttribute("userName",userName);
		map.addAttribute("departmentId",departmentId);
		return view; 
	}
	
	
	@RequestMapping(value = "/getTransferInAccessoriesSizeList", method = RequestMethod.GET)
	public JSONObject getTransferInAccessoriesSizeList(String departmentId,String transferDepartmentId) {
		JSONObject mainObject = new JSONObject();
		List<AccessoriesSize> accessoriesSizeList = storeService.getTransferInAccessoriesSizeList(departmentId,transferDepartmentId);
		mainObject.put("accessoriesSizeList",accessoriesSizeList);
		return mainObject;
	}

	@RequestMapping(value = "/submitAccessoriesTransferIn",method=RequestMethod.POST)
	public @ResponseBody JSONObject submitAccessoriesTransferIn(AccessoriesTransferIn	accessoriesTransferIn) {
		System.out.println("it'Execute");
		JSONObject objmain = new JSONObject();
		if(storeService.submitAccessoriesTransferIn(accessoriesTransferIn)) {
			objmain.put("result", "successfull");
		}else {
			objmain.put("result", "duplicate");
		}
		return objmain;
	}

	@RequestMapping(value = "/editAccessoriesTransferIn",method=RequestMethod.POST)
	public @ResponseBody JSONObject editAccessoriesTransferIn(AccessoriesTransferIn	accessoriesTransferIn) {
		System.out.println("it'Execute");
		JSONObject objmain = new JSONObject();
		if(storeService.editAccessoriesTransferIn(accessoriesTransferIn)) {
			objmain.put("result", "successfull");
		}else {
			objmain.put("result", "duplicate");
		}
		return objmain;
	}

	@RequestMapping(value = "/deleteTransferInSizeFromTransaction",method=RequestMethod.GET)
	public @ResponseBody JSONObject deleteTransferInSizeFromTransaction(AccessoriesSize accessoriesSize) {
		System.out.println("it'Execute");
		JSONObject objmain = new JSONObject();

		objmain.put("result", storeService.deleteTransferIndSizeFromTransaction(accessoriesSize));

		return objmain;
	}

	@RequestMapping(value = "/editTransferInSizeInTransaction",method=RequestMethod.GET)
	public @ResponseBody JSONObject editTransferInSizeInTransaction(AccessoriesSize accessoriesSize) {
		System.out.println("it'Execute");
		JSONObject objmain = new JSONObject();

		objmain.put("result", storeService.editTransferIndSizeInTransaction(accessoriesSize));

		return objmain;
	}
	@RequestMapping(value = "/getAccessoriesTransferInList", method = RequestMethod.GET)
	public JSONObject getAccessoriesTransferInList() {
		JSONObject mainObject = new JSONObject();
		List<AccessoriesTransferIn> accessoriesTransferInList = storeService.getAccessoriesTransferInList();
		mainObject.put("accessoriesTransferInList",accessoriesTransferInList);
		return mainObject;
	}
	@RequestMapping(value = "/getAccessoriesTransferInInfo", method = RequestMethod.GET)
	public JSONObject getAccessoriesTransferInInfo(String transactionId) {
		JSONObject mainObject = new JSONObject();
		AccessoriesTransferIn accessoriesTransferIn = storeService.getAccessoriesTransferInInfo(transactionId);
		mainObject.put("accessoriesTransferIn",accessoriesTransferIn);
		return mainObject;
	}



	@RequestMapping(value = "/general_item_create",method=RequestMethod.GET)
	public ModelAndView general_item_create(ModelMap map,HttpSession session) {
		
		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");
		String departmentId=passService.getUserDepartmentId(userId);
		ModelAndView view = new ModelAndView("store/general_item_create");
		List<Unit> unitList= registerService.getUnitList();
		List<StoreGeneralCategory> catList= registerService.getStoreCategoryList();
		List<StoreGeneralCategory> List= storeService.getStoreGeneralItemList();
		view.addObject("unitList", unitList);
		view.addObject("catList", catList);
		view.addObject("itemList", List);
		
		map.addAttribute("userId",userId);
		map.addAttribute("userName",userName);
		map.addAttribute("departmentId",departmentId);

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
		
		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");
		String departmentId=passService.getUserDepartmentId(userId);
		
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
		
		map.addAttribute("userId",userId);
		map.addAttribute("userName",userName);
		map.addAttribute("departmentId",departmentId);

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


	@RequestMapping(value = "/cutting_fabrics_requistion",method=RequestMethod.GET)
	public ModelAndView cutting_fabrics_requistion(ModelMap map,HttpSession session) {
		
		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");
		String departmentId=passService.getUserDepartmentId(userId);
		
		List<CuttingInformation> cuttingInformationList = productionService.getCuttingInformationList();
		List<CuttingInformation> cuttingReqList = productionService.getCuttingRequisitionList();
		ModelAndView view = new ModelAndView("store/cutting_fabrics_requistion");

		view.addObject("cuttingInformationList",cuttingInformationList);
		view.addObject("cuttingReqList",cuttingReqList);
		map.addAttribute("userId",userId);
		map.addAttribute("userName",userName);
		map.addAttribute("departmentId",departmentId);
		return view; 
	}

	@RequestMapping(value = "/searchCuttingUsedFabrics",method=RequestMethod.GET)
	public @ResponseBody JSONObject searchCuttingUsedFabrics(String cuttingEntryId) {
		JSONObject objmain = new JSONObject();

		List<CuttingFabricsUsed> List= storeService.getCuttingUsedFabricsList(cuttingEntryId);

		objmain.put("result", List);

		return objmain;
	}

	@RequestMapping(value = "/sendCuttingFabricsRequistion",method=RequestMethod.POST)
	public @ResponseBody String sendCuttingFabricsRequistion(CuttingFabricsUsed v) {

		String msg="Create occure while send requistion";

		if(storeService.sendCuttingFabricsRequistion(v)) {
			msg="Send requistion sucessfully";

		}

		return msg;
	}

	@RequestMapping(value = "/printCuttingUsedFabricsInfo/{cuttingEntryId}")
	public @ResponseBody ModelAndView printLayoutInfo(ModelMap map,@PathVariable ("cuttingEntryId") String cuttingEntryId) {

		ModelAndView view=new ModelAndView("store/printCuttingFabricsRequisitionReport");
		map.addAttribute("cuttingEntryId", cuttingEntryId);


		return view;
	}

	@RequestMapping(value = "/cutting_fabrics_issue",method=RequestMethod.GET)
	public ModelAndView cutting_fabrics_issue(ModelMap map,HttpSession session) {
		
		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");
		String departmentId=passService.getUserDepartmentId(userId);
		
		List<CuttingInformation> cuttingReqList = productionService.getCuttingRequisitionList();
		ModelAndView view = new ModelAndView("store/cutting_fabrics_issue");

		//view.addObject("cuttingInformationList",cuttingInformationList);
		view.addObject("cuttingReqList",cuttingReqList);
		map.addAttribute("userId",userId);
		map.addAttribute("userName",userName);
		map.addAttribute("departmentId",departmentId);
		
		return view; 
	}

	@RequestMapping(value = "/searchCuttingUsedFabricsRequisition",method=RequestMethod.GET)
	public @ResponseBody JSONObject searchCuttingUsedFabricsRequisition(String cuttingEntryId,String departmentId) {
		JSONObject objmain = new JSONObject();

		List<FabricsRoll> fabricsRollList= storeService.getCuttingUsedFabricsRequisitionList(cuttingEntryId,departmentId);

		objmain.put("fabricsRollList", fabricsRollList);

		return objmain;
	}


	//fabrics pending transaction
	@RequestMapping(value = "/fabrics_pending_transaction",method=RequestMethod.GET)
	public ModelAndView fabrics_pending_transaction(ModelMap map,HttpSession session) {
		
		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");
		String departmentId=passService.getUserDepartmentId(userId);
		
		ModelAndView view = new ModelAndView("store/fabrics-pending-transaction");
		List<Login> lg = (List<Login>)session.getAttribute("pg_admin");
		
		List<PendingTransaction> pendingList = storeService.getPendingTransactionList(lg.get(0).getDepartmentId());
		view.addObject("pendingList",pendingList);
		
		map.addAttribute("userId",userId);
		map.addAttribute("userName",userName);
		map.addAttribute("departmentId",departmentId);
		
		return view; 
	}

	
	@RequestMapping(value = "/printFabricsIssue/{transactionId}/{transactionType}")
	public @ResponseBody ModelAndView printFabricsIssue(ModelMap map,@PathVariable ("transactionId") String transactionId,@PathVariable ("transactionType") String transactionType) {


		
		ModelAndView view=new ModelAndView("store/printFabricsIssue");
		map.addAttribute("transactionId", transactionId);
		map.addAttribute("transactionType", transactionType);
		

		return view;
	}
	
	@RequestMapping(value = "/fabricsIssueReceive",method=RequestMethod.POST)
	public @ResponseBody String fabricsIssueReceive(String transactionId,String transactionType,String departmentId,String userId) {

		if(storeService.fabricsIssueReceive(transactionId,transactionType,departmentId,userId))
			return "true";
		return "false";
	}
	
	@RequestMapping(value = "/getPendingFabricsIssueList",method=RequestMethod.GET)
	public @ResponseBody JSONObject getPendingFabricsIssueList(String departmentId,String fromDate,String toDate,String itemType,String approveType) {
		JSONObject objmain = new JSONObject();
		objmain.put("pendingList", storeService.getPendingFabricsIssueList(departmentId,fromDate,toDate,itemType,approveType));
		
		return objmain;
	}
	
	//accessories pending transaction
	@RequestMapping(value = "/accessories_pending_transaction",method=RequestMethod.GET)
	public ModelAndView accessories_pending_transaction(ModelMap map,HttpSession session) {
		
		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");
		String departmentId=passService.getUserDepartmentId(userId);
		
		ModelAndView view = new ModelAndView("store/fabrics-pending-transaction");
		List<Department> departmentList = registerService.getDepartmentList();
		List<CuttingInformation> cuttingReqList = productionService.getCuttingRequisitionList();
		map.addAttribute("departmentList",departmentList);
		view.addObject("cuttingReqList",cuttingReqList);
		
		map.addAttribute("userId",userId);
		map.addAttribute("userName",userName);
		map.addAttribute("departmentId",departmentId);
		
		return view; 
	}
	
	
	// Stock Position
	@RequestMapping(value = "/stock_position",method=RequestMethod.GET)
	public ModelAndView stock_position(ModelMap map,HttpSession session) {
		
		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");
		String departmentId=passService.getUserDepartmentId(userId);
		
		ModelAndView view = new ModelAndView("store/stock-position");
	
		
		String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		List<StockItem> stockItemList = storeService.getStockItemSummeryList(currentDate,currentDate,departmentId);
		view.addObject("stockItemList",stockItemList);
		
		map.addAttribute("userId",userId);
		map.addAttribute("userName",userName);
		map.addAttribute("departmentId",departmentId);
		
		return view; 
	}
	
	@RequestMapping(value = "/getStockItemPositionSummery",method=RequestMethod.GET)
	public @ResponseBody JSONObject getStockItemPositionSummery(String fromDate,String toDate,String departmentId) {
		JSONObject objmain = new JSONObject();

		List<StockItem> stockItemList = storeService.getStockItemSummeryList(fromDate,toDate,departmentId);

		objmain.put("itemList", stockItemList);

		return objmain;
	}
	
	// Stock Position Details
		@RequestMapping(value = "/stock_position_details",method=RequestMethod.GET)
		public ModelAndView stock_position_details(ModelMap map,HttpSession session) {
			
			String userId=(String)session.getAttribute("userId");
			String userName=(String)session.getAttribute("userName");
			String departmentId=passService.getUserDepartmentId(userId);
			
			ModelAndView view = new ModelAndView("store/stock-position-details");
			
			
			String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			List<StockItem> stockItemList = storeService.getStockItemDetailsList(currentDate,currentDate,departmentId);
			
			System.out.println("list size-"+stockItemList.size());
			view.addObject("stockItemList",stockItemList);
			
			map.addAttribute("userId",userId);
			map.addAttribute("userName",userName);
			map.addAttribute("departmentId",departmentId);
			return view; 
		}
		
		@RequestMapping(value = "/getStockItemPositionDetails",method=RequestMethod.GET)
		public @ResponseBody JSONObject getStockItemPositionDetails(String fromDate,String toDate,String departmentId) {
			JSONObject objmain = new JSONObject();

			List<StockItem> stockItemList = storeService.getStockItemDetailsList(fromDate,toDate,departmentId);

			objmain.put("itemList", stockItemList);

			return objmain;
		}
}
