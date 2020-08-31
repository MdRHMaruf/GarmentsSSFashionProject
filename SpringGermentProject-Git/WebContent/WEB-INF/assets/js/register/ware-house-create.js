



function saveAction() {
  var factoryId = $("#factoryName").val().trim();
  var wareHouseName = $("#wareHouseName").val().trim();
  var userId = $("#userId").val();

  if (factoryId != '0') {
    if (wareHouseName != '') {
      $.ajax({
        type: 'POST',
        dataType: 'json',
        url: './saveWareHouse',
        data: {
          wareHouseId: "0",
          factoryId: factoryId,
          wareHouseName: wareHouseName,
          userId: userId
        },
        success: function (data) {
          if (data.result == "Something Wrong") {
            dangerAlert("Something went wrong");
          } else if (data.result == "duplicate") {
            dangerAlert("Duplicate Ware House Name..This Ware House Name Allreary Exist")
          } else {
            successAlert("Ware House Name Save Successfully");

            $("#dataList").empty();
            $("#dataList").append(drawDataTable(data.result));

          }
        }
      });
    } else {
      warningAlert("Empty Ware House Name... Please Enter Ware House Name");
    }
  } else {
    warningAlert("Empty Factory Name... Please Select a Factory Name");
  }
}


function editAction() {
  var wareHouseId = $("#wareHouseId").val();
  var factoryId = $("#factoryName").val().trim();
  var wareHouseName = $("#wareHouseName").val().trim();
  var userId = $("#userId").val();

  if (factoryId != '0') {
    if (wareHouseName != '') {

      $.ajax({
        type: 'POST',
        dataType: 'json',
        url: './editWareHouse',
        data: {
          wareHouseId: wareHouseId,
          factoryId: factoryId,
          wareHouseName: wareHouseName,
          userId: userId
        },
        success: function (data) {
          if (data.result == "Something Wrong") {
            dangerAlert("Something went wrong");
          } else if (data.result == "duplicate") {
            dangerAlert("Duplicate Ware House Name..This Ware House Name Allreary Exist")
          } else {
            successAlert("Ware House Name Edit Successfully");

            $("#dataList").empty();
            $("#dataList").append(drawDataTable(data.result));

          }
        }
      });
    } else {
      warningAlert("Empty Ware House Name... Please Enter Ware House Name");
    }
  } else {
    warningAlert("Empty Factory Name... Please Select a Factory Name");
  }
}


function refreshAction() {
  location.reload();
  /*var element = $(".alert");
  element.hide();
  document.getElementById("wareHouseId").value = "0";
  document.getElementById("factoryName").value = "";
  document.getElementById("wareHouseName").value = "";
  document.getElementById("btnSave").disabled = false;
  document.getElementById("btnEdit").disabled = true;*/
}


function setData(wareHouseId,factoryId) {


  document.getElementById("wareHouseId").value = wareHouseId;
  document.getElementById("factoryName").value = factoryId;
  document.getElementById("wareHouseName").value = document.getElementById("wareHouseName" + wareHouseId).innerHTML;
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
  row.append($("<td>" + rowData.wareHouseId + "</td>"));
  row.append($("<td id='factoryName" + rowData.wareHouseId + "'>" + rowData.factoryName + "</td>"));
  row.append($("<td id='wareHouseName" + rowData.wareHouseId + "'>" + rowData.wareHouseName + "</td>"));
  row.append($("<td ><i class='fa fa-edit' onclick=\"setData(" + rowData.wareHouseId + "," + rowData.factoryId + ")\"> </i></td>"));

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
  $("#search").on("keyup", function () {
    var value = $(this).val().toLowerCase();
    $("#dataList tr").filter(function () {
      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
    });
  });
});

