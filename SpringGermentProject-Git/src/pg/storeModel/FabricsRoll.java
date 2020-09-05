package pg.storeModel;

public class FabricsRoll {
	String autoId;
	String transectionId;
	String rollId;
	String purchaseOrder;
	String styleId;
	String itemId;
	String itemColorId;
	String fabricsId;
	String fabricsName;
	String fabricsColorId;
	String fabricsColorName;
	String unitId;
	String unit;
	double unitQty;
	double qcPassedQty;
	double issueQty;
	double balanceQty;
	double rate;
	double totalAmount;
	String remarks;
	String rackName;
	String binName;
	String userId;
	String qcTransectionId;
	int qcPassedType;
	String returnTransectionId;
	boolean isReturn;
	
	public FabricsRoll() {}
	
	
	

	public FabricsRoll(String autoId, String transectionId, String purchaseOrder, String styleId, String itemId,
			String itemColorId, String fabricsId,String fabricsName, String fabricsColorId,String fabricsColorName, String rollId, String unitId, String unit, double unitQty,
			String rackName, String binName) {
		super();
		this.autoId = autoId;
		this.transectionId = transectionId;
		this.purchaseOrder = purchaseOrder;
		this.styleId = styleId;
		this.itemId = itemId;
		this.itemColorId = itemColorId;
		this.fabricsId = fabricsId;
		this.fabricsName = fabricsName;
		this.fabricsColorId = fabricsColorId;
		this.fabricsColorName = fabricsColorName;
		this.rollId = rollId;
		this.unitId = unitId;
		this.unit = unit;
		this.unitQty = unitQty;
		this.rackName = rackName;
		this.binName = binName;
	}




	public FabricsRoll(String autoId, String transectionId, String rollId, String unitId,
			String unit, double unitQty, double qcPassedQty, double issueQty, double balanceQty, double rate,
			double totalAmount, String remarks, String rackName, String binName,String qcTransectionId,int qcPassedType,String userId) {
		super();
		this.autoId = autoId;
		this.transectionId = transectionId;
		this.rollId = rollId;
		this.unitId = unitId;
		this.unit = unit;
		this.unitQty = unitQty;
		this.qcPassedQty = qcPassedQty;
		this.issueQty = issueQty;
		this.balanceQty = balanceQty;
		this.rate = rate;
		this.totalAmount = totalAmount;
		this.remarks = remarks;
		this.rackName = rackName;
		this.binName = binName;
		this.qcTransectionId = qcTransectionId;
		this.qcPassedType = qcPassedType;
		this.userId = userId;
	}
	
	public FabricsRoll(String autoId,String qcTransectionId,double qcPassedQty,int qcPassedType,boolean isReturn,String userId) {
		this.autoId = autoId;
		this.qcTransectionId = qcTransectionId;
		this.qcPassedQty = qcPassedQty;
		this.qcPassedType = qcPassedType;
		this.isReturn = isReturn;
		this.userId = userId;
	}
	
	
	
	public FabricsRoll(String autoId, String returnTransectionId, String rollId, String unitId,
			String unit, double unitQty, double qcPassedQty, String rackName, String binName,
			int qcPassedType, boolean isReturn, String userId) {
		super();
		this.autoId = autoId;
		this.returnTransectionId = returnTransectionId;
		this.rollId = rollId;
		
		this.unitId = unitId;
		this.unit = unit;
		this.unitQty = unitQty;
		this.qcPassedQty = qcPassedQty;
		this.rackName = rackName;
		this.binName = binName;
		this.qcPassedType = qcPassedType;
		this.isReturn = isReturn;
		this.userId = userId;
		
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


	public String getItemColorId() {
		return itemColorId;
	}


	public void setItemColorId(String itemColorId) {
		this.itemColorId = itemColorId;
	}


	public String getFabricsId() {
		return fabricsId;
	}


	public void setFabricsId(String fabricsId) {
		this.fabricsId = fabricsId;
	}


	public String getFabricsName() {
		return fabricsName;
	}


	public void setFabricsName(String fabricsName) {
		this.fabricsName = fabricsName;
	}


	public String getFabricsColorId() {
		return fabricsColorId;
	}


	public void setFabricsColorId(String fabricsColorId) {
		this.fabricsColorId = fabricsColorId;
	}


	public String getFabricsColorName() {
		return fabricsColorName;
	}


	public void setFabricsColorName(String fabricsColorName) {
		this.fabricsColorName = fabricsColorName;
	}

	
	public String getReturnTransectionId() {
		return returnTransectionId;
	}

	public void setReturnTransectionId(String returnTransectionId) {
		this.returnTransectionId = returnTransectionId;
	}

	public boolean isReturn() {
		return isReturn;
	}

	public void setReturn(boolean isReturn) {
		this.isReturn = isReturn;
	}

	public String getQcTransectionId() {
		return qcTransectionId;
	}

	public void setQcTransectionId(String qcTransectionId) {
		this.qcTransectionId = qcTransectionId;
	}

	public int getQcPassedType() {
		return qcPassedType;
	}

	public void setQcPassedType(int qcPassedType) {
		this.qcPassedType = qcPassedType;
	}

	public String getTransectionId() {
		return transectionId;
	}

	public void setTransectionId(String transectionId) {
		this.transectionId = transectionId;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getAutoId() {
		return autoId;
	}
	public void setAutoId(String autoId) {
		this.autoId = autoId;
	}
	public String getRollId() {
		return rollId;
	}
	public void setRollId(String rollId) {
		this.rollId = rollId;
	}
	public double getUnitQty() {
		return unitQty;
	}
	public void setUnitQty(double unitQty) {
		this.unitQty = unitQty;
	}
	public double getQcPassedQty() {
		return qcPassedQty;
	}
	public void setQcPassedQty(double qcPassedQty) {
		this.qcPassedQty = qcPassedQty;
	}
	public double getIssueQty() {
		return issueQty;
	}
	public void setIssueQty(double issueQty) {
		this.issueQty = issueQty;
	}
	public double getBalanceQty() {
		return balanceQty;
	}
	public void setBalanceQty(double balanceQty) {
		this.balanceQty = balanceQty;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getRackName() {
		return rackName;
	}
	public void setRackName(String rackName) {
		this.rackName = rackName;
	}
	public String getBinName() {
		return binName;
	}
	public void setBinName(String binName) {
		this.binName = binName;
	}
	
	
	

}
