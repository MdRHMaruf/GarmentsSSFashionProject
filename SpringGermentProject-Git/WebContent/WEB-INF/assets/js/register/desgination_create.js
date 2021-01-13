
window.onload = ()=>{
  document.title = "Designation Create";
  allDesignation();
} 

let id;
function saveAction() {
	
  let departmentId = $("#departmentName").val();
  let designation = $("#designation").val();
  let userId = $("#userId").val();

  if (departmentName != '' & designation != '') {
    $.ajax({
      type: 'POST',
      dataType: 'json',
      url: './saveDesignation',
      data: {
	
        departmentId: departmentId,
        designation: designation,
        userId: userId

      },
      success: function (data) {
        if (data.result == "Something Wrong") {
          dangerAlert("Something went wrong");
        } else if (data.result == "duplicate") {
          dangerAlert("Duplicate Designation..This Designation Allreary Exist")
        } else {
          successAlert("Designation Save Successfully");

          $("#designationList").empty();
         // $("#designationList").append(drawDataTable(data.result));
			allDesignation();
        }
      }
    });
  } else {
    warningAlert("Empty Designation ... Please Enter Designation");
  }
}


function editAction() {
	
  let departmentId = $("#departmentName").val();
  let designation = $("#designation").val();
  let userId = $("#userId").val();

  if (departmentName != '' & designation != '') {
    $.ajax({
      type: 'POST',
      dataType: 'json',
      url: './editDesignation',
      data: {
	
			departmentId: departmentId,
			id:id,
        	designation: designation,
        	userId: userId
      },
      success: function (data) {
        if (data.result == "Something Wrong") {
          dangerAlert("Something went wrong");
        } else if (data.result == "duplicate") {
          dangerAlert("Duplicate Designation..This Designation Name Allreary Exist")
        } else {
          successAlert("Designation Edit Successfully");

          $("#designationList").empty();
         /* $("#designationList").append(drawDataTable(data.result));*/
			allDesignation();
        }
      }
    });
  } else {
    warningAlert("Empty Designation... Please Enter Designation");
  }
}


function refreshAction() {
  location.reload();
}

function allDesignation(){
	 $.ajax({
      type: 'POST',
      dataType: 'json',
      url: './allDesignation',
      data: {
      },
     success: function (data) {
			$("#designationList").empty();
  			patchdata(data.result);
      }
    });	
}

function patchdata(data){
	let rows = [];
	
	for (let i = 0; i < data.length; i++) {
		rows.push(drawRow(data[i],i+1));

	}

	$("#designationList").append(rows);
}

function drawRow(rowData,c) {
	
	let row = $("<tr />")
	row.append($("<td>" + c + "</td>"));
	row.append($("<td id='rowData.departmentId'>" + rowData.departmentName+ "</td>"));
	row.append($("<td>" + rowData.designationId+ "</td>"));
	row.append($("<td>" + rowData.designationName+ "</td>"));
	row.append($("<td ><i class='fa fa-edit' onclick=setData('"+encodeURIComponent(rowData.departmentId)+"','"+encodeURIComponent(rowData.designationId)+"','"+encodeURIComponent(rowData.designationName)+"')> </i></td>"));
	

	return row;
}

function setData(departmentId,designationId,designationName){
	
	id = decodeURIComponent(designationId);
	departmentId = decodeURIComponent(departmentId);
	let designation = decodeURIComponent(designationName);
	$("#departmentName").val(departmentId).change();
	$("#designation").val(designation);
	
	$("#btnSave").hide();
  $("#btnEdit").show();

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
    $("#designationList tr").filter(function () {
      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
    });
  });
});

