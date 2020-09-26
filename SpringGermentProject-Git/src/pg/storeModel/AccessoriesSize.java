package pg.storeModel;

public class AccessoriesSize {
	String autoId;
	String supplierId;
	String supplierName;
	String transactionId;
	String sizeId;
	String supplierSizeId;
	String purchaseOrder;
	String styleId;
	String styleNo;
	String itemId;
	String itemName;
	String itemColorId;
	String itemColor;
	String accessoriesId;
	String accessoriesName;
	String accessoriesColorId;
	String accessoriesColorName;
	String unitId;
	String unit;
	double unitQty;
	double orderQty;
	double previousReceiveQty;
	double qcPassedQty;
	double issueQty;
	double previousReturnQty;
	double returnQty;
	double balanceQty;
	double rate;
	double totalAmount;
	double shadeQty;
	double shrinkageQty;
	double gsmQty;
	double widthQty;
	double defectQty;
	String remarks;
	String rackName;
	String binName;
	String userId;
	int qcPassedType;
	boolean isReturn;

	public AccessoriesSize() {}

	public AccessoriesSize(String autoId, String transactionId, String purchaseOrder, String styleId, String styleNo, String itemId,String itemName,
			String itemColorId,String itemColor, String accessoriesId,String accessoriesName, String accessoriesColorId,String accessoriesColorName, String sizeId,String supplierSizeId, String unitId, String unit,double balanceQty, double unitQty,
			String rackName, String binName,int qcPassedType) {
		super();
		this.autoId = autoId;
		this.transactionId = transactionId;
		this.purchaseOrder = purchaseOrder;
		this.styleId = styleId;
		this.styleNo = styleNo;
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemColorId = itemColorId;
		this.itemColor = itemColor;
		this.accessoriesId = accessoriesId;
		this.accessoriesName = accessoriesName;
		this.accessoriesColorId = accessoriesColorId;
		this.accessoriesColorName = accessoriesColorName;
		this.sizeId = sizeId;
		this.supplierSizeId = supplierSizeId;
		this.unitId = unitId;
		this.unit = unit;
		this.balanceQty = balanceQty;
		this.unitQty = unitQty;
		this.rackName = rackName;
		this.binName = binName;
		this.qcPassedType = qcPassedType;
	}




	public AccessoriesSize(String autoId, String TransactionId, String sizeId, String unitId,
			String unit, double unitQty, double qcPassedQty, double issueQty, double balanceQty, double rate,
			double totalAmount, String remarks, String rackName, String binName,int qcPassedType,String userId) {
		super();
		this.autoId = autoId;
		this.transactionId = TransactionId;
		this.sizeId = sizeId;
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
		this.qcPassedType = qcPassedType;
		this.userId = userId;
	}
	
	public AccessoriesSize(String autoId,String TransactionId,String purchaseOrder,String styleId,String itemId,String itemColorId,String accessoriesId,String accessoriesColorId,String sizeId,String unitId,double unitQty,String rackName,String binName,String remarks,int qcPassedType,boolean isReturn,String userId) {
		this.autoId = autoId;
		this.transactionId = TransactionId;
		this.purchaseOrder = purchaseOrder;
		this.styleId = styleId;
		this.itemId = itemId;
		this.itemColorId = itemColorId;
		this.accessoriesId = accessoriesId;
		this.accessoriesColorId = accessoriesColorId;
		this.sizeId = sizeId;
		this.unitId = unitId;
		this.unitQty = unitQty;
		this.rackName = rackName;
		this.binName = binName;
		this.remarks = remarks;
		this.qcPassedType = qcPassedType;
		this.isReturn = isReturn;
		this.userId = userId;
	}
	public AccessoriesSize(String autoId, String TransactionId, String sizeId,String supplierSizeId, String purchaseOrder, String styleId,
			String itemId, String itemColorId, String accessoriesId, String accessoriesName, String accessoriesColorId,
			String accessoriesColorName, String unitId, String unit, double unitQty, double qcPassedQty, double shadeQty,
			double shrinkageQty, double gsmQty, double widthQty, double defectQty, String remarks, String rackName,
			String binName, int qcPassedType) {
		super();
		this.autoId = autoId;
		this.transactionId = TransactionId;
		this.sizeId = sizeId;
		this.supplierSizeId = supplierSizeId;
		this.purchaseOrder = purchaseOrder;
		this.styleId = styleId;
		this.itemId = itemId;
		this.itemColorId = itemColorId;
		this.accessoriesId = accessoriesId;
		this.accessoriesName = accessoriesName;
		this.accessoriesColorId = accessoriesColorId;
		this.accessoriesColorName = accessoriesColorName;
		this.unitId = unitId;
		this.unit = unit;
		this.unitQty = unitQty;
		this.qcPassedQty = qcPassedQty;
		this.shadeQty = shadeQty;
		this.shrinkageQty = shrinkageQty;
		this.gsmQty = gsmQty;
		this.widthQty = widthQty;
		this.defectQty = defectQty;
		this.remarks = remarks;
		this.rackName = rackName;
		this.binName = binName;
		this.qcPassedType = qcPassedType;
	}
	
	
	
	public AccessoriesSize(String autoId, String supplierId, String supplierName, String purchaseOrder, String styleId,
			String styleNo, String itemId, String itemName, String itemColorId, String itemColor, String accessoriesId,
			String accessoriesName, String accessoriesColorId, String accessoriesColorName, String sizeId,String supplierSizeId,String unitId,String unit, double balanceQty,String rackName,String binName) {
		super();
		this.autoId = autoId;
		this.supplierId = supplierId;
		this.supplierName = supplierName;
		this.purchaseOrder = purchaseOrder;
		this.styleId = styleId;
		this.styleNo = styleNo;
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemColorId = itemColorId;
		this.itemColor = itemColor;
		this.accessoriesId = accessoriesId;
		this.accessoriesName = accessoriesName;
		this.accessoriesColorId = accessoriesColorId;
		this.accessoriesColorName = accessoriesColorName;
		this.sizeId = sizeId;
		this.supplierSizeId = supplierSizeId;
		this.unitId = unitId;
		this.unit = unit;
		this.balanceQty = balanceQty;
		this.rackName = rackName;
		this.binName = binName;
	}
	
	public AccessoriesSize( String purchaseOrder, String styleId,
			String styleNo, String itemId, String itemName, String itemColorId, String itemColor, String accessoriesId,
			String accessoriesName, String accessoriesColorId, String accessoriesColorName, String sizeId,String supplierSizeId,String unitId,String unit, double issueQty,double returnQty,double previousReturnQty) {
		super();
		this.purchaseOrder = purchaseOrder;
		this.styleId = styleId;
		this.styleNo = styleNo;
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemColorId = itemColorId;
		this.itemColor = itemColor;
		this.accessoriesId = accessoriesId;
		this.accessoriesName = accessoriesName;
		this.accessoriesColorId = accessoriesColorId;
		this.accessoriesColorName = accessoriesColorName;
		this.sizeId = sizeId;
		this.supplierSizeId = supplierSizeId;
		this.unitId = unitId;
		this.unit = unit;
		this.issueQty = issueQty;
		this.returnQty = returnQty;
		this.previousReturnQty = previousReturnQty;
	}

	public String getSupplierSizeId() {
		return supplierSizeId;
	}
	public void setSupplierSizeId(String supplierSizeId) {
		this.supplierSizeId = supplierSizeId;
	}

	public double getShadeQty() {
		return shadeQty;
	}
	public void setShadeQty(double shadeQty) {
		this.shadeQty = shadeQty;
	}
	public double getShrinkageQty() {
		return shrinkageQty;
	}
	public void setShrinkageQty(double shrinkageQty) {
		this.shrinkageQty = shrinkageQty;
	}
	public double getGsmQty() {
		return gsmQty;
	}
	public void setGsmQty(double gsmQty) {
		this.gsmQty = gsmQty;
	}
	public double getWidthQty() {
		return widthQty;
	}
	public void setWidthQty(double widthQty) {
		this.widthQty = widthQty;
	}
	public double getDefectQty() {
		return defectQty;
	}
	public void setDefectQty(double defectQty) {
		this.defectQty = defectQty;
	}
	public double getOrderQty() {
		return orderQty;
	}

	public void setOrderQty(double orderQty) {
		this.orderQty = orderQty;
	}

	public double getPreviousReceiveQty() {
		return previousReceiveQty;
	}

	public void setPreviousReceiveQty(double previousReceiveQty) {
		this.previousReceiveQty = previousReceiveQty;
	}

	public double getReturnQty() {
		return returnQty;
	}

	public void setReturnQty(double returnQty) {
		this.returnQty = returnQty;
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


	public String getAccessoriesId() {
		return accessoriesId;
	}


	public void setAccessoriesId(String accessoriesId) {
		this.accessoriesId = accessoriesId;
	}


	public String getAccessoriesName() {
		return accessoriesName;
	}


	public void setAccessoriesName(String accessoriesName) {
		this.accessoriesName = accessoriesName;
	}


	public String getAccessoriesColorId() {
		return accessoriesColorId;
	}


	public void setAccessoriesColorId(String accessoriesColorId) {
		this.accessoriesColorId = accessoriesColorId;
	}


	public String getAccessoriesColorName() {
		return accessoriesColorName;
	}


	public void setAccessoriesColorName(String accessoriesColorName) {
		this.accessoriesColorName = accessoriesColorName;
	}


	public boolean isReturn() {
		return isReturn;
	}

	public void setReturn(boolean isReturn) {
		this.isReturn = isReturn;
	}

	public int getQcPassedType() {
		return qcPassedType;
	}

	public void setQcPassedType(int qcPassedType) {
		this.qcPassedType = qcPassedType;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String TransactionId) {
		this.transactionId = TransactionId;
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
	public String getSizeId() {
		return sizeId;
	}
	public void setSizeId(String sizeId) {
		this.sizeId = sizeId;
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
	public String getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
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
	public String getItemColor() {
		return itemColor;
	}
	public void setItemColor(String itemColor) {
		this.itemColor = itemColor;
	}

	public double getPreviousReturnQty() {
		return previousReturnQty;
	}

	public void setPreviousReturnQty(double previousReturnQty) {
		this.previousReturnQty = previousReturnQty;
	}
	

}
