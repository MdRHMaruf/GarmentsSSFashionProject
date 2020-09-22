package pg.storeModel;

public class StoreGeneralCategory {

	String categoryId="";
	String categoryName="";
	String itemId="";
	String itemName="";
	String unitId="";
	String buyPrice="";
	String openingStock="";
	String stockLimit="";
	String userId="";
	
	public StoreGeneralCategory() {
		
	}
	
	public StoreGeneralCategory(String CategoryId, String CategoryName) {
		this.categoryId=CategoryId;
		this.categoryName=CategoryName;
	}
	
	
	public StoreGeneralCategory(String ItemId, String ItemName, String Category,String CategoryId,String UnitId,String PcsBuyPrice,String OpeningStock,String StockLimit) {
		this.itemId=ItemId;
		this.itemName=ItemName;
		this.categoryName=Category;
		this.categoryId=CategoryId;
		this.unitId=UnitId;
		this.buyPrice=PcsBuyPrice;
		this.openingStock=OpeningStock;
		this.stockLimit=StockLimit;
	}

	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(String buyPrice) {
		this.buyPrice = buyPrice;
	}

	public String getOpeningStock() {
		return openingStock;
	}

	public void setOpeningStock(String openingStock) {
		this.openingStock = openingStock;
	}

	public String getStockLimit() {
		return stockLimit;
	}

	public void setStockLimit(String stockLimit) {
		this.stockLimit = stockLimit;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	

}	
