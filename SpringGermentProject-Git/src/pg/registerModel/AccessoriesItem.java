package pg.registerModel;

public class AccessoriesItem {
	String accessoriesItemId;
	String accessoriesItemName;
	String accessoriesItemCode;
	String userId;
	
	
	public AccessoriesItem() {};
	public AccessoriesItem(String accessoriesItemId, String accessoriesItemName, String accessoriesItemCode,
			String userId) {
		super();
		this.accessoriesItemId = accessoriesItemId;
		this.accessoriesItemName = accessoriesItemName;
		this.accessoriesItemCode = accessoriesItemCode;
		this.userId = userId;
	}
	public String getAccessoriesItemId() {
		return accessoriesItemId;
	}
	public void setAccessoriesItemId(String accessoriesItemId) {
		this.accessoriesItemId = accessoriesItemId;
	}
	public String getAccessoriesItemName() {
		return accessoriesItemName;
	}
	public void setAccessoriesItemName(String accessoriesItemName) {
		this.accessoriesItemName = accessoriesItemName;
	}
	public String getAccessoriesItemCode() {
		return accessoriesItemCode;
	}
	public void setAccessoriesItemCode(String accessoriesItemCode) {
		this.accessoriesItemCode = accessoriesItemCode;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	

}
