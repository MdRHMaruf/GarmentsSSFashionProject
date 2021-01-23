
package pg.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import pg.model.CommonModel;
import pg.model.Login;
import pg.orderModel.SampleRequisitionItem;
import pg.orderModel.Style;
import pg.proudctionModel.CuttingInformation;
import pg.proudctionModel.SewingLinesModel;
import pg.proudctionModel.ProductionPlan;
import pg.proudctionModel.CuttingRequsition;
import pg.proudctionModel.Process;
import pg.registerModel.BuyerModel;
import pg.registerModel.Department;
import pg.registerModel.Employee;
import pg.registerModel.FabricsItem;
import pg.registerModel.Factory;
import pg.registerModel.FactoryModel;
import pg.registerModel.Line;
import pg.registerModel.Machine;
import pg.registerModel.ProcessInfo;
import pg.registerModel.SizeGroup;
import pg.registerModel.SupplierModel;
import pg.registerModel.Unit;
import pg.services.OrderService;
import pg.services.PasswordService;
import pg.services.ProductionService;
import pg.services.RegisterService;
import pg.share.ProductionType;

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
	private PasswordService passService;

	@Autowired
	private ProductionService productionService;

	String BuyerId="";
	String BuyerorderId="";
	String StyleId="";
	String ItemId="";
	String ProductionDate="";
	String LayoutDate="";
	String LineId="";

	//Cutting Requisition
	@RequestMapping(value = "/cutting_requisition",method=RequestMethod.GET)
	public ModelAndView cutting_requisition(ModelMap map,HttpSession session) {

		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");
		
		ModelAndView view = new ModelAndView("production/cutting_requisition");
		List<SizeGroup> groupList = registerService.getStyleSizeGroupList();
		List<BuyerModel> buyerList= registerService.getAllBuyers();
		List<FabricsItem> fabricsList = registerService.getFabricsItemList();
		List<SampleRequisitionItem> sampleReqList = orderService.getSampleRequisitionList();
		List<CommonModel> sampleList = orderService.getSampleList();
		List<CommonModel> inchargeList = orderService.getInchargeList();
		List<CommonModel> merchendizerList = orderService.getMerchendizerList();



		view.addObject("groupList",groupList);
		view.addObject("buyerList",buyerList);
		view.addObject("fabricsList",fabricsList);
		view.addObject("sampleReqList",sampleReqList);
		view.addObject("sampleList",sampleList);
		view.addObject("inchargeList",inchargeList);
		view.addObject("merchendizerList",merchendizerList);
		
		map.addAttribute("userId",userId);
		map.addAttribute("userName",userName);


		return view; //JSP - /WEB-INF/view/index.jsp
	}

	//Cutting Requisition
	@RequestMapping(value = "/cuttingRequisitionEnty",method=RequestMethod.POST)
	public @ResponseBody String cuttingRequisitionEnty(CuttingRequsition v) {
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
		
		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");
		String departmentId=passService.getUserDepartmentId(userId);
		
		ModelAndView view = new ModelAndView("store/fabrics-receive");
		List<Unit> unitList= registerService.getUnitList();
		List<SupplierModel> supplierList = registerService.getAllSupplier();
		view.addObject("unitList", unitList);
		view.addObject("supplierList",supplierList);
		
		map.addAttribute("userId",userId);
		map.addAttribute("userName",userName);
		map.addAttribute("departmentId",departmentId);
		return view; 
	}

	//Production Plan

	@RequestMapping(value = "/production_plan",method=RequestMethod.GET)
	public ModelAndView production_plan(ModelMap map,HttpSession session) {

		
		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");
		
		ModelAndView view = new ModelAndView("production/production_plan");
		List<SizeGroup> groupList = registerService.getStyleSizeGroupList();
		List<BuyerModel> buyerList= registerService.getAllBuyers();
		List<FabricsItem> fabricsList = registerService.getFabricsItemList();
		List<SampleRequisitionItem> sampleReqList = orderService.getSampleRequisitionList();
		List<CommonModel> merchendizerList = orderService.getMerchendizerList();
		List<ProductionPlan> productionPlanList = productionService.getProductionPlanList();

		view.addObject("groupList",groupList);
		view.addObject("buyerList",buyerList);
		view.addObject("fabricsList",fabricsList);
		view.addObject("sampleReqList",sampleReqList);
		view.addObject("merchendizerList",merchendizerList);
		view.addObject("productionPlanList",productionPlanList);
		
		map.addAttribute("userId",userId);
		map.addAttribute("userName",userName);
		
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


	@RequestMapping(value="/getProductionPlan/{buyerId}/{styleId}/{buyerorderId}")
	public @ResponseBody ModelAndView getPurchaseOrderReport(ModelMap map,@PathVariable String buyerId,@PathVariable String styleId,@PathVariable String buyerorderId) {
		ModelAndView view = new ModelAndView("production/ProductionPlanReportView");

		System.out.println("buyerId "+buyerId);
		map.addAttribute("buyerId",buyerId);
		map.addAttribute("styleId",styleId);
		map.addAttribute("buyerorderId",buyerorderId);
		return view;
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

		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");
		
		ModelAndView view = new ModelAndView("production/cutting_plan");
		List<SizeGroup> groupList = registerService.getStyleSizeGroupList();
		List<Factory> factoryList= registerService.getFactoryNameList();


		List<CommonModel> inchargeList = orderService.getInchargeList();
		List<CommonModel> merchendizerList = orderService.getMerchendizerList();

		List<ProductionPlan> productionPlanList = productionService.getProductionPlanForCutting();
		List<CuttingInformation> cuttingInformationList = productionService.getCuttingInformationList();

		view.addObject("groupList",groupList);
		view.addObject("factoryList",factoryList);

		view.addObject("inchargeList",inchargeList);
		view.addObject("merchendizerList",merchendizerList);
		view.addObject("productionPlanList",productionPlanList);
		view.addObject("cuttingInformationList",cuttingInformationList);
		
		map.addAttribute("userId",userId);
		map.addAttribute("userName",userName);

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
	public @ResponseBody ModelAndView printCuttingInformationReport(ModelMap map) {

		System.out.println("printCuttingInformationReport");
		ModelAndView view=new ModelAndView("production/printCuttingInformationReport");
		map.addAttribute("CuttingEntryId", CuttingEntryId);

		return view;
	}

	//Sewing 
	@RequestMapping(value = "/sewing_line_setup",method=RequestMethod.GET)
	public ModelAndView sewing_line_setup(ModelMap map,HttpSession session) {

		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");
		
		List<Factory> factorylist = registerService.getFactoryNameList();
		List<Line> lines=productionService.getLineNames();
		List<ProductionPlan> productionPlanList = productionService.getProductionPlanForCutting();
		ModelAndView view = new ModelAndView("production/sewinglinesetup");
		view.addObject("productionPlanList",productionPlanList);
		view.addObject("factorylist",factorylist);
		map.addAttribute("lines",lines);

		map.addAttribute("userId",userId);
		map.addAttribute("userName",userName);
		
		return view; //JSP - /WEB-INF/view/index.jsp
	}


	//@RequestParam(value = "Line[]", required = false) String[] Line,

	@RequestMapping(value = "/InsertLines",method=RequestMethod.POST,consumes = { "application/json" },headers = "content-type=application/x-www-form-urlencoded")
	public String InsertLines(@RequestParam(value = "user", required = false) String user,@RequestParam(value = "style", required = false) String style,
			@RequestParam(value = "itemId", required = false) String itemId,
			@RequestParam(value = "buyerOrderId", required = false) String buyerOrderId,
			@RequestParam(value = "poNo", required = false) String poNo,
			@RequestParam(value = "Line[]", required = false) String[] Line,
			@RequestParam(value = "start", required = false) String start,
			@RequestParam(value = "end", required = false) String end,
			@RequestParam(value = "duration", required = false) String duration) throws JSONException {



		SewingLinesModel lin=new SewingLinesModel(poNo,buyerOrderId,user,style,itemId,Line, start, end, duration);
		String insertlines=productionService.InserLines(lin);

		return insertlines; //JSP - /WEB-INF/view/index.jsp
	}

	@RequestMapping(value = "/layout_plan",method=RequestMethod.GET)
	public ModelAndView layout_plan(ModelMap map,HttpSession session) {


		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");

		List<ProductionPlan> productionPlanList = productionService.getProductionPlanFromCutting();
		List<ProductionPlan> layoutList = productionService.getInspectionLayoutList(String.valueOf(ProductionType.LINE_INSPECTION_LAYOUT.getType()));
		List<Employee> employeeList = registerService.getEmployeeList();
		ModelAndView view = new ModelAndView("production/inspection_layout_plan");
		view.addObject("productionPlanList",productionPlanList);
		view.addObject("layoutList",layoutList);
		view.addObject("employeeList",employeeList);

		map.addAttribute("userId",userId);
		map.addAttribute("userName",userName);

		return view; //JSP - /WEB-INF/view/index.jsp
	}

	@RequestMapping(value = "/saveLineProductionDetails",method=RequestMethod.POST)
	public @ResponseBody String saveLineProductionDetails(ProductionPlan v) {

		String msg="Create Occure while Saving Inception Layout Details";
		boolean flag= productionService.saveLineProductionDetails(v);

		if(flag) {
			msg="Saving Sewing Inception Layout Details Sucessfully";
		}

		return msg; //JSP - /WEB-INF/view/index.jsp
	}
	
	@RequestMapping(value = "/saveFinishingProductionDetails",method=RequestMethod.POST)
	public @ResponseBody String saveFinishingProductionDetails(ProductionPlan v) {

		String msg="Create Occure while Saving Inception Layout Details";
		boolean flag= productionService.saveFinishingProductionDetails(v);

		if(flag) {
			msg="Saving Sewing Inception Layout Details Sucessfully";
		}

		return msg; //JSP - /WEB-INF/view/index.jsp
	}
	
	@RequestMapping(value = "/saveIronProductionDetails",method=RequestMethod.POST)
	public @ResponseBody String saveIronProductionDetails(ProductionPlan v) {

		String msg="Create Occure while Saving Inception Layout Details";
		boolean flag= productionService.saveIronProductionDetails(v);

		if(flag) {
			msg="Saving Sewing Inception Layout Details Sucessfully";
		}

		return msg; //JSP - /WEB-INF/view/index.jsp
	}
	
	
	@RequestMapping(value = "/saveLineInceptionLayoutLineDetails",method=RequestMethod.POST)
	public @ResponseBody String saveLineInceptionLayoutLineDetails(ProductionPlan v) {

		String msg="Create Occure while Saving Inception Layout Details";

		boolean flag= productionService.saveInceptionLayoutLineDetails(v);

		if(flag) {
			msg="Saving Sewing Inception Layout Details Sucessfully";
		}

		return msg; //JSP - /WEB-INF/view/index.jsp
	}

	@RequestMapping(value = "/searchLayoutInfo",method=RequestMethod.GET)
	public @ResponseBody String searchLayoutInfo(String buyerId,String buyerorderId,String styleId,String itemId,String layoutDate) {

		this.BuyerId=buyerId;
		this.BuyerorderId=buyerorderId;
		this.StyleId=styleId;
		this.ItemId=itemId;
		this.LayoutDate=layoutDate;

		return "Success"; //JSP - /WEB-INF/view/index.jsp
	}

	@RequestMapping(value = "/printLayoutInfo/{idList}")
	public @ResponseBody ModelAndView printLayoutInfo(ModelMap map,@PathVariable ("idList") String idList) {

		ModelAndView view=new ModelAndView("production/printLayoutPlanReport");
		String id[] = idList.split("@");
		map.addAttribute("buyerId", id[0]);
		map.addAttribute("buyerorderId", id[1]);
		map.addAttribute("styleId", id[2]);
		map.addAttribute("itemId", id[3]);
		map.addAttribute("layoutDate", id[4]);
		map.addAttribute("layoutType", id[5]);
		map.addAttribute("layoutName", id[6]);

		return view;
	}

	@RequestMapping(value = "/printProductionDetails/{idList}")
	public @ResponseBody ModelAndView printProductionDetails(ModelMap map,@PathVariable ("idList") String idList) {

		ModelAndView view=new ModelAndView("production/printProductionAndRejectReport");
		String id[] = idList.split("@");
		map.addAttribute("buyerId", id[0]);
		map.addAttribute("buyerorderId", id[1]);
		map.addAttribute("styleId", id[2]);
		map.addAttribute("itemId", id[3]);
		map.addAttribute("layoutDate", id[4]);
		map.addAttribute("layoutType", id[5]);
		map.addAttribute("layoutName", id[6]);
		map.addAttribute("layoutCategory", id[7]);

		return view;
	}

	/*	@RequestMapping(value = "/printLayoutInfo",method=RequestMethod.GET)
	public @ResponseBody ModelAndView searchLayoutInfo(ModelMap map) {


		ModelAndView view=new ModelAndView("production/printLayoutPlanReport");


		map.addAttribute("buyerId", BuyerId);
		map.addAttribute("buyerorderId", BuyerorderId);
		map.addAttribute("styleId", StyleId);
		map.addAttribute("itemId", ItemId);
		map.addAttribute("layoutDate", LayoutDate);
		map.addAttribute("layoutType", "1");
		map.addAttribute("layoutName", "Line Inspection");


		return view;
	}*/

	@RequestMapping(value = "/sewing_hourly_layout",method=RequestMethod.GET)
	public ModelAndView sewing_hourly_production(ModelMap map,HttpSession session) {



		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");
		
		List<ProductionPlan> layoutList = productionService.getLayoutPlanDetails("1");
		List<ProductionPlan> sewingProductionList = productionService.getSewingProductionReport("1");
		ModelAndView view = new ModelAndView("production/sewing_hourly_layout");
		view.addObject("layoutList",layoutList);
		view.addObject("sewingProductionList",sewingProductionList);
		
		map.addAttribute("userId",userId);
		map.addAttribute("userName",userName);


		return view; //JSP - /WEB-INF/view/index.jsp
	}



	@RequestMapping(value = "/saveSewingLayoutDetails",method=RequestMethod.POST)
	public @ResponseBody String saveSewingLayoutDetails(ProductionPlan v) {

		System.out.println("v swing");
		String msg="Create Occure while saving information";

		boolean flag= productionService.saveSewingLayoutDetails(v);

		if(flag) {
			msg="Saving information successfully";
		}

		return msg; //JSP - /WEB-INF/view/index.jsp
	}

	@RequestMapping(value = "/searchProductionInfo",method=RequestMethod.GET)
	public @ResponseBody String searchProductionInfo(String buyerId,String buyerorderId,String styleId,String itemId,String lineId,String productionDate) {

		this.BuyerId=buyerId;
		this.BuyerorderId=buyerorderId;
		this.StyleId=styleId;
		this.ItemId=itemId;
		this.LineId=lineId;
		this.ProductionDate=productionDate;

		return "Success"; //JSP - /WEB-INF/view/index.jsp
	}

	@RequestMapping(value = "/printSewingHourlyReport",method=RequestMethod.GET)
	public @ResponseBody ModelAndView printSewingHourlyReport(ModelMap map) {


		ModelAndView view=new ModelAndView("production/printSewingHourlyLayoutReport");


		map.addAttribute("buyerId", BuyerId);
		map.addAttribute("buyerorderId", BuyerorderId);
		map.addAttribute("styleId", StyleId);
		map.addAttribute("itemId", ItemId);
		map.addAttribute("lineId", LineId);
		map.addAttribute("productionDate", ProductionDate);

		return view;
	}


	@RequestMapping(value = "/sewing_production",method=RequestMethod.GET)
	public ModelAndView sewing_production(ModelMap map,HttpSession session) {

		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");
		
		List<ProductionPlan> sewingLayoutList = productionService.getSewingProductionReport("1");
		List<ProductionPlan> sewingProudctiontList = productionService.getSewingProductionReport("2");
		ModelAndView view = new ModelAndView("production/sewing_hourly_production");
		view.addObject("sewingLayoutList",sewingLayoutList);
		view.addObject("sewingProudctiontList",sewingProudctiontList);
		
		map.addAttribute("userId",userId);
		map.addAttribute("userName",userName);

		return view; //JSP - /WEB-INF/view/index.jsp
	}


	@RequestMapping(value = "/searchSewingLayoutLineProduction",method=RequestMethod.GET)
	public @ResponseBody JSONObject searchSewingLayoutLineProduction(ProductionPlan v) {
		JSONObject objmain = new JSONObject();

		JSONArray mainArray = new JSONArray();
		List<ProductionPlan> sewingLayyoutSizeList = productionService.getSewingLayoutLineProduction(v);

		//List<Employee> employeeList = registerService.getEmployeeList();

		objmain.put("result",sewingLayyoutSizeList);
		//objmain.put("employeeresult",employeeList);

		return objmain;
	}

	@RequestMapping(value = "/saveSewingProductionDetails",method=RequestMethod.POST)
	public @ResponseBody String saveSewingProductionDetails(ProductionPlan v) {

		System.out.println("v swing");
		String msg="Create Occure while saving sewing proudction";

		boolean flag= productionService.saveSewingProductionDetails(v);

		if(flag) {
			msg="Saving sewing proudction success";
		}

		return msg; //JSP - /WEB-INF/view/index.jsp
	}

	@RequestMapping(value = "/printSewingHourlyProductionReport",method=RequestMethod.GET)
	public @ResponseBody ModelAndView printSewingHourlyProductionReport(ModelMap map) {


		ModelAndView view=new ModelAndView("production/printSewingHourlyProductionReport");


		map.addAttribute("buyerId", BuyerId);
		map.addAttribute("buyerorderId", BuyerorderId);
		map.addAttribute("styleId", StyleId);
		map.addAttribute("itemId", ItemId);
		map.addAttribute("lineId", LineId);
		map.addAttribute("productionDate", ProductionDate);

		return view;
	}

	//Finishing


	/*@RequestMapping(value = "/saveFinishProductionDetails",method=RequestMethod.POST)
	public @ResponseBody String saveFinishProductionDetails(ProductionPlan v) {

		String msg="Create Occure while saving finishing proudction";

		boolean flag= productionService.saveFinishProductionDetails(v);

		if(flag) {
			msg="Saving finishing proudction success";
		}

		return msg; //JSP - /WEB-INF/view/index.jsp
	}*/


	@RequestMapping(value = "/viewSewingProduction",method=RequestMethod.GET)
	public @ResponseBody JSONObject viewSewingProduction(String buyerId,String buyerorderId,String styleId,String itemId,String productionDate) {
		JSONObject objmain = new JSONObject();

		JSONArray mainArray = new JSONArray();
		List<ProductionPlan> sewingList = productionService.viewSewingProduction(buyerId,buyerorderId,styleId,itemId,productionDate);
		objmain.put("result",sewingList);

		return objmain;
	}

	@RequestMapping(value = "/printFinishingHourlyReport",method=RequestMethod.GET)
	public @ResponseBody ModelAndView printFinishingHourlyReport(ModelMap map) {


		ModelAndView view=new ModelAndView("production/printFinishingHourlyReport");


		map.addAttribute("buyerId", BuyerId);
		map.addAttribute("buyerorderId", BuyerorderId);
		map.addAttribute("styleId", StyleId);
		map.addAttribute("itemId", ItemId);
		map.addAttribute("productionDate", ProductionDate);

		return view;
	}

	@RequestMapping(value = "/sewing_finishing_reject",method=RequestMethod.GET)
	public ModelAndView sewing_finishing_reject(ModelMap map,HttpSession session) {



		//List<ProductionPlan> sewingProductionList = productionService.getSewingProductionReport();

		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");
		
		ModelAndView view = new ModelAndView("production/sewing_finishing_reject");
		//view.addObject("sewingProductionList",sewingProductionList);
		map.addAttribute("userId",userId);
		map.addAttribute("userName",userName);


		return view; //JSP - /WEB-INF/view/index.jsp
	}


	@RequestMapping(value = "/viewSewingFinishingProduction",method=RequestMethod.GET)
	public @ResponseBody JSONObject viewSewingFinishingProduction(String buyerId,String buyerorderId,String styleId,String itemId,String productionDate) {
		JSONObject objmain = new JSONObject();

		JSONArray mainArray = new JSONArray();
		List<ProductionPlan> sewingList = productionService.viewSewingFinishingProduction(buyerId,buyerorderId,styleId,itemId,productionDate);
		objmain.put("result",sewingList);

		return objmain;
	}


	@RequestMapping(value = "/searchSewingLineSetup",method=RequestMethod.GET)
	public @ResponseBody JSONObject searchSewingLineSetup(ProductionPlan v) {
		JSONObject objmain = new JSONObject();

		JSONArray mainArray = new JSONArray();
		List<ProductionPlan> sewingList = productionService.getSewingLineSetupinfo(v);

		List<Employee> employeeList = registerService.getEmployeeList();

		//List<ProductionPlan> sizelist = productionService.getSizeListForProduction(v);

		objmain.put("result",sewingList);
		objmain.put("employeeresult",employeeList);
		//objmain.put("sizelist",sizelist);

		return objmain;
	}
	
	@RequestMapping(value = "/searchSewingPassProduction",method=RequestMethod.GET)
	public @ResponseBody JSONObject searchSewingPassProduction(ProductionPlan v) {
		JSONObject objmain = new JSONObject();

		JSONArray mainArray = new JSONArray();
		List<ProductionPlan> sewingList = productionService.getSewingPassProduction(v);

		List<Employee> employeeList = registerService.getEmployeeList();

		//List<ProductionPlan> sizelist = productionService.getSizeListForProduction(v);

		objmain.put("result",sewingList);
		objmain.put("employeeresult",employeeList);
		//objmain.put("sizelist",sizelist);

		return objmain;
	}
	
	@RequestMapping(value = "/searchFinishingPassData",method=RequestMethod.GET)
	public @ResponseBody JSONObject searchFinishingPassData(ProductionPlan v) {
		JSONObject objmain = new JSONObject();

		JSONArray mainArray = new JSONArray();
		List<ProductionPlan> sewingList = productionService.getFinishingPassData(v);

		List<Employee> employeeList = registerService.getEmployeeList();

		//List<ProductionPlan> sizelist = productionService.getSizeListForProduction(v);

		objmain.put("result",sewingList);
		objmain.put("employeeresult",employeeList);
		//objmain.put("sizelist",sizelist);

		return objmain;
	}


	@RequestMapping(value = "/lineWiseMachineList",method=RequestMethod.GET)
	public @ResponseBody JSONObject lineWiseMachineList(ProductionPlan v) {
		JSONObject objmain = new JSONObject();

		JSONArray mainArray = new JSONArray();
		List<ProductionPlan> machineList = productionService.getLineWiseMachineList(v);

		List<ProductionPlan> sizelist = productionService.getSizeListForProduction(v);
		List<ProcessInfo> processList=registerService.getProcessList();
		objmain.put("result",machineList);
		objmain.put("sizelistresult",sizelist);
		objmain.put("processList",processList);

		return objmain;
	}
	
	@RequestMapping(value = "/lineWiseMachineListByLineId",method=RequestMethod.GET)
	public @ResponseBody JSONObject lineWiseMachineListByLineId(String lineId) {
		JSONObject objmain = new JSONObject();

		JSONArray mainArray = new JSONArray();
		List<Machine> machineList = productionService.getLineWiseMachineListByLineId(lineId);	
		List<ProcessInfo> processList=registerService.getProcessList();
		objmain.put("result",machineList);
		
		objmain.put("processList",processList);

		return objmain;
	}


	@RequestMapping(value = "/printSewingFinishingHourlyReport",method=RequestMethod.GET)
	public @ResponseBody ModelAndView printSewingFinishingHourlyReport(ModelMap map) {


		ModelAndView view=new ModelAndView("production/printSewingFinishingHourlyReport");


		map.addAttribute("buyerId", BuyerId);
		map.addAttribute("buyerorderId", BuyerorderId);
		map.addAttribute("styleId", StyleId);
		map.addAttribute("itemId", ItemId);
		map.addAttribute("productionDate", ProductionDate);

		return view;
	}


	//Finishing 
	@RequestMapping(value = "/finishing_layout",method=RequestMethod.GET)
	public ModelAndView finshing_layout(ModelMap map,HttpSession session) {

		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");
		List<ProductionPlan> productionPlanList = productionService.getProductionPlanForCutting();
		List<ProductionPlan> layoutList = productionService.getLayoutPlanDetails(String.valueOf(ProductionType.FINISHING_LAYOUT.getType()));
		ModelAndView view = new ModelAndView("production/finishing-layout");
		view.addObject("productionPlanList",productionPlanList);
		view.addObject("layoutList",layoutList);
		
		map.addAttribute("userId",userId);
		map.addAttribute("userName",userName);
		return view; //JSP - /WEB-INF/view/index.jsp
	}

	//Iron 
	@RequestMapping(value = "/iron_layout",method=RequestMethod.GET)
	public ModelAndView iron_layout(ModelMap map,HttpSession session) {

		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");
		
		List<ProductionPlan> productionPlanList = productionService.getProductionPlanForCutting();
		List<ProductionPlan> layoutList = productionService.getLayoutPlanDetails(String.valueOf(ProductionType.IRON_LAYOUT.getType()));
		ModelAndView view = new ModelAndView("production/iron-layout");
		view.addObject("productionPlanList",productionPlanList);
		view.addObject("layoutList",layoutList);
		
		map.addAttribute("userId",userId);
		map.addAttribute("userName",userName);
		return view; //JSP - /WEB-INF/view/index.jsp
	}

	//Final QC 
	@RequestMapping(value = "/final_qc_layout",method=RequestMethod.GET)
	public ModelAndView final_qc_layout(ModelMap map,HttpSession session) {

		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");
		
		List<ProductionPlan> productionPlanList = productionService.getProductionPlanForCutting();
		List<ProductionPlan> layoutList = productionService.getLayoutPlanDetails(String.valueOf(ProductionType.FINAL_QC_LAYOUT.getType()));
		ModelAndView view = new ModelAndView("production/final-qc-layout");
		view.addObject("productionPlanList",productionPlanList);
		view.addObject("layoutList",layoutList);
		
		map.addAttribute("userId",userId);
		map.addAttribute("userName",userName);
		return view; //JSP - /WEB-INF/view/index.jsp
	}

	//Line Inspection Production
	@RequestMapping(value = "/line_inspection_production",method=RequestMethod.GET)
	public ModelAndView line_inspection_production(ModelMap map,HttpSession session) {
		//List<ProductionPlan> productionPlanList = productionService.getProductionPlanForCutting();
		
		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");
		
		List<ProductionPlan> layoutList = productionService.getLayoutPlanDetails(String.valueOf(ProductionType.LINE_PRODUCTION.getType()));
		List<ProductionPlan> productionPlanList = productionService.getLayoutPlanDetails(String.valueOf(ProductionType.LINE_INSPECTION_LAYOUT.getType()));
		List<ProcessInfo> processlist = registerService.getProcessList();
		ModelAndView view = new ModelAndView("production/line-inspection-production");
		view.addObject("productionPlanList",productionPlanList);
		view.addObject("layoutList",layoutList);
		view.addObject("processlist",processlist);
		
		map.addAttribute("userId",userId);
		map.addAttribute("userName",userName);
		return view; //JSP - /WEB-INF/view/index.jsp
	}

	//Finishing Production
	@RequestMapping(value = "/finishing_production",method=RequestMethod.GET)
	public ModelAndView finishing_production(ModelMap map,HttpSession session) {

		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");
		
		List<ProductionPlan> layoutList = productionService.getLayoutPlanDetails(String.valueOf(ProductionType.LINE_PASS.getType()));
		List<ProductionPlan> productionList = productionService.getLayoutPlanDetails(String.valueOf(ProductionType.FINISHING_PASS.getType()));
		List<ProcessInfo> processlist = registerService.getProcessList();
		ModelAndView view = new ModelAndView("production/finishing-production");
		view.addObject("layoutList",layoutList);
		view.addObject("productionList",productionList);
		view.addObject("processlist",processlist);
		
		map.addAttribute("userId",userId);
		map.addAttribute("userName",userName);
		return view; //JSP - /WEB-INF/view/index.jsp
	}


	//Iron Production
	@RequestMapping(value = "/iron_production",method=RequestMethod.GET)
	public ModelAndView iron_production(ModelMap map,HttpSession session) {

		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");
		
		List<ProductionPlan> layoutList = productionService.getLayoutPlanDetails(String.valueOf(ProductionType.FINISHING_PASS.getType()));
		List<ProductionPlan> productionList = productionService.getLayoutPlanDetails(String.valueOf(ProductionType.IRON_PASS.getType()));
		List<ProcessInfo> processlist = registerService.getProcessList();
		ModelAndView view = new ModelAndView("production/iron-production");
		view.addObject("productionList",productionList);
		view.addObject("layoutList",layoutList);
		view.addObject("processlist",processlist);
		
		map.addAttribute("userId",userId);
		map.addAttribute("userName",userName);
		return view; //JSP - /WEB-INF/view/index.jsp
	}


	//Final QC Production
	@RequestMapping(value = "/final_qc_production",method=RequestMethod.GET)
	public ModelAndView final_qc_production(ModelMap map,HttpSession session) {

		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");
		
		List<ProductionPlan> productionPlanList = productionService.getProductionPlanForCutting();
		List<ProductionPlan> layoutList = productionService.getLayoutPlanDetails(String.valueOf(ProductionType.FINAL_QC_PRODUCTION.getType()));
		List<ProcessInfo> processlist = registerService.getProcessList();
		ModelAndView view = new ModelAndView("production/final-qc-production");
		view.addObject("productionPlanList",productionPlanList);
		view.addObject("layoutList",layoutList);
		view.addObject("processlist",processlist);
		
		map.addAttribute("userId",userId);
		map.addAttribute("userName",userName);
		return view; //JSP - /WEB-INF/view/index.jsp
	}

	//Line Inspection Reject
	@RequestMapping(value = "/line_inspection_reject",method=RequestMethod.GET)
	public ModelAndView line_inspection_reject(ModelMap map,HttpSession session) {

		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");
		
		List<ProductionPlan> layoutList = productionService.getLayoutPlanDetails(String.valueOf(ProductionType.LINE_INSPECTION_LAYOUT.getType()));
		List<ProductionPlan> rejectList = productionService.getLayoutPlanDetails(String.valueOf(ProductionType.LINE_REJECT.getType()));

		ModelAndView view = new ModelAndView("production/line_inspection_reject");
		view.addObject("rejectList",rejectList);
		view.addObject("layoutList",layoutList);
		
		map.addAttribute("userId",userId);
		map.addAttribute("userName",userName);

		return view; //JSP - /WEB-INF/view/index.jsp
	}

	@RequestMapping(value = "/saveLineInceptionRejectDetails",method=RequestMethod.POST)
	public @ResponseBody String saveLineInceptionRejectDetails(ProductionPlan v) {

		String msg="Create Occure while Saving Inception Reject Details";

		boolean flag= productionService.saveInceptionLayoutDetails(v);

		if(flag) {
			msg="Saving Sewing Inception Reject Details Sucessfully";
		}

		return msg; //JSP - /WEB-INF/view/index.jsp
	}

	@RequestMapping(value = "/searchLayoutData",method=RequestMethod.GET)
	public @ResponseBody JSONObject searchLayoutData(ProductionPlan productionPlan) {
		System.out.println("Controller Execute");
		JSONObject objmain = new JSONObject();
		List<Employee> employeeList = registerService.getEmployeeList();
		List<ProductionPlan> productionPlanList = productionService.getLayoutData(productionPlan);
		objmain.put("result",productionPlanList);
		objmain.put("employeeList",employeeList);
		return objmain;
	}
	
	@RequestMapping(value = "/searchLayoutLineData",method=RequestMethod.GET)
	public @ResponseBody JSONObject searchLayoutLineData(ProductionPlan productionPlan) {
		System.out.println("Controller Execute");
		JSONObject objmain = new JSONObject();
		List<ProcessInfo> processList = registerService.getProcessList();
		List<ProductionPlan> productionPlanList = productionService.getLayoutLineData(productionPlan);
		objmain.put("result",productionPlanList);
		objmain.put("processList",processList);
		return objmain;
	}

	@RequestMapping(value = "/searchProductionData",method=RequestMethod.GET)
	public @ResponseBody JSONObject searchProductionData(ProductionPlan productionPlan) {
		System.out.println("Controller Execute");
		JSONObject objmain = new JSONObject();
		List<Employee> employeeList = registerService.getEmployeeList();
		List<ProductionPlan> productionPlanList = productionService.getProductionData(productionPlan);
		List<Process> processValues = productionService.getProcessValues(productionPlan);
		objmain.put("result",productionPlanList);
		objmain.put("employeeList",employeeList);
		objmain.put("processValues", processValues);
		return objmain;
	}
	
	@RequestMapping(value = "/searchFinishingData",method=RequestMethod.GET)
	public @ResponseBody JSONObject searchFinishingData(ProductionPlan productionPlan) {
		System.out.println("Controller Execute");
		JSONObject objmain = new JSONObject();
		List<Employee> employeeList = registerService.getEmployeeList();
		List<ProductionPlan> productionPlanList = productionService.getFinishingData(productionPlan);
		objmain.put("result",productionPlanList);
		objmain.put("employeeList",employeeList);
		return objmain;
	}
	
	@RequestMapping(value = "/searchIronData",method=RequestMethod.GET)
	public @ResponseBody JSONObject searchIronData(ProductionPlan productionPlan) {
		System.out.println("Controller Execute");
		JSONObject objmain = new JSONObject();
		List<Employee> employeeList = registerService.getEmployeeList();
		List<ProductionPlan> productionPlanList = productionService.getIronData(productionPlan);
		objmain.put("result",productionPlanList);
		objmain.put("employeeList",employeeList);
		return objmain;
	}


	@RequestMapping(value = "/editLayoutLineData",method=RequestMethod.POST)
	public @ResponseBody JSONObject editLayoutLineData(ProductionPlan productionPlan) {
		System.out.println("Its Execute controller");
		JSONObject objMain = new JSONObject();
		objMain.put("result",productionService.editLayoutLineData(productionPlan));
		return objMain;
	}

	//Poly Production
	@RequestMapping(value = "/poly_production",method=RequestMethod.GET)
	public ModelAndView poly_production(ModelMap map,HttpSession session) {

		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");
		List<ProductionPlan> layoutList = productionService.getLayoutPlanDetails(String.valueOf(ProductionType.FINAL_QC_PRODUCTION.getType()));
		List<ProductionPlan> polyList = productionService.getPolyPackingDetails("1");

		ModelAndView view = new ModelAndView("production/poly_production");
		view.addObject("polyList",polyList);
		view.addObject("layoutList",layoutList);

		map.addAttribute("userId",userId);
		map.addAttribute("userName",userName);
		
		return view; //JSP - /WEB-INF/view/index.jsp
	}

	@RequestMapping(value = "/savePolyPackingDetails",method=RequestMethod.POST)
	public @ResponseBody String savePolyPackingDetails(ProductionPlan v) {

		System.out.println("v swing");
		String msg="Create Occure while saving information";

		boolean flag= productionService.savePolyPackingDetails(v);

		if(flag) {
			msg="Saving information successfully";
		}

		return msg; //JSP - /WEB-INF/view/index.jsp
	}


	@RequestMapping(value = "/printPolyDetails/{idList}")
	public @ResponseBody ModelAndView printPolyDetails(ModelMap map,@PathVariable ("idList") String idList) {

		ModelAndView view=new ModelAndView("production/printPolyDetailsReport");
		String id[] = idList.split("@");
		map.addAttribute("buyerId", id[0]);
		map.addAttribute("buyerorderId", id[1]);
		map.addAttribute("styleId", id[2]);
		map.addAttribute("itemId", id[3]);

		System.out.println("layoutDate "+id[4]);
		map.addAttribute("layoutDate", id[4]);
		map.addAttribute("layoutType", id[5]);
		map.addAttribute("layoutName", id[6]);
		map.addAttribute("layoutCategory", id[7]);

		return view;
	}



	//Send Cutting Body
	@RequestMapping(value = "/send_cutting_body",method=RequestMethod.GET)
	public ModelAndView send_cutting_body(ModelMap map,HttpSession session) {
		
		
		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");
		
		ModelAndView view = new ModelAndView("production/send_cutting_body");
		List<CuttingInformation> cuttingInformationList = productionService.getCuttingInformationList();
		List<CuttingInformation> sendCuttingBodyInfoList = productionService.getSendCuttingBodyInfoList();
		
		view.addObject("cuttingInformationList",cuttingInformationList);
		view.addObject("sendCuttingBodyInfoList",sendCuttingBodyInfoList);
		
		map.addAttribute("userId",userId);
		map.addAttribute("userName",userName);
		return view; 
	}

	@RequestMapping(value = "/searchCuttingPlanQuantity",method=RequestMethod.GET)
	public @ResponseBody JSONObject searchCuttingPlanQuantity(String cuttingEntryId,String sizeGroupId) {

		JSONObject objmain = new JSONObject();
		List<ProductionPlan> planQuantityList = productionService.searchCuttingPlanQuantity(cuttingEntryId,sizeGroupId);

		objmain.put("cuttingPlanQuantityList",planQuantityList);
		return objmain;
	}

	@RequestMapping(value = "/getSendCuttingBodyList",method=RequestMethod.GET)
	public @ResponseBody JSONObject getSendCuttingPlanQuantity(String cuttingEntryId,String sizeGroupId) {

		JSONObject objmain = new JSONObject();
		List<ProductionPlan> planQuantityList = productionService.getSendCuttingBodyList(cuttingEntryId,sizeGroupId);

		objmain.put("cuttingPlanQuantityList",planQuantityList);
		return objmain;
	}

	@RequestMapping(value = "/sendCuttingPlanBodyQuantity",method=RequestMethod.POST)
	public @ResponseBody JSONObject sendCuttingPlanBodyQuantity(String sendItemList,String userId) {
		JSONObject objmain = new JSONObject();

		System.out.println(sendItemList);
		objmain.put("result",productionService.sendCuttingPlanBodyQuantity(sendItemList,userId));
		return objmain;
	}

	@RequestMapping(value = "/printSendCuttingBody/{cuttingEntryId}")
	public @ResponseBody ModelAndView printSendCuttingBody(ModelMap map,@PathVariable ("cuttingEntryId") String cuttingEntryId) {

		ModelAndView view=new ModelAndView("production/printSendCuttingBody");
		map.addAttribute("cuttingEntryId", cuttingEntryId);


		return view;
	}


	//Receive Cutting Body
	
	@RequestMapping(value = "/receive_cutting_body",method=RequestMethod.GET)
	public ModelAndView receive_cutting_body(ModelMap map,HttpSession session) {

		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");
		
		ModelAndView view = new ModelAndView("production/receive_cutting_body");
		
		List<CuttingInformation> sendCuttingBodyInfoList = productionService.getSendCuttingBodyInfoList();
		List<CuttingInformation> receiveCuttingBodyInfoList = productionService.getReceiveCuttingBodyInfoList();
		
		view.addObject("sendCuttingBodyInfoList",sendCuttingBodyInfoList);
		view.addObject("receiveCuttingBodyInfoList",receiveCuttingBodyInfoList);
		
		map.addAttribute("userId",userId);
		map.addAttribute("userName",userName);
		return view; 
	}
	
	
	@RequestMapping(value = "/searchSendCuttingBodyQuantity",method=RequestMethod.GET)
	public @ResponseBody JSONObject searchSendCuttingBodyQuantity(String cuttingEntryId,String sizeGroupId) {

		JSONObject objmain = new JSONObject();
		List<ProductionPlan> planQuantityList = productionService.searchSendCuttingBodyQuantity(cuttingEntryId,sizeGroupId);

		objmain.put("cuttingPlanQuantityList",planQuantityList);
		return objmain;
	}
	
	@RequestMapping(value = "/receiveCuttingPlanBodyQuantity",method=RequestMethod.POST)
	public @ResponseBody JSONObject receiveCuttingPlanBodyQuantity(String receiveItemList,String userId) {
		JSONObject objmain = new JSONObject();
		objmain.put("result",productionService.receiveCuttingPlanBodyQuantity(receiveItemList,userId));
		return objmain;
	}

	
	@RequestMapping(value = "/printReceiveCuttingBody/{cuttingEntryId}")
	public @ResponseBody ModelAndView printReceiveCuttingBody(ModelMap map,@PathVariable ("cuttingEntryId") String cuttingEntryId) {

		ModelAndView view=new ModelAndView("production/printReceiveCuttingBody");
		map.addAttribute("cuttingEntryId", cuttingEntryId);


		return view;
	}
}

