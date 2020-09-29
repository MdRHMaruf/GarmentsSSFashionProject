
function saveAction() {
  var processName = $("#processName").val().trim();
  var userId = $("#userId").val();

  if (processName != '') {
    $.ajax({
      type: 'POST',
      dataType: 'json',
      url: './saveProcess',
      data: {
    	processId: "0",
        processName: processName,
        userId: userId
      },
      success: function (data) {
        if (data.result == "Something Wrong") {
          dangerAlert("Something went wrong");
        } else if (data.result == "duplicate") {
          dangerAlert("Duplicate Process Name..This Process Name Allready Exist")
        } else {
          successAlert("Process Name Save Successfully");
          refreshAction(); 
          $("#dataList").empty();
          $("#dataList").append(drawDataTable(data.result));

        }
      }
    });
  } else {
    warningAlert("Empty Process Name... Please Enter Process Name");
  }
}


function editAction() {
  var processId = $("#processId").val();
  var processName = $("#processName").val().trim();
  var userId = $("#userId").val();

  if (processName != '') {
    $.ajax({
      type: 'POST',
      dataType: 'json',
      url: './editProcess',
      data: {
    	processId: processId,
    	processName: processName,
        userId: userId
      },
      success: function (data) {
        if (data.result == "Something Wrong") {
          dangerAlert("Something went wrong");
        } else if (data.result == "duplicate") {
          dangerAlert("Duplicate Process Name..This Process Name Allreary Exist")
        } else {
          successAlert("Process Edit Successfully");
          refreshAction(); 
          $("#dataList").empty();
          $("#dataList").append(drawDataTable(data.result));
          

        }
      }
    });
  } else {
    warningAlert("Empty Sample Type Name... Please Enter Sample Type Name");
  }
}


function refreshAction() {
  location.reload();
  /*var element = $(".alert");
  element.hide();
  document.getElementById("sampleTypeId").value = "0";
  document.getElementById("sampleTypeName").value = "";
  document.getElementById("btnSave").disabled = false;
  document.getElementById("btnEdit").disabled = true;*/
}


function setData(processId) {


  document.getElementById("processId").value = processId;
  document.getElementById("processName").value = document.getElementById("processName" + processId).innerHTML;
  document.getElementById("btnSave").disabled = true;
  document.getElementById("btnEdit").disabled = false;

}

function drawDataTable(data) {
  var rows = [];
  var length = data.length;

  for (var i = 0; i < length; i++) {
    rows.push(drawRowDataTable(data[i], i+1));
  }

  return rows;
}

function drawRowDataTable(rowData, c) {

  var row = $("<tr />")
  row.append($("<td>" + c + "</td>"));
  row.append($("<td id='sampleTypeName" + rowData.ProcessId + "'>" + rowData.Name + "</td>"));
  row.append($("<td ><i class='fa fa-edit' onclick=\"setData(" + rowData.ProcessId + ")\"> </i></td>"));

  return row;
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
  document.getElementById("warningAlert").innerHTML = "<strong>Warning!</strong> "+message+"..";
  element.show();
}

function dangerAlert(message) {
  var element = $(".alert");
  element.hide();
  element = $(".alert-danger");
  document.getElementById("dangerAlert").innerHTML = "<strong>Duplicate!</strong> "+message+"..";
  element.show();
}

$(document).ready(function () {
  $("input:text").focus(function () { $(this).select(); });
});

$(document).ready(function () {
  $("#search").on("keyup", function () {
    var value = $(this).val().toLowerCase();
    $("#dataList tr").filter(function () {
      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
    });
  });
});

