package pg.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pg.dao.StoreDAO;
import pg.orderModel.AccessoriesIndent;
import pg.orderModel.FabricsIndent;
import pg.orderModel.PurchaseOrderItem;
import pg.storeModel.AccessoriesIssue;
import pg.storeModel.AccessoriesIssueReturn;
import pg.storeModel.AccessoriesQualityControl;
import pg.storeModel.AccessoriesReceive;
import pg.storeModel.AccessoriesReturn;
import pg.storeModel.AccessoriesSize;
import pg.storeModel.AccessoriesTransferIn;
import pg.storeModel.AccessoriesTransferOut;
import pg.storeModel.FabricsIssue;
import pg.storeModel.FabricsIssueReturn;
import pg.storeModel.FabricsQualityControl;
import pg.storeModel.FabricsReceive;
import pg.storeModel.FabricsReturn;
import pg.storeModel.FabricsRoll;
import pg.storeModel.FabricsTransferIn;
import pg.storeModel.FabricsTransferOut;
import pg.storeModel.StoreGeneralCategory;
import pg.storeModel.StoreGeneralReceived;

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


	public boolean submitFabricsTransferOut(FabricsTransferOut fabricsTransferOut) {
		// TODO Auto-generated method stub
		return storeDao.submitFabricsTransferOut(fabricsTransferOut);
	}

	@Override
	public boolean editFabricsTransferOut(FabricsTransferOut fabricsTransferOut) {
		// TODO Auto-generated method stub
		return storeDao.editFabricsTransferOut(fabricsTransferOut);
	}

	@Override
	public String editTransferOutdRollInTransaction(FabricsRoll fabricsRoll) {
		// TODO Auto-generated method stub
		return storeDao.editTransferOutdRollInTransaction(fabricsRoll);
	}

	@Override
	public String deleteTransferOutdRollFromTransaction(FabricsRoll fabricsRoll) {
		// TODO Auto-generated method stub
		return storeDao.deleteReturnRollFromTransaction(fabricsRoll);
	}

	@Override
	public List<FabricsTransferOut> getFabricsTransferOutList() {
		// TODO Auto-generated method stub
		return storeDao.getFabricsTransferOutList();
	}

	@Override
	public FabricsTransferOut getFabricsTransferOutInfo(String issueTransectionId) {
		// TODO Auto-generated method stub
		return storeDao.getFabricsTransferOutInfo(issueTransectionId);
	}

	@Override
	public boolean submitFabricsTransferIn(FabricsTransferIn fabricsTransferIn) {
		// TODO Auto-generated method stub
		return storeDao.submitFabricsTransferIn(fabricsTransferIn);
	}

	@Override
	public boolean editFabricsTransferIn(FabricsTransferIn fabricsTransferIn) {
		// TODO Auto-generated method stub
		return storeDao.editFabricsTransferIn(fabricsTransferIn);
	}

	@Override
	public String editTransferIndRollInTransaction(FabricsRoll fabricsRoll) {
		// TODO Auto-generated method stub
		return storeDao.editTransferIndRollInTransaction(fabricsRoll);
	}

	@Override
	public String deleteTransferIndRollFromTransaction(FabricsRoll fabricsRoll) {
		// TODO Auto-generated method stub
		return storeDao.deleteTransferIndRollFromTransaction(fabricsRoll);
	}

	@Override
	public List<FabricsTransferIn> getFabricsTransferInList() {
		// TODO Auto-generated method stub
		return storeDao.getFabricsTransferInList();
	}

	@Override
	public FabricsTransferIn getFabricsTransferInInfo(String transferTransactionId) {
		// TODO Auto-generated method stub
		return storeDao.getFabricsTransferInInfo(transferTransactionId);
	}
	
	@Override
	public boolean isStoreGenralItemExist(StoreGeneralCategory v) {
		// TODO Auto-generated method stub
		return storeDao.isStoreGenralItemExist(v);
	}

	@Override
	public boolean saveGeneralItem(StoreGeneralCategory v) {
		// TODO Auto-generated method stub
		return storeDao.saveGeneralItem(v);
	}

	@Override
	public List<StoreGeneralCategory> getStoreGeneralItemList() {
		// TODO Auto-generated method stub
		return storeDao.getStoreGeneralItemList();
	}

	@Override
	public boolean editGeneralItem(StoreGeneralCategory v) {
		// TODO Auto-generated method stub
		return storeDao.editGeneralItem(v);
	}

	@Override
	public boolean addGeneralReceivedItem(StoreGeneralReceived v) {
		// TODO Auto-generated method stub
		return storeDao.addGeneralReceivedItem(v);
	}

	@Override
	public String getMaxInvoiceId(String string) {
		// TODO Auto-generated method stub
		return storeDao.getMaxInvoiceId(string);
	}

	@Override
	public List<StoreGeneralReceived> getStoreGeneralReceivedItemList(String invoiceNo, String type) {
		// TODO Auto-generated method stub
		return storeDao.getStoreGeneralReceivedItemList(invoiceNo, type);
	}

	@Override
	public boolean confrimtoreGeneralReceivedItemt(StoreGeneralReceived v) {
		// TODO Auto-generated method stub
		return storeDao.confrimtoreGeneralReceivedItemt(v);
	}

	@Override
	public List<StoreGeneralReceived> getStoreGeneralReceivedIList(String string) {
		// TODO Auto-generated method stub
		return storeDao.getStoreGeneralReceivedIList(string);

	}

	
	
	
	
	
	
	
	
	
	
	@Override
	public List<AccessoriesIndent> getAccessoriesPurchaseOrdeIndentrList() {
		// TODO Auto-generated method stub
		return storeDao.getAccessoriesPurchaseOrdeIndentrList();
	}

	@Override
	public AccessoriesIndent getAccessoriesIndentInfo(String autoId) {
		// TODO Auto-generated method stub
		return storeDao.getAccessoriesIndentInfo(autoId);
	}

	@Override
	public boolean submitAccessoriesReceive(AccessoriesReceive accessoriesReceive) {
		// TODO Auto-generated method stub
		return storeDao.submitAccessoriesReceive(accessoriesReceive);
	}

	@Override
	public boolean editAccessoriesReceive(AccessoriesReceive accessoriesReceive) {
		// TODO Auto-generated method stub
		return storeDao.editAccessoriesReceive(accessoriesReceive);
	}

	@Override
	public String editReceiveSizeInTransaction(AccessoriesSize accessoriesSize) {
		// TODO Auto-generated method stub
		return storeDao.editReceiveSizeInTransaction(accessoriesSize);
	}

	@Override
	public String deleteReceiveSizeFromTransaction(AccessoriesSize accessoriesSize) {
		// TODO Auto-generated method stub
		return storeDao.deleteReceiveSizeFromTransaction(accessoriesSize);
	}

	@Override
	public List<AccessoriesReceive> getAccessoriesReceiveList() {
		// TODO Auto-generated method stub
		return storeDao.getAccessoriesReceiveList();
	}

	@Override
	public AccessoriesReceive getAccessoriesReceiveInfo(String transectionId) {
		// TODO Auto-generated method stub
		return storeDao.getAccessoriesReceiveInfo(transectionId);
	}

	@Override
	public boolean submitAccessoriesQC(AccessoriesQualityControl accessoriesReceive) {
		// TODO Auto-generated method stub
		return storeDao.submitAccessoriesQC(accessoriesReceive);
	}

	@Override
	public boolean editAccessoriesQC(AccessoriesQualityControl accessoriesReceive) {
		// TODO Auto-generated method stub
		return storeDao.editAccessoriesQC(accessoriesReceive);
	}

	@Override
	public List<AccessoriesQualityControl> getAccessoriesQCList() {
		// TODO Auto-generated method stub
		return storeDao.getAccessoriesQCList();
	}

	@Override
	public AccessoriesQualityControl getAccessoriesQCInfo(String qcTransectionId) {
		// TODO Auto-generated method stub
		return storeDao.getAccessoriesQCInfo(qcTransectionId);
	}

	@Override
	public List<AccessoriesSize> getAccessoriesSizeListBySupplier(String supplierId) {
		// TODO Auto-generated method stub
		return storeDao.getAccessoriesSizeListBySupplier(supplierId);
	}

	@Override
	public boolean submitAccessoriesReturn(AccessoriesReturn accessoriesReceive) {
		// TODO Auto-generated method stub
		return storeDao.submitAccessoriesReturn(accessoriesReceive);
	}

	@Override
	public boolean editAccessoriesReturn(AccessoriesReturn accessoriesReceive) {
		// TODO Auto-generated method stub
		return storeDao.editAccessoriesReturn(accessoriesReceive);
	}

	@Override
	public String deleteReturnSizeFromTransaction(AccessoriesSize accessoriesSize) {
		// TODO Auto-generated method stub
		return storeDao.deleteReturnSizeFromTransaction(accessoriesSize);
	}

	@Override
	public String editReturnSizeInTransaction(AccessoriesSize accessoriesSize) {
		// TODO Auto-generated method stub
		return storeDao.editReturnSizeInTransaction(accessoriesSize);
	}

	@Override
	public List<AccessoriesReturn> getAccessoriesReturnList() {
		// TODO Auto-generated method stub
		return storeDao.getAccessoriesReturnList();
	}

	@Override
	public AccessoriesReturn getAccessoriesReturnInfo(String returnTransectionId) {
		// TODO Auto-generated method stub
		return storeDao.getAccessoriesReturnInfo(returnTransectionId);
	}

	@Override
	public AccessoriesReceive getAccessoriesReceiveInfoForReturn(String transectionId) {
		// TODO Auto-generated method stub
		return storeDao.getAccessoriesReceiveInfoForReturn(transectionId);
	}

	@Override
	public List<AccessoriesSize> getAvailableAccessoriesSizeListInDepartment(String departmentId) {
		// TODO Auto-generated method stub
		return storeDao.getAvailableAccessoriesSizeListInDepartment(departmentId);
	}

	@Override
	public boolean submitAccessoriesIssue(AccessoriesIssue accessoriesIssue) {
		// TODO Auto-generated method stub
		return storeDao.submitAccessoriesIssue(accessoriesIssue);
	}

	@Override
	public boolean editAccessoriesIssue(AccessoriesIssue accessoriesIssue) {
		// TODO Auto-generated method stub
		return storeDao.editAccessoriesIssue(accessoriesIssue);
	}

	@Override
	public String editIssuedSizeInTransaction(AccessoriesSize accessoriesSize) {
		// TODO Auto-generated method stub
		return storeDao.editIssuedSizeInTransaction(accessoriesSize);
	}

	@Override
	public String deleteIssuedSizeFromTransaction(AccessoriesSize accessoriesSize) {
		// TODO Auto-generated method stub
		return storeDao.deleteIssuedSizeFromTransaction(accessoriesSize);
	}

	@Override
	public List<AccessoriesSize> getIssuedAccessoriesSizeListInDepartment(String departmentId, String returnDepartmentId) {
		// TODO Auto-generated method stub
		return storeDao.getIssuedAccessoriesSizeListInDepartment(departmentId, returnDepartmentId);
	}

	@Override
	public List<AccessoriesIssue> getAccessoriesIssueList() {
		// TODO Auto-generated method stub
		return storeDao.getAccessoriesIssueList();
	}

	@Override
	public AccessoriesIssue getAccessoriesIssueInfo(String issueTransectionId) {
		// TODO Auto-generated method stub
		return storeDao.getAccessoriesIssueInfo(issueTransectionId);
	}

	@Override
	public boolean submitAccessoriesIssueReturn(AccessoriesIssueReturn accessoriesIssueReturn) {
		// TODO Auto-generated method stub
		return storeDao.submitAccessoriesIssueReturn(accessoriesIssueReturn);
	}

	@Override
	public boolean editAccessoriesIssueReturn(AccessoriesIssueReturn accessoriesIssueReturn) {
		// TODO Auto-generated method stub
		return storeDao.editAccessoriesIssueReturn(accessoriesIssueReturn);
	}

	@Override
	public String editIssueReturndSizeInTransaction(AccessoriesSize accessoriesSize) {
		// TODO Auto-generated method stub
		return storeDao.editIssueReturndSizeInTransaction(accessoriesSize);
	}

	@Override
	public String deleteIssueReturndSizeFromTransaction(AccessoriesSize accessoriesSize) {
		// TODO Auto-generated method stub
		return storeDao.deleteIssueReturndSizeFromTransaction(accessoriesSize);
	}

	@Override
	public List<AccessoriesIssueReturn> getAccessoriesIssueReturnList() {
		// TODO Auto-generated method stub
		return storeDao.getAccessoriesIssueReturnList();
	}

	@Override
	public AccessoriesIssueReturn getAccessoriesIssueReturnInfo(String issueReturnTransectionId) {
		// TODO Auto-generated method stub
		return storeDao.getAccessoriesIssueReturnInfo(issueReturnTransectionId);
	}

	@Override
	public boolean submitAccessoriesTransferOut(AccessoriesTransferOut accessoriesTransferOut) {
		// TODO Auto-generated method stub
		return storeDao.submitAccessoriesTransferOut(accessoriesTransferOut);
	}

	@Override
	public boolean editAccessoriesTransferOut(AccessoriesTransferOut accessoriesTransferOut) {
		// TODO Auto-generated method stub
		return storeDao.editAccessoriesTransferOut(accessoriesTransferOut);
	}

	@Override
	public String editTransferOutdSizeInTransaction(AccessoriesSize accessoriesSize) {
		// TODO Auto-generated method stub
		return storeDao.editTransferOutdSizeInTransaction(accessoriesSize);
	}

	@Override
	public String deleteTransferOutdSizeFromTransaction(AccessoriesSize accessoriesSize) {
		// TODO Auto-generated method stub
		return storeDao.deleteReturnSizeFromTransaction(accessoriesSize);
	}

	@Override
	public List<AccessoriesTransferOut> getAccessoriesTransferOutList() {
		// TODO Auto-generated method stub
		return storeDao.getAccessoriesTransferOutList();
	}

	@Override
	public AccessoriesTransferOut getAccessoriesTransferOutInfo(String issueTransectionId) {
		// TODO Auto-generated method stub
		return storeDao.getAccessoriesTransferOutInfo(issueTransectionId);
	}

	@Override
	public boolean submitAccessoriesTransferIn(AccessoriesTransferIn accessoriesTransferIn) {
		// TODO Auto-generated method stub
		return storeDao.submitAccessoriesTransferIn(accessoriesTransferIn);
	}

	@Override
	public boolean editAccessoriesTransferIn(AccessoriesTransferIn accessoriesTransferIn) {
		// TODO Auto-generated method stub
		return storeDao.editAccessoriesTransferIn(accessoriesTransferIn);
	}

	@Override
	public String editTransferIndSizeInTransaction(AccessoriesSize accessoriesSize) {
		// TODO Auto-generated method stub
		return storeDao.editTransferIndSizeInTransaction(accessoriesSize);
	}

	@Override
	public String deleteTransferIndSizeFromTransaction(AccessoriesSize accessoriesSize) {
		// TODO Auto-generated method stub
		return storeDao.deleteTransferIndSizeFromTransaction(accessoriesSize);
	}

	@Override
	public List<AccessoriesTransferIn> getAccessoriesTransferInList() {
		// TODO Auto-generated method stub
		return storeDao.getAccessoriesTransferInList();
	}

	@Override
	public AccessoriesTransferIn getAccessoriesTransferInInfo(String transferTransactionId) {
		// TODO Auto-generated method stub
		return storeDao.getAccessoriesTransferInInfo(transferTransactionId);
	}


}
