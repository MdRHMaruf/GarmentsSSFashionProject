package pg.registerModel;

import java.util.List;

public class FabricsItem {
	
	String fabricsItemId;
	
	String fabricsItemName;
	String reference;
	String unitId;
	String userId;
	List<Unit> unitList;
	
	public FabricsItem() {}
	
	
	public FabricsItem(String fabricsItemId, String fabricsItemName, String reference,String unitId, String userId) {
		super();
		this.fabricsItemId = fabricsItemId;
		this.fabricsItemName = fabricsItemName;
		this.reference = reference;
		this.unitId = unitId;
		this.userId = userId;
	}
	public String getFabricsItemId() {
		return fabricsItemId;
	}
	public void setFabricsItemId(String fabricsItemId) {
		this.fabricsItemId = fabricsItemId;
	}

	public String getFabricsItemName() {
		return fabricsItemName;
	}
	public void setFabricsItemName(String fabricsItemName) {
		this.fabricsItemName = fabricsItemName;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
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
