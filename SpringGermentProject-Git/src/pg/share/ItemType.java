package pg.share;

public enum ItemType {
	FABRICS(1),
	ACCESSORIES(2),
	GENERAL(3);
	private int type;
	private ItemType(int type) {
		this.type = type;
	}	
	public int getType() {
		return type;
	}
}
