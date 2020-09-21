package pg.storeModel;

import java.util.ArrayList;
import java.util.List;

public class FabricsTransferOut {
	String autoId;
	String transactionId;
	String transferDate;
	String transferTo;
	String transferDepartmentName;
	String remarks;
	String receiveBy;
	String departmentId;
	String rollList;
	List<FabricsRoll> fabricsRollList;
	String userId;
	
	public FabricsTransferOut() {}
	public FabricsTransferOut(String autoId, String transectionId, String transferDate, String transferTo, String receiveBy, String remarks, String userId) {
		super();
		this.autoId = autoId;
		this.transactionId = transectionId;
		this.transferDate = transferDate;
		this.transferTo = transferTo;
		this.receiveBy = receiveBy;
		this.remarks = remarks;
		this.userId = userId;
	}
	public String getAutoId() {
		return autoId;
	}
	public void setAutoId(String autoId) {
		this.autoId = autoId;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	
	public String getTransferDate() {
		return transferDate;
	}
	public void setTransferDate(String transferDate) {
		this.transferDate = transferDate;
	}
	public String getTransferTo() {
		return transferTo;
	}
	public void setTransferTo(String transferTo) {
		this.transferTo = transferTo;
	}
	public String getTransferDepartmentName() {
		return transferDepartmentName;
	}
	public void setTransferDepartmentName(String transferDepartmentName) {
		this.transferDepartmentName = transferDepartmentName;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getReceiveBy() {
		return receiveBy;
	}
	public void setReceiveBy(String receiveBy) {
		this.receiveBy = receiveBy;
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
				String autoId,transectionId,purchaseOrder,styleId,itemId,itemColorId,fabricsId,fabricsName,fabricsColorId,fabricsColorName,rollId,supplierRollId,unitId,unit,userId,rackName,binName;
				double qcReturnQty,unitQty,balanceQty; int qcPassedType;
				boolean isReturn;
				for (String item : rollLists) {
					String[] itemProperty = item.split(",");
					autoId = itemProperty[0].substring(itemProperty[0].indexOf(":")+1).trim();
					transectionId = itemProperty[1].substring(itemProperty[1].indexOf(":")+1).trim();
					purchaseOrder = itemProperty[2].substring(itemProperty[2].indexOf(":")+1).trim();
					styleId = itemProperty[3].substring(itemProperty[3].indexOf(":")+1).trim();
					itemId = itemProperty[4].substring(itemProperty[4].indexOf(":")+1).trim();
					itemColorId = itemProperty[5].substring(itemProperty[5].indexOf(":")+1).trim();
					fabricsId = itemProperty[6].substring(itemProperty[6].indexOf(":")+1).trim();
					fabricsName = itemProperty[7].substring(itemProperty[7].indexOf(":")+1).trim();
					fabricsColorId = itemProperty[8].substring(itemProperty[8].indexOf(":")+1).trim();
					fabricsColorName = itemProperty[9].substring(itemProperty[9].indexOf(":")+1).trim();;
					balanceQty = 0;
					rollId = itemProperty[10].substring(itemProperty[10].indexOf(":")+1).trim();
					supplierRollId = itemProperty[11].substring(itemProperty[11].indexOf(":")+1).trim();
					unitId = itemProperty[12].substring(itemProperty[12].indexOf(":")+1).trim();
					unit = itemProperty[13].substring(itemProperty[13].indexOf(":")+1).trim();
					unitQty = Double.valueOf(itemProperty[14].substring(itemProperty[14].indexOf(":")+1).trim());
					rackName = itemProperty[15].substring(itemProperty[15].indexOf(":")+1).trim();
					binName = itemProperty[16].substring(itemProperty[16].indexOf(":")+1).trim();
					qcPassedType = 1;
					list.add(new FabricsRoll(autoId, transectionId, purchaseOrder, styleId,"Style No", itemId,"item Name" ,itemColorId,"item color", fabricsId,fabricsName, fabricsColorId,fabricsColorName, rollId ,supplierRollId, unitId,"unitName", balanceQty,unitQty, rackName, binName,qcPassedType));
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
