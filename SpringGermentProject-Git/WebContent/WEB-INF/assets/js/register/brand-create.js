



function saveAction() {
  var brandName = $("#brandName").val().trim();
  var brandCode = $("#brandCode").val().trim();
  var userId = $("#userId").val();

  if (brandName != '') {
    $.ajax({
      type: 'POST',
      dataType: 'json',
      url: './saveBrand',
      data: {
        brandId: "0",
        brandName: brandName,
        brandCode: brandCode,
        userId: userId
      },
      success: function (data) {
        if (data.result == "Something Wrong") {
          dangerAlert("Something went wrong");
        } else if (data.result == "duplicate") {
          dangerAlert("Duplicate Brand Name..This Brand Name Allreary Exist")
        } else {
          successAlert("Brand Name Save Successfully");

          $("#dataList").empty();
          $("#dataList").append(drawDataTable(data.result));

        }
      }
    });
  } else {
    warningAlert("Empty Brand Name... Please Enter Brand Name");
  }
}


function editAction() {
  var brandId = $("#brandId").val();
  var brandName = $("#brandName").val().trim();
  var brandCode = $("#brandCode").val().trim();
  var userId = $("#userId").val();

  if (brandName != '') {
    $.ajax({
      type: 'POST',
      dataType: 'json',
      url: './editBrand',
      data: {
        brandId: brandId,
        brandName: brandName,
        brandCode: brandCode,
        userId: userId
      },
      success: function (data) {
        if (data.result == "Something Wrong") {
          dangerAlert("Something went wrong");
        } else if (data.result == "duplicate") {
          dangerAlert("Duplicate Brand Name..This Brand Name Allreary Exist")
        } else {
          successAlert("Brand Name Edit Successfully");

          $("#dataList").empty();
          $("#dataList").append(drawDataTable(data.result));

        }
      }
    });
  } else {
    warningAlert("Empty Brand Name... Please Enter Brand Name");
  }
}


function refreshAction() {
  location.reload();
  /*var element = $(".alert");
  element.hide();
  document.getElementById("brandId").value = "0";
  document.getElementById("brandName").value = "";
  document.getElementById("brandCode").value = "";
  document.getElementById("btnSave").disabled = false;
  document.getElementById("btnEdit").disabled = true;*/
}


function setData(brandId) {


  document.getElementById("brandId").value = brandId;
  document.getElementById("brandName").value = document.getElementById("brandName" + brandId).innerHTML;
  document.getElementById("brandCode").value = document.getElementById("brandCode" + brandId).innerHTML;
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
  row.append($("<td>" + rowData.brandId + "</td>"));
  row.append($("<td id='brandName" + rowData.brandId + "'>" + rowData.brandName + "</td>"));
  row.append($("<td id='brandCode" + rowData.brandId + "'>" + rowData.brandCode + "</td>"));
  row.append($("<td ><i class='fa fa-edit' onclick=\"setData(" + rowData.brandId + ")\"> </i></td>"));

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

