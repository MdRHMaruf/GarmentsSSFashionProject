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

@Controller
@RestController
public class StoreController {
	
	@Autowired
	private RegisterService registerService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private StoreService storeService;
	


	
	@RequestMapping(value = "/getAccessoriesPurchaseOrderList",method = RequestMethod.GET)
	public JSONObject getAccessoriesPurchaseOrderList() {
		JSONObject mainObject = new JSONObject();	
		List<PurchaseOrderItem> purchaseOrderList = storeService.getAccessoriesPurchaseOrderList();	
		mainObject.put("purchaseOrderList", purchaseOrderList);
		return mainObject;
	}
	
	@RequestMapping(value = "/getFabricsListByItemId",method = RequestMethod.GET)
	public JSONObject getFabricsListByItemId(String purchaseOrder,String styleId,String itemId) {
		JSONObject mainObject = new JSONObject();
		List<FabricsIndent> fabricsList = storeService.getFabricsListByItemId(purchaseOrder,styleId,itemId);
		mainObject.put("fabricsList", fabricsList);
		return mainObject;
	}
	
	@RequestMapping(value = "/getFabricsIndentInfo", method = RequestMethod.GET)
	public JSONObject getFabricsIndentInfo(String autoId) {
		JSONObject mainObject = new JSONObject();
		FabricsIndent fabricsIndentInfo = orderService.getFabricsIndent(autoId);
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
		if(storeService.submitFabricsQC(fabricsQualityControl)) {
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
}
