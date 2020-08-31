package pg.share;

public enum SizeValuesType {
	BUYER_PO(1),
	SAMPLE(2),
	CUTTING(3);
	private int type;
	private SizeValuesType(int type) {
		this.type = type;
	}	
	public int getType() {
		return type;
	}
}
