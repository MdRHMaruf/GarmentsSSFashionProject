package pg.orderModel;

public class Costing {
	String autoId;
	String costingNo;
	String styleId;
	String styleName;
	String itemId;
	String itemName;
	String particularType;
	String particularId;
	String particularName;
	String size;
	String unitId;
	double width;
	double yard;
	double gsm;
	double consumption;
	double unitPrice;
	double amount;
	double commission;
	String date;
	String userId;
	String styleNo;
	String resultList;
	String submissionDate;
	
	public Costing() {
		super();
	}
	
	public Costing(String autoId, String styleId, String itemId, String particularType, String particularId,
			String unitId, double width, double yard, double gsm, double consumption, double unitPrice, double amount,
			double commission, String date, String userId) {
		super();
		this.autoId = autoId;
		this.styleId = styleId;
		this.itemId = itemId;
		this.particularType = particularType;
		this.particularId = particularId;
		this.unitId = unitId;
		this.width = width;
		this.yard = yard;
		this.gsm = gsm;
		this.consumption = consumption;
		this.unitPrice = unitPrice;
		this.amount = amount;
		this.commission = commission;
		this.date = date;
		this.userId = userId;
	}

	
	public Costing(String autoId, String styleId, String styleName, String itemId, String itemName,
			String particularType, String particularId, String particularName, String size, String unitId, double width,
			double yard, double gsm, double consumption, double unitPrice, double amount, double commission, String date,
			String userId) {
		super();
		this.autoId = autoId;
		this.styleId = styleId;
		this.styleName = styleName;
		this.itemId = itemId;
		this.itemName = itemName;
		this.particularType = particularType;
		this.particularId = particularId;
		this.particularName = particularName;
		this.size = size;
		this.unitId = unitId;
		this.width = width;
		this.yard = yard;
		this.gsm = gsm;
		this.consumption = consumption;
		this.unitPrice = unitPrice;
		this.amount = amount;
		this.commission = commission;
		this.date = date;
		this.userId = userId;
	}
	
	public Costing(String autoId, String styleName,String itemName,double amount,String date) {
		super();
		this.autoId = autoId;
		this.styleNo = styleName;
		this.itemName = itemName;
		this.amount = amount;
		this.date = date;
	}
	
	public Costing(String ItemId,String ItemName) {
		this.itemId=ItemId;
		this.itemName=ItemName;
	}

	public Costing(String costingNo, String AutoId, String StyleNo, String itemName, String fabricsItem,
			String groupType, String size, String unitId, double width, double yard, double gsm,
			double commission, double consumtion, double unitPrice,double Amount) {
		this.costingNo=costingNo;
		this.autoId=AutoId;
		this.styleNo=StyleNo;
		this.itemName=itemName;
		this.particularName=fabricsItem;
		this.particularType=groupType;
		this.size=size;
		this.unitId=unitId;
		this.width=width;
		this.yard=yard;
		this.gsm=gsm;
		this.commission=commission;
		this.consumption=consumtion;
		this.unitPrice=unitPrice;
		this.amount=Amount;
		this.size=size;
	}

	public String getAutoId() {
		return autoId;
	}
	public void setAutoId(String autoId) {
		this.autoId = autoId;
	}
	public String getStyleId() {
		return styleId;
	}
	public void setStyleId(String styleId) {
		this.styleId = styleId;
	}
	public String getStyleName() {
		return styleName;
	}
	public void setStyleName(String styleName) {
		this.styleName = styleName;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getParticularType() {
		return particularType;
	}
	public void setParticularType(String particularType) {
		this.particularType = particularType;
	}
	public String getParticularId() {
		return particularId;
	}
	public void setParticularId(String particularId) {
		this.particularId = particularId;
	}
	public String getParticularName() {
		return particularName;
	}
	public void setParticularName(String particularName) {
		this.particularName = particularName;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public double getWidth() {
		return width;
	}
	public void setWidth(double width) {
		this.width = width;
	}
	public double getYard() {
		return yard;
	}
	public void setYard(double yard) {
		this.yard = yard;
	}
	public double getGsm() {
		return gsm;
	}
	public void setGsm(double gsm) {
		this.gsm = gsm;
	}
	public double getConsumption() {
		return consumption;
	}
	public void setConsumption(double consumption) {
		this.consumption = consumption;
	}
	public double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getCommission() {
		return commission;
	}
	public void setCommission(double comission) {
		this.commission = comission;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCostingNo() {
		return costingNo;
	}

	public void setCostingNo(String costingNo) {
		this.costingNo = costingNo;
	}

	public String getStyleNo() {
		return styleNo;
	}

	public void setStyleNo(String styleNo) {
		this.styleNo = styleNo;
	}

	public String getResultList() {
		return resultList;
	}

	public void setResultList(String resultList) {
		this.resultList = resultList;
	}

	public String getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(String submissionDate) {
		this.submissionDate = submissionDate;
	}
	
	
	
	
}
