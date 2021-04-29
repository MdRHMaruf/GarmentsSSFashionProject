

window.onload = ()=>{
	document.title = "Department Create";
} 


function saveAction() {
  let factoryId = $("#factoryName").val().trim();
  let departmentName = $("#departmentName").val().trim();
  let userId = $("#userId").val();

  if (factoryId != '0') {
    if (departmentName != '') {
      $("#loader").show();
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
            dangerAlert("Duplicate Department Name..This Department Name Already Exist")
          } else {
            successAlert("Department Name Save Successfully");

            $("#dataList").empty();
            $("#dataList").append(drawDataTable(data.result));

          }
          $("#loader").hide();
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
  let departmentId = $("#departmentId").val();
  let factoryId = $("#factoryName").val().trim();
  let departmentName = $("#departmentName").val().trim();
  let userId = $("#userId").val();

  if (factoryId != '0') {
    if (departmentName != '') {
      $("#loader").show();
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
          $("#loader").hide();
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
  /*let element = $(".alert");
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
  row.append($("<td>" + rowData.departmentId + "</td>"));
  row.append($("<td id='factoryName" + rowData.departmentId + "'>" + rowData.factoryName + "</td>"));
  row.append($("<td id='departmentName" + rowData.departmentId + "'>" + rowData.departmentName + "</td>"));
  row.append($("<td ><i class='fa fa-edit' onclick=\"setData(" + rowData.departmentId + "," + rowData.factoryId + ")\"> </i></td>"));

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


let idListMicro = ["factoryName","departmentName","btnSave"];
idListMicro.forEach((id,index)=>{
  $('#'+id).keyup(function(event){
    if(event.keyCode ===13){
      event.preventDefault();
      $("#"+idListMicro[index+1]).focus();
    }
  });
})