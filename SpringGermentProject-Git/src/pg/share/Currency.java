package pg.share;

public enum Currency {
	BDT(1),
	USD(2),
	CD(3),
	EURO(4),
	YEN(5),
	POUND(6),
	IND_RUPEE(7);
	private int type;
	private Currency(int type) {
		this.type = type;
	}
	
	public int getType() {
		return type;
	}
}
