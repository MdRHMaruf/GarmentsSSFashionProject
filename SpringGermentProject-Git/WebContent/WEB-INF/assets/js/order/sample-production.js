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
                const sample = data.sampleProduction;

                $("#sampleCommentsNo").val(sample.sampleCommentId);
                $("#sampleCommentsId").val(sample.sampleCommentId);
                $("#purchaseOrder").val(sample.purchaseOrder);
                $("#styleNo").val(sample.styleNo);
                $("#itemName").val(sample.itemName);
                $("#color").val(sample.colorName);
                $("#size").val(sample.size);
                let date = sample.cuttingDate.split("/");
                // console.log(date)
                // $("#cuttingDate").val(date[2] + "-" + date[1] + "-" + date[0]);
                $("#cuttingDate").val(sample.cuttingDate);
                $("#cuttingQty").val(sample.cuttingQty);
                $("#reqQty").val(sample.requisitionQty);
                $("#printSendDate").val(sample.printSendDate);
                $("#printReceivedDate").val(sample.printReceivedDate);
                $("#printReceivedQty").val(sample.printReceivedQty);
                $("#embroiderySendDate").val(sample.embroiderySendDate);
                $("#embroideryReceivedDate").val(sample.embroideryReceivedDate);
                $("#embroideryReceivedQty").val(sample.embroideryReceivedQty);
                $("#sewingSendDate").val(sample.sewingSendDate);
                $("#sewingFinishDate").val(sample.sewingFinishDate);
                $("#operatorName").val(sample.operatorName);
                $("#quality").val(sample.quality);
                $('#searchModal').modal('hide');
            }
        }
    });
}

function setTotalQty() {

	let productionQty1 = parseFloat(($("#production-h1").val() == '' ? "0" : $("#production-h1").val()));
	let productionQty2 = parseFloat(($("#production-h2").val() == '' ? "0" : $("#production-h2").val()));
	let productionQty3 = parseFloat(($("#production-h3").val() == '' ? "0" : $("#production-h3").val()));
	let productionQty4 = parseFloat(($("#production-h4").val() == '' ? "0" : $("#production-h4").val()));
	let productionQty5 = parseFloat(($("#production-h5").val() == '' ? "0" : $("#production-h5").val()));
	let productionQty6 = parseFloat(($("#production-h6").val() == '' ? "0" : $("#production-h6").val()));
	let productionQty7 = parseFloat(($("#production-h7").val() == '' ? "0" : $("#production-h7").val()));
	let productionQty8 = parseFloat(($("#production-h8").val() == '' ? "0" : $("#production-h8").val()));
	let productionQty9 = parseFloat(($("#production-h9").val() == '' ? "0" : $("#production-h9").val()));
	let productionQty10 = parseFloat(($("#production-h10").val() == '' ? "0" : $("#production-h10").val()));
	let productionQty11 = parseFloat(($("#production-h11").val() == '' ? "0" : $("#production-h11").val()));
	let productionQty12 = parseFloat(($("#production-h12").val() == '' ? "0" : $("#production-h12").val()));

	let totalQty = productionQty1 + productionQty2 + productionQty3 + productionQty4 + productionQty5 + productionQty6 + productionQty7 + productionQty8 + productionQty9 + productionQty10 + productionQty11 + productionQty12;

	$("#production-total").val(totalQty);


	let passQty1 = parseFloat(($("#pass-h1").val() == '' ? "0" : $("#pass-h1").val()));
	let passQty2 = parseFloat(($("#pass-h2").val() == '' ? "0" : $("#pass-h2").val()));
	let passQty3 = parseFloat(($("#pass-h3").val() == '' ? "0" : $("#pass-h3").val()));
	let passQty4 = parseFloat(($("#pass-h4").val() == '' ? "0" : $("#pass-h4").val()));
	let passQty5 = parseFloat(($("#pass-h5").val() == '' ? "0" : $("#pass-h5").val()));
	let passQty6 = parseFloat(($("#pass-h6").val() == '' ? "0" : $("#pass-h6").val()));
	let passQty7 = parseFloat(($("#pass-h7").val() == '' ? "0" : $("#pass-h7").val()));
	let passQty8 = parseFloat(($("#pass-h8").val() == '' ? "0" : $("#pass-h8").val()));
	let passQty9 = parseFloat(($("#pass-h9").val() == '' ? "0" : $("#pass-h9").val()));
	let passQty10 = parseFloat(($("#pass-h10").val() == '' ? "0" : $("#pass-h10").val()));
	let passQty11 = parseFloat(($("#pass-h11").val() == '' ? "0" : $("#pass-h11").val()));
	let passQty12 = parseFloat(($("#pass-h12").val() == '' ? "0" : $("#pass-h12").val()));

	

	totalQty = passQty1 + passQty2 + passQty3 + passQty4 + passQty5 + passQty6 + passQty7 + passQty8 + passQty9 + passQty10 + passQty11 + passQty12;

    $("#pass-total").val(totalQty);

}

$("#btnPost").click(() => {
    let productionType = $('#productionType').val();
    let passType = $('#passType').val();
    
    let buyerId = '';
    let buyerOrderId = '';
    let styleId = '';
    let itemId = '';
    let purchaseOrder = '';
    const sampleCommentsId = $("#sampleCommentsId").val();

    const cuttingDate = $("#cuttingDate").val();
    const cuttingQty = $("#cuttingQty").val();
    const printSendDate = $("#printSendDate").val();
    const printReceivedDate = $("#printReceivedDate").val();
    const printReceivedQty = $("#printReceivedQty").val();
    const embroiderySendDate = $("#embroiderySendDate").val();
    const embroideryReceivedDate = $("#embroideryReceivedDate").val();
    const embroideryReceivedQty = $("#embroideryReceivedQty").val();
    const sewingSendDate = $("#sewingSendDate").val();
    const sewingFinishDate = $("#sewingFinishDate").val();
    const operatorName = $("#operatorName").val();
    const quality = $("#quality").val();
    const userId = $("#userId").val();

    const productionQty1 = parseFloat(($("#production-h1").val() == '' ? "0" : $("#production-h1").val()));
    const productionQty2 = parseFloat(($("#production-h2").val() == '' ? "0" : $("#production-h2").val()));
    const productionQty3 = parseFloat(($("#production-h3").val() == '' ? "0" : $("#production-h3").val()));
    const productionQty4 = parseFloat(($("#production-h4").val() == '' ? "0" : $("#production-h4").val()));
    const productionQty5 = parseFloat(($("#production-h5").val() == '' ? "0" : $("#production-h5").val()));
    const productionQty6 = parseFloat(($("#production-h6").val() == '' ? "0" : $("#production-h6").val()));
    const productionQty7 = parseFloat(($("#production-h7").val() == '' ? "0" : $("#production-h7").val()));
    const productionQty8 = parseFloat(($("#production-h8").val() == '' ? "0" : $("#production-h8").val()));
    const productionQty9 = parseFloat(($("#production-h9").val() == '' ? "0" : $("#production-h9").val()));
    const productionQty10 = parseFloat(($("#production-h10").val() == '' ? "0" : $("#production-h10").val()));
    const productionQty11 = parseFloat(($("#production-h11").val() == '' ? "0" : $("#production-h11").val()));
    const productionQty12 = parseFloat(($("#production-h12").val() == '' ? "0" : $("#production-h12").val()));

    let totalProductionQty = productionQty1 + productionQty2 + productionQty3 + productionQty4 + productionQty5 + productionQty6 + productionQty7 + productionQty8 + productionQty9 + productionQty10 + productionQty11 + productionQty12;


    let passQty1 = parseFloat(($("#pass-h1").val() == '' ? "0" : $("#pass-h1").val()));
    let passQty2 = parseFloat(($("#pass-h2").val() == '' ? "0" : $("#pass-h2").val()));
    let passQty3 = parseFloat(($("#pass-h3").val() == '' ? "0" : $("#pass-h3").val()));
    let passQty4 = parseFloat(($("#pass-h4").val() == '' ? "0" : $("#pass-h4").val()));
    let passQty5 = parseFloat(($("#pass-h5").val() == '' ? "0" : $("#pass-h5").val()));
    let passQty6 = parseFloat(($("#pass-h6").val() == '' ? "0" : $("#pass-h6").val()));
    let passQty7 = parseFloat(($("#pass-h7").val() == '' ? "0" : $("#pass-h7").val()));
    let passQty8 = parseFloat(($("#pass-h8").val() == '' ? "0" : $("#pass-h8").val()));
    let passQty9 = parseFloat(($("#pass-h9").val() == '' ? "0" : $("#pass-h9").val()));
    let passQty10 = parseFloat(($("#pass-h10").val() == '' ? "0" : $("#pass-h10").val()));
    let passQty11 = parseFloat(($("#pass-h11").val() == '' ? "0" : $("#pass-h11").val()));
    let passQty12 = parseFloat(($("#pass-h12").val() == '' ? "0" : $("#pass-h12").val()));

    let totalQty = passQty1 + passQty2 + passQty3 + passQty4 + passQty5 + passQty6 + passQty7 + passQty8 + passQty9 + passQty10 + passQty11 + passQty12;

    let productionValue = productionType + ":" + productionQty1 + ":" + productionQty2 + ":" + productionQty3 + ":" + productionQty4 + ":" + productionQty5 + ":" + productionQty6 + ":" + productionQty7 + ":" + productionQty8 + ":" + productionQty9 + ":" + productionQty10 + ":" + productionQty11+ ":" + productionQty12;
	let passValue = passType + ":" + passQty1 + ":" + passQty2 + ":" + passQty3 + ":" + passQty4 + ":" + passQty5 + ":" + passQty6 + ":" + passQty7 + ":" + passQty8 + ":" + passQty9 + ":" + passQty10 + ":" + passQty11 + ":" + passQty12;
			
    let resultList = operatorName + "*" + sampleCommentsId + "*" + totalProductionQty + "*" + totalQty  + "*" + productionValue + "*" + passValue ;
    

    console.log(resultList)

    if (sampleCommentsId != "") {
        if (confirm("Are you sure to update this Sample Production...")) {
            $.ajax({
                type: 'POST',
                dataType: 'json',
                url: './postSampleProduction',
                data: {
                    sampleCommentId: sampleCommentsId,
                    buyerId : buyerId,
                    buyerOrderId : buyerOrderId,
                    purchaseOrder : purchaseOrder,
                    styleId : styleId,
                    itemId : itemId,
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
                    resultList: resultList,
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


function checkProductionChange() {




    let tables = "";


    
        tables += ``;

        //<th scope="col">Edit</th>

   

    tables += 
    //"<td><button type='button' class='btn btn-sm btn-outline-dark btn-sm'><i class='fa fa-edit'></i></button></td>



    tables += "</tbody></table> </div></div>";
    // tables += "</tbody></table> </div></div>";
    document.getElementById("tableList").innerHTML = tables;
    // $('.tableSelect').selectpicker('refresh');
    // dataList.forEach((data) => {
    // 	$("#employee-" + data.lineId).val(data.employeeId).change();
    // })

}

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
            + "<td id='size'>" + rowData.size + "</td>"
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