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
                console.log(data.sampleProduction);
                $("#sampleCommentsNo").val(sample.sampleCommentId);
                $("#sampleCommentsId").val(sample.sampleCommentId);
                $("#purchaseOrder").val(sample.purchaseOrder);
                $("#styleNo").val(sample.styleNo);
                $("#itemName").val(sample.itemName);
                $("#color").val(sample.colorName);
                $("#size").val(sample.size);
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
    var url = "getSampleProductionReport/" + commentsId + "/" + printType;
    window.open(url, '_blank');
};

function drawSampleCommentsListSearchTable(data) {
    const length = data.length;
    var tr_list = "";
    $("#sampleCommentsList").empty();

    for (var i = 0; i < length; i++) {
        const rowData = data[i];
        const id = rowData.sampleCommentId;
        tr_list = tr_list + "<tr id='row-" + id + "'  data-style-id='" + rowData.styleId + "' data-item-id='" + rowData.itemId + "' data-size-id='" + rowData.sizeId + "' data-sample-type-id='" + rowData.sampleTypeId + "' >"
            + "<td id='id-" + id + "'>" + id + "</td>"
            + "<td id='purchaseOrder-" + id + "'>" + rowData.purchaseOrder + "</td>"
            + "<td id='styleNo-" + id + "'>" + rowData.styleNo + "</td>"
            + "<td id='itemName-" + id + "'>" + rowData.itemName + "</td>"
            + "<td id='colorName-" + id + "'>" + rowData.colorName + "</td>"
            + "<td id='size-" + id + "'>" + rowData.size + "</td>"
            + "<td id='sampleTypeName-" + id + "'>" + rowData.sampleTypeName + "</td>"
            + "<td ><i class='fa fa-search' style='cursor:pointer' onclick='setSampleProductionInfo(" + id + ")'></td>"
            + "</tr>";
    }
    $("#sampleCommentsList").html(tr_list);
}


function successAlert(message) {
    var element = $(".alert");
    element.hide();
    element = $(".alert-success");
    document.getElementById("successAlert").innerHTML = "<strong>Success!</strong> " + message + "...";
    element.show();
    setTimeout(() => {
        element.toggle('fade');
    }, 2500);
}

function warningAlert(message) {
    var element = $(".alert");
    element.hide();
    element = $(".alert-warning");
    document.getElementById("warningAlert").innerHTML = "<strong>Warning!</strong> " + message + "..";
    element.show();
    setTimeout(() => {
        element.toggle('fade');
    }, 2500);
}

function dangerAlert(message) {
    var element = $(".alert");
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