package pg.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URLConnection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.IOUtils;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.sun.istack.internal.logging.Logger;

import pg.Commercial.deedOfContacts;
import pg.model.commonModel;
import pg.model.login;
import pg.orderModel.BuyerPO;
import pg.orderModel.BuyerPoItem;
import pg.orderModel.Costing;
import pg.orderModel.FabricsIndent;
import pg.orderModel.PurchaseOrder;
import pg.orderModel.PurchaseOrderItem;
import pg.orderModel.SampleRequisitionItem;
import pg.orderModel.Style;

import pg.orderModel.accessoriesindentcarton;
import pg.registerModel.AccessoriesItem;
import pg.registerModel.Brand;
import pg.registerModel.BuyerModel;
import pg.registerModel.Color;
import pg.registerModel.FabricsItem;
import pg.registerModel.Factory;
import pg.registerModel.FactoryModel;
import pg.registerModel.ItemDescription;
import pg.registerModel.MerchandiserInfo;
import pg.registerModel.ParticularItem;
import pg.registerModel.Size;
import pg.registerModel.SizeGroup;
import pg.registerModel.SupplierModel;
import pg.registerModel.Unit;
import pg.services.CommercialService;
import pg.services.OrderService;
import pg.services.RegisterService;
import pg.share.Currency;


@Controller
@RestController
public class CommercialController {


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
	CommercialService commservice;

	//Style Create 
	
	String styleNo="",date="";
	
	String FrontImg="",BackImg;
	
	String StyleId="",ItemId="";


	@RequestMapping(value = "deed_of_contact")
	public ModelAndView deed_of_contact(ModelMap map,HttpSession session) {

		ModelAndView view = new ModelAndView("commercial/deedOfContact");

		
		List<String> poList = orderService.getPurchaseOrderList();
		List<Unit> unitList = registerService.getUnitList();
		List<BuyerModel> buyerList= registerService.getAllBuyers();
		List<deedOfContacts>ContractsList=commservice.deedOfContractsList();
		
		view.addObject("Lists",ContractsList);
		view.addObject("unitList",unitList);
		
		view.addObject("buyerList", buyerList);
		
		

		return view; //JSP - /WEB-INF/view/index.jsp
	}

	
	
	@RequestMapping(value = "/insert", method=RequestMethod.POST)
	public String insert(deedOfContacts deedcontact) {
		boolean insert=commservice.insertDeedOfContact(deedcontact);
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
	public ModelAndView department_medicine_delvierOpen(ModelAndView map, FabricsIndent p) {
		
			System.out.println(" deed of contacts report ");	
			ModelAndView view = new ModelAndView("commercial/deedOfContactReport");
	
			view.addObject("contractId",contractId);
		 	
		 	
			
			
			return view;
		
	}
	
	
	@RequestMapping(value = "/deedOfContratDetails/{id}", method=RequestMethod.POST)
	public List<deedOfContacts> deedOfContratDetails(@PathVariable ("id") String id) {

		List<deedOfContacts> details=commservice.deedOfContractDetails(id);
		
		System.out.println(" ud receive date "+details.get(0).getuNReceivedDate());
		

		return details; //JSP - /WEB-INF/view/index.jsp
	}
	



}
