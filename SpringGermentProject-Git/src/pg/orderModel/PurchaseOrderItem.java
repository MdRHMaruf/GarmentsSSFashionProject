package pg.orderModel;

public class PurchaseOrderItem {
	String autoId;
	String purchaseOrder;
	String styleId;
	String styleNo;
	String type;
	String indentItemId;
	String indentItemName;
	String supplierId;
	String supplierName;
	double rate;
	String dollar;
	String colorName;
	String size;
	double qty;
	double grandQty;
	String unit;
	double amount;
	String currency;
	String userId;
	boolean isCheck;
	
	public PurchaseOrderItem() {}
	
	public PurchaseOrderItem(String purchaseOrder,String styleId,String styleNo,String indentItemId,String indentItemName) {
		this.purchaseOrder = purchaseOrder;
		this.styleId = styleId;
		this.styleNo = styleNo;
		this.indentItemId  = indentItemId;
		this.indentItemName = indentItemName;
	}
	
	public PurchaseOrderItem(String purchaseOrder, String styleId, String styleNo, String type, String indentItemId,
			String indentItemName, String supplierId, String supplierName, double rate, String dollar, String colorName,
			String size, double qty, double grandQty, String unit, double amount, String userId) {
		super();
		this.purchaseOrder = purchaseOrder;
		this.styleId = styleId;
		this.styleNo = styleNo;
		this.type = type;
		this.indentItemId = indentItemId;
		this.indentItemName = indentItemName;
		this.supplierId = supplierId;
		this.supplierName = supplierName;
		this.rate = rate;
		this.dollar = dollar;
		this.colorName = colorName;
		this.size = size;
		this.qty = qty;
		this.grandQty = grandQty;
		this.unit = unit;
		this.amount = amount;
		this.userId = userId;
	}
	
	public PurchaseOrderItem(String autoId,String purchaseOrder, String styleNo,String type, String indentItemName, String supplierId, double rate,
			String dollar, String colorName, String size, double qty, double grandQty, String unit,String currency,boolean isCheck) {
		super();
		this.autoId = autoId;
		this.purchaseOrder = purchaseOrder;
		this.styleNo = styleNo;
		this.type = type;
		this.indentItemName = indentItemName;
		this.supplierId = supplierId;
		this.rate = rate;
		this.dollar = dollar;
		this.colorName = colorName;
		this.size = size;
		this.qty = qty;
		this.grandQty = grandQty;
		this.amount = rate * grandQty;
		this.unit = unit;
		this.currency = currency;
		this.isCheck = isCheck;
	}

	
	public PurchaseOrderItem(String autoId, String type, String supplierId, double rate, String dollar,
			double amount, String currency, String userId, boolean isCheck) {
		super();
		this.autoId = autoId;
		this.type = type;
		this.supplierId = supplierId;
		this.rate = rate;
		this.dollar = dollar;
		this.amount = amount;
		this.currency = currency;
		this.userId = userId;
		this.isCheck = isCheck;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public boolean isCheck() {
		return isCheck;
	}

	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}

	public String getAutoId() {
		return autoId;
	}

	public void setAutoId(String autoId) {
		this.autoId = autoId;
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
	public String getStyleNo() {
		return styleNo;
	}
	public void setStyleNo(String styleNo) {
		this.styleNo = styleNo;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIndentItemId() {
		return indentItemId;
	}
	public void setIndentItemId(String indentItemId) {
		this.indentItemId = indentItemId;
	}
	public String getIndentItemName() {
		return indentItemName;
	}
	public void setIndentItemName(String indentItemName) {
		this.indentItemName = indentItemName;
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
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public String getDollar() {
		return dollar;
	}
	public void setDollar(String dollar) {
		this.dollar = dollar;
	}
	public String getColorName() {
		return colorName;
	}
	public void setColorName(String colorName) {
		this.colorName = colorName;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public double getQty() {
		return qty;
	}
	public void setQty(double qty) {
		this.qty = qty;
	}
	public double getGrandQty() {
		return grandQty;
	}
	public void setGrandQty(double grandQty) {
		this.grandQty = grandQty;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
