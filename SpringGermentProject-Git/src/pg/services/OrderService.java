package pg.services;

import java.sql.SQLException;
import java.util.List;

import pg.model.commonModel;
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
	List<ItemDescription> getStyleWiseItem(String styleId);

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
	public List<commonModel>PurchaseOrders();
	public List<commonModel>Styles(String po);
	public List<commonModel>Colors(String style, String item);
	public List<commonModel>Items(String buyerorderid,String style);
	public List<commonModel>AccessoriesItem(String type);
	public List<commonModel>Size(String style, String item, String color, String type);
	public List<commonModel>Unit();
	public List<commonModel>Brands();

	public List<commonModel>ShippingMark(String po, String style, String item);
	public List<commonModel>AllColors();
	public List<commonModel>SizewiseQty(String buyerorderid, String style,String item,String color,String size);

	public boolean insertaccessoriesIndent(AccessoriesIndent ai);

	public List<AccessoriesIndent>PendingList();
	List<commonModel> styleItemsWiseColor(String buyerorderid,String style,String item);
	List<AccessoriesIndent> getAccessoriesIndent(String po, String style, String itemname, String itemcolor);
	List<AccessoriesIndent> getPendingAccessoriesIndent();
	List<AccessoriesIndent> getAccessoriesIndentItemDetails(String id);
	boolean editaccessoriesIndent(AccessoriesIndent v);
	boolean confrimAccessoriesIndent(String user, String aiNo);
	List<AccessoriesIndent> getPostedAccessoriesIndent();

	//Accessories Carton
	boolean saveAccessoriesCurton(accessoriesindentcarton v);
	List<accessoriesindentcarton> getAccessoriesIndentCarton(String poNo, String style, String item, String itemColor);
	List<accessoriesindentcarton> getAllAccessoriesCartonData();
	List<accessoriesindentcarton> getAccessoriesIndentCartonItemDetails(String id);
	boolean editAccessoriesCurton(accessoriesindentcarton v);


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
	List<commonModel> BuyerWisePo(String buyerId);

	//Sample Requisition
	List<commonModel> getSampleList();
	List<commonModel> getInchargeList();
	List<commonModel> getMerchendizerList();
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
	List<pg.orderModel.fileUpload> findfiles(String start, String end, String user);
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

	public List<ParcelModel> getParcelDetails(String id);
	public boolean editParecel(ParcelModel parcel);


	public boolean sampleCadInsert(SampleCadAndProduction sample);

	public List<SampleCadAndProduction> getSampleComments();
	public List<SampleCadAndProduction> getSampleDetails(String id);
	public boolean editSampleCad(SampleCadAndProduction sample);


	//Purchase Order Approval for MD
	List<PurchaseOrder> getPurchaseOrderApprovalList(String fromDate,String toDate,String itemType,String approveType);
	boolean purchaseOrderApproveConfirm(List<PurchaseOrder> purchaseOrderList);
	

}


