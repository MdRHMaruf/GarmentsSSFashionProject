package pg.storeModel;

public class StoreGeneralReceived {
	String userId="";
	String invoiceNo="";
	String ItemId="";
	String unitId="";
	String qty="";
	String pirce="";
	String type="";
	String autoId="";
	String itemName="";
	String unitName="";
	String totalPrice="";
	String challanNo="";
	String supplierId="";
	String supplierName="";
	String date="";
	
	public StoreGeneralReceived() {
		
	}
	
	
	
	public StoreGeneralReceived(String AutoId, String ItemName, String UnitName, String Qty,String BuyPrice, String TotalPrice
			) {
	this.autoId=AutoId;
	this.itemName=ItemName;
	this.unitName=UnitName;
	this.qty=Qty;
	this.pirce=BuyPrice;
	this.totalPrice=TotalPrice;
	}



	public StoreGeneralReceived(String InvoiceNo, String SupplierName, String ChallanNo, String Date) {
		this.invoiceNo=InvoiceNo;
		this.supplierName=SupplierName;
		this.challanNo=ChallanNo;
		this.date=Date;
	}



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}



	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getItemId() {
		return ItemId;
	}
	public void setItemId(String itemId) {
		ItemId = itemId;
	}
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public String getQty() {
		return qty;
	}
	public void setQty(String qty) {
		this.qty = qty;
	}



	public String getPirce() {
		return pirce;
	}



	public void setPirce(String pirce) {
		this.pirce = pirce;
	}



	public String getAutoId() {
		return autoId;
	}



	public void setAutoId(String autoId) {
		this.autoId = autoId;
	}



	public String getItemName() {
		return itemName;
	}



	public void setItemName(String itemName) {
		this.itemName = itemName;
	}



	public String getUnitName() {
		return unitName;
	}



	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}



	public String getTotalPrice() {
		return totalPrice;
	}



	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}



	public String getChallanNo() {
		return challanNo;
	}



	public void setChallanNo(String challanNo) {
		this.challanNo = challanNo;
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



	public String getDate() {
		return date;
	}



	public void setDate(String date) {
		this.date = date;
	}
	
	
}
