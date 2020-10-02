package pg.dao;

import java.util.List;

import pg.orderModel.SampleRequisitionItem;
import pg.orderModel.Style;
import pg.proudctionModel.CuttingInformation;
import pg.proudctionModel.SewingLinesModel;
import pg.proudctionModel.ProductionPlan;
import pg.proudctionModel.cuttingRequsition;
import pg.registerModel.Department;
import pg.registerModel.Line;
import pg.registerModel.SizeGroup;

public interface ProductionDAO {

	//Cutting Requistion Entry
	boolean cuttingRequisitionEnty(cuttingRequsition v);
	
	//Production
	boolean productionEnty(cuttingRequsition v);
	String getOrderQty(String buyerorderid, String style, String item);
	boolean checkDoplicationPlanSet(ProductionPlan v);
	boolean productionPlanSave(ProductionPlan v);
	List<ProductionPlan> getProductionPlanList();
	List<ProductionPlan> getProductionPlan(String buyerId, String buyerorderId, String styleId);
	
	List<ProductionPlan> getProductionPlanForCutting();
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
	
	boolean saveSewingProductionDetails(ProductionPlan v);
	List<ProductionPlan> getSewingProductionReport();
	

	List<ProductionPlan> viewSewingProduction(String buyerId, String buyerorderId, String styleId, String itemId,
			String productionDate);
	
	boolean saveFinishProductionDetails(ProductionPlan v);
	
	List<ProductionPlan> viewSewingFinishingProduction(String buyerId, String buyerorderId, String styleId,
			String itemId, String productionDate);
	
	//Inception Layout
	boolean saveInceptionLayoutDetails(ProductionPlan v);
	List<ProductionPlan> getLayoutPlanDetails(String string);
	
	//Sewing Production
	List<ProductionPlan> getLineWiseMachineList(ProductionPlan v);
	List<ProductionPlan> getSizeListForProduction(ProductionPlan v);
}
