package pg.services;

import java.util.List;

import pg.proudctionModel.ProductionPlan;
import pg.registerModel.AccessoriesItem;
import pg.registerModel.Bank;
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
import pg.registerModel.Line;
import pg.registerModel.LocalItem;
import pg.registerModel.Machine;
import pg.registerModel.MerchandiserInfo;
import pg.registerModel.Notifyer;
import pg.registerModel.ParticularItem;
import pg.registerModel.ProcessInfo;
import pg.registerModel.SampleType;
import pg.registerModel.Size;
import pg.registerModel.SizeGroup;
import pg.registerModel.StyleItem;
import pg.registerModel.SupplierModel;
import pg.registerModel.Unit;
import pg.registerModel.WareHouse;
import pg.storeModel.StoreGeneralCategory;

public interface RegisterService {

	public String maxBuyerId();
	public List<String> countries(String value);

	//Buyer Create
	public boolean insertBuyer(BuyerModel buyer);
	public List<String> BuyersList(String value);
	public List<BuyerModel> BuyersDetails(String value);
	public boolean editBuyer(BuyerModel buyer);
	public List<BuyerModel> getAllBuyers(String userId);

	//Notify Create
	public boolean saveNotifyer(Notifyer notifyer);
	public boolean editNotifyer(Notifyer notifyer);
	public Notifyer getNotifyerInfo(String id);
	public List<Notifyer> getNotifyerList();
	public List<Notifyer> getNotifyerListByBuyerId(String buyerId);

	//Supplier Create
	public String maxSupplierId();
	public boolean insertSupplier(SupplierModel supplier);
	public List<String> supplierList(String value);
	public List<SupplierModel> SupplierDetails(String value);
	public boolean editSupplier(SupplierModel supplier);
	public List<SupplierModel> getAllSupplier();


	//Factory Create

	public String maxFactoryId();
	public boolean insertFactory(FactoryModel factory);
	public List<FactoryModel> FactoryDetails(String value);
	public List<String> factorylist(String value);
	public boolean editFactory(FactoryModel factory);
	public List<FactoryModel> getAllFactories();


	//Courier Create
	public String maxCourierId();
	public boolean insertCourier(CourierModel courier);
	public List<String> courierList(String value);
	public List<CourierModel> CourierDetails(String value);
	public boolean editCourier(CourierModel courier);
	public List<CourierModel> getAllCouriers();

	//Brand Create 
	public boolean saveBrand(Brand brand);
	public boolean editBrand(Brand brand);
	public List<Brand> getBrandList();
	public boolean isBrandExist(Brand brand);

	//Fabrics Item Create 
	public boolean saveFabricsItem(FabricsItem fabricsItem);
	public boolean editFabricsItem(FabricsItem fabricsItem);
	public FabricsItem getFabricsItem(String fabricsItemId);
	public List<FabricsItem> getFabricsItemList();
	public boolean isFabricsItemExist(FabricsItem fabricsItem);

	public boolean addItemUnits(Unit unit,String itemId,String itemType); 
	public List<Unit> getItemUnitsList(String itemId,String itemType);

	//Acccessories Item Create
	public boolean saveAccessoriesItem(AccessoriesItem accessoriesItem);
	public boolean editAccessoriesItem(AccessoriesItem accessoriesItem);
	public AccessoriesItem getAccessoriesItem(String accessoriesItem);
	public List<AccessoriesItem> getAccessoriesItemList();
	public boolean isAccessoriesItemExist(AccessoriesItem accessoriesItem);


	//StyleItem Create 
	public boolean saveStyleItem(StyleItem styleItem);
	public boolean editStyleItem(StyleItem styleItem);
	public List<StyleItem> getStyleItemList();
	public boolean isStyleItemExist(StyleItem styleItem);

	//Unit Create 
	public boolean saveUnit(Unit unit);
	public boolean editUnit(Unit unit);
	public List<Unit> getUnitList();
	public boolean isUnitExist(Unit unit);

	//Color Create 
	public boolean saveColor(Color color);
	public boolean editColor(Color color);
	public List<Color> getColorList();
	public boolean isColorExist(Color color);

	//Local Item Create 
	public boolean saveLocalItem(LocalItem localItem);
	public boolean editLocalItem(LocalItem localItem);
	public List<LocalItem> getLocalItemList();
	public boolean isLocalItemExist(LocalItem localItem);

	//Particular Item Create 
	public boolean saveParticularItem(ParticularItem particularItem);
	public boolean editParticularItem(ParticularItem particularItem);
	public List<ParticularItem> getParticularItemList();
	public boolean isParticularItemExist(ParticularItem particularItem);


	//Country Create 
	public boolean saveCountry(Country country);
	public boolean editCountry(Country country);
	public List<Country> getCountryList();
	public boolean isCountryExist(Country country);

	//Sample Type Create 
	public boolean saveSampleType(SampleType sampleType);
	public boolean editSampleType(SampleType sampleType);
	public List<SampleType> getSampleTypeList();
	public boolean isSampleTypeExist(SampleType sampleType);

	//Department Create 
	public boolean saveDepartment(Department department);
	public boolean editDepartment(Department department);
	public List<Department> getDepartmentList();
	public boolean isDepartmentExist(Department department);
	public List<Department> getFactoryWiseDepartment(String factoryId);

	//Merchandiser Create 
	public boolean isMerchandiserExist(MerchandiserInfo v);
	public boolean saveMerchandiser(MerchandiserInfo v);
	public List<MerchandiserInfo> getMerchandiserList();
	public boolean editMerchandiser(MerchandiserInfo v);

	//Incharge Create 
	public boolean isInchargeExist(InchargeInfo v);
	public boolean saveIncharge(InchargeInfo v);
	public List<InchargeInfo> getInchargeList();

	//Ware House Create 
	public boolean saveWareHouse(WareHouse wareHouse);
	public boolean editWareHouse(WareHouse wareHouse);
	public List<WareHouse> getWareHouseList();
	public boolean isWareHouseExist(WareHouse wareHouse);

	//Line Create 
	public boolean saveLine(Line line);
	public boolean editLine(Line line);
	public List<Line> getLineList();
	public boolean isLineExist(Line line);
	public List<Line> getDepartmentWiseLine(String departmentId);

	//Style Size Create 
	public boolean saveStyleSize(Size size);
	public boolean editStyleSize(Size size);
	public List<Size> getStyleSizeList();
	public boolean isStyleSizeExist(Size size);

	//Style Size Group
	public boolean saveStyleSizeGroup(SizeGroup sizeGroup);
	public boolean editStyleSizeGroup(SizeGroup sizeGroup);
	public List<SizeGroup> getStyleSizeGroupList();
	public boolean isStyleSizeGroupExist(SizeGroup sizeGroup);
	public Unit getUnit(String unitId);

	//Common get method
	public List<Factory> getFactoryNameList();

	//Store Category
	public List<StoreGeneralCategory> getStoreCategoryList();

	public boolean editIncharge(InchargeInfo v);

	// Designation

	public boolean saveDesignation(Designation designation);
	public List<Designation> getDesignationList();
	public boolean editDesignation(Designation designation);
	public boolean isDesignationExist(Designation v);

	//Employee

	public boolean saveEmployee(Employee saveEmployee);
	public List<Employee> getEmployeeList();
	public boolean editEmployee(Employee editEmployee);
	public boolean isEmployeeExist(Employee v);


	//Machine

	public boolean saveMachine(Machine saveMachine);
	public List<Machine> getMachineList();
	public boolean editMachine(Machine editMachine);
	public boolean isMachineExist(Machine v);

	//Process
	public boolean isProcessExist(ProcessInfo v);
	public boolean saveProcess(ProcessInfo v);
	public List<ProcessInfo> getProcessList();
	public boolean editProcess(ProcessInfo v);


	//Bank Create
	public boolean saveBank(Bank bank);
	public boolean editBank(Bank bank);
	public Bank getBankInfo(String id);
	public List<Bank> getBankList();


}
