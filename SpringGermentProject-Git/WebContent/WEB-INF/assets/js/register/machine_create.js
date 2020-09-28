var machineId;
var lineId=0;
var departmentId=0;

function factoryWiseLine(){
	  var factoryId = $("#factoryId").val();
	  if(factoryId!=0){
		    $.ajax({
		        type: 'GET',
		        dataType: 'json',
		        url: './factorytWiseDepartment/'+factoryId,
		        success: function (data) {
		        	loadDepartment(data.departmentList);
		        }
		      });
	  }
}

function loadDepartment(data){

	var itemList = data;
	var options = "<option id='departmentId' value='0' selected>Select Department</option>";
	var length = itemList.length;
	for(var i=0;i<length;i++) {
		options += "<option id='departmentId' value='"+itemList[i].departmentId+"'>"+itemList[i].departmentName+"</option>";
	};
	document.getElementById("departmentId").innerHTML = options;
	$('.selectpicker').selectpicker('refresh');
	$('#departmentId').val(departmentId).change();
	departmentId=0;

}

function departmentWiseLine(){
	
	  var departmentId = $("#departmentId").val();
	  
	  if(departmentId!=0){
		    $.ajax({
		        type: 'GET',
		        dataType: 'json',
		        url: './departmentWiseLine/'+departmentId,
		        success: function (data) {
		        	loadLine(data.lineList);
		        }
		      });
	  }
}

function loadLine(data){

	var itemList = data;
	var options = "<option id='lineId' value='0' selected>Select Line</option>";
	var length = itemList.length;
	for(var i=0;i<length;i++) {
		options += "<option id='lineId' value='"+itemList[i].lineId+"'>"+itemList[i].lineName+"</option>";
	};
	document.getElementById("lineId").innerHTML = options;
	$('.selectpicker').selectpicker('refresh');
	$('#lineId').val(lineId).change();
	lineId=0;

}

function saveAction() {
 
	var factoryId = $("#factoryId").val();
	var departmentId = $("#departmentId").val();
	var lineId = $("#lineId").val();
	var name = $("#name").val();
	var brand = $("#brand").val();
	var modelNo = $("#modelNo").val();
	var motor = $("#motor").val();
	var empId = $("#employee").val();
	var userId = $("#userId").val();

if(name != ''){
    $.ajax({
      type: 'POST',
      dataType: 'json',
      url: './saveMachine',
      data: {
      factoryId:factoryId,
      departmentId:departmentId,
      lineId:lineId,
      name:name,
	  brand:brand,
	  modelNo:modelNo,
	  motor:motor,
	  employeeId:empId,
      UserId:userId,

      },

		success: function (data) {
        if (data.result == "Something Wrong") {
          dangerAlert("Something went wrong");
        } else if (data.result == "duplicate") {
          dangerAlert("Duplicate Machine Code..This Machine Name Allreary Exist")
        } else {
          successAlert("Machine Save Successfully");

          $("#machineList").empty();
			allMachine();
        }
      }

    });
}
  }


function editAction() {
 
	var name = $("#name").val();
	var brand = $("#brand").val();
	var modelNo = $("#modelNo").val();
	var motor = $("#motor").val();
	var empId = $("#employee").val();
	var userId = $("#userId").val();

if(name != ''){
    $.ajax({
      type: 'POST',
      dataType: 'json',
      url: './editMachine',
      data: {
	
      machineId:machineId,
      name:name,
	  brand:brand,
	  modelNo:modelNo,
	  motor:motor,
	  employeeId:empId,
      UserId:userId,

      },

    success: function (data) {
        if (data.result == "Something Wrong") {
          dangerAlert("Something went wrong");
        } else if (data.result == "duplicate") {
          dangerAlert("Duplicate Machine Name..This Machine Name Allreary Exist")
        } else {
          successAlert("Update Successfully");

          $("#machineList").empty();
			allMachine();
        }
      }

    });
  }
}

function allMachine(){
	 $.ajax({
      type: 'POST',
      dataType: 'json',
      url: './allMachine',
      data: {
      },
     success: function (data) {
			$("#machineList").empty();
  			patchdata(data.result);
      }
    });	
}

function patchdata(data){
	var rows = [];
	
	for (var i = 0; i < data.length; i++) {
		rows.push(drawRow(data[i],i+1));

	}

	$("#machineList").append(rows);
}

function drawRow(rowData,c) {
	console.log("name : "+rowData.Name)
	var row = $("<tr />");
	row.append($("<td>" + c + "</td>"));
	row.append($("<td>" + rowData.Name+ "</td>"));
	row.append($("<td>" + rowData.ModelNo+ "</td>"));
	row.append($("<td>" + rowData.EmployeeName+ "</td>"));
	row.append($("<td ><i class='fa fa-edit' onclick=setData('"+encodeURIComponent(rowData.MachineId)+"','"+encodeURIComponent(rowData.Name)+"','"+encodeURIComponent(rowData.Brand)+"','"+encodeURIComponent(rowData.ModelNo)+"','"+encodeURIComponent(rowData.Motor)+"','"+encodeURIComponent(rowData.EmployeeId)+"','"+encodeURIComponent(rowData.EmployeeName)+"')> </i></td>"));
	

	return row;
}

function setData(MachineId,Name,Brand,ModelNo,Motor,EmployeeId,EmployeeName){
	
	machineId=decodeURIComponent(MachineId);
	var Name=decodeURIComponent(Name);
	console.log("name : "+Name);
	var Brand=decodeURIComponent(Brand);
	var ModelNo=decodeURIComponent(ModelNo);
	var Motor=decodeURIComponent(Motor);
	var EmployeeId=decodeURIComponent(EmployeeId);
	var EmployeeName=decodeURIComponent(EmployeeName);
	
	$("#name").val(Name);
	$("#brand").val(Brand);
	$("#modelNo").val(ModelNo);
	$("#motor").val(Motor);
	$("#employee").val(EmployeeId).change();
	
	
	document.getElementById("btnSave").disabled = true;
    document.getElementById("btnEdit").disabled = false;

}

function refreshAction() {
	
  	location.reload();
	document.getElementById("btnSave").disabled = false;
    document.getElementById("btnEdit").disabled = true;
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
    $("#machineList tr").filter(function () {
      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
    });
  });
});