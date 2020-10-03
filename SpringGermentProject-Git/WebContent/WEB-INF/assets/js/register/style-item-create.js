



function saveAction() {
  var styleItemName = $("#styleItemName").val().trim();
  var styleItemCode = $("#styleItemCode").val().trim();
  var userId = $("#userId").val();

  if (styleItemName != '') {
    $.ajax({
      type: 'POST',
      dataType: 'json',
      url: './saveStyleItem',
      data: {
        styleItemId: "0",
        styleItemName: styleItemName,
        styleItemCode: styleItemCode,
        userId: userId
      },
      success: function (data) {
        if (data.result == "Something Wrong") {
          dangerAlert("Something went wrong");
        } else if (data.result == "duplicate") {
          dangerAlert("Duplicate Style Item Name..This Style Item Name Allreary Exist")
        } else {
          successAlert("Style Item Name Save Successfully");

          $("#dataList").empty();
          $("#dataList").append(drawDataTable(data.result));

        }
      }
    });
  } else {
    warningAlert("Empty Style Item Name... Please Enter Style Item Name");
  }
}


function editAction() {
  var styleItemId = $("#styleItemId").val();
  var styleItemName = $("#styleItemName").val().trim();
  var styleItemCode = $("#styleItemCode").val().trim();
  var userId = $("#userId").val();

  if (styleItemName != '') {
    $.ajax({
      type: 'POST',
      dataType: 'json',
      url: './editStyleItem',
      data: {
        styleItemId: styleItemId,
        styleItemName: styleItemName,
        styleItemCode: styleItemCode,
        userId: userId
      },
      success: function (data) {
        if (data.result == "Something Wrong") {
          dangerAlert("Something went wrong");
        } else if (data.result == "duplicate") {
          dangerAlert("Duplicate Style Item Name..This Style Item Name Allreary Exist")
        } else {
          successAlert("Style Item Name Edit Successfully");

          $("#dataList").empty();
          $("#dataList").append(drawDataTable(data.result));

        }
      }
    });
  } else {
    warningAlert("Empty Style Item Name... Please Enter Style Item Name");
  }
}


function refreshAction() {
  location.reload();
  /*var element = $(".alert");
  element.hide();
  document.getElementById("styleItemId").value = "0";
  document.getElementById("styleItemName").value = "";
  document.getElementById("styleItemCode").value = "";
  document.getElementById("btnSave").disabled = false;
  document.getElementById("btnEdit").disabled = true;*/
}


function setData(styleItemId) {


  document.getElementById("styleItemId").value = styleItemId;
  document.getElementById("styleItemName").value = document.getElementById("styleItemName" + styleItemId).innerHTML;
  document.getElementById("styleItemCode").value = document.getElementById("styleItemCode" + styleItemId).innerHTML;
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
  row.append($("<td>" + rowData.styleItemId + "</td>"));
  row.append($("<td id='styleItemName" + rowData.styleItemId + "'>" + rowData.styleItemName + "</td>"));
  row.append($("<td id='styleItemCode" + rowData.styleItemId + "'>" + rowData.styleItemCode + "</td>"));
  row.append($("<td ><i class='fa fa-edit' onclick=\"setData(" + rowData.styleItemId + ")\"> </i></td>"));

  return row;
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
  document.getElementById("warningAlert").innerHTML = "<strong>Warning!</strong> "+message+"..";
  element.show();
  setTimeout(() => {
    element.toggle('fade');
  }, 2500);
}

function dangerAlert(message) {
  var element = $(".alert");
  element.hide();
  element = $(".alert-danger");
  document.getElementById("dangerAlert").innerHTML = "<strong>Duplicate!</strong> "+message+"..";
  element.show();
  setTimeout(() => {
    element.toggle('fade');
  }, 2500);
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

