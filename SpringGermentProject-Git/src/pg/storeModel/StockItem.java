package pg.storeModel;

public class StockItem {
	
	String sizeId;
	String sizeName;
	String purchaseOrder;
	String styleId;
	String styleNo;
	String itemId;
	String itemName;
	String itemColorId;
	String itemColor;
	String stockItemId;
	String stockItemName;
	String stockItemColorId;
	String stockItemColorName;
	String unitId;
	String unit;
	double unitQty;
	double openingBalance;
	double receiveQty;
	double qcPassedQty;
	double returnQty;
	double issueQty;
	double issueReturnQty;
	double transferInQty;
	double transferOutQty;
	double closingBalance;
	double balanceQty;
	
	String userId;
	int qcPassedType;

	String type;

	public StockItem() {}

	
	public StockItem( String purchaseOrder, String styleId,
			String styleNo, String itemId, String itemName, String itemColorId, String itemColor, String stockItemId,
			String stockItemName, String stockItemColorId, String stockItemColorName, String sizeId,String sizeName,String unitId,String unit,double balanceQty,String type) {
		super();
		this.purchaseOrder = purchaseOrder;
		this.styleId = styleId;
		this.styleNo = styleNo;
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemColorId = itemColorId;
		this.itemColor = itemColor;
		this.stockItemId = stockItemId;
		this.stockItemName = stockItemName;
		this.stockItemColorId = stockItemColorId;
		this.stockItemColorName = stockItemColorName;
		this.sizeId = sizeId;
		this.sizeName = sizeName;
		this.unitId = unitId;
		this.unit = unit;
		this.balanceQty = balanceQty;
		this.type = type;
	}
	
	public StockItem( String purchaseOrder, String styleId,
			String styleNo, String itemId, String itemName, String itemColorId, String itemColor, String stockItemId,
			String stockItemName, String stockItemColorId, String stockItemColorName, String sizeId,String sizeName,String unitId,String unit,double openingBalance,double receiveQty,double qcPassedQty,double returnQty,double issueQty,double issueReturnQty,double transferInQty,double transferOutQty,double closingQty,String type) {
		super();
		this.purchaseOrder = purchaseOrder;
		this.styleId = styleId;
		this.styleNo = styleNo;
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemColorId = itemColorId;
		this.itemColor = itemColor;
		this.stockItemId = stockItemId;
		this.stockItemName = stockItemName;
		this.stockItemColorId = stockItemColorId;
		this.stockItemColorName = stockItemColorName;
		this.sizeId = sizeId;
		this.sizeName = sizeName;
		this.unitId = unitId;
		this.unit = unit;
		this.openingBalance = openingBalance;
		this.receiveQty = openingBalance;
		this.qcPassedQty = qcPassedQty;
		this.returnQty = returnQty;
		this.issueQty = issueQty;
		this.issueReturnQty = issueReturnQty;
		this.transferInQty = transferInQty;
		this.transferOutQty = transferOutQty;
		this.closingBalance = closingQty;
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSizeName() {
		return sizeName;
	}
	public void setSizeName(String sizeName) {
		this.sizeName = sizeName;
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


	public String getStockItemId() {
		return stockItemId;
	}


	public void setStockItemId(String stockItemId) {
		this.stockItemId = stockItemId;
	}


	public String getStockItemName() {
		return stockItemName;
	}


	public void setStockItemName(String stockItemName) {
		this.stockItemName = stockItemName;
	}


	public String getStockItemColorId() {
		return stockItemColorId;
	}


	public void setStockItemColorId(String stockItemColorId) {
		this.stockItemColorId = stockItemColorId;
	}


	public String getStockItemColorName() {
		return stockItemColorName;
	}


	public void setStockItemColorName(String stockItemColorName) {
		this.stockItemColorName = stockItemColorName;
	}

	public int getQcPassedType() {
		return qcPassedType;
	}

	public void setQcPassedType(int qcPassedType) {
		this.qcPassedType = qcPassedType;
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


	public double getOpeningBalance() {
		return openingBalance;
	}

	public void setOpeningBalance(double openingBalance) {
		this.openingBalance = openingBalance;
	}

	public double getReceiveQty() {
		return receiveQty;
	}

	public void setReceiveQty(double receiveQty) {
		this.receiveQty = receiveQty;
	}

	public double getIssueReturnQty() {
		return issueReturnQty;
	}

	public void setIssueReturnQty(double issueReturnQty) {
		this.issueReturnQty = issueReturnQty;
	}

	public double getTransferInQty() {
		return transferInQty;
	}

	public void setTransferInQty(double transferInQty) {
		this.transferInQty = transferInQty;
	}


	public double getTransferOutQty() {
		return transferOutQty;
	}


	public void setTransferOutQty(double transferOutQty) {
		this.transferOutQty = transferOutQty;
	}


	public double getClosingBalance() {
		return closingBalance;
	}


	public void setClosingBalance(double closingBalance) {
		this.closingBalance = closingBalance;
	}

}
