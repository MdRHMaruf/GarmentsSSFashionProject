package pg.services;

import java.util.List;

import pg.orderModel.FabricsIndent;
import pg.orderModel.PurchaseOrderItem;
import pg.storeModel.FabricsQualityControl;
import pg.storeModel.FabricsReceive;
import pg.storeModel.FabricsReturn;

public interface StoreService {
	//Fabrics Receive
	List<FabricsIndent> getFabricsPurchaseOrdeIndentrList();
	FabricsIndent getFabricsIndentInfo(String autoId);
	boolean submitFabricsReceive(FabricsReceive fabricsReceive);
	boolean editFabricsReceive(FabricsReceive fabricsReceive);
	List<FabricsReceive> getFabricsReceiveList();
	FabricsReceive getFabricsReceiveInfo(String transectionId);


	//Fabrics Quality Control
	boolean submitFabricsQC(FabricsQualityControl fabricsReceive);
	boolean editFabricsQC(FabricsQualityControl fabricsReceive);
	List<FabricsQualityControl> getFabricsQCList();
	FabricsQualityControl getFabricsQCInfo(String qcTransectionId);

	//Fabrics Return
	boolean submitFabricsReturn(FabricsReturn fabricsReceive);
	boolean editFabricsReturn(FabricsReturn fabricsReceive);
	List<FabricsReturn> getFabricsReturnList();
	FabricsReturn getFabricsReturnInfo(String returnTransectionId);
	FabricsReceive getFabricsReceiveInfoForReturn(String transectionId);
}
