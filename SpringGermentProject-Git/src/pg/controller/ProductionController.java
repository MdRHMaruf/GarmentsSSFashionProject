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

import pg.model.commonModel;
import pg.orderModel.SampleRequisitionItem;
import pg.orderModel.Style;
import pg.proudctModel.CuttingInformation;
import pg.proudctModel.ProductionPlan;
import pg.proudctModel.cuttingRequsition;
import pg.registerModel.BuyerModel;
import pg.registerModel.Department;
import pg.registerModel.FabricsItem;
import pg.registerModel.Factory;
import pg.registerModel.FactoryModel;
import pg.registerModel.Line;
import pg.registerModel.SizeGroup;
import pg.registerModel.SupplierModel;
import pg.registerModel.Unit;
import pg.services.OrderService;
import pg.services.ProductionService;
import pg.services.RegisterService;

@Controller
@RestController
public class ProductionController {

	String CuttingEntryId="";
	DecimalFormat df = new DecimalFormat("#.00");
	
	@Autowired
	private RegisterService registerService;

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ProductionService productionService;
	
	//Cutting Requisition
	@RequestMapping(value = "/cutting_requisition",method=RequestMethod.GET)
	public ModelAndView cutting_requisition(ModelMap map,HttpSession session) {

		ModelAndView view = new ModelAndView("production/cutting_requisition");
		List<SizeGroup> groupList = registerService.getStyleSizeGroupList();
		List<BuyerModel> buyerList= registerService.getAllBuyers();
		List<FabricsItem> fabricsList = registerService.getFabricsItemList();
		List<SampleRequisitionItem> sampleReqList = orderService.getSampleRequisitionList();
		List<commonModel> sampleList = orderService.getSampleList();
		List<commonModel> inchargeList = orderService.getInchargeList();
		List<commonModel> merchendizerList = orderService.getMerchendizerList();
		
	

		view.addObject("groupList",groupList);
		view.addObject("buyerList",buyerList);
		view.addObject("fabricsList",fabricsList);
		view.addObject("sampleReqList",sampleReqList);
		view.addObject("sampleList",sampleList);
		view.addObject("inchargeList",inchargeList);
		view.addObject("merchendizerList",merchendizerList);
	
		return view; //JSP - /WEB-INF/view/index.jsp
	}
	
	//Cutting Requisition
	@RequestMapping(value = "/cuttingRequisitionEnty",method=RequestMethod.POST)
	public @ResponseBody String cuttingRequisitionEnty(cuttingRequsition v) {
		String msg="Create occure while entry cutting requisition entry";
		boolean flag= productionService.cuttingRequisitionEnty(v);
		if(flag) {
			msg="Cutting requisition entry successfull!!";
		}
		return msg;
	}
	
	//Fabrics Receive 
	@RequestMapping(value = "/fabrics_received",method=RequestMethod.GET)
	public ModelAndView fabrics_receive(ModelMap map,HttpSession session) {
		ModelAndView view = new ModelAndView("store/fabrics-receive");
		List<Unit> unitList= registerService.getUnitList();
		List<SupplierModel> supplierList = registerService.getAllSupplier();
		view.addObject("unitList", unitList);
		view.addObject("supplierList",supplierList);
		return view; 
	}
	
	//Production Plan
	
	@RequestMapping(value = "/production_plan",method=RequestMethod.GET)
	public ModelAndView production_plan(ModelMap map,HttpSession session) {

		ModelAndView view = new ModelAndView("production/production_plan");
		List<SizeGroup> groupList = registerService.getStyleSizeGroupList();
		List<BuyerModel> buyerList= registerService.getAllBuyers();
		List<FabricsItem> fabricsList = registerService.getFabricsItemList();
		List<SampleRequisitionItem> sampleReqList = orderService.getSampleRequisitionList();
		List<commonModel> merchendizerList = orderService.getMerchendizerList();
		List<ProductionPlan> productionPlanList = productionService.getProductionPlanList();

		view.addObject("groupList",groupList);
		view.addObject("buyerList",buyerList);
		view.addObject("fabricsList",fabricsList);
		view.addObject("sampleReqList",sampleReqList);
		view.addObject("merchendizerList",merchendizerList);
		view.addObject("productionPlanList",productionPlanList);
		return view; //JSP - /WEB-INF/view/index.jsp
	}
	
	@RequestMapping(value = "/styleItemsWiseOrder",method=RequestMethod.POST)
	public @ResponseBody String styleItemsWiseOrder(String buyerorderid,String style,String item) {
		
		String orderQty=productionService.getOrderQty(buyerorderid,style,item);
		return df.format(Double.parseDouble(orderQty));
	}
	
	@RequestMapping(value = "/productionPlanSave",method=RequestMethod.POST)
	public @ResponseBody JSONObject productionPlanSave(ProductionPlan v) {
		
		JSONObject objmain = new JSONObject();
		JSONArray mainarray = new JSONArray();

		if(!productionService.checkDoplicationPlanSet(v)) {
			boolean flag=productionService.productionPlanSave(v);
			if(flag) {
				objmain.put("result", "Successfull!!");
			}
		}
		else {
			objmain.put("result", "Doplication Information Never Be Save");
		}
		
		return objmain;
	}
	
	@RequestMapping(value = "/searchProductionPlan",method=RequestMethod.POST)
	public @ResponseBody JSONObject searchProductionPlan(String buyerId,String buyerorderId,String styleId) {
		JSONObject objmain = new JSONObject();
		
		List<ProductionPlan> groupList = productionService.getProductionPlan(buyerId,buyerorderId,styleId);
		objmain.put("result", groupList);
		
		return objmain;
	}
	
	//Cutting Plan
	@RequestMapping(value = "/cutting_plan",method=RequestMethod.GET)
	public ModelAndView cutting_plan(ModelMap map,HttpSession session) {

		ModelAndView view = new ModelAndView("production/cutting_plan");
		List<SizeGroup> groupList = registerService.getStyleSizeGroupList();
		List<Factory> factoryList= registerService.getFactoryNameList();


		List<commonModel> inchargeList = orderService.getInchargeList();
		List<commonModel> merchendizerList = orderService.getMerchendizerList();
		
		List<ProductionPlan> productionPlanList = productionService.getProductionPlanForCutting();
		List<CuttingInformation> cuttingInformationList = productionService.getCuttingInformationList();

		view.addObject("groupList",groupList);
		view.addObject("factoryList",factoryList);

		view.addObject("inchargeList",inchargeList);
		view.addObject("merchendizerList",merchendizerList);
		view.addObject("productionPlanList",productionPlanList);
		view.addObject("cuttingInformationList",cuttingInformationList);
	
		return view; //JSP - /WEB-INF/view/index.jsp
	}
	
	@ResponseBody
	@RequestMapping(value = "/factoryWiseDepartmentLoad/{factoryId}",method=RequestMethod.GET)
	public JSONObject factoryWiseDepartmentLoad(@PathVariable ("factoryId") String factoryId) {
		JSONObject objMain = new JSONObject();	
		List<Department> departmentList = productionService.getFactoryWiseDepartmentLoad(factoryId);

		objMain.put("departmentList",departmentList);
		return objMain; 
	}
	
	@ResponseBody
	@RequestMapping(value = "/factoryDepartmentWiseLineLoad",method=RequestMethod.POST)
	public JSONObject factoryDepartmentWiseLineLoad(String factoryId,String departmentId) {
		JSONObject objMain = new JSONObject();	
		List<Line> lineList = productionService.getFactoryDepartmentWiseLineLoad(factoryId,departmentId);
		
		objMain.put("lineList",lineList);
		return objMain; 
	}
	
	@RequestMapping(value = "/searchBuyerPoDetails",method=RequestMethod.GET)
	public @ResponseBody JSONObject searchBuyerPoDetails(String buyerId,String buyerorderId,String styleId,String itemId) {
		JSONObject objmain = new JSONObject();

		JSONArray mainArray = new JSONArray();
		List<CuttingInformation> buyerposList = productionService.getBuyerPoDetails(buyerId,buyerorderId,styleId,itemId);
		objmain.put("result",buyerposList);

		return objmain;
	}
	
	@RequestMapping(value = "/cuttingInformationEnty",method=RequestMethod.POST)
	public @ResponseBody String cuttingInformationEnty(CuttingInformation v) {
		String msg="Create occure while entry cutting information entry";
		boolean flag= productionService.cuttingInformationEnty(v);
		if(flag) {
			msg="Cutting information entry successfull!!";
		}
		return msg;
	}
	
	@RequestMapping(value = "/setCuttingEntryId",method=RequestMethod.GET)
	public @ResponseBody String setCuttingEntryId(String cuttingEntryId) {
		
		this.CuttingEntryId=cuttingEntryId;
		System.out.println("CuttingEntryId "+CuttingEntryId);
		return "Sucess"; 
	}
	
	@RequestMapping(value = "/printCuttingInformationReport",method=RequestMethod.GET)
	public @ResponseBody ModelAndView printCuttingInformationReport() {
		
		System.out.println("printCuttingInformationReport");
		ModelAndView view=new ModelAndView("production/printCuttingInformationReport");
		
		return view;
	}
}
