var id;
function saveAction() {
	
  var departmentId = $("#departmentName").val();
  var designation = $("#designation").val();
  var userId = $("#userId").val();
  
	console.log("designation : "+designation)

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
	
  var departmentId = $("#departmentName").val();
  var designation = $("#designation").val();
  var userId = $("#userId").val();

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
	var rows = [];
	
	for (var i = 0; i < data.length; i++) {
		rows.push(drawRow(data[i],i+1));

	}

	$("#designationList").append(rows);
}

function drawRow(rowData,c) {
	
	var row = $("<tr />")
	row.append($("<td>" + c + "</td>"));
	row.append($("<td id='rowData.departmentId'>" + rowData.departmentName+ "</td>"));
	row.append($("<td>" + rowData.designationId+ "</td>"));
	row.append($("<td>" + rowData.designationName+ "</td>"));
	row.append($("<td ><i class='fa fa-edit' onclick=setData('"+encodeURIComponent(rowData.departmentId)+"','"+encodeURIComponent(rowData.designationId)+"','"+encodeURIComponent(rowData.designationName)+"')> </i></td>"));
	

	return row;
}

function setData(departmentId,designationId,designationName){
	
	id = decodeURIComponent(designationId);
	var departmentId = decodeURIComponent(departmentId);
	var designation = decodeURIComponent(designationName);
	var departmentName = decodeURIComponent(departmentName);
	$("#departmentName").val(departmentId).change();
	$("#designation").val(designation);
	
	document.getElementById("btnSave").disabled = true;
    document.getElementById("btnEdit").disabled = false;

}

function successAlert(message) {
  var element = $(".alert");
  element.hide();
  element = $(".alert-success");
  document.getElementById("successAlert").innerHTML = "<strong>Success!</strong> " + message + "...";
  element.show();
}

function warningAlert(message) {
  var element = $(".alert");
  element.hide();
  element = $(".alert-warning");
  document.getElementById("warningAlert").innerHTML = "<strong>Warning!</strong> "+message+"..";
  element.show();
}

function dangerAlert(message) {
  var element = $(".alert");
  element.hide();
  element = $(".alert-danger");
  document.getElementById("dangerAlert").innerHTML = "<strong>Duplicate!</strong> "+message+"..";
  element.show();
}

$(document).ready(function () {
  $("input:text").focus(function () { $(this).select(); });
});

$(document).ready(function () {
  $("#search").on("keyup", function () {
    var value = $(this).val().toLowerCase();
    $("#designationList tr").filter(function () {
      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
    });
  });
});

