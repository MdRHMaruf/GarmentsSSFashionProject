
$("#newFabricsQCBtn").click(function () {

  $("#qcTransectionId").val("-- New Transection --");
  $("#btnSubmit").prop("disabled", false);
  $("#btnEdit").prop("disabled", true);
});

$("#findFabricsQCBtn").click(function () {
  $.ajax({
    type: 'GET',
    dataType: 'json',
    url: './getFabricsQCList',
    data: {},
    success: function (data) {

      console.log(data);
      drawFabricsQCListTable(data.fabricsQCList);
    }
  });
});

$("#grnSearchBtn").click(function () {
  $.ajax({
    type: 'GET',
    dataType: 'json',
    url: './getFabricsReceiveList',
    data: {},
    success: function (data) {

      $("#fabricsReceiveList").empty();
      $("#fabricsReceiveList").append(drawFabricsReceiveListTable(data.fabricsReceiveList));

    }
  });
});

function setFabricsReceiveInfo(transectionId) {
  $.ajax({
    type: 'GET',
    dataType: 'json',
    url: './getFabricsReceiveInfo',
    data: {
      transectionId: transectionId
    },
    success: function (data) {

      const fabricsReceive = data.fabricsReceive;
      $("#qcTransectionId").val(fabricsReceive.transectionId);
      $("#indentId").val(fabricsReceive.indentId);
      $("#fabricsId").val(fabricsReceive.fabricsId);
      $("#fabrics").val(fabricsReceive.fabricsName);
      $("#grnNo").val(fabricsReceive.grnNo);
      let date = fabricsReceive.grnDate.split("/");
      $("#receiveDate").val(date[2] + "-" + date[1] + "-" + date[0]);
      
      $("#remarks").val(fabricsReceive.remarks);
      $("#supplier").val(fabricsReceive.supplierId).change();
      $('#grnSearchModal').modal('hide');
      drawFabricsRollListTable(fabricsReceive.fabricsRollList);
      $("#btnSubmit").prop("disabled", true);
      $("#btnEdit").prop("disabled", false);

    }
  });
}

function setFabricsQCInfo(qcTransectionId) {
  $.ajax({
    type: 'GET',
    dataType: 'json',
    url: './getFabricsQCInfo',
    data: {
      qcTransectionId: qcTransectionId
    },
    success: function (data) {

      const fabricsQC = data.fabricsQC;
      console.log(fabricsQC);
      $("#qcTransectionId").val(fabricsQC.qcTransectionId);
      $("#fabrics").val(fabricsQC.fabricsName);
      $("#grnNo").val(fabricsQC.grnNo);
      let date = fabricsQC.receiveDate.split("/");
      $("#receiveDate").val(date[2] + "-" + date[1] + "-" + date[0]);
      
      $("#remarks").val(fabricsQC.remarks);
      $("#supplier").val(fabricsQC.supplierId).change();
      $('#qcSearchModal').modal('hide');
      drawFabricsRollListTable(fabricsQC.fabricsRollList);
      $("#btnSubmit").prop("disabled", true);
      $("#btnEdit").prop("disabled", false);

    }
  });
}

function submitAction() {
  const rowList = $("#rollList tr");
  const length = rowList.length;

  const transectionId = $("#qcTransectionId").val();
  const qcDate = $("#qcDate").val();
  const grnNo = $("#grnNo").val();
  const remarks = $("#remarks").val();
  const userId = $("#userId").val();

  let rollList = ""

  for (let i = 0; i < length; i++) {

    const id = rowList[i].id.slice(4);
    const qcPassedQty = $("#qcPassedQty-" + id).text().trim() == '' ? "0" : $("#qcPassedQty-" + id).text();
    const qcPassedType = $("#qcPassed-" + id).val();

    rollList += `autoId : ${id},qcTransectionId : ${transectionId},qcPassedQty : ${qcPassedQty},qcPassedType : ${qcPassedType},userId : ${userId} #`;
  }

  rollList = rollList.slice(0, -1);
  console.log(rollList);
  if (length > 0) {
    if (transectionId != '') {
      if(grnNo != ''){
          if (confirm("Are you sure to submit this Fabrics Quality Control...")) {
            $.ajax({
              type: 'POST',
              dataType: 'json',
              url: './submitFabricsQC',
              data: {
                transectionId: transectionId,
                qcDate : qcDate,
                grnNo: grnNo,
                remarks : remarks,
                rollList: rollList,
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
      }else{
        warningAlert("Please Enter GRN No")
        $("#grnNo").focus();
      }
    } else {
      warningAlert("Please Get a transection Id")
      $("#transectionId").focus();
    }
  } else {
    warningAlert("Please Enter Fabrics Roll");
  }
}



function editAction() {
  const rowList = $("#rollList tr");
  const length = rowList.length;

  const transectionId = $("#qcTransectionId").val();
  const qcDate = $("#qcDate").val();
  const grnNo = $("#grnNo").val();
  const remarks = $("#remarks").val();
  const userId = $("#userId").val();

  let rollList = ""

  for (let i = 0; i < length; i++) {

    const id = rowList[i].id.slice(4);
    const qcPassedQty = $("#qcPassedQty-" + id).text().trim() == '' ? "0" : $("#qcPassedQty-" + id).text();
    const qcPassedType = $("#qcPassed-" + id).val();

    rollList += `autoId : ${id},qcTransectionId : ${transectionId},qcPassedQty : ${qcPassedQty},qcPassedType : ${qcPassedType},userId : ${userId} #`;
  }

  rollList = rollList.slice(0, -1);
  console.log(rollList);
  if (length > 0) {
    if (transectionId != '') {
      if(grnNo != ''){
          if (confirm("Are you sure to Edit this Fabrics Quality Control...")) {
            $.ajax({
              type: 'POST',
              dataType: 'json',
              url: './editFabricsQC',
              data: {
                transectionId: transectionId,
                qcDate : qcDate,
                grnNo: grnNo,
                remarks : remarks,
                rollList: rollList,
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
      }else{
        warningAlert("Please Enter GRN No")
        $("#grnNo").focus();
      }
    } else {
      warningAlert("Please Get a transection Id")
      $("#transectionId").focus();
    }
  } else {
    warningAlert("Please Enter Fabrics Roll");
  }
}

function refreshAction() {
  location.reload();

}


function drawFabricsReceiveListTable(data) {
  let rows = [];
  const length = data.length;

  for (var i = 0; i < length; i++) {
    const rowData = data[i];
    let row = $("<tr/>")
    row.append($("<td>" + rowData.transectionId + "</td>"));
    row.append($("<td>" + rowData.grnNo + "</td>"));
    row.append($("<td>" + rowData.grnDate + "</td>"));
    row.append($("<td>" + rowData.grnQty + "</td>"));
    row.append($("<td>" + rowData.noOfRoll + "</td>"));
    row.append($("<td ><i class='fa fa-search' onclick=\"setFabricsReceiveInfo('" + rowData.transectionId + "')\" style='cursor:pointer;'> </i></td>"));
    
    rows.push(row);
  }

  return rows;
}

function drawFabricsRollListTable(data){
    const length = data.length;
    let tr_list="";
    $("#rollList").empty();
    let options="<option  id='qcPassed-1' value='1'>QC Passed</option><option  id='qcPassed-1' value='2'>QC Failed</option>";
    for (var i = 0; i < length; i++) {
      
      const rowData = data[i];
      tr_list=tr_list+"<tr id='row-" + rowData.autoId + "'>"
                +"<td id='rollId-" + i + "' contenteditable = 'true'>" + rowData.rollId + "</td>"
                +"<td id='supplierRollId-" + i + "'>" + rowData.supplierRollId + "</td>"
                +"<td id='rollQty-" + i + "'>" + rowData.rollQty + "</td>"
                +"<td id='rollQty-" + i + "'>" + rowData.unit + "</td>"
                +"<td id='rate-" + i + "'> " + rowData.rate + "</td>"
                +"<td id='qcPassedQty-" + rowData.autoId + "'  contenteditable = 'true'>"+rowData.rollQty+"</td>"
                +"<td id='shade-" + i + "'>0</td>"
                +"<td id='shrinkage-" + i + "'>0</td>"
                +"<td id='gsm-" + i + "'>0</td>"
                +"<td id='width-" + i + "'>0</td>"
                +"<td id='defect-" + i + "'>0</td>"
                +"<td id='rackName-" + i + "'>"+rowData.rackName+"</td>"
                +"<td id='binName-" + i + "'>"+rowData.binName+"</td>"
                +"<td><select id='qcPassed-" + rowData.autoId + "' class='form-control-sm px-0 bg-success' onchange='qcPassedChangeBackground(this)'>"+options+"</select></td>"
              +"</tr>";
    }
    $("#rollList").html(tr_list);

    for(var i = 0;i<length;i++){
      const rowData = data[i];
      console.log("qcPassed="+rowData.qcPassedType);
      var element = document.getElementById("qcPassed-"+rowData.autoId);
      if(rowData.qcPassedType == 1){
        
        element.classList.remove('bg-danger');
        element.classList.add('bg-success');
      }else{
        element.classList.remove('bg-success');
        element.classList.add('bg-danger');
      }
      element.value = rowData.qcPassedType;
    }
}

function drawFabricsQCListTable(data){
  const length = data.length;
  var tr_list="";
  $("#fabricsQCList").empty();
  var options="<option value='1'>QC Passed</option><option value='2'>QC Failed</option>";
  for (var i = 0; i < length; i++) {
    const rowData = data[i];
    tr_list=tr_list+"<tr id='row-" + rowData.autoId + "'>"
              +"<td>" + rowData.qcTransectionId + "</td>"
              +"<td>" + rowData.qcDate + "</td>"
              +"<td>" + rowData.grnNo + "</td>"
              +"<td>" + rowData.fabricsName + "</td>"
              +"<td ><i class='fa fa-search' onclick=\"setFabricsQCInfo('" + rowData.qcTransectionId + "')\" style='cursor:pointer;'> </i></td>"
            +"</tr>";
  }
  $("#fabricsQCList").html(tr_list);
}

function qcPassedChangeBackground(element){
  if(element.value==1){
    element.classList.remove('bg-danger');
    element.classList.add('bg-success');
  }else{
    element.classList.remove('bg-success');
    element.classList.add('bg-danger');
  }
}

function successAlert(message) {
  var element = $(".alert");
  element.hide();
  element = $(".alert-success");
  document.getElementById("successAlert").innerHTML = "<strong>Success!</strong> " + message + "...";
  element.show();
}

function warningAlert(message) {
  var element = $(".alert");
  element.hide();
  element = $(".alert-warning");
  document.getElementById("warningAlert").innerHTML = "<strong>Warning!</strong> " + message + "..";
  element.show();
}

function dangerAlert(message) {
  var element = $(".alert");
  element.hide();
  element = $(".alert-danger");
  document.getElementById("dangerAlert").innerHTML = "<strong>Duplicate!</strong> " + message + "..";
  element.show();
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
