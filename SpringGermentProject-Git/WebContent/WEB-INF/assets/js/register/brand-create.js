window.onload = ()=>{
  document.title = "Brand Create";
 
} 

function saveAction() {
  let brandName = $("#brandName").val().trim();
  let brandCode = $("#brandCode").val().trim();
  let userId = $("#userId").val();

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
  let brandId = $("#brandId").val();
  let brandName = $("#brandName").val().trim();
  let brandCode = $("#brandCode").val().trim();
  let userId = $("#userId").val();

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
}


function setData(brandId) {
  document.getElementById("brandId").value = brandId;
  document.getElementById("brandName").value = document.getElementById("brandName" + brandId).innerHTML;
  document.getElementById("brandCode").value = document.getElementById("brandCode" + brandId).innerHTML;
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
  row.append($("<td>" + rowData.brandId + "</td>"));
  row.append($("<td id='brandName" + rowData.brandId + "'>" + rowData.brandName + "</td>"));
  row.append($("<td id='brandCode" + rowData.brandId + "'>" + rowData.brandCode + "</td>"));
  row.append($("<td ><i class='fa fa-edit' onclick=\"setData(" + rowData.brandId + ")\"> </i></td>"));

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

