


window.onload = ()=>{
	document.title = "Local Item Create";
}

function saveAction() {
  let localItemName = $("#localItemName").val().trim();
  let localItemCode = $("#localItemCode").val().trim();
  let userId = $("#userId").val();

  if (localItemName != '') {
    $("#loader").show();
    $.ajax({
      type: 'POST',
      dataType: 'json',
      url: './saveLocalItem',
      data: {
        localItemId: "0",
        localItemName: localItemName,
        localItemCode: localItemCode,
        userId: userId
      },
      success: function (data) {
        if (data.result == "Something Wrong") {
          dangerAlert("Something went wrong");
        } else if (data.result == "duplicate") {
          dangerAlert("Duplicate Loca lItem Name..This Local Item Name Allreary Exist")
        } else {
          successAlert("Local Item Name Save Successfully");

          $("#dataList").empty();
          $("#dataList").append(drawDataTable(data.result));

        }
        $("#loader").hide();
      }
    });
  } else {
    warningAlert("Empty Local Item Name... Please Enter Local Item Name");
  }
}


function editAction() {
  let localItemId = $("#localItemId").val();
  let localItemName = $("#localItemName").val().trim();
  let localItemCode = $("#localItemCode").val().trim();
  let userId = $("#userId").val();

  if (localItemName != '') {
    $("#loader").show();
    $.ajax({
      type: 'POST',
      dataType: 'json',
      url: './editLocalItem',
      data: {
        localItemId: localItemId,
        localItemName: localItemName,
        localItemCode: localItemCode,
        userId: userId
      },
      success: function (data) {
        if (data.result == "Something Wrong") {
          dangerAlert("Something went wrong");
        } else if (data.result == "duplicate") {
          dangerAlert("Duplicate Local Item Name..This Local Item Name Allreary Exist")
        } else {
          successAlert("Local Item Name Edit Successfully");

          $("#dataList").empty();
          $("#dataList").append(drawDataTable(data.result));
        }
        $("#loader").hide();
      }
    });
  } else {
    warningAlert("Empty Local Item Name... Please Enter Local Item Name");
  }
}


function refreshAction() {
  location.reload();
  /*let element = $(".alert");
  element.hide();
  document.getElementById("localItemId").value = "0";
  document.getElementById("localItemName").value = "";
  document.getElementById("localItemCode").value = "";
  document.getElementById("btnSave").disabled = false;
  document.getElementById("btnEdit").disabled = true;*/
}


function setData(localItemId) {


  document.getElementById("localItemId").value = localItemId;
  document.getElementById("localItemName").value = document.getElementById("localItemName" + localItemId).innerHTML;
  document.getElementById("localItemCode").value = document.getElementById("localItemCode" + localItemId).innerHTML;
  $("#btnSave").hide();
  $("#btnEdit").show();

}

function drawDataTable(data) {
  let rows = [];
  let length = data.length;

  for (let i = 0; i < length; i++) {
    rows.push(drawRowDataTable(data[i], i));
  }

  return rows;
}

function drawRowDataTable(rowData, c) {

  let row = $("<tr />")
  row.append($("<td>" + rowData.localItemId + "</td>"));
  row.append($("<td id='localItemName" + rowData.localItemId + "'>" + rowData.localItemName + "</td>"));
  row.append($("<td id='localItemCode" + rowData.localItemId + "'>" + rowData.localItemCode + "</td>"));
  row.append($("<td ><i class='fa fa-edit' onclick=\"setData(" + rowData.localItemId + ")\"> </i></td>"));

  return row;
}

function successAlert(message) {
  let element = $(".alert");
  element.hide();
  element = $(".alert-success");
  document.getElementById("successAlert").innerHTML = "<strong>Success!</strong> " + message + "...";
  element.show();
  setTimeout(() => {
    element.toggle('fade');
  }, 2500);
}

function warningAlert(message) {
  let element = $(".alert");
  element.hide();
  element = $(".alert-warning");
  document.getElementById("warningAlert").innerHTML = "<strong>Warning!</strong> "+message+"..";
  element.show();
  setTimeout(() => {
    element.toggle('fade');
  }, 2500);
}

function dangerAlert(message) {
  let element = $(".alert");
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
    let value = $(this).val().toLowerCase();
    $("#dataList tr").filter(function () {
      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
    });
  });
});

