
package pg.services;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.sql.ordering.antlr.OrderByAliasResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pg.dao.OrderDAO;
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
import pg.registerModel.Color;
import pg.registerModel.Factory;
import pg.registerModel.ItemDescription;
import pg.registerModel.ParticularItem;
import pg.registerModel.StyleItem;
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
	public List<ItemDescription> getStyleWiseItem(String styleId) {
		// TODO Auto-generated method stub
		return orderDAO.getStyleWiseItem(styleId);
	}

	@Override
	public List<Style> getStyleList() {
		// TODO Auto-generated method stub
		return orderDAO.getStyleList();
	}

	@Override
	public List<Style> getStyleWiseItemList() {
		// TODO Auto-generated method stub
		return orderDAO.getStyleWiseItemList();
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
	public List<Costing> getCostingList(String styleId, String itemId) {
		// TODO Auto-generated method stub
		return orderDAO.getCostingList(styleId, itemId);
	}

	@Override
	public List<Costing> getCostingList() {
		// TODO Auto-generated method stub
		return orderDAO.getCostingList();
	}

	@Override
	public Costing getCostingItem(String autoId) {
		// TODO Auto-generated method stub
		return orderDAO.getCostingItem(autoId);
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
	public List<BuyerPoItem> getBuyerPOItemList(String buyerPOId) {
		// TODO Auto-generated method stub
		return orderDAO.getBuyerPOItemList(buyerPOId);
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
	public List<BuyerPO> getBuyerPoList() {
		// TODO Auto-generated method stub
		return orderDAO.getBuyerPoList();
	}

	@Override
	public BuyerPO getBuyerPO(String buyerPoNo) {
		// TODO Auto-generated method stub
		return orderDAO.getBuyerPO(buyerPoNo);
	}

	@Override
	public boolean SaveStyleCreate(String user, String buyerName, String itemName, String styleNo, String size,
			String date, String frontimg, String backimg) throws SQLException {
		// TODO Auto-generated method stub
		return orderDAO.SaveStyleCreate(user, buyerName, itemName, styleNo, size, date, frontimg, backimg);
	}

	@Override
	public String maxAIno() {
		// TODO Auto-generated method stub
		return orderDAO.maxAIno();
	}

	@Override
	public List<commonModel> PurchaseOrders() {
		// TODO Auto-generated method stub
		return orderDAO.PurchaseOrders();
	}

	@Override
	public List<commonModel> Styles(String po) {
		// TODO Auto-generated method stub
		return orderDAO.Styles(po);
	}

	@Override
	public List<commonModel> Colors(String style, String item) {
		// TODO Auto-generated method stub
		return orderDAO.Colors(style, item);
	}

	@Override
	public List<commonModel> Items(String buyerorderid,String style) {
		// TODO Auto-generated method stub
		return orderDAO.Items(buyerorderid,style);
	}

	@Override
	public List<commonModel> AccessoriesItem(String type) {
		// TODO Auto-generated method stub
		return orderDAO.AccessoriesItem(type);
	}

	@Override
	public List<commonModel> Size(String buyerorderid, String style, String item, String color) {
		// TODO Auto-generated method stub
		return orderDAO.Size(buyerorderid, style, item, color);
	}

	@Override
	public List<commonModel> Unit() {
		// TODO Auto-generated method stub
		return orderDAO.Unit();
	}

	@Override
	public List<commonModel> Brands() {
		// TODO Auto-generated method stub
		return orderDAO.Brands();
	}

	@Override
	public List<commonModel> ShippingMark(String po, String style, String item) {
		// TODO Auto-generated method stub
		return orderDAO.ShippingMark(po, style, item);
	}

	@Override
	public List<commonModel> AllColors() {
		// TODO Auto-generated method stub
		return orderDAO.AllColors();
	}

	@Override
	public List<commonModel> SizewiseQty(String buyerorderid, String style,String item,String color,String size) {
		// TODO Auto-generated method stub
		return orderDAO.SizewiseQty(buyerorderid, style, item, color, size);
	}

	@Override
	public boolean insertaccessoriesIndent(AccessoriesIndent ai) {
		// TODO Auto-generated method stub
		return orderDAO.insertaccessoriesIndent(ai);
	}

	@Override
	public List<AccessoriesIndent> PendingList() {
		// TODO Auto-generated method stub
		return orderDAO.PendingList();
	}

	@Override
	public boolean cloningCosting(String oldStyleId, String oldItemId, String newStyleId, String newItemId,
			String userId) {
		// TODO Auto-generated method stub
		return orderDAO.cloningCosting(oldStyleId, oldItemId, newStyleId, newItemId, userId);
	}

	@Override
	public List<commonModel> styleItemsWiseColor(String buyerorderid, String style, String item) {
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
	public List<AccessoriesIndent> getAccessoriesIndentItemDetails(String id) {
		// TODO Auto-generated method stub
		return orderDAO.getAccessoriesIndentItemDetails(id);
	}

	@Override
	public boolean editaccessoriesIndent(AccessoriesIndent v) {
		// TODO Auto-generated method stub
		return orderDAO.editaccessoriesIndent(v);
	}

	@Override
	public boolean confrimAccessoriesIndent(String user, String aiNo) {
		// TODO Auto-generated method stub
		return orderDAO.confrimAccessoriesIndent(user, aiNo);
	}
	
	@Override
	public List<AccessoriesIndent> getPostedAccessoriesIndent() {
		// TODO Auto-generated method stub
		return orderDAO.getPostedAccessoriesIndent();
	}

	@Override
	public boolean saveAccessoriesCurton(accessoriesindentcarton v) {
		// TODO Auto-generated method stub
		return orderDAO.saveAccessoriesCurton(v);
	}

	@Override
	public List<accessoriesindentcarton> getAccessoriesIndentCarton(String poNo, String style, String item,
			String itemColor) {
		// TODO Auto-generated method stub
		return orderDAO.getAccessoriesIndentCarton(poNo, style, item, itemColor);
	}

	@Override
	public List<accessoriesindentcarton> getAllAccessoriesCartonData() {
		// TODO Auto-generated method stub
		return orderDAO.getAllAccessoriesCartonData();
	}

	@Override
	public List<accessoriesindentcarton> getAccessoriesIndentCartonItemDetails(String id) {
		// TODO Auto-generated method stub
		return orderDAO.getAccessoriesIndentCartonItemDetails(id);
	}

	@Override
	public boolean editAccessoriesCurton(accessoriesindentcarton v) {
		// TODO Auto-generated method stub
		return orderDAO.editAccessoriesCurton(v);
	}

	@Override
	public List<String> getPurchaseOrderList() {
		// TODO Auto-generated method stub
		return orderDAO.getPurchaseOrderList();
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
	public boolean saveFabricsIndent(FabricsIndent fabricsIndent) {
		// TODO Auto-generated method stub
		return orderDAO.saveFabricsIndent(fabricsIndent);
	}

	@Override
	public boolean editFabricsIndent(FabricsIndent fabricsIndent) {
		// TODO Auto-generated method stub
		return orderDAO.editFabricsIndent(fabricsIndent);
	}

	@Override
	public FabricsIndent getFabricsIndent(String indentId) {
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
	public List<commonModel> BuyerWisePo(String buyerId) {
		// TODO Auto-generated method stub
		return orderDAO.BuyerWisePo(buyerId);
	}

	
	@Override
	public List<commonModel> getSampleList() {
		// TODO Auto-generated method stub
		return orderDAO.getSampleList();
	}

	@Override
	public List<commonModel> getInchargeList() {
		// TODO Auto-generated method stub
		return orderDAO.getInchargeList();
	}

	@Override
	public List<commonModel> getMerchendizerList() {
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
	public boolean confrimItemToSampleRequisition(SampleRequisitionItem v) {
		// TODO Auto-generated method stub
		return orderDAO.confrimItemToSampleRequisition(v);
	}

	@Override
	public List<SampleRequisitionItem> getSampleRequisitionList() {
		// TODO Auto-generated method stub
		return orderDAO.getSampleRequisitionList();
	}

	@Override
	public List<SampleRequisitionItem> getSampleRequisitionDetails(String sampleReqId) {
		// TODO Auto-generated method stub
		return orderDAO.getSampleRequisitionDetails(sampleReqId);
	}

	@Override
	public List<pg.registerModel.AccessoriesItem> getTypeWiseIndentItems(String purchaseOrder, String styleId,
			String type) {
		// TODO Auto-generated method stub
		return orderDAO.getTypeWiseIndentItems(purchaseOrder, styleId, type);
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
	public List<PurchaseOrder> getPurchaseOrderSummeryList() {
		// TODO Auto-generated method stub
		return orderDAO.getPurchaseOrderSummeryList();
	}

	@Override
	public PurchaseOrder getPurchaseOrder(String poNo) {
		// TODO Auto-generated method stub
		return orderDAO.getPurchaseOrder(poNo);
	}

	@Override
	public List<PurchaseOrderItem> getPurchaseOrderItemList(PurchaseOrderItem purchaseOrderItem) {
		// TODO Auto-generated method stub
		return orderDAO.getPurchaseOrderItemList(purchaseOrderItem);
	}

	@Override
	public List<SampleRequisitionItem> getIncomepleteSampleRequisitionItemList(String userId) {
		// TODO Auto-generated method stub
		return orderDAO.getIncomepleteSampleRequisitionItemList(userId);
	}

	@Override
	public boolean fileUpload(String uploadFileName, String computerName, String string, String purpose, String user) {
		// TODO Auto-generated method stub
		return orderDAO.fileUpload(uploadFileName, computerName, string, purpose, user);
	}

	@Override
	public List<pg.orderModel.fileUpload> findfiles(String start, String end, String user) {
		// TODO Auto-generated method stub
		return orderDAO.findfiles(start, end, user);
	}

	@Override
	public boolean fileDownload(String fileName, String user, String string, String computerName) {
		// TODO Auto-generated method stub
		return orderDAO.fileDownload(fileName, user, string, computerName);
	}

	@Override
	public boolean deletefile(String filename) {
		// TODO Auto-generated method stub
		return orderDAO.deletefile(filename);
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

	

}

