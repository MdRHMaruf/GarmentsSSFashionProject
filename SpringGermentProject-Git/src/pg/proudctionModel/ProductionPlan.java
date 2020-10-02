package pg.proudctionModel;

public class ProductionPlan {
	String userId="";
	String buyerId="";
	String buyerName="";
	String buyerorderId="";
	String purchaseOrder="";
	String styleId="";
	String styleNo="";
	String itemId="";
	String itemName="";
	String orderQty="";
	String planQty="";
	String shipDate="";
	String merchendizerId="";
	String fileSample="";
	String ppStatus="";
	String accessoriesInhouse="";
	String fabricsInhouse="";
	String startDate="";
	String endDate="";
	String sewingLineAutoId="";
	String lineId="";
	String lineName="";
	
	String duration="";
	String dailyTarget="";
	String dailyLineTarget="";
	String hourlyTarget="";
	
	String resultlist="";
	String productionDate="";
	String layoutDate="";
	String layoutName="";
	
	
	int line=0;
	
	String TotalLine="";
	
	String hour1="";
	String hour2="";
	String hour3="";
	String hour4="";
	String hour5="";
	String hour6="";
	String hour7="";
	String hour8="";
	String hour9="";
	String hour10="";
	String hours="";
	String total="";
	String date="";
	
	String machineId="";
	String machineName="";
	String operatorId="";
	String operatorName="";
	String sizeGroupId="";
	String sizeId="";
	String sizeName="";
	
	String proudctionType="";

	public ProductionPlan() {
		
	}
	
	public ProductionPlan(String BuyerName,String BuyerId,String BuyerOrderId,String PoNo,String StyleNo,String StyleId,String ItemName,String ItemId,String shipDate,String OrderQty,String PlanQty,String FileSample,String PPStatus,String AccessoriesInhouseStatus,String FabricsInhouseStatus,String StartDate,String EndDate) {
		this.buyerName=BuyerName;
		this.buyerId=BuyerId;
		this.buyerorderId=BuyerOrderId;
		this.purchaseOrder=PoNo;
		this.styleNo=StyleNo;
		this.styleId=StyleId;
		this.itemName=ItemName;
		this.itemId=ItemId;
		this.shipDate=shipDate;
		this.orderQty=OrderQty;
		this.planQty=PlanQty;
		this.fileSample=FileSample;
		this.ppStatus=PPStatus;
		this.accessoriesInhouse=AccessoriesInhouseStatus;
		this.fabricsInhouse=FabricsInhouseStatus;
		this.startDate=StartDate;
		this.endDate=EndDate;
	}
	
	public ProductionPlan(String BuyerName,String BuyerId,String BuyerOrderId,String PoNo,String StyleNo,String StyleId) {
		this.buyerName=BuyerName;
		this.buyerId=BuyerId;
		this.buyerorderId=BuyerOrderId;
		this.purchaseOrder=PoNo;
		this.styleNo=StyleNo;
		this.styleId=StyleId;
	}
	
	public ProductionPlan(String BuyerName,String BuyerId,String BuyerOrderId,String PoNo,String StyleNo,String StyleId,String ItemName,String ItemId,String ProductionDate,String s) {
		this.buyerName=BuyerName;
		this.buyerId=BuyerId;
		this.buyerorderId=BuyerOrderId;
		this.purchaseOrder=PoNo;
		this.styleNo=StyleNo;
		this.styleId=StyleId;
		this.itemName=ItemName;
		this.itemId=ItemId;
		this.productionDate=ProductionDate;

	}
	
	public ProductionPlan(String BuyerName,String BuyerId,String BuyerOrderId,String PoNo,String StyleNo,String StyleId,String ItemName,String ItemId,String PlanQty) {
		this.buyerName=BuyerName;
		this.buyerId=BuyerId;
		this.buyerorderId=BuyerOrderId;
		this.purchaseOrder=PoNo;
		this.styleNo=StyleNo;
		this.styleId=StyleId;
		this.itemName=ItemName;
		this.itemId=ItemId;
		this.planQty=PlanQty;
		
	
	}
	
	public ProductionPlan(String StyleId,String StyleNo,String ItemId,String ItemName,String SewingLineAutoId,String Duration,String LineId,String LineName,String PlanQty,int LineCount) {
		this.styleId=StyleId;
		this.styleNo=StyleNo;
		this.itemId=ItemId;
		this.itemName=ItemName;
		this.sewingLineAutoId=SewingLineAutoId;
		this.duration=Duration;
		this.lineId=LineId;
		this.lineName=LineName;
		this.planQty=PlanQty;
		

		System.out.println("LineCount "+LineCount);
		
		this.dailyTarget=Double.toString((Double.parseDouble(planQty)/Double.parseDouble(duration)));
		this.dailyLineTarget=Double.toString((Double.parseDouble(planQty)/Double.parseDouble(duration))/LineCount);

		
		
		this.hourlyTarget=Double.toString(((Double.parseDouble(planQty)/Double.parseDouble(duration))/LineCount)/10);
	}

	public ProductionPlan(String SewingLineAutoId, String BuyerId, String BuyerOrderId, String PurchaseOrder, String StyleId, String ItemId,
			String LineId, String ProudctionType, String DailyLineTarget, String HourlyTarget, String Hours, String Hour1,
			String Hour2, String Hour3, String Hour4, String Hour5, String Hour6, String Hour7,
			String Hour8, String Hour9, String Hour10, String Total, String ProductionDate, String BuyerName,
			String StyleNo, String ItemName,String LineName) {
		
		this.sewingLineAutoId=SewingLineAutoId;
		this.buyerId=BuyerId;
		this.buyerorderId=BuyerOrderId;
		this.purchaseOrder=PurchaseOrder;
		this.styleId=StyleId;
		this.itemId=ItemId;
		this.lineId=LineId;
		this.proudctionType=ProudctionType;
		this.dailyLineTarget=DailyLineTarget;
		this.hourlyTarget=HourlyTarget;
		this.hours=Hours;
		this.hour1=Hour1;
		this.hour2=Hour2;
		this.hour3=Hour3;
		this.hour4=Hour4;
		this.hour5=Hour5;
		this.hour6=Hour6;
		this.hour7=Hour7;
		this.hour8=Hour8;
		this.hour9=Hour9;
		this.hour10=Hour10;
		this.total=Total;
		this.productionDate=ProductionDate;
		this.buyerName=BuyerName;
		this.styleNo=StyleNo;
		this.itemName=ItemName;
		this.lineName=LineName;
	}


	public ProductionPlan(String BuyerName, String BuyerId, String BuyerOrderId, String PurchaseOrder, String StyleNo, String StyleId,
			String ItemName, String ItemId, String PlanQty, String DailyTarget, String LineTarget, String HourlryTarget,String Hours,String LineId,String LineName) {
		this.buyerName=BuyerName;
		this.buyerId=BuyerId;
		this.buyerorderId=BuyerOrderId;
		this.purchaseOrder=PurchaseOrder;
		this.styleNo=StyleNo;
		this.styleId=StyleId;
		this.itemName=ItemName;
		this.itemId=ItemId;
		this.planQty=PlanQty;
		this.dailyTarget=DailyTarget;
		this.dailyLineTarget=LineTarget;
		this.hourlyTarget=HourlryTarget;
		this.hours=Hours;
		this.lineId=LineId;
		this.lineName=LineName;
	}

	public ProductionPlan(String MachineId, String MachineName, String OperatorId, String OperatorName) {
		this.machineId=MachineId;
		this.machineName=MachineName;
		this.operatorId=OperatorId;
		this.operatorName=OperatorName;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}
	public String getBuyerorderId() {
		return buyerorderId;
	}
	public void setBuyerorderId(String buyerorderId) {
		this.buyerorderId = buyerorderId;
	}
	public String getPurchaseOrder() {
		return purchaseOrder;
	}
	public void setPurchaseOrder(String purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}
	public String getStyleId() {
		return styleId;
	}
	public void setStyleId(String styleId) {
		this.styleId = styleId;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getOrderQty() {
		return orderQty;
	}
	public void setOrderQty(String orderQty) {
		this.orderQty = orderQty;
	}
	public String getPlanQty() {
		return planQty;
	}
	public void setPlanQty(String planQty) {
		this.planQty = planQty;
	}
	public String getShipDate() {
		return shipDate;
	}
	public void setShipDate(String shipDate) {
		this.shipDate = shipDate;
	}
	public String getMerchendizerId() {
		return merchendizerId;
	}
	public void setMerchendizerId(String merchendizerId) {
		this.merchendizerId = merchendizerId;
	}
	public String getFileSample() {
		return fileSample;
	}
	public void setFileSample(String fileSample) {
		this.fileSample = fileSample;
	}
	public String getPpStatus() {
		return ppStatus;
	}
	public void setPpStatus(String ppStatus) {
		this.ppStatus = ppStatus;
	}
	public String getAccessoriesInhouse() {
		return accessoriesInhouse;
	}
	public void setAccessoriesInhouse(String accessoriesInhouse) {
		this.accessoriesInhouse = accessoriesInhouse;
	}
	public String getFabricsInhouse() {
		return fabricsInhouse;
	}
	public void setFabricsInhouse(String fabricsInhouse) {
		this.fabricsInhouse = fabricsInhouse;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getStyleNo() {
		return styleNo;
	}

	public void setStyleNo(String styleNo) {
		this.styleNo = styleNo;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getSewingLineAutoId() {
		return sewingLineAutoId;
	}

	public void setSewingLineAutoId(String sewingLineAutoId) {
		this.sewingLineAutoId = sewingLineAutoId;
	}

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getDailyTarget() {
		return dailyTarget;
	}

	public void setDailyTarget(String dailyTarget) {
		this.dailyTarget = dailyTarget;
	}

	public String getDailyLineTarget() {
		return dailyLineTarget;
	}

	public void setDailyLineTarget(String dailyLineTarget) {
		this.dailyLineTarget = dailyLineTarget;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public String getTotalLine() {
		return TotalLine;
	}

	public void setTotalLine(String totalLine) {
		TotalLine = totalLine;
	}

	public String getHourlyTarget() {
		return hourlyTarget;
	}

	public void setHourlyTarget(String hourlyTarget) {
		this.hourlyTarget = hourlyTarget;
	}

	public String getResultlist() {
		return resultlist;
	}

	public void setResultlist(String resultlist) {
		this.resultlist = resultlist;
	}

	public String getProductionDate() {
		return productionDate;
	}

	public void setProductionDate(String productionDate) {
		this.productionDate = productionDate;
	}

	public String getHour1() {
		return hour1;
	}

	public void setHour1(String hour1) {
		this.hour1 = hour1;
	}

	public String getHour2() {
		return hour2;
	}

	public void setHour2(String hour2) {
		this.hour2 = hour2;
	}

	public String getHour3() {
		return hour3;
	}

	public void setHour3(String hour3) {
		this.hour3 = hour3;
	}

	public String getHour4() {
		return hour4;
	}

	public void setHour4(String hour4) {
		this.hour4 = hour4;
	}

	public String getHour5() {
		return hour5;
	}

	public void setHour5(String hour5) {
		this.hour5 = hour5;
	}

	public String getHour6() {
		return hour6;
	}

	public void setHour6(String hour6) {
		this.hour6 = hour6;
	}

	public String getHour7() {
		return hour7;
	}

	public void setHour7(String hour7) {
		this.hour7 = hour7;
	}

	public String getHour8() {
		return hour8;
	}

	public void setHour8(String hour8) {
		this.hour8 = hour8;
	}

	public String getHour9() {
		return hour9;
	}

	public void setHour9(String hour9) {
		this.hour9 = hour9;
	}

	public String getHour10() {
		return hour10;
	}

	public void setHour10(String hour10) {
		this.hour10 = hour10;
	}

	public String getHours() {
		return hours;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getProudctionType() {
		return proudctionType;
	}

	public void setProudctionType(String proudctionType) {
		this.proudctionType = proudctionType;
	}

	public String getLayoutDate() {
		return layoutDate;
	}

	public void setLayoutDate(String layoutDate) {
		this.layoutDate = layoutDate;
	}

	public String getLayoutName() {
		return layoutName;
	}

	public void setLayoutName(String layoutName) {
		this.layoutName = layoutName;
	}

	public String getMachineId() {
		return machineId;
	}

	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}

	public String getMachineName() {
		return machineName;
	}

	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getSizeGroupId() {
		return sizeGroupId;
	}

	public void setSizeGroupId(String sizeGroupId) {
		this.sizeGroupId = sizeGroupId;
	}

	public String getSizeId() {
		return sizeId;
	}

	public void setSizeId(String sizeId) {
		this.sizeId = sizeId;
	}

	public String getSizeName() {
		return sizeName;
	}

	public void setSizeName(String sizeName) {
		this.sizeName = sizeName;
	}
	
	
}
