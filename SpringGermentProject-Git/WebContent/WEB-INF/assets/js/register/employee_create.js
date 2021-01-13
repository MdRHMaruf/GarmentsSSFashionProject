
window.onload = ()=>{
  document.title = "Employee Create";
  allEmployee();
  
} 
function saveAction() {
	
  var employeeCode = $("#employeeCode").val();
  var employeeName = $("#employeeName").val();
  var cardNo = $("#cardNo").val();
  var department = $("#department").val();
  var designation = $("#designation").val();
  var line = $("#line").val();
  var grade = $("#grade").val();
  var joinDate = $("#joinDate").val();
  var userId = $("#userId").val();

  if (employeeCode != '' && employeeName != '') {
    $.ajax({
      type: 'POST',
      dataType: 'json',
      url: './saveEmployee',
      data: {
	
		employeeCode:employeeCode,
		employeeName:employeeName,
		cardNo:cardNo,
		department:department,
		designation:designation,
		line:line,
		grade:grade,
		joinDate:joinDate,
        userId: userId

      },
      success: function (data) {
        if (data.result == "Something Wrong") {
          dangerAlert("Something went wrong");
        } else if (data.result == "duplicate") {
          dangerAlert("Duplicate Employee Code..This Employee Allreary Exist")
        } else {
          successAlert("Employee Save Successfully");

          $("#empList").empty();
			allEmployee();
        }
      }
    });
  } else {
    warningAlert("Empty Employee ... Please Enter Employee");
  }
}


function editAction() {
	
  var employeeCode = $("#employeeCode").val();
  var employeeName = $("#employeeName").val();
  var cardNo = $("#cardNo").val();
  var department = $("#department").val();
  var designation = $("#designation").val();
  var line = $("#line").val();
  var grade = $("#grade").val();
  var joinDate = $("#joinDate").val();
  var userId = $("#userId").val();

  //if (designation != '') {
    $.ajax({
      type: 'POST',
      dataType: 'json',
      url: './editEmployee',
      data: {
			
			employeeCode:employeeCode,
			employeeName:employeeName,
			cardNo:cardNo,
			department:department,
			designation:designation,
			line:line,
			grade:grade,
			joinDate:joinDate,
	        userId: userId
      },
      success: function (data) {
        if (data.result == "Something Wrong") {
          dangerAlert("Something went wrong");
        } else if (data.result == "duplicate") {
          dangerAlert("Duplicate Employee..This Employee Code Allreary Exist")
        } else {
          successAlert("Employee Edit Successfully");

          $("#empList").empty();
         /* $("#designationList").append(drawDataTable(data.result));*/
			allEmployee();
        }
      }
    });
  //} else {
 //   warningAlert("Empty Employee... Please Enter Employee");
  //}
}


function refreshAction() {
  location.reload();
}

function allEmployee(){
	 $.ajax({
      type: 'POST',
      dataType: 'json',
      url: './allEmployee',
      data: {
      },
     success: function (data) {
			$("#empList").empty();
  			patchdata(data.result);
      }
    });	
}

function patchdata(data){
	var rows = [];
	
	for (var i = 0; i < data.length; i++) {
		rows.push(drawRow(data[i],i+1));

	}

	$("#empList").append(rows);
}

function drawRow(rowData,c) {
	
	var row = $("<tr />")
	row.append($("<td>" + c + "</td>"));
	row.append($("<td>" + rowData.EmployeeName+ "</td>"));
	row.append($("<td>" + rowData.Department+ "</td>"));
	row.append($("<td>" + rowData.Designation+ "</td>"));
	row.append($("<td ><i class='fa fa-edit' onclick=setData('"+encodeURIComponent(rowData.EmployeeName)+"','"+encodeURIComponent(rowData.DepartmentId)+"','"+encodeURIComponent(rowData.DesignationId)+"','"+encodeURIComponent(rowData.EmployeeCode)+"','"+encodeURIComponent(rowData.CardNo)+"','"+encodeURIComponent(rowData.Line)+"','"+encodeURIComponent(rowData.Grade)+"','"+encodeURIComponent(rowData.JoinDate)+"') style='cursor : pointer;'> </i></td>"));
	

	return row;
}

function setData(EmployeeName,Department,Designation,EmployeeCode,CardNo,Line,Grade,JoinDate){
	
	var EmployeeName = decodeURIComponent(EmployeeName);
	var Department = decodeURIComponent(Department);
	var Designation = decodeURIComponent(Designation);
	
	var EmployeeCode = decodeURIComponent(EmployeeCode);
	var CardNo = decodeURIComponent(CardNo);
	var Line = decodeURIComponent(Line);
	var Grade = decodeURIComponent(Grade);
	var JoinDate = decodeURIComponent(JoinDate);
	
	$("#employeeName").val(EmployeeName);
	$("#department").val(Department).change();
	$("#designation").val(Designation).change();
	
	$("#employeeCode").val(EmployeeCode);
	$("#cardNo").val(CardNo);
	$("#line").val(Line);
	$("#grade").val(Grade);
	$("#joinDate").val(JoinDate);
	
	document.getElementById("employeeCode").disabled = true;
	$("#btnSave").hide();
  $("#btnEdit").show();

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
    $("#empList tr").filter(function () {
      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
    });
  });
});

