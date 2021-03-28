

window.onload = ()=>{
	document.title = "Bank Create";
} 


function saveAction() {
  let bankName = $("#bankName").val().trim();
  let branchName = $("#branch").val().trim();
  let bankAddress = $("#bankAddress").val().trim();
  let userId = $("#userId").val();

 
    if (bankName != '') {
      if(confirm("Are you sure to Save This Bank?")){
        $("#loader").show();
        $.ajax({
          type: 'POST',
          dataType: 'json',
          url: './saveBank',
          data: {
            id: "0",
            bankName: bankName,
            branchName: branchName,
            address: bankAddress,
            userId: userId
          },
          success: function (data) {
            if (data.result == "Something Wrong") {
              dangerAlert("Something went wrong");
            } else if (data.result == "duplicate") {
              dangerAlert("Duplicate Bank Name..This Bank Name All ready Exist")
            } else {
              successAlert("Bank Name Save Successfully");
  
              $("#dataList").empty();
              $("#dataList").append(drawDataTable(data.bankList));
              $("#loader").hide();
            }
          }
        });
      }
      
    } else {
      warningAlert("Empty Bank Name... Please Enter Bank Name");
    }
  
}


function editAction() {
  let bankId = $("#bankId").val();
  let bankName = $("#bankName").val().trim();
  let branchName = $("#branch").val().trim();
  let bankAddress = $("#bankAddress").val().trim();
  let userId = $("#userId").val();

  
    if (bankName != '') {
      if(confirm("Are you sure to Edit This Bank?")){
        $("#loader").show();
        $.ajax({
          type: 'POST',
          dataType: 'json',
          url: './editBank',
          data: {
            id: bankId,
            bankName: bankName,
            branchName: branchName,
            address: bankAddress,
            userId: userId
          },
          success: function (data) {
            if (data.result == "Something Wrong") {
              dangerAlert("Something went wrong");
            } else if (data.result == "duplicate") {
              dangerAlert("Duplicate Bank Name..This Bank Name All ready Exist")
            } else {
              successAlert("Bank Name Save Successfully");
  
              $("#dataList").empty();
              $("#dataList").append(drawDataTable(data.bankList));
              $("#loader").hide();
            }
          }
        });
      }
      
    } else {
      warningAlert("Empty Bank Name... Please Enter Bank Name");
    }
  
}


function refreshAction() {
  location.reload();
  /*let element = $(".alert");
  element.hide();
  document.getElementById("bankId").value = "0";
  document.getElementById("factoryName").value = "";
  document.getElementById("bankName").value = "";
  document.getElementById("btnSave").disabled = false;
  document.getElementById("btnEdit").disabled = true;*/
}


function setData(bankId) {

  $("#loader").show();
  $.ajax({
    type: 'GET',
    dataType: 'json',
    url: './getBankInfo',
    data: {
      bankId: bankId
    },
    success: function (data) {
      
        let bank = data.bankInfo;
        $("#bankId").val(bank.id);
        $("#bankName").val(bank.bankName);
        $("#branch").val(bank.branchName);
        $("#bankAddress").val(bank.address);
        
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
    let banker = data[i];
    rows += `<tr>
    <td>${i+1}</td>
    <td>${banker.bankName}</td>
    <td>${banker.branchName}</td>
    <td>${banker.address}</td>
    <td><i class="fa fa-edit"
      onclick="setData(${banker.id})"
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

