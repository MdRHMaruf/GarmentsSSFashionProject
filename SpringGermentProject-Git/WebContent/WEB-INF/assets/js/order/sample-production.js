window.onload = ()=>{
	document.title = "Sample Production";
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

function setSampleProductionInfo(sampleCommentId) {

    $.ajax({
        type: 'GET',
        dataType: 'json',
        url: './getSampleProductionInfo',
        data: {
            sampleCommentId: sampleCommentId
        },
        success: function (data) {
            if (data.sampleProduction == "Something Wrong") {
                dangerAlert("Something went wrong");
            } else {
                //drawSampleCommentsListSearchTable(data.sampleCommentsList);
            	
            	$('#searchModal').modal('hide');
                const sample = data.sampleProduction;
                
                $("#sampleCommentsNo").val(sample.sampleCommentId);
                $("#sampleCommentsId").val(sample.sampleCommentId);
                $("#purchaseOrder").val(sample.purchaseOrder);
                $("#styleNo").val(sample.styleNo);
                $("#itemName").val(sample.itemName);
                $("#color").val(sample.colorName);
                $("#size").val(sample.size);

                
                var actualCuttingDate = new Date(sample.cuttingDate); 
            	var cuttingDate=actualCuttingDate.getFullYear() + "-" +('0' + (actualCuttingDate.getMonth() + 1)).slice(-2) + "-" + ('0' + actualCuttingDate.getDate()).slice(-2) + "T" + ('0' + actualCuttingDate.getHours()).slice(-2) + ":" + ('0' + actualCuttingDate.getMinutes()).slice(-2);
            	
            	if(sample.cuttingDate!=' :00'){
            		$('#cuttingDate').val(cuttingDate);
            	}
                
            	
                $("#cuttingQty").val(parseFloat(sample.cuttingQty).toFixed(2));
                $("#reqQty").val(parseFloat(sample.requisitionQty).toFixed(2));
                
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
            	
            	
            	 $("#printReceivedQty").val(parseFloat(sample.printReceivedQty).toFixed(2));
            	 
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

             
                $("#embroideryReceivedQty").val(parseFloat(sample.embroideryReceivedQty).toFixed(2));
                
                
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



$("#btnPost").click(() => {

    const sampleCommentsId = $("#sampleCommentsId").val();

    
	let cuttingDate = $("#cuttingDate").val();
	cuttingDate = cuttingDate.slice(0, cuttingDate.indexOf('T')) + " " + cuttingDate.slice(cuttingDate.indexOf('T') + 1) + ":00";
    const cuttingQty = $("#cuttingQty").val();
    
	let printSendDate = $("#printSendDate").val();
	printSendDate = printSendDate.slice(0, printSendDate.indexOf('T')) + " " + printSendDate.slice(printSendDate.indexOf('T') + 1) + ":00";
	
	
	let printReceivedDate = $("#printReceivedDate").val();
	printReceivedDate = printReceivedDate.slice(0, printReceivedDate.indexOf('T')) + " " + printReceivedDate.slice(printReceivedDate.indexOf('T') + 1) + ":00";
	
 
    const printReceivedQty = $("#printReceivedQty").val();
    
	let embroiderySendDate = $("#embroiderySendDate").val();
	embroiderySendDate = embroiderySendDate.slice(0, embroiderySendDate.indexOf('T')) + " " + embroiderySendDate.slice(embroiderySendDate.indexOf('T') + 1) + ":00";
	
	let embroideryReceivedDate = $("#embroideryReceivedDate").val();
	embroideryReceivedDate = embroideryReceivedDate.slice(0, embroideryReceivedDate.indexOf('T')) + " " + embroideryReceivedDate.slice(embroideryReceivedDate.indexOf('T') + 1) + ":00";
	
	
    const embroideryReceivedQty = $("#embroideryReceivedQty").val();
    
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
                    printReceivedQty: printReceivedQty,
                    embroiderySendDate: embroiderySendDate,
                    embroideryReceivedDate: embroideryReceivedDate,
                    embroideryReceivedQty: embroideryReceivedQty,
                    sewingSendDate: sewingSendDate,
                    sewingFinishDate: sewingFinishDate,
                    SampleProductionUserId: '',
                    SampleProductionUserIp: '',
                    SampleCommentFlag: '',
                    operatorName: operatorName,
                    quality: quality,
                    userId: userId
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

function showPreview() {
    const commentsId = $("#sampleCommentsId").val();
    const printType = "sizeWise";
    let url = "getSampleProductionReport/" + commentsId + "/" + printType;
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

jQuery.extend(jQuery.expr[':'], {
    focusable: function (el, index, selector) {
        return $(el).is('a, button, :input, [tabindex]');
    }
});

$(document).on('keypress', 'input,select', function (e) {
    if (e.which == 13) {
        e.preventDefault();
        // Get all focusable elements on the page
        var $canfocus = $(':focusable');
        var index = $canfocus.index(document.activeElement) + 1;
        if (index >= $canfocus.length) index = 0;
        $canfocus.eq(index).focus();
    }
});