

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
function insertSample(){

	var user=$('#userId').val();


	var buyerOrderId=$('#buyerOrderId').val()==''?"0":$('#buyerOrderId').val();
	var styleId=$('#styleId').val();
	var itemId=$('#itemId').val();
	var colorId=$('#colorId').val();
	var POStatus=$('#POStatus').val();
	
	var sampleReqId=$('#sampleReqId').val()==''?"0":$('#sampleReqId').val();
	
	console.log("buyerOrderId "+buyerOrderId);
	console.log("sampleReqId "+sampleReqId);


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
						styleId : styleId,
						itemId : itemId,	
						colorId : colorId,		 
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


function editSmapleCad() {


	var user=$('#userId').val();

	var withPO=$('#withPO')[0].checked;
	var withOutPO=$('#withOutPO')[0].checked;

	var purchaseOrder=$('#purchaseOrder').val();
	var styleId=$('#styleId').val();
	var itemId=$('#itemName').val();
	var sampleCommentsNo=$('#sampleCommentsNo').val();
	var itemColor=$('#itemColor').val();
	var size=$('#size').val();
	var sampletype=$('#sampletype').val();

	var patternmakingdate=dateFormatting($('#patternmakingdate').val());
	var makeingDespatch=$('#makeingDespatch').val();
	var patternmakingreceivedby=$('#patternmakingreceivedby').val();
	console.log(" pattern making recevied by "+patternmakingreceivedby)

	var feedback=$('#feedback').val();

	var patterncorrectiondate=dateFormatting($('#patterncorrectiondate').val());
	var patterncorrectiondispatch=$('#patterncorrectiondispatch').val();
	var correctionReceviedBy=$('#correctionReceviedBy').val();

	var gradingDate=dateFormatting($('#gradingDate').val());
	var gradingDespatch=$('#gradingDespatch').val();
	var gradingdispatchreceivedby=$('#gradingdispatchreceivedby').val();

	var markingDate=dateFormatting($('#markingDate').val());
	var markingdispatch=$('#markingDespatch').val();
	var markingReceviedBy=$('#markingReceviedBy').val();

	if (withPO==true) {


		$.ajax({
			type: 'GET',
			dataType: 'json',
			url: './editSampleCad',
			data: {
				sampleCommentId:sampleCommentId,
				purchaseOrder:purchaseOrder,
				styleId:styleId,
				itemId:itemId,	
				colorId:colorId,		 
				sizeid:size,

				patternMakingDate:patternmakingdate,
				patternMakingDespatch:makeingDespatch,
				patternMadingReceived:patternmakingreceivedby,

				patternCorrectionDate:patterncorrectiondate,
				patternCorrectionDespatch:patterncorrectiondispatch,
				PatternCorrectionReceived:correctionReceviedBy,

				patternGradingDate:gradingDate,
				patternGradingDespatch:gradingDespatch,
				patternGradingReceived:gradingdispatchreceivedby,


				patternMarkingDate:markingDate,
				patternMarkingDespatch:markingdispatch,
				patternMarkingReceived:markingReceviedBy,

				feedbackComments:feedback,
				POStatus:'1',

			},
			success: function (data) {

				if(data=='success'){
					alert("Successfully Inserted")
					
				}else{
					alert(" Insert Failed")
				}
			}
		});




	}else{

	}


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
				<th scope="col"><i class="fa fa-edit"></i></th>
				<th scope="col"><i class="fa fa-trash"></i></th>
				</tr>
				</thead>
				<tbody id="dataList">`
				isClosingNeed = true;
		}
		
		$('#sampleReqId').val(item.sampleReqId);
		$('#buyerOrderId').val(item.purchaseOrder);
		$('#styleId').val(item.styleId);
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
		tables += "<td class='totalUnit' id='totalUnit" + item.autoId + "'>" + totalSizeQty + "</td><td><i class='fa fa-edit' onclick='setBuyerPoItemDataForEdit(" + item.autoId + ")'> </i></td><td><i class='fa fa-trash' onclick='deleteBuyerPoItem(" + item.autoId + ")'> </i></td></tr>";

	}
	tables += "</tbody></table> </div></div>";



	document.getElementById("samplecadtableList").innerHTML = tables;


}