package pg.orderModel;

import org.springframework.web.multipart.MultipartFile;

public class Style {
	String styleId;
	String buyerId;
	String buyerName;
	String styleNo;
	String itemType;
	String size;
	
	String date;
	String itemName;
	String itemId;
	String styleItemAutoId;
	
	private MultipartFile  frontimage;
	private MultipartFile  backImage;
	
	public Style() {}
	
	public Style(String styleId, String buyerId, String buyerName, String styleNo, String itemType, String size) {
		super();
		this.styleId = styleId;
		this.buyerId = buyerId;
		this.buyerName = buyerName;
		this.styleNo = styleNo;
		this.itemType = itemType;
		this.size = size;
	}

	public Style(String StyleItemAutoId,String StyleNo,String ItemName,String ItemId) {
		this.styleItemAutoId=StyleItemAutoId;
		this.styleNo=StyleNo;
		this.itemName=ItemName;
		this.itemId=ItemId;
	}
	
	
	public Style(String StyleId,String StyleNo) {
		this.styleId=StyleId;
		this.styleNo=StyleNo;
	}
	
	
	public Style(String StyleId,String ItemId,String ItemName) {
		this.styleId=StyleId;
		this.itemId=ItemId;
		this.itemName=ItemName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getStyleItemAutoId() {
		return styleItemAutoId;
	}

	public void setStyleItemAutoId(String styleItemAutoId) {
		this.styleItemAutoId = styleItemAutoId;
	}

	public MultipartFile getFrontimage() {
		return frontimage;
	}

	public void setFrontimage(MultipartFile frontimage) {
		this.frontimage = frontimage;
	}

	public MultipartFile getBackImage() {
		return backImage;
	}

	public void setBackImage(MultipartFile backImage) {
		this.backImage = backImage;
	}

	public String getStyleId() {
		return styleId;
	}

	public void setStyleId(String styleId) {
		this.styleId = styleId;
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
	public String getStyleNo() {
		return styleNo;
	}
	public void setStyleNo(String styleNo) {
		this.styleNo = styleNo;
	}
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	
	
}
