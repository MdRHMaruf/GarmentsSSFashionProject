package pg.share;

public enum ProductionType {
	BUYER_PO(1),
	SAMPLE_REQUISITION(2),
	CUTTING(3),
	SEWING(4),
	PRODUCTION_FINISHING(5),
	LINE_INSPECTION(1),
	FINISHING_LAYOUT(2),
	IRON_LAYOUT(3),
	FINAL_QC_LAYOUT(4),
	LINE_INSPETION_PRODUCTION(5),
	FINISHING_PRODUCTION(6),
	IRON_PRODUCTION(7),
	FINAL_QC_PRODUCTION(8);
	
	private int type;
	private ProductionType(int type) {
		this.type = type;
	}
	
	public int getType() {
		return type;
	}
}
