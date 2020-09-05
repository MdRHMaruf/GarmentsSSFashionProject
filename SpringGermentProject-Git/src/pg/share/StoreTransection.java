package pg.share;

public enum StoreTransection {
	FABRICS_RECEIVE(1),
	FABRICS_QC(2),
	FABRICS_RETURN(3),
	FABRICS_ISSUE(4);
	
	private int type;
	private StoreTransection(int type) {
		this.type = type;
	}
	
	public int getType() {
		return type;
	}
}
