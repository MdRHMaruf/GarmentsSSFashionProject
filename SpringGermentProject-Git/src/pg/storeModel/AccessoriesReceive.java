package pg.storeModel;

import java.util.ArrayList;
import java.util.List;

import pg.orderModel.PurchaseOrderItem;

public class AccessoriesReceive {

	String autoId;
	String transactionId;
	String grnNo;
	String grnDate;
	String location;
	String rollList;
	String supplierId;
	String challanNo;
	String challanDate;
	String remarks;
	String departmentId;
	String preparedBy;
	String userId;
	List<AccessoriesSize> accessoriesSizeList;

	public AccessoriesReceive() {}

	public AccessoriesReceive(String autoId, String transectionId, String grnNo, String grnDate, String location,
			String rollList, String supplierId, String challanNo, String challanDate, String remarks, String preparedBy,
			String userId) {
		super();
		this.autoId = autoId;
		this.transactionId = transectionId;
		this.grnNo = grnNo;
		this.grnDate = grnDate;
		this.location = location;
		this.rollList = rollList;
		this.supplierId = supplierId;
		this.challanNo = challanNo;
		this.challanDate = challanDate;
		this.remarks = remarks;
		this.preparedBy = preparedBy;
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

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
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

	public String getPreparedBy() {
		return preparedBy;
	}

	public void setPreparedBy(String preparedBy) {
		this.preparedBy = preparedBy;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<AccessoriesSize> getAccessoriesSizeList() {
		return accessoriesSizeList;
	}

	public void setAccessoriesSizeList(List<AccessoriesSize> accessoriesSizeList) {
		this.accessoriesSizeList = accessoriesSizeList;
	}

	public String getRollList() {
		return rollList;
	}

	public void setRollList(String rollList) {
		try {
			System.out.println(rollList);
			if(rollList.trim().length()>0) {
				String[] rollLists = rollList.split("#");
				List<AccessoriesSize> list = new ArrayList<AccessoriesSize>();
				String autoId,transectionId,purchaseOrder,styleId,itemId,itemColorId,fabricsId,fabricsName,fabricsColorId,fabricsColorName,rollId,supplierRollId,unitId,unit,rackName,binName;
				double unitQty,qcPassedQty,balanceQty;
				int qcPassedType=0;
				for (String item : rollLists) {
					System.out.println(item);
					String[] itemProperty = item.split(",");
					autoId="";
					transectionId = "";
					purchaseOrder = itemProperty[0].substring(itemProperty[0].indexOf(":")+1).trim();
					styleId = itemProperty[1].substring(itemProperty[1].indexOf(":")+1).trim();
					itemId = itemProperty[2].substring(itemProperty[2].indexOf(":")+1).trim();
					itemColorId = itemProperty[3].substring(itemProperty[3].indexOf(":")+1).trim();
					fabricsId = itemProperty[4].substring(itemProperty[4].indexOf(":")+1).trim();
					fabricsName = itemProperty[5].substring(itemProperty[5].indexOf(":")+1).trim();;
					fabricsColorId = itemProperty[6].substring(itemProperty[6].indexOf(":")+1).trim();
					fabricsColorName = itemProperty[7].substring(itemProperty[7].indexOf(":")+1).trim();;
					rollId = itemProperty[8].substring(itemProperty[8].indexOf(":")+1).trim();
					supplierRollId = itemProperty[9].substring(itemProperty[9].indexOf(":")+1).trim();
					unitId = itemProperty[10].substring(itemProperty[10].indexOf(":")+2).trim();
					balanceQty =0;
					unitQty = Double.valueOf(itemProperty[11].substring(itemProperty[11].indexOf(":")+1).trim());
					qcPassedQty = Double.valueOf(itemProperty[12].substring(itemProperty[12].indexOf(":")+1).trim());
					rackName = itemProperty[13].substring(itemProperty[13].indexOf(":")+1).trim();
					binName = itemProperty[14].substring(itemProperty[14].indexOf(":")+1).trim();
					qcPassedType = Integer.valueOf(itemProperty[15].substring(itemProperty[15].indexOf(":")+1).trim());

					list.add(new AccessoriesSize(autoId, transectionId, purchaseOrder, styleId,"Style No", itemId,"item Name" ,itemColorId,"item color", fabricsId,fabricsName, fabricsColorId,fabricsColorName, rollId,supplierRollId, unitId,"unitName", balanceQty,unitQty, rackName, binName,qcPassedType));
				}

				this.accessoriesSizeList = list;
				
			}
			this.rollList = rollList;
		}catch(Exception e) {
			e.printStackTrace();
		}

	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}


}
