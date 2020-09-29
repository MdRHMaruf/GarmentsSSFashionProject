

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
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.sun.istack.internal.logging.Logger;

import javassist.tools.reflect.Sample;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import pg.model.commonModel;
import pg.model.login;
import pg.orderModel.BuyerPO;
import pg.orderModel.BuyerPoItem;
import pg.orderModel.Costing;
import pg.orderModel.FabricsIndent;
import pg.orderModel.PurchaseOrder;
import pg.orderModel.PurchaseOrderItem;
import pg.orderModel.SampleCadAndProduction;
import pg.orderModel.SampleRequisitionItem;
import pg.orderModel.Style;
import pg.orderModel.AccessoriesIndent;
import pg.orderModel.accessoriesindentcarton;
import pg.orderModel.parcelModel;
import pg.registerModel.AccessoriesItem;
import pg.registerModel.Brand;
import pg.registerModel.BuyerModel;
import pg.registerModel.Color;
import pg.registerModel.CourierModel;
import pg.registerModel.Department;
import pg.registerModel.FabricsItem;
import pg.registerModel.Factory;
import pg.registerModel.FactoryModel;
import pg.registerModel.ItemDescription;
import pg.registerModel.Line;
import pg.registerModel.MerchandiserInfo;
import pg.registerModel.ParticularItem;
import pg.registerModel.Size;
import pg.registerModel.SizeGroup;
import pg.registerModel.SupplierModel;
import pg.registerModel.Unit;
import pg.services.OrderService;
import pg.services.RegisterService;

@Controller
@RestController
public class OrderController {

	private static final String UPLOAD_FILE_SAVE_FOLDER = "E:/uploadspringfiles/";

	private static final String UPLOAD_DIRECTORY ="/WEB-INF/upload";  

	private static final String DIRECTORY="E:/uploadspringfiles/";


	DecimalFormat df = new DecimalFormat("#.00");

	@Autowired
	private OrderService orderService;
	@Autowired
	private RegisterService registerService;
	
	 String poid;
	 String styleid;
	 String itemid;
	 String ParcelId;
	 String sampleId;
	 

	//Style Create 

	String styleNo="",date="";

	String FrontImg="",BackImg;

	String StyleId="",ItemId="",AiNo="",BuyerPoId="",SampleReqId="";


	private String getImageName(CommonsMultipartFile frontImage, HttpSession session) throws IOException  {
		ServletContext context = session.getServletContext();  
		String path = context.getRealPath(UPLOAD_DIRECTORY);  

		String filename = frontImage.getOriginalFilename();  

		byte[] bytes = frontImage.getBytes();  
		BufferedOutputStream stream =new BufferedOutputStream(new FileOutputStream(  
				new File(path + File.separator + filename)));  
		stream.write(bytes);  
		stream.flush();  
		stream.close();  

		String frontfile=session.getServletContext().getRealPath("WEB-INF/upload/"+filename);

		return frontfile;
	}


	@RequestMapping(value = "/getStyleWiseItem/{value}",method=RequestMethod.GET)
	public @ResponseBody JSONObject getStyleAndItem(@PathVariable ("value") String value) {
		JSONObject objmain = new JSONObject();
		JSONArray mainarray = new JSONArray();

		List<Style> lablist=orderService.getStyleAndItem(value);
		for(int a=0;a<lablist.size();a++) {
			JSONObject obj = new JSONObject();
			obj.put("id", lablist.get(a).getItemId());
			obj.put("value", lablist.get(a).getItemName());

			mainarray.add(obj);
		}
		objmain.put("result", mainarray);
		System.out.println(objmain.toString());
		return objmain;
	}
	
	
	
	//Costing Create Work of nasir bai...
	@RequestMapping(value = "/costing_create",method=RequestMethod.GET)
	public ModelAndView costing_create(ModelMap map,HttpSession session) {

		ModelAndView view = new ModelAndView("order/costing_create");
		List<Unit> unitList= registerService.getUnitList();	
		List<Style> styleList= orderService.getStyleList();
		List<ParticularItem> particularList = orderService.getTypeWiseParticularList("1");
		List<Costing> costingList = orderService.getCostingList();
		map.addAttribute("styleList",styleList);
		map.addAttribute("unitList",unitList);
		map.addAttribute("particularList",particularList);
		map.addAttribute("costingList",costingList);

		return view; //JSP - /WEB-INF/view/index.jsp
	}

	@RequestMapping(value = "/saveCosting",method=RequestMethod.POST)
	public @ResponseBody JSONObject saveCosting(Costing costing) {
		JSONObject objmain = new JSONObject();
		if(orderService.saveCosting(costing)) {

			List<Costing> costingList = orderService.getCostingList(costing.getStyleId(),costing.getItemId());

			objmain.put("result",costingList);
		}else {
			objmain.put("result", "Something Wrong");
		}

		return objmain;
	}

	@RequestMapping(value = "/editCosting",method=RequestMethod.POST)
	public @ResponseBody JSONObject editCosting(Costing costing) {
		JSONObject objmain = new JSONObject();
		if(orderService.editCosting(costing)) {

			List<Costing> costingList = orderService.getCostingList(costing.getStyleId(),costing.getItemId());

			objmain.put("result",costingList);
		}else {
			objmain.put("result", "Something Wrong");
		}

		return objmain;
	}

	@RequestMapping(value = "/deleteCosting",method=RequestMethod.GET)
	public @ResponseBody JSONObject deleteCosting(String autoId,String styleId,String itemId) {
		JSONObject objmain = new JSONObject();
		if(orderService.deleteCosting(autoId)) {

			List<Costing> costingList = orderService.getCostingList(styleId,itemId);

			objmain.put("result",costingList);
		}else {
			objmain.put("result", "Something Wrong");
		}

		return objmain;
	}

	@RequestMapping(value = "/searchCosting",method=RequestMethod.GET)
	public @ResponseBody JSONObject searchCosting(String styleId,String itemId) {
		JSONObject objmain = new JSONObject();

		JSONArray mainArray = new JSONArray();
		List<Costing> costingList = orderService.getCostingList(styleId,itemId);
		objmain.put("result",costingList);
		return objmain;
	}

	@RequestMapping(value = "/searchCostingItem",method=RequestMethod.GET)
	public @ResponseBody JSONObject searchCostingItem(String autoId) {
		JSONObject objmain = new JSONObject();

		JSONArray mainArray = new JSONArray();
		Costing costing = orderService.getCostingItem(autoId);
		objmain.put("result",costing);
		return objmain;
	}

	@RequestMapping(value = "/costingReportInfo",method=RequestMethod.GET)
	public @ResponseBody String costingReportInfo(String styleId,String itemId) {
		this.StyleId=styleId;
		this.ItemId=itemId;
		return "Success";
	}

	@RequestMapping(value = "/printCostingReport",method=RequestMethod.GET)
	public @ResponseBody ModelAndView printCostingReport(ModelMap map) {


		ModelAndView view=new ModelAndView("order/printCostingReport");


		map.addAttribute("StyleId", StyleId);
		map.addAttribute("ItemId", ItemId);

		return view;
	}

	@RequestMapping(value = "/typeWiseParticularLoad",method=RequestMethod.GET)
	public @ResponseBody JSONObject typeWiseParticularLoad(String type) {
		JSONObject objmain = new JSONObject();
		List<ParticularItem> particularList= orderService.getTypeWiseParticularList(type);

		objmain.put("particularList", particularList);

		return objmain;
	}

	@RequestMapping(value = "/itemDescriptionLoad",method=RequestMethod.GET)
	public @ResponseBody JSONObject itemDescriptionLoad() {
		JSONObject objmain = new JSONObject();
		JSONArray mainarray = new JSONArray();

		List<ItemDescription> List= orderService.getItemDescriptionList();

		for(int a=0;a<List.size();a++) {
			JSONObject obj = new JSONObject();
			obj.put("id", List.get(a).getItemId());
			obj.put("value", List.get(a).getItemName());
			mainarray.add(obj);
		}

		objmain.put("result", mainarray);
		System.out.println(objmain.toString());
		return objmain;
	}

	@RequestMapping(value = "/buyerLoad",method=RequestMethod.GET)
	public @ResponseBody JSONObject buyerLoad() {
		JSONObject objmain = new JSONObject();


		JSONArray mainarray = new JSONArray();

		/*		List<Buyer> List= orderService.getBuyerList();

		for(int a=0;a<List.size();a++) {
			JSONObject obj = new JSONObject();
			obj.put("id", List.get(a).getBuyerId());
			obj.put("value", List.get(a).getBuyerName());
			mainarray.add(obj);
		}*/

		objmain.put("result", mainarray);
		System.out.println(objmain.toString());
		return objmain;
	}


	//Buyer Purchase Order Create
	@RequestMapping(value = "/buyer_purchase_order",method=RequestMethod.GET)
	public ModelAndView buyer_purchase_order(ModelMap map,HttpSession session) {

		ModelAndView view = new ModelAndView("order/buyer-purchase-order");
		List<SizeGroup> groupList = registerService.getStyleSizeGroupList();
		List<BuyerModel> buyerList= registerService.getAllBuyers();
		List<FactoryModel> factoryList = registerService.getAllFactories();
		List<Color> colorList = registerService.getColorList();
		List<BuyerPO> buyerPoList = orderService.getBuyerPoList();
		view.addObject("groupList",groupList);
		view.addObject("buyerList",buyerList);
		view.addObject("factoryList",factoryList);
		view.addObject("colorList",colorList);
		view.addObject("buyerPoList",buyerPoList);
		return view; //JSP - /WEB-INF/view/index.jsp
	}

	@ResponseBody
	@RequestMapping(value = "/buyerIndentInfo",method=RequestMethod.GET)
	public String buyerIndentInfo(String buyerPoId) {
		this.BuyerPoId=buyerPoId;
		return "Success";
	}


	@RequestMapping(value = "/printBuyerPoOrder",method=RequestMethod.GET)
	public @ResponseBody ModelAndView printBuyerPoOrder(ModelMap map) {

		ModelAndView view=new ModelAndView("order/printBuyerPoOrder");
		map.addAttribute("BuyerPoId", BuyerPoId);
		return view;
	}


	@ResponseBody
	@RequestMapping(value = "/sizesLoadByGroup",method=RequestMethod.GET)
	public JSONObject sizesLoadByGroup() {

		JSONObject objmain = new JSONObject();	
		JSONObject size = new JSONObject() ;
		JSONArray sizeArray = new JSONArray();
		JSONObject groupObj = new JSONObject();

		List<Size> sizeList = registerService.getStyleSizeList();
		int lastSize= sizeList.size();

		for(int i=0;i<lastSize;i++) {

			size.put("sizeId", sizeList.get(i).getSizeId());
			size.put("sizeName", sizeList.get(i).getSizeName());
			sizeArray.add(size);
			if((i+1 == lastSize) || !sizeList.get(i).getGroupId().equals( sizeList.get(i+1).getGroupId())) {

				groupObj.put("groupId"+sizeList.get(i).getGroupId(), sizeArray);
				sizeArray = new JSONArray();
			}
			size = new JSONObject();

		}
		objmain.put("sizeList", groupObj);
		return objmain;

	}

	@ResponseBody
	@RequestMapping(value = "/getBuyerWiseStylesItem",method=RequestMethod.GET)
	public JSONObject getBuyerWiseStylesItem(String buyerId) {
		JSONObject objMain = new JSONObject();	
		List<Style> styleList = orderService.getBuyerWiseStylesItem(buyerId);

		objMain.put("styleList",styleList);
		return objMain; 
	}

	@ResponseBody
	@RequestMapping(value = "/getStyleWiseItem",method=RequestMethod.GET)
	public JSONObject getStyleWiseItem(String styleId) {
		JSONObject objMain = new JSONObject();	
		List<ItemDescription> itemList = orderService.getStyleWiseItem(styleId);

		objMain.put("itemList",itemList);
		return objMain; 
	}



	@RequestMapping(value = "/addItemToBuyerPO",method=RequestMethod.POST)
	public @ResponseBody JSONObject addItemToBuyerPO(BuyerPoItem buyerPoItem) {
		JSONObject objmain = new JSONObject();

		if(orderService.addBuyerPoItem(buyerPoItem)) {
			JSONArray mainArray = new JSONArray();
			List<BuyerPoItem> buyerPOItemList = orderService.getBuyerPOItemList(buyerPoItem.getBuyerPOId());
			objmain.put("result",buyerPOItemList);
		}else {
			objmain.put("result", "Something Wrong");
		}

		return objmain;
	}



	@RequestMapping(value = "/editBuyerPoItem",method=RequestMethod.POST)
	public @ResponseBody JSONObject editBuyerPoItem(BuyerPoItem buyerPoItem) {
		JSONObject objmain = new JSONObject();

		if(orderService.editBuyerPoItem(buyerPoItem)) {
			JSONArray mainArray = new JSONArray();
			List<BuyerPoItem> buyerPOItemList = orderService.getBuyerPOItemList(buyerPoItem.getBuyerPOId());
			objmain.put("result",buyerPOItemList);
		}else {
			objmain.put("result", "Something Wrong");
		}

		return objmain;
	}

	@RequestMapping(value = "/getBuyerPOItemsList",method=RequestMethod.GET)
	public @ResponseBody JSONObject getBuyerPOItemsList(String buyerPoNo) {
		JSONObject objmain = new JSONObject();


		JSONArray mainArray = new JSONArray();
		List<BuyerPoItem> buyerPOItemList = orderService.getBuyerPOItemList(buyerPoNo);
		objmain.put("result",buyerPOItemList);

		return objmain;
	}

	@RequestMapping(value = "/getBuyerPOItem",method=RequestMethod.GET)
	public @ResponseBody JSONObject getBuyerPOItem(String itemAutoId) {
		JSONObject objmain = new JSONObject();


		JSONArray mainArray = new JSONArray();
		BuyerPoItem buyerPoItem = orderService.getBuyerPOItem(itemAutoId);
		objmain.put("poItem",buyerPoItem);

		return objmain;
	}

	@RequestMapping(value = "/deleteBuyerPoItem",method=RequestMethod.GET)
	public @ResponseBody JSONObject deleteBuyerPoItem(String buyerPoNo,String itemAutoId) {
		JSONObject objmain = new JSONObject();

		if(orderService.deleteBuyerPoItem(itemAutoId)) {
			JSONArray mainArray = new JSONArray();
			List<BuyerPoItem> buyerPOItemList = orderService.getBuyerPOItemList(buyerPoNo);
			objmain.put("result",buyerPOItemList);
		}else {
			objmain.put("result","Something Wrong");
		}
		return objmain;
	}

	@RequestMapping(value = "/getBuyerPO",method=RequestMethod.GET)
	public @ResponseBody JSONObject getBuyerPO(String buyerPoNo) {
		JSONObject objmain = new JSONObject();


		JSONArray mainArray = new JSONArray();
		BuyerPO buyerPo = orderService.getBuyerPO(buyerPoNo);

		objmain.put("buyerPO",buyerPo);

		return objmain;
	}

	@RequestMapping(value = "/submitBuyerPO",method=RequestMethod.POST)
	public @ResponseBody JSONObject submitBuyerPO(BuyerPO buyerPO) {
		JSONObject objmain = new JSONObject();

		if(orderService.submitBuyerPO(buyerPO)) {
			objmain.put("result", "Successfull");
		}else {
			objmain.put("result", "Something Wrong");
		}

		return objmain;
	}

	@RequestMapping(value = "/editBuyerPO",method=RequestMethod.POST)
	public @ResponseBody JSONObject editBuyerPO(BuyerPO buyerPO) {
		JSONObject objmain = new JSONObject();

		if(orderService.editBuyerPO(buyerPO)) {
			objmain.put("result", "Successfull");
		}else {
			objmain.put("result", "Something Wrong");
		}

		return objmain;
	}




	//File Upload

	@RequestMapping(value = "/file_upload",method=RequestMethod.GET)
	public ModelAndView fileUpload(ModelMap map,HttpSession session) {


		ModelAndView view = new ModelAndView("order/fileupload");

		return view; //JSP - /WEB-INF/view/index.jsp
	}






	// Process multiple file upload action and return a result page to user. 
	@RequestMapping(value="/save-product/{purpose}/{user}", method={RequestMethod.PUT, RequestMethod.POST})
	public String uploadFileSubmit(@PathVariable ("purpose") String purpose,@PathVariable ("user") String user, MultipartHttpServletRequest multipartRequest, HttpServletRequest request, HttpServletResponse response) {
		try
		{


			Logger.getLogger(this.getClass()).warning("Inside Confirm Servlet");  
			response.setContentType("text/html");

			String hostname = request.getRemoteHost(); // hostname
			System.out.println("hostname "+hostname);

			String computerName = null;
			String remoteAddress = request.getRemoteAddr();
			InetAddress inetAddress=null;


			inetAddress = InetAddress.getByName(remoteAddress);
			System.out.println("inetAddress: " + inetAddress);
			computerName = inetAddress.getHostName();

			System.out.println("computerName: " + computerName);


			if (computerName.equalsIgnoreCase("localhost")) {
				computerName = java.net.InetAddress.getLocalHost().getCanonicalHostName();
			}else if(hostname.equalsIgnoreCase("0:0:0:0:0:0:0:1")){
				inetAddress = InetAddress.getLocalHost();
				computerName=inetAddress.getHostName();
			}
			System.out.println("ip : " + inetAddress);
			System.out.println("computerName: " + computerName);

			//   Date date=new Date();



			// Get multiple file control names.
			Iterator<String> it = multipartRequest.getFileNames();

			while(it.hasNext())
			{
				String fileControlName = it.next();

				MultipartFile srcFile = multipartRequest.getFile(fileControlName);

				String uploadFileName = srcFile.getOriginalFilename();

				System.out.println(" file names "+uploadFileName);

				orderService.fileUpload(uploadFileName, computerName,inetAddress.toString(), purpose,user);

				// Create server side target file path.


				String destFilePath = UPLOAD_FILE_SAVE_FOLDER+uploadFileName;

				File destFile = new File(destFilePath);

				// Save uploaded file to target.
				srcFile.transferTo(destFile);

				//msgBuf.append("Upload file " + uploadFileName + " is saved to " + destFilePath + "<br/><br/>");
			}

			// Set message that will be displayed in return page.
			//  model.addAttribute("message", msgBuf.toString());

		}catch(IOException ex)
		{
			ex.printStackTrace();
		}finally
		{
			return "upload_file_result";
		}
	}

	@RequestMapping(value = "/findfile/{start}/{end}/{user}",method=RequestMethod.POST)
	public @ResponseBody JSONObject findfile(@PathVariable ("start") String start,@PathVariable ("end") String end,@PathVariable ("user") String user) {

		List<pg.orderModel.fileUpload> FileList=orderService.findfiles(start, end,user);
		JSONObject objmain = new JSONObject();

		JSONArray mainArray = new JSONArray();

		for (int i = 0; i < FileList.size(); i++) {
			JSONObject obj = new JSONObject();
			obj.put("id", FileList.get(i).getAutoid());
			obj.put("filename", FileList.get(i).getFilename());
			obj.put("upby", FileList.get(i).getUploadby());
			obj.put("upIp", FileList.get(i).getUploadip());
			obj.put("upMachine", FileList.get(i).getUploadmachine());
			obj.put("purpose", FileList.get(i).getPurpose());
			obj.put("upDate", FileList.get(i).getUploaddate());
			obj.put("upDateTime", FileList.get(i).getDatetime());
			obj.put("DownBy", FileList.get(i).getDownloadby());
			obj.put("DownIp", FileList.get(i).getDownloadip());
			obj.put("DownMachine", FileList.get(i).getDownloadmachine());
			obj.put("DownDate", FileList.get(i).getDownloaddate());
			obj.put("DownDatetime", FileList.get(i).getDownloadtime());

			mainArray.add(obj);

		}

		objmain.put("result", mainArray);
		return objmain;
	}


	@RequestMapping(value="/download/{fileName:.+}/{user}", method=RequestMethod.POST)
	public @ResponseBody void downloadfile(HttpServletResponse response,@PathVariable ("fileName") String fileName,@PathVariable ("user") String user,HttpServletRequest request) throws IOException {
		System.out.println(" download controller ");

		Logger.getLogger(this.getClass()).warning("Inside Confirm Servlet");  
		response.setContentType("text/html");

		String hostname = request.getRemoteHost(); // hostname
		System.out.println("hostname "+hostname);

		String computerName = null;
		String remoteAddress = request.getRemoteAddr();
		InetAddress inetAddress=null;


		inetAddress = InetAddress.getByName(remoteAddress);
		System.out.println("inetAddress: " + inetAddress);
		computerName = inetAddress.getHostName();

		System.out.println("computerName: " + computerName);


		if (computerName.equalsIgnoreCase("localhost")) {
			computerName = java.net.InetAddress.getLocalHost().getCanonicalHostName();
		}else if(hostname.equalsIgnoreCase("0:0:0:0:0:0:0:1")){
			inetAddress = InetAddress.getLocalHost();
			computerName=inetAddress.getHostName();
		}
		System.out.println("ip : " + inetAddress);
		System.out.println("computerName: " + computerName);




		try {




			String filePath = DIRECTORY+fileName;

			System.out.println(" filename "+fileName);

			try {
				File file = new File(filePath);
				System.out.println(" file "+file.length()/(1024*1024));
				FileInputStream in = new FileInputStream(file);
				System.out.println(" file in "+in);
				response.setHeader("Expires", new Date().toGMTString());
				response.setContentType(URLConnection.guessContentTypeFromStream(in));

				// response.setContentLength(Files.readAllBytes(file.toPath()).length);

				response.setContentLength((int)file.length());

				response.setHeader("Content-Disposition","attachment; filename=\"" + fileName +"\"");
				response.setHeader("Pragma", "no-cache");

				response.setContentType("application/octet-stream");
				// FileCopyUtils.copy(in, response.getOutputStream());


				IOUtils.copyLarge(in, response.getOutputStream());


				boolean download=orderService.fileDownload(fileName, user, inetAddress.toString(), computerName);

				in.close();
				response.flushBuffer();

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@RequestMapping(value = "/delete/{filename:.+}",method=RequestMethod.POST)
	public @ResponseBody boolean findfile(@PathVariable ("filename") String filename) {

		boolean delete=orderService.deletefile(filename);

		if (delete) {
			File file=new File(DIRECTORY+filename);
			file.delete();
		}

		return delete;
	}


	//Accessoires Indent Create
	@RequestMapping(value = "/accessories_indent",method=RequestMethod.GET)
	public ModelAndView accessories_indent(ModelMap map,HttpSession session) {

		List<commonModel>purchaseorders=orderService.PurchaseOrders();

		List<AccessoriesIndent>listAccPending=orderService.getPendingAccessoriesIndent();

		List<commonModel>accessoriesitem=orderService.AccessoriesItem("1");

		List<AccessoriesIndent>listAccPostedData=orderService.getPostedAccessoriesIndent();

		List<commonModel>unit=orderService.Unit();
		List<commonModel>brand=orderService.Brands();
		List<commonModel>color=orderService.AllColors();
		ModelAndView view = new ModelAndView("order/accessories_indent");
		view.addObject("purchaseorders",purchaseorders);
		view.addObject("accessories",accessoriesitem);
		view.addObject("unit",unit);
		view.addObject("brand",brand);
		view.addObject("color",color);
		view.addObject("listAccPostedData",listAccPostedData);

		view.addObject("listAccPending",listAccPending);

		return view; //JSP - /WEB-INF/view/index.jsp
	}




	@ResponseBody
	@RequestMapping(value = "/maxAIno",method=RequestMethod.POST)
	public String maxAIno( ) {
		System.out.println(" maxAIno Id ");
		String maxaino="";

		maxaino=orderService.maxAIno();

		return maxaino;

	}

	

	@ResponseBody
	@RequestMapping(value = "/poWiseStyles/{po}",method=RequestMethod.POST)
	public JSONObject poWiseStyles(@PathVariable ("po") String po) {
		System.out.println(" powisestyles ");


		JSONObject objmain = new JSONObject();
		JSONArray mainarray = new JSONArray();

		List<commonModel>styles=orderService.Styles(po);

		for (int i = 0; i < styles.size(); i++) {
			JSONObject obj=new JSONObject();

			obj.put("id", styles.get(i).getId());
			obj.put("name", styles.get(i).getName());

			mainarray.add(obj);

		}

		objmain.put("result", mainarray);
		System.out.println(" obj main "+objmain);

		return objmain;

	}

	@ResponseBody
	@RequestMapping(value = "/stylewiseitems",method=RequestMethod.GET)
	public JSONObject stylewiseitems(String buyerorderid,String style) {

		JSONObject objmain = new JSONObject();
		JSONArray mainarray = new JSONArray();

		List<commonModel>items=orderService.Items(buyerorderid,style);

		for (int i = 0; i < items.size(); i++) {
			JSONObject obj=new JSONObject();

			obj.put("id", items.get(i).getId());
			obj.put("name", items.get(i).getName());

			mainarray.add(obj);

		}

		objmain.put("result", mainarray);
		System.out.println(" obj main "+objmain);

		return objmain;

	}


	@ResponseBody
	@RequestMapping(value = "/styleItemsWiseColor",method=RequestMethod.GET)
	public JSONObject styleItemsWiseColor(String buyerorderid,String style,String item) {
		System.out.println(" stylewisei items ");


		JSONObject objmain = new JSONObject();
		JSONArray mainarray = new JSONArray();

		System.out.println("item "+item);

		List<commonModel>items=orderService.styleItemsWiseColor(buyerorderid,style,item);

		for (int i = 0; i < items.size(); i++) {
			JSONObject obj=new JSONObject();

			obj.put("id", items.get(i).getId());
			obj.put("name", items.get(i).getName());

			mainarray.add(obj);

		}

		objmain.put("result", mainarray);
		System.out.println(" obj main "+objmain);

		return objmain;

	}

	@ResponseBody
	@RequestMapping(value = "/shippingMark/{po}/{style}/{item}",method=RequestMethod.POST)
	public List purchaseOrders(@PathVariable ("po") String po,@PathVariable ("style") String style,@PathVariable ("item") String item) {
		System.out.println(" shippingmarks ");

		List<commonModel>shippingMarks=orderService.ShippingMark(po, style, item);



		return shippingMarks;

	}


	@ResponseBody
	@RequestMapping(value = "/styleitemColorWiseSize",method=RequestMethod.GET)
	public JSONObject itemWiseSize(String buyerorderid,String style,String item,String color) {

		System.out.println("size");

		JSONObject objmain = new JSONObject();
		JSONArray mainarray = new JSONArray();

		List<commonModel>sizes=orderService.Size(buyerorderid, style,item,color);

		for (int i = 0; i < sizes.size(); i++) {
			JSONObject obj=new JSONObject();

			obj.put("id", sizes.get(i).getId());
			obj.put("name", sizes.get(i).getName());

			mainarray.add(obj);

		}

		objmain.put("size", mainarray);
		return objmain;

	}

	@ResponseBody
	@RequestMapping(value = "/styleitemColorWiseOrderQty",method=RequestMethod.GET)
	public JSONObject styleitemColorWiseOrderQty(String buyerorderid,String style,String item,String color,String size) {


		JSONObject objmain = new JSONObject();
		JSONArray mainarray = new JSONArray();

		List<commonModel>sizes=orderService.SizewiseQty(buyerorderid, style,item,color,size);

		for (int i = 0; i < sizes.size(); i++) {
			JSONObject obj=new JSONObject();

			obj.put("qty", sizes.get(i).getQty());
			mainarray.add(obj);

		}

		objmain.put("size", mainarray);
		return objmain;

	}


	@ResponseBody
	@RequestMapping(value = "/itemWiseColor/{style}/{item}",method=RequestMethod.POST)
	public JSONObject itemWiseColor(@PathVariable ("style") String style,@PathVariable ("item") String item) {
		System.out.println(" Purchase Orders ");
		JSONObject objmain = new JSONObject();
		JSONArray mainarray = new JSONArray();




		List<commonModel>colors=orderService.Colors(style, item);

		for (int i = 0; i < colors.size(); i++) {
			JSONObject obj=new JSONObject();

			obj.put("id", colors.get(i).getId());
			obj.put("name", colors.get(i).getName());

			mainarray.add(obj);

		}

		objmain.put("color", mainarray);
		System.out.println(" obj main "+objmain);

		return objmain;

	}


	@ResponseBody
	@RequestMapping(value = "/SizeWiseQty/{style}/{item}/{size}/{color}/{type}",method=RequestMethod.POST)
	public JSONObject SizeWiseQty(@PathVariable ("style") String style,@PathVariable ("item") String item,@PathVariable ("size") String size,@PathVariable ("color") String color,@PathVariable ("type") String type) {
		System.out.println(" Purchase Orders ");
		JSONObject objmain = new JSONObject();
		JSONArray mainarray = new JSONArray();




		List<commonModel>qty=orderService.SizewiseQty(style,item,size,color,type);

		for (int i = 0; i < qty.size(); i++) {
			JSONObject obj=new JSONObject();


			obj.put("name", qty.get(i).getQty());

			mainarray.add(obj);

		}

		objmain.put("size", mainarray);
		System.out.println(" obj main "+objmain);

		return objmain;

	}


	@ResponseBody
	@RequestMapping(value = "/insertAccessoriesIndent",method=RequestMethod.POST)
	public JSONObject insertAccessoriesIndent(AccessoriesIndent v) {
		JSONObject objmain = new JSONObject();
		JSONArray mainarray = new JSONArray();
		boolean insert= orderService.insertaccessoriesIndent(v);

		if(insert) {
			List<AccessoriesIndent>qty=orderService.getAccessoriesIndent(v.getPo(),v.getStyle(),v.getItemname(),v.getItemcolor());

			for (int i = 0; i < qty.size(); i++) {
				JSONObject obj=new JSONObject();

				obj.put("autoId", qty.get(i).getAutoid());
				obj.put("po", qty.get(i).getPo());
				obj.put("style", qty.get(i).getStyle());
				obj.put("itemname", qty.get(i).getItemname());
				obj.put("itemcolor", qty.get(i).getItemcolor());
				obj.put("shippingmark", qty.get(i).getShippingmark());
				obj.put("accessoriesName", qty.get(i).getAccessoriesName());
				obj.put("sizeName", qty.get(i).getSizeName());
				obj.put("requiredUnitQty", qty.get(i).getRequiredUnitQty());
				mainarray.add(obj);

			}

			objmain.put("result", mainarray);
			System.out.println(" obj main "+objmain);

			return objmain;
		}
		else {
			return null;
		}

	}

	@ResponseBody
	@RequestMapping(value = "/InstallDataAsSameParticular",method=RequestMethod.POST)
	public JSONObject InstallDataAsSameParticular(String userId,String purchaseOrder,String styleId,String itemId,String colorId,String installAccessories,String forAccessories) {
		JSONObject objmain = new JSONObject();
		JSONArray mainarray = new JSONArray();
		boolean insert= orderService.InstallDataAsSameParticular(userId,purchaseOrder,styleId,itemId,colorId,installAccessories,forAccessories);

		if(insert) {
			List<AccessoriesIndent>qty=orderService.getAccessoriesIndent(purchaseOrder,styleId,itemId,colorId);

			for (int i = 0; i < qty.size(); i++) {
				JSONObject obj=new JSONObject();

				obj.put("autoId", qty.get(i).getAutoid());
				obj.put("po", qty.get(i).getPo());
				obj.put("style", qty.get(i).getStyle());
				obj.put("itemname", qty.get(i).getItemname());
				obj.put("itemcolor", qty.get(i).getItemcolor());
				obj.put("shippingmark", qty.get(i).getShippingmark());
				obj.put("accessoriesName", qty.get(i).getAccessoriesName());
				obj.put("sizeName", qty.get(i).getSizeName());
				obj.put("requiredUnitQty", qty.get(i).getRequiredUnitQty());
				mainarray.add(obj);

			}

			objmain.put("result", mainarray);
			System.out.println(" obj main "+objmain);

			return objmain;
		}
		else {
			return null;
		}

	}

	@ResponseBody
	@RequestMapping(value = "/confrimAccessoriesIndent",method=RequestMethod.POST)
	public String confrimAccessoriesIndent(String user,String aiNo) {
		String msg="Create Occured while cofrim accessories indent";

		boolean update= orderService.confrimAccessoriesIndent(user,aiNo);
		if(update){
			msg="Update Accessories successfully Confrimed";
		}

		return msg;
	}

	@ResponseBody
	@RequestMapping(value = "/accessoriesIndentInfo",method=RequestMethod.GET)
	public String accessoriesIndentInfo(String aiNo) {
		this.AiNo=aiNo;
		return "Success";
	}


	@RequestMapping(value = "/printAccessoriesIndent",method=RequestMethod.GET)
	public @ResponseBody ModelAndView printAccessoriesIndent(ModelMap map) {


		ModelAndView view=new ModelAndView("order/printAccessoriesIndent");


		map.addAttribute("AiNo", AiNo);


		return view;
	}


	@ResponseBody
	@RequestMapping(value = "/editAccessoriesIndent",method=RequestMethod.POST)
	public String editAccessoriesIndent(AccessoriesIndent v) {
		//JSONObject objmain = new JSONObject();
		//JSONArray mainarray = new JSONArray();
		String msg="Create Occured while updating accessories indent";
		boolean update= orderService.editaccessoriesIndent(v);
		if(update) {
			msg="Update Accessories successfully";
		}


		return msg;
	}


	@ResponseBody
	@RequestMapping(value = "/accessoriesItemSet/{id}",method=RequestMethod.GET)
	public JSONObject accessoriesItemSet(@PathVariable ("id") String id) {
		JSONObject objmain = new JSONObject();
		JSONArray mainarray = new JSONArray();

		List<AccessoriesIndent>list=orderService.getAccessoriesIndentItemDetails(id);

		for (int i = 0; i < list.size(); i++) {
			JSONObject obj=new JSONObject();

			obj.put("autoid", list.get(i).getAutoid());
			obj.put("po", list.get(i).getPo());
			obj.put("style", list.get(i).getStyle());
			obj.put("itemname", list.get(i).getItemname());
			obj.put("itemcolor", list.get(i).getItemcolor());
			obj.put("shippingmark", list.get(i).getShippingmark());
			obj.put("accessoriesname", list.get(i).getAccessoriesName());
			obj.put("sizeName", list.get(i).getSizeName());

			System.out.println("itemcolor "+list.get(i).getItemcolor());
			obj.put("accessoriessize", list.get(i).getAccessoriessize());
			obj.put("perunit",df.format(Double.parseDouble( list.get(i).getPerunit())));
			obj.put("totalbox", df.format(Double.parseDouble(list.get(i).getTotalbox())));
			obj.put("orderqty", df.format(Double.parseDouble(list.get(i).getOrderqty())));
			obj.put("qtyindozen", df.format(Double.parseDouble(list.get(i).getQtyindozen())));
			obj.put("reqperpcs", df.format(Double.parseDouble(list.get(i).getReqperpcs())));
			obj.put("reqperdozen", df.format(Double.parseDouble(list.get(i).getReqperdozen())));
			obj.put("dividedby", df.format(Double.parseDouble(list.get(i).getDividedby())));
			obj.put("extrainpercent", df.format(Double.parseDouble(list.get(i).getExtrainpercent())));
			obj.put("percentqty", df.format(Double.parseDouble(list.get(i).getPercentqty())));
			obj.put("totalqty", df.format(Double.parseDouble(list.get(i).getTotalqty())));
			obj.put("unit", list.get(i).getUnit());
			obj.put("requiredUnitQty", df.format(Double.parseDouble(list.get(i).getRequiredUnitQty())));
			obj.put("indentBrandId", list.get(i).getIndentBrandId());
			obj.put("indentColorId", list.get(i).getIndentColorId());

			obj.put("indentColorId", list.get(i).getIndentColorId());

			mainarray.add(obj);

		}

		objmain.put("result", mainarray);
		System.out.println(" obj main "+objmain);

		return objmain;

	}

	//accessories_indent_curton 
	@RequestMapping(value = "/accessories_indent_curton",method=RequestMethod.GET)
	public ModelAndView accessories_indent_curton(ModelMap map,HttpSession session) {

		List<commonModel>purchaseorders=orderService.PurchaseOrders();
		List<commonModel>accessoriesitem=orderService.AccessoriesItem("2");
		List<commonModel>unit=orderService.Unit();

		ModelAndView view = new ModelAndView("order/accessories_indent_curton");
		view.addObject("purchaseorders",purchaseorders);
		view.addObject("accessories",accessoriesitem);
		view.addObject("unit",unit);
		//map.addAttribute("styleList",styleList);

		return view; //JSP - /WEB-INF/view/index.jsp
	}


	@ResponseBody
	@RequestMapping(value = "/saveAccessoriesCurton",method=RequestMethod.POST)
	public JSONObject saveAccessoriesCurton(accessoriesindentcarton v) {
		JSONObject objmain = new JSONObject();
		JSONArray mainarray = new JSONArray();
		boolean insert= orderService.saveAccessoriesCurton(v);

		if(insert) {
			List<accessoriesindentcarton>qty=orderService.getAccessoriesIndentCarton(v.getPoNo(),v.getStyle(),v.getItem(),v.getItemColor());

			for (int i = 0; i < qty.size(); i++) {
				JSONObject obj=new JSONObject();

				obj.put("autoId", qty.get(i).getAutoid());
				obj.put("poNo", qty.get(i).getPoNo());
				obj.put("style", qty.get(i).getStyle());
				obj.put("itemName", qty.get(i).getItem());
				obj.put("itemColor", qty.get(i).getItemColor());
				obj.put("shippingMark", qty.get(i).getShippingMark());
				obj.put("accessoriesName", qty.get(i).getAccessoriesItem());
				obj.put("cartonSize", qty.get(i).getAccessoriesSize());
				obj.put("totalQty", df.format(Double.parseDouble(qty.get(i).getTotalQty())));
				mainarray.add(obj);

			}

			objmain.put("result", mainarray);
			System.out.println(" obj main "+objmain);

			return objmain;
		}
		else {
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/editAccessoriesCurton",method=RequestMethod.POST)
	public JSONObject editAccessoriesCurton(accessoriesindentcarton v) {
		JSONObject objmain = new JSONObject();
		JSONArray mainarray = new JSONArray();
		boolean insert= orderService.editAccessoriesCurton(v);

		if(insert) {
			List<accessoriesindentcarton>qty=orderService.getAccessoriesIndentCarton(v.getPoNo(),v.getStyle(),v.getItem(),v.getItemColor());

			for (int i = 0; i < qty.size(); i++) {
				JSONObject obj=new JSONObject();

				obj.put("autoId", qty.get(i).getAutoid());
				obj.put("poNo", qty.get(i).getPoNo());
				obj.put("style", qty.get(i).getStyle());
				obj.put("itemName", qty.get(i).getItem());
				obj.put("itemColor", qty.get(i).getItemColor());
				obj.put("shippingMark", qty.get(i).getShippingMark());
				obj.put("accessoriesName", qty.get(i).getAccessoriesItem());
				obj.put("cartonSize", qty.get(i).getAccessoriesSize());
				obj.put("totalQty", df.format(Double.parseDouble(qty.get(i).getTotalQty())));
				mainarray.add(obj);

			}

			objmain.put("result", mainarray);
			System.out.println(" obj main "+objmain);

			return objmain;
		}
		else {
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/getAllAccessoriesCartonData",method=RequestMethod.POST)
	public JSONObject getAllAccessoriesCartonData(accessoriesindentcarton v) {
		JSONObject objmain = new JSONObject();
		JSONArray mainarray = new JSONArray();

		List<accessoriesindentcarton>qty=orderService.getAllAccessoriesCartonData();

		for (int i = 0; i < qty.size(); i++) {
			JSONObject obj=new JSONObject();

			obj.put("autoId", qty.get(i).getAutoid());
			obj.put("poNo", qty.get(i).getPoNo());
			obj.put("style", qty.get(i).getStyle());
			obj.put("itemName", qty.get(i).getItem());
			obj.put("itemColor", qty.get(i).getItemColor());
			obj.put("shippingMark", qty.get(i).getShippingMark());
			obj.put("accessoriesName", qty.get(i).getAccessoriesItem());
			obj.put("cartonSize", qty.get(i).getAccessoriesSize());
			obj.put("totalQty", df.format(Double.parseDouble(qty.get(i).getTotalQty())));
			mainarray.add(obj);

		}

		objmain.put("result", mainarray);
		System.out.println(" obj main "+objmain);

		return objmain;

	}


	@ResponseBody
	@RequestMapping(value = "/accessoriesCartonItemSet/{id}",method=RequestMethod.GET)
	public JSONObject accessoriesCartonItemSet(@PathVariable ("id") String id) {
		JSONObject objmain = new JSONObject();
		JSONArray mainarray = new JSONArray();

		//List<AccessoriesIndent>list=orderService.getAccessoriesIndentItemDetails(id);
		List<accessoriesindentcarton>list=orderService.getAccessoriesIndentCartonItemDetails(id);

		for (int i = 0; i < list.size(); i++) {
			JSONObject obj=new JSONObject();

			obj.put("autoid", list.get(i).getAutoid());
			obj.put("poNo", list.get(i).getPoNo());
			obj.put("style", list.get(i).getStyle());
			obj.put("item", list.get(i).getItem());
			obj.put("itemColor", list.get(i).getItemColor());
			obj.put("shippingMark", list.get(i).getShippingMark());
			obj.put("accessoriesItem", list.get(i).getAccessoriesItem());
			obj.put("accessoriesSize", list.get(i).getAccessoriesSize());
			obj.put("unit", list.get(i).getUnit());


			obj.put("orderqty",df.format(Double.parseDouble( list.get(i).getOrderqty())));

			obj.put("length1", df.format(Double.parseDouble(list.get(i).getLength1())));
			obj.put("width1", df.format(Double.parseDouble(list.get(i).getWidth1())));
			obj.put("height1", df.format(Double.parseDouble(list.get(i).getHeight1())));
			obj.put("add1", df.format(Double.parseDouble(list.get(i).getAdd1())));


			obj.put("length2", df.format(Double.parseDouble(list.get(i).getLength2())));
			obj.put("width2", df.format(Double.parseDouble(list.get(i).getWidth2())));
			obj.put("height2", df.format(Double.parseDouble(list.get(i).getHeight2())));
			obj.put("add2", df.format(Double.parseDouble(list.get(i).getAdd2())));

			obj.put("totalQty", df.format(Double.parseDouble(list.get(i).getTotalQty())));
			obj.put("devideBy", df.format(Double.parseDouble(list.get(i).getDevideBy())));
			obj.put("ply", df.format(Double.parseDouble(list.get(i).getPly())));



			mainarray.add(obj);

		}

		objmain.put("result", mainarray);
		System.out.println(" obj main "+objmain);

		return objmain;

	}


	//Fabrics Indent 
	@RequestMapping(value = "/fabrics_indent",method=RequestMethod.GET)
	public ModelAndView fabrics_indent(ModelMap map,HttpSession session) {
		List<FabricsIndent> fabricindentlist= orderService.getStyleDetailsForFabricsIndent();
		List<String> poList = orderService.getPurchaseOrderList();
		List<Color> colorList = registerService.getColorList();
		List<FabricsItem> fabricsItemList = registerService.getFabricsItemList();
		List<Brand> brandList = registerService.getBrandList();
		List<Unit> unitList = registerService.getUnitList();
		List<FabricsIndent> fabricsIndentList = orderService.getFabricsIndentList();
		ModelAndView view = new ModelAndView("order/fabrics-indent");
		
		view.addObject("fabricindentlist", fabricindentlist);
		view.addObject("poList", poList);
		view.addObject("fabricsList",fabricsItemList);
		view.addObject("colorList",colorList);
		view.addObject("brandList",brandList);
		view.addObject("unitList",unitList);
		view.addObject("fabricsIndentList",fabricsIndentList);
		return view; //JSP - /WEB-INF/view/index.jsp
	}

	@RequestMapping(value = "/saveFabricsIndent",method=RequestMethod.POST)
	public @ResponseBody JSONObject saveFabricsIndent(FabricsIndent	fabricsIndent) {
		JSONObject objmain = new JSONObject();
		if(!orderService.isFabricsIndentExist(fabricsIndent)) {
			if(orderService.saveFabricsIndent(fabricsIndent)) {

				List<FabricsIndent> fabricsIndentList = orderService.getFabricsIndentList();

				objmain.put("result",fabricsIndentList);
			}else {
				objmain.put("result", "Something Wrong");
			}
		}else {
			objmain.put("result", "duplicate");
		}
		return objmain;
	}

	@RequestMapping(value = "/editFabricsIndent",method=RequestMethod.POST)
	public @ResponseBody JSONObject editFabricsIndent(FabricsIndent	fabricsIndent) {
		JSONObject objmain = new JSONObject();
		if(!orderService.isFabricsIndentExist(fabricsIndent)) {
			if(orderService.editFabricsIndent(fabricsIndent)) {
				List<FabricsIndent> fabricsIndentList = orderService.getFabricsIndentList();
				objmain.put("result",fabricsIndentList);
			}else {
				objmain.put("result", "Something Wrong");
			}
		}else {
			objmain.put("result", "duplicate");
		}

		return objmain;
	}

	@RequestMapping(value = "/getFabricsIndent",method=RequestMethod.GET)
	public @ResponseBody JSONObject getFabricsIndent(String autoId) {
		JSONObject objmain = new JSONObject();
		FabricsIndent fabricsIndent = orderService.getFabricsIndent(autoId);
		objmain.put("fabricsIndent",fabricsIndent);
		return objmain;
	}

	@RequestMapping(value = "/getPOWiseStyleLoad",method=RequestMethod.GET)
	public @ResponseBody JSONObject getPOWiseStyleLoad(String purchaseOrder) {
		JSONObject objmain = new JSONObject();
		List<Style> styleList = orderService.getPOWiseStyleList(purchaseOrder);
		objmain.put("styleList",styleList);
		return objmain;
	}

	@RequestMapping(value = "/getStyleItemWiseColor",method=RequestMethod.GET)
	public @ResponseBody JSONObject getItemWiseColor(String styleId,String itemId) {
		JSONObject objmain = new JSONObject();
		List<Color> colorList = orderService.getStyleItemWiseColor(styleId,itemId);
		objmain.put("colorList",colorList);
		return objmain;
	}

	@RequestMapping(value = "/getOrderQtyByPOStyleItemAndColor",method=RequestMethod.GET)
	public @ResponseBody JSONObject getOrderQtyByPOStyleItemAndColor(String purchaseOrder,String styleId,String itemId,String colorId) {
		JSONObject objmain = new JSONObject();
		double orderQty = orderService.getOrderQuantity(purchaseOrder,styleId,itemId,colorId);
		objmain.put("orderQuantity",orderQty);
		objmain.put("dozenQuantity", orderQty/12);
		return objmain;
	}

	@RequestMapping(value = "/getUnitValue",method=RequestMethod.GET)
	public @ResponseBody JSONObject getUnitValue(String unitId) {
		JSONObject objmain = new JSONObject();
		Unit unit = registerService.getUnit(unitId);
		objmain.put("unitValue",unit.getUnitValue());
		return objmain;
	}


	//Buyer Purchase Order Create
	@RequestMapping(value = "/sample_requisition",method=RequestMethod.GET)
	public ModelAndView sample_requisition(ModelMap map,HttpSession session) {

		ModelAndView view = new ModelAndView("order/sample_requisition");
		List<SizeGroup> groupList = registerService.getStyleSizeGroupList();
		List<BuyerModel> buyerList= registerService.getAllBuyers();
		//List<FactoryModel> factoryList = registerService.getAllFactories();
		List<SampleRequisitionItem> sampleReqList = orderService.getSampleRequisitionList();
		List<commonModel> sampleList = orderService.getSampleList();
		List<commonModel> inchargeList = orderService.getInchargeList();
		List<commonModel> merchendizerList = orderService.getMerchendizerList();

		view.addObject("groupList",groupList);
		view.addObject("buyerList",buyerList);
		//view.addObject("factoryList",factoryList);
		view.addObject("sampleReqList",sampleReqList);
		view.addObject("sampleList",sampleList);
		view.addObject("inchargeList",inchargeList);
		view.addObject("merchendizerList",merchendizerList);
		return view; //JSP - /WEB-INF/view/index.jsp
	}


	@RequestMapping(value = "/addItemToSampleRequisition",method=RequestMethod.POST)
	public @ResponseBody JSONObject addItemToSampleRequisition(SampleRequisitionItem v) {
		JSONObject objmain = new JSONObject();

		if(orderService.addItemToSampleRequisition(v)) {
			JSONArray mainArray = new JSONArray();
			List<SampleRequisitionItem> sampleList = orderService.getIncomepleteSampleRequisitionItemList(v.getUserId());
			//List<SampleRequisitionItem> sampleList = orderService.getSampleRequisitionItemList(v.getUserId());
			objmain.put("result",sampleList);
		}else {
			objmain.put("result", "Something Wrong");
		}

		return objmain;
	}


	@RequestMapping(value = "/confrimItemToSampleRequisition",method=RequestMethod.POST)
	public @ResponseBody String confrimItemToSampleRequisition(SampleRequisitionItem v) {
		String msg="Create Occue while confrim sample requisition";

		if(orderService.confrimItemToSampleRequisition(v)) {
			msg="Sample Requisition Confrim Successfully";
		}

		return msg;
	}

	@RequestMapping(value = "/searchSampleReuisition/{sampleReqId}",method=RequestMethod.GET)
	public @ResponseBody JSONObject searchSampleReuisition(@PathVariable ("sampleReqId") String sampleReqId) {
		JSONObject objmain = new JSONObject();

		JSONArray mainArray = new JSONArray();
		List<SampleRequisitionItem> sampleList = orderService.getSampleRequisitionDetails(sampleReqId);
		objmain.put("result",sampleList);

		return objmain;
	}

	@ResponseBody
	@RequestMapping(value = "/sampleRequisitionInfo",method=RequestMethod.GET)
	public String sampleRequisitionInfo(String sampleReqId) {
		this.SampleReqId=sampleReqId;
		return "Success";
	}
	
	
	@RequestMapping(value = "/printsampleRequisition",method=RequestMethod.GET)
	public @ResponseBody ModelAndView printsampleRequisition(ModelMap map) {
		
		
		ModelAndView view=new ModelAndView("order/printsampleRequisition");
		
		
		map.addAttribute("SampleReqId", SampleReqId);

	
		return view;
	}
	
	//Purchase Order
	@RequestMapping(value = "/purchase_order",method=RequestMethod.GET)
	public ModelAndView purchase_order(ModelMap map,HttpSession session) {

		ModelAndView view = new ModelAndView("order/purchase-order");
		List<String> poList = orderService.getPurchaseOrderList();
		List<Factory> factoryList = registerService.getFactoryNameList();
		List<MerchandiserInfo> merchandiserList = registerService.getMerchandiserList();
		List<SupplierModel> supplierList = registerService.getAllSupplier();
		List<PurchaseOrder> purchaseOrderList = orderService.getPurchaseOrderSummeryList();
		view.addObject("poList",poList);
		view.addObject("factoryList",factoryList);
		view.addObject("merchendiserList",merchandiserList);
		view.addObject("supplierList",supplierList);
		view.addObject("purchaseOrderList",purchaseOrderList);
		return view; //JSP - /WEB-INF/view/index.jsp
	}

	@RequestMapping(value = "/getTypeWiseIndentItems",method=RequestMethod.GET)
	public @ResponseBody JSONObject getTypeWiseIndentItems(String purchaseOrder,String styleId,String type) {
		JSONObject objmain = new JSONObject();
		List<AccessoriesItem>  itemList = orderService.getTypeWiseIndentItems(purchaseOrder,styleId,type);
		objmain.put("itemList", itemList);
		return objmain;
	}

	@RequestMapping(value = "/addIndentItem",method=RequestMethod.GET)
	public @ResponseBody JSONObject addIndentItem(PurchaseOrderItem purchaseOrderItem) {
		JSONObject objmain = new JSONObject();
		List<PurchaseOrderItem> poItemList = orderService.getPurchaseOrderItemList(purchaseOrderItem);
		objmain.put("poItemList", poItemList);
		return objmain;
	}

	@RequestMapping(value = "/submitPurchaseOrder",method=RequestMethod.POST)
	public @ResponseBody JSONObject submitPurchaseOrder(PurchaseOrder	purchaseOrder) {
		JSONObject objmain = new JSONObject();
		if(orderService.submitPurchaseOrder(purchaseOrder)) {
			objmain.put("result", "successfull");
		}else {
			objmain.put("result", "duplicate");
		}
		return objmain;
	}

	@RequestMapping(value = "/editPurchaseOrder",method=RequestMethod.POST)
	public @ResponseBody JSONObject editPurchaseOrder(PurchaseOrder	purchaseOrder) {
		JSONObject objmain = new JSONObject();
		if(orderService.editPurchaseOrder(purchaseOrder)) {
			objmain.put("result", "successfull");
		}else {
			objmain.put("result", "duplicate");
		}
		return objmain;
	}

	@RequestMapping(value = "/searchPurchaseOrder",method=RequestMethod.GET)
	public @ResponseBody JSONObject searchPurchaseOrder(String poNo) {
		JSONObject objmain = new JSONObject();
		PurchaseOrder  purchaseOrder = orderService.getPurchaseOrder(poNo);
		objmain.put("poInfo", purchaseOrder);
		return objmain;
	}

	@RequestMapping(value="/getPurchaseOrderReport/{poNo}/{supplierId}/{type}")
	public @ResponseBody ModelAndView getPurchaseOrderReport(ModelMap map,@PathVariable String poNo,@PathVariable String supplierId,@PathVariable String type) {

		ModelAndView view = new ModelAndView("order/purchaseOrderReportView");
		System.out.println("null test"+poNo+" "+supplierId+" "+type);
		map.addAttribute("poNo",poNo);
		map.addAttribute("supplierId",supplierId);
		map.addAttribute("type",type);

		return view;

	}



	
	//Sample Production
	@RequestMapping(value = "/sample_production",method=RequestMethod.GET)
	public ModelAndView sample_production(ModelMap map,HttpSession session) {

		ModelAndView view = new ModelAndView("order/sample_production");
		//List<SampleCadAndProduction> sampleCommentsList = orderService.getSampleCommentsList();
		//view.addObject("sampleCommentsList",sampleCommentsList);
		return view; //JSP - /WEB-INF/view/index.jsp
	}
	
	@RequestMapping(value = "/getSampleCommentsList",method=RequestMethod.GET)
	public @ResponseBody JSONObject getSampleCommentsList() {
		JSONObject objmain = new JSONObject();
		List<SampleCadAndProduction> sampleCommentsList = orderService.getSampleCommentsList();
		objmain.put("sampleCommentsList", sampleCommentsList);
		return objmain;
	}
	
	@RequestMapping(value = "/getSampleProductionInfo",method=RequestMethod.GET)
	public @ResponseBody JSONObject getSampleProductionInfo(String sampleCommentId) {
		JSONObject objmain = new JSONObject();
		SampleCadAndProduction sampleProduction = orderService.getSampleProductionInfo(sampleCommentId);
		objmain.put("sampleProduction", sampleProduction);
		return objmain;
	}
	
	@RequestMapping(value = "/postSampleProduction",method=RequestMethod.POST)
	public @ResponseBody JSONObject postSampleProduction(SampleCadAndProduction	sampleCadAndProduction) {
		JSONObject objmain = new JSONObject();
		if(orderService.postSampleProductionInfo(sampleCadAndProduction)) {
			objmain.put("result", "successfull");
		}else {
			objmain.put("result", "duplicate");
		}
		return objmain;
	}
	
	@RequestMapping(value="/getSampleProductionReport/{idList}/{printType}")
	public @ResponseBody ModelAndView getSampleProductionReport(ModelMap map,@PathVariable String idList,@PathVariable String printType) {

		ModelAndView view = new ModelAndView("order/sample-production-report-view");
		
		map.addAttribute("purchaseOrder","");
		map.addAttribute("styleId","");
		map.addAttribute("itemId","");
		map.addAttribute("sampleTypeId","");
		map.addAttribute("printType",printType);
		map.addAttribute("sampleCommentId",idList);

		return view;

	}
	
	
	 




	@RequestMapping(value = "style_create")
	public ModelAndView style_create(ModelMap map,HttpSession session) {

		ModelAndView view = new ModelAndView("order/style_create");
		List<BuyerModel> List= registerService.getAllBuyers();
		List<ItemDescription> itemList= orderService.getItemDescriptionList();

		List<Style> styleList= orderService.getStyleWiseItemList();

		map.addAttribute("buyerList",List);
		map.addAttribute("itemList",itemList);
		map.addAttribute("styleList",styleList);
		


		return view; //JSP - /WEB-INF/view/index.jsp
	}

	@ResponseBody
	@RequestMapping(value = "/submitStyleFiles", method = RequestMethod.POST)
	public ModelAndView submitFiles(@RequestParam String buyerId,@RequestParam String itemId,@RequestParam String styleNo,@RequestParam String size,@RequestParam String date,@RequestParam CommonsMultipartFile frontImage,@RequestParam CommonsMultipartFile backImage,HttpSession session,ModelMap map) throws IOException, SQLException {

		List<login> user=(List<login>)session.getAttribute("pg_admin");


		String frontimg=getImageName(frontImage,session);
		System.out.println("frontimg "+frontimg);
		this.FrontImg=frontimg;

		String backimg=getImageName(backImage,session);
		this.BackImg=backimg;
		System.out.println("backimg "+backimg);

		String userId=Integer.toString(user.get(0).getId());

		boolean flag=orderService.SaveStyleCreate(userId,buyerId,itemId,styleNo,size,date,frontimg,backimg) ;

		if(flag) {
			System.out.println("Sucess");
		}
		
		ModelAndView view=new ModelAndView("redirect:style_create");
		map.addAttribute("buyerId", buyerId);
		

		return view;
	}


	@RequestMapping(value = "/cloningCosting",method=RequestMethod.GET)
	public @ResponseBody JSONObject cloningCosting(String oldStyleId,String oldItemId,String newStyleId,String newItemId,String userId) {
		JSONObject objmain = new JSONObject();

		if(orderService.cloningCosting(oldStyleId,oldItemId,newStyleId,newItemId,userId)) {

			List<Costing> costingList = orderService.getCostingList(newStyleId,newItemId);
			objmain.put("result",costingList);
		}else {
			objmain.put("result", "Something Wrong");
		}

		return objmain;
	}

	@ResponseBody
	@RequestMapping(value = "/buyerWisePoLoad/{buyerId}",method=RequestMethod.POST)
	public JSONObject buyerWisePoNo(@PathVariable ("buyerId") String buyerId) {
		System.out.println(" powisestyles ");


		JSONObject objmain = new JSONObject();
		JSONArray mainarray = new JSONArray();

		List<commonModel>styles=orderService.BuyerWisePo(buyerId);

		for (int i = 0; i < styles.size(); i++) {
			JSONObject obj=new JSONObject();

			obj.put("id", styles.get(i).getId());
			obj.put("name", styles.get(i).getName());

			mainarray.add(obj);

		}

		objmain.put("result", mainarray);
		System.out.println(" obj main "+objmain);

		return objmain;

	}
	
	
	@ResponseBody
	@RequestMapping(value = "/fabricsIndentReport/{poid}/{styleid}/{itemid}",method=RequestMethod.POST)
	public String fabricsIndentReport(@PathVariable ("poid") String poid,@PathVariable ("styleid") String styleid,@PathVariable ("itemid") String itemid) {
		System.out.println(" Open Ooudoor sales report 1");
		
		this.poid=poid;
		this.styleid=styleid;
		this.itemid=itemid;
		
		return "yes";
		
	}
	
	
	
	
	@ResponseBody
	@RequestMapping(value = "/fabricsIndentReportView",method=RequestMethod.GET)
	public ModelAndView fabricsIndentReportView(ModelAndView map, FabricsIndent p) {
		
			System.out.println(" Open Ooudoor sales report ");	
			ModelAndView view = new ModelAndView("order/fabricsIndentReport");
	
			view.addObject("poid",poid);
		 	view.addObject("styleid",styleid);
		 	view.addObject("itemid",itemid);
		 	
			
			
			return view;
		
	}
	
	//Parcel
	@RequestMapping(value = "/parcel",method=RequestMethod.GET)
	public ModelAndView parcel(ModelMap map,HttpSession session) {

		ModelAndView view = new ModelAndView("order/parcel");
		List<commonModel> sampleList = orderService.getSampleList();
		List<Style> styleList= orderService.getStyleList();
		List<CourierModel> courierList=orderService.getcourierList();
		List<Unit> unitList= registerService.getUnitList();	
		List<parcelModel> parcelList= orderService.parcelList();	
		
		view.addObject("StyleList",styleList);
		view.addObject("sampletype",sampleList);
		view.addObject("courierList",courierList);
		view.addObject("unitList",unitList);
		view.addObject("parcelList",parcelList);
		return view; //JSP - /WEB-INF/view/index.jsp
	}
	
	
	
	
		@ResponseBody
		@RequestMapping(value = "/insertParcel",method=RequestMethod.GET)
		public String insertParcel(parcelModel parcel) {
		 boolean insert=orderService.insertParcel(parcel);
		 
		 if (insert) {
			return "success";
		}
			return "fail";
		
		}
		
		
		
		@ResponseBody
		@RequestMapping(value = "/getParcelDetails/{id}",method=RequestMethod.GET)
		public List<parcelModel> insertParcel(@PathVariable ("id") String id) {
		 List<parcelModel> List=orderService.getParcelDetails(id);
			
		return List;
		
		}
		
		
		
		@ResponseBody
		@RequestMapping(value = "/editParcel",method=RequestMethod.GET)
		public String editParcel(parcelModel parcel) {
		
			
		 boolean insert=orderService.editParecel(parcel);
		 
		 if (insert) {
			return "success";
		}			
		return "fail";
		
		}
		
		
		
		@ResponseBody
		@RequestMapping(value = "/parcelRepor/{id}",method=RequestMethod.POST)
		public String parcelRepor(@PathVariable ("id") String id) {
			System.out.println(" Open Ooudoor sales report 1");
			
			this.ParcelId=id;
			return "yes";
			
		}
		
		
		@ResponseBody
		@RequestMapping(value = "/parcelReportView",method=RequestMethod.GET)
		public ModelAndView department_medicine_delvierOpen(ModelAndView map, FabricsIndent p) {
			
				System.out.println(" Open Ooudoor sales report ");	
				ModelAndView view = new ModelAndView("order/ParcelReportView");
		
				view.addObject("id",ParcelId);					
				
				return view;			
		}
		
		
		
		@RequestMapping(value = "/sample_cad",method=RequestMethod.GET)
		public ModelAndView sample_cad(ModelMap map,HttpSession session) {

			ModelAndView view = new ModelAndView("order/sample_cad");
			
			
			List<String> poList = orderService.getPurchaseOrderList();
			List<commonModel> sampleList = orderService.getSampleList();
			List<SampleCadAndProduction>Samples=orderService.getSampleComments();	
			view.addObject("Samples",Samples);
			view.addObject("poList",poList);
			view.addObject("sampletype",sampleList);
			view.addObject("SampleList",Samples);
			
			return view; //JSP - /WEB-INF/view/index.jsp
		}
		
		
		
		
		
		@ResponseBody
		@RequestMapping(value = "/insertSamplCad",method=RequestMethod.GET)
		public String insertSamplCad(SampleCadAndProduction sample) {
		 boolean insert=orderService.sampleCadInsert(sample);
		 
		 if (insert) {
			return "success";
		}
			return "fail";
		
		}
		
		
		
		@ResponseBody
		@RequestMapping(value = "/getSampleDetails/{id}",method=RequestMethod.GET)
		public List<SampleCadAndProduction> insertSamplCad(@PathVariable ("id") String id) {
		List<SampleCadAndProduction>  samplelist=orderService.getSampleDetails(id);
		
		return samplelist;
		
		}
		
		
		
		@ResponseBody
		@RequestMapping(value = "/editSampleCad",method=RequestMethod.GET)
		public String editSampleCad(SampleCadAndProduction sample) {
		 boolean insert=orderService.editSampleCad(sample);
		 
		 if (insert) {
			return "success";
		}
			return "fail";
		
		}
		 
		
		
		
		@ResponseBody
		@RequestMapping(value = "/SampleReport/{id}",method=RequestMethod.POST)
		public String SampleReport(@PathVariable ("id") String id) {
			System.out.println(" Open Ooudoor sales report 1");
			
			this.sampleId=id;
			return "yes";
			
		}
		
		
		@ResponseBody
		@RequestMapping(value = "/SampleReportView",method=RequestMethod.GET)
		public ModelAndView SampleReportView(ModelAndView map, FabricsIndent p) {
			
				System.out.println(" Open Ooudoor sales report ");	
				ModelAndView view = new ModelAndView("order/SampleCadReportView");
		
				view.addObject("id",sampleId);					
				
				return view;			
		}

}

