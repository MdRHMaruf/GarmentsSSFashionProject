package pg.services;

import java.sql.SQLException;
import java.util.List;

import pg.model.CommonModel;
import pg.orderModel.BuyerPO;
import pg.orderModel.BuyerPoItem;
import pg.orderModel.CheckListModel;
import pg.orderModel.Costing;
import pg.orderModel.FabricsIndent;
import pg.orderModel.PurchaseOrder;
import pg.orderModel.PurchaseOrderItem;
import pg.orderModel.SampleCadAndProduction;
import pg.orderModel.SampleRequisitionItem;
import pg.orderModel.Style;
import pg.orderModel.AccessoriesIndent;
import pg.orderModel.AccessoriesIndentCarton;
import pg.orderModel.ParcelModel;
import pg.proudctionModel.ProductionPlan;
import pg.registerModel.Color;
import pg.registerModel.CourierModel;
import pg.registerModel.Factory;
import pg.registerModel.ItemDescription;
import pg.registerModel.ParticularItem;
import pg.registerModel.SizeGroup;
import pg.registerModel.StyleItem;

public interface OrderService {

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
	
	List<Color> getColorListByMultiplePoAndStyle(String purchaseOrders,String styleIdList);
	List<String> getShippingMarkListByMultiplePoAndStyle(String purchaseOrders,String styleIdList);

	boolean SaveStyleCreate(String user, String buyerName, String itemName, String styleNo,String size, String date,
			String frontimg, String backimg) throws SQLException;
	List<Style> getStyleList();
	List<Style> getStyleWiseItemList();
	List<Style> getStyleAndItem(String value);

	//Costing Create
	List<ParticularItem> getTypeWiseParticularList(String type);
	public boolean saveCosting(Costing costing);
	public String confirmCosting(List<Costing> costingList);
	public boolean editCosting(Costing costing);
	public boolean deleteCosting(String autoId);
	List<Costing> getCostingList(String styleId,String itemId);
	List<Costing> getCostingList();
	public List<Costing> cloningCosting(String oldStyleId,String oldItemId);
	Costing getCostingItem(String autoId);

	//Buyder Po Order
	public boolean addBuyerPoItem(BuyerPoItem buyerPoItem);
	boolean editBuyerPoItem(BuyerPoItem buyerPoItem);
	List<BuyerPoItem> getBuyerPOItemList(String buyerPOId);
	BuyerPoItem getBuyerPOItem(String itemAutoId);
	boolean deleteBuyerPoItem(String itemAutoId);
	boolean submitBuyerPO(BuyerPO buyerPo);
	boolean editBuyerPO(BuyerPO buyerPo);
	List<BuyerPO> getBuyerPoList();
	BuyerPO getBuyerPO(String buyerPoNo);
	

	//Accessories
	public String maxAIno(); 
	public List<CommonModel>PurchaseOrders();
	public List<CommonModel>Styles(String po);
	public List<CommonModel>Colors(String style, String item);
	public List<CommonModel>Items(String buyerorderid,String style);
	public List<CommonModel>AccessoriesItem(String type);
	public List<CommonModel>Size(String style, String item, String color, String type);
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
	List<AccessoriesIndent> getAccessoriesIndentItemList(String accessoriesItemId);
	boolean editAccessoriesIndent(AccessoriesIndent v);
	boolean deleteAccessoriesIndent(String accessorienIndentId,String indentAutoId);
	public String confirmAccessoriesIndent(String accessoriesIndentId, String accessoriesItems);
	List<AccessoriesIndent> getPostedAccessoriesIndent();

	//Accessories Carton
	boolean saveAccessoriesCurton(AccessoriesIndentCarton v);
	List<AccessoriesIndentCarton> getAccessoriesIndentCarton(String poNo, String style, String item, String itemColor);
	List<AccessoriesIndentCarton> getAllAccessoriesCartonData();
	List<AccessoriesIndentCarton> getAccessoriesIndentCartonItemDetails(String id);
	boolean editAccessoriesCurton(AccessoriesIndentCarton v);


	//Fabrics Indent
	List<String> getPurchaseOrderList();
	List<FabricsIndent> getFabricsIndentList();
	boolean saveFabricsIndent(FabricsIndent fabricsIndent);
	boolean editFabricsIndent(FabricsIndent fabricsIndent);
	boolean isFabricsIndentExist(FabricsIndent fabricsIndent);
	FabricsIndent getFabricsIndent(String indentId);
	List<Style> getPOWiseStyleList(String purchaseOrder);
	List<Color> getStyleItemWiseColor(String styleId,String itemId);
	double getOrderQuantity(String purchaseOrder,String styleId,String itemId,String colorId);
	List<CommonModel> BuyerWisePo(String buyerId);

	//Sample Requisition
	List<CommonModel> getSampleList();
	List<CommonModel> getInchargeList();
	List<CommonModel> getMerchendizerList();
	boolean addItemToSampleRequisition(SampleRequisitionItem v);
	List<SampleRequisitionItem> getSampleRequisitionItemList(String userId);
	boolean confirmItemToSampleRequisition(SampleRequisitionItem v);
	List<SampleRequisitionItem> getSampleRequisitionList();
	List<SampleRequisitionItem> getSampleRequisitionDetails(String sampleReqId);
	List<SampleRequisitionItem> getIncomepleteSampleRequisitionItemList(String userId);
	List<ProductionPlan> getSampleProduction(String sampleCommentId,String operatorId,String date);
	
	//Purchase Order
	List<pg.registerModel.AccessoriesItem> getTypeWiseIndentItems(String purchaseOrder,String styleId,String type);
	boolean submitPurchaseOrder(PurchaseOrder purchaseOrder);
	boolean editPurchaseOrder(PurchaseOrder purchaseOrder);
	List<PurchaseOrder> getPurchaseOrderSummeryList();
	PurchaseOrder getPurchaseOrder(String poNo);
	List<PurchaseOrderItem> getPurchaseOrderItemList(PurchaseOrderItem purchaseOrderItem);

	//File Upload
	boolean fileUpload(String uploadFileName, String computerName, String string, String purpose, String user);
	List<pg.orderModel.FileUpload> findfiles(String start, String end, String user);
	boolean fileDownload(String fileName, String user, String string, String computerName);
	boolean deletefile(String filename);
	boolean InstallDataAsSameParticular(String userId,String purchaseOrder, String styleId, String itemId, String colorId,
			String installAccessories, String forAccessories);


	//Sample Production
	List<SampleCadAndProduction> getSampleCommentsList();
	SampleCadAndProduction getSampleProductionInfo(String sampleCommentsId);
	boolean postSampleProductionInfo(SampleCadAndProduction sampleCadAndProduction);


	public List<FabricsIndent> getStyleDetailsForFabricsIndent();
	public List<CourierModel> getcourierList();
	public boolean ConfirmParcel(ParcelModel parcel);

	public List<ParcelModel> parcelList();

	public ParcelModel getParcelInfo(String id);
	public List<ParcelModel> getParcelItems(String autoId);
	public boolean editParecel(ParcelModel parcel);
	public boolean editParecelItem(ParcelModel parcel);


	public boolean sampleCadInsert(SampleCadAndProduction sample);

	public List<SampleCadAndProduction> getSampleComments();
	public List<SampleCadAndProduction> getSampleDetails(String id);
	public boolean editSampleCad(SampleCadAndProduction sample);


	//Purchase Order Approval for MD
	List<PurchaseOrder> getPurchaseOrderApprovalList(String fromDate,String toDate,String itemType,String approveType);
	boolean purchaseOrderApproveConfirm(List<PurchaseOrder> purchaseOrderList);
	
	
	public boolean ConfirmCheckList(CheckListModel checkList);
	public List<CheckListModel> getChekList();
	public CheckListModel getCheckListInfo(String autoId);
	public List<CheckListModel> getCheckListItems(String autoId);
	public boolean editCheckList(CheckListModel checkList);
	public boolean editCheckListItem(CheckListModel checkList);
	

}


