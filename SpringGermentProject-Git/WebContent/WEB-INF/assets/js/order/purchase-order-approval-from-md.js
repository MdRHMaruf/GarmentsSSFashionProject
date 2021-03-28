
$("#btnSearch").click(() => {
    const fromDate = $("#fromDate").val();
    const toDate = $("#toDate").val();
    const itemType = $("#itemType").val();
    const approveType = $("#approveType").val();


    if (fromDate != '') {
        if (toDate != '') {
            if (itemType != '0') {
                $.ajax({
                    type: 'GET',
                    dataType: 'json',
                    url: './getPOListForMd',
                    data: {
                        fromDate: fromDate,
                        toDate: toDate,
                        itemType: itemType,
                        approveType: approveType
                    },
                    success: function (data) {
                        drawPurchaseOrderList(data.purchaseOrderList);
                    }
                });
            } else {
                warningAlert("Please Select Item Type..");
                $("#itemType").focus();
            }
        } else {
            warningAlert("Please Select to Date..");
            $("#toDate").focus();
        }
    } else {
        warningAlert("Please Select From Date..");
        $("#fromDate").focus();
    }
});

function printPurchaseOrder(autoId) {
    const purchaseOrder = $("#purchaseOrder-" + autoId).text();
    const row = document.getElementById("row-" + autoId);
    console.log(row);
    const styleId = row.getAttribute("data-style-id");
    const supplierId = row.getAttribute("data-supplier-id");
    const poNo = $("#poNo-" + autoId).text();
    const type = $("#itemType-" + autoId).text();
    const previewType = "general";
    var url = "getPurchaseOrderReport/" + poNo + "/" + supplierId + "/" + type + "/"+previewType;
    window.open(url, '_blank');
}

$("#btnConfirm").click(() => {
    const rowList = $("#purchaseOrderList tr");
    const length = rowList.length;

    let purchaseOrderList = ""

    let isChecked = false;

    for (let i = 0; i < length; i++) {

        const id = rowList[i].id.slice(4);
        const checked = $("#check-" + id).prop('checked');
        console.log("checked", checked);

        const purchaseOrder = $("#purchaseOrder-" + id).text();
        const styleId = rowList[i].getAttribute("data-style-id");
        const supplierId = rowList[i].getAttribute("data-supplier-id");
        const poNo = $("#poNo-" + id).text();
        const type = $("#itemType-" + id).text();

        if (checked) {
            isChecked = checked;
            purchaseOrderList += `purchaseOrder : ${purchaseOrder}@styleId : ${styleId}@supplierId : ${supplierId}@poNo : ${poNo}@type : ${type}@mdApproval : 1 #`;
        } else {
            purchaseOrderList += `purchaseOrder : ${purchaseOrder}@styleId : ${styleId}@supplierId : ${supplierId}@poNo : ${poNo}@type : ${type}@mdApproval : 0 #`;
        }


    }
    if (isChecked) {
        purchaseOrderList = purchaseOrderList.slice(0, -1);
        console.log("purchaseOrderList"+purchaseOrderList);
        $.ajax({
            type: 'GET',
            dataType: 'json',
            url: './confirmPurchaseOrder',
            data: {
                purchaseOrderList: purchaseOrderList
            },
            success: function (data) {
                if (data.result == "Something Wrong") {
                    alert("Something Went Wrong..");
                } else if (data.result == "Successfull") {
                    alert("Purchase Order Approve Successfully..");
                }

            }
        });

    } else {
        alert("Please Check any Purchase Order");
    }


});

$("#btnRefresh").click(() => {
    location.reload();
});


document.getElementById("checkAll").addEventListener("click", function () {
    if ($(this).prop('checked')) {
        $(".check").prop('checked', true);
    } else {
        $(".check").prop('checked', false);
    }

});
function drawPurchaseOrderList(data) {
    const length = data.length;
    var tr_list = "";
    $("#purchaseOrderList").empty();

    for (var i = 0; i < length; i++) {
        const rowData = data[i];
        const id = i;
        tr_list = tr_list + "<tr id='row-" + id + "' data-style-id='" + rowData.styleId + "' data-supplier-id='" + rowData.supplierId + "' >"
            + "<td>" + id + "</td>"
            + "<td id='purchaseOrder-" + id + "'>" + rowData.purchaseOrder + "</td>"
            + "<td id='styleNo-" + id + "'>" + rowData.styleNo + "</td>"
            + "<td id='supplierName-" + id + "'>" + rowData.supplierName + "</td>"
            + "<td id='poNo-" + id + "'>" + rowData.poNo + "</td>"
            + "<td ><input type='checkbox' id='check-" + id + "' class='form-control-sm check' " + (rowData.mdApproval == 1 ? 'Checked' : '') + " ></td>"
            + "<td id='itemType-" + id + "'>" + rowData.type + "</td>"
            + "<td id='orderDate-" + id + "'>" + rowData.orderDate + "</td>"
            + "<td ><i class='fa fa-eye' style='cursor:pointer' onclick='printPurchaseOrder(" + id + ")'></td>"
            + "</tr>";
    }
    $("#purchaseOrderList").html(tr_list);
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

var today = new Date();
document.getElementById("fromDate").value = today.getFullYear() + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + ('0' + today.getDate()).slice(-2);
document.getElementById("toDate").value = today.getFullYear() + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + ('0' + today.getDate()).slice(-2);