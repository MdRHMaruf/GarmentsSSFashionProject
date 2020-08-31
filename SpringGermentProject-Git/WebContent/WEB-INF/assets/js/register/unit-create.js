



function saveAction() {
  var unitName = $("#unitName").val().trim();
  var unitValue = $("#unitValue").val().trim();
  var userId = $("#userId").val();

  if (unitName != '') {
    $.ajax({
      type: 'POST',
      dataType: 'json',
      url: './saveUnit',
      data: {
        unitId: "0",
        unitName: unitName,
        unitValue: unitValue,
        userId: userId
      },
      success: function (data) {
        if (data.result == "Something Wrong") {
          dangerAlert("Something went wrong");
        } else if (data.result == "duplicate") {
          dangerAlert("Duplicate Unit Name..This Unit Name Allreary Exist")
        } else {
          successAlert("Unit Name Save Successfully");

          $("#dataList").empty();
          $("#dataList").append(drawDataTable(data.result));

        }
      }
    });
  } else {
    warningAlert("Empty Unit Name... Please Enter Unit Name");
  }
}


function editAction() {
  var unitId = $("#unitId").val();
  var unitName = $("#unitName").val().trim();
  var unitValue = $("#unitValue").val().trim();
  var userId = $("#userId").val();

  if (unitName != '') {
    $.ajax({
      type: 'POST',
      dataType: 'json',
      url: './editUnit',
      data: {
        unitId: unitId,
        unitName: unitName,
        unitValue: unitValue,
        userId: userId
      },
      success: function (data) {
        if (data.result == "Something Wrong") {
          dangerAlert("Something went wrong");
        } else if (data.result == "duplicate") {
          dangerAlert("Duplicate Unit Name..This Unit Name Allreary Exist")
        } else {
          successAlert("Unit Name Edit Successfully");

          $("#dataList").empty();
          $("#dataList").append(drawDataTable(data.result));

        }
      }
    });
  } else {
    warningAlert("Empty Unit Name... Please Enter Unit Name");
  }
}


function refreshAction() {
  location.reload();
  /*var element = $(".alert");
  element.hide();
  document.getElementById("unitId").value = "0";
  document.getElementById("unitName").value = "";
  document.getElementById("unitValue").value = "";
  document.getElementById("btnSave").disabled = false;
  document.getElementById("btnEdit").disabled = true;*/
}


function setData(unitId) {


  document.getElementById("unitId").value = unitId;
  document.getElementById("unitName").value = document.getElementById("unitName" + unitId).innerHTML;
  document.getElementById("unitValue").value = document.getElementById("unitValue" + unitId).innerHTML;
  document.getElementById("btnSave").disabled = true;
  document.getElementById("btnEdit").disabled = false;

}

function drawDataTable(data) {
  var rows = [];
  var length = data.length;

  for (var i = 0; i < length; i++) {
    rows.push(drawRowDataTable(data[i], i));
  }

  return rows;
}

function drawRowDataTable(rowData, c) {

  var row = $("<tr />")
  row.append($("<td>" + rowData.unitId + "</td>"));
  row.append($("<td id='unitName" + rowData.unitId + "'>" + rowData.unitName + "</td>"));
  row.append($("<td id='unitValue" + rowData.unitId + "'>" + rowData.unitValue + "</td>"));
  row.append($("<td ><i class='fa fa-edit' onclick=\"setData(" + rowData.unitId + ")\"> </i></td>"));

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

