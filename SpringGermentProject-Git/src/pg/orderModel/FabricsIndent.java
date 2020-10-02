package pg.orderModel;

public class FabricsIndent {
	String autoId;
	String purchaseOrder;
	String buyerOrderId;
	String buyerName;
	String styleId;
	String styleName;
	String itemId;
	String itemName;
	String itemColorId;
	String itemColorName;
	String fabricsId;
	String fabricsName;
	double qty;
	double dozenQty;
	double consumption;
	double inPercent;
	double percentQty;
	double totalQty;
	String unitId;
	String unit;
	double width;
	double yard;
	double gsm;
	double grandQty;
	double previousReceiveQty;
	String purchaserOrderid;
	
	public FabricsIndent(String BuyerName,String BuyerOrderId, String PurchaseOrder,String styleId, String Stylename,String itemid, String ItemName) {
		this.buyerName=BuyerName;
		this.buyerOrderId=BuyerOrderId;
		this.purchaseOrder = PurchaseOrder;
		this.styleId=styleId;
		this.styleName=Stylename;
		this.itemId=itemid;
		this.itemName=ItemName;
	}
	
	
	
	public double getPreviousReceiveQty() {
		return previousReceiveQty;
	}

	public void setPreviousReceiveQty(double previousReceiveQty) {
		this.previousReceiveQty = previousReceiveQty;
	}

	double rate;
	String fabricsColor;
	String fabricsColorId;
	String brandId;
	String userId;
	

	
	
	public FabricsIndent() {}
	
	public FabricsIndent(String autoId,String itemName,String itemColorName,String fabricsName) {
		this.autoId = autoId;
		this.itemName = itemName;
		this.itemColorName = itemColorName;
		this.fabricsName = fabricsName;
	}
	
	public FabricsIndent(String autoId,String purchaseOrder,String styleId,String styleName,String itemId,String itemName,String itemColorId,String itemColorName,String fabricsId,String fabricsName,String fabricsColorId,String fabricsColor) {
		this.autoId = autoId;
		this.purchaseOrder = purchaseOrder;
		this.styleId = styleId;
		this.styleName = styleName;
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemColorId = itemColorId;
		this.itemColorName = itemColorName;
		this.fabricsId = fabricsId;
		this.fabricsName = fabricsName;
		this.fabricsColorId = fabricsColorId;
		this.fabricsColor = fabricsColor;
	}
	
	public FabricsIndent(String autoId, String purchaseOrder, String styleId, String itemId, String itemColorId,
			String fabricsId,String fabricsName, double qty, double dozenQty, double consumption, double inPercent, double percentQty,
			double totalQty, String unitId, double width, double yard, double gsm, double grandQty,
			String fabricsColorId, String brandId, String userId) {
		super();
		this.autoId = autoId;
		this.purchaseOrder = purchaseOrder;
		this.styleId = styleId;
		this.itemId = itemId;
		this.itemColorId = itemColorId;
		this.fabricsId = fabricsId;
		this.fabricsName = fabricsName;
		this.qty = qty;
		this.dozenQty = dozenQty;
		this.consumption = consumption;
		this.inPercent = inPercent;
		this.percentQty = percentQty;
		this.totalQty = totalQty;
		this.unitId = unitId;
		this.width = width;
		this.yard = yard;
		this.gsm = gsm;
		this.grandQty = grandQty;
		this.fabricsColorId = fabricsColorId;
		this.brandId = brandId;
		this.userId = userId;
	}
	
	public FabricsIndent(String autoId, String purchaseOrder, String styleId,String styleName, String itemId,String itemName, String itemColorId,String itemColorName,
			String fabricsId,String fabricsName, double qty, double dozenQty, double consumption, double inPercent, double percentQty,
			double totalQty, String unitId,String unit) {
		super();
		this.autoId = autoId;
		this.purchaseOrder = purchaseOrder;
		this.styleId = styleId;
		this.styleName = styleName;
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemColorId = itemColorId;
		this.itemColorName = itemColorName;
		this.fabricsId = fabricsId;
		this.fabricsName = fabricsName;
		this.qty = qty;
		this.dozenQty = dozenQty;
		this.consumption = consumption;
		this.inPercent = inPercent;
		this.percentQty = percentQty;
		this.totalQty = totalQty;
		this.unitId = unitId;
		this.unit = unit;
	}
	
	public FabricsIndent(String autoId, String purchaseOrder, String styleId, String styleName,
			String itemId, String itemName, String itemColorId, String itemColorName, String fabricsId,
			String fabricsName, String fabricsColorId, String fabricsColor, String unitId,
			String unit, double grandQty,double previousReceiveQty) {
		super();
		this.autoId = autoId;
		this.purchaseOrder = purchaseOrder;
		this.styleId = styleId;
		this.styleName = styleName;
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemColorId = itemColorId;
		this.itemColorName = itemColorName;
		this.fabricsId = fabricsId;
		this.fabricsName = fabricsName;
		this.fabricsColorId = fabricsColorId;
		this.fabricsColor = fabricsColor;
		this.unitId = unitId;
		this.unit = unit;
		this.grandQty = grandQty;
		this.previousReceiveQty = previousReceiveQty;
	}

	public String getFabricsColor() {
		return fabricsColor;
	}

	public void setFabricsColor(String fabricsColor) {
		this.fabricsColor = fabricsColor;
	}

	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
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
	public double getQty() {
		return qty;
	}
	public void setQty(double qty) {
		this.qty = qty;
	}
	public double getDozenQty() {
		return dozenQty;
	}
	public void setDozenQty(double dozenQty) {
		this.dozenQty = dozenQty;
	}
	public double getConsumption() {
		return consumption;
	}
	public void setConsumption(double consumption) {
		this.consumption = consumption;
	}
	public double getInPercent() {
		return inPercent;
	}
	public void setInPercent(double inPercent) {
		this.inPercent = inPercent;
	}
	public double getPercentQty() {
		return percentQty;
	}
	public void setPercentQty(double percentQty) {
		this.percentQty = percentQty;
	}
	public double getTotalQty() {
		return totalQty;
	}
	public void setTotalQty(double totalQty) {
		this.totalQty = totalQty;
	}
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public double getWidth() {
		return width;
	}
	public void setWidth(double width) {
		this.width = width;
	}
	public double getYard() {
		return yard;
	}
	public void setYard(double yard) {
		this.yard = yard;
	}
	public double getGsm() {
		return gsm;
	}
	public void setGsm(double gsm) {
		this.gsm = gsm;
	}
	public double getGrandQty() {
		return grandQty;
	}
	public void setGrandQty(double grandQty) {
		this.grandQty = grandQty;
	}
	public String getFabricsColorId() {
		return fabricsColorId;
	}
	public void setFabricsColorId(String fabricsColorId) {
		this.fabricsColorId = fabricsColorId;
	}
	public String getBrandId() {
		return brandId;
	}
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getStyleName() {
		return styleName;
	}

	public void setStyleName(String styleName) {
		this.styleName = styleName;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemColorName() {
		return itemColorName;
	}

	public void setItemColorName(String itemColorName) {
		this.itemColorName = itemColorName;
	}

	public String getFabricsName() {
		return fabricsName;
	}

	public void setFabricsName(String fabricsName) {
		this.fabricsName = fabricsName;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getPurchaserOrderid() {
		return purchaserOrderid;
	}

	public void setPurchaserOrderid(String purchaserOrderid) {
		this.purchaserOrderid = purchaserOrderid;
	}



	public String getBuyerOrderId() {
		return buyerOrderId;
	}



	public void setBuyerOrderId(String buyerOrderId) {
		this.buyerOrderId = buyerOrderId;
	}



	public String getBuyerName() {
		return buyerName;
	}



	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	
	
}
