package pg.Commercial;

public class deedOfContacts {
	
	 String userid ;
	  String buyerId ;
	  String contractId ;
	  String receivedDate ;
	  String purchaseOrder ;
	  String expiryDate ;
	  String styleNo ;
	  String ammendmentDate;
	  String courieer ;
	  String itemName ;
	  String extendedDate;
	  String forwardAddress ;
	  String itemColor;
	  String exportDate ;
	  
	  
	  
	  String goodsDescription ;
	  String rollQty ;
	  String invoiceNumber;
	  String unMakeingDate ;
	  String ctnQty ;
	  String invoiceDate ;
	  String unAmmendment;
	  String grossWeight;
	  String awbNumber ;
	  String unSubmitDate ;
	  String netWeight;
	  String blDate ;
	  String UNReceivedDate ;
	  String unit ;
	  String trackingNumber ;
	  String unHoverDate ;
	  String unitPrice;
	  String shipperAddress ;
	  String birthingDate ;
	  
	  
	  
	  String currency ;
	  String amount ;
	  String consignAddress ;
	  String etdDate ;
	  String cfHandoverDate ;
	  String masterLC ;
	  String etaDate ;
	  String cfAddress ;
	  String bblc ;
	  String etcDate ;
	  String telephone ;
	  String vvsselName ;
	  String clearDate ;
	  String mobile ;
	  String invoiceQty ; 
	  String contactNo ;
	  String faxNo ;
	  String onBoardDate ;
	  String readyDate ;
	  String contactPerson ;  
	  String submitDate ;
	  
	  public deedOfContacts() {
		  
	  }
	  
	  
	  
  public deedOfContacts(String contractId, String pono, String styleid, String itemname) {
		  this.contractId=contractId;
		  this.purchaseOrder=pono;
		  this.styleNo=styleid;
		  this.itemName=itemname;
	  }
	  
	  
	public deedOfContacts(String userid, String buyerId, String contractId, String receivedDate, String purchaseOrder,
			String expiryDate, String styleNo, String ammendmentDate, String courieer, String itemName,
			String extendedDate, String forwardAddress, String itemColor, String exportDate, String goodsDescription,
			String rollQty, String invoiceNumber, String unMakeingDate, String ctnQty, String invoiceDate,
			String unAmmendment, String grossWeight, String awbNumber, String unSubmitDate, String netWeight,
			String blDate, String uNReceivedDate, String unit, String trackingNumber, String unHoverDate,
			String unitPrice, String shipperAddress, String birthingDate, String currency, String amount,
			String consignAddress, String etdDate, String cfHandoverDate, String masterLC, String etaDate,
			String cfAddress, String bblc, String etcDate, String telephone, String vvsselName, String clearDate,
			String mobile, String invoiceQty, String contactNo, String faxNo, String onBoardDate, String readyDate,
			String contactPerson, String submitDate) {
		super();
		this.userid = userid;
		this.buyerId = buyerId;
		this.contractId = contractId;
		this.receivedDate = receivedDate;
		this.purchaseOrder = purchaseOrder;
		this.expiryDate = expiryDate;
		this.styleNo = styleNo;
		this.ammendmentDate = ammendmentDate;
		this.courieer = courieer;
		this.itemName = itemName;
		this.extendedDate = extendedDate;
		this.forwardAddress = forwardAddress;
		this.itemColor = itemColor;
		this.exportDate = exportDate;
		this.goodsDescription = goodsDescription;
		this.rollQty = rollQty;
		this.invoiceNumber = invoiceNumber;
		this.unMakeingDate = unMakeingDate;
		this.ctnQty = ctnQty;
		this.invoiceDate = invoiceDate;
		this.unAmmendment = unAmmendment;
		this.grossWeight = grossWeight;
		this.awbNumber = awbNumber;
		this.unSubmitDate = unSubmitDate;
		this.netWeight = netWeight;
		this.blDate = blDate;
		this.UNReceivedDate = uNReceivedDate;
		this.unit = unit;
		this.trackingNumber = trackingNumber;
		this.unHoverDate = unHoverDate;
		this.unitPrice = unitPrice;
		this.shipperAddress = shipperAddress;
		this.birthingDate = birthingDate;
		this.currency = currency;
		this.amount = amount;
		this.consignAddress = consignAddress;
		this.etdDate = etdDate;
		this.cfHandoverDate = cfHandoverDate;
		this.masterLC = masterLC;
		this.etaDate = etaDate;
		this.cfAddress = cfAddress;
		this.bblc = bblc;
		this.etcDate = etcDate;
		this.telephone = telephone;
		this.vvsselName = vvsselName;
		this.clearDate = clearDate;
		this.mobile = mobile;
		this.invoiceQty = invoiceQty;
		this.contactNo = contactNo;
		this.faxNo = faxNo;
		this.onBoardDate = onBoardDate;
		this.readyDate = readyDate;
		this.contactPerson = contactPerson;
		this.submitDate = submitDate;
	}
	
	
	
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}
	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public String getReceivedDate() {
		return receivedDate;
	}
	public void setReceivedDate(String receivedDate) {
		this.receivedDate = receivedDate;
	}
	public String getPurchaseOrder() {
		return purchaseOrder;
	}
	public void setPurchaseOrder(String purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getStyleNo() {
		return styleNo;
	}
	public void setStyleNo(String styleNo) {
		this.styleNo = styleNo;
	}
	public String getAmmendmentDate() {
		return ammendmentDate;
	}
	public void setAmmendmentDate(String ammendmentDate) {
		this.ammendmentDate = ammendmentDate;
	}
	public String getCourieer() {
		return courieer;
	}
	public void setCourieer(String courieer) {
		this.courieer = courieer;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getExtendedDate() {
		return extendedDate;
	}
	public void setExtendedDate(String extendedDate) {
		this.extendedDate = extendedDate;
	}
	public String getForwardAddress() {
		return forwardAddress;
	}
	public void setForwardAddress(String forwardAddress) {
		this.forwardAddress = forwardAddress;
	}
	public String getItemColor() {
		return itemColor;
	}
	public void setItemColor(String itemColor) {
		this.itemColor = itemColor;
	}
	public String getExportDate() {
		return exportDate;
	}
	public void setExportDate(String exportDate) {
		this.exportDate = exportDate;
	}
	public String getGoodsDescription() {
		return goodsDescription;
	}
	public void setGoodsDescription(String goodsDescription) {
		this.goodsDescription = goodsDescription;
	}
	public String getRollQty() {
		return rollQty;
	}
	public void setRollQty(String rollQty) {
		this.rollQty = rollQty;
	}
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public String getUnMakeingDate() {
		return unMakeingDate;
	}
	public void setUnMakeingDate(String unMakeingDate) {
		this.unMakeingDate = unMakeingDate;
	}
	public String getCtnQty() {
		return ctnQty;
	}
	public void setCtnQty(String ctnQty) {
		this.ctnQty = ctnQty;
	}
	public String getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public String getUnAmmendment() {
		return unAmmendment;
	}
	public void setUnAmmendment(String unAmmendment) {
		this.unAmmendment = unAmmendment;
	}
	public String getGrossWeight() {
		return grossWeight;
	}
	public void setGrossWeight(String grossWeight) {
		this.grossWeight = grossWeight;
	}
	public String getAwbNumber() {
		return awbNumber;
	}
	public void setAwbNumber(String awbNumber) {
		this.awbNumber = awbNumber;
	}
	public String getUnSubmitDate() {
		return unSubmitDate;
	}
	public void setUnSubmitDate(String unSubmitDate) {
		this.unSubmitDate = unSubmitDate;
	}
	public String getNetWeight() {
		return netWeight;
	}
	public void setNetWeight(String netWeight) {
		this.netWeight = netWeight;
	}
	public String getBlDate() {
		return blDate;
	}
	public void setBlDate(String blDate) {
		this.blDate = blDate;
	}
	public String getUNReceivedDate() {
		return UNReceivedDate;
	}
	public void setUNReceivedDate(String uNReceivedDate) {
		UNReceivedDate = uNReceivedDate;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getTrackingNumber() {
		return trackingNumber;
	}
	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}
	public String getUnHoverDate() {
		return unHoverDate;
	}
	public void setUnHoverDate(String unHoverDate) {
		this.unHoverDate = unHoverDate;
	}
	public String getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}
	public String getShipperAddress() {
		return shipperAddress;
	}
	public void setShipperAddress(String shipperAddress) {
		this.shipperAddress = shipperAddress;
	}
	public String getBirthingDate() {
		return birthingDate;
	}
	public void setBirthingDate(String birthingDate) {
		this.birthingDate = birthingDate;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getConsignAddress() {
		return consignAddress;
	}
	public void setConsignAddress(String consignAddress) {
		this.consignAddress = consignAddress;
	}
	public String getEtdDate() {
		return etdDate;
	}
	public void setEtdDate(String etdDate) {
		this.etdDate = etdDate;
	}
	public String getCfHandoverDate() {
		return cfHandoverDate;
	}
	public void setCfHandoverDate(String cfHandoverDate) {
		this.cfHandoverDate = cfHandoverDate;
	}
	public String getMasterLC() {
		return masterLC;
	}
	public void setMasterLC(String masterLC) {
		this.masterLC = masterLC;
	}
	public String getEtaDate() {
		return etaDate;
	}
	public void setEtaDate(String etaDate) {
		this.etaDate = etaDate;
	}
	public String getCfAddress() {
		return cfAddress;
	}
	public void setCfAddress(String cfAddress) {
		this.cfAddress = cfAddress;
	}
	public String getBblc() {
		return bblc;
	}
	public void setBblc(String bblc) {
		this.bblc = bblc;
	}
	public String getEtcDate() {
		return etcDate;
	}
	public void setEtcDate(String etcDate) {
		this.etcDate = etcDate;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getVvsselName() {
		return vvsselName;
	}
	public void setVvsselName(String vvsselName) {
		this.vvsselName = vvsselName;
	}
	public String getClearDate() {
		return clearDate;
	}
	public void setClearDate(String clearDate) {
		this.clearDate = clearDate;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getInvoiceQty() {
		return invoiceQty;
	}
	public void setInvoiceQty(String invoiceQty) {
		this.invoiceQty = invoiceQty;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public String getFaxNo() {
		return faxNo;
	}
	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}
	public String getOnBoardDate() {
		return onBoardDate;
	}
	public void setOnBoardDate(String onBoardDate) {
		this.onBoardDate = onBoardDate;
	}
	public String getReadyDate() {
		return readyDate;
	}
	public void setReadyDate(String readyDate) {
		this.readyDate = readyDate;
	}
	public String getContactPerson() {
		return contactPerson;
	}
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	public String getSubmitDate() {
		return submitDate;
	}
	public void setSubmitDate(String submitDate) {
		this.submitDate = submitDate;
	}
	  
	  
	  
	  
	
	

}
