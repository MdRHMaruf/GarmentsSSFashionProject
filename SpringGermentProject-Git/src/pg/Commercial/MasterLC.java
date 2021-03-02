package pg.Commercial;

public class MasterLC {
	
	String autoId;
	String masterLCNo;
	String buyerId;
	String buyerName;
	String senderBankId;
	String receiverBankId;
	String beneficiaryBankId;
	String throughBankId;
	String date;
	String totalValue;
	String currencyId;
	String shipmentDate;
	String expiryDate;
	String remarks;
	String userId;
	String styleList;
	String editedStyleList;
	String amendmentNo;
	String amendmentDate;
	
	public MasterLC() {
		super();
	}
	
	public MasterLC(String autoId,String masterLCNo,String buyerId,String amendmentNo,String amendmentDate) {
		this.autoId = autoId;
		this.masterLCNo = masterLCNo;
		this.buyerId  = buyerId;
		this.amendmentNo = amendmentNo;
		this.amendmentDate = amendmentDate;
	}
	
	public MasterLC(String autoId, String masterLCNo, String buyerId, String buyerName, String senderBankId,
			String receiverBankId, String beneficiaryBankId, String throughBankId, String date, String totalValue,
			String currencyId, String shipmentDate, String expiryDate, String remarks, String userId) {
		super();
		this.autoId = autoId;
		this.masterLCNo = masterLCNo;
		this.buyerId = buyerId;
		this.buyerName = buyerName;
		this.senderBankId = senderBankId;
		this.receiverBankId = receiverBankId;
		this.beneficiaryBankId = beneficiaryBankId;
		this.throughBankId = throughBankId;
		this.date = date;
		this.totalValue = totalValue;
		this.currencyId = currencyId;
		this.shipmentDate = shipmentDate;
		this.expiryDate = expiryDate;
		this.remarks = remarks;
		this.userId = userId;
	}
	public String getAutoId() {
		return autoId;
	}
	public void setAutoId(String autoId) {
		this.autoId = autoId;
	}
	public String getMasterLCNo() {
		return masterLCNo;
	}
	public void setMasterLCNo(String masterLCNo) {
		this.masterLCNo = masterLCNo;
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
	public String getSenderBankId() {
		return senderBankId;
	}
	public void setSenderBankId(String senderBankId) {
		this.senderBankId = senderBankId;
	}
	public String getReceiverBankId() {
		return receiverBankId;
	}
	public void setReceiverBankId(String receiverBankId) {
		this.receiverBankId = receiverBankId;
	}
	public String getBeneficiaryBankId() {
		return beneficiaryBankId;
	}
	public void setBeneficiaryBankId(String beneficiaryBankId) {
		this.beneficiaryBankId = beneficiaryBankId;
	}
	public String getThroughBankId() {
		return throughBankId;
	}
	public void setThroughBankId(String throughBankId) {
		this.throughBankId = throughBankId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTotalValue() {
		return totalValue;
	}
	public void setTotalValue(String totalValue) {
		this.totalValue = totalValue;
	}
	public String getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}
	public String getShipmentDate() {
		return shipmentDate;
	}
	public void setShipmentDate(String shipmentDate) {
		this.shipmentDate = shipmentDate;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
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
	public String getStyleList() {
		return styleList;
	}
	public void setStyleList(String styleList) {
		this.styleList = styleList;
	}

	public String getAmendmentNo() {
		return amendmentNo;
	}

	public void setAmendmentNo(String amendmentNo) {
		this.amendmentNo = amendmentNo;
	}

	public String getAmendmentDate() {
		return amendmentDate;
	}

	public void setAmendmentDate(String amendmentDate) {
		this.amendmentDate = amendmentDate;
	}
	
	
	public String getEditedStyleList() {
		return editedStyleList;
	}

	public void setEditedStyleList(String editedStyleList) {
		this.editedStyleList = editedStyleList;
	}



	public class StyleInfo{
		String autoId;
		String styleId;
		String styleNo;
		String itemId;
		String itemName;
		String purchaseOrderId;
		String purchaseOrder;
		String quantity;
		String unitPrice;
		String amount;
		
		public StyleInfo() {
			super();
		}
		
		public StyleInfo(String autoId, String styleId, String styleNo, String itemId, String itemName,
				String purchaseOrderId, String purchaseOrder, String quantity, String unitPrice, String amount) {
			super();
			this.autoId = autoId;
			this.styleId = styleId;
			this.styleNo = styleNo;
			this.itemId = itemId;
			this.itemName = itemName;
			this.purchaseOrderId = purchaseOrderId;
			this.purchaseOrder = purchaseOrder;
			this.quantity = quantity;
			this.unitPrice = unitPrice;
			this.amount = amount;
		}
		public String getAutoId() {
			return autoId;
		}
		public void setAutoId(String autoId) {
			this.autoId = autoId;
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
		public String getItemId() {
			return itemId;
		}
		public void setItemId(String itemId) {
			this.itemId = itemId;
		}
		public String getItemName() {
			return itemName;
		}
		public void setItemName(String itemName) {
			this.itemName = itemName;
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
		public String getQuantity() {
			return quantity;
		}
		public void setQuantity(String quantity) {
			this.quantity = quantity;
		}
		public String getUnitPrice() {
			return unitPrice;
		}
		public void setUnitPrice(String unitPrice) {
			this.unitPrice = unitPrice;
		}
		public String getAmount() {
			return amount;
		}
		public void setAmount(String amount) {
			this.amount = amount;
		}	
	}
	
}
