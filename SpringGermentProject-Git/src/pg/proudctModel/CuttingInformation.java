package pg.proudctModel;

import java.util.ArrayList;

import pg.registerModel.Size;

public class CuttingInformation {
	String autoId="";
	String buyerId="";
	String buyerName="";
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
	String colorlistvalue="";

	String userId="";
	
	String factoryId="";
	String departmentId="";
	String lineId="";
	String inchargeId="";
	String cuttingNo="";
	String cuttingDate="";
	String markingLayer="";
	String markingLength="";
	String markingWidth="";
	String resultvalue="";
	String sizegroupvalue="";
	String cuttinglistvalue="";
	String cuttingEntryId="";
	
	ArrayList<Size> sizeList;
	
	
	public CuttingInformation() {
		
	}
	
	
	public CuttingInformation(String CuttingEntryId,String BuyerName,String PurchaseOrder,String StyleName,String ItemName,String CuttingNo,String CuttingDate) {
		this.cuttingEntryId=CuttingEntryId;
		this.buyerName=BuyerName;
		this.purchaseOrder=PurchaseOrder;
		this.styleNo=StyleName;
		this.itemName=ItemName;
		this.cuttingNo=CuttingNo;
		this.cuttingDate=CuttingDate;
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

	public String getFactoryId() {
		return factoryId;
	}

	public void setFactoryId(String factoryId) {
		this.factoryId = factoryId;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

	public String getCuttingNo() {
		return cuttingNo;
	}

	public void setCuttingNo(String cuttingNo) {
		this.cuttingNo = cuttingNo;
	}

	public String getCuttingDate() {
		return cuttingDate;
	}

	public void setCuttingDate(String cuttingDate) {
		this.cuttingDate = cuttingDate;
	}

	public String getMarkingLayer() {
		return markingLayer;
	}

	public void setMarkingLayer(String markingLayer) {
		this.markingLayer = markingLayer;
	}

	public String getMarkingLength() {
		return markingLength;
	}

	public void setMarkingLength(String markingLength) {
		this.markingLength = markingLength;
	}

	public String getMarkingWidth() {
		return markingWidth;
	}

	public void setMarkingWidth(String markingWidth) {
		this.markingWidth = markingWidth;
	}

	public String getResultvalue() {
		return resultvalue;
	}

	public void setResultvalue(String resultvalue) {
		this.resultvalue = resultvalue;
	}

	public String getSizegroupvalue() {
		return sizegroupvalue;
	}

	public void setSizegroupvalue(String sizegroupvalue) {
		this.sizegroupvalue = sizegroupvalue;
	}

	public String getColorlistvalue() {
		return colorlistvalue;
	}

	public void setColorlistvalue(String colorlistvalue) {
		this.colorlistvalue = colorlistvalue;
	}

	public String getCuttinglistvalue() {
		return cuttinglistvalue;
	}

	public void setCuttinglistvalue(String cuttinglistvalue) {
		this.cuttinglistvalue = cuttinglistvalue;
	}


	public String getBuyerName() {
		return buyerName;
	}


	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}


	public String getCuttingEntryId() {
		return cuttingEntryId;
	}


	public void setCuttingEntryId(String cuttingEntryId) {
		this.cuttingEntryId = cuttingEntryId;
	}
	
	

}
