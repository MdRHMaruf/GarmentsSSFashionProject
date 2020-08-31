



function saveAction() {
  var sampleTypeName = $("#sampleTypeName").val().trim();
  var userId = $("#userId").val();

  if (sampleTypeName != '') {
    $.ajax({
      type: 'POST',
      dataType: 'json',
      url: './saveSampleType',
      data: {
        sampleTypeId: "0",
        sampleTypeName: sampleTypeName,
        userId: userId
      },
      success: function (data) {
        if (data.result == "Something Wrong") {
          dangerAlert("Something went wrong");
        } else if (data.result == "duplicate") {
          dangerAlert("Duplicate Sample Type Name..This Sample Type Name Allreary Exist")
        } else {
          successAlert("Sample Type Name Save Successfully");

          $("#dataList").empty();
          $("#dataList").append(drawDataTable(data.result));

        }
      }
    });
  } else {
    warningAlert("Empty Sample Type Name... Please Enter Sample Type Name");
  }
}


function editAction() {
  var sampleTypeId = $("#sampleTypeId").val();
  var sampleTypeName = $("#sampleTypeName").val().trim();
  var userId = $("#userId").val();

  if (sampleTypeName != '') {
    $.ajax({
      type: 'POST',
      dataType: 'json',
      url: './editSampleType',
      data: {
        sampleTypeId: sampleTypeId,
        sampleTypeName: sampleTypeName,
        userId: userId
      },
      success: function (data) {
        if (data.result == "Something Wrong") {
          dangerAlert("Something went wrong");
        } else if (data.result == "duplicate") {
          dangerAlert("Duplicate Sample Type Name..This Sample Type Name Allreary Exist")
        } else {
          successAlert("Sample Type Name Edit Successfully");

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


function setData(sampleTypeId) {


  document.getElementById("sampleTypeId").value = sampleTypeId;
  document.getElementById("sampleTypeName").value = document.getElementById("sampleTypeName" + sampleTypeId).innerHTML;
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
  row.append($("<td>" + rowData.sampleTypeId + "</td>"));
  row.append($("<td id='sampleTypeName" + rowData.sampleTypeId + "'>" + rowData.sampleTypeName + "</td>"));
  row.append($("<td ><i class='fa fa-edit' onclick=\"setData(" + rowData.sampleTypeId + ")\"> </i></td>"));

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

