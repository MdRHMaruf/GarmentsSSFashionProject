package pg.storeModel;

import java.util.ArrayList;
import java.util.List;

public class FabricsQualityControl {
	String autoId;
	String qcTransactionId;
	String qcDate;
	String grnNo;
	String receiveDate;
	String remarks;
	String fabricsName;
	String supplierId;
	String checkBy;
	String departmentId;
	String rollList;
	List<FabricsRoll> fabricsRollList;
	String userId;
	public FabricsQualityControl() {}
	public FabricsQualityControl(String autoId, String qcTransectionId, String qcDate, String grnNo, String remarks,String userId) {
		super();
		this.autoId = autoId;
		this.qcTransactionId = qcTransectionId;
		this.qcDate = qcDate;
		this.grnNo = grnNo;
		this.remarks = remarks;
		this.userId = userId;
	}
	
	public FabricsQualityControl(String autoId, String transectionId, String qcDate, String grnNo,String receiveDate, String remarks,
			 String supplierId,String checkBy, String userId) {
		super();
		this.autoId = autoId;
		this.qcTransactionId = transectionId;
		this.qcDate = qcDate;
		this.grnNo = grnNo;
		this.receiveDate = receiveDate;
		this.remarks = remarks;
		this.supplierId = supplierId;
		this.checkBy = checkBy;
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
	public String getQcTransactionId() {
		return qcTransactionId;
	}
	public void setQcTransactionId(String qcTransactionId) {
		this.qcTransactionId = qcTransactionId;
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
			if(rollList.trim().length()>0) {
				String[] rollLists = rollList.split("#");
				List<FabricsRoll> list = new ArrayList<FabricsRoll>();
				String autoId,transectionId,purchaseOrder,styleId,itemId,itemColorId,fabricsId,fabricsColorId,rollId,unitId,remarks,rackName,binName,userId;
				double unitQty; int qcPassedType;
				for (String item : rollLists) {
					String[] itemProperty = item.split(",");

					autoId = itemProperty[0].substring(itemProperty[0].indexOf(":")+1).trim();
					transectionId = itemProperty[1].substring(itemProperty[1].indexOf(":")+1).trim();
					purchaseOrder = itemProperty[2].substring(itemProperty[2].indexOf(":")+1).trim();
					styleId = itemProperty[3].substring(itemProperty[3].indexOf(":")+1).trim();
					itemId = itemProperty[4].substring(itemProperty[4].indexOf(":")+1).trim();
					itemColorId = itemProperty[5].substring(itemProperty[5].indexOf(":")+1).trim();
					fabricsId = itemProperty[6].substring(itemProperty[6].indexOf(":")+1).trim();
					fabricsColorId = itemProperty[7].substring(itemProperty[7].indexOf(":")+1).trim();
					rollId = itemProperty[8].substring(itemProperty[8].indexOf(":")+1).trim();
					unitId = itemProperty[9].substring(itemProperty[9].indexOf(":")+1).trim();
					unitQty = Double.valueOf(itemProperty[10].substring(itemProperty[10].indexOf(":")+1).trim());
					rackName = itemProperty[11].substring(itemProperty[11].indexOf(":")+1).trim();
					binName = itemProperty[12].substring(itemProperty[12].indexOf(":")+1).trim();
					remarks = "";
					qcPassedType = Integer.valueOf(itemProperty[13].substring(itemProperty[13].indexOf(":")+1).trim());
					userId = itemProperty[14].substring(itemProperty[14].indexOf(":")+1).trim();
					list.add(new FabricsRoll(autoId, transectionId,purchaseOrder,styleId,itemId,itemColorId,fabricsId,fabricsColorId,rollId,unitId ,unitQty,rackName,binName,remarks, qcPassedType, false,userId));
				}

				this.fabricsRollList = list;
			}
			
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
	public String getCheckBy() {
		return checkBy;
	}
	public void setCheckBy(String checkBy) {
		this.checkBy = checkBy;
	}
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}



}
