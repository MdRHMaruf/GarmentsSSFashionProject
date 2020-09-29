package pg.orderModel;

public class SampleCadAndProduction {
	String user;
	String autoId;
	String sampleCommentId;
	String styleId;
	String styleNo;
	String purchaseOrder;
	String itemId;
	String itemName;
	String colorId;
	String colorName;
	String sizeId;
	String size;
	String sizeCol;
	String sampleTypeId;
	String sampleTypeName;
	String requisitionQty;
	String patternMakingDate;
	String patternMakingDespatch;
	String patternMadingReceived;
	String patternCorrectionDate;
	String patternCorrectionDespatch;
	String PatternCorrectionReceived;
	String patternGradingDate;
	String patternGradingDespatch;
	String patternGradingReceived;
	String patternMarkingDate;
	String patternMarkingDespatch;
	String patternMarkingReceived;
	String sampleCommentFlag;
	String sampleCommentUserId;
	String sampleCommentUserIp;
	String sampleCommentPcName;
	String sampleCommentDate;
	String sampleCommentEntryTime;
	String cuttingQty;
	String cuttingDate;
	String printSendDate;
	String printReceivedDate;
	String printReceivedQty;
	String embroiderySendDate;
	String embroideryReceivedDate;
	String embroideryReceivedQty;
	String sewingSendDate;
	String sewingReceivedDate;
	String sewingFinishDate;
	String sampleProductionUserId;
	String sampleProductionUserIp;
	String sampleProductionPcName;
	String sampleProductionDate;
	String sampleProductionEntryTime;
	String operatorName;
	String feedbackComments;
	String quality;
	String userId;
	String sizeid;
	String sampleComment;
	String POStatus;
	String buyername;
	
	
	
	public SampleCadAndProduction(String sampleCommentId, String po,
			String style, String item, String color, String size, 
			String samapletype, String makingdate, String makingdipatch, 
			String makingreceive, String correctiondate, String correctiondispatch, 
			String correctionreceivedby, String gradingdate, String gradingdispatch, String gradingreceivedby,
			String markingdare, String markingdispatch, String markingrecievedby,
			String feedback, String POStatsus) {	
		
		super();
		
		this.sampleCommentId=sampleCommentId;
		this.purchaseOrder=po;
		this.styleId=style;
		this.itemId=item;
		this.colorId=color;
		this.sizeid=size;
		this.sampleTypeId=samapletype;
		
		
		this.patternMakingDate=makingdate;
		this.patternMakingDespatch=makingdipatch;
		this.patternMadingReceived=makingreceive;
		
		this.patternCorrectionDate=correctiondate;
		this.patternCorrectionDespatch=correctiondispatch;
		this.PatternCorrectionReceived=correctionreceivedby;
		
		
		this.patternGradingDate=gradingdate;
		this.patternGradingDespatch=gradingdispatch;
		this.patternGradingReceived=gradingreceivedby;
		
		this.patternMarkingDate=markingdare;
		this.patternMarkingDespatch=markingdispatch;
		this.patternMarkingReceived=markingrecievedby;
		
		this.feedbackComments=feedback;
		this.POStatus=POStatsus;
		
	}
	
	
	public SampleCadAndProduction(String sampleCommentId,String buyername,String po,String style,String itemname,String sampletype) {
		super();
		this.sampleCommentId=sampleCommentId;
		this.buyername=buyername;
		this.purchaseOrder=po;
		this.styleNo=style;
		this.itemName=itemname;
		this.sampleTypeId=sampletype;
		
		
	}
	
	
	public String getUserId() {
		return userId;
	}



	public void setUserId(String userId) {
		this.userId = userId;
	}



	public String getQuality() {
		return quality;
	}



	public void setQuality(String quality) {
		this.quality = quality;
	}



	public SampleCadAndProduction() {
		super();
	}
	
	
	
	public SampleCadAndProduction(String sampleCommentId, String purchaseOrder, String styleId, String styleNo,
			String itemId, String itemName, String colorId, String colorName, String sizeId, String size,
			String sampleTypeId, String sampleTypeName) {
		super();
		this.sampleCommentId = sampleCommentId;
		this.purchaseOrder = purchaseOrder;
		this.styleId = styleId;
		this.styleNo = styleNo;
		this.itemId = itemId;
		this.itemName = itemName;
		this.colorId = colorId;
		this.colorName = colorName;
		this.sizeId = sizeId;
		this.size = size;
		this.sampleTypeId = sampleTypeId;
		this.sampleTypeName = sampleTypeName;
	}



	public SampleCadAndProduction(String sampleCommentId, String purchaseOrder, String styleId, String styleNo,
			String itemId, String itemName, String colorId, String colorName, String sizeId, String size,
			String sampleTypeId, String sampleTypeName, String cuttingQty, String cuttingDate, String requisitionQty,
			String printSendDate, String printReceivedDate, String printReceivedQty, String embroiderySendDate,
			String embroideryReceivedDate, String embroideryReceivedQty, String sewingSendDate,
			String sewingFinishDate, String operatorName, String quality) {
		super();
		this.sampleCommentId = sampleCommentId;
		this.purchaseOrder = purchaseOrder;
		this.styleId = styleId;
		this.styleNo = styleNo;
		this.itemId = itemId;
		this.itemName = itemName;
		this.colorId = colorId;
		this.colorName = colorName;
		this.sizeId = sizeId;
		this.size = size;
		this.sampleTypeId = sampleTypeId;
		this.sampleTypeName = sampleTypeName;
		this.cuttingQty = cuttingQty;
		this.cuttingDate = cuttingDate;
		this.requisitionQty = requisitionQty;
		this.printSendDate = printSendDate;
		this.printReceivedDate = printReceivedDate;
		this.printReceivedQty = printReceivedQty;
		this.embroiderySendDate = embroiderySendDate;
		this.embroideryReceivedDate = embroideryReceivedDate;
		this.embroideryReceivedQty = embroideryReceivedQty;
		this.sewingSendDate = sewingSendDate;
		this.sewingFinishDate = sewingFinishDate;
		this.operatorName = operatorName;
		this.quality = quality;
	}



	public String getAutoId() {
		return autoId;
	}
	public void setAutoId(String autoId) {
		this.autoId = autoId;
	}
	public String getSampleCommentId() {
		return sampleCommentId;
	}
	public void setSampleCommentId(String sampleCommentId) {
		this.sampleCommentId = sampleCommentId;
	}
	public String getStyleId() {
		return styleId;
	}
	public void setStyleId(String styleId) {
		this.styleId = styleId;
	}
	public String getPurchaseOrder() {
		return purchaseOrder;
	}
	public void setPurchaseOrder(String purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getColorId() {
		return colorId;
	}
	public void setColorId(String colorId) {
		this.colorId = colorId;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getSizeCol() {
		return sizeCol;
	}
	public void setSizeCol(String sizeCol) {
		this.sizeCol = sizeCol;
	}
	public String getSampleTypeId() {
		return sampleTypeId;
	}
	public void setSampleTypeId(String sampleTypeId) {
		this.sampleTypeId = sampleTypeId;
	}
	public String getPatternMakingDate() {
		return patternMakingDate;
	}
	public void setPatternMakingDate(String patternMakingDate) {
		this.patternMakingDate = patternMakingDate;
	}
	public String getPatternMakingDespatch() {
		return patternMakingDespatch;
	}
	public void setPatternMakingDespatch(String patternMakingDespatch) {
		this.patternMakingDespatch = patternMakingDespatch;
	}
	public String getPatternMadingReceived() {
		return patternMadingReceived;
	}
	public void setPatternMadingReceived(String patternMadingReceived) {
		this.patternMadingReceived = patternMadingReceived;
	}
	public String getPatternCorrectionDate() {
		return patternCorrectionDate;
	}
	public void setPatternCorrectionDate(String patternCorrectionDate) {
		this.patternCorrectionDate = patternCorrectionDate;
	}
	public String getPatternCorrectionDespatch() {
		return patternCorrectionDespatch;
	}
	public void setPatternCorrectionDespatch(String patternCorrectionDespatch) {
		this.patternCorrectionDespatch = patternCorrectionDespatch;
	}
	public String getPatternCorrectionReceived() {
		return PatternCorrectionReceived;
	}
	public void setPatternCorrectionReceived(String patternCorrectionReceived) {
		PatternCorrectionReceived = patternCorrectionReceived;
	}
	public String getPatternGradingDate() {
		return patternGradingDate;
	}
	public void setPatternGradingDate(String patternGradingDate) {
		this.patternGradingDate = patternGradingDate;
	}
	public String getPatternGradingDespatch() {
		return patternGradingDespatch;
	}
	public void setPatternGradingDespatch(String patternGradingDespatch) {
		this.patternGradingDespatch = patternGradingDespatch;
	}
	public String getPatternGradingReceived() {
		return patternGradingReceived;
	}
	public void setPatternGradingReceived(String patternGradingReceived) {
		this.patternGradingReceived = patternGradingReceived;
	}
	public String getPatternMarkingDate() {
		return patternMarkingDate;
	}
	public void setPatternMarkingDate(String patternMarkingDate) {
		this.patternMarkingDate = patternMarkingDate;
	}
	public String getPatternMarkingDespatch() {
		return patternMarkingDespatch;
	}
	public void setPatternMarkingDespatch(String patternMarkingDespatch) {
		this.patternMarkingDespatch = patternMarkingDespatch;
	}
	public String getPatternMarkingReceived() {
		return patternMarkingReceived;
	}
	public void setPatternMarkingReceived(String patternMarkingReceived) {
		this.patternMarkingReceived = patternMarkingReceived;
	}
	public String getSampleCommentFlag() {
		return sampleCommentFlag;
	}
	public void setSampleCommentFlag(String sampleCommentFlag) {
		this.sampleCommentFlag = sampleCommentFlag;
	}
	public String getSampleCommentUserId() {
		return sampleCommentUserId;
	}
	public void setSampleCommentUserId(String sampleCommentUserId) {
		this.sampleCommentUserId = sampleCommentUserId;
	}
	public String getSampleCommentUserIp() {
		return sampleCommentUserIp;
	}
	public void setSampleCommentUserIp(String sampleCommentUserIp) {
		this.sampleCommentUserIp = sampleCommentUserIp;
	}
	public String getSampleCommentPcName() {
		return sampleCommentPcName;
	}
	public void setSampleCommentPcName(String sampleCommentPcName) {
		this.sampleCommentPcName = sampleCommentPcName;
	}
	public String getSampleCommentDate() {
		return sampleCommentDate;
	}
	public void setSampleCommentDate(String sampleCommentDate) {
		this.sampleCommentDate = sampleCommentDate;
	}
	public String getSampleCommentEntryTime() {
		return sampleCommentEntryTime;
	}
	public void setSampleCommentEntryTime(String sampleCommentEntryTime) {
		this.sampleCommentEntryTime = sampleCommentEntryTime;
	}
	public String getCuttingQty() {
		return cuttingQty;
	}
	public void setCuttingQty(String cuttingQty) {
		this.cuttingQty = cuttingQty;
	}
	public String getCuttingDate() {
		return cuttingDate;
	}
	public void setCuttingDate(String cuttingDate) {
		this.cuttingDate = cuttingDate;
	}
	public String getPrintSendDate() {
		return printSendDate;
	}
	public void setPrintSendDate(String printSendDate) {
		this.printSendDate = printSendDate;
	}
	public String getPrintReceivedDate() {
		return printReceivedDate;
	}
	public void setPrintReceivedDate(String printReceivedDate) {
		this.printReceivedDate = printReceivedDate;
	}
	public String getPrintReceivedQty() {
		return printReceivedQty;
	}
	public void setPrintReceivedQty(String printReceivedQty) {
		this.printReceivedQty = printReceivedQty;
	}
	public String getEmbroiderySendDate() {
		return embroiderySendDate;
	}
	public void setEmbroiderySendDate(String embroiderySendDate) {
		this.embroiderySendDate = embroiderySendDate;
	}
	public String getEmbroideryReceivedDate() {
		return embroideryReceivedDate;
	}
	public void setEmbroideryReceivedDate(String embroideryReceivedDate) {
		this.embroideryReceivedDate = embroideryReceivedDate;
	}
	public String getEmbroideryReceivedQty() {
		return embroideryReceivedQty;
	}
	public void setEmbroideryReceivedQty(String embroideryReceivedQty) {
		this.embroideryReceivedQty = embroideryReceivedQty;
	}
	public String getSewingSendDate() {
		return sewingSendDate;
	}
	public void setSewingSendDate(String sewingSendDate) {
		this.sewingSendDate = sewingSendDate;
	}
	public String getSewingReceivedDate() {
		return sewingReceivedDate;
	}
	public void setSewingReceivedDate(String sewingReceivedDate) {
		this.sewingReceivedDate = sewingReceivedDate;
	}
	public String getSewingFinishDate() {
		return sewingFinishDate;
	}
	public void setSewingFinishDate(String sewingFinishDate) {
		this.sewingFinishDate = sewingFinishDate;
	}
	public String getSampleProductionUserId() {
		return sampleProductionUserId;
	}
	public void setSampleProductionUserId(String sampleProductionUserId) {
		this.sampleProductionUserId = sampleProductionUserId;
	}
	public String getSampleProductionUserIp() {
		return sampleProductionUserIp;
	}
	public void setSampleProductionUserIp(String sampleProductionUserIp) {
		this.sampleProductionUserIp = sampleProductionUserIp;
	}
	public String getSampleProductionPcName() {
		return sampleProductionPcName;
	}
	public void setSampleProductionPcName(String sampleProductionPcName) {
		this.sampleProductionPcName = sampleProductionPcName;
	}
	public String getSampleProductionDate() {
		return sampleProductionDate;
	}
	public void setSampleProductionDate(String sampleProductionDate) {
		this.sampleProductionDate = sampleProductionDate;
	}
	public String getSampleProductionEntryTime() {
		return sampleProductionEntryTime;
	}
	public void setSampleProductionEntryTime(String sampleProductionEntryTime) {
		this.sampleProductionEntryTime = sampleProductionEntryTime;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public String getFeedbackComments() {
		return feedbackComments;
	}
	public void setFeedbackComments(String feedbackComments) {
		this.feedbackComments = feedbackComments;
	}

	public String getStyleNo() {
		return styleNo;
	}

	public void setStyleNo(String styleNo) {
		this.styleNo = styleNo;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getColorName() {
		return colorName;
	}

	public void setColorName(String colorName) {
		this.colorName = colorName;
	}

	public String getSampleTypeName() {
		return sampleTypeName;
	}

	public void setSampleTypeName(String sampleTyleName) {
		this.sampleTypeName = sampleTyleName;
	}

	public String getRequisitionQty() {
		return requisitionQty;
	}

	public void setRequisitionQty(String requisitionQty) {
		this.requisitionQty = requisitionQty;
	}

	public String getSizeId() {
		return sizeId;
	}

	public void setSizeId(String sizeId) {
		this.sizeId = sizeId;
	}


	public String getUser() {
		return user;
	}


	public void setUser(String user) {
		this.user = user;
	}


	public String getSizeid() {
		return sizeid;
	}


	public void setSizeid(String sizeid) {
		this.sizeid = sizeid;
	}


	public String getSampleComment() {
		return sampleComment;
	}


	public void setSampleComment(String sampleComment) {
		this.sampleComment = sampleComment;
	}


	public String getPOStatus() {
		return POStatus;
	}


	public void setPOStatus(String pOStatus) {
		POStatus = pOStatus;
	}


	public String getBuyername() {
		return buyername;
	}


	public void setBuyername(String buyername) {
		this.buyername = buyername;
	}
	
	
	
}
