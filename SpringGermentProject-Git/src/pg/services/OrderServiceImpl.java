package pg.services;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.sql.ordering.antlr.OrderByAliasResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import pg.dao.OrderDAO;
import pg.model.CommonModel;
import pg.orderModel.BuyerPO;
import pg.orderModel.BuyerPoItem;
import pg.orderModel.CheckListModel;
import pg.orderModel.Costing;
import pg.orderModel.FabricsIndent;
import pg.orderModel.FileUpload;
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
import pg.registerModel.AccessoriesItem;

@Service
public class OrderServiceImpl implements OrderService{

	@Autowired 
	OrderDAO orderDAO;

	@Override
	public List<ItemDescription> getItemDescriptionList() {
		// TODO Auto-generated method stub
		return orderDAO.getItemDescriptionList();
	}

	@Override
	public List<Style> getBuyerWiseStylesItem(String buyerId) {
		// TODO Auto-generated method stub
		return orderDAO.getBuyerWiseStylesItem(buyerId);
	}

	@Override
	public List<CommonModel> getPurchaseOrderListByMultipleBuyers(String buyersId) {
		// TODO Auto-generated method stub
		return orderDAO.getPurchaseOrderListByMultipleBuyers(buyersId);
	}

	@Override
	public List<Style> getBuyerPOStyleListByMultipleBuyers(String buyersId) {
		// TODO Auto-generated method stub
		return orderDAO.getBuyerPOStyleListByMultipleBuyers(buyersId);
	}

	@Override
	public List<Style> getBuyerPOStyleListByMultiplePurchaseOrders(String purchaseOrders) {
		// TODO Auto-generated method stub
		return orderDAO.getBuyerPOStyleListByMultiplePurchaseOrders(purchaseOrders);
	}



	@Override
	public List<CommonModel> getStyleWiseBuyerPO(String styleId) {
		// TODO Auto-generated method stub
		return orderDAO.getStyleWiseBuyerPO(styleId);
	}

	@Override
	public List<CommonModel> getPurchaseOrderByMultipleStyle(String styleIdList) {
		// TODO Auto-generated method stub
		return orderDAO.getPurchaseOrderByMultipleStyle(styleIdList);
	}


	@Override
	public List<ItemDescription> getStyleWiseItem(String styleId) {
		// TODO Auto-generated method stub
		return orderDAO.getStyleWiseItem(styleId);
	}

	@Override
	public List<ItemDescription> getItemListByMultipleStyleId(String styleIdList) {
		// TODO Auto-generated method stub
		return orderDAO.getItemListByMultipleStyleId(styleIdList);
	}

	@Override
	public List<Color> getColorListByMultiplePoAndStyle(String purchaseOrders, String styleIdList) {
		// TODO Auto-generated method stub
		return orderDAO.getColorListByMultiplePoAndStyle(purchaseOrders, styleIdList);
	}

	@Override
	public List<String> getShippingMarkListByMultiplePoAndStyle(String purchaseOrders, String styleIdList) {
		// TODO Auto-generated method stub
		return orderDAO.getShippingMarkListByMultiplePoAndStyle(purchaseOrders, styleIdList);
	}



	@Override
	public List<Style> getStyleList(String userId) {
		// TODO Auto-generated method stub
		return orderDAO.getStyleList(userId);
	}

	@Override
	public List<Style> getStyleWiseItemList(String userId) {
		// TODO Auto-generated method stub
		return orderDAO.getStyleWiseItemList(userId);
	}


	@Override
	public List<Style> getStyleAndItem(String value) {
		// TODO Auto-generated method stub
		return orderDAO.getStyleAndItem(value);
	}


	@Override
	public List<ParticularItem> getTypeWiseParticularList(String type) {
		// TODO Auto-generated method stub
		return orderDAO.getTypeWiseParticularList(type);
	}

	@Override
	public boolean saveCosting(Costing costing) {
		// TODO Auto-generated method stub
		return orderDAO.saveCosting(costing);
	}

	@Override
	public String confirmCosting(List<Costing> costingList) {
		// TODO Auto-generated method stub
		return orderDAO.confirmCosting(costingList);
	}
	
	@Override
	public String editCostingNo(List<Costing> costingList) {
		// TODO Auto-generated method stub
		return orderDAO.editCostingNo(costingList);
	}

	@Override
	public boolean editCosting(Costing costing) {
		// TODO Auto-generated method stub
		return orderDAO.editCosting(costing);
	}

	@Override
	public boolean deleteCosting(String autoId) {
		// TODO Auto-generated method stub
		return orderDAO.deleteCosting(autoId);
	}

	@Override
	public List<Costing> getCostingList(String styleId, String itemId,String costingNo) {
		// TODO Auto-generated method stub
		return orderDAO.getCostingList(styleId, itemId, costingNo);
	}
	
	@Override
	public List<Costing> getBuyerWiseCostingList(String buyerId,String userId) {
		// TODO Auto-generated method stub
		return orderDAO.getBuyerWiseCostingList(buyerId,userId);
	}

	@Override
	public List<Costing> getCostingList(String userId) {
		// TODO Auto-generated method stub
		return orderDAO.getCostingList(userId);
	}

	@Override
	public List<Costing> cloningCosting(String costingNo,String oldStyleId, String oldItemId) {
		// TODO Auto-generated method stub
		return orderDAO.cloningCosting(costingNo,oldStyleId, oldItemId);
	}

	@Override
	public Costing getCostingItem(String autoId) {
		// TODO Auto-generated method stub
		return orderDAO.getCostingItem(autoId);
	}

	@Override
	public boolean isBuyerPoItemExist(BuyerPoItem buyerPoItem) {
		// TODO Auto-generated method stub
		return orderDAO.isBuyerPoItemExist(buyerPoItem);
	}
	
	@Override
	public boolean addBuyerPoItem(BuyerPoItem buyerPoItem) {
		// TODO Auto-generated method stub
		return orderDAO.addBuyerPoItem(buyerPoItem);
	}

	@Override
	public boolean editBuyerPO(BuyerPO buyerPo) {
		// TODO Auto-generated method stub
		return orderDAO.editBuyerPO(buyerPo);
	}

	@Override
	public boolean editBuyerPoItem(BuyerPoItem buyerPoItem) {
		// TODO Auto-generated method stub
		return orderDAO.editBuyerPoItem(buyerPoItem);
	}

	@Override
	public boolean deleteBuyerPoItem(String itemAutoId) {
		// TODO Auto-generated method stub
		return orderDAO.deleteBuyerPoItem(itemAutoId);
	}

	@Override
	public List<BuyerPoItem> getBuyerPOItemList(String buyerPOId,String userId) {
		// TODO Auto-generated method stub
		return orderDAO.getBuyerPOItemList(buyerPOId,userId);
	}

	@Override
	public BuyerPoItem getBuyerPOItem(String itemAutoId) {
		// TODO Auto-generated method stub
		return orderDAO.getBuyerPOItem(itemAutoId);
	}

	@Override
	public boolean submitBuyerPO(BuyerPO buyerPo) {
		// TODO Auto-generated method stub
		return orderDAO.submitBuyerPO(buyerPo);
	}

	@Override
	public List<BuyerPO> getBuyerPoList(String userId) {
		// TODO Auto-generated method stub
		return orderDAO.getBuyerPoList(userId);
	}

	@Override
	public BuyerPO getBuyerPO(String buyerPoNo) {
		// TODO Auto-generated method stub
		return orderDAO.getBuyerPO(buyerPoNo);
	}


	@Override
	public String maxAIno() {
		// TODO Auto-generated method stub
		return orderDAO.maxAIno();
	}

	@Override
	public List<CommonModel> getPurchaseOrders(String userId) {
		// TODO Auto-generated method stub
		return orderDAO.getPurchaseOrders(userId);
	}

	@Override
	public List<CommonModel> Styles(String po) {
		// TODO Auto-generated method stub
		return orderDAO.Styles(po);
	}

	@Override
	public List<CommonModel> Colors(String style, String item) {
		// TODO Auto-generated method stub
		return orderDAO.Colors(style, item);
	}

	@Override
	public List<CommonModel> Items(String buyerorderid,String style) {
		// TODO Auto-generated method stub
		return orderDAO.Items(buyerorderid,style);
	}

	@Override
	public List<CommonModel> AccessoriesItem(String type) {
		// TODO Auto-generated method stub
		return orderDAO.AccessoriesItem(type);
	}

	@Override
	public List<CommonModel> Size(String buyerorderid, String style, String item, String color) {
		// TODO Auto-generated method stub
		return orderDAO.Size(buyerorderid, style, item, color);
	}

	@Override
	public List<CommonModel> Unit() {
		// TODO Auto-generated method stub
		return orderDAO.Unit();
	}

	@Override
	public List<CommonModel> Brands() {
		// TODO Auto-generated method stub
		return orderDAO.Brands();
	}

	@Override
	public List<CommonModel> ShippingMark(String po, String style, String item) {
		// TODO Auto-generated method stub
		return orderDAO.ShippingMark(po, style, item);
	}

	@Override
	public List<CommonModel> AllColors() {
		// TODO Auto-generated method stub
		return orderDAO.AllColors();
	}

	@Override
	public List<CommonModel> SizewiseQty(String buyerorderid, String style,String item,String color,String size) {
		// TODO Auto-generated method stub
		return orderDAO.SizewiseQty(buyerorderid, style, item, color, size);
	}

	@Override
	public List<AccessoriesIndent> getAccessoriesRecyclingData(String query) {
		// TODO Auto-generated method stub
		return orderDAO.getAccessoriesRecyclingData(query);
	}

	@Override
	public List<AccessoriesIndent> getAccessoriesRecyclingDataWithSize(String query,String query2) {
		// TODO Auto-generated method stub
		return orderDAO.getAccessoriesRecyclingDataWithSize(query,query2);
	}

	@Override
	public boolean insertAccessoriesIndent(AccessoriesIndent ai) {
		// TODO Auto-generated method stub
		return orderDAO.insertAccessoriesIndent(ai);
	}

	@Override
	public List<AccessoriesIndent> PendingList() {
		// TODO Auto-generated method stub
		return orderDAO.PendingList();
	}



	@Override
	public List<CommonModel> styleItemsWiseColor(String buyerorderid, String style, String item) {
		// TODO Auto-generated method stub
		return orderDAO.styleItemsWiseColor(buyerorderid, style, item);
	}

	@Override
	public List<AccessoriesIndent> getAccessoriesIndent(String po, String style, String itemname, String itemcolor) {
		// TODO Auto-generated method stub
		return orderDAO.getAccessoriesIndent(po, style, itemname, itemcolor);
	}

	@Override
	public List<AccessoriesIndent> getPendingAccessoriesIndent() {
		// TODO Auto-generated method stub
		return orderDAO.getPendingAccessoriesIndent();
	}

	@Override
	public List<AccessoriesIndent> getAccessoriesIndentItemList(String id) {
		// TODO Auto-generated method stub
		return orderDAO.getAccessoriesIndentItemList(id);
	}

	@Override
	public boolean editAccessoriesIndent(AccessoriesIndent v) {
		// TODO Auto-generated method stub
		return orderDAO.editAccessoriesIndent(v);
	}
	@Override
	public boolean deleteAccessoriesIndent(String accessorienIndentId, String indentAutoId) {
		// TODO Auto-generated method stub
		return orderDAO.deleteAccessoriesIndent(accessorienIndentId, indentAutoId);
	}

	@Override
	public String confirmAccessoriesIndent(String accessoriesIndentId, String accessoriesItems) {
		// TODO Auto-generated method stub
		return orderDAO.confirmAccessoriesIndent(accessoriesIndentId, accessoriesItems);
	}

	@Override
	public List<AccessoriesIndent> getPostedAccessoriesIndent(String userId) {
		// TODO Auto-generated method stub
		return orderDAO.getPostedAccessoriesIndent(userId);
	}

	@Override
	public List<AccessoriesIndent> getZipperIndentItemList(String zipperIndentId) {
		// TODO Auto-generated method stub
		return orderDAO.getZipperIndentItemList(zipperIndentId);
	}
	
	@Override
	public boolean editZipperIndent(AccessoriesIndent v) {
		// TODO Auto-generated method stub
		return orderDAO.editZipperIndent(v);
	}
	
	@Override
	public boolean deleteZipperIndent(String zipperIndentId, String indentAutoId) {
		// TODO Auto-generated method stub
		return orderDAO.deleteZipperIndent(zipperIndentId, indentAutoId);
	}
	
	@Override
	public boolean saveAccessoriesCurton(AccessoriesIndentCarton v) {
		// TODO Auto-generated method stub
		return orderDAO.saveAccessoriesCurton(v);
	}
	
	@Override
	public String confirmZipperIndent(String zipperIndentId, String zipperItems) {
		// TODO Auto-generated method stub
		return orderDAO.confirmZipperIndent(zipperIndentId, zipperItems);
	}
	
	@Override
	public List<AccessoriesIndent> getPostedZipperIndent(String userId) {
		// TODO Auto-generated method stub
		return orderDAO.getPostedZipperIndent(userId);
	}
	
	@Override
	public String confirmCartonIndent(String cartonIndentId,String cartonItems) {
		// TODO Auto-generated method stub
		return orderDAO.confirmCartonIndent(cartonIndentId, cartonItems);
	}

	@Override
	public List<AccessoriesIndentCarton> getAccessoriesIndentCarton(String poNo, String style, String item,
			String itemColor) {
		// TODO Auto-generated method stub
		return orderDAO.getAccessoriesIndentCarton(poNo, style, item, itemColor);
	}

	@Override
	public List<AccessoriesIndentCarton> getAllAccessoriesCartonData(String userId) {
		// TODO Auto-generated method stub
		return orderDAO.getAllAccessoriesCartonData(userId);
	}

	@Override
	public List<AccessoriesIndentCarton> getAccessoriesIndentCartonItemDetails(String id) {
		// TODO Auto-generated method stub
		return orderDAO.getAccessoriesIndentCartonItemDetails(id);
	}

	@Override
	public boolean editAccessoriesCarton(AccessoriesIndentCarton v) {
		// TODO Auto-generated method stub
		return orderDAO.editAccessoriesCarton(v);
	}
	
	@Override
	public boolean deleteAccessoriesCarton(String autoId,String indentId){
		// TODO Auto-generated method stub
		return orderDAO.deleteAccessoriesCarton(autoId, indentId);
	}

	@Override
	public List<String> getPurchaseOrderList(String userId) {
		// TODO Auto-generated method stub
		return orderDAO.getPurchaseOrderList(userId);
	}

	@Override
	public List<FabricsIndent> getFabricsIndentList() {
		// TODO Auto-generated method stub
		return orderDAO.getFabricsIndentList();
	}

	@Override
	public boolean isFabricsIndentExist(FabricsIndent fabricsIndent) {
		// TODO Auto-generated method stub
		return orderDAO.isFabricsIndentExist(fabricsIndent);
	}

	@Override
	public boolean deleteFabricsIndent(String autoId, String indentId) {
		// TODO Auto-generated method stub
		return orderDAO.deleteFabricsIndent(autoId, indentId);
	}

	@Override
	public String confirmFabricsIndent(String fabricsIndentId,String fabricsItems) {
		// TODO Auto-generated method stub
		return orderDAO.confirmFabricsIndent(fabricsIndentId, fabricsItems);
	}

	@Override
	public boolean editFabricsIndent(FabricsIndent fabricsIndent) {
		// TODO Auto-generated method stub
		return orderDAO.editFabricsIndent(fabricsIndent);
	}

	@Override
	public FabricsIndent getFabricsIndentInfo(String autoId) {
		// TODO Auto-generated method stub
		return orderDAO.getFabricsIndentInfo(autoId);
	}

	@Override
	public List<FabricsIndent> getFabricsIndent(String indentId) {
		// TODO Auto-generated method stub
		return orderDAO.getFabricsIndent(indentId);
	}

	@Override
	public List<Style> getPOWiseStyleList(String purchaseOrder) {
		// TODO Auto-generated method stub
		return orderDAO.getPOWiseStyleList(purchaseOrder);
	}

	@Override
	public List<Color> getStyleItemWiseColor(String styleId, String itemId) {
		// TODO Auto-generated method stub
		return orderDAO.getStyleItemWiseColor(styleId, itemId);
	}

	@Override
	public double getOrderQuantity(String purchaseOrder, String styleId, String itemId, String colorId) {
		// TODO Auto-generated method stub
		return orderDAO.getOrderQuantity(purchaseOrder, styleId, itemId, colorId);
	}

	@Override
	public double getOrderQuantityByMultipleId(String purchaseOrder, String styleId, String itemId, String colorId) {
		// TODO Auto-generated method stub
		return orderDAO.getOrderQuantityByMultipleId(purchaseOrder, styleId, itemId, colorId);
	}

	@Override
	public List<CommonModel> BuyerWisePo(String buyerId) {
		// TODO Auto-generated method stub
		return orderDAO.BuyerWisePo(buyerId);
	}


	@Override
	public List<CommonModel> getSampleList() {
		// TODO Auto-generated method stub
		return orderDAO.getSampleList();
	}

	@Override
	public List<CommonModel> getInchargeList() {
		// TODO Auto-generated method stub
		return orderDAO.getInchargeList();
	}

	@Override
	public List<CommonModel> getMerchendizerList() {
		// TODO Auto-generated method stub
		return orderDAO.getMerchendizerList();
	}

	@Override
	public boolean addItemToSampleRequisition(SampleRequisitionItem v) {
		// TODO Auto-generated method stub
		return orderDAO.addItemToSampleRequisition(v);
	}

	@Override
	public List<SampleRequisitionItem> getSampleRequisitionItemList(String userId) {
		// TODO Auto-generated method stub
		return orderDAO.getSampleRequisitionItemList(userId);
	}

	@Override
	public boolean confirmItemToSampleRequisition(SampleRequisitionItem v) {
		// TODO Auto-generated method stub
		return orderDAO.confirmItemToSampleRequisition(v);
	}

	@Override
	public List<SampleRequisitionItem> getSampleRequisitionList(String userId) {
		// TODO Auto-generated method stub
		return orderDAO.getSampleRequisitionList(userId);
	}

	@Override
	public List<SampleRequisitionItem> getSampleRequisitionDetails(String sampleReqId) {
		// TODO Auto-generated method stub
		return orderDAO.getSampleRequisitionDetails(sampleReqId);
	}

	@Override
	public List<AccessoriesItem> getTypeWiseIndentItems(String purchaseOrder, String styleId,
			String type) {
		// TODO Auto-generated method stub
		return orderDAO.getTypeWiseIndentItems(purchaseOrder, styleId, type);
	}
	
	@Override
	public List<AccessoriesItem> getIndentItems(String indentId,String indentType) {
		// TODO Auto-generated method stub
		return orderDAO.getIndentItems(indentId, indentType);
	}
	
	@Override
	public List<Style> getIndentStyles(String indentId,String indentType) {
		// TODO Auto-generated method stub
		return orderDAO.getIndentStyles(indentId, indentType);
	}


	@Override
	public boolean submitPurchaseOrder(PurchaseOrder purchaseOrder) {
		// TODO Auto-generated method stub
		return orderDAO.submitPurchaseOrder(purchaseOrder);
	}

	@Override
	public boolean editPurchaseOrder(PurchaseOrder purchaseOrder) {
		// TODO Auto-generated method stub
		return orderDAO.editPurchaseOrder(purchaseOrder);
	}

	@Override
	public List<PurchaseOrder> getPurchaseOrderSummeryList(String userId) {
		// TODO Auto-generated method stub
		return orderDAO.getPurchaseOrderSummeryList(userId);
	}
	
	@Override
	public List<CommonModel> getPendingIndentList(String userId){
		return orderDAO.getPendingIndentList(userId);
	}

	@Override
	public PurchaseOrder getPurchaseOrder(String poNo,String poType) {
		// TODO Auto-generated method stub
		return orderDAO.getPurchaseOrder(poNo,poType);
	}

	@Override
	public List<PurchaseOrderItem> getPurchaseOrderItemList(AccessoriesIndent accessoriesIndent) {
		// TODO Auto-generated method stub
		return orderDAO.getPurchaseOrderItemList(accessoriesIndent);
	}
	
	@Override
	public List<PurchaseOrderItem> getPurchaseOrderItemListByStyleId(AccessoriesIndent accessoriesIndent) {
		// TODO Auto-generated method stub
		return orderDAO.getPurchaseOrderItemListByStyleId(accessoriesIndent);
	}

	@Override
	public List<SampleRequisitionItem> getIncomepleteSampleRequisitionItemList(String userId) {
		// TODO Auto-generated method stub
		return orderDAO.getIncomepleteSampleRequisitionItemList(userId);
	}

	@Override
	public boolean fileUpload(String uploadFileName, String computerName, String string, String purpose, String user, String buyerName, String purchaseOrder) {
		// TODO Auto-generated method stub
		return orderDAO.fileUpload(uploadFileName, computerName, string, purpose, user ,buyerName,  purchaseOrder);
	}

	@Override
	public List<pg.orderModel.FileUpload> findfiles(String start, String end, String user) {
		// TODO Auto-generated method stub
		return orderDAO.findfiles(start, end, user);
	}

	@Override
	public List<FileUpload> findfiles(String buyerId, String purchaseOrder, int uploadFileType) {
		// TODO Auto-generated method stub
		return orderDAO.findfiles(buyerId, purchaseOrder, uploadFileType);
	}

	@Override
	public boolean fileDownload(String fileName, String user, String string, String computerName) {
		// TODO Auto-generated method stub
		return orderDAO.fileDownload(fileName, user, string, computerName);
	}

	@Override
	public boolean deletefile(String filename,String id) {
		// TODO Auto-generated method stub
		return orderDAO.deletefile(filename,id);
	}

	@Override
	public boolean InstallDataAsSameParticular(String userId, String purchaseOrder, String styleId, String itemId,
			String colorId, String installAccessories, String forAccessories) {
		// TODO Auto-generated method stub
		return orderDAO.InstallDataAsSameParticular(userId, purchaseOrder, styleId, itemId, colorId, installAccessories, forAccessories);
	}



	@Override
	public List<SampleCadAndProduction> getSampleCommentsList() {
		// TODO Auto-generated method stub
		return orderDAO.getSampleCommentsList();
	}
	@Override
	public SampleCadAndProduction getSampleProductionInfo(String sampleCommentsId) {
		// TODO Auto-generated method stub
		return orderDAO.getSampleProductionInfo(sampleCommentsId);
	}

	@Override
	public boolean postSampleProductionInfo(SampleCadAndProduction sampleCadAndProduction) {
		// TODO Auto-generated method stub
		return orderDAO.postSampleProductionInfo(sampleCadAndProduction);
	}

	@Override
	public List<FabricsIndent> getStyleDetailsForFabricsIndent(String userId) {
		// TODO Auto-generated method stub
		return orderDAO.getStyleDetailsForFabricsIndent(userId);
	}

	@Override
	public List<CourierModel> getcourierList() {
		// TODO Auto-generated method stub
		return orderDAO.getcourierList();
	}

	@Override
	public boolean ConfirmParcel(ParcelModel parcel) {
		// TODO Auto-generated method stub
		return orderDAO.ConfirmParcel(parcel);
	}

	@Override
	public List<ParcelModel> parcelList( ) {
		// TODO Auto-generated method stub
		return orderDAO.parcelList();
	}

	@Override
	public ParcelModel getParcelInfo(String id) {
		// TODO Auto-generated method stub
		return orderDAO.getParcelInfo(id);
	}

	@Override
	public List<ParcelModel> getParcelItems(String autoId) {
		// TODO Auto-generated method stub
		return orderDAO.getParcelItems(autoId);
	}

	@Override
	public boolean editParecel(ParcelModel parcel) {
		// TODO Auto-generated method stub
		return orderDAO.editParecel(parcel);
	}

	@Override
	public boolean editParecelItem(ParcelModel parcel) {
		// TODO Auto-generated method stub
		return orderDAO.editParecelItem(parcel);
	}

	@Override
	public boolean sampleCadInsert(SampleCadAndProduction sample) {
		// TODO Auto-generated method stub
		return orderDAO.sampleCadInsert(sample);
	}

	@Override
	public List<SampleCadAndProduction> getSampleComments(String userId) {
		// TODO Auto-generated method stub
		return orderDAO.getSampleComments(userId);
	}

	@Override
	public List<SampleCadAndProduction> getSampleDetails(String id) {
		// TODO Auto-generated method stub
		return orderDAO.getSampleDetails(id);
	}

	@Override
	public boolean editSampleCad(SampleCadAndProduction sample) {
		// TODO Auto-generated method stub
		return orderDAO.editSampleCad(sample);
	}

	@Override
	public List<PurchaseOrder> getPurchaseOrderApprovalList(String fromDate, String toDate,String itemType,String approveType) {
		// TODO Auto-generated method stub
		return orderDAO.getPurchaseOrderApprovalList(fromDate, toDate, itemType,approveType);
	}

	@Override
	public boolean purchaseOrderApproveConfirm(List<PurchaseOrder> purchaseOrderList) {
		// TODO Auto-generated method stub
		return orderDAO.purchaseOrderApproveConfirm(purchaseOrderList);
	}
	@Override
	public List<ProductionPlan> getSampleProduction(String arg0, String arg1, String arg2) {
		// TODO Auto-generated method stub
		System.out.println("it's ok Service");
		return orderDAO.getSampleProduction(arg0,arg1,arg2);
	}

	@Override
	public boolean ConfirmCheckList(CheckListModel checkList) {
		// TODO Auto-generated method stub
		return orderDAO.ConfirmCheckList(checkList);
	}

	@Override
	public List<CheckListModel> getChekList() {
		// TODO Auto-generated method stub
		return orderDAO.getChekList();
	}

	@Override
	public CheckListModel getCheckListInfo(String autoId) {
		// TODO Auto-generated method stub
		return orderDAO.getCheckListInfo(autoId);
	}

	@Override
	public List<CheckListModel> getCheckListItems(String autoId) {
		// TODO Auto-generated method stub
		return orderDAO.getCheckListItems(autoId);
	}

	@Override
	public boolean editCheckList(CheckListModel checkList) {
		// TODO Auto-generated method stub
		return orderDAO.editCheckList(checkList);
	}

	@Override
	public boolean editCheckListItem(CheckListModel checkList) {
		// TODO Auto-generated method stub
		return orderDAO.editCheckListItem(checkList);
	}
	
	// Create by Arman
	
		@Override
		public List<CommonModel> departmentWiseReceiver(String deptId) {
			// TODO Auto-generated method stub
			return orderDAO.departmentWiseReceiver(deptId);
		}


	@Override
	public List<Style> images(Style style) {
		// TODO Auto-generated method stub
		return orderDAO.images(style);
	}

	@Override
	public boolean editStyle(String styleItemAutoId, String buyerId, String itemId, String styleid, String styleNo,
			String size, String date, MultipartFile frontImage, MultipartFile backImage) {
		// TODO Auto-generated method stub
		return orderDAO.editStyle(styleItemAutoId, buyerId, itemId, styleid, styleNo, size, date, frontImage, backImage);
	}

	@Override
	public boolean SaveStyleCreate(String user, String buyerName, String itemName, String styleNo, String size,
			String date, MultipartFile frontimg, MultipartFile backimg) throws SQLException {
		// TODO Auto-generated method stub
		return orderDAO.SaveStyleCreate(user, buyerName, itemName, styleNo, size, date, frontimg, backimg);
	}

	@Override
	public boolean saveFileAccessDetails(CommonModel v) {
		// TODO Auto-generated method stub
		return orderDAO.saveFileAccessDetails(v);
	}

	@Override
	public List<CommonModel> getAllFromFileLogDetails(CommonModel v) {
		// TODO Auto-generated method stub
		return orderDAO.getAllFromFileLogDetails(v);
	}
	
	@Override
	public boolean addNewPermission(CommonModel v) {
		// TODO Auto-generated method stub
		return orderDAO.addNewPermission(v);
	}
	
	@Override
	public boolean deleteSampleRequisitionItem(String sapleAutoId) {
		// TODO Auto-generated method stub
		return orderDAO.deleteSampleRequisitionItem(sapleAutoId);
	}

	@Override
	public List<SampleRequisitionItem> getSampleRequistionItemData(String itemAutoId) {
		// TODO Auto-generated method stub
		return orderDAO.getSampleRequistionItemData(itemAutoId);
	}

	@Override
	public boolean editItemToSampleRequisition(SampleRequisitionItem v) {
		// TODO Auto-generated method stub
		return orderDAO.editItemToSampleRequisition(v);
	}

	@Override
	public List<SampleCadAndProduction> getSampleCadDetails(String sampleCommentId) {
		// TODO Auto-generated method stub
		return orderDAO.getSampleCadDetails(sampleCommentId);
	}

	@Override
	public boolean samplecadfileupload(String smaplecadid, String filename, String user, String uploadedpcip,String searchtype) {
		// TODO Auto-generated method stub
		return orderDAO.samplecadfileupload(smaplecadid, filename, user, uploadedpcip,searchtype);
	}

	@Override
	public List<FileUpload> findsamplecadfiles(String userid, String samplereqid) {
		// TODO Auto-generated method stub
		return orderDAO.findsamplecadfiles(userid, samplereqid);
	}

	@Override
	public boolean deletesamplefile(String filename, String id) {
		// TODO Auto-generated method stub
		return orderDAO.deletesamplefile(filename, id);
	}

	@Override
	public List<SampleRequisitionItem> getAllSampleRequisitionList() {
		// TODO Auto-generated method stub
		return orderDAO.getAllSampleRequisitionList();
	}
	
	@Override
	public List<CommonModel> getStyleWisePurchaseOrder(String styleId) {
		// TODO Auto-generated method stub
		return orderDAO.getStyleWisePurchaseOrder(styleId);
	}

	@Override
	public List<String> getMultifiles(String bpo) {
		// TODO Auto-generated method stub
		return orderDAO.getMultifiles(bpo);
	}

	@Override
	public String getMaxCadId() {
		// TODO Auto-generated method stub
		return orderDAO.getMaxCadId();
	}

	@Override
	public List<String> getMultiCadfiles(String bpo) {
		// TODO Auto-generated method stub
		return orderDAO.getMultiCadfiles(bpo);
	}

	


	public List<SampleCadAndProduction> getSampleCadDetailsForProduction(String sampleCommentId) {
		// TODO Auto-generated method stub
		return orderDAO.getSampleCadDetailsForProduction(sampleCommentId);
	}

	@Override
	public List<SampleRequisitionItem> getSampleRequisitionAndCuttingDetails(String sampleReqId,
			String sampleCommentId) {
		// TODO Auto-generated method stub
		return orderDAO.getSampleRequisitionAndCuttingDetails(sampleReqId, sampleCommentId);
	}


	
	
}