



function saveAction() {
  var factoryId = $("#factoryName").val().trim();
  var departmentName = $("#departmentName").val().trim();
  var userId = $("#userId").val();

  if (factoryId != '0') {
    if (departmentName != '') {
      $.ajax({
        type: 'POST',
        dataType: 'json',
        url: './saveDepartment',
        data: {
          departmentId: "0",
          factoryId: factoryId,
          departmentName: departmentName,
          userId: userId
        },
        success: function (data) {
          if (data.result == "Something Wrong") {
            dangerAlert("Something went wrong");
          } else if (data.result == "duplicate") {
            dangerAlert("Duplicate Department Name..This Department Name Allreary Exist")
          } else {
            successAlert("Department Name Save Successfully");

            $("#dataList").empty();
            $("#dataList").append(drawDataTable(data.result));

          }
        }
      });
    } else {
      warningAlert("Empty Department Name... Please Enter Department Name");
    }
  } else {
    warningAlert("Empty Factory Name... Please Select a Factory Name");
  }
}


function editAction() {
  var departmentId = $("#departmentId").val();
  var factoryId = $("#factoryName").val().trim();
  var departmentName = $("#departmentName").val().trim();
  var userId = $("#userId").val();

  if (factoryId != '0') {
    if (departmentName != '') {

      $.ajax({
        type: 'POST',
        dataType: 'json',
        url: './editDepartment',
        data: {
          departmentId: departmentId,
          factoryId: factoryId,
          departmentName: departmentName,
          userId: userId
        },
        success: function (data) {
          if (data.result == "Something Wrong") {
            dangerAlert("Something went wrong");
          } else if (data.result == "duplicate") {
            dangerAlert("Duplicate Department Name..This Department Name Allreary Exist")
          } else {
            successAlert("Department Name Edit Successfully");

            $("#dataList").empty();
            $("#dataList").append(drawDataTable(data.result));

          }
        }
      });
    } else {
      warningAlert("Empty Department Name... Please Enter Department Name");
    }
  } else {
    warningAlert("Empty Factory Name... Please Select a Factory Name");
  }
}


function refreshAction() {
  location.reload();
  /*var element = $(".alert");
  element.hide();
  document.getElementById("departmentId").value = "0";
  document.getElementById("factoryName").value = "";
  document.getElementById("departmentName").value = "";
  document.getElementById("btnSave").disabled = false;
  document.getElementById("btnEdit").disabled = true;*/
}


function setData(departmentId,factoryId) {


  document.getElementById("departmentId").value = departmentId;
  document.getElementById("factoryName").value = factoryId;
  document.getElementById("departmentName").value = document.getElementById("departmentName" + departmentId).innerHTML;
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
  row.append($("<td>" + rowData.departmentId + "</td>"));
  row.append($("<td id='factoryName" + rowData.departmentId + "'>" + rowData.factoryName + "</td>"));
  row.append($("<td id='departmentName" + rowData.departmentId + "'>" + rowData.departmentName + "</td>"));
  row.append($("<td ><i class='fa fa-edit' onclick=\"setData(" + rowData.departmentId + "," + rowData.factoryId + ")\"> </i></td>"));

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
  document.getElementById("warningAlert").innerHTML = "<strong>Warning!</strong> " + message + "..";
  element.show();
  setTimeout(() => {
    element.toggle('fade');
  }, 2500);
}

function dangerAlert(message) {
  var element = $(".alert");
  element.hide();
  element = $(".alert-danger");
  document.getElementById("dangerAlert").innerHTML = "<strong>Duplicate!</strong> " + message + "..";
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

