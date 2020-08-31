package pg.registerModel;

import java.util.List;

public class AccessoriesItem {
	String accessoriesItemId;
	String accessoriesItemName;
	String accessoriesItemCode;
	String unitId;
	String userId;
	List<Unit> unitList;
	
	public AccessoriesItem() {};
	public AccessoriesItem(String accessoriesItemId, String accessoriesItemName, String accessoriesItemCode,
			String unitId,String userId) {
		super();
		this.accessoriesItemId = accessoriesItemId;
		this.accessoriesItemName = accessoriesItemName;
		this.accessoriesItemCode = accessoriesItemCode;
		this.unitId = unitId;
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
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public List<Unit> getUnitList() {
		return unitList;
	}
	public void setUnitList(List<Unit> unitList) {
		this.unitList = unitList;
	}
	

}
