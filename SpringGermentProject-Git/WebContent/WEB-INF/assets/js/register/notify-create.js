

window.onload = ()=>{
	document.title = "Notify Create";
} 


function saveAction() {
  let buyerId = $("#buyerName").val().trim();
  let notifyName = $("#notifyName").val().trim();
  let notifyAddress = $("#notifyAddress").val().trim();
  let country = $("#country").val().trim();
  let telephone = $("#telephone").val().trim();
  let email = $("#email").val().trim();
  let userId = $("#userId").val();

  if (buyerId != '0') {
    if (notifyName != '') {
      if(confirm("Are you sure to Save This Notify?")){
        $("#loader").show();
        $.ajax({
          type: 'POST',
          dataType: 'json',
          url: './saveNotify',
          data: {
            id: "0",
            buyerId: buyerId,
            name: notifyName,
            address: notifyAddress,
            country: country,
            telephone: telephone,
            email: email,
            userId: userId
          },
          success: function (data) {
            if (data.result == "Something Wrong") {
              dangerAlert("Something went wrong");
            } else if (data.result == "duplicate") {
              dangerAlert("Duplicate Notify Name..This Notify Name All ready Exist")
            } else {
              successAlert("Notify Name Save Successfully");
  
              $("#dataList").empty();
              $("#dataList").append(drawDataTable(data.notifyerList));
              $("#loader").hide();
            }
          }
        });
      }
      
    } else {
      warningAlert("Empty Notify Name... Please Enter Notify Name");
    }
  } else {
    warningAlert("Empty Factory Name... Please Select a Factory Name");
  }
}


function editAction() {
  let notifyId = $("#notifyId").val();
  let buyerId = $("#buyerName").val().trim();
  let notifyName = $("#notifyName").val().trim();
  let notifyAddress = $("#notifyAddress").val().trim();
  let country = $("#country").val().trim();
  let telephone = $("#telephone").val().trim();
  let email = $("#email").val().trim();
  let userId = $("#userId").val();

  if (buyerId != '0') {
    if (notifyName != '') {
      if(confirm("Are you sure to Edit This Notify?")){
        $("#loader").show();
        $.ajax({
          type: 'POST',
          dataType: 'json',
          url: './editNotify',
          data: {
            id: notifyId,
            buyerId: buyerId,
            name: notifyName,
            address: notifyAddress,
            country: country,
            telephone: telephone,
            email: email,
            userId: userId
          },
          success: function (data) {
            if (data.result == "Something Wrong") {
              dangerAlert("Something went wrong");
            } else if (data.result == "duplicate") {
              dangerAlert("Duplicate Notify Name..This Notify Name All ready Exist")
            } else {
              successAlert("Notify Name Save Successfully");
  
              $("#dataList").empty();
              $("#dataList").append(drawDataTable(data.notifyerList));
              $("#loader").hide();
            }
          }
        });
      }
      
    } else {
      warningAlert("Empty Notify Name... Please Enter Notify Name");
    }
  } else {
    warningAlert("Empty Factory Name... Please Select a Factory Name");
  }
}


function refreshAction() {
  location.reload();
  /*let element = $(".alert");
  element.hide();
  document.getElementById("notifyId").value = "0";
  document.getElementById("factoryName").value = "";
  document.getElementById("notifyName").value = "";
  document.getElementById("btnSave").disabled = false;
  document.getElementById("btnEdit").disabled = true;*/
}


function setData(notifyId) {

  $("#loader").show();
  $.ajax({
    type: 'GET',
    dataType: 'json',
    url: './getNotifyInfo',
    data: {
      notifyId: notifyId
    },
    success: function (data) {
      
        let notify = data.notifyInfo;
        $("#notifyId").val(notify.id);
        $("#buyerName").val(notify.buyerId).change();
        $("#notifyName").val(notify.name);
        $("#notifyAddress").val(notify.address);
        $("#country").val(notify.country);
        $("#telephone").val(notify.telephone);
        $("#email").val(notify.email);
        
        //$("#dataList").empty();
        //$("#dataList").append(drawDataTable(data.result));
        $("#btnSave").hide();
        $("#btnEdit").show();
        $("#loader").hide();
      
    }
  });
 

}

function drawDataTable(data) {
  let rows = '';
  let length = data.length;

  for (let i = 0; i < length; i++) {
    let notifyer = data[i];
    rows += `<tr>
    <td>${i+1}</td>
    <td>${notifyer.name}</td>
    <td>${notifyer.country}</td>
    <td>${notifyer.address}</td>
    <td><i class="fa fa-edit"
      onclick="setData(${notifyer.id})"
      style="cursor: pointer;"> </i></td>
  </tr>`;
  }

  return rows;
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
  document.getElementById("warningAlert").innerHTML = "<strong>Warning!</strong> " + message + "..";
  element.show();
  setTimeout(() => {
    element.toggle('fade');
  }, 2500);
}

function dangerAlert(message) {
  let element = $(".alert");
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
    let value = $(this).val().toLowerCase();
    $("#dataList tr").filter(function () {
      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
    });
  });
});

