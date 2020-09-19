package pg.services;

import java.util.List;

import pg.orderModel.FabricsIndent;
import pg.orderModel.PurchaseOrderItem;
import pg.storeModel.FabricsIssue;
import pg.storeModel.FabricsIssueReturn;
import pg.storeModel.FabricsQualityControl;
import pg.storeModel.FabricsReceive;
import pg.storeModel.FabricsReturn;
import pg.storeModel.FabricsRoll;

public interface StoreService {
	//Fabrics Receive
	List<FabricsIndent> getFabricsPurchaseOrdeIndentrList();
	FabricsIndent getFabricsIndentInfo(String autoId);
	boolean submitFabricsReceive(FabricsReceive fabricsReceive);
	boolean editFabricsReceive(FabricsReceive fabricsReceive);
	String editReceiveRollInTransaction(FabricsRoll fabricsRoll);
	String deleteReceiveRollFromTransaction(FabricsRoll fabricsRoll);
	List<FabricsReceive> getFabricsReceiveList();
	FabricsReceive getFabricsReceiveInfo(String transectionId);


	//Fabrics Quality Control
	boolean submitFabricsQC(FabricsQualityControl fabricsReceive);
	boolean editFabricsQC(FabricsQualityControl fabricsReceive);
	List<FabricsQualityControl> getFabricsQCList();
	FabricsQualityControl getFabricsQCInfo(String qcTransectionId);

	//Fabrics Return
	List<FabricsRoll> getFabricsRollListBySupplier(String supplierId);
	boolean submitFabricsReturn(FabricsReturn fabricsReceive);
	boolean editFabricsReturn(FabricsReturn fabricsReceive);
	String editReturnRollInTransaction(FabricsRoll fabricsRoll);
	String deleteReturnRollFromTransaction(FabricsRoll fabricsRoll);
	List<FabricsReturn> getFabricsReturnList();
	FabricsReturn getFabricsReturnInfo(String returnTransectionId);
	FabricsReceive getFabricsReceiveInfoForReturn(String transectionId);

	//Fabrics Issue
	List<FabricsRoll> getAvailableFabricsRollListInDepartment(String departmentId);
	boolean submitFabricsIssue(FabricsIssue fabricsIssue);
	boolean editFabricsIssue(FabricsIssue fabricsIssue);
	String editIssuedRollInTransaction(FabricsRoll fabricsRoll);
	String deleteIssuedRollFromTransaction(FabricsRoll fabricsRoll);
	List<FabricsIssue> getFabricsIssueList();
	FabricsIssue getFabricsIssueInfo(String issueTransectionId);

	//Fabrics Issue Return
	List<FabricsRoll> getIssuedFabricsRollListInDepartment(String departmentId,String returnDepartmentId);
	boolean submitFabricsIssueReturn(FabricsIssueReturn fabricsIssueReturn);
	boolean editFabricsIssueReturn(FabricsIssueReturn fabricsIssueReturn);
	String editIssueReturndRollInTransaction(FabricsRoll fabricsRoll);
	String deleteIssueReturndRollFromTransaction(FabricsRoll fabricsRoll);
	List<FabricsIssueReturn> getFabricsIssueReturnList();
	FabricsIssueReturn getFabricsIssueReturnInfo(String issueReturnTransectionId);
}
