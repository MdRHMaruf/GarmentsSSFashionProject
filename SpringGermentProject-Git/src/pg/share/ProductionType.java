package pg.share;

public enum ProductionType {
	BUYER_PO(1),
	SAMPLE_REQUISITION(2),
	CUTTING(3),
	SEWING(4),
	PRODUCTION_FINISHING(5),
	
	LINE_INSPECTION_LAYOUT(1),
	LINE_PRODUCTION(2),
	LINE_PASS(3),
	LINE_REJECT(4),
	
	
	FINISHING_LAYOUT(5),
	FINISHING_PASS(6),
	FINISHING_REJECT(7),
	
	IRON_LAYOUT(8),
	IRON_PASS(9),
	IRON_REJECT(10),
	
	FINAL_QC_LAYOUT(11),
	FINAL_QC_PRODUCTION(12),
	FINAL_QC_REJECT(13),
	
	SAMPLE_PRODUCTION(14),
	SAMPLE_PASS(15),;
	
	private int type;
	private ProductionType(int type) {
		this.type = type;
	}
	
	public int getType() {
		return type;
	}
}
