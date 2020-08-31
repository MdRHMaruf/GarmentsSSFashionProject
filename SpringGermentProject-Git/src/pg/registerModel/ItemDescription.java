package pg.registerModel;

public class ItemDescription {
	String itemId="";
	String itemName="";
	
	public ItemDescription() {
		
	}
	
	public ItemDescription(String ItemId,String ItemName) {
		this.itemId=ItemId;
		this.itemName=ItemName;
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
	
	
}
