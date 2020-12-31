package pg.share;

public enum StateStatus {
	NEW(1),
	INDENT(2),
	SAMPLE(3),
	CUTTING(4),
	PRODUCTION(5),
	FINISHING(6),
	EXPORTED(7),
	END(8);
	private int type;
	private StateStatus(int type) {
		this.type = type;
	}
	
	public int getType() {
		return type;
	}
}
