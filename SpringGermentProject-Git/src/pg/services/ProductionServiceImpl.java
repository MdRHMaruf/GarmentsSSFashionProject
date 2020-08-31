package pg.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pg.dao.ProductionDAO;
import pg.orderModel.SampleRequisitionItem;
import pg.proudctModel.CuttingInformation;
import pg.proudctModel.ProductionPlan;
import pg.proudctModel.cuttingRequsition;
import pg.registerModel.Department;
import pg.registerModel.Line;
import pg.registerModel.SizeGroup;

@Service
public class ProductionServiceImpl implements ProductionService {
	
	@Autowired
	ProductionDAO productionDao;

	@Override
	public boolean cuttingRequisitionEnty(cuttingRequsition v) {
		// TODO Auto-generated method stub
		return productionDao.cuttingRequisitionEnty(v);
	}

	
	@Override
	public String getOrderQty(String buyerorderid, String style, String item) {
		// TODO Auto-generated method stub
		return productionDao.getOrderQty(buyerorderid, style, item);
	}

	@Override
	public boolean checkDoplicationPlanSet(ProductionPlan v) {
		// TODO Auto-generated method stub
		return productionDao.checkDoplicationPlanSet(v);
	}

	@Override
	public boolean productionPlanSave(ProductionPlan v) {
		// TODO Auto-generated method stub
		return productionDao.productionPlanSave(v);
	}


	@Override
	public List<ProductionPlan> getProductionPlanList() {
		// TODO Auto-generated method stub
		return productionDao.getProductionPlanList();
	}


	@Override
	public List<ProductionPlan> getProductionPlan(String buyerId, String buyerorderId, String styleId) {
		// TODO Auto-generated method stub
		return productionDao.getProductionPlan(buyerId, buyerorderId, styleId);
	}


	@Override
	public List<ProductionPlan> getProductionPlanForCutting() {
		// TODO Auto-generated method stub
		return productionDao.getProductionPlanForCutting();
	}


	@Override
	public List<Department> getFactoryWiseDepartmentLoad(String factoryId) {
		// TODO Auto-generated method stub
		return productionDao.getFactoryWiseDepartmentLoad(factoryId);
	}


	@Override
	public List<Line> getFactoryDepartmentWiseLineLoad(String factoryId, String departmentId) {
		// TODO Auto-generated method stub
		return productionDao.getFactoryDepartmentWiseLineLoad(factoryId, departmentId);
	}


	@Override
	public List<CuttingInformation> getBuyerPoDetails(String buyerId, String buyerorderId, String styleId,
			String itemId) {
		// TODO Auto-generated method stub
		return productionDao.getBuyerPoDetails(buyerId, buyerorderId, styleId, itemId);
	}
	
}
