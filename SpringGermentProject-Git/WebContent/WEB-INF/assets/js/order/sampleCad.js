

var po;
var style;
var item;
var color;
var size;
var sample;
var sampleCommentId;

window.onload = () => {
	document.title = "Sample Cad";
}  
function poWiseStyleLoad() {
	var purchaseOrder = $("#purchaseOrder option:selected").text();

	if (purchaseOrder != "") {
		$.ajax({
			type: 'GET',
			dataType: 'json',
			url: './getPOWiseStyleLoad',
			data: {
				purchaseOrder: purchaseOrder
			},
			success: function (data) {

				var styleList = data.styleList;
				var options = "<option id='styleNo' value='0' selected>Select Style No</option>";
				var length = styleList.length;
				for (var i = 0; i < length; i++) {
					options += "<option id='styleNo' value='" + styleList[i].styleId + "'>" + styleList[i].styleNo + "</option>";
				};
				document.getElementById("styleNo").innerHTML = options;
				$('.selectpicker').selectpicker('refresh');
				  $('#styleNo').val(style).change();
				styleIdForSet = 0;
			}
		});
	} else {
		var options = "<option id='styleNo' value='0' selected>Select Style No</option>";
		$("#styleNo").html(options);
		$('#styleNo').selectpicker('refresh');
		$('#styleNo').val(0).change();
		styleIdForSet = 0;
	}
}



function styleWiseItemLoad() {
	var styleId = $("#styleNo").val();

	if (styleId != 0) {
		$.ajax({
			type: 'GET',
			dataType: 'json',
			url: './getStyleWiseItem',
			data: {
				styleId: styleId
			},
			success: function (data) {

				var itemList = data.itemList;
				var options = "<option id='itemName' value='0' selected>Select Item Name</option>";
				var length = itemList.length;
				for (var i = 0; i < length; i++) {
					options += "<option id='itemName' value='" + itemList[i].itemId + "'>" + itemList[i].itemName + "</option>";
				};
				document.getElementById("itemName").innerHTML = options;
				$('.selectpicker').selectpicker('refresh');
				  $('#itemName').val(item).change();
				itemIdForSet = 0;
			}
		});
	} else {
		var options = "<option id='itemName' value='0' selected>Select Item Name</option>";
		$("#itemName").html(options);
		$('#itemName').selectpicker('refresh');
		$('#itemName').val(0).change();
		itemIdForSet = 0;
	}

}

function styleItemWiseColorLoad() {
	var styleId = $("#styleNo").val();
	var itemId = $("#itemName").val();

	if (styleId != 0 && itemId != 0) {
		$.ajax({
			type: 'GET',
			dataType: 'json',
			url: './getStyleItemWiseColor',
			data: {
				styleId: styleId,
				itemId: itemId
			},
			success: function (data) {

				var colorList = data.colorList;
				var options = "<option id='itemColor' value='0' selected>Select Item Color</option>";
				var length = colorList.length;
				for (var i = 0; i < length; i++) {
					options += "<option id='itemColor' value='" + colorList[i].colorId + "'>" + colorList[i].colorName + "</option>";
				};
				document.getElementById("itemColor").innerHTML = options;
				$('.selectpicker').selectpicker('refresh');
				$('#itemColor').val(color).change();
				itemColorIdForSet = 0;
			}
		});
	} else {
		var options = "<option id='itemColor' value='0' selected>Select Item Color</option>";
		$("#itemColor").html(options);
		$('#itemColor').selectpicker('refresh');
		$('#itemColor').val(0).change();
		itemColorIdForSet = 0;
	}
}



function styleItemWiseColorsizeLoad() {
	var purchaseOrder = $("#purchaseOrder").val();
	var styleId = $("#styleNo").val();
	var itemId = $("#itemName").val();
	var itemColor = $("#itemColor").val();



	if (styleId != 0 && itemId != 0) {
		$.ajax({
			type: 'GET',
			dataType: 'json',
			url: './styleitemColorWiseSize',
			data: {
				buyerorderid:purchaseOrder,
				style: styleId,
				item: itemId,
				color:itemColor
			},
			success: function (data) {

				var colorList = data.size;
				var options = "<option id='size' value='0' selected>Select Item Color</option>";
				var length = colorList.length;
				for (var i = 0; i < length; i++) {
					options += "<option id='size' value='" + colorList[i].id + "'>" + colorList[i].name + "</option>";
				};
				document.getElementById("size").innerHTML = options;
				$('.selectpicker').selectpicker('refresh');
				$('#size').val(size).change();
				itemColorIdForSet = 0;
			}
		});
	} else {
		var options = "<option id='size' value='0' selected>Select Item Color</option>";
		$("#size").html(options);
		$('#size').selectpicker('refresh');
		$('#size').val(0).change();
		itemColorIdForSet = 0;
	}
}



function insertSample(){

	var user=$('#userId').val();

	var withPO=$('#withPO')[0].checked;
	var withOutPO=$('#withOutPO')[0].checked;

	var purchaseOrder=$('#purchaseOrder').val();
	var styleNo=$('#styleNo').val();
	var itemName=$('#itemName').val();
	var sampleCommentsNo=$('#sampleCommentsNo').val();
	var itemColor=$('#itemColor').val();
	var size=$('#size').val();
	var sampletype=$('#sampletype').val();

	var patternMakingDate=dateFormatting($('#patternmakingdate').val());
	var makingDespatch=$('#makeingDespatch').val();
	var patternMakingReceivedBy=$('#patternmakingreceivedby').val();
	console.log(" pattern making recevied by "+patternMakingReceivedBy)

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
			url: './insertSampleCad',
			data: {
				user : user,
				purchaseOrder : purchaseOrder,
				styleId : styleNo,
				itemId : itemName,	
				colorId : itemColor,		 
				sizeId : size,
				sampleTypeId : sampletype,
				sampleComment : sampleCommentsNo,

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
	var styleNo=$('#styleNo').val();
	var itemName=$('#itemName').val();
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
				styleId:styleNo,
				itemId:itemName,	
				colorId:itemColor,		 
				sizeid:size,
				sampleTypeId:sampletype,
				sampleComment:sampleCommentsNo,

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
				var url = "SampleReportView";
      		window.open(url, '_blank');
			}
	    }
	  });
	}