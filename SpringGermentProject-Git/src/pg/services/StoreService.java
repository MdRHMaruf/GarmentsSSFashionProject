package pg.services;

import java.util.List;

import pg.orderModel.FabricsIndent;
import pg.orderModel.PurchaseOrderItem;
import pg.storeModel.FabricsQualityControl;
import pg.storeModel.FabricsReceive;

public interface StoreService {
	//Fabrics Receive
	List<PurchaseOrderItem> getAccessoriesPurchaseOrderList();
	List<FabricsIndent> getFabricsListByItemId(String purchaseOrder,String styleId,String itemId);
	boolean submitFabricsReceive(FabricsReceive fabricsReceive);
	boolean editFabricsReceive(FabricsReceive fabricsReceive);
	List<FabricsReceive> getFabricsReceiveList();
	FabricsReceive getFabricsReceiveInfo(String transectionId);
	
	
	//Fabrics Quality Control
	boolean submitFabricsQC(FabricsQualityControl fabricsReceive);
	boolean editFabricsQC(FabricsQualityControl fabricsReceive);
	List<FabricsQualityControl> getFabricsQCList();
	FabricsQualityControl getFabricsQCInfo(String qcTransectionId);
}
