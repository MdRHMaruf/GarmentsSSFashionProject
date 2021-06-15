package pg.share;

public enum CommercialType {
	MasterLC(1),
	MasterUD(2);
	private int type;
	private CommercialType(int type) {
		this.type = type;
	}
	
	public int getType() {
		return type;
	}
}
