package pg.registerModel;

public class LocalItem {
	
	String localItemId;
	String localItemName;
	String localItemCode;
	String userId;
	
	public LocalItem() {}
	
	public LocalItem(String localItemId, String localItemName, String localItemCode, String userId) {
		super();
		this.localItemId = localItemId;
		this.localItemName = localItemName;
		this.localItemCode = localItemCode;
		this.userId = userId;
	}
	public String getLocalItemId() {
		return localItemId;
	}
	public void setLocalItemId(String localItemId) {
		this.localItemId = localItemId;
	}
	public String getLocalItemName() {
		return localItemName;
	}
	public void setLocalItemName(String localItemName) {
		this.localItemName = localItemName;
	}
	public String getLocalItemCode() {
		return localItemCode;
	}
	public void setLocalItemCode(String localItemCode) {
		this.localItemCode = localItemCode;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	

}
