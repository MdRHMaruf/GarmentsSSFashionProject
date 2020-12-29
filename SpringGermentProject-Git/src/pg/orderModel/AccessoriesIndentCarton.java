package pg.orderModel;

public class AccessoriesIndentCarton {
	String user="";
	String poNo="";
	String style="";
	String item="";
	String itemColor="";
	String shippingMark="";
	String accessoriesItem="";
	String accessoriesSize="";
	String unit="";
	String orderqty="";
	String length1="";
	String width1="";
	String height1="";
	String add1="";
	String length2="";
	String width2="";
	String height2="";
	String add2="";
	String totalQty="";
	String devideBy="";
	String ply="";
	String autoid="";
	public AccessoriesIndentCarton() {
		
	}
	
	public AccessoriesIndentCarton(String AccIndentId,String PurchaseOrder,String StyleId,String ItemId,String ColorId,String ShippingMarks,String AccessoriesName,String cartonSize,String UnitName,String OrderQty,String Qty,String Length1,String Width1,String Height1,String Add1,String Length2,String Width2,String Height2,String Add2,String DivideBy,String Ply) {
		this.autoid=AccIndentId;
		this.poNo=PurchaseOrder;
		this.style=StyleId;
		this.item=ItemId;
		this.itemColor=ColorId;
		this.shippingMark=ShippingMarks;
		this.accessoriesItem=AccessoriesName;
		this.accessoriesSize=cartonSize;
		this.unit=UnitName;
		this.orderqty=OrderQty;
		this.totalQty=Qty;
		this.length1=Length1;
		this.width1=Width1;
		this.height1=Height1;
		this.add1=Add1;
		
		this.length2=Length2;
		this.width2=Width2;
		this.height2=Height2;
		this.add2=Add2;
		
		this.devideBy=DivideBy;
		this.ply=Ply;

		
	}
	
	public AccessoriesIndentCarton(String autoId,String po, String style, String itemname, String itemcolor,String shippingmark,String accessoriesName,String sizeName,String totalqty) {
		this.autoid=autoId;
		this.poNo=po;
		this.style=style;
		this.item=itemname;
		this.itemColor=itemcolor;
		this.shippingMark=shippingmark;
		this.accessoriesItem=accessoriesName;
		this.accessoriesSize=sizeName;
		this.totalQty=totalqty;
	}
	
	
	
	public String getAutoid() {
		return autoid;
	}

	public void setAutoid(String autoid) {
		this.autoid = autoid;
	}

	public String getUser() {
		return user;
	}
	public String getPly() {
		return ply;
	}

	public void setPly(String ply) {
		this.ply = ply;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPoNo() {
		return poNo;
	}

	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}

	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getItemColor() {
		return itemColor;
	}
	public void setItemColor(String itemColor) {
		this.itemColor = itemColor;
	}
	public String getShippingMark() {
		return shippingMark;
	}
	public void setShippingMark(String shippingMark) {
		this.shippingMark = shippingMark;
	}
	public String getAccessoriesItem() {
		return accessoriesItem;
	}
	public void setAccessoriesItem(String accessoriesItem) {
		this.accessoriesItem = accessoriesItem;
	}
	public String getAccessoriesSize() {
		return accessoriesSize;
	}
	public void setAccessoriesSize(String accessoriesSize) {
		this.accessoriesSize = accessoriesSize;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getOrderqty() {
		return orderqty;
	}
	public void setOrderqty(String orderqty) {
		this.orderqty = orderqty;
	}
	public String getLength1() {
		return length1;
	}
	public void setLength1(String length1) {
		this.length1 = length1;
	}
	public String getWidth1() {
		return width1;
	}
	public void setWidth1(String width1) {
		this.width1 = width1;
	}
	public String getHeight1() {
		return height1;
	}
	public void setHeight1(String height1) {
		this.height1 = height1;
	}
	public String getAdd1() {
		return add1;
	}
	public void setAdd1(String add1) {
		this.add1 = add1;
	}
	public String getLength2() {
		return length2;
	}
	public void setLength2(String length2) {
		this.length2 = length2;
	}
	public String getWidth2() {
		return width2;
	}
	public void setWidth2(String width2) {
		this.width2 = width2;
	}
	public String getHeight2() {
		return height2;
	}
	public void setHeight2(String height2) {
		this.height2 = height2;
	}
	public String getAdd2() {
		return add2;
	}
	public void setAdd2(String add2) {
		this.add2 = add2;
	}
	public String getTotalQty() {
		return totalQty;
	}
	public void setTotalQty(String totalQty) {
		this.totalQty = totalQty;
	}

	public String getDevideBy() {
		return devideBy;
	}

	public void setDevideBy(String devideBy) {
		this.devideBy = devideBy;
	}

	
	
}
