window.onload = ()=>{
    document.title = "Fabrics Pending Transaction";
  } 
$("#btnSearch").click(() => {

    const departmentId = $("#departmentId").val();

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
                    url: './getPendingFabricsIssueList',
                    data: {
                        departmentId: departmentId,
                        fromDate: fromDate,
                        toDate: toDate,
                        itemType: itemType,
                        approveType: approveType
                    },
                    success: function (data) {
                        drawPendingList(data.pendingList);
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



function printFabricsIssue(transactionId, transactionType) {
    let url = "printFabricsIssue/" + transactionId + "/" + transactionType;
    window.open(url, '_blank');
}

function receiveFabricsIssue(transactionId, transactionType) {

    const departmentId = $("#departmentId").val();
    const userId = $("#userId").val();

    if (confirm("Are you sure to receive this issue?")) {
        $.ajax({
            type: 'POST',
            dataType: 'json',
            url: './fabricsIssueReceive',
            data: {
                transactionId: transactionId,
                transactionType: transactionType,
                departmentId: departmentId,
                userId: userId
            },
            success: function (data) {
                if (data == 'true') {
                    location.reload();
                } else {
                    alert("Something Wrong....");
                }
            }
        });
    }

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
            purchaseOrderList += `purchaseOrder : ${purchaseOrder},styleId : ${styleId},supplierId : ${supplierId},poNo : ${poNo},type : ${type},mdApproval : 1 #`;
        } else {
            purchaseOrderList += `purchaseOrder : ${purchaseOrder},styleId : ${styleId},supplierId : ${supplierId},poNo : ${poNo},type : ${type},mdApproval : 0 #`;
        }


    }
    if (isChecked) {
        purchaseOrderList = purchaseOrderList.slice(0, -1);
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


function drawPendingList(data) {
    const length = data.length;
    let tr_list = "";
    $("#pendingInvoiceList").empty();

    for (let i = 0; i < length; i++) {
        const rowData = data[i];
        const id = i;
        tr_list += `<tr>
        <td>${rowData.transactionId}</td>
        <td>${rowData.departmentName}</td>
        <td>${rowData.itemType}</td>
        <td>${rowData.date}</td>
        <td><button class="form-control-sm btn" onclick="printFabricsIssue('${rowData.transactionId}')"><i class="fa fa-eye"></i> Preview</button></td>
        <td><button class="form-control-sm btn" onclick="receiveFabricsIssue('${rowData.transactionId}')"> Receive</button></td>
    </tr>`;
    }
    $("#pendingInvoiceList").html(tr_list);
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

let today = new Date();
document.getElementById("fromDate").value = today.getFullYear() + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + ('0' + today.getDate()).slice(-2);
document.getElementById("toDate").value = today.getFullYear() + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + ('0' + today.getDate()).slice(-2);