package pg.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

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
import pg.storeModel.FabricsTransferIn;
import pg.storeModel.FabricsTransferOut;

@Controller
@RestController
public class StoreController {

	@Autowired
	private RegisterService registerService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private StoreService storeService;


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
			ModelAndView view = new ModelAndView("store/fabrics-transfer-out");
			List<Department> departmentList = registerService.getDepartmentList();
			map.addAttribute("departmentList",departmentList);
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
			ModelAndView view = new ModelAndView("store/fabrics-transfer-in");
			List<Department> departmentList = registerService.getDepartmentList();
			map.addAttribute("departmentList",departmentList);
			return view; 
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
}
