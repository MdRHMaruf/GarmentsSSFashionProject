



function saveAction() {
  var countryName = $("#countryName").val().trim();
  var userId = $("#userId").val();

  if (countryName != '') {
    $.ajax({
      type: 'POST',
      dataType: 'json',
      url: './saveCountry',
      data: {
        countryId: "0",
        countryName: countryName,
        userId: userId
      },
      success: function (data) {
        if (data.result == "Something Wrong") {
          dangerAlert("Something went wrong");
        } else if (data.result == "duplicate") {
          dangerAlert("Duplicate Country Name..This Country Name Allreary Exist")
        } else {
          successAlert("Country Name Save Successfully");

          $("#dataList").empty();
          $("#dataList").append(drawDataTable(data.result));

        }
      }
    });
  } else {
    warningAlert("Empty Country Name... Please Enter Country Name");
  }
}


function editAction() {
  var countryId = $("#countryId").val();
  var countryName = $("#countryName").val().trim();
  var userId = $("#userId").val();

  if (countryName != '') {
    $.ajax({
      type: 'POST',
      dataType: 'json',
      url: './editCountry',
      data: {
        countryId: countryId,
        countryName: countryName,
        userId: userId
      },
      success: function (data) {
        if (data.result == "Something Wrong") {
          dangerAlert("Something went wrong");
        } else if (data.result == "duplicate") {
          dangerAlert("Duplicate Country Name..This Country Name Allreary Exist")
        } else {
          successAlert("Country Name Edit Successfully");

          $("#dataList").empty();
          $("#dataList").append(drawDataTable(data.result));

        }
      }
    });
  } else {
    warningAlert("Empty Country Name... Please Enter Country Name");
  }
}


function refreshAction() {
  location.reload();
  /*var element = $(".alert");
  element.hide();
  document.getElementById("countryId").value = "0";
  document.getElementById("countryName").value = "";
  document.getElementById("btnSave").disabled = false;
  document.getElementById("btnEdit").disabled = true;*/
}


function setData(countryId) {


  document.getElementById("countryId").value = countryId;
  document.getElementById("countryName").value = document.getElementById("countryName" + countryId).innerHTML;
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
  row.append($("<td>" + rowData.countryId + "</td>"));
  row.append($("<td id='countryName" + rowData.countryId + "'>" + rowData.countryName + "</td>"));
  row.append($("<td ><i class='fa fa-edit' onclick=\"setData(" + rowData.countryId + ")\"> </i></td>"));

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

