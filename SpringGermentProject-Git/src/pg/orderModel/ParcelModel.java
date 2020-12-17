package pg.orderModel;

public class ParcelModel {
	  
	 String autoId;
	 String buyerId;
	 String courierId;
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
	 
	 
	 
	public ParcelModel() {
		super();
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
	 
	 
}
