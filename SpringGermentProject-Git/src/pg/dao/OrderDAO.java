package pg.dao;

import java.sql.SQLException;
import java.util.List;

import pg.model.CommonModel;
import pg.orderModel.AccessoriesIndent;
import pg.orderModel.AccessoriesIndentCarton;
import pg.orderModel.BuyerPO;
import pg.orderModel.BuyerPoItem;
import pg.orderModel.CheckListModel;
import pg.orderModel.Costing;
import pg.orderModel.FabricsIndent;
import pg.orderModel.ParcelModel;
import pg.orderModel.PurchaseOrder;
import pg.orderModel.PurchaseOrderItem;
import pg.orderModel.SampleCadAndProduction;
import pg.orderModel.SampleRequisitionItem;
import pg.orderModel.Style;
import pg.proudctionModel.ProductionPlan;
import pg.registerModel.Color;
import pg.registerModel.CourierModel;
import pg.registerModel.ItemDescription;
import pg.registerModel.ParticularItem;
import pg.registerModel.AccessoriesItem;
import org.springframework.web.multipart.MultipartFile;

public interface OrderDAO {

	//Style Create
	List<ItemDescription> getItemDescriptionList();
	List<Style> getBuyerWiseStylesItem(String buyerId);
	List<CommonModel> getPurchaseOrderListByMultipleBuyers(String buyersId);
	List<Style> getBuyerPOStyleListByMultipleBuyers(String buyersId);
	List<Style> getBuyerPOStyleListByMultiplePurchaseOrders(String purchaseOrders);
	List<CommonModel> getStyleWiseBuyerPO(String styleId);
	List<CommonModel> getPurchaseOrderByMultipleStyle(String styleIdList);
	List<ItemDescription> getStyleWiseItem(String styleId);
	List<ItemDescription> getItemListByMultipleStyleId(String styleIdList);
	boolean SaveStyleCreate(String user, String buyerName, String itemName, String styleNo,String size, String date,
			MultipartFile frontimg, MultipartFile backimg) throws SQLException;
	
	List<Color> getColorListByMultiplePoAndStyle(String purchaseOrders,String styleIdList);
	List<String> getShippingMarkListByMultiplePoAndStyle(String purchaseOrders,String styleIdList);

	List<Style> getStyleWiseItemList(String userId);
	List<Style> getStyleList(String userId);
	List<Style> getStyleAndItem(String value);

	//Costing Create
	List<ParticularItem> getTypeWiseParticularList(String type);
	boolean saveCosting(Costing costing);
	String confirmCosting(List<Costing> costingList);
	String editCostingNo(List<Costing> costingList);
	boolean editCosting(Costing costing);
	boolean deleteCosting(String autoId);
	List<Costing> getCostingList(String styleId,String itemId,String costingNo);
	List<Costing> getBuyerWiseCostingList(String buyerId,String userId);
	List<Costing> getCostingList(String userId);
	//Buyer Po Order
	List<Costing> cloningCosting(String costingNo,String oldStyleId,String oldItemId);
	Costing getCostingItem(String autoId);

	boolean isBuyerPoItemExist(BuyerPoItem buyerPoItem);
	boolean addBuyerPoItem(BuyerPoItem buyerPoItem);
	boolean editBuyerPoItem(BuyerPoItem buyerPoItem);
	List<BuyerPoItem> getBuyerPOItemList(String buyerPOId,String userId);
	BuyerPoItem getBuyerPOItem(String itemAutoId);
	boolean deleteBuyerPoItem(String itemAutoId);
	boolean submitBuyerPO(BuyerPO buyerPo);
	boolean editBuyerPO(BuyerPO buyerPo);
	List<BuyerPO> getBuyerPoList(String userId);
	BuyerPO getBuyerPO(String buyerPoNo);

	//Accessories
	public String maxAIno(); 
	public List<CommonModel>getPurchaseOrders(String userId);
	public List<CommonModel>Styles(String po);
	public List<CommonModel>Colors(String style, String item);
	public List<CommonModel>Items(String buyerorderid,String style);
	public List<CommonModel>AccessoriesItem(String type);
	public List<CommonModel>Size(String buyerorderid, String style, String item, String color);
	public List<CommonModel>Unit();
	public List<CommonModel>Brands();

	public List<CommonModel>ShippingMark(String po, String style, String item);
	public List<CommonModel>AllColors();
	public List<CommonModel>SizewiseQty(String buyerorderid, String style,String item,String color,String size);

	public List<AccessoriesIndent> getAccessoriesRecyclingData(String query);
	public List<AccessoriesIndent> getAccessoriesRecyclingDataWithSize(String query,String query2);
	public boolean insertAccessoriesIndent(AccessoriesIndent ai);

	public List<AccessoriesIndent>PendingList();
	List<CommonModel> styleItemsWiseColor(String buyerorderid,String style,String item);

	List<AccessoriesIndent> getAccessoriesIndent(String po, String style, String itemname, String itemcolor);
	List<AccessoriesIndent> getPendingAccessoriesIndent();
	List<AccessoriesIndent> getAccessoriesIndentItemList(String accessoriesIndentId);
	boolean editAccessoriesIndent(AccessoriesIndent v);
	boolean deleteAccessoriesIndent(String accessorienIndentId,String indentAutoId);
	public String confirmAccessoriesIndent(String accessoriesIndentId, String accessoriesItems);
	List<AccessoriesIndent> getPostedAccessoriesIndent(String userId);

	//Zipper Indent
	public String confirmZipperIndent(String zipperIndentId, String zipperItems);
	List<AccessoriesIndent> getPostedZipperIndent(String userId);
	List<AccessoriesIndent> getZipperIndentItemList(String zipperIndentId);
	boolean deleteZipperIndent(String zipperIndentId,String indentAutoId);
	boolean editZipperIndent(AccessoriesIndent v);
	
	//Accessories Carton
	boolean saveAccessoriesCurton(AccessoriesIndentCarton v);
	String confirmCartonIndent(String cartonIndentId,String cartonItems);
	List<AccessoriesIndentCarton> getAccessoriesIndentCarton(String poNo, String style, String item, String itemColor);
	List<AccessoriesIndentCarton> getAllAccessoriesCartonData(String userId);

	List<AccessoriesIndentCarton> getAccessoriesIndentCartonItemDetails(String id);
	boolean editAccessoriesCarton(AccessoriesIndentCarton cartonIndent);
	boolean deleteAccessoriesCarton(String autoId,String indentId);
	boolean InstallDataAsSameParticular(String userId,String purchaseOrder, String styleId, String itemId, String colorId,
			String installAccessories, String forAccessories);

	//Fabrics Indent
	List<String> getPurchaseOrderList(String userId);
	List<Color> getStyleItemWiseColor(String styleId,String itemId);
	List<Style> getPOWiseStyleList(String purchaseOrder);
	public String confirmFabricsIndent(String fabricsIndentId,String fabricsItems);
	boolean editFabricsIndent(FabricsIndent fabricsIndent);
	boolean isFabricsIndentExist(FabricsIndent fabricsIndent);
	boolean deleteFabricsIndent(String autoId,String indentId);
	List<FabricsIndent> getFabricsIndentList();
	FabricsIndent getFabricsIndentInfo(String autoId);
	List<FabricsIndent> getFabricsIndent(String indentId);
	double getOrderQuantity(String purchaseOrder,String styleId,String itemId,String colorId);
	double getOrderQuantityByMultipleId(String purchaseOrder,String styleId,String itemId,String colorId);

	//Common 
	List<CommonModel> BuyerWisePo(String buyerId);
	List<CommonModel> getSampleList();
	List<CommonModel> getInchargeList();
	List<CommonModel> getMerchendizerList();

	//Sample Requisition
	boolean addItemToSampleRequisition(SampleRequisitionItem v);
	List<SampleRequisitionItem> getSampleRequisitionItemList(String userId);
	boolean confirmItemToSampleRequisition(SampleRequisitionItem v);
	List<SampleRequisitionItem> getSampleRequisitionList(String userId);
	List<SampleRequisitionItem> getSampleRequisitionDetails(String sampleReqId);
	List<SampleRequisitionItem> getIncomepleteSampleRequisitionItemList(String userId);
	List<ProductionPlan> getSampleProduction(String sampleCommentId,String operatorId,String date);

	//Purchase Order
	List<AccessoriesItem> getTypeWiseIndentItems(String purchaseOrder,String styleId,String type);
	List<AccessoriesItem> getIndentItems(String indentId,String indentType);
	List<Style> getIndentStyles(String indentId,String indentType);
	boolean submitPurchaseOrder(PurchaseOrder purchaseOrder);
	boolean editPurchaseOrder(PurchaseOrder purchaseOrder);
	List<PurchaseOrder> getPurchaseOrderSummeryList(String userId);
	List<CommonModel> getPendingIndentList(String userId);
	PurchaseOrder getPurchaseOrder(String poNo,String poType);
	List<PurchaseOrderItem> getPurchaseOrderItemList(AccessoriesIndent accessoriesIndent);
	List<PurchaseOrderItem> getPurchaseOrderItemListByStyleId(AccessoriesIndent accessoriesIndent);

	//File Upload
	boolean fileUpload(String uploadFileName, String computerName, String string, String purpose, String user, String buyerName, String purchaseOrder);
	List<pg.orderModel.FileUpload> findfiles(String start, String end, String user);
	List<pg.orderModel.FileUpload> findfiles(String buyerId,String purchaseOrder,int uploadFileType);
	boolean fileDownload(String fileName, String user, String string, String computerName);
	boolean deletefile(String filename, String id);

	//Sample Production
	List<SampleCadAndProduction> getSampleCommentsList();
	SampleCadAndProduction getSampleProductionInfo(String sampleCommentsId);
	boolean postSampleProductionInfo(SampleCadAndProduction sampleCadAndProduction);
	
	
	public List<FabricsIndent> getStyleDetailsForFabricsIndent(String userId);
	public List<CourierModel> getcourierList();
	
	
	public boolean ConfirmParcel(ParcelModel parcel);
	public List<ParcelModel> parcelList();
	public ParcelModel getParcelInfo(String autoId);
	public List<ParcelModel> getParcelItems(String autoId);
	public boolean editParecel(ParcelModel parcel);
	public boolean editParecelItem(ParcelModel parcel);
	
	
	public boolean sampleCadInsert(SampleCadAndProduction sample);
	
	public List<SampleCadAndProduction> getSampleComments(String userId);
	
	public List<SampleCadAndProduction> getSampleDetails(String id);
	public boolean editSampleCad(SampleCadAndProduction sample);

	//Purchase Order Approval for MD
	List<PurchaseOrder> getPurchaseOrderApprovalList(String fromDate,String toDate,String itemType,String approveType,String buyerId,String supplierId);
	boolean purchaseOrderApproveConfirm(List<PurchaseOrder> purchaseOrderList);
	
	
	public boolean ConfirmCheckList(CheckListModel checkList);
	public List<CheckListModel> getChekList();
	public CheckListModel getCheckListInfo(String autoId);
	public List<CheckListModel> getCheckListItems(String autoId);
	public boolean editCheckList(CheckListModel checkList);
	public boolean editCheckListItem(CheckListModel checkList);

	public List<Style>images(Style style);
	public boolean editStyle(String styleItemAutoId,String buyerId,String itemId,String styleid,String styleNo,String size,String date,MultipartFile frontImage,MultipartFile backImage);

	// Create by Arman
	List<CommonModel> departmentWiseReceiver(String deptId);
	public boolean saveFileAccessDetails(CommonModel v);
	List<CommonModel> getAllFromFileLogDetails(CommonModel v);
	public boolean addNewPermission (CommonModel v);
	
	boolean deleteSampleRequisitionItem(String sapleAutoId);
	
	List<SampleRequisitionItem> getSampleRequistionItemData(String itemAutoId);
	boolean editItemToSampleRequisition(SampleRequisitionItem v);
	List<SampleCadAndProduction> getSampleCadDetails(String sampleCommentId);
	
	boolean samplecadfileupload(String smaplecadid, String filename, String user, String uploadedpcip,String searchtype);
	
	List<pg.orderModel.FileUpload> findsamplecadfiles(String userid, String samplereqid);
	
	boolean deletesamplefile(String filename, String id);
	
	List<SampleRequisitionItem> getAllSampleRequisitionList();

	List<String> getMultifiles(String bpo);
	String getMaxCadId();
	List<String> getMultiCadfiles(String bpo);

	List<CommonModel> getStyleWisePurchaseOrder(String styleId);
	
	List<SampleCadAndProduction> getSampleCadDetailsForProduction(String sampleCommentId);
	List<SampleRequisitionItem> getSampleRequisitionAndCuttingDetails(String sampleReqId,String sampleCommentId);

	List<CommonModel> getSampleEmployeeList();
	
	boolean checkDoplicateSampleRequisition(SampleRequisitionItem v);
	
	//Costing New Version
	List<Costing> getFabricsItemForCosting();
	List<Costing> getCostingItemList();
	List<pg.registerModel.Unit> getUnitList();
	boolean checkCostingExist(Costing v);
	boolean saveCostingNewVersion(Costing v);
	
	List<Costing> getNewCostingList(String userId);
	List<Costing> searchCostingNewVersion(String costingNo);
}





