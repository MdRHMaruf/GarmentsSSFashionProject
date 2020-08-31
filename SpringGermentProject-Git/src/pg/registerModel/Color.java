package pg.registerModel;

public class Color {
	String colorId;
	String colorName;
	String colorCode;
	String userId;
	
	public Color() {}
	
	public Color(String colorId, String colorName, String colorCode, String userId) {
		super();
		this.colorId = colorId;
		this.colorName = colorName;
		this.colorCode = colorCode;
		this.userId = userId;
	}
	public String getColorId() {
		return colorId;
	}
	public void setColorId(String colorId) {
		this.colorId = colorId;
	}
	public String getColorName() {
		return colorName;
	}
	public void setColorName(String colorName) {
		this.colorName = colorName;
	}
	public String getColorCode() {
		return colorCode;
	}
	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
