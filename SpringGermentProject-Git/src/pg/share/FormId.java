package pg.share;

public enum FormId {
	BUYER_CREATE(1),
	STYLE_CREATE(23),
	COSTING_CREATE(24),
	BUYER_PO(25),
	ACCESSORIES_INDENT(32),
	ZIPPER_INDENT(4101),
	FABRICS_INDENT(33),
	CARTON_INDENT(56),
	PURCHASE_ORDER(35),
	SAMPLE_REQUISITION(27);
	
	private int id;
	private FormId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
}
