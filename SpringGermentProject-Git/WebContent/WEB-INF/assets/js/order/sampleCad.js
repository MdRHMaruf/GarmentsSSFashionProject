

var po;
var style;
var item;
var color;
var size;
var sample;
var sampleCommentId;

var sampleRequistionQty=0;

var sizeValueListForSet = [];
var sizesListByGroup = JSON;


window.onload = () => {
	document.title = "Sample Cad";

	$.ajax({
		type: 'GET',
		dataType: 'json',
		url: './sizesLoadByGroup',
		data: {},
		success: function (obj) {
			sizesListByGroup = [];
			sizesListByGroup = obj.sizeList;
		}
	});
}

function refreshAction() {
	location.reload();
}

function searchSampleCad(sampleCommentId,sampleReqId){

	
	$.ajax({
		type: 'GET',
		dataType: 'json',
		data:{
			sampleCommentId:sampleCommentId,
			sampleReqId:sampleReqId
		},
		url: './searchSampleCadDetails',
		success: function (data) {
			if (data.result == "Something Wrong") {
				dangerAlert("Something went wrong");
			} else if (data.result == "duplicate") {
				dangerAlert("Duplicate Item Name..This Item Name Already Exist")
			} else {
				drawItemTable(data.result_sample_requisition);
				setCadData(data.result_sample_cad);
			}
		}
	});
}

function setCadData(data){
	$('#sampleCadModal').modal('hide');
	
	var actualPatternmakingdate = new Date(data[0].patternMakingDate); 
	var patternmakingdate=actualPatternmakingdate.getFullYear() + "-" +('0' + (actualPatternmakingdate.getMonth() + 1)).slice(-2) + "-" + ('0' + actualPatternmakingdate.getDate()).slice(-2) + "T" + ('0' + actualPatternmakingdate.getHours()).slice(-2) + ":" + ('0' + actualPatternmakingdate.getMinutes()).slice(-2);
	
	if(data[0].patternMakingDate!=' :00'){
		$('#patternmakingdate').val(patternmakingdate);
	} 
	

	$('#makeingDespatch').val(data[0].patternMakingDespatch);
	$('#makeingDespatch').selectpicker('refresh');
	$('#patternmakingreceivedby').val(data[0].patternMakingReceived);
	
	
	var actualPatternCorrectionDate = new Date(data[0].patternCorrectionDate); 
	var patterncorrectiondate=actualPatternCorrectionDate.getFullYear() + "-" +('0' + (actualPatternCorrectionDate.getMonth() + 1)).slice(-2) + "-" + ('0' + actualPatternCorrectionDate.getDate()).slice(-2) + "T" + ('0' + actualPatternCorrectionDate.getHours()).slice(-2) + ":" + ('0' + actualPatternCorrectionDate.getMinutes()).slice(-2);
	
	if(data[0].patternCorrectionDate!=' :00'){
		$('#patterncorrectiondate').val(patterncorrectiondate);
	} 
	

	$('#patterncorrectiondispatch').val(data[0].patternCorrectionDespatch);
	$('#patterncorrectiondispatch').selectpicker('refresh');
	$('#correctionReceviedBy').val(data[0].patternCorrectionReceived);
	
	
	var actualPatternGradingDate = new Date(data[0].patternGradingDate); 
	var gradingDate=actualPatternGradingDate.getFullYear() + "-" +('0' + (actualPatternGradingDate.getMonth() + 1)).slice(-2) + "-" + ('0' + actualPatternGradingDate.getDate()).slice(-2) + "T" + ('0' + actualPatternGradingDate.getHours()).slice(-2) + ":" + ('0' + actualPatternGradingDate.getMinutes()).slice(-2);
	
	if(data[0].patternGradingDate!=' :00'){
		$('#gradingDate').val(gradingDate);
	}
	  
	
	$('#gradingDespatch').val(data[0].patternGradingDespatch);
	$('#gradingDespatch').selectpicker('refresh');
	$('#gradingdispatchreceivedby').val(data[0].patternGradingReceived);
	
	var actualPatternMarkingDate = new Date(data[0].patternMarkingDate); 
	var markingDate=actualPatternMarkingDate.getFullYear() + "-" +('0' + (actualPatternMarkingDate.getMonth() + 1)).slice(-2) + "-" + ('0' + actualPatternMarkingDate.getDate()).slice(-2) + "T" + ('0' + actualPatternMarkingDate.getHours()).slice(-2) + ":" + ('0' + actualPatternMarkingDate.getMinutes()).slice(-2);
	
	if(data[0].patternMarkingDate!=' :00'){
		$('#markingDate').val(markingDate);
	}
	  
	
	$('#markingDespatch').val(data[0].patternMarkingDespatch);
	$('#markingDespatch').selectpicker('refresh');
	$('#markingReceviedBy').val(data[0].patternMarkingReceived);
	$('#sampleReqId').val(data[0].sampleReqId);
	$('#sampleCommentId').val(data[0].sampleCadId);
	$('#sampleCommentsNo').val(data[0].sampleCadId);
	
	$('#save').prop('disabled', true);

}

function insertSample(){

	var user=$('#userId').val();


	var buyerOrderId=$('#buyerOrderId').val()==''?"0":$('#buyerOrderId').val();
	var purchaseOrder=$('#purchaseOrder').val()==''?"0":$('#purchaseOrder').val();
	
	var sampleId=$('#sampleId').val();
	
	var styleId=$('#styleId').val();
	var itemId=$('#itemId').val();
	var colorId=$('#colorId').val();
	var POStatus=$('#POStatus').val();
	
	var sampleReqId=$('#sampleReqId').val()==''?"0":$('#sampleReqId').val();
	


	let patternMakingDate = $("#patternmakingdate").val();
	patternMakingDate = patternMakingDate.slice(0, patternMakingDate.indexOf('T')) + " " + patternMakingDate.slice(patternMakingDate.indexOf('T') + 1) + ":00";


	var makingDespatch=$('#makeingDespatch').val();
	var patternMakingReceivedBy=$('#patternmakingreceivedby').val();
	console.log(" pattern making recevied by "+patternMakingReceivedBy)

	var feedback=$('#feedback').val();

	let patterncorrectiondate = $("#patterncorrectiondate").val();
	patterncorrectiondate = patterncorrectiondate.slice(0, patterncorrectiondate.indexOf('T')) + " " + patterncorrectiondate.slice(patterncorrectiondate.indexOf('T') + 1) + ":00";


	var patterncorrectiondispatch=$('#patterncorrectiondispatch').val();
	var correctionReceviedBy=$('#correctionReceviedBy').val();

	let gradingDate = $("#gradingDate").val();
	gradingDate = gradingDate.slice(0, gradingDate.indexOf('T')) + " " + gradingDate.slice(gradingDate.indexOf('T') + 1) + ":00";



	var gradingDespatch=$('#gradingDespatch').val();
	var gradingdispatchreceivedby=$('#gradingdispatchreceivedby').val();

	let markingDate = $("#markingDate").val();
	markingDate = markingDate.slice(0, markingDate.indexOf('T')) + " " + markingDate.slice(markingDate.indexOf('T') + 1) + ":00";


	var markingdispatch=$('#markingDespatch').val();
	var markingReceviedBy=$('#markingReceviedBy').val();

	if(sampleReqId!='0'){
		if(styleId!='0'){
			if(itemId!='0'){
				$.ajax({
					type: 'GET',
					dataType: 'json',
					url: './insertSampleCad',
					data: {
						user : user,
						buyerOrderId : buyerOrderId,
						purchaseOrder:purchaseOrder,
						styleId : styleId,
						itemId : itemId,	
						colorId : colorId,	
						sampleTypeId:sampleId,
						patternMakingDate : patternMakingDate,
						patternMakingDespatch : makingDespatch,
						patternMadingReceived : patternMakingReceivedBy,

						patternCorrectionDate : patterncorrectiondate,
						patternCorrectionDespatch : patterncorrectiondispatch,
						PatternCorrectionReceived : correctionReceviedBy,

						patternGradingDate : gradingDate,
						patternGradingDespatch : gradingDespatch,
						patternGradingReceived : gradingdispatchreceivedby,


						patternMarkingDate : markingDate,
						patternMarkingDespatch : markingdispatch,
						patternMarkingReceived : markingReceviedBy,

						feedbackComments:feedback,
						POStatus:POStatus,
						sampleRequistionQty:sampleRequistionQty,
						sampleReqId:sampleReqId

					},
					success: function (data) {

						if(data=='success'){
								alert("Successfully Inserted");
								refreshAction();
						}else{
								alert(" Insert Failed")
						}
					}
				});
			}
			else{
				alert("Provide Item Name");
			}
		}
		else{
			alert("Provide Style No");
		}
	}
	else{
		alert("Provide Sample Requistion No");
	}

}


function editSmapleCad() {


	var user=$('#userId').val();


	var buyerOrderId=$('#buyerOrderId').val()==''?"0":$('#buyerOrderId').val();
	var purchaseOrder=$('#purchaseOrder').val()==''?"0":$('#purchaseOrder').val();
	
	var sampleId=$('#sampleId').val();
	
	var styleId=$('#styleId').val();
	var itemId=$('#itemId').val();
	var colorId=$('#colorId').val();
	var POStatus=$('#POStatus').val();
	
	var sampleReqId=$('#sampleReqId').val()==''?"0":$('#sampleReqId').val();
	var sampleCommentId=$('#sampleCommentId').val()==''?"0":$('#sampleCommentId').val();


	let patternMakingDate = $("#patternmakingdate").val();
	patternMakingDate = patternMakingDate.slice(0, patternMakingDate.indexOf('T')) + " " + patternMakingDate.slice(patternMakingDate.indexOf('T') + 1) + ":00";


	var makingDespatch=$('#makeingDespatch').val();
	var patternMakingReceivedBy=$('#patternmakingreceivedby').val();
	console.log(" pattern making recevied by "+patternMakingReceivedBy)

	var feedback=$('#feedback').val();

	let patterncorrectiondate = $("#patterncorrectiondate").val();
	patterncorrectiondate = patterncorrectiondate.slice(0, patterncorrectiondate.indexOf('T')) + " " + patterncorrectiondate.slice(patterncorrectiondate.indexOf('T') + 1) + ":00";


	var patterncorrectiondispatch=$('#patterncorrectiondispatch').val();
	var correctionReceviedBy=$('#correctionReceviedBy').val();

	let gradingDate = $("#gradingDate").val();
	gradingDate = gradingDate.slice(0, gradingDate.indexOf('T')) + " " + gradingDate.slice(gradingDate.indexOf('T') + 1) + ":00";



	var gradingDespatch=$('#gradingDespatch').val();
	var gradingdispatchreceivedby=$('#gradingdispatchreceivedby').val();

	let markingDate = $("#markingDate").val();
	markingDate = markingDate.slice(0, markingDate.indexOf('T')) + " " + markingDate.slice(markingDate.indexOf('T') + 1) + ":00";


	var markingdispatch=$('#markingDespatch').val();
	var markingReceviedBy=$('#markingReceviedBy').val();
	
		$.ajax({
			type: 'GET',
			dataType: 'json',
			url: './editSampleCad',
			data: {						
				user : user,
				buyerOrderId : buyerOrderId,
				purchaseOrder:purchaseOrder,
				styleId : styleId,
				itemId : itemId,	
				colorId : colorId,	
				sampleTypeId:sampleId,
				patternMakingDate : patternMakingDate,
				patternMakingDespatch : makingDespatch,
				patternMadingReceived : patternMakingReceivedBy,

				patternCorrectionDate : patterncorrectiondate,
				patternCorrectionDespatch : patterncorrectiondispatch,
				PatternCorrectionReceived : correctionReceviedBy,

				patternGradingDate : gradingDate,
				patternGradingDespatch : gradingDespatch,
				patternGradingReceived : gradingdispatchreceivedby,


				patternMarkingDate : markingDate,
				patternMarkingDespatch : markingdispatch,
				patternMarkingReceived : markingReceviedBy,

				feedbackComments:feedback,
				POStatus:POStatus,
				sampleRequistionQty:sampleRequistionQty,
				sampleReqId:sampleReqId,
				sampleCommentId:sampleCommentId


			},
			success: function (data) {

				if(data=='success'){
					alert("Successfully Update")
					
				}else{
					alert(" Insert Failed")
				}
			}
		});

}



function getSampleDetails(id) {

	sampleCommentId=id;

	$.ajax({
		type: 'GET',
		dataType: 'json',
		url: './getSampleDetails/'+id,
		data: {

		},
		success: function (data) {
			setData(data)

		}
	});

}



function setData(data){

	if (data[0].POStatus=='1') {
		$('#withPO')[0].checked;
	}else{
		$('#withOutPO')[0].checked;
	}

	console.log(data[0].purchaseOrder)
	$('#purchaseOrder').val(data[0].purchaseOrder);
	$('#purchaseOrder').val(data[0].purchaseOrder).change();

	style=data[0].styleId;
	item=data[0].itemId;
	color=data[0].colorId;
	size=data[0].sizeid;
	sample=data[0].sampleTypeId;

	$('#sampletype').val(data[0].sampleTypeId).change();
	/*	$('#styleNo').val(data[0]);
	$('#itemName').val(data[0]);
	$('#sampleCommentsNo').val(data[0]);
	$('#itemColor').val(data[0]);
	$('#size').val(data[0]();
	$('#sampletype').val(data[0]);*/

	$('#patternmakingdate').val(data[0].patternMakingDate);
	$('#makeingDespatch').val(data[0].patternMakingDespatch);
	console.log(" dispatch "+data[0].patternMakingDespatch)
	//$('#makeingDespatch').val(data[0].patternMakingDespatch).change();;
	$('#patternmakingreceivedby').val(data[0].patternMadingReceived);


	$('#feedback').val(data[0].feedbackComments);


	console.log(" pattern correction "+data[0].PatternCorrectionReceived)
	$('#patterncorrectiondate').val(data[0].patternCorrectionDate);
	$('#patterncorrectiondispatch').val(data[0].patternCorrectionDespatch).change();;
	$('#correctionReceviedBy').val(data[0].PatternCorrectionReceived);


	$('#gradingDate').val(data[0].patternGradingDate);
	$('#gradingDespatch').val(data[0].patternGradingDespatch).change();;
	$('#gradingdispatchreceivedby').val(data[0].patternGradingReceived);

	$('#markingDate').val(data[0].patternMarkingDate)
	$('#markingDespatch').val(data[0].patternMakingDespatch).change();;
	$('#markingReceviedBy').val(data[0].patternMarkingReceived);


	$('#exampleModal').modal('hide');


	$("#save").attr('disabled', true);
	$("#edit").attr('disabled', false);

}




function dateFormatting(field){

	var date= new Date(field).toLocaleDateString('fr-CA');

	return date;
}



function sampleCadReport(id) {

	$.ajax({
		type: 'POST',
		dataType: 'json',
		url: './SampleReport/'+id,
		data: {

		},
		success: function (data) {
			if(data=='yes'){
				var url = "SampleCadReportView";
				window.open(url, '_blank');
			}
		}
	});
}


function searchSampleRequisition(v) {
	  $('#exampleModal').modal('hide');
	$.ajax({
		type: 'GET',
		dataType: 'json',
		url: './searchSampleRequisition/' + v,
		success: function (data) {
			if (data.result == "Something Wrong") {
				dangerAlert("Something went wrong");
			} else if (data.result == "duplicate") {
				dangerAlert("Duplicate Item Name..This Item Name Already Exist")
			} else {
				drawItemTable(data.result);
			}
		}
	});
}


function printSampleRequisition(sampleReqId) {
	$.ajax({
		type: 'GET',
		dataType: 'json',
		url: './sampleRequisitionInfo',
		data: {
			sampleReqId: sampleReqId
		},
		success: function (data) {
			if (data == "Success") {
				var url = "printsampleRequisition";
				window.open(url, '_blank');

			}
		}
	});
}
function drawItemTable(dataList) {


	var length = dataList.length;
	sizeGroupId = "";
	var tables = "";
	var isClosingNeed = false;
	for (var i = 0; i < length; i++) {
		var item = dataList[i];

		if (sizeGroupId != item.sizeGroupId) {
			if (isClosingNeed) {
				tables += "</tbody></table> </div></div>";
			}
			sizeGroupId = item.sizeGroupId;
			tables += `<div class="row">
				<div class="col-md-12 table-responsive" >
				<table class="table table-hover table-bordered table-sm mb-0 small-font">
				<thead class="no-wrap-text bg-light">
				<tr>
				<th scope="col" class="min-width-150">Style</th>
				<th scope="col" class="min-width-150">Item Name</th>
				<th scope="col" class="min-width-150">Color</th>
				<th scope="col">Purchase Order</th>`
				var sizeListLength = sizesListByGroup['groupId' + sizeGroupId].length;
			for (var j = 0; j < sizeListLength; j++) {
				tables += "<th class=\"min-width-60 mx-auto\"scope=\"col\">" + sizesListByGroup['groupId' + sizeGroupId][j].sizeName + "</th>";
			}
			tables += `<th scope="col">Total Qty</th>
				</tr>
				</thead>
				<tbody id="dataList">`
				isClosingNeed = true;
		}
		
		$('#sampleReqId').val(item.sampleReqId);
		$('#buyerOrderId').val(item.buyerOrderId);
		$('#purchaseOrder').val(item.purchaseOrder);
		$('#styleId').val(item.styleId);
		$('#styleNo').val(item.styleNo);
		$('#itemName').val(item.itemName);
		$('#vPurchaseOrder').val(item.purchaseOrder);
		$('#itemId').val(item.itemId);
		$('#colorId').val(item.colorId);
		if(item.buyerId=='0'){
			$('#POStatus').val('0');
		}
		else{
			$('#POStatus').val('1');
		}
		
		tables += "<tr id='itemRow" + i + "' data-id='" + item.autoId + "'><td>" + item.styleNo + "</td><td>" + item.itemName + "</td><td>" + item.colorName + "</td><td>" + item.purchaseOrder + "</td>"
		var sizeList = item.sizeList;
		var sizeListLength = sizeList.length;
		var totalSizeQty = 0;
		//sampleCadQty=0;
		for (var j = 0; j < sizeListLength; j++) {
			totalSizeQty = totalSizeQty + parseFloat(sizeList[j].sizeQuantity);
			sampleRequistionQty=sampleRequistionQty+parseFloat(sizeList[j].sizeQuantity);
			tables += "<td>" + sizeList[j].sizeQuantity + "</td>"
		}
		tables += "<td class='totalUnit' id='totalUnit" + item.autoId + "'>" + totalSizeQty + "</td></tr>";

	}
	tables += "</tbody></table> </div></div>";



	document.getElementById("samplecadtableList").innerHTML = tables;


}