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
import pg.storeModel.StoreGeneralCategory;
import pg.storeModel.StoreGeneralReceived;

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
	
	//General Store Item
	boolean isStoreGenralItemExist(StoreGeneralCategory v);
	boolean saveGeneralItem(StoreGeneralCategory v);
	List<StoreGeneralCategory> getStoreGeneralItemList();
	boolean editGeneralItem(StoreGeneralCategory v);
	
	//General Store Item Received
	boolean addGeneralReceivedItem(StoreGeneralReceived v);
	String getMaxInvoiceId(String string);
	List<StoreGeneralReceived> getStoreGeneralReceivedItemList(String invoiceNo, String type);
	boolean confrimtoreGeneralReceivedItemt(StoreGeneralReceived v);
	List<StoreGeneralReceived> getStoreGeneralReceivedIList(String string);

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
		String editReceiveSizeInTransaction(AccessoriesSize accessoriesSize);
		String deleteReceiveSizeFromTransaction(AccessoriesSize accessoriesSize);
		List<AccessoriesReceive> getAccessoriesReceiveList();
		AccessoriesReceive getAccessoriesReceiveInfo(String transectionId);

		//Accessories Quality Control
		boolean submitAccessoriesQC(AccessoriesQualityControl accessoriesReceive);
		boolean editAccessoriesQC(AccessoriesQualityControl accessoriesReceive);
		List<AccessoriesQualityControl> getAccessoriesQCList();
		AccessoriesQualityControl getAccessoriesQCInfo(String qcTransectionId);


		//Accessories Return
		List<AccessoriesSize> getAccessoriesSizeListBySupplier(String supplierId);
		boolean submitAccessoriesReturn(AccessoriesReturn accessoriesReceive);
		boolean editAccessoriesReturn(AccessoriesReturn accessoriesReceive);
		String editReturnSizeInTransaction(AccessoriesSize accessoriesSize);
		String deleteReturnSizeFromTransaction(AccessoriesSize accessoriesSize);
		List<AccessoriesReturn> getAccessoriesReturnList();
		AccessoriesReturn getAccessoriesReturnInfo(String returnTransectionId);
		AccessoriesReceive getAccessoriesReceiveInfoForReturn(String transectionId);


		//Accessories Issue
		List<AccessoriesSize> getAvailableAccessoriesSizeListInDepartment(String departmentId);
		boolean submitAccessoriesIssue(AccessoriesIssue accessoriesIssue);
		boolean editAccessoriesIssue(AccessoriesIssue accessoriesIssue);
		String editIssuedSizeInTransaction(AccessoriesSize accessoriesSize);
		String deleteIssuedSizeFromTransaction(AccessoriesSize accessoriesSize);
		List<AccessoriesIssue> getAccessoriesIssueList();
		AccessoriesIssue getAccessoriesIssueInfo(String issueTransectionId);

		//Accessories Issue Return
		List<AccessoriesSize> getIssuedAccessoriesSizeListInDepartment(String departmentId,String returnDepartmentId);
		boolean submitAccessoriesIssueReturn(AccessoriesIssueReturn accessoriesIssueReturn);
		boolean editAccessoriesIssueReturn(AccessoriesIssueReturn accessoriesIssueReturn);
		String editIssueReturndSizeInTransaction(AccessoriesSize accessoriesSize);
		String deleteIssueReturndSizeFromTransaction(AccessoriesSize accessoriesSize);
		List<AccessoriesIssueReturn> getAccessoriesIssueReturnList();
		AccessoriesIssueReturn getAccessoriesIssueReturnInfo(String issueReturnTransectionId);

		//Accessories TransferOut
		boolean submitAccessoriesTransferOut(AccessoriesTransferOut accessoriesTransferOut);
		boolean editAccessoriesTransferOut(AccessoriesTransferOut accessoriesTransferOut);
		String editTransferOutdSizeInTransaction(AccessoriesSize accessoriesSize);
		String deleteTransferOutdSizeFromTransaction(AccessoriesSize accessoriesSize);
		List<AccessoriesTransferOut> getAccessoriesTransferOutList();
		AccessoriesTransferOut getAccessoriesTransferOutInfo(String issueTransectionId);

		//Accessories TransferIn
		boolean submitAccessoriesTransferIn(AccessoriesTransferIn accessoriesTransferIn);
		boolean editAccessoriesTransferIn(AccessoriesTransferIn accessoriesTransferIn);
		String editTransferIndSizeInTransaction(AccessoriesSize accessoriesSize);
		String deleteTransferIndSizeFromTransaction(AccessoriesSize accessoriesSize);
		List<AccessoriesTransferIn> getAccessoriesTransferInList();
		AccessoriesTransferIn getAccessoriesTransferInInfo(String issueTransectionId);

}
