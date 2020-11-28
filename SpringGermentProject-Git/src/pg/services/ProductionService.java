package pg.services;

import java.util.List;

import pg.orderModel.SampleRequisitionItem;
import pg.orderModel.Style;
import pg.proudctionModel.CuttingInformation;
import pg.proudctionModel.SewingLinesModel;
import pg.proudctionModel.ProductionPlan;
import pg.proudctionModel.cuttingRequsition;
import pg.registerModel.Department;
import pg.registerModel.Line;
import pg.registerModel.Machine;
import pg.registerModel.SizeGroup;

public interface ProductionService {

	boolean cuttingRequisitionEnty(cuttingRequsition v);

	//Production	

	String getOrderQty(String buyerorderid, String style, String item);

	boolean checkDoplicationPlanSet(ProductionPlan v);

	boolean productionPlanSave(ProductionPlan v);

	List<ProductionPlan> getProductionPlanList();

	List<ProductionPlan> getProductionPlan(String buyerId, String buyerorderId, String styleId);

	List<ProductionPlan> getProductionPlanForCutting();
	List<ProductionPlan> getProductionPlanFromCutting();

	List<Department> getFactoryWiseDepartmentLoad(String factoryId);

	List<Line> getFactoryDepartmentWiseLineLoad(String factoryId, String departmentId);
	List<CuttingInformation> getBuyerPoDetails(String buyerId, String buyerorderId, String styleId, String itemId);

	boolean cuttingInformationEnty(CuttingInformation v);

	List<CuttingInformation> getCuttingInformationList();

	//Sewing Service
	public List<Style> stylename();
	public List<Line> getLineNames();
	public String InserLines(SewingLinesModel linemodels);
	public List<SewingLinesModel> Lines();

	List<SewingLinesModel> getSewingProductionLines();

	List<ProductionPlan> getSewingLineSetupinfo(ProductionPlan v);

	boolean saveSewingLayoutDetails(ProductionPlan v);

	List<ProductionPlan> getSewingProductionReport(String Type);

	List<ProductionPlan> viewSewingProduction(String buyerId, String buyerorderId, String styleId, String itemId,
			String productionDate);

	boolean saveFinishProductionDetails(ProductionPlan v);

	List<ProductionPlan> viewSewingFinishingProduction(String buyerId, String buyerorderId, String styleId,
			String itemId, String productionDate);

	//Inception Layout
	boolean saveInceptionLayoutDetails(ProductionPlan v);
	boolean saveInceptionLayoutLineDetails(ProductionPlan v);

	List<ProductionPlan> getLayoutPlanDetails(String type);
	List<ProductionPlan> getInspectionLayoutList(String type);

	List<ProductionPlan> getProductionData(ProductionPlan productionPlan);

	//Sewing
	boolean saveLineProductionDetails(ProductionPlan v);
	List<ProductionPlan> getLineWiseMachineList(ProductionPlan v);
	List<Machine> getLineWiseMachineListByLineId(String lineId);

	List<ProductionPlan> getSizeListForProduction(ProductionPlan v);

	List<ProductionPlan> getSewingLayoutLineProduction(ProductionPlan v);

	boolean saveSewingProductionDetails(ProductionPlan v);

	//Finishing
	List<ProductionPlan> getLayoutData(ProductionPlan productionPlan);
	List<ProductionPlan> getLayoutLineData(ProductionPlan productionPlan);
	String editLayoutLineData(ProductionPlan productionPlan);

	boolean savePolyPackingDetails(ProductionPlan v);

	List<ProductionPlan> getPolyPackingDetails(String string);

	List<CuttingInformation> getCuttingRequisitionList();

	//send cutting body
	List<ProductionPlan> searchCuttingPlanQuantity(String cuttingEntryId,String sizeGroupId);
	List<ProductionPlan> getSendCuttingBodyList(String cuttingEntryId,String sizeGroupId);
	public List<CuttingInformation> getSendCuttingBodyInfoList();
	String sendCuttingPlanBodyQuantity(String sendItemList,String userId);

	//receive Cutting Body...
	List<ProductionPlan> searchSendCuttingBodyQuantity(String cuttingEntryId,String sizeGroupId);
	List<ProductionPlan> getReceiveCuttingBodyList(String cuttingEntryId,String sizeGroupId);
	public List<CuttingInformation> getReceiveCuttingBodyInfoList();
	String receiveCuttingPlanBodyQuantity(String sendItemList,String userId);
}

