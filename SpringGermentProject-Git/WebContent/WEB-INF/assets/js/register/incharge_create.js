
function saveAction() {

	
  var name = $("#name").val();
  var factoryId = $("#factoryname").val();
  var depId = $("#departmentname").val();
  var telephone = $("#telephone").val();
  var mobile = $("#mobile").val();
  var fax = $("#fax").val();
  var email = $("#email").val();
  var skype = $("#skype").val();
  var address = $("#address").val();
  var userId = $("#userId").val();
  

  if (name != '') {
    $.ajax({
      type: 'POST',
      dataType: 'json',
      url: './saveIncharge',
      data: {
    	inchargeId: "0",
        name: name,
        factoryId:factoryId,
        depId:depId,
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
          dangerAlert("Duplicate Incharge Name..This Incharge Name Allreary Exist")
        } else {
          successAlert("Incharge Item Name Save Successfully");

          $("#dataList").empty();
          $("#dataList").append(drawDataTable(data.result));

        }
      }
    });
  } else {
    warningAlert("Empty Incharge Name... Please Enter Incharge Name");
  }
}


function editAction() {
  var inchargeId = $("#inchargeId").val();
  var name = $("#name").val();
  var factoryId = $("#factoryId").val();
  var depId = $("#depId").val();
  var telephone = $("#telephone").val();
  var mobile = $("#mobile").val();
  var fax = $("#fax").val();
  var email = $("#email").val();
  var skype = $("#skype").val();
  var address = $("#address").val();
  var userId = $("#userId").val();

  if (name != '') {
    $.ajax({
      type: 'POST',
      dataType: 'json',
      url: './editIncharge',
      data: {
    	inchargeId: inchargeId,
    	name: name,
    	factoryId:factoryId,
    	depId:depId,
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
          dangerAlert("Duplicate Incharge Name..This Incharge Name Allreary Exist")
        } else {
          successAlert("Incharge Name Edit Successfully");

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


function setData(inchargeId) {


  document.getElementById("inchargeId").value = inchargeId;
  document.getElementById("name").value = document.getElementById("name" + inchargeId).innerHTML;
  document.getElementById("telephone").value = document.getElementById("telephone" + inchargeId).innerHTML;
  document.getElementById("mobile").value = document.getElementById("mobile" + inchargeId).value;
  document.getElementById("fax").value = document.getElementById("fax" + inchargeId).value;
  document.getElementById("email").value = document.getElementById("email" + inchargeId).value;
  document.getElementById("skype").value = document.getElementById("skype" + inchargeId).value;
  document.getElementById("address").value = document.getElementById("address" + inchargeId).value;
  document.getElementById("factoryId").value = document.getElementById("factory" + inchargeId).value;
  document.getElementById("depId").value = document.getElementById("depId" + inchargeId).value;
  document.getElementById("btnSave").disabled = true;
  document.getElementById("btnEdit").disabled = false;

}

function drawDataTable(data) {
  var rows = [];
  var length = data.length;

  for (var i = 0; i < length; i++) {
    rows.push(drawRowDataTable(data[i], i+1));
  }

  return rows;
}

function drawRowDataTable(rowData, c) {

  var row = $("<tr />")
  row.append($("<td>" + c + "</td>"));
  row.append($("<td id='name" + rowData.InchargeId + "'>" + rowData.Name + "</td>"));
  row.append($("<td id='telephone" + rowData.InchargeId + "'>" + rowData.Telephone + "</td>"));
  row.append($("<td ><i class='fa fa-edit' onclick=\"setData(" + rowData.InchargeId + ")\"> </i></td>"));

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

