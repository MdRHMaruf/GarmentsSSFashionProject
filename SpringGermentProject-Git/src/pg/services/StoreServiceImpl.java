package pg.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pg.dao.StoreDAO;
import pg.orderModel.FabricsIndent;
import pg.orderModel.PurchaseOrderItem;
import pg.storeModel.FabricsIssue;
import pg.storeModel.FabricsIssueReturn;
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
	public List<FabricsRoll> getFabricsRollListBySupplier(String supplierId) {
		// TODO Auto-generated method stub
		return storeDao.getFabricsRollListBySupplier(supplierId);
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
	public String deleteReturnRollFromTransaction(FabricsRoll fabricsRoll) {
		// TODO Auto-generated method stub
		return storeDao.deleteReturnRollFromTransaction(fabricsRoll);
	}

	@Override
	public String editReturnRollInTransaction(FabricsRoll fabricsRoll) {
		// TODO Auto-generated method stub
		return storeDao.editReturnRollInTransaction(fabricsRoll);
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

	@Override
	public List<FabricsRoll> getAvailableFabricsRollListInDepartment(String departmentId) {
		// TODO Auto-generated method stub
		return storeDao.getAvailableFabricsRollListInDepartment(departmentId);
	}

	@Override
	public boolean submitFabricsIssue(FabricsIssue fabricsIssue) {
		// TODO Auto-generated method stub
		return storeDao.submitFabricsIssue(fabricsIssue);
	}

	@Override
	public boolean editFabricsIssue(FabricsIssue fabricsIssue) {
		// TODO Auto-generated method stub
		return storeDao.editFabricsIssue(fabricsIssue);
	}

	@Override
	public String editIssuedRollInTransaction(FabricsRoll fabricsRoll) {
		// TODO Auto-generated method stub
		return storeDao.editIssuedRollInTransaction(fabricsRoll);
	}

	@Override
	public String deleteIssuedRollFromTransaction(FabricsRoll fabricsRoll) {
		// TODO Auto-generated method stub
		return storeDao.deleteIssuedRollFromTransaction(fabricsRoll);
	}

	@Override
	public List<FabricsRoll> getIssuedFabricsRollListInDepartment(String departmentId, String returnDepartmentId) {
		// TODO Auto-generated method stub
		return storeDao.getIssuedFabricsRollListInDepartment(departmentId, returnDepartmentId);
	}

	@Override
	public List<FabricsIssue> getFabricsIssueList() {
		// TODO Auto-generated method stub
		return storeDao.getFabricsIssueList();
	}

	@Override
	public FabricsIssue getFabricsIssueInfo(String issueTransectionId) {
		// TODO Auto-generated method stub
		return storeDao.getFabricsIssueInfo(issueTransectionId);
	}

	@Override
	public boolean submitFabricsIssueReturn(FabricsIssueReturn fabricsIssueReturn) {
		// TODO Auto-generated method stub
		return storeDao.submitFabricsIssueReturn(fabricsIssueReturn);
	}

	@Override
	public boolean editFabricsIssueReturn(FabricsIssueReturn fabricsIssueReturn) {
		// TODO Auto-generated method stub
		return storeDao.editFabricsIssueReturn(fabricsIssueReturn);
	}

	@Override
	public String editIssueReturndRollInTransaction(FabricsRoll fabricsRoll) {
		// TODO Auto-generated method stub
		return storeDao.editIssueReturndRollInTransaction(fabricsRoll);
	}

	@Override
	public String deleteIssueReturndRollFromTransaction(FabricsRoll fabricsRoll) {
		// TODO Auto-generated method stub
		return storeDao.deleteIssueReturndRollFromTransaction(fabricsRoll);
	}

	@Override
	public List<FabricsIssueReturn> getFabricsIssueReturnList() {
		// TODO Auto-generated method stub
		return storeDao.getFabricsIssueReturnList();
	}

	@Override
	public FabricsIssueReturn getFabricsIssueReturnInfo(String issueReturnTransectionId) {
		// TODO Auto-generated method stub
		return storeDao.getFabricsIssueReturnInfo(issueReturnTransectionId);
	}

	

}
