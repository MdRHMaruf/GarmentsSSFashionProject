package pg.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pg.dao.StoreDAO;
import pg.orderModel.FabricsIndent;
import pg.orderModel.PurchaseOrderItem;
import pg.storeModel.FabricsQualityControl;
import pg.storeModel.FabricsReceive;

@Service
public class StoreServiceImpl implements StoreService{
	
	@Autowired
	StoreDAO storeDao;

	@Override
	public List<PurchaseOrderItem> getAccessoriesPurchaseOrderList() {
		// TODO Auto-generated method stub
		return storeDao.getAccessoriesPurchaseOrderList();
	}

	@Override
	public List<FabricsIndent> getFabricsListByItemId(String purchaseOrder, String styleId, String itemId) {
		// TODO Auto-generated method stub
		return storeDao.getFabricsListByItemId(purchaseOrder, styleId, itemId);
	}

	@Override
	public boolean submitFabricsReceive(FabricsReceive fabricsReceive) {
		// TODO Auto-generated method stub
		return storeDao.submitFabricsReceive(fabricsReceive);
	}

	@Override
	public boolean editFabricsReceive(FabricsReceive fabricsReceive) {
		// TODO Auto-generated method stub
		return storeDao.editFabricsReceive(fabricsReceive);
	}

	@Override
	public List<FabricsReceive> getFabricsReceiveList() {
		// TODO Auto-generated method stub
		return storeDao.getFabricsReceiveList();
	}

	@Override
	public FabricsReceive getFabricsReceiveInfo(String transectionId) {
		// TODO Auto-generated method stub
		return storeDao.getFabricsReceiveInfo(transectionId);
	}

	@Override
	public boolean submitFabricsQC(FabricsQualityControl fabricsReceive) {
		// TODO Auto-generated method stub
		return storeDao.submitFabricsQC(fabricsReceive);
	}

	@Override
	public boolean editFabricsQC(FabricsQualityControl fabricsReceive) {
		// TODO Auto-generated method stub
		return storeDao.editFabricsQC(fabricsReceive);
	}

	@Override
	public List<FabricsQualityControl> getFabricsQCList() {
		// TODO Auto-generated method stub
		return storeDao.getFabricsQCList();
	}

	@Override
	public FabricsQualityControl getFabricsQCInfo(String qcTransectionId) {
		// TODO Auto-generated method stub
		return storeDao.getFabricsQCInfo(qcTransectionId);
	}

	

}
