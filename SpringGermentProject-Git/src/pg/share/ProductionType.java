package pg.share;

public enum ProductionType {
	BUYER_PO(1),
	SAMPLE_REQUISITION(2),
	CUTTING(3),
	SEWING(4),
	PRODUCTION_FINISHING(5),
	
	LINE_INSPECTION_LAYOUT(1),
	LINE_INSPETION_PRODUCTION(2),
	LINE_INSPECTION_REJECT(3),
	
	FINISHING_LAYOUT(4),
	FINISHING_PRODUCTION(5),
	FINISHING_REJECT(6),
	
	IRON_LAYOUT(7),
	IRON_PRODUCTION(8),
	
	
	FINAL_QC_LAYOUT(9),
	FINAL_QC_PRODUCTION(10),
	FINAL_QC_REJECT(11);
	
	private int type;
	private ProductionType(int type) {
		this.type = type;
	}
	
	public int getType() {
		return type;
	}
}
