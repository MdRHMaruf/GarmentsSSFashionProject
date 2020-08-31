



function saveAction() {
  var fabricsItemName = $("#fabricsItemName").val();
  var reference = $("#reference").val();
  var userId = $("#userId").val();

  if (fabricsItemName != '') {
    $.ajax({
      type: 'POST',
      dataType: 'json',
      url: './saveFabricsItem',
      data: {
        fabricsItemId: "0",
        fabricsItemName: fabricsItemName,
        reference: reference,
        userId: userId
      },
      success: function (data) {
        if (data.result == "Something Wrong") {
          dangerAlert("Something went wrong");
        } else if (data.result == "duplicate") {
          dangerAlert("Duplicate Fabrics Item Name..This Fabrics Item Name Allreary Exist")
        } else {
          successAlert("Fabrics Item Name Save Successfully");

          $("#dataList").empty();
          $("#dataList").append(drawDataTable(data.result));

        }
      }
    });
  } else {
    warningAlert("Empty Fabrics Item Name... Please Enter Fabrics Item Name");
  }
}


function editAction() {
  var fabricsItemId = $("#fabricsItemId").val();
  var fabricsItemName = $("#fabricsItemName").val();
  var reference = $("#reference").val();
  var userId = $("#userId").val();

  if (fabricsItemName != '') {
    $.ajax({
      type: 'POST',
      dataType: 'json',
      url: './editFabricsItem',
      data: {
        fabricsItemId: fabricsItemId,
        fabricsItemName: fabricsItemName,
        reference: reference,
        userId: userId
      },
      success: function (data) {
        if (data.result == "Something Wrong") {
          dangerAlert("Something went wrong");
        } else if (data.result == "duplicate") {
          dangerAlert("Duplicate Fabrics Item Name..This Fabrics Item Name Allreary Exist")
        } else {
          successAlert("Fabrics Item Name Edit Successfully");

          $("#dataList").empty();
          $("#dataList").append(drawDataTable(data.result));

        }
      }
    });
  } else {
    warningAlert("Empty Fabrics Item Name... Please Enter Fabrics Item Name");
  }
}


function refreshAction() {
  location.reload();
  /*var element = $(".alert");
  element.hide();
  document.getElementById("fabricsItemId").value = "0";
  document.getElementById("fabricsItemName").value = "";
  document.getElementById("reference").value = "";
  document.getElementById("btnSave").disabled = false;
  document.getElementById("btnEdit").disabled = true;*/
}


function setData(fabricsItemId) {


  document.getElementById("fabricsItemId").value = fabricsItemId;
  document.getElementById("fabricsItemName").value = document.getElementById("fabricsItemName" + fabricsItemId).innerHTML;
  document.getElementById("reference").value = document.getElementById("reference" + fabricsItemId).innerHTML;
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
  row.append($("<td>" + rowData.fabricsItemId + "</td>"));
  row.append($("<td id='fabricsItemName" + rowData.fabricsItemId + "'>" + rowData.fabricsItemName + "</td>"));
  row.append($("<td id='reference" + rowData.fabricsItemId + "'>" + rowData.reference + "</td>"));
  row.append($("<td ><i class='fa fa-edit' onclick=\"setData(" + rowData.fabricsItemId + ")\"> </i></td>"));

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

