package pg.orderModel;

public class ParcelModel {
	  
	 String autoId;
	 String parcelId;
	 String buyerId;
	 String buyerName;
	 String courierId;
	 String courierName;
	 String trackingNo;
	 String dispatchedDate;
	 String deliveryBy;
	 String deliveryTo;
	 String mobileNo;
	 String parcelItems;
	 String unitId;
	 String grossWeight;
	 String rate;
	 String amount;
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
	 String quantity;
	 
	 
	 
	public ParcelModel() {
		super();
	}
	
	public ParcelModel(String autoId, String buyerId, String buyerName, String courierId, String courierName,
			String trackingNo, String dispatchedDate) {
		super();
		this.autoId = autoId;
		this.buyerId = buyerId;
		this.buyerName = buyerName;
		this.courierId = courierId;
		this.courierName = courierName;
		this.trackingNo = trackingNo;
		this.dispatchedDate = dispatchedDate;
	}
	public ParcelModel(String autoId, String buyerId, String courierId,String trackingNo, String dispatchedDate, String deliveryBy,
			String deliveryTo, String mobileNo, String parcelItems, String unitId, String grossWeight, String rate,
			String amount, String remarks, String userId) {
		super();
		this.autoId = autoId;
		this.buyerId = buyerId;
		this.courierId = courierId;
		this.trackingNo = trackingNo;
		this.dispatchedDate = dispatchedDate;
		this.deliveryBy = deliveryBy;
		this.deliveryTo = deliveryTo;
		this.mobileNo = mobileNo;
		this.parcelItems = parcelItems;
		this.unitId = unitId;
		this.grossWeight = grossWeight;
		this.rate = rate;
		this.amount = amount;
		this.remarks = remarks;
		this.userId = userId;
	}
	
	
	public ParcelModel(String autoId, String buyerId, String buyerName, String styleId, String styleNo,
			String purchaseOrderId, String purchaseOrder, String colorId,
			String colorName, String sizeId, String sizeName, String sampleId, String sampleType, String quantity) {
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
		this.quantity = quantity;
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
	public String getCourierName() {
		return courierName;
	}
	public void setCourierName(String courierName) {
		this.courierName = courierName;
	}
	public String getCourierId() {
		return courierId;
	}
	public void setCourierId(String courierId) {
		this.courierId = courierId;
	}
	public String getTrackingNo() {
		return trackingNo;
	}
	public void setTrackingNo(String trackingNo) {
		this.trackingNo = trackingNo;
	}
	public String getDispatchedDate() {
		return dispatchedDate;
	}
	public void setDispatchedDate(String dispatchedDate) {
		this.dispatchedDate = dispatchedDate;
	}
	public String getDeliveryBy() {
		return deliveryBy;
	}
	public void setDeliveryBy(String deliveryBy) {
		this.deliveryBy = deliveryBy;
	}
	public String getDeliveryTo() {
		return deliveryTo;
	}
	public void setDeliveryTo(String deliveryTo) {
		this.deliveryTo = deliveryTo;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getParcelItems() {
		return parcelItems;
	}
	public void setParcelItems(String parcelItems) {
		this.parcelItems = parcelItems;
	}
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public String getGrossWeight() {
		return grossWeight;
	}
	public void setGrossWeight(String grossWeight) {
		this.grossWeight = grossWeight;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
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

	public String getParcelId() {
		return parcelId;
	}

	public void setParcelId(String parcelId) {
		this.parcelId = parcelId;
	}
	 
	 
}
