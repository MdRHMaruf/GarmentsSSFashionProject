
$("#newAccessoriesQCBtn").click(function () {

  $("#qcTransactionId").val("-- New Transaction --");
  $("#btnSubmit").prop("disabled", false);
  $("#btnEdit").prop("disabled", true);
});

$("#findAccessoriesQCBtn").click(function () {
  $.ajax({
    type: 'GET',
    dataType: 'json',
    url: './getAccessoriesQCList',
    data: {},
    success: function (data) {

      console.log(data);
      drawAccessoriesQCListTable(data.accessoriesQCList);
    }
  });
});

$("#grnSearchBtn").click(function () {
  $.ajax({
    type: 'GET',
    dataType: 'json',
    url: './getAccessoriesReceiveList',
    data: {},
    success: function (data) {

      $("#accessoriesReceiveList").empty();
      $("#accessoriesReceiveList").append(drawAccessoriesReceiveListTable(data.accessoriesReceiveList));

    }
  });
});

function setAccessoriesReceiveInfo(transactionId) {
  $.ajax({
    type: 'GET',
    dataType: 'json',
    url: './getAccessoriesReceiveInfo',
    data: {
      transactionId: transactionId
    },
    success: function (data) {

      const accessoriesReceive = data.accessoriesReceive;

      $("#qcTransactionId").val(accessoriesReceive.transactionId);
      $("#accessoriesId").val(accessoriesReceive.accessoriesId);
      $("#accessories").val(accessoriesReceive.accessoriesName);
      $("#grnNo").val(accessoriesReceive.grnNo);
      let date = accessoriesReceive.grnDate.split("/");
      $("#receiveDate").val(date[2] + "-" + date[1] + "-" + date[0]);

      $("#remarks").val(accessoriesReceive.remarks);
      $("#supplier").val(accessoriesReceive.supplierId).change();
      $('#grnSearchModal').modal('hide');
      drawAccessoriesSizeListTable(accessoriesReceive.accessoriesSizeList,false);
      $("#btnSubmit").prop("disabled", false);
      $("#btnEdit").prop("disabled", true);

    }
  });
}

function setAccessoriesQCInfo(qcTransactionId) {
  $.ajax({
    type: 'GET',
    dataType: 'json',
    url: './getAccessoriesQCInfo',
    data: {
      qcTransactionId: qcTransactionId
    },
    success: function (data) {

      const accessoriesQC = data.accessoriesQC;
      console.log(accessoriesQC);
      $("#qcTransactionId").val(accessoriesQC.qcTransactionId);
      $("#accessories").val(accessoriesQC.accessoriesName);
      $("#grnNo").val(accessoriesQC.grnNo);
      let date = accessoriesQC.receiveDate.split("/");
      $("#receiveDate").val(date[2] + "-" + date[1] + "-" + date[0]);

      $("#remarks").val(accessoriesQC.remarks);
      $("#supplier").val(accessoriesQC.supplierId).change();
      $('#qcSearchModal').modal('hide');
      drawAccessoriesSizeListTable(accessoriesQC.accessoriesSizeList,true);
      $("#btnSubmit").prop("disabled", true);
      $("#btnEdit").prop("disabled", false);

    }
  });
}

function submitAction() {
  const rowList = $("#sizeList tr");
  const length = rowList.length;

  const qcTransactionId = $("#qcTransactionId").val();
  const qcDate = $("#qcDate").val();
  const grnNo = $("#grnNo").val();
  const remarks = $("#remarks").val();
  const checkBy = $("#checkBy").val();
  const departmentId = 1;
  const userId = $("#userId").val();

  let sizeList = ""

  for (let i = 0; i < length; i++) {

    const row = rowList[i];
    const id = row.id.slice(6);
    const checked = $("#isCheck-" + id).prop('checked');
    if (checked) {
      const purchaseOrder = row.getAttribute('data-purchase-order');
      const styleId = row.getAttribute('data-style-id');
      const itemId = row.getAttribute('data-item-id');
      const itemColorId = row.getAttribute('data-item-color-id');
      const accessoriesId = row.getAttribute('data-accessories-id');
      const accessoriesColorId = row.getAttribute('data-accessories-color-id');
      const sizeId = row.getAttribute('data-size-id');
      const unitId = row.getAttribute('data-unit-id');
      const unitQty = $("#checkQty-" + id).text().trim() == '' ? "0" : $("#checkQty-" + id).text();
      const qcPassedType = $("#qcPassed-" + id).val();
      const rackName = $("#rackName-" + id).text();
      const binName = $("#binName-" + id).text();

      sizeList += `autoId : ${id},qcTransactionId : ${qcTransactionId},purchaseOrder : ${purchaseOrder},styleId : ${styleId},itemId : ${itemId},itemColorId : ${itemColorId},accessoriesId : ${accessoriesId},accessoriesColorId : ${accessoriesColorId},sizeId : ${sizeId},unitId : ${unitId},unitQty : ${unitQty},rackName : ${rackName},binName : ${binName},qcPassedType : ${qcPassedType},userId : ${userId} #`;
    }
  }

  sizeList = sizeList.slice(0, -1);
  
  if (length > 0) {
    if (qcTransactionId != '') {
      if (grnNo != '') {
        if (confirm("Are you sure to submit this Accessories Quality Control...")) {
          $.ajax({
            type: 'POST',
            dataType: 'json',
            url: './submitAccessoriesQC',
            data: {
              qcTransactionId: qcTransactionId,
              qcDate: qcDate,
              grnNo: grnNo,
              remarks: remarks,
              sizeList: sizeList,
              checkBy: checkBy,
              departmentId : departmentId,
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
        warningAlert("Please Enter GRN No")
        $("#grnNo").focus();
      }
    } else {
      warningAlert("Please Get a transaction Id")
      $("#transactionId").focus();
    }
  } else {
    warningAlert("Please Enter Accessories Size");
  }
}



function editAction() {
  const rowList = $("#sizeList tr");
  const length = rowList.length;

  const qcTransactionId = $("#qcTransactionId").val();
  const qcDate = $("#qcDate").val();
  const grnNo = $("#grnNo").val();
  const remarks = $("#remarks").val();
  const checkBy = $("#checkBy").val();
  const userId = $("#userId").val();

  let sizeList = ""

  for (let i = 0; i < length; i++) {

    const row = rowList[i];
    const id = row.id.slice(6);
    const checked = $("#isCheck-" + id).prop('checked');
    if (checked) {
      const purchaseOrder = row.getAttribute('data-purchase-order');
      const styleId = row.getAttribute('data-style-id');
      const itemId = row.getAttribute('data-item-id');
      const itemColorId = row.getAttribute('data-item-color-id');
      const accessoriesId = row.getAttribute('data-accessories-id');
      const accessoriesColorId = row.getAttribute('data-accessories-color-id');
      const sizeId = row.getAttribute('data-size-id');;
      const unitId = row.getAttribute('data-unit-id');
      const qcPassedQty = $("#qcPassedQty-" + id).text().trim() == '' ? "0" : $("#qcPassedQty-" + id).text();
      const qcPassedType = $("#qcPassed-" + id).val();
      const rackName = $("#rackName-" + id).text();
      const binName = $("#binName-" + id).text();

      sizeList += `autoId : ${id},qcTransactionId : ${qcTransactionId},purchaseOrder : ${purchaseOrder},styleId : ${styleId},itemId : ${itemId},itemColorId : ${itemColorId},accessoriesId : ${accessoriesId},accessoriesColorId : ${accessoriesColorId},sizeId : ${sizeId},unitId : ${unitId},qcPassedQty : ${qcPassedQty},rackName : ${rackName},binName : ${binName},qcPassedType : ${qcPassedType},userId : ${userId} #`;
    }
  }

  sizeList = sizeList.slice(0, -1);
  console.log(sizeList);
  if (length > 0) {
    if (qcTransactionId != '') {
      if (grnNo != '') {
        if (confirm("Are you sure to Edit this Accessories Quality Control...")) {
          $.ajax({
            type: 'POST',
            dataType: 'json',
            url: './editAccessoriesQC',
            data: {
              qcTransactionId: qcTransactionId,
              qcDate: qcDate,
              grnNo: grnNo,
              remarks: remarks,
              sizeList: sizeList,
              userId: userId
            },
            success: function (data) {
              if (data.result == "Something Wrong") {
                dangerAlert("Something went wrong");
              } else if (data.result == "duplicate") {
                dangerAlert("Duplicate Item Name..This Item Name Already Exist")
              } else {
                alert("Successfully Edit...");
                refreshAction();
              }
            }
          });
        }
      } else {
        warningAlert("Please Enter GRN No")
        $("#grnNo").focus();
      }
    } else {
      warningAlert("Please Get a transaction Id")
      $("#transactionId").focus();
    }
  } else {
    warningAlert("Please Enter Accessories Size");
  }
}

function refreshAction() {
  location.reload();

}


function drawAccessoriesReceiveListTable(data) {
  let rows = [];
  const length = data.length;

  for (var i = 0; i < length; i++) {
    const rowData = data[i];
    let row = $("<tr/>")
    row.append($("<td>" + rowData.transactionId + "</td>"));
    row.append($("<td>" + rowData.grnNo + "</td>"));
    row.append($("<td>" + rowData.grnDate + "</td>"));
    row.append($("<td ><i class='fa fa-search' onclick=\"setAccessoriesReceiveInfo('" + rowData.transactionId + "')\" style='cursor:pointer;'> </i></td>"));

    rows.push(row);
  }

  return rows;
}

function drawAccessoriesSizeListTable(data,isSearch) {
  const length = data.length;
  let rows = "";
  $("#sizeList").empty();
  let options = "<option  id='qcPassed-1' value='1'>QC Passed</option><option  id='qcPassed-1' value='2'>QC Failed</option>";
  for (var i = 0; i < length; i++) {

    const rowData = data[i];
    const id = rowData.autoId;
    rows += "<tr id='rowId-" + id + "' data-purchase-order='" + rowData.purchaseOrder + "' data-style-id='" + rowData.styleId + "' data-item-id='" + rowData.itemId + "' data-item-color-id='" + rowData.itemColorId + "' data-accessories-id='" + rowData.accessoriesId + "' data-accessories-color-id='" + rowData.accessoriesColorId + "' data-size-id='"+rowData.sizeId+"' data-unit-id='" + rowData.unitId + "'>"
      + "<td id='sizeName-" + id + "'>" + rowData.sizeName + "</td>"
      + "<td id='unitQty-" + id + "'>" + rowData.unitQty + "</td>"
      + "<td id='qcPassedQty-" + rowData.autoId + "'>"+rowData.qcPassedQty+"</td>"
      + "<td id='checkQty-" + rowData.autoId + "'  contenteditable = 'true'>" + (isSearch? rowData.unitQty : (rowData.unitQty - rowData.qcPassedQty)) + "</td>"
      + "<td id='unit-" + id + "'>" + rowData.unit + "</td>"
      + "<td id='shade-" + id + "'>0</td>"
      + "<td id='shrinkage-" + id + "'>0</td>"
      + "<td id='gsm-" + id + "'>0</td>"
      + "<td id='width-" + id + "'>0</td>"
      + "<td id='defect-" + id + "'>0</td>"
      + "<td id='rackName-" + id + "'>" + rowData.rackName + "</td>"
      + "<td id='binName-" + id + "'>" + rowData.binName + "</td>"
      + "<td><select id='qcPassed-" + rowData.autoId + "' class='form-control-sm px-0 bg-success' onchange='qcPassedChangeBackground(this)'>" + options + "</select></td>"
      + "<td class='text-center align-middle'><input class='check' id='isCheck-" + rowData.autoId + "' type='checkbox'></td>"
      + "</tr>";
  }
  $("#sizeList").html(rows);
  for (var i = 0; i < length; i++) {
    const rowData = data[i];
    var element = document.getElementById("qcPassed-" + rowData.autoId);
    if (rowData.qcPassedType == 1) {

      element.classList.remove('bg-danger');
      element.classList.add('bg-success');
    } else {
      element.classList.remove('bg-success');
      element.classList.add('bg-danger');
    }
    element.value = rowData.qcPassedType;
  }
}



function drawAccessoriesQCListTable(data) {
  const length = data.length;
  var tr_list = "";
  $("#accessoriesQCList").empty();
  var options = "<option value='1'>QC Passed</option><option value='2'>QC Failed</option>";
  for (var i = 0; i < length; i++) {
    const rowData = data[i];
    tr_list = tr_list + "<tr id='row-" + rowData.autoId + "'>"
      + "<td>" + rowData.qcTransactionId + "</td>"
      + "<td>" + rowData.qcDate + "</td>"
      + "<td>" + rowData.grnNo + "</td>"
      + "<td ><i class='fa fa-search' onclick=\"setAccessoriesQCInfo('" + rowData.qcTransactionId + "')\" style='cursor:pointer;'> </i></td>"
      + "</tr>";
  }
  $("#accessoriesQCList").html(tr_list);
}
function qcPassedChangeBackground(element) {
  if (element.value == 1) {
    element.classList.remove('bg-danger');
    element.classList.add('bg-success');
  } else {
    element.classList.remove('bg-success');
    element.classList.add('bg-danger');
  }
}

document.getElementById("allCheck").addEventListener("click", function () {
  if ($(this).prop('checked')) {
    $(".check").prop('checked', true);
  } else {
    $(".check").prop('checked', false);
  }

});

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
$(document).ready(function () {
  $("#search").on("keyup", function () {
    var value = $(this).val().toLowerCase();
    $("#poList tr").filter(function () {
      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
    });
  });
});


var today = new Date();
document.getElementById("qcDate").value = today.getFullYear() + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + ('0' + today.getDate()).slice(-2);
