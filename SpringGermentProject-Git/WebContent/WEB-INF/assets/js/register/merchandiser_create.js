
window.onload = ()=>{
	document.title = "Merchandiser Create";
} 

function saveAction() {

	
  let name = $("#name").val();
  let telephone = $("#telephone").val();
  let mobile = $("#mobile").val();
  let fax = $("#fax").val();
  let email = $("#email").val();
  let skype = $("#skype").val();
  let address = $("#address").val();
  let userId = $("#userId").val();
  

  if (name != '') {
    $("#loader").show();
    $.ajax({
      type: 'POST',
      dataType: 'json',
      url: './saveMerchandiser',
      data: {
    	merchendiserId: "0",
        name: name,
        telephone: telephone,
        mobile:mobile,
        fax:fax,
        email:email,
        skype:skype,
        address:address,
        userId: userId
      },
      success: function (data) {
        if (data.result == "Something Wrong") {
          dangerAlert("Something went wrong");
        } else if (data.result == "duplicate") {
          dangerAlert("Duplicate Merchandiser Name..This Merchandiser Name Allreary Exist")
        } else {
          successAlert("Merchandiser Item Name Save Successfully");

          $("#dataList").empty();
          $("#dataList").append(drawDataTable(data.result));

        }
        $("#loader").hide();
      }
    });
  } else {
    warningAlert("Empty Merchandiser Name... Please Enter Merchandiser Name");
  }
}


function editAction() {
  let merchendiserId = $("#merchendiserId").val();
  let name = $("#name").val();
  let telephone = $("#telephone").val();
  let mobile = $("#mobile").val();
  let fax = $("#fax").val();
  let email = $("#email").val();
  let skype = $("#skype").val();
  let address = $("#address").val();
  let userId = $("#userId").val();

  if (name != '') {
    $("#loader").show();
    $.ajax({
      type: 'POST',
      dataType: 'json',
      url: './editMerchandiser',
      data: {
    	merchendiserId: merchendiserId,
    	name: name,
    	telephone: telephone,
    	mobile:mobile,
    	fax:fax,
    	email:email,
    	skype:skype,
    	address:address,
        userId: userId
      },
      success: function (data) {
        if (data.result == "Something Wrong") {
          dangerAlert("Something went wrong");
        } else if (data.result == "duplicate") {
          dangerAlert("Duplicate Merchandiser Name..This Merchandiser Name Allreary Exist")
        } else {
          successAlert("Merchandiser Name Edit Successfully");

          $("#dataList").empty();
          $("#dataList").append(drawDataTable(data.result));

        }
        $("#loader").hide();
      }
    });
  } else {
    warningAlert("Empty Fabrics Item Name... Please Enter Fabrics Item Name");
  }
}


function refreshAction() {
  location.reload();
}


function setData(merchendiserId) {


  document.getElementById("merchendiserId").value = merchendiserId;
  document.getElementById("name").value = document.getElementById("name" + merchendiserId).innerHTML;
  document.getElementById("telephone").value = document.getElementById("telephone" + merchendiserId).innerHTML;
  document.getElementById("mobile").value = document.getElementById("mobile" + merchendiserId).value;
  document.getElementById("fax").value = document.getElementById("fax" + merchendiserId).value;
  document.getElementById("email").value = document.getElementById("email" + merchendiserId).value;
  document.getElementById("skype").value = document.getElementById("skype" + merchendiserId).value;
  document.getElementById("address").value = document.getElementById("address" + merchendiserId).value;
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
  row.append($("<td>" + c + "</td>"));
  row.append($("<td id='name" + rowData.MerchendiserId + "'>" + rowData.Name + "</td>"));
  row.append($("<td id='telephone" + rowData.MerchendiserId + "'>" + rowData.Telephone + "</td>"));
  row.append($("<td ><i class='fa fa-edit' onclick=\"setData(" + rowData.MerchendiserId + ")\"> </i></td>"));

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

