package pg.proudctModel;

import java.util.ArrayList;

import pg.registerModel.Size;

public class CuttingInformation {
	String autoId="";
	String buyerId="";
	String styleId="";
	String buyerOrderId="";
	String styleNo="";
	String itemId="";
	String itemName="";
	String colorId="";
	String colorName="";
	String purchaseOrder="";
	String sizeGroupId="";
	String sizeListString="";
	String inchargeId="";
	String userId="";
	
	ArrayList<Size> sizeList;
	
	
	public CuttingInformation() {
		
	}
	
	public CuttingInformation(String AutoId,String BuyerId,String BuyerOrderId,String StyleId,String StyleNo,String ItemId,String ItemName,String ColorId,String ColorName,String PurchaseOrder,String sizeGroupId,String UserId) {
		this.autoId=AutoId;
		this.buyerId=BuyerId;
		this.buyerOrderId=BuyerOrderId;
		this.styleId=StyleId;
		this.styleNo=StyleNo;
		this.itemId=ItemId;
		this.itemName=ItemName;
		this.colorId=ColorId;
		this.colorName=ColorName;
		this.purchaseOrder=PurchaseOrder;
		this.sizeGroupId=sizeGroupId;
		this.userId=UserId;
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

	public String getPurchaseOrder() {
		return purchaseOrder;
	}

	public void setPurchaseOrder(String purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}

	public String getSizeGroupId() {
		return sizeGroupId;
	}

	public void setSizeGroupId(String sizeGroupId) {
		this.sizeGroupId = sizeGroupId;
	}

	public String getSizeListString() {
		return sizeListString;
	}

	public void setSizeListString(String sizeListString) {
		this.sizeListString = sizeListString;
	}

	public String getInchargeId() {
		return inchargeId;
	}

	public void setInchargeId(String inchargeId) {
		this.inchargeId = inchargeId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public ArrayList<Size> getSizeList() {
		return sizeList;
	}

	public void setSizeList(ArrayList<Size> sizeList) {
		this.sizeList = sizeList;
	}

	public String getBuyerOrderId() {
		return buyerOrderId;
	}

	public void setBuyerOrderId(String buyerOrderId) {
		this.buyerOrderId = buyerOrderId;
	}
	
	

}
