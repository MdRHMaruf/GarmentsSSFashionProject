package pg.storeModel;

public class FabricsRoll {
	String autoId;
	String transectionId;
	String rollId;
	String supplierRollId;
	String unitId;
	String unit;
	double rollQty;
	double qcPassedQty;
	double issueQty;
	double balanceQty;
	double rate;
	double totalAmount;
	String remarks;
	String rackName;
	String binName;
	String userId;
	
	String qcTransectionId;
	int qcPassedType;
	
	public FabricsRoll() {}
	
	public FabricsRoll(String autoId, String transectionId, String rollId, String supplierRollId, String unitId,
			String unit, double rollQty, double qcPassedQty, double issueQty, double balanceQty, double rate,
			double totalAmount, String remarks, String rackName, String binName, String userId) {
		super();
		this.autoId = autoId;
		this.transectionId = transectionId;
		this.rollId = rollId;
		this.supplierRollId = supplierRollId;
		this.unitId = unitId;
		this.unit = unit;
		this.rollQty = rollQty;
		this.qcPassedQty = qcPassedQty;
		this.issueQty = issueQty;
		this.balanceQty = balanceQty;
		this.rate = rate;
		this.totalAmount = totalAmount;
		this.remarks = remarks;
		this.rackName = rackName;
		this.binName = binName;
		this.qcPassedType = 1;
		this.userId = userId;
	}
	public FabricsRoll(String autoId, String transectionId, String rollId, String supplierRollId, String unitId,
			String unit, double rollQty, double qcPassedQty, double issueQty, double balanceQty, double rate,
			double totalAmount, String remarks, String rackName, String binName,String qcTransectionId,int qcPassedType,String userId) {
		super();
		this.autoId = autoId;
		this.transectionId = transectionId;
		this.rollId = rollId;
		this.supplierRollId = supplierRollId;
		this.unitId = unitId;
		this.unit = unit;
		this.rollQty = rollQty;
		this.qcPassedQty = qcPassedQty;
		this.issueQty = issueQty;
		this.balanceQty = balanceQty;
		this.rate = rate;
		this.totalAmount = totalAmount;
		this.remarks = remarks;
		this.rackName = rackName;
		this.binName = binName;
		this.qcTransectionId = qcTransectionId;
		this.qcPassedType = qcPassedType;
		this.userId = userId;
	}
	
	public FabricsRoll(String autoId,String qcTransectionId,double qcPassedQty,int qcPassedType,String userId) {
		this.autoId = autoId;
		this.qcTransectionId = qcTransectionId;
		this.qcPassedQty = qcPassedQty;
		this.qcPassedType = qcPassedType;
		this.userId = userId;
	}
	
	public String getQcTransectionId() {
		return qcTransectionId;
	}

	public void setQcTransectionId(String qcTransectionId) {
		this.qcTransectionId = qcTransectionId;
	}

	public int getQcPassedType() {
		return qcPassedType;
	}

	public void setQcPassedType(int qcPassedType) {
		this.qcPassedType = qcPassedType;
	}

	public String getTransectionId() {
		return transectionId;
	}

	public void setTransectionId(String transectionId) {
		this.transectionId = transectionId;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getAutoId() {
		return autoId;
	}
	public void setAutoId(String autoId) {
		this.autoId = autoId;
	}
	public String getRollId() {
		return rollId;
	}
	public void setRollId(String rollId) {
		this.rollId = rollId;
	}
	public String getSupplierRollId() {
		return supplierRollId;
	}
	public void setSupplierRollId(String supplierRollId) {
		this.supplierRollId = supplierRollId;
	}
	
	public double getRollQty() {
		return rollQty;
	}
	public void setRollQty(double rollQty) {
		this.rollQty = rollQty;
	}
	public double getQcPassedQty() {
		return qcPassedQty;
	}
	public void setQcPassedQty(double qcPassedQty) {
		this.qcPassedQty = qcPassedQty;
	}
	public double getIssueQty() {
		return issueQty;
	}
	public void setIssueQty(double issueQty) {
		this.issueQty = issueQty;
	}
	public double getBalanceQty() {
		return balanceQty;
	}
	public void setBalanceQty(double balanceQty) {
		this.balanceQty = balanceQty;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getRackName() {
		return rackName;
	}
	public void setRackName(String rackName) {
		this.rackName = rackName;
	}
	public String getBinName() {
		return binName;
	}
	public void setBinName(String binName) {
		this.binName = binName;
	}
	
	
	

}
