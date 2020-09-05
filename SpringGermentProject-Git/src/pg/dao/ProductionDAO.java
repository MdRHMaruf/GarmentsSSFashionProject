package pg.dao;

import java.util.List;

import pg.orderModel.SampleRequisitionItem;
import pg.proudctModel.CuttingInformation;
import pg.proudctModel.ProductionPlan;
import pg.proudctModel.cuttingRequsition;
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
}
