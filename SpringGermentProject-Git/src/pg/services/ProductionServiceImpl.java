
package pg.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pg.dao.ProductionDAO;
import pg.orderModel.SampleRequisitionItem;
import pg.orderModel.Style;
import pg.proudctionModel.CuttingInformation;
import pg.proudctionModel.Process;
import pg.proudctionModel.SewingLinesModel;
import pg.proudctionModel.ProductionPlan;
import pg.proudctionModel.CuttingRequsition;
import pg.registerModel.Department;
import pg.registerModel.Line;
import pg.registerModel.Machine;
import pg.registerModel.SizeGroup;

@Service
public class ProductionServiceImpl implements ProductionService {
	
	@Autowired
	ProductionDAO productionDao;

	@Override
	public boolean cuttingRequisitionEnty(CuttingRequsition v) {
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
	public List<ProductionPlan> getProductionPlanFromCutting() {
		// TODO Auto-generated method stub
		return productionDao.getProductionPlanFromCutting();
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


	@Override
	public boolean cuttingInformationEnty(CuttingInformation v) {
		// TODO Auto-generated method stub
		return productionDao.cuttingInformationEnty(v);
	}


	@Override
	public List<CuttingInformation> getCuttingInformationList() {
		// TODO Auto-generated method stub
		return productionDao.getCuttingInformationList();
	}


	@Override
	public List<Style> stylename() {
		// TODO Auto-generated method stub
		return productionDao.stylename();
	}


	@Override
	public List<Line> getLineNames() {
		// TODO Auto-generated method stub
		return productionDao.getLineNames();
	}


	@Override
	public String InserLines(SewingLinesModel linemodels) {
		// TODO Auto-generated method stub
		return productionDao.InserLines(linemodels);
	}


	@Override
	public List<SewingLinesModel> Lines() {
		// TODO Auto-generated method stub
		return productionDao.Lines();
	}


	@Override
	public List<SewingLinesModel> getSewingProductionLines() {
		// TODO Auto-generated method stub
		return productionDao.getSewingProductionLines();
	}


	@Override
	public List<ProductionPlan> getSewingLineSetupinfo(ProductionPlan v) {
		// TODO Auto-generated method stub
		return productionDao.getSewingLineSetupinfo(v);
	}
	
	@Override
	public List<ProductionPlan> getSewingPassProduction(ProductionPlan v) {
		// TODO Auto-generated method stub
		return productionDao.getSewingPassProduction(v);
	}


	@Override
	public boolean saveSewingLayoutDetails(ProductionPlan v) {
		// TODO Auto-generated method stub
		return productionDao.saveSewingLayoutDetails(v);
	}


	@Override
	public List<ProductionPlan> getSewingProductionReport(String Type) {
		// TODO Auto-generated method stub
		return productionDao.getSewingProductionReport(Type);
	}


	@Override
	public List<ProductionPlan> viewSewingProduction(String buyerId, String buyerorderId, String styleId, String itemId,
			String productionDate) {
		// TODO Auto-generated method stub
		return productionDao.viewSewingProduction(buyerId, buyerorderId, styleId, itemId, productionDate);
	}


	/*@Override
	public boolean saveFinishProductionDetails(ProductionPlan v) {
		// TODO Auto-generated method stub
		return productionDao.saveFinishProductionDetails(v);
	}*/


	@Override
	public List<ProductionPlan> viewSewingFinishingProduction(String buyerId, String buyerorderId, String styleId,
			String itemId, String productionDate) {
		// TODO Auto-generated method stub
		return productionDao.viewSewingFinishingProduction(buyerId, buyerorderId, styleId, itemId, productionDate);
	}


	@Override
	public boolean saveInceptionLayoutDetails(ProductionPlan v) {
		// TODO Auto-generated method stub
		return productionDao.saveInceptionLayoutDetails(v);
	}
	
	@Override
	public boolean saveInceptionLayoutLineDetails(ProductionPlan v) {
		// TODO Auto-generated method stub
		return productionDao.saveInceptionLayoutLineDetails(v);
	}


	@Override
	public List<ProductionPlan> getLayoutPlanDetails(String string) {
		// TODO Auto-generated method stub
		return productionDao.getLayoutPlanDetails(string);
	}
	
	@Override
	public List<ProductionPlan> getInspectionLayoutList(String type) {
		// TODO Auto-generated method stub
		return productionDao.getInspectionLayoutList(type);
	}


	@Override
	public List<ProductionPlan> getLineWiseMachineList(ProductionPlan v) {
		// TODO Auto-generated method stub
		return productionDao.getLineWiseMachineList(v);
	}
	
	@Override
	public List<Machine> getLineWiseMachineListByLineId(String lineId) {
		// TODO Auto-generated method stub
		return productionDao.getLineWiseMachineListByLineId(lineId);
	}


	@Override
	public List<ProductionPlan> getSizeListForProduction(ProductionPlan v) {
		// TODO Auto-generated method stub
		return productionDao.getSizeListForProduction(v);
	}

	
	@Override
	public List<ProductionPlan> getSewingLayoutLineProduction(ProductionPlan v) {
		// TODO Auto-generated method stub
		return productionDao.getSewingLayoutLineProduction(v);
	}

	@Override
	public boolean saveLineProductionDetails(ProductionPlan v) {
		// TODO Auto-generated method stub
		return productionDao.saveLineProductionDetails(v);
	}
	
	@Override
	public boolean saveFinishingProductionDetails(ProductionPlan v) {
		// TODO Auto-generated method stub
		return productionDao.saveFinishingProductionDetails(v);
	}


	@Override
	public boolean saveIronProductionDetails(ProductionPlan v) {
		// TODO Auto-generated method stub
		return productionDao.saveIronProductionDetails(v);
	}


	@Override
	public boolean saveSewingProductionDetails(ProductionPlan v) {
		// TODO Auto-generated method stub
		return productionDao.saveSewingProductionDetails(v);
	}
	@Override
	public List<ProductionPlan> getLayoutData(ProductionPlan productionPlan) {
		// TODO Auto-generated method stub
		return productionDao.getLayoutData(productionPlan);
	}
	
	@Override
	public List<ProductionPlan> getLayoutLineData(ProductionPlan productionPlan) {
		// TODO Auto-generated method stub
		return productionDao.getLayoutLineData(productionPlan);
	}


	@Override
	public String editLayoutLineData(ProductionPlan productionPlan) {
		// TODO Auto-generated method stub
		return productionDao.editLayoutLineData(productionPlan);
	}


	@Override
	public List<ProductionPlan> getProductionData(ProductionPlan productionPlan) {
		// TODO Auto-generated method stub
		return productionDao.getProductionData(productionPlan);
	}


	@Override
	public List<ProductionPlan> getFinishingPassData(ProductionPlan v) {
		// TODO Auto-generated method stub
		return productionDao.getFinishingPassData(v);
	}


	@Override
	public List<ProductionPlan> getFinishingData(ProductionPlan productionPlan) {
		// TODO Auto-generated method stub
		return productionDao.getFinishingData(productionPlan);
	}

	@Override
	public List<ProductionPlan> getIronData(ProductionPlan v) {
		// TODO Auto-generated method stub
		return productionDao.getIronData(v);
	}


	@Override
	public boolean savePolyPackingDetails(ProductionPlan v) {
		// TODO Auto-generated method stub
		return productionDao.savePolyPackingDetails(v);
	}


	@Override
	public List<ProductionPlan> getPolyPackingDetails(String string) {
		// TODO Auto-generated method stub
		return productionDao.getPolyPackingDetails(string);
	}


	@Override
	public List<CuttingInformation> getCuttingRequisitionList() {
		// TODO Auto-generated method stub
		return productionDao.getCuttingRequisitionList();
	}


	@Override
	public List<ProductionPlan> searchCuttingPlanQuantity(String cuttingEntryId, String sizeGroupId) {
		// TODO Auto-generated method stub
		return productionDao.searchCuttingPlanQuantity(cuttingEntryId, sizeGroupId);
	}
	
	@Override
	public List<ProductionPlan> getSendCuttingBodyList(String cuttingEntryId, String sizeGroupId) {
		// TODO Auto-generated method stub
		return productionDao.getSendCuttingBodyList(cuttingEntryId, sizeGroupId);
	}
	
	@Override
	public List<CuttingInformation> getSendCuttingBodyInfoList() {
		// TODO Auto-generated method stub
		return productionDao.getSendCuttingBodyInfoList();
	}

	@Override
	public String sendCuttingPlanBodyQuantity(String sendItemList,String userId) {
		// TODO Auto-generated method stub
		return productionDao.sendCuttingPlanBodyQuantity(sendItemList,userId);
	}

	@Override
	public List<ProductionPlan> searchSendCuttingBodyQuantity(String cuttingEntryId, String sizeGroupId) {
		// TODO Auto-generated method stub
		return productionDao.searchSendCuttingBodyQuantity(cuttingEntryId, sizeGroupId);
	}

	@Override
	public List<ProductionPlan> getReceiveCuttingBodyList(String cuttingEntryId, String sizeGroupId) {
		// TODO Auto-generated method stub
		return productionDao.getReceiveCuttingBodyList(cuttingEntryId, sizeGroupId);
	}


	@Override
	public List<CuttingInformation> getReceiveCuttingBodyInfoList() {
		// TODO Auto-generated method stub
		return productionDao.getReceiveCuttingBodyInfoList();
	}


	@Override
	public String receiveCuttingPlanBodyQuantity(String sendItemList, String userId) {
		// TODO Auto-generated method stub
		return productionDao.receiveCuttingPlanBodyQuantity(sendItemList, userId);
	}


	@Override
	public List<Process> getProcessValues(ProductionPlan productionPlan) {
		// TODO Auto-generated method stub
		return productionDao.getProcessValues(productionPlan);
	}


}

