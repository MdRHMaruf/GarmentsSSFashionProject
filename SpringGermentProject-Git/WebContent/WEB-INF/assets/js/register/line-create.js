

window.onload = ()=>{
	document.title = "Line Create";
} 

let departmentsByFactoryId  = JSON;
let departmentIdForSet = 0;
function loadData(){
  
    $.ajax({
        type: 'GET',
        dataType: 'json',
        url: './departmentLoadByFactory',
        data: {},
        success: function (obj) {
          departmentsByFactoryId = [];
          departmentsByFactoryId = obj.departmentList;
        }
    });
}

window.onload = loadData;
function saveAction() {
  let factoryId = $("#factoryName").val().trim();
  let departmentId = $("#departmentName").val().trim();
  let lineName = $("#lineName").val().trim();
  let userId = $("#userId").val();

  if (factoryId != '0') {
    if (departmentId != '0') {
      if (lineName != '') {
        $.ajax({
          type: 'POST',
          dataType: 'json',
          url: './saveLine',
          data: {
            lineId: "0",
            factoryId: factoryId,
            departmentId: departmentId,
            lineName: lineName,
            userId: userId
          },
          success: function (data) {
            if (data.result == "Something Wrong") {
              dangerAlert("Something went wrong");
            } else if (data.result == "duplicate") {
              dangerAlert("Duplicate Line Name..This Line Name Already Exist")
            } else {
              successAlert("Line Name Save Successfully");

              $("#dataList").empty();
              $("#dataList").append(drawDataTable(data.result));

            }
          }
        });
      } else {
        warningAlert("Empty Line Name... Please Enter Line Name");
      }
    } else {
      warningAlert("Empty Department Name... Please Select a Department Name");
    }
  } else {
    warningAlert("Empty Factory Name... Please Select a Factory Name");
  }
}


function editAction() {
  let lineId = $("#lineId").val().trim();
  let factoryId = $("#factoryName").val().trim();
  let departmentId = $("#departmentName").val().trim();
  let lineName = $("#lineName").val().trim();
  let userId = $("#userId").val();

  if (factoryId != '0') {
    if (departmentId != '0') {
      if (lineName != '') {

        $.ajax({
          type: 'POST',
          dataType: 'json',
          url: './editLine',
          data: {
            lineId: lineId,
            factoryId: factoryId,
            departmentId: departmentId,
            lineName: lineName,
            userId: userId
          },
          success: function (data) {
            if (data.result == "Something Wrong") {
              dangerAlert("Something went wrong");
            } else if (data.result == "duplicate") {
              dangerAlert("Duplicate Line Name..This Line Name Allreary Exist")
            } else {
              successAlert("Line Name Edit Successfully");

              $("#dataList").empty();
              $("#dataList").append(drawDataTable(data.result));

            }
          }
        });
      } else {
        warningAlert("Empty Line Name... Please Enter Line Name");
      }
    } else {
      warningAlert("Empty Department Name... Please Select a Department Name");
    }
  } else {
    warningAlert("Empty Factory Name... Please Select a Factory Name");
  }
}


function refreshAction() {
  location.reload();
  /*let element = $(".alert");
  element.hide();
  document.getElementById("lineId").value = "0";
  document.getElementById("factoryName").value = "";
  document.getElementById("lineName").value = "";
  document.getElementById("btnSave").disabled = false;
  document.getElementById("btnEdit").disabled = true;*/
}

function loadDepartmentByFactory() {
  let factoryId = $("#factoryName").val().trim();
  
  let length= departmentsByFactoryId['factId'+factoryId].length;
  let options = "<option value='0'>Select Department</option>";
  
  for(let i=0;i<length;i++){
    options += "<option value='"+departmentsByFactoryId['factId'+factoryId][i].departmentId+"'>"+departmentsByFactoryId['factId'+factoryId][i].departmentName+"</option>"
  }
  
  document.getElementById("departmentName").innerHTML = options;
  document.getElementById("departmentName").value = departmentIdForSet;
  departmentIdForSet = 0;
  
}

function setData(factoryId, departmentId, lineId) {


  document.getElementById("lineId").value = lineId;
  departmentIdForSet = departmentId;
  document.getElementById("factoryName").value = factoryId;
  $("#factoryName").change();
  document.getElementById("lineName").value = document.getElementById("lineName" + lineId).innerHTML;
  document.getElementById("btnSave").disabled = true;
  document.getElementById("btnEdit").disabled = false;

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
  row.append($("<td>" + rowData.lineId + "</td>"));
  row.append($("<td id='factoryName" + rowData.lineId + "'>" + rowData.factoryName + "</td>"));
  row.append($("<td id='departmentName" + rowData.lineId + "'>" + rowData.departmentName + "</td>"));
  row.append($("<td id='lineName" + rowData.lineId + "'>" + rowData.lineName + "</td>"));
  row.append($("<td ><i class='fa fa-edit' onclick=\"setData(" + rowData.factoryId + "," + rowData.departmentId + "," + rowData.lineId + ")\"> </i></td>"));

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

