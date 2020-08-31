package pg.registerModel;

public class StyleItem {
	
	String styleItemId;
	String styleItemName;
	String styleItemCode;
	String userId;
	
	public StyleItem() {}
	public StyleItem(String styleItemId, String styleItemName, String styleItemCode, String userId) {
		super();
		this.styleItemId = styleItemId;
		this.styleItemName = styleItemName;
		this.styleItemCode = styleItemCode;
		this.userId = userId;
	}
	public String getStyleItemId() {
		return styleItemId;
	}
	public void setStyleItemId(String styleItemId) {
		this.styleItemId = styleItemId;
	}
	public String getStyleItemName() {
		return styleItemName;
	}
	public void setStyleItemName(String styleItemName) {
		this.styleItemName = styleItemName;
	}
	public String getStyleItemCode() {
		return styleItemCode;
	}
	public void setStyleItemCode(String styleItemCode) {
		this.styleItemCode = styleItemCode;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	

}
