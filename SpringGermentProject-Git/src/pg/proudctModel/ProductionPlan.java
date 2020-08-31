package pg.proudctModel;

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
	
	public ProductionPlan(String BuyerName,String BuyerId,String BuyerOrderId,String PoNo,String StyleNo,String StyleId,String ItemName,String ItemId) {
		this.buyerName=BuyerName;
		this.buyerId=BuyerId;
		this.buyerorderId=BuyerOrderId;
		this.purchaseOrder=PoNo;
		this.styleNo=StyleNo;
		this.styleId=StyleId;
		this.itemName=ItemName;
		this.itemId=ItemId;
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
	
	
}
