package pg.share;

public enum PaymentType {
	TT(1),
	LC(2),
	Cash(3),
	Card(4);
	
	private int type;
	private PaymentType(int type) {
		this.type = type;
	}
	
	public int getType() {
		return type;
	}
}
