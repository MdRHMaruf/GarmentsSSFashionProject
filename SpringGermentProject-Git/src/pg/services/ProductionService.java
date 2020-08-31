package pg.services;

import java.util.List;

import pg.orderModel.SampleRequisitionItem;
import pg.proudctModel.CuttingInformation;
import pg.proudctModel.ProductionPlan;
import pg.proudctModel.cuttingRequsition;
import pg.registerModel.Department;
import pg.registerModel.Line;
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

	List<Department> getFactoryWiseDepartmentLoad(String factoryId);

	List<Line> getFactoryDepartmentWiseLineLoad(String factoryId, String departmentId);
	List<CuttingInformation> getBuyerPoDetails(String buyerId, String buyerorderId, String styleId, String itemId);

}
