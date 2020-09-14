package pg.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pg.dao.StoreDAO;
import pg.orderModel.FabricsIndent;
import pg.orderModel.PurchaseOrderItem;
import pg.storeModel.FabricsQualityControl;
import pg.storeModel.FabricsReceive;
import pg.storeModel.FabricsReturn;
import pg.storeModel.FabricsRoll;

@Service
public class StoreServiceImpl implements StoreService{
	
	@Autowired
	StoreDAO storeDao;
	@Override
	public List<FabricsIndent> getFabricsPurchaseOrdeIndentrList() {
		// TODO Auto-generated method stub
		return storeDao.getFabricsPurchaseOrdeIndentrList();
	}

	@Override
	public FabricsIndent getFabricsIndentInfo(String autoId) {
		// TODO Auto-generated method stub
		return storeDao.getFabricsIndentInfo(autoId);
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
	public String editReceiveRollInTransaction(FabricsRoll fabricsRoll) {
		// TODO Auto-generated method stub
		return storeDao.editReceiveRollInTransaction(fabricsRoll);
	}

	@Override
	public String deleteReceiveRollFromTransaction(FabricsRoll fabricsRoll) {
		// TODO Auto-generated method stub
		return storeDao.deleteReceiveRollFromTransaction(fabricsRoll);
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

	@Override
	public List<FabricsRoll> getFabricsRollList(String supplierId) {
		// TODO Auto-generated method stub
		return storeDao.getFabricsRollList(supplierId);
	}

	@Override
	public boolean submitFabricsReturn(FabricsReturn fabricsReceive) {
		// TODO Auto-generated method stub
		return storeDao.submitFabricsReturn(fabricsReceive);
	}

	@Override
	public boolean editFabricsReturn(FabricsReturn fabricsReceive) {
		// TODO Auto-generated method stub
		return storeDao.editFabricsReturn(fabricsReceive);
	}

	@Override
	public List<FabricsReturn> getFabricsReturnList() {
		// TODO Auto-generated method stub
		return storeDao.getFabricsReturnList();
	}

	@Override
	public FabricsReturn getFabricsReturnInfo(String returnTransectionId) {
		// TODO Auto-generated method stub
		return storeDao.getFabricsReturnInfo(returnTransectionId);
	}

	@Override
	public FabricsReceive getFabricsReceiveInfoForReturn(String transectionId) {
		// TODO Auto-generated method stub
		return storeDao.getFabricsReceiveInfoForReturn(transectionId);
	}

	

}
