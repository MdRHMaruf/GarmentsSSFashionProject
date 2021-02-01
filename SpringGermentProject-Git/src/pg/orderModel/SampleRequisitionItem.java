package pg.orderModel;

import java.util.ArrayList;

import pg.registerModel.Size;

public class SampleRequisitionItem {
	
	String autoId="";
	String buyerId="";
	String styleId="";
	String styleNo="";
	String itemId="";
	String itemName="";
	String colorId="";
	String colorName="";
	String buyerOrderId="";
	String purchaseOrder="";
	String sizeGroupId="";
	String sizeListString="";
	String inchargeId="";
	String marchendizerId="";
	String userId="";
	String instruction="";
	String sampleDeadline="";
	String sampleId="";
	String sampleReqId="";
	
	ArrayList<Size> sizeList;
	
	public SampleRequisitionItem(){
		
	}
	
	public SampleRequisitionItem(String sampleReqId,String BuyerOrderId,String PurchaseOrder,String StyleNo,String StyleId,String SampleDeadline) {
		this.autoId=sampleReqId;
		this.buyerOrderId=BuyerOrderId;
		this.purchaseOrder=PurchaseOrder;
		this.styleNo=StyleNo;
		this.styleId=StyleId;
		this.sampleDeadline=SampleDeadline;
	}
	
	
	public SampleRequisitionItem(String SampleReqId,String SampleId,String BuyerId,String sampleAutoId,String StyleId,String StyleNo,String ItemId,String ItemName,String ColorId,String ColorName,String PurchaseOrder,String SizeGroupId,String UserId){
		this.sampleReqId=SampleReqId;
		this.sampleId=SampleId;
		this.buyerId=BuyerId;
		this.autoId=sampleAutoId;
		this.styleId=StyleId;
		this.styleNo=StyleNo;
		this.itemId=ItemId;
		this.itemName=ItemName;
		this.colorId=ColorId;
		this.colorName=ColorName;
		this.purchaseOrder=PurchaseOrder;
		this.sizeGroupId=SizeGroupId;
		this.userId=UserId;
		
	}
	
	public SampleRequisitionItem(String sampleReqId,String InchargeId,String MerchendizerId,String Instruction,String SampleId,String BuyerId,String sampleAutoId,String StyleId,String StyleNo,String ItemId,String ItemName,String ColorId,String ColorName,String BuyerOrderId,String PurchaseOrder,String SizeGroupId,String UserId){
		this.sampleReqId=sampleReqId;
		this.inchargeId=InchargeId;
		this.marchendizerId=MerchendizerId;
		this.instruction=Instruction;
		this.sampleId=SampleId;
		this.buyerId=BuyerId;
		this.autoId=sampleAutoId;
		this.styleId=StyleId;
		this.styleNo=StyleNo;
		this.itemId=ItemId;
		this.itemName=ItemName;
		this.colorId=ColorId;
		this.colorName=ColorName;
		this.buyerOrderId=BuyerOrderId;
		this.purchaseOrder=PurchaseOrder;
		this.sizeGroupId=SizeGroupId;
		this.userId=UserId;
		
	}
	
	public String getSampleDeadline() {
		return sampleDeadline;
	}

	public void setSampleDeadline(String sampleDeadline) {
		this.sampleDeadline = sampleDeadline;
	}

	public String getInstruction() {
		return instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

	public String getInchargeId() {
		return inchargeId;
	}

	public void setInchargeId(String inchargeId) {
		this.inchargeId = inchargeId;
	}

	public String getMarchendizerId() {
		return marchendizerId;
	}

	public void setMarchendizerId(String marchendizerId) {
		this.marchendizerId = marchendizerId;
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
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getColorId() {
		return colorId;
	}
	public void setColorId(String colorId) {
		this.colorId = colorId;
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

	public void setSizeList(ArrayList<Size> sizeList) {
		this.sizeList = sizeList;
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

	public void setSizeListString(String sizeListString) {
		this.sizeListString = sizeListString;
		String sizeList[] = sizeListString.split(" ");
		this.sizeList = new ArrayList<Size>();
		for (String size : sizeList) {
			this.sizeList.add(new Size(size.substring(size.indexOf('=')+1,size.indexOf(',')),size.substring(size.lastIndexOf('=')+1)));
		}
	}

	public String getSampleId() {
		return sampleId;
	}

	public void setSampleId(String sampleId) {
		this.sampleId = sampleId;
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

	public String getColorName() {
		return colorName;
	}

	public void setColorName(String colorName) {
		this.colorName = colorName;
	}

	public String getSampleReqId() {
		return sampleReqId;
	}

	public void setSampleReqId(String sampleReqId) {
		this.sampleReqId = sampleReqId;
	}

	public String getBuyerOrderId() {
		return buyerOrderId;
	}

	public void setBuyerOrderId(String buyerOrderId) {
		this.buyerOrderId = buyerOrderId;
	}
	
	
	
}
