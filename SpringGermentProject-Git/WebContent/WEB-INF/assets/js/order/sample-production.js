var sizeValueListForSet = [];
var sizeValueListForCutting = [];
var sizesListByGroup = JSON;
var sizeGroupId="";

var sizeCount=0;

window.onload = ()=>{
	document.title = "Sample Production";
	
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

function sizeLoadByGroup() {

	var groupId = $("#sizeGroup").val().trim();
	var child = "";
	var length = 0;
	if (groupId != "0") {
		length = sizesListByGroup['groupId' + groupId].length;
		for (var i = 0; i < length; i++) {
			//child += " <div class=\"list-group-item pt-0 pb-0 sizeNameList\"> <div class=\"form-group row mb-0\"><label for=\"sizeId" + sizesListByGroup['groupId' + groupId][i].sizeId + "\" class=\"col-md-6 col-form-label-sm pb-0 mb-0\" style=\"height:25px;\">" + sizesListByGroup['groupId' + groupId][i].sizeName + "</label><input type=\"number\" class=\"form-control-sm col-md-6\" id=\"sizeValue" + sizesListByGroup['groupId' + groupId][i].sizeId + "\" style=\"height:25px;\"></div></div>";
			child += " <div class=\"list-group-item pt-0 pb-0\"> <div class=\"form-group row mb-0\"><label for=\"sizeValue" + i + "\" class=\"col-md-6 col-form-label-sm pb-0 mb-0\" style=\"height:25px;\">" + sizesListByGroup['groupId' + groupId][i].sizeName + "</label><input type=\"number\" class=\"form-control-sm col-md-6 sizeValue\" id=\"sizeValue" + i + "\" style=\"height:25px;\"> <input type=\"hidden\" id=\"sizeId" + i + "\" value=" + sizesListByGroup['groupId' + groupId][i].sizeId + "></div></div>";
		}
		
	}
	
	$("#listGroup").html(child);
	if (sizeValueListForSet.length > 0) {
		for (var i = 0; i < length; i++) {
			$("#sizeValue" + i).val(sizeValueListForSet[i].sizeQuantity);
		}
		sizeValueListForSet = [];
	}
	
	if (sizeValueListForCutting.length > 0) {
		for (var i = 0; i < length; i++) {
			$("#sizeValue" + i).val(sizeValueListForCutting[i].sizeQuantity);
		}
		sizeValueListForCutting = [];
	}

}


$("#sampleSearch").click(() => {
    $.ajax({
        type: 'GET',
        dataType: 'json',
        url: './getSampleCommentsList',
        data: {},
        success: function (data) {
            if (data.sampleCommentsList == "Something Wrong") {
                dangerAlert("Something went wrong");
            } else if (data.sampleCommentsList == "duplicate") {
                dangerAlert("Duplicate Unit Name..This Unit Name Already Exist")
            } else {

                drawSampleCommentsListSearchTable(data.sampleCommentsList);
            }
        }
    });
});


function searchSampleCad(sampleCommentId,sampleReqId){

	$("#sampleCadModal").modal('hide');
	
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

function setCadData(dataList){
	var length = dataList.length;
	for (var i = 0; i < length; i++) {
		var item = dataList[i];
		$('#purchaseOrder').val(item.purchaseOrder);
		$('#styleNo').val(item.styleNo);
		$('#itemName').val(item.itemName);
		$('#sampleCommentsNo').val(item.sampleCadId);
		$('#sampleType').val(item.sampleTypeId);
	}
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
				<th scope="col">Purchase Order</th>
				<th scope="col">Type</th>`
				var sizeListLength = sizesListByGroup['groupId' + sizeGroupId].length;
			for (var j = 0; j < sizeListLength; j++) {
				tables += "<th class=\"min-width-60 mx-auto\"scope=\"col\">" + sizesListByGroup['groupId' + sizeGroupId][j].sizeName + "</th>";
			}
			tables += `<th scope="col">Total Units</th>
				<th scope="col"><i class="fa fa-edit"></i></th>
				<th scope="col"><i class="fa fa-trash"></i></th>
				</tr>
				
				
				</thead>
				<tbody id="dataList">`
				isClosingNeed = true;
		}
		//add Unit
		tables += "<tr id='itemRow" + i + "' data-id='" + item.autoId + "'><td>" + item.styleNo + "</td><td>" + item.itemName + "</td><td>" + item.colorName + "</td><td>" + item.purchaseOrder + "</td><td>Requisition</td>"
		var sizeList = item.sizeList;
		var sizeListLength = sizeList.length;
		var totalSizeQty = 0;
		for (var j = 0; j < sizeListLength; j++) {
			totalSizeQty = totalSizeQty + parseFloat(sizeList[j].sizeQuantity);
			tables += "<td>" + sizeList[j].sizeQuantity + "</td>"
		}
		tables += "<td class='totalUnit' id='totalUnit" + item.autoId + "'>" + totalSizeQty + "</td><td><i class='fa fa-edit' onclick='setSampleRequisitionItem(" + item.autoId + ")'> </i></td><td><i class='fa fa-trash' onclick='deleteSampleRequisitioonItem(" + item.autoId + ","+item.sampleReqId+")'> </i></td></tr>";
	
		//
		
		tables += "<tr class='itemCuttingRow' data-id='" + item.autoId + "'><td></td><td></td><td></td><td></td><td>Cutting Qty</td>"
		var sizeList = item.sizeList;
		var sizeListLength = sizeList.length;
		var totalSizeQty = 0;
		sizeCount=0;
		for (var j = 0; j < sizeListLength; j++) {
			sizeCount++;
			totalSizeQty = totalSizeQty + parseFloat(sizeList[j].sizeQuantity);
			tables += "<td><input type='text' style='width:70px;height:25px;' class='cuttingQty-"+j+"'/><input type='hidden' style='width:70px;height:25px;' value='"+sizeList[j].sizeId+"' class='sizeId-"+j+"'/></td>"
		}
		tables += "<td class='totalUnit' id='totalUnit" + item.autoId + "'><input readonly id='totalCuttingQty' type='text' style='width:70px;height:25px;'/></td><td><i class='fa fa-edit' onclick='setSampleRequisitionItem(" + item.autoId + ")'> </i></td><td><i class='fa fa-trash' onclick='deleteSampleRequisitioonItem(" + item.autoId + ","+item.sampleReqId+")'> </i></td></tr>";
	
	}
	tables += "</tbody></table> </div></div>";
	document.getElementById("tableList").innerHTML = tables;
}


function setSampleProductionInfo(sampleCommentId,sampleReqId) {
	
	$('#sampleCadProductionModal').modal('hide');
	
    $.ajax({
        type: 'GET',
        dataType: 'json',
        url: './getSampleProductionInfo',
        data: {
            sampleCommentId: sampleCommentId,
            sampleReqId:sampleReqId
        },
        success: function (data) {
        	
        	console.log("data "+data);
        	
            if (data.sampleProduction == "Something Wrong") {
                dangerAlert("Something went wrong");
            } else {
            	
            	drawSampleCadItemTable(data.result_sample_requisition);
            	
                const sample = data.sampleProduction;
                
                $("#sampleCommentsNo").val(sample.sampleCommentId);
                $("#sampleCommentsId").val(sample.sampleCommentId);
                
                $("#sampleType").val(sample.sampleTypeName);
                $("#purchaseOrder").val(sample.purchaseOrder);
                $("#styleNo").val(sample.styleNo);
                $("#itemName").val(sample.itemName);
                $("#color").val(sample.colorName);
                $("#size").val(sample.size);


            	var cuttingDateDT = new Date(sample.cuttingDate); 
            	var cuttingDate=cuttingDateDT.getFullYear() + "-" +('0' + (cuttingDateDT.getMonth() + 1)).slice(-2) + "-" + ('0' + cuttingDateDT.getDate()).slice(-2) + "T" + ('0' + cuttingDateDT.getHours()).slice(-2) + ":" + ('0' + cuttingDateDT.getMinutes()).slice(-2);
            	
      		    console.log("cuttingDate "+cuttingDate);
      		    $("#cuttingDate").val(cuttingDate);
      		  
                
              	var actualPrintSendDate = new Date(sample.printSendDate); 
            	var printSendDate=actualPrintSendDate.getFullYear() + "-" +('0' + (actualPrintSendDate.getMonth() + 1)).slice(-2) + "-" + ('0' + actualPrintSendDate.getDate()).slice(-2) + "T" + ('0' + actualPrintSendDate.getHours()).slice(-2) + ":" + ('0' + actualPrintSendDate.getMinutes()).slice(-2);
            	
            	if(sample.printSendDate!=' :00'){
            		$('#printSendDate').val(printSendDate);
            	}
            	
            	
            	var actualprintReceivedDate = new Date(sample.printReceivedDate); 
            	var printReceivedDate=actualprintReceivedDate.getFullYear() + "-" +('0' + (actualprintReceivedDate.getMonth() + 1)).slice(-2) + "-" + ('0' + actualprintReceivedDate.getDate()).slice(-2) + "T" + ('0' + actualprintReceivedDate.getHours()).slice(-2) + ":" + ('0' + actualprintReceivedDate.getMinutes()).slice(-2);
            	
            	if(sample.printReceivedDate!=' :00'){
            		$('#printReceivedDate').val(printReceivedDate);
            	}
            	
            	
            	 $("#printReceivedBy").val(sample.printReceivedBy);
            	 
               	var actualEmbroiderySendDate = new Date(sample.embroiderySendDate); 
            	var embroiderySendDate=actualEmbroiderySendDate.getFullYear() + "-" +('0' + (actualEmbroiderySendDate.getMonth() + 1)).slice(-2) + "-" + ('0' + actualEmbroiderySendDate.getDate()).slice(-2) + "T" + ('0' + actualEmbroiderySendDate.getHours()).slice(-2) + ":" + ('0' + actualEmbroiderySendDate.getMinutes()).slice(-2);
            	
            	if(sample.embroiderySendDate!=' :00'){
            		$('#embroiderySendDate').val(embroiderySendDate);
            	
            	}
            	
            	
             	var actualEmbroideryReceivedDate = new Date(sample.embroideryReceivedDate); 
            	var embroiderySendDate=actualEmbroideryReceivedDate.getFullYear() + "-" +('0' + (actualEmbroideryReceivedDate.getMonth() + 1)).slice(-2) + "-" + ('0' + actualEmbroideryReceivedDate.getDate()).slice(-2) + "T" + ('0' + actualEmbroideryReceivedDate.getHours()).slice(-2) + ":" + ('0' + actualEmbroideryReceivedDate.getMinutes()).slice(-2);
            	
            	if(sample.embroideryReceivedDate!=' :00'){
            		$('#embroideryReceivedDate').val(embroiderySendDate);
            	
            	}

             
                $("#embroideryReceived").val(sample.embroideryReceived);
                
                
              	var actualSewingSendDate = new Date(sample.sewingSendDate); 
            	var sewingSendDate=actualSewingSendDate.getFullYear() + "-" +('0' + (actualSewingSendDate.getMonth() + 1)).slice(-2) + "-" + ('0' + actualSewingSendDate.getDate()).slice(-2) + "T" + ('0' + actualSewingSendDate.getHours()).slice(-2) + ":" + ('0' + actualSewingSendDate.getMinutes()).slice(-2);
            	
            	if(sample.sewingSendDate!=' :00'){
            		$('#sewingSendDate').val(sewingSendDate);
            	
            	}
              

              	var actualSewingFinishDate = new Date(sample.sewingFinishDate); 
            	var sewingFinishDate=actualSewingFinishDate.getFullYear() + "-" +('0' + (actualSewingFinishDate.getMonth() + 1)).slice(-2) + "-" + ('0' + actualSewingFinishDate.getDate()).slice(-2) + "T" + ('0' + actualSewingFinishDate.getHours()).slice(-2) + ":" + ('0' + actualSewingFinishDate.getMinutes()).slice(-2);
            	
            	if(sample.sewingSendDate!=' :00'){
            		$('#sewingFinishDate').val(sewingFinishDate);
            	
            	}

                $("#operatorName").val(sample.operatorName);
                $("#quality").val(sample.quality);

            }
        }
    });
}



function drawSampleCadItemTable(dataList) {


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
				<th scope="col">Purchase Order</th>
				<th scope="col">Type</th>`
				var sizeListLength = sizesListByGroup['groupId' + sizeGroupId].length;
			for (var j = 0; j < sizeListLength; j++) {
				tables += "<th class=\"min-width-60 mx-auto\"scope=\"col\">" + sizesListByGroup['groupId' + sizeGroupId][j].sizeName + "</th>";
			}
			tables += `<th scope="col">Total Units</th>
				<th scope="col"><i class="fa fa-edit"></i></th>
				<th scope="col"><i class="fa fa-trash"></i></th>
				</tr>
				
				
				</thead>
				<tbody id="dataList">`
				isClosingNeed = true;
		}
		//add Unit
		tables += "<tr id='itemRow" + i + "' data-id='" + item.autoId + "'><td>" + item.styleNo + "</td><td>" + item.itemName + "</td><td>" + item.colorName + "</td><td>" + item.purchaseOrder + "</td><td>Requisition</td>"
		var sizeList = item.sizeList;

		var sizeListLength = sizeList.length;
		var totalSizeQty = 0;
		for (var j = 0; j < sizeListLength; j++) {
			totalSizeQty = totalSizeQty + parseFloat(sizeList[j].sizeQuantity);
			tables += "<td>" + sizeList[j].sizeQuantity + "</td>"
		}
		tables += "<td class='totalUnit' id='totalUnit" + item.autoId + "'>" + totalSizeQty + "</td><td><i class='fa fa-edit' onclick='setSampleRequisitionItem(" + item.autoId + ")'> </i></td><td><i class='fa fa-trash' onclick='deleteSampleRequisitioonItem(" + item.autoId + ","+item.sampleReqId+")'> </i></td></tr>";
	
		//
		
		tables += "<tr class='itemCuttingRow' data-id='" + item.autoId + "'><td></td><td></td><td></td><td></td><td>Cutting Qty</td>"
		var sizeList = item.sizeCuttingList;
		var sizeListLength = sizeList.length;
		
		console.log("sizeListLength "+sizeListLength);
		
		var totalSizeQty = 0;
		sizeCount=0;
		for (var j = 0; j < sizeListLength; j++) {
			sizeCount++;
			totalSizeQty = totalSizeQty + parseFloat(sizeList[j].sizeQuantity);
			tables += "<td><input type='text' style='width:70px;height:25px;' class='cuttingQty-"+j+"' value='"+parseFloat(sizeList[j].sizeQuantity)+"'/><input type='hidden' style='width:70px;height:25px;' value='"+sizeList[j].sizeId+"' class='sizeId-"+j+"'/></td>"
		}
		tables += "<td class='totalUnit' id='totalUnit" + item.autoId + "'><input readonly id='totalCuttingQty' value='"+totalSizeQty+"' type='text' style='width:70px;height:25px;'/></td><td><i class='fa fa-edit' onclick='setSampleRequisitionItem(" + item.autoId + ")'> </i></td><td><i class='fa fa-trash' onclick='deleteSampleRequisitioonItem(" + item.autoId + ","+item.sampleReqId+")'> </i></td></tr>";
	
	}
	tables += "</tbody></table> </div></div>";
	document.getElementById("tableList").innerHTML = tables;
}


$("#btnPost").click(() => {

    const sampleCommentsId = $("#sampleCommentsNo").val();
    
    console.log("sampleCommentsId "+sampleCommentsId);
    
	var i = 0;
	var value = 0;
	var resultList = [];
	
	for(i=0;i<sizeCount;i++){
		var cuttingQty=$('.cuttingQty-'+i).val();
		var sizeId=$('.sizeId-'+i).val();
		//sizeGroupId="";

		resultList[i] = sizeId+"*"+cuttingQty;

	}
	resultList = "[" + resultList + "]";
    
    
    
	let cuttingDate = $("#cuttingDate").val();
	cuttingDate = cuttingDate.slice(0, cuttingDate.indexOf('T')) + " " + cuttingDate.slice(cuttingDate.indexOf('T') + 1) + ":00";

    
	let printSendDate = $("#printSendDate").val();
	printSendDate = printSendDate.slice(0, printSendDate.indexOf('T')) + " " + printSendDate.slice(printSendDate.indexOf('T') + 1) + ":00";
	
	
	let printReceivedDate = $("#printReceivedDate").val();
	printReceivedDate = printReceivedDate.slice(0, printReceivedDate.indexOf('T')) + " " + printReceivedDate.slice(printReceivedDate.indexOf('T') + 1) + ":00";
	
 
    const printReceivedBy = $("#printReceivedBy").val();
    
	let embroiderySendDate = $("#embroiderySendDate").val();
	embroiderySendDate = embroiderySendDate.slice(0, embroiderySendDate.indexOf('T')) + " " + embroiderySendDate.slice(embroiderySendDate.indexOf('T') + 1) + ":00";
	
	let embroideryReceivedDate = $("#embroideryReceivedDate").val();
	embroideryReceivedDate = embroideryReceivedDate.slice(0, embroideryReceivedDate.indexOf('T')) + " " + embroideryReceivedDate.slice(embroideryReceivedDate.indexOf('T') + 1) + ":00";
	
	
    const embroideryReceivedBy = $("#embroideryReceivedBy").val();
    
	let sewingSendDate = $("#sewingSendDate").val();
	sewingSendDate = sewingSendDate.slice(0, sewingSendDate.indexOf('T')) + " " + sewingSendDate.slice(sewingSendDate.indexOf('T') + 1) + ":00";
	
	let sewingFinishDate = $("#sewingFinishDate").val();
	sewingFinishDate = sewingFinishDate.slice(0, sewingFinishDate.indexOf('T')) + " " + sewingFinishDate.slice(sewingFinishDate.indexOf('T') + 1) + ":00";
	
	
    const operatorName = $("#operatorName").val();
    const quality = $("#quality").val();
    const userId = $("#userId").val();
    
    
    if (sampleCommentsId != "") {
        if (confirm("Are you sure to update this Sample Production...")) {
            $.ajax({
                type: 'POST',
                dataType: 'json',
                url: './postSampleProduction',
                data: {
                    sampleCommentId: sampleCommentsId,
                    cuttingDate: cuttingDate,
                    cuttingQty: cuttingQty,
                    printSendDate: printSendDate,
                    printReceivedDate: printReceivedDate,
                    printReceivedBy: printReceivedBy,
                    embroiderySendDate: embroiderySendDate,
                    embroideryReceivedDate: embroideryReceivedDate,
                    embroideryReceivedBy: embroideryReceivedBy,
                    sewingSendDate: sewingSendDate,
                    sewingFinishDate: sewingFinishDate,
                    SampleProductionUserId: '',
                    SampleProductionUserIp: '',
                    SampleCommentFlag: '',
                    operatorName: operatorName,
                    quality: quality,
                    userId: userId,
                    resultList:resultList,
                    sizeGroupId:sizeGroupId
                },
                success: function (data) {
                    if (data.result == "Something Wrong") {
                        dangerAlert("Something went wrong");
                    } else if (data.result == "duplicate") {
                        dangerAlert("Duplicate Item Name..This Item Name Already Exist")
                    } else {
                        alert("Successfully Submit...");
                        refreshAction();
                    }
                }
            });
        }
    } else {
        warningAlert("Please Select a Sample Comments ");
    }
    
    

});
function refreshAction() {
    location.reload();
}

function sampleProductionDateWiseReport(){
    var date =$('#sampleCadProductionSearchDate').val();
    var reportType = $('#ProductionReportType').val();
    
    if(date!=''){
        var url = `sampleProductionDateWiseReport/${date}@${reportType}`;
        window.open(url, '_blank');
    }
    else{
    	alert("Provide Date");
    }
    

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

function showPreview(sampleCommentId) {
    const commentsId =sampleCommentId;
    const printType = "1";
    
    console.log("commentsId "+commentsId);
    console.log("printType "+printType);
    
    let url = `getSampleProductionReport/${commentsId}@${printType}`;
    window.open(url, '_blank');
};



function drawSampleCommentsListSearchTable(data) {
    const length = data.length;
    let tr_list = "";
    $("#sampleCommentsList").empty();

    for (let i = 0; i < length; i++) {
        const rowData = data[i];
        const id = rowData.sampleCommentId;
        tr_list = tr_list + "<tr id='row'  data-style-id='" + rowData.styleId + "' data-item-id='" + rowData.itemId + "' data-size-id='" + rowData.sizeId + "' data-sample-type-id='" + rowData.sampleTypeId + "' >"
            + "<td id='id'>" + id + "</td>"
            + "<td id='purchaseOrder'>" + rowData.purchaseOrder + "</td>"
            + "<td id='styleNo'>" + rowData.styleNo + "</td>"
            + "<td id='itemName'>" + rowData.itemName + "</td>"
            + "<td id='colorName'>" + rowData.colorName + "</td>"
            + "<td id='sampleTypeName'>" + rowData.sampleTypeName + "</td>"
            + "<td ><i class='fa fa-search' style='cursor:pointer' onclick='setSampleProductionInfo(" + id + ")'></td>"
            + "</tr>";
    }
    $("#sampleCommentsList").html(tr_list);
}


function successAlert(message) {
    let element = $(".alert");
    element.hide();
    element = $(".alert-success");
    document.getElementById("successAlert").innerHTML = "<strong>Success!</strong> " + message + "...";
    element.show();
    setTimeout(() => {
        element.toggle('fade');
    }, 2500);
}

function warningAlert(message) {
    let element = $(".alert");
    element.hide();
    element = $(".alert-warning");
    document.getElementById("warningAlert").innerHTML = "<strong>Warning!</strong> " + message + "..";
    element.show();
    setTimeout(() => {
        element.toggle('fade');
    }, 2500);
}

function dangerAlert(message) {
    let element = $(".alert");
    element.hide();
    element = $(".alert-danger");
    document.getElementById("dangerAlert").innerHTML = "<strong>Duplicate!</strong> " + message + "..";
    element.show();
    setTimeout(() => {
        element.toggle('fade');
    }, 2500);
}

$(document).ready(function () {
    $("input:text").focus(function () { $(this).select(); });
});
$(document).ready(function () {
    $("input").focus(function () { $(this).select(); });
});


$(document).ready(function () {
	$("#searchSampleCad").on("keyup", function () {
		var value = $(this).val().toLowerCase();
		$("#sampleCadList tr").filter(function () {
			$(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
		});
	});
});

$(document).ready(function () {
	$("#searchProduction").on("keyup", function () {
		var value = $(this).val().toLowerCase();
		$("#sampleCadProductionList tr").filter(function () {
			$(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
		});
	});
});
