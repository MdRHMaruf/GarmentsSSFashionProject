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
import pg.registerModel.SupplierModel;
import pg.registerModel.Unit;
import pg.services.OrderService;
import pg.services.RegisterService;
import pg.services.StoreService;
import pg.storeModel.FabricsQualityControl;
import pg.storeModel.FabricsReceive;
import pg.storeModel.FabricsReturn;

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
	@RequestMapping(value = "/getFabricsReceiveList", method = RequestMethod.GET)
	public JSONObject getFabricsReceiveList() {
		JSONObject mainObject = new JSONObject();
		List<FabricsReceive> fabricsReceiveList = storeService.getFabricsReceiveList();
		mainObject.put("fabricsReceiveList",fabricsReceiveList);
		return mainObject;
	}

	@RequestMapping(value = "/getFabricsReceiveInfo", method = RequestMethod.GET)
	public JSONObject getFabricsReceiveInfo(String transectionId) {
		JSONObject mainObject = new JSONObject();
		FabricsReceive fabricsReceive = storeService.getFabricsReceiveInfo(transectionId);
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
	public JSONObject getFabricsQCInfo(String qcTransectionId) {
		JSONObject mainObject = new JSONObject();
		FabricsQualityControl fabricsQC = storeService.getFabricsQCInfo(qcTransectionId);
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
	@RequestMapping(value = "/getFabricsReturnList", method = RequestMethod.GET)
	public JSONObject getFabricsReturnList() {
		JSONObject mainObject = new JSONObject();
		List<FabricsReturn> fabricsReturnList = storeService.getFabricsReturnList();
		mainObject.put("fabricsReturnList",fabricsReturnList);
		return mainObject;
	}
	@RequestMapping(value = "/getFabricsReturnInfo", method = RequestMethod.GET)
	public JSONObject getFabricsReturnInfo(String returnTransectionId) {
		JSONObject mainObject = new JSONObject();
		FabricsReturn fabricsReturn = storeService.getFabricsReturnInfo(returnTransectionId);
		mainObject.put("fabricsReturn",fabricsReturn);
		return mainObject;
	}
	
	@RequestMapping(value = "/getFabricsReceiveInfoForReturn", method = RequestMethod.GET)
	public JSONObject getFabricsReceiveInfoForReturn(String transectionId) {
		JSONObject mainObject = new JSONObject();
		FabricsReceive fabricsReceive = storeService.getFabricsReceiveInfoForReturn(transectionId);
		mainObject.put("fabricsReceive",fabricsReceive);
		return mainObject;
	}

}
