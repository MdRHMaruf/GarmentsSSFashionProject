



function saveAction() {
  var accessoriesItemName = $("#accessoriesItemName").val().trim();
  var accessoriesItemCode = $("#accessoriesItemCode").val().trim();
  var userId = $("#userId").val();

  if (accessoriesItemName != '') {
    $.ajax({
      type: 'POST',
      dataType: 'json',
      url: './saveAccessoriesItem',
      data: {
        accessoriesItemId: "0",
        accessoriesItemName: accessoriesItemName,
        accessoriesItemCode: accessoriesItemCode,
        userId: userId
      },
      success: function (data) {
        if (data.result == "Something Wrong") {
          dangerAlert("Something went wrong");
        } else if (data.result == "duplicate") {
          dangerAlert("Duplicate Accessories Item Name..This Accessories Item Name Allreary Exist")
        } else {
          successAlert("AccessoriesItem Name Save Successfully");

          $("#dataList").empty();
          $("#dataList").append(drawDataTable(data.result));

        }
      }
    });
  } else {
    warningAlert("Empty Accessories Item Name... Please Enter Accessories Item Name");
  }
}


function editAction() {
  var accessoriesItemId = $("#accessoriesItemId").val();
  var accessoriesItemName = $("#accessoriesItemName").val().trim();
  var accessoriesItemCode = $("#accessoriesItemCode").val().trim();
  var userId = $("#userId").val();

  if (accessoriesItemName != '') {
    $.ajax({
      type: 'POST',
      dataType: 'json',
      url: './editAccessoriesItem',
      data: {
        accessoriesItemId: accessoriesItemId,
        accessoriesItemName: accessoriesItemName,
        accessoriesItemCode: accessoriesItemCode,
        userId: userId
      },
      success: function (data) {
        if (data.result == "Something Wrong") {
          dangerAlert("Something went wrong");
        } else if (data.result == "duplicate") {
          dangerAlert("Duplicate Accessories Item Name..This Accessories Item Name Allreary Exist")
        } else {
          successAlert("Accessories Item Name Edit Successfully");

          $("#dataList").empty();
          $("#dataList").append(drawDataTable(data.result));

        }
      }
    });
  } else {
    warningAlert("Empty Accessories Item Name... Please Enter Accessories Item Name");
  }
}


function refreshAction() {
  location.reload();
  /*var element = $(".alert");
  element.hide();
  document.getElementById("accessoriesItemId").value = "0";
  document.getElementById("accessoriesItemName").value = "";
  document.getElementById("accessoriesItemCode").value = "";
  document.getElementById("btnSave").disabled = false;
  document.getElementById("btnEdit").disabled = true;*/
}


function setData(accessoriesItemId) {


  document.getElementById("accessoriesItemId").value = accessoriesItemId;
  document.getElementById("accessoriesItemName").value = document.getElementById("accessoriesItemName" + accessoriesItemId).innerHTML;
  document.getElementById("accessoriesItemCode").value = document.getElementById("accessoriesItemCode" + accessoriesItemId).innerHTML;
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
  row.append($("<td>" + rowData.accessoriesItemId + "</td>"));
  row.append($("<td id='accessoriesItemName" + rowData.accessoriesItemId + "'>" + rowData.accessoriesItemName + "</td>"));
  row.append($("<td id='accessoriesItemCode" + rowData.accessoriesItemId + "'>" + rowData.accessoriesItemCode + "</td>"));
  row.append($("<td ><i class='fa fa-edit' onclick=\"setData(" + rowData.accessoriesItemId + ")\"> </i></td>"));

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

