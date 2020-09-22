package pg.dao;

import java.util.List;

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

public interface StoreDAO {

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

	//Fabrics TransferOut
	boolean submitFabricsTransferOut(FabricsTransferOut fabricsTransferOut);
	boolean editFabricsTransferOut(FabricsTransferOut fabricsTransferOut);
	String editTransferOutdRollInTransaction(FabricsRoll fabricsRoll);
	String deleteTransferOutdRollFromTransaction(FabricsRoll fabricsRoll);
	List<FabricsTransferOut> getFabricsTransferOutList();
	FabricsTransferOut getFabricsTransferOutInfo(String issueTransectionId);

	//Fabrics TransferIn
	boolean submitFabricsTransferIn(FabricsTransferIn fabricsTransferIn);
	boolean editFabricsTransferIn(FabricsTransferIn fabricsTransferIn);
	String editTransferIndRollInTransaction(FabricsRoll fabricsRoll);
	String deleteTransferIndRollFromTransaction(FabricsRoll fabricsRoll);
	List<FabricsTransferIn> getFabricsTransferInList();
	FabricsTransferIn getFabricsTransferInInfo(String issueTransectionId);
	
	
	
	//Accessories Receive
		List<AccessoriesIndent> getAccessoriesPurchaseOrdeIndentrList();
		AccessoriesIndent getAccessoriesIndentInfo(String autoId);
		boolean submitAccessoriesReceive(AccessoriesReceive accessoriesReceive);
		boolean editAccessoriesReceive(AccessoriesReceive accessoriesReceive);
		String editReceiveRollInTransaction(AccessoriesSize accessoriesRoll);
		String deleteReceiveRollFromTransaction(AccessoriesSize accessoriesRoll);
		List<AccessoriesReceive> getAccessoriesReceiveList();
		AccessoriesReceive getAccessoriesReceiveInfo(String transectionId);

		//Accessories Quality Control
		boolean submitAccessoriesQC(AccessoriesQualityControl accessoriesReceive);
		boolean editAccessoriesQC(AccessoriesQualityControl accessoriesReceive);
		List<AccessoriesQualityControl> getAccessoriesQCList();
		AccessoriesQualityControl getAccessoriesQCInfo(String qcTransectionId);


		//Accessories Return
		List<AccessoriesSize> getAccessoriesRollListBySupplier(String supplierId);
		boolean submitAccessoriesReturn(AccessoriesReturn accessoriesReceive);
		boolean editAccessoriesReturn(AccessoriesReturn accessoriesReceive);
		String editReturnRollInTransaction(AccessoriesSize accessoriesRoll);
		String deleteReturnRollFromTransaction(AccessoriesSize accessoriesRoll);
		List<AccessoriesReturn> getAccessoriesReturnList();
		AccessoriesReturn getAccessoriesReturnInfo(String returnTransectionId);
		AccessoriesReceive getAccessoriesReceiveInfoForReturn(String transectionId);


		//Accessories Issue
		List<AccessoriesSize> getAvailableAccessoriesRollListInDepartment(String departmentId);
		boolean submitAccessoriesIssue(AccessoriesIssue accessoriesIssue);
		boolean editAccessoriesIssue(AccessoriesIssue accessoriesIssue);
		String editIssuedRollInTransaction(AccessoriesSize accessoriesRoll);
		String deleteIssuedRollFromTransaction(AccessoriesSize accessoriesRoll);
		List<AccessoriesIssue> getAccessoriesIssueList();
		AccessoriesIssue getAccessoriesIssueInfo(String issueTransectionId);

		//Accessories Issue Return
		List<AccessoriesSize> getIssuedAccessoriesRollListInDepartment(String departmentId,String returnDepartmentId);
		boolean submitAccessoriesIssueReturn(AccessoriesIssueReturn accessoriesIssueReturn);
		boolean editAccessoriesIssueReturn(AccessoriesIssueReturn accessoriesIssueReturn);
		String editIssueReturndRollInTransaction(AccessoriesSize accessoriesRoll);
		String deleteIssueReturndRollFromTransaction(AccessoriesSize accessoriesRoll);
		List<AccessoriesIssueReturn> getAccessoriesIssueReturnList();
		AccessoriesIssueReturn getAccessoriesIssueReturnInfo(String issueReturnTransectionId);

		//Accessories TransferOut
		boolean submitAccessoriesTransferOut(AccessoriesTransferOut accessoriesTransferOut);
		boolean editAccessoriesTransferOut(AccessoriesTransferOut accessoriesTransferOut);
		String editTransferOutdRollInTransaction(AccessoriesSize accessoriesRoll);
		String deleteTransferOutdRollFromTransaction(AccessoriesSize accessoriesRoll);
		List<AccessoriesTransferOut> getAccessoriesTransferOutList();
		AccessoriesTransferOut getAccessoriesTransferOutInfo(String issueTransectionId);

		//Accessories TransferIn
		boolean submitAccessoriesTransferIn(AccessoriesTransferIn accessoriesTransferIn);
		boolean editAccessoriesTransferIn(AccessoriesTransferIn accessoriesTransferIn);
		String editTransferIndRollInTransaction(AccessoriesSize accessoriesRoll);
		String deleteTransferIndRollFromTransaction(AccessoriesSize accessoriesRoll);
		List<AccessoriesTransferIn> getAccessoriesTransferInList();
		AccessoriesTransferIn getAccessoriesTransferInInfo(String issueTransectionId);

}
