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

$("#btnUpload").click(() => {
    const id = "54600600212020000789";

    const length = id.length;
    let indexList = [];
    let nextChar = "";
    let currentChar = "";
    let prevChar = "";
    let index = 0;
    console.log(id);
    for (let i = 1; i < length; i++) {
        if (i < (length - 1)) {
            prevChar = id[i - 1];
            currentChar = id[i];
            nextChar = id[i + 1];

            if (prevChar == "0" && currentChar == "0" && nextChar != "0") {
                indexList.push(i);
            }
        }
    }

    console.log(id.slice(0, indexList[0] - 1));
    console.log(id.slice(indexList[0] + 1, indexList[1] - 1));
    console.log(id.slice(indexList[1] + 1));
})

$("#btnPost").click(() => {

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

    let totalProductionQty = productionQty1 + productionQty2 + productionQty3 + productionQty4 + productionQty5 + productionQty6 + productionQty7 + productionQty8 + productionQty9 + productionQty10;


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

    let totalQty = passQty1 + passQty2 + passQty3 + passQty4 + passQty5 + passQty6 + passQty7 + passQty8 + passQty9 + passQty10;



    //reject
    let rejectQty1 = parseFloat(($("#reject-h1").val() == '' ? "0" : $("#reject-h1").val()));
    let rejectQty2 = parseFloat(($("#reject-h2").val() == '' ? "0" : $("#reject-h2").val()));
    let rejectQty3 = parseFloat(($("#reject-h3").val() == '' ? "0" : $("#reject-h3").val()));
    let rejectQty4 = parseFloat(($("#reject-h4").val() == '' ? "0" : $("#reject-h4").val()));
    let rejectQty5 = parseFloat(($("#reject-h5").val() == '' ? "0" : $("#reject-h5").val()));
    let rejectQty6 = parseFloat(($("#reject-h6").val() == '' ? "0" : $("#reject-h6").val()));
    let rejectQty7 = parseFloat(($("#reject-h7").val() == '' ? "0" : $("#reject-h7").val()));
    let rejectQty8 = parseFloat(($("#reject-h8").val() == '' ? "0" : $("#reject-h8").val()));
    let rejectQty9 = parseFloat(($("#reject-h9").val() == '' ? "0" : $("#reject-h9").val()));
    let rejectQty10 = parseFloat(($("#reject-h10").val() == '' ? "0" : $("#reject-h10").val()));

    let totalRejectQty = rejectQty1 + rejectQty2 + rejectQty3 + rejectQty4 + rejectQty5 + rejectQty6 + rejectQty7 + rejectQty8 + rejectQty9 + rejectQty10;


    
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


function checkProductionChange() {




    let tables = "";


    
        tables += `<div class="row">
				<div class="col-md-12 table-responsive" >
				<table class="table table-hover table-bordered table-sm mb-0 small-font">
				<thead class="no-wrap-text bg-light">

				<tr>

				
				<th scope="col">Type</th>
				<th scope="col">08-09</th>
				<th scope="col">09-10</th>
				<th scope="col">10-11</th>
				<th scope="col">11-12</th>
				<th scope="col">12-01</th>
				<th scope="col">02-03</th>
				<th scope="col">03-04</th>
				<th scope="col">04-05</th>
				<th scope="col">05-06</th>
				<th scope="col">06-07</th>
				<th scope="col">07-08</th>
				<th scope="col">08-09</th>
				<th scope="col">Total</th>
				
				</tr>
				</thead>
				<tbody id="dataList">`;

        //<th scope="col">Edit</th>

   

    tables += "<tr class='itemRow' data-id=''>" +
        "<td><p style='color:black;font-weight:bold;'>Production</p><p style='color:green;font-weight:bold;'>Pass</p><p style='color:red;font-weight:bold;'>Reject</p></td>" +
        "<td><input  type='number' class='form-control-sm' id='production-h1' onchange='setTotalQty()' value=''/><input  type='number' onchange='setTotalQty()' class='form-control-sm' id='pass-h1'  value=''/><input  type='number'  class='form-control-sm' id='reject-h1'  value='' /></td>" +
        "<td><input  type='number' class='form-control-sm' id='production--h2' onchange='setTotalQty()' value=''/><input  type='number' onchange='setTotalQty()' class='form-control-sm' id='pass-h2'  value=''/><input  type='number' class='form-control-sm' id='reject-h2'  value='' /></td>" +
        "<td><input  type='number' class='form-control-sm' id='production--h3' onchange='setTotalQty()' value=''/><input  type='number' onchange='setTotalQty()' class='form-control-sm' id='pass-h3'  value=''/><input  type='number' class='form-control-sm' id='reject-h3'  value='' /></td>" +
        "<td><input  type='number' class='form-control-sm' id='production--h4' onchange='setTotalQty()' value=''/><input  type='number' onchange='setTotalQty()' class='form-control-sm' id='pass-h4'  value=''/><input  type='number' class='form-control-sm' id='reject-h4'  value='' /></td>" +
        "<td><input  type='number' class='form-control-sm' id='production--h5' onchange='setTotalQty()' value=''/><input  type='number' onchange='setTotalQty()' class='form-control-sm' id='pass-h5'  value=''/><input  type='number' class='form-control-sm' id='reject-h5'  value='' /></td>" +
        "<td><input  type='number' class='form-control-sm' id='production--h6' onchange='setTotalQty()' value=''/><input  type='number' onchange='setTotalQty()' class='form-control-sm' id='pass-h6'  value=''/><input  type='number' class='form-control-sm' id='reject-h6'  value='' /></td>" +
        "<td><input  type='number' class='form-control-sm' id='production--h7' onchange='setTotalQty()' value=''/><input  type='number' onchange='setTotalQty()' class='form-control-sm' id='pass-h7'  value=''/><input  type='number' class='form-control-sm' id='reject-h7'  value='' /></td>" +
        "<td><input  type='number' class='form-control-sm' id='production--h8' onchange='setTotalQty()' value=''/><input  type='number' onchange='setTotalQty()' class='form-control-sm' id='pass-h8'  value=''/><input  type='number' class='form-control-sm' id='reject-h8'  value='' /></td>" +
        "<td><input  type='number' class='form-control-sm' id='production--h9' onchange='setTotalQty()' value=''/><input  type='number' onchange='setTotalQty()' class='form-control-sm' id='pass-h9'  value=''/><input  type='number' class='form-control-sm' id='reject-h9'  value='' /></td>" +
        "<td><input  type='number' class='form-control-sm' id='production--h10' onchange='setTotalQty()' value=''/><input  type='number' onchange='setTotalQty()' class='form-control-sm' id='pass-h10'  value=''/><input  type='number' class='form-control-sm' id='reject-h10'  value='' /></td>" +
        "<td><input  type='number' class='form-control-sm' id='production--h11' onchange='setTotalQty()' value=''/><input  type='number' onchange='setTotalQty()' class='form-control-sm' id='pass-h11'  value=''/><input  type='number' class='form-control-sm' id='reject-h11'  value='' /></td>" +
        "<td><input  type='number' class='form-control-sm' id='production--h12' onchange='setTotalQty()' value=''/><input  type='number' onchange='setTotalQty()' class='form-control-sm' id='pass-h12'  value=''/><input  type='number' class='form-control-sm' id='reject-h12'  value='' /></td>" +
        "<td><input  type='number' class='form-control-sm' id='production-total'  value='' readonly/><input  type='number' id='pass-total' readonly class='form-control-sm'/><input  type='number'  class='form-control-sm' id='reject-total'  value='' /></td>" +
        "</tr>"
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