package pg.controller;

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
import org.springframework.web.servlet.ModelAndView;

import pg.registerModel.AccessoriesItem;
import pg.registerModel.Brand;
import pg.registerModel.BuyerModel;
import pg.registerModel.Color;
import pg.registerModel.Country;
import pg.registerModel.CourierModel;
import pg.registerModel.Department;
import pg.registerModel.Designation;
import pg.registerModel.Employee;
import pg.registerModel.FabricsItem;
import pg.registerModel.Factory;
import pg.registerModel.FactoryModel;
import pg.registerModel.InchargeInfo;
import pg.registerModel.ItemDescription;
import pg.registerModel.Line;
import pg.registerModel.LocalItem;
import pg.registerModel.Machine;
import pg.registerModel.MerchandiserInfo;
import pg.registerModel.ParticularItem;
import pg.registerModel.ProcessInfo;
import pg.registerModel.SampleType;
import pg.registerModel.Size;
import pg.registerModel.SizeGroup;
import pg.registerModel.StyleItem;
import pg.registerModel.SupplierModel;
import pg.registerModel.Unit;
import pg.services.RegisterService;

@Controller
public class RegisterController {

	@Autowired
	private RegisterService registerService;


	@RequestMapping(value = "/buyer_create",method=RequestMethod.GET)
	public ModelAndView buyer_create(ModelMap map) {

		ModelAndView view = new ModelAndView("register/Buyer_Create");

		return view; //JSP - /WEB-INF/view/index.jsp
	}


	@ResponseBody
	@RequestMapping(value = "/max_buyerId",method=RequestMethod.POST)
	public String max_buyerId( ) {
		System.out.println(" max buyer Id ");

		String maxId=registerService.maxBuyerId();


		return maxId;

	}


	@ResponseBody
	@RequestMapping(value = "/countryNames/{value}",method=RequestMethod.GET)
	public JSONArray countryNames(@PathVariable ("value") String value) {
		System.out.println(" Countrylist ");

		JSONArray array=new JSONArray();

		List<String> countries=registerService.countries(value);

		for (int i = 0; i < countries.size(); i++) {
			array.add(countries.get(i));
		}




		return array;

	}



	@ResponseBody
	@RequestMapping(value = "/inserBuyer",method=RequestMethod.POST)
	public boolean InsertBuyer(BuyerModel buyer) {
		System.out.println(" insertbuyer  ");


		boolean insertbuyer=registerService.insertBuyer(buyer);

		if (insertbuyer) {
			return true;
		}



		return insertbuyer;

	}



	@ResponseBody
	@RequestMapping(value = "/buerySearch/{value}",method=RequestMethod.GET)
	public JSONArray buerySearch(@PathVariable ("value") String value) {
		System.out.println(" Buyer list ");
		JSONArray array=new JSONArray();
		List<String> buyers=registerService.BuyersList(value);
		for (int i = 0; i < buyers.size(); i++) {
			array.add(buyers.get(i));
		}

		return array;

	}


	@ResponseBody
	@RequestMapping(value = "/bueryDetails/{value}",method=RequestMethod.POST)
	public JSONArray bueryDetails(@PathVariable ("value") String value) {
		System.out.println(" Countrylist ");

		JSONArray array=new JSONArray();

		List<BuyerModel> buyerdetails=registerService.BuyersDetails(value);

		for (int i = 0; i < buyerdetails.size(); i++) {
			array.add(buyerdetails.get(i).getBuyerid());
			array.add(buyerdetails.get(i).getBuyername());
			array.add(buyerdetails.get(i).getBuyercode());
			array.add(buyerdetails.get(i).getBuyerAddress());
			array.add(buyerdetails.get(i).getConsigneeAddress());
			array.add(buyerdetails.get(i).getNotifyAddress());
			array.add(buyerdetails.get(i).getCountry());
			array.add(buyerdetails.get(i).getTelephone());
			array.add(buyerdetails.get(i).getMobile());

			array.add(buyerdetails.get(i).getFax());
			array.add(buyerdetails.get(i).getEmail());
			array.add(buyerdetails.get(i).getSkypeid());

			array.add(buyerdetails.get(i).getBankname());
			array.add(buyerdetails.get(i).getBankaddress());
			array.add(buyerdetails.get(i).getSwiftcode());
			array.add(buyerdetails.get(i).getBankcountry());
		}

		return array;

	}


	@ResponseBody
	@RequestMapping(value = "/editBuyer",method=RequestMethod.POST)
	public boolean editBuyer(BuyerModel buyer) {
		System.out.println(" edit buyer  "+buyer.getBuyername());


		boolean editbuyer=registerService.editBuyer(buyer);
		System.out.println(" edit buyer2  "+editbuyer);

		if (editbuyer) {

			editbuyer=true;
			return editbuyer;
		}



		return editbuyer;

	}



	@ResponseBody
	@RequestMapping(value = "/getAllBuyers",method=RequestMethod.POST)
	public JSONObject getAllBuyers() {
		System.out.println(" all buyers list ");

		JSONObject objmain = new JSONObject();
		JSONArray mainarray = new JSONArray();

		List<BuyerModel> buyerdetails=registerService.getAllBuyers();

		for (int i = 0; i < buyerdetails.size(); i++) {
			JSONObject obj=new JSONObject();

			obj.put("id", buyerdetails.get(i).getBuyerid());
			obj.put("name", buyerdetails.get(i).getBuyername());
			obj.put("code", buyerdetails.get(i).getBuyercode());

			mainarray.add(obj);

		}

		objmain.put("result", mainarray);

		return objmain;

	}

	//Supplier Create



	@RequestMapping(value = "/supplier_create",method=RequestMethod.GET)
	public ModelAndView supplier_create(ModelMap map,HttpSession session) {

		
		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");
		ModelAndView view = new ModelAndView("register/Supplier_Create");
		
		map.addAttribute("userId",userId);
		map.addAttribute("userName",userName);


		return view; //JSP - /WEB-INF/view/index.jsp
	}

	@ResponseBody
	@RequestMapping(value = "/max_supplierid",method=RequestMethod.POST)
	public String max_supplierid( ) {
		System.out.println(" max buyer Id ");

		String maxId=registerService.maxSupplierId();


		return maxId;

	}







	@ResponseBody
	@RequestMapping(value = "/insertSupplier",method=RequestMethod.POST)
	public boolean insertSupplier(SupplierModel supplier) {
		System.out.println(" insert supplier  ");


		boolean insertSupplier=registerService.insertSupplier(supplier);

		if (insertSupplier) {
			return true;
		}



		return insertSupplier;

	}



	@ResponseBody
	@RequestMapping(value = "/suppliersearch/{value}",method=RequestMethod.GET)
	public JSONArray suppliersearch(@PathVariable ("value") String value) {
		System.out.println(" Buyer list ");

		JSONArray array=new JSONArray();

		List<String> suppliers=registerService.supplierList(value);

		for (int i = 0; i < suppliers.size(); i++) {
			array.add(suppliers.get(i));
		}

		return array;

	}



	@ResponseBody
	@RequestMapping(value = "/supplierDetails/{value}",method=RequestMethod.POST)
	public JSONArray supplierDetails(@PathVariable ("value") String value) {
		System.out.println(" Countrylist ");

		JSONArray array=new JSONArray();

		List<SupplierModel> buyerdetails=registerService.SupplierDetails(value);

		for (int i = 0; i < buyerdetails.size(); i++) {
			array.add(buyerdetails.get(i).getSupplierid());
			array.add(buyerdetails.get(i).getSuppliername());
			array.add(buyerdetails.get(i).getSuppliercode());
			array.add(buyerdetails.get(i).getContcatPerson());
			array.add(buyerdetails.get(i).getSupplieraddress());

			array.add(buyerdetails.get(i).getConsigneeAddress());
			array.add(buyerdetails.get(i).getNotifyAddress());
			array.add(buyerdetails.get(i).getCountry());
			array.add(buyerdetails.get(i).getTelephone());
			array.add(buyerdetails.get(i).getMobile());

			array.add(buyerdetails.get(i).getFax());
			array.add(buyerdetails.get(i).getEmail());
			array.add(buyerdetails.get(i).getSkypeid());

			array.add(buyerdetails.get(i).getBankname());
			array.add(buyerdetails.get(i).getBankaddress());
			array.add(buyerdetails.get(i).getAccountno());
			array.add(buyerdetails.get(i).getAccountname());
			array.add(buyerdetails.get(i).getSwiftcode());
			array.add(buyerdetails.get(i).getBankcountry());



		}


		System.out.println(" array  "+array);


		return array;

	}


	@ResponseBody
	@RequestMapping(value = "/editsupplier",method=RequestMethod.POST)
	public boolean editsupplier(SupplierModel supplier) {
		System.out.println(" edit editsupplier  ");


		boolean editbuyer=registerService.editSupplier(supplier);


		if (editbuyer) {

			editbuyer=true;
			return editbuyer;
		}



		return editbuyer;

	}


	@ResponseBody
	@RequestMapping(value = "/getSuppliers",method=RequestMethod.POST)
	public JSONObject getSuppliers() {
		System.out.println(" all buyers list ");

		JSONObject objmain = new JSONObject();
		JSONArray mainarray = new JSONArray();

		List<SupplierModel> buyerdetails=registerService.getAllSupplier();

		for (int i = 0; i < buyerdetails.size(); i++) {
			JSONObject obj=new JSONObject();

			obj.put("id", buyerdetails.get(i).getSupplierid());
			obj.put("name", buyerdetails.get(i).getSuppliername());
			obj.put("code", buyerdetails.get(i).getSuppliercode());

			mainarray.add(obj);

		}

		objmain.put("result", mainarray);

		return objmain;

	}


	//Factory Create

	@RequestMapping(value = "/factory_create",method=RequestMethod.GET)
	public ModelAndView factory_create(ModelMap map,HttpSession session) {

		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");
		
		ModelAndView view = new ModelAndView("register/Factory_Create");
		
		map.addAttribute("userId",userId);
		map.addAttribute("userName",userName);

		return view; //JSP - /WEB-INF/view/index.jsp
	}


	@ResponseBody
	@RequestMapping(value = "/max_factoryId",method=RequestMethod.POST)
	public String max_factoryId( ) {
		System.out.println(" max buyer Id ");

		String maxId=registerService.maxFactoryId();


		return maxId;

	}


	@ResponseBody
	@RequestMapping(value = "/insertFactory",method=RequestMethod.POST)
	public boolean insertFactory(FactoryModel factory) {
		System.out.println(" insert factory  ");


		boolean insertSupplier=registerService.insertFactory(factory);

		if (insertSupplier) {
			return true;
		}
		return insertSupplier;
	}


	@ResponseBody
	@RequestMapping(value = "/factorysearch/{value}",method=RequestMethod.GET)
	public JSONArray factorysearch(@PathVariable ("value") String value) {
		System.out.println(" Buyer list ");

		JSONArray array=new JSONArray();

		List<String> suppliers=registerService.factorylist(value);

		for (int i = 0; i < suppliers.size(); i++) {
			array.add(suppliers.get(i));
		}

		return array;		
	}


	@ResponseBody
	@RequestMapping(value = "/factoryDetails/{value}",method=RequestMethod.POST)
	public JSONArray factoryDetails(@PathVariable ("value") String value) {
		System.out.println(" Countrylist ");

		JSONArray array=new JSONArray();

		List<FactoryModel> factorydetails=registerService.FactoryDetails(value);

		for (int i = 0; i < factorydetails.size(); i++) {
			array.add(factorydetails.get(i).getFactoryid());
			array.add(factorydetails.get(i).getFactoryname());
			array.add(factorydetails.get(i).getTelephone());
			array.add(factorydetails.get(i).getMobile());
			array.add(factorydetails.get(i).getFax());

			array.add(factorydetails.get(i).getEmail());
			array.add(factorydetails.get(i).getSkypeid());
			array.add(factorydetails.get(i).getFactoryaddress());
			array.add(factorydetails.get(i).getBondlicense());



			array.add(factorydetails.get(i).getBankname());
			array.add(factorydetails.get(i).getBankaddress());
			array.add(factorydetails.get(i).getAccountno());
			array.add(factorydetails.get(i).getAccountname());
			array.add(factorydetails.get(i).getSwiftcode());
			array.add(factorydetails.get(i).getBankcountry());



		}
		System.out.println(" array  "+array);

		return array;

	}

	@ResponseBody
	@RequestMapping(value = "/editFactory",method=RequestMethod.POST)
	public boolean editFactory(FactoryModel factory) {
		System.out.println(" edit editsupplier  ");


		boolean editbuyer=registerService.editFactory(factory);


		if (editbuyer) {

			editbuyer=true;
			return editbuyer;
		}



		return editbuyer;

	}


	@ResponseBody
	@RequestMapping(value = "/getFactories",method=RequestMethod.POST)
	public JSONObject getFactories() {
		System.out.println(" all buyers list ");

		JSONObject objmain = new JSONObject();
		JSONArray mainarray = new JSONArray();

		List<FactoryModel> factory=registerService.getAllFactories();

		for (int i = 0; i < factory.size(); i++) {
			JSONObject obj=new JSONObject();

			obj.put("id", factory.get(i).getFactoryid());
			obj.put("name", factory.get(i).getFactoryname());
			obj.put("code", factory.get(i).getFactoryaddress());

			mainarray.add(obj);

		}

		objmain.put("result", mainarray);

		return objmain;

	}


	//Courier Create

	@RequestMapping(value = "/courier_create",method=RequestMethod.GET)
	public ModelAndView courier_create(ModelMap map,HttpSession session) {

		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");
		
		ModelAndView view = new ModelAndView("register/courier_create");
		
		map.addAttribute("userId",userId);
		map.addAttribute("userName",userName);

		return view; //JSP - /WEB-INF/view/index.jsp
	}


	@ResponseBody
	@RequestMapping(value = "/max_courierId",method=RequestMethod.POST)
	public String max_courierId( ) {
		System.out.println(" max courier Id ");

		String maxId=registerService.maxCourierId();


		return maxId;

	}


	@ResponseBody
	@RequestMapping(value = "/insertCourier",method=RequestMethod.POST)
	public boolean insertCourier(CourierModel courier) {
		System.out.println(" insert factory  ");


		boolean insertSupplier=registerService.insertCourier(courier);

		if (insertSupplier) {
			return true;
		}
		return insertSupplier;
	}


	@ResponseBody
	@RequestMapping(value = "/courierList/{value}",method=RequestMethod.GET)
	public JSONArray courierList(@PathVariable ("value") String value) {
		System.out.println(" Buyer list ");

		JSONArray array=new JSONArray();

		List<String> couriers=registerService.courierList(value);

		for (int i = 0; i < couriers.size(); i++) {
			array.add(couriers.get(i));
		}

		return array;		
	}


	@ResponseBody
	@RequestMapping(value = "/courierdetails/{value}",method=RequestMethod.POST)
	public JSONArray courierdetails(@PathVariable ("value") String value) {
		System.out.println(" Countrylist ");

		JSONArray array=new JSONArray();

		List<CourierModel> factorydetails=registerService.CourierDetails(value);

		for (int i = 0; i < factorydetails.size(); i++) {
			array.add(factorydetails.get(i).getCourierid());
			array.add(factorydetails.get(i).getCouriername());
			array.add(factorydetails.get(i).getCouriercode());
			array.add(factorydetails.get(i).getCourierAddress());
			array.add(factorydetails.get(i).getConsigneeAddress());
			array.add(factorydetails.get(i).getNotifyAddress());
			array.add(factorydetails.get(i).getCountry());
			array.add(factorydetails.get(i).getTelephone());
			array.add(factorydetails.get(i).getMobile());

			array.add(factorydetails.get(i).getFax());
			array.add(factorydetails.get(i).getEmail());
			array.add(factorydetails.get(i).getSkypeid());

			array.add(factorydetails.get(i).getBankname());
			array.add(factorydetails.get(i).getBankaddress());
			array.add(factorydetails.get(i).getSwiftcode());
			array.add(factorydetails.get(i).getBankcountry());



		}
		System.out.println(" array  "+array);

		return array;

	}


	@ResponseBody
	@RequestMapping(value = "/editCourier",method=RequestMethod.POST)
	public boolean editCourier(CourierModel courier) {
		System.out.println(" edit editsupplier  ");


		boolean editbuyer=registerService.editCourier(courier);


		if (editbuyer) {

			editbuyer=true;
			return editbuyer;
		}



		return editbuyer;

	}

	@ResponseBody
	@RequestMapping(value = "/getCouriers",method=RequestMethod.POST)
	public JSONObject getCouriers() {
		System.out.println(" all courier list ");

		JSONObject objmain = new JSONObject();
		JSONArray mainarray = new JSONArray();

		List<CourierModel> factory=registerService.getAllCouriers();

		for (int i = 0; i < factory.size(); i++) {
			JSONObject obj=new JSONObject();

			obj.put("id", factory.get(i).getCourierid());
			obj.put("name", factory.get(i).getCouriername());
			obj.put("code", factory.get(i).getCourierAddress());

			mainarray.add(obj);

		}

		objmain.put("result", mainarray);

		return objmain;

	}

	//Brand Create 
	@RequestMapping(value = "/brand_create",method=RequestMethod.GET)
	public ModelAndView brand_create(ModelMap map,HttpSession session) {

		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");
		
		ModelAndView view = new ModelAndView("register/brand-create");
		List<Brand> brandList = registerService.getBrandList();
		System.out.println("list size="+brandList.size());

		map.addAttribute("brandList",brandList);
		map.addAttribute("userId",userId);
		map.addAttribute("userName",userName);

		return view; //JSP - /WEB-INF/view/index.jsp
	}


	@RequestMapping(value = "/saveBrand",method=RequestMethod.POST)
	public @ResponseBody JSONObject saveBrand(Brand brand) {
		JSONObject objmain = new JSONObject();
		if(!registerService.isBrandExist(brand)) {
			if(registerService.saveBrand(brand)) {

				JSONArray mainarray = new JSONArray();

				List<Brand> brandList= registerService.getBrandList();

				for(int a=0;a<brandList.size();a++) {
					JSONObject obj = new JSONObject();
					obj.put("brandId", brandList.get(a).getBrandId());
					obj.put("brandName", brandList.get(a).getBrandName());
					obj.put("brandCode", brandList.get(a).getBrandCode());

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

	@RequestMapping(value = "/editBrand",method=RequestMethod.POST)
	public @ResponseBody JSONObject editBrand(Brand brand) {
		JSONObject objmain = new JSONObject();
		if(!registerService.isBrandExist(brand)) {
			if(registerService.editBrand(brand)) {

				JSONArray mainarray = new JSONArray();

				List<Brand> brandList= registerService.getBrandList();

				for(int a=0;a<brandList.size();a++) {
					JSONObject obj = new JSONObject();
					obj.put("brandId", brandList.get(a).getBrandId());
					obj.put("brandName", brandList.get(a).getBrandName());
					obj.put("brandCode", brandList.get(a).getBrandCode());

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


	//Fabrics Create
	@RequestMapping(value = "/fabrics_create",method=RequestMethod.GET)
	public ModelAndView fabrics_create(ModelMap map,HttpSession session) {

		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");
		ModelAndView view = new ModelAndView("register/fabrics-create");
		List<FabricsItem> fabricsItemList = registerService.getFabricsItemList();
		List<Unit> unitList = registerService.getUnitList();

		map.addAttribute("fabricsItemList",fabricsItemList);
		view.addObject("unitList",unitList);
		
		map.addAttribute("userId",userId);
		map.addAttribute("userName",userName);

		return view; //JSP - /WEB-INF/view/index.jsp
	}

	@RequestMapping(value = "/saveFabricsItem",method=RequestMethod.POST)
	public @ResponseBody JSONObject saveFabricsItem(FabricsItem fabricsItem) {
		JSONObject objmain = new JSONObject();
		if(!registerService.isFabricsItemExist(fabricsItem)) {
			if(registerService.saveFabricsItem(fabricsItem)) {

				JSONArray mainarray = new JSONArray();

				List<FabricsItem> fabricsItemList= registerService.getFabricsItemList();

				for(int a=0;a<fabricsItemList.size();a++) {
					JSONObject obj = new JSONObject();
					obj.put("fabricsItemId", fabricsItemList.get(a).getFabricsItemId());
					obj.put("fabricsItemName", fabricsItemList.get(a).getFabricsItemName());
					obj.put("reference", fabricsItemList.get(a).getReference());

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

	@RequestMapping(value = "/getFabricsItem",method=RequestMethod.POST)
	public @ResponseBody JSONObject getFabricsItem(String fabricsItemId) {
		JSONObject objmain = new JSONObject();
		FabricsItem fabricsItem= registerService.getFabricsItem(fabricsItemId);
		objmain.put("result", fabricsItem);
		return objmain;
	}

	@RequestMapping(value = "/editFabricsItem",method=RequestMethod.POST)
	public @ResponseBody JSONObject editFabricsItem(FabricsItem fabricsItem) {
		JSONObject objmain = new JSONObject();
		if(!registerService.isFabricsItemExist(fabricsItem)) {
			if(registerService.editFabricsItem(fabricsItem)) {

				JSONArray mainarray = new JSONArray();

				List<FabricsItem> fabricsItemList= registerService.getFabricsItemList();

				for(int a=0;a<fabricsItemList.size();a++) {
					JSONObject obj = new JSONObject();
					obj.put("fabricsItemId", fabricsItemList.get(a).getFabricsItemId());
					obj.put("fabricsItemName", fabricsItemList.get(a).getFabricsItemName());
					obj.put("reference", fabricsItemList.get(a).getReference());

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
	
	@RequestMapping(value = "/addItemUnits",method=RequestMethod.POST)
	public @ResponseBody JSONObject addItemUnits(Unit unit,String itemId,String itemType) {
		JSONObject objmain = new JSONObject();
		
		if(registerService.addItemUnits(unit,itemId,itemType)) {
			List<Unit> unitList = registerService.getItemUnitsList(itemId,itemType);
			objmain.put("result", unitList);
		}else {
			objmain.put("result", "Something Wrong");
		}

		return objmain;
	}


	//Accessories Create 
	@RequestMapping(value = "/accessories_create",method=RequestMethod.GET)
	public ModelAndView accessories_create(ModelMap map,HttpSession session) {

		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");
		
		ModelAndView view = new ModelAndView("register/accessories-create");
		List<AccessoriesItem> accessoriesItemList = registerService.getAccessoriesItemList();
		List<Unit> unitList = registerService.getUnitList();

		map.addAttribute("accessoriesItemList",accessoriesItemList);
		view.addObject("unitList",unitList);
		map.addAttribute("userId",userId);
		map.addAttribute("userName",userName);



		return view; //JSP - /WEB-INF/view/index.jsp
	}


	@RequestMapping(value = "/saveAccessoriesItem",method=RequestMethod.POST)
	public @ResponseBody JSONObject saveAccessoriesItem(AccessoriesItem accessoriesItem) {
		JSONObject objmain = new JSONObject();
		if(!registerService.isAccessoriesItemExist(accessoriesItem)) {
			if(registerService.saveAccessoriesItem(accessoriesItem)) {

				JSONArray mainarray = new JSONArray();

				List<AccessoriesItem> accessoriesItemList= registerService.getAccessoriesItemList();

				for(int a=0;a<accessoriesItemList.size();a++) {
					JSONObject obj = new JSONObject();
					obj.put("accessoriesItemId", accessoriesItemList.get(a).getAccessoriesItemId());
					obj.put("accessoriesItemName", accessoriesItemList.get(a).getAccessoriesItemName());
					obj.put("accessoriesItemCode", accessoriesItemList.get(a).getAccessoriesItemCode());

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
	
	@RequestMapping(value = "/getAccessoriesItem",method=RequestMethod.POST)
	public @ResponseBody JSONObject getAccessoriesItem(String accessoriesItemId) {
		JSONObject objmain = new JSONObject();
		AccessoriesItem accessoriesItem= registerService.getAccessoriesItem(accessoriesItemId);
		objmain.put("result", accessoriesItem);
		return objmain;
	}

	@RequestMapping(value = "/editAccessoriesItem",method=RequestMethod.POST)
	public @ResponseBody JSONObject editAccessoriesItem(AccessoriesItem accessoriesItem) {
		JSONObject objmain = new JSONObject();
		if(!registerService.isAccessoriesItemExist(accessoriesItem)) {
			if(registerService.editAccessoriesItem(accessoriesItem)) {

				JSONArray mainarray = new JSONArray();

				List<AccessoriesItem> accessoriesItemList= registerService.getAccessoriesItemList();

				for(int a=0;a<accessoriesItemList.size();a++) {
					JSONObject obj = new JSONObject();
					obj.put("accessoriesItemId", accessoriesItemList.get(a).getAccessoriesItemId());
					obj.put("accessoriesItemName", accessoriesItemList.get(a).getAccessoriesItemName());
					obj.put("accessoriesItemCode", accessoriesItemList.get(a).getAccessoriesItemCode());

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

	//Local Item Create 
	@RequestMapping(value = "/local_item_create",method=RequestMethod.GET)
	public ModelAndView local_item_create(ModelMap map,HttpSession session) {

		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");
		ModelAndView view = new ModelAndView("register/local-item-create");
		List<LocalItem> localItemList = registerService.getLocalItemList();

		map.addAttribute("localItemList",localItemList);
		map.addAttribute("userId",userId);
		map.addAttribute("userName",userName);

		return view; //JSP - /WEB-INF/view/index.jsp
	}


	@RequestMapping(value = "/saveLocalItem",method=RequestMethod.POST)
	public @ResponseBody JSONObject saveLocalItem(LocalItem localItem) {
		JSONObject objmain = new JSONObject();
		if(!registerService.isLocalItemExist(localItem)) {
			if(registerService.saveLocalItem(localItem)) {

				JSONArray mainarray = new JSONArray();

				List<LocalItem> localItemList= registerService.getLocalItemList();

				for(int a=0;a<localItemList.size();a++) {
					JSONObject obj = new JSONObject();
					obj.put("localItemId", localItemList.get(a).getLocalItemId());
					obj.put("localItemName", localItemList.get(a).getLocalItemName());
					obj.put("localItemCode", localItemList.get(a).getLocalItemCode());

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

	@RequestMapping(value = "/editLocalItem",method=RequestMethod.POST)
	public @ResponseBody JSONObject editLocalItem(LocalItem localItem) {
		JSONObject objmain = new JSONObject();
		if(!registerService.isLocalItemExist(localItem)) {
			if(registerService.editLocalItem(localItem)) {

				JSONArray mainarray = new JSONArray();

				List<LocalItem> localItemList= registerService.getLocalItemList();

				for(int a=0;a<localItemList.size();a++) {
					JSONObject obj = new JSONObject();
					obj.put("localItemId", localItemList.get(a).getLocalItemId());
					obj.put("localItemName", localItemList.get(a).getLocalItemName());
					obj.put("localItemCode", localItemList.get(a).getLocalItemCode());

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

	//Style Item Create 
	@RequestMapping(value = "/item_description_create",method=RequestMethod.GET)
	public ModelAndView item_description_create(ModelMap map,HttpSession session) {

		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");
		
		ModelAndView view = new ModelAndView("register/style-item-create");
		List<StyleItem> styleItemList = registerService.getStyleItemList();
		System.out.println("list size="+styleItemList.size());

		map.addAttribute("styleItemList",styleItemList);
		map.addAttribute("userId",userId);
		map.addAttribute("userName",userName);
		return view; //JSP - /WEB-INF/view/index.jsp
	}


	@RequestMapping(value = "/saveStyleItem",method=RequestMethod.POST)
	public @ResponseBody JSONObject saveStyleItem(StyleItem styleItem) {
		JSONObject objmain = new JSONObject();
		if(!registerService.isStyleItemExist(styleItem)) {
			if(registerService.saveStyleItem(styleItem)) {

				JSONArray mainarray = new JSONArray();

				List<StyleItem> styleItemList= registerService.getStyleItemList();

				for(int a=0;a<styleItemList.size();a++) {
					JSONObject obj = new JSONObject();
					obj.put("styleItemId", styleItemList.get(a).getStyleItemId());
					obj.put("styleItemName", styleItemList.get(a).getStyleItemName());
					obj.put("styleItemCode", styleItemList.get(a).getStyleItemCode());

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

	@RequestMapping(value = "/editStyleItem",method=RequestMethod.POST)
	public @ResponseBody JSONObject editStyleItem(StyleItem styleItem) {
		JSONObject objmain = new JSONObject();
		if(!registerService.isStyleItemExist(styleItem)) {
			if(registerService.editStyleItem(styleItem)) {

				JSONArray mainarray = new JSONArray();

				List<StyleItem> styleItemList= registerService.getStyleItemList();

				for(int a=0;a<styleItemList.size();a++) {
					JSONObject obj = new JSONObject();
					obj.put("styleItemId", styleItemList.get(a).getStyleItemId());
					obj.put("styleItemName", styleItemList.get(a).getStyleItemName());
					obj.put("styleItemCode", styleItemList.get(a).getStyleItemCode());

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


	//Unit Create 
	@RequestMapping(value = "/unit_create",method=RequestMethod.GET)
	public ModelAndView unit_create(ModelMap map,HttpSession session) {

		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");
		ModelAndView view = new ModelAndView("register/unit-create");
		List<Unit> unitList = registerService.getUnitList();

		map.addAttribute("unitList",unitList);
		map.addAttribute("userId",userId);
		map.addAttribute("userName",userName);

		return view; //JSP - /WEB-INF/view/index.jsp
	}


	@RequestMapping(value = "/saveUnit",method=RequestMethod.POST)
	public @ResponseBody JSONObject saveUnit(Unit unit) {
		JSONObject objmain = new JSONObject();
		if(!registerService.isUnitExist(unit)) {
			if(registerService.saveUnit(unit)) {

				JSONArray mainarray = new JSONArray();

				List<Unit> unitList= registerService.getUnitList();

				for(int a=0;a<unitList.size();a++) {
					JSONObject obj = new JSONObject();
					obj.put("unitId", unitList.get(a).getUnitId());
					obj.put("unitName", unitList.get(a).getUnitName());
					obj.put("unitValue", unitList.get(a).getUnitValue());

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

	@RequestMapping(value = "/editUnit",method=RequestMethod.POST)
	public @ResponseBody JSONObject editUnit(Unit unit) {
		JSONObject objmain = new JSONObject();
		if(!registerService.isUnitExist(unit)) {
			if(registerService.editUnit(unit)) {

				JSONArray mainarray = new JSONArray();

				List<Unit> unitList= registerService.getUnitList();

				for(int a=0;a<unitList.size();a++) {
					JSONObject obj = new JSONObject();
					obj.put("unitId", unitList.get(a).getUnitId());
					obj.put("unitName", unitList.get(a).getUnitName());
					obj.put("unitValue", unitList.get(a).getUnitValue());

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
	
	@RequestMapping(value = "/getUnitList",method=RequestMethod.GET)
	public @ResponseBody JSONObject getUnitList() {
		JSONObject objmain = new JSONObject();
		List<Unit> unitList= registerService.getUnitList();
		objmain.put("unitList", unitList);
		return objmain;
	}

	//Color Create 
	@RequestMapping(value = "/color_create",method=RequestMethod.GET)
	public ModelAndView color_create(ModelMap map,HttpSession session) {

		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");
		
		ModelAndView view = new ModelAndView("register/color-create");
		List<Color> colorList = registerService.getColorList();

		map.addAttribute("colorList",colorList);
		map.addAttribute("userId",userId);
		map.addAttribute("userName",userName);

		return view; //JSP - /WEB-INF/view/index.jsp
	}


	@RequestMapping(value = "/saveColor",method=RequestMethod.POST)
	public @ResponseBody JSONObject saveColor(Color color) {
		JSONObject objmain = new JSONObject();
		if(!registerService.isColorExist(color)) {
			if(registerService.saveColor(color)) {

				JSONArray mainarray = new JSONArray();

				List<Color> colorList= registerService.getColorList();

				for(int a=0;a<colorList.size();a++) {
					JSONObject obj = new JSONObject();
					obj.put("colorId", colorList.get(a).getColorId());
					obj.put("colorName", colorList.get(a).getColorName());
					obj.put("colorCode", colorList.get(a).getColorCode());

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

	@RequestMapping(value = "/editColor",method=RequestMethod.POST)
	public @ResponseBody JSONObject editColor(Color color) {
		JSONObject objmain = new JSONObject();
		if(!registerService.isColorExist(color)) {
			if(registerService.editColor(color)) {

				JSONArray mainarray = new JSONArray();

				List<Color> colorList= registerService.getColorList();

				for(int a=0;a<colorList.size();a++) {
					JSONObject obj = new JSONObject();
					obj.put("colorId", colorList.get(a).getColorId());
					obj.put("colorName", colorList.get(a).getColorName());
					obj.put("colorCode", colorList.get(a).getColorCode());

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

	//Costing Create 
	@RequestMapping(value = "/costing_details_create",method=RequestMethod.GET)
	public ModelAndView costing_details_create(ModelMap map,HttpSession session) {

		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");
		ModelAndView view = new ModelAndView("register/particular-item-create");
		List<ParticularItem> particularItemList = registerService.getParticularItemList();

		map.addAttribute("userId",userId);
		map.addAttribute("userName",userName);
		map.addAttribute("particularItemList",particularItemList);

		return view; //JSP - /WEB-INF/view/index.jsp
	}


	@RequestMapping(value = "/saveParticularItem",method=RequestMethod.POST)
	public @ResponseBody JSONObject saveParticularItem(ParticularItem particularItem) {
		JSONObject objmain = new JSONObject();
		if(!registerService.isParticularItemExist(particularItem)) {
			if(registerService.saveParticularItem(particularItem)) {

				JSONArray mainarray = new JSONArray();

				List<ParticularItem> particularItemList= registerService.getParticularItemList();

				for(int a=0;a<particularItemList.size();a++) {
					JSONObject obj = new JSONObject();
					obj.put("particularItemId", particularItemList.get(a).getParticularItemId());
					obj.put("particularItemName", particularItemList.get(a).getParticularItemName());


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


	@RequestMapping(value = "/editParticularItem",method=RequestMethod.POST)
	public @ResponseBody JSONObject editParticularItem(ParticularItem particularItem) {
		JSONObject objmain = new JSONObject();
		if(!registerService.isParticularItemExist(particularItem)) {
			if(registerService.editParticularItem(particularItem)) {

				JSONArray mainarray = new JSONArray();

				List<ParticularItem> particularItemList= registerService.getParticularItemList();

				for(int a=0;a<particularItemList.size();a++) {
					JSONObject obj = new JSONObject();
					obj.put("particularItemId", particularItemList.get(a).getParticularItemId());
					obj.put("particularItemName", particularItemList.get(a).getParticularItemName());


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

	//Country Create 
	@RequestMapping(value = "/country_create",method=RequestMethod.GET)
	public ModelAndView country_create(ModelMap map,HttpSession session) {

		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");
		ModelAndView view = new ModelAndView("register/country-create");
		List<Country> countryList = registerService.getCountryList();

		map.addAttribute("countryList",countryList);
		map.addAttribute("userId",userId);
		map.addAttribute("userName",userName);

		return view; //JSP - /WEB-INF/view/index.jsp
	}


	@RequestMapping(value = "/saveCountry",method=RequestMethod.POST)
	public @ResponseBody JSONObject saveCountry(Country country) {
		JSONObject objmain = new JSONObject();
		if(!registerService.isCountryExist(country)) {
			if(registerService.saveCountry(country)) {

				JSONArray mainarray = new JSONArray();

				List<Country> countryList= registerService.getCountryList();

				for(int a=0;a<countryList.size();a++) {
					JSONObject obj = new JSONObject();
					obj.put("countryId", countryList.get(a).getCountryId());
					obj.put("countryName", countryList.get(a).getCountryName());


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

	@RequestMapping(value = "/editCountry",method=RequestMethod.POST)
	public @ResponseBody JSONObject editCountry(Country country) {
		JSONObject objmain = new JSONObject();
		if(!registerService.isCountryExist(country)) {
			if(registerService.editCountry(country)) {

				JSONArray mainarray = new JSONArray();

				List<Country> countryList= registerService.getCountryList();

				for(int a=0;a<countryList.size();a++) {
					JSONObject obj = new JSONObject();
					obj.put("countryId", countryList.get(a).getCountryId());
					obj.put("countryName", countryList.get(a).getCountryName());


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

	//Country Create 
	@RequestMapping(value = "/sample_type_create",method=RequestMethod.GET)
	public ModelAndView sample_type_create(ModelMap map,HttpSession session) {

		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");
		ModelAndView view = new ModelAndView("register/sample-type-create");
		List<SampleType> sampleTypeList = registerService.getSampleTypeList();

		map.addAttribute("sampleTypeList",sampleTypeList);
		map.addAttribute("userId",userId);
		map.addAttribute("userName",userName);

		return view; //JSP - /WEB-INF/view/index.jsp
	}


	@RequestMapping(value = "/saveSampleType",method=RequestMethod.POST)
	public @ResponseBody JSONObject saveSampleType(SampleType sampleType) {
		JSONObject objmain = new JSONObject();
		if(!registerService.isSampleTypeExist(sampleType)) {
			if(registerService.saveSampleType(sampleType)) {

				JSONArray mainarray = new JSONArray();

				List<SampleType> sampleTypeList= registerService.getSampleTypeList();

				for(int a=0;a<sampleTypeList.size();a++) {
					JSONObject obj = new JSONObject();
					obj.put("sampleTypeId", sampleTypeList.get(a).getSampleTypeId());
					obj.put("sampleTypeName", sampleTypeList.get(a).getSampleTypeName());


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

	@RequestMapping(value = "/editSampleType",method=RequestMethod.POST)
	public @ResponseBody JSONObject editSampleType(SampleType sampleType) {
		JSONObject objmain = new JSONObject();
		if(!registerService.isSampleTypeExist(sampleType)) {
			if(registerService.editSampleType(sampleType)) {

				JSONArray mainarray = new JSONArray();

				List<SampleType> sampleTypeList= registerService.getSampleTypeList();

				for(int a=0;a<sampleTypeList.size();a++) {
					JSONObject obj = new JSONObject();
					obj.put("sampleTypeId", sampleTypeList.get(a).getSampleTypeId());
					obj.put("sampleTypeName", sampleTypeList.get(a).getSampleTypeName());


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

	//Brand Create 
	@RequestMapping(value = "/department_create",method=RequestMethod.GET)
	public ModelAndView department_create(ModelMap map,HttpSession session) {

		List<Factory> factorylist = registerService.getFactoryNameList();
		ModelAndView view = new ModelAndView("register/department-create");
		List<Department> departmentList = registerService.getDepartmentList();


		map.addAttribute("factorylist",factorylist);
		map.addAttribute("departmentList",departmentList);

		return view; //JSP - /WEB-INF/view/index.jsp
	}

	@RequestMapping(value = "/editDepartment",method=RequestMethod.POST)
	public @ResponseBody JSONObject editDepartment(Department department) {
		JSONObject objmain = new JSONObject();
		if(!registerService.isDepartmentExist(department)) {
			if(registerService.editDepartment(department)) {

				JSONArray mainarray = new JSONArray();

				List<Department> departmentList= registerService.getDepartmentList();

				for(int a=0;a<departmentList.size();a++) {
					JSONObject obj = new JSONObject();
					obj.put("departmentId", departmentList.get(a).getDepartmentId());
					obj.put("factoryId", departmentList.get(a).getFactoryId());
					obj.put("factoryName", departmentList.get(a).getFactoryName());
					obj.put("departmentName", departmentList.get(a).getDepartmentName());

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

	@RequestMapping(value = "/saveDepartment",method=RequestMethod.POST)
	public @ResponseBody JSONObject saveDepartment(Department department) {
		JSONObject objmain = new JSONObject();
		if(!registerService.isDepartmentExist(department)) {
			if(registerService.saveDepartment(department)) {

				JSONArray mainarray = new JSONArray();

				List<Department> departmentList= registerService.getDepartmentList();

				for(int a=0;a<departmentList.size();a++) {
					JSONObject obj = new JSONObject();
					obj.put("departmentId", departmentList.get(a).getDepartmentId());
					obj.put("factoryId", departmentList.get(a).getFactoryId());
					obj.put("factoryName", departmentList.get(a).getFactoryName());
					obj.put("departmentName", departmentList.get(a).getDepartmentName());

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

	//Merchandiser Create 
	@RequestMapping(value = "/merchandiser_create",method=RequestMethod.GET)
	public ModelAndView merchandiser_create(ModelMap map,HttpSession session) {

		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");
		
		ModelAndView view = new ModelAndView("register/merchandiser_create");
		List<MerchandiserInfo> merchandiserList= registerService.getMerchandiserList();

		map.addAttribute("userId",userId);
		map.addAttribute("userName",userName);
		map.addAttribute("merchandiserList",merchandiserList);

		return view; //JSP - /WEB-INF/view/index.jsp
	}

	@RequestMapping(value = "/saveMerchandiser",method=RequestMethod.POST)
	public @ResponseBody JSONObject saveMerchandiser(MerchandiserInfo v) {
		JSONObject objmain = new JSONObject();
		if(!registerService.isMerchandiserExist(v)) {
			if(registerService.saveMerchandiser(v)) {

				JSONArray mainarray = new JSONArray();

				List<MerchandiserInfo> List= registerService.getMerchandiserList();

				for(int a=0;a<List.size();a++) {
					JSONObject obj = new JSONObject();
					obj.put("MerchendiserId", List.get(a).getMerchendiserId());
					obj.put("Name", List.get(a).getName());
					obj.put("Telephone", List.get(a).getTelephone());

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

	@RequestMapping(value = "/editMerchandiser",method=RequestMethod.POST)
	public @ResponseBody JSONObject editMerchandiser(MerchandiserInfo v) {
		JSONObject objmain = new JSONObject();
		if(!registerService.isMerchandiserExist(v)) {
			if(registerService.editMerchandiser(v)) {

				JSONArray mainarray = new JSONArray();

				List<MerchandiserInfo> List= registerService.getMerchandiserList();

				for(int a=0;a<List.size();a++) {
					JSONObject obj = new JSONObject();
					obj.put("MerchendiserId", List.get(a).getMerchendiserId());
					obj.put("Name", List.get(a).getName());
					obj.put("Telephone", List.get(a).getTelephone());

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

	//Merchandiser Create 
	@RequestMapping(value = "/incharge_create",method=RequestMethod.GET)
	public ModelAndView incharge_create(ModelMap map,HttpSession session) {

		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");
		
		ModelAndView view = new ModelAndView("register/incharge_create");
		List<FactoryModel> factoryList= registerService.getAllFactories();

		List<InchargeInfo> inchargeList= registerService.getInchargeList();
		map.addAttribute("inchargeList",inchargeList);

		map.addAttribute("factoryList",factoryList);
		
		map.addAttribute("userId",userId);
		map.addAttribute("userName",userName);

		return view; //JSP - /WEB-INF/view/index.jsp
	}

	@RequestMapping(value = "/saveIncharge",method=RequestMethod.POST)
	public @ResponseBody JSONObject saveIncharge(InchargeInfo v) {
		JSONObject objmain = new JSONObject();
		if(!registerService.isInchargeExist(v)) {
			if(registerService.saveIncharge(v)) {

				

				List<InchargeInfo> List= registerService.getInchargeList();

				
				objmain.put("result", List);

			}else {
				objmain.put("result", "Something Wrong");
			}	
		}else {
			objmain.put("result", "duplicate");
		}

		return objmain;
	}

	@RequestMapping(value = "/editIncharge",method=RequestMethod.POST)
	public @ResponseBody JSONObject editIncharge(InchargeInfo v) {
		JSONObject objmain = new JSONObject();
		if(!registerService.isInchargeExist(v)) {
			if(registerService.editIncharge(v)) {

				

				List<InchargeInfo> List= registerService.getInchargeList();

				objmain.put("result", List);

			}else {
				objmain.put("result", "Something Wrong");
			}	
		}else {
			objmain.put("result", "duplicate");
		}

		return objmain;
	}
	
	//Line Create 
	@RequestMapping(value = "/line_create",method=RequestMethod.GET)
	public ModelAndView line_create(ModelMap map,HttpSession session) {

		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");
		
		ModelAndView view = new ModelAndView("register/line-create");
		List<Line> lineList = registerService.getLineList();
		List<FactoryModel> factoryList = registerService.getAllFactories();

		map.addAttribute("userId",userId);
		map.addAttribute("userName",userName);
		
		map.addAttribute("lineList",lineList);
		view.addObject("factoryList",factoryList);
		return view; //JSP - /WEB-INF/view/index.jsp
	}

	@ResponseBody
	@RequestMapping(value = "/departmentLoadByFactory",method=RequestMethod.GET)
	public JSONObject departmentLoadByFactory() {

		JSONObject objmain = new JSONObject();	
		JSONObject department = new JSONObject() ;
		JSONArray departmentArray = new JSONArray();
		JSONObject factoryObj = new JSONObject();

		List<Department> departmentList = registerService.getDepartmentList();
		int lastSize= departmentList.size();

		for(int i=0;i<lastSize;i++) {

			department.put("departmentId", departmentList.get(i).getDepartmentId());
			department.put("departmentName", departmentList.get(i).getDepartmentName());
			departmentArray.add(department);
			if((i+1 == lastSize) || !departmentList.get(i).getFactoryId().equals( departmentList.get(i+1).getFactoryId())) {

				factoryObj.put("factId"+departmentList.get(i).getFactoryId(), departmentArray);
				departmentArray = new JSONArray();
			}
			department = new JSONObject();

		}
		objmain.put("departmentList", factoryObj);
		return objmain;

	}

	@RequestMapping(value = "/saveLine",method=RequestMethod.POST)
	public @ResponseBody JSONObject saveLine(Line line) {
		JSONObject objmain = new JSONObject();
		if(!registerService.isLineExist(line)) {
			if(registerService.saveLine(line)) {

				JSONArray mainarray = new JSONArray();

				List<Line> lineList= registerService.getLineList();

				for(int a=0;a<lineList.size();a++) {
					JSONObject obj = new JSONObject();
					obj.put("lineId", lineList.get(a).getLineId());
					obj.put("factoryId", lineList.get(a).getFactoryId());
					obj.put("factoryName", lineList.get(a).getFactoryName());
					obj.put("departmentId", lineList.get(a).getDepartmentId());
					obj.put("departmentName", lineList.get(a).getDepartmentName());
					obj.put("lineName", lineList.get(a).getLineName());

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

	@RequestMapping(value = "/editLine",method=RequestMethod.POST)
	public @ResponseBody JSONObject editLine(Line line) {
		JSONObject objmain = new JSONObject();
		if(!registerService.isLineExist(line)) {
			if(registerService.editLine(line)) {

				JSONArray mainarray = new JSONArray();

				List<Line> lineList= registerService.getLineList();

				for(int a=0;a<lineList.size();a++) {
					JSONObject obj = new JSONObject();
					obj.put("lineId", lineList.get(a).getLineId());
					obj.put("factoryId", lineList.get(a).getFactoryId());
					obj.put("factoryName", lineList.get(a).getFactoryName());
					obj.put("departmentId", lineList.get(a).getDepartmentId());
					obj.put("departmentName", lineList.get(a).getDepartmentName());
					obj.put("lineName", lineList.get(a).getLineName());

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

	//Style Size Create

	@RequestMapping(value = "/style_size_create",method=RequestMethod.GET)
	public ModelAndView style_size_create(ModelMap map,HttpSession session) {

		
		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userName");
	
		
		ModelAndView view = new ModelAndView("register/style-size-create");
		List<Size> sizeList = registerService.getStyleSizeList();
		view.addObject("sizeList",sizeList);
		
		map.addAttribute("userId",userId);
		map.addAttribute("userName",userName);
		
		return view; //JSP - /WEB-INF/view/index.jsp
	}

	@RequestMapping(value = "/saveSize",method=RequestMethod.POST)
	public @ResponseBody JSONObject saveSize(Size size) {
		JSONObject objmain = new JSONObject();
		if(!registerService.isStyleSizeExist(size)) {
			if(registerService.saveStyleSize(size)) {

				JSONArray mainarray = new JSONArray();

				List<Size> sizeList= registerService.getStyleSizeList();

				for(int a=0;a<sizeList.size();a++) {
					JSONObject obj = new JSONObject();
					obj.put("sizeId", sizeList.get(a).getSizeId());
					obj.put("groupId", sizeList.get(a).getGroupId());
					obj.put("groupName", sizeList.get(a).getGroupName());
					obj.put("sizeName", sizeList.get(a).getSizeName());
					obj.put("sorting", sizeList.get(a).getSizeSorting());
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

	@RequestMapping(value = "/editSize",method=RequestMethod.POST)
	public @ResponseBody JSONObject editSize(Size size) {
		JSONObject objmain = new JSONObject();
		if(!registerService.isStyleSizeExist(size)) {
			if(registerService.editStyleSize(size)) {

				JSONArray mainarray = new JSONArray();

				List<Size> sizeList= registerService.getStyleSizeList();

				for(int a=0;a<sizeList.size();a++) {
					JSONObject obj = new JSONObject();
					obj.put("sizeId", sizeList.get(a).getSizeId());
					obj.put("groupId", sizeList.get(a).getGroupId());
					obj.put("groupName", sizeList.get(a).getGroupName());
					obj.put("sizeName", sizeList.get(a).getSizeName());
					obj.put("sorting", sizeList.get(a).getSizeSorting());
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


	@RequestMapping(value = "/saveSizeGroup",method=RequestMethod.POST)
	public @ResponseBody JSONObject saveSizeGroup(SizeGroup sizeGroup) {
		JSONObject objmain = new JSONObject();
		if(!registerService.isStyleSizeGroupExist(sizeGroup)) {
			if(registerService.saveStyleSizeGroup(sizeGroup)) {

				JSONArray mainarray = new JSONArray();

				List<SizeGroup> sizeGroupList= registerService.getStyleSizeGroupList();

				for(int a=0;a<sizeGroupList.size();a++) {
					JSONObject obj = new JSONObject();
					obj.put("groupId", sizeGroupList.get(a).getGroupId());
					obj.put("groupName", sizeGroupList.get(a).getGroupName());
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

	@RequestMapping(value = "/editSizeGroup",method=RequestMethod.POST)
	public @ResponseBody JSONObject editSizeGroup(SizeGroup sizeGroup) {
		JSONObject objmain = new JSONObject();
		if(!registerService.isStyleSizeGroupExist(sizeGroup)) {
			if(registerService.editStyleSizeGroup(sizeGroup)) {

				JSONArray mainarray = new JSONArray();

				List<SizeGroup> sizeGroupList= registerService.getStyleSizeGroupList();

				for(int a=0;a<sizeGroupList.size();a++) {
					JSONObject obj = new JSONObject();
					obj.put("groupId", sizeGroupList.get(a).getGroupId());
					obj.put("groupName", sizeGroupList.get(a).getGroupName());
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

	@RequestMapping(value = "/sizeGroupLoad",method=RequestMethod.GET)
	public @ResponseBody JSONObject sizeGroupLoad(SizeGroup sizeGroup) {
		JSONObject objmain = new JSONObject();


		JSONArray mainarray = new JSONArray();

		List<SizeGroup> sizeGroupList= registerService.getStyleSizeGroupList();

		for(int a=0;a<sizeGroupList.size();a++) {
			JSONObject obj = new JSONObject();
			obj.put("id", sizeGroupList.get(a).getGroupId());
			obj.put("value", sizeGroupList.get(a).getGroupName());
			obj.put("groupId", sizeGroupList.get(a).getGroupId());
			obj.put("groupName", sizeGroupList.get(a).getGroupName());
			mainarray.add(obj);
		}


		objmain.put("result", mainarray);



		return objmain;
	}

	//Designation Create
	
		@RequestMapping(value = "/desgination_create",method=RequestMethod.GET)
		public ModelAndView desgination_create(ModelMap map,HttpSession session) {
			
			String userId=(String)session.getAttribute("userId");
			String userName=(String)session.getAttribute("userName");
			
			List<Department> departmentList = registerService.getDepartmentList();
			ModelAndView view = new ModelAndView("register/desgination_create");
			
			
			map.addAttribute("userId",userId);
			map.addAttribute("userName",userName);
			
			view.addObject("department",departmentList);
			return view; //JSP - /WEB-INF/view/index.jsp
		}
		
		@RequestMapping(value = "/allDesignation", method = RequestMethod.POST)
		public @ResponseBody JSONObject allDesignation() {

			List<Designation> designationList = registerService.getDesignationList();
			
			JSONObject mainobj = new JSONObject();
			JSONArray mainarray = new JSONArray();

			for (int i = 0; i < designationList.size(); i++) {

				JSONObject obj = new JSONObject();
				obj.put("departmentId", designationList.get(i).getDepartmentId());
				obj.put("departmentName", designationList.get(i).getDepartmentName());
				obj.put("designationId", designationList.get(i).getDesignationId());
				obj.put("designationName", designationList.get(i).getDesignation());
				
				mainarray.add(obj);
			}
			mainobj.put("result", mainarray);

			return mainobj;
		}

		
		@RequestMapping(value = "/saveDesignation",method=RequestMethod.POST)
		public @ResponseBody JSONObject saveDesignation(Designation v) {
			
			System.out.println("Designation : "+v.getDesignation());
			
			JSONObject objmain = new JSONObject();
			if(!registerService.isDesignationExist(v)) {
				if(registerService.saveDesignation(v)) {

					JSONArray mainarray = new JSONArray();

					List<Designation> List= registerService.getDesignationList();

					for(int a=0;a<List.size();a++) {
						JSONObject obj = new JSONObject();
						obj.put("DesignationId", List.get(a).getDesignationId());
						obj.put("Designation", List.get(a).getDesignation());
						obj.put("DepartmentId", List.get(a).getDepartmentId());

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
		
		@RequestMapping(value = "/editDesignation",method=RequestMethod.POST)
		public @ResponseBody JSONObject editDesignation(Designation v) {
			
			System.out.println("Designation : "+v.getDesignation());
			
			JSONObject objmain = new JSONObject();
			if(registerService.isDesignationExist(v)) {
				if(registerService.editDesignation(v)) {

					JSONArray mainarray = new JSONArray();

					List<Designation> List= registerService.getDesignationList();

					for(int a=0;a<List.size();a++) {
						JSONObject obj = new JSONObject();
						obj.put("DesignationId", List.get(a).getDesignationId());
						obj.put("Designation", List.get(a).getDesignation());
						obj.put("DepartmentId", List.get(a).getDepartmentId());

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
		
		
		//Employee
		
		@RequestMapping(value = "/employee_create",method=RequestMethod.GET)
		public ModelAndView employee_create(ModelMap map,HttpSession session) {
			
			String userId=(String)session.getAttribute("userId");
			String userName=(String)session.getAttribute("userName");
			
			List<Line> lineList = registerService.getLineList();
			List<Designation> designationList = registerService.getDesignationList();
			List<Department> departmentList = registerService.getDepartmentList();
			ModelAndView view = new ModelAndView("register/employee_create");
			
			view.addObject("department",departmentList);
			view.addObject("designation",designationList);
			view.addObject("line",lineList);
			
			map.addAttribute("userId",userId);
			map.addAttribute("userName",userName);
			
			return view; //JSP - /WEB-INF/view/index.jsp
		}
		
		@RequestMapping(value = "/allEmployee", method = RequestMethod.POST)
		public @ResponseBody JSONObject allEmployee() {

			List<Employee> employeeList = registerService.getEmployeeList();

			JSONObject mainobj = new JSONObject();
			JSONArray mainarray = new JSONArray();

			for (int i = 0; i < employeeList.size(); i++) {

				JSONObject obj = new JSONObject();
				
				obj.put("EmployeeName", employeeList.get(i).getEmployeeName());
				obj.put("Department", employeeList.get(i).getDepartment());
				obj.put("Designation", employeeList.get(i).getDesignation());
				obj.put("DepartmentId", employeeList.get(i).getDepartmentId());
				obj.put("DesignationId", employeeList.get(i).getDesignationId());
				
				obj.put("CardNo", employeeList.get(i).getCardNo());
				obj.put("Line", employeeList.get(i).getLine());
				obj.put("Grade", employeeList.get(i).getGrade());
				obj.put("EmployeeCode", employeeList.get(i).getEmployeeCode());
				obj.put("JoinDate", employeeList.get(i).getJoinDate());
				
				mainarray.add(obj);
			}
			mainobj.put("result", mainarray);

			return mainobj;
		}
		
		@RequestMapping(value = "/saveEmployee",method=RequestMethod.POST)
		public @ResponseBody JSONObject saveEmployee(Employee v) {
			
			JSONObject objmain = new JSONObject();
			if(!registerService.isEmployeeExist(v)) {
				if(registerService.saveEmployee(v)) {

					JSONArray mainarray = new JSONArray();

					List<Employee> List= registerService.getEmployeeList();

					for(int a=0;a<List.size();a++) {
						
						JSONObject obj = new JSONObject();
						obj.put("EmployeeCode", List.get(a).getEmployeeCode());
						obj.put("EmployeeName", List.get(a).getEmployeeName());
						obj.put("CardNo", List.get(a).getCardNo());
						obj.put("Department", List.get(a).getDepartment());
						obj.put("Designation", List.get(a).getDesignation());
						obj.put("Line", List.get(a).getLine());
						obj.put("Grade", List.get(a).getGrade());
						obj.put("JoinDate", List.get(a).getJoinDate());
						
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
		
		@RequestMapping(value = "/editEmployee",method=RequestMethod.POST)
		public @ResponseBody JSONObject editEmployee(Employee v) {
			
			JSONObject objmain = new JSONObject();
			if(registerService.isEmployeeExist(v)) {
				if(registerService.editEmployee(v)) {

					JSONArray mainarray = new JSONArray();

					List<Employee> List= registerService.getEmployeeList();

					for(int a=0;a<List.size();a++) {
						
						JSONObject obj = new JSONObject();
						obj.put("EmployeeCode", List.get(a).getEmployeeCode());
						obj.put("EmployeeName", List.get(a).getEmployeeName());
						obj.put("CardNo", List.get(a).getCardNo());
						obj.put("Department", List.get(a).getDepartment());
						obj.put("Designation", List.get(a).getDesignation());
						obj.put("Line", List.get(a).getLine());
						obj.put("Grade", List.get(a).getGrade());
						obj.put("JoinDate", List.get(a).getJoinDate());
						
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
		
		//Machine Create
		
		@RequestMapping(value = "/machine_create",method=RequestMethod.GET)
		public ModelAndView machine_create(ModelMap map,HttpSession session) {
					
			String userId=(String)session.getAttribute("userId");
			String userName=(String)session.getAttribute("userName");
			List<Employee> employeeList = registerService.getEmployeeList();
			List<Factory> factorylist = registerService.getFactoryNameList();
			ModelAndView view = new ModelAndView("register/machine_create");
			
			view.addObject("employee",employeeList);
			view.addObject("factorylist",factorylist);
			
			map.addAttribute("userId",userId);
			map.addAttribute("userName",userName);
			
			return view; //JSP - /WEB-INF/view/index.jsp
		}
		
		@ResponseBody
		@RequestMapping(value = "/departmentWiseLine/{departmentId}",method=RequestMethod.GET)
		public JSONObject departmentWiseLine(@PathVariable ("departmentId") String departmentId) {
			JSONObject objMain = new JSONObject();	
			List<Line> lineList = registerService.getDepartmentWiseLine(departmentId);

			objMain.put("lineList",lineList);
			return objMain; 
		}

		@ResponseBody
		@RequestMapping(value = "/factoryWiseDepartment/{factoryId}",method=RequestMethod.GET)
		public JSONObject factoryWiseDepartment(@PathVariable ("factoryId") String factoryId) {
			JSONObject objMain = new JSONObject();	
			List<Department> departmentList = registerService.getFactoryWiseDepartment(factoryId);

			objMain.put("departmentList",departmentList);
			return objMain; 
		}

		
		@RequestMapping(value = "/saveMachine",method=RequestMethod.POST)
		public @ResponseBody JSONObject saveMachine(Machine v) {
			
			JSONObject objmain = new JSONObject();
			if(!registerService.isMachineExist(v)) {
				if(registerService.saveMachine(v)) {

					JSONArray mainarray = new JSONArray();

					List<Machine> List= registerService.getMachineList();

					for(int a=0;a<List.size();a++) {
						
						JSONObject obj = new JSONObject();
						obj.put("MachineId", List.get(a).getMachineId());
						obj.put("Name", List.get(a).getName());
						obj.put("Brand", List.get(a).getBrand());
						obj.put("ModelNo", List.get(a).getModelNo());
						obj.put("Motor", List.get(a).getMotor());
						obj.put("EmployeeId", List.get(a).getEmployeeId());
						
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
		
		
		@RequestMapping(value = "/editMachine",method=RequestMethod.POST)
		public @ResponseBody JSONObject editMachine(Machine v) {
			
			JSONObject objmain = new JSONObject();
			if(registerService.isMachineExist(v)) {
				if(registerService.editMachine(v)) {

					JSONArray mainarray = new JSONArray();

					List<Machine> List= registerService.getMachineList();

					for(int a=0;a<List.size();a++) {
						
						JSONObject obj = new JSONObject();
						obj.put("MachineId", List.get(a).getMachineId());
						obj.put("Name", List.get(a).getName());
						obj.put("Brand", List.get(a).getBrand());
						obj.put("ModelNo", List.get(a).getModelNo());
						obj.put("Motor", List.get(a).getMotor());
						obj.put("EmployeeId", List.get(a).getEmployeeId());
						
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
		
		@RequestMapping(value = "/allMachine", method = RequestMethod.POST)
		public @ResponseBody JSONObject allMachine() {

			List<Machine> allMachineList = registerService.getMachineList();

			JSONObject mainobj = new JSONObject();
			JSONArray mainarray = new JSONArray();

			for (int i = 0; i < allMachineList.size(); i++) {

				JSONObject obj = new JSONObject();
				
				obj.put("MachineId", allMachineList.get(i).getMachineId());
				obj.put("Name", allMachineList.get(i).getName());
				obj.put("Brand", allMachineList.get(i).getBrand());
				obj.put("ModelNo", allMachineList.get(i).getModelNo());
				obj.put("Motor", allMachineList.get(i).getMotor());
				obj.put("EmployeeId", allMachineList.get(i).getEmployeeId());
				obj.put("EmployeeName", allMachineList.get(i).getEmployeeName());
				
				mainarray.add(obj);
			}
			mainobj.put("result", mainarray);

			return mainobj;
		}
		
		
		//Process Create
		
		@RequestMapping(value = "/process_create",method=RequestMethod.GET)
		public ModelAndView process_create(ModelMap map,HttpSession session) {
			
			String userId=(String)session.getAttribute("userId");
			String userName=(String)session.getAttribute("userName");
			
			List<ProcessInfo> List= registerService.getProcessList();
			ModelAndView view = new ModelAndView("register/process_create");
			
			view.addObject("processlist",List);
			
			map.addAttribute("userId",userId);
			map.addAttribute("userName",userName);
			
			return view; //JSP - /WEB-INF/view/index.jsp
		}

		@RequestMapping(value = "/saveProcess",method=RequestMethod.POST)
		public @ResponseBody JSONObject saveProcess(ProcessInfo v) {
			
			JSONObject objmain = new JSONObject();
			if(!registerService.isProcessExist(v)) {
				if(registerService.saveProcess(v)) {

					JSONArray mainarray = new JSONArray();

					List<ProcessInfo> List= registerService.getProcessList();

					for(int a=0;a<List.size();a++) {
						
						JSONObject obj = new JSONObject();
						obj.put("ProcessId", List.get(a).getProcessId());
						obj.put("Name", List.get(a).getProcessName());						
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
		
		@RequestMapping(value = "/editProcess",method=RequestMethod.POST)
		public @ResponseBody JSONObject editProcess(ProcessInfo v) {
			
			JSONObject objmain = new JSONObject();
			if(!registerService.isProcessExist(v)) {
				if(registerService.editProcess(v)) {

					JSONArray mainarray = new JSONArray();

					List<ProcessInfo> List= registerService.getProcessList();

					for(int a=0;a<List.size();a++) {
						
						JSONObject obj = new JSONObject();
						obj.put("ProcessId", List.get(a).getProcessId());
						obj.put("Name", List.get(a).getProcessName());						
						mainarray.add(obj);
						
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
		
}
