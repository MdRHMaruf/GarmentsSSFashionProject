package pg.storeModel;

import java.util.ArrayList;
import java.util.List;

import pg.orderModel.PurchaseOrderItem;

public class FabricsReceive {
	
	String autoId;
	String transectionId;
	String grnNo;
	String grnDate;
	String location;
	String purcharOrder;
	String styleId;
	String styleNo;
	String itemId;
	String itemName;
	String colorId;
	String colorName;
	String fabricsId;
	String fabricsName;
	String indentId;
	String rollList;
	String unitId;
	double grnQty;
	double noOfRoll;
	String supplierId;
	String buyer;
	String challanNo;
	String challanDate;
	String remarks;
	String preparedBy;
	String userId;
	List<FabricsRoll> fabricsRollList;
	
	public FabricsReceive() {}
	
	
	
	public FabricsReceive(String autoId, String transectionId, String grnNo, String grnDate, String location,
			String purcharOrder, String styleId, String styleNo, String itemId, String itemName,String colorId,String colorName,
			String fabricsId, String fabricsName, String indentId, String unitId, double grnQty, double noOfRoll,
			String supplierId, String buyer, String challanNo, String challanDate, String remarks, String preparedBy,
			String userId) {
		super();
		this.autoId = autoId;
		this.transectionId = transectionId;
		this.grnNo = grnNo;
		this.grnDate = grnDate;
		this.location = location;
		this.purcharOrder = purcharOrder;
		this.styleId = styleId;
		this.styleNo = styleNo;
		this.itemId = itemId;
		this.itemName = itemName;
		this.colorId = colorId;
		this.colorName = colorName;
		this.fabricsId = fabricsId;
		this.fabricsName = fabricsName;
		this.indentId = indentId;
		this.unitId = unitId;
		this.grnQty = grnQty;
		this.noOfRoll = noOfRoll;
		this.supplierId = supplierId;
		this.buyer = buyer;
		this.challanNo = challanNo;
		this.challanDate = challanDate;
		this.remarks = remarks;
		this.preparedBy = preparedBy;
		this.userId = userId;	
	}



	/*public FabricsReceive(String autoId, String transectionId, String grnNo, String grnDate, String location,
			String indentId,String fabricsId,String fabricsName, String rollList, String grnQty, String noOfRoll, String supplierId, String buyer,
			String challanNo, String challanDate, String remarks,String preparedBy, String userId) {
		super();
		this.autoId = autoId;
		this.transectionId = transectionId;
		this.grnNo = grnNo;
		this.grnDate = grnDate;
		this.location = location;
		this.indentId = indentId;
		this.fabricsId = fabricsId;
		this.fabricsName =fabricsName;
		this.rollList = rollList;
		this.grnQty = grnQty;
		this.noOfRoll = noOfRoll;
		this.supplierId = supplierId;
		this.buyer = buyer;
		this.challanNo = challanNo;
		this.challanDate = challanDate;
		this.remarks = remarks;
		this.preparedBy = preparedBy;
		this.userId = userId;
	}*/
	
	
	public String getColorId() {
		return colorId;
	}



	public void setColorId(String colorId) {
		this.colorId = colorId;
	}



	public String getColorName() {
		return colorName;
	}



	public void setColorName(String colorName) {
		this.colorName = colorName;
	}



	public String getPurcharOrder() {
		return purcharOrder;
	}

	public void setPurcharOrder(String purcharOrder) {
		this.purcharOrder = purcharOrder;
	}

	public String getStyleId() {
		return styleId;
	}

	public void setStyleId(String styleId) {
		this.styleId = styleId;
	}

	public String getStyleNo() {
		return styleNo;
	}

	public void setStyleNo(String styleNo) {
		this.styleNo = styleNo;
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

	public String getFabricsName() {
		return fabricsName;
	}

	public void setFabricsName(String fabricsName) {
		this.fabricsName = fabricsName;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getAutoId() {
		return autoId;
	}
	public void setAutoId(String autoId) {
		this.autoId = autoId;
	}
	public String getTransectionId() {
		return transectionId;
	}
	public void setTransectionId(String transectionId) {
		this.transectionId = transectionId;
	}
	public String getGrnNo() {
		return grnNo;
	}
	public void setGrnNo(String grnNo) {
		this.grnNo = grnNo;
	}
	public String getGrnDate() {
		return grnDate;
	}
	public void setGrnDate(String grnDate) {
		this.grnDate = grnDate;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	public String getIndentId() {
		return indentId;
	}

	public void setIndentId(String indentId) {
		this.indentId = indentId;
	}

	public String getFabricsId() {
		return fabricsId;
	}
	public void setFabricsId(String fabricsId) {
		this.fabricsId = fabricsId;
	}
	public String getRollList() {
		return rollList;
	}
	public void setRollList(String rollList) {
		try {
			
			String[] rollLists = rollList.split("#");
			List<FabricsRoll> list = new ArrayList<FabricsRoll>();
			String autoId,transectionId,rollId,supplierRollId,unitId,unit,remarks,rackName,binName;
			double rollQty,qcPassedQty,issueQty,balanceQty,rate,totalAmount;
			
			for (String item : rollLists) {
				String[] itemProperty = item.split(",");
				autoId="";
				transectionId = "";
				rollId = itemProperty[0].substring(itemProperty[0].indexOf(":")+1).trim();
				supplierRollId = itemProperty[1].substring(itemProperty[1].indexOf(":")+1).trim();
				unitId = itemProperty[2].substring(itemProperty[2].indexOf(":")+2).trim();
				unit = itemProperty[3].substring(itemProperty[3].indexOf(":")+3).trim();
				rollQty = Double.valueOf(itemProperty[4].substring(itemProperty[4].indexOf(":")+1).trim());
				qcPassedQty = Double.valueOf(itemProperty[5].substring(itemProperty[5].indexOf(":")+1).trim());
				issueQty = Double.valueOf(itemProperty[6].substring(itemProperty[6].indexOf(":")+1).trim());
				balanceQty = Double.valueOf(itemProperty[7].substring(itemProperty[7].indexOf(":")+1).trim());
				rate = Double.valueOf(itemProperty[8].substring(itemProperty[8].indexOf(":")+1).trim());
				totalAmount = Double.valueOf(itemProperty[9].substring(itemProperty[9].indexOf(":")+1).trim());
				remarks = itemProperty[10].substring(itemProperty[10].indexOf(":")+1).trim();
				rackName = itemProperty[11].substring(itemProperty[11].indexOf(":")+1).trim();
				binName = itemProperty[12].substring(itemProperty[12].indexOf(":")+1).trim();
				
				list.add(new FabricsRoll(autoId, transectionId,rollId, supplierRollId,unitId,unit, rollQty, qcPassedQty, issueQty, balanceQty, rate, totalAmount, remarks, rackName, binName, item));
			}
			
			this.fabricsRollList = list;
			this.rollList = rollList;
			}catch(Exception e) {
				e.printStackTrace();
			}
		
	}
	public double getGrnQty() {
		return grnQty;
	}
	public void setGrnQty(double grnQty) {
		this.grnQty = grnQty;
	}
	public double getNoOfRoll() {
		return noOfRoll;
	}
	public void setNoOfRoll(double noOfRoll) {
		this.noOfRoll = noOfRoll;
	}
	public String getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	public String getBuyer() {
		return buyer;
	}
	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}
	public String getChallanNo() {
		return challanNo;
	}
	public void setChallanNo(String challanNo) {
		this.challanNo = challanNo;
	}
	public String getChallanDate() {
		return challanDate;
	}
	public void setChallanDate(String challanDate) {
		this.challanDate = challanDate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getPreparedBy() {
		return preparedBy;
	}
	public void setPreparedBy(String preparedBy) {
		this.preparedBy = preparedBy;
	}
	public List<FabricsRoll> getFabricsRollList() {
		return fabricsRollList;
	}
	public void setFabricsRollList(List<FabricsRoll> fabricsRollList) {
		this.fabricsRollList = fabricsRollList;
	}
	
	

}
