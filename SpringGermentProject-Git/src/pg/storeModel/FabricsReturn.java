package pg.storeModel;

import java.util.ArrayList;
import java.util.List;

public class FabricsReturn {
	
	String autoId;
	String returnTransectionId;
	String returnDate;
	String grnNo;
	String receiveDate;
	String fabricsName;
	String supplierId;
	String remarks;
	String rollList;
	List<FabricsRoll> fabricsRollList;
	String userId;
	
	public FabricsReturn() {}
	public FabricsReturn(String autoId, String returnTransectionId, String returnDate, String grnNo, String grnDate,
			String fabricsName, String supplierId, String remarks, String userId) {
		super();
		this.autoId = autoId;
		this.returnTransectionId = returnTransectionId;
		this.returnDate = returnDate;
		this.grnNo = grnNo;
		this.receiveDate = grnDate;
		this.fabricsName = fabricsName;
		this.supplierId = supplierId;
		this.remarks = remarks;
		this.userId = userId;
	}
	public String getAutoId() {
		return autoId;
	}
	public void setAutoId(String autoId) {
		this.autoId = autoId;
	}
	public String getReturnTransectionId() {
		return returnTransectionId;
	}
	public void setReturnTransectionId(String returnTransectionId) {
		this.returnTransectionId = returnTransectionId;
	}
	public String getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}
	public String getGrnNo() {
		return grnNo;
	}
	public void setGrnNo(String grnNo) {
		this.grnNo = grnNo;
	}
	
	public String getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(String receiveDate) {
		this.receiveDate = receiveDate;
	}

	public String getFabricsName() {
		return fabricsName;
	}
	public void setFabricsName(String fabricsName) {
		this.fabricsName = fabricsName;
	}
	public String getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getRollList() {
		return rollList;
	}
	public void setRollList(String rollList) {
		try {
			System.out.println(rollList);
			String[] rollLists = rollList.split("#");
			List<FabricsRoll> list = new ArrayList<FabricsRoll>();
			String autoId,transectionId,userId;
			double qcPassedQty; int qcPassedType;
			boolean isReturn;
			for (String item : rollLists) {
				String[] itemProperty = item.split(",");

				autoId = itemProperty[0].substring(itemProperty[0].indexOf(":")+1).trim();
				transectionId = itemProperty[1].substring(itemProperty[1].indexOf(":")+1).trim();
				qcPassedQty = Double.valueOf(itemProperty[2].substring(itemProperty[2].indexOf(":")+1).trim());
				qcPassedType = Integer.valueOf(itemProperty[3].substring(itemProperty[3].indexOf(":")+1).trim());
				isReturn = Boolean.valueOf(itemProperty[4].substring(itemProperty[4].indexOf(":")+1).trim());
				userId = itemProperty[5].substring(itemProperty[5].indexOf(":")+1).trim();


				list.add(new FabricsRoll(autoId, transectionId, qcPassedQty, qcPassedType,isReturn, userId));
			}

			this.fabricsRollList = list;
			this.rollList = rollList;
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public List<FabricsRoll> getFabricsRollList() {
		return fabricsRollList;
	}
	public void setFabricsRollList(List<FabricsRoll> fabricsRollList) {
		this.fabricsRollList = fabricsRollList;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
	

}
