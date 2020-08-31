package pg.orderModel;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import pg.registerModel.Size;



public class BuyerPoItem {
	
	String autoId;
	String buyerPOId;
	String buyerId;
	String styleId;
	String style;
	String itemId;
	String itemName;
	String factoryId;
	String colorId;
	String colorName;
	String customerOrder;
	String purchaseOrder;
	String shippingMark;
	String sizeReg;
	String sizeGroupId;
	String sizeListString;
	ArrayList<Size> sizeList;
	double totalUnit;
	double unitCmt;
	double totalPrice;
	double unitFob;
	double totalAmount;
	String userId;
	
	public BuyerPoItem() {};

	public BuyerPoItem(String autoId, String buyerPOId, String buyerId, String styleId, String style, String itemId,
			String itemName, String factoryId, String colorId, String colorName, String customerOrder,
			String purchaseOrder, String shippingMark, String sizeReg, String sizeGroupId,
			double totalUnit, double unitCmt, double totalPrice, double unitFob,
			double totalAmount, String userId) {
		super();
		this.autoId = autoId;
		this.buyerPOId = buyerPOId;
		this.buyerId = buyerId;
		this.styleId = styleId;
		this.style = style;
		this.itemId = itemId;
		this.itemName = itemName;
		this.factoryId = factoryId;
		this.colorId = colorId;
		this.colorName = colorName;
		this.customerOrder = customerOrder;
		this.purchaseOrder = purchaseOrder;
		this.shippingMark = shippingMark;
		this.sizeReg = sizeReg;
		this.sizeGroupId = sizeGroupId;
		this.totalUnit = totalUnit;
		this.unitCmt = unitCmt;
		this.totalPrice = totalPrice;
		this.unitFob = unitFob;
		this.totalAmount = totalAmount;
		this.userId = userId;
	}






	public String getStyle() {
		return style;
	}



	public void setStyle(String style) {
		this.style = style;
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



	public String getBuyerPOId() {
		return buyerPOId;
	}

	public void setBuyerPOId(String buyerPOId) {
		this.buyerPOId = buyerPOId;
	}

	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
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

	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}



	public String getFactoryId() {
		return factoryId;
	}
	public void setFactoryId(String factoryId) {
		this.factoryId = factoryId;
	}

	public String getColorId() {
		return colorId;
	}
	public void setColorId(String colorId) {
		this.colorId = colorId;
	}

	public String getCustomerOrder() {
		return customerOrder;
	}
	public void setCustomerOrder(String customerOrder) {
		this.customerOrder = customerOrder;
	}

	public String getPurchaseOrder() {
		return purchaseOrder;
	}
	public void setPurchaseOrder(String purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}

	public String getShippingMark() {
		return shippingMark;
	}
	public void setShippingMark(String shippingMark) {
		this.shippingMark = shippingMark;
	}

	public String getSizeGroupId() {
		return sizeGroupId;
	}
	public void setSizeGroupId(String sizeGroupId) {
		this.sizeGroupId = sizeGroupId;
	}

	public List<Size> getSizeList() {
		return sizeList;
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

	public String getSizeListString() {
		return sizeListString;
	}

	public String getSizeReg() {
		return sizeReg;
	}



	public void setSizeReg(String sizeReg) {
		this.sizeReg = sizeReg;
	}



	public double getTotalUnit() {
		return totalUnit;
	}



	public void setTotalUnit(double totalUnit) {
		this.totalUnit = totalUnit;
	}



	public double getUnitCmt() {
		return unitCmt;
	}



	public void setUnitCmt(double unitCmt) {
		this.unitCmt = unitCmt;
	}



	public double getTotalPrice() {
		return totalPrice;
	}



	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}



	public double getUnitFob() {
		return unitFob;
	}



	public void setUnitFob(double unitFob) {
		this.unitFob = unitFob;
	}



	public double getTotalAmount() {
		return totalAmount;
	}



	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}



	public void setSizeListString(String sizeListString) {
		this.sizeListString = sizeListString;
		String sizeList[] = sizeListString.split(" ");
		this.sizeList = new ArrayList<Size>();
		for (String size : sizeList) {
			this.sizeList.add(new Size(size.substring(size.indexOf('=')+1,size.indexOf(',')),size.substring(size.lastIndexOf('=')+1)));
		}
	}
	
	
	
}
