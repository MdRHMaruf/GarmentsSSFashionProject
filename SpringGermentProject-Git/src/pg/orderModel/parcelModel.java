package pg.orderModel;

public class parcelModel {
	  String user;
	  String id;
	  String styleNo ;
	  String itemName ;
	  String sampletype ;	  
	  String dispatchedDate;	
	  String courierName;
	  String trackingNo ;
	  String grossWeight ;
	  String unit ;
	  String totalQty ;
	  String parcel ;
	  String rate ;
	  String amount ;
	  String deiveryDate ;
	  String delieryTime ;
	  String deliveryTo ;
	  
	  public parcelModel() {
		  
	  }
	  
	  public parcelModel(String id, String style, String item, String tracking) {
		  this.id=id;
		  this.styleNo=style;
		  this.itemName=item;
		  this.trackingNo=tracking;
	  }
	  
	  
	public parcelModel(String id,String styleNo, String itemName, String sampletype, String dispatchedDate, String courierName,
			String trackingNo, String grossWeight, String unit, String totalQty, String parcel, String rate,
			String amount, String deiveryDate, String delieryTime, String deliveryTo,String user) {
		super();
		this.id=id;
		
		this.styleNo = styleNo;
		this.itemName = itemName;
		this.sampletype = sampletype;
		this.dispatchedDate = dispatchedDate;
		this.courierName = courierName;
		this.trackingNo = trackingNo;
		this.grossWeight = grossWeight;
		this.unit = unit;
		this.totalQty = totalQty;
		this.parcel = parcel;
		this.rate = rate;
		this.amount = amount;
		this.deiveryDate = deiveryDate;
		this.delieryTime = delieryTime;
		this.deliveryTo = deliveryTo;
		this.user=user;
	}
	
	
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}




	public void setUser(String user) {
		this.user = user;
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
	public String getSampletype() {
		return sampletype;
	}
	public void setSampletype(String sampletype) {
		this.sampletype = sampletype;
	}
	public String getDispatchedDate() {
		return dispatchedDate;
	}
	public void setDispatchedDate(String dispatchedDate) {
		this.dispatchedDate = dispatchedDate;
	}
	public String getCourierName() {
		return courierName;
	}
	public void setCourierName(String courierName) {
		this.courierName = courierName;
	}
	public String getTrackingNo() {
		return trackingNo;
	}
	public void setTrackingNo(String trackingNo) {
		this.trackingNo = trackingNo;
	}
	public String getGrossWeight() {
		return grossWeight;
	}
	public void setGrossWeight(String grossWeight) {
		this.grossWeight = grossWeight;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getTotalQty() {
		return totalQty;
	}
	public void setTotalQty(String totalQty) {
		this.totalQty = totalQty;
	}
	public String getParcel() {
		return parcel;
	}
	public void setParcel(String parcel) {
		this.parcel = parcel;
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
	public String getDeiveryDate() {
		return deiveryDate;
	}
	public void setDeiveryDate(String deiveryDate) {
		this.deiveryDate = deiveryDate;
	}
	public String getDelieryTime() {
		return delieryTime;
	}
	public void setDelieryTime(String delieryTime) {
		this.delieryTime = delieryTime;
	}
	public String getDeliveryTo() {
		return deliveryTo;
	}
	public void setDeliveryTo(String deliveryTo) {
		this.deliveryTo = deliveryTo;
	}
	  
	  
	  
	  
	  

}
