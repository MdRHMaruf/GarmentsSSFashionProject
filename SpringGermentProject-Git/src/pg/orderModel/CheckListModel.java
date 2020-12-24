package pg.orderModel;

public class CheckListModel {
	  
	 String autoId;
	 String checkListId;
	 String buyerId;
	 String buyerName;
	 String checkListItems;
	 String remarks;
	 String userId;
	 
	 String styleId;
	 String styleNo;
	 String purchaseOrderId;
	 String purchaseOrder;
	 String colorId;
	 String colorName;
	 String sizeId;
	 String sizeName;
	 String sampleId;
	 String sampleType;
	 String itemType;
	 String accItemId;
	 String accItemName;
	 String quantity;	 
	 String status;
	 
	public CheckListModel() {
		super();
	}
	
	public CheckListModel(String autoId, String checkListId, String buyerId, String buyerName, String sampleId,String sampleType,String checkListItems,
			String remarks, String userId) {
		super();
		this.autoId = autoId;
		this.checkListId = checkListId;
		this.buyerId = buyerId;
		this.buyerName = buyerName;
		this.sampleId = sampleId;
		this.sampleType = sampleType;
		this.checkListItems = checkListItems;
		this.remarks = remarks;
		this.userId = userId;
	}
	
	public CheckListModel(String autoId, String buyerId, String sampleId,String remarks) {
		super();
		this.autoId = autoId;
		this.checkListId = autoId;
		this.buyerId = buyerId;
		this.sampleId = sampleId;
		this.remarks = remarks;
	}

	public CheckListModel(String autoId, String buyerId, String buyerName, String styleId, String styleNo,
			String purchaseOrderId, String purchaseOrder, String colorId,
			String colorName, String sizeId, String sizeName, String sampleId, String sampleType,String itemType,String accItemId,String accItemName, String quantity,String status) {
		super();
		this.autoId = autoId;
		this.buyerId = buyerId;
		this.buyerName = buyerName;
		this.styleId = styleId;
		this.styleNo = styleNo;
		this.purchaseOrderId = purchaseOrderId;
		this.purchaseOrder = purchaseOrder;
		this.colorId = colorId;
		this.colorName = colorName;
		this.sizeId = sizeId;
		this.sizeName = sizeName;
		this.sampleId = sampleId;
		this.sampleType = sampleType;
		this.itemType = itemType;
		this.accItemId = accItemId;
		this.accItemName = accItemName;
		this.quantity = quantity;
		this.status = status;
	}

	public String getAutoId() {
		return autoId;
	}
	public void setAutoId(String autoId) {
		this.autoId = autoId;
	}
	public String getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}
	
	public String getBuyerName() {
		return buyerName;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getPurchaseOrderId() {
		return purchaseOrderId;
	}

	public void setPurchaseOrderId(String purchaseOrderId) {
		this.purchaseOrderId = purchaseOrderId;
	}

	public String getPurchaseOrder() {
		return purchaseOrder;
	}

	public void setPurchaseOrder(String purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
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

	public String getSampleId() {
		return sampleId;
	}

	public void setSampleId(String sampleId) {
		this.sampleId = sampleId;
	}

	public String getSampleType() {
		return sampleType;
	}

	public void setSampleType(String sampleType) {
		this.sampleType = sampleType;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getCheckListId() {
		return checkListId;
	}
	public void setCheckListId(String checkListId) {
		this.checkListId = checkListId;
	}
	public String getCheckListItems() {
		return checkListItems;
	}
	public void setCheckListItems(String checkListItems) {
		this.checkListItems = checkListItems;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getAccItemId() {
		return accItemId;
	}

	public void setAccItemId(String accItemId) {
		this.accItemId = accItemId;
	}

	public String getAccItemName() {
		return accItemName;
	}

	public void setAccItemName(String accItemName) {
		this.accItemName = accItemName;
	}

	
}
