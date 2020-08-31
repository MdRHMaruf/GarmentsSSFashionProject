



function saveAction() {
  var particularItemName = $("#particularItemName").val().trim();
  var userId = $("#userId").val();

  if (particularItemName != '') {
    $.ajax({
      type: 'POST',
      dataType: 'json',
      url: './saveParticularItem',
      data: {
        particularItemId: "0",
        particularItemName: particularItemName,
        userId: userId
      },
      success: function (data) {
        if (data.result == "Something Wrong") {
          dangerAlert("Something went wrong");
        } else if (data.result == "duplicate") {
          dangerAlert("Duplicate Particular Item Name..This Particular Item Name Allreary Exist")
        } else {
          successAlert("Particular Item Name Save Successfully");

          $("#dataList").empty();
          $("#dataList").append(drawDataTable(data.result));

        }
      }
    });
  } else {
    warningAlert("Empty Particular Item Name... Please Enter Particular Item Name");
  }
}


function editAction() {
  var particularItemId = $("#particularItemId").val();
  var particularItemName = $("#particularItemName").val().trim();
  var userId = $("#userId").val();

  if (particularItemName != '') {
    $.ajax({
      type: 'POST',
      dataType: 'json',
      url: './editParticularItem',
      data: {
        particularItemId: particularItemId,
        particularItemName: particularItemName,
        userId: userId
      },
      success: function (data) {
        if (data.result == "Something Wrong") {
          dangerAlert("Something went wrong");
        } else if (data.result == "duplicate") {
          dangerAlert("Duplicate Particular Item Name..This Particular Item Name Allreary Exist")
        } else {
          successAlert("Particular Item Name Edit Successfully");

          $("#dataList").empty();
          $("#dataList").append(drawDataTable(data.result));

        }
      }
    });
  } else {
    warningAlert("Empty Particular Item Name... Please Enter Particular Item Name");
  }
}


function refreshAction() {
  location.reload();
  /*var element = $(".alert");
  element.hide();
  document.getElementById("particularItemId").value = "0";
  document.getElementById("particularItemName").value = "";
  document.getElementById("btnSave").disabled = false;
  document.getElementById("btnEdit").disabled = true;*/
}


function setData(particularItemId) {


  document.getElementById("particularItemId").value = particularItemId;
  document.getElementById("particularItemName").value = document.getElementById("particularItemName" + particularItemId).innerHTML;
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
  row.append($("<td>" + rowData.particularItemId + "</td>"));
  row.append($("<td id='particularItemName" + rowData.particularItemId + "'>" + rowData.particularItemName + "</td>"));
  row.append($("<td ><i class='fa fa-edit' onclick=\"setData(" + rowData.particularItemId + ")\"> </i></td>"));

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

