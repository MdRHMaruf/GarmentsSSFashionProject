package pg.storeModel;

public class CuttingFabricsUsed {
	String buyerName="";
	String purchaseOrder="";
	String styleId="";
	String itemId="";
	String styleNo="";
	String itemName="";
	String colorId="";
	String colorName="";
	String fabricsId="";
	String fabricsItem="";
	String fabricsColor="";
	String fabricsItemColor="";
	String unitId="";
	String unitName="";
	String usedFabrics="";
	String cuttingEntryId="";
	String resultList="";
	String userId="";
	String requisitionQty="";
	String cuttingNo="";
	String date="";
	
	public CuttingFabricsUsed() {
		
	}
	
	

	public CuttingFabricsUsed(String CuttingEntryId,String purchaseOrder, String styleId, String itemId, String styleNo, String itemName,
			String colorId, String UsedFabrics,String colorName, String fabricsId, String fabricsItem, String fabricsColor,
			String fabricsItemColor,String UnitId,String UnitName) {
		this.cuttingEntryId = CuttingEntryId;
		this.purchaseOrder = purchaseOrder;
		this.styleId = styleId;
		this.itemId = itemId;
		this.styleNo = styleNo;
		this.itemName = itemName;
		this.colorId = colorId;
		this.usedFabrics=UsedFabrics;
		this.colorName = colorName;
		this.fabricsId = fabricsId;
		this.fabricsItem = fabricsItem;
		this.fabricsColor = fabricsColor;
		this.fabricsItemColor = fabricsItemColor;
		this.unitId = UnitId;
		this.unitName = UnitName;
	}



	public CuttingFabricsUsed(String CuttingEntryId,String BuyerName,String purchaseOrder, String StyleNo,String ItemName, String FabricsItem, String ColorName,
			String RequisitionQty, String UnitName,String CuttingNo, String Date) {
		this.cuttingEntryId = CuttingEntryId;
		this.buyerName=BuyerName;
		this.purchaseOrder = purchaseOrder;
		this.styleNo = StyleNo;
		this.itemName = ItemName;
		this.colorName = ColorName;
		this.fabricsItem = FabricsItem;
		this.requisitionQty = RequisitionQty;
		this.unitName = UnitName;
		this.cuttingNo = CuttingNo;
		this.date = Date;
	}
	
	public String getUsedFabrics() {
		return usedFabrics;
	}



	public void setUsedFabrics(String usedFabrics) {
		this.usedFabrics = usedFabrics;
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

	public String getColorId() {
		return colorId;
	}

	public void setColorId(String colorId) {
		this.colorId = colorId;
	}

	public String getColorName() {
		return colorName;
	}

	public void setColorName(String colorName) {
		this.colorName = colorName;
	}

	public String getFabricsId() {
		return fabricsId;
	}

	public void setFabricsId(String fabricsId) {
		this.fabricsId = fabricsId;
	}

	public String getFabricsItem() {
		return fabricsItem;
	}

	public void setFabricsItem(String fabricsItem) {
		this.fabricsItem = fabricsItem;
	}

	public String getFabricsColor() {
		return fabricsColor;
	}

	public void setFabricsColor(String fabricsColor) {
		this.fabricsColor = fabricsColor;
	}

	public String getFabricsItemColor() {
		return fabricsItemColor;
	}

	public void setFabricsItemColor(String fabricsItemColor) {
		this.fabricsItemColor = fabricsItemColor;
	}



	public String getUnitId() {
		return unitId;
	}



	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}



	public String getUnitName() {
		return unitName;
	}



	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}



	public String getCuttingEntryId() {
		return cuttingEntryId;
	}



	public void setCuttingEntryId(String cuttingEntryId) {
		this.cuttingEntryId = cuttingEntryId;
	}



	public String getResultList() {
		return resultList;
	}



	public void setResultList(String resultList) {
		this.resultList = resultList;
	}



	public String getUserId() {
		return userId;
	}



	public void setUserId(String userId) {
		this.userId = userId;
	}



	public String getBuyerName() {
		return buyerName;
	}



	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}



	public String getRequisitionQty() {
		return requisitionQty;
	}



	public void setRequisitionQty(String requisitionQty) {
		this.requisitionQty = requisitionQty;
	}



	public String getCuttingNo() {
		return cuttingNo;
	}



	public void setCuttingNo(String cuttingNo) {
		this.cuttingNo = cuttingNo;
	}



	public String getDate() {
		return date;
	}



	public void setDate(String date) {
		this.date = date;
	}
	
	
}
