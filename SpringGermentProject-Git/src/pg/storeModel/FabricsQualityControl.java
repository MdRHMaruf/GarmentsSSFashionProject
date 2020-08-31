package pg.storeModel;

import java.util.ArrayList;
import java.util.List;

public class FabricsQualityControl {
	String autoId;
	String qcTransectionId;
	String qcDate;
	String grnNo;
	String receiveDate;
	String remarks;
	String fabricsName;
	String supplierId;
	String rollList;
	List<FabricsRoll> fabricsRollList;
	String userId;
	public FabricsQualityControl() {}
	public FabricsQualityControl(String autoId, String qcTransectionId, String qcDate, String grnNo, String remarks,String userId) {
		super();
		this.autoId = autoId;
		this.qcTransectionId = qcTransectionId;
		this.qcDate = qcDate;
		this.grnNo = grnNo;
		this.remarks = remarks;
		this.userId = userId;
	}
	
	public FabricsQualityControl(String autoId, String qcTransectionId, String qcDate, String grnNo,String receiveDate, String remarks,
			String fabricsName, String supplierId, String userId) {
		super();
		this.autoId = autoId;
		this.qcTransectionId = qcTransectionId;
		this.qcDate = qcDate;
		this.grnNo = grnNo;
		this.receiveDate = receiveDate;
		this.remarks = remarks;
		this.fabricsName = fabricsName;
		this.supplierId = supplierId;
		this.userId = userId;
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
	public String getQcTransectionId() {
		return qcTransectionId;
	}
	public void setQcTransectionId(String qcTransectionId) {
		this.qcTransectionId = qcTransectionId;
	}
	public String getQcDate() {
		return qcDate;
	}
	public void setQcDate(String qcDate) {
		this.qcDate = qcDate;
	}
	
	public String getReceiveDate() {
		return receiveDate;
	}
	public void setReceiveDate(String receiveDate) {
		this.receiveDate = receiveDate;
	}
	public String getGrnNo() {
		return grnNo;
	}
	public void setGrnNo(String grnNo) {
		this.grnNo = grnNo;
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
			
			for (String item : rollLists) {
				String[] itemProperty = item.split(",");

				autoId = itemProperty[0].substring(itemProperty[0].indexOf(":")+1).trim();
				transectionId = itemProperty[1].substring(itemProperty[1].indexOf(":")+1).trim();
				qcPassedQty = Double.valueOf(itemProperty[2].substring(itemProperty[2].indexOf(":")+1).trim());
				qcPassedType = Integer.valueOf(itemProperty[3].substring(itemProperty[3].indexOf(":")+1).trim());
				userId = itemProperty[4].substring(itemProperty[4].indexOf(":")+1).trim();


				list.add(new FabricsRoll(autoId, transectionId, qcPassedQty, qcPassedType, userId));
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



}
